package cc.chinagps.seat.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.ReportComplanint;
import cc.chinagps.seat.bean.table.ComplaintTable;
import cc.chinagps.seat.dao.ComplaintDao;
import cc.chinagps.seat.service.ComplaintService;
import cc.chinagps.seat.util.Consts;

@Service
public class ComplaintServiceImpl implements ComplaintService {

	private static final Logger LOGGER = Logger.getLogger(
			ComplaintServiceImpl.class);
	
	@Autowired
	private ComplaintDao complaintDao;

	@Override
	public  int saveOrUpdate(ComplaintTable complaintTable) {
		complaintDao.saveOrUpdate(complaintTable);
		return Consts.SUCCEED;
	}
	@Override
	public ReportCommonResponse getComplaintCommonResp(ReportCommonQuery query,
			Map<String, String> param) {
		long count = complaintDao.getComplaintCount(query, param);
		ReportCommonResponse commResp = new ReportCommonResponse();
		commResp.setRecordsFiltered(count);
		commResp.setRecordsTotal(count);
		return commResp;
	} 
	@Override
	public List<ReportComplanint> getComplaint(ReportCommonQuery query,
    		Map<String, String> param){	
		List<HashMap<String, Object>> list = complaintDao.getComplaint(query, param);
		List<ReportComplanint> resultList = new ArrayList<ReportComplanint>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int sn = 0;
		for(HashMap<String, Object> entity:list){
			ReportComplanint complanint = new ReportComplanint();
			ComplaintTable complaintTable = new ComplaintTable();
			sn++;
			complanint.setSn(sn);
			try {
			
			BigInteger id = (BigInteger)entity.get("cp_id");
			complaintTable.setCp_id(id.longValue());
			complaintTable.setCp_no(entity.get("cp_no")+"");
			if(entity.get("accept_time")!=null){
			    complaintTable.setAccept_time(sdf.parse(entity.get("accept_time")+""));	
			}
			
			complaintTable.setVehicle_id(entity.get("vehicle_id")+"");
			complaintTable.setCp_name(entity.get("cp_name")+"");
			complaintTable.setCp_phone(entity.get("cp_phone")+"");
			complaintTable.setCp_type_remark(entity.get("cp_type_remark")+"");
			complaintTable.setCp_type_id((Integer)entity.get("cp_type_id"));
			complaintTable.setCp_content(entity.get("cp_content")+"");
			complaintTable.setAcceptance(entity.get("acceptance")+"");
			complaintTable.setCp_request(entity.get("cp_request")+"");
			complaintTable.setFollow_rec(entity.get("follow_rec")+"");
			complaintTable.setAc_op_name(entity.get("ac_op_name")+"");
			complaintTable.setOp_name(entity.get("op_name")+"");
			complaintTable.setStatus_id((Integer)entity.get("status_id"));
			if(entity.get("follow_time") !=null ){
				complaintTable.setFollow_time(sdf.parse(entity.get("follow_time")+""));
				}
			if(entity.get("stamp") !=null ){
			complaintTable.setStamp(sdf.parse(entity.get("stamp")+""));
			}
			complanint.setComplaintTable(complaintTable);
			complanint.setCustomer_name(entity.get("customer_name")+"");
			complanint.setPlate_no(entity.get("plate_no")+"");
			complanint.setCall_letter(entity.get("call_letter")+"");
			complanint.setSubco_no(entity.get("subco_no")+"");
			complanint.setSname(entity.get("sname")+"");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			resultList.add(complanint);
			
		}
		return resultList;
	
	}
	
	@Override
	public int deleteComplaint(long id) {
		complaintDao.deleteComplaint(id);
		return Consts.SUCCEED;
	}
	@Override
	public ComplaintTable getById(long id) {		
		return complaintDao.getById(id);
	}
	
}
