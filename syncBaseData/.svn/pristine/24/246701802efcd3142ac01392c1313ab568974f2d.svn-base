package com.gboss.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gboss.comm.SystemException;
import com.gboss.dao.ComboDao;
import com.gboss.pojo.Combo;
import com.gboss.util.StringUtils;

/**
 * @Package:com.gboss.dao.impl
 * @ClassName:ComboDaoImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-18 下午2:56:42
 */
@Repository("comboDao")
public class ComboDaoImpl extends BaseDaoImpl implements ComboDao {

	public boolean isExist(Combo combo) throws SystemException {
		String sql = "  select count(s.combo_id) from t_fee_sim_combo as s  where 1=1  ";
		if (combo != null) {
			if (StringUtils.isNotBlank(combo.getCombo_name())) {
				sql += " and s.combo_name='" + combo.getCombo_name() + "'";
			}
			if (combo.getCombo_id() != null) {
				sql += " and s.combo_id<>= " + combo.getCombo_id();
			}
		}
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Map<String, Object>> findComboes(Long conpanyId, Map<String, Object> conditionMap, String order, boolean isDesc, int pn, int pageSize) throws SystemException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select c.combo_id as id , c.combo_code ,c.combo_name,c.month_fee,c.data,c.voice_time,c.sim_type ");
		sb = getConditionHql(conpanyId, sb, conditionMap);
		sb.append(" order by c.combo_id desc");
		String sql = buildPageSql(sb.toString(),pn,pageSize);
		return jdbcTemplate.queryForList(sql);
	}

	private StringBuffer getConditionHql(Long conpanyId, StringBuffer sb, Map conditionMap) {
		sb.append(" FROM t_fee_sim_combo c where c.flag = 1 ");
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("name"))) {
			sb.append(" and c.combo_name like '%").append(conditionMap.get("name")).append("%'");
		}
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("type"))) {
			sb.append(" and c.sim_type=").append(conditionMap.get("type"));
		}
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("telco"))) {
			sb.append(" and c.telco=").append(conditionMap.get("telco"));
		}
		if (StringUtils.isNotNullOrEmpty(conditionMap.get("code"))) {
			sb.append(" and c.combo_code='").append(conditionMap.get("code")).append("'");
		}
		// sb.append(" and c.subco_no=").append(conpanyId);
		return sb;
	}

	public int countComboes(Long conpanyId, Map<String, Object> conditionMap) throws SystemException {
		return 0 ;
	}

	public int delete(List<Long> ids) throws SystemException {

		String sql = " update t_fee_sim_combo set flag = 9 where combo_id = ? ";

		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (Long id : ids) {
			Object[] objects = new Object[] { id };
			batchArgs.add(objects);
		}

		int count = 0;
		for (int i : jdbcTemplate.batchUpdate(sql, batchArgs)) {
			count += i;
		}
		return count;
	}

	public Combo getComboByname(String name) throws SystemException {
		String sql = "select * from t_fee_sim_combo t where 1=1 ";
		sql += " and combo_name='" + name + "'";
		List<Combo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Combo>(Combo.class));
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Combo getComboBySync_id(Long sync_id) {
		String sql = "select * from t_fee_sim_combo t where 1=1 ";
		sql += " and sync_id=" + sync_id;
		List<Combo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Combo>(Combo.class));
		if (list.size() > 0) {
			return  list.get(0);
		}
		return null;
	}

	public String buildPageSql(String _sql, int page, int pageSize) {
		String sql_str = null;
		String db_type = "0";
		if (db_type.equals("1")) {
			db_type = "mysql";
		} else {
			db_type = "oracle";
		}

		if (db_type.equals("mysql")) {
			sql_str = _sql + " LIMIT " + (page - 1) * pageSize + "," + pageSize;
		} else {
			StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT t1.*,rownum sn1 FROM (");
			sql.append(_sql);
			sql.append(") t1) t2 WHERE t2.sn1 BETWEEN ");
			sql.append((page - 1) * pageSize + 1);
			sql.append(" AND ");
			sql.append(page * pageSize);
			sql_str = sql.toString();
		}
		return sql_str.toString();
	}

}
