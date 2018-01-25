package com.chinagps.driverbook.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.chinagps.driverbook.controller.admin.BaseController;
import com.chinagps.driverbook.pojo.ReturnValue;
import com.chinagps.driverbook.pojo.VehicleConf;
import com.chinagps.driverbook.service.VehicleConfService;
import com.chinagps.driverbook.util.RequestUtil;
import com.chinagps.driverbook.util.ResponseUtil;
import com.chinagps.driverbook.util.SGErrorInfoConstants;

@RestController
@RequestMapping(value="/conf_query")
public class VehicleConfController extends BaseController {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private List<String> getConfs(Long val) {
		char[] buffer = new char[64];
	    Arrays.fill(buffer, '0');
	    for (int i = 0; i < 64; ++i) {
	        long mask = 1L << i;
	        if ((val & mask) == mask) {
	            buffer[63 - i] = '1';
	        }
	    }
	    
	    String binaryStr = new String(buffer);
	    StringBuffer sb = new StringBuffer(binaryStr);
	    binaryStr = sb.reverse().toString();
	    
		List<String> list = new ArrayList<String>();
 		for(int i=0 ; i < binaryStr.length(); i++){
			if(binaryStr.charAt(i)=='1'){
				list.add(String.valueOf(i+1));
			}
		}
 		
		return list;
	}
	
	@Autowired
	@Qualifier("vehicleConfServiceImpl")
	private VehicleConfService vehicleConfService;
	
	@RequestMapping(value="/getconfbycl")
	@ResponseBody
	public String getconfbycl(@RequestBody String encryptStr, ReturnValue rv) {
		Map<String, Object> datas = new HashMap<String, Object>();
		try {
			String call_letter = RequestUtil.getStringValue(encryptStr, "call_letter");
			if(call_letter !=null){
				VehicleConf vehicleConf = vehicleConfService.findByCL(call_letter);
				if(vehicleConf != null){
					datas.put("call_letter",call_letter );
					Long code = vehicleConf.getCode1();
					List<String> codes = getConfs(code);
					JSONArray ja = new JSONArray();
					/**
					 * 1=TBOX,2=ABS,3=ESP/DCU,4=PEPS,5=TPMS,6=SRS,7=EPS,8=EMS,9=IMMO,10=BCM,11=TCU,12=ICM,13=APM
					 */
//					JSONObject json = new JSONObject();
//					String[] keys = {"ABS","ESP/DCU","PEPS","TPMS","SRS","EPS","EMS","IMMO","BCM","TCU","ICM","APM"};
					for(String  s: codes){
//						json.put(keys[Integer.parseInt(s)], s);
						ja.add(s);
					}
					datas.put("codes", ja);
					datas.put("is_on", vehicleConf.getIsOn());
					rv.setDatas(datas);
					System.out.println(SDF.format(new Date()) + ",call_letter:"+call_letter+",请求成功! " + codes);
				}
				else{//没有查询到结果
					rv.setErrorCode(SGErrorInfoConstants.SG_CODE_409);
					rv.setErrorMsg(SGErrorInfoConstants.SG_MSG_409);
					rv.setSuccess(false);
					System.out.println(SDF.format(new Date()) + ",call_letter:"+call_letter+",没有查询到结果! ");
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
