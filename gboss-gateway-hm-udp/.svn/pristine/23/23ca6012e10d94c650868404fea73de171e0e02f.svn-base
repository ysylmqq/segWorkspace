package cc.chinagps.gateway.mq;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

import cc.chinagps.gateway.util.Constants;
import cc.chinagps.gateway.util.SystemConfig;

public class MQManager {
	private static ActiveMQConnectionFactory factory;
	
	//队列定义
	public static String QUEUE_NAME_CMD;
	public static int QUEUE_TYPE_CMD;
	public static int QUEUE_MODE_CMD;	//not used
	
	public static String QUEUE_NAME_CMD_RESPONSE;
	public static int QUEUE_TYPE_CMD_RESPONSE;
	public static int QUEUE_MODE_CMD_RESPONSE;
	
	public static String QUEUE_NAME_SEND_CMD_RESULT;
	public static int QUEUE_TYPE_SEND_CMD_RESULT;
	public static int QUEUE_MODE_SEND_CMD_RESULT;
	
	public static String QUEUE_NAME_ALARM;
	public static int QUEUE_TYPE_ALARM;
	public static int QUEUE_MODE_ALARM;
	
	public static String QUEUE_NAME_GPS;
	public static int QUEUE_TYPE_GPS;
	public static int QUEUE_MODE_GPS;
	
	public static String QUEUE_NAME_SERVICE_DATA;
	public static int QUEUE_TYPE_SERVICE_DATA;
	public static int QUEUE_MODE_SERVICE_DATA;
	
	public static String QUEUE_NAME_SHORT_MESSAGE;
	public static int QUEUE_TYPE_SHORT_MESSAGE;
	public static int QUEUE_MODE_SHORT_MESSAGE;
	
	//车况信息(OBD)
	public static String QUEUE_NAME_OBD;
	public static int QUEUE_TYPE_OBD;
	public static int QUEUE_MODE_OBD;
	
	//行程信息(OBD)
	public static String QUEUE_NAME_TRAVEL;
	public static int QUEUE_TYPE_TRAVEL;
	public static int QUEUE_MODE_TRAVEL;
	
	//模块故障信息(OBD)
	public static String QUEUE_NAME_FAULT;
	public static int QUEUE_TYPE_FAULT;
	public static int QUEUE_MODE_FAULT;
	
	//登录退录信息
	public static String QUEUE_NAME_LOGINOUT;
	public static int QUEUE_TYPE_LOGINOUT;
	public static int QUEUE_MODE_LOGINOUT;
	
	//车台版本信息
	public static String QUEUE_NAME_UNIT_VERSION;
	public static int QUEUE_TYPE_UNIT_VERSION;
	public static int QUEUE_MODE_UNIT_VERSION;
	
	//电控单元配置信息
	public static String QUEUE_NAME_ECU_CONFIG;
	public static int QUEUE_TYPE_ECU_CONFIG;
	public static int QUEUE_MODE_ECU_CONFIG;
	
	//批量读写长度
	public static int BATCH_SIZE_WRITE;
	public static int BATCH_SIZE_READ;
	
	public static int READ_COMMIT_INTERVAL;
	
	//写缓存最大长度
	public static int MAX_CACHE_SIZE_WRITE = 50000;
	
	public static void init(){
		//Properties properties
		String url = SystemConfig.getMQProperty("url");
		QUEUE_NAME_CMD = SystemConfig.getMQProperty("queue_name_cmd") + Constants.SYSTEM_ID;
		QUEUE_TYPE_CMD = Integer.valueOf(SystemConfig.getMQProperty("queue_type_cmd"));
		QUEUE_MODE_CMD = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_cmd"));
		
		QUEUE_NAME_CMD_RESPONSE = SystemConfig.getMQProperty("queue_name_cmd_response");
		QUEUE_TYPE_CMD_RESPONSE = Integer.valueOf(SystemConfig.getMQProperty("queue_type_cmd_response"));
		QUEUE_MODE_CMD_RESPONSE = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_cmd_response"));
		
		QUEUE_NAME_SEND_CMD_RESULT = SystemConfig.getMQProperty("queue_name_send_cmd_result");
		QUEUE_TYPE_SEND_CMD_RESULT = Integer.valueOf(SystemConfig.getMQProperty("queue_type_send_cmd_result"));
		QUEUE_MODE_SEND_CMD_RESULT = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_send_cmd_result"));
		
		QUEUE_NAME_ALARM = SystemConfig.getMQProperty("queue_name_alarm");
		QUEUE_TYPE_ALARM = Integer.valueOf(SystemConfig.getMQProperty("queue_type_alarm"));
		QUEUE_MODE_ALARM = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_alarm"));
		
		QUEUE_NAME_GPS = SystemConfig.getMQProperty("queue_name_gps");
		QUEUE_TYPE_GPS = Integer.valueOf(SystemConfig.getMQProperty("queue_type_gps"));
		QUEUE_MODE_GPS = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_gps"));

		QUEUE_NAME_SERVICE_DATA = SystemConfig.getMQProperty("queue_name_service_data");
		QUEUE_TYPE_SERVICE_DATA = Integer.valueOf(SystemConfig.getMQProperty("queue_type_service_data"));
		QUEUE_MODE_SERVICE_DATA = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_service_data"));
		
		QUEUE_NAME_SHORT_MESSAGE = SystemConfig.getMQProperty("queue_name_short_message");
		QUEUE_TYPE_SHORT_MESSAGE = Integer.valueOf(SystemConfig.getMQProperty("queue_type_short_message"));
		QUEUE_MODE_SHORT_MESSAGE = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_short_message"));
		
		//车况信息
		//QUEUE_NAME_OBD
		QUEUE_NAME_OBD = SystemConfig.getMQProperty("queue_name_obd");;
		QUEUE_TYPE_OBD = Integer.valueOf(SystemConfig.getMQProperty("queue_type_obd"));;
		QUEUE_MODE_OBD = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_obd"));;
		
		//行程信息(OBD)
		QUEUE_NAME_TRAVEL = SystemConfig.getMQProperty("queue_name_travel");
		QUEUE_TYPE_TRAVEL = Integer.valueOf(SystemConfig.getMQProperty("queue_type_travel"));
		QUEUE_MODE_TRAVEL = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_travel"));
		
		//模块故障信息(OBD)
		QUEUE_NAME_FAULT = SystemConfig.getMQProperty("queue_name_fault");
		QUEUE_TYPE_FAULT = Integer.valueOf(SystemConfig.getMQProperty("queue_type_fault"));
		QUEUE_MODE_FAULT = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_fault"));
		
		//登录退录信息
		QUEUE_NAME_LOGINOUT = SystemConfig.getMQProperty("queue_name_loginout");
		QUEUE_TYPE_LOGINOUT = Integer.valueOf(SystemConfig.getMQProperty("queue_type_loginout"));
		QUEUE_MODE_LOGINOUT = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_loginout"));
		
		//车台版本信息
		QUEUE_NAME_UNIT_VERSION = SystemConfig.getMQProperty("queue_name_unit_version");
		QUEUE_TYPE_UNIT_VERSION = Integer.valueOf(SystemConfig.getMQProperty("queue_type_unit_version"));
		QUEUE_MODE_UNIT_VERSION = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_unit_version"));
		
		//电控单元配置信息
		QUEUE_NAME_ECU_CONFIG = SystemConfig.getMQProperty("queue_name_ecu_config");
		QUEUE_TYPE_ECU_CONFIG = Integer.valueOf(SystemConfig.getMQProperty("queue_type_ecu_config"));
		QUEUE_MODE_ECU_CONFIG = Integer.valueOf(SystemConfig.getMQProperty("queue_mode_ecu_config"));
		
		//批量读写大小
		BATCH_SIZE_WRITE = Integer.valueOf(SystemConfig.getMQProperty("batch_size_write"));
		BATCH_SIZE_READ = Integer.valueOf(SystemConfig.getMQProperty("batch_size_read"));
		
		READ_COMMIT_INTERVAL = Integer.valueOf(SystemConfig.getMQProperty("read_commit_interval"));
		
		MAX_CACHE_SIZE_WRITE = Integer.valueOf(SystemConfig.getMQProperty("max_cache_size_write"));
		
		factory = new ActiveMQConnectionFactory(url);
		
		factory.setUserName(SystemConfig.getMQProperty("user"));
		factory.setPassword(SystemConfig.getMQProperty("password"));
	}
	
	public static Connection openConnection() throws JMSException {
		Connection connection = factory.createConnection();
		return connection;
	}
	
	public static void main(String[] args) {
		try {
			openConnection();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}