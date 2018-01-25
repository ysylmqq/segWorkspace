package com.chinagps.driverbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.util.MemcachedUtil;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.chinagps.driverbook.util.SGErrorInfoConstants;


@RestController
@RequestMapping(value="/notice_query")
public class SnController {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private MemcachedClient mc = MemcachedUtil.getClient(false);
	
	@RequestMapping(value="/getnoticebysn")
	@ResponseBody
	public String getnoticebysn(@RequestBody String encryptStr, ReturnValue rv) {
		Map<String, Object> datas = new HashMap<String, Object>();
		try {
			String sn = RequestUtil.getStringValue(encryptStr, "sn");
			if(sn!=null){
				String cacheStrs = (String) mc.get(sn);
				if(cacheStrs != null){
					String[] arrs = cacheStrs.split(",");
					datas.put("sn", sn);
					datas.put("title",arrs[1]);
					datas.put("content",arrs[0]);
					rv.setSuccess(true);
					rv.setDatas(datas);
				}
			}else{
				rv.setErrorCode(SGErrorInfoConstants.SG_CODE_403);
				rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_403);
				System.out.println( "["+SDF.format(new Date()) +"-!]，传入参数【sn】为空");
			}
		} catch (Exception e) {
			//错误处理
			System.out.println( "["+SDF.format(new Date()) +"-!]，异常信息：" + e.getMessage());
			rv.setErrorCode(SGErrorInfoConstants.SG_CODE_500);
			rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_500);
		}
		return ResponseUtil.encrypt(rv);
	}
	
}
