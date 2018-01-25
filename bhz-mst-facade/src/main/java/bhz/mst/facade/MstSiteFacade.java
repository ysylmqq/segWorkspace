package bhz.mst.facade;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface MstSiteFacade {
	
	public List<JSONObject> getList() throws Exception;

}
