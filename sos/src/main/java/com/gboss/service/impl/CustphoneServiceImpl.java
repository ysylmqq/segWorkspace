package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.CustphoneDao;
import com.gboss.pojo.Linkman;
import com.gboss.service.CustphoneService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:CustphoneServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午5:28:50
 */
@Service("CustphoneService")
@Transactional
public class CustphoneServiceImpl extends BaseServiceImpl implements CustphoneService {

	@Autowired
	@Qualifier("CustphoneDao")
	private CustphoneDao custphoneDao;
	
	@Override
	public void add(Linkman custphone) {
		save(custphone);
	}

	@Override
	public List<Linkman> listCustphone(Long cust_id) {
		return custphoneDao.listCustphone(cust_id);
	}

	@Override
	public void deleteByCust_id(Long cust_id) {
		custphoneDao.deleteByCust_id(cust_id);
	}

	@Override
	public List<Linkman> getLinkmanList(Long customer_id) {
		return custphoneDao.getLinkmanList(customer_id);
	}

	@Override
	public Page<Linkman> findLinkman(PageSelect<Linkman> pageSelect) {
		return custphoneDao.findLinkman(pageSelect);
	}

}

