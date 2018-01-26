package cc.chinagps.seat.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import cc.chinagps.seat.bean.QVehicleUnit;
import cc.chinagps.seat.util.HibernateUtil;

public class VehicleDao {
	private static VehicleDao instance = new VehicleDao();
	
	private VehicleDao(){
		
	}
	
	public static VehicleDao getInstance(){
		return instance;
	}
	
	public List<QVehicleUnit> queryVehicleUnit(String plate_or_call, int limit){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u.call_letter,v.plate_no,u.unit_id,v.vehicle_id");
		sb.append(" FROM t_ba_unit u,t_ba_vehicle v");
		sb.append(" WHERE u.vehicle_id=v.vehicle_id ");
		sb.append(" AND (u.call_letter LIKE ? OR v.plate_no LIKE ?)");
		sb.append(" LIMIT 0,").append(limit);
		
		Session session = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			Query q = session.createSQLQuery(sb.toString());
			q.setString(0, "%" + plate_or_call + "%");
			q.setString(1, "%" + plate_or_call + "%");
			q.setResultTransformer(Transformers.aliasToBean(QVehicleUnit.class));
			
			@SuppressWarnings("unchecked")
			List<QVehicleUnit> list = q.list();
			return list;
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
}
