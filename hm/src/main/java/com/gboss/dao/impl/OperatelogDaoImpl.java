package com.gboss.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.OperatelogDao;

@Repository("OperatelogDao")  
@Transactional
public class OperatelogDaoImpl extends BaseDaoImpl implements OperatelogDao {

}
