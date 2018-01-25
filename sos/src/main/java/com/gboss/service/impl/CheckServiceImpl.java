package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.DataLine;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.CheckDao;
import com.gboss.pojo.Check;
import com.gboss.pojo.CheckDetails;
import com.gboss.pojo.Stockout;
import com.gboss.pojo.StockinDetails;
import com.gboss.pojo.StockoutDetails;
import com.gboss.pojo.Supplycontracts;
import com.gboss.service.CheckService;
import com.gboss.service.StockService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:CheckServiceImpl
 * @Description:盘点业务层实现类
 * @author:zfy
 * @date:2013-9-16 上午11:27:30
 */
@Service("checkService")
@Transactional
public class CheckServiceImpl extends BaseServiceImpl implements CheckService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(CheckServiceImpl.class);
	
	@Autowired  
	@Qualifier("checkDao")
	private CheckDao checkDao;
	
	@Autowired
	@Qualifier("stockService")
	private StockService stockService;
	
	
	@Override
	public Check getCheckAndDetails(Map<String,Object> map)
			throws SystemException {
		//先查询盘点详细表，判断是否已经有本月的盘点信息(如果有，就编辑，否则添加)
		Check check = checkDao.getCheck(map);
		if (check != null) {
			if(map.get("id")!=null){
				map.put("checkId", map.get("id"));
			}
			check.setCheckDetailsMap(checkDao.findCheckDetails(map));
		} else {
			//如果没有，则查询出来
			check = new Check();
			List<HashMap<String, Object>> checkdDetails2 = new ArrayList<HashMap<String, Object>>();
			//第一步：先查询上月账面结存数量、上月借货账面结存数，上个月没有，则查上一次的
			List<HashMap<String, Object>> preCheckDetails = null;
			Object yearMonth=null;
			Object startDate=null;
			Object endDate=null;
			if (map != null) {
				yearMonth=map.get("yearMonth");
				startDate=map.get("startDate");
				endDate=map.get("endDate");
				map.remove("yearMonth");
				map.remove("startDate");
				map.remove("endDate");
				Check check2 = checkDao.getCheck(map);
				if (check2 != null) {
					map.put("checkId", check2.getId());
				}
				preCheckDetails = checkDao.findCheckDetails(map);
			}
			
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("yearMonth", yearMonth);
			
		/*	//根据分公司或者营业处id得到下面的所有仓库列表
			OpenLdap openLdap = OpenLdapManager.getInstance();
			List<Long> wareHouseIds = new ArrayList<Long>();
			List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(map.get("orgId").toString());
			for (CommonCompany commonCompany : wareHouseList) {
				wareHouseIds.add(commonCompany.getCompanyno());
			}
			if (!wareHouseIds.isEmpty()) {
				map.put("wareHouseIds", wareHouseIds);
			}*/
			
			List<HashMap<String, Object>> stockinDetails = checkDao.findStockInDetails(map);
			
			List<HashMap<String, Object>> stockoutDetails = checkDao.findStockOutDetails(map);
			
			Long lastSaveNum = 0l;//上月帐面结存数量=上月月未帐面结存数量+调账数
			Long curInNum= 0l;//本月入库数量
			Long curOutNum= 0l;//本月出库数量
			Long curSaveNum= 0l;//月未帐面结存数量
			Long curObjectNum= 0l;//月未实物盘存
			Long overShortNum= 0l;//盘盈数/盘亏数（系统自动计算）
			//Long changeNum= 0l;//调账数(出库为负数、入库为正数)
			
			//出库、入库明细比上月盘点多出的，要添加的商品
			List<HashMap<String, Object>> addedPreCheckDetails=new ArrayList<HashMap<String,Object>>();
			
		    //从出库、入库明细中添加上月没有盘点在内的商品
			HashMap<String, Object> productIdKeyMap=new HashMap<String, Object>();//已存在的商品
			
			boolean isFirst=false;//是否在第一次的初始化阶段
			
			if((preCheckDetails!=null && !preCheckDetails.isEmpty())) {
				for (HashMap<String, Object> checkDetails2 : preCheckDetails) {
					if(checkDetails2.get("productId")!=null){
						productIdKeyMap.put(checkDetails2.get("productId").toString(), checkDetails2);
					}
			    }
			}else{
				isFirst=true;
			}
			//从出库明细中添加
			if((stockoutDetails!=null && !stockoutDetails.isEmpty())) {
				for (HashMap<String, Object> stockoutDetails2 : stockoutDetails) {
					if(stockoutDetails2.get("productId")!=null ){
						if(productIdKeyMap.containsKey(stockoutDetails2.get("productId").toString())){
						  continue;
						}else{
							addedPreCheckDetails.add(stockoutDetails2);
							productIdKeyMap.put(stockoutDetails2.get("productId").toString(), stockoutDetails2);
						}
					}
				}
			}
			//从入库明细中添加
			if((stockinDetails!=null && !stockinDetails.isEmpty())) {
				for (HashMap<String, Object> stockinDetails2 : stockinDetails) {
					if(stockinDetails2.get("productId")!=null){
						if(productIdKeyMap.containsKey(stockinDetails2.get("productId").toString())){
						  continue;
						}else{
							addedPreCheckDetails.add(stockinDetails2);
							productIdKeyMap.put(stockinDetails2.get("productId").toString(), stockinDetails2);
						}
					}
				
				}
			}
			
			 //如果是在第一次的初始化阶段,需要把初始化的库存加进去，账面库存=库存,不加借用
			if(isFirst){
				List<HashMap<String, Object>> stockList=stockService.findAllCurrentStocks(map,false);
				if(stockList!=null && !stockList.isEmpty()){
					for (HashMap<String, Object> hashMap : stockList) {
						if(hashMap.get("productId")!=null){
							if(productIdKeyMap.containsKey(hashMap.get("productId").toString())){
							  continue;
							}else{
								addedPreCheckDetails.add(hashMap);
								productIdKeyMap.put(hashMap.get("productId").toString(), hashMap);
							}
						}
					}
				}
			}
			//整理数据
			if (preCheckDetails==null) {	
				preCheckDetails=new ArrayList<HashMap<String,Object>>();
			}
			
			//添加从出库、入库明细多出来的商品
			preCheckDetails.addAll(addedPreCheckDetails);
			
			if((preCheckDetails!=null && !preCheckDetails.isEmpty())) {	
				for (HashMap<String, Object> checkDetails2 : preCheckDetails) {
					lastSaveNum = 0l;//上月帐面结存数量=上月月未帐面结存数量-调账（出库）数
					curInNum= 0l;//本月入库数量
					curOutNum= 0l;//本月出库数量
					curSaveNum= 0l;//月未帐面结存数量
					curObjectNum= 0l;//月未实物盘存
					overShortNum= 0l;//盘盈数/盘亏数（系统自动计算）
					//changeNum= 0l;//调账数(出库为负数、入库为正数)
					
					//第一步：设置上月账面结存数量lastSaveNum=月未帐面结存数量-调账数(上个月的 )
					lastSaveNum=(checkDetails2.get("curSaveNum")==null?0:Long.valueOf(checkDetails2.get("curSaveNum").toString()))-(checkDetails2.get("changeNum")==null?0:Long.valueOf(checkDetails2.get("changeNum").toString()));
					checkDetails2.put("lastSaveNum", lastSaveNum);
					
					//第二步：再统计本月入库数量
					for (HashMap<String, Object> stockinDetails2 : stockinDetails) {
						if(StringUtils.isNotNullOrEmpty(checkDetails2.get("productId"))&&StringUtils.isNotNullOrEmpty(stockinDetails2.get("productId"))&&checkDetails2.get("productId").toString().equals(stockinDetails2.get("productId").toString())) {
							//入库数量
							curInNum+=((Long)stockinDetails2.get("inNum")).intValue();
						}
					}
					checkDetails2.put("curInNum",curInNum);//本月入库数量
					
					//第三步：再统计本月出库数量
					for (HashMap<String, Object> stockoutDetails2 : stockoutDetails) {
						if(StringUtils.isNotNullOrEmpty(checkDetails2.get("productId"))&&StringUtils.isNotNullOrEmpty(stockoutDetails2.get("productId"))&&checkDetails2.get("productId").toString().equals(stockoutDetails2.get("productId").toString())) {
							//出库数量
							curOutNum+=((Long)stockoutDetails2.get("outNum")).intValue();
						}
					}
					checkDetails2.put("curOutNum",curOutNum);//出库销售安装数量
					
					//第三步：计算 月末账面结存数量=上月账面结存数量 lastSaveNum+本月入库数量curInNum-本月出库数量curOutNum
					curSaveNum=lastSaveNum+curInNum-curOutNum;
					
					checkDetails2.put("curSaveNum",curSaveNum);//月末账面结存数量
					  //如果是在第一次的初始化阶段,需要把初始化的库存加进去，且账面库存=库存，不用加借用数量
					if(isFirst){
						List<HashMap<String, Object>> stockList=stockService.findAllCurrentStocks(map,false);
						if(stockList!=null && !stockList.isEmpty()){
							for (HashMap<String, Object> hashMap : stockList) {
								//月未帐面结存数量为库存数量，不用加借用数量
								if(StringUtils.isNotNullOrEmpty(checkDetails2.get("productId"))&&StringUtils.isNotNullOrEmpty(hashMap.get("productId"))&&checkDetails2.get("productId").toString().equals(hashMap.get("productId").toString())) {
								   curSaveNum=(StringUtils.isNullOrEmpty(hashMap.get("num"))?0:Long.valueOf(hashMap.get("num").toString()));//+(StringUtils.isNullOrEmpty(hashMap.get("borrowNum"))?0:Long.valueOf(hashMap.get("borrowNum").toString()));
								   checkDetails2.put("curSaveNum",curSaveNum);//月末账面结存数量
								   break;
								 }
							}
						}
					}
					//第四步：计算 盘盈数/盘亏数=月未实物盘存curObjectNum-月未帐面结存数量curSaveNum
					curObjectNum=checkDetails2.get("curObjectNum")==null?0:Long.valueOf(checkDetails2.get("curObjectNum").toString());
					overShortNum=curObjectNum-curSaveNum;
					checkDetails2.put("overShortNum", overShortNum);
					
					checkdDetails2.add(checkDetails2);
				}
			}/* else{//如果没有出入库记录，很可能是在数据初始化的过程，则查询库存表
				List<HashMap<String, Object>> stockList=stockService.findAllCurrentStocks(map,false);
				if(stockList!=null && !stockList.isEmpty()){
					for (HashMap<String, Object> hashMap : stockList) {
						//上月帐面结存数量、本月入库数量、本月出库数量为0，月未帐面结存数量为库存数量+借用数量
						hashMap.put("lastSaveNum", 0);
						hashMap.put("curInNum", 0);
						hashMap.put("curOutNum", 0);
						hashMap.put("curSaveNum", (hashMap.get("num")==null?0:Long.valueOf(hashMap.get("num").toString()))+(hashMap.get("borrowNum")==null?0:Long.valueOf(hashMap.get("borrowNum").toString())));
						checkdDetails2.add(hashMap);		
					}
				}
				
			}*/
			check.setCheckDetailsMap(checkdDetails2);
		}
		return check;
	}

	@Override
	public int addCheck(Check check) throws SystemException {
		if (check == null) {
			return -1;
		}
		int result = 1;
		//判断是否已经存在
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("orgId",check.getOrgId());
		map.put("companyId",check.getCompanyId());
		map.put("startDate",check.getStartDate());
		map.put("endDate",check.getEndDate());
		if(checkDao.getCheck(map)!=null){
			result=2;
		}else{
			//先添加盘点表
			checkDao.save(check);
			Long id = check.getId();
			//再添加盘点详细
			if (check.getCheckDetails() != null && !check.getCheckDetails().isEmpty()) {
				for (CheckDetails checkDetails : check.getCheckDetails()) {
					checkDetails.setCheckId(id);
					checkDao.save(checkDetails);
				}
			}
		}
		return result;
	}

	@Override
	public int updateCheck(Check check) throws SystemException {
		if (check == null || check.getId() == null) {
			return -1;
		}
		int result=1;
		Long id=check.getId();
		//修改之前，判断存在不存在
		Check checkOld=checkDao.get(Check.class, id) ;
		if (checkOld!= null) {
			checkOld.setUserId(check.getUserId());
			if(StringUtils.isNotBlank(check.getName())){
				checkOld.setName(check.getName());
			}
			if(check.getStatus()!=null) {
				checkOld.setStatus(check.getStatus());//盘点状态（2:盘点录入 ，3：审核结束（只能调账），4.调账结束（所有记录都不能改））
			}
			//修改盘点表
			checkDao.merge(checkOld);
			//再添加明细
			if (check.getCheckDetails() != null && !check.getCheckDetails().isEmpty()) {
				
				//再删除明细
				checkDao.deleteDetailsByCheckId(id);
				
				for (CheckDetails checkDetails : check.getCheckDetails()) {
					checkDetails.setCheckId(id);
					checkDao.save(checkDetails);
				}
			}
			
		} else {
			result = 0;
		}
		return result;
	}

	@Override
	public Check getCheckAndDetails4Report(Map<String, Object> map)
			throws SystemException {
		//先查询盘点详细表，判断是否已经有本月的盘点信息(如果有，就编辑，否则添加)
				Check check = checkDao.getCheck(map);
				List<HashMap<String, Object>> detailList=null;//查出来数据的情况
				if (check != null) {
					map.put("checkId", check.getId());
					detailList=checkDao.findCheckDetails(map);
					map.put("isDetail", true);//按类型分组查明细
					map.put("startDate", DateUtil.format(check.getStartDate(),DateUtil.YMD_DASH));
					map.put("endDate", DateUtil.format(check.getEndDate(),DateUtil.YMD_DASH));
					List<HashMap<String, Object>> stockinDetails = checkDao.findStockInDetails(map);
					
					List<HashMap<String, Object>> stockoutDetails = checkDao.findStockOutDetails(map);
					
					//Long lastSaveNum=0l;//上月账面结存数量
					
					Long stockInNum=0l;//本月商品入库数量
					Long otherInNum=0l;//本月其他入库数量
					
					Long borrowOutNum=0l;//本月商品车行调剂数量
					Long selOutNum=0l;//本月商品销售安装数量
					Long upgradeOutNum=0l;//本月商品升级数量
					Long otherOutNum=0l;//本月商品除开销售出库（车行调剂）、销售安装、升级的数量
					
					//Long curSaveNum=0l;//月未帐面结存数量
					
					//Long curObjectNum=0l;//月未实物盘存
					
					//Long lastBorrowNum=0l;//上月借货账面结存数
					Long curBorrowNum=0l;//本月商品借出数量
					Long curReturnNum=0l;//本月商品归还数量
					//Long endBorrowNum=0l;//月未借贷帐面
					
				    //Long overShortNum=0l;//盘盈数/盘亏数
					//Long changeNum=0l;//调整数
					
					
					if((detailList!=null && !detailList.isEmpty())) {	
						for (HashMap<String, Object> checkDetails : detailList) {
							//lastSaveNum=0l;//上月账面结存数量
							
							stockInNum=0l;//本月商品入库数量
							otherInNum=0l;//本月其他入库数量
							
							borrowOutNum=0l;//本月商品车行调剂数量
							selOutNum=0l;//本月商品销售安装数量
							upgradeOutNum=0l;//本月商品升级数量
							otherOutNum=0l;//本月商品除开销售出库（车行调剂）、销售安装、升级的数量
							
							//curSaveNum=0l;//月未帐面结存数量
							
							//curObjectNum=0l;//月未实物盘存
							
							//lastBorrowNum=0l;//上月借货账面结存数
							curBorrowNum=0l;//本月商品借出数量
							curReturnNum=0l;//本月商品归还数量
							//endBorrowNum=0l;//月未借贷帐面
							
						    //overShortNum=0l;//盘盈数/盘亏数
							//changeNum=0l;//调整数
							//第一步：统计本月入库数量、当月归还数量
							for (HashMap<String, Object> stockinDetails2 : stockinDetails) {
								if(checkDetails.get("productId").toString().equals(stockinDetails2.get("productId").toString())) {
									//归还数量
									if (((Integer)stockinDetails2.get("type")).intValue() == 2) {
										curReturnNum+=((Long)stockinDetails2.get("inNum")).intValue();
									}else if (((Integer)stockinDetails2.get("type")).intValue() == 1) {//调拨
										otherInNum+=((Long)stockinDetails2.get("inNum")).intValue();
									}else if(((Integer)stockinDetails2.get("type")).intValue() == 9) {//调账,不需统计
										continue;
									} else {
										stockInNum+=((Long)stockinDetails2.get("inNum")).intValue();
									}
								}
							}
							checkDetails.put("curInNum",stockInNum);//入库数量
							checkDetails.put("otherInNum",otherInNum);//其他入库数量(调拨)
							checkDetails.put("curReturnNum",curReturnNum);//归还数量
							
							//第二步：再统计本月销售安装、车行调剂、升级安装数量,本月除开销售安装、车行调剂、升级安装的数量,当月借出数量
							for (HashMap<String, Object> stockoutDetails2 : stockoutDetails) {
								if(checkDetails.get("productId").toString().equals(stockoutDetails2.get("productId").toString())) {
									//借出数量
									if (((Integer)stockoutDetails2.get("type")).intValue() == 3) {
										curBorrowNum+=((Long)stockoutDetails2.get("outNum")).intValue();
									} else if(((Integer)stockoutDetails2.get("type")).intValue() == 0) {//代理销售（车行调剂）数量
										borrowOutNum+=((Long)stockoutDetails2.get("outNum")).intValue();
									} else if(((Integer)stockoutDetails2.get("type")).intValue() == 1) {//直销(销售安装数量)
										selOutNum+=((Long)stockoutDetails2.get("outNum")).intValue();
									} else if(((Integer)stockoutDetails2.get("type")).intValue() == 2) {//升级数量
										upgradeOutNum+=((Long)stockoutDetails2.get("outNum")).intValue();
									}else if(((Integer)stockoutDetails2.get("type")).intValue() == 9) {//调账,不需统计
										continue;
									}  else {
										otherOutNum+=((Long)stockoutDetails2.get("outNum")).intValue();
									}
								}
							}
							checkDetails.put("selOutNum",selOutNum);//出库销售安装数量
							checkDetails.put("borrowOutNum",borrowOutNum);//出库车行调剂数量
							checkDetails.put("upgradeOutNum",upgradeOutNum);//出库升级数量
							checkDetails.put("otherOutNum",otherOutNum);//出库除销售安装、车行调剂、升级安装以外的数量
							checkDetails.put("curBorrowNum",curBorrowNum);//本月借出的数量
							
							//第三步：计算 月末借货账面结存数=上月借货账面结存数lastBorrowNum+当月借出数量curBorrowNum-当月归还数量curReturnNum
							//checkDetails.put("endBorrowNum",lastBorrowNum+curBorrowNum-curReturnNum);
							
						}
					} 
					check.setCheckDetailsMap(detailList);
				}
				return check;
	}

	@Override
	public Page<HashMap<String, Object>> findChecksByPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=checkDao.countChecks(pageSelect.getFilter());
		List<HashMap<String, Object>> list=checkDao.findChecks(pageSelect.getFilter(),pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public int deleteChecks(List<Long> ids)
			throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			for (Long id : ids) {
				//操作之前，判断存在不存在
				if(checkDao.get(Check.class, id)!=null){
					//先删除明细
					checkDao.deleteDetailsByCheckId(id);
					//再删除盘点主表
					checkDao.delete(Check.class,id);
				}
			}
			
		}
		return result;
	}

	@Override
	public Boolean checkStatus(Long companyId, List<Long> whsIds, Integer status,
			Boolean isEqlStatus) throws SystemException {
		return checkDao.checkStatus(companyId, whsIds, status, isEqlStatus);
	}

	@Override
	public int updateCheckStatus(Check check) throws SystemException,Exception {
		if (check == null || check.getId() == null) {
			return -1;
		}
		int result=1;
		Long id=check.getId();
		//修改之前，判断存在不存在
		Check checkOld=checkDao.get(Check.class, id) ;
		if (checkOld!= null) {
			checkOld.setCheckStamp(new Date());
			checkOld.setCheckUserId(check.getCheckUserId());
			if(check.getStatus()!=null) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("checkId", check.getId());
				map.put("overShortNum", 0);//有不等于0的就直接进入调账结束
				//盘点盘盈、盘亏数有没有不等于0的，如果没有，则自动进入调账结束的状态
				List<HashMap<String, Object>> checkDetails=checkDao.findCheckDetails(map);
				if(checkDetails!=null && !checkDetails.isEmpty()){
					checkOld.setStatus(check.getStatus());//3：审核结束（只能调账）
				}else{
					checkOld.setStatus(4);//4.调账结束（所有记录都不能改））
					//修改调账数为0
					checkDao.updateChangeNumByCheckId(check.getId());
				}
			}
			checkDao.merge(checkOld);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public HashMap<String, Object>  updateCheckChangeNum(Check check,String whsCode) throws SystemException,Exception {
		HashMap<String, Object> result =new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if (check == null || check.getId() == null) {
			flag=false;
			msg="操作的对象不存在!";
		}else{
			Long id=check.getId();
			//修改之前，判断存在不存在
			Check checkOld=checkDao.get(Check.class, id) ;
	
			if (checkOld!= null) {
				checkOld.setUserId(check.getUserId());
				
				Integer status=4;//已调账完毕
				
				//出库信息
				Stockout stockout = new Stockout();
				stockout.setWhsId(checkOld.getWhsId());
				stockout.setWhsName(checkOld.getWhsName());
				stockout.setCompanyId(checkOld.getCompanyId());
				stockout.setCompanyName(checkOld.getCompanyName());
				stockout.setType(9);
				stockout.setUserId(checkOld.getUserId());
				stockout.setUserName(checkOld.getUserName());
				stockout.setStamp(checkOld.getEndDate());//入库日期必须是在盘点开始到结束日期之间，默认选择结束日期
				stockout.setCode(stockService.getStockInOutNo(checkOld.getCompanyId(), checkOld.getWhsId(), whsCode, false,checkOld.getEndDate()));
				stockout.setManagersId(check.getUserId());
				stockout.setManagersName(check.getUserName());
				stockout.setRemark("盘点名称为["+checkOld.getName()+"]的调账.");
				
				//出库明细
				List<StockoutDetails> stockoutDetailsList = new ArrayList<StockoutDetails>();
				
				//再修改明细
				if (check.getCheckDetails() != null && !check.getCheckDetails().isEmpty()) {
					CheckDetails checkDetailsOld=null;
					Long overShortNum=null;
					Long changeNum=null;
					StockoutDetails stockoutDetails=null;
					for (CheckDetails checkDetails : check.getCheckDetails()) {
						checkDetailsOld=checkDao.get(CheckDetails.class, checkDetails.getId());
						if(checkDetailsOld!=null){
							
							overShortNum=checkDetailsOld.getOverShortNum()==null?0:checkDetailsOld.getOverShortNum();
							changeNum=checkDetails.getChangeNum()==null?0:checkDetails.getChangeNum();
							//进行出库操作
							if(changeNum!=0){//出库明细
								stockoutDetails = new StockoutDetails();
								stockoutDetails.setProductId(checkDetailsOld.getProductId());
								//商品名称
								stockoutDetails.setProductName(checkDetails.getProductName());
								//商品编码
								stockoutDetails.setProductCode(checkDetails.getProductCode());
								//商品规格
								stockoutDetails.setNorm(checkDetails.getNorm());
								stockoutDetails.setOutNum(changeNum.intValue());
								
								stockoutDetails.setRemark(checkDetails.getRemark());
								stockoutDetailsList.add(stockoutDetails);
							}
							//比较盈亏数和调账数目，如果有一个不相等，则调账未完成
							if(overShortNum+(checkDetailsOld.getChangeNum()==null?0:checkDetailsOld.getChangeNum())+changeNum!=0){
								status=3;
							}
							checkDetailsOld.setChangeNum((checkDetailsOld.getChangeNum()==null?0:checkDetailsOld.getChangeNum())+changeNum);
							checkDao.merge(checkDetailsOld);
						}
					}
				}
				checkOld.setStatus(status);
				checkDao.merge(checkOld);
				
				if(!stockoutDetailsList.isEmpty()){
					stockout.setStockoutDetails(stockoutDetailsList);
					//出库
					result.put("stockOut", stockout);//出库信息回传，用于打印
					HashMap<String, Object> map=stockService.addStockOut(stockout,  whsCode,false);
					Boolean flag2=(Boolean) map.get(SystemConst.SUCCESS);
					if(!flag2){
						throw new RuntimeException("调账出库异常 ["+map.get(SystemConst.MSG)+"]请重试!");
					}
				}
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}

	@Override
	public List<HashMap<String, Object>> findAllChecks(Map<String, Object> map)
			throws SystemException {
		return checkDao.findChecks(map,null,false ,0,0);
	}

	@Override
	public Check getCheck(Map<String, Object> map) throws SystemException {
		return checkDao.getCheck(map);
	}

	@Override
	public List<HashMap<String, Object>> findCheckDetails4Reprot(Map<String, Object> map)
			throws SystemException {
		return checkDao.findCheckDetails4Reprot(map);
	}

}

