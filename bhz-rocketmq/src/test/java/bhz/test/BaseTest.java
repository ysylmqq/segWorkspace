package bhz.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import bhz.entity.Subscribe;
import bhz.listener.ApplicationFactory;
import bhz.mapper.SubscribeMapper;

/** 
 * <br>类 名: BaseTest 
 * <br>描 述: 描述类完成的主要功能 
 * <br>作 者: bhz
 * <br>创 建： 2013年5月8日 
 * <br>版 本：v1.0.0 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */

@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(rollbackFor = Exception.class)
public class BaseTest {
	
	private SubscribeMapper subscribeMapper;
	
	public SubscribeMapper getSubscribeMapper() {
		return subscribeMapper;
	}
	@Autowired
	public void setSubscribeMapper(SubscribeMapper subscribeMapper) {
		this.subscribeMapper = subscribeMapper;
	}
	@Test
	public void test1() throws Exception {
		System.out.println("执行1..");
		//Thread.sleep(100000000);
	}
	@Test
	public void test2() throws Exception {
		System.out.println("执行2..");
		//Thread.sleep(100000000);
	}	
	
	@Test
	public void insertSubscribe(){
		
		Subscribe s = new Subscribe();
		
		s.setProname("crm管理系统");
		s.setUrl("http://localhost:8080/test1.html");
		s.setTopic("topic1");
		s.setTag("tag1");
		s.setGro("g1");
		s.setBusinesskey("{id:\"1\",name:\"aaa\"}");
		
		/*s.setProname("oa管理系统");
		s.setUrl("http://localhost:8080/test2.html");
		s.setTopic("topic2");
		s.setTag("tag2");
		s.setGro("g2");	
		s.setBusinesskey("{id:\"2\",name:\"bbb\"}");
		*/
		s.setConsumefromwhere("CONSUME_FROM_LAST_OFFSET");
		s.setConsumethreadmin("10");
		s.setConsumethreadmax("20");
		s.setPullthresholdforqueue("1000");
		s.setConsumemessagebatchmaxsize("1");
		s.setPullbatchsize("32");
		s.setPullinterval("0");
		
		s.setStatus("1");
		s.setCreateTime(new Date());
		s.setUpdateTime(new Date());
		this.subscribeMapper.insert(s);
	}
	
	
	/**
	@Test
	public void findAll(){
		
		List<Subscribe> list = this.subscribeMapper.findAllSubscribe();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Subscribe subscribe = (Subscribe) iterator.next();
			System.out.println(subscribe.getProname());
		}
	}
	*/
	
	/*
	@Test
	public void findByPage(){
		PageBounds pageBounds = new PageBounds(1, 2); 
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("TOPIC", "topic");
		List<Subscribe> list = this.subscribeMapper.findByPage(map, pageBounds);
		for (Subscribe subscribe : list) {
			System.out.println(subscribe.getProname());
		}
	}
	*/
	

}
