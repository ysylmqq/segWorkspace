package com.gboss.service;

import java.util.HashMap;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Answer;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:AnswerService
 * @Description:TODO
 * @author:bzhang
 * @date:2014-4-28 下午3:59:50
 */
public interface AnswerService extends BaseService {
	
	public HashMap<String, Object> addAnswer(Answer answer) throws SystemException;
	
	public Page<HashMap<String, Object>> findAnswer(PageSelect<Map<String, Object>> pageSelect) throws SystemException;

}

