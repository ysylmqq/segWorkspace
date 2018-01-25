package com.gboss.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.util.LDAPSecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;
import com.gboss.pojo.ReturnValue;
import com.gboss.service.PreloadService;
import com.gboss.service.ServicepwdService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.DESPlus;
import com.gboss.util.StringUtils;

@Controller
public class VehicleUnitController extends BaseController {
	
	@Autowired
	@Qualifier("vehicleService")
	private VehicleService vehicleService;
	
	@Autowired
	@Qualifier("UnitService")
	private UnitService unitService;
	
	@Autowired
	private PreloadService preloadService;
	
	@Autowired
	private ServicepwdService servicepwdService;
	
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(VehicleUnitController.class);
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 车载电话与服务密码服务接口
	 * 
	 * @param vin
	 * @param barcode
	 * @param rv
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/servicepwd")
	public @ResponseBody ReturnValue servicepwd(String vin,ReturnValue rv,HttpServletRequest request,HttpServletResponse response){
		try {
			System.out.println("请求参数：vin:" + vin );
			rv = new ReturnValue();
			if((vin == null ||"".equals(vin))){
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数不完整!");
				System.out.println(SDF.format(new Date()) + "，请求调用接口失败！");
				SystemConst.D_LOG.debug("[hm servicepwd] vin error,vin:"+vin);
				return rv;
			}
			vin = vin.trim();
			//先查询sim卡中是否存在barcode和vin的sim信息
			Preload sim = preloadService.getPreloadByVinBarcode(vin, null);
			if(sim == null){
				//处理错误信息
				rv.setErrorCode(404);
				rv.setErrorMsg("请求sim卡信息不存在.");
				SystemConst.D_LOG.debug("[hm servicepwd] vin:"+vin+";result:no sim info");
				return rv;
			}
			
			List<Map<String, Object>>  result = servicepwdService.getServicePwdByBV(null,vin);
			
			if(result != null && result.size() > 0){
				//拿出服务密码
				Map resultMap = result.get(0);
				JSONObject json = handlePwdWithSHA(resultMap);
				rv.setDatas(json);
				rv.setSuccess(true);
				SystemConst.D_LOG.debug("[hm servicepwd] vin:"+vin+";result:"+json);
			}else{
				rv.setErrorMsg("没有查询到结果!");
				rv.setSuccess(true);
				SystemConst.D_LOG.debug("[hm servicepwd] vin:"+vin+";result:no result found");
			}
			
		} catch (SystemException e) {
			rv.setErrorCode(500);
			rv.setErrorMsg("服务器出错!" +e.getMessage());
			SystemConst.D_LOG.debug("[hm servicepwd] vin:"+vin+";result: server error");
		}
		return rv;
	}

	private JSONObject handlePwdWithSHA(Map resultMap) {
		
		String servicePwd =(String) resultMap.get("servicepwd");
		JSONObject json = new JSONObject();
		json.put("vin", resultMap.get("vin"));
		if(!"".equals(servicePwd)){
			
			DESPlus m = new DESPlus("seg12345");
			String mingwen = m.Decode(servicePwd).toString().trim();
			
			String sha16 = LDAPSecurityUtils.toHexStr(LDAPSecurityUtils.encodeSHA(mingwen));
			json.put("servicepwd", sha16);
		}else{
			json.put("servicepwd", servicePwd);
		}
		return json;
	}
	
	@RequestMapping(value = "/servicepwdt")
	public @ResponseBody ReturnValue servicepwdt(String begintime,String endtime,ReturnValue rv,HttpServletRequest request,HttpServletResponse response){
		try {
			
			System.out.println(SDF.format(new Date()) + "，请求参数，begintime:"+begintime+"，endtime:" + endtime);
			rv = new ReturnValue();
			
			if(StringUtils.isNullOrEmpty(begintime) || 
				StringUtils.isNullOrEmpty(endtime)){
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数不完整!");
				System.out.println(SDF.format(new Date()) + "，请求调用接口失败！");
				SystemConst.D_LOG.debug("[hm servicepwdt] param error,begintime:"+begintime+";endtime:"+endtime);
				return rv;
			}
			
			begintime = begintime.trim();
			endtime   = endtime.trim();
			
			List<Map<String, Object>>  result = servicepwdService.getServicePwdByTimes(begintime,endtime);
			
			if(result != null && result.size() > 0){
				JSONArray result_jsons = new JSONArray();
				//拿出服务密码
				for(Map<String, Object> temp : result){
					String vin = (String)temp.get("vin");
					if(vin == null || "".equals(vin)){
//						temp.put("vin", "");//若是需要vin为空的也开放只需要把这行注释去掉，然后注释掉下面continue;这行代码
						continue;//
					}
					
					String servicepwd = (String)temp.get("servicepwd");
					if(servicepwd == null || "".equals(servicepwd)){
						temp.put("servicepwd", "");
					}
					
					JSONObject json = handlePwdWithSHA(temp);
					result_jsons.add(json);
				}
				rv.setDatas(result_jsons);
				rv.setSuccess(true);
				SystemConst.D_LOG.debug("[hm servicepwdt] begintime:"+begintime+";endtime:"+endtime+";result:"+result_jsons);
			}else{//查询没有结果
				rv.setErrorMsg("没有查询到结果!");
				rv.setSuccess(true);
				SystemConst.D_LOG.debug("[hm servicepwdt] begintime:"+begintime+";endtime:"+endtime+";result:no record");
			}
			
		} catch (SystemException e) {
			rv.setErrorCode(500);
			rv.setErrorMsg("服务器出错!" +e.getMessage());
			SystemConst.D_LOG.debug("[hm servicepwdt] param error,begintime:"+begintime+";endtime:"+endtime+";result:server error");
		}
		return rv;
	}
	

}
