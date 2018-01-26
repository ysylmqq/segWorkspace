package cc.chinagps.seat.service;

import java.math.BigInteger;
import java.util.List;

import cc.chinagps.seat.auth.CompanyInfo;
import cc.chinagps.seat.bean.table.MarkTable;
import cc.chinagps.seat.bean.table.NavTable;

public interface MapService {

	/**
	 * 获取所有标注
	 * @param opId
	 * @param companyInfos 
	 * @return
	 */
	List<MarkTable> getAllMarks(Long opId, 
			List<CompanyInfo> companyInfoList);

	/**
	 * 增加标注
	 * @param mark
	 * @return
	 */
	long addMark(MarkTable mark);

	/**
	 * 删除标注
	 * @param mark
	 */
	int delMark(Long id, BigInteger[] companyNos);

	void delMarkCompany(Long id, BigInteger[] companyNos);
	
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
	NavTable loadLastNav(Long vehicleId, Long opId);
}
