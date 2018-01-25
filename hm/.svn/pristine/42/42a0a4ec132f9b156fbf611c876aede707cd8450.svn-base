package com.gboss.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.service.BarcodeService;
import com.gboss.service.CustomerService;
import com.gboss.service.ModelService;
import com.gboss.service.OperatelogService;
import com.gboss.service.PreloadService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.DateUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.controller
 * @ClassName:NetworkController
 * @Description:入网控制层
 * @author:xiaoke
 * @date:2014-3-24
 */
@Controller
public class NetworkController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(NetworkController.class);

	@Autowired
	private CustomerService customerService;


	@Autowired
	private VehicleService vehicleService;


	@Autowired
	private UnitService unitService;


	@Autowired
	private BarcodeService barcodeService;


	@Autowired
	private ModelService modelService;

	
	@Autowired
	private OperatelogService operatelogService;
	
	@Autowired
	private PreloadService preloadService;
	
	@Autowired
	private SystemConfig systemconfig;
	
	private OpenLdap ldap = OpenLdapManager.getInstance();



	@RequestMapping(value = "/getVehicles", method = RequestMethod.POST)
	public @ResponseBody
	Page<Vehicle> getVehicles(@RequestBody PageSelect<Vehicle> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ISHQ);
		if("0".equals(searchValue)&&"true".equals(ishq)){
			companyid = "2";
		}
		Page<Vehicle> result = vehicleService.search(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getlargecustVehicles", method = RequestMethod.POST)
	public @ResponseBody
	Page<Vehicle> getlargecustVehicles(
			@RequestBody PageSelect<Vehicle> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<Vehicle> result = vehicleService.searchlargecustVehicle(
				pageSelect, Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getprivateVehicles", method = RequestMethod.POST)
	public @ResponseBody
	Page<Vehicle> getprivateVehicles(
			@RequestBody PageSelect<Vehicle> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<Vehicle> result = vehicleService.searchprivateVehicle(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getUnits", method = RequestMethod.POST)
	public @ResponseBody
	Page<Unit> getUnits(@RequestBody PageSelect<Unit> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String searchValue = pageSelect.getSearchValue();
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		String ishq = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ISHQ);
		if("0".equals(searchValue)&&"true".equals(ishq)){
			companyid = "2";
		}
		Page<Unit> result = unitService.search(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getlcmakeupUnits", method = RequestMethod.POST)
	public @ResponseBody
	Page<Unit> getlcmuUnits(@RequestBody PageSelect<Unit> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search2(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getpamakeupUnits", method = RequestMethod.POST)
	public @ResponseBody
	Page<Unit> getpamuUnits(@RequestBody PageSelect<Unit> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search3(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getlcupdateUnits", method = RequestMethod.POST)
	public @ResponseBody
	Page<Unit> getlcudUnits(@RequestBody PageSelect<Unit> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search4(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	@RequestMapping(value = "/getpaupdateUnits", method = RequestMethod.POST)
	public @ResponseBody
	Page<Unit> getprivateUnits(@RequestBody PageSelect<Unit> pageSelect,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		String companyid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Page<Unit> result = unitService.search5(pageSelect,
				Long.valueOf(companyid));
		return result;
	}

	

	
	

	
    
		public String  getHttpResponse(String path, Map<String, String> m) throws Exception {
			String obdUrl = systemconfig.getObdconnectUrl();
			ObjectMapper mapper = new ObjectMapper();
			URL url = new URL(obdUrl + path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");// 提交模式
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setConnectTimeout(3000);// 连接超时 单位毫秒
			conn.setReadTimeout(3000);// 读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			conn.setDoInput(true);
			conn.connect();
			byte[] bytes = mapper.writeValueAsString(m).getBytes("UTF-8");
			conn.getOutputStream().write(bytes);// 输入参数
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			
			InputStream in = conn.getInputStream();
			String returnValue = "";
			byte[] buffer = new byte[512];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int len = 0; (len = in.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			returnValue = new String(baos.toByteArray(), "UTF-8");
			baos.flush();
			baos.close();
			in.close();
			System.out.println("服务端返回的内容："+ returnValue);
			conn.disconnect();
			return returnValue;
		}
		
		
		   public static HashMap<String, Object> parseJSON2Map(String jsonStr){
			   HashMap<String, Object> map = new HashMap<String, Object>();
		        //最外层解析
		        JSONObject json = JSONObject.fromObject(jsonStr);
		        for(Object k : json.keySet()){
		            Object v = json.get(k); 
		            //如果内层还是数组的话，继续解析
		            if(v instanceof JSONArray){
		                List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		                Iterator<JSONObject> it = ((JSONArray)v).iterator();
		                while(it.hasNext()){
		                    JSONObject json2 = it.next();
		                    list.add(parseJSON2Map(json2.toString()));
		                }
		                map.put(k.toString(), list);
		            } else {
		                map.put(k.toString(), v);
		            }
		        }
		        return map;
		    }
		   
		   
			public static List<HashMap<String, Object>> parseJSON2List(Object jsonStr) {
				JSONArray jsonArr = JSONArray.fromObject(jsonStr);
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				Iterator<JSONObject> it = jsonArr.iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				return list;
			}
		   
		   
		   
			@RequestMapping(value = "/checkConnect")
			@LogOperation(description = "检查网络连接", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =2)
			public @ResponseBody HashMap<String, Object> checkConnect(HttpServletRequest request) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("检查网络连接开始");
				}
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				String ip = systemconfig.getObdconnectIp();
				boolean flag = false;
				String msg = "obd故障查询系统网络异常！";
				String obd_port = systemconfig.getObdconnectPort();
				int port = Integer.valueOf(obd_port);
	        	Socket my = new Socket(); //实例化socket对象
	        	try{
	        	    InetSocketAddress mySock = new InetSocketAddress(InetAddress.getByName(ip), port);//假设连接目标的80端口
	        	    my.connect(mySock,100);//100毫秒超时
	        	    System.out.println("连接成功!");
	        	    flag = true;
	        	    msg = SystemConst.OP_SUCCESS;
	        	}catch(SocketTimeoutException e) {//捉到了!
	        	     System.out.println("cannot connect ...");
	        	}catch(Exception e) {//其他有IOException等
	        		  System.out.println("Exception,cannot connect ...");
	        	} 
				resultMap.put(SystemConst.SUCCESS, flag);
				resultMap.put(SystemConst.MSG, msg);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("检查网络连接结束");
				}
				return resultMap;
			}
			
	
		   @RequestMapping(value = "/getObdErrorMsg")
			public @ResponseBody HashMap<String, Object> getObdErrorMsg(String call_letter, HttpServletRequest request) throws SystemException {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获得车辆最新故障码信息开始");
				}
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				try {
					String path = "obd/getObdErrorMsg";
					boolean flag = false;
					String obdErrorMsg = "暂无故障信息";
					Object datas= new Object();
					Map<String,String> m = new HashMap<String,String>();
					//m.put("call_letter", "13912345001");
					m.put("call_letter", call_letter);
					String msg = getHttpResponse(path, m);
					HashMap<String, Object> map = null;
					if(StringUtils.isNotBlank(msg)){
						map = parseJSON2Map(msg);
						if(null != map){
							Object mark = map.get(SystemConst.SUCCESS);
							String remark = null;
							if(null != mark){
								remark = mark.toString();
							}
							if(null != remark && remark.equals("true")){
								flag = true;
								//故障码信息
								datas = map.get("datas");
								if(null != datas && !datas.toString().equals("null")){
									List<HashMap<String, Object>> results = parseJSON2List(datas);
									obdErrorMsg = packObdErrorMsg(results);
								}
							}
						}
					}
					resultMap.put(SystemConst.SUCCESS, flag);
					resultMap.put("obdErrorMsg", obdErrorMsg);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					e.printStackTrace();
					// 同时把异常抛到前台
					throw new SystemException();
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获得车辆最新故障码信息结束");
				}
				return resultMap;
			}
		   
		   
		   
		   /**
		    * 
		    * @Title:getObdMsg
		    * @Description:获取车辆最新obd信息
		    * @param call_letter
		    * @param request
		    * @return
		    * @throws SystemException
		    * @throws
		    */
		   @RequestMapping(value = "/getObdMsg")
			public @ResponseBody HashMap<String, Object> getObdMsg(String call_letter, HttpServletRequest request) throws SystemException {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获得车辆最新OBD信息开始");
				}
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				try {
					String path = "obd/getObdMsg";
					boolean flag = false;
					String k_msg = SystemConst.OP_FAILURE;
					Object datas= new Object();
					Map<String,String> m = new HashMap<String,String>();
					//m.put("call_letter", "13912345001");
					m.put("call_letter", call_letter);
					String msg = getHttpResponse(path, m);
					HashMap<String, Object> map = null;
					if(StringUtils.isNotBlank(msg)){
						map = parseJSON2Map(msg);
						if(null != map){
							Object mark = map.get(SystemConst.SUCCESS);
							String remark = null;
							if(null != mark){
								remark = mark.toString();
							}
							if(null != remark && remark.equals("true")){
								flag = true;
								datas = map.get("datas");
								k_msg = SystemConst.SUCCESS;
							}
						}
					}
					resultMap.put("datas", datas);
					resultMap.put(SystemConst.SUCCESS, flag);
					resultMap.put("msg", k_msg);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					e.printStackTrace();
					// 同时把异常抛到前台
					throw new SystemException();
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获得车辆最新OBD码信息结束");
				}
				return resultMap;
			}
		   
		   
		   public String  packObdErrorMsg(List<HashMap<String, Object>> results){
			   String msg = "暂无故障信息";
			   if(results != null && results.size() >0){
				   msg = "";
				   for (HashMap<String, Object> map : results) {
					   Object sysId = map.get("sysId");
					   Object obdErrorMsg = map.get("obdErrorMsg");
					   if(StringUtils.isNotNullOrEmpty(sysId) && StringUtils.isNotNullOrEmpty(obdErrorMsg)){
						   msg += SystemConst.errorMap.get(sysId);
						   msg = msg + ":" + obdErrorMsg.toString() + ";";
					   }
				}
			   }
			   if(msg.endsWith(";") && msg.length() >2 ){
				   msg = msg.substring(0, msg.length()-2);
			   }else if(StringUtils.isBlank(msg)){
				   msg = "暂无故障信息";
			   }
			   return msg;
		   }
		   
		   
		   @RequestMapping(value = "/getObdErrorMsgList")
			public @ResponseBody Page<HashMap<String, Object>> getObdErrorMsgList(@RequestBody PageSelect<Map<String, Object>> pageSelect,BindingResult bindingResult,
					HttpServletRequest request) throws SystemException {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获得故障码列表开始");
				}
				Map conditionMap = pageSelect.getFilter();
				int total = 0;
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
				List<HashMap<String, Object>> results = null;
				try {
					String path = "obd/getObdErrorMsgList";
					HashMap<String,String> m = new HashMap<String,String>();
					if(!StringUtils.isNotNullOrEmpty(conditionMap.get("startDate"))){
						conditionMap.put("startDate", DateUtil.getBeforeTenDay());
					}
					if(!StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))){
						conditionMap.put("endDate", DateUtil.formatlastBeforeday());
					}
					if(StringUtils.isNotNullOrEmpty(conditionMap.get("call_letter")) && StringUtils.isNotNullOrEmpty(conditionMap.get("startDate")) && StringUtils.isNotNullOrEmpty(conditionMap.get("endDate"))){
						m.put("call_letter", conditionMap.get("call_letter").toString());
						m.put("start_date", DateUtil.dateforSearch(conditionMap, "startDate"));
						m.put("end_date", DateUtil.dateforSearch(conditionMap, "endDate"));
					}
					/*m.put("call_letter", "13912345001");
					m.put("start_date", "2014-12-10 08:00:00");
					m.put("end_date", "2014-12-22 08:00:00");*/
					String  msg = getHttpResponse(path, m);
					HashMap<String, Object> map = null;
					if(StringUtils.isNotBlank(msg)){
						map = parseJSON2Map(msg);
					if(null != map){
						Object flag = map.get(SystemConst.SUCCESS);
						String remark = null;
						if(null != flag){
							remark = flag.toString();
						}
						Object datas = map.get("datas");
						if(null != remark && remark.equals("true") && null != datas && !datas.toString().equals("null")){
							results = parseJSON2List(datas);
							if(results != null && results.size() < 1){
								return PageUtil.getPage(0, pageSelect.getPageNo(), null, pageSelect.getPageSize());
							}else{
								total = results.size();
								int start = (pageSelect.getPageNo()-1)*pageSelect.getPageSize();
								int end = pageSelect.getPageNo()*pageSelect.getPageSize();
								int num = total<end?total:end;
								for(int i = start; i < num; i++){
									HashMap<String, Object> errormap = new HashMap<String, Object>();
									String sysName = SystemConst.errorMap.get(results.get(i).get("sysId"));
									errormap.put("sysName", sysName);
									errormap.put("obdErrorMsg", results.get(i).get("obdErrorMsg"));
									errormap.put("date", results.get(i).get("date"));
									list.add(errormap);
								}
							}
						}
					}
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					e.printStackTrace();
					// 同时把异常抛到前台
					throw new SystemException();
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("获得故障码列表结束");
				}
				return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
			}
	
}