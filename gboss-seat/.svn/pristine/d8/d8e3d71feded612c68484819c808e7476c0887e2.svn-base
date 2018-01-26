package cc.chinagps.seat.dao;

import java.util.Collection;
import java.util.List;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.table.ProductLibTable;
import cc.chinagps.seat.bean.table.SeatSegGroupTable;
import cc.chinagps.seat.bean.table.SeatSegPhonebookTable;

public interface KnowledegLibDao {

	/**
	 * 获取电话检索——群组列表
	 * @return
	 */
	List<SeatSegGroupTable> getTelSearchGroups();

	/**
	 * 获取电话检索——群组下挂电话簿
	 * @param groupId
	 * @return
	 */
	Collection<SeatSegPhonebookTable> getTelSearchPhoneBooks(int groupId);

	/**
	 * 电话检索——搜索群组
	 * @param param
	 * @return
	 */
	List<SeatSegPhonebookTable> getPhoneBooksInTelSearch(String param);

	/**
	 * 获取产品库总数
	 * @param param
	 * @return
	 */
	long getProductLibCount(String param);

	/**
	 * 获取产品库
	 * @param param 搜索字符串
	 * @param query
	 * @return
	 */
	List<ProductLibTable> getProductLibs(String param, ReportCommonQuery query);
	
	//修改
	public void save(SeatSegPhonebookTable sspt);
	
	public void del(Long[] phonebook_id);
	
	//修改或保存-产品
	public void addProduct(ProductLibTable plt);
	
	public ProductLibTable findProduct(int pid) ;
	
	public void delProducts(Integer[] pid) ;


}