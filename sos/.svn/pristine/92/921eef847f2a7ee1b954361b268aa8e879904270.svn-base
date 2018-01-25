package com.gboss.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import ldap.util.Config;
import ldap.util.IOUtil;

import org.csource.common.*;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Documents;
import com.gboss.service.CustomerService;
import com.gboss.service.DocumentService;
import com.gboss.util.FileUploadClient;
import com.gboss.util.LogOperation;
import com.gboss.util.UUIDCreater;
import com.gboss.util.Utils;

/**
 * @Package:com.gboss.controller
 * @ClassName:DocumentController
 * @Description:原始单据控制层
 * @author:xiaoke
 * @date:2014-4-23 下午3:15:49
 */
@Controller
public class DocumentController extends BaseController{
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(NetworkController.class);
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/document/list")
	public @ResponseBody List<Documents> list
			(Documents documents, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Object unit_id = request.getSession().getAttribute(SystemConst.SEARCH_UNITID);
		if(unit_id==null){
			unit_id = 0;
		}
		List<Documents> result = documentService.search(Long.parseLong(unit_id.toString()));
		request.getSession().removeAttribute(SystemConst.SEARCH_UNITID);
		return result;
	}
	
	/*
	@RequestMapping(value = "/document/fileUpLoad",method = RequestMethod.POST)
	public @ResponseBody HashMap fileUpLoad(@RequestBody Documents documents, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Long cust_id = documents.getCustomer_id();
		Customer cust = customerService.getCustomer(cust_id);
		String time = Utils.getDateString();
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		String typename = "";
		List<Integer> types = documents.getTypes();
		for(Integer i:types){
			switch (i) {
			case 1:
				typename = "身份证";
				break;
			case 2:
				typename = "行驶证";
				break;
			case 3:
				typename = "入网证";
				break;
			case 4:
				typename = "托收协议";
				break;
			case 5:
				typename = "银行卡/存折";
				break;
			case 6:
				typename = "保单";
				break;
			case 7:
				typename = "保卡";
				break;
			case 8:
				typename = "回单";
				break;
			}
			String filename = cust.getCustomer_name()+typename+"单据("+time+")";
			Documents entry = new Documents();
            entry.setCustomer_id(documents.getCustomer_id());
            entry.setVehicle_id(documents.getVehicle_id());
            entry.setName(filename);
			entry.setType(i);
			//entry.setTime(Utils.getNowTimeString());
			
			entry.setLocation("");
			documentService.add(entry);
		}
        resultMap.put("success", true);
		resultMap.put("msg", "操作成功!");
		request.getSession().setAttribute(SystemConst.SEARCH_CUSTID, cust_id);
		return resultMap;
	}*/
	
	@RequestMapping(value = "/document/audit",method = RequestMethod.POST)
	@LogOperation(description = "审核通过", type = SystemConst.OPERATELOG_UPDATE, model_id = 20015)
	public @ResponseBody HashMap audit(@RequestBody Documents documents, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Long id = documents.getUnit_id();
		documentService.auditsuccsee(id);
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
        resultMap.put("success", true);
		resultMap.put("msg", "操作成功!");
		request.getSession().setAttribute(SystemConst.SEARCH_UNITID, id);
		return resultMap;
	}
	
	@RequestMapping(value = "/document/auditno",method = RequestMethod.POST)
	@LogOperation(description = "审核不通过", type = SystemConst.OPERATELOG_UPDATE, model_id = 20015)
	public @ResponseBody HashMap auditno(@RequestBody Documents documents, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Long id = documents.getUnit_id();
		String remark = documents.getRemark();
		documentService.auditfail(id, remark);
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
        resultMap.put("success", true);
		resultMap.put("msg", "操作成功!");
		request.getSession().setAttribute(SystemConst.SEARCH_UNITID, id);
		return resultMap;
	}
	
	/*
	@RequestMapping(value = "/document/fileUpLoad",method = RequestMethod.POST)
	public String fileUpLoad(@RequestParam(value = "file", required = false) MultipartFile[] files, 
			                                @RequestParam(value = "cust_id", required = false) Long cust_id, 
			                                @RequestParam(value = "vehicle_id", required = false) Long vehicle_id,
			                                @RequestParam(value = "unit_id", required = false) Long unit_id,
			                                @RequestParam(value = "documenttype", required = false) Integer[] types, 
			                                HttpServletRequest request) throws SystemException {
		String configPath = Config.getConfigPath();
		String propertiesPath = configPath + "classes/fileupload.properties";
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(propertiesPath);
			properties.load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeIS(is);
		}
		String documentfileUrl = properties.getProperty("documentfileUrl");
		
		Customer cust = customerService.getCustomer(cust_id);
		String time = Utils.getDateString();
		String typename = "";
		for(int i=0;i<files.length;i++){
			MultipartFile file = files[i];
			Integer type = types[i];
			switch (type) {
			case 1:
				typename = "身份证";
				break;
			case 2:
				typename = "行驶证";
				break;
			case 3:
				typename = "入网证";
				break;
			case 4:
				typename = "托收协议";
				break;
			case 5:
				typename = "银行卡/存折";
				break;
			case 6:
				typename = "保单";
				break;
			case 7:
				typename = "保卡";
				break;
			case 8:
				typename = "回单";
				break;
			}
			String file_name = file.getOriginalFilename();
			String extension = file_name.substring(file_name.indexOf("."));
			String prefix = UUIDCreater.getUUID();
			String location = documentfileUrl + "/fileSys/"+(prefix+extension);
			String urlStr = documentfileUrl + "/upload.action?fileName="+(prefix+extension);
			try {
				InputStream input = file.getInputStream();
				FileUploadClient.fileUpload(urlStr, input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String filename = cust.getCustomer_name()+typename+"单据("+time+")";
			Documents entry = new Documents();
            entry.setCustomer_id(cust_id);
            entry.setVehicle_id(vehicle_id);
            entry.setUnit_id(unit_id);
            entry.setDoc_name(filename);
			entry.setDoc_type(type);
			entry.setDoc_path(location);
			documentService.add(entry);
		}
		request.getSession().setAttribute(SystemConst.SEARCH_UNITID, unit_id);
		return "close";
	}*/
	
	@RequestMapping(value = "/document/fileUpLoad",method = RequestMethod.POST)
	@LogOperation(description = "上传原始单据", type = SystemConst.OPERATELOG_ADD, model_id = 20015)
	public String fileUpLoad(@RequestParam(value = "file", required = false) MultipartFile[] files, 
			                                @RequestParam(value = "cust_id", required = false) Long cust_id, 
			                                @RequestParam(value = "vehicle_id", required = false) Long vehicle_id,
			                                @RequestParam(value = "unit_id", required = false) Long unit_id,
			                                @RequestParam(value = "documenttype", required = false) Integer[] types, 
			                                HttpServletRequest request) throws SystemException {
		String configPath = Config.getConfigPath();
		String propertiesPath = configPath + "classes/fileupload.properties";
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(propertiesPath);
			properties.load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeIS(is);
		}
		String serverUrl = properties.getProperty("serverUrl");
		
		String conf_filename = Config.getConfigPath()+"classes/fdfs_client.conf";
		Customer cust = customerService.getCustomer(cust_id);
		String time = Utils.getDateString();
		String typename = "";
		
		try {
			ClientGlobal.init(conf_filename);
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			
			for(int i=0;i<files.length;i++){
				MultipartFile file = files[i];
				Integer type = types[i];
				switch (type) {
				case 1:
					typename = "身份证";
					break;
				case 2:
					typename = "行驶证";
					break;
				case 3:
					typename = "入网证";
					break;
				case 4:
					typename = "托收协议";
					break;
				case 5:
					typename = "银行卡/存折";
					break;
				case 6:
					typename = "保单";
					break;
				case 7:
					typename = "保卡";
					break;
				case 8:
					typename = "回单";
					break;
				}
				String file_name = file.getOriginalFilename();
				String extension = file_name.substring(file_name.indexOf(".")+1);
				NameValuePair[] metaList = new NameValuePair[1];
				metaList[0] = new NameValuePair("fileName", file_name);
				String location = client.upload_appender_file1(file.getBytes(), extension, metaList);
				System.out.println("upload success. file id is: " + location);
				
				String filename = cust.getCustomer_name()+typename+"单据("+time+")";
				Documents entry = new Documents();
	            entry.setCustomer_id(cust_id);
	            entry.setVehicle_id(vehicle_id);
	            entry.setUnit_id(unit_id);
	            entry.setDoc_name(filename);
				entry.setDoc_type(type);
				entry.setDoc_path(serverUrl+location);
				documentService.add(entry);
			}
			trackerServer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.getSession().setAttribute(SystemConst.SEARCH_UNITID, unit_id);
		return "close";
	}
	
	@RequestMapping(value = "/document/delDoc",method = RequestMethod.POST)
	@LogOperation(description = "删除原始单据", type = SystemConst.OPERATELOG_DEL, model_id = 20015)
	public @ResponseBody HashMap delDoc(@RequestBody Documents documents, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		Long docid = documents.getDoc_id();
		Long unit_id = documents.getUnit_id();
		if(docid==null){
			resultMap.put("success", false);
			resultMap.put("msg", "操作失败!");
		}else{
			documentService.delete(Documents.class, docid);
			resultMap.put("success", true);
			resultMap.put("msg", "操作成功!");
		}
		request.getSession().setAttribute(SystemConst.SEARCH_UNITID, unit_id);
		return resultMap;
	}

}

