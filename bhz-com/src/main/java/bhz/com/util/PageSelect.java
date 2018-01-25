package bhz.com.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tx 后台数据库用来分页的封装类
 * 
 */

public class PageSelect<E> implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 第几页
	private int pageNo = 1;
	// 每页记录数
	private int pageSize = 10;
	// 开始记录位置
	private int offset;
	// 条件过滤
	private Map filter;

	private List<E> items;// 当前页包含的记录列表

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

	public boolean getIs_desc() {
		return is_desc;
	}

	public void setIs_desc(boolean is_desc) {
		this.is_desc = is_desc;
	}

	public List<E> getItems() {
		return items;
	}

	public void setItems(List<E> items) {
		this.items = items;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
