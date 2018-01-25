package com.gboss.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.chinagps.lib.util.Config;
import cc.chinagps.lib.util.Util;

import com.gboss.comm.InitHelper;
import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.VehicleConf;
import com.gboss.service.UpgradeService;
import com.gboss.service.VehicleService;
import com.gboss.util.CommandSendHelper;
import com.gboss.util.DateUtil;
import com.gboss.util.HibernateUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:UpgradeController
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午11:56:52
 */
@Controller
public class UpgradeController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(UpgradeController.class);

	@Autowired
	@Qualifier("upgradeService")
	private UpgradeService upgradeService;
	
	@Autowired
	@Qualifier("vehicleService")
	private VehicleService vehicleService;
	
	@Autowired
	private SystemConfig systemconfig;
	
	ArrayList<String> call_letterList = new ArrayList<String>();
	public  Hashtable<String, Object> this_upgrade = new Hashtable<String, Object>();
	Hashtable<String, Object> middle_upgrade = new Hashtable<String, Object>();
	
	
	@RequestMapping(value = "/upgrade/add")
	@LogOperation(description = "升级", type = SystemConst.OPERATELOG_ADD,model_id=20100)
	public @ResponseBody
	HashMap<String, Object> add(@RequestBody Upgrade upgrade,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		String compannyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Long id = compannyId == null ? null : Long.valueOf(compannyId);

		ConcurrentHashMap <String, Upgrade> tableAll = SystemConst.hm_call_letter_upgradeAll;
		ConcurrentHashMap <String, Upgrade>wait_upgrade = SystemConst.hm_call_letter_upgrade;
	    
		try {
			Integer is_all = upgrade.getIs_all();
			if(is_all == 0){
				String [] call_letters = upgrade.getCall_letters();
				for (String call_letter : call_letters) {
					Upgrade old_ug = upgradeService.getUpgradeBycall_letter(call_letter);
					//存在则更新
					if(null != old_ug){
						old_ug.setIp(upgrade.getIp());
						old_ug.setPort(upgrade.getPort());
						old_ug.setUg_ver(upgrade.getUg_ver());
						old_ug.setFlag(1);
						old_ug.setS_time(null);
						old_ug.setE_time(null);
						upgradeService.update(old_ug);						
					}else{
						old_ug = new Upgrade();
						old_ug.setIp(upgrade.getIp());
						old_ug.setPort(upgrade.getPort());
						old_ug.setCall_letter(call_letter);
						//ug.setS_time(new Date());
//						old_ug.setSubco_no(id);
						old_ug.setFlag(1);
						old_ug.setUg_ver(upgrade.getUg_ver());
						old_ug.setCur_ver("");
						upgradeService.save(old_ug);						
					}
					//把要升级的呼号数据放到等待队列
					wait_upgrade.put(call_letter, old_ug);						
					tableAll.put(call_letter, old_ug);
				}
			//批量数据下放升级指令，采用批量处理	
			}else if(is_all == 1){
				HibernateUtil hibernateUtil = (HibernateUtil)SpringContext.getBean("HibernateUtil");
				// 获取Session
				Session session = hibernateUtil.getSession();   
				// 开启事物  
		        session.beginTransaction(); 
		        Object call_letters = request.getSession().getAttribute("call_letters");
		        if( StringUtils.isNotNullOrEmpty(call_letters)){
		        	String calls = call_letters.toString();
		        	String[] call_data = calls.split(",");
		        	for (int i = 0; i < call_data.length; i++) {
						try {
							if(StringUtils.isNotBlank(call_data[i])){
								Upgrade old_ug = upgradeService.getUpgradeBycall_letter(call_data[i]);
								if(null != old_ug){
									old_ug.setIp(upgrade.getIp());
									old_ug.setPort(upgrade.getPort());
									old_ug.setUg_ver(upgrade.getUg_ver());
									old_ug.setFlag(1);
									session.update(old_ug);
									old_ug.setS_time(null);
									old_ug.setE_time(null);
									
								}else {
									old_ug = new Upgrade();
									old_ug.setCall_letter(call_data[i]);
									old_ug.setIp(upgrade.getIp());
									old_ug.setPort(upgrade.getPort());
									old_ug.setUg_ver(upgrade.getUg_ver());
									//ug.setS_time(new Date());
//									old_ug.setSubco_no(id);
									old_ug.setCur_ver("");
									old_ug.setFlag(1);
									session.save(old_ug);									
								}
								//把要升级的呼号数据放到等待队列
								wait_upgrade.put(call_data[i], old_ug);						
								tableAll.put(call_data[i], old_ug);
							}
							//每一百条做批量提交
							if(i%100 == 0 || i == call_data.length -1){
								session.flush();  
							    session.clear();
								session.getTransaction().commit();// 提交事物 
								session.beginTransaction();
							}
						} catch (Exception e) {
							System.out.println("异常数据:"+call_letters.toString());
							continue;
						}
					}
		        }
				hibernateUtil.closeSession(session);
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	@RequestMapping(value = "/upgrade/cancel")
	@LogOperation(description = "取消升级", type = SystemConst.OPERATELOG_UPDATE,model_id=20100)
	public @ResponseBody
	HashMap<String, Object> cancel(@RequestBody Upgrade upgrade,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		//ConcurrentHashMap <String, Upgrade> table = SystemConst.hm_call_letter_upgrade;
		Hashtable<String, Upgrade> tablecancel = SystemConst.hm_call_letter_cancel;		
		try {
			Integer is_all = upgrade.getIs_all();
			String [] call_letters={};
			
			if(is_all == 0){
				call_letters = upgrade.getCall_letters();
			}else if(is_all == 1){
				Object call_lettersObject = request.getSession().getAttribute("call_letters");
				 if( StringUtils.isNotNullOrEmpty(call_lettersObject)){
					 call_letters = call_lettersObject.toString().split(",");
				 }
			}
				for (String call_letter : call_letters) {
					Upgrade ug = upgradeService.getUpgradeBycall_letter(call_letter);
					//存在则更新
					if(null != ug){
						ug.setFlag(7);
						upgradeService.update(ug);
						//table.remove(call_letter);
						tablecancel.put(call_letter, ug);
					}
				}
		}catch (Exception e) {
				e.printStackTrace();
				resultMap.put(SystemConst.SUCCESS, false);
				resultMap.put(SystemConst.MSG, SystemConst.OP_FAILURE);
			}
			
		resultMap.put(SystemConst.SUCCESS, true);
		resultMap.put(SystemConst.MSG, SystemConst.OP_SUCCESS);
		return resultMap;
	}
	
	@RequestMapping(value = "/upgrade/confCommand")
//	@LogOperation(description = "查询配置", type = SystemConst.OPERATELOG_UPDATE,model_id=20100)
	public @ResponseBody
	HashMap<String, Object> confCommand(@RequestBody Upgrade upgrade,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();	
		resultMap.put(SystemConst.MSG, SystemConst.OP_FAILURE);
		resultMap.put(SystemConst.SUCCESS, true);
		try {
			Integer is_all = upgrade.getIs_all();
			String [] call_letters={};
			
			if(is_all == 0){
				call_letters = upgrade.getCall_letters();
			}else if(is_all == 1){
				Object call_lettersObject = request.getSession().getAttribute("call_letters");
				 if( StringUtils.isNotNullOrEmpty(call_lettersObject)){
					 call_letters = call_lettersObject.toString().split(",");
				 }
			}
			 ArrayList<String> callletterList = new ArrayList<String>();
			for (String call_letter : call_letters) {
				callletterList.add(call_letter);
			}
			
			/**
			 * 1：刷新内存
			 * 2：发送查询指令
			 */
			InitHelper.initConfCache();
			ConcurrentHashMap<String, VehicleConf> hm_vehicleconf_cache = SystemConst.hm_vehicleconf_cache;
		    int count_success_num = 0;
		    int count_fail_num = 0;
		    for (String call_letter : callletterList) {
		    	VehicleConf vc = (VehicleConf)hm_vehicleconf_cache.get(call_letter);
		        if (vc == null) {
		        	VehicleService vehicleService = (VehicleService)SpringContext.getBean("vehicleService");
		        	vc = new VehicleConf();
		        	vc.setCall_letter(call_letter);
		        	vc.setCode1(Long.valueOf(0L));
		        	vc.setIs_on(1);
		        	vehicleService.saveOrUpdate(vc);
		        	hm_vehicleconf_cache.put(call_letter, vc);
		        }
		        ArrayList<String> tempcallletterList = new ArrayList<String>();
		        tempcallletterList.add(call_letter);
		        String cmdsn = SystemConst.clienthandler.SendCommand(tempcallletterList, 180, null, true);
		        if (!cmdsn.equals("")) {
		        	vc.setSend_conf_count(vc.getSend_conf_count() + 1);
		        	vc.setSend_queryconf(new Date());
		        	vc.setS_time(new Date());
		        	SystemConst.hm_vehicleconf_cache.put(call_letter, vc);
		        	count_success_num++;
		        	System.out.println(DateUtil.formatNowTime() + " 呼号：" + call_letter + " 查询配置指令下发成功，sn=" + cmdsn);
		        } else {
		        	count_fail_num++;
		        	System.out.println(DateUtil.formatNowTime() + " 呼号：" + call_letter + " 查询配置指令下发失败");
		        }
		      }
		      resultMap.put("success", Boolean.valueOf(true));
		      resultMap.put("msg", "成功:" + count_success_num + "条，失败：" + count_fail_num + "条.");
		      
		      return resultMap;
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	resultMap.put("success", Boolean.valueOf(false));
		    	resultMap.put("msg", "操作失败");
		    }
		return resultMap;
	}
	
	@RequestMapping(value = "/upgrade/searchCommand")
	//@LogOperation(description = "取消升级", type = SystemConst.OPERATELOG_UPDATE,model_id=20100)
	public @ResponseBody
	HashMap<String, Object> searchCommand(@RequestBody Upgrade upgrade,
			BindingResult bindingResult, HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();	
		resultMap.put(SystemConst.MSG, SystemConst.OP_FAILURE);
		resultMap.put(SystemConst.SUCCESS, true);
		try {
			Integer is_all = upgrade.getIs_all();
			String [] call_letters={};
			
			if(is_all == 0){
				call_letters = upgrade.getCall_letters();
			}else if(is_all == 1){
				Object call_lettersObject = request.getSession().getAttribute("call_letters");
				 if( StringUtils.isNotNullOrEmpty(call_lettersObject)){
					 call_letters = call_lettersObject.toString().split(",");
				 }
			}
			 ArrayList<String> callletterList = new ArrayList<String>();
			for (String call_letter : call_letters) {
				callletterList.add(call_letter);
			}
			String cmdsn = SystemConst.clienthandler.SendCommand(
					callletterList, 0x01, null, false);
					if(!cmdsn.equals("")){
						resultMap.put(SystemConst.SUCCESS, true);
						resultMap.put(SystemConst.MSG, SystemConst.OP_SUCCESS);
						System.out.println("查车指令下发成功，sn="+cmdsn);
					}
		}catch (Exception e) {
				e.printStackTrace();
				resultMap.put(SystemConst.SUCCESS, false);
				resultMap.put(SystemConst.MSG, SystemConst.OP_FAILURE);
			}
		return resultMap;
	}
	
	@RequestMapping(value = "/upgrade/findSimUpgradeByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findSimUpgradeByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		Page<HashMap<String, Object>> result = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result = upgradeService.getSimUpgradePage(request, id, pageSelect);
			List<HashMap<String, Object>> pageDatas = result.getItems();
			List<HashMap<String, Object>> new_pageDatas = new ArrayList<HashMap<String, Object>>(); 
			for(int i=0 ; i< pageDatas.size() ; i++){
				HashMap<String, Object> upgrade = pageDatas.get(i);
				upgrade.put("conf_code", upgrade.get("conf_code").toString().equals("0") ?"0":getConfsInfo(Long.valueOf(upgrade.get("conf_code").toString())));
				new_pageDatas.add(upgrade);
			}
			result.setItems(new_pageDatas);
			
		} catch (Exception e) {
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		return result;
	}
	
	/**
	 * 数字型code1配置信息转换成字符串形式
	 * 1251 ==>  ABS,PEPS,TPMS,SRS,BCM,TCU,APM
	 * @param val
	 * @return
	 */
	private String getConfsInfo(Long val) {
		if(val!=null && val == 0)
			return "0";
		//子系统配置, 每1位表示1个子系统, 1=有, 0=无, 从低位1到高位64依次为ABS,ESP/DCU,PEPS,TPMS,SRS,EPS,EMS,IMMO,BCM,TCU,ICM,APM
		String[] sub_sys_names = {"ABS","ESP/DCU","PEPS","TPMS","SRS","EPS","EMS","IMMO","BCM","TCU","ICM","APM"};
		StringBuffer result = new StringBuffer();
		char[] buffer = new char[64];
	    Arrays.fill(buffer, '0');
	    for (int i = 0; i < 64; ++i) {
	        long mask = 1L << i;
	        if ((val & mask) == mask) {
	            buffer[63 - i] = '1';
	        }
	    }
	    String binaryStr = new String(buffer);
	    StringBuffer sb = new StringBuffer(binaryStr);
	    binaryStr = sb.reverse().toString();
 		for(int i=0 ; i < binaryStr.length() - (64-12); i++){
			if(binaryStr.charAt(i)=='1'){
				result.append(sub_sys_names[i]).append(",");
			}
		}
 		result = result.deleteCharAt(result.length()-1);
		return result.toString();
	}
	
	private static String getConfsHexInfo(Long val) {
		if(val!=null && val == 0)
			return "0";
		char[] buffer = new char[64];
	    Arrays.fill(buffer, '0');
	    for (int i = 0; i < 64; ++i) {
	        long mask = 1L << i;
	        if ((val & mask) == mask) {
	            buffer[63 - i] = '1';
	        }
	    }
	    String binaryStr = new String(buffer);
		return binaryStr.substring(64-12);
	}

	@RequestMapping(value = "/upgrade/exportUpgrade")
	@LogOperation(description = "空中升级汇总报表导出", type = SystemConst.OPERATELOG_SEARCHKEY, model_id =20061)
	public @ResponseBody
	void exportUpgrade(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		try {
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
		    Map map = new HashMap();
		    Iterator entries =returnMap.entrySet().iterator();
		    Map.Entry entry = null;
		    String name = "";
		    String value = "";
		    while (entries.hasNext()) {
		        entry = (Map.Entry) entries.next();
		        name = (String) entry.getKey();
		        Object valueObj = entry.getValue();
		        if(null == valueObj){
		            value = "";
		        }else if(valueObj instanceof String[]){
		            String[] values = (String[])valueObj;
		            for(int i=0;i<values.length;i++){
		                value = values[i] + ",";
		            }
		            value = value.substring(0, value.length()-1);
		        }else{
		            value = valueObj.toString();
		        }
		        if("null".equals(value)){
		        	value="";
		        }
		        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)){
		        	map.put(name, value);
		        }
		    }
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long id = compannyId == null ? null : Long.valueOf(compannyId);
		    
			String[][] title = { { "序号", "10" }, { "终端呼号", "20" },
					{ "TBOX条码", "30" }, { "车架号", "30" }, { "当前版本", "20" },
					{ "升级状态", "20" }, { "批次名称", "30" }, { "扫描时间", "30" },
					{ "配置简码", "30" },{ "配置信息", "30" },{ "配置状态", "30" },{ "体检开关", "30" },
					{ "升级开始时间", "30" }, { "升级结束时间", "30" } };
			List<HashMap<String, Object>> list = upgradeService.getSimUpgradeList(id, map);
			List valueList = new ArrayList();
			HashMap<String, Object> upgrade = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				values = new String[title.length];
				upgrade = list.get(i);
				values[0] = String.valueOf(i+1);
				values[1] = upgrade.get("call_letter")==null ?"":upgrade.get("call_letter").toString();
				values[2] = upgrade.get("barcode")==null ?"":upgrade.get("barcode").toString();
				values[3] = upgrade.get("vin")==null ?"":upgrade.get("vin").toString();
				values[4] = upgrade.get("cur_ver")==null ?"":upgrade.get("cur_ver").toString();
				values[5] = upgrade.get("flag")==null ?"":upgrade.get("flag").toString();
				 if(values[5].equals("1")){
					values[5] = "要求升级";
				}else if(values[5].equals("2")){
					values[5] = "已发查车";
				}else if(values[5].equals("3")){
					values[5] = "已发升级";
				}else if(values[5].equals("4")){
					values[5] = "升级中";
				}else if(values[5].equals("5")){
					values[5] = "升级失败";
				}else if(values[5].equals("6")){
					values[5] = "升级成功";
				}else if(values[5].equals("7")){
					values[5] = "升级取消";
				}else{
					values[5] = "未升级";
				}
				 values[6] = upgrade.get("upgrade_name")==null ?"":upgrade.get("upgrade_name").toString();
				 values[7] = upgrade.get("scan_time")==null ?"":upgrade.get("scan_time").toString();
				 values[8] = upgrade.get("equip_code")==null ?"":upgrade.get("equip_code").toString();
				 values[9] = upgrade.get("conf_code")==null ?"":getConfsHexInfo(Long.valueOf(upgrade.get("conf_code").toString()));
				 values[10] = upgrade.get("conf_flag")==null ?"":upgrade.get("conf_flag").toString();
				 if(values[10].equals("0")){
						values[10] = "未配置";
				 }else if(values[10].equals("1")){
					values[10] = "已发查询指令";
				 }else if(values[10].equals("2")){
					values[10] = "已发设置指令";
				 }else if(values[10].equals("3")){
					values[10] = "设置失败";
				 }else if(values[10].equals("4")){
					values[10] = "设置成功";
				 }
				
				 values[11] = upgrade.get("is_on") ==null ?"":upgrade.get("is_on").toString();
				 values[12] = upgrade.get("s_time")==null ?"":upgrade.get("s_time").toString();
				 values[13] = upgrade.get("e_time")==null ?"":upgrade.get("e_time").toString();
				 valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
			CommonCompany commonCompany =  openLdap.getCompanyById(compannyId);
			CreateExcel_PDF_CSV.createExcel(valueList, response, "空中升级报表汇总", title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
	}
	
	
	

	@RequestMapping(value = "/upgrade/getUpgradeList", method = RequestMethod.POST)
	public @ResponseBody
	List<Upgrade> getupgradeList(@RequestBody Upgrade upgrade,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		List<Upgrade> upgradeList = upgradeService.getUpgradeListforCur_ver();
		return upgradeList;
	}

	@RequestMapping(value = "/upgrade/importUpgrade")
	public void importUpgrade(@RequestParam("upgrade_file") MultipartFile sim_file,
			@RequestParam(required = false) Boolean isOverride,String ip, String port,
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException, IOException {
		Date sDate = new Date();
		String compId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		Long compannyId = compId == null ? null : Long.valueOf(compId);
		String uid = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		Long userId = Long.valueOf(uid);
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> params = new ArrayList<String>();
	    params.add(ip);
	    params.add(port);	    

		StringBuffer ug_ver= new StringBuffer();
		
			// excel上传保存路径
			String path = systemconfig.getExcelUploadPath();
			File fileDir = new File(path);
			if (!fileDir.exists()) { // 判断路径是否存在，不存在则创建
				fileDir.mkdirs();
			}
			// 保存文件和数据
			File descFile = new File(fileDir, sim_file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(sim_file.getInputStream(), descFile);
			boolean remark = CreateExcel_PDF_CSV.checkExcel(descFile);	
			String msg="操作失败";
			if(remark){
				List<String[]> dataList = CreateExcel_PDF_CSV.getData(descFile);
				String upgradeName = dataList.get(0)[2];
				// 解析并保存用户数据
				for (int i = 0; i<5;i++){
				    dataList.remove(0);
				}
			 msg = upgradeService.importUpgrade(dataList, compannyId, userId,upgradeName,params);
				
				
			}else{
				map.put("success", false);
				map.put("msg", "请导入相应格式的excel文件！");
			}
			// 删除上传文件
			descFile.delete();
        Date eDate = new Date();
        msg ="耗时："+(eDate.getTime()-sDate.getTime())/1000+"秒，"+msg;
		map.put("success", true);
		map.put("msg", msg);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String str = "<script>parent.callback('" + map.get("msg") + "',"
					+ map.get("success") + ");</script>";
			out.write(str.getBytes("utf-8"));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			InitHelper.initConfCache();
			if (out != null) {
				try {
					out.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}	
	}
	
	@RequestMapping(value = "/upgrade/getIsOpen")	
	public @ResponseBody
	HashMap<String, Object> getIsOpen(HttpServletRequest request) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String path = Config.getConfigPath()+"classes/cmd.properties";
		String isOpen ="";
		try {
			Properties properties = Util.loadProperties(path);
			List<String> roleids = (List<String>) request.getSession()
					.getAttribute(SystemConst.ACCOUNT_ROLEIDS);
			System.out.println("roleids="+roleids);
			if (StringUtils.isNotNullOrEmpty(roleids)) {
				// 权限判断
				if (roleids.contains(SystemConst.ROLEID_UPDATE)) {
					isOpen = properties.getProperty("hasupgrade");
				} 
			}else {
				isOpen = "";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, true);
		resultMap.put(SystemConst.MSG, isOpen);
		return resultMap;
	}
}

