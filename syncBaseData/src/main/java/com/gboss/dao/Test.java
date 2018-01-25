package com.gboss.dao;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

public class Test {

	/**
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		
//		LinkMan man = new LinkMan();
//		man.setAppsign(1);
//		man.setCustomer_id(1234l);
//		man.setLinkman("张三");
//		man.setLinkman_id(123456l);
//		man.setLinkman_type(10);
//		man.setPhone("18711083009");
//		man.setTitle("测试标题");
//		man.setVehicle_id(6543l);
//		
//		Class<? extends LinkMan> clazz = man.getClass();
//		
//		Method[] ms = clazz.getDeclaredMethods();
//		Table table = clazz.getAnnotation(Table.class);
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ");
//		sql.append(table.name());
//		sql.append("(");
//		StringBuffer sqlargs = new StringBuffer();
//		sqlargs.append("(");
//		for(Method method: ms){
//			Column column = method.getAnnotation(Column.class);
//			GeneratedValue gv = method.getAnnotation(GeneratedValue.class);
//			if(column==null || gv!=null )continue;
//			if("stamp".equals(column.name()))continue;
//			sql.append(column.name()).append(",");
//			String filedName = method.getName().substring(3, method.getName().length());
//			sqlargs.append(":").append(Character.toLowerCase(filedName.charAt(0))).append(filedName.substring(1)).append(",");
//		}
//		
//		String insertargssql = sqlargs.substring(0, sqlargs.lastIndexOf(","));
//		sqlargs.delete(0, sqlargs.length());
//		sqlargs.append(insertargssql).append(")");
//		
//		String insertsql = sql.substring(0, sql.lastIndexOf(","));
//		sql.delete(0, sql.length());
//		sql.append(insertsql).append(")").append(" values ");
//		sql.append(sqlargs);
//		System.out.println(sql);
		
		
		
		
	}

}
