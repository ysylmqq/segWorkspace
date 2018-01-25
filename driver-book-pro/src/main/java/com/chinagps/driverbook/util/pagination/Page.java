package com.chinagps.driverbook.util.pagination;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Page implements Serializable {
	private static final long serialVersionUID = 7431299951069849525L;

	/** 当前页数 */
	private int pageNum = 1;
	/** 每页多少条 */
	private int numPerPage = 20;
	/** 偏移多少条 */
	private int offset;
	/** 总记录数 */
	private int totalCount;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getOffset() {
		offset = (pageNum == 0) ? 0 : (pageNum - 1) * numPerPage;
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}