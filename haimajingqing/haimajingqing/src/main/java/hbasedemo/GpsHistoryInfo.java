package hbasedemo;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;

public class GpsHistoryInfo extends DemoHbaseHistoryInfo {
	private static byte[] family = "gps".getBytes();
	private static byte[] qualifier_base = "baseinfo".getBytes();
	private static CopyOnWriteArrayList<GpsBaseInfo> positionInfoList ;
	
	//对外OBD队列, 读取历史的OBD信息都保存在这队列中
	//public ArrayList<OBDInfo> obdlist;
	
	public GpsHistoryInfo(HConnection connection) {
		super(connection);
		//hbase中表名为"t_history"
		super.tablename = "t_history";
		//obdlist = new ArrayList<OBDInfo>();
		positionInfoList = new CopyOnWriteArrayList<GpsBaseInfo>();
	}

	@Override
	protected boolean GetRecordInfo(Result rs) {
		try {
			//判断呼号是否正常
			/*byte[] callletter = rs.getValue(family, qualifier_callLetter);
			String rec_callletter = new String(callletter);
			if (!rec_callletter.equals(super.callletter)) {
				return false;
			}*/
			//Fault data
			byte[] data = rs.getValue(family, qualifier_base);
			 if (data != null) {
				//System.out.println("取得userscore");//obdlist.add(OBDInfo.parseFrom(data));
				GpsBaseInfo userpositionInfo = GBossDataBuff.GpsBaseInfo.parseFrom(data);
				positionInfoList.add(userpositionInfo);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public CopyOnWriteArrayList<GpsBaseInfo> getUserPositionList(){
		return this.positionInfoList;
	}
}