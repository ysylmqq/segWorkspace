package cc.chinagps.gateway.mq.export;

public class MQItem {
	private String queueName;
	
	private Object data;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}