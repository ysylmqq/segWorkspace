package cc.chinagps.seat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.ServerTypeTable;
import cc.chinagps.seat.dao.CallBriefDao;
import cc.chinagps.seat.service.CallBriefService;
import cc.chinagps.seat.util.Consts;

@Service("callBriefService")
public class CallBriefServiceImpl extends BasicService  implements CallBriefService {

	@Autowired
	private CallBriefDao callBriefDao;
	
	@Override
	public List<Map<String, Object>> getCallInBriefs(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		int start = query.getStart();
		List<Map<String, Object>> ret = callBriefDao.getCallInBriefs(brief, query, param);
		List<Map<String, Object>> _ret = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> bean : ret){
			
			//父类名称
			String super_servertype_names = String.valueOf(((Integer)bean.get("super_servertype_name")));
			if(super_servertype_names != null&& !"".equals(super_servertype_names)){
				String[] st_ids = super_servertype_names.split(",");
				for(String st_id:st_ids){
					ServerTypeTable stt = Consts.SERVICE_TYPE_CACHE.get(st_id);
					if(stt != null){
						if(stt.getP_id() != 0){
							stt =  Consts.SERVICE_TYPE_CACHE.get(String.valueOf(stt.getP_id()));
						}
						bean.put("super_servertype_name", stt.getSt_name());
						break;
					}
				}
			}
			//子类名称
			String contents = (String) bean.get("content");
			StringBuffer ret_content = new StringBuffer();
			if(contents!=null&& !"".equals(contents)){
				String[] st_ids = contents.split(",");
				for(String st_id:st_ids){
					ServerTypeTable stt = Consts.SERVICE_TYPE_CACHE.get(st_id);
					if(stt != null){
						ret_content.append(stt.getSt_name()).append(",");
					}
				}
				bean.put("content", ret_content.toString());
			}
			bean.put("sn", ++start);
			_ret.add(bean);
		}
		return _ret;
	}

	@Override
	public ReportCommonResponse getCallInBriefsCount(BriefTable brief, ReportCommonQuery query,
			Map<String, String> param) {
		return callBriefDao.getCallInBriefsCount(brief, query, param);
	}

	@Override
	public List<Map<String, Object>> getCallInSubStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		return callBriefDao.getCallInSubStatistics(brief, query, param);
	}

	@Override
	public List<Map<String, Object>> getCallInSuperStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		return callBriefDao.getCallInSuperStatistics(brief, query, param);
	}

	@Override
	public List<Map<String, Object>> getCalloutFlagStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		return callBriefDao.getCalloutFlagStatistics(brief, query, param);
	}

	@Override
	public List<Map<String, Object>> getCalloutStatistics(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		int start = query.getStart();
		List<Map<String, Object>> ret = callBriefDao.getCalloutStatistics(brief, query, param);
		List<Map<String, Object>> _ret = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> bean : ret){
			bean.put("sn", ++start);
			_ret.add(bean);
		}
		return _ret;
	}

	@Override
	public ReportCommonResponse getCalloutStatisticsCount(BriefTable brief,
			ReportCommonQuery query, Map<String, String> param) {
		return callBriefDao.getCalloutStatisticsCount(brief, query, param);
	}

}
