package cc.chinagps.seat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.ReportCommonQuery;
import cc.chinagps.seat.bean.table.ProductLibTable;
import cc.chinagps.seat.bean.table.SeatSegGroupTable;
import cc.chinagps.seat.bean.table.SeatSegPhonebookTable;
import cc.chinagps.seat.dao.KnowledegLibDao;

@Repository("nlDao")
public class KnowledgeLibDaoImpl extends BasicDao implements KnowledegLibDao {
	
	/**
	 * 获取电话检索——群组列表
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SeatSegGroupTable> getTelSearchGroups() {
		return getSession().getNamedQuery("SelectTelSearchGroup").list();
	}
	
	/**
	 * 获取电话检索——群组下挂电话簿
	 * @param groupId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<SeatSegPhonebookTable> getTelSearchPhoneBooks(int groupId) {
		
		Collection<SeatSegPhonebookTable> group = (Collection<SeatSegPhonebookTable>)getSession()
		.getNamedQuery("SelectTelSearchPhoneBooksByGroupId")
		.setParameter("groupId", groupId).list();
		for(SeatSegPhonebookTable sspt:group){
			if(sspt!=null)
			if (!Hibernate.isInitialized(sspt.getGroups())) {
				Hibernate.initialize(sspt.getGroups());
			}
		}
		return group;
	}
	
	/**
	 * 电话检索——搜索群组
	 * @param param
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SeatSegPhonebookTable> getPhoneBooksInTelSearch(String param) {
		return getSession().getNamedQuery("SelectTelSearchSearchPhoneBooks")
			.setParameter("param", "%" + param + "%").list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductLibTable> getProductLibs(String param, ReportCommonQuery query) {
		Session session = getSession();
		Query namedQuery = getProductLibNamedQuery(session, "SelectProductLib", param)
				.setFirstResult(query.getStart());
		if (query.getLength() > 0) {
			namedQuery.setMaxResults(query.getLength());
		}
		return namedQuery.list();
	}
	
	@Override
	public long getProductLibCount(String param) {
		Session session = getSession();
		Query namedQuery = getProductLibNamedQuery(session, "SelectProductLibCount", param);
		return (Long) namedQuery.uniqueResult();
	}
	
	private Query getProductLibNamedQuery(Session session, String queryName, String param) {
		Query namedQuery = session.getNamedQuery(queryName);
		if (param != null && param.trim().length() > 0) {
			StringBuilder queryString = new StringBuilder(namedQuery.getQueryString());
			queryString.append(" WHERE product.cname LIKE '%").append(param).append("%'");
			queryString.append(" OR unitType.type LIKE '%").append(param).append("%'");
			namedQuery = session.createQuery(queryString.toString());
		}
		return namedQuery;
	}

	@Override
	public void save(SeatSegPhonebookTable sspt) {
		getSession().saveOrUpdate(sspt);
	}

	@Override
	public void del(final Long[] phonebook_ids) {
		PreparedStatement ps = null;
		Connection connection = null;
		try {
			connection = SessionFactoryUtils.getDataSource(getSession().getSessionFactory()).getConnection();
			connection.setAutoCommit(false);
			String sqls[] = {" delete from t_seat_seg_group_user where phonebook_id = ? ",
							 " delete from t_seat_seg_phonebook  where phonebook_id = ? " };
			
			for (int i = 0; i < sqls.length; i++) {
				String sql = sqls[i];
				ps = connection.prepareStatement(sql);
				for(Long phonebook_id:phonebook_ids){
					ps.setLong(1, phonebook_id);
					ps.addBatch();
				}
				ps.executeBatch();
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (Exception e) {
			try {
				if(connection!=null) connection.rollback();
			} catch (SQLException e1) {}
		}finally{
			try {
				if(connection!=null) connection.close();
			} catch (SQLException e1) {}
		}
	}

	@Override
	public void addProduct(ProductLibTable plt) {
		getSession().saveOrUpdate(plt);
	}

	@Override
	public ProductLibTable findProduct(int pid) {
		return (ProductLibTable) getSession().get(ProductLibTable.class, pid);
	}

	@Override
	public void delProducts(Integer[] pids) {
		String hql 	= " delete from t_seat_seg_product where product_id IN ("+Arrays.toString(pids).replace("[", "").replace("]", "")+")  ";
		SQLQuery  q	= getSession().createSQLQuery(hql);
		q.executeUpdate();
	}
}
