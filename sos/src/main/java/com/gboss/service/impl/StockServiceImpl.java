package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.OrderDao;
import com.gboss.dao.StockDao;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.Order;
import com.gboss.pojo.OrderDetails;
import com.gboss.pojo.Stock;
import com.gboss.pojo.Stockin;
import com.gboss.pojo.StockinDetails;
import com.gboss.pojo.Stockout;
import com.gboss.pojo.StockoutDetails;
import com.gboss.pojo.Writeoffs;
import com.gboss.pojo.WriteoffsDetails;
import com.gboss.service.CheckService;
import com.gboss.service.StockService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:StockServiceImpl
 * @Description:库存业务层实现类
 * @author:zfy
 * @date:2013-8-30 上午11:20:01
 */
@Service("stockService")
@Transactional
public  class StockServiceImpl extends BaseServiceImpl implements StockService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(StockSetupServiceImpl.class);
	
	@Autowired  
	@Qualifier("stockDao")
	private StockDao stockDao;
	
	@Autowired  
	@Qualifier("orderDao")
	private OrderDao orderDao;
	
	@Autowired
	@Qualifier("checkService")
	private CheckService checkService;
	
	
	@Override
	public Page<HashMap<String, Object>> findCurrentStocks(PageSelect<Map<String, Object>> pageSelect,boolean onlyStock) throws SystemException {
		List<HashMap<String, Object>> dataList=null;
		int total = 0;
		if(onlyStock){//只查询当前库存
			dataList=stockDao.findCurrentStocks(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
			total = stockDao.countCurrentStocks(pageSelect.getFilter());
		}else{//查询当前库存、借用数量、销售未还款数量
			dataList=stockDao.findCurrentStocks2(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
			total = stockDao.countCurrentStocks2(pageSelect.getFilter());
		}
		return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());		
	}
	@Override
	public List<HashMap<String, Object>> findAllCurrentStocks(Map<String, Object> map,boolean onlyStock) throws SystemException {
		List<HashMap<String, Object>> dataList=null;
		if(onlyStock){//只查询当前库存
			dataList=stockDao.findCurrentStocks(map,null,false,0,0);
		}else{//查询当前库存、借用数量、销售未还款数量
			dataList=stockDao.findCurrentStocks2(map,null,false,0,0);
		}
		return dataList;
	}
	@Override
	public List<HashMap<String, Object>> findStockLtSetup(
			Map<String, Object> conditionMap) throws SystemException {
		List<HashMap<String, Object>> stockLtSetups=stockDao.findStockLtSetup(conditionMap);
		List<HashMap<String, Object>> stockOverTimes=stockDao.findStockOverTime(conditionMap);
		if(stockLtSetups!=null&&stockOverTimes!=null&&!stockOverTimes.isEmpty()){
			String productId=null;
			String productId2=null;
			String whsId=null;
			String whsId2=null;
			List<HashMap<String, Object>> addedList=new ArrayList<HashMap<String,Object>>();
			for (HashMap<String, Object> hashMap : stockLtSetups) {
				productId=hashMap.get("productId")!=null?	hashMap.get("productId").toString():null;	
				whsId=hashMap.get("whsId")!=null?	hashMap.get("whsId").toString():null;
				for (HashMap<String, Object> hashMap2 : stockOverTimes) {
					productId2=hashMap2.get("productId")!=null?	hashMap2.get("productId").toString():null;	
					whsId2=hashMap2.get("whsId")!=null?	hashMap2.get("whsId").toString():null;
					if(productId!=null && productId.equals(productId2) && whsId!=null && whsId.equals(whsId2)){
						hashMap.put("stamp", hashMap2.get("stamp"));
					}else{
						addedList.add(hashMap2);
					}
				}
			}
			stockLtSetups.addAll(addedList);
		}
		return stockLtSetups;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public HashMap<String, Object> addStockOut(Stockout stockout,String whsCode,boolean isNotChangeNum) throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		Boolean isWriteoffNow=null;
		if(stockout==null){
			flag=false;
			msg="参数不正确!";
		}else{
			//判断是否在盘点，如果不是才需要入库
			List<Long> wareHouseIds=new ArrayList<Long>();
			wareHouseIds.add(stockout.getWhsId());
			if(isNotChangeNum && checkService.checkStatus(stockout.getCompanyId(), wareHouseIds, 4, false)){
				flag=false;
				msg="仓库正在盘点，不能进行出库操作!";
			}else{
				//判断单号是否存在
				if(stockDao.checkStockOutCode(stockout)){
					//自动生成单号
					flag=false;
					msg = "出库单号为[" + stockout.getCode() + "]的已存在,将自动生成新的出库单号!";
					result.put("code", getStockInOutNo(stockout.getCompanyId(), stockout.getWhsId(), whsCode, false,null));
				}else{
					//类型
					Integer type=stockout.getType();
					if(stockout.getStamp()==null){
						stockout.setStamp(new Date());
					}
					stockout.setIsCompleted(0);//调拨未完成
					stockDao.save(stockout);
					Long id=stockout.getId();
					isWriteoffNow=stockout.getIsWriteoffNow();//是否立即核销
					//核销的信息
					Writeoffs writeoffs=null;
					List<WriteoffsDetails> writeoffsDetails=null;
					WriteoffsDetails writeoffsDetailsObj=null;
					if(isWriteoffNow!=null && isWriteoffNow){
						writeoffs=new Writeoffs();
						writeoffsDetails=new ArrayList<WriteoffsDetails>();
						writeoffs.setCompanyId(stockout.getCompanyId());
						writeoffs.setManagersId(stockout.getManagersId());
						writeoffs.setManagersName(stockout.getManagersName());
						writeoffs.setOrgId(stockout.getOrgId());
						writeoffs.setPeopleMoney(stockout.getMoney());
						writeoffs.setReceiptNo(stockout.getReceiptNo());
						writeoffs.setRemark(stockout.getRemark());
						writeoffs.setStamp(new Date());
						writeoffs.setUserId(stockout.getUserId());
						writeoffs.setUserName(stockout.getUserName());
						writeoffs.setWhsId(stockout.getWhsId());
						writeoffs.setWriteoffsNo(getWriteOffsNo(stockout.getCompanyId(), stockout.getOrgId(), stockout.getWhsId(), whsCode, true));
					}
					//库存
					Stock stock=null;
					Stock stock2=null;
					
					OpenLdap openLdap=new OpenLdap();
					CommonOperator commonOperator=null;
					String orgId = null;
					String orgName = null;
					Integer numBefore=0;//出库前的库存
					
					Float price = null; //订单中的商品价格
					//StringBuffer sb=new StringBuffer();//存放出库明细ID
					
					if(stockout.getStockoutDetails()!=null&&!stockout.getStockoutDetails().isEmpty()){
						for (StockoutDetails stockoutDetails : stockout.getStockoutDetails()) {
							//第一步:修改库存
							//库存
							stock=new Stock();
							stock.setWhsId(stockout.getWhsId());
							stock.setWhsName(stockout.getWhsName());
							stock.setProductId(stockoutDetails.getProductId());
							
							//判断表中记录是否存在
							stock2=stockDao.getStockByWhsAndProduct(stock);
							if(stock2!=null){
								numBefore=stock2.getNum();
								//存在就修改,stock已持久化，则set也能修改值
								if(stock2.getNum()-stockoutDetails.getOutNum()<0){
									/*try {
										//手动回滚
										TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
										System.out.println("["+stock2.getProductName()+"]库存不足!");
									}finally{
										return 0;
									}*/
									throw new RuntimeException("名称为["+stockoutDetails.getProductName()+"]的商品库存不足,操作失败!");
									
								}
								stock2.setUserId(stockout.getUserId());
								stock2.setUserName(stockout.getUserName());
								stock2.setNum(stock2.getNum()-stockoutDetails.getOutNum());
								stockDao.merge(stock2);
							}else{//不存在就回滚，提示用户
								/*try {
									//手动回滚
									TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
									System.out.println("["+stock.getProductName()+"]库存不足!");
								}finally{
									return 0;
								}*/
								throw new RuntimeException("名称为["+stockoutDetails.getProductName()+"]的商品库存不足,操作失败!");
							}
							
							//第二步:出库
							stockoutDetails.setCurNum(numBefore);
							stockoutDetails.setIsCompleted(0);//调拨未完成
							stockoutDetails.setAllocatedNum(0);//已调拨数量
							stockoutDetails.setStockoutId(id);
							//借用人信息
							commonOperator=openLdap.getOperatorById(stockout.getManagersId().toString());
							if(commonOperator!=null&&!commonOperator.getCompanynos().isEmpty()){
								orgId = commonOperator.getCompanynos().get(0);
								CommonCompany commonOrg=openLdap.getCompanyById(orgId);
								if(commonOrg!=null){
									orgName=commonOrg.getCompanyname();
								}
							}
							//如果是借用、其他,价格从订单中获得-》改从总部供货合同中获得；代理销售、直销、升级从页面输入
							if(type.intValue()==3 || type.intValue()==5) {
								//price = orderDao.getOrderPriceByProductId(stockout.getCompanyId(),stockoutDetails.getProductId());
								price = orderDao.getPriceByProductId(stockout.getCompanyId(),stockoutDetails.getProductId(),DateUtil.formatToday(),1);
								if (price != null) {
									stockoutDetails.setPrice(price);
								}
							}
							stockDao.save(stockoutDetails);
							
							//第三步：操作其他相关表
							//如果是除开调拨、调账以外的出库都要挂账,添加借用挂账记录
							if(!(type.intValue()==4 || type.intValue()==9)){
								BorrowDetails borrow=new BorrowDetails();
								//sb.append(stockoutDetails.getId()).append(",");
								borrow.setBorrowerId(stockout.getManagersId());
								borrow.setBorrowerName(stockout.getManagersName());
								//设置用户的所属机构和分公司ID
								if(orgId!=null){
									borrow.setOrgId(Long.valueOf(orgId));
								}
								borrow.setOrgName(orgName);
								borrow.setCompanyId(stockout.getCompanyId());
								borrow.setCompanyName(stockout.getCompanyName());
								borrow.setNum(stockoutDetails.getOutNum());
								borrow.setProductId(stockoutDetails.getProductId());
								borrow.setContractId(stockoutDetails.getContractId());//销售合同id
								borrow.setPrice(stockoutDetails.getPrice());
								borrow.setWhsId(stockout.getWhsId());
								borrow.setWhsName(stockout.getWhsName());
								borrow.setType(stockout.getType());//挂账类型
								borrow.setChannelId(stockout.getChannelId());
								borrow.setCustomerId(stockout.getCustomerId());
								borrow.setWriteoffsNum(0);
								borrow.setWriteoffsNum2(0);
								borrow.setReturnNum(0);
								borrow.setStatus(0);
								borrow.setUserId(stockout.getUserId());
								borrow.setUserName(stockout.getUserName());
								borrow.setStockoutDetailsId(stockoutDetails.getId());
/*								//代理销售-》销账
								if(stockout.getType()==0){
									borrow.setType(1);
								}else{//借用-》电工核销
									borrow.setType(0);
								}
*/								Long borrowDetailsId=saveBorrows(borrow,1);

								//直销、升级是否立即核销
								if(isWriteoffNow!=null && isWriteoffNow){
									writeoffsDetailsObj=new WriteoffsDetails();
									writeoffsDetailsObj.setBorrowId(borrowDetailsId);
									writeoffsDetailsObj.setMoney(stockoutDetails.getMoney());
									writeoffsDetailsObj.setNum(stockoutDetails.getOutNum());
									writeoffsDetailsObj.setPrice(stockoutDetails.getPrice());
									writeoffsDetailsObj.setProductId(stockoutDetails.getProductId());
									writeoffsDetails.add(writeoffsDetailsObj);
								}
							}
						}
						/*if(sb.length()>0){
							sb.deleteCharAt(sb.length()-1);
							result.put("stockoutDetailsIds", sb.toString());
						}*/
					}
					//核销操作
					if(isWriteoffNow!=null && isWriteoffNow){
						if(writeoffsDetails!=null && !writeoffsDetails.isEmpty()){
							writeoffs.setWriteoffsDetails(writeoffsDetails);
						}
						result=addWriteoffInfo(writeoffs, whsCode);
					}
			}
			}
		}
		if(!(isWriteoffNow!=null && isWriteoffNow)){
			result.put(SystemConst.SUCCESS, flag);
			result.put(SystemConst.MSG, msg);
		}
		return result;
	}

	@Override
	public HashMap<String, Object> addStockIn(Stockin stockin,String whsCode) throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(stockin==null){
			flag=false;
			msg="参数不正确!";
		}else{
			List<Long> wareHouseIds=new ArrayList<Long>();
			wareHouseIds.add(stockin.getWhsId());
			//判断是否在盘点，如果不是才需要入库
			if(checkService.checkStatus(stockin.getCompanyId(), wareHouseIds, 4, false)){
				flag=false;
				msg="仓库正在盘点，不能进行入库操作!";
			}else{
				//判断单号是否存在
				if(stockDao.checkStockInCode(stockin)){
					//自动生成单号
					flag=false;
					msg = "入库单号为[" + stockin.getCode() + "]的已存在,将自动生成新的入库单号!";
					result.put("code", getStockInOutNo(stockin.getCompanyId(), stockin.getWhsId(), whsCode, false,null));
				}else{
					//类型
					Integer type=stockin.getType();
					//stockin.setStamp(DateUtil.formatToday());
					stockDao.save(stockin);
					Long id=stockin.getId();
					
					//库存
 					Stock stock=null;
					
					Integer numBefore=0;//入库前库存数量
					
					if(stockin.getStockinDetails()!=null&&!stockin.getStockinDetails().isEmpty()){
						for (StockinDetails stockinDetails : stockin.getStockinDetails()) {
							//第一步:先修改库存数量
							stock=new Stock();
							stock.setWhsId(stockin.getWhsId());
							stock.setWhsName(stockin.getWhsName());
							stock.setProductId(stockinDetails.getProductId());
							
							//判断表中记录是否存在
							stock=stockDao.getStockByWhsAndProduct(stock);
							if(stock!=null){
								//存在就修改
								//Stock stock2=new Stock();
								//BeanUtils.copyProperties(stock, stock2);
								stock.setUserId(stockin.getUserId());
								stock.setUserName(stockin.getUserName());
								numBefore=stock.getNum();
								stock.setNum(stock.getNum()+stockinDetails.getInNum());
								//stockDao.updateStockNum(stock);
								stockDao.merge(stock);
							}else{//不存在就添加
								stock=new Stock();
								stock.setWhsId(stockin.getWhsId());
								stock.setWhsName(stockin.getWhsName());
								stock.setCompanyId(stockin.getCompanyId());
								stock.setCompanyName(stockin.getCompanyName());
								stock.setProductId(stockinDetails.getProductId());
								stock.setUserId(stockin.getUserId());
								stock.setUserName(stockin.getUserName());
								stock.setNum(stockinDetails.getInNum());
								numBefore=0;
								stockDao.save(stock);
							}
							
							//第二步：再入库
							stockinDetails.setCurNum(numBefore);
							stockinDetails.setStockinId(id);
							stockDao.save(stockinDetails);
							
							//第三步：操作其他相关表
							//如果是归还,修改借用记录
							if(type.intValue()==2){
								BorrowDetails borrow=new BorrowDetails();
								borrow.setId(stockinDetails.getBorrowId());
								borrow.setBorrowerId(stockin.getManagersId());
								borrow.setBorrowerName(stockin.getManagersName());
								borrow.setReturnNum(stockinDetails.getInNum());
								borrow.setProductId(stockinDetails.getProductId());
								borrow.setWhsId(stockin.getWhsId());
								borrow.setWhsName(stockin.getWhsName());
								saveBorrows(borrow,2);
							}else if(type.intValue()==0){//采购入库，修改订单明细数量，是否完成字段,订单表是否完成字段
								if(StringUtils.isNotNullOrEmpty(stockin.getOrderId())){
									saveOrder(stockin.getOrderId(),stockinDetails.getOrderDetailId(),stockinDetails.getInNum());
								}
							}else if(type.intValue()==1){//调拨入库，修改出库明细已调拨数量，是否完成字段,出库表是否完成字段
								saveStockOut(stockin.getStockoutId(),stockinDetails.getStockoutDetailId(),stockinDetails.getInNum());
							}
						}
					}
			}
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}

	/**
	 * @Title:saveBorrows
	 * @Description:TODO
	 * @param borrow
	 * @param type 1:出库，2：归还入库
	 * @return
	 * @throws SystemException
	 */
	public Long saveBorrows(BorrowDetails borrow,int type) throws SystemException {
		Long id=0l;
		if(type==1){//出库
			//添加
			//borrow.setStamp(new Date());
			stockDao.save(borrow);
			id=borrow.getId();
		} else { //入库==归还
			/*//判断表中记录是否存在
			List<BorrowDetails> borrowDetailsList=stockDao.getBorrowByWhsAndProduct(borrow);
			if (borrowDetailsList!= null && !borrowDetailsList.isEmpty()){
				//存在就修改
				int curReturnNum = borrow.getNum();
				for (BorrowDetails borrowDetails : borrowDetailsList) {
					if(curReturnNum>0 && borrowDetails.getProductId().equals(borrow.getProductId())){
						if(borrowDetails.getNum()>=curReturnNum) {
							borrowDetails.setNum(borrowDetails.getNum()-curReturnNum);
							curReturnNum = 0;
						} else {
							curReturnNum = curReturnNum - borrowDetails.getNum();
							borrowDetails.setNum(0);//本条记录都归还完毕
						}
						stockDao.merge(borrowDetails);
					}
				}
			} else {
				//不存在就添加
				borrow.setId(UUIDCreater.getUUID());
				borrow.setStamp(DateUtil.formatNowTime());
				borrow.setNum(-borrow.getNum());
				stockDao.save(borrow);
			}*/
			BorrowDetails borrowDetailsOld=stockDao.get(BorrowDetails.class, borrow.getId());
			Integer befTotalBorrowNum=0;
			if(borrowDetailsOld !=null) {//存在就修改
				befTotalBorrowNum =borrowDetailsOld.getNum()-borrowDetailsOld.getWriteoffsNum()-borrowDetailsOld.getWriteoffsNum2()-borrowDetailsOld.getReturnNum();
				//修改借用挂账的数量
				if(befTotalBorrowNum.intValue() == borrow.getReturnNum().intValue()) {
					borrowDetailsOld.setStatus(1);
				} else if(befTotalBorrowNum.intValue() > borrow.getReturnNum().intValue()) {
					borrowDetailsOld.setStatus(0);
				}else if(befTotalBorrowNum.intValue()< borrow.getReturnNum().intValue()) {
					throw new RuntimeException("实际归还数量应小于或等于借用数量，归还失败!");
				}
				borrowDetailsOld.setReturnNum(borrowDetailsOld.getReturnNum()+borrow.getReturnNum());
				
				stockDao.merge(borrowDetailsOld);
			}else{//不存在就添加
				//设置用户的所属机构和分公司ID
				
				OpenLdap openLdap=new OpenLdap();
				CommonOperator commonOperator=null;
				commonOperator=openLdap.getOperatorById(borrow.getBorrowerId().toString());
				//借用人信息
				if(commonOperator!=null&&!commonOperator.getCompanynos().isEmpty()){
					String orgId=commonOperator.getCompanynos().get(0);
					if(orgId!=null){
						borrow.setOrgId(Long.valueOf(orgId));
						CommonCompany commonOrg=openLdap.getCompanyById(borrow.getOrgId().toString());
						if(commonOrg!=null){
							borrow.setOrgName(commonOrg.getCompanyname());
						}
						commonOrg=openLdap.getCompanyByOrgId(borrow.getOrgId().toString());
						if(commonOrg!=null){
							if(commonOrg.getCompanyno()!=null){
								borrow.setCompanyId(Long.valueOf(commonOrg.getCompanyno()));
							}
							borrow.setCompanyName(commonOrg.getCompanyname());
						}
					}
				}
				borrow.setStamp(new Date());
				borrow.setNum(-borrow.getReturnNum());
				borrow.setStatus(1);
				stockDao.save(borrow);
			}
			id=borrow.getId();
		}
		return id;
	}

	@Override
	public Page<HashMap<String, Object>> findStockInOutDetailsPage(
			PageSelect<HashMap<String, Object>> pageSelect)
			throws SystemException {
		if(pageSelect==null){
			return null;
		}else{
			List<HashMap<String, Object>> dataList=stockDao.findStockInOutDetailsPage(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
			/*if(dataList!=null){
				OpenLdap openLdap = OpenLdapManager.getInstance();
				HashMap<String, String> wareHouseMap = new HashMap<String, String>();
				HashMap<String, String> whsManagerMap = new HashMap<String, String>();
				CommonOperator commonOperator=null;
				String whsId = null;
				String whsName= null;//仓库名称
				String whsManagersName=null;//仓库管理名称
				String managersName=null;//经办人名称
	        	CommonCompany commonCompany = null;
				for (HashMap<String, Object> hashMap : dataList) {
					 whsId = null;
					 whsName= null;//仓库名称
					 whsManagersName=null;//仓库管理名称
					 managersName=null;//经办人名称
		        	 commonCompany = null;
					if(hashMap.get("managersId")!=null){
						commonOperator=openLdap.getOperatorById((String)hashMap.get("managersId"));
						if(commonOperator!=null){
							managersName=commonOperator.getOpname();
						}
					}
					//设置仓库名称、仓管员名称
					if (hashMap.get("whsId") != null) {
						whsId=hashMap.get("whsId").toString();
						if (wareHouseMap.get(whsId) != null && whsManagerMap.get(whsId) != null) {
							whsName = wareHouseMap.get(whsId) ;
							whsManagersName = whsManagerMap.get(whsId) ;
						} else {
							commonCompany = openLdap.getCompanyById(whsId);
							if (commonCompany != null) {
								wareHouseMap.put(whsId, commonCompany.getCompanyname());
								whsName = commonCompany.getCompanyname() ;
								
								//仓管员名称
								commonOperator=openLdap.getOperatorById(commonCompany.getOpid());//仓管员id
								if(commonOperator!=null){
									whsManagerMap.put(whsId,commonOperator.getOpname());
									whsManagersName=commonOperator.getOpname();
								}
							}
						}
						
					}
					hashMap.put("whsName", whsName);//仓库名称
					hashMap.put("managersName", managersName);//经办人名称
					hashMap.put("whsManagersName", whsManagersName);//仓库管理名称
				}
			}*/
			int total=stockDao.countStockInOutDetails(pageSelect.getFilter());
			return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public HashMap<String, Object> addWriteoffInfo(Writeoffs writeoffs,String whsCode) throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		if(writeoffs==null){
			flag=false;
			msg="参数不正确!";
		}else{
			List<Long> wareHouseIds=new ArrayList<Long>();
			wareHouseIds.add(writeoffs.getWhsId());
			//判断是否在盘点，如果不是才需要入库
			if(checkService.checkStatus(writeoffs.getCompanyId(), wareHouseIds, 4, false)){
				flag=false;
				msg="仓库正在盘点，不能进行核销操作!";
			}else{
				//判断单号是否存在
				if(stockDao.checkWriteoffsCode(writeoffs)){
					//自动生成单号
					flag=false;
					msg = "核销单号为[" + writeoffs.getWriteoffsNo() + "]的已存在,将自动生成新的核销单号!";
					result.put("code", getWriteOffsNo(writeoffs.getCompanyId(), writeoffs.getOrgId(),writeoffs.getWhsId(), whsCode, true));
				}else{
				//添加核销
					stockDao.save(writeoffs);
					Long id=writeoffs.getId();
					
					BorrowDetails borrowDetails = null;//借用挂账
					Integer befTotalBorrowNum=0;//剩余需核销的数量
					//添加核销明细
					List<WriteoffsDetails> writeoffsDetails=writeoffs.getWriteoffsDetails();
					if(writeoffsDetails!=null&&!writeoffsDetails.isEmpty()){
						for (WriteoffsDetails writeoffsDetails2 : writeoffsDetails) {
							writeoffsDetails2.setWriteoffsId(id);
							stockDao.save(writeoffsDetails2);
							
							borrowDetails = stockDao.get(BorrowDetails.class, writeoffsDetails2.getBorrowId());
							if (borrowDetails != null) {
								//borrowDetails.setType(0);
								befTotalBorrowNum =borrowDetails.getNum()-borrowDetails.getWriteoffsNum()-borrowDetails.getWriteoffsNum2()-borrowDetails.getReturnNum();
								//修改借用挂账的数量
								if(befTotalBorrowNum.intValue() == writeoffsDetails2.getNum().intValue()) {
									borrowDetails.setStatus(1);
								} else if(befTotalBorrowNum.intValue() > writeoffsDetails2.getNum().intValue()) {
									borrowDetails.setStatus(0);
								}else if(befTotalBorrowNum.intValue()< writeoffsDetails2.getNum().intValue()) {
									throw new RuntimeException("实际核销数量应小于或等于借用数量，核销失败!");
								}
								borrowDetails.setWriteoffsNum2(borrowDetails.getWriteoffsNum2()+writeoffsDetails2.getNum());
								stockDao.merge(borrowDetails);
							} else {
								throw new RuntimeException("抱歉，没有找到核销明细，核销失败!");
							}
						
						}
					}
			}
		}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}

	@Override
	public List<HashMap<String, Object>> findAllStockInDetails3(
			Map<String, Object> conditionMap) throws SystemException {
		return stockDao.findStockInDetails3(conditionMap,null,false,0,0);
	}

	@Override
	public List<HashMap<String, Object>> findAllProducts(
			Map<String, Object> conditionMap) throws SystemException {
		return stockDao.findAllProducts(conditionMap);
	}

	@Override
	public Page<HashMap<String, Object>> findStockInsPage(
			PageSelect<HashMap<String, Object>> pageSelect)
			throws SystemException {
		if(pageSelect==null){
			return null;
		}else{
			List<HashMap<String, Object>> dataList=stockDao.findStockIns(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
			/*String managersName=null;//经办人名称
			CommonOperator commonOperator=null;
			OpenLdap openLdap=new OpenLdap();
			if(dataList!=null){
				HashMap<String, String> wareHouseMap = new HashMap<String, String>();
				String whsId = null;
	        	String whsName= null;
	        	String outWhsId = null;
	        	String outWhsName= null;
	        	CommonCompany commonCompany = null;
				for (HashMap<String, Object> hashMap : dataList) {
					whsId = null;
	        		whsName = null;
	        		outWhsId = null;
		        	outWhsName= null;
					if(hashMap.get("managersId")!=null){
						commonOperator=openLdap.getOperatorById((String)hashMap.get("managersId"));
						if(commonOperator!=null){
							managersName=commonOperator.getOpname();
						}
					}
				
					if (hashMap.get("whsId") != null) {
						whsId=hashMap.get("whsId").toString();
						if (wareHouseMap.get(whsId) != null) {
							whsName = wareHouseMap.get(whsId) ;
						} else {
							commonCompany = openLdap.getCompanyById(whsId);
							if (commonCompany != null) {
								wareHouseMap.put(whsId, commonCompany.getCompanyname());
								whsName = commonCompany.getCompanyname() ;
							}
						}
						
					}
					if (hashMap.get("outWhsId") != null) {
						outWhsId=hashMap.get("outWhsId").toString();
						if (wareHouseMap.get(outWhsId) != null) {
							outWhsName = wareHouseMap.get(outWhsId) ;
						} else {
							commonCompany = openLdap.getCompanyById(outWhsId);
							if (commonCompany != null) {
								wareHouseMap.put(outWhsId, commonCompany.getCompanyname());
								outWhsName = commonCompany.getCompanyname() ;
							}
						}
						
					}
					hashMap.put("whsName", whsName);
					hashMap.put("outWhsName", outWhsName);//调拨出仓库名称
					hashMap.put("managersName", managersName);//经办人名称
				}
			}*/
			int total=stockDao.countStockIns(pageSelect.getFilter());
			return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
		}
	}

	@Override
	public Page<HashMap<String, Object>> findStockOutsPage(
			PageSelect<HashMap<String, Object>> pageSelect)
			throws SystemException {
		if(pageSelect==null){
			return null;
		}else{
			List<HashMap<String, Object>> dataList=stockDao.findStockOuts(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
			int total=stockDao.countStockOuts(pageSelect.getFilter());
			return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
		}
	}

	@Override
	public List<HashMap<String, Object>> findStockInDetails4(
			Map<String, Object> conditionMap) throws SystemException {
		return stockDao.findStockInDetails4(conditionMap);
	}

	@Override
	public List<HashMap<String, Object>> findStockOutDetails4(
			Map<String, Object> conditionMap) throws SystemException {
		return stockDao.findStockOutDetails4(conditionMap);
	}

	@Override
	public List<Map<String, Object>> findBorrows(Map<String,Object> conditionMap)
			throws SystemException {
		return stockDao.findBorrows(conditionMap);
	}

	@Override
	public List<HashMap<String, Object>> findStockInOutDetails(
			HashMap<String, Object> map) throws SystemException {
		List<HashMap<String, Object>> dataList=stockDao.findStockInOutDetailsPage(map, null, true,0,0);
		/*if(dataList!=null){
			OpenLdap openLdap = OpenLdapManager.getInstance();
			HashMap<String, String> wareHouseMap = new HashMap<String, String>();
			HashMap<String, String> whsManagerMap = new HashMap<String, String>();
			CommonOperator commonOperator=null;
			String whsId = null;
			String whsName= null;//仓库名称
			String whsManagersName=null;//仓库管理名称
			String managersName=null;//经办人名称
        	CommonCompany commonCompany = null;
			for (HashMap<String, Object> hashMap : dataList) {
				 whsId = null;
				 whsName= null;//仓库名称
				 whsManagersName=null;//仓库管理名称
				 managersName=null;//经办人名称
	        	 commonCompany = null;
				if(hashMap.get("managersId")!=null){
					commonOperator=openLdap.getOperatorById((String)hashMap.get("managersId"));
					if(commonOperator!=null){
						managersName=commonOperator.getOpname();
					}
				}
				//设置仓库名称、仓管员名称
				if (hashMap.get("whsId") != null) {
					whsId=hashMap.get("whsId").toString();
					if (wareHouseMap.get(whsId) != null && whsManagerMap.get(whsId) != null) {
						whsName = wareHouseMap.get(whsId) ;
						whsManagersName = whsManagerMap.get(whsId) ;
					} else {
						commonCompany = openLdap.getCompanyById(whsId);
						if (commonCompany != null) {
							wareHouseMap.put(whsId, commonCompany.getCompanyname());
							whsName = commonCompany.getCompanyname() ;
							
							//仓管员名称
							commonOperator=openLdap.getOperatorById(commonCompany.getOpid());//仓管员id
							if(commonOperator!=null){
								whsManagerMap.put(whsId,commonOperator.getOpname());
								whsManagersName=commonOperator.getOpname();
							}
						}
					}
					
				}
				hashMap.put("whsName", whsName);//仓库名称
				hashMap.put("managersName", managersName);//经办人名称
				hashMap.put("whsManagersName", whsManagersName);//仓库管理名称
			}
		}*/
		return dataList;
	}


	@Override
	public String getStockInOutNo(Long companyId, Long whsId,String whsCode,boolean isIn,Date today)
			throws SystemException {
		OpenLdap openLdap=OpenLdapManager.getInstance();
		CommonCompany commonCompany=null;
		String companyNo="";
		
		if(companyId!=null){
			commonCompany=openLdap.getCompanyById(companyId.toString());
			companyNo=commonCompany.getCompanycode()==null?"":commonCompany.getCompanycode();
		}
		if(today==null){
			today=new Date();
		}
	    if(StringUtils.isBlank(companyNo)){
			return "1";
		}else {
			int length=companyNo.length();
			if(length>=2){
			   companyNo=companyNo.substring(length-2, length);
			}
		}
	    if(StringUtils.isBlank(whsCode)){
			return "2";
		}else {
			int length=whsCode.length();
			if(length>=4){
				whsCode=whsCode.substring(length-4, length);
			}
		}
	    
		//流水号加1，前面不足4位，用0补充
		String serialNoStr=Utils.formatSerial(stockDao.getMaxStockNo(whsId, DateUtil.format(today, DateUtil.YMD_DASH),isIn));
		String prefix=isIn?SystemConst.STOCKIN_NO_PREFIX:SystemConst.STOCKOUT_NO_PREFIX;
		
		return prefix+companyNo+whsCode+DateUtil.format(today, DateUtil.YMD)+serialNoStr;
	}


	@Override
	public String getWriteOffsNo(Long companyId, Long orgId,Long whsId,String whsCode,boolean isHx)
			throws SystemException {
		//流水号加1，前面不足4位，用0补充
		OpenLdap openLdap=OpenLdapManager.getInstance();
		CommonCompany commonCompany=null;
		String companyNo="";
		if(companyId!=null){
			commonCompany=openLdap.getCompanyById(companyId.toString());
			companyNo=commonCompany.getCompanycode()==null?"":commonCompany.getCompanycode();
		}
		 if(StringUtils.isBlank(companyNo)){
				return "1";
		}else {
			int length=companyNo.length();
			if(length>=2){
			   companyNo=companyNo.substring(length-2, length);
			}
		}
		if(StringUtils.isBlank(whsCode)){
			return "2";
		}else {
			int length=whsCode.length();
			if(length>=4){
				whsCode=whsCode.substring(length-4, length);
			}
		}
		String serialNoStr=Utils.formatSerial(stockDao.getMaxWriteoffsNo(whsId,orgId, DateUtil.formatToday(),isHx));
		String prefix=isHx?SystemConst.WRITEOFFS_NO_PREFIX:SystemConst.WRITEOFF_NO_PREFIX;
		return prefix+companyNo+(StringUtils.isNotBlank(whsCode)?whsCode:"")+DateUtil.format(new Date(), DateUtil.YMD)+serialNoStr;
	}
	
	/**
	 * @Title:saveOrder
	 * @Description:修改订单信息
	 * @throws
	 */
	private void saveOrder(Long orderId,Long orderDetailId,Integer inNum) throws SystemException{
		//先修改明细
		if(orderDetailId!=null){
			OrderDetails orderDetailOld=stockDao.get(OrderDetails.class, orderDetailId);
			if(orderDetailOld!=null){
				Integer befTotalInNum=orderDetailOld.getNum()-(orderDetailOld.getInNum()==null?0:orderDetailOld.getInNum());
				//修改已采购入库的数量
				if(befTotalInNum.intValue() == inNum.intValue()) {
					orderDetailOld.setIsCompleted(1);
				} else if(befTotalInNum.intValue() > inNum.intValue()) {
					orderDetailOld.setIsCompleted(0);
				}else if(befTotalInNum.intValue()< inNum.intValue()) {
					throw new RuntimeException("实际采购数量大于订单数量，采购失败!");
				}
				orderDetailOld.setInNum((orderDetailOld.getInNum()==null?0:orderDetailOld.getInNum())+inNum);
				
				//修改订单明细
				stockDao.merge(orderDetailOld);
				
				//再修改订单
				if(!orderDao.checkOrderDetailsNotCompleted(orderId)){//如果已经没有未完成的订单
					Order order=stockDao.get(Order.class, orderId);
					order.setIsCompleted(1);//已完成
					orderDao.merge(order);
				}
			}
		}
	}
	
	/**
	 * @Title:saveOrder
	 * @Description:修改订单信息
	 * @throws
	 */
	private void saveStockOut(Long stockoutId,Long stockoutDetailId,Integer inNum) throws SystemException{
		//先修改明细
		if(stockoutDetailId!=null){
			StockoutDetails stockoutDetailsOld=stockDao.get(StockoutDetails.class, stockoutDetailId);
			if(stockoutDetailsOld!=null){
				Integer befTotalInNum=stockoutDetailsOld.getOutNum()-(stockoutDetailsOld.getAllocatedNum()==null?0:stockoutDetailsOld.getAllocatedNum());
				//修改已调拨入库的数量
				if(befTotalInNum.intValue() == inNum.intValue()) {
					stockoutDetailsOld.setIsCompleted(1);
				} else if(befTotalInNum.intValue() > inNum.intValue()) {
					stockoutDetailsOld.setIsCompleted(0);
				}else if(befTotalInNum.intValue()< inNum.intValue()) {
					throw new RuntimeException("实际调拨数量大于调拨数量，采购失败!");
				}
				stockoutDetailsOld.setAllocatedNum((stockoutDetailsOld.getAllocatedNum()==null?0:stockoutDetailsOld.getAllocatedNum())+inNum);
				
				//修改出库明细
				stockDao.merge(stockoutDetailsOld);
				
				//再修改出库单
				if(!stockDao.checkAllocateDetailsNotCompleted(stockoutId)){//如果已经没有未完成的调拨
					Stockout stockout=stockDao.get(Stockout.class, stockoutId);
					if(stockout!=null){
						stockout.setIsCompleted(1);//已完成
						stockDao.merge(stockout);
					}
				}
			}
		}
	}

	@Override
	public Page<HashMap<String, Object>> findStockInDetails3ByPage(
			PageSelect<Map<String, Object>> pageSelect)
			throws SystemException {
				int total=stockDao.countStockInDetails3(pageSelect.getFilter());
			    List<HashMap<String, Object>> dataList=stockDao.findStockInDetails3(pageSelect.getFilter(), pageSelect.getOrder(),pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
				return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
		
	}

	@Override
	public Long getBorrowTotalNum(Long borrowerId,Long channelId,List<Long> whsIds) throws SystemException {
		if(borrowerId!=null){
			return stockDao.getBorrowTotalNum(borrowerId,channelId,whsIds);
		}else{
			return 0l;
		}
	}

	@Override
	public int saveBorrows(BorrowDetails borrowDetails)
			throws SystemException {
		if(borrowDetails==null){
			return -1;
		}else{
			if(borrowDetails.getId()!=null){//编辑
				//判断是否存在
				if(stockDao.get(BorrowDetails.class, borrowDetails.getId())!=null){
					//borrowDetails.setStamp(DateUtil.formatNowTime());
					//自动判断是否已销完、归还完
					Integer num=borrowDetails.getNum()==null?0:borrowDetails.getNum();//数量
					Integer writeoffsNum=borrowDetails.getWriteoffsNum()==null?0:borrowDetails.getWriteoffsNum();//已销账数量
					Integer writeoffsNum2=borrowDetails.getWriteoffsNum2()==null?0:borrowDetails.getWriteoffsNum2();//已核销数量
					Integer returnNum=borrowDetails.getReturnNum()==null?0:borrowDetails.getReturnNum();//已归还数量
					if(num.intValue()-writeoffsNum.intValue()-writeoffsNum2.intValue()-returnNum.intValue()==0){
						borrowDetails.setStatus(1);
					}else if(num.intValue()-writeoffsNum.intValue()-writeoffsNum2.intValue()-returnNum.intValue()<0){
						throw new RuntimeException("实际归还数量与核销数量之和应小于或等于借用数量，操作失败!");
					}else{
						borrowDetails.setStatus(0);
					}
					stockDao.merge(borrowDetails);
				}else{//不存在
					return 0;
				}
			}else{//新增
				//borrowDetails.setStamp(DateUtil.formatNowTime());
				//自动判断是否已销完、归还完
				Integer num=borrowDetails.getNum()==null?0:borrowDetails.getNum();//数量
				Integer writeoffsNum=borrowDetails.getWriteoffsNum()==null?0:borrowDetails.getWriteoffsNum();//已销数量
				Integer writeoffsNum2=borrowDetails.getWriteoffsNum2()==null?0:borrowDetails.getWriteoffsNum2();//已销数量
				Integer returnNum=borrowDetails.getReturnNum()==null?0:borrowDetails.getReturnNum();//已归还数量
				if(num.intValue()-writeoffsNum.intValue()-writeoffsNum2.intValue()-returnNum.intValue()==0){
					borrowDetails.setStatus(1);
				}else if(num.intValue()-writeoffsNum.intValue()-writeoffsNum2.intValue()-returnNum.intValue()<0){
					throw new RuntimeException("实际归还数量与核销数量之和应小于或等于借用数量，操作失败!");
				}else{
					borrowDetails.setStatus(0);
				}
				stockDao.save(borrowDetails);
			}
			return 1;
		}
	}

	@Override
	public int deleteBorrows(List<Long> ids) throws SystemException {
		if(ids==null || ids.isEmpty()){
			return -1;
		}else{
			stockDao.deleteBorrowDetails(ids);
			return 1;
		}
	}

	@Override
	public int saveStock(Stock stock)
			throws SystemException {
		int result=1;
		if(stock==null){
			result=-1;
		}else{
			//判断表中商品是否已初始化了
			Stock stock2=stockDao.getStockByWhsAndProduct(stock);
			if(stock2!=null){
				result=2;
			}else{
				if(stock.getId()!=null){//编辑
					stockDao.merge(stock);
				}else{
					stockDao.save(stock);
				}
			}
			
		}
		return result;
	}

	@Override
	public int deleteStocks(List<Long> ids) throws SystemException {
		if(ids==null || ids.isEmpty()){
			return -1;
		}else{
			stockDao.deleteStocks(ids);
			return 1;
		}
	}
	@Override
	public List<HashMap<String, Object>> findOperates(
			Map<String, Object> conditionMap) throws SystemException {
		//出库
		List<HashMap<String, Object>> dataList=stockDao.findStockOutDetailsOperates(conditionMap);
		//入库
		dataList.addAll(stockDao.findStockInDetailsOperates(conditionMap));
		//核销
		dataList.addAll(stockDao.findWriteoffsOperates(conditionMap));
		//销账
		dataList.addAll(stockDao.findWriteoffsXzOperates(conditionMap));
		return dataList;
	}
	
	@Override
	public List<HashMap<String, Object>> findProductStock(
			Map<String, Object> conditionMap) throws SystemException {
		return stockDao.findProductStock(conditionMap, null,false,0,0);
	}
	@Override
	public Page<HashMap<String, Object>> findWriteoffs(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=stockDao.countWriteoffs(pageSelect.getFilter());
		return PageUtil.getPage(total, pageSelect.getPageNo(), stockDao.findWriteoffs(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize()), pageSelect.getPageSize());

	}
	@Override
	public Page<HashMap<String, Object>> findWriteoffsXz(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=stockDao.countWriteoffsXz(pageSelect.getFilter());
		return PageUtil.getPage(total, pageSelect.getPageNo(), stockDao.findWriteoffsXz(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize()), pageSelect.getPageSize());
	}
	@Override
	public List<HashMap<String, Object>> findAllWriteoffs(
			Map<String, Object> map) throws SystemException {
		return stockDao.findWriteoffs(map, null, false, 0, 0);
	}
	@Override
	public List<HashMap<String, Object>> findAllWriteoffsXz(
			Map<String, Object> map) throws SystemException {
		return stockDao.findWriteoffsXz(map, null, false, 0, 0);
	}
	@Override
	public Page<HashMap<String, Object>> findSaleOutDetails(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=stockDao.countSaleOutDetails(pageSelect.getFilter());
		return PageUtil.getPage(total, pageSelect.getPageNo(), stockDao.findSaleOutDetails(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize()), pageSelect.getPageSize());

	}
	@Override
	public List<HashMap<String, Object>> findAllSaleOutDetails(
			Map<String, Object> map) throws SystemException {
		return stockDao.findSaleOutDetails(map,null,false,0,0);
	}
	@Override
	public List<HashMap<String, Object>> findWriteoffsDetails(
			Map<String, Object> conditionMap) throws SystemException {
		return  stockDao.findWriteoffsDetails(conditionMap);
	}
	@Override
	public List<HashMap<String, Object>> findWriteoffsDetailsXz(
			Map<String, Object> conditionMap) throws SystemException {
		return stockDao.findWriteoffsDetailsXz(conditionMap);
	}
	@Override
	public List<HashMap<String, Object>> findAllStockOuts(
			Map<String, Object> map) throws SystemException {
		return stockDao.findStockOuts(map,null,false,0,0);
	}
	@Override
	public List<HashMap<String, Object>> findAllStockIns(Map<String, Object> map)
			throws SystemException {
		return stockDao.findStockIns(map,null,false,0,0);
	}
	
	@Override
	public int saveBorrowTo(List<Long> ids,Long borrowerId,String borrowerName) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			//先copy一份，保存
			for (Long id : ids) {
				BorrowDetails fromBorrowDetails=stockDao.get(BorrowDetails.class, id);
				BorrowDetails toBorrowDetails=new BorrowDetails();
				BeanUtils.copyProperties(fromBorrowDetails, toBorrowDetails);
				if(toBorrowDetails!=null){
					if(StringUtils.isNotNullOrEmpty(borrowerId)){
						toBorrowDetails.setBorrowerId(borrowerId);
						toBorrowDetails.setBorrowerName(borrowerName);
						stockDao.save(toBorrowDetails);
						
						//再修改原来的挂账记录，状态置为已转移，修改toBorrowId
						fromBorrowDetails.setStatus(2);
						fromBorrowDetails.setToBorrowId(toBorrowDetails.getId());
						stockDao.merge(fromBorrowDetails);
					}
				}else{
					result=0;
				}
			}
			
		}
		return result;
	}
	@Override
	public Page<HashMap<String, Object>> findBorrowDetailsByPage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=stockDao.countBorrowDetails(pageSelect.getFilter());
	    List<HashMap<String, Object>> dataList=stockDao.findBorrowDetails(pageSelect.getFilter(), pageSelect.getOrder(),pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
	}
	@Override
	public List<HashMap<String, Object>> findAllBorrowDetails(
			Map<String, Object> map) throws SystemException {
		return stockDao.findBorrowDetails(map,null,false,0,0);
	}
	@Override
	public List<HashMap<String, Object>> findWriteoffsAndDetails4Xz(
			Map<String, Object> map) throws SystemException {
		return stockDao.findWriteoffsXzOperates(map);
	}
	@Override
	public List<HashMap<String, Object>> findWriteoffsAndDetails(
			Map<String, Object> map) throws SystemException {
		return stockDao.findWriteoffsOperates(map);
	}
	
	
	
	
}
