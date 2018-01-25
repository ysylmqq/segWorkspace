package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.DocumentDao;
import com.gboss.pojo.Documents;
import com.gboss.service.DocumentService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:DocumentServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-23 下午4:28:07
 */
@Service("DocumentService")
@Transactional
public class DocumentServiceImpl extends BaseServiceImpl implements DocumentService {

	@Autowired
	@Qualifier("DocumentDao")
	private DocumentDao documentDao;
	
	@Override
	public List<Documents> search(Long unit_id) {
		return documentDao.search(unit_id);
	}

	@Override
	public void auditsuccsee(Long id) {
		documentDao.auditsuccsee(id);
	}

	@Override
	public void auditfail(Long id, String remark) {
		documentDao.auditfail(id, remark);
	}

	@Override
	public void add(Documents documents) {
		save(documents);
	}

}

