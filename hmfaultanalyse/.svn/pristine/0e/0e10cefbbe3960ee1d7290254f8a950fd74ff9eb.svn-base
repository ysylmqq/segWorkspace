package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SystemConfig {
	private static final String SYSTEM_CONFIG_FILE_PATH = "./config/system.properties";
	private static final String LOG_CONFIG_FILE_PATH = "./config/log4j.properties";
	private static final String DB_CONFIG_FILE_PATH = "./config/db.properties";
	
	private static Properties system_properties;
	private static Properties log_properties;
	private static Properties db_properties;
	
	static {
		try {
			system_properties = Util.loadProperties(SYSTEM_CONFIG_FILE_PATH);
			log_properties = Util.loadProperties(LOG_CONFIG_FILE_PATH);
			db_properties = Util.loadProperties(DB_CONFIG_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getSystemProperties(String key){
		return system_properties.getProperty(key);
	}
	
	public static void setSystemProperties(String key, String value){
        try { 
        	OutputStream fos = new FileOutputStream(SYSTEM_CONFIG_FILE_PATH); 
        	system_properties.setProperty(key, value);
        	system_properties.store(fos, ""); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
	}

	public static String getLogProperties(String key){
		return log_properties.getProperty(key);
	}
	
	public static Properties getDBproperties(){
		return db_properties;
	}

	public static String getDBProperties(String key){
		return db_properties.getProperty(key);
	}
	
	/*
	 * /** 
     * 传递键值对的Map，更新properties文件 
     *  
     * @param fileName 
     *            文件名(放在resource源包目录下)，需要后缀 
     * @param keyValueMap 
     *            键值对Map 
    public static void updateProperties(String fileName,Map<String, String> keyValueMap) {  
        //getResource方法使用了utf-8对路径信息进行了编码，当路径中存在中文和空格时，他会对这些字符进行转换，这样，  
        //得到的往往不是我们想要的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的中文及空格路径。  
        String filePath = PropertiesUtil.class.getClassLoader().getResource(fileName).getFile();  
        Properties props = null;  
        BufferedWriter bw = null;  
  
        try {  
            filePath = URLDecoder.decode(filePath,"utf-8");      
            log.debug("updateProperties propertiesPath:" + filePath);  
            props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(fileName));  
            log.debug("updateProperties old:"+props);  
              
            // 写入属性文件  
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));  
              
            props.clear();// 清空旧的文件  
              
            for (String key : keyValueMap.keySet())  
                props.setProperty(key, keyValueMap.get(key));  
              
            log.debug("updateProperties new:"+props);  
            props.store(bw, "");  
        } catch (IOException e) {  
            log.error(e.getMessage());  
        } finally {  
            try {  
                bw.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
	 */
}