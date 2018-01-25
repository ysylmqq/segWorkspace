/*
 ********************************************************************************************
Discription:  通信中心单元测试工具用
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
 ********************************************************************************************
 */
package cc.chinagps.gboss.comcenter.interprotocol.clienttest;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverECUConfig;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.ECUConfig;
import cc.chinagps.gboss.comcenter.buff.ResultCode;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.VehicleConf;
import com.gboss.service.VehicleConfService;
import com.gboss.util.CommandSendHelper;
import com.gboss.util.DateUtil;
import com.gboss.util.SpringContext;

public class DeliverECUConfigHandler extends ClientBaseHandler {

	public String callLetter = ""; // 车辆呼号
	/**
	 * 子系统配置, 每1位表示1个子系统, 1=有, 0=无, 
	 * 从低位1到高位64依次为
	 * ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM
	 */
	public StringBuffer code1 = new StringBuffer();
	
	//体检开关 0关闭  ；1 开启 
	private int is_on = 0; 

	public DeliverECUConfigHandler(ComCenterBaseMessage basemsg, CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverECUConfig decuConfig = DeliverECUConfig.parseFrom(msgcontent);
			ECUConfig  ecuConfig = decuConfig.getEcuConfig();
			
			if (ecuConfig.hasCallLetter())
				callLetter = ecuConfig.getCallLetter();
			
			if (ecuConfig.hasAbs())
				code1.append(ecuConfig.getAbs());
			if (ecuConfig.hasEsp())
				code1.append(ecuConfig.getEsp());
			if (ecuConfig.hasPeps())
				code1.append(ecuConfig.getPeps());
			if (ecuConfig.hasTpms())
				code1.append(ecuConfig.getTpms());
			if (ecuConfig.hasSrs())
				code1.append(ecuConfig.getSrs());
			if (ecuConfig.hasEps())
				code1.append(ecuConfig.getEps());
			if (ecuConfig.hasEms())
				code1.append(ecuConfig.getEms());
			if (ecuConfig.hasImmo())
				code1.append(ecuConfig.getImmo());
			if (ecuConfig.hasBcm())
				code1.append(ecuConfig.getBcm());
			if (ecuConfig.hasTcu())
				code1.append(ecuConfig.getTcu());
			if (ecuConfig.hasIcm())
				code1.append(ecuConfig.getIcm());
			if (ecuConfig.hasApm())
				code1.append(ecuConfig.getApm());
			
			if(ecuConfig.hasCheckFlag())
				is_on = ecuConfig.getCheckFlag();

		} catch (Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		/**
		 * 数据两种来源
		 * 		1查询结果处理 
		 * 		2自动上报处理 *
		 */
		ConcurrentHashMap <String, VehicleConf> hm_vehicleconf_cache = SystemConst.hm_vehicleconf_cache;	
		VehicleConf vc = hm_vehicleconf_cache.get(callLetter);
		if( vc== null ){
			vc = new VehicleConf();
			vc.setCall_letter(callLetter);
			vc.setS_time(new Date());//上报时间
			System.out.println(DateUtil.formatNowTime() + " 呼号：" + callLetter + "自动上报数据!");
		}
		Long code = Long.valueOf(0L);
		code1  = code1.reverse();
		if(code1.length()!=0){
			code = Long.valueOf(code1.toString(),2);
		}
		
		ConcurrentHashMap <String, Upgrade> hm_call_letter_upgradeAll= SystemConst.hm_call_letter_upgradeAll;		
		if(code.longValue() == 0L ){//查询或者是上报的数据为空
			//需要下发设置指令
			Upgrade up = hm_call_letter_upgradeAll.get(callLetter);
			if( up != null ){
				System.out.println(DateUtil.formatNowTime() + " 呼号：" + callLetter + " 查询配置 code=0， 发送设置配置指令开始!");
				CommandSendHelper.sendCommand(up, vc, SystemConst.SETCONF_CMD);
			}
			vc.setCode1(Long.valueOf(0L));
		}else {//返回有值，更新t_ba_vehicle_conf 的 code1和flag字段
			vc.setFlag(4);
			vc.setE_time(new Date());
			
//			vc.setIs_on(1);
//			if(code != vc.getCode1()){//不相同就更新
//				vc.setCode1(code);
//			}
			
			vc.setIs_on(is_on);//根据上报情况设值
			if(code.longValue() != vc.getCode1().longValue()){//不相同就更新
				vc.setCode1(code);
			}
			
			System.out.println(DateUtil.formatNowTime() + " 呼号：" + callLetter + "设置配置指令成功!");
		}
		//把新的对象插放入缓存和数据库
		VehicleConfService vehicleConfService = (VehicleConfService) SpringContext.getBean("vehicleConfService");
		vehicleConfService.saveOrUpdate(vc);
		hm_vehicleconf_cache.put(callLetter, vc);
	}

}
