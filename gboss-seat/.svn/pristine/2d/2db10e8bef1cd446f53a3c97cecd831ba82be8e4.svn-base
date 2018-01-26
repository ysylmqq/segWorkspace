package cc.chinagps.seat.util;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
  
public class Xml2JsonUtil {  
	private static final String STR_JSON = "{\"name\":\"Michael\",\"address\":{\"city\":\"Suzou\",\"street\":\" Changjiang Road \",\"postcode\":100025},\"blog\":\"http://www.ij2ee.com\"}";
    public static String xml2JSON(String xml){
        return new XMLSerializer().read(xml).toString();
    }
     
    public static String json2XML(String json){
        JSONObject jobj = JSONObject.fromObject(json);
        String xml =  new XMLSerializer().write(jobj);
        return xml;
    }
     
    public static void main(String[] args) {
        String xml = json2XML(STR_JSON);
        System.out.println("xml = "+xml);
        String json = xml2JSON(xml);
        System.out.println("json="+json);
    }
}  
