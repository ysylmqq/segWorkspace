/*
********************************************************************************************
Discription:  通信中心单元测试工具，用线程向Hbase写短信
			  
			  
Written By:   ZXZ
Date:         2014-05-22
Version:      1.0

Modified by:
Modified Date:
Version:
********************************************************************************************
*/
package testtools;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.hadoop.hbase.client.Put;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.ShortMessage;
import cc.chinagps.gboss.hbase.SmsLastInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;
public class WriteHbaseSms extends WriteHbase {

	public WriteHbaseSms(String callletters, long starttime, long stoptime, int interval) throws IOException {
		super("t_sm", callletters, starttime, stoptime, interval);
	}

	@Override
	public void run() {
		ArrayList<Put> putlist = new ArrayList<Put>();
		try {
			for(long l=starttime; l<=stoptime; l+=interval) {
				for(int i=0; i<callletterlist.length; i++) {
					ShortMessage.Builder smsinfo = ShortMessage.newBuilder();
					smsinfo.setCallLetter(callletterlist[i]);
					smsinfo.setMsg("赛格导航欢迎您！联系电话 - 075526719888, 952100");
					smsinfo.setRecvTime(System.currentTimeMillis());

					byte[] key = HBaseKeyUtil.getKey(callletterlist[i], l);
					Put put = new Put(key);
					put.add(SmsLastInfo.family, SmsLastInfo.qualifier_callLetter, callletterlist[i].getBytes());
					byte[] data = smsinfo.build().toByteArray();
					put.add(SmsLastInfo.family, SmsLastInfo.qualifier_data, data);
					putlist.add(put);
					if (putlist.size() >= 1000) {
						table.put(putlist);
						table.flushCommits();
						putlist.clear();
					}
				} //for(callletterlist)
			} //for(long)
			if (putlist.size() > 0) {
				table.put(putlist);
				table.flushCommits();
				putlist.clear();
			}
			JOptionPane.showMessageDialog(null, "插入短信到Hbase结束");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "插入短信到Hbase失败");
		}
	}

}
