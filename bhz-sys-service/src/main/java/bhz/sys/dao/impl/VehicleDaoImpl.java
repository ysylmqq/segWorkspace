package bhz.sys.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.com.util.PageUtil;
import bhz.com.util.StringUtils;
import bhz.com.util.Utils;
import bhz.sys.dao.VehicleDao;
import bhz.sys.entity.Vehicle;


@Repository("VehicleDao")
public class VehicleDaoImpl extends BaseDaoImpl implements VehicleDao {


	@Override
	public Page<HashMap<String, Object>> search(PageSelect<Vehicle> pageSelect, Long subco_no) {
		StringBuffer sbQuery =new StringBuffer( " SELECT cust.customer_name, veh.plate_no,unit.call_letter,sim.imei,sim.iccid,sim.barcode,sim.vin  ");
		StringBuffer sbCount =new StringBuffer( " SELECT count(1)  ");

		StringBuffer sb =new StringBuffer();
		sb.append(" FROM t_ba_vehicle veh LEFT JOIN t_ba_unit unit  ON veh.vehicle_id = unit.vehicle_id ");
		sb.append(" LEFT JOIN  t_ba_sim  sim ON unit.call_letter = sim.call_letter ");
		sb.append(" LEFT JOIN t_ba_customer cust ON unit.customer_id = cust.customer_id  WHERE 1=1 ");
		
		if (Utils.isNotNullOrEmpty(subco_no)) {
			sb.append(" and veh.subco_no = ").append(subco_no.toString());
		}
		
		Map filter = pageSelect.getFilter();
		String callLetter = (String) filter.get("call_letter");
		if (StringUtils.isNotBlank(callLetter)) {
			sb.append(" and unit.call_letter like  '%").append(callLetter).append("%' ");
		}
		
		String vin = (String) filter.get("vin");
		if (StringUtils.isNotBlank(vin)) {
			sb.append(" and sim.vin like  '%").append(vin).append("%' ");
		}
		
		String barcode = (String) filter.get("barcode");
		if (StringUtils.isNotBlank(barcode)) {
			sb.append(" and sim.barcode like  '%").append(barcode).append("%' ");
		}
		
		if (StringUtils.isNotBlank(pageSelect.getOrder())) {
			sb.append(" order by ").append(pageSelect.getOrder());
			if (pageSelect.getIs_desc()) {
				sb.append(" desc");
			} else {
				sb.append(" asc");
			}
		}else{
			sb.append(" order by veh.vehicle_id desc");
		}
		Query query = mysql1SessionFactory.getCurrentSession().createSQLQuery(sbQuery.append(sb).toString());  
		query.setFirstResult(PageUtil.getPageStart(pageSelect.getPageNo(), pageSelect.getPageSize()));
		query.setMaxResults(pageSelect.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();
		
		int count =((BigInteger)mysql1SessionFactory.getCurrentSession().createSQLQuery(sbCount.append(sb).toString()).uniqueResult()).intValue();//countAll(sb);
	    return PageUtil.getPage(count, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public List<Map<String, Object>> findAllVehicles(Map<String, Object> map) {
		StringBuffer sb =new StringBuffer("  SELECT cust.customer_name, veh.plate_no,unit.call_letter,sim.imei,sim.iccid,sim.barcode,sim.vin  ");
		sb.append(" FROM t_ba_vehicle veh LEFT JOIN t_ba_unit unit  ON veh.vehicle_id = unit.vehicle_id ");
		sb.append(" LEFT JOIN  t_ba_sim  sim ON unit.call_letter = sim.call_letter ");
		sb.append(" LEFT JOIN t_ba_customer cust ON unit.customer_id = cust.customer_id  WHERE 1=1 ");
		
		String subcoNo = (String) map.get("subcoNo");
		if (StringUtils.isNotBlank(subcoNo)) {
			sb.append(" and veh.subco_no = ").append(subcoNo);
		}
		
		String callLetter = (String) map.get("call_letter");
		if (StringUtils.isNotBlank(callLetter)) {
			sb.append(" and unit.call_letter like  '%").append(callLetter).append("%' ");
		}
		
		String vin = (String) map.get("vin");
		if (StringUtils.isNotBlank(vin)) {
			sb.append(" and sim.vin like  '%").append(vin).append("%' ");
		}
		
		String barcode = (String) map.get("barcode");
		if (StringUtils.isNotBlank(barcode)) {
			sb.append(" and sim.barcode like  '%").append(vin).append("%' ");
		}
		sb.append(" order by veh.vehicle_id desc");
		
		Query query = mysql1SessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();
		return list;
	}

	@Override
	public List<Map<String, Object>> findHmVehicles(Map<String, Object> map) {
		StringBuffer sb =new StringBuffer(" SELECT u.call_letter as callLetter,v.vehicle_id as vehicleId ");
		sb.append(" FROM t_ba_cust_vehicle cv  ");
		sb.append(" JOIN t_ba_customer bc ON bc.customer_id=cv.customer_id ");
		sb.append(" JOIN t_ba_vehicle v ON cv.vehicle_id=v.vehicle_id ");
		sb.append(" JOIN t_ba_unit u ON v.vehicle_id=u.vehicle_id  ");
		sb.append(" WHERE v.subco_no=201 and u.reg_status!=4	  ");
		
		Query query = mysql1SessionFactory.getCurrentSession().createSQLQuery(sb.toString());  
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();
		return list;
	}

}
