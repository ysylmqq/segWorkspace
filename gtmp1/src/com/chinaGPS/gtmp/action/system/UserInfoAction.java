package com.chinaGPS.gtmp.action.system;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.ProjectInfoPOJO;
import com.chinaGPS.gtmp.entity.UserInfoPOJO;
import com.chinaGPS.gtmp.service.UserInfoService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.system
 * @ClassName:SystemParamAction
 * @Description:系统参数配置接口
 * @author:zfy
 * @date:2013-5-22 下午05:31:54
 * 
 */
@Scope("prototype")
@Controller
public class UserInfoAction extends BaseAction implements ModelDriven<UserInfoPOJO> {
	private static final long serialVersionUID = -3628830040926824485L;
	private Logger logger = Logger.getLogger(UserInfoAction.class);

	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserInfoPOJO userInfo;
	@Resource
	private ProjectInfoPOJO projectInfo;
	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;

	

	/**
	 * @Title:saveOrUpdate
	 * @Description:处理保存或更新终端信息请求业务
	 * @return
	 * @throws
	 */
	@OperationLog(description = "系统参数保存")
	public String saveOrUpdate() {
		String a=null;
		Integer lnt_edFlag = null;
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名ID
		String userName = (String) getSession().get(Constants.USER_NAME);// 得到用户名ID
		String edFlag = (getRequest().getParameter("ed"));
		if(edFlag!=null && !"".equals(edFlag)){
			//回复的权限
			lnt_edFlag = Integer.valueOf(edFlag);
		}
		else{
			//发布问题
			lnt_edFlag = 0;
		}
		userInfo.setUserId(userId);
		userInfo.setUserName(userName);
		//userInfo.setTextContent(textContent);
		boolean result = false;
		String msg = OP_FAIL;
		try {
			if(lnt_edFlag==1){
				//编辑操作
				a = userInfoService.editAnswer(userInfo);
			}
			else{
				//插入操作
				 a = userInfoService.addInfo(userInfo);
			}
			if(a!=null && !"".equals(a)){
				 msg = OP_SUCCESS;
				 result = true;
			}
			
		} catch (Exception e) {
			result = false;
			msg = OP_FAIL;
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
		return NONE;
	}
	
	
	
	
	
	
	/**
	 * 删除问题
	 */
	public String del() throws Exception {
		String a=null;
		boolean result = false;
		String msg = OP_FAIL;
		try {
			userInfo.setIsValid(1);
				a = userInfoService.del(userInfo);
			
			if(a!=null && !"".equals(a)){
				 msg = OP_SUCCESS;
				 result = true;
			}
			
		} catch (Exception e) {
			result = false;
			msg = OP_FAIL;
			logger.error(e.getMessage(), e);
		}
		renderMsgJson(result, msg);
		return NONE;
	}
	
	/**
	 * 查询问题信息，返回分页数据
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名ID
		String userName = (String) getSession().get(Constants.USER_NAME);// 得到用户名ID
		userInfo.setUserId(userId);
		userInfo.setUserName(userName);
		//有回复权限的标志位
		String glFlag = getRequest().getParameter("gl");
		if("1".equals(glFlag)){
			userInfo.setUserId(null);
		}
		renderObject(userInfoService.selectQuestion(userInfo, pageSelect));
		return NONE;
	}
	
	/**
	 * 查询问题信息，返回分页数据
	 */
	public String getById() throws Exception {
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名ID
		String userName = (String) getSession().get(Constants.USER_NAME);// 得到用户名ID
		userInfo.setUserId(userId);
		userInfo.setUserName(userName);
		renderObject(userInfoService.getById(userInfo));
		return NONE;
	}
	
	

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

	public int getPage() {
		return (page == 0) ? 1 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return (rows == 0) ? 10 : rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}


	@Override
	public UserInfoPOJO getModel() {
		return userInfo;
	}



	public ProjectInfoPOJO getProjectInfo() {
		return projectInfo;
	}



	public void setProjectInfo(ProjectInfoPOJO projectInfo) {
		this.projectInfo = projectInfo;
	}

	

}
