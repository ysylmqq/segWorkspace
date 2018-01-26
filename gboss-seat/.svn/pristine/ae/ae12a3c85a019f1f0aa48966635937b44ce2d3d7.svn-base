package cc.chinagps.seat.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import cc.chinagps.seat.bean.BasicInfoBean;
import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.VehicleUnitInfo;
import cc.chinagps.seat.bean.table.AppContact;
import cc.chinagps.seat.bean.table.ChinaCityTable;
import cc.chinagps.seat.bean.table.CollectionTable;
import cc.chinagps.seat.bean.table.DriverTable;
import cc.chinagps.seat.bean.table.FeeInfoTable;
import cc.chinagps.seat.bean.table.FeePaytxtTable;
import cc.chinagps.seat.bean.table.InsuranceTable;
import cc.chinagps.seat.bean.table.LinkmanTable;
import cc.chinagps.seat.bean.table.S4ShopTable;
import cc.chinagps.seat.bean.table.UnitTable;

public interface InfoDao {

	/**
	 * 通过电话号码（即车载号码）获取基本信息
	 * @param phoneNo
	 * @param companyNo
	 * @return
	 */
	public BasicInfoBean getBasicInfoByCallLetter(String phoneNo,
			BigInteger... companyNo);
	
	/**
	 * 通过联系人电话号码获取基本信息
	 * @param phoneNo
	 * @param companyNo 所属公司编号
	 * @return
	 */
	public BasicInfoBean getBasicInfoByPhoneNo(String phoneNo,
			BigInteger... companyNo);

	/**
	 * 通过车辆id获取基本信息
	 * @param vehicleId
	 * @param companyNo 所属公司编号
	 * @return
	 */
	public BasicInfoBean getBasicInfoByVehicleId(BigInteger vehicleId,
			BigInteger... companyNo);

	/**
	 * 根据车牌号或车载号获取车辆信息
	 * @param platNoOrCallLetter
	 * @param limit
	 * @param companyNo 所属公司编号
	 * @return
	 */
	public List<VehicleUnitInfo> getVehicleInfo(String platNoOrCallLetter,
			int limit, BigInteger... companyNo);

	/**
	 * 获取客户联系人信息
	 * @param customerId
	 * @return
	 */
	public List<LinkmanTable> getLinkmanTable(BigInteger customerId,
			BigInteger vehicleId);
	
	/**
	 * 保存联系人信息
	 * @param linkman
	 * @return
	 */
	public BigInteger saveLinkman(LinkmanTable linkman);

	/**
	 * 获取司机信息
	 * @param customerId
	 * @return
	 */
	public List<DriverTable> getDriverTableList(BigInteger customerId,
			BigInteger vehicleId);

	/**
	 * 获取保险公司信息
	 * @param customerId
	 * @return
	 */
	public List<InsuranceTable> getInsuranceByCustomerId(BigInteger customerId);

	/**
	 * 获取4s店电话信息
	 * @param customerId
	 * @return
	 */
	public List<S4ShopTable> get4sShopTableList(BigInteger customerId);

	/**
	 * 修改私有密码
	 * @param vehicleId
	 * @param privatePwdOld
	 * @param privatePwdNew
	 * @return
	 */
	public int modifyPrivatePwd(BigInteger vehicleId, String privatePwdOld,
			String privatePwdNew);

	/**
	 * 修改车辆备注
	 * @param vehicleId
	 * @param remark
	 * @return
	 */
	public int modifyVehicleRemark(BigInteger vehicleId, String remark);

	/**
	 * 通过车辆id获取{@link FeePaytxtTable}信息
	 * @param vehicleId
	 * @return
	 */
	public FeePaytxtTable getFeePaytxtByVehicleId(BigInteger vehicleId);

	public CollectionTable getCollectionByVehicleId(BigInteger vehicleId);

	/**
	 * 通过车辆id获取{@link InsuranceTable}信息
	 * @param vehicleId
	 * @return
	 */
	public InsuranceTable getInsuranceByVehicleId(BigInteger vehicleId);

	/**
	 * 通过车辆id获取{@link FeeInfoTable}信息
	 * @param vehicleId
	 * @return
	 */
	public List<FeeInfoTable> getFeeInfoByVehicleId(BigInteger vehicleId);

	/**
	 * 通过电话号码获取{@link InsuranceTable}信息
	 * @param phoneNum
	 * @return
	 */
	public InsuranceTable getInsuranceByPhoneNo(String phoneNum);

	/**
	 * 通过电话号码获取{@link FeePaytxtTable}信息
	 * @param phoneNum
	 * @return
	 */
	public FeePaytxtTable getFeePaytxtByPhoneNo(String phoneNum);

	public CollectionTable getCollectionByPhoneNo(String phoneNum);

	/**
	 * 通过电话号码获取{@link FeeInfoTable}信息
	 * @param phoneNum
	 * @return
	 */
	public List<FeeInfoTable> getFeeInfoByPhoneNo(String phoneNum);

	/**
	 * 获取所有城市信息数据。返回格式为Map<provinceName, Map<name, ChinaCityTable>>
	 * @return
	 */
	public Map<String, Map<String, ChinaCityTable>> getChinaCities();

	/**
	 * 获取车载信息
	 * @param unitId
	 * @return
	 */
	public UnitTable getUnit(BigInteger unitId);

	/**
	 * 获取电话簿信息记录
	 * @param customerId
	 * @param queryString
	 * @param query
	 * @return
	 */
	List<AppContact> getAppContactList(String customerId, String queryString, ReportCommonQuery query);

	/**
	 * 获取电话簿信息记录长度
	 * @param customerId
	 * @param queryString
	 * @return
	 */
	long getAppContactCount(String customerId, String queryString);
}
