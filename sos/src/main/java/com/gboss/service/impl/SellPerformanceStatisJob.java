package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gboss.dao.SellPerformanceDao;
import com.gboss.pojo.Plan;
import com.gboss.pojo.SellPerformance;
import com.gboss.util.DateUtil;
import com.gboss.util.HibernateUtil;
import com.gboss.util.UUIDCreater;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SellPerformanceServiceImpl
 * @Description:销售业绩统计业务层实现类
 * @author:zfy
 * @date:2013-8-6 上午8:42:54
 */
@Component("sellPerformanceStatisJob")
public class SellPerformanceStatisJob {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired  
	@Qualifier("sellPerformanceDao")
	private SellPerformanceDao sellPerformanceDao;
	
	@Autowired  
	private HibernateUtil hibernateUtil;
	
	public static final String TOTAL_YEAR="全年";
	
	/**
	 * @Title:statisSellPerformance
	 * @Description:每个月1号 0点过5分统计上一个月的销售业绩
	 * @throws
	 */
	 @Scheduled(cron = "0 5 0 1 * ?")  
	public void statisSellPerformance(){
		Session session = null;  
		try {
			HashMap<String, String> conditionMap=new HashMap<String, String>();
			//上个月销售计划
			String yearMonth=DateUtil.formatPreMonth();
			String[] yearMonths=yearMonth.split("-");
			int month=Integer.parseInt(yearMonths[1]);
			conditionMap.put("year",yearMonths[0]);
			conditionMap.put("monthQuota",month+"月,"+TOTAL_YEAR);
			List<Plan> plans=sellPerformanceDao.getPlan(conditionMap);
			conditionMap.clear();
			//上个月实际销售数量
			conditionMap.put("preYearMonth", yearMonth);
			List<Map<String, Object>> realSaleNums=sellPerformanceDao.getSaleProducts(conditionMap);
					
			//上个月实际入网数量
			List<Map<String, Object>> realNetInNums=null;//sellPerformanceDao.getNetIn(conditionMap);
			
			//上个月实际回款金额
			List<Map<String, Object>> realReturnMoneys=sellPerformanceDao.getReturnMoney(conditionMap);
			SellPerformance sellPerformance=null;
			Integer saleNum=0;//销售数量
			Integer netInNum=0;//入网数量
			Float returnMoney=0f;//回款金额
			boolean isPerson=true;//是否是个人
			boolean isOrg=true;//是否是部门
			boolean isCompany=true;//是否是分公司
			//生成记录
			//上个月计划
			session = hibernateUtil.getSession(); // 获取Session  
			session.beginTransaction(); // 开启事物 
			Plan plan=null;
			for (int i = 0; i < plans.size(); i++) {
				plan=plans.get(i);
				saleNum=0;
				netInNum=0;
				returnMoney=0f;
				//上个月实际销售数量
				for (Map<String, Object> realSaleNum:realSaleNums) {
					isPerson=plan.getLevel().intValue()==1&&plan.getOrgUserId().equals(realSaleNum.get("userId"));
					isOrg=plan.getLevel().intValue()==2&&plan.getOrgUserId().equals(realSaleNum.get("orgId"));
					isCompany=plan.getLevel().intValue()==3&&plan.getOrgUserId().equals(realSaleNum.get("companyId"));
					if(isPerson||isOrg||isCompany){
						saleNum+=Integer.parseInt(String.valueOf(realSaleNum.get("saleNum")));
					}
				}
				//上个月实际入网数量
				if(realNetInNums!=null && !realNetInNums.isEmpty()){
					for (Map<String, Object> realNetInNum:realNetInNums) {
						isPerson=plan.getLevel().intValue()==1&&plan.getOrgUserId().equals(realNetInNum.get("userId"));
						isOrg=plan.getLevel().intValue()==2&&plan.getOrgUserId().equals(realNetInNum.get("orgId"));
						isCompany=plan.getLevel().intValue()==3&&plan.getOrgUserId().equals(realNetInNum.get("companyId"));
						if(isPerson||isOrg||isCompany){
							netInNum+=Integer.parseInt(String.valueOf(realNetInNum.get("netNum")));
						}
					}
				}
					
				//上个月实际还款金额
				for (Map<String, Object> realReturnMoney:realReturnMoneys) {
					isPerson=plan.getLevel().intValue()==1&&plan.getOrgUserId().equals(realReturnMoney.get("userId"));
					isOrg=plan.getLevel().intValue()==2&&plan.getOrgUserId().equals(realReturnMoney.get("orgId"));
					isCompany=plan.getLevel().intValue()==3&&plan.getOrgUserId().equals(realReturnMoney.get("companyId"));
					if(isPerson||isOrg||isCompany){
						returnMoney+=Float.parseFloat(String.valueOf(realReturnMoney.get("retMoney")));
					}
				}
				//如果是全年
				if(TOTAL_YEAR.equals(plan.getMonthQuota())){
					//如果不存在全年记录，就添加，否则修改
					sellPerformance=new SellPerformance();
					sellPerformance.setType(plan.getLevel());
					sellPerformance.setUserOrgId(plan.getOrgUserId());
					sellPerformance.setYear(yearMonths[0]);
					sellPerformance.setMonth(yearMonths[1]);
					sellPerformance=sellPerformanceDao.getSellPerformance(sellPerformance);
					if(sellPerformance!=null){//已存在，修改
						sellPerformance.setRealSellNum(saleNum+sellPerformance.getRealSellNum());//之前的销售数量+上个月的销售数量
						sellPerformance.setSellNumRate((float)(saleNum+sellPerformance.getRealSellNum()/plan.getSellNum().intValue()));
						sellPerformance.setRealNet(netInNum+sellPerformance.getRealNet());//之前的入网数量+上个月的入网数量
						sellPerformance.setNetRate(((netInNum+sellPerformance.getRealNet())/(float)plan.getNetQuota().intValue()));
						sellPerformance.setRealReturnMoney(returnMoney+sellPerformance.getRealReturnMoney());//之前的回款金额+上个月的回款金额
						sellPerformance.setReturnMoneyRate((returnMoney+sellPerformance.getRealReturnMoney())/plan.getMoneyTotal());
						
						
						sellPerformanceDao.update(sellPerformance);
					}else{//新增
						sellPerformance=new SellPerformance();
						sellPerformance.setType(plan.getLevel());
						sellPerformance.setUserOrgId(plan.getOrgUserId());
						sellPerformance.setYear(yearMonths[0]);
						sellPerformance.setMonth(yearMonths[1]);
						sellPerformance.setPlanSellNum(plan.getSellNum());
						sellPerformance.setPlanNet(plan.getNetQuota());
						sellPerformance.setPlanReturnMoney(plan.getMoneyTotal());
								
						sellPerformance.setRealSellNum(saleNum);
						sellPerformance.setSellNumRate((float)saleNum/plan.getSellNum().intValue());
						sellPerformance.setRealNet(netInNum);
						sellPerformance.setNetRate((float)netInNum/plan.getNetQuota().intValue());
						sellPerformance.setRealReturnMoney(returnMoney);
						sellPerformance.setReturnMoneyRate(returnMoney/plan.getMoneyTotal());
						
						session.save(sellPerformance); // 保存销售业绩对象 
					}
				}else{
					sellPerformance=new SellPerformance();
					sellPerformance.setType(plan.getLevel());
					sellPerformance.setUserOrgId(plan.getOrgUserId());
					sellPerformance.setYear(yearMonths[0]);
					sellPerformance.setMonth(yearMonths[1]);
					sellPerformance.setPlanSellNum(plan.getSellNum());
					sellPerformance.setPlanNet(plan.getNetQuota());
					sellPerformance.setPlanReturnMoney(plan.getMoneyTotal());
			
					sellPerformance.setRealSellNum(saleNum);
					sellPerformance.setSellNumRate((float)saleNum/plan.getSellNum().intValue());
					sellPerformance.setRealNet(netInNum);
					sellPerformance.setNetRate((float)netInNum/plan.getNetQuota().intValue());
					sellPerformance.setRealReturnMoney(returnMoney);
					sellPerformance.setReturnMoneyRate((float)returnMoney/plan.getMoneyTotal().intValue());
					session.save(sellPerformance); // 保存销售业绩对象 
					
				}
				
				// 批插入/更新的对象立即写入数据库并释放内存  
			    if (i % 10 == 0) {  
			        session.flush();  
			        session.clear();  
			    } 
				
			}
			 session.getTransaction().commit(); // 提交事物  
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
			session.getTransaction().rollback(); // 出错将回滚事物 
		}finally{
			hibernateUtil.closeSession(session); // 关闭Session  
		}
	}
}

