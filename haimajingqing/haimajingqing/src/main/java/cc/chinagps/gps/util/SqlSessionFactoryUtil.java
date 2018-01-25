package cc.chinagps.gps.util;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class SqlSessionFactoryUtil {	
	private static SqlSessionFactoryBuilder sqlSessionFactoryBuilder;
	//每个MyBatis应用程序主要都是使用SqlSessionFactory实例的	
	private static SqlSessionFactory sqlSessionFactory;
	//用于打印log信息	
	private static final Logger log = Logger.getLogger(SqlSessionFactoryUtil.class);
	
	private static void init() {
		//在mybatis-config.xml中
		String resource = "mybatis-config.xml";
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			//SqlSessionFactoryBuilder可以从一个xml配置文件或者一个预定义的配置类的实例获得
			sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
			//一个SqlSessionFactory实例可以通过SqlSessionFactoryBuilder获得
			sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);
		} catch (IOException e) {
			log.error(null, e);
		}
	}
	
	public static SqlSessionFactory getSqlSessionInstance(){
		if (sqlSessionFactory == null) {
			init();
		}
		return sqlSessionFactory;
	}
}
