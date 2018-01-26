package cc.chinagps.seat.service;

import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.ReportComplanint;
import cc.chinagps.seat.bean.table.ComplaintTable;

public interface ComplaintService {
    /*int addComplaint(ComplaintTable complaint);
    int updateComplaint(ComplaintTable complaint);*/
    int saveOrUpdate(ComplaintTable complaintTable);
    int deleteComplaint(long id);
    ComplaintTable getById(long id);
    ReportCommonResponse getComplaintCommonResp(ReportCommonQuery query, Map<String, String> param);
    List<ReportComplanint> getComplaint(ReportCommonQuery query,
    		Map<String, String> param);
}
