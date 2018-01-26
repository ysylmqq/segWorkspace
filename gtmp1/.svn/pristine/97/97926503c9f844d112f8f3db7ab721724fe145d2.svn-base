package com.chinaGPS.gtmp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import sun.security.util.BigInt;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.SimPayPOJO;
import com.chinaGPS.gtmp.entity.SimReplaceLogPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.mapper.AllProfitPOJOMapper;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.CustomerPayPOJOMapper;
import com.chinaGPS.gtmp.mapper.CustomerSimPOJOMapper;
import com.chinaGPS.gtmp.mapper.SimPayPOJOMapper;
import com.chinaGPS.gtmp.mapper.SimReplaceLogMapper;
import com.chinaGPS.gtmp.mapper.SimServerMapper;
import com.chinaGPS.gtmp.mapper.SimServerPOJOMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.service.ISimServerService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:ISimServerService
 * @Description:玉柴重工使用SIM卡service
 * @author:于松亚
 * @date:2017-3-21 上午10:15:43
 */
@Service
public class SimServerServiceImpl extends BaseServiceImpl<SimServerPOJO> implements ISimServerService {
	@Resource
	private SimServerPOJOMapper<SimServerPOJO> simServerPOJOMapper;
	
	@Resource
	private SimPayPOJOMapper<SimPayPOJO> simPayPOJOMapper;
	
	@Resource
	private SimServerMapper<SimServerPOJO> simServerMapper;
	
	@Resource
	private CustomerSimPOJOMapper<CustomerSimPOJO> customerSimPOJOMapper;
	
	@Resource
	private CustomerPayPOJOMapper<CustomerPayPOJO> customerPayPOJOMapper;
	
	@Resource
	private UnitMapper<UnitPOJO> unitPOJOMapper;
	
	@Resource
	private SimReplaceLogMapper<SimReplaceLogPOJO> simReplaceLogMapper;
	
	
	@Resource
	private AllProfitPOJOMapper<SimServerPOJO> allProfitPOJOMapper;

	@Override
	protected BaseSqlMapper<SimServerPOJO> getMapper() {
		return this.simServerPOJOMapper;
	}

	@Override
	public SimServerPOJO getSimServerPOJOById(String simNo) {
		return simServerPOJOMapper.getSimServerPOJOById(simNo);
	}

	@Override
	public void updateStatus(SimServerPOJO record) {
		simServerPOJOMapper.updateStatus(record);
	}

	@Override
	public void updateByPrimaryKeySelective(SimServerPOJO record) throws Exception {
		simServerPOJOMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public HashMap<String, Object> changeUnitsearch(SimServerPOJO simServerPOJO,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("simServerPOJO", simServerPOJO);
		int total = simServerMapper.countAll(map);
		List<SimServerPOJO> resultList =  simServerMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public HashMap<String, Object> getSimServer(SimServerPOJO simServerPOJO,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("simServerPOJO", simServerPOJO);
		int total = simServerPOJOMapper.countAll(map);
		List<SimServerPOJO> resultList =  simServerPOJOMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	
	@Override
	public HashMap<String, Object> allProfit(SimServerPOJO simServerPOJO,
			PageSelect pageSelect) throws Exception {
		Map map = new HashMap();
		map.put("simServerPOJO", simServerPOJO);
		int total = allProfitPOJOMapper.countAll(map);
		List<SimServerPOJO> resultList =  allProfitPOJOMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		//遍历  查询机械的收益 速度太慢了 
		Map customerMap = new HashMap();
		//客户SIM卡缴费
		Map customerMapSIMAllFee = new HashMap();
		//客户停机保号费用
		Map customerMapStopAllFee = new HashMap();

		List<Map> customerAllPayMap = simServerPOJOMapper.customerAllPayMap();
		for (Iterator iterator = customerAllPayMap.iterator(); iterator
				.hasNext();) {
			Map map2 = (Map) iterator.next();
			customerMap.put(map2.get("SIM_NO"),map2.get("FEE"));
			customerMapSIMAllFee.put(map2.get("SIM_NO"),map2.get("SIM_ALL_FEE"));
			customerMapStopAllFee.put(map2.get("SIM_NO"),map2.get("STOP_ALL_FEE"));
		}
		
		Map companyMap = new HashMap();
		List<Map> companyAllPayMap = simServerPOJOMapper.companyAllPayMap();
		for (Iterator iterator = companyAllPayMap.iterator(); iterator
				.hasNext();) {
			Map map2 = (Map) iterator.next();
			companyMap.put(map2.get("SIM_NO"),map2.get("FEE"));
		}
		
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			SimServerPOJO simServerPOJO2 = (SimServerPOJO) iterator.next();
			String simNo = simServerPOJO2.getSimNo();
			if( StringUtils.isNotEmpty(simNo) ){
				float  customerAllPay1 = 0 , allCompanyPay1 = 0,customerSimAllPay = 0,customerStopAllFee = 0;
				if( customerMap.containsKey(simNo)){
					if(!"".equalsIgnoreCase(customerMap.get(simNo).toString())){
						customerAllPay1 = Float.parseFloat(customerMap.get(simNo).toString());
						customerSimAllPay = Float.parseFloat(customerMapSIMAllFee.get(simNo).toString());
						customerStopAllFee = Float.parseFloat(customerMapStopAllFee.get(simNo).toString());
					}
				}else{
					//客户没有开通SIM服务
					customerAllPay1 = 0;
					customerSimAllPay = 0;
					customerStopAllFee = 0;
				}
				
				if( companyMap.containsKey(simNo)){
					if(!"".equalsIgnoreCase(companyMap.get(simNo).toString())){
						allCompanyPay1 = Float.parseFloat(companyMap.get(simNo).toString());
					}
				}else{
					//公司没有开通SIM服务
					allCompanyPay1 = 0;
				}
				float allProfit = customerAllPay1 -allCompanyPay1 ;
				simServerPOJO2.setAllProfit(allProfit);
				simServerPOJO2.setCustomerAllPay(customerAllPay1);
				simServerPOJO2.setCompanyAllPay(allCompanyPay1);
				simServerPOJO2.setCustomerSimAllPay(customerSimAllPay);
				simServerPOJO2.setCustomerStopAllFee(customerStopAllFee);
			}
		}
		
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public int changeSim(SimReplaceLogPOJO simReplaceLog)
			throws Exception {
		// 在注册终端的时候，t_sim_server表当当中已经存在了 先判断新的SimNo是否存在
		SimServerPOJO newTemp = simServerPOJOMapper.getSimServerPOJOById(simReplaceLog.getNewCallLetter());
		if(newTemp == null){
			return -1; //"新的SIM卡已经不存在t_sim_server  也就是说 公司没有开通新卡的SIM卡服务"
		}
		SimServerPOJO oldTemp = simServerPOJOMapper.getSimServerPOJOById(simReplaceLog.getOldCallLetter());
		if(oldTemp == null){
			return -2; //"老的SIM卡已经不存在t_sim_server  也就是说 公司没有开通老卡的SIM卡服务"
		}
		
		CustomerSimPOJO customerSimPOJOTempNew = customerSimPOJOMapper.getCustomerSimPOJOById(simReplaceLog.getNewCallLetter());
		if(customerSimPOJOTempNew != null){
			return -3; //"新的SIM卡已经被占用 t_customer_sim当中已经存在"
		}
		
		CustomerSimPOJO customerSimPOJOTempOld =  customerSimPOJOMapper.getCustomerSimPOJOById(simReplaceLog.getOldCallLetter());
		if(customerSimPOJOTempOld == null){
			//"客户没有开通SIM卡功能"   所有的费用为0 。  不用往t_customer_sim卡当中插入记录
		}else{
			//客户开通了sim卡功能   把新的SIM插入到t_customer_sim当中
			CustomerSimPOJO  customerSimPOJO = new CustomerSimPOJO();
			customerSimPOJO.setSimNo(simReplaceLog.getNewCallLetter());
			customerSimPOJO.setStartTime(customerSimPOJOTempOld.getStartTime());
			customerSimPOJO.setEndTime(customerSimPOJOTempOld.getEndTime());
			customerSimPOJO.setFreePeriod(customerSimPOJOTempOld.getFreePeriod());
			customerSimPOJO.setOperId(simReplaceLog.getOperId());
			customerSimPOJO.setStatus(new BigDecimal(0));
			//获取SIm更新之前的客户的缴费情况        旧卡的缴费总金额+旧卡allFee
			int customerAllPay = customerPayPOJOMapper.getAllPay(simReplaceLog.getOldCallLetter())+customerSimPOJOTempOld.getAllFee().intValue();
			customerSimPOJO.setAllFee(new BigDecimal(customerAllPay));
			customerSimPOJOMapper.insertSelective(customerSimPOJO);
		}
		// update t_sim_server   更新新卡的allFee
		//获取SIm更新之前的公司的缴费情况  old      旧卡的缴费总金额+旧卡allFee+旧卡的成本   +oldTemp.getAllFee().intValue()
		int allCompanyPay = simPayPOJOMapper.getAllPay(simReplaceLog.getOldCallLetter())+oldTemp.getPayAmount().intValue();
		newTemp.setAllFee(new BigDecimal(allCompanyPay));
		simServerPOJOMapper.updateAllFee(newTemp);
		//insert t_sim_replacelog
		
		int newSimAll = simPayPOJOMapper.getAllPay(simReplaceLog.getNewCallLetter()); // 新卡缴费总金额
		BigDecimal newSimAllFee =  newTemp.getPayAmount().add(new BigDecimal(newSimAll));
		
		simReplaceLog.setNewAllFee( newSimAllFee ); // 新卡的成本  +新卡的feeAll +新卡的缴费
		simReplaceLog.setNewStartDate(newTemp.getOpenTime());
		simReplaceLog.setNewEndDate(newTemp.getEndTime());
		
		simReplaceLog.setOldAllFee(new BigDecimal(allCompanyPay));
		simReplaceLog.setOldStartDate(oldTemp.getOpenTime());
		simReplaceLog.setOldEndDate(oldTemp.getEndTime());
		
		simReplaceLogMapper.insertSelective(simReplaceLog);
		return 1;
	}

	
	@Override
	public int changeUnit(SimServerPOJO simServerPOJO,String oldSimNo) throws Exception{
		//插入的时候 先判断是否存在 
		SimServerPOJO newTemp = simServerPOJOMapper.getSimServerPOJOById(simServerPOJO.getSimNo());
		if(newTemp != null){
			return -1; //"新的SIM卡已经被占用"
		}
		SimServerPOJO oldTemp = simServerPOJOMapper.getSimServerPOJOById(oldSimNo);
		if(oldTemp == null){
			return -2; //"公司没有开通SIM卡服务"
		}
		if( newTemp ==  null && oldTemp != null ){
			//判断客户是否开通了SIM卡服务  ，如果开通了 插入记录   原来的记录保留？？？  待定
			CustomerSimPOJO customerSimPOJOTempOld =  customerSimPOJOMapper.getCustomerSimPOJOById(oldSimNo);
			if(customerSimPOJOTempOld == null){
				return 0; //"客户没有开通SIM卡功能"
			}
			
			CustomerSimPOJO customerSimPOJOTempNew =  customerSimPOJOMapper.getCustomerSimPOJOById(simServerPOJO.getSimNo());
			if(customerSimPOJOTempNew != null){
				return -1; //"新的SIM卡已经被占用"
			}
			
			if( customerSimPOJOTempNew == null  && customerSimPOJOTempOld != null){
				CustomerSimPOJO  customerSimPOJO = new CustomerSimPOJO();
				customerSimPOJO.setSimNo(simServerPOJO.getSimNo());
				customerSimPOJO.setStartTime(customerSimPOJOTempOld.getStartTime());
				customerSimPOJO.setEndTime(customerSimPOJOTempOld.getEndTime());
				customerSimPOJO.setFreePeriod(customerSimPOJOTempOld.getFreePeriod());
				customerSimPOJO.setOperId(simServerPOJO.getOperId());
				customerSimPOJO.setStatus(new BigDecimal(0));
				//获取SIm更新之前的客户的缴费情况        旧卡的缴费总金额+旧卡allFee
				int customerAllPay = customerPayPOJOMapper.getAllPay(oldSimNo)+customerSimPOJOTempOld.getAllFee().intValue();
				customerSimPOJO.setAllFee(new BigDecimal(customerAllPay));
				customerSimPOJOMapper.insertSelective(customerSimPOJO);
				
				//t_sim_server
				//获取SIm更新之前的公司的缴费情况  old      旧卡的缴费总金额+旧卡allFee+旧卡的成本
				int allCompanyPay = simPayPOJOMapper.getAllPay(oldSimNo)+oldTemp.getAllFee().intValue()+oldTemp.getPayAmount().intValue();
				simServerPOJO.setAllFee(new BigDecimal(allCompanyPay));
				simServerPOJOMapper.insertSelective(simServerPOJO);
				return 1;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	
	@Override
	public 	int[] getAllFee(String simNo) throws Exception{
		SimServerPOJO simServerPOJO = simServerPOJOMapper.getSimServerPOJOById(simNo);
		CustomerSimPOJO customerSimPOJO =  customerSimPOJOMapper.getCustomerSimPOJOById(simNo);
		int allProfit = 0;
		int customerAllPay = 0;
		int allCompanyPay = 0;
		if( simServerPOJO != null && customerSimPOJO != null ){
			//客户缴费所有记录总金额    = 该卡的所有缴费记录总和+该卡所对应的allFee
			 customerAllPay = customerPayPOJOMapper.getAllPay(simNo)+customerSimPOJO.getAllFee().intValue();
			//公司SIM卡缴费的总金额  = SIM缴费总和+ allFee+ SIM卡成本  payAmount
			 allCompanyPay = simPayPOJOMapper.getAllPay(simNo)+simServerPOJO.getAllFee().intValue()+simServerPOJO.getPayAmount().intValue();
			// 总收益 =  客户缴费总金额- 公司缴费总额
			allProfit = customerAllPay - allCompanyPay;
		}
		int []res = new int[]{allProfit,customerAllPay,allCompanyPay};
		return res;
	}
	
	@Override
	public boolean insertSelective(SimServerPOJO simServerPOJO)
			throws Exception {
		//插入的时候 先判断是否存在 
		SimServerPOJO temp = simServerPOJOMapper.getSimServerPOJOById(simServerPOJO.getSimNo());
		if( temp ==  null){
			simServerPOJOMapper.insertSelective(simServerPOJO);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<SimServerPOJO>  allVehicleProfit(SimServerPOJO simServerPOJO) throws Exception {
		Map map = new HashMap();
		map.put("simServerPOJO", simServerPOJO);
		
		Map customerMap = new HashMap();
		Map customerMapSIMAllFee = new HashMap();
		//客户停机保号费用
		Map customerMapStopAllFee = new HashMap();
		
		List<Map> customerAllPayMap = simServerPOJOMapper.customerAllPayMap();
		for (Iterator iterator = customerAllPayMap.iterator(); iterator
				.hasNext();) {
			Map map2 = (Map) iterator.next();
			customerMap.put(map2.get("SIM_NO"),map2.get("FEE"));
			customerMapSIMAllFee.put(map2.get("SIM_NO"),map2.get("SIM_ALL_FEE"));
			customerMapStopAllFee.put(map2.get("SIM_NO"),map2.get("STOP_ALL_FEE"));
		}
		
		Map companyMap = new HashMap();
		List<Map> companyAllPayMap = simServerPOJOMapper.companyAllPayMap();
		for (Iterator iterator = companyAllPayMap.iterator(); iterator
				.hasNext();) {
			Map map2 = (Map) iterator.next();
			companyMap.put(map2.get("SIM_NO"),map2.get("FEE"));
		}
		
		List<SimServerPOJO> resultList =  simServerPOJOMapper.outputProfit(map);
		
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			SimServerPOJO simServerPOJO2 = (SimServerPOJO) iterator.next();
			String simNo = simServerPOJO2.getSimNo();
			if( StringUtils.isNotEmpty(simNo) ){
				float  customerAllPay1 = 0 , allCompanyPay1 = 0,customerSimAllPay = 0,customerStopAllFee = 0;
				if( customerMap.containsKey(simNo)){
					if(!"".equalsIgnoreCase(customerMap.get(simNo).toString())){
						customerAllPay1 = Float.parseFloat(customerMap.get(simNo).toString());
						customerSimAllPay = Float.parseFloat(customerMapSIMAllFee.get(simNo).toString());
						customerStopAllFee = Float.parseFloat(customerMapStopAllFee.get(simNo).toString());
					}
				}else{
					//客户没有开通SIM服务
					customerAllPay1 = 0;
					customerSimAllPay = 0;
					customerStopAllFee = 0;
				}
				
				if( companyMap.containsKey(simNo)){
					if(!"".equalsIgnoreCase(companyMap.get(simNo).toString())){
						allCompanyPay1 = Float.parseFloat(companyMap.get(simNo).toString());
					}
				}else{
					//公司没有开通SIM服务
					allCompanyPay1 = 0;
				}
				float allProfit = customerAllPay1 -allCompanyPay1 ;
				simServerPOJO2.setAllProfit(allProfit);
				simServerPOJO2.setCustomerAllPay(customerAllPay1);
				simServerPOJO2.setCompanyAllPay(allCompanyPay1);
				simServerPOJO2.setCustomerSimAllPay(customerSimAllPay);
				simServerPOJO2.setCustomerStopAllFee(customerStopAllFee);
			}
		}
		return resultList;
	}
	
	
	@Override
	public List<UnitPOJO> getUnitList() {
		return simServerPOJOMapper.getUnitList();
	}

	@Override
	public List<SimServerPOJO> simServerList() {
		return simServerPOJOMapper.simServerList();
	}

	@Override
	public List<SimServerPOJO> allSimServer(SimServerPOJO simServerPOJO)  {
		Map map = new HashMap();
		map.put("simServerPOJO", simServerPOJO);
		return simServerPOJOMapper.allSimServer(map);
	}
	
	@Override
	public List<SimServerPOJO> exportSimServer(SimServerPOJO simServerPOJO)  {
		Map map = new HashMap();
		map.put("simServerPOJO", simServerPOJO);
		return simServerPOJOMapper.exportSimServer(map);
	}
	
	@Override
	public List<SimServerPOJO> exportChangeUnit(SimServerPOJO simServerPOJO)  {
		Map map = new HashMap();
		map.put("simServerPOJO", simServerPOJO);
		return simServerPOJOMapper.exportChangeUnit(map);
	}

	
	@Override
	public List<SimServerPOJO> batchInsert(List<SimServerPOJO> list,String userId) throws Exception {
		List<SimServerPOJO> errorList = new ArrayList<SimServerPOJO>();
		boolean res = true;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SimServerPOJO simServerPOJO = (SimServerPOJO) iterator.next();
			String simNo = simServerPOJO.getSimNo();
			
			Date openTime = simServerPOJO.getOpenTime();
			String unitId = simServerPOJO.getUnitId();
			StringBuilder erroTips = new StringBuilder("");
			boolean flag = true;
			if(simNo == null || "".equalsIgnoreCase(simNo)){
				erroTips.append("simNO为空");
				flag = false;
			}else {
				int len = simNo.length();
				if( len != 11){
					erroTips.append("simNO有误");
					flag = false;
				}
			}
			if(openTime == null){
				erroTips.append("服务开始日期为空");
				flag = false;
			}
			simServerPOJO.setOperId(userId);
			if(flag){
			//包含了 判断是否存在	
				res = this.insertSelective(simServerPOJO); 
				if(!res){
					simServerPOJO.setRemark("已经开通SIM服务功能");
					errorList.add(simServerPOJO);
				}
			}else{
				simServerPOJO.setRemark(erroTips.toString());
				errorList.add(simServerPOJO);
			}
		}
		return errorList;
	}
	
	@Override
	public List<SimServerPOJO> batchCancelSim(List<SimServerPOJO> list,String userId) throws Exception {
		List<SimServerPOJO> errorList = new ArrayList<SimServerPOJO>();
		boolean res = true;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SimServerPOJO simServerPOJO = (SimServerPOJO) iterator.next();
			String simNo = simServerPOJO.getSimNo();
			
			Date openTime = simServerPOJO.getStopTime();
			StringBuilder erroTips = new StringBuilder("");
			boolean flag = true;
			if(simNo == null || "".equalsIgnoreCase(simNo)){
				erroTips.append("simNO为空");
				flag = false;
			}else {
				int len = simNo.length();
				if( len != 11){
					erroTips.append("simNO有误");
					flag = false;
				}
			}
			if(openTime == null){
				erroTips.append("注销日期为空");
				flag = false;
			}
			simServerPOJO.setOperId(userId);
			if(flag){
			//包含了 判断是否存在	
				SimServerPOJO temp = simServerPOJOMapper.getSimServerPOJOById(simServerPOJO.getSimNo());
				if(temp == null){ //不存在
					simServerPOJO.setRemark("没有开通SIM服务功能");
					errorList.add(simServerPOJO);
					continue;
				}else{
					 int status = temp.getStatus().intValue();
					 if( status == 1){
						 	simServerPOJO.setRemark("该卡已注销");
							errorList.add(simServerPOJO);
							continue;
					 }else{
						simServerPOJO.setStatus(new BigDecimal(1));
						simServerPOJOMapper.updateStatus(simServerPOJO); 
					 }
				}
			}else{
				simServerPOJO.setRemark(erroTips.toString());
				errorList.add(simServerPOJO);
			}
		}
		return errorList;
	}

	@Override
	public void delSimServerPOJOById(String simNo) {
		simServerPOJOMapper.deleteByPrimaryKey(simNo);
	}

}
