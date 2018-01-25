package com.hm.bigdata.comm;

import com.alibaba.fastjson.JSONObject;

/**
 * @Package:com.chinagps.smh.comm
 * @ClassName:JSONObjectWrapper
 * @Description:TODO
 * @author:zfy
 * @date:2014-4-14 上午8:58:46
 */
public class JSONObjectWrapper {
	private JSONObject jsonObject;

    public JSONObjectWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }
}

