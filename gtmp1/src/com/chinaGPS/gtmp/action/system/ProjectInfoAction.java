package com.chinaGPS.gtmp.action.system;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.ProjectInfoPOJO;
import com.chinaGPS.gtmp.service.ProjectInfoService;
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
public class ProjectInfoAction extends BaseAction implements ModelDriven<ProjectInfoPOJO> {
	private static final long serialVersionUID = -3628830040926824485L;
	private Logger logger = Logger.getLogger(ProjectInfoAction.class);

	@Resource
	private ProjectInfoService projectInfoService;
	@Resource
	private ProjectInfoPOJO projectInfo;
	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;

	
	/**
	 * 查询工程信息，返回分页数据
	 */
	public String search() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名ID
		String userName = (String) getSession().get(Constants.USER_NAME);// 得到用户名ID
		projectInfo.setCreateId(userId);
		projectInfo.setCreateName(userName);
		renderObject(projectInfoService.selectProjectInfo(projectInfo, pageSelect));
		return NONE;
	}
	
	/**
	 * 查询工程信息，返回数据
	 */
	public String getById() throws Exception {
		renderObject(projectInfoService.getById(projectInfo));
		return NONE;
	}
	
	/**
	 * 删除工程信息
	 */
	public String del() throws Exception {
		String a=null;
		boolean result = false;
		String msg = OP_FAIL;
		try {
			projectInfo.setIsValid(1);
				a = projectInfoService.del(projectInfo);
			
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
	 * @Title:projectInfoSaveOrUpdate
	 * @Description:工程信息发布
	 * @return
	 * @throws
	 */
	@OperationLog(description = "系统参数保存")
	public String projectInfoSaveOrUpdate() {
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
		projectInfo.setCreateId(userId);
		projectInfo.setCreateName(userName);
		//userInfo.setTextContent(textContent);
		boolean result = false;
		String msg = OP_FAIL;
		try {
			if(lnt_edFlag==1){
				//编辑操作
				//a = userInfoService.editAnswer(userInfo);
			}
			else{
				//插入操作
				 a = projectInfoService.addProjectInfo(projectInfo);
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
	 * @Title:projectInfoEditProjectInfo
	 * @Description:工程信息编辑保存
	 * @return
	 * @throws
	 */
	@OperationLog(description = "系统参数保存")
	public String projectInfoEditProjectInfo() {
		String a=null;
		Integer lnt_edFlag = null;
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名ID
		String userName = (String) getSession().get(Constants.USER_NAME);// 得到用户名ID
		projectInfo.setCreateId(userId);
		projectInfo.setCreateName(userName);
		//userInfo.setTextContent(textContent);
		boolean result = false;
		String msg = OP_FAIL;
		try {
				//编辑操作
				a = projectInfoService.editProjectInfo(projectInfo);
				
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
	public ProjectInfoPOJO getModel() {
		return projectInfo;
	}

	

}
