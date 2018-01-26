package cc.chinagps.seat.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.table.ComplaintTable;
public interface ComplaintDao {
	//void addComplaint(ComplaintTable complaint); 
	//void updateComplaint(ComplaintTable complaint);
	void saveOrUpdate(ComplaintTable complaint);
	int deleteComplaint(long id);
	ComplaintTable getById(long id);
    List<HashMap<String,Object>> getComplaint(ReportCommonQuery query,
    		Map<String, String> param);
    int getComplaintCount(ReportCommonQuery query,Map<String, String>param);
}
