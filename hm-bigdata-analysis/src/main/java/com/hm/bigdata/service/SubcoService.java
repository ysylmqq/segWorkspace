package com.hm.bigdata.service;

import java.util.List;
import java.util.Map;

import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.entity.po.Subco;

/**
 * @Package:com.chinagps.fee.service
 * @ClassName:SubcoService
 * @Description:公司托收账号信息表 业务层接口
 * @author:zfy
 * @date:2014-5-27 下午2:54:45
 */
public interface SubcoService extends BaseService{
	
	/**
	 * @Title:findSubco
	 * @Description:查询公司托收账号信息
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 * @throws
	 */
	public List<Subco> findSubco(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:getSubco
	 * @Description:查找分公司托收账号
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public Subco getSubco(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:addSubco
	 * @Description:添加公司托收账号信息
	 * @param subco
	 * @return 1:成功，2：编号已存在，3：名称已存在
	 * @throws SystemException
	 * @throws
	 */
	public int addSubco(Subco subco) throws SystemException;
	
	/**
	 * @Title:updateSubco
	 * @Description:添加公司托收账号信息
	 * @param subco
	 * @return 1:成功，2：编号已存在，3：名称已存在
	 * @throws SystemException
	 * @throws
	 */
	public int updateSubco(Subco subco) throws SystemException;
	
	/**
	 * @Title:deleteSubco
	 * @Description:删除公司托收账号信息
	 * @param subco
	 * @return 1:成功，2：公司托收账号信息在使用中,不能删除
	 * @throws SystemException
	 * @throws
	 */
	public int deleteSubco(Subco subco) throws SystemException;
}

