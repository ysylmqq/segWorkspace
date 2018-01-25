package com.chinagps.driverbook.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.chinagps.driverbook.dao.AppBindMapper;
import com.chinagps.driverbook.dao.NoticeHisMapper;
import com.chinagps.driverbook.dao.VehicleMapper;
import com.chinagps.driverbook.pojo.AppBind;
import com.chinagps.driverbook.pojo.NoticeHis;
import com.chinagps.driverbook.pojo.Vehicle;
import com.chinagps.driverbook.protobuf.GBossDataBuff.AppNoticeInfo;
import com.chinagps.driverbook.service.IPushService;
import com.chinagps.driverbook.util.LogManager;
import com.chinagps.driverbook.util.MemcachedUtil;
import com.chinagps.driverbook.util.PropertyUtil;
import com.chinagps.driverbook.util.RequestUtil;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.exceptions.InvalidSSLConfig;
import com.notnoop.exceptions.RuntimeIOException;

@Service("pushService")
@Scope("prototype")
public class PushServiceImpl implements IPushService {
	
	private final static String UB_LOG 		= "nameUnBind";
	private final static String PUSH_LOG 	= "namePush";
	private final static String E_LOG 		= "nameException";
	
	private static final Logger unbind_logger 		= LogManager.getLogger(UB_LOG);
	private static final Logger push_logger 		= LogManager.getLogger(PUSH_LOG);
	private static final Logger exception_logger 	= LogManager.getLogger(E_LOG);
	
	private MemcachedClient mc = MemcachedUtil.getClient(false);
	private static final String API_KEY = "jq0Hc4cGyxGEISbZcG0PoICP";
	private static final String SECRET_KEY = "3xRmGHCwDBGCFZrxsgcBqU0FnffFrszV";
	private static final String HAIMA_API_KEY = "tjK0NA1msBXFkeqtXpNGB6KD";
	private static final String HAIMA_SECRET_KEY = "ANKPqCN1Mk8Gwr0P2kOOQfd0DYUi9xdV";
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private AppBindMapper appBindMapper;
	
	@Autowired
	private VehicleMapper vehicleMapper;

	@Autowired
	private NoticeHisMapper noticeHisMapper;

	@Override
	public void push(Map<String, Object> params) throws Exception {
		push_logger.info("push-意见反馈推送开始");
		Integer origin = Integer.parseInt(params.get("origin").toString());
		String title = params.get("title").toString();
		String content = params.get("content").toString();
		Integer type = Integer.parseInt(params.get("type").toString());
		String uri = params.get("uri").toString();

		List<AppBind> appBinds = appBindMapper.findByCustomerIdsAndOrigin(params);
		for (AppBind appBind : appBinds) {
			if (appBind.getDeviceType() == 0) {
				androidPush(origin, appBind.getChannelId(),
						appBind.getPushUserId(), appBind.getCustomerId(),
						title, content, type, uri, null, null,null);
			} else if (appBind.getDeviceType() == 1) {
				iOSPush(origin, appBind.getDeviceToken(),
						appBind.getCustomerId(), title, content, type, uri,
						null, null,null);
			}
		}
		push_logger.info("push-意见反馈推送结束");
	}

	/**
	 * 离线推送
	 */
	public void notifyCenter(AppNoticeInfo appNoticeInfo) throws Exception  {
		push_logger.info("离线推送开始");
		Map<String, Object> result = new HashMap<String,Object>();
		
		String call_letter = appNoticeInfo.getCallLetter();
		
		List<AppBind> appBinds = appBindMapper.findByCallLetter(call_letter);//
		if(appBinds == null || appBinds.size()==0 ){
			push_logger.debug("离线推送 呼号："+ appNoticeInfo.getCallLetter() +"未获取到APP绑定信息!");
			String msg = "离线推送 :呼号:"+appNoticeInfo.getCallLetter()+" 未绑定";
			result.put("code", 403);
			result.put("success", false);
			result.put("msg", msg);
			unbind_logger.info(msg);
		}
		
		Collections.sort(appBinds, new Comparator<AppBind>() {
			public int compare(AppBind o1, AppBind o2) {
				return o1.getStamp().compareTo(o2.getStamp());
			}
		});
		//获取时间最新的记录
		AppBind appBind = appBinds.get(appBinds.size()-1);
		
		Vehicle vehicle = vehicleMapper.findByCallLetter(appNoticeInfo.getCallLetter());//获取车牌号 和 vin码

		String cache_hold_time_str 	= PropertyUtil.getPropertyValue("comm.properties", "cache_hold_time");
		int cache_hold_time  		= 24;
		try{
			cache_hold_time  		= Integer.parseInt(cache_hold_time_str);
		}catch(Exception e){
			exception_logger.error( "["+SDF.format(new Date()) +"-cache_hold_time 获取失败。");
		}
		
		//appNoticeInfo.getCmdId();//ios那边不清楚是否使用了cmdid字段
		
		//存放在缓存中
//		String 	key = getKey(appNoticeInfo.getCmdsn());//注意是否有后缀 时间戳
		String 	key = appNoticeInfo.getCmdsn();
		int 	exp = cache_hold_time*60*60;
		mc.add(key, exp, appNoticeInfo.getContent().concat(",").concat(appNoticeInfo.getTitle()));
		if(appBind!=null){
			boolean flag = false;
			String msg = "";
			if (appBind.getDeviceType() == 0) {//安卓系统推送
				result = androidPush(appBind.getOrigin(), appBind.getChannelId(),
						appBind.getPushUserId(), appBind.getCustomerId(),
						appNoticeInfo.getTitle(), appNoticeInfo.getContent(), 3,
						"content://notifyCenter", appNoticeInfo.getCallLetter(),
						vehicle.getPlateNo(),key);
				flag = (Boolean) result.get("success");
				msg  += (String)  result.get("msg");
			} else if (appBind.getDeviceType() == 1) {//IOS系统推送
				result = iOSPush(appBind.getOrigin(), appBind.getDeviceToken(),
						appBind.getCustomerId(), appNoticeInfo.getTitle(),
						appNoticeInfo.getContent(), 3, "content://notifyCenter",
						appNoticeInfo.getCallLetter(), vehicle.getPlateNo(),key);
				flag = (Boolean) result.get("success");
				msg  += (String)  result.get("msg");
			}
			
			//web推送
			//判断是否是海马的车
			if(vehicle.getSubcoNo()==201 && StringUtils.hasText(vehicle.getVin())){
				System.out.println( "离线推送[webpush].vin=" + vehicle.getVin() + ",呼号："+ appNoticeInfo.getCallLetter());
				String params = "vin="+vehicle.getVin()+"&title="+appNoticeInfo.getTitle()+"&body="+appNoticeInfo.getContent();
				result = webPush(params);
			}
			
			flag   = (Boolean) result.get("success");
			msg   += ","+(String)  result.get("msg");
			
			if(flag){
				result.put("code", 200);
				result.put("msg", "离线推送成功");
				result.put("success",true);
				push_logger.info("call_letter:"+appNoticeInfo.getCallLetter()+" 离线推送成功！ ");
			}else{
				result.put("code", 500);
				result.put("msg", msg);
				result.put("success",false);
				exception_logger.error("call_letter:"+appNoticeInfo.getCallLetter()+" 离线推送失败！ msg=" + msg);
			}
		}else{
			unbind_logger.debug("离线推送，呼号：" + appNoticeInfo.getCallLetter() + "未绑定!");
		}
		push_logger.info("离线推送结束");
	}
	
	private Map<String, Object> androidPush(Integer origin, Long channelId, String userId,
			Long customerId, String title, String content, Integer type,
			String uri, String callLetter, String plateNo,String cmdsn) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		PushKeyPair pair = null;
		if (origin == 1) {
			pair = new PushKeyPair(API_KEY,SECRET_KEY);
		} else if (origin == 2) {
			pair = new PushKeyPair(HAIMA_API_KEY,HAIMA_SECRET_KEY);
		}
		BaiduPushClient pushClient = new BaiduPushClient(pair,BaiduPushConstants.CHANNEL_REST_URL);
		pushClient.setChannelLogHandler (new YunLogHandler () {
            @Override
            public void onHandle (YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });
		try {
			//待推送消息
			StringBuffer sb = new StringBuffer();
			sb.append("{\"title\":\"").append(title)
			.append("\",\"open_type\":\"").append(2)
			.append("\",\"description\":\"").append(content)
			.append("\",\"custom_content\":{")
			.append("\"type\":").append(type).append(",\"uri\":\"")
			.append(uri).append("\",\"customerId\":\"")
			.append(customerId).append("\"");
			if (callLetter != null) {
				sb.append(",\"callLetter\":\"").append(callLetter).append("\"");
			}
			if (plateNo != null) {
				sb.append(",\"plateNumber\":\"").append(plateNo).append("\"");
			}
				
			if (cmdsn != null) {
				sb.append(",\"cmdsn\":\"").append(cmdsn).append("\"");
			}
			sb.append("}");
			sb.append("}");
			// 4. 设置请求参数，创建请求实例
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                addChannelId(""+channelId).//通道ID
                addMsgExpires(new Integer(7*24*3600)).//消息时效性 7天
                addMessageType(0).//设置消息类型,0表示透传消息,1表示通知,默认为0.
                addMessage(sb.toString()).//消息
                addDeviceType(3);//设置设备类型，deviceType => 1 for web, 2 for pc, 3 for android, 4 for ios, 5 for wp.
            // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
            
            // 6. Http请求返回值解析
			result.put("code", 200);
			result.put("success", true);
			result.put("msg", "安卓推送成功!");
			push_logger.debug("安卓推送成功，呼号："+callLetter + " ,msgId: " + response.getMsgId()  + ",sendTime: " + response.getSendTime() + "推送内容："  + sb);
			return result;
		} catch (PushClientException  e) {
			String msg = "android推送错误:PushClientException";
			exception_logger.error(e.getMessage(), e);
			result.put("code", 500);
			result.put("success", false);
			result.put("msg", msg);
			return result;
		} catch (PushServerException e) {
			String msg = "android推送错误=request_id: "+e.getRequestId()+", error_code: "+e.getErrorCode()+", error_message: " + e.getErrorMsg();
			exception_logger.error(msg);
			result.put("code", 500);
			result.put("success", false);
			result.put("msg", msg);
			return result;
        }
	}

	private Map<String, Object> iOSPush(Integer origin, String deviceToken, Long customerId,
			String title, String content, Integer type, String uri,
			String callLetter, String plateNo,String cmdsn)  {
		Map<String, Object> result = new HashMap<String, Object>();
		ApnsService service = null;
		// 1. 建立连接
			try {
				if (origin == 1) {
				service = APNS.newService().withCert(
						URLDecoder.decode(this.getClass().getClassLoader().getResource("gboss_prod.p12").getPath(),"UTF-8"), "123456")
						.withProductionDestination().build();
				} else if (origin == 2) {
					service = APNS
							.newService()
							.withCert(
									URLDecoder.decode(this.getClass().getClassLoader()
											.getResource("haima_apns_prod.p12").getPath(),
											"UTF-8"), "123456")
											.withProductionDestination().build();
				}
				// 2. 创建并发送消息
				HashMap<String, Object> customMap = new HashMap<String, Object>();
				customMap.put("title", title);
				customMap.put("content", content);
				customMap.put("type", type);
				customMap.put("uri", uri);
				customMap.put("customerId", customerId);
				
				if (callLetter != null) {
					customMap.put("callLetter", callLetter);
				}
				if (cmdsn != null) {
					customMap.put("cmdsn", cmdsn);
				}
				if (plateNo != null) {
					customMap.put("plateNumber", plateNo);
				}
				String payload = APNS.newPayload().alertTitle(title).alertBody(content).sound("default").badge(1).customField("data", customMap).build();
				service.push(deviceToken, payload);
				result.put("code", 200);
				result.put("success", true);
				result.put("msg", "IOS推送成功!");
				push_logger.debug("IOS推送成功，呼号："+callLetter + " 推送内容："  + customMap);
				return result;
			} catch (RuntimeIOException e) {
				exception_logger.error("IOS推送消息失败，异常信息：" + e.getMessage());
				result.put("code", 500);
				result.put("success", false);
				result.put("msg", "IOS推送错误:error_message: " + e.getMessage());
				return result;
			} catch (InvalidSSLConfig e) {
				exception_logger.error("IOS推送消息失败，异常信息：" + e.getMessage());
				result.put("code", 500);
				result.put("success", false);
				result.put("msg", "IOS推送错误:error_message: " + e.getMessage());
				return result;
			} catch (UnsupportedEncodingException e) {
				exception_logger.error("IOS推送消息失败，异常信息：" + e.getMessage());
				result.put("code", 500);
				result.put("success", false);
				result.put("msg", "IOS推送错误:error_message: " + e.getMessage());
				return result;
			}
			
		}

	@Override
	public Map<String, Object> pushOffLine(Map<String, Object> params){
		Map<String, Object> result = new HashMap<String,Object>();
		push_logger.debug("脱网提醒推送开始");
		try {
			String title = params.get("title").toString();
			String content = params.get("content").toString();
			String call_letter = params.get("call_letter").toString();
			
			//先保存 t_app_notice_his 然后推送
			NoticeHis entity = new NoticeHis();
			entity.setCallLetter(call_letter);
			entity.setTitle(title);
			entity.setContent(content);
			noticeHisMapper.add(entity);
//			System.out.println(SDF.format(new Date()) + "，脱网提醒保存数据结束!");
			List<AppBind> appBinds = appBindMapper.findByCallLetter(call_letter);//
			
			if(appBinds == null || appBinds.size()==0 ){
				push_logger.debug("脱网提醒 呼号："+ call_letter +"未获取到APP绑定信息!");
				String msg = "脱网提醒 :呼号:"+call_letter+" 未绑定";
				result.put("code", 403);
				result.put("success", false);
				result.put("msg", msg);
				unbind_logger.info(msg);
				return result;
			}
			Collections.sort(appBinds, new Comparator<AppBind>() {
				public int compare(AppBind o1, AppBind o2) {
					return o1.getStamp().compareTo(o2.getStamp());
				}
			});
			
			//获取时间最新的记录
			AppBind appBind = appBinds.get(appBinds.size()-1);
			Vehicle vehicle = vehicleMapper.findByCallLetter(call_letter);//获取车牌号 和 vin码
			
			if(appBind != null){
				push_logger.info("channelId:"+appBind.getChannelId() + ",token:"+appBind.getDeviceToken());
				boolean flag_ph = false;
				String msg = "";
				if (appBind.getDeviceType() == 0) {
					result = androidPush(appBind.getOrigin(), appBind.getChannelId(),
							appBind.getPushUserId(), appBind.getCustomerId(),
							title, content, 3,
							"content://notifyCenter", call_letter,vehicle.getPlateNo(),null);
					flag_ph = (Boolean) result.get("success");
					msg  += (String)  result.get("msg");
				} else if (appBind.getDeviceType() == 1) {
					result = iOSPush(appBind.getOrigin(), appBind.getDeviceToken(),
							appBind.getCustomerId(), title,
							content, 3, "content://notifyCenter",call_letter, vehicle.getPlateNo(),null);
					flag_ph = (Boolean) result.get("success");
					msg  += (String)  result.get("msg");
				}
				
				
				//判断是否是海马的车
				if(vehicle.getSubcoNo()==201 && StringUtils.hasText(vehicle.getVin())){
					push_logger.debug("脱网提醒[webpush].vin=" + vehicle.getVin() + ",呼号："+ call_letter );
					String params1 = "vin="+vehicle.getVin()+"&title="+title+"&body="+content;
					result = webPush(params1);
				}
				
				boolean flag_web   = (Boolean) result.get("success");
				msg   += (String)  result.get("msg");
				//
				if( flag_ph && flag_web ){
					result.put("code", 200);
					result.put("msg", "脱网提醒推送成功");
					result.put("success",true);
					push_logger.info("呼号:"+call_letter+" 脱网提醒推送成功！ ");
				}else{
					result.put("code", 500);
					result.put("msg", msg);
					result.put("success",false);
					push_logger.warn("呼号:"+call_letter+" 脱网提醒推送失败！ msg=" + msg);
				}
			}
			push_logger.debug("脱网提醒推送结束,呼号：" + call_letter +" --> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appBind.getStamp()));
			push_logger.info("----------分-割-线-------------");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("success", false);
			result.put("msg", "推送发生异常");
			exception_logger.error("脱网提醒推送消息失败，异常信息：" + e.getMessage());
			return result;
		}
	}
	
	/**
	 * 向web推送
	 * @param params
	 * @param params1
	 */
	public Map<String, Object> webPush(String params1){
		push_logger.info("海马web推送开始");
		Map<String, Object> result = new HashMap<String,Object>();
		String server = PropertyUtil.getPropertyValue("comm.properties", "push.server");
		//推送
		String json = RequestUtil.sendPost(server, params1);
		//结果
		JSONObject object = JSONObject.parseObject(json);
		String data = object.getString("data");
		if("1".equals(data)){
			String message = object.getString("message");
//			System.out.println( "["+SDF.format(new Date()) +"-向web应用推送消息失败]，推送内容："  + params + "，异常信息：" + message);
			result.put("code", 1);
			result.put("success", false);
			result.put("msg", "海马接受推送数据出错:"+message);
			exception_logger.error("web推送消息失败，推送内容："  + params1 + "，海马接受推送数据出错:"+message );
			return result;
		}
		result.put("code", 200);
		result.put("success", true);
		result.put("msg", "web推送成功");
		push_logger.info("海马web推送结束,推送内容："  + params1);
		return result;
	}
	
}
