package com.gboss.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.liteframework.core.util.ImageUtils;
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

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Brand;
import com.gboss.pojo.News;
import com.gboss.pojo.NewsImages;
import com.gboss.pojo.SysValue;
import com.gboss.pojo.web.VerifyPOJO;
import com.gboss.service.NewsService;
import com.gboss.upload.FileUpload;
import com.gboss.upload.FileUploadFactory;
import com.gboss.util.Constants;
import com.gboss.util.DateUtil;
import com.gboss.util.LogOperation;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.controller
 * @ClassName:NewsController
 * @Description:TODO
 * @author:bzhang
 * @date:2014-6-16 上午10:42:56
 */
@Controller
public class NewsController extends BaseController {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(NewsController.class);
	
	@Autowired
	@Qualifier("newsService")
	private NewsService newsService;

	
	@RequestMapping(value = "/news/add", method = RequestMethod.POST)
	public String add(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam(value = "file_large", required = false) MultipartFile image_large,
			@RequestParam(value = "file_small", required = false) MultipartFile image_small,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "is_top", required = false) Integer is_top,
			@RequestParam(value = "new_origin", required = false) String new_origin,
			@RequestParam(value = "type", required = false) Integer type,
			HttpServletRequest request) throws SystemException, IOException {
		String userId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_ID);
		String name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		String companyId = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_COMPANYID);
		if(type==1){
				News news = new News();
				news.setContent(content);
				news.setState(Constants.NEWS_NORMAL);
				news.setIs_top(is_top);
				news.setNew_origin(new_origin);
				news.setTitle(title);
				news.setOp_id(userId == null ? null : Long.valueOf(userId));
				news.setOp_name(name);
				news.setOrg_id(companyId == null ? null : Long.valueOf(companyId));
				newsService.save(news);

			Long newsId = news.getId();
			if (null != image_large) {
				uploadImages(news, image_large,
						image_large.getOriginalFilename(),
						Constants.UPLOAD_LARGE_IMG);
			}

			if (null != image_small) {
				uploadImages(news, image_small,
						image_small.getOriginalFilename(),
						Constants.UPLOAD_LITTLE_IMG);
			}

			if (null != files && files.length > 0) {
				for (MultipartFile file : files) {
					if(file != null && file.getOriginalFilename() != null){
						uploadImages(news, file, file.getOriginalFilename(),
								Constants.UPLOAD_CONTENT_IMG);
					}
				}

			}
			return "close";
		}else{
			if (null != image_large) {
				String big_img  = getUploadPath(image_large, image_large.getOriginalFilename(), Constants.UPLOAD_LARGE_IMG);
				request.setAttribute("big_img", big_img);
			}

			if (null != image_small) {
				String small_img  = getUploadPath(image_small, image_small.getOriginalFilename(), Constants.UPLOAD_LITTLE_IMG);
				request.setAttribute("small_img", small_img);
			}

			if (null != files && files.length > 0) {
				List<String> imgUrlList = new ArrayList<String>();
				for (MultipartFile file : files) {
					if(file != null && file.getOriginalFilename() != null){
					String url = getUploadPath(file, file.getOriginalFilename(), Constants.UPLOAD_CONTENT_IMG);
					imgUrlList.add(url);
					}
				}
				request.setAttribute("imgUrlList", imgUrlList);
			}
			//把数据传到前台JSP显示页面
			String time = DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME);
			request.setAttribute("time", time);
			request.setAttribute("name", name);
			request.setAttribute("time_msg", "发布时间：");
			request.setAttribute("org_msg", "文章来源：");
			request.setAttribute("title", title);
			request.setAttribute("content", content);
			request.setAttribute("is_top", is_top);
			request.setAttribute("new_origin", new_origin);
			return "news_view";
		}
		
		
	}
	
	
	/**
	 * 
	 * view
	 * @Description:资讯预览
	 * @param files
	 * @param image_large
	 * @param image_small
	 * @param title
	 * @param content
	 * @param is_top
	 * @param new_origin
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping(value = "/news/view", method = RequestMethod.POST)
	public String view(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam(value = "file_large", required = false) MultipartFile image_large,
			@RequestParam(value = "file_small", required = false) MultipartFile image_small,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "is_top", required = false) Integer is_top,
			@RequestParam(value = "new_origin", required = false) String new_origin,
			@RequestParam(value = "type", required = false) Integer type,
			HttpServletRequest request) throws SystemException, IOException {
		String name = (String) request.getSession().getAttribute(
				SystemConst.ACCOUNT_USERNAME);
		if (null != image_large) {
			String big_img  = getUploadPath(image_large, image_large.getOriginalFilename(), Constants.UPLOAD_LARGE_IMG);
			request.setAttribute("big_img", big_img);
		}

		if (null != image_small) {
			String small_img  = getUploadPath(image_small, image_small.getOriginalFilename(), Constants.UPLOAD_LITTLE_IMG);
			request.setAttribute("small_img", small_img);
		}

		if (null != files && files.length > 0) {
			List<String> imgUrlList = new ArrayList<String>();
			for (MultipartFile file : files) {
				String url = getUploadPath(file, file.getOriginalFilename(), Constants.UPLOAD_CONTENT_IMG);
				imgUrlList.add(url);
			}
			request.setAttribute("imgUrlList", imgUrlList);
		}
		//把数据传到前台JSP显示页面
		String time = DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME);
		request.setAttribute("time", time);
		request.setAttribute("name", name);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("is_top", is_top);
		request.setAttribute("new_origin", new_origin);
		return "news_vew";
	}

	private void uploadImages(News news, MultipartFile file,
			String imgFileName, int type)  {
		int scaleMode = Constants.intSysConfig("scaleMode");
		float quality = Constants.floatSysConfig("quality");
		int adImgWidth = 0;
		int adImgHeight = 0;
		int kind = -1;
		switch (type) {
		case Constants.UPLOAD_LARGE_IMG:
			kind = 0;
			adImgWidth = Constants.intSysConfig("adLargeImgWidth");
			adImgHeight = Constants.intSysConfig("adLargeImgHeight");
			break;
		case Constants.UPLOAD_CONTENT_IMG:
			kind = 2;
			adImgWidth = Constants.intSysConfig("adContentImgWidth");
			adImgHeight = Constants.intSysConfig("adContentImgHeight");
			break;
		case Constants.UPLOAD_LITTLE_IMG:
			kind = 1;
			adImgWidth = Constants.intSysConfig("adSmallImgWidth");
			adImgHeight = Constants.intSysConfig("adSmallImgHeight");
			break;
		}

		try {
			byte[] bytes = ImageUtils.resize(file.getBytes(), adImgWidth,
					adImgHeight, scaleMode, quality);
			if (bytes.length != 0) {
				// 通过工厂获取上传实现类
				FileUpload fileUpload = FileUploadFactory
						.getInstance(Constants.IMAGE_UPLOAD);
				// 上传文件到文件服务器，返回文件调用地址
				String imgPath = fileUpload.fileUpload(new ByteArrayInputStream(
						bytes), imgFileName);
				NewsImages image = new NewsImages();
				// 新闻类型
				image.setImg_type(0);
				image.setNews_id(news.getId());
				image.setSrc(imgPath);
				image.setType(kind);
				newsService.save(image);
				if(kind ==0){
					news.setImg_large(image.getSrc());
					newsService.save(news);
				}else if(kind == 1){
					news.setImg_little(image.getSrc());
					newsService.save(news);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @Title:getUploadPath
	 * @Description:预览获取上传图片路径
	 * @param file
	 * @param imgFileName
	 * @param type
	 * @return
	 * @throws
	 */
	private String getUploadPath( MultipartFile file,
			String imgFileName, int type)  {
		String imgPath = "";
		int scaleMode = Constants.intSysConfig("scaleMode");
		float quality = Constants.floatSysConfig("quality");
		int adImgWidth = 0;
		int adImgHeight = 0;
		switch (type) {
		case Constants.UPLOAD_LARGE_IMG:
			adImgWidth = Constants.intSysConfig("adLargeImgWidth");
			adImgHeight = Constants.intSysConfig("adLargeImgHeight");
			break;
		case Constants.UPLOAD_CONTENT_IMG:
			adImgWidth = Constants.intSysConfig("adContentImgWidth");
			adImgHeight = Constants.intSysConfig("adContentImgHeight");
			break;
		case Constants.UPLOAD_LITTLE_IMG:
			adImgWidth = Constants.intSysConfig("adSmallImgWidth");
			adImgHeight = Constants.intSysConfig("adSmallImgHeight");
			break;
		}

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
	
	
	@RequestMapping(value = "/news/getNewsList")
	public @ResponseBody
	List<HashMap<String, Object>> getTodayPolicy(HttpServletRequest request)
			throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得最新资讯开始");
		}
		List<HashMap<String, Object>> results = null;
		try {
			String compannyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
					Long id = compannyId == null ? null : Long.valueOf(compannyId);
			results = newsService.getNewsList(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得最新资讯结束");
		}
		return results;
	}
	
	
	@RequestMapping(value = "/news/findNewsByPage")
	public @ResponseBody
	Page<HashMap<String, Object>> findNewsByPage(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询资讯开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = (String) request.getSession().getAttribute(
					SystemConst.ACCOUNT_COMPANYID);
			Long company_id = companyId == null ? null : Long.valueOf(companyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result =newsService.findNewsList(company_id, pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询资讯结束");
		}
		return result;
	}
	@RequestMapping(value = "/news/findNewsByPageSaige")
	public @ResponseBody
	Page<HashMap<String, Object>> findNewsByPageSaige(
			@RequestBody PageSelect<Map<String, Object>> pageSelect,
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询资讯开始");
		}
		Page<HashMap<String, Object>> result = null;
		try {
			String companyId = "101";
			Long company_id = companyId == null ? null : Long.valueOf(companyId);
			if (pageSelect != null) {
				Map map = pageSelect.getFilter();
				if (map == null) {
					map = new HashMap<String, Object>();
				}
				pageSelect.setFilter(map);
			}
			result =newsService.findNewsList(company_id, pageSelect);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 打印
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("分页查询资讯结束");
		}
		return result;
	}
	
	@RequestMapping(value = "/news/operateNews")
	@LogOperation(description = "资讯状态更改", type = SystemConst.OPERATELOG_UPDATE, model_id = 20080)
	public @ResponseBody
	HashMap<String, Object> operateNews(@RequestParam(value="ids[]",required=false) List<Long> ids,Integer type, 
			HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("资讯状态更改开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = newsService.operateNews(ids, type);
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
			LOGGER.debug("资讯状态更改结束");
		}
		return resultMap;
	}
	
	

	@RequestMapping(value = "/news/delete")
	@LogOperation(description = "删除资讯", type = SystemConst.OPERATELOG_DEL, model_id = 20080)
	public @ResponseBody
	HashMap<String, Object> delete(@RequestBody List<Long> ids,
			HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("资讯删除开始");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			int result = newsService.delete(ids);
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
			LOGGER.debug("资讯删除结束");
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/news/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(@RequestBody News news, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String msg = SystemConst.OP_SUCCESS;
		try {
			News old_news = newsService.get(News.class, news.getId());
			old_news.setTitle(news.getTitle());
			old_news.setContent(news.getContent());
			old_news.setIs_top(news.getIs_top());
			old_news.setNew_origin(news.getNew_origin());
			newsService.saveOrUpdate(old_news);
		} catch (Exception e) {
			 flag = false;
			 msg = SystemConst.OP_FAILURE;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		map.put(SystemConst.SUCCESS, flag);
		map.put(SystemConst.MSG, msg);
		return map;
	}
	
	
	@RequestMapping(value = "/news/getAddress")
	public @ResponseBody
	String getAddress(HttpServletRequest request) throws SystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得文件服务器地址开始");
		}
		String results = null;
		try {
			results =  Constants.strSysConfig("documentfileUrl");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			// 同时把异常抛到前台
			throw new SystemException();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("获得文件服务器地址结束");
		}
		return results;
	}
	
	
	

	@RequestMapping(value = "/news/getNewsImages", method = RequestMethod.POST)
	public @ResponseBody List<NewsImages> getNewsImages
			(@RequestBody News news, BindingResult bindingResult,HttpServletRequest request) throws SystemException {
		List<NewsImages> list = null;
		try {
			
			list = newsService.getNewsImages(news.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
}
