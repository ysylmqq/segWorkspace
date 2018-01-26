package com.chinaGPS.gtmp.util.page;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PageSelect implements Serializable{
	private int page;
	private int rows;	//一页多少行，默认1行
	private int offset;	//查询开始行数:自动计算
	/*private int total; //总共多少条记录
        private List<T> rows;//该页数据
	public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }*/
	public int getOffset() {
	    offset=(page==0)?0:(page-1)*rows;
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getPage() {
	    return page;
	}
	public void setPage(int page) {
	    this.page = page;
	}
	public int getRows() {
	    return rows;
	}
	public void setRows(int rows) {
	    this.rows = rows;
	}
}