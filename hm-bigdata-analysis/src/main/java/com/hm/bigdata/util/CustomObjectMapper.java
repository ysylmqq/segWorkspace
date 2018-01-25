package com.hm.bigdata.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.stereotype.Component;

/**
 * @Package:com.gboss.util
 * @ClassName:CustomObjectMapper
 * @Description:解决SpringMVC使用@ResponseBody返回json时，日期格式默认显示为时间戳的问题。需配合<mvc:message-converters>使用 
 * @author:zfy
 * @date:2014-5-6 下午6:34:51
 */
@Component("customObjectMapper") 
public class CustomObjectMapper extends ObjectMapper {  
  
    public CustomObjectMapper() {  
        CustomSerializerFactory factory = new CustomSerializerFactory();  
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {  
            @Override  
            public void serialize(Date value, JsonGenerator jsonGenerator,  
                    SerializerProvider provider) throws IOException, JsonProcessingException {
            	SimpleDateFormat sdf = null;
            	try {
					if(value !=null && value.toString().length()<19){//精确到日
						sdf = new SimpleDateFormat(DateUtil.YMD_DASH);  
					}else{
						sdf = new SimpleDateFormat(DateUtil.YMD_DASH_WITH_FULLTIME24);  
					}
					jsonGenerator.writeString(sdf.format(value));
				} catch (Exception e) {
					e.printStackTrace();
				}  
            }  
        });  
        
/*        factory.addGenericMapping(Float.class, new JsonSerializer<Float>() {  
        	DecimalFormat df  = new DecimalFormat("###.00"); 
        	DecimalFormat df2  = new DecimalFormat("###");
            @Override  
            public void serialize(Float value, JsonGenerator jsonGenerator,  
                    SerializerProvider provider) throws IOException, JsonProcessingException {
            	try {
            		if(value.intValue()==value){//没小数
            			jsonGenerator.writeString(df2.format(value));
            		}else{
            			jsonGenerator.writeString(df.format(value));
            		}
				} catch (Exception e) {
					e.printStackTrace();
				}  
            }  
        }); 
        factory.addGenericMapping(Double.class, new JsonSerializer<Double>() {  
        	DecimalFormat df  = new DecimalFormat("###.00"); 
        	DecimalFormat df2  = new DecimalFormat("###");
            @Override  
            public void serialize(Double value, JsonGenerator jsonGenerator,  
                    SerializerProvider provider) throws IOException, JsonProcessingException {
            	try {
            		if(value.intValue()==value){//没小数
            			jsonGenerator.writeString(df2.format(value));
            		}else{
            			jsonGenerator.writeString(df.format(value));
            		}
				} catch (Exception e) {
					e.printStackTrace();
				}   
            }  
        });*/
      this.setSerializerFactory(factory);
    }  
}

