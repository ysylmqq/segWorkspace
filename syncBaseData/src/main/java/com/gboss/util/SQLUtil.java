package com.gboss.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gboss.pojo.BaseEntity;
import com.gboss.pojo.LinkMan;

public class SQLUtil {
	
	/**
	 * 删除SQL
	 * @param model
	 * @return 只需要传一个参数就直接用的? 其他的需要传多个参数用的:
	 */
	
	public static <T extends BaseEntity> String createDeleteSQL(Class<T> entityClass){
		Class<? extends BaseEntity> clazz = entityClass ;
		Method[] ms = clazz.getDeclaredMethods();
		Table table = clazz.getAnnotation(Table.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM ");
		sql.append(table.name());
		
		for(Method method: ms){
			Id id = method.getAnnotation(Id.class);
			if(id!=null){
				Column column = method.getAnnotation(Column.class);
//				sql.append(" where  ").append(column.name()).append("=:pkid").append(getClassFiled(method.getName()));
				sql.append(" where  ").append(column.name()).append("=?");
				break;
			}
		}
//		System.out.println(model.getClass().getName() + ",sql=" + sql);
		return sql.toString();
	}
	
	public static <T extends BaseEntity>  String createQueryByIdSQL(Class<T> entityClass){
		Class<? extends BaseEntity> clazz = entityClass;
		Table table = clazz.getAnnotation(Table.class);
		Method[] ms = clazz.getDeclaredMethods();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ");
		sql.append(table.name()); 
		
		for(Method method: ms){
			GeneratedValue gv = method.getAnnotation(GeneratedValue.class);
			if(gv!=null){
				Column column = method.getAnnotation(Column.class);
				sql.append(" where  ").append(column.name()).append("=:").append(getClassFiled(method.getName()));
				break;
			}
		}
//		System.out.println(model.getClass().getName() + ",sql=" + sql);
		return sql.toString();
	}
	
	public static String getClassFiled(String input){
		if(input!=null && !"".equals(input)){
			String temp = input.substring(3, input.length());
			return String.valueOf(Character.toLowerCase(temp.charAt(0))).concat(temp.substring(1));
		}
		return null;
	}
	
	public  static <T extends BaseEntity, PK extends Serializable> T setPrimaryKey(T t,PK id){
		Class<? extends BaseEntity> clazz = t.getClass();
		Method[] ms = clazz.getDeclaredMethods();
		for(Method method: ms){
			Column column = method.getAnnotation(Column.class);
			if(column==null)continue;
			if("stamp".equals(column.name()))continue;
			Id gv = method.getAnnotation(Id.class);
			if(gv!=null){
				try {
					Method setMethod = clazz.getMethod("set" + method.getName().substring(3),id.getClass());  
					setMethod.invoke(t,new Object[]{id});
					return t;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public  static <T extends BaseEntity, PK extends Serializable> PK getIdVal(T t){
		Class<? extends BaseEntity> clazz = t.getClass();
		Method[] ms = clazz.getDeclaredMethods();
		for(Method method: ms){
			Column column = method.getAnnotation(Column.class);
			if(column==null)continue;
			Id gv = method.getAnnotation(Id.class);
			if(gv!=null){
				try {
					Method getMethod = clazz.getMethod("get" + method.getName().substring(3));  
					return ((PK) getMethod.invoke(t));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param man
	 * @return
	 */
	public static <T extends BaseEntity> String createUpdateSQL(Class<T> entityClass) {
		Class<? extends BaseEntity> clazz = entityClass;
		Method[] ms = clazz.getDeclaredMethods();
		Table table = clazz.getAnnotation(Table.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" update ");
		sql.append(table.name());
		sql.append(" set ");
		StringBuffer wheresql = new StringBuffer();
		for(Method method: ms){
			Column column = method.getAnnotation(Column.class);
			if(column==null)continue;
			if("stamp".equals(column.name()))continue;
			String paramName = getClassFiled(method.getName());
			Id gv = method.getAnnotation(Id.class);
			String name = column.name();
			if(gv!=null){
				wheresql.append(" where  " ).append(name).append("=:").append(paramName);
				continue;
			}
			sql.append(" ").append(name).append("=:").append(paramName).append(" ").append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(wheresql);
//		System.out.println(model.getClass().getName() + ",sql=" + sql);
		return sql.toString();
	}
	
	/**
	 * 创建插入SQL，其中主键和stamp字段自动过滤掉
	 * @param man
	 * @return
	 */
	public static <T extends BaseEntity> String createInsertSQL(Class<T> entityClass) {
		Class<? extends BaseEntity> clazz = entityClass;
		Method[] ms = clazz.getDeclaredMethods();
		Table table = clazz.getAnnotation(Table.class);
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ");
		sql.append(table.name());
		sql.append("(");
		StringBuffer sqlargs = new StringBuffer();
		sqlargs.append("(");
		for(Method method: ms){
			Column column = method.getAnnotation(Column.class);
			Id gv = method.getAnnotation(Id.class);
			if(column==null || gv!=null )continue;
			if("stamp".equals(column.name()))continue;
			sql.append(" ").append(column.name()).append(" ").append(",");
			String paramName = getClassFiled(method.getName());
			sqlargs.append(":").append(paramName).append(",");
		}
		sqlargs.deleteCharAt(sqlargs.length() - 1);
		sqlargs.append(")");
		sql.deleteCharAt(sql.length() - 1).append(")").append(" values ");
		sql.append(sqlargs);
		
//		System.out.println(model.getClass().getName() + ",sql=" + sql);
		return sql.toString();
	}
	
	public static void main(String[] args) {
		LinkMan man= new LinkMan();
//		System.out.println(SQLUtil.createInsertSQL(LinkMan.class));
//		System.out.println(SQLUtil.createUpdateSQL(LinkMan.class));
		System.out.println(SQLUtil.createDeleteSQL(LinkMan.class));
//		System.out.println(SQLUtil.createUpdateSQL(Customer.class));
//		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
//		CustomerService customerService = application.getBean("CustomerService", CustomerService.class);
//		Customer customer = new Customer();
//		// 分公司代码 201 海马
//		customer.setSubco_no(SystemConst.HM_SUBCO_NO);//
//		customer.setSubco_code("");
//		customer.setCust_type(0);// 客户类型, 0=私家车客户, 1=集团客户, 2=担保公司
//		customer.setCustomer_name("张三");
//		customer.setAddress("测试地址");
//		customer.setCustco_code("");// 'LDAP客户子机构代码, // 用于集团客户车辆的归属关系, // 无填''0'', 每级2位字符',
//		customer.setCustco_no(0L);// LDAP客户根节点ID, 集团客户有效 ;  默认给什么值 不能为空
//		customer.setVip(1);//
//		customer.setIdtype(1);
//		customer.setSubco_name(SystemConst.HM_SUBCO_NAME);
//		customer.setSex(1);
//		customer.setEmail("123@163.com");
//		customer.setId_no("4222123199012252145");
//		customer.setTrade(0);// '所属行业(网上查车行业版本), 系统值2040, 0=私家车, 1=物流车, 2=出租车,3=混凝土',
//		customer.setFlag(1);// '标志, 0=未审核, 1=审核通过, 2=删除',
//		customer.setPay_model(0);// 预留, 付费方式, 冗余, 系统值3050, 0=托收, 1=现金, 2=转账, 3=刷卡
//		customer.setOp_id(1L);// 操作员ID
//		customer.setCustomer_id(customerService.save(customer));
//		System.out.println("结束!");
		man = SQLUtil.setPrimaryKey(man, 1234l);
		System.out.println(SQLUtil.getIdVal(man));
	}
	
}
