package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.CustomerDao;
import com.gboss.dao.DictDao;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Dict;
import com.gboss.util.StringUtils;

@Repository("DictDao")  
@Transactional
public class DictDaoImpl extends BaseDaoImpl implements DictDao {

	@Override
	public List<Dict> getDicts(int type_id) {
		String hql = " from Dict where type=" + type_id + " and isdel=0 order by sno,id";
		return listAll(hql);
	}

	@Override
	public List<Dict> getDicts(int type_id, String name) {
		String hql = " from Dict where type=" + type_id + " and isdel=0 " ;
		if(StringUtils.isNotBlank(name)){
			hql+= " and name like '%"+name+"%' ";
		}
		hql+= " order by sno,id";
		return listAll(hql);
	}
	

}
