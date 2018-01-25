package com.hm.bigdata.dao;

import java.util.List;
import java.util.Map;

import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.entity.po.Subco;


/**
 * @Package:com.chinagps.fee.dao
 * @ClassName:SubcoDao
 * @Description:公司托收账号信息 数据持久层接口
 * @author:zfy
 * @date:2014-5-27 下午2:27:44
 */
public interface SubcoDao extends BaseDao {
	
	/**
	 * @Title:updateFlagOthers
	 * @Description:修改分公司其他托收账号的有效状态
	 * @param subco
	 * @return
	 * @throws SystemException
	 */
	public int updateFlagOthers(Subco subco) throws SystemException;
	
	/**
	 * @Title:findAllSubcos
	 * @Description:查询分公司所有托收账号
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public List<Subco> findAllSubcos(Map<String,Object> conditionMap) throws SystemException;
	
}

