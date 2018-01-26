package cc.chinagps.seat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.gboss.comcenter.buff.GBossDataBuff;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.GpsBaseInfo;
import cc.chinagps.seat.bean.GpsBasicInformation;
import cc.chinagps.seat.bean.HistoryTrack;
import cc.chinagps.seat.bean.table.AlarmStatusTable;
import cc.chinagps.seat.dao.AlarmDao;
import cc.chinagps.seat.dao.HBaseDao;
import cc.chinagps.seat.hbase.HBaseUtil;
import cc.chinagps.seat.hbase.ResultsExtractor;
import cc.chinagps.seat.service.VehicleMonitorService;
import cc.chinagps.seat.util.Consts;
import cc.chinagps.seat.util.HttpClientUtil;
import cc.chinagps.seat.util.PropertiesReader;

@Service
public class VehicleMonitorServiceImpl implements VehicleMonitorService {
	
	private static String URI_LOCATION = 
			"http://api.map.baidu.com/geocoder/v2/?ak=CFc17969f0cc5f894f15ec69a2be4344&output=json&coordtype=wgs84ll&location=";
	
	@Autowired
	private HBaseDao exportDao;
	
	@Autowired
	private AlarmDao alarmDao;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<GpsBasicInformation> getGpsBasicInfoList(HistoryTrack historyTrack) throws Exception {
		List<GpsBaseInfo> baseInfoList = exportDao.getGpsBasicInfoList(
			"t_history", historyTrack, new ResultsExtractor<List<GpsBaseInfo>>() {
				@Override
				public List<GpsBaseInfo> extractData(ResultScanner scanner)
						throws Exception {
					List<GpsBaseInfo> infoList = new ArrayList<GpsBaseInfo>();
					for (Result result : scanner) {
						byte[] value = result.getValue(
								Bytes.toBytes("gps"), 
								Bytes.toBytes("baseinfo"));
						GpsBaseInfo info = GBossDataBuff.GpsBaseInfo.PARSER.parseFrom(
								value);
						infoList.add(info);
					}
					return infoList;
				}
		});
		Map<Long, AlarmStatusTable> statusMap = 
				alarmDao.getAlarmStatus();
		
		List<GpsBasicInformation> infoList = new ArrayList<GpsBasicInformation>(baseInfoList.size());
		for (GpsBaseInfo baseInfo : baseInfoList) {
			GpsBasicInformation info = new GpsBasicInformation();
			info.setBaseInfo(baseInfo);
			
			String[] status = new String[baseInfo.getStatusCount()];
			for (int i = 0; i < status.length; i++) {
				status[i] = statusMap.get((long)baseInfo.getStatus(i))
						.getName();
			}
			info.setStatus(status);
			infoList.add(info);
		}
		if (historyTrack.isExportRefPos()) {
			Properties properties = PropertiesReader.readProperties(
					Consts.PROPERTIES_MISC);
			if(Boolean.parseBoolean(properties.getProperty(
					"proxy.use"))){					
				String url = properties.getProperty("proxy.url");
				String host = properties.getProperty("proxy.host");
				HttpClient httpClient = new HttpClient();
				httpClient.getHostConfiguration().setProxy(url, Integer.parseInt(host));
				httpClient.getParams().setAuthenticationPreemptive(true);
			    if(	Boolean.parseBoolean(properties.getProperty(
						"proxy.use.username"))){
			    	String username = properties.getProperty("proxy.username");
					String password = properties.getProperty("proxy.password");
			    	httpClient.getState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));
						}
			    DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
			}
			HttpGet get;
			for (GpsBasicInformation info : infoList) {
				GpsBaseInfo baseInfo = info.getBaseInfo();
				double lat = HBaseUtil.translateLngLat(baseInfo.getLat());
				double lng = HBaseUtil.translateLngLat(baseInfo.getLng());
				String uri = URI_LOCATION + lat + "," + lng;				
				get = new HttpGet(uri);
				try {
				Map<String, Object> result = HttpClientUtil.execute(get);
				if (String.valueOf(result.get("status")).equals("0")) {
					result = (Map<String, Object>) result.get("result");
					info.setReferPosition((String) result.get("formatted_address"));
				}
				} catch (Exception e) {
					
				}
			}
		}
		
		return infoList;
	}
}
