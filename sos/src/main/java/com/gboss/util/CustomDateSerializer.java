package com.gboss.util;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @Package:com.gboss.util
 * @ClassName:CustomDateSerializer
 * @Description:自定义时间，序列化类  yyyy-MM-dd hh:mm:ss 
 * @author:zfy
 * @date:2014-4-23 下午1:51:56
 */
public class CustomDateSerializer  extends JsonSerializer<Date> {  
  
    @Override  
    public void serialize(Date value, JsonGenerator jgen,  
            SerializerProvider provider) throws IOException,  
            JsonProcessingException {  
        jgen.writeString(DateUtil.format(value,DateUtil.YMD_DASH_WITH_FULLTIME24));  
  
    }  

}

