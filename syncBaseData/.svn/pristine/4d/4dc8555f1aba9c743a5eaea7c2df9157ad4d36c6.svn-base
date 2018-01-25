package com.gboss.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.BrandDao;
import com.gboss.pojo.Brand;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:BrandDaoImpl
 * @date:2014-5-9 下午2:49:57
 */
@Repository("BrandDao")  
public class BrandDaoImpl extends BaseDaoImpl implements BrandDao {

	public List<Brand> getBrandList() {
		List<Brand>  brandList = jdbcTemplate.query("select * from t_ba_brand t where t.is_valid = 1",new BeanPropertyRowMapper<Brand>(Brand.class));
		return brandList;
	}

	
	public Boolean isExist(String name) {
		List<Brand>  brandList = jdbcTemplate.query("select * from t_ba_brand t where t.is_valid = 1 and name ='" +name+ "'",new BeanPropertyRowMapper<Brand>(Brand.class));
		if(brandList.size() > 0){
			return true;
		}
		return false;
	}

	
	 
	public int delete(Long id) throws SystemException {
		return jdbcTemplate.update("update Brand b set b.is_valid=0 where b.id= ?", id);
	}

	
	public Boolean isExist(String name, Long id) {
		List<Brand>  brandList = jdbcTemplate.query("select * from t_ba_brand t where t.is_valid = 1 AND name ='" +name+ "' and id<>" + id,new BeanPropertyRowMapper<Brand>(Brand.class));
		if(brandList.size() > 0){
			return true;
		}
		return false;
	}

	
	public List<Brand> serachBrandList(String keyword) {
		StringBuffer hqlSb=new StringBuffer();
		hqlSb.append(" SELECT * from t_ba_brand as br ");
		hqlSb.append(" where br.id in ( SELECT DISTINCT b.id FROM t_ba_brand b,Series s,t_ba_model m WHERE ");
		hqlSb.append(" b.name like '%").append(keyword).append("%'");
		hqlSb.append(" OR ( ");
		hqlSb.append(" s.name like '%").append(keyword).append("%'");
		hqlSb.append(" AND s.brand_id = b.id ) ");
		hqlSb.append(" OR ( ");
		hqlSb.append(" m.name  like '%").append(keyword).append("%'");
		hqlSb.append(" AND m.seriesId = s.id  AND s.brand_id = b.id ) ) ");
		List<Brand> list = jdbcTemplate.query(hqlSb.toString(), new BeanPropertyRowMapper< Brand>(Brand.class));
		return list;
	}

	
	public Brand getBrandName(String name) {
		String sql = "SELECT * FROM t_ba_brand t where t.brand_name = '"+name+"'";
		List<Brand> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Brand>(Brand.class));
		if(list.size() > 0){
			return (Brand) list.get(0);
		}
		return null;
	}

}

