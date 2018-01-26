package cc.chinagps.seat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.dao.SmsTemplateDao;
import cc.chinagps.seat.service.SenderService;
import cc.chinagps.seat.util.HttpUtil;

@Service("senderService")
public class SenderServiceImpl implements SenderService {
	
	@Autowired
	private SmsTemplateDao smsTemplateDao;
	
	/**
	 * 发送
	 * @param responseXML
	 * @return
	 */
	public  String smsSend(Map<String, String> params){
		return HttpUtil.smsRequest(params);
	}
	

	public String send(Map<String, String> params) {
		//响应xml结果
		String ret_xml = smsSend(params);
		return ret_xml;
	}


	@Override
	public List<Map<String, Object>> getSendsmsLists(ReportCommonQuery query,
			Map<String, String> param) {
		
		int start = query.getStart();
		List<Map<String, Object>> ret = smsTemplateDao.getSendsmsLists(query, param);
		List<Map<String, Object>> _ret = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> bean : ret){
			bean.put("sn", ++start);
			_ret.add(bean);
		}
			
		return _ret;
	}


	@Override
	public ReportCommonResponse getSendsmsListsCount(ReportCommonQuery query,
			Map<String, String> param) {
		return smsTemplateDao.getSendsmsListsCount(query, param);
	}


	@Override
	public List<Map<String, Object>> getSendsmsStatisticsLists(
			ReportCommonQuery query, Map<String, String> param) {
		int start = query.getStart();
		List<Map<String, Object>> ret = smsTemplateDao.getSendsmsStatisticsLists(query, param);
		List<Map<String, Object>> _ret = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> bean : ret){
			bean.put("sn", ++start);
			_ret.add(bean);
		}
		return _ret;
	}


	@Override
	public ReportCommonResponse getSendsmsStatisticsCount(
			ReportCommonQuery query, Map<String, String> param) {
		
		return smsTemplateDao.getSendsmsStatisticsCount(query, param);
	}
	

}
