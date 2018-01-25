package bhz.mq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bhz.entity.Subscribe;
import bhz.service.SubscribeService;

import com.alibaba.rocketmq.client.exception.MQClientException;

@Component("mqReloadJob")
@PropertySource("classpath:rocketmq.properties")
public class MQReloadJob {

	@Autowired
	private Environment environment;
	//创建map容器
	private static Map<String /*consumerId*/, String /*updateTime*/> updateMQSubscribeMap = new ConcurrentHashMap<String, String>();
	
	private SubscribeService subscribeService;
	
	public SubscribeService getSubscribeService() {
		return subscribeService;
	}
	
	@Autowired
	public void setSubscribeService(SubscribeService subscribeService) {
		this.subscribeService = subscribeService;
	}

	@Scheduled(initialDelay=1000, fixedDelay=30000)
	public void reloadJob(){
		System.err.println("reloadJob");
		List<Subscribe> list = this.subscribeService.findAllSubscribe();
		for (Subscribe subscribe : list) {
			int id = subscribe.getId();
			String proName = subscribe.getProname();
			//consumerId 自定义规则
			String consumerId = proName + "_" + id ;
			String url = subscribe.getUrl();
			String topic = subscribe.getTopic();
			String tag = subscribe.getTag();
			String gro = subscribe.getGro();
			String businessKey = subscribe.getBusinesskey();
			String status = subscribe.getStatus();
			String updateTime = DateFormatUtils.format(subscribe.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
			System.out.println(updateTime);
			//配置参数：
			
			Map<String, String> options = new HashMap<String, String>();
			String consumeFromWhere = subscribe.getConsumefromwhere();
			options.put("consumeFromWhere", consumeFromWhere);
			String consumeThreadMin = subscribe.getConsumethreadmin();
			options.put("consumeThreadMin", consumeThreadMin);
			String consumeThreadMax = subscribe.getConsumethreadmax();
			options.put("consumeThreadMax", consumeThreadMax);
			String pullThresholdForQueue = subscribe.getPullthresholdforqueue();
			options.put("pullThresholdForQueue", pullThresholdForQueue);
			String consumeMessageBatchMaxSize = subscribe.getConsumemessagebatchmaxsize();
			options.put("consumeMessageBatchMaxSize", consumeMessageBatchMaxSize);
			String pullBatchSize = subscribe.getPullbatchsize();
			options.put("pullBatchSize", pullBatchSize);
			String pullInterval = subscribe.getPullinterval();
			options.put("pullInterval", pullInterval);
			
			String mqUpdateTime = updateMQSubscribeMap.get(consumerId);
			//如果对比结果为null 或者是俩者不相等
			if(mqUpdateTime == null || !mqUpdateTime.equals(updateTime)){
				//如果两者不相等并且其状态为status=0 则证明这consumer关系已经停用
				if(!updateTime.equals(mqUpdateTime) && status.equals("0")){
					//停止服务
					MQFactory.getInstance().stopConsumer(consumerId);
				} else {
					//停止服务
					MQFactory.getInstance().stopConsumer(consumerId);
					//创建Consumer
					MQListener mqListener = new MQListener(consumerId, proName, url, businessKey);
					//传递参数
					MQConsumer consumer = MQFactory.getInstance().createConsumer(consumerId, 
							gro, 
							this.environment.getProperty("rocketmq.nameservers"),
							topic, 
							tag, 
							mqListener, 
							options);
					try {
						//启动consumer
						consumer.start();
					} catch (MQClientException e) {
						e.printStackTrace();
					}
					//存储已经存在的订阅关系
					updateMQSubscribeMap.put(consumerId, updateTime);
					System.out.println("end..");
				}
			} else ;
		}
	}
	
}
