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

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.ComCenterMessage.ComCenterBaseMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitVersion;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.UnitVersion;
import cc.chinagps.gboss.comcenter.buff.ResultCode;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.VehicleConf;
import com.gboss.service.UpgradeService;
import com.gboss.util.CommandSendHelper;
import com.gboss.util.DateUtil;
import com.gboss.util.SpringContext;

public class DeliverUnitVersionHandler extends ClientBaseHandler {

	public String callLetter = ""; // 车辆呼号
	public String version = ""; // 版本号
    public int result = 1;//0成功，其它失败

	public DeliverUnitVersionHandler(ComCenterBaseMessage basemsg,
			CenterClientHandler handler) {
		super(basemsg, handler);
	}

	@Override
	public int decode() {
		try {
			DeliverUnitVersion duv = DeliverUnitVersion.parseFrom(msgcontent);
			UnitVersion unitVersion = duv.getUnitVersion();
			if (null != unitVersion) {
				if (unitVersion.hasCallLetter())
					callLetter = unitVersion.getCallLetter();
				if (unitVersion.hasVersion())
					version = unitVersion.getVersion();
				if(unitVersion.hasResult())
				    result = unitVersion.getResult();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			retcode = ResultCode.Decode_Error;
			retmsg = "解码失败";
		}
		return retcode;
	}

	@Override
	public void run() {
		ConcurrentHashMap <String, Upgrade> table = SystemConst.hm_call_letter_upgrade;
		//配置信息缓存
		ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache = SystemConst.hm_vehicleconf_cache;
		
		Upgrade up = (Upgrade) table.get(callLetter);
		if (up != null) {
			/*System.out.println("时间:" + DateUtil.formatNowTime()
					+ "升级成功处理的run方法！");*/
			UpgradeService upgradeService = (UpgradeService) SpringContext
					.getBean("upgradeService");
			

			/*System.out.println("时间:" + DateUtil.formatNowTime() + "要求升级版本号："
					+ up.getUg_ver() + "当前版本号：" + version
					+ "------------------------");*/
			// 在升级队列的呼号升级成功处理
			if (result == ResultCode.OK) {
				up.setCur_ver(version);
				up.setUg_ver("");
				up.setFlag(6);
				up.setE_time(new Date());
				upgradeService.update(up);
				System.out.println(DateUtil.formatNowTime() + " 呼号："
						+ callLetter + "升级成功！");
				// 已经成功的从升级队列去掉
				table.remove(callLetter);
				
				//发送查询配置指令
				VehicleConf vc = hm_vehicleconf_cache.get(callLetter);
				CommandSendHelper.sendCommand(up, vc, SystemConst.QUERYCONF_CMD);
				
				//从监控列表移除
				ArrayList<String> callletterlist = new ArrayList<String>();
				callletterlist.add(callLetter);
				ArrayList<Integer> infotypes = new ArrayList<Integer>();
				infotypes.add(-1);
				SystemConst.clienthandler.RemoveMonitor(callletterlist, infotypes);
			} else {			//失败
				System.out.println(DateUtil.formatNowTime() + " 呼号："
						+ callLetter + "升级失败");
				up.setFlag(5);
				up.setS_time(null);
				upgradeService.update(up);
				table.put(callLetter, up);
			}
		}
	}
}
