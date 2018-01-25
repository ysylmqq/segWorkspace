package com.gboss.service;

import java.util.List;

import com.gboss.dao.CustphoneDao;
import com.gboss.pojo.Linkman;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.service
 * @ClassName:CustphoneService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午5:26:41
 */
public interface CustphoneService extends BaseService {
	
	/**
	 * 添加联系电话
	 * @param custphone
	 */
	public void add(Linkman custphone);
	
	/**
	 * 通过客户id获取客户联系电话列表
	 * @param cust_id
	 * @return
	 */
	public List<Linkman> listCustphone(Long cust_id);
	
	/**
	 * 删除客户联系电话
	 * @param cust_id
	 */
	public void deleteByCust_id(Long cust_id);

	/**
	 * 通过客户id获取客户联系人列表
	 * @param customer_id
	 * @return
	 */
	public List<Linkman> getLinkmanList(Long customer_id);
	
	/**
	 * 分页查询联系人
	 * @param pageSelect
	 * @return
	 */
	public Page<Linkman> findLinkman(PageSelect<Linkman> pageSelect);
}

