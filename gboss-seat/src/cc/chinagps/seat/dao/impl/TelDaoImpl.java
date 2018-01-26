package cc.chinagps.seat.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import cc.chinagps.seat.bean.table.BriefDetailTable;
import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.dao.TelDao;

@Repository("telDao")
public class TelDaoImpl extends BasicDao implements TelDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BriefTable> getBriefList(BigInteger vehicleId,String phone, int count) {									
		String sql = " SELECT t.b_id ,t.vehicle_id,t.type , t.content ,t.result,t.phone,"
			+ "t.op_id,t.op_name,t.stamp,t.p_st_id FROM t_seat_brief t where  1=1 ";
		
		if( phone != null && !"".equals(phone) ){
			sql = sql.concat(" AND t.phone like '%").concat(phone).concat("%' ");
		}
		
		if(vehicleId != null && vehicleId.intValue() != 0 ){
			sql = sql.concat(" AND t.vehicle_id =").concat(String.valueOf(vehicleId.intValue()));
		}
		sql = sql.concat(" ORDER BY UNIX_TIMESTAMP(t.stamp) DESC ");
		
		return getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(BriefTable.class)).setMaxResults(count).list();
	}
	
	@Override
	public long saveBrief(BriefTable table) {
		String content = table.getContent();
		BigInteger b_id = (BigInteger) getWriteSession().save(table);
		if(content!=null && !"".equals(content)){
			String[] stids = table.getContent().split(",");
			for(String stid: stids){
				if(stid!=null && !"".equals(stid)){
					BriefDetailTable detail = new BriefDetailTable(b_id,Integer.parseInt(stid),new Date());
					getWriteSession().save(detail);
				}
			}
		}
		return b_id.longValue();
	}
	
	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-dao.xml"});
//		System.out.println(context);
		TelDao telDao = context.getBean("telDao", TelDao.class);
//		System.out.println(telDao.getBriefList(BigInteger.valueOf(1L), 1));
	}

}
