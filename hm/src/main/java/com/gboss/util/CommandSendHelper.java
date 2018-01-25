package com.gboss.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.lib.util.Config;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.EquipCode;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.VehicleConf;
import com.gboss.service.VehicleService;

/**
 * 配置指令下发帮助类：分查询配置指令和设置配置指令两种 
 * queryOrSet=0为查询配置指令 
 * queryOrSet=1为设置配置指令
 */
public abstract class CommandSendHelper {

	/**
	 * H260及以上版本才有车辆配置功能，低于H260版本，
	 * 不需要下发查询配置、设置配置指令
	 * 
	 * @param up 获取版本信息
	 * @param vc 配置信息
	 * @param queryOrSet: SystemConst.QUERYCONF_CMD Or SystemConst.SETCONF_CMD   
	 * @return
	 */
	public static synchronized String sendCommand(Upgrade up, VehicleConf vc, int queryOrSet) {
		String key = up.getCall_letter();
		
		if(vc == null ){//vc为空的时候直接插入 conf表 
			ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache = SystemConst.hm_vehicleconf_cache;
			VehicleService vehicleService = (VehicleService) SpringContext.getBean("vehicleService") ;
			vc = new VehicleConf();
			vc.setCall_letter(key);
			vc.setCode1(0l);
			vc.setIs_on(1);
			vehicleService.saveOrUpdate(vc);
			hm_vehicleconf_cache.put(key, vc);
		}
		
		//需要发送指令的最低版本
		String sendcommand_version = Config.getCmdProperties().getProperty("sendcommand_version");
		//终端目前版本
		String cur_version = up.getCur_ver();
		if (cur_version == null) {
			System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 终端版本为空,不发送查询配置指令！");
			return "";
		}
		
		int flag = up.getFlag().intValue();
	    if (flag != 6) {
	      System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 空中升级处理中!!!");
	      return "";
	    }

		// H260及以上版本才有车辆配置功能，低于H260版本，不需要下发查询配置、设置配置指令
		if (cur_version.compareTo(sendcommand_version) < 0) {
			System.out.println(DateUtil.formatNowTime() + " 呼号："+ vc.getCall_letter() + " 终端版本为：" + cur_version + " 低于指定版本：" + sendcommand_version + "，不发送查询配置指令！");
			return "";
		} else {
			ArrayList<String> callletterList = new ArrayList<String>();
			callletterList.add(key);
			switch (queryOrSet) {
			case SystemConst.QUERYCONF_CMD:// 为0时发送查询配置指令
				if ( vc.getCode1().longValue() == 0L ) {
					String cmdsn = SystemConst.clienthandler.SendCommand(callletterList, 0x00B4, null, true);
					if (!cmdsn.equals("")) {
						vc.setSend_conf_count(vc.getSend_conf_count() + 1);
						vc.setSend_queryconf(new Date());
						vc.setS_time(new Date());
						System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 第"+ (vc.getSend_conf_count()) + "次查询配置指令下发， sn="+ cmdsn);
						SystemConst.hm_vehicleconf_cache.put(key, vc);
						return cmdsn;
					} else {
						System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 发送查询配置指令失败");
					}
				}else{
					System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 已有配置简码 code1:" + vc.getCode1());
				}
				break;
				case SystemConst.SETCONF_CMD:// 为1时发送设置配置指令
				if (cur_version.compareTo(sendcommand_version) < 0) {
					System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + "，终端版本为：" + cur_version+ " " + "低于指定版本：" + sendcommand_version+ "，不能发送设置配置指令！");
					return "";
				} else {
					// 先从t_ba_model 中查询出 code1 ////////// equip_code
					Preload sim = SystemConst.hm_sim_cache.get(key);
					if(sim!=null ){
						if( vc.getCode1().longValue() == 0L ){
							ConcurrentHashMap <String, EquipCode> ecode_cache = SystemConst.hm_equipcode_cache;
							String equip_code = sim.getEquip_code();
							if(equip_code==null){
								System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 终端配置简码=" + equip_code + "发送设置指令结束!");
								return "";
							}
							EquipCode ec = ecode_cache.get(equip_code);
							if( ec!=null  && ec.getCode1() != null && ec.getCode1() != 0 ){
								ArrayList<String> params = new ArrayList<String>();
								params.add("00000001");//体检开00000001 体检关00000000
								String[] result = getConf(ec.getCode1());
								if("".equals(result[0]) && "".equals(result[1])){
									System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 发送设置配置指令时code1配置为空,停止发送设置配置指令");
									return "";
								}
								
								//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM
								params.add(result[0]);//前面八位设置 从左到右 ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO  例如：10001101 ==> ABS,SRS,EPS,IMMO
								params.add(result[1]);//四位设置不足补0。从左到右 BCM,TCU,ICM,APM						例如：10100000 ==> BCM,ICM
								String cmdsn = SystemConst.clienthandler.SendCommand(callletterList, 0x00B3, params, true);
								if (!"".equals(cmdsn)) {
									vc.setSend_setconf_count(vc.getSend_setconf_count() + 1);
									System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 第"+ (vc.getSend_setconf_count()) + "次设置配置指令下发， sn="+ cmdsn);
									SystemConst.hm_vehicleconf_cache.put(key, vc);
									return cmdsn;
								}else{
									System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 发送设置配置指令失败");
								}
							}else{
								System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 配置简码为空！");
							}
						}else {
							System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " 已有配置简码 code1:" + vc.getCode1());
				        }
					}else{
						System.out.println(DateUtil.formatNowTime() + " 呼号：" + vc.getCall_letter() + " sim不在缓存中！");
					}
				}
				break;
			}
		}
		return "";
	}
	
	/**
	 * 获取Long对应的二进制字符串数组
	 * @param val
	 * @return
	 */
	private static String[] getConf(Long val) {
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
	    String[] arrs = {"",""};
	    arrs[0] = binaryStr.substring(0, 8);
	    arrs[1] = binaryStr.substring(8, 16);
	    return arrs;
	}

	
	public static boolean is_overtime(Date stamp, int n) {
		Date now = new Date();
		if(stamp==null){
			return true;
		}
		long between = (now.getTime() - stamp.getTime());
//		System.out.println("===time：："+between);
		if (between >= n) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		showConfMap();
	}
	
	public static void showConfMap(){
		ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache = SystemConst.hm_vehicleconf_cache;
		ConcurrentHashMap<String, Upgrade> hm_call_letter_upgradeAll = SystemConst.hm_call_letter_upgradeAll;
		for( Iterator<String>  it = hm_vehicleconf_cache.keySet().iterator();it.hasNext();){
			String key = it.next();
			VehicleConf vc = hm_vehicleconf_cache.get(key);
			if(vc.getFlag() != 4 ){
				Upgrade up = hm_call_letter_upgradeAll.get(key);
				if(up!=null ){
					String sendcommand_version = Config.getCmdProperties().getProperty("sendcommand_version");
					if(up.getCur_ver()!=null && up.getCur_ver().compareTo(sendcommand_version) >= 0 ){
						System.out.println("["+up + "]=====[" + vc +"]");
					}
				}
			}
		}
	}

}
