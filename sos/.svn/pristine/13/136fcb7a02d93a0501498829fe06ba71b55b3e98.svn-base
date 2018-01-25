package com.gboss.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.mysql.IdCreater;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.BoundDao;
import com.gboss.dao.UnitDao;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Linkman;
import com.gboss.pojo.Model;
import com.gboss.pojo.Renew;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.service.BoundService;
import com.gboss.service.UnitService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:BoundServiceImpl
 * @Description:TODO
 * @author:hansm
 * @date:2016-6-12 下午3:45:05
 */

@Service("BoundService")
@Transactional
public class BoundServiceImpl extends BaseServiceImpl implements BoundService {
	
	@Autowired
	@Qualifier("BoundDao")
	private BoundDao boundDao;

	@Override
	public Page<HashMap<String, Object>> findBoundInNetsBypage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		List<HashMap<String, Object>> dataList=boundDao.findBoundInNets(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
		int total = boundDao.countBoundInNets(pageSelect.getFilter());
		return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
	}

	@Override
	public List<HashMap<String, Object>> findAllBoundInNets(
			Map<String, Object> conditionMap) throws SystemException {
		return boundDao.findBoundInNets(conditionMap,null,false,0,0);
	}

	@Override
	public List<HashMap<String, Object>> findTBoxServerExpire(
			Map<String, Object> conditionMap) throws SystemException {
		return boundDao.findTBoxServerExpire(conditionMap,null,false,0,0);
	}

	@Override
	public Map<String, Object> importHMTBOX(List<String[]> dataList, Long compannyId, Long userId, String companyName, String companyCode)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Date date = new Date(); // 导入时间
		String markDate = DateUtil.format(date, "yyyy-MM-dd");
		String message = "";
		Integer sussNum = 0;
		Integer failNum = 0;
		Integer repeatNum = 0;
		Integer total = dataList.size();
		try {
			/*
			 * List<Vehicle> vList = vehicleDao.listAll(Vehicle.class);
			 * Map<String, Object> plateNoMap = new HashMap<String, Object>();
			 * Map<String, Object> vinMap = new HashMap<String, Object>();
			 * Map<String, Object> engineNoMap = new HashMap<String, Object>();
			 * for(Vehicle v : vList){ //将数据表中数据存在内存中，导入时不再查询数据库
			 * if(StringUtils.isNotBlank(v.getPlate_no())){
			 * plateNoMap.put(v.getPlate_no(), "has"); }
			 * if(StringUtils.isNotBlank(v.getVin())){ vinMap.put(v.getVin(),
			 * "has"); } if(StringUtils.isNotBlank(v.getEngine_no())){
			 * engineNoMap.put(v.getEngine_no(), "has"); } }
			 */
			for (int i = 0; i < total; i++) {
				Integer rowNum = i + 2;
				String[] column = dataList.get(i); // 读取一行
				String customer_name = column[0].trim(); // 客户名称
				String telPhone = column[1].trim(); // 联系电话
				String vin = column[2].trim(); // vin码
				String engine_no = column[3].trim(); // 发动机号
				String plate_no = column[4].trim(); // 车牌号
				String barCode = column[5].trim(); // T-box条码
				String call_letter = column[6].trim(); // T-box呼号
				String equip_code = column[7].trim(); // 配置简码
				String service_start_date = column[8].trim(); // 开通服务时间
				String service_end_date = column[9].trim();// 期满时间
				String combo_change_date = column[10].trim();// 更改套餐时间
				String combo_type  = column[11].trim();// 套餐类型
				String service_end_newdate = column[12].trim();// 新有效期时间  
				// 空值判断
				if (StringUtils.isBlank(customer_name)) { // 客户姓名
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>客户姓名</span>为空";
					continue;
				}
				if (StringUtils.isBlank(telPhone)) { // 联系电话
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>联系电话</span>为空";
					continue;
				}
				if (StringUtils.isBlank(vin)) { // vin码
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>vin码</span>为空";
					continue;
				}
				if (StringUtils.isBlank(engine_no)) { // 发动机号
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>发动机号</span>为空";
					continue;
				}
				if (StringUtils.isBlank(plate_no)) { // 车牌号
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>车牌号</span>为空";
					continue;
				}
				if (StringUtils.isBlank(barCode)) { // T-box条码
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>T-box条码</span>为空";
					continue;
				}
				if (StringUtils.isBlank(call_letter)) { // T-box呼号
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>T-box呼号</span>为空";
					continue;
				}
				if (StringUtils.isBlank(equip_code)) { // 配置简码
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>配置简码</span>为空";
					continue;
				}
				if (StringUtils.isBlank(service_start_date)) { // 开通服务时间
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>开通服务时间</span>为空";
					continue;
				}
				if (StringUtils.isBlank(service_end_date)) { // 期满时间
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>期满时间</span>为空";
					continue;
				}
				if (StringUtils.isBlank(combo_change_date)) { // 更改套餐时间
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>更改套餐时间</span>为空";
					continue;
				}
				if (StringUtils.isBlank(combo_type)) { // 套餐类型
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>套餐类型</span>为空";
					continue;
				}
				if (StringUtils.isBlank(service_end_newdate)) { // 新有效期时间  
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>新有效期时间</span>为空";
					continue;
				}
				
				Date isService_start_date = null;
				Date isService_end_date = null;
				Date isCombo_change_date = null;
				Date isService_end_newdate = null;
				try {
					if (StringUtils.isNotBlank(service_start_date)) {
						isService_start_date = DateUtil.parse(service_start_date, "yyyy-MM-dd");
					}
					if (StringUtils.isNotBlank(service_end_date)) {
						isService_end_date = DateUtil.parse(service_end_date, "yyyy-MM-dd");
					}
					if (StringUtils.isNotBlank(combo_change_date)) {
						isCombo_change_date = DateUtil.parse(combo_change_date, "yyyy-MM-dd");
					}
					if (StringUtils.isNotBlank(service_end_newdate)) {
						isService_end_newdate = DateUtil.parse(service_end_newdate, "yyyy-MM-dd");
					}
				} catch (Exception el) {

				}
				
				try {
					// 保存客户信息
					Renew c = new Renew();
					c.setCustomer_name(customer_name);
					c.setBarCode(barCode);
					c.setCall_letter(call_letter);
					c.setCombo_change_date(isCombo_change_date);
					c.setCombo_type(combo_type);
					c.setEngine_no(engine_no);
					c.setEquip_code(equip_code);
					c.setPlate_no(plate_no);
					c.setService_end_date(isService_end_date);
					c.setService_end_newdate(isService_end_newdate);
					c.setService_start_date(isService_start_date);
					c.setTelphone(telPhone);
					c.setVin(vin);
					Integer cId = boundDao.add(c);
					//根据Vin修改t_fee_info服务截止时间
					if(isService_end_newdate!=null){//转换成data失败说明数据有问题,不进行更新
						boundDao.updateFeeSedateByVin(vin ,service_end_newdate);
					}
					sussNum++;
				} catch (Exception e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚本次插入
					message += "第<span style=color:green>" + rowNum + "</span>行,<span style=color:red>插入异常，原因:</span>" + e.getMessage();
					continue;
				}
			}
		} catch (Exception e) {

		}
		message = message + "<br />";
		failNum = total - sussNum - repeatNum;
		message = "共<span style=color:green>" + total + "</span>条数据，其中成功插入<span style=color:green>" + sussNum + "</span>条，重复<span style=color:red>"
				+ repeatNum + "</span>条，" + "失败<span style=color:red>" + failNum + "</span>条<br />" + message;

		map.put("success", true);
		map.put("msg", message);
		return map;
	}
	
	@Override
	public Page<Map<String, Object>> findTBOXByPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = boundDao.countTBOXPage(pageSelect.getFilter());
		List<Map<String, Object>> list = boundDao.findTBOXByPage(pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}
}

