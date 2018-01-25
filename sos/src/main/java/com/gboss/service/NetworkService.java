package com.gboss.service;

import java.util.HashMap;
import java.util.Map;

import ldap.oper.OpenLdap;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.pojo.web.CustInfo;
import com.gboss.pojo.web.LargeCustMngEntry;
import com.gboss.pojo.web.LargeCustNetworkEntry;
import com.gboss.pojo.web.PrivateNetworkEntry;

/**
 * @Package:com.gboss.service
 * @ClassName:NetworkService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-8-1 下午3:09:38
 */
public interface NetworkService extends BaseService {
	
	/**
	 * 快速入网
	 * @param custInfo
	 * @param userId
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap fastNetwork(CustInfo custInfo,String userId,String companyid,String companycode,String companyname);
	
	/**
	 * 添加集客机构
	 * @param largeCustMngEntry
	 * @param userId
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap addLargeCustMng(LargeCustMngEntry largeCustMngEntry,String userId,String companyid,String companycode,String companyname);
	
	/**
	 * 添加集客子机构
	 * @param largeCustMngEntry
	 * @param userId
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap addChildlargeCust(LargeCustMngEntry largeCustMngEntry,String userId,String companyid,String companycode,String companyname);
	
	/**
	 * 更新集客机构
	 * @param largeCustMngEntry
	 * @param userId
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap updateLargeCustMng(LargeCustMngEntry largeCustMngEntry,String userId,String companyid,String companycode,String companyname);
	
	/**
	 * 私家车入网
	 * @param privateNetworkEntry
	 * @param userId
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap addPrivateCust(PrivateNetworkEntry privateNetworkEntry,String userId,String companyid,String companycode,String companyname);
	
	/**
	 * 修改私家车
	 * @param privateNetworkEntry
	 * @param userId
	 * @param username
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap updatePrivateCust(PrivateNetworkEntry privateNetworkEntry,String userId,String username,String companyid,String companycode,String companyname);
	
	/**
	 * 添加集团客户
	 * @param largeCustNetworkEntry
	 * @param userId
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap addLargecust(LargeCustNetworkEntry largeCustNetworkEntry,String userId,String companyid,String companycode,String companyname);
	
	/**
	 * 修改集团客户
	 * @param largeCustNetworkEntry
	 * @param userId
	 * @param username
	 * @param companyid
	 * @param companycode
	 * @param companyname
	 * @return
	 */
	public HashMap updateLargecust(LargeCustNetworkEntry largeCustNetworkEntry,String userId,String username,String companyid,String companycode,String companyname);
	
	/**
	 * 集客联系电话编辑
	 * @param largeCustNetworkEntry
	 * @param userId
	 * @param companyid
	 * @return
	 */
	public HashMap largeCustPhone(LargeCustNetworkEntry largeCustNetworkEntry,String userId,String companyid);
	
	/**
	 * 验证客户、车辆、车台添加/修改时重复性，计费信息修改时是否在计费托收中
	 * @param customer
	 * @param vehicle
	 * @param unit
	 * @param serviceInfo
	 * @param simfeeInfo
	 * @param insuranceInfo
	 * @param webgisInfo
	 * @return
	 */
	public HashMap getErrorMap(Customer customer, Vehicle vehicle, Unit unit, 
			FeeInfo serviceInfo, FeeInfo simfeeInfo, FeeInfo insuranceInfo, FeeInfo webgisInfo)throws SystemException;

	/**
	 * imei码重复验证
	 * @param param
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> checkImei(Map<String, Object> param)throws SystemException;

	/**
	 * 更新服务密码、隐私密码(私家车同步修改app登录密码)
	 * @param param
	 * @param userId
	 * @param userName
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> updateNetworkPwd(Map<String, Object> param, String userId, String userName)throws SystemException;

	/**
	 * 验证ldap中用户是否存在
	 * @param param
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> checkOperator(Map<String, Object> param)throws SystemException;

	/**
	 * 清除隐私密码
	 * @param customerId
	 * @param vehicleId
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> updatePrivatePwd(Long customerId, Long vehicleId)throws SystemException;

	/**
	 * 海马密码同步
	 * @param customerId
	 * @param vehicleId
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> changeServicePwd2Hm(Long customerId, Long vehicleId)throws SystemException;
}

