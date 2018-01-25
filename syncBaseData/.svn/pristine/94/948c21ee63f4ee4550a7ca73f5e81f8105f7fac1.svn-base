package com.gboss.service.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Insurer;
import com.gboss.util.DateUtil;

@Service("insuranceSyncStrategyService")
public class InsuranceSyncStrategyServiceImpl extends AbstractSyncServiceImpl {
	
	public InsuranceSyncStrategyServiceImpl(){
		super.setAPI_NAME(SystemConst.HM_INSURANCE);
	}
	
	/**
	 * 保险信息同步
	 */
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws SystemException, ClientProtocolException, IOException {
		
		for (HashMap<String, Object> hashMap : results) {
			// 保险公司ID 同步ID
			Long id = hashMap.get("ic_id") == null ? null : Long.valueOf(hashMap.get("ic_id").toString());
			// 联系电话
			String tel = hashMap.get("tel") == null ? "" : hashMap.get("tel").toString();
			if(tel!=null){
				tel = tel.trim();
			}
			// 多出来的
			// String tel_400 = hashMap.get("tel_400")
			// == null ? "" :
			// hashMap.get("tel_400").toString();
			// String tel_800 = hashMap.get("tel_800")
			// == null ? "" :
			// hashMap.get("tel_800").toString();

			// 名称简称,下拉选择名称
			String s_name = hashMap.get("s_name") == null ? "" : hashMap.get("s_name").toString();
			if(s_name!=null){
				s_name = s_name.trim();
			}
			// 中文全称
			String c_name = hashMap.get("c_name") == null ? "" : hashMap.get("c_name").toString();
			if(c_name!=null){
				c_name = c_name.trim();
			}
			// 备注
			String remark = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();
			if(remark!=null){
				remark = remark.trim();
			}
			Insurer insurer = new Insurer();
			insurer = insurerService.getInsurerBySync_id(id);
			if (insurer == null) {
				insurer = insurerService.getInsurerByName(c_name);
				if (null == insurer) {
					insurer = new Insurer();
					insurer.setTel(tel);
					insurer.setS_name(s_name);
					insurer.setRemark(remark);
					insurer.setSync_id(id);
					insurer.setTel_400("");
					insurer.setTel_800("");
					insurer.setC_name(c_name);
					try {
						insurerService.save(insurer);
					} catch (Exception e) {
						SystemConst.E_LOG.error("保险公司" + c_name + "，保存失败！" ,e);
					}
					msg =  " 保险公司" + c_name + "，保存成功！";
					System.out.println(DateUtil.formatNowTime() + msg );
				} else {
					insurer.setTel(tel);
					insurer.setS_name(s_name);
					insurer.setRemark(remark);
					insurer.setTel_400("");
					insurer.setTel_800("");
					insurer.setSync_id(id);
					insurer.setC_name(c_name);
					insurerService.update(insurer);
					msg =  " 保险公司" + c_name + "，更新成功！";
					System.out.println(DateUtil.formatNowTime() + msg );
				}
			} else {
				insurer.setTel(tel);
				insurer.setS_name(s_name);
				insurer.setRemark(remark);
				insurer.setTel_400("");
				insurer.setTel_800("");
				insurer.setSync_id(id);
				insurer.setC_name(c_name);
				insurerService.update(insurer);
				msg = " 保险公司" + c_name + "，更新成功！";
				System.out.println(DateUtil.formatNowTime() + msg );
			}
		}
		result.put("msg", msg);
		return result;
	}

}
