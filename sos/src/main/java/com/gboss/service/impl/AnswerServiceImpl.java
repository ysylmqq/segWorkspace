package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.AnswerDao;
import com.gboss.pojo.Answer;
import com.gboss.pojo.AnswerDetails;
import com.gboss.service.AnswerService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:AnswerServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 下午4:00:27
 */
@Service("answerService")
@Transactional
public class AnswerServiceImpl extends BaseServiceImpl implements AnswerService {
	
	@Autowired  
	@Qualifier("answerDao")
	private AnswerDao answerDao;

	@Override
	public HashMap<String, Object> addAnswer(Answer answer)
			throws SystemException {
		HashMap<String, Object> result=new HashMap<String, Object>();
		boolean flag=true;
		String msg=SystemConst.OP_SUCCESS;
		answerDao.save(answer);
		Long id = answer.getId();
		List<AnswerDetails> answerDetails = answer.getAnswerDetails();
		if(null != answerDetails && answerDetails.size() > 0){
			for (AnswerDetails details : answerDetails) {
				details.setAnswer_id(id);
				answerDao.save(details);
			}
		}
		result.put(SystemConst.SUCCESS, flag);
		result.put(SystemConst.MSG, msg);
		return result;
	}

	@Override
	public Page<HashMap<String, Object>> findAnswer(
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=answerDao.countAnswers(pageSelect.getFilter());
		List<HashMap<String, Object>> list=answerDao.findAnswers(pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

}

