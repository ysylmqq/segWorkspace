package com.gboss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.InsurerDao;
import com.gboss.pojo.Insurer;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:InsurerDaoImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-24 上午11:20:38
 */
@Repository("InsurerDao")  
@Transactional
public class InsurerDaoImpl extends BaseDaoImpl implements InsurerDao {

	@Override
	public List<Insurer> getInsurersByname(String insurer_name) {
		String hql = "from Insurer where 1=1 and s_name like '%"+insurer_name+"%' order by stamp";
		List<Insurer> list = listAll(hql);
		return list;
	}

}

