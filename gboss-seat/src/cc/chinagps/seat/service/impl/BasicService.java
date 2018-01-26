package cc.chinagps.seat.service.impl;

import java.math.BigInteger;
import java.util.List;

import cc.chinagps.seat.auth.CompanyInfo;
import cc.chinagps.seat.controller.BasicController;
import cc.chinagps.seat.util.Consts;

public abstract class BasicService {

	/**
	 * 获取公司ID。
	 * @param companyList
	 * @param needDataAuth 如果需要数据权限，且公司列表中包含总部，则返回0长度公司ID。
	 * 		否则返回公司列表的公司ID
	 * @return
	 * @deprecated 使用{@link BasicController.getLoginUserCompanyNo}获取公司ID
	 */
	@Deprecated
	protected BigInteger[] getCompanyNo(List<CompanyInfo> companyList,
			boolean needDataAuth) {
		BigInteger[] companyNo;
		if (needDataAuth && (
				companyList.contains(CompanyInfo.HEADQUARTERS) ||
				Consts.AUTH_DISABLE)) {
			// 公司总部能够查看所有车辆
			companyNo = new BigInteger[0];
		} else {
			companyNo = new BigInteger[companyList.size()];
			for (int i = 0; i < companyNo.length; i++) {
				companyNo[i] = companyList.get(i).getCompanyNo();
			}
		}
		return companyNo;
	}

}
