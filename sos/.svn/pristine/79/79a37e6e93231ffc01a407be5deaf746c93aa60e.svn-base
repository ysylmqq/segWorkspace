package com.gboss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.WriteoffDao;
import com.gboss.pojo.Writeoff;

@Repository("WriteoffDao")  
@Transactional
public class WriteoffDaoImpl extends BaseDaoImpl implements WriteoffDao {

	@Override
	public float getLastBalance(Long manager_id) {
		String hql = "from Writeoff where manager_id = " + manager_id + " order by time desc ";
		List<Writeoff> list = listAll(hql);
		if(list.size()>0){
			Writeoff writeoff = list.get(0);
			return writeoff.getBalance();
		}
		return 0;
	}

}
