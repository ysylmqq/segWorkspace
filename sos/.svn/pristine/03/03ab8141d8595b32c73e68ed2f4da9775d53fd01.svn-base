package com.gboss.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import ldap.util.Config;
import ldap.util.IOUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemException;
import com.gboss.pojo.web.VerifyPOJO;

@Controller
public class FileUpLoadController extends BaseController{
	
	@RequestMapping(value = "/fileUpLoad",method = RequestMethod.POST)
	public @ResponseBody HashMap fileUpLoad(@RequestBody VerifyPOJO verify, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
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
		String uploadRootPath = properties.getProperty("uploadRootPath"); // 上传文件的根目录
	    String tempPath = properties.getProperty("tempPath"); // 临时文件目录
	    Calendar cal = Calendar.getInstance();
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int day = cal.get(Calendar.DATE);
	    
	    String uploadPath = uploadRootPath + "/" + year + "/" + month + "/" + day;
	    File uploadFile = new File(uploadPath);
	    if (!uploadFile.exists()) {
	    	uploadFile.mkdirs();
	    }
        File tempPathFile = new File(tempPath);
	    if (!tempPathFile.exists()) {
	    	tempPathFile.mkdirs();
	    };
	    
	    try{
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
	        factory.setRepository(tempPathFile);// 设置缓冲区目录

	        ServletFileUpload upload = new ServletFileUpload(factory);

	        upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB

	        List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
	        Iterator<FileItem> i = items.iterator();
	        while (i.hasNext()) {
	            FileItem fi = (FileItem) i.next();
	            String fileName = fi.getName();
	            if (fileName != null) {
	                File fullFile = new File(fi.getName());
	                File savedFile = new File(uploadPath, fullFile.getName());
	                fi.write(savedFile);
	            }
	        }
	        resultMap.put("success", true);
			resultMap.put("msg", "上传附件成功!");
			resultMap.put("uploadPath", uploadPath);
			return resultMap;
	    }catch(Exception e){
	    	resultMap.put("success", false);
			resultMap.put("msg", "上传附件失败!");
			return resultMap;
	    }
	}
	
}
