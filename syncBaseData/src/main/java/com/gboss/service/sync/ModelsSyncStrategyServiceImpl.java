package com.gboss.service.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Model;
import com.gboss.pojo.Series;
import com.gboss.util.DateUtil;

/**
 * 车型同步服务类
 * 
 */
@Service("modelsSyncStrategyService")
public class ModelsSyncStrategyServiceImpl extends AbstractSyncServiceImpl {
	public ModelsSyncStrategyServiceImpl(){
		super.setAPI_NAME(SystemConst.HM_MODEL);
	}
	/**
	 * 
	 * @param results 同步数据
	 * @param result  处理同步结果
	 * @param msg	     提示信息
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SystemException 
	 */
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws SystemException, ClientProtocolException, IOException {
		for (HashMap<String, Object> hashMap : results) {
			// 车型ID--同步ID
			Long id = hashMap.get("model_id") == null ? null : Long.valueOf(hashMap.get("model_id").toString());
			// 车系ID
			Long series_id = hashMap.get("series_id") == null ? null : Long.valueOf(hashMap.get("series_id").toString());
			// 车型名称
			String model_name = hashMap.get("model_name") == null ? null : hashMap.get("model_name").toString();
			if(model_name!=null){
				model_name = model_name.trim();
			}
			// 车型标准油耗
			Float standart_oil = hashMap.get("standart_oil") == null ? null : Float.valueOf(hashMap.get("standart_oil").toString());
			// 排量
			Float displacement = hashMap.get("displacement") == null ? null : Float.valueOf(hashMap.get("displacement").toString());
			// 车型图片URL路径
			String img = hashMap.get("img") == null ? "" : hashMap.get("img").toString();
			if(img!=null){
				img = img.trim();
			}
			// 备用图片URL路径(IPAD)
			String img1 = hashMap.get("img1") == null ? "" : hashMap.get("img1").toString();
			if(img1!=null){
				img1 = img1.trim();
			}
			// 车型年份
			String car_type_year = hashMap.get("car_type_year") == null ? "" : hashMap.get("car_type_year").toString();
			if(car_type_year!=null){
				car_type_year = car_type_year.trim();
			}
			// 车型级别
			Integer car_type_level = hashMap.get("car_type_level") == null ? null : Integer.valueOf(hashMap.get("car_type_level").toString());
			// 是否涡轮增压
			String is_turbo = hashMap.get("is_turbo") == null ? "" : hashMap.get("is_turbo").toString();
			if(is_turbo!=null){
				is_turbo = is_turbo.trim();
			}
			// 变速箱类型
			String gearbox_type = hashMap.get("gearbox_type") == null ? "" : hashMap.get("gearbox_type").toString();
			if(gearbox_type!=null){
				gearbox_type = gearbox_type.trim();
			}
			// 档位
			Integer gear_num = hashMap.get("gear_num") == null ? null : Integer.valueOf(hashMap.get("gear_num").toString());
			// 车门
			Integer car_door_num = hashMap.get("car_door_num") == null ? null : Integer.valueOf(hashMap.get("car_door_num").toString());

			// 车身结构
			String car_bodywork = hashMap.get("car_bodywork") == null ? "" : hashMap.get("car_bodywork").toString();
			if(car_bodywork!=null){
				car_bodywork = car_bodywork.trim();
			}
			Integer car_long = hashMap.get("car_long") == null ? null : Integer.valueOf(hashMap.get("car_long").toString());
			Integer car_width = hashMap.get("car_width") == null ? null : Integer.valueOf(hashMap.get("car_width").toString());
			Integer car_height = hashMap.get("car_height") == null ? null : Integer.valueOf(hashMap.get("car_height").toString());
			// 最大速度
			Float car_max_speed = hashMap.get("car_max_speed") == null ? null : Float.valueOf(hashMap.get("car_max_speed").toString());
			// 平均油耗
			Float avg_oil = hashMap.get("avg_oil") == null ? null : Float.valueOf(hashMap.get("avg_oil").toString());
			// 备注
			String remark = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();
			if(remark!=null){
				remark = remark.trim();
			}
			// 是否支持中控控制,0=√,1=*,2=O,3=其它,4=支持,5=不支持
			Integer center_control = hashMap.get("center_control") == null ? null : Integer.valueOf(hashMap.get("center_control").toString());
			// 是否支持喇叭控制,:0=√,1=*,2=O,3=其它,4=支持,5=不支持
			Integer horn_control = hashMap.get("horn_control") == null ? null : Integer.valueOf(hashMap.get("horn_control").toString());
			// 是否支持车窗控制,0=√,1=*,2=O,3=其它,4=支持,5=不支持
			Integer window_control = hashMap.get("window_control") == null ? null : Integer.valueOf(hashMap.get("window_control").toString());

			// light_control
			// 是否支持危险灯控制,0=√,1=*,2=O,3=其它,4=支持,5=不支持
			Integer light_control = hashMap.get("light_control") == null ? null : Integer.valueOf(hashMap.get("light_control").toString());

			// 油箱容量,单位L,默认50L
			Integer oil_volume = hashMap.get("oil_volume") == null ? null : Integer.valueOf(hashMap.get("oil_volume").toString());
			// 是否有效
			Integer is_valid = hashMap.get("is_valid") == null ? null : Integer.valueOf(hashMap.get("is_valid").toString());
			//配置简码
			//String equip_code = hashMap.get("equip_code") == null ? null : hashMap.get("equip_code").toString();
			//简码对应映射信息
			//Long code1 = hashMap.get("code1") == null ? null : Long.valueOf(hashMap.get("code1").toString());

			Model model = modelService.getModelBySync_id(id);// 车型
			Series series = seriesService.getSeriesBySync_id(series_id);// 车系
			if (series != null) {
				if (model == null) {
					if (modelService.isExist(model_name)) {
						model = modelService.getModelByName(model_name);
						model.setAve_oil(avg_oil);
						model.setCar_bodywork(car_bodywork);
						model.setCar_height(car_height);
						model.setCar_long(car_long);
						model.setCar_max_speed(car_max_speed);
						model.setCar_type_level(car_type_level);
						model.setCar_type_year(car_type_year);
						model.setCar_width(car_width);
						model.setCarDoorNum(car_door_num);
						model.setCenter_control(center_control);
						model.setDisplacement(displacement);
						model.setGearbox_type(gearbox_type);
						model.setGearNum(gear_num);
						model.setHorn_control(horn_control);
						model.setImg(img);
						model.setImg1(img1);
						model.setIs_turbo(is_turbo);
						model.setName(model_name);
						model.setOil_volume(oil_volume);
						model.setRemark(remark);
						model.setStandart_oil(standart_oil);
						model.setWindow_control(window_control);
						model.setSeriesId(series.getSeries_id());
						model.setSync_id(id);
						model.setIs_alid(is_valid);
						model.setLight_control(light_control);
//						model.setEquip_code(equip_code);
//						model.setCode1(code1);
						modelService.update(model);
						msg = " 车型：" + model_name + ",同步更新成功！";
						System.out.println(DateUtil.formatNowTime() + msg );
					} else {
						model = new Model();
						model.setAve_oil(avg_oil);
						model.setCar_bodywork(car_bodywork);
						model.setCar_height(car_height);
						model.setCar_long(car_long);
						model.setCar_max_speed(car_max_speed);
						model.setCar_type_level(car_type_level);
						model.setCar_type_year(car_type_year);
						model.setCar_width(car_width);
						model.setCarDoorNum(car_door_num);
						model.setCenter_control(center_control);
						model.setDisplacement(displacement);
						model.setGearbox_type(gearbox_type);
						model.setGearNum(gear_num);
						model.setHorn_control(horn_control);
						model.setImg(img);
						model.setImg1(img1);
						model.setIs_turbo(is_turbo);
						model.setName(model_name);
						model.setOil_volume(oil_volume);
						model.setRemark(remark);
						model.setStandart_oil(standart_oil);
						model.setWindow_control(window_control);
						model.setSeriesId(series.getSeries_id());
						model.setSync_id(id);
						model.setLight_control(light_control);
//						model.setEquip_code(equip_code);
//						model.setCode1(code1);
						try {
							modelService.save(model);
						} catch (Exception e) {
							SystemConst.E_LOG.error("车型：" + model_name + "，同步保存失败！" ,e);
						}
						msg = " 车型：" + model_name + ",同步保存成功！";
						System.out.println(DateUtil.formatNowTime() + msg );
					}
				} else {
					model.setAve_oil(avg_oil);
					model.setCar_bodywork(car_bodywork);
					model.setCar_height(car_height);
					model.setCar_long(car_long);
					model.setCar_max_speed(car_max_speed);
					model.setCar_type_level(car_type_level);
					model.setCar_type_year(car_type_year);
					model.setCar_width(car_width);
					model.setCarDoorNum(car_door_num);
					model.setCenter_control(center_control);
					model.setDisplacement(displacement);
					model.setGearbox_type(gearbox_type);
					model.setGearNum(gear_num);
					model.setHorn_control(horn_control);
					model.setImg(img);
					model.setImg1(img1);
					model.setIs_turbo(is_turbo);
					model.setName(model_name);
					model.setOil_volume(oil_volume);
					model.setRemark(remark);
					model.setStandart_oil(standart_oil);
					model.setWindow_control(window_control);
					model.setLight_control(light_control);
					model.setSeriesId(series.getSeries_id());
					model.setSync_id(id);
					model.setIs_alid(is_valid);
//					model.setEquip_code(equip_code);
//					model.setCode1(code1);
					modelService.update(model);
					msg = " 车型：" + model_name + ",同步更新成功！";
					System.out.println(DateUtil.formatNowTime() + msg );
				}
			} else {
				msg = "车系不存在，不能同步该车型！";
				System.out.println(DateUtil.formatNowTime() + msg );
			}
		}
		result.put("msg", msg);
		return result;
	}
}
