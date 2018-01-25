package com.gboss.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import ldap.util.Config;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.CarTypeDao;
import com.gboss.dao.CompanyDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Model;
import com.gboss.pojo.NewPOJO;
import com.gboss.pojo.Vehicle;
import com.gboss.service.NewsService;
import com.gboss.util.Constants;
import com.gboss.util.ImageUtils;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;

@Controller
public class NewsManageController extends BaseController{

	protected static final Logger log = LoggerFactory
			.getLogger(NewsManageController.class);

	private static int UPDATE_NEW_SUBMIT =1;//是否提交
	private static int UPDATE_NEW_CHECKE =2;//审核
	private static int UPDATE_NEW_ONLINE =3;//上架
	private static int UPDATE_NEW_BAN =4;//下架
	@Autowired
	@Qualifier("newsService")
	private NewsService newsService;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CarTypeDao carTypeDao;
	
	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao vehicleDao;
	
	@RequestMapping(value = "/news/news/add", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> add(@RequestBody NewPOJO newPOJO, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		
		try{
			if(newPOJO!=null){
				//手动格式化开始时间和结束时间，以及生产日期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//开始时间
				if(newPOJO.getStartTime()!=null){
					
					String s = sdf.format(newPOJO.getStartTime());
					s = s.concat(" 00:00:00");
					newPOJO.setStartTime(sdf2.parse(s));
				}
				
				//结束时间
				if(newPOJO.getEndTime()!=null){
					
					String s = sdf.format(newPOJO.getEndTime());
					s = s.concat(" 23:59:59");
					newPOJO.setEndTime(sdf2.parse(s));
				}
				//生产开始时间
				if(newPOJO.getProduceStartTime()!=null){
					
					String s = sdf.format(newPOJO.getProduceStartTime());
					s = s.concat(" 00:00:00");
					newPOJO.setProduceStartTime(sdf2.parse(s));
				}
				
				//结束时间
				if(newPOJO.getProduceEndTime()!=null){
					
					String s = sdf.format(newPOJO.getProduceEndTime());
					s = s.concat(" 23:59:59");
					newPOJO.setProduceEndTime(sdf2.parse(s));
				}
				
				//操作员ID，操作员Name
				   String userId = (String) request.getSession().getAttribute(
							SystemConst.ACCOUNT_ID);
				  
				 //最后操作人ID
					newPOJO.setLastOpId(userId);
					//机构范围
					if(StringUtils.isNotBlank(newPOJO.getRange())){
						newPOJO.setRange(",".concat(newPOJO.getRange()).concat(","));
					}
					//车辆范围
					if(StringUtils.isNotBlank(newPOJO.getRangeVehicle())){
						newPOJO.setRangeVehicle(",".concat(newPOJO.getRangeVehicle()).concat(","));
					}
					//车型范围
					if(StringUtils.isNotBlank(newPOJO.getRangeCarType())){
								newPOJO.setRangeCarType(",".concat(newPOJO.getRangeCarType()).concat(","));
					 }		
				if( newPOJO.getId()!=null){
					updateNew(newPOJO,request);
				}
				else{
					addNew(newPOJO,request);
				}
			}
			
		}catch(Exception e){
			flag = false;
			   log.error(e.getMessage(),e);
			   msg = SystemConst.OP_FAILURE;
		}
		
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		return resultMap;
	}
	
	private boolean updateNew(NewPOJO newPOJO, HttpServletRequest request) {
		
		//先查询出原资讯
		NewPOJO updatePOJO = this.newsService.get(NewPOJO.class, newPOJO.getId());
		if(updatePOJO==null){
			return false;
		}
		
		//处理资讯更新里面的大图小图
		   //首先判断是否要修改：第一张图片是否一致
		    if(StringUtils.isNotBlank(newPOJO.getImgLarge())){
		    	String imgLarge = newPOJO.getImgLarge();//上传的图片
		    	 String url = "";//原有的第一张图片
		    	 //用正则表达式获取
		    	 Pattern p =Pattern.compile("\\s*(?i)src\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))", Pattern.CASE_INSENSITIVE);  
		         Matcher matcher = p.matcher(updatePOJO.getContent()); 
		         if(matcher.find()){
		        	 url = matcher.group();
		        	 url = url.replaceAll("src\\s*=\\s*(['|\"]*)", "").replaceAll("['|\"]", "");
		         }  
		        
		         if(StringUtils.isNotBlank(url)){
		        	 if(url.trim().equals(imgLarge.trim())){//如果相同,则不需要重新生成大小图
		        		 newPOJO.setImgLarge(updatePOJO.getImgLarge());
		        		 newPOJO.setImgLittle(updatePOJO.getImgLittle());
		        	 }
		        	 else{
		        		 //删除原有的文件服务器文件
		        		 imgDelete(url);
		        		 imgDelete(updatePOJO.getImgLarge());
		        		 imgDelete(updatePOJO.getImgLittle());
		        		 //重新上传大小图
		        		 imgLargeLittle(newPOJO);
		        	 }
		         }
		    }
		    else{//上传的内容没有图片，则要将原来的图片文件删除,原来的大小图片置空
		    	if(StringUtils.isNotBlank(updatePOJO.getImgLarge())){
		    		 imgDelete(updatePOJO.getImgLarge());
		    	}
		    	if(StringUtils.isNotBlank(updatePOJO.getImgLittle())){
		    		 imgDelete(updatePOJO.getImgLittle());
		    	}
		    	
		    	updatePOJO.setImgLarge("");
		    	updatePOJO.setImgLittle("");
		    }
		    
		if(newPOJO.getTitle()!=null)
		   updatePOJO.setTitle(newPOJO.getTitle());
		if(newPOJO.getContent()!=null)
			   updatePOJO.setContent(newPOJO.getContent());
		if(newPOJO.getIsTop()!=null)
			   updatePOJO.setIsTop(newPOJO.getIsTop());
		if(newPOJO.getImgLarge()!=null)
			   updatePOJO.setImgLarge(newPOJO.getImgLarge());
		if(newPOJO.getImgLittle()!=null)
			   updatePOJO.setImgLittle(newPOJO.getImgLittle());
		if(newPOJO.getRange()!=null)
			   updatePOJO.setRange(newPOJO.getRange());
		if(newPOJO.getRangeArea()!=null)
			   updatePOJO.setRangeArea(newPOJO.getRangeArea());
		if(newPOJO.getRangeVehicle()!=null)
			   updatePOJO.setRangeVehicle(newPOJO.getRangeVehicle());
		if(newPOJO.getRangeCarType()!=null)
			   updatePOJO.setRangeCarType(newPOJO.getRangeCarType());
		if(newPOJO.getSummary()!=null)
			   updatePOJO.setSummary(newPOJO.getSummary());
		if(newPOJO.getLastOpId()!=null)
			updatePOJO.setLastOpId(newPOJO.getLastOpId());
		if(newPOJO.getStartTime()!=null)
			updatePOJO.setStartTime(newPOJO.getStartTime());
		if(newPOJO.getEndTime()!=null)
			updatePOJO.setEndTime(newPOJO.getEndTime());
		updatePOJO.setLastTime(new java.util.Date());
		
		this.newsService.merge(updatePOJO);
		return true;
	}

	private void addNew(NewPOJO newPOJO,HttpServletRequest request){
		//从session中得到缓存数据
		  //机构ID，机构名称
		   String compannyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ORGID);
		   String compannyName = "";
		   CommonOperator operate = (CommonOperator) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USER);
		   if(operate!=null){
			   compannyName = operate.getCompanyname();
		   }
		//入库时间
		newPOJO.setAddTime(new java.util.Date());
		//默认生效时间
		if(newPOJO.getStartTime()==null){
			newPOJO.setStartTime(new java.util.Date());
		}
		//最后操作时间
		newPOJO.setLastTime(new java.util.Date());
		//默认未删除
		newPOJO.setState(0);
		//默认未提交
		newPOJO.setIsSubmit(0);
		//默认未上架
		newPOJO.setIsOnline(0);
		//默认未审核
		newPOJO.setCheckState(0);
		
		//新增人员姓名
		String userName = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_USERNAME);
		newPOJO.setOpName(userName);
		//新增人员ID
		if(StringUtils.isNotBlank(newPOJO.getLastOpId())){
			newPOJO.setOpId(Long.valueOf(newPOJO.getLastOpId()));
		}
		if(StringUtils.isNotBlank(compannyId)){
			newPOJO.setOrgId(Long.valueOf(compannyId));
			newPOJO.setOrgName(compannyName);
			//发布单位
			newPOJO.setNewOrigin(compannyName);
			//是否赛格总部，硬编码
			if(Long.valueOf(compannyId).longValue()==2L){
				newPOJO.setIsSaige(1);
			}
			else{
				newPOJO.setIsSaige(0);
			}
			
			//可见机构范围，如果为赛格分公司或者4S机构,机构范围手动填本机构
			if(newPOJO.getIsSaige().intValue()==0 && StringUtils.isBlank(newPOJO.getRange())){
				newPOJO.setRange(",".concat(compannyId).concat(","));
			}
				
		}
		//处理大图小图缩略图
		imgLargeLittle(newPOJO);
		newsService.save(newPOJO);
	}
	
	//根据上传的第一张图片，得到大图小图缩略图；第一张图片赋值在imgLarge字段
	private boolean imgLargeLittle(NewPOJO newPOJO){
		boolean flag = true;
		
		//处理大图小图
				if(StringUtils.isNotBlank(newPOJO.getImgLarge())){
					String url = newPOJO.getImgLarge();
					String conf_filename = Config.getConfigPath()+"classes/fdfs_client.conf";

					try {
						ClientGlobal.init(conf_filename);
					
					TrackerClient tracker = new TrackerClient();
					TrackerServer trackerServer = tracker.getConnection();
					StorageServer storageServer = null;
					StorageClient1 client = new StorageClient1(trackerServer,
							storageServer);
					//得到已上传的图片数据
					byte [] image = ImageUtils.getImageFromNetByUrl(url);
					BufferedImage urlImag = ImageIO.read(new ByteArrayInputStream(image));
					int oldwidth = urlImag.getWidth();
					//int oilheight = urlImag.getHeight();
					
					int large_width=0,little_width = 0;
					int large_height = 0,little_height = 0;
					
					String serverUrl = Constants.strSysConfig("serverUrl");//项目路径（图片地址前缀）
					large_height = Constants.intSysConfig("bigImgHeight");//图片的高
					float bigImgScale = Constants.floatSysConfig("bigImgScale");//图片比例
					large_width = (int)(large_height * bigImgScale);//根据图片的高和图片比例计算图片的宽度
					
					little_height = Constants.intSysConfig("smallImgHeight");//图片的高
					float smallImgScale = Constants.floatSysConfig("smallImagScale");//图片比例
					little_width = (int)(little_height * smallImgScale);//根据图片的高和图片比例计算图片的宽度
					
					//开始切图
					int beginWidthLarge = Math.abs(large_width-oldwidth)/4;
					int beginWidthLittle = Math.abs(oldwidth-little_width)/2;
					byte [] image_large = ImageUtils.cut(image, beginWidthLarge, 0, large_width, large_height);
					byte [] image_little = ImageUtils.cut(image, beginWidthLittle, 0, little_width, little_height);
					
					String file_name = url.substring(url.lastIndexOf("/")+1);
					
					String extension = file_name.substring(file_name.indexOf(".")+1);
					NameValuePair[] metaList = new NameValuePair[1];
					metaList[0] = new NameValuePair("fileName", file_name);
					
					String upload_imageLarge_url = client.upload_appender_file1(image_large, extension, metaList);
					String upload_imageLittle_url = client.upload_appender_file1(image_little, extension, metaList);
					
					trackerServer.close();
					
					
					if(StringUtils.isNotBlank(serverUrl)){
						
						if(StringUtils.isNotBlank(upload_imageLarge_url)){
							newPOJO.setImgLarge(serverUrl.concat(upload_imageLarge_url));
						}
						if(StringUtils.isNotBlank(upload_imageLittle_url)){
							newPOJO.setImgLittle(serverUrl.concat(upload_imageLittle_url));
						}
					}
				
					
					} catch (Exception e) {
						log.error("生成大图小图缩约图失败:" + newPOJO.getTitle() +",机构ID为:" + newPOJO.getOrgId()+",机构名称为:"+newPOJO.getOrgName());
						log.error(e.getMessage(),e);
						
					}
				}
		return flag;
	}
	
	
	//删除文件服务器文件
	private boolean imgDelete(String url) {
		boolean flag = true;

		if(StringUtils.isNotBlank(url)){
			url = url.trim();
			//文件服务器删除只需要上面的地址，无需http请求ip:port
			String serverUrl = Constants.strSysConfig("serverUrl");//项目路径（图片地址前缀）
			if(url.contains("http")){
				url = url.replaceAll(serverUrl, "/");
			}
			// 处理大图小图
			String conf_filename = Config.getConfigPath()
					+ "classes/fdfs_client.conf";
			
			try {
				ClientGlobal.init(conf_filename);
				
				TrackerClient tracker = new TrackerClient();
				TrackerServer trackerServer = tracker.getConnection();
				StorageServer storageServer = null;
				StorageClient1 client = new StorageClient1(trackerServer,
						storageServer);
				
				client.delete_file1(url);
				trackerServer.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				
			}
		}
		return flag;
	}
	@RequestMapping(value = "/news/news/isseg", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> isSEG(HttpServletRequest request) throws SystemException {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		if (log.isDebugEnabled()) {
			log.debug("判断机构类型开始");
		}
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;		
		try{
			
			//从SESSION读取机构ID，判断是否赛格用户
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			//查询LDAP里面的数据，判断机构类型
			int type= 0;//0,总部;1,分公司;type=2代表是营业处,type=4代表仓库;5,海马车厂,6,海马4S
			CommonCompany commonCompany = this.companyDao.getById(compannyId);
			String dn = commonCompany.getDn();
			if(commonCompany.getCompanylevel().equals("1")){
	    		//所属总部机构
				type = 0;
	    	}else if(dn.indexOf(SystemConst.TOPCOMPANYNO)!=-1){
	    		//所属分公司
	    		if("2".equals(commonCompany.getCompanytype())||"4".equals(commonCompany.getCompanytype())||"5".equals(commonCompany.getCompanytype())||"6".equals(commonCompany.getCompanytype())){
	    			type = Integer.valueOf(commonCompany.getCompanytype());
	    		}else{
	    			type=1;
	    		}
	    	}
			flag = true;
			resultMap.put("type", type);
			if (log.isDebugEnabled()) {
				log.debug("判断机构类型,机构ID为:" + compannyId);
			}
		}catch(Exception e){
			flag = false;
		   log.error(e.getMessage(),e);
		   msg = SystemConst.OP_FAILURE;
		}
		
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		
		
		return resultMap;
	}
	
	@RequestMapping(value = "/news/news/findNewsByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findNewsByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (log.isDebugEnabled()) {
			log.debug("分页查询资讯开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);
			Long company_id = companyId == null ? null : Long.valueOf(companyId);
			if (pageSelect != null) {
				@SuppressWarnings("rawtypes")
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result =newsService.findNewsList(company_id, pageSelect);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (log.isDebugEnabled()) {
			log.debug("分页查询资讯结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/news/news/findNewsByPageSaige")
	public @ResponseBody
	Page<HashMap<String, Object>> findNewsSaigeByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		
		Page<HashMap<String, Object>> result = null;
		try {
			
			if (pageSelect != null) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Map<String,Object> map = pageSelect.getFilter();
				
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				map.put("isSubmit", 1);
				pageSelect.setFilter(map);
			}
			result =newsService.findNewsSaigeList(pageSelect);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (log.isDebugEnabled()) {
			log.debug("分页查询资讯结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/news/news/newsSubmit")
	@LogOperation(description = "资讯提交审核", type = SystemConst.OPERATELOG_UPDATE, model_id=20080)
	public @ResponseBody
	HashMap<String, Object> operateNews(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer type, 
			HttpServletRequest request) throws SystemException {
		if (log.isDebugEnabled()) {
			log.debug("资讯状态更改开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			 String userId = (String) request.getSession().getAttribute(
						SystemConst.ACCOUNT_ID);
			 Map<String,Object> param = new HashMap<String,Object>();
			 param.put("opId", userId);
			//修改几个状态： 1：提交状态；2：审核状态；3:上架状态；
			int result = newsService.operateNewsByType(ids, type, NewsManageController.UPDATE_NEW_SUBMIT,param);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("资讯状态更改结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/news/news/newsonline")
	@LogOperation(description = "资讯发布", type = SystemConst.OPERATELOG_UPDATE, model_id=20080)
	public @ResponseBody
	HashMap<String, Object> newsonline(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer type, 
			HttpServletRequest request) throws SystemException {
		if (log.isDebugEnabled()) {
			log.debug("资讯发布更改开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//修改几个状态： 1：提交状态；2：审核状态；3:上架状态；4：下架
			//发布操作，资讯的状态为审核通过且上线
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
		    Map<String,Object> param = new HashMap<String,Object>();
		    param.put("opId", userId);
			int result = newsService.operateNewsByType(ids, type, NewsManageController.UPDATE_NEW_ONLINE,param);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("资讯发布结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/news/news/newsban")
	@LogOperation(description = "资讯下架", type = SystemConst.OPERATELOG_UPDATE, model_id=20080)
	public @ResponseBody
	HashMap<String, Object> newsban(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer type, 
			HttpServletRequest request) throws SystemException {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//修改几个状态： 1：提交状态；2：审核状态；3:上架状态；4:下架
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
		     Map<String,Object> param = new HashMap<String,Object>();
		     param.put("opId", userId);
			int result = newsService.operateNewsByType(ids, type, NewsManageController.UPDATE_NEW_BAN,param);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		
		return resultMap;
	}
	
	@RequestMapping(value = "/news/news/checkfalse")
	@LogOperation(description = "资讯审核失败", type = SystemConst.OPERATELOG_UPDATE, model_id=20080)
	public @ResponseBody
	HashMap<String, Object> checkFalse(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer type, 
			HttpServletRequest request) throws SystemException {
		if (log.isDebugEnabled()) {
			log.debug("资讯审核失败开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//其他参数
			Map<String,Object> param = new HashMap<String,Object>();
			if(request.getParameter("feedBack")!=null){
				param.put("feedBack", request.getParameter("feedBack"));
			}
			//修改几个状态： 1：提交状态；2：审核状态；3:上架状态；4:下架
			String userId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ID);
		     param.put("opId", userId);
			int result = newsService.operateNewsByType(ids, type, NewsManageController.UPDATE_NEW_CHECKE,param);
			if (result == -1) {
				flag = false;
				msg = "操作对象为空";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("资讯审核失败结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value="/news/news/showNew/{id}")  
	public void showNew(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response) {  
	   try{
		   NewPOJO newPOJO = new NewPOJO();
		   if(id!=null){
		    	newPOJO.setId(Long.valueOf(id));
		    	newPOJO = this.newsService.get(newPOJO.getClass(), new Long(id) );
		   }
		   
		   StringBuffer sbuffer = new StringBuffer();
		   OutputStream  pw = response.getOutputStream();
		   String head ="<!doctype HTML>"
		    +"<html>"
		    + "<head>"
		    + "<meta charset=\"UTF-8\">"
		    +"<meta name=\"viewport\" content=\"width=device-width,user-scalable=no,maximum-scale=1\">"
		    +"<title>资讯标题</title>"
			+"<style type=\"text/css\">"
			+"body{padding: 10px 2px;font-family:Arial, Helvetica, sans-serif;}"
			+".container{width:800px;max-width:100%;margin:0px auto;padding:0px;}"
			+"h1{font-size:24px;color:#333333;margin:2px;padding:0px;text-align:center}"
			+"header p{color:#9bacb9;margin:0px;padding:0px;text-align:center}"
			+"hr{border-color:rgba(155,172,185,0.2);border-top:1px;}"
			+"img{max-width:100%;}"
			+".content{font-size:18px;color:#535656;line-height:28px;}"
			+"</style>"
			+"</head>";
		   
		   String body="<body><div class=\"container\"><article><header><h1>"+ newPOJO.getTitle() +"</h1>"+"<p>"+ newPOJO.getAddTime() +"</p></header><hr>";
		   String content = "<p>" + newPOJO.getContent() +"</p>";
		   content= content.concat("<input type=\"hidden\" id=\"new_check_id\" ></input>");
		   
		   body = body.concat("<div class=\"content\">").concat(content).concat("</div>").concat("</article>").concat("</div>").concat("<div id=\"news_check_online_hide\"></div>").concat("</body>");
		   sbuffer.append(head).append(body).append("</html>");
		   
		   
		   pw.write(sbuffer.toString().getBytes("UTF-8"));
		   pw.flush();
		   pw.close();
	   }
	   catch(Exception e){
		   log.error(e.getMessage(),e);
		   
	   }
		
	}
	
	@RequestMapping(value="/news/news/search/{id}")  
	public @ResponseBody Map<String,Object> search(@PathVariable Long id,HttpServletRequest request,HttpServletResponse response) {  
		  NewPOJO newPOJO = new NewPOJO();
		  HashMap<String, Object> resultMap = new HashMap<String, Object>();
		  boolean flag = true;
		  String msg = SystemConst.OP_SUCCESS;
		
		  try{
		   if(id!=null){
		    	newPOJO.setId(Long.valueOf(id));
		    	newPOJO = this.newsService.get(newPOJO.getClass(), new Long(id) );
		    	
		    	resultMap.put("obj", newPOJO);
		    	
		   }
	   }
	   catch(Exception e){
		   log.error(e.getMessage(),e);
		   flag = false;
		   msg = "获取资讯内容失败!";
	   }
		 
		  resultMap.put(SystemConst.SUCCESS, flag);
		  resultMap.put(SystemConst.MSG, msg);
	   return resultMap;
	}
	
	@RequestMapping(value = "/news/news/delete")
	@LogOperation(description = "删除资讯", type = SystemConst.OPERATELOG_DEL, model_id=20080)
	public @ResponseBody
	HashMap<String, Object> delete(@RequestBody List<Long> ids,
			HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("资讯删除开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			//先判断能否被删除：1 提交，未审核，不能被删除 2 已经上线不能被删除
			 NewPOJO newPOJO = new NewPOJO();
			   if(ids!=null && ids.size()>0){
			    	newPOJO.setId(Long.valueOf(ids.get(0)));
			    	newPOJO = this.newsService.get(newPOJO.getClass(), new Long(ids.get(0)) );
			   }
			
			if(newPOJO.getIsSubmit().intValue()==1 &&newPOJO.getCheckState()==0 ){
				flag = false;
			}
			
			if(newPOJO.getIsOnline().intValue() ==1){
				flag = false;
			}
			if(!flag){
				msg = "该资讯不能被删除!";
			}
			else{
				int result = newsService.delete(ids);
				if (result == -1) {
					flag = false;
					msg = "操作对象为空";
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
			msg = SystemConst.OP_FAILURE;
			e.printStackTrace();
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("资讯删除结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/news/news/findDepartNode")
	public @ResponseBody
	HashMap<String, Object> departNode(@RequestParam(value="q",required=false) String param,
			HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("查询机构数据");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			
			List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
			/*//机构ID，机构名称
			   String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_ORGID);*/
			 OpenLdap ldap = OpenLdapManager.getInstance();
			 //查询所有4S店
			String filter = "(&(objectclass=commonCompany)(companytype=6))";
			List<CommonCompany> companys = ldap.searchCompany("", filter);
//			   List<CommonCompany> companys = companyDao.getCompanysByPid(compannyId);
			
			   if(companys!=null &&companys.size()>0){
				for(CommonCompany cc:companys){
					
						Map<String, Object> m = new HashMap<String,Object>();
						m.put("id", cc.getCompanyno());
						m.put("text", cc.getCompanyname());
						l.add(m);
					
					
				}
			}
			resultMap.put("items", l);
		} catch (Exception e) {
			flag = false;
			log.error(e.getMessage(), e);			
			msg = SystemConst.OP_FAILURE;
			
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("查询机构数据结束");
		}
		return resultMap;
	}
	

	@RequestMapping(value = "/news/news/findCarTypeNode")
	public @ResponseBody
	HashMap<String, Object> findCarTypeNode(@RequestParam(value="q",required=false) String param,
			HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("查询机构数据");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			
			List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
			PageSelect<Model> pageSelect = new PageSelect<Model>();
			Map<String,Object> filter = new HashMap<String,Object>();
			filter.put("is_valid",1);
			filter.put("name", param);
			//机构ID，机构名称
			pageSelect.setPageNo(1);
			pageSelect.setPageSize(200);
			pageSelect.setOffset(0);
			pageSelect.setFilter(filter);
			
		    Page<Model> page = this.carTypeDao.findCarType(pageSelect );
		    if(page!=null && page.getItems()!=null && page.getTotal()>0){
		    	for(Model m:page.getItems()){
		    		Map<String,Object> mm = new HashMap<String,Object>();
		    		mm.put("id", m.getId());
		    		mm.put("text", m.getName());
		    		l.add(mm);
		    	}
		    }
			resultMap.put("items", l);
		} catch (Exception e) {
			flag = false;
			log.error(e.getMessage(), e);			
			msg = SystemConst.OP_FAILURE;
			
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("查询机构数据结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/news/news/findCarTypeAllNode")
	public @ResponseBody
	HashMap<String, Object> findCarTypeAllNode(@RequestParam(value="q",required=false) String param,
			HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("查询机构数据");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			
			List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
			List<Model> datas = this.carTypeDao.getCarTypeList();
			for(Model m:datas){
	    		Map<String,Object> mm = new HashMap<String,Object>();
	    		mm.put("id", m.getId());
	    		mm.put("text", m.getName());
	    		l.add(mm);
	    	}
			resultMap.put("items", l);
		} catch (Exception e) {
			flag = false;
			log.error(e.getMessage(), e);			
			msg = SystemConst.OP_FAILURE;
			
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("查询机构数据结束");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/news/news/findVehicleNode")
	public @ResponseBody
	HashMap<String, Object> findVehicleNode(@RequestParam(value="q",required=false) String param,
			HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("查询车辆数据");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			
			List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
			PageSelect<Vehicle> pageSelect = new PageSelect<Vehicle>();
			Map<String,Object> filter = new HashMap<String,Object>();
			
			filter.put("plate_no", param);
			//机构ID，机构名称
			pageSelect.setPageNo(1);
			pageSelect.setPageSize(200);
			pageSelect.setOffset(0);
			pageSelect.setFilter(filter);
			
		    Long subco_no = 0L;//机构ID，机构名称
		    String companyid = (String) request.getSession().getAttribute(SystemConst.ACCOUNT_ORGID);
		 	if(StringUtils.isNotBlank(companyid)){
		 		subco_no = Long.valueOf(companyid);
		 	}			
			Page<Vehicle> page = this.vehicleDao.search(pageSelect, subco_no );
		    if(page!=null && page.getItems()!=null && page.getTotal()>0){
		    	for(Vehicle m:page.getItems()){
		    		Map<String,Object> mm = new HashMap<String,Object>();
		    		mm.put("id", m.getVehicle_id());
		    		mm.put("text", m.getPlate_no());
		    		l.add(mm);
		    	}
		    }
			resultMap.put("items", l);
		} catch (Exception e) {
			flag = false;
			log.error(e.getMessage(), e);			
			msg = SystemConst.OP_FAILURE;
			
		}
		resultMap.put(SystemConst.SUCCESS, flag);
		resultMap.put(SystemConst.MSG, msg);
		if (log.isDebugEnabled()) {
			log.debug("查询车辆数据结束");
		}
		return resultMap;
	}
}
