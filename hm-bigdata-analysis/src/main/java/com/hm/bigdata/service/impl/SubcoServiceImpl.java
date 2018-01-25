package com.hm.bigdata.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.dao.SubcoDao;
import com.hm.bigdata.entity.po.Subco;
import com.hm.bigdata.service.SubcoService;
import com.hm.bigdata.util.Utils;

/**
 * @Package:com.chinagps.fee.service
 * @ClassName:SubcoServiceImpl
 * @Description:公司托收账号信息表 业务层实现类
 * @author:zfy
 * @date:2014-5-27 下午2:56:38
 */
@Service("subcoService")
@Transactional(value="mysql1TxManager")
public class SubcoServiceImpl extends BaseServiceImpl implements SubcoService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SubcoServiceImpl.class);
	
	@Autowired  
	@Qualifier("subcoDao")
	private SubcoDao subcoDao;
	
	@Override
	public List<Subco> findSubco(Map<String,Object> conditionMap) throws SystemException {
		return subcoDao.findAllSubcos(conditionMap);
	}

	@Override
	public int addSubco(Subco subco) throws SystemException {
		if(subco == null){
			return -1;
		}
		subcoDao.save(subco);
		//如果本条记录标记设置为有效，则需要修改本分公司的其他标识为无效
		if(subco.getFlag()==1){
			subcoDao.updateFlagOthers(subco);
		}
		return 1;
	}

	@Override
	public int updateSubco(Subco subco) throws SystemException {
		int result=1;
		if(subco == null || subco.getAcId()==null){
			return -1;
		}
		//修改之前，判断存在不存在
		if(subcoDao.get(Subco.class, subco.getAcId())!=null){
			subcoDao.merge(subco);
			//如果本条记录标记设置为有效，则需要修改本分公司的其他标识为无效
			if(subco.getFlag()==1){
				subcoDao.updateFlagOthers(subco);
			}
			
		}else{
			result=0;
		}
		return result;
	}

	@Override
	public int deleteSubco(Subco subco) throws SystemException {
		int result=1;
		if(subco == null || subco.getAcId()==null){
			return -1;
		}
		//删除之前，判断存在不存在
		if(subcoDao.get(Subco.class, subco.getAcId())!=null){
			subcoDao.delete(Subco.class,subco.getAcId());
		}else{
			result=0;
		}
		return result;
	}


	@Override
	public Subco getSubco(Map<String,Object> conditionMap) throws SystemException {
		List<Subco> subcos=subcoDao.findAllSubcos(conditionMap);
		if(Utils.isNotNullOrEmpty(subcos)){
			return subcos.get(0);
		}
		return null;
	}

}

