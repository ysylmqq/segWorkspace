package com.gboss.dao;

import java.util.List;

import com.gboss.pojo.Linkman;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:CustphoneDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午5:15:25
 */
public interface CustphoneDao extends BaseDao {
	
	public List<Linkman> listCustphone(Long cust_id);
	
	public void deleteByCust_id(Long cust_id);
	
	public List<Linkman> getLinkmanList(Long customer_id);
	
	public Page<Linkman> findLinkman(PageSelect<Linkman> pageSelect);

}

