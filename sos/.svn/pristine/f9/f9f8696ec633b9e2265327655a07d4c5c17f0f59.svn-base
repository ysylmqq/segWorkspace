package com.gboss.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.util.Config;
import ldap.util.IOUtil;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.liteframework.core.util.ImageUtils;
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
import com.gboss.pojo.MaintainRule;
import com.gboss.pojo.Model;
import com.gboss.service.MaintainRuleService;
import com.gboss.service.ModelService;
import com.gboss.upload.FileUpload;
import com.gboss.upload.FileUploadFactory;
import com.gboss.util.Constants;
import com.gboss.util.LogOperation;


/**
 * @Package:com.gboss.controller
 * @ClassName:ModelController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-25 上午10:39:25
 */
@Controller
public class ModelController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ModelController.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private MaintainRuleService maintainRuleService;
	
	

	@RequestMapping(value = "/model/update", method = RequestMethod.POST)
	@LogOperation(description = "车型修改", type = SystemConst.OPERATELOG_UPDATE,model_id=20070)
	public @ResponseBody
	Map<String, Object> update(@RequestBody Model model,
			BindingResult bindingResult, HttpServletRequest request)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean isExist = modelService.isExist(model.getName(),model.getId());
		String msg = isExist == true ? "该车型名称已存在！": SystemConst.OP_SUCCESS;
		map.put("success", !isExist);
		map.put(SystemConst.MSG, msg);
		if (!isExist) {
			Model oldModel = modelService.get(Model.class, model.getId());
			oldModel.setName(model.getName());
			modelService.saveOrUpdate(oldModel);
		}
		return map;
	}
	
	
	@RequestMapping(value = "/model/getMaintainRuleMsg")
	@LogOperation(description = "获取车型信息", type = SystemConst.OPERATELOG_SEARCHKEY,model_id=20070)
	public @ResponseBody HashMap<String, Object> getMaintainRuleMsg(Long model_id, HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车型信息开始");
		}
		HashMap<String, Object> results = null;
		try {
			results= modelService.getMaintainRuleMsg(model_id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得车型信息结束");
		}
		return results;
	}
	
	
	
	
	

	@RequestMapping(value = "/model/add", method = RequestMethod.POST)
	@LogOperation(description = "新增车型", type = SystemConst.OPERATELOG_ADD,model_id=20070)
	public void add(
			@RequestParam(value = "series_id", required = false) Long series_id,
			@RequestParam(value = "model_name", required = false) String model_name,
			@RequestParam(value = "standart_oil", required = false) Float standart_oil,
			@RequestParam(value = "displacement", required = false) Float displacement,
			@RequestParam(value = "car_type_year", required = false) String car_type_year,
			@RequestParam(value = "img", required = false) MultipartFile img,
			@RequestParam(value = "first_service_mileage", required = false) Integer first_service_mileage,
			@RequestParam(value = "first_service_time", required = false) Integer first_service_time,
			@RequestParam(value = "second_service_mileage", required = false) Integer second_service_mileage,
			@RequestParam(value = "second_service_time", required = false) Integer second_service_time,
			@RequestParam(value = "interval_mileage", required = false) Integer interval_mileage,
			@RequestParam(value = "interval_time", required = false) Integer interval_time,
			@RequestParam(value = "center_control", required = false) Integer center_control,
			@RequestParam(value = "horn_control", required = false) Integer horn_control,
			@RequestParam(value = "window_control", required = false) Integer window_control,
			@RequestParam(value = "oil_volume", required = false) Integer oil_volume,
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException, IOException {
		
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
		//String time = Utils.getDateString();
		String location = "";
		try {
			ClientGlobal.init(conf_filename);
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			String file_name = img.getOriginalFilename();
			String extension = file_name.substring(file_name.indexOf(".")+1);
			NameValuePair[] metaList = new NameValuePair[1];
			metaList[0] = new NameValuePair("fileName", file_name);
			//通过流把图片裁剪成指定
			int scaleMode = Constants.intSysConfig("scaleMode");
			float quality = Constants.floatSysConfig("quality");
			int adImgWidth = Constants.intSysConfig("adLargeImgWidth");
			int adImgHeight = Constants.intSysConfig("adLargeImgHeight");
			byte[] bytes = ImageUtils.resize(img.getBytes(), adImgWidth,
						adImgHeight, scaleMode, quality);
			location = client.upload_appender_file1(bytes, extension, metaList);
			System.out.println("upload success. file id is: " + location);
			trackerServer.close();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		Model model = new Model();
		model.setStandart_oil(standart_oil);
		model.setName(model_name);
		model.setDisplacement(displacement);
		model.setCar_type_year(car_type_year);
		model.setImg(serverUrl+location);
		model.setCenter_control(center_control);
		model.setHorn_control(horn_control);
		model.setWindow_control(window_control);
		model.setSeriesId(series_id);
		model.setOil_volume(oil_volume);
		modelService.save(model);
		
		MaintainRule maintainRule = new MaintainRule();
		maintainRule.setModel(model.getId());
		maintainRule.setFirst_service_mileage(first_service_mileage);
		maintainRule.setFirst_service_time(first_service_time);
		maintainRule.setInterval_mileage(interval_mileage);
		maintainRule.setInterval_time(interval_time);
		maintainRule.setSecond_service_mileage(second_service_mileage);
		maintainRule.setSecond_service_time(second_service_time);
		modelService.save(maintainRule);
		
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String str = "<script>parent.callback('" + true + "');</script>";
			out.write(str.getBytes());
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		}
	}
	
	
	
	
	@RequestMapping(value = "/model/updateModel", method = RequestMethod.POST)
	@LogOperation(description = "车型修改", type = SystemConst.OPERATELOG_UPDATE,model_id=20070)
	public void updateModel(
			@RequestParam(value = "id_1", required = false) Long id_1,
			@RequestParam(value = "series_id_1", required = false) Long series_id_1,
			@RequestParam(value = "model_name", required = false) String model_name,
			@RequestParam(value = "standart_oil", required = true) Float standart_oil,
			@RequestParam(value = "displacement", required = true) Float displacement,
			@RequestParam(value = "img", required = false) MultipartFile img,
			@RequestParam(value = "first_service_mileage", required = false) Integer first_service_mileage,
			@RequestParam(value = "first_service_time", required = false) Integer first_service_time,
			@RequestParam(value = "second_service_mileage", required = false) Integer second_service_mileage,
			@RequestParam(value = "second_service_time", required = false) Integer second_service_time,
			@RequestParam(value = "interval_mileage", required = false) Integer interval_mileage,
			@RequestParam(value = "interval_time", required = false) Integer interval_time,
			@RequestParam(value = "center_control", required = false) Integer center_control,
			@RequestParam(value = "horn_control", required = false) Integer horn_control,
			@RequestParam(value = "window_control", required = false) Integer window_control,
			@RequestParam(value = "oil_volume", required = false) Integer oil_volume,
			HttpServletRequest request, HttpServletResponse response)
			throws SystemException, IOException {
			Boolean flag = true;
			String url = null;
			Boolean isExist = modelService.isExist(model_name,id_1);
			if(isExist){
				flag = false;
			}else{
				
				Model model_old = modelService.get(Model.class, id_1);
				model_old.setName(model_name);
				model_old.setStandart_oil(standart_oil);
				model_old.setDisplacement(displacement);
				model_old.setCenter_control(center_control);
				model_old.setHorn_control(horn_control);
				model_old.setWindow_control(window_control);
				model_old.setOil_volume(oil_volume);
				
				if(null != img){
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
					//String time = Utils.getDateString();
					String location = "";
					try {
						ClientGlobal.init(conf_filename);
						TrackerClient tracker = new TrackerClient();
						TrackerServer trackerServer = tracker.getConnection();
						StorageServer storageServer = null;
						StorageClient1 client = new StorageClient1(trackerServer,
								storageServer);
						String file_name = img.getOriginalFilename();
						String extension = file_name.substring(file_name.indexOf(".")+1);
						NameValuePair[] metaList = new NameValuePair[1];
						metaList[0] = new NameValuePair("fileName", file_name);
						//通过流把图片裁剪成指定
						int scaleMode = Constants.intSysConfig("scaleMode");
						float quality = Constants.floatSysConfig("quality");
						int adImgWidth = Constants.intSysConfig("adLargeImgWidth");
						int adImgHeight = Constants.intSysConfig("adLargeImgHeight");
						byte[] bytes = ImageUtils.resize(img.getBytes(), adImgWidth,
									adImgHeight, scaleMode, quality);
						location = client.upload_appender_file1(bytes, extension, metaList);
						System.out.println("upload success. file id is: " + location);
						//上传地址
						model_old.setImg(serverUrl+location);
						url = model_old.getImg();
						trackerServer.close();
					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				
			}
			modelService.saveOrUpdate(model_old);
			
			MaintainRule rule = maintainRuleService.getMaintainRuleByModelId(id_1);
			rule.setFirst_service_mileage(first_service_mileage);
			rule.setFirst_service_time(first_service_time);
			rule.setInterval_mileage(interval_mileage);
			rule.setInterval_time(interval_time);
			rule.setSecond_service_mileage(second_service_mileage);
			rule.setSecond_service_time(second_service_time);
			maintainRuleService.saveOrUpdate(rule);
			
		}
		
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			//String str = "<script>parent.callback('" + flag + "');</script>";
			String str = "<script>parent.callback('"+url+"',"+flag+");</script>";
			out.write(str.getBytes());
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		}
	}
	
	
	
	
	
	private String uploadImages( MultipartFile file,
			String imgFileName)  {
		int scaleMode = Constants.intSysConfig("scaleMode");
		float quality = Constants.floatSysConfig("quality");
		int adImgWidth = Constants.intSysConfig("adLargeImgWidth");
		int adImgHeight = Constants.intSysConfig("adLargeImgHeight");
		String imgPath = "";
		try {
			byte[] bytes = ImageUtils.resize(file.getBytes(), adImgWidth,
					adImgHeight, scaleMode, quality);
			if (bytes.length != 0) {
				// 通过工厂获取上传实现类
				FileUpload fileUpload = FileUploadFactory
						.getInstance(Constants.IMAGE_UPLOAD);
				// 上传文件到文件服务器，返回文件调用地址
				 imgPath = fileUpload.fileUpload(new ByteArrayInputStream(
						bytes), imgFileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgPath;
		
	}
	
	
	@RequestMapping(value = "/model/delete")
	@LogOperation(description = "删除车型", type = SystemConst.OPERATELOG_DEL,model_id =20070)
	public @ResponseBody
	HashMap<String, Object> delete(Long id,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("删除车型开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = modelService.delete(id);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
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
			LOGGER.debug("删除车型结束");
		}
		return resultMap;
	}

}
