package cc.chinagps.seat.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.GroupUserTable;
import cc.chinagps.seat.bean.table.ProductLibTable;
import cc.chinagps.seat.bean.table.SeatSegGroupTable;
import cc.chinagps.seat.bean.table.SeatSegPhonebookTable;
import cc.chinagps.seat.dao.GroupUserTableDao;
import cc.chinagps.seat.dao.KnowledegLibDao;
import cc.chinagps.seat.service.KnowledgeLibService;

@Service("nlService")
public class KnowledgeLibServiceImpl implements KnowledgeLibService {
	
	@Autowired
	private KnowledegLibDao nlDao;
	@Autowired
	private GroupUserTableDao groupUserTableDao;
	
	@Override
	public List<SeatSegGroupTable> getTelSearchGroups() {
		List<SeatSegGroupTable> groups = nlDao.getTelSearchGroups();
		Map<Integer, SeatSegGroupTable> groupMap = 
				new HashMap<Integer, SeatSegGroupTable>(groups.size());
		
		//根group列表
		List<SeatSegGroupTable> rootGroupList = new ArrayList<SeatSegGroupTable>();
		for (SeatSegGroupTable group : groups) {
			groupMap.put(group.getGroupId(), group);
			if (group.getParentGroupId() != null && group.getParentGroupId() == -1) {
				rootGroupList.add(group);
			}
		}
		
		// 将组以parentGroupId进行分组，
		for (SeatSegGroupTable group : groups) {
			SeatSegGroupTable parentGroup = groupMap.get(group.getParentGroupId());
			if (parentGroup != null) {
				parentGroup.addChild(group);
			}
		}
		return rootGroupList;
	}
	
	@Override
	public Collection<SeatSegPhonebookTable> getTelSearchPhoneBooks(int groupId) {
		return nlDao.getTelSearchPhoneBooks(groupId);
	}
	
	@Override
	public List<SeatSegPhonebookTable> searchPhoneBooksInTelSearch(String param) {
		return nlDao.getPhoneBooksInTelSearch(param);
	}
	
	@Override
	public ReportCommonResponse getProductLibCommonResp(String param, ReportCommonQuery query) {
		ReportCommonResponse commResp = new ReportCommonResponse();
		long count = nlDao.getProductLibCount(null);
		long filterdCount;
		if (param != null && param.trim().length() > 0) {
			filterdCount = nlDao.getProductLibCount(param);
		} else {
			filterdCount = count;
		}
		commResp.setRecordsFiltered(filterdCount);
		commResp.setRecordsTotal(count);
		return commResp;
	}
	
	@Override
	public List<ProductLibTable> getProductLibs(String param, ReportCommonQuery query) {
		return nlDao.getProductLibs(param, query);
	}

	@Override
	public void save(SeatSegPhonebookTable sspt,String groupid) {
		if( sspt.getPhonebookId() == 0 ){
			nlDao.save(sspt);
			Long phid = sspt.getPhonebookId();
			GroupUserTable gut = new GroupUserTable();
			gut.setGroup_id(Long.valueOf(groupid));
			gut.setPhonebook_id(phid);
			groupUserTableDao.save(gut );
		} else {
			nlDao.save(sspt);
		}
	}

	@Override
	public void del(Long[] phonebook_id) {
		nlDao.del(phonebook_id);
	}

	//修改或保存-产品
	public void addProduct(ProductLibTable plt){
		nlDao.addProduct(plt);
	}

	@Override
	public ProductLibTable findProduct(int pid) {
		return nlDao.findProduct(pid);
	}

	@Override
	public void delProducts(Integer[] pid) {
		nlDao.delProducts(pid);
	}

}
