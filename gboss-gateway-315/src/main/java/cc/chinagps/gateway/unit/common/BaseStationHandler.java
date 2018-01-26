package cc.chinagps.gateway.unit.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import cc.chinagps.gateway.StartServer;
import cc.chinagps.gateway.buff.GBossDataBuff.BaseStationAddress;
import cc.chinagps.gateway.buff.GBossDataBuff.BaseStationInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.GpsInfo;
import cc.chinagps.gateway.buff.GBossDataBuff.OBDInfo;
import cc.chinagps.gateway.log.LogManager;
import cc.chinagps.gateway.unit.beans.GbossGpsInfo;
import cc.chinagps.gateway.unit.beans.LocRadiusPoiResponse;
import cc.chinagps.gateway.unit.seg.pkg.SegPackage;
import cc.chinagps.gateway.unit.seg.upload.beans.SegGPSInfo;
import cc.chinagps.gateway.unit.seg.util.SegPkgUtil;
import cc.chinagps.gateway.unit.seg.util.SegProtobufUtil;
import cc.chinagps.gateway.util.HexUtil;
import cc.chinagps.gateway.util.HttpClientPoolUtil;
import cc.chinagps.gateway.util.UnitProtocolTypes;

public class BaseStationHandler{
	private List<GbossGpsInfo> gpsInfos = new ArrayList<GbossGpsInfo>();
	private Object lock = new Object();
	private int index;
	private String url = "http://minigps.net/cw?p=1&needaddress=0&mt=0";
	private Logger logger_debug = Logger.getLogger(LogManager.LOGGER_NAME_DEBUG);
	private int maxCacheSize = 5000;
	private int tryTime = 3;
	private int tryInterval = 3;

	public BaseStationHandler(int index, String url) {
		this.index = index;
		this.url = url;
	}
	
	public BaseStationHandler(int index, String url,int cacheSize) {
		this.index = index;
		this.url = url;
		this.maxCacheSize = cacheSize;
	}

	public boolean addGps(GbossGpsInfo gps) {
		synchronized (lock) {
			if (gpsInfos.size() >= maxCacheSize) {
				logger_debug.debug("BaseStationHandler " + index + " throw a agps.");
				return false;
			}
			logger_debug.debug("BaseStationHandler " + index + " recev a agps info:" + gps);
			gpsInfos.add(gps);
			logger_debug.debug("BaseStationHandler " + index + " GPSInfos size:" + gpsInfos.size());
			lock.notifyAll();
			return true;
		}
	}
	

	public List<GbossGpsInfo> getAllGps() {
		synchronized (lock) {
			while (gpsInfos.size() == 0) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			List<GbossGpsInfo> temp = gpsInfos;
			gpsInfos = new ArrayList<GbossGpsInfo>();

			return temp;
		}
	}
	
	
	public void startService() {
		saveThread = new SaveThread();
		saveThread.start();
	}

	private SaveThread saveThread;

	private class SaveThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					List<GbossGpsInfo> list = getAllGps();
					int size = list.size();
					logger_debug.debug("BaseStationHandler " + index + " begin to handle agps count:" + size);
					for (GbossGpsInfo gps : list) {
						try {
							switch (gps.getProtocolType()) {
							case UnitProtocolTypes.UNIT_PROTOCOL_TYPE_T3688:
								setBaseStationAddress(gps);
								GpsInfo gpsInfo = gps.getGpsBuilder().build();
								GpsBaseInfo baseInfo = gpsInfo.getBaseInfo();
								
								StartServer.instance.getUnitServer().getExportMQ().addGPS(gpsInfo);
								logger_debug.debug("BaseStationHandler save agps info to mq finished!");
								StartServer.instance.getUnitServer().getExportHBase().addGPS(gpsInfo.getCallLetter(), baseInfo,
										gpsInfo.getContent().toByteArray());
								if(baseInfo.hasObdInfo()){
									OBDInfo obdInfo = baseInfo.getObdInfo();
									StartServer.instance.getUnitServer().getExportMQ().addOBDInfo(obdInfo);
								}
								break;
							case UnitProtocolTypes.UNIT_PROTOCOL_TYPE_KELONG:{
								setBaseStationAddress(gps);
								GpsInfo klgpsInfo = gps.getGpsBuilder().build();
								GpsBaseInfo klbaseInfo = klgpsInfo.getBaseInfo();
								//1.将科隆的gps信息存入Mq
								StartServer.instance.getUnitServer().getExportMQ().addGPS(klgpsInfo);
								
								//2.将科隆的gps信息存入hbase
								StartServer.instance.getUnitServer().getExportHBase().addGPS(klgpsInfo.getCallLetter(), klbaseInfo,
										klgpsInfo.getContent().toByteArray());
								logger_debug.debug("[KeLong]BaseStationHandler save agps info to mq and hbase finished!klgpsInfo：" + klgpsInfo);
								
								if(klbaseInfo.hasObdInfo()){
									OBDInfo obdInfo = klbaseInfo.getObdInfo();
									StartServer.instance.getUnitServer().getExportMQ().addOBDInfo(obdInfo);
								}								
							}break;
							default:
								break;
							}
							
						} catch (Exception e) {
							// TODO: handle exception
							StartServer.instance.getUnitServer().exceptionCaught(e);
						}
					}

				} catch (Throwable e) {
					StartServer.instance.getUnitServer().exceptionCaught(e);
				}
			}
		}
	}

	public void setBaseStationAddress(GbossGpsInfo gps) {
		String minigpsUrl = null;
		try {
			GpsBaseInfo.Builder gpsBaseBuilder = gps.getGpsBuilder().getBaseInfoBuilder();
			//GpsBaseInfo.Builder baseInfobuild = GpsBaseInfo.newBuilder();
			//baseInfobuild.mergeFrom(gpsBase.toByteArray());
			LocRadiusPoiResponse loc = null;
			GpsBaseInfo gpsBase = gps.getGps().getBaseInfo();
			List<BaseStationInfo> list = gpsBase.getBaseStationsList();
			if (list == null || list.size() == 0)
				return ;
			
			String x = getXParameter(list);
			minigpsUrl = url + "&x=" + x;
			int tryT = 0;
			String result = null;
			CloseableHttpClient httpClient = HttpClientPoolUtil.getHttpClient(minigpsUrl);
			while (tryT <= tryTime) {
				if (tryT != 0) {
					Thread.sleep(tryInterval * tryT * 1000);
				}
				result = HttpClientPoolUtil.getRequest(httpClient,minigpsUrl,"application/json;charset=utf-8",null);
				//result = HttpUtil.request(minigpsUrl, "GET", "application/json;charset=utf-8", null);
				logger_debug.debug("[basestation]Calletter:"+ gps.getGps().getCallLetter() + ";Full url :" + minigpsUrl + ";tryTime:" + tryT
						+ ";LocRadiusPoiResponse result:" + result);
				if (result == null||"".equals(result)) {
					tryT++;
				} else {
					Gson gson = new Gson();
					try {
						loc = gson.fromJson(result, LocRadiusPoiResponse.class);
					} catch (Exception e) {
						// TODO: handle exception
						StartServer.instance.getUnitServer().exceptionCaught(e);
					}
					if (loc == null) {
						tryT++;
					} else {
						BaseStationAddress.Builder builder = BaseStationAddress.newBuilder();
						Double lat = loc.getLat();
						lat = lat * 1000000;
						Double lng = loc.getLon();
						lng = lng * 1000000;
						builder.setLat(lat.intValue());
						builder.setLng(lng.intValue());
						gpsBaseBuilder.setAddress(builder);
						if (gps.getProtocolType() == UnitProtocolTypes.UNIT_PROTOCOL_TYPE_T3688){
							gpsBaseBuilder.setGpsTime(new Date().getTime());
						}
						if(gps.getProtocolType() == UnitProtocolTypes.UNIT_PROTOCOL_TYPE_KELONG){//科隆根据基站转化的gps包或者不定位的包
							//设置gps的经纬度
							gpsBaseBuilder.setLat(lat.intValue());
							gpsBaseBuilder.setLng(lng.intValue());							
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			StartServer.instance.getUnitServer().exceptionCaught(e);
		}
	}

	public String getXParameter(List<BaseStationInfo> baseStationInfos) {// mcc-mnc
		String x = "%x-%x";
		int index = 0;
		for (BaseStationInfo baseStationInfo : baseStationInfos) {
			if (index == 0) {
				x = String.format(x, Integer.valueOf(baseStationInfo.getMcc()), Integer.valueOf(baseStationInfo.getMnc()));
			}
			x += "-%x-%x-%x";
			x = String.format(x, baseStationInfo.getLac(), baseStationInfo.getCid(), baseStationInfo.getBsss());
			index++;
		}
		return x;
	}

	public int getGpsCount() {
		int count = gpsInfos.size();
		System.out.println("BaseStationHandler " + index + " gps count:" + count);
		return count;
	}
	
	public static void main(String[] args) throws Exception {
		String s = "5b93303130303130303731344c2849545630393334323141323234302e393839324e31313431362e36383130453030302e3430303232303431363030303030303030264b3130303038424244412c4b353030303030303030295d";
		s = "5b9330313030313030313136932849545630323036333641323533332e393235344e31303331352e37343437453030302e3130303035303131373030303030303030264b3130303038383346302c4b353030303030303030264a30393834363030302c33353036322c363637332c35372c37352c33353036322c363637312c35302c33353036322c33343033372c34352c33353036322c33343033382c3432295d";
		byte data[] = HexUtil.hexToBytes(s);
		SegPackage pkg = SegPackage.parse(data);
		String strBody = new String(pkg.getBody(), SegPkgUtil.PKG_CHAR_ENCODING);
		System.out.println("strBody:" + strBody);
		SegGPSInfo gps = SegGPSInfo.parse(strBody, 4);
		System.out.println(gps);

		String callLetter = "64853548462";
		final GpsInfo.Builder gpsInfoBuilder = SegProtobufUtil.transformGPSInfoBuilder(callLetter, gps, pkg);
		final GpsInfo gpsInfo = gpsInfoBuilder.build();
		StartServer.instance.start();
		String url = "http://116.7.230.213:9020/gps/basestation?p=1&needaddress=0&mt=0";
		final BaseStationHandler handler = new BaseStationHandler(0,url);
		handler.startService();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					handler.addGps(new GbossGpsInfo(gpsInfoBuilder, gpsInfo, UnitProtocolTypes.UNIT_PROTOCOL_TYPE_T3688));
					try {
						Thread.sleep(1000000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
