package cc.chinagps.seat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.SmsTemplateTable;
import cc.chinagps.seat.dao.SmsTemplateDao;
import cc.chinagps.seat.service.SmsTemplateService;

@Service("smsTemplateService")
public class SmsTemplateServiceImpl extends BasicService implements
		SmsTemplateService {

	@Autowired
	private SmsTemplateDao smsTemplateDao;
	
	@Override
	public void saveOrUpdate(SmsTemplateTable stt) {
		smsTemplateDao.saveOrUpdate(stt);
	}

	@Override
	public void delteSms(SmsTemplateTable obj) {
		smsTemplateDao.delteSms(obj);
	}

	@Override
	public List<SmsTemplateTable> getAllSmsTemps(Map<String, Object> params) {
		return smsTemplateDao.getAllSmsTemps(params);
	}

	@Override
	public SmsTemplateTable getSmsTemplateByID(Integer stId) {
		return smsTemplateDao.getSmsTemplateByID(stId);
	}

	@Override
	public int batchSmsDel(Integer[] stIds) {
		return smsTemplateDao.batchSmsDel(stIds);
	}

	@Override
	public int getNewStCode() {
		// TODO Auto-generated method stub
		return smsTemplateDao.getNewStCode()+1;
	}

	@Override
	public int updateType(String stCode, String stName) {
		// TODO Auto-generated method stub
		return smsTemplateDao.updateType(stCode, stName);
	}

	@Override
	public void delType(String stCode) {
		smsTemplateDao.delType(stCode);
	}

}
