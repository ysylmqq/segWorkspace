package com.chinagps.driverbook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chinagps.driverbook.dao.NewsMapper;
import com.chinagps.driverbook.dao.NewsRecordMapper;
import com.chinagps.driverbook.pojo.News;
import com.chinagps.driverbook.pojo.NewsRecord;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.service.INewsService;
import com.chinagps.driverbook.util.pagination.Page;

@Service
@Scope("prototype")
public class NewsServiceImpl extends BaseServiceImpl<News> implements INewsService {

	@Autowired
	private NewsMapper newsMapper;
	@Autowired
	private NewsRecordMapper newsRecordMapper;
	
	@Override
	public News findById(Long id) throws Exception {
		return newsMapper.findById(id);
	}
	
	public ReturnValue listNews(Map<String, String> params, ReturnValue rv) throws Exception {
		Page page = new Page();
		List<News> newsList = null;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		if (params.get("pageNum") == null) {
			page.setPageNum(1);
		} else {
			page.setPageNum(Integer.parseInt(params.get("pageNum")));
		}
		if (params.get("numPerPage") == null) {
			page.setNumPerPage(20);
		} else {
			page.setNumPerPage(Integer.parseInt(params.get("numPerPage")));
		}
		if (params.get("sg") == null || "1".equals(params.get("sg"))) {
			int count = newsMapper.saigeCount(params);
			page.setTotalCount(count);
			newsList = newsMapper.saigeNews(params, new RowBounds(page.getOffset(), page.getNumPerPage()));
		} else {
			int count = newsMapper.notSaigeCount(params);
			page.setTotalCount(count);
			newsList = newsMapper.notSaigeNews(params, new RowBounds(page.getOffset(), page.getNumPerPage()));
		}
		resultMap.put("page", page);
		resultMap.put("news", newsList);
		rv.setDatas(resultMap);
		return rv;
	}
	
	public int addRecord(NewsRecord newsRecord) throws Exception {
		if (newsRecordMapper.find(newsRecord) == null) {
			return newsRecordMapper.add(newsRecord);
		}
		return 0;
	}

}
