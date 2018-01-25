package bhz.mq;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.message.MessageExt;
/**
 *由于该接口实现的是MessageListenerOrderly这个接口
 *所以队列当中的数据是顺序消费的
 *也可以实现MessageListenerConcurrently这个接口
 *
 *
 */
class MQListener implements MessageListenerConcurrently { // MessageListenerOrderly

	//传入业务参数
	private String id;
	private String proName;
	private String url;
	private String businessKey;
	public MQListener(String id, String proName, String url, String businessKey){
		this.id = id;
		this.proName = proName;
		this.url = url;
		this.businessKey = businessKey;
	}
	
	/*@Override
	public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
		try {
			for(MessageExt msg : msgs){
				String topic = msg.getTopic();
				String msgBody = new String(msg.getBody(),"utf-8");
				String tags = msg.getTags();
				System.out.println("收到消息：" + "  topic :" + topic + "  ,tags : " + tags + " ,msg : " + msgBody);
				System.out.println("-------------------------------");
				//String orignMsgId = msg.getProperties().get(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID);
			
				// 1 使用httpClient - 发送请求 到指定的系统中 (url)
				
				//if  response ( return ConsumeConcurrentlyStatus.RECONSUME_LATER;)
				//...日志
				
		}
		} catch (Exception e) {	
			e.printStackTrace();
			return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
		}	
		return ConsumeOrderlyStatus.SUCCESS;
	}*/

	
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		try {
			for(MessageExt msg : msgs){
				String topic = msg.getTopic();
				String msgBody = new String(msg.getBody(),"utf-8");
				String tags = msg.getTags();
				System.out.println("收到消息：" + "  topic :" + topic + "  ,tags : " + tags + " ,msg : " + msgBody);
				System.out.println("-------------------------------");
				//String orignMsgId = msg.getProperties().get(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID);
			
				// 1 使用httpClient - 发送请求 到指定的系统中 (url)
				
				//if  response ( return ConsumeConcurrentlyStatus.RECONSUME_LATER;)
				//...日志
				
			}
		} catch (Exception e) {	
			e.printStackTrace();
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}			
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
