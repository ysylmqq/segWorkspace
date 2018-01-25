package com.gboss.service.sync;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;
import com.gboss.util.DateUtil;
import com.gboss.util.TimeHelper;

@Service("bandInfoSyncStrategyService")
public class BandInfoSyncStrategyServiceImpl extends AbstractSyncServiceImpl {

	public BandInfoSyncStrategyServiceImpl(){
		super.setAPI_NAME(SystemConst.HM_BANDINFO);
	}
	
	/**
	 * 
		$sv = s(vin); -- 根据VIN码查找s卡表是否有记录
		$sc= s(barcode); -- 根据TBox条码查找s卡表是否有记录
		if $sc is null {
	  		continue;
		} else {
			if $sv is null { 
			    $sc:update(vin);
			  } else{
			  	if $sv.barcode==$sc.barcode{
			    	$sc:update(scan_time);
			  	}else{
			    	$sv:update(vin=null);
			    	$sc:update(vin,scan_time);
			  	}
			}
		}
	 * 
	 * @param results 同步数据
	 * @param result  处理同步结果
	 * @param msg	     提示信息
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SystemException 
	 */
	/*{
	    scan_time = 1484655703,
	    sn = 14915975635,
	    bar_code = 020344C6C16C1900227,
	    vehicle_type = HMA7160GA4W,
	    equip_code = M4AE,
	    vin = LMVHDEED4HA250026,
	    engine_no = HMA GN16 - VF1 - 71002529
	}*/
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws SystemException, ClientProtocolException, IOException {
		System.err.println("开始同步绑定信息");
		for (HashMap<String, Object> hashMap : results) {
			String barcode = hashMap.get("bar_code") == null ? null : hashMap.get("bar_code").toString();
			if (barcode == null) {
				msg =  " 本地库中不存在barcode=" + barcode + "的sim信息";
				System.out.println(DateUtil.formatNowTime() + msg );
				result.put("msg", msg);
				continue;
			}
			barcode = barcode.trim();
			String vin = hashMap.get("vin") == null ? null : hashMap.get("vin").toString();
			
			if (vin == null) {
				msg = " vin码为null!";
				System.out.println(DateUtil.formatNowTime() + msg );
				result.put("msg", msg);
				continue;
			}
			
			vin = vin.trim();

			String scan_time_s = (String) hashMap.get("scan_time");
			Date scan_time = TimeHelper.getDate(scan_time_s);
			
			String equip_code = hashMap.get("equip_code") == null ? null : hashMap.get("equip_code").toString().replace("'", "");
			
			if (equip_code == null) {
				msg = " equip_code码为null!";
				System.out.println(DateUtil.formatNowTime() + msg );
				result.put("msg", msg);
				continue;
			}
			
			
			String vehicle_type = hashMap.get("vehicle_type") == null ? "" : hashMap.get("vehicle_type").toString();
            String engine_no = hashMap.get("engine_no") == null ? "" : hashMap.get("engine_no").toString();
            String color = hashMap.get("color") == null ? "" : hashMap.get("color").toString();
			
			/**
			 * 根据给定的 barcode 在 t_ba_sim 表中查找有没有数据 ，barcode值唯一
			 * 有：update； vin 扫描时间字段 无：报错日志
			 */
			
			//根据 条形码查找 sim信息  。
			Preload simbc = preloadService.getPreloadByBarCode( barcode);
			
			if (simbc == null) {
				msg = "本地库中不存在barcode=" + barcode + "的sim信息! vin=" + vin;
				System.out.println(DateUtil.formatNowTime() + msg );
				result.put("msg", msg);
				continue;
			}else{
				List<Preload> simsv = preloadService.getPreloadsByVin(vin);//多条   根据海马返回的vin码，去数据库查找SIM
				if(simsv == null){
					//海马返回的新的VIN码，在数据库当中唯一，直接根据barcode(或id)去更新 字段
					msg = updateSIM(barcode, vin, scan_time, equip_code, simbc, vehicle_type, engine_no, color);
				}else{
					//海马返回的VIN码在数库当中已经给存在了。 
					for(Preload simv : simsv){
						/*if(simv.getBarcode().equalsIgnoreCase(simv.getBarcode())){
							//要更新vin 为海马返回的vin
						}else{
							// 把不相等的条形码记录 的vin更新为空 ？？？？
						}*/
						//
						/*if(!simbc.getBarcode().equals(simv.getBarcode())){
							simv.setVin(null);
							preloadService.update(simv);
						}
						msg = updateSIM(barcode, vin, scan_time, equip_code, simbc, vehicle_type, engine_no, color);*/
						if(!simbc.getBarcode().equals(simv.getBarcode())){
							simv.setVin(null);
							preloadService.update(simv);
						}else{
							msg = updateSIM(barcode, vin, scan_time, equip_code, simbc, vehicle_type, engine_no, color);
						}
					}
				}
				result.put("msg", msg);
			}
		}
		return result;
	}
	
	private String updateSIM(String barcode, String vin, Date scan_time,
			String equip_code, Preload simbc, String vehicle_type,
			String engine_no, String color) {
		if(hasText(vin))simbc.setVin(vin);
		
		if(hasText(scan_time))simbc.setScan_time(scan_time);
		if(hasText(equip_code))simbc.setEquip_code(equip_code);
		if(hasText(vehicle_type))simbc.setVehicle_type(vehicle_type);
		if(hasText(engine_no))simbc.setEngine_no(engine_no);
		if(hasText(color))simbc.setColor(color);

		this.preloadService.update(simbc);
		String msg = " --sim更新成功， barcode=" + barcode + "，vin=" + vin + "，scan_time=" + scan_time + "，equip_code=" + equip_code;
		System.out.println(DateUtil.formatNowTime() + msg);
		return msg;
	}

}