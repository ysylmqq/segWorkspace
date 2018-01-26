/*
********************************************************************************************
Discription:  客户端读历史信息的临时队列单元信息 
			  
Written By:   ZXZ
Date:         2014-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import org.apache.hadoop.hbase.client.ResultScanner;

import cc.chinagps.gboss.comcenter.WebsocketClientInfo;

public class ReqHistoryInfo {
	WebsocketClientInfo clientinfo;
	String callletter;
	String sn;
	long starttime;
	long endtime;
	int pagenumber;
	int totalnumber;
	boolean autonext;	//开始表示是否自动回复下一页，如果是不自动，以后表示客户端是否请求下一页
	boolean readstop;	//是否结束读历史信息
	boolean reversed;
	boolean norepeat;
	int infotype;	//请求类型
	int readnumber;	//已读数量
	int nexttimes;  //申请未回复下一页次数
	long reqtime;	//请求开始时间, 如果30分钟内没有请求下一页，则自动结束历史请求
	ResultScanner rsscan;	//保存被
	
	public ReqHistoryInfo(WebsocketClientInfo clientinfo, String callletter, int infotype, 
			long starttime, long endtime, int pagenumber, int totalnumber, boolean autonextpage,
			String sn, boolean reversed, boolean norepeat) {
		this.clientinfo = clientinfo;
		this.callletter = callletter;
		this.infotype = infotype;
		this.starttime = starttime;
		this.endtime = endtime;
		this.pagenumber = pagenumber;
		this.totalnumber = totalnumber;
		this.autonext = autonextpage;
		this.sn = sn;
		this.readnumber = 0;
		this.nexttimes = 0;
		this.reqtime = System.currentTimeMillis();
		this.rsscan = null;
		this.readstop = false;
		this.reversed = reversed;
		this.norepeat = norepeat;
	}
	
	public void stop() {
		this.readstop = true;
		if (rsscan != null) {
			rsscan.close();
		}
	}
}
