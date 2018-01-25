package cc.chinagps.gps.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import cc.chinagps.gps.util.SqlSessionFactoryUtil;

public class CommonService {
	public SqlSession getSqlSession(boolean needTransaction) {
		SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionInstance();
		return needTransaction ? sqlSessionFactory.openSession(false) : sqlSessionFactory.openSession();
	}
	
	public Object getOperatorDao(SqlSession session, Class clazz){
		return session.getMapper(clazz);
	}
}
