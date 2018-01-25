package com.gboss.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gboss.service.CallletterService;
import com.gboss.service.PreloadService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.StringUtils;
import com.gboss.util.TimeHelper;

@Controller
public class CallletterController extends BaseController {
	
	@Autowired
	@Qualifier("vehicleService")
	private VehicleService vehicleService;
	
	@Autowired
	@Qualifier("UnitService")
	private UnitService unitService;
	
	@Autowired
	private PreloadService preloadService;
	
	@Autowired
	private CallletterService callletterService;
	
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(VehicleUnitController.class);
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value = "/caller")
	public @ResponseBody ReturnValue caller(String barcode, String vin, String scan_time, String equip_code,
			String vehicle_type, String engine_no, String color, ReturnValue rv, HttpServletRequest request,
			HttpServletResponse response) {
		String params = getCallerParams(barcode, vin, scan_time, equip_code, vehicle_type, engine_no, color);
		try {
			rv = new ReturnValue();
			if ((barcode == null || "".equals(barcode))) {
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数错误，barcode is null!");
				SystemConst.D_LOG.debug("[hm caller] barcode is null");
				return rv;
			}

			barcode = barcode.trim();
			
			if ((vin == null || "".equals(vin))) {
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数错误，vin is null!");
				SystemConst.D_LOG.debug("[hm caller] vin is null");
				return rv;
			}

			vin = vin.trim();
			
			if ((scan_time == null || "".equals(scan_time))) {
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数错误，scan_time is null!");
				SystemConst.D_LOG.debug("[hm caller] scan_time is null");
				return rv;
			}

			scan_time = scan_time.trim();
			
			if ((equip_code == null || "".equals(equip_code))) {
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数错误，equip_code is null!");
				SystemConst.D_LOG.debug("[hm caller] equip_code is null");
				return rv;
			}

			equip_code = equip_code.trim();
			
			Preload sim = preloadService.getPreloadByBarCode(barcode);
			if (sim == null) {
				// 处理错误信息
				rv.setErrorCode(404);
				rv.setErrorMsg("请求sim卡信息不存在.");
				SystemConst.D_LOG.debug("[hm caller] params:" + params + ";msg:" + "请求sim卡信息不存在");
				return rv;
			}
			String call_letter = sim.getCall_letter();
			call_letter = call_letter.trim();
			if (call_letter == null || "".equals(call_letter)) {
				rv.setErrorCode(405);
				rv.setErrorMsg("barcode=" + barcode + "的车载号码不存在.");
				SystemConst.D_LOG.debug("[hm caller] params:" + params + ";msg:" + "请求的车载号码不存在");
				return rv;
			}

			List<Preload> sim1 = callletterService.getCallLettersByTimes(null, null, barcode);

			if (sim1 != null && sim1.size() > 0) {
				// 执行绑定更新操作
				if (StringUtils.isNotBlank(vin) && StringUtils.isNotBlank(scan_time)) {
					try {
						List<Preload> simsv = preloadService.getPreloadsByVin(vin);
						Date scanTime = TimeHelper.getDate(scan_time);

						updateSIM(barcode, vin, scanTime, equip_code, sim, vehicle_type, engine_no, color);
						SystemConst.D_LOG.debug("[hm caller] update bindinfo success params:" + params);
						if (simsv != null) {
							for (Preload simv : simsv) {
								if (!sim.getBarcode().equals(simv.getBarcode())) {
									simv.setVin(null);
									preloadService.update(simv);
								}
							}
						}
						JSONObject json = new JSONObject();
						json.put("barcode", barcode);
						json.put("call_letter", call_letter);
						rv.setDatas(json);
						rv.setSuccess(true);
						SystemConst.D_LOG.debug("[hm caller] params:" + params + ";call_letter:" + call_letter);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						rv.setErrorCode(500);
						rv.setErrorMsg("服务器出错!" + e.getMessage());
						SystemConst.D_LOG
								.debug("[hm caller] update bindinfo error params:" + params + ";msg:" + e.getMessage());
					}
				}
			} else {
				rv.setErrorCode(406);
				rv.setErrorMsg("barcode=" + barcode + "的sim卡信息不存在本地库中.");
				rv.setSuccess(false);
				SystemConst.D_LOG.debug("[hm caller] params:" + params + ";msg:" + "sim卡信息不存在本地库中");
			}
		} catch (SystemException e) {
			rv.setErrorCode(500);
			rv.setErrorMsg("服务器出错!" + e.getMessage());
			SystemConst.D_LOG.debug("[hm caller] params:" + params + ";msg:" + "server error");
		}
		return rv;
	}
	
	private String getCallerParams(String barcode, String vin, String scan_time, String equip_code,
			String vehicle_type, String engine_no, String color){
		StringBuilder builder=new StringBuilder();
		builder.append("barcode:").append(barcode);
		builder.append(";");
		builder.append("vin:").append(vin);
		builder.append(";");
		builder.append("scan_time:").append(scan_time);
		builder.append(";");
		builder.append("equip_code:").append(equip_code);
		builder.append(";");
		builder.append("vehicle_type:").append(vehicle_type);
		builder.append(";");
		builder.append("engine_no:").append(engine_no);
		builder.append(";");
		builder.append("color:").append(color);
		return builder.toString();
	}
	
	private void updateSIM(String barcode, String vin, Date scan_time, String equip_code, Preload simbc,
			String vehicle_type, String engine_no, String color) {
		if (StringUtils.isNotBlank(vin))
			simbc.setVin(vin);
		if (scan_time!=null)
			simbc.setScan_time(scan_time);
		if (StringUtils.isNotBlank(equip_code))
			simbc.setEquip_code(equip_code);
		if (StringUtils.isNotBlank(vehicle_type))
			simbc.setVehicle_type(vehicle_type);
		if (StringUtils.isNotBlank(engine_no))
			simbc.setEngine_no(engine_no);
		if (StringUtils.isNotBlank(color))
			simbc.setColor(color);
		this.preloadService.update(simbc);
	}
	
	@RequestMapping(value = "/callert")
	public @ResponseBody ReturnValue callert(String begintime,String endtime,ReturnValue rv,HttpServletRequest request,HttpServletResponse response){
		try {
			
			System.out.println(SDF.format(new Date()) + "，请求参数，begintime:"+begintime+"，endtime:" + endtime);
			rv = new ReturnValue();
			
			if(StringUtils.isNullOrEmpty(begintime) || 
				StringUtils.isNullOrEmpty(endtime)){
				rv.setErrorCode(403);
				rv.setErrorMsg("请求参数不完整!");
				System.out.println(SDF.format(new Date()) + "，请求调用接口失败！");
				SystemConst.D_LOG.debug("[hm callert] param error");
				return rv;
			}
			
			begintime = begintime.trim();
			endtime   = endtime.trim();
			
			List<Preload>  result = callletterService.getCallLettersByTimes(begintime,endtime,null);
			
			if(result != null && result.size() > 0){
				JSONArray result_jsons = new JSONArray();
				//拿出服务密码
				for(Preload sim : result){
					String call_letter = sim.getCall_letter();
					if(call_letter == null || "".equals(call_letter)){
						continue;//
					}
					String barcode = sim.getBarcode();
					if(barcode == null || "".equals(barcode)){
						continue;
					}
					JSONObject json = new JSONObject();
					json.put("barcode", barcode);
					json.put("call_letter", call_letter);
					result_jsons.add(json);
				}
				
				rv.setDatas(result_jsons);
				rv.setSuccess(true);
				SystemConst.D_LOG.debug("[hm callert] begintime:"+begintime+";endtime:"+endtime+";results:"+result_jsons);
			}else{//查询没有结果
				rv.setErrorMsg("没有查询到结果!");
				rv.setSuccess(true);
				SystemConst.D_LOG.debug("[hm callert] begintime:"+begintime+";endtime:"+endtime+";results:no records");
			}
		} catch (SystemException e) {
			rv.setErrorCode(500);
			rv.setErrorMsg("服务器出错!" +e.getMessage());
			SystemConst.D_LOG.debug("[hm callert] server error");
		}
		return rv;
	}

}
