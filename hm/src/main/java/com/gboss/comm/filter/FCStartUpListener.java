package com.gboss.comm.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gboss.comm.SystemConst;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.service.FCCmdManageService;
import com.gboss.util.SpringContext;
import com.gboss.util.StringUtils;

import cc.chinagps.gboss.comcenter.interprotocol.clienttest.ClientTest;
import cc.chinagps.gboss.comcenter.interprotocol.clienttest.FCClientCommandThread;
import cc.chinagps.lib.util.Config;

/**
 * 
* @ClassName: FCStartUpListener 
* @Description:流量控制系统监听器; 
* @author:cj
* @date 2017年7月19日 下午3:13:53 
*
 */
public class FCStartUpListener implements ServletContextListener {
	private ClientTest client = new ClientTest();
	private FCClientCommandThread fccommandThread= new FCClientCommandThread();

	public FCStartUpListener() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		//初始化配置
		confInit();
		
		// 启动通信线程
		client.startTask();
		
		System.out.println("------------------------------启动加载数据保存到内存---------------------------");
		FCCmdManageService fcCmdManageService = (FCCmdManageService)SpringContext.getBean("fcCmdManageService");

		//从t_flow_cmd_send表获取所有call_letter的信息;
		SystemConst.hm_send_search_cmd = fcCmdManageService.getAllSendSearchMap();
		
		System.out.println("**********1.加载开启导航主机指令到内存************");		
		loadFcCmdToMap(SystemConst.NAVI_HOST_OPEN_CMD,fcCmdManageService,SystemConst.hm_navi_host_open_cmd);
		System.out.println("**********2.加载关闭导航主机指令到内存************");		
		loadFcCmdToMap(SystemConst.NAVI_HOST_CLOSE_CMD,fcCmdManageService,SystemConst.hm_navi_host_close_cmd);
		System.out.println("**********3.加载开启省流量指令到内存************");		
		loadFcCmdToMap(SystemConst.SAVE_FLOW_OPEN_CMD,fcCmdManageService,SystemConst.hm_save_flow_open_cmd);
		System.out.println("**********4.加载关闭省流量指令到内存************");		
		loadFcCmdToMap(SystemConst.SAVE_FLOW_CLOSE_CMD,fcCmdManageService,SystemConst.hm_save_flow_close_cmd);
		
		try {
			//启动查车指令线程
			fccommandThread.startTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description:将指定的cmdId的指令加载到内存;
	 * @Params:
	 * @Return:void
	 * @Author:cj
	 * @Date:2017年7月20日 下午4:07:32
	 */
	public void loadFcCmdToMap(Integer cmdId,FCCmdManageService fcCmdManageService,
			ConcurrentHashMap<String, FlowCtrolCmd> fcMap){
		
		if(cmdId == null || fcCmdManageService == null || fcMap == null){
			System.out.println("参数为空，加载流量控制指令失败！");
			return;
		}
		
		List<FlowCtrolCmd> fcList = fcCmdManageService.getFlowCtrlList(cmdId);
		
		if(null != fcList && fcList.size() > 0){
			for (FlowCtrolCmd fc : fcList) {
				fcMap.put(fc.getCallLetter(), fc);
			}
			System.out.println("流量控制指令队列中的车台数量:" + fcList.size());
		}
	}
	
	/**
	 * 
	 * @Description:配置初始化;
	 * @Params:
	 * @Return:void
	 * @Author:cj
	 * @Date:2017年7月25日 下午1:48:28
	 */
	public void confInit(){
		System.out.println("------------------------------初始化配置参数------------------------------");
		String openNaviCmd = Config.getCmdProperties().getProperty("hm_navihost_open");
		if(StringUtils.isNotNullOrEmpty(openNaviCmd)){
			SystemConst.NAVI_HOST_OPEN_CMD = Integer.parseInt(openNaviCmd.replaceAll("^0[x|X]", ""),16);
			System.out.println("[INIT]:NAVI_HOST_OPEN_CMD:" + SystemConst.NAVI_HOST_OPEN_CMD);
		}
		String closeNaviCmd = Config.getCmdProperties().getProperty("hm_navihost_close");
		if(StringUtils.isNotNullOrEmpty(closeNaviCmd)){
			SystemConst.NAVI_HOST_CLOSE_CMD = Integer.parseInt(closeNaviCmd.replaceAll("^0[x|X]", ""),16);
			System.out.println("[INIT]:NAVI_HOST_CLOSE_CMD:" + SystemConst.NAVI_HOST_CLOSE_CMD);
		}
		String openSaveCmd = Config.getCmdProperties().getProperty("hm_saveflow_open");
		if(StringUtils.isNotNullOrEmpty(openSaveCmd)){
			SystemConst.SAVE_FLOW_OPEN_CMD = Integer.parseInt(openSaveCmd.replaceAll("^0[x|X]", ""),16);
			System.out.println("[INIT]:SAVE_FLOW_OPEN_CMD:" + SystemConst.SAVE_FLOW_OPEN_CMD);
		}
		String closeSaveCmd = Config.getCmdProperties().getProperty("hm_saveflow_close");
		if(StringUtils.isNotNullOrEmpty(closeSaveCmd)){
			SystemConst.SAVE_FLOW_CLOSE_CMD = Integer.parseInt(closeSaveCmd.replaceAll("^0[x|X]", ""),16);
			System.out.println("[INIT]:SAVE_FLOW_CLOSE_CMD:" + SystemConst.SAVE_FLOW_CLOSE_CMD);
		}
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		client.shutdown();
	}

}
