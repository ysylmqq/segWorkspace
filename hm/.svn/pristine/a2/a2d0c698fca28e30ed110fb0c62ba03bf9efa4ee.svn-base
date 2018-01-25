package com.gboss.comm;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.gboss.pojo.EquipCode;
import com.gboss.pojo.Preload;
import com.gboss.pojo.VehicleConf;
import com.gboss.service.EquipCodeService;
import com.gboss.service.PreloadService;
import com.gboss.service.VehicleConfService;
import com.gboss.util.SpringContext;

public abstract class InitHelper {

	public static void initConfCache() {
		System.out.println("------------------------------初始化配置查询开始------------------------------");
		//配置表所有记录
		ConcurrentHashMap <String, VehicleConf> hm_vehicleconf_cache = SystemConst.hm_vehicleconf_cache;//都需要用
		//海马equipcode与code1的映射关系
		ConcurrentHashMap <String, EquipCode> hm_equipcode_cache = SystemConst.hm_equipcode_cache;//下发设置指令时用
		
		VehicleConfService vehicleConfService = (VehicleConfService)SpringContext.getBean("vehicleConfService");
		PreloadService preloadService = (PreloadService)SpringContext.getBean("preloadService");
		EquipCodeService equipCodeService = (EquipCodeService)SpringContext.getBean("equipCodeService");
		
		List<VehicleConf> vc_list = vehicleConfService.getVehicleConfs();
		if(vc_list!=null){
			for(VehicleConf vc : vc_list){
				hm_vehicleconf_cache.put(vc.getCall_letter(), vc);
			}
		}
		
		try {
			 List<Preload>  list_sim = preloadService.getSims(null);
			 if(list_sim!=null){
				 for(Preload sim : list_sim){
					//存放待发送配置指令的简码信息EquipCode
					 SystemConst.hm_sim_cache.put(sim.getCall_letter(), sim);
				 }
			 }
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		List<EquipCode> list_models = equipCodeService.getAllEquipCode(new HashMap());
		 if(list_models!=null){
			 for(EquipCode ec : list_models){
				 if(ec.getEquip_code()!=null) hm_equipcode_cache.put(ec.getEquip_code(), ec);
			 }
		 }
		 System.out.println("------------------------------初始化配置查询结束------------------------------");
	}
	
}
