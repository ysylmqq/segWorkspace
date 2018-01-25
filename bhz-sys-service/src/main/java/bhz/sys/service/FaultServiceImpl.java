package bhz.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhz.com.util.Page;
import bhz.com.util.PageSelect;
import bhz.sys.dao.FaultDao;
import bhz.sys.entity.Fault;
import bhz.sys.facade.FaultService;

@Service("faultService")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass=bhz.sys.facade.FaultService.class, 
	protocol = { "dubbo"},retries=0,timeout=60000)
public class FaultServiceImpl extends BaseServiceImpl implements FaultService {

	@Autowired
	private FaultDao faultDao;
	
	@Override
	public Page<HashMap<String, Object>> search(PageSelect<Fault> pageSelect, Long subco_no) {
		return faultDao.search(pageSelect, subco_no);
	}

	@Override
	public List<Map<String, Object>> findAllAlarms(Map<String, Object> map) {
		return faultDao.findAllFaults(map);
	}

	@Override
	public void saveFaultInfo(Fault fault) {
		faultDao.save(fault);
	}

	@Override
	public List<Map<String, Object>> getFalutCodeByName(String faultName) {
		return faultDao.getFalutCodeByName(faultName);
	}

}

