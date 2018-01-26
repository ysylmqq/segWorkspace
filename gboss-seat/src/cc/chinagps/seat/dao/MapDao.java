package cc.chinagps.seat.dao;

import java.math.BigInteger;
import java.util.List;

import cc.chinagps.seat.bean.table.MarkCompanyTable;
import cc.chinagps.seat.bean.table.MarkTable;
import cc.chinagps.seat.bean.table.NavTable;

public interface MapDao {

	/**
	 * 获取所有标注
	 * @param opId 操作员id
	 * @param companyNo
	 * @return
	 */
	List<MarkTable> getAllMarks(Long opId, BigInteger... companyNo);
	
	/**
	 * 获取所有标注公司
	 * @param pk
	 * @return
	 */
	List<MarkCompanyTable> getAllMarkCompanys(Long markId);
	
	/**
	 * 增加标注
	 * @param mark
	 * @return
	 */
	long addMark(MarkTable mark);
	
	void addMarkCompany(List<MarkCompanyTable> markCompanyList);

	/**
	 * 删除标注
	 * @param mark
	 */
	int delMark(Long id);
	void delMarkCompany(List<MarkCompanyTable> markCompanyList);

	/**
	 * 保存导航信息
	 * @param nav
	 * @return
	 */
	long saveNav(NavTable nav);

	/**
	 * 加载上次导航信息
	 * @param vehicleId
	 * @return
	 */
	NavTable getLastNav(Long vehicleId, Long opId);
}
