package cc.chinagps.seat.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.auth.CompanyInfo;
import cc.chinagps.seat.bean.table.MarkCompanyTable;
import cc.chinagps.seat.bean.table.MarkCompanyTablePK;
import cc.chinagps.seat.bean.table.MarkTable;
import cc.chinagps.seat.bean.table.NavTable;
import cc.chinagps.seat.dao.MapDao;
import cc.chinagps.seat.service.MapService;
import cc.chinagps.seat.util.Consts;

@Service
public class MapServiceImpl extends BasicService implements MapService {

	@Autowired
	private MapDao mapDao;
	
	@Override
	public List<MarkTable> getAllMarks(Long opId, 
			List<CompanyInfo> companyInfoList) {
		//总公司就根本不需要看这些标注，这些对总部没意义。总部只关心运营报表之类的东西。
		return mapDao.getAllMarks(opId, getCompanyNo(companyInfoList, 
				false));
	}

	@Override
	public long addMark(MarkTable mark) {
		long id = mapDao.addMark(mark);
		BigInteger[] companyNos = mark.getCompanyNo();
		List<MarkCompanyTable> companyList = getCompanyList(
				id, companyNos);
		mapDao.addMarkCompany(companyList);
		return id;
	}

	@Override
	public void delMarkCompany(Long id, BigInteger[] companyNos) {
		List<MarkCompanyTable> companyList = getCompanyList(
				id, companyNos);
		mapDao.delMarkCompany(companyList);
	}
	
	@Override
	public int delMark(Long id, BigInteger[] companyNos) {
		List<MarkCompanyTable> markCompanyList = 
				mapDao.getAllMarkCompanys(id);
		
		int result = Consts.SUCCEED;
		if (markCompanyList.size() == 0) {
			result = mapDao.delMark(id);
		}
		return result;
	}
	
	private List<MarkCompanyTable> getCompanyList(long id, 
			BigInteger[] companyNos) {
		List<MarkCompanyTable> companyList = 
				new ArrayList<MarkCompanyTable>(companyNos.length);
		for (BigInteger companyNo : companyNos) {
			MarkCompanyTablePK pk = new MarkCompanyTablePK();
			pk.setId(id);
			pk.setCompanyNo(companyNo);
			MarkCompanyTable markCompany = new MarkCompanyTable();
			markCompany.setPk(pk);
			companyList.add(markCompany);
		}
		return companyList;
	}

	@Override
	public long saveNav(NavTable nav) {
		return mapDao.saveNav(nav);
	}
	
	@Override
	public NavTable loadLastNav(Long vehicleId, Long opId) {
		return mapDao.getLastNav(vehicleId, opId);
	}
}
