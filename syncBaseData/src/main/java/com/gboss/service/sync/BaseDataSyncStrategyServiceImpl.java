package com.gboss.service.sync;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Barcode;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Equipcode;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.FeeSimM;
import com.gboss.pojo.FeeSimP;
import com.gboss.pojo.Feeinfobk;
import com.gboss.pojo.Insurer;
import com.gboss.pojo.LinkMan;
import com.gboss.pojo.Model;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Series;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.util.DateUtil;
import com.gboss.util.PropertyUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.TimeHelper;

import ldap.objectClasses.CommonCompany;

@Service("baseDataSyncStrategyService")
public class BaseDataSyncStrategyServiceImpl extends AbstractSyncServiceImpl {
	
	private static int FEE_MON_LOW = 25;
	private static int FEE_MON_HIGH = 50;
	
	static{
		try {
			FEE_MON_LOW = Integer.valueOf(PropertyUtil.getSystemProperty("fee_mon_low"));
			FEE_MON_HIGH = Integer.valueOf(PropertyUtil.getSystemProperty("fee_mon_high"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public BaseDataSyncStrategyServiceImpl(){
		super.setAPI_NAME(SystemConst.HM_BASEDASTA);
	}

	/**
	 * 同步车辆基础信息资料的处理方法
	 * 
	 * @param start_date
	 *            开始时间
	 * @param end_date
	 *            结束时间
	 * @param url
	 *            同步url
	 * @param flag
	 *            是否成功
	 * @param i
	 *            统计次数
	 * @return
	 * @throws SystemException 
	 */
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws SystemException {
		System.err.println("开始同步车辆基础信息");
		for (HashMap<String, Object> hashMap : results) {
			// 车辆识别号码/车架号,17位
			String vin = hashMap.get("vin") == null ? null : hashMap.get("vin").toString();
			if (StringUtils.isNullOrEmpty(vin)) {
				msg = " 海马接口传入的vin码为空!";
				System.out.println(DateUtil.formatNowTime() + msg );
				continue;
			}
			vin = vin.trim();
			// 车载电话
			// call_letter:终端条形码 接口文档 -> 文档有误 唯一值
			String bar_code = hashMap.get("bar_code") == null ? "" : hashMap.get("bar_code").toString();
			if(StringUtils.isNullOrEmpty(bar_code)){
				msg = " 海马接口传入的bar_code码为空!";
				System.out.println(DateUtil.formatNowTime() + msg );
				continue;
			}
			bar_code = bar_code.trim();
			
			//根据bar_code获取call_letter
			String call_letter = "";
			Preload f = preloadService.getPreloadByBarCode(bar_code);
			if(f!=null ){
				call_letter = f.getCall_letter();
				if(call_letter==null || "".equals(call_letter)){
					msg = " 海马接口传入的bar_code:"+bar_code+"对应的sim呼号为空!";
					System.out.println(DateUtil.formatNowTime() + msg );
					continue;
				}
			}else{
				msg = " 海马接口传入的bar_code:"+bar_code+"没有对应的sim!";
				System.out.println(DateUtil.formatNowTime() + msg );
				continue;
			}
			
			call_letter = call_letter.trim();
			
			// 客戶ID
//			Long customer_id = hashMap.get("customer_id") == null ? 0l : Long.valueOf(hashMap.get("customer_id").toString());
		
			Preload sim_temp = preloadService.getPreloadByVin(vin);// 根据vin查询sim信息
			Customer customer = null;
			CustVehicle cv = null;
			if (sim_temp == null) {
				msg = " 本地库中不存在vin=" + vin + "，的sim信息,call_letter=" + call_letter  ;
				System.out.println(DateUtil.formatNowTime() + msg );
				continue;
			}

			if( sim_temp.getSubco_no() != SystemConst.HM_SUBCO_NO){
				msg =  " 非海马公司的sim卡，vin=" + vin ;
				System.out.println(DateUtil.formatNowTime() + msg );
				continue;
			}
			
			
			// 根据VIN查询 t_ba_sim 是否存在卡 没有则不进行下面的操作：
			Long vehicle_id = vehicleService.getIdByVin(vin);// 根据vin码查询
			Vehicle vehicle = vehicleService.get(Vehicle.class, vehicle_id);// 车辆基础资料
			if( vehicle!=null && vehicle.getSubco_no() != SystemConst.HM_SUBCO_NO ){
				msg =  " 非海马公司的车辆，vin=" + vin ;
				System.out.println(DateUtil.formatNowTime() + msg );
				continue;
			}
			
			//vehicle == null 继续
			
			Unit unit = unitService.getUnitByCL(call_letter);//若是unit为空如何处理:
			if(unit != null && unit.getSubco_no() != SystemConst.HM_SUBCO_NO){
				msg =  " 非海马公司的车台，call_letter=" + call_letter ;
				System.out.println(DateUtil.formatNowTime() + msg );
				continue;
			}
			//unit == null 继续
			
			// 客戶名稱
//			String customer_name = hashMap.get("customer_name") == null ? call_letter : hashMap.get("customer_name").toString();
			String customer_name = hashMap.get("name") == null ? call_letter : hashMap.get("name").toString();
			customer_name = customer_name.trim();
			// 地址
			String address = hashMap.get("address") == null ? "" : hashMap.get("address").toString();
			address = address.trim();
			// 证件类型,1=居民身份证,2=士官证,3=学生证,4=驾驶证,5=护照,6=港澳通行证,99=其它
			Integer idtype = hashMap.get("idtype") == null ? 1 : Integer.valueOf(hashMap.get("idtype").toString());
			// 身份证号 最新版
			String id_no = hashMap.get("idcard_no") == null ? "" : hashMap.get("idcard_no").toString();
			id_no = id_no.trim();
			// 性别,1=男,2=女
			Integer sex = hashMap.get("sex") == null ? 1 : Integer.valueOf(hashMap.get("sex").toString());
			// 邮箱
			String email = hashMap.get("email") == null ? "" : hashMap.get("email").toString();
			email = email.trim();
			// 车辆颜色
			String color = hashMap.get("color") == null ? "" : hashMap.get("color").toString();
			color = color.trim();
			// 发动机号
			String engine_no = hashMap.get("engine_no") == null ? "" : hashMap.get("engine_no").toString();
			engine_no = engine_no.trim();
			// 生产厂家
			String factory = hashMap.get("factory") == null ? "" : hashMap.get("factory").toString();
			factory = factory.trim();
			// 车辆生产日期
			String production_date_s = hashMap.get("production_date") == null ? null : (String) hashMap.get("production_date");
			Date production_date = TimeHelper.getDate(production_date_s);

			// 购车日期 海马汽车不提供
			String buy_date_s = hashMap.get("buy_date") == null ? null : (String) hashMap.get("buy_date");
			Date buy_date = TimeHelper.getDate(buy_date_s);

			// 载重/乘客数
			Integer vload = hashMap.get("vload") == null ? null : Integer.valueOf(hashMap.get("vload").toString());
			
			String series = hashMap.get("series") == null ? null : hashMap.get("series").toString();
			
			Long series_id = null;
			if(series  != null && !"".equals(series)){
				Series s = seriesService.getSeriesByName(series);
				if(s!=null){
					series_id = s.getSeries_id();
				}
			}
			
			String model = hashMap.get("model") == null ? null : hashMap.get("model").toString();
			Long model_id = null;
			if(model  != null && !"".equals(model)){
				Model  modelPO = modelService.getModelByName(model);
				if(modelPO!=null){
					model_id = modelPO.getModel_id();
				}
			}
			
			// 商业保险公司ID,由海马给出定义列表
			Long insurance_id = hashMap.get("insurance_id") == null ? null : Long.valueOf(hashMap.get("insurance_id").toString());

            if (insurance_id != null) {
            	Insurer insurer = this.insurerService.getInsurerBySync_id(insurance_id);
              	if (insurer != null) {
              		insurance_id = Long.valueOf(insurer.getIc_id().intValue());
              	}
            }

			// 车牌
			// plaate_no:车牌号 文档中的内容 ??????????
			// -->文档有误返回实际值 plate_no键
			String plate_no1 = hashMap.get("plate_no") == null ? call_letter : hashMap.get("plate_no").toString();
//			plate_no1 = plate_no1.trim().replaceAll(" ", "");
			plate_no1 = plate_no1.trim();
			
			String equip_code = hashMap.get("equip_code") == null ? "" : hashMap.get("equip_code").toString();
			equip_code = equip_code.trim().replace("'", "");
			
			
			//联系电话
			String phone = hashMap.get("phone") == null ? "" : hashMap.get("phone").toString();
			phone = phone.trim();

			// 套餐
			Long pack_id = hashMap.get("pack_id") == null ? null : Long.valueOf(hashMap.get("pack_id").toString());
			
			// 车型
//			Integer vehicle_type = hashMap.get("vehicle_type") == null ? 1 : Integer.valueOf(hashMap.get("vehicle_type").toString());
			Integer vehicle_type = Integer.valueOf(5);

			// 入网时间 这个值不变
			// String create_date_s = hashMap.get("create_date") ==
			// null ? null : (String) hashMap.get("create_date");
			String create_date_s = hashMap.get("create_time") == null ? null : (String) hashMap.get("create_time");
			Date create_date = TimeHelper.getDateFromStr(create_date_s);

			if (create_date == null) {
				create_date = DateUtil.getNow();
			}

			// 服务开通时间/计费时间,停用后可能又开通,时间会不同
			String service_date_s = hashMap.get("service_date") == null ? null : (String) hashMap.get("service_date");
			Date service_date = TimeHelper.getDate(service_date_s);

			if (service_date == null) {
				service_date = create_date;
			}

			// 停用时间
			String stop_date_s = hashMap.get("stop_date") == null ? null : (String) hashMap.get("stop_date");
			Date stop_date = TimeHelper.getDate(stop_date_s);
			//
			Insurer insurer = insurerService.getInsurerBySync_id(insurance_id);// 如果没有该id需要先调用保险信息的接口
			Integer i_id = 0;
			if (null != insurer) {
				i_id = insurer.getIc_id();
			} 
			
			// 车辆4s店ID,由海马给出定义列表 -->这个没有值
			Long s_id = hashMap.get("4s_id") == null ? null : Long.valueOf(hashMap.get("4s_id").toString());
			Long s_no = null;// 4s店ID为空
			//
			if (s_id != null && !"".equals(s_id)) {
				CommonCompany com = ldap.getSyncCommonCompany(s_id.toString());// 4s店
				s_no = com == null ? null : Long.valueOf(com.getCompanyno());
			}
			try{
				if (null == vehicle) {
					
					vehicle = saveVehicle(vin, color, engine_no, factory, production_date, buy_date, vload, plate_no1, i_id, s_no, series_id, model_id, vehicle_type,equip_code);
					SystemConst.D_LOG.debug("新增车辆部分信息:" + vehicle);
					customer = insertCustomer(customer_name, address, idtype, sex, email,id_no);
					if(unit== null){
						unit = insertUnit(call_letter, vehicle.getVehicle_id(), create_date, service_date, customer);
					}else{
						unit = updataUnit(vehicle_id, unit, customer.getCustomer_id(), pack_id, create_date, service_date, stop_date);
					}
					
					cv = custVehicleService.getCustVehicleByCVID(String.valueOf(customer.getCustomer_id()), String.valueOf(vehicle.getVehicle_id()));
					if(cv == null){
						saveCv(vehicle, customer);
					}
					
					//更新或保存联系人
					megerLinkMan(vehicle, phone, customer);
					// 终端信息实体
					megerBarcode(sim_temp, unit);
					/**
					 * 同步程序根据车辆的配置简码，
					 * 根据t_ba_equip的is_media=1来确定是否高配(7元/月)，=0为低配(2.7元/月)
					 * 插入feetype_id=101(前装服务费)到t_fee_info表里。
					 * 
					 * 只有当VIN配置简码有变化才插入或更新，不需要每次更新；
					 * 如果是更新，需要把t_fee_info的之前记录插入到t_fee_info_bk表(计费信息历史记录)。
		            megerFeeInfo(vehicle, unit, equip_code, customer,service_date);
					 */
					msg =  " vin=" + vin + "的车辆基础资料同步保存成功";
					System.out.println(DateUtil.formatNowTime() + msg );
					
				} else {
					
					vehicle = updateVechicle(plate_no1, vin, vehicle, color, engine_no, factory, production_date, buy_date, vload, i_id, s_no, series_id, model_id, vehicle_type,equip_code);
					
					//****************************************************************************************************
					//cv 判断
					cv = custVehicleService.getCustVehicleByCVID(null, String.valueOf(vehicle.getVehicle_id()));
					//****************************************************************************************************
					
					if(cv != null){
						customer = customerService.getCustomer(cv.getCustomer_id());
						if (customer == null)
							customer = insertCustomer(customer_name, address, idtype, sex, email, id_no);
						else
							customer = updateCustomer(customer, customer_name, address, idtype, id_no, sex, email);
					}else{
						customer = insertCustomer(customer_name, address, idtype, sex, email,id_no);
						saveCv(vehicle, customer);
					}
					
					if(unit== null){
						unit = insertUnit(call_letter, vehicle.getVehicle_id(), create_date, service_date, customer);
					}else{
						updataUnit(vehicle_id, unit, customer.getCustomer_id(), pack_id, create_date, service_date, stop_date);
					}
					
					//更新或保存联系人
					/**********************************/
					megerLinkMan(vehicle, phone, customer);
					/**********************************/
					// 终端信息实体
					megerBarcode(sim_temp, unit);
					
					
					
					//插入 
					/**
					 * 同步程序根据车辆的配置简码，
					 * 根据t_ba_equip的is_media=1来确定是否高配(7元/月)，=0为低配(2.7元/月)
					 * 插入feetype_id=101(前装服务费)到t_fee_info表里。
					 */
					
					msg =  " vin=" + vin + "，基础资料同步更新成功!";
					System.out.println(DateUtil.formatNowTime() + msg );
					
				}
				
				sim_temp = updateSim(vin, unit, color, engine_no, production_date, sim_temp);

				/**************************计费信息处理*********************************/
				if(equip_code != null && !"".equals(equip_code) ){
					//缓存配置简码码表中是否存在信息
					Equipcode equipcode = SystemConst.equipcode_Cache.get(equip_code);
					if(equipcode != null){
						handleFeeInfo(vehicle, unit, equip_code, customer,service_date,equipcode,sim_temp);
						SystemConst.D_LOG.debug("[Base Data] vin=" + vin +";unit_id:"+unit.getUnit_id()+";equip_code:" +equip_code+"，计费信息处理成功!");
					}else{
						SystemConst.D_LOG.debug("[Base Data] 数据库中不存在配置简码:"+equip_code);
					}
				}else{
					SystemConst.D_LOG.debug( "[Base Data] 呼号："+unit.getCall_letter()+" equip_code 为空，计费信息不处理!");
				}
				
				
				/**************************计费套餐处理*********************************/
				/*long s = System.currentTimeMillis();
			//接口中是否存在code值
			if(equip_code != null && !"".equals(equip_code) ){
				//缓存配置简码码表中是否存在信息
				Equipcode equipcode = SystemConst.equipcode_Cache.get(equip_code);
				if(equipcode != null){
					megerFeeInfo(vehicle, unit, equip_code, customer,service_date,equipcode,sim_temp);
					System.out.println(DateUtil.formatNowTime() + " 呼号："+unit.getCall_letter()+" 配置简码:"+ equip_code+" 计费处理成功!");
				}else{
					System.out.println(DateUtil.formatNowTime() + " 呼号："+unit.getCall_letter()+" 配置简码:"+ equip_code+" 对应的套餐为空，计费套餐不处理!");
				}
			}else{//不做操作
				System.out.println(DateUtil.formatNowTime() + " 呼号："+unit.getCall_letter()+" equip_code 为空，计费套餐不处理!");
			}
			long e = System.currentTimeMillis();
			long jft = (e-s);*/
				/**************************计费套餐处理*********************************/
				
				/*****************************************************sim卡阶段处理开始**************************************************/
				/*************************************************************************************************************************
				 *第一阶段  
				 ************************************************************************************************************************/
				if ((service_date != null) && (sim_temp.getS_date() != null) && (stop_date == null))
				{
					FeeSimP fsp1 = this.feeSimPService.getFeeSimPByCL(call_letter, 1);
					if (fsp1 == null) {
						addFeesimP1(call_letter, sim_temp, service_date);
					}else if 	((fsp1.getS_date() != null) && (fsp1.getE_date() != null) && (
							(fsp1.getS_date().compareTo(sim_temp.getS_date()) != 0) || 
							(service_date.compareTo(fsp1.getE_date()) != 0))) {
						fsp1.setE_date(service_date);
						fsp1.setS_date(sim_temp.getS_date());
						feeSimPService.modifyFeeSimP(fsp1);
					}
					
					FeeSimM fsm3 = this.feeSimMService.getFeeSimMByCL(call_letter, 3);
					if (fsm3 == null) addFeeSIM_M3(call_letter);
					else  feeSimMService.updateFeeSimM(fsm3);
					
				}
				
				/*************************************************************************************************************************
				 *第三阶段 
				 *************************************************************************************************************************/
				if ((service_date != null) && (stop_date != null) && (stop_date.after(service_date)))
				{
					FeeSimP fsp3 = this.feeSimPService.getFeeSimPByCL(call_letter, 3);
					if (fsp3 == null) {
						addFeesimP3(call_letter, sim_temp, null, service_date, stop_date);
					}else if ((fsp3.getS_date() != null) 
							&& (fsp3.getE_date() != null) 
							&& ((fsp3.getS_date().compareTo(service_date) != 0) 
									|| (stop_date.compareTo(fsp3.getE_date()) != 0))) {
						fsp3.setS_date(service_date);
						fsp3.setE_date(stop_date);
						feeSimPService.modifyFeeSimP(fsp3);
					}
					
					FeeSimM fsm4 = this.feeSimMService.getFeeSimMByCL(call_letter, 4);
					if (fsm4 == null){
						addFeeSIM_M4(call_letter);
					}else {
						feeSimMService.updateFeeSimM(fsm4); 
					}
				}
				/*************************************************************************************************************************
				 *第四阶段 
				 *************************************************************************************************************************/
				if ((service_date != null) && (stop_date != null) && (service_date.after(stop_date))) {
					FeeSimP fsp4 = feeSimPService.getFeeSimPByCL(call_letter,4);
					if (fsp4 == null) {
						addFeesimP4(call_letter, sim_temp, service_date, stop_date);
					} else if ((fsp4.getS_date() != null) && (fsp4.getE_date() != null) && 
							((fsp4.getS_date().compareTo(stop_date) != 0) || (service_date.compareTo(fsp4.getE_date()) != 0))) {
						fsp4.setS_date(stop_date);
						fsp4.setE_date(service_date);
						feeSimPService.modifyFeeSimP(fsp4);
					}
					
					FeeSimM fsm3 = feeSimMService.getFeeSimMByCL(call_letter,3);
					if (fsm3 == null)
						addFeeSIM_M3(call_letter);
					else {
						feeSimMService.updateFeeSimM(fsm3);
					}
				}
				System.out.println(DateUtil.formatNowTime() + " 车载电话:" + sim_temp.getCall_letter() + "的 sim卡计费阶段处理结束!");
				result.put("msg", msg);
			}catch(Exception e){
				throw new SystemException(e);
			}
		}
		return result;
	}

	private void saveCv(Vehicle vehicle, Customer customer) throws Exception {
		CustVehicle cv = null;
		// 客户和车辆关联表
		cv= new CustVehicle();
		cv.setCustomer_id(customer.getCustomer_id());
		cv.setVehicle_id(vehicle.getVehicle_id());
		custVehicleService.save(cv);
	}
	
	
	private void handleFeeInfo(Vehicle vehicle, Unit unit, String equip_code, Customer customer, Date service_date,
			Equipcode equipcode, Preload sim) throws Exception {
		if(service_date==null){
			SystemConst.D_LOG.debug("[Base Data] vin: "+vehicle.getVin()+" service_date is null , cannot handleFeeInfo..");
			return;
		}
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("vehicle_id", vehicle.getVehicle_id());
		params.put("unit_id", unit.getUnit_id());
		// 根据uid+vid条件查询出结果feeinfos
		List<FeeInfo> feeinfos = feeInfoService.queryFeeInfo(params);
		// 需要更新的计费信息 t_fee_info
		List<FeeInfo> update_feeinfos = new ArrayList<FeeInfo>();
		// 需要备份的计费信息 t_fee_info_bk
		List<Feeinfobk> feeinfobks = new ArrayList<Feeinfobk>();
		if (feeinfos != null) {
			for (FeeInfo feeInfo : feeinfos) {
				String oldCode = sim.getEquip_code();
				if (oldCode != null && !"".equals(oldCode)) {
					if (!oldCode.equals(equip_code)) {
						// 需要更新t_fee_info
						// 先插入 t_fee_info_bk 表备份
						Feeinfobk feeinfobk = new Feeinfobk();
						BeanUtils.copyProperties(feeInfo, feeinfobk);
						feeinfobks.add(feeinfobk);

						// 再更新 t_fee_info 表
						int amount = FEE_MON_LOW;
						if (equipcode.getIs_media() == 1) {
							amount = FEE_MON_HIGH;
						}
						feeInfo.setMonth_fee(amount);
						feeInfo.setReal_amount(amount);
						feeInfo.setAc_amount(amount);
						feeInfo.setFee_date(service_date);
						feeInfo.setFee_sedate(DateUtil.addMonth(service_date, 12));

						update_feeinfos.add(feeInfo);
					}
				}
			}
			// 批量备份
			if (feeinfobks.size() > 0) {
				feeInfoService.batchSave(Feeinfobk.class, feeinfobks);
			}
			// 批量更新
			if (update_feeinfos.size() > 0) {
				SystemConst.D_LOG.debug("[Base Data] batchUpdate hm feeinfo:equipcode:"+equipcode+";unit_id:"+unit.getUnit_id());
				feeInfoService.batchUpdate(FeeInfo.class, update_feeinfos);
			}
		} else {
			// 插入t_fee_info
			SystemConst.D_LOG.debug("[Base Data] insert hm feeinfo:equipcode:"+equipcode+";unit_id:"+unit.getUnit_id()+";service_date:"+service_date);
			addFeeinfo(service_date, equipcode, vehicle, unit, customer);
		}

	}

	@SuppressWarnings("unused")
	private void megerFeeInfo(Vehicle vehicle, Unit unit, String equip_code,
			Customer customer,Date service_date,Equipcode equipcode,Preload sim) throws Exception {
		
		Map<String,Long> params = new HashMap<String, Long>();		
		params.put("vehicle_id", vehicle.getVehicle_id());
		params.put("unit_id", unit.getUnit_id());
		//根据uid、vid、uid+vid 三个条件查询出结果feeinfos
		List<FeeInfo>  feeinfos = feeInfoService.getFeeInfo(params);
		//需要删除的计费信息 t_fee_info
		List<FeeInfo>  delete_feeinfos = new ArrayList<FeeInfo>();
		//需要更新的计费信息 t_fee_info
		List<FeeInfo>  update_feeinfos = new ArrayList<FeeInfo>();
		//需要备份的计费信息 t_fee_info_bk
		List<Feeinfobk>  feeinfobks = new ArrayList<Feeinfobk>();
		//存在结果则
		if(feeinfos != null){
			for(FeeInfo feeInfo:feeinfos){
				//判断 vid +　uid 相等的结果 用于更新 
				if(feeInfo.getVehicle_id() == vehicle.getVehicle_id() && unit.getUnit_id() == feeInfo.getUnit_id()){
					String oldCode = sim.getEquip_code();
					if(oldCode!=null && !"".equals(oldCode)){
						if(!oldCode.equals(equip_code)){
							//需要更新t_fee_info
							//先插入 t_fee_info_bk 表备份
							Feeinfobk feeinfobk = new Feeinfobk();
							BeanUtils.copyProperties(feeInfo, feeinfobk);
							feeinfobks.add(feeinfobk);
							
							//再更新 t_fee_info 表
							double amount = SystemConst.FEE_MON_LOW;
							if(equipcode.getModel_id()==1){
								amount = SystemConst.FEE_MON_HIGH;
							}
							feeInfo.setMonth_fee(amount);
							feeInfo.setReal_amount(amount);
							feeInfo.setAc_amount(amount);
							
							update_feeinfos.add(feeInfo);
						}
					}
				}else{
					//其他的结果全部删除			
					Feeinfobk feeinfobk = new Feeinfobk();
					BeanUtils.copyProperties(feeInfo, feeinfobk);
					feeinfobks.add(feeinfobk);
					delete_feeinfos.add(feeInfo);
				}
			}
			//批量备份
			if(feeinfobks.size() > 0){
				feeInfoService.batchSave(Feeinfobk.class, feeinfobks);
			}
			//批量删除
			if(delete_feeinfos.size() > 0){
				feeInfoService.batchDelete(FeeInfo.class, delete_feeinfos);
			}
			//批量更新
			if(update_feeinfos.size() > 0){
				feeInfoService.batchUpdate(FeeInfo.class, update_feeinfos);
			}
		}else{
			//插入t_fee_info
			saveFeeinfo(service_date, equipcode,vehicle,unit,customer);
		}
		
	}
	
	private void addFeeinfo(Date service_date, Equipcode equipcode,Vehicle vehicle, Unit unit,Customer customer) throws Exception {
		FeeInfo newfi = new FeeInfo();
		newfi.setFeetype_id(101);
		newfi.setPay_model(1);
		newfi.setItem_id(0);
		newfi.setItems_id("");
		newfi.setCollection_id(0);
		newfi.setPay_ct_no("");
		newfi.setSubco_no(201L);
		newfi.setFee_cycle(12);
		newfi.setOp_id(0);
		newfi.setFlag(0);
		double amount = FEE_MON_LOW;
		if(equipcode.getIs_media()==1){
			amount = FEE_MON_HIGH;    
		}
		newfi.setMonth_fee(amount);
		newfi.setReal_amount(amount);
		newfi.setAc_amount(amount);
		newfi.setFee_date(service_date);
		newfi.setFee_sedate(DateUtil.addMonth(service_date, 12));
		
		newfi.setVehicle_id(vehicle.getVehicle_id());
		newfi.setUnit_id(unit.getUnit_id());
		newfi.setCustomer_id(customer.getCustomer_id());
		
		feeInfoService.save(newfi);
	}
	
	private void saveFeeinfo(Date service_date, Equipcode equipcode,Vehicle vehicle, Unit unit,Customer customer) throws Exception {
		//直接插入
		FeeInfo newfi = new FeeInfo();
		newfi.setFeetype_id(101);
		newfi.setPay_model(1);
		newfi.setItem_id(0);
		newfi.setItems_id("");
		newfi.setCollection_id(0);
		newfi.setPay_ct_no("");
		newfi.setSubco_no(201L);
		newfi.setFee_cycle(1);
		newfi.setOp_id(0);
		newfi.setFlag(0);
		double amount = SystemConst.FEE_MON_LOW;
		if(equipcode.getIs_media()==1){
			amount = SystemConst.FEE_MON_HIGH;    
		}
		newfi.setMonth_fee(amount);
		newfi.setReal_amount(amount);
		newfi.setAc_amount(amount);
		newfi.setFee_date(service_date);
		newfi.setFee_sedate(service_date);
		
		newfi.setVehicle_id(vehicle.getVehicle_id());
		newfi.setUnit_id(unit.getUnit_id());
		newfi.setCustomer_id(customer.getCustomer_id());
		
		feeInfoService.save(newfi);
	}

	private void addFeesimP4(String call_letter, Preload sim_temp,
			Date service_date, Date stop_date) throws SystemException {
		FeeSimP fsp4 = new FeeSimP();
		fsp4.setS_date(stop_date);
		fsp4.setE_date(service_date);
		fsp4.setDays(DateUtil.daysBetween(sim_temp.getS_date(), service_date));
		fsp4.setPeriod(SystemConst.SIM_PERIOD_FOUR.intValue());
		fsp4.setSubco_no(Long.valueOf(201L).intValue());
		fsp4.setCall_letter(call_letter);
		this.feeSimPService.addFeeSimP(fsp4);
	}

	private void addFeesimP3(String call_letter, Preload sim_temp,
			Date buy_date, Date service_date, Date stop_date) throws SystemException {
		FeeSimP fsp3 = new FeeSimP();
		fsp3.setS_date(service_date);
		fsp3.setE_date(stop_date);
		fsp3.setDays(DateUtil.daysBetween(sim_temp.getS_date(), service_date));
		fsp3.setPeriod(SystemConst.SIM_PERIOD_THREE.intValue());
		fsp3.setSubco_no(Long.valueOf(201L).intValue());
		fsp3.setCall_letter(call_letter);
		this.feeSimPService.addFeeSimP(fsp3);
	}

	private void addFeeSIM_M4(String call_letter) throws SystemException {
		FeeSimM fsm4 = new FeeSimM();
		fsm4.setPeriod(SystemConst.SIM_PERIOD_FOUR.intValue());
		fsm4.setCall_letter(call_letter);
		fsm4.setSubco_no(Long.valueOf(201L).intValue());
		fsm4.setMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
		this.feeSimMService.addFeeSimM(fsm4);
	}

	

	private void addFeeSIM_M3(String call_letter) throws SystemException {
		FeeSimM fsm3 = new FeeSimM();
		fsm3.setPeriod(SystemConst.SIM_PERIOD_THREE.intValue());
		fsm3.setCall_letter(call_letter);
		fsm3.setSubco_no(Long.valueOf(201L).intValue());
		fsm3.setMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
		this.feeSimMService.addFeeSimM(fsm3);
	}

	private void addFeesimP1(String call_letter, Preload sim_temp,
			Date service_date) throws SystemException {
		FeeSimP fsp1 = new FeeSimP();
		fsp1.setS_date(sim_temp.getS_date());
		fsp1.setE_date(service_date);
		fsp1.setDays(DateUtil.daysBetween(sim_temp.getS_date(), service_date));
		fsp1.setPeriod(SystemConst.SIM_PERIOD_ONE.intValue());
		fsp1.setSubco_no(Long.valueOf(201L).intValue());
		fsp1.setCall_letter(call_letter);
		this.feeSimPService.addFeeSimP(fsp1);
	}

	/**
	 * 更新客户信息-0
	 * @param unit
	 * @param customer_name
	 * @param address
	 * @param idtype
	 * @param id_no
	 * @param sex
	 * @param email
	 * @return
	 */
	private Customer updateCustomer(Customer customer, String customer_name, String address, Integer idtype, String id_no, Integer sex, String email) {
		if(StringUtils.isNotNullOrEmpty(customer_name)){
			customer.setCustomer_name(customer_name);
		}
		if(StringUtils.isNotNullOrEmpty(address)){
			customer.setAddress(address);
		}
		if(StringUtils.isNotNullOrEmpty(id_no)){
			customer.setId_no(id_no);
		}
		if(idtype!=null){
			customer.setIdtype(idtype);
		}
		customer.setSubco_name(SystemConst.HM_SUBCO_NAME);
		customer.setSubco_no(SystemConst.HM_SUBCO_NO);
		
		customer.setSex(sex);
		if(StringUtils.isNotNullOrEmpty(email)){
			customer.setEmail(email);
		}
		customerService.update(customer);
		return customer;
	}

	/**
	 * 更新车辆信息-0
	 * @param vin
	 * @param vehicle
	 * @param color
	 * @param engine_no
	 * @param factory
	 * @param production_date
	 * @param buy_date
	 * @param vload
	 * @param i_id
	 * @param s_no
	 */
	private Vehicle updateVechicle(String plate_no, String vin, Vehicle vehicle, String color, String engine_no, String factory, 
			Date production_date, Date buy_date, Integer vload, Integer i_id, Long s_no, Long series_id, 
			Long model_id, Integer vehicle_type,String equip_code)
	{
	    if (StringUtils.isNotNullOrEmpty(plate_no)) {
	      vehicle.setPlate_no(plate_no);
	    }
	    if (StringUtils.isNotNullOrEmpty(engine_no)) {
	      vehicle.setEngine_no(engine_no);
	    }
	    if (StringUtils.isNotNullOrEmpty(color)) {
	      vehicle.setColor(color);
	    }
	    if (StringUtils.isNotNullOrEmpty(factory)) {
	      vehicle.setFactory(factory);
	    }
	    if (production_date != null) {
	      vehicle.setProduction_date(production_date);
	    }
	    if (buy_date != null) {
	      vehicle.setBuy_date(buy_date);
	    }
	    vehicle.setVload(vload);
	    if (s_no != null) {
	      vehicle.setId_4s(s_no);
	    }
	    if (StringUtils.isNotNullOrEmpty(vin)) {
	      vehicle.setVin(vin);
	    }
	    if (i_id != null) {
	      vehicle.setInsurance_id(i_id);
	    }

	    if (model_id != null) {
	      vehicle.setModel(model_id);
	    }

	    if (series_id != null) {
	      vehicle.setSeries(series_id);
	    }

	    if (vehicle_type != null) {
	      vehicle.setVehicle_type(vehicle_type);
	    }
	    
	    if(StringUtils.isNotNullOrEmpty(equip_code)){
	    	vehicle.setEquip_code(equip_code);
	    }

	    vehicleService.update(vehicle);

	    return vehicle;
	  }

	/**
	 * 更新sim卡-0
	 * @param vin
	 * @param unit
	 * @param color
	 * @param engine_no
	 * @param production_date
	 * @param sim
	 */
	private Preload updateSim(String vin, Unit unit, String color, String engine_no, Date production_date, Preload sim) {
		if (sim != null) {
			if(StringUtils.isNotNullOrEmpty(vin)){
				sim.setVin(vin);
			}
			if(StringUtils.isNotNullOrEmpty(engine_no)){
				sim.setEngine_no(engine_no);
			}

			if(StringUtils.isNotNullOrEmpty(color)){
				sim.setColor(color);
			}
			if(production_date != null){
				sim.setProduction_date(production_date);
			}
			
			sim.setUnit_id(unit.getUnit_id());
			
			sim.setUnittype_id(1033L);
			preloadService.update(sim);
		}
		
		return sim;
	}

	/**
	 * 更新或者保存barcode-0
	 * @param sim_temp
	 * @param unit
	 * @throws Exception 
	 */
	private void megerBarcode(Preload sim_temp, Unit unit) throws Exception {
		Barcode bc = barcodeService.getByUnit_idAndType(unit.getUnit_id(), 1);
		if(bc==null){
			Barcode model = new Barcode();
			model.setContent(sim_temp.getBarcode());
			model.setType(1);// 1终端条形码2导航条形码
			model.setUnit_id(unit.getUnit_id());
			barcodeService.save(model);
		}else{
			if(StringUtils.isNotNullOrEmpty(sim_temp.getBarcode())){
				bc.setContent(sim_temp.getBarcode());
			}
			bc.setType(1);// 1终端条形码2导航条形码
			if(StringUtils.isNotNullOrEmpty(unit.getUnit_id())){
				bc.setUnit_id(unit.getUnit_id());
			}
			barcodeService.update(bc);
		}
	}

	/**
	 * 更新或者保存联系人信息-0
	 * @param vehicle
	 * @param phone
	 * @param cus
	 * @throws Exception
	 */
	private void megerLinkMan(Vehicle vehicle, String phone, Customer cus) throws Exception  {
		LinkMan man = linkmanService.getLinkMan(vehicle.getVehicle_id());
		
		if(man!=null){
			man.setCustomer_id(cus.getCustomer_id());
			man.setLinkman(cus.getCustomer_name());
			man.setLinkman_type(1);//类型, 系统参数2100, 1=第一联系人/常用联系人, 2=第二联系人/报警联系人, 99=其它
			if(StringUtils.isNotNullOrEmpty(phone)){
				man.setPhone(phone);
			}
			if(StringUtils.isNotNullOrEmpty(cus.getCustomer_name())){
				man.setTitle(cus.getCustomer_name());
			}
			man.setVehicle_id(vehicle.getVehicle_id());
			man.setAppsign(1);//App绑定标记, 1=绑定, 0=不绑定
			linkmanService.update(man);
		}else{
			man = new LinkMan();
			man.setCustomer_id(cus.getCustomer_id());
			man.setLinkman(cus.getCustomer_name());
			man.setLinkman_type(1);//类型, 系统参数2100, 1=第一联系人/常用联系人, 2=第二联系人/报警联系人, 99=其它
			man.setPhone(phone);
			man.setTitle(cus.getCustomer_name());
			man.setVehicle_id(vehicle.getVehicle_id());
			man.setAppsign(1);//App绑定标记, 1=绑定, 0=不绑定
			linkmanService.save(man);
		}
	}

	/**
	 * 更新车台终端-o
	 * @param vehicle_id
	 * @param unit
	 * @param customer_id
	 * @param pack_id
	 * @param create_date
	 * @param service_date
	 * @param stop_date
	 */
	private Unit updataUnit(Long vehicle_id, Unit unit, Long customer_id, Long pack_id, Date create_date, Date service_date, Date stop_date) {
		
		
		if(create_date!=null){
			unit.setCreate_date(create_date);// 该值不更新
		}
		if(service_date!=null){
			unit.setService_date(service_date);
		}
		if(stop_date != null){
			unit.setStop_date(stop_date);
		}
		if(pack_id != null){
			unit.setPack_id(pack_id);
		}
		unit.setUnittype_id(1033L);// 车台类型,
		unit.setMode(3);// 通信模式, 1=短信, 2=数据流量, //
						// 3=流量+短信
		unit.setData_node(0);// 流量通道网关编号, 无填0, // 预留, //// 见t_sys_node, //// 流量网关节点, // 界面上需显示网关别名
		unit.setSms_node(5);// 短信通道网关编号,
		unit.setFlag(0);
		unit.setSales_id(0L);
		
		if(customer_id != null){
			unit.setCustomer_id(customer_id);
		}
		if(vehicle_id != null){
			unit.setVehicle_id(vehicle_id);
		}
		
		unitService.update(unit);
		
		return unit;
	}

	/**
	 * 保存车台终端
	 * @param call_letter
	 * @param vehicle
	 * @param create_date
	 * @param service_date
	 * @param customer
	 * @return
	 * @throws Exception 
	 */
	private Unit insertUnit(String call_letter, long vehicle_id, Date create_date, Date service_date, Customer customer) throws Exception {
		Unit unit_save = new Unit();
		unit_save.setVehicle_id(vehicle_id);// 车辆
		unit_save.setCustomer_id(customer.getCustomer_id());// 客户ID
		unit_save.setCall_letter(call_letter);// 车载号码 //
											// ，是唯一索引，本身具有约束性，如果该字段已经存在相同的只的话，就不能再插入该数据了，当然也插不进去，比普通索引快。
		unit_save.setSubco_no(SystemConst.HM_SUBCO_NO);// 分公司
		unit_save.setUnittype_id(1033l);// 车台类型,
									// 关联t_ba_unittype,
									// 考虑商品名称分类太多,
									// 操作员录入复杂,
									// 故沿用老系统
		unit_save.setMode(3);// 通信模式, 1=短信, 2=数据流量,
						// 3=流量+短信
		unit_save.setData_node(0);// 流量通道网关编号, 无填0,
								// 预留,
								// 见t_sys_node,
								// 流量网关节点,
								// 界面上需显示网关别名
		unit_save.setSms_node(5);// 短信通道网关编号, 无填0,
							// 多个短信通道时需要,
							// 见t_sys_node,
							// 短信网关节点,
							// 界面上需显示网关别名
		unit_save.setTelecom(3);// SIM卡运营商, 1=移动,
							// 2=联通, 3=电信
		unit_save.setSim_type(1);// SIM卡类型, 1=自带卡,
							// 2=公司卡, 3=总部卡
		unit_save.setTrade(0);// 所属行业(网上查车行业版本),
							// 系统值2040, 0=私家车,
							// 1=物流车, 2=出租车,
							// 3=混凝土, 默认与客户相同,
							// 考虑客户有多个行业情况

		unit_save.setPay_model(1);// '付费方式,
								// 集团客户可能每车不同,
								// 系统值3050,
								// 0=托收, 1=现金,
								// 2=转账, 3=刷卡',
		unit_save.setReg_status(0);// 入网状态, 系统值2050,
								// 0=在网, 1=离网,
								// 2=欠费离网,
								// 3=非入网,
								// 4=研发测试,
								// 5=电工测试,
								// 6=重新开通, 7=不开通

		unit_save.setCreate_date(create_date);
		unit_save.setService_date(service_date);
		unit_save.setSales_id(0L);
		unit_save.setFlag(0);
		unit_save.setUnit_id(unitService.save(unit_save));
		return unit_save;
	}

	/**
	 * 保存客户信息
	 * @param customer_name
	 * @param address
	 * @param idtype
	 * @param sex
	 * @param email
	 * @return
	 * @throws Exception 
	 */
	private Customer insertCustomer(String customer_name, String address, Integer idtype, Integer sex, String email,String id_no) throws Exception {
		Customer customer = new Customer();
		// 分公司代码 201 海马
		customer.setSubco_no(SystemConst.HM_SUBCO_NO);//
		customer.setSubco_code("");
		customer.setCust_type(0);// 客户类型, 0=私家车客户, 1=集团客户, 2=担保公司
		customer.setCustomer_name(customer_name);
		customer.setAddress(address);
		customer.setCustco_code("");// 'LDAP客户子机构代码, // 用于集团客户车辆的归属关系, // 无填''0'', 每级2位字符',
		customer.setCustco_no(0L);// LDAP客户根节点ID, 集团客户有效 ;  默认给什么值 不能为空
		customer.setVip(1);//
		customer.setIdtype(idtype);
		customer.setSubco_name(SystemConst.HM_SUBCO_NAME);
		customer.setSex(sex);
		customer.setEmail(email);
		customer.setId_no(id_no);
		customer.setTrade(0);// '所属行业(网上查车行业版本), 系统值2040, 0=私家车, 1=物流车, 2=出租车,3=混凝土',
		customer.setFlag(1);// '标志, 0=未审核, 1=审核通过, 2=删除',
		customer.setPay_model(0);// 预留, 付费方式, 冗余, 系统值3050, 0=托收, 1=现金, 2=转账, 3=刷卡
		customer.setOp_id(1L);// 操作员ID
		customer.setCustomer_id(customerService.save(customer));
		return customer;
	}

	/**
	 * 保存车辆信息
	 * @param vin
	 * @param color
	 * @param engine_no
	 * @param factory
	 * @param production_date
	 * @param buy_date
	 * @param vload
	 * @param plate_no1
	 * @param i_id
	 * @param s_no
	 * @return
	 * @throws Exception 
	 */
	private Vehicle saveVehicle(String vin, String color, String engine_no, String factory, Date production_date, Date buy_date, 
		Integer vload, String plate_no1, Integer i_id, Long s_no,Long series_id,Long model_id,Integer vehicle_type,String equip_code) throws Exception {
		Vehicle vehicle = new Vehicle();
		// 海马暂时默认为201
		vehicle.setSubco_no(SystemConst.HM_SUBCO_NO);// LDAP分公司根节点ID, //对应我们的分公司, // 内部机构
		vehicle.setPlate_no(plate_no1);// 车牌号码
		vehicle.setPlate_color(1);// 车牌颜色, 系统值2110, 1=蓝, 2=黄, 3=黑, 4=白, 9=其他
		vehicle.setVehicle_type(vehicle_type);// 车辆类型, 系统值2030, 1=小型轿车
		vehicle.setVehicle_status(0);// 车辆状态, 系统值2060, 0=正常, 1=故障, 2=维修, 3=警情
		vehicle.setVin(vin);// 车辆识别号码/车架号, 17位
		vehicle.setColor(color);// 车辆颜色
		vehicle.setEngine_no(engine_no);// 发动机编号
		vehicle.setFactory(factory);// 生产厂家
		vehicle.setProduction_date(production_date);// 生产日期
		vehicle.setBuy_date(buy_date);// 购买日期
		vehicle.setFlag(1);//
		vehicle.setVload(vload);// 载重/乘客数
		vehicle.setOp_id(1L);// 操作人员
		vehicle.setId_4s(s_no);// 车辆4s店, 对应前装的4s店机构ID
		vehicle.setInsurance_id(i_id);// 商业保险公司,与综合盗抢险不同
		vehicle.setEquip_code(equip_code);//简码配置
		vehicle.setModel(model_id);
		vehicle.setSeries(series_id);
		
		vehicle.setVehicle_id(vehicleService.save(vehicle));
		return vehicle;
	}
	
}
