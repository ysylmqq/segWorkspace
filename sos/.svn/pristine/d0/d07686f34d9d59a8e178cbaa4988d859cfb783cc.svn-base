package com.gboss.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.CarTypeDao;
import com.gboss.pojo.Model;
import com.gboss.pojo.UnitType;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:CarTypeDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-3-25 下午5:19:48
 */
@Repository("carTypeDao")  
@Transactional 
public class CarTypeDaoImpl extends BaseDaoImpl implements CarTypeDao {

	@Override
	public List<Model> getCarTypeList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Model.class); 
		List<Model> typeList =  criteria.list();
		return typeList;
	}

	@Override
	public List<Model> getCarTypeList(Long series_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Model.class); 
		if(series_id!=null){
			criteria.add(Restrictions.eq("seriesId", series_id));
		}
		List<Model> typeList =  criteria.list();
		return typeList;
	}

	@Override
	public Page<Model> findCarType(PageSelect<Model> pageSelect) {
		String hql = "from Model "
				   + "where 1=1 ";
		Map filter = pageSelect.getFilter();
		Set<String> keys = filter.keySet();
		for(Iterator it=keys.iterator();it.hasNext();){
			String key = (String)it.next();
			 if (filter.get(key) instanceof Integer) {
				 Integer new_name = (Integer) filter.get(key);
				 hql += " and " + key + "=" + new_name ;
			}else if (filter.get(key) instanceof String) {
				String value = (String)filter.get(key);
				hql += " and " + key + " like '%" + value + "%' ";
			}
			
		}
		if(StringUtils.isNotBlank(pageSelect.getStart_date())){
			hql += " and stamp > '" + pageSelect.getStart_date() + "'";
		}
		if(StringUtils.isNotBlank(pageSelect.getEnd_date())){
			hql += " and stamp < '" + pageSelect.getEnd_date() + "'";
		}
		if(StringUtils.isNotBlank(pageSelect.getOrder())){
			hql += " order by " + pageSelect.getOrder();
			if(pageSelect.getIs_desc()){
				hql += " desc ";
			}else{
				hql += " asc ";
			}
		}
		List list = listAll(hql, pageSelect.getPageNo(), pageSelect.getPageSize());
		int count = countAll(hql);
		Page<Model> page = PageUtil.getPage(count, pageSelect.getPageNo(), list, pageSelect.getPageSize());
		return page;
	}

}

