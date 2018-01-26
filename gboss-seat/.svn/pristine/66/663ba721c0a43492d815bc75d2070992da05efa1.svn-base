package cc.chinagps.seat.service;

import java.util.Collection;
import java.util.List;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.ReportCommonResponse;
import cc.chinagps.seat.bean.table.ProductLibTable;
import cc.chinagps.seat.bean.table.SeatSegGroupTable;
import cc.chinagps.seat.bean.table.SeatSegPhonebookTable;

public interface KnowledgeLibService {

	List<SeatSegGroupTable> getTelSearchGroups();

	Collection<SeatSegPhonebookTable> getTelSearchPhoneBooks(int groupId);

	List<SeatSegPhonebookTable> searchPhoneBooksInTelSearch(String param);
	
	//修改或保存-电话编组
	public void save(SeatSegPhonebookTable sspt,String groupid);
	
	//修改或保存-产品
	public void addProduct(ProductLibTable plt);
	
	/**
	 * 根据id查询产品ID
	 * @param pid
	 * @return
	 */
	public ProductLibTable findProduct(int pid);
	
	public void del(Long[] phonebook_ids);

	ReportCommonResponse getProductLibCommonResp(String param,
			ReportCommonQuery query);

	List<ProductLibTable> getProductLibs(String param, ReportCommonQuery query);
	
	public void delProducts(Integer[] pid) ;

}