package cc.chinagps.seat.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.MarkCompanyTable;
import cc.chinagps.seat.bean.table.MarkTable;
import cc.chinagps.seat.bean.table.NavTable;
import cc.chinagps.seat.dao.MapDao;
import cc.chinagps.seat.util.Consts;

@Repository
public class MapDaoImpl extends BasicDao implements MapDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<MarkTable> getAllMarks(Long opId, BigInteger... companyNo) {
		return getSession().getNamedQuery("SelectMarks")
				.setParameterList("companyNos", companyNo).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarkCompanyTable> getAllMarkCompanys(Long markId) {
		return (List<MarkCompanyTable>) getSession()
				.getNamedQuery("SelectMarkCompanyByID")
				.setParameter("id", markId).list();
	}
	
	@Override
	public long addMark(MarkTable mark) {
		Session session = getWriteSession();
		return (Long) session.save(mark);
	}
	
	@Override
	public void addMarkCompany(List<MarkCompanyTable> markCompanyList) {
		Session session = getWriteSession();
		for (MarkCompanyTable markCompany : markCompanyList) {
			session.save(markCompany);
		}
	}
	
	@Override
	public int delMark(Long id) {
		Session session = getWriteSession();
		Object obj = session.get(MarkTable.class, id);
		if (obj == null) {
			return Consts.MAP_MARK_ERROR_NO_MARK;
		}
		session.delete(obj);
		return Consts.SUCCEED;
	}
	
	@Override
	public void delMarkCompany(
			List<MarkCompanyTable> markCompanyList) {
		Session session = getWriteSession();
		for (MarkCompanyTable markCompany : markCompanyList) {
			Object obj = session.get(MarkCompanyTable.class, 
					markCompany.getPk());
			if (obj != null) {
				session.delete(obj);
			}
		}
	}
	
	@Override
	public long saveNav(NavTable nav) {
		return (Long) getWriteSession().save(nav);
	}
	
	@Override
	public NavTable getLastNav(Long vehicleId, Long opId) {
		return (NavTable) getSession().getNamedQuery("SelectNavByVehicleId")
				.setLong("vehicleId", vehicleId)
				.setLong("opId", opId).uniqueResult();
	}
}
