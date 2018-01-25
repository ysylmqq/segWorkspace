package bhz.com.util;

import java.util.HashMap;
import java.util.Map;

public class ResultJson {

	public static Map<String, Object> converToMap ( Page<HashMap<String, Object>> page){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("errorCode", null);
		map.put("message",null);
		Map<String,Object> datas =  new HashMap<String,Object>();
		datas.put("count",page.getTotal());
		datas.put("list",page.getItems());
		map.put("datas", datas);
		return map;
	}
}
