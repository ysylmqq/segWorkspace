/**
 * <br>项目名: test
 * <br>文件名: BaseTest.java
 * <br>Copyright 2015 恒昌互联网运营中心
 */
package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import bjsxt.main.GenerateData;
import bjsxt.mapper.DatCheckDataMapper;
import bjsxt.service.DatCheckDataService;

/** 
 * <br>类 名: BaseTest 
 * <br>描 述: 描述类完成的主要功能 
 * <br>作 者: bhz
 * <br>创 建： 2013年5月8日 
 * <br>版 本：v1.0.0 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */

//@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional(rollbackFor = Exception.class)
public class BaseTest {
	
	@Autowired
	private DatCheckDataService service;
	
	@Autowired
	private GenerateData generate;
	
	//@Test
//	public void testGetKey(){
//		System.out.println(this.service.generateKey());
//	}
	
	//@Test
	public void testGenerate() throws Exception{
		this.generate.batchAdd(10);
	}
	//@Test
	public void testUpdateSync(){
		this.service.updateSync("0000201509234DCA84D1A9008EC39BA3");
	}
	
	
}
