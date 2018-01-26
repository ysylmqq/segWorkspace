/*
********************************************************************************************
Discription:  读GPS历史信息 
			  
Written By:   ZXZ
Date:         2014-06-04
Version:      1.0

Modified by:  
Modified Date:
Version:
********************************************************************************************
*/
package cc.chinagps.gboss.hbase;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.GetHistoryInfo_ACK;
import cc.chinagps.gboss.comcenter.buff.ResultCode;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsReferPosition;

public class GpsHistoryInfo extends HbaseHistoryInfo {

	public GpsHistoryInfo(HConnection connection) {
		super(connection);
		super.tablename = "t_history";
	}

	@Override
	protected int getHistoryInfo1(ReqHistoryInfo reqinfo, Result rs, GetHistoryInfo_ACK.Builder historyack) {
		try {
			//判断呼号是否正常
			String callletter1 = new String(rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter));
			if (!callletter1.equals(reqinfo.callletter)) {
				return ResultCode.Hbase_Error;
			}
			//gps baseinfo
			byte[] baseData = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_base);
			//参考位置
			byte[] referposition = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_referposition);
			//源码
			//byte[] content = rs.getValue(GpsLastInfo.family, GpsLastInfo.qualifier_content);
		
			GpsInfo.Builder gpsinfo = historyack.addGpsesBuilder();
			gpsinfo.setCallLetter(reqinfo.callletter);
			gpsinfo.setBaseInfo(GpsBaseInfo.parseFrom(baseData));
			if (referposition != null && referposition.length > 0) {
				GpsReferPosition refer = GpsReferPosition.parseFrom(referposition);
				gpsinfo.setReferPosition(refer);
			}
			//GPS历史回放，不带源码
			//if (content != null && content.length > 0) {
			//	gpsinfo.setContent(ByteString.copyFrom(content));
			//}
			
			//2016-10-29返回入库时间
			historyack.addTimestamps(rs.getColumnLatestCell(GpsLastInfo.family, GpsLastInfo.qualifier_callLetter).getTimestamp());
			
			return ResultCode.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultCode.Hbase_Error;
	}
	/*
	 * 
	String str = "中国"; 
         String[] encoding = { "Unicode", "UnicodeBig", "UnicodeLittle", "UnicodeBigUnmarked", 
         "UnicodeLittleUnmarked", "UTF-16", "UTF-16BE", "UTF-16LE" }; 
         
         for (int i = 0; i < encoding.length; i++) { 
         System.out 
         .printf("%-22s %s%n", encoding[i], bytes2HexString(str.getBytes(encoding[i]))); 
         } 
    } 
	byte[] bufs = str.getBytes(str,"GBK");//将utf-8转换为gbk
	
	String str1 = new String(bufs,"GBK");//此时str1就是GBK类型的数据了
	
	//------------------------------------------------
	byte[] buf = str.getBytes(str,"GBK");//将gbk类型的数据生成到字节数组中
	String str1 = new String(buf,"UTF-8");//生成utf-8类型数据

	//判断当前字符串的编码格式
	if(destination.equals(new String(destination.getBytes("iso8859-1"), "iso8859-1")))
	{
　　		destination=new String(destination.getBytes("iso8859-1"),"utf-8");
	}	
	*/
}
