/*
********************************************************************************************
Discription:  通信中心JMX监控StandardMBean
			  
Written By:   ZXZ
Date:         2014-07-18
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.comcenter.jmxtool;

public interface GbossControlMBean {
	
	public String getStarttime();
	public int getAllClientcount();			//读所有终端信息的APP数量
	public int  getClientMaxCount();		//最大连接数
	public int[] getClientCountList();		//最近一天的客户端数(10分钟一个点) 共144个点

	public long getGPSTotalCount();		//上传GPS总数
	public int getGPS10mCount();		//上传GPS(10分钏内的最大数)
	public int getGPSMaxCount();		//最大待处理GPS数量
	public int[] getGPSCountList();		//最近一天的待处理GPS数(10分钟一个点) 共144个点

	public long getAlarmTotalCount();	//上传Alarm总数
	public int getAlarm10mCount();		//上传Alarm(10分钟内最大数)
	public int getAlarmMaxCount();		//最大待处理Alarm数量
	public int[] getAlarmCountList();	//最近一天的待处理Alarm数(10分钟一个点) 共144个点

	public String getMaxLoginoutTime();	//终端登退录和服务器相差最大时间
	public void ClearMaxLoginoutTime();	//终端登退录和服务器相差最大时间

	public int getCommand10mCount();	//上传Command(10分钟内最大数)
	public int getCommandMaxCount();	//最大待处理Command数量
	public int[] getCommandCountList();	//最近一天的待处理Command数(10分钟一个点) 共144个点
	public long getCommandTotalCount();	//下发命令总数
	public int getCommandCount();	//下发命令总数
	public int getDBCommandMaxCount();	//保存命令队列缓存最大数
	public int getDBCommandCount();	//保存命令队列缓存最大数
	
	public int getUnitInfoCount();
	
	public int getOutInterCallletterCount();
	/* 发关人工编码命令
	 * callletters: 多个终端呼号
	 * content: 发送内容:0x开始表示HEX码, 其他表示源码
	 * gatewayId: 如果是短信网关，必须输入正确，网络，则为0
	 */
	public boolean SendCommand(String callletters, String content, int gatewayId); 
	
	/*
	 * 为了更新参数后，减少重启，使警情丢失，用Jmx强制读参数
	 * 重新读数据库参数, 或从Memcache读参数
	 */
	public void RefreshDBParams();
	
	/*
	 * 取某终端在内存中的信息
	 */
	public String getUnitInfo(String callletter);
}
