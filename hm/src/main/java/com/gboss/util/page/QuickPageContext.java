package com.gboss.util.page;

import java.util.List;

/**
 * 动态分页实现.每次查询返回一页记录的情况下使用.
 *
 * @version 1.0=
 */
public class QuickPageContext<E> implements IPageContext<E>{
    private List<E> items;
    private int totalCount;//总记录数
    private int pageSize;  //每页显示记录数
    
    /**
     * 
     * @param totalCount
     * @param pageSize
     * @param items
     */
    public QuickPageContext(int totalCount, int pageSize, List<E> items) {
        this.totalCount = totalCount;
        this.pageSize = pageSize == 0 ? 10 : pageSize;
        if (items != null) {
            this.items = items;
        }
    }
    
    @Override
	public Page<E> getPage(int index) {
        Page<E> page = new Page<E>();
        page.setContext(this);
        int index2 = index > getPageCount() ? 1 : index;
        page.setHasNext(index2 < getPageCount());
        page.setHasPre(index2 > 1);
        page.setIndex(index2);
        page.setItems(items);
        return page;
    }

    /**
     * 计算总页数.
     * 
     * @return
     */
    @Override
	public int getPageCount() {
        int div = totalCount / pageSize;
        int result = (totalCount % pageSize == 0) ? div : div + 1;
        
        return result;
    }
    
    @Override
	public int getTotal() {
        return this.totalCount;
    }
    
    @Override
	public int getPageSize() {
        return this.pageSize;
    }
}
