package bhz.mq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * 实现MQ工厂
 * @author alienware
 *
 */
public class MQFactory {

	private static class SingletonHolder {
		static final MQFactory instance = new MQFactory();
	}
	
	public static MQFactory getInstance(){
		return SingletonHolder.instance;
	}
	
	/**
	 * 存放mq消费者对象
	 */
	private static Map<String, MQConsumer> consumers = new ConcurrentHashMap<String, MQConsumer>();

	/**
	 * 
	 * @param consumerId 消费端id
	 * @param groupName  消费端组名
	 * @param namesrvAddr 地址
	 * @param topic 
	 * @param tag
	 * @param mqListener
	 * @param options  消费端的配置参数
 	 * @return
	 */
	public MQConsumer createConsumer(String consumerId,
			String groupName,
			String namesrvAddr,
			String topic,
			String tag,
			MQListener mqListener,
			Map<String, String> options){
		//先判断consumer消费者当中是否已经存在消费者
		if(consumers.get(consumerId) != null) {
			return consumers.get(consumerId);
		}
		try {
			//创建Consumer实例、订阅、注册监听、配置参数、最后装入集合
			MQConsumer consumer = new MQConsumer(consumerId, groupName, namesrvAddr);
			consumer.subscribe(topic, tag);
			consumer.registerMessageListener(mqListener);
			//设置消费者其它参数
			/**Consumer 启动后，默认从什么位置开始消费:默认CONSUME_FROM_LAST_OFFSET*/
			String consumeFromWhere=options.get("consumeFromWhere");
			/**消费线程池最小数量:默认10*/
			String consumeThreadMin=options.get("consumeThreadMin");
			/**消费线程池最大数量:默认20*/
			String consumeThreadMax=options.get("consumeThreadMax");
			/**拉消息本地队列缓存消息最大数：默认1000*/
			String pullThresholdForQueue=options.get("pullThresholdForQueue");
			/**批量消费，一次消费多少条消息:默认1条*/
			String consumeMessageBatchMaxSize=options.get("consumeMessageBatchMaxSize");
			/**批量拉消息，一次最多拉多少条,默认32条*/
			String pullBatchSize=options.get("pullBatchSize");
			/**消息拉取线程每隔多久拉取一次,默认为0*/
			String pullInterval=options.get("pullInterval");
			
			if(StringUtils.isNotBlank(consumeFromWhere)){
				if(StringUtils.equals(consumeFromWhere, "CONSUME_FROM_LAST_OFFSET")){
					consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
				}else if(StringUtils.equals(consumeFromWhere, "CONSUME_FROM_FIRST_OFFSET")){
					consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
				}
			}		
			if(StringUtils.isNotBlank(consumeThreadMin)){
				consumer.setConsumeThreadMin(Integer.parseInt(consumeThreadMin));
			}
			if(StringUtils.isNotBlank(consumeThreadMax)){
				consumer.setConsumeThreadMax(Integer.parseInt(consumeThreadMax));
			}
			if(StringUtils.isNotBlank(pullThresholdForQueue)){
				consumer.setPullThresholdForQueue(Integer.parseInt(pullThresholdForQueue));
			}
			if(StringUtils.isNotBlank(consumeMessageBatchMaxSize)){
				consumer.setConsumeMessageBatchMaxSize(Integer.parseInt(consumeMessageBatchMaxSize));
			}
			if(StringUtils.isNotBlank(pullBatchSize)){
				consumer.setPullBatchSize(Integer.parseInt(pullBatchSize));
			}
			if(StringUtils.isNotBlank(pullInterval)){
				consumer.setPullInterval(Integer.parseInt(pullInterval));
			}
			consumers.put(consumerId, consumer);
			return consumer;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//停掉之后从map当中删除
	public void stopConsumer(String consumerId){
		if(consumers.get(consumerId)!=null){
			consumers.get(consumerId).shutdown();
			consumers.remove(consumerId);
		}
	}		
	
	//停掉map当中所有的消费者
	public void stopConsumers(){
		for(String consumerId:consumers.keySet()){
			consumers.get(consumerId).shutdown();
		}
		consumers.clear();
	}
			
}
