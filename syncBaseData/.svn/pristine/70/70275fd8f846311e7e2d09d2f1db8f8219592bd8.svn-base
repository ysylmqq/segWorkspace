package com.gboss.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.util.LDAPSecurityUtils;
import net.minidev.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Preload;
import com.gboss.service.CallletterService;
import com.gboss.service.ServicepwdService;
import com.gboss.service.sync.AccountsSyncStrategyServicesImpl;
import com.gboss.service.sync.BandInfoSyncStrategyServiceImpl;
import com.gboss.service.sync.BaseDataSyncStrategyServiceImpl;
import com.gboss.service.sync.ComboSyncStrategyServiceImpl;
import com.gboss.service.sync.CommonCompanySyncStrategyServiceImpl;
import com.gboss.service.sync.InsuranceSyncStrategyServiceImpl;
import com.gboss.service.sync.ModelsSyncStrategyServiceImpl;
import com.gboss.service.sync.SeriesSyncStrategyServiceImpl;
import com.gboss.util.DataFetcher;
import com.gboss.util.StringUtils;

@Controller
public class SyncByManController extends BaseController {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SyncByManController.class);
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private SeriesSyncStrategyServiceImpl seriesSyncStrategyService;
	
	@Autowired
	private CommonCompanySyncStrategyServiceImpl commonCompanySyncStrategyService;
	
	@Autowired
	private BandInfoSyncStrategyServiceImpl bandInfoSyncStrategyService;
	
	@Autowired
	private AccountsSyncStrategyServicesImpl accountsSyncStrategyServices;
	
	@Autowired
	private BaseDataSyncStrategyServiceImpl baseDataSyncStrategyService;
	
	@Autowired
	private ModelsSyncStrategyServiceImpl modelsSyncStrategyService;
	
	@Autowired
	private  ComboSyncStrategyServiceImpl comboSyncStrategyService;
	
	@Autowired
	private  InsuranceSyncStrategyServiceImpl insuranceSyncStrategyService;
	
	@Autowired
	private CallletterService callletterService;
	
	@Autowired
	private ServicepwdService servicepwdService;
	
	@Autowired
	protected SystemConfig systemconfig;
	
	@RequestMapping("/main")
	public ModelAndView sysnMain(){
		return new ModelAndView("syncMain");
	}
	
	/******************************************************************************************
	 * 以下接口是对中心人员提供一些验证功能和同步功能
	******************************************************************************************/
	
	@RequestMapping("/sync/bcinfo")
	public @ResponseBody JSONObject bcinfo(HttpServletRequest request,HttpServletResponse response){
		
		JSONObject  json = new JSONObject();
		try {
			Map<String,String> params = new HashMap<String,String>();
			String begintime	= request.getParameter("start_date");
			String endtime		= request.getParameter("end_date");
			String barcode 		= request.getParameter("barcode");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(begintime) )
			{
				start_date 	= SDF.parse(begintime).getTime()/1000;
				params.put("begintime", start_date.toString());
			}
			
			if( StringUtils.isNotNullOrEmpty(endtime))
			{
				end_date 	= SDF.parse(endtime).getTime()/1000;
				params.put("endtime", end_date.toString());
			}
			
			if( StringUtils.isNotNullOrEmpty(barcode)){
				params.put("barcode", barcode);
			}
			
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
	        List<Preload> result =  callletterService.getCallLettersByTimes(params);
				
			if(result == null){
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
				
			if(result.size() <= start){
				start = 0;
			}
			
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/sync/vpinfo")
	public @ResponseBody JSONObject vpinfo(HttpServletRequest request,HttpServletResponse response){
		
		JSONObject  json = new JSONObject();
		try {
			Map<String,String> params = new HashMap<String,String>();
			String begintime	= request.getParameter("start_date");
			String endtime		= request.getParameter("end_date");
//			String barcode 		= request.getParameter("barcode");
			String vin 			= request.getParameter("vin");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(begintime))
			{
				start_date 	= SDF.parse(begintime).getTime()/1000;
				params.put("begintime", start_date.toString());
			}
			
			if( StringUtils.isNotNullOrEmpty(endtime))
			{
				end_date 	= SDF.parse(endtime).getTime()/1000;
				params.put("endtime", end_date.toString());
			}
			
			/*if(StringUtils.isNotNullOrEmpty(barcode) )
			{
				params.put("barcode", barcode);
			}*/
			
			if(StringUtils.isNotNullOrEmpty(vin) )
			{
				params.put("vin", vin);
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
			List<Map<String, Object>> result = servicepwdService.getServicePwdByTimes(params);
			
			if(result == null){
				System.out.println("本次未查询到同步车系数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/sync/checkPWD")
	public  @ResponseBody JSONObject checkPWD(HttpServletRequest request,HttpServletResponse response){
		JSONObject  json = new JSONObject();
		try {
			String mingwen = request.getParameter("inputpswd");
			if(StringUtils.isNotNullOrEmpty(mingwen)){
				String sha16 = LDAPSecurityUtils.toHexStr(LDAPSecurityUtils.encodeSHA(mingwen));
				json.put("pswd", sha16);
				json.put("success", true);
			}else{
				json.put("pswd", null);
				json.put("success", false);
			}
		}catch(Exception E){
			System.out.println( SDF.format(new Date()) + "，验证服务密码出错! " + E.getMessage());
			json.put("success", false);
		}
		return json;
	}

	
	/**
	 * 人工同步车系查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/series")
	public @ResponseBody JSONObject series(HttpServletRequest request,HttpServletResponse response){
		
		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getSysnSeries())+"?1=1";
			String series_id = request.getParameter("series_id");
			
			if(StringUtils.isNotNullOrEmpty(series_id)){
				url = url.concat("&series_id=" +series_id);
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
			boolean flag = false;
			List<HashMap<String, Object>> result = null;
			result = DataFetcher.getSyncData(start_date, end_date, url, flag, 1);
			if(result == null){
				System.out.println("本次未查询到同步车系数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doSeries")
	public @ResponseBody JSONObject doSeries(HttpServletRequest request,HttpServletResponse response){
		String series_id = request.getParameter("series_id");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getSysnSeries())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(series_id)){
				url = url.concat("&series_id=" +series_id);
				Map<String, String>  result = seriesSyncStrategyService.syncByMan(url);
				json.put("msg", result.get("msg"));
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(SDF.format(new Date())+" 人工同步车系失败![series_id=" + series_id+"]");
			json.put("msg", "同步车系失败!");
			json.put("success", false);
			return json;
		}
	}
	
	@RequestMapping("/sync/ssssList")
	public @ResponseBody JSONObject ssssList(HttpServletRequest request,HttpServletResponse response){
		
		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getSsssList())+"?1=1";
			String companyno = request.getParameter("companyno");
			
			if(StringUtils.isNotNullOrEmpty(companyno)){
				url = url.concat("&companyno=" +companyno);
			}
			
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
			boolean flag = false;
			List<HashMap<String, Object>> result = null;
			result = DataFetcher.getSyncData(start_date, end_date, url, flag, 1);
			if(result == null){
				System.out.println("本次未查询到同步车系数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doSsss")
	public @ResponseBody JSONObject doSsss(HttpServletRequest request,HttpServletResponse response){
		String company = request.getParameter("company");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getSsssList())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(company)){
				url = url.concat("&companyno=" +company);
				Map<String, String>  result = commonCompanySyncStrategyService.syncByMan(url);
				json.put("msg", result.get("msg"));
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			System.out.println(SDF.format(new Date())+"人工同步同步4s店失败![series_id=" + company+"]");
			json.put("msg", "同步4s店失败!");
			json.put("success", false);
			return json;
		}
	}
	
	@RequestMapping("/sync/bindinfo")
	public @ResponseBody JSONObject bindinfo(HttpServletRequest request,HttpServletResponse response){
		
		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getBandInfo())+"?1=1";
			String vin = request.getParameter("vin");
			if(StringUtils.isNotNullOrEmpty(vin)){
				url = url.concat("&vin=" +vin);
			}
			
			String bar_code = request.getParameter("bar_code");
			if(StringUtils.isNotNullOrEmpty(bar_code)){
				url = url.concat("&bar_code=" +bar_code);
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
	        List<HashMap<String, Object>> result = null;
	        result = DataFetcher.getSyncData(start_date, end_date, url, false, 1);
			if(result == null){
				System.out.println("本次未查询到绑定信息数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} 
		return json;
	}
	
	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doBindinfo")
	public @ResponseBody JSONObject doBindinfo(HttpServletRequest request,HttpServletResponse response){
		String vin = request.getParameter("vin");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getBandInfo())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(vin)){
				url = url.concat("&vin=" +vin);
				Map<String, String>  result = bandInfoSyncStrategyService.syncByMan(url);
				json.put("msg", result.get("msg"));
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			System.out.println(SDF.format(new Date())+"人工同步绑定信息失败![vin=" + vin+"]");
			json.put("msg", "同步绑定信息失败!");
			json.put("success", false);
			return json;
		}
	}
	
	
	@RequestMapping("/sync/accounts")
	public @ResponseBody JSONObject accounts(HttpServletRequest request,HttpServletResponse response){
		
		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getAccount())+"?1=1";
			String vin = request.getParameter("vin");
			if(StringUtils.isNotNullOrEmpty(vin) && vin.length() > 3){
				url = url.concat("&vin=" +vin.substring(3));
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
	        List<HashMap<String, Object>> result = null;
	        result = DataFetcher.getSyncData(start_date, end_date, url, false, 1);
			if(result == null){
				System.out.println("本次未查询到账户数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} 
		return json;
	}
	
	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doAccounts")
	public @ResponseBody JSONObject doAccounts(HttpServletRequest request,HttpServletResponse response){
		String vin = request.getParameter("vin");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getAccount())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(vin)){
				url = url.concat("&vin=" +vin.substring(3));
				List<HashMap<String, Object>> result = null;
		        result = DataFetcher.getSyncData(null, null, url, false, 1);
		        if(result==null){
		        	json.put("msg", "根据给定的vin="+vin.substring(3)+"\n未查询到海马接口的数据!");
		        }else{
		        	accountsSyncStrategyServices.syncByMan(url);
		        	json.put("msg", "同步账户信息成功!");
		        }
				
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			System.out.println(SDF.format(new Date())+"人工同步账户信息失败![vin=" + vin+"]");
			json.put("msg", "同步账户信息失败!");
			json.put("success", false);
			return json;
		}
	}
	
	
	@RequestMapping("/sync/info")
	public @ResponseBody JSONObject info(HttpServletRequest request,HttpServletResponse response){
//		vin: 车牌号
//		phone: 电话号码
		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getInfo())+"?1=1";
			String vin = request.getParameter("vin");
			if(StringUtils.isNotNullOrEmpty(vin)){
				url = url.concat("&vin=" +vin);
			}
			
			String phone = request.getParameter("phone");
			if(StringUtils.isNotNullOrEmpty(phone)){
				url = url.concat("&phone=" +phone);
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
	        List<HashMap<String, Object>> result = null;
	        result = DataFetcher.getSyncData(start_date, end_date, url, false, 1);
			if(result == null){
				System.out.println("本次未查询到车辆基础信息数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} 
		return json;
	}
	
	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doInfo")
	public @ResponseBody JSONObject doInfo(HttpServletRequest request,HttpServletResponse response){
		String vin = request.getParameter("vin");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getInfo())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(vin)){
				url = url.concat("&vin=" +vin);
				List<HashMap<String, Object>> result =  DataFetcher.getSyncData(null, null, url, false, 1);
		        if(result==null){
		        	json.put("msg", "根据给定的vin="+vin+"\n未查询到海马接口的数据!");
		        }else{
		        	Map<String, String> result1 = baseDataSyncStrategyService.syncByMan(url);
		        	json.put("msg", result1.get("msg"));
		        }
				
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			System.out.println(SDF.format(new Date())+"人工同步车辆基本信息失败![vin=" + vin+"]");
			json.put("msg", "同步车辆基本信息失败!");
			json.put("success", false);
			return json;
		}
	}
	
	@RequestMapping("/sync/models")
	public @ResponseBody JSONObject models(HttpServletRequest request,HttpServletResponse response){
//		vin: 车牌号
//		phone: 电话号码
		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getSysnModel())+"?1=1";
			String model_id = request.getParameter("model_id");
			if(StringUtils.isNotNullOrEmpty(model_id)){
				url = url.concat("&model_id=" +model_id);
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
	        List<HashMap<String, Object>> result = null;
	        result = DataFetcher.getSyncData(start_date, end_date, url, false, 1);
			if(result == null){
				System.out.println("本次未查询到车型数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return json;
	}
	
	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doModels")
	public @ResponseBody JSONObject doModels(HttpServletRequest request,HttpServletResponse response){
		String model_id = request.getParameter("model_id");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getSysnModel())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(model_id)){
				url = url.concat("&model_id=" +model_id);
				List<HashMap<String, Object>> result = null;
		        result = DataFetcher.getSyncData(null, null, url, false, 1);
		        if(result==null){
		        	json.put("msg", "根据给定的model_id="+model_id+"\n未查询到海马接口的数据!");
		        }else{
		        	Map<String, String> result1 = modelsSyncStrategyService.syncByMan(url);
		        	json.put("msg", result1.get("msg"));
		        }
				
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			System.out.println(SDF.format(new Date())+"人工同步车型信息失败![model_id=" + model_id+"]");
			json.put("msg", "同步车型信息失败!");
			json.put("success", false);
			return json;
		}
	}
	
	@RequestMapping("/sync/combos")
	public @ResponseBody JSONObject combos(HttpServletRequest request,HttpServletResponse response){
//		vin: 车牌号
//		phone: 电话号码
		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getPackList())+"?1=1";
			String combo_id = request.getParameter("combo_id");
			if(StringUtils.isNotNullOrEmpty(combo_id)){
				url = url.concat("&combo_id=" +combo_id);
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
	        List<HashMap<String, Object>> result = null;
	        result = DataFetcher.getSyncData(start_date, end_date, url, false, 1);
			if(result == null){
				System.out.println("本次未查询到车型数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} 
		return json;
	}
	
	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doCombos")
	public @ResponseBody JSONObject doCombos(HttpServletRequest request,HttpServletResponse response){
		String combo_id = request.getParameter("combo_id");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getPackList())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(combo_id)){
				url = url.concat("&combo_id=" +combo_id);
				List<HashMap<String, Object>> result = null;
		        result = DataFetcher.getSyncData(null, null, url, false, 1);
		        if(result==null){
		        	json.put("msg", "根据给定的combo_id="+combo_id+"\n未查询到海马接口的数据!");
		        }else{
		        	Map<String, String> result1 = comboSyncStrategyService.syncByMan(url);
		        	json.put("msg", result1.get("msg"));
		        }
				
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			System.out.println(SDF.format(new Date())+"人工同步套餐信息失败![combo_id=" + combo_id+"]");
			json.put("msg", "同步套餐信息失败!");
			json.put("success", false);
			return json;
		}
	}
	
	
	@RequestMapping("/sync/insurances")
	public @ResponseBody JSONObject insurances(HttpServletRequest request,HttpServletResponse response){

		JSONObject  json = new JSONObject();
		try {
			
			String start_date_str 	= request.getParameter("start_date");
			String end_date_str 	= request.getParameter("end_date");
			
			Long start_date = null;
			Long end_date   = null;
			
			if(StringUtils.isNotNullOrEmpty(start_date_str) )
			{
				start_date 	= SDF.parse(start_date_str).getTime();
			}
			
			if( StringUtils.isNotNullOrEmpty(end_date_str))
			{
				end_date 	= SDF.parse(end_date_str).getTime();
			}
			
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getInsuracerList())+"?1=1";
			String ic_id = request.getParameter("ic_id");
			if(StringUtils.isNotNullOrEmpty(ic_id)){
				url = url.concat("&ic_id=" +ic_id);
			}
			
			String rows= "20";//每页显示的记录数  
			rows = request.getParameter("rows");
			String page = "1";//当前第几页  
			page = request.getParameter("page");
			
			//当前页  
	        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	        //每页显示条数  
	        int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (intPage-1)*number;  
	        int toIndex = intPage*number;
			
	        List<HashMap<String, Object>> result = null;
	        result = DataFetcher.getSyncData(start_date, end_date, url, false, 1);
	        
			if(result == null){
				System.out.println("本次未查询到保险公司数据!");
				json.put("rows", new JSONArray());
				json.put("total", 0);
				return json;
			}
			
			if(result.size() <= start){
				start = 0;
			}
			if(result.size() < toIndex){
				toIndex = result.size();
			}
			json.put("rows", com.alibaba.fastjson.JSONObject.toJSONString(result.subList(start, toIndex)));
			json.put("total", result.size());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return json;
	}
	
	/**
	 * 人工同步操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sync/doInsurance")
	public @ResponseBody JSONObject doInsurance(HttpServletRequest request,HttpServletResponse response){
		String ic_id = request.getParameter("ic_id");
		JSONObject json = new JSONObject();
		try {
			String url = systemconfig.getSysnDadaUrl();
			url = url.concat(systemconfig.getInsuracerList())+"?1=1";
			if(StringUtils.isNotNullOrEmpty(ic_id)){
				url = url.concat("&ic_id=" +ic_id);
				List<HashMap<String, Object>> result = null;
		        result = DataFetcher.getSyncData(null, null, url, false, 1);
		        if(result==null){
		        	json.put("msg", "根据给定的ic_id="+ic_id+"\n未查询到海马接口的数据!");
		        }else{
		        	Map<String, String> result1 =  insuranceSyncStrategyService.syncByMan(url);
		        	json.put("msg", result1.get("msg"));
		        }
				json.put("success", true);
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(SDF.format(new Date())+" 人工同步保险公司信息失败![ic_id=" + ic_id+"]");
			json.put("msg", "同步保险公司信息失败!");
			json.put("success", false);
			return json;
		}
	}
	
	
}
