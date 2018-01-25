package com.chinagps.driverbook.controller.admin;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chinagps.driverbook.pojo.AppVersion;
import com.chinagps.driverbook.service.IAppVersionService;
import com.chinagps.driverbook.util.AndroidUtil;
import com.chinagps.driverbook.util.pagination.Page;

@Controller
@RequestMapping(value="/admin/versions")
public class VersionController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(VersionController.class);
	
	@Autowired
	@Qualifier("appVersionServiceImpl")
	private IAppVersionService appVersionService;
	
	@RequestMapping
	public String index(AppVersion appVersion, String pageNum, String numPerPage, Model model) {
		try {
			Page page = new Page();
			if (pageNum == null || "".equals(pageNum)) {
				page.setPageNum(1);
			} else {
				page.setPageNum(Integer.valueOf(pageNum));
			}
			if (numPerPage == null || "".equals(numPerPage)) {
				page.setNumPerPage(20);
			} else {
				page.setNumPerPage(Integer.valueOf(numPerPage));
			}
			int totalCount = appVersionService.countAll(appVersion);
			page.setTotalCount(totalCount);
			List<AppVersion> versionList = appVersionService.findByPage(appVersion, new RowBounds(page.getOffset(), page.getNumPerPage()));
			model.addAttribute("origin", appVersion.getOrigin());
			model.addAttribute("versionName", appVersion.getVersionName());
			model.addAttribute("page", page);
			model.addAttribute("versionList", versionList);
			page.setTotalCount(totalCount);
			return "/admin/version/index";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String _new() {
		return "/admin/version/new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public ModelAndView create(@RequestParam("apkFile") CommonsMultipartFile file, String caption, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			Integer origin = null;
			if (".apk".equals(fileName.substring(fileName.lastIndexOf(".")).toLowerCase())) {
				File apkFile = new File("/usr/local/tomcat/webapps/download/" + fileName);
				file.transferTo(apkFile);
				List<String> infoList = AndroidUtil.getInfoFromAPK(apkFile);
				HashMap<String, String> apkInfo = new HashMap<String ,String>();
				for (String info : infoList) {
					String[] pairs = info.split("=");
					apkInfo.put(pairs[0], pairs[1]);
				}
				if ("com.sg.client".equals(apkInfo.get("package"))) {
					origin = 1;
				} else if ("com.haima.client".equals(apkInfo.get("package"))) {
					origin = 2;
				} else {
					apkFile.delete();
					return ajaxDoneError("此APK文件既不是车圣互联程序，又不是海马程序！");
				}
				File destPath = new File("/usr/local/tomcat/webapps/download/" + apkInfo.get("android:versionName"));
				if (!destPath.exists()) {
					destPath.mkdirs();
				}
				File destFile = new File("/usr/local/tomcat/webapps/download/" + apkInfo.get("android:versionName") + "/" + fileName);
				apkFile.renameTo(destFile);
				AppVersion av = new AppVersion();
				av.setOrigin(origin);
				av.setVersionName(apkInfo.get("android:versionName"));
				av.setUrl("/download/" + apkInfo.get("android:versionName") + "/" + fileName);
				av.setCaption(caption);
				appVersionService.add(av);
				return dialogAjaxDoneSuccess("新版本发布成功！", "versions");
			} else {
				return ajaxDoneError("请上传正确的APK文件！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxDoneError("系统内部错误！请联系管理员。");
		}
	}
	
	@RequestMapping(value="/{id}/delete", method=RequestMethod.POST)
	public ModelAndView delete(@PathVariable String id) {
		try {
			appVersionService.removeById(id);
			return ajaxDoneSuccess("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxDoneError("系统内部错误！请联系管理员。");
		}
	}
	
}
