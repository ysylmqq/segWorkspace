package cc.chinagps.seat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cc.chinagps.seat.auth.AuthHelper;
import cc.chinagps.seat.auth.CompanyInfo;
import cc.chinagps.seat.auth.User;
import cc.chinagps.seat.util.Consts;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

public class BasicController {
	
	protected final Logger LOGGER = Logger.getLogger(getClass());
	
	private MessageSourceAccessor messages;

	@Autowired
	public void setMessages(MessageSource messageSource) {
		messages = new MessageSourceAccessor(messageSource);
	}
	
	public String getText(String msgKey, Locale locale, Object... args) {
        if (args.length == 0) {
        	return messages.getMessage(msgKey, locale);
		}
        return messages.getMessage(msgKey, args, locale);
    }
	
	public String getText(MessageSourceResolvable resolvable, Locale locale) {
		return messages.getMessage(resolvable);
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @param file_path
	 * @return
	 */
	protected void upLoadFile(CommonsMultipartFile file,HttpServletRequest request, String file_path) {
		try {
			if( file != null ){
				if (!file.isEmpty()) {
					FileOutputStream os = new FileOutputStream(file_path);
					InputStream  in = file.getInputStream();
					FileCopyUtils.copy(in, os);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传出错 ");
		}
	}
	protected Map<String, Object> getErrorMap(BindingResult errors, 
			Locale locale) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ObjectError> allErrors = errors.getAllErrors();
		List<String> msgList = new ArrayList<String>(allErrors.size());
		for (ObjectError error : allErrors) {
			msgList.add(getText(error, locale));
		}
		if (msgList.size() > 1) {
			resultMap.put("message", msgList);
		} else {
			resultMap.put("message", msgList.get(0));
		}
		return resultMap;
	}
	
	/**
	 * 获取登录用户
	 * @param request
	 * @return
	 */
	public User getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Consts.SESSION_ATTR_LOGIN_USER);
		if (user == null) {
			AssertionImpl impl = (AssertionImpl) session.getAttribute("_const_cas_assertion_");
			if (impl != null) {
				AttributePrincipal pp = impl.getPrincipal();
				session.setAttribute(Consts.SESSION_ATTR_LOGIN_USER, AuthHelper.getUser(pp.getName()));
			}
			user = (User) session.getAttribute(Consts.SESSION_ATTR_LOGIN_USER);
		}
		return user;
	}
	
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public String bindExceptionHandler(BindException e, Locale locale)
			throws JSONException {
		LOGGER.error("", e);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		resultMap.putAll(getErrorMap(e, locale));
		return JSONUtil.serialize(resultMap);
	}
	
	@ExceptionHandler
	@ResponseBody
	public String exceptionHandler(Throwable t) throws JSONException {
		LOGGER.error("", t);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		resultMap.put("message", t.getMessage());
		return JSONUtil.serialize(resultMap);
	}

	/**
	 * 获取登录用户的公司id数组
	 * @param request
	 * @return
	 */
	protected BigInteger[] getLoginUserCompanyNo(HttpServletRequest request) {
		return getLoginUserCompanyNo(request, true);
	}
	
	/**
	 * 获取登录用户的公司id数组
	 * @param request
	 * @param needDataAuth 如果需要数据权限，且公司列表中包含总部，则返回0长度公司ID。
	 * 		否则返回公司列表的公司ID
	 * @return
	 */
	protected BigInteger[] getLoginUserCompanyNo(HttpServletRequest request, boolean needDataAuth) {
		List<CompanyInfo> companyInfoList = getLoginUser(request)
				.getCompanyInfoList();
		BigInteger[] companyNo;
		if (needDataAuth && (
				companyInfoList.contains(CompanyInfo.HEADQUARTERS) ||
				Consts.AUTH_DISABLE)) {
			// 公司总部能够查看所有车辆
			companyNo = new BigInteger[0];
		} else {
			companyNo = new BigInteger[companyInfoList.size()];
			for (int i = 0; i < companyNo.length; i++) {
				companyNo[i] = companyInfoList.get(i).getCompanyNo();
			}
		}
		return companyNo;
	}
	
	protected void exportWord(HttpServletResponse response, File file,String fileName){
		InputStream in = null;
		OutputStream out = null;
        try {
			in = new FileInputStream(file);
			response.setCharacterEncoding("utf-8");  
			response.setContentType("application/msword");  
			fileName = URLEncoder.encode(fileName, "UTF-8");  
			response.addHeader("Content-Disposition", "attachment;filename="+fileName+".doc");  
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];  // 缓冲区  
			int bytesToRead = -1;  
			while((bytesToRead = in.read(buffer)) != -1) {  
				out.write(buffer, 0, bytesToRead);  
			}  
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(in != null)	in.close();
	            if(out != null) out.close();  
	            if(file != null)file.delete(); // 删除临时文件  
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}
	
}
