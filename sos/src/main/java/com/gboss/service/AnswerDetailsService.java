package com.gboss.service;

import java.util.List;

import com.gboss.pojo.AnswerDetails;

/**
 * @Package:com.gboss.service
 * @ClassName:AnswerDetailsService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-4-30 下午2:43:30
 */
public interface AnswerDetailsService extends BaseService {
	
	public List<AnswerDetails> getByAnswerid(Long answer_id);

}

