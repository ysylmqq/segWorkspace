package bhz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.sys.dao.AlarmDao;
import bhz.sys.entity.Alarm;
import bhz.sys.facade.AlarmService;

@Service("alarmService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass=bhz.sys.facade.AlarmService.class,protocol = {"dubbo"},timeout=60000)
public class AlarmServiceImpl extends BaseServiceImpl implements AlarmService {

	@Autowired
	private AlarmDao alarmDao;
	

	@Override
	public Page<HashMap<String, Object>> search(PageSelect<Alarm> pageSelect, Long subco_no) {
		return alarmDao.search(pageSelect, subco_no);
	}

	@Override
	public List<Map<String, Object>> findAllAlarms(Map<String, Object> map) {
		return alarmDao.findAllAlarms(map);
	}

	@Override
	public  List<Map<String, Object>> alarmCount(Map<String, Object> map) {
		return alarmDao.alarmCount(map);
	}

	@Override
	public List<Map<String, Object>> faultCount(Map<String, Object> map) {
		return alarmDao.faultCount(map);
	}

	@Override
	public void saveAlarmInfo(Alarm alarm) {
		alarmDao.save(alarm);
	}

	@Override
	public List<HashMap<String, Object>> search() {
		return alarmDao.search();
	}

}

