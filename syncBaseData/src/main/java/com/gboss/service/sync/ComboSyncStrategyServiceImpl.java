package com.gboss.service.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Combo;
import com.gboss.util.DateUtil;

@Service("comboSyncStrategyService")
public class ComboSyncStrategyServiceImpl extends AbstractSyncServiceImpl {

	public ComboSyncStrategyServiceImpl(){
		super.setAPI_NAME(SystemConst.HM_COMBO);
	}
	
	/**
	 * 套餐同步业务
	 */
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws SystemException, ClientProtocolException, IOException {
		for (HashMap<String, Object> hashMap : results) {
			// 套餐ID
			Long id = hashMap.get("combo_id") == null ? null : Long.valueOf(hashMap.get("combo_id").toString());
			// 状态
			Integer flag1 = hashMap.get("flag") == null ? 1 : Integer.valueOf(hashMap.get("flag").toString());
			// 运营商
			Integer telco = hashMap.get("telco") == null ? 3 : Integer.valueOf(hashMap.get("telco").toString());

			// 套餐名称
			// String combo_code =
			// hashMap.get("combo_code") == null ? "" :
			// hashMap.get("combo_code").toString();

			// 套餐名称
			String combo_name = hashMap.get("combo_name") == null ? null : hashMap.get("combo_name").toString();
			if(combo_name!=null){
				combo_name = combo_name.trim();
			}

			// 套餐费用
			Float month_fee = hashMap.get("month_fee") == null ? 0 : Float.valueOf(hashMap.get("month_fee").toString());
			// 通话时长
			Float voice_time = hashMap.get("voice_time") == null ? null : Float.valueOf(hashMap.get("voice_time").toString());
			// 总流量
			Float data = hashMap.get("data") == null ? null : Float.valueOf(hashMap.get("data").toString());

			String remark1 = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();
			remark1 = remark1.trim(); 

			Combo combo = new Combo();
			combo = comboService.getComboBySync_id(id);
			if (combo == null) {
				combo = comboService.getComboByName(combo_name);
				if (null == combo) {
					combo = new Combo();
					combo.setCombo_code("");
					combo.setCombo_name(combo_name);
					combo.setVoice_time(voice_time);
					combo.setData(data);
					
					combo.setMonth_fee(month_fee);//0 每月价格(元)
					combo.setFlag(flag1);//标志, 1=正常, 0=删除(对新用户不可选)
					combo.setTelco(telco);//运营商
					combo.setFeetype_id(101);//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
					combo.setOp_id(0L);//操作员id 
					combo.setSubco_no(201L);//分公司, 0=总部定义, 大于0=分公司自定义
					combo.setSim_type(2);//卡类型, 1=语音卡, 2=流量卡
					
					combo.setSync_id(id);
					combo.setRemark(remark1);
					try {
						comboService.save(combo);
					} catch (Exception e) {
						SystemConst.E_LOG.error(" 套餐combo_name=" + combo_name + "，保存失败！", e);
					}
					msg = " 套餐combo_name=" + combo_name + "，保存成功！";
					System.out.println(DateUtil.formatNowTime() + msg );
				} else {
					combo.setCombo_code("");
					combo.setCombo_name(combo_name);
					combo.setFlag(flag1);
					combo.setMonth_fee(month_fee);
					combo.setTelco(telco);
					combo.setVoice_time(voice_time);
					combo.setData(data);
					combo.setSync_id(id);
					combo.setRemark(remark1);
					
					combo.setFeetype_id(101);//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
					combo.setOp_id(0L);//操作员id 
					combo.setSubco_no(201L);//分公司, 0=总部定义, 大于0=分公司自定义
					combo.setSim_type(2);//卡类型, 1=语音卡, 2=流量卡
					
					comboService.update(combo);
					msg = " 套餐combo_name=" + combo_name + "，更新成功！";
					System.out.println(DateUtil.formatNowTime() + msg );
				}
			} else {
				combo.setCombo_code("");
				combo.setCombo_name(combo_name);
				combo.setFlag(flag1);
				combo.setMonth_fee(month_fee);
				combo.setTelco(telco);
				combo.setVoice_time(voice_time);
				combo.setData(data);
				combo.setOp_id(1L);
				combo.setSync_id(id);
				combo.setRemark(remark1);
				combo.setFeetype_id(101);//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
				combo.setOp_id(0L);//操作员id 
				combo.setSubco_no(201L);//分公司, 0=总部定义, 大于0=分公司自定义
				combo.setSim_type(2);//卡类型, 1=语音卡, 2=流量卡
				comboService.update(combo);
				System.out.println(DateUtil.formatNowTime() + " 套餐combo_name=" + combo_name + "，更新成功！");
				msg = " 套餐combo_name=" + combo_name + "，更新成功！";
				System.out.println(DateUtil.formatNowTime() + msg );
			}
		}
		result.put("msg", msg);
		return result;
	}

	
	
}
