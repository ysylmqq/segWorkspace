/*
********************************************************************************************
Discription:  通信中心单元测试工具，用线程向Hbase写故障
			  
			  
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

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.FaultDefine;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.FaultInfo;
import cc.chinagps.gboss.hbase.FaultLastInfo;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class WriteHbaseFault extends WriteHbase {

	public WriteHbaseFault(String callletters, long starttime, long stoptime, int interval) throws IOException {
		super("t_fault", callletters, starttime, stoptime, interval);
	}

	@Override
	public void run() {
		ArrayList<Put> putlist = new ArrayList<Put>();
		try {
			for(long l=starttime; l<=stoptime; l+=interval) {
				for(int i=0; i<callletterlist.length; i++) {
					FaultInfo.Builder faultinfo = FaultInfo.newBuilder();
					faultinfo.setCallLetter(callletterlist[i]);
					faultinfo.setFaultTime(l);
					FaultDefine.Builder faultdefine = FaultDefine.newBuilder();
					faultdefine.setFaultType(0);
					faultdefine.addFaultCode("P0301");
					faultdefine.addFaultCode("P0325");
					faultdefine.addFaultCode("P0400");
					faultdefine.addFaultCode("P0520");
					faultinfo.addFaults(faultdefine.build());
					byte[] key = HBaseKeyUtil.getKey(callletterlist[i], l);
					Put put = new Put(key);
					put.add(FaultLastInfo.family, FaultLastInfo.qualifier_callLetter, callletterlist[i].getBytes());
					byte[] data = faultinfo.build().toByteArray();
					put.add(FaultLastInfo.family, FaultLastInfo.qualifier_data, data);
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
			JOptionPane.showMessageDialog(null, "插入故障到Hbase结束");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "插入故障到Hbase失败");
		}
	}

}
