package com.gboss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.AnswerDetailsDao;
import com.gboss.pojo.AnswerDetails;
import com.gboss.service.AnswerDetailsService;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:AnswerDetailsServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-30 下午2:50:12
 */
@Service("answerDetailsService")
@Transactional
public class AnswerDetailsServiceImpl extends BaseServiceImpl implements AnswerDetailsService {

	@Autowired  
	@Qualifier("AnswerDetailsDao")
	private AnswerDetailsDao answerDetailsDao;
	
	@Override
	public List<AnswerDetails> getByAnswerid(Long answer_id) {
		return answerDetailsDao.getByAnswerid(answer_id);
	}

}

