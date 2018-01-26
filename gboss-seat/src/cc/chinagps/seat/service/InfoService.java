package cc.chinagps.seat.service;

import java.math.BigInteger;
import java.util.List;

import cc.chinagps.seat.auth.CompanyInfo;
import cc.chinagps.seat.auth.Organization;
import cc.chinagps.seat.bean.BasicInfoBean;
import cc.chinagps.seat.bean.FeeInfo;
import cc.chinagps.seat.bean.PhoneInfo;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.VehicleUnitInfo;
import cc.chinagps.seat.bean.table.AppContact;
import cc.chinagps.seat.bean.table.LinkmanTable;

public interface InfoService {

	/**
	 * 获取基本信息
	 * @param param
	 * @param type
	 * @return
	 */
	public BasicInfoBean getBasicInfo(String param, int type, 
			List<CompanyInfo> companyList);

	/**
	 * 获取车牌号、车载号信息。模糊查询车牌号、车载号
	 * @param platNo
	 * @param limit
	 * @return
	 */
	public List<VehicleUnitInfo> getVehicleInfo(String platNoOrCallLetter,
			int limit, List<CompanyInfo> companyList);

	/**
	 * 获取电话信息
	 * @param customerId
	 * @param vehicleId 
	 * @return
	 */
	public List<PhoneInfo> getPhoneInfo(BigInteger customerId, 
			BigInteger vehicleId);

	/**
	 * 保存联系人信息
	 * @param linkman
	 * @return
	 */
	public int saveLinkman(LinkmanTable linkman);
	
	/**
	 * 修改隐私密码
	 * @param vehicleId
	 * @param privatePwdOld
	 * @param privatePwdNew
	 * @return
	 */
	public int modifyPrivatePwd(BigInteger vehicleId,
			String privatePwdOld, String privatePwdNew);

	/**
	 * 修改车辆备注
	 * @param vehicleId
	 * @param remark
	 * @return
	 */
	public int modifyVehicleRemark(BigInteger vehicleId, String remark);

	/**
	 * 获取缴费信息
	 * @param param
	 * @param type
	 * @return
	 */
	public FeeInfo getFeeInfo(String param, int type);

	/**
	 * 通过省市获取区号
	 * @param province
	 * @param city
	 * @return
	 */
	public String getAreaCode(String province, String city);

	/**
	 * 获取电话簿信息记录
	 * @param customerId
	 * @param queryString
	 * @param query
	 * @return
	 */
	List<AppContact> getAppContactList(String customerId, String queryString, ReportCommonQuery query);

	/**
	 * 获取通用响应信息
	 * @param customerId
	 * @param queryString
	 * @return
	 */
	ReportCommonResponse getCommonResponse(String customerId, String queryString);

	/**
	 * 电话绑定座席
	 * @param phone
	 * @param seatNo
	 * @return 
	 */
	String setPhoneToAgent(String phone, String seatNo);

	Organization get4sShop(BigInteger s4Id);
}

