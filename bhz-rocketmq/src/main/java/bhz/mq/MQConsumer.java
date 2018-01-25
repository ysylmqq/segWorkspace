package bhz.mq;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
/**
 * 对MqConsumer消费端进行封装，继承默认的DefaultMQPushConsumer
 * consumerId是用来标识这个消费者唯一的ID
 *
 */
public class MQConsumer extends DefaultMQPushConsumer {

	private String consumerId;
	
	public MQConsumer(String consumerId, String groupName, String namesrvAddr){
		super(groupName);
		//设置mq的nameserver地址
		this.setNamesrvAddr(namesrvAddr);
		this.consumerId = consumerId;
	}

	/*
	 * 一个消费者，可以注册多个监听器
	 */
	public void registerMessageListener(List<MQListener> mqListeners){
		for (MQListener mqListener : mqListeners) {
			this.registerMessageListener(mqListeners);
		}
	}
	
	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	
	
	
	
}
