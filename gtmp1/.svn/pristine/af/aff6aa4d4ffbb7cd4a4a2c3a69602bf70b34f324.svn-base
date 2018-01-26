package com.chinaGPS.gtmp.action.system;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.business.command.Push;
import com.chinaGPS.gtmp.entity.MapPushRelationPOJO;
import com.chinaGPS.gtmp.entity.MessagePOJO;
import com.chinaGPS.gtmp.service.MessageService;
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
public class MessageAction extends BaseAction implements ModelDriven<MessagePOJO> {
	private static final long serialVersionUID = -3628830040926824485L;
	private Logger logger = Logger.getLogger(MessageAction.class);

	@Resource
	private MessageService messageService;
	@Resource
	private MessagePOJO message;
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
		message.setCreateId(userId);
		message.setCreateName(userName);
		renderObject(messageService.selectProjectInfo(message, pageSelect));
		return NONE;
	}
	
	/**
	 * 查询工程信息，返回数据
	 */
	public String getById() throws Exception {
		renderObject(messageService.getById(message));
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
			message.setIsValid(1);
				a = messageService.del(message);
			
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
	 * @Title:messageSaveOrUpdate
	 * @Description:工程信息发布
	 * @return
	 * @throws
	 */
	@OperationLog(description = "系统参数保存")
	public String messageSaveOrUpdate() {
		String a=null;
		Integer lnt_edFlag = null;
		Long userId = (Long) getSession().get(Constants.USER_ID);// 得到用户名ID
		String userName = (String) getSession().get(Constants.USER_NAME);// 得到用户名ID
		String edFlag = (getRequest().getParameter("ed"));
		String contentText = getRequest().getParameter("contentText");
		if(edFlag!=null && !"".equals(edFlag)){
			//回复的权限
			lnt_edFlag = Integer.valueOf(edFlag);
		}
		else{
			//发布问题
			lnt_edFlag = 0;
		}
		message.setCreateId(userId);
		message.setCreateName(userName);
		//userInfo.setTextContent(textContent);
		boolean result = false;
		String msg = OP_FAIL;
		try {
			if(lnt_edFlag==1){
				//编辑操作
				//a = userInfoService.editAnswer(userInfo);
			}
			else{
				//List<MapPushRelationPOJO> mapPushRelation = messageService.selectMessageService();
				//插入操作
				 a = messageService.addProjectInfo(message);
				 Push.pushMsg2All(message.getTitle(), contentText);
					/*******************************消息推送**************************/
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
	 * @Title:messageEditProjectInfo
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
		message.setCreateId(userId);
		message.setCreateName(userName);
		//userInfo.setTextContent(textContent);
		boolean result = false;
		String msg = OP_FAIL;
		try {
				//编辑操作
				a = messageService.editProjectInfo(message);
				
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
	public MessagePOJO getModel() {
		return message;
	}

	

}
