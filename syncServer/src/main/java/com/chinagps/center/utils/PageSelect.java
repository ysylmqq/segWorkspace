package com.chinagps.center.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tx 后台数据库用来分页的封装类
 * 
 */

public class PageSelect<E> {
	// 第几页
	private int pageNo = 1;
	// 每页记录数
	private int pageSize;
	// 开始记录位置
	private int offset;
	// 条件过滤
	private Map filter;

	private List<E> items;// 当前页包含的记录列表

	private String searchValue;

	private String userId;
	
	private String start_date;
	
	private String end_date;

	// 排序
	private String order;
	
	private boolean is_desc;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Map getFilter() {
		if(filter == null){
			return new HashMap();
		}
		return filter;
	}

	public void setFilter(Map filter) {
		this.filter = filter;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public boolean getIs_desc() {
		return is_desc;
	}

	public void setIs_desc(boolean is_desc) {
		this.is_desc = is_desc;
	}
	
}
