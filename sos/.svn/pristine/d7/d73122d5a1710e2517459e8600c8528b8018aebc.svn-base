package com.gboss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import ldap.util.Config;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SelConst;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.comm.WhsConst;
import com.gboss.pojo.Check;
import com.gboss.pojo.CheckDetails;
import com.gboss.pojo.Product;
import com.gboss.service.CheckService;
import com.gboss.service.ProductService;
import com.gboss.util.DateUtil;
import com.gboss.util.JsonUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;
import com.gboss.util.excel.CreateExcel_PDF_CSV;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:CheckController
 * @Description:盘点控制层
 * @author:zfy
 * @date:2013-9-17 下午4:18:57
 */
@Controller
public class CheckController extends BaseController {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(CheckController.class);

	@Autowired
	@Qualifier("checkService")
	private CheckService checkService;
	
	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonGenerator jsonGenerator = null;
	
	@RequestMapping(value = "/sell/addCheck")
	@LogOperation(description = "添加盘点", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addCheck(
			@RequestBody Check check, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加盘点 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(check,true));
		
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(check);
			}
			if (check != null) {
				// 从session中得到当前登录人id
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String orgId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				check.setUserId(userId==null?null:Long.valueOf(userId));
				check.setUserName((String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_USERNAME));
				check.setOrgId(orgId==null?null:Long.valueOf(orgId));
				check.setCompanyId(companyId==null?null:Long.valueOf(companyId));
				//设置机构、分公司name
				OpenLdap openLdap = OpenLdapManager.getInstance();
				if(StringUtils.isNotBlank(companyId)){
					CommonCompany commonCompany=openLdap.getCompanyById(companyId);
					if(commonCompany!=null){
						check.setCompanyName(commonCompany.getCompanyname());
					}
				}
				if(StringUtils.isNotBlank(orgId)){
					CommonCompany commonCompany=openLdap.getCompanyById(orgId);
					if(commonCompany!=null){
						check.setOrgName(commonCompany.getCompanyname());
					}
				}
				//营业处负责人id opid
				CommonCompany commonCompany=openLdap.getCompanyById(companyId);
				if(commonCompany!=null){
					if(StringUtils.isNotBlank(commonCompany.getOpid())){
						check.setDirectorId(Long.valueOf(commonCompany.getOpid()));
					}
					check.setDirectorName(commonCompany.getCompanyname());
				}
				/*//获得当前登录人==仓管员所管理的仓库 to do...
				commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
						check.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
					}
					check.setWhsName(commonCompany.getCompanyname());
				}*/
				//设置仓库，机构下的仓库
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					check.setWhsId(Long.valueOf(commonCompany2.getCompanyno()));
					check.setWhsName(commonCompany2.getCompanyname());
					break;
				}
				check.setStatus(1);//已开始
				int result = checkService.addCheck(check);
				if (result == -1) {
					flag = false;
					msg = "操作对象为空";
				}else if (result == 2){
					flag = false;
					msg = "操作对象已存在";
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加盘点 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/sell/addCheckDetail")
	@LogOperation(description = "添加盘点明细记录", type = SystemConst.OPERATELOG_ADD, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> addCheckDetail(
			@RequestBody CheckDetails checkDetails, HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加盘点明细 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(checkDetails,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(checkDetails);
			}
			if (checkDetails != null && checkDetails.getCheckId()!=null) {
				if(checkService.get(Check.class, checkDetails.getCheckId())!=null){
					checkService.save(checkDetails);
				}else{
					flag = false;
					msg = "操作对象不存在";
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("添加盘点明细 结束");
		}
		return resultMap;
	}


	@RequestMapping(value = "/sell/updateCheck")
	@LogOperation(description = "修改盘点", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateCheck(
		 @RequestBody Check check,HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改盘点 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(check,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(check);
			}
			if (check != null) {
				// 从session中得到当前登录人id
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				check.setUserId(userId==null?null:Long.valueOf(userId));
				int result = checkService.updateCheck(check);
				if (result == -1) {
					flag = false;
					msg = "操作对象为空";
				} else if (result == 0) {
					flag = false;
					msg = "要操作的对象不存在";
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改盘点 结束");
		}
		return resultMap;
	}
	@RequestMapping(value = "/sell/updateCheckStatus")
	@LogOperation(description = "修改盘点状态", type = SystemConst.OPERATELOG_UPDATE, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> updateCheckStatus(
		 @RequestBody Check check,HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改盘点状态 开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(check,true));
			
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(check);
			}
			if (check != null) {
				// 从session中得到当前登录人id
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				check.setCheckUserId(userId==null?null:Long.valueOf(userId));
				int result = checkService.updateCheckStatus(check);
				if (result == -1) {
					flag = false;
					msg = "操作对象为空";
				} else if (result == 0) {
					flag = false;
					msg = "要操作的对象不存在";
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
			msg =StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage();
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("修改盘点状态 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/sell/getCheck")
	public @ResponseBody
	Check getCheck(@RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找最后一次盘点信息 开始");
		}
		Check result = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			/*String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);*/
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//map.put("orgId", orgId);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//根据分公司或者营业处id得到下面的所有仓库列表
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
				List<Long> wareHouseIds = new ArrayList<Long>();
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}else{//如果下面没有仓库，则不能看到任何数据
					map.put("whsId", "00");
				}
			}
			map.put("companyId", companyId);
			result = checkService.getCheck(map);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找最后一次盘点信息 结束");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/sell/getCheckAndDetails")
	public @ResponseBody
	Check getCheckAndDetails(@RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找盘点记录(用于生成盘点表) 开始");
		}
		Check result = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//map.put("orgId", orgId);
			map.put("companyId", companyId);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//根据分公司或者营业处id得到下面的所有仓库列表
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
				List<Long> wareHouseIds = new ArrayList<Long>();
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}else{//如果下面没有仓库，则不能看到任何数据
					map.put("whsId", "00");
				}
			}
			result = checkService.getCheckAndDetails(map);
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找盘点记录(用于生成盘点表) 结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/sell/getCheckDetails")
	public @ResponseBody
	List<HashMap<String, Object>> getCheckDetails(@RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找盘点明细开始");
		}
		List<HashMap<String, Object>> result = null;
		try {
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			//map.put("orgId", orgId);
			map.put("companyId", companyId);
			Check check = checkService.getCheckAndDetails(map);
			if(check!=null){
				result=check.getCheckDetailsMap();
			}
		} catch (SystemException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查找盘点明细 结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/sell/exportExcel4CheckReport")
	public void exportExcel4CheckReport(HttpServletRequest request,HttpServletResponse response)  {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出盘点记录(报表) 开始");
		}
		OutputStream os = null;
		WritableWorkbook wb = null;
		try {
			WritableFont wf_key = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD); 
	        WritableFont wf_value = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD); 
            
	        WritableCellFormat wcf_value = new WritableCellFormat(wf_value); 
            wcf_value.setAlignment(jxl.format.Alignment.CENTRE); 
            wcf_value.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            wcf_value.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
             
            WritableCellFormat wcf_name_center = new WritableCellFormat(wf_key); 
            wcf_name_center.setAlignment(Alignment.CENTRE); 
            wcf_name_center.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf_name_center.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
             
            //WritableFont wf_title = new WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD); 
            WritableFont wf_title = new jxl.write.WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD); 
            WritableCellFormat  wcf_title = new WritableCellFormat(wf_title); 
            wcf_title.setAlignment(Alignment.CENTRE); 
            wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
            
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
		    String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
		    String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
		    String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
		    /*map.put("orgId", orgId);*/
			//根据分公司或者营业处id得到下面的所有仓库列表
		    OpenLdap openLdap = OpenLdapManager.getInstance();
		    if(StringUtils.isNullOrEmpty(map.get("whsId"))){
				List<Long> wareHouseIds = new ArrayList<Long>();
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}else{//如果下面没有仓库，则不能看到任何数据
					map.put("whsId", "00");
				}
		    }
			
			//机构名称
			String orgName="";
			//获得分公司中英文名称
			CommonCompany commonCompany1 =openLdap.getCompanyById(companyId);
			if (StringUtils.isNotBlank(orgId)) {
				if (commonCompany1 != null){
					orgName =commonCompany1.getCompanyname();
				}
				/*CommonCompany commonCompany =  openLdap.getCompanyById(orgId);
				if (commonCompany != null && StringUtils.isNotBlank(commonCompany.getCompanyname())) {
					orgName =orgName+"-"+ commonCompany.getCompanyname();
				}*/
			}
			//查询数据库
			Check check =checkService.getCheckAndDetails4Report(map);
			String fileName="盘点报表";
			if(check!=null) {
				orgName =orgName+"-"+ check.getOrgName();
				fileName=check.getOrgName()+"盘点报表";
			}
			os = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename="+ java.net.URLEncoder.encode(fileName,"UTF-8")+ ".xls"); //设定输出文件头
			response.setContentType("application/excel"); //定义输出类型
			wb = Workbook.createWorkbook(os);
	        WritableSheet ws = wb.createSheet("仓库进出汇总表",0); 
	         
	        int startRowNum=0;//起始行 
	        int startColNum=0;//起始列 
	        //int maxColSize = 18;//最大列数 
	         
	        //设置列宽 
	        ws.setColumnView(0, 18); //编码
	        ws.setColumnView(1, 18); //名称
	        ws.setColumnView(2, 25); //规格
	        for (int i = 3; i < 16; i++) {
	    	   ws.setColumnView(i, 8); 
	        }
	        ws.setColumnView(5, 12); 
	        ws.setColumnView(7, 13); 
	        ws.setColumnView(13, 16); 
	        ws.setColumnView(18, 14); //备注列
	        
	        ws.setRowView(0, 500);
	        ws.setRowView(1, 500);
	        ws.setRowView(2, 500);
	        
	        //去掉整个sheet中的网格线  
	        ws.getSettings().setShowGridLines(false);  
         
			//页眉:第一行分公司中文全称，第二行分公司英文名,第三行文件名
	        ws.addCell(new Label(0, 0, commonCompany1.getCnfullname(),wcf_title));
	        ws.mergeCells(0,0, 17,0); 
			
	        ws.addCell(new Label(0, 1, commonCompany1.getEnfullname(),wcf_title));
	        ws.mergeCells(0,1, 17,1); 
			
	        ws.addCell(new Label(0, 2, fileName,wcf_title));
	        ws.mergeCells(0,2, 17,2); 
			
	        //第三行
	        startRowNum=3;
	        startColNum=0;
	        
	        //第四行
	        ws.addCell(new Label(startColNum,startRowNum, "报送单位："+orgName,wcf_name_center)); 
	        ws.mergeCells(startColNum,startRowNum, startColNum+4,startRowNum); 
	        
	        ws.addCell(new Label(startColNum+4,startRowNum, ""));//空格
	       
	        Object startDateOjb=map.get("startDate")==null?(check==null?"":check.getStartDate()):map.get("startDate");
	        Object endDateOjb=map.get("endDate")==null?(check==null?"":check.getEndDate()):map.get("endDate");
	       
	        ws.addCell(new Label(startColNum+5,startRowNum, "报表所属期："+startDateOjb+" 至 "+endDateOjb,wcf_name_center));
	        ws.mergeCells(startColNum+5,startRowNum, startColNum+17,startRowNum); 
	        
	        startColNum=0; 
	        startRowNum=4; 
	        
	        //第五行、第六行
	        ws.addCell(new Label(startColNum,startRowNum,"商品编码",wcf_value)); 
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1); 
	        
	        startColNum=1; 
	        ws.addCell(new Label(startColNum,startRowNum,"商品名称",wcf_value)); 
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1); 
	        
	        startColNum=2; 
	        ws.addCell(new Label(startColNum,startRowNum,"商品规格",wcf_value)); 
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1); 
	        
	        startColNum=3; 
	        ws.addCell(new Label(startColNum,startRowNum,"计量单位",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1); 
	       
	        startColNum=4; 
	        ws.addCell(new Label(startColNum,startRowNum,"上月账面结存数量",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1); 
	       
	        startColNum=5; 
	        startRowNum=4;
	        ws.addCell(new Label(startColNum,startRowNum,"本月入库数（账面数）",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum+2,startRowNum); 
	        ws.addCell(new Label(startColNum,5,"本月入库数量",wcf_value));
	        startColNum=6; 
	        ws.addCell(new Label(startColNum,5,"归还数量",wcf_value));
	        startColNum=7; 
	        ws.addCell(new Label(startColNum,5,"其他",wcf_value));
	        
	        startColNum=8; 
	        ws.addCell(new Label(startColNum,startRowNum,"本月减少数     （账面数）",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, 12,startRowNum);
	        startRowNum=5;
	        ws.addCell(new Label(startColNum,startRowNum,"销售安装",wcf_value));
	        startColNum=9; 
	        ws.addCell(new Label(startColNum,startRowNum,"车行调剂",wcf_value));
	        startColNum=10;
	        ws.addCell(new Label(startColNum,startRowNum,"升级",wcf_value));
	        startColNum=11;
	        ws.addCell(new Label(startColNum,startRowNum,"借出数量",wcf_value));
	        startColNum=12;
	        ws.addCell(new Label(startColNum,startRowNum,"其他",wcf_value));
	       
	        startColNum=13; 
	        startRowNum=4;
	        ws.addCell(new Label(startColNum,startRowNum,"月末账面结存数量",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1);
	        
	        startColNum=14;
	        ws.addCell(new Label(startColNum,startRowNum,"月末实物盘存",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1);
	        
	        startColNum=15;
	        startRowNum=4;
	        ws.addCell(new Label(startColNum,startRowNum,"盈亏数",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1);
		    
	        startColNum=16;
	        startRowNum=4;
	        ws.addCell(new Label(startColNum,startRowNum,"调账数",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1);
		    
	        startColNum=17; 
	        ws.addCell(new Label(startColNum,startRowNum,"备注",wcf_value));
	        ws.mergeCells(startColNum,startRowNum, startColNum,startRowNum+1);
		    
	        startRowNum=6;       
	        startColNum=0; 
	         
	         if (check != null) {
	        	List<HashMap<String, Object>> checkDetailsList = check.getCheckDetailsMap();
	        	HashMap<String, Object> checkDetails=null;
	        	int rowIndex=0;
	        	if (checkDetailsList != null && !checkDetailsList.isEmpty()) {
	        		int listLenth=checkDetailsList.size();
	        		for (int j = 0; j < listLenth; j++) {
	        			checkDetails = checkDetailsList.get(j);
						rowIndex = startRowNum+j; 
						ws.addCell(new Label(startColNum++,rowIndex,checkDetails.get("productCode") == null ?"":checkDetails.get("productCode").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,checkDetails.get("productName") == null ?"":checkDetails.get("productName").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,checkDetails.get("norm") == null ?"":checkDetails.get("norm").toString(),wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,checkDetails.get("unit") == null ?"":checkDetails.get("unit")+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("lastSaveNum") == null ?"":checkDetails.get("lastSaveNum"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("curInNum") == null ?"":checkDetails.get("curInNum"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("curReturnNum") == null ?"":checkDetails.get("curReturnNum"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("otherInNum") == null ?"":checkDetails.get("otherInNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("selOutNum") == null ?"":checkDetails.get("selOutNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("borrowOutNum") == null ?"":checkDetails.get("borrowOutNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("upgradeOutNum") == null ?"":checkDetails.get("upgradeOutNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("curBorrowNum") == null ?"":checkDetails.get("curBorrowNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("otherOutNum") == null ?"":checkDetails.get("otherOutNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("curSaveNum") == null ?"":checkDetails.get("curSaveNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("curObjectNum") == null ?"":checkDetails.get("curObjectNum"))+"",wcf_value)); 
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("overShortNum") == null ?"":checkDetails.get("overShortNum"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,(checkDetails.get("changeNum") == null ?"":checkDetails.get("changeNum"))+"",wcf_value));
						ws.addCell(new Label(startColNum++,rowIndex,checkDetails.get("remark")==null?"":checkDetails.get("remark").toString(),wcf_value));
						startColNum=0;
					}
	        	}
	        }
	        wb.write();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}  finally {
			try {
				wb.close();
				//os.flush();    
				os.close();
			} catch (WriteException e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				e.printStackTrace();
			}
		} 
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出盘点记录(报表) 结束");
		}
	}
	@RequestMapping(value = "/sell/findCheck4Report")
	public @ResponseBody Check findCheck4Report(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询盘点记录 开始");
		}
		Check results =null;
		try {
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//根据分公司或者营业处id得到下面的所有仓库列表
			List<Long> wareHouseIds = new ArrayList<Long>();
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			/*String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);*/
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}else{//如果下面没有仓库，则不能看到任何数据
					map.put("whsId", "00");
				}
			}
			
			results = checkService.getCheckAndDetails4Report(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询盘点记录 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/sell/findCheck4CompanyReport")
	public @ResponseBody List<HashMap<String, Object>> findCheck4CompanyReport(@RequestBody Map<String, Object> map,HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询盘点记录(用于分公司或者总部) 开始");
		}
		List<HashMap<String, Object>> results =null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel(orgId);
			//是否是总部，特殊处理
			String ishead = Utils.isNullOrEmpty((request.getSession().getAttribute(
					SystemConst.ACCOUNT_ISHQ)))? "false":request.getSession().getAttribute(
							SystemConst.ACCOUNT_ISHQ).toString();
			boolean ishq = Boolean.valueOf(ishead);
			if(level==0){//总部
				
			}else if(level==1){//分公司
				if(!ishq){
					map.put("companyId", Long.valueOf(companyId));
				}
			}else if(level==2){//营业处
				if(!ishq){
					map.put("companyId", Long.valueOf(companyId));
				}
			}
			results = checkService.findCheckDetails4Reprot(map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询盘点记录(用于分公司或者总部) 结束");
		}
		return results;
	}
	@RequestMapping(value = "/sell/impCheck")
	@LogOperation(description = "导入盘点", type =  SystemConst.OPERATELOG_ADD, model_id = 2)
	public void impCheck(@RequestParam MultipartFile checkFile,HttpServletRequest request,HttpServletResponse response) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导入盘点 开始");
		}
		InputStream is = null;
		OutputStream out = null;
		String msg="导入成功";
		String totalMsg=null;
		try {
			//上传文件
			String configPath = Config.getConfigPath();
			String propertiesPath = configPath + "classes/fileupload.properties";
			Properties properties = new Properties();
			is = new FileInputStream(propertiesPath);
			properties.load(is);
			
			//上传文件的根目录
	        String path =properties.getProperty("uploadRootPath"); 
			// request.getSession().getServletContext().getRealPath("upload");  
	        //String fileName = checkFile.getOriginalFilename();  
	        File targetFileDir = new File(path);
	        boolean isGoOn=true;
	        if(!targetFileDir.exists()){  
	            isGoOn=targetFileDir.mkdirs();  
	        }  
	        //保存  文件
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的 
            if(isGoOn){
            	File targetFile = new File(targetFileDir, checkFile.getOriginalFilename());
            	FileUtils.copyInputStreamToFile(checkFile.getInputStream(), targetFile); 
            	List<String[]> dataList = CreateExcel_PDF_CSV.getData(targetFile);
            	jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("上传的数据:");
				jsonGenerator.writeObject(dataList);
				
				//表示有数据
				String[] datas=null;
				String tempStr=null;
				String dataArray[]=null;
				List<String> productCodes=new ArrayList<String>();//存放商品编码
				if(dataList!=null && dataList.size()>4){
					
					Check check = new Check();
					String orgId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_ORGID);
					String companyId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_COMPANYID);
					String userId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_ID);
					check.setOrgId(orgId==null?null:Long.valueOf(orgId));
					check.setCompanyId(companyId==null?null:Long.valueOf(companyId));
					check.setUserId(userId==null?null:Long.valueOf(userId));
					OpenLdap openLdap = OpenLdapManager.getInstance();
					CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
					String whsName=null;
					if(commonCompany!=null) {
						whsName=commonCompany.getCompanyname();
						if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
							check.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
						}
						if(StringUtils.isNotBlank(commonCompany.getOpid())){
							check.setDirectorId(Long.valueOf(commonCompany.getOpid()));
						}
					}
					commonCompany =  openLdap.getCompanyByOrgId(orgId);
					String orgName=null;
					if (commonCompany != null && StringUtils.isNotBlank(commonCompany.getCompanyname())) {
						orgName=commonCompany.getCompanyname();
					}
					CheckDetails checkDetails=null;
					List<CheckDetails> checkDetailsList=null;
					int listLenth=dataList.size();
					for (int i = 0; i < listLenth; i++) {
						datas=dataList.get(i);
						if(i==0||i==2){
							//第一、三跳过
							continue;
						}else if(i==1){
							//第二行 获得开始日期，结束日期
							if(datas.length>4){
								tempStr=datas[5].replace("报表所属期：", "");
								dataArray=tempStr.split("至 ");
								if(dataArray.length>1){
									//标题
									check.setName(whsName+" "+tempStr.replaceAll("\\s*", "")+"盘点");
									//开始时间
									check.setStartDate(DateUtil.parse(dataArray[0].trim(), DateUtil.YDM_DASH));
									//结束时间
									check.setEndDate(DateUtil.parse(dataArray[1].trim(), DateUtil.YDM_DASH));
								}else{
									msg="导入格式有误!";
									break;
								}
							}else{
								msg="导入格式有误!";
								break;
							}
						}else{
							checkDetails=new CheckDetails();
							
							productCodes.add(datas[0]);//商品编码
							
							checkDetails.setProductCode(datas[0]);//先用productCode保存商品编码
							checkDetails.setLastSaveNum(StringUtils.isBlank(datas[4])?0:Long.valueOf(datas[4]));//上月帐面结存数量
							checkDetails.setCurInNum(StringUtils.isBlank(datas[5])?0:Long.valueOf(datas[5]));//本月入库数量
							checkDetails.setCurOutNum(StringUtils.isBlank(datas[6])?0:Long.valueOf(datas[6]));//本月出库数量
							checkDetails.setCurSaveNum(StringUtils.isBlank(datas[7])?0:Long.valueOf(datas[7]));//月未帐面结存数量
							checkDetails.setCurObjectNum(StringUtils.isBlank(datas[8])?0:Long.valueOf(datas[8]));//月未实物盘存
							checkDetails.setOverShortNum(StringUtils.isBlank(datas[9])?0:Long.valueOf(datas[9]));//盘盈数/盘亏数
							checkDetails.setChangeNum(StringUtils.isBlank(datas[10])?0:Long.valueOf(datas[10]));//盘盈数/盘亏数
							checkDetails.setRemark(datas[11]);
							//如果调账数加上盈亏数=0，则表示调账已完成,否则表示已审核完成,还需调账
							if(checkDetails.getOverShortNum()+checkDetails.getChangeNum()==0){
								check.setStatus(3);
							}else{
								check.setStatus(2);
							}
							if(check.getCheckDetails()==null){
								checkDetailsList=new ArrayList<CheckDetails>();
								check.setCheckDetails(checkDetailsList);
							}
							check.getCheckDetails().add(checkDetails);
						}
						
					}
					if(check.getCheckDetails()!=null && !check.getCheckDetails().isEmpty()){
						Map<String,Object> map=new HashMap<String, Object>();
						map.put("codes", productCodes);
						map.put("companyId", companyId);
						List<HashMap<String, Object>> products=productService.findProducts(map);
						if(products!=null && !products.isEmpty()){
							for (CheckDetails checkDetails2 : check.getCheckDetails()) {
								for (HashMap<String, Object> product : products) {
									if(checkDetails2.getProductId().equals(product.get("code"))){
										if(product.get("id")!=null){
											checkDetails2.setProductId(Long.valueOf(product.get("id").toString()));
										}
										break;
									}
								}
							}
							
						}
						int result = checkService.addCheck(check);
						if (result == -1) {
							msg = "导入格式有误";
						}else if (result == 2){
							msg = "本月已盘点过，无需再盘点!";
						}
					}
				}else{
					msg="导入格式有误!";
				}
            }
            out = response.getOutputStream();
            totalMsg = "<script>parent.callback('"+msg+"');</script>";
            out.write(totalMsg.getBytes());
            out.flush();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}finally{
            if(out != null){
                try{
                	out.close();
                }catch(Exception e1){
                	LOGGER.error(e1.getMessage(), e1);
                	e1.printStackTrace();
                }
             }
         }
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导入盘点  结束");
		}
	}
	
	@RequestMapping(value = "/sell/deleteChecks")
	@LogOperation(description = "删除盘点", type = SystemConst.OPERATELOG_DEL, model_id = 2)
	public @ResponseBody
	HashMap<String, Object> deleteChecks(
			@RequestBody List<Long> list,
			HttpServletRequest request) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
				//将参数添加到日志中
				request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(list,true));
			
				int result = checkService.deleteChecks(list);
				if (result == -1) {
					flag = false;
					msg = "参数不正确";
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
	
	@RequestMapping(value = "/sell/canStock")
	public @ResponseBody Boolean canStock(HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("判断能进行出入库操作 开始");
		}
		Boolean results = false;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			results=!checkService.checkStatus(companyId==null?null:Long.valueOf(companyId), null, 3, false);//判断是否还有盘点未完成,有:true，没有：false
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("判断能进行出入库操作 结束");
		}
		return results;
	}
	
	@RequestMapping(value = "/sell/findChecksByPage")
	public @ResponseBody Page<HashMap<String, Object>> findChecksByPage(@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询盘点记录 开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			if (pageSelect != null) {
				if ( pageSelect.getFilter() == null) {
					pageSelect.setFilter(new HashMap<String, Object>());
				}
				/*String orgId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID);
				if (StringUtils.isNotBlank(orgId)) {
					map.put("orgId", orgId);
				}*/
				String orgId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ORGID);
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				OpenLdap openLdap = OpenLdapManager.getInstance();
				//判断是否是分公司
				int level=openLdap.getUserCompanyLevel(orgId);
				if(level==0){//总部
					
				}else{//分公司或者营业处
					//根据分公司或者营业处id得到下面的所有仓库列表
					if(StringUtils.isNullOrEmpty(pageSelect.getFilter().get("whsId"))){
						List<Long> wareHouseIds = new ArrayList<Long>();
						List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
						for (CommonCompany commonCompany2 : wareHouseList) {
							wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
						}
						if (!wareHouseIds.isEmpty()) {
							pageSelect.getFilter().put("wareHouseIds", wareHouseIds);
						}else{//如果下面没有仓库，则不能看到任何数据
							pageSelect.getFilter().put("whsId", "00");
						}
					}
				}
			}
			result = checkService.findChecksByPage(pageSelect);
		
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询盘点记录 结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/sell/updateCheckChangeNum")
	@LogOperation(description = "盘点调账", type = SystemConst.OPERATELOG_UPDATE, model_id = 2 )
	public @ResponseBody
	HashMap<String, Object> updateCheckChangeNum(
		 @RequestBody Check check,HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("盘点调账 开始");
		}
		HashMap<String, Object> resultMap =null;
		try {
			//将参数添加到日志中
			request.setAttribute(SystemConst.OPLOG_PARAMS_KEY, JsonUtil.oToJ(check,true));
		
			if (LOGGER.isDebugEnabled()) {
				jsonGenerator = objectMapper.getJsonFactory()
						.createJsonGenerator(System.out, JsonEncoding.UTF8);
				System.out.println("参数:");
				jsonGenerator.writeObject(check);
			}
			if (check != null) {
				// 从session中得到当前登录人id
				String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
				check.setUserId(userId==null?null:Long.valueOf(userId));
				String companyId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_COMPANYID);
				check.setCompanyId(companyId==null?null:Long.valueOf(companyId));
				String currentWhsCode=null;
				OpenLdap openLdap = OpenLdapManager.getInstance();
				/*//获得当前登录人==仓管员所管理的仓库
				CommonCompany commonCompany = openLdap.getWarehouseByUserId(userId);
				if(commonCompany!=null) {
					if(StringUtils.isNotBlank(commonCompany.getCompanyno())){
						check.setWhsId(Long.valueOf(commonCompany.getCompanyno()));
					}
					currentWhsCode=commonCompany.getCompanycode();
				}*/
				//设置仓库，机构下的仓库
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					check.setWhsId(Long.valueOf(commonCompany2.getCompanyno()));
					check.setWhsName(commonCompany2.getCompanyname());
					currentWhsCode=commonCompany2.getCompanycode();
					break;
				}
				resultMap = checkService.updateCheckChangeNum(check,currentWhsCode);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			resultMap=new HashMap<String, Object>();
			resultMap.put(SystemConst.SUCCESS, false);
			resultMap.put(SystemConst.MSG, StringUtils.isBlank(e.getMessage())?SystemConst.OP_FAILURE:e.getMessage());
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("盘点调账 结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/sell/exportCheck")
	public @ResponseBody
	void exportCheck(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出excel 开始");
		}
		List<HashMap<String, Object>> list = null;
		try {
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
			HashMap map = new HashMap();
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
		    
		    String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
			list = checkService.findAllChecks(map);
		/*	String[] title = new String[6];
			title[columnIndex] = "序号";
			title[columnIndex] = "盘点名称";//name
			title[columnIndex] = "开始日期";//startDate
			title[columnIndex] = "结束日期";//endDate
			title[columnIndex] = "盘点状态";//status
			title[columnIndex] = "盘点时间";//stamp
*/		     
			String[][] title = {{"序号","10"},{"盘点名称","20"},{"开始日期","20"},{"结束日期","20"},{"盘点状态","16"},{"盘点时间","30"}};
			
			List valueList = new ArrayList();
			HashMap<String, Object> dataMap = null;
			String[] values = null;
			int listLenth=list.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
        		values = new String[6];
				dataMap = list.get(i);
				values[0] = String.valueOf(i+1);
				values[columnIndex] = dataMap.get("name") == null ?"":dataMap.get("name").toString();
				values[columnIndex] = dataMap.get("startDate") == null ?"":dataMap.get("startDate").toString();
				values[columnIndex] = dataMap.get("endDate") == null ?"":dataMap.get("endDate").toString();
				values[columnIndex] = WhsConst.CHECK_STATUS.get(dataMap.get("status") == null ?0:Integer.parseInt(dataMap.get("status").toString()));
				values[columnIndex] = dataMap.get("stamp") == null ?"":dataMap.get("stamp").toString();
				valueList.add(values);
			}
			//获得分公司中英文名称
			OpenLdap openLdap = OpenLdapManager.getInstance();
        	CommonCompany commonCompany =openLdap.getCompanyById(companyId);
        	//获得当前登录人==仓管员所管理的仓库 
        	String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			String fileTitle="盘点列表";
			/*CommonCompany commonCompany2 = openLdap.getWarehouseByUserId(userId);
			if(commonCompany2!=null) {
				fileTitle=commonCompany2.getCompanyname()+"盘点列表";
			}*/
			//设置仓库，机构下的仓库
			List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
			for (CommonCompany commonCompany2 : wareHouseList) {
				fileTitle=commonCompany2.getCompanyname()+"盘点列表";
				break;
			}
			CreateExcel_PDF_CSV.createExcel(valueList, response, fileTitle, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("导出excel 结束");
		}
	}
	
	@RequestMapping(value = "/sell/exportCheck4View")
	public @ResponseBody
	void exportCheck4View(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查看页面中导出excel 开始");
		}
		List<HashMap<String, Object>> list = null;
		try {
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
			HashMap map = new HashMap();
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
		    
		    String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			if (StringUtils.isNotBlank(companyId)) {
				map.put("companyId", companyId);
			}
		   /* String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
		    map.put("orgId", orgId);*/
			//根据分公司或者营业处id得到下面的所有仓库列表
			List<Long> wareHouseIds = new ArrayList<Long>();
			String orgId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			if(StringUtils.isNullOrEmpty(map.get("whsId"))){
				List<CommonCompany> wareHouseList = openLdap.getWarehouseByOrgId(companyId,userId);
				for (CommonCompany commonCompany2 : wareHouseList) {
					wareHouseIds.add(commonCompany2.getCompanyno()==null?null:Long.valueOf(commonCompany2.getCompanyno()));
				}
				if (!wareHouseIds.isEmpty()) {
					map.put("wareHouseIds", wareHouseIds);
				}else{//如果下面没有仓库，则不能看到任何数据
					map.put("whsId", "00");
				}
			}
			Check check=checkService.getCheckAndDetails4Report(map);
			String fileName="盘点列表";
			if(check!=null){
				list =check.getCheckDetailsMap();
				fileName=check.getName();
			}

			String[][] title = {{"序号","10"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"上月帐面结存数量","16"},{"本月入库数量","16"},{"本月出库数量","20"},{"月未帐面结存数量","16"},{"月未实物盘存","16"},{"盈亏数","16"},{"调账数","16"},{"备注","30"}};
			int columnIndex=0;
			if(map.get("isCheck")!=null){//审核
				title=null;
				title= new String[11][];
				columnIndex=0;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="序号";
				title[columnIndex][1]="10";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="商品编码";
				title[columnIndex][1]="20";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="商品名称";
				title[columnIndex][1]="20";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="商品规格";
				title[columnIndex][1]="40";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="上月帐面结存数量";
				title[columnIndex][1]="16";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="本月入库数量";
				title[columnIndex][1]="16";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="本月出库数量";
				title[columnIndex][1]="16";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="月未帐面结存数量";
				title[columnIndex][1]="16";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="月未实物盘存";
				title[columnIndex][1]="16";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="盈亏数";
				title[columnIndex][1]="16";
				columnIndex++;
				title[columnIndex]=new String[2];
				title[columnIndex][0]="备注";
				title[columnIndex][1]="20";
			}
			
			List valueList = new ArrayList();
			HashMap<String, Object> dataMap = null;
			String[] values = null;
			int listLenth=list.size();
			for(int i=0; i<listLenth; i++){
				
				dataMap = list.get(i);
				if(map.get("isCheck")!=null){//审核
					values = new String[11];
					columnIndex=0;
					values[columnIndex] = String.valueOf(i+1);
					columnIndex++;
					values[columnIndex] = dataMap.get("productCode") == null ? "" : dataMap.get("productCode").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("productName") == null ? "" : dataMap.get("productName").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("norm") == null ? "" : dataMap.get("norm").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("lastSaveNum") == null ?"":dataMap.get("lastSaveNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curInNum") == null ?"":dataMap.get("curInNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curOutNum") == null ?"":dataMap.get("curOutNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curSaveNum") == null ?"":dataMap.get("curSaveNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curObjectNum") == null ?"":dataMap.get("curObjectNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("overShortNum") == null ?"":dataMap.get("overShortNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("remark") == null ?"":dataMap.get("remark").toString();

				}else{
					values = new String[12];
					columnIndex=0;
					values[columnIndex] = String.valueOf(i+1);
					columnIndex++;
					values[columnIndex] = dataMap.get("productCode") == null ? "" : dataMap.get("productCode").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("productName") == null ? "" : dataMap.get("productName").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("norm") == null ? "" : dataMap.get("norm").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("lastSaveNum") == null ?"":dataMap.get("lastSaveNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curInNum") == null ?"":dataMap.get("curInNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curOutNum") == null ?"":dataMap.get("curOutNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curSaveNum") == null ?"":dataMap.get("curSaveNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("curObjectNum") == null ?"":dataMap.get("curObjectNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("overShortNum") == null ?"":dataMap.get("overShortNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("changeNum") == null ?"":dataMap.get("changeNum").toString();
					columnIndex++;
					values[columnIndex] = dataMap.get("remark") == null ?"":dataMap.get("remark").toString();
				}
        		
				valueList.add(values);
			}
			//获得分公司中英文名称
        	CommonCompany commonCompany =openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, fileName, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),false);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查看页面中导出excel 结束");
		}
	}
	
	@RequestMapping(value = "/sell/exportCheck4CompanyReport")
	public @ResponseBody
	void exportCheck4CompanyReport(
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分公司、总部导出excel 开始");
		}
		List<HashMap<String, Object>> list = null;
		try {
			Map returnMap =   request.getParameterMap();
			//将request.getParameterMap()转换成可操作的普通Map
			// 返回值Map
			HashMap map = new HashMap();
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
		    
		    String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			OpenLdap openLdap = OpenLdapManager.getInstance();
			//判断是否是分公司
			int level=openLdap.getUserCompanyLevel((String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID));
			//是否是总部，特殊处理
			String ishead = Utils.isNullOrEmpty((request.getSession().getAttribute(
					SystemConst.ACCOUNT_ISHQ)))? "false":request.getSession().getAttribute(
							SystemConst.ACCOUNT_ISHQ).toString();
			boolean ishq = Boolean.valueOf(ishead);
			if(level==0){//总部
				
			}else if(level==1){//分公司
				if(!ishq){
					map.put("companyId", companyId);
				}
			}
			list=checkService.findCheckDetails4Reprot(map);
			String fileName="盘点报表";

			String[][] title = {{"序号","10"},{"商品编码","20"},{"商品名称","20"},{"商品规格","40"},{"单位","10"},{"上月帐面结存数量","16","1"},{"本月入库数量","16","1"},{"本月出库数量","20","1"},{"月未帐面结存数量","16","1"},{"月未实物盘存","16","1"},{"盈亏数","16","1"},{"调账数","16","1"}};
			HashMap<String, Object> dataMap = null;
			String[] values = null;
			List valueList = new ArrayList();
			int listLenth=list.size();
			int columnIndex=0;
			for(int i=0; i<listLenth; i++){
				dataMap = list.get(i);
				values = new String[12];
				columnIndex=0;
				values[columnIndex] = String.valueOf(i+1);
				columnIndex++;
				values[columnIndex] = dataMap.get("productCode") == null ? "" : dataMap.get("productCode").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("productName") == null ? "" : dataMap.get("productName").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("norm") == null ? "" : dataMap.get("norm").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("unit") == null ? "" : dataMap.get("unit").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("lastSaveNum") == null ?"":dataMap.get("lastSaveNum").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("curInNum") == null ?"":dataMap.get("curInNum").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("curOutNum") == null ?"":dataMap.get("curOutNum").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("curSaveNum") == null ?"":dataMap.get("curSaveNum").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("curObjectNum") == null ?"":dataMap.get("curObjectNum").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("overShortNum") == null ?"":dataMap.get("overShortNum").toString();
				columnIndex++;
				values[columnIndex] = dataMap.get("changeNum") == null ?"":dataMap.get("changeNum").toString();

        		
				valueList.add(values);
			}
			//获得分公司中英文名称
        	CommonCompany commonCompany =openLdap.getCompanyById(companyId);
	
			CreateExcel_PDF_CSV.createExcel(valueList, response, fileName, title,commonCompany.getCnfullname(),commonCompany.getEnfullname(),true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查看页面中导出excel 结束");
		}
	}
}
