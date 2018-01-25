package com.gboss.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.UnitDao;
import com.gboss.pojo.Unit;
import com.gboss.service.UnitService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:UnitServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:45:05
 */

@Service("UnitService")
@Transactional
public class UnitServiceImpl extends BaseServiceImpl implements UnitService {
	
	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;

	@Override
	public Long add(Unit unit) {
		save(unit);
		return unit.getUnit_id();
	}

	@Override
	public boolean is_repeat(Unit unit) {
		return unitDao.is_repeat(unit);
	}

	@Override
	public List<Unit> getByVehicle_id(Long vehicle_id) {
		return unitDao.getByVehicle_id(vehicle_id);
	}

	@Override
	public Page<Unit> search(PageSelect<Unit> pageSelect, Long subco_no) {
		return unitDao.search(pageSelect, subco_no);
	}

	@Override
	public Unit getUnitByid(Long id) {
		return unitDao.getUnitByid(id);
	}

	@Override
	public void delete(Long id) {
		delete(Unit.class, id);
	}

	@Override
	public Unit getByCustAndVehicle(Long cust_id, Long vehicle_id) {
		return unitDao.getByCustAndVehicle(cust_id, vehicle_id);
	}

	@Override
	public Page<HashMap<String, Object>> getUnitMsgBypage(Long commpanyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = unitDao.countUnits(commpanyId, pageSelect.getFilter());
		List<HashMap<String, Object>> list=unitDao.getUnitMsgBypage(commpanyId,pageSelect.getFilter(),  pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		/*for (HashMap<String, Object> map : list) {
			String mark = "0";
			if(StringUtils.isNotNullOrEmpty(map.get("stamp"))){
				String stamp = map.get("stamp").toString();
				Object register_date = map.get("register_date");
				if(null == register_date){
					mark = "1";
				}else{
					String reg_date = register_date.toString();
					Date date1 = DateUtil.parse(reg_date, DateUtil.YMD_DASH_WITH_FULLTIME);
					Date date2 = DateUtil.parse(stamp, DateUtil.YMD_DASH_WITH_FULLTIME);
					mark = DateUtil.daysBetween(date1, date2) > SystemConst.SUBMIT_DATE ? "0" :"1";
				}
			}
			map.put("mark", mark);
		}*/
		
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public List<HashMap<String, Object>> getexportExcelUnits(Long companyId,
			Map<String, Object> map) throws SystemException {
		return unitDao.getUnitMsgBypage( companyId ,map, null, false, 0, 0);
	}

	@Override
	public List<HashMap<String, Object>> findUnits(Long companyId, Map<String, Object> conditionMap)
			throws SystemException {
		List<HashMap<String, Object>> list=unitDao.getUnitList(companyId, conditionMap);
		for (HashMap<String, Object> map : list) {
			//是否交单
			if(StringUtils.isNotNullOrEmpty(map.get("d_id"))){
				map.put("is_submit", "是");
			}else{
				map.put("is_submit", "否");
			}
			/*//是否交单及时
			String mark = "否";
			if(StringUtils.isNotNullOrEmpty(map.get("stamp"))){
				String stamp = map.get("stamp").toString();
				Object register_date = map.get("register_date");
				if(null == register_date){
					mark = "是";
				}else{
					String reg_date = register_date.toString();
					Date date1 = DateUtil.parse(reg_date, DateUtil.YMD_DASH_WITH_FULLTIME);
					Date date2 = DateUtil.parse(stamp, DateUtil.YMD_DASH_WITH_FULLTIME);
					mark = DateUtil.daysBetween(date1, date2) > SystemConst.SUBMIT_DATE ? "否" :"是";
				}
			}
			map.put("mark", mark);*/
			
			//是否审核通过
			String  is_checked = "否";
			if(StringUtils.isNotNullOrEmpty(map.get("flag"))){
				if(map.get("flag").toString().equals("1"))
					is_checked = "是";
			}
			map.put("is_checked", is_checked);
		}
		
		return list;
		
	}

	@Override
	public HashMap<String, Object> getInnetwork(Long subco_no, String time) {
		return unitDao.getInnetwork(subco_no, time);
	}

	@Override
	public Page<Unit> search2(PageSelect<Unit> pageSelect, Long subco_no) {
		return unitDao.search2(pageSelect, subco_no);
	}

	@Override
	public Page<Unit> search3(PageSelect<Unit> pageSelect, Long subco_no) {
		return unitDao.search3(pageSelect, subco_no);
	}

	@Override
	public Page<Unit> search4(PageSelect<Unit> pageSelect, Long subco_no) {
		return unitDao.search4(pageSelect, subco_no);
	}

	@Override
	public Page<Unit> search5(PageSelect<Unit> pageSelect, Long subco_no) {
		return unitDao.search5(pageSelect, subco_no);
	}

	@Override
	public Page<HashMap<String, Object>> findUnitInNetsBypage(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		List<HashMap<String, Object>> dataList=unitDao.findUnitInNets(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
		int total = unitDao.countUnitInNets(pageSelect.getFilter());
		return PageUtil.getPage(total, pageSelect.getPageNo(), dataList, pageSelect.getPageSize());
	}

	@Override
	public List<HashMap<String, Object>> findAllUnitInNets(
			Map<String, Object> conditionMap) throws SystemException {
		return unitDao.findUnitInNets(conditionMap,null,false,0,0);
	}

}

