package com.chinaGPS.gtmp.util.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 * 必须传入当前页数pageNo，总数据条数total
 * getPages() 返回总页数
 * getStartNum() 返回查询开始行数
 * getEndNum() 返回查询结束行数
 * 
 * @author xk
 */
public class Page implements Serializable{

	private int pageNo=0; 	//当前页数，默认第一页
	private int pageSize=10;	//一页多少行，默认10行
	private int total;		//总共有多少数据
	private int pages;		//总页数
	private int startNum;	//查询开始行数
	private int endNum;		//查询结束行数
    private List<?> resultList;//该页数据
	public int getPageNo() {
		if (total < pageNo * pageSize) {	//如果少于一页，显示为0
			pageNo = 0;
		}
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<?> getResultList() {
		return resultList;
	}
	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}
	
	//返回总页数
	public int getPages() {
		pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		return pages;
	}
	//返回查询开始行数
	public int getStartNum(){
		startNum = pageNo*pageSize;
		return startNum;
	}
	//返回查询结束行数
	public int getEndNum(){
		endNum = pageNo*pageSize+pageSize;
		return endNum;
	}
}
