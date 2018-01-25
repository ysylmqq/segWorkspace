package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.OperatelogDao;
import com.gboss.dao.OperatorUnitDao;
import com.gboss.dao.StopDao;
import com.gboss.pojo.Backup;
import com.gboss.pojo.Collection;
import com.gboss.pojo.Collectionbk;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Customerbk;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.FeeInfobk;
import com.gboss.pojo.Operatelog;
import com.gboss.pojo.OperatorUnit;
import com.gboss.pojo.Policy;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Stop;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Unitbk;
import com.gboss.pojo.Vehicle;
import com.gboss.pojo.Vehiclebk;
import com.gboss.pojo.web.DoOpen;
import com.gboss.service.BackupService;
import com.gboss.service.CollectionService;
import com.gboss.service.CustomerService;
import com.gboss.service.DatalockService;
import com.gboss.service.FeeInfoService;
import com.gboss.service.PolicyService;
import com.gboss.service.PreloadService;
import com.gboss.service.StopService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.DESPlus;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:StopServiceImpl
 * @author:xiaoke
 * @date:2014-7-16 下午5:34:00
 */
@Service("StopService")
@Transactional
public class StopServiceImpl extends BaseServiceImpl implements StopService {

	@Autowired  
	@Qualifier("StopDao")
	private StopDao stopDao;
	
	@Autowired
	@Qualifier("BackupService")
	private BackupService backupService;
	
	@Autowired
	@Qualifier("UnitService")
	private UnitService unitService;
	
	@Autowired
	private FeeInfoService feeInfoService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private DatalockService datalockService;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PreloadService preloadService;
	
	@Autowired
	private OperatelogDao olDao;
	
	@Autowired
	private OperatorUnitDao ouDao;
	
	@Override
	public List<Stop> getListByid(Long vehicle_id) {
		return stopDao.getListByid(vehicle_id);
	}

	@Override
	public void deleteStop(Long vehicle_id, Integer type) {
		stopDao.deleteStop(vehicle_id, type);
	}

	@Override
	public boolean isExist(Long vehicle_id, Integer type) {
		return stopDao.isExist(vehicle_id, type);
	}

	@Override
	public Long addStop(Stop stop) {
		save(stop);
		return stop.getId();
	}

	@Override
	public List<HashMap<String, Object>> findStops(Map<String, Object> params) throws SystemException {
		List<HashMap<String, Object>> list = stopDao.findStops(params);
		for(int i = 0; i < list.size(); i++){
			Map<String, Object> map = list.get(i);
			String typeId = Utils.isNotNullOrEmpty(map.get("type"))?map.get("type").toString():"";
			if(StringUtils.isNotBlank(typeId)){
				map.put("type_name", SystemConst.reasonMap.get(typeId));
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> doStop(Map<String, String> params, List<Stop> stops) throws SystemException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = params.get("userId");
		String username = params.get("username");
		String companyid = params.get("companyid");
		String orgid = params.get("orgid");
		try{
			String stopTypes = " 办停项目:";
			for(Stop stop:stops){
				Integer feetype_id = stop.getType();
				Long vehicle_id = stop.getVehicle_id();
				if(feetype_id==1){
					stopTypes += "服务费,";
				}else if(feetype_id==2){
					stopTypes += "SIM卡,";
				}else if(feetype_id==3){
					stopTypes += "盗抢险,";
					Policy policy = new Policy();
					policy.setVehicle_id(vehicle_id);
					List<Long> ids = policyService.getPolicys(policy);
					policyService.operatePolicy(ids, 0);
				}else if(feetype_id==4){
					stopTypes += "网上查车,";
				}else if(feetype_id==101){
					stopTypes += "前装服务费,";
				}else if(feetype_id==0){
					stopTypes += "其他项目,";
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			boolean flag = false;
			Customer customer = null;
			Vehicle vehicle = null;
			if(stops.size()>0){
				Long vehicle_id = stops.get(0).getVehicle_id();
				vehicle = vehicleService.getVehicleByid(vehicle_id);
				Vehiclebk vehiclebk = backupService.getVehiclebk(vehicle);
				Long vehiclebk_id = backupService.addVehiclebk(vehiclebk);
				Long customer_id = stops.get(0).getCustomer_id();
				List<Long> lockcustids = datalockService.getLockCustomer();
				if(lockcustids.contains(customer_id)){
					resultMap.put("success", false);
					resultMap.put("msg", "办停失败,该客户正在进行计费不能办停!");
					return resultMap;
				}
				customer = customerService.getCustomer(customer_id);
				Customerbk customerbk = backupService.getCustomerbk(customer);
				Long customerbk_id = backupService.addCustomerbk(customerbk);
				Collection collection = collectionService.getCollectionByCustId(customer_id);
				Long collectionbk_id = 0L;
				if(collection!=null){
					Collectionbk collectionbk = backupService.getCollectionbk(collection);
					collectionbk_id = backupService.addCollectionbk(collectionbk);
				}
				String remark = stops.get(0).getRemark();
				remark = remark + stopTypes;
				if(stops.get(0).getIs_tear()==0){
					remark = remark+"不拆机.";
				}else if(stops.get(0).getIs_tear()==1){
					remark = remark+"拆机.";
				}
				if(stops.get(0).getFlag()==2){
					remark = remark+"营业处办理离网.";
				}else if(stops.get(0).getFlag()==3){
					remark = remark+"中心欠费催缴脱网.";
				}else if(stops.get(0).getFlag()==4){
					remark = remark+"分公司办理离网.";
				}else{
					remark = remark+"其他办停.";
				}
				Unit unit = unitService.getByCustAndVehicle(customer_id, vehicle_id);
				//更新sim卡状态
				Preload sim = preloadService.getPreloadByCl(unit.getCall_letter());
				if(sim!=null){
					sim.setFlag(0);
					sim.setCombo_status(0);
					sim.setOp_id(Long.valueOf(userId));
					preloadService.update(sim);
				}
				Unitbk unitbk = backupService.getUnitbk(unit);
				Long unitbk_id = backupService.addUnitbk(unitbk);
				Long unit_id = unit.getUnit_id();
				List<FeeInfo> feeInfos = feeInfoService.getListByid(unit_id);
				String bkf_ids = "";
				for(FeeInfo fee:feeInfos){
					FeeInfobk feeInfobk = backupService.getFeeInfobk(fee);
					Long feeInfobk_id = backupService.addFeeInfobk(feeInfobk);
					map.put("fee_sedate"+fee.getFeetype_id(), fee.getFee_sedate());
					map.put("fi_bk_id"+fee.getFeetype_id(), feeInfobk_id);
					bkf_ids += feeInfobk_id + ",";
				}
				Backup backup = new Backup();
				backup.setUnit_id(unit_id);
				backup.setVehicle_id(vehicle_id);
				backup.setBk_type("7");
				backup.setBkc_id(customerbk_id);
				backup.setBkv_id(vehiclebk_id);
				backup.setBku_id(unitbk_id);
				backup.setBkfc_id(collectionbk_id);
				backup.setBkf_ids(bkf_ids);
				backup.setOp_id(Long.valueOf(userId));
				backup.setOp_name(username);
				backup.setRemark(remark);
				backupService.addBackup(backup);
			}
			for(Stop stop:stops){
				//删除计费信息
				Long customer_id = stop.getCustomer_id();
				Long vehicle_id = stop.getVehicle_id();
				Long unit_id = stop.getUnit_id();
				Integer feetype_id = stop.getType();
				FeeInfo feeInfo = new FeeInfo();
				feeInfo.setCustomer_id(customer_id);
				feeInfo.setVehicle_id(vehicle_id);
				feeInfo.setUnit_id(unit_id);
				feeInfo.setFeetype_id(feetype_id);
				feeInfoService.deleteFeeInfo(feeInfo);
				if(feetype_id==1&&feetype_id==101){
					//证明有办停服务费
					flag = true;
				}
				//添加办停记录
				Object fee_sedate = map.get("fee_sedate"+feetype_id);
				Object fi_bk_id = map.get("fi_bk_id"+feetype_id);
				stop.setSubco_no(Long.valueOf(companyid));
				stop.setOrg_id(Long.valueOf(orgid));
				stop.setUser_id(Long.valueOf(userId));
				if(fee_sedate!=null){
					stop.setFee_sedate((Date)fee_sedate);
				}
				if(fi_bk_id!=null){
					stop.setFi_bk_id((Long)fi_bk_id);
				}
				addStop(stop);
			}
			if(stops.size()>0){
				Long vehicle_id = stops.get(0).getVehicle_id();
				Long customer_id = stops.get(0).getCustomer_id();
				Unit unit = unitService.getByCustAndVehicle(customer_id, vehicle_id);
				Long unit_id = unit.getUnit_id();
				List<FeeInfo> feeInfos = feeInfoService.getListByid(unit_id);
				//如果办停了所有的计费项目,更新unit表的状态
				if(feeInfos==null||feeInfos.size()==0){
					unit.setFlag(stops.get(0).getFlag());
					unit.setReg_status(1);
					//判断客户类型，私家车删除ldap数据，集客不做删除
					if(null != customer){
						Integer cust_type = customer.getCust_type();
						if(0 == cust_type){
							OpenLdap ldap = OpenLdapManager.getInstance();
							CommonOperator op = ldap.getOperatorBycustId(customer.getCustomer_id()+"");
							if(null != op){
								ldap.delete(op.getDn());
								Operatelog log = new Operatelog();
								log.setUser_id(Long.valueOf(userId));
								log.setUser_name(username);
								log.setModel_id(20020);
								log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_DEL));
								log.setSubco_no(Long.valueOf(companyid));
								log.setRemark("操作ip地址:" + params.get("ip_address") + ";" +"车辆离网删除ldap数据,车牌:"
				                           +vehicle.getPlate_no()+",车载电话" +unit.getCall_letter()
				                           + "{opid:"+op.getOpid()+", customerid:"+op.getCustomerid()
				                           +",loginname:"+op.getLoginname()+",mobile:"+op.getMobile()+",numberplate:"+op.getNumberplate()+"}");
								olDao.save(log);
							}
						}
					}
				}
				if(flag){
					unit.setStop_date(new Date());
				}
				unitService.update(unit);
			}
			resultMap.put("success", true);
			resultMap.put("msg", "办停成功!");
		}catch(Exception e){
			resultMap.put("success", false);
			resultMap.put("msg", "办停失败,原因:"+e.getMessage());
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> doOpen(Map<String, String> params, DoOpen doOpen)throws SystemException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = params.get("userId");
		String username = params.get("username");
		String companyid = params.get("companyid");
		try{

			Long customer_id = 0L;
			Long vehicle_id = 0L;
			Long unit_id = 0L;
			Collection collection = null;
			List<Stop> stops = doOpen.getStops();
			List<Integer> typeList = new ArrayList<Integer>();
			String openTypes = " 开通项目:";
			for(Stop stop:stops){
				deleteStop(stop.getVehicle_id(), stop.getType());
				typeList.add(stop.getType());
				Integer feetype_id = stop.getType();
				vehicle_id = stop.getVehicle_id();
				Date startDate = doOpen.getInsuranceInfo().getFee_date();
				startDate = startDate==null?new Date():startDate;
				if(feetype_id==1){
					openTypes += "服务费,";
				}else if(feetype_id==2){
					openTypes += "SIM卡,";
				}else if(feetype_id==3){
					openTypes += "盗抢险,";
					Policy policy = new Policy();
					policy.setVehicle_id(vehicle_id);
					policy.setStamp(startDate);
					List<Long> ids = policyService.getPolicys(policy);
					if(ids.size()==1){
						policyService.operatePolicy(ids, 2);
					}else{
						resultMap.put("success", false);
						resultMap.put("msg", "开通失败,该客户选择的盗抢险开通时间无效,请先到盗抢险管理办理有效保单.");
						return resultMap;
					}
				}else if(feetype_id==4){
					openTypes += "网上查车,";
				}else if(feetype_id==101){
					openTypes += "前装服务费,";
				}
			}
			Vehicle vehicle = null;
			Customer customer = null;
			if(stops.size()>0){
				vehicle_id = stops.get(0).getVehicle_id();
				vehicle = vehicleService.getVehicleByid(vehicle_id);
				Vehiclebk vehiclebk = backupService.getVehiclebk(vehicle);
				Long vehiclebk_id = backupService.addVehiclebk(vehiclebk);
				customer_id = stops.get(0).getCustomer_id();
//				List<Long> lockcustids = datalockService.getLockCustomer();
//				if(lockcustids.contains(customer_id)){
//					resultMap.put("success", false);
//					resultMap.put("msg", "开通失败,该客户正在进行计费不能开通!");
//					return resultMap;
//				}
				customer = customerService.getCustomer(customer_id);
				Customerbk customerbk = backupService.getCustomerbk(customer);
				Long customerbk_id = backupService.addCustomerbk(customerbk);
				collection = collectionService.getCollectionByCustId(customer_id);
				Long collectionbk_id = 0L;
				if(collection!=null){
					Collectionbk collectionbk = backupService.getCollectionbk(collection);
					collectionbk_id = backupService.addCollectionbk(collectionbk);
				}
				String remark = stops.get(0).getRemark();
				remark = remark + openTypes;
				Unit unit = unitService.getByCustAndVehicle(customer_id, vehicle_id);
				Unitbk unitbk = backupService.getUnitbk(unit);
				Long unitbk_id = backupService.addUnitbk(unitbk);
				unit_id = unit.getUnit_id();
				List<FeeInfo> feeInfos = feeInfoService.getListByid(unit_id);
				String bkf_ids = "";
				for(FeeInfo fee:feeInfos){
					FeeInfobk feeInfobk = backupService.getFeeInfobk(fee);
					Long feeInfobk_id = backupService.addFeeInfobk(feeInfobk);
					bkf_ids += feeInfobk_id + ",";
				}
				//开通时更新unit表状态
				if(unit!=null){
					unit.setFlag(0);
					unit.setReg_status(0);
					unit.setService_date(new Date());
					unitService.update(unit);
					//开通时更新sim表状态
					Preload sim = preloadService.getPreloadByCl(unit.getCall_letter());
					if(sim!=null){
						sim.setNew_combo_id(doOpen.getCombo_id());
						sim.setPack_id(doOpen.getPrePack_id());
						sim.setFlag(1);
						sim.setCombo_status(1);
						sim.setOp_id(Long.valueOf(userId));
						preloadService.update(sim);
					}
				}
				Backup backup = new Backup();
				backup.setUnit_id(unit_id);
				backup.setVehicle_id(vehicle_id);
				backup.setBk_type("8");
				backup.setBkc_id(customerbk_id);
				backup.setBkv_id(vehiclebk_id);
				backup.setBku_id(unitbk_id);
				backup.setBkfc_id(collectionbk_id);
				backup.setBkf_ids(bkf_ids);
				backup.setOp_id(Long.valueOf(userId));
				backup.setOp_name(username);
				backup.setRemark(remark);
				backupService.addBackup(backup);
			}
			String pay_ct_no = "";
			if(collection!=null){
				pay_ct_no = collection.getPay_ct_no();
			}
			Long collecitonId = 0L;
			if(collection != null){
				collecitonId = collection.getCollection_id();
			}
			FeeInfo serviceInfo = doOpen.getServiceInfo();
			if(serviceInfo.getMonth_fee()!=null && typeList.contains(Integer.valueOf(1))){
				
				serviceInfo.setSubco_no(Long.valueOf(companyid));
				serviceInfo.setCustomer_id(customer_id);
				serviceInfo.setVehicle_id(vehicle_id);
				serviceInfo.setUnit_id(unit_id);
				serviceInfo.setFeetype_id(1);
				serviceInfo.setItem_id(0L);
				if (serviceInfo.getFee_date() == null) {
					serviceInfo.setFee_date(new Date());
					serviceInfo.setFee_sedate(new Date());
				}else {
					serviceInfo.setFee_sedate(serviceInfo.getFee_date());
				}
				if(serviceInfo.getFee_cycle()==null){
					serviceInfo.setFee_cycle(1);
				}
				serviceInfo.setAc_amount(serviceInfo.getMonth_fee()*serviceInfo.getFee_cycle());
				serviceInfo.setReal_amount(serviceInfo.getMonth_fee()*serviceInfo.getFee_cycle());
				serviceInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collecitonId);
				serviceInfo.setOp_id(Long.valueOf(userId));
				feeInfoService.add(serviceInfo);
			}
			//SIM卡月租费
			FeeInfo simfeeInfo = doOpen.getSimfeeInfo();
			if(simfeeInfo.getMonth_fee()!=null && typeList.contains(Integer.valueOf(2))){
				simfeeInfo.setSubco_no(Long.valueOf(companyid));
				simfeeInfo.setCustomer_id(customer_id);
				simfeeInfo.setVehicle_id(vehicle_id);
				simfeeInfo.setUnit_id(unit_id);
				simfeeInfo.setFeetype_id(2);
				simfeeInfo.setItem_id(0L);
				if (simfeeInfo.getFee_date() == null) {
					simfeeInfo.setFee_date(new Date());
					simfeeInfo.setFee_sedate(new Date());
				}else {
					simfeeInfo.setFee_sedate(simfeeInfo.getFee_date());
				}
				if(simfeeInfo.getFee_cycle()==null){
					simfeeInfo.setFee_cycle(1);
				}
				simfeeInfo.setAc_amount(simfeeInfo.getMonth_fee()*simfeeInfo.getFee_cycle());
				simfeeInfo.setReal_amount(simfeeInfo.getMonth_fee()*simfeeInfo.getFee_cycle());
				simfeeInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collecitonId);
				simfeeInfo.setOp_id(Long.valueOf(userId));
				feeInfoService.add(simfeeInfo);
			}
			//盗抢险计费信息
			FeeInfo insuranceInfo = doOpen.getInsuranceInfo();
			if(insuranceInfo.getMonth_fee()!=null && typeList.contains(Integer.valueOf(3))){
				insuranceInfo.setSubco_no(Long.valueOf(companyid));
				insuranceInfo.setCustomer_id(customer_id);
				insuranceInfo.setVehicle_id(vehicle_id);
				insuranceInfo.setUnit_id(unit_id);
				insuranceInfo.setFeetype_id(3);
				insuranceInfo.setItem_id(0L);
				if (insuranceInfo.getFee_date() == null) {
					insuranceInfo.setFee_date(new Date());
					insuranceInfo.setFee_sedate(new Date());
				}else{
					insuranceInfo.setFee_sedate(insuranceInfo.getFee_date());
				}
				if(insuranceInfo.getFee_cycle()==null){
					insuranceInfo.setFee_cycle(1);
				}
				insuranceInfo.setAc_amount(insuranceInfo.getMonth_fee()*insuranceInfo.getFee_cycle());
				insuranceInfo.setReal_amount(insuranceInfo.getMonth_fee()*insuranceInfo.getFee_cycle());
				insuranceInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collecitonId);
				insuranceInfo.setOp_id(Long.valueOf(userId));
				feeInfoService.add(insuranceInfo);
			}
			//网上查车费
			FeeInfo webgisInfo = doOpen.getWebgisInfo();
			if(webgisInfo.getMonth_fee()!=null && typeList.contains(Integer.valueOf(4))){
				webgisInfo.setSubco_no(Long.valueOf(companyid));
				webgisInfo.setCustomer_id(customer_id);
				webgisInfo.setVehicle_id(vehicle_id);
				webgisInfo.setUnit_id(unit_id);
				webgisInfo.setFeetype_id(4);
				webgisInfo.setItem_id(0L);
				if (webgisInfo.getFee_date() == null) {
					webgisInfo.setFee_date(new Date());
					webgisInfo.setFee_sedate(new Date());
				}else{
					webgisInfo.setFee_sedate(webgisInfo.getFee_date());
				}
				if(webgisInfo.getFee_cycle()==null){
					webgisInfo.setFee_cycle(1);
				}
				webgisInfo.setAc_amount(webgisInfo.getMonth_fee()*webgisInfo.getFee_cycle());
				webgisInfo.setReal_amount(webgisInfo.getMonth_fee()*webgisInfo.getFee_cycle());
				webgisInfo.setPay_ct_no(pay_ct_no);
				serviceInfo.setCollection_id(collecitonId);
				webgisInfo.setOp_id(Long.valueOf(userId));
				feeInfoService.add(webgisInfo);
			}
			//前装服务费
			FeeInfo preloadInfo = doOpen.getPreloadInfo();
			if(preloadInfo.getMonth_fee()!=null && typeList.contains(Integer.valueOf(101))){
				preloadInfo.setSubco_no(Long.valueOf(companyid));
				preloadInfo.setCustomer_id(customer_id);
				preloadInfo.setVehicle_id(vehicle_id);
				preloadInfo.setUnit_id(unit_id);
				preloadInfo.setFeetype_id(101);
				preloadInfo.setItem_id(doOpen.getPrePack_id());
				if (preloadInfo.getFee_date() == null) {
					preloadInfo.setFee_date(new Date());
					preloadInfo.setFee_sedate(new Date());
				}else{
					preloadInfo.setFee_sedate(preloadInfo.getFee_date());
				}
				if(preloadInfo.getFee_cycle()==null){
					preloadInfo.setFee_cycle(1);
				}
				preloadInfo.setAc_amount(preloadInfo.getMonth_fee()*preloadInfo.getFee_cycle());
				preloadInfo.setReal_amount(preloadInfo.getMonth_fee()*preloadInfo.getFee_cycle());
				preloadInfo.setPay_ct_no("");
				serviceInfo.setCollection_id(null);
				preloadInfo.setOp_id(Long.valueOf(userId));
				feeInfoService.add(preloadInfo);
			}
			if(vehicle != null && customer !=null){
				OperatorUnit ou = ouDao.getByVehicle(vehicle.getVehicle_id());
				OpenLdap ldap = OpenLdapManager.getInstance();
				CommonOperator operator = ldap.getOperatorById(ou.getOp_id()+"");
				if(ou != null && customer.getCust_type() == 0 && operator == null){	//私家车在t_ba_op_unit表中有数据(海马不存数据)，且在ldap中无数据
					operator = new CommonOperator();
					operator.setOpid(ou.getOp_id()+"");
					operator.setLoginname(vehicle.getPlate_no());
					operator.setOpname(customer.getCustomer_name());
					operator.setCustomerid(customer.getCustomer_id()+"");
					operator.setNumberplate(vehicle.getPlate_no());
					String service_pwd = customer.getService_pwd();
					if(StringUtils.isNotBlank(service_pwd)){
						service_pwd = DESPlus.getDecPWD(service_pwd);
					}else{
						service_pwd = "";
					}
					operator.setUserPassword(service_pwd);
					ldap.add(operator);
					Operatelog log = new Operatelog();
					log.setUser_id(Long.valueOf(userId));
					log.setUser_name(username);
					log.setModel_id(20030);
					log.setOp_type(Integer.valueOf(SystemConst.OPERATELOG_ADD));
					log.setSubco_no(Long.valueOf(companyid));
					log.setRemark("操作ip地址:" + params.get("ip_address") + ";" +"车辆开通补录ldap数据,车牌:"
	                           +vehicle.getPlate_no()+", 参数"
	                           + "{opid:"+operator.getOpid()+", customerid:"+operator.getCustomerid()+",opname:"+operator.getOpname()
	                           +",loginname:"+operator.getLoginname()+",numberplate:"+operator.getNumberplate()+"}");
					olDao.save(log);
				}
			}
			resultMap.put("success", true);
			resultMap.put("msg", "开通成功!");
		}catch(Exception e){
			resultMap.put("success", false);
			resultMap.put("msg", "开通失败，原因:"+e.getMessage());
		}
		return resultMap;
	}

	@Override
	public Page<HashMap<String, Object>> findStopsPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=stopDao.countStops(pageSelect.getFilter());
		List<HashMap<String, Object>> list=stopDao.findStopsPage(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	
}

