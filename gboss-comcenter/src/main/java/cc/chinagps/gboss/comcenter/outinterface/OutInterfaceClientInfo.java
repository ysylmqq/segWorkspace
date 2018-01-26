/*
********************************************************************************************
Discription: 对外接口客户端基本信息，继承于客户端的基本信息，
                          主要是不用添加监控列表，直接按权限分配终端递交信息
			  
Written By:   ZXZ
Date:         2015-03-01
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.outinterface;

import cc.chinagps.gboss.comcenter.UnitInfoManager;
import cc.chinagps.gboss.comcenter.WebsocketClientInfo;
import cc.chinagps.gboss.comcenter.UnitInfoManager.UnitInfo;

public class OutInterfaceClientInfo extends WebsocketClientInfo {
	
	private String orgnos;
	
	public OutInterfaceClientInfo() {
	}
	
	public void setOrgnos(String orgno) {
		this.orgnos = orgno;
	}
	
	public String getOrgnos() {
		return orgnos;
	}
	
	public boolean hasCallletter(String callletter) {
		if (orgnos == null) {
			return false;
		}
		UnitInfo unitinfo = UnitInfoManager.unitinfomanager.getUnitInfo(callletter);
		if (unitinfo == null) {
			return false;
		}
		return unitinfo.orgcodes.startsWith(orgnos);
	}
}
