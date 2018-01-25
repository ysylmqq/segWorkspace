package bhz.mst.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;

@Repository
public class MstSiteDao  {
	
	public List<JSONObject> getList() throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();
		for (int i = 0; i < 10; i++) {
			JSONObject json = new JSONObject();
			json.put("name", "mqq_"+i);
			json.put("age", i);
			list.add(json);
		}
		return list;
	}
}
