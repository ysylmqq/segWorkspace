package testtools;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.hadoop.hbase.client.Put;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gboss.hbase.OBDTable;
import cc.chinagps.lib.util.HBaseKeyUtil;

public class WriteHbaseOBD extends WriteHbase {

	public WriteHbaseOBD(String callletters, long starttime, long stoptime, int interval) throws IOException {
		super("t_obd", callletters, starttime, stoptime, interval);
	}

	@Override
	public void run() {
		ArrayList<Put> putlist = new ArrayList<Put>();
		try {
			for(long l=starttime; l<=stoptime; l+=interval) {
				for(int i=0; i<callletterlist.length; i++) {
					OBDInfo.Builder obdinfo = OBDInfo.newBuilder();
					obdinfo.setCallLetter(callletterlist[i]);
					obdinfo.setRemainOil(12050);
					obdinfo.setRemainPercentOil(501);
					obdinfo.setAverageOil(800);
					obdinfo.setHourOil(1050);
					obdinfo.setTotalDistance(1050000);
					obdinfo.setWaterTemperature(65);

					byte[] key = HBaseKeyUtil.getKey(callletterlist[i], l);
					Put put = new Put(key);
					put.add(OBDTable.family, OBDTable.qualifier_callLetter, callletterlist[i].getBytes());
					byte[] data = obdinfo.build().toByteArray();
					put.add(OBDTable.family, OBDTable.qualifier_data, data);
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
			JOptionPane.showMessageDialog(null, "插入OBD到Hbase结束");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "插入OBD到Hbase失败");
		}
	}

}
