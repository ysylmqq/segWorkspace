package com.chinaGPS.gtmp.action.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.business.command.SendCommand;
import com.chinaGPS.gtmp.entity.CommandPOJO;
import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.entity.DepartPOJO;
import com.chinaGPS.gtmp.entity.DicCommandType;
import com.chinaGPS.gtmp.entity.RemoteUpgradePOJO;
import com.chinaGPS.gtmp.entity.RolePOJO;
import com.chinaGPS.gtmp.entity.UserPOJO;
import com.chinaGPS.gtmp.service.ISendCommandService;
import com.chinaGPS.gtmp.util.Constants;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.FormatUtil;
import com.chinaGPS.gtmp.util.OperationLog;
import com.chinaGPS.gtmp.util.page.PageSelect;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.run
 * @ClassName:CommandAction
 * @Description:
 * @author:zfy
 * @date:2013-4-15 下午04:45:33
 */
@Scope("prototype")
@Controller
public class CommandAction extends BaseAction implements
		ModelDriven<CommandSendPOJO> {
	private static final long serialVersionUID = -1153193050430425657L;
	private static Logger logger = LoggerFactory.getLogger(CommandAction.class);

	@Resource
	private ISendCommandService sendCommandService;
	
	@Resource
	private SendCommand sendCommand;

	@Resource
	private CommandSendPOJO commandSendPOJO;

	@Resource
	private CommandPOJO commandPOJO;

	@Resource
	private RemoteUpgradePOJO upgradeOJO;
	@Resource
	private PageSelect pageSelect;

	private int page;
	private int rows;
	private String startTimeStr;
	private String endTimeStr;

	/**
	 * @Title:sendCommand
	 * @Description:发指令
	 * @throws
	 */
	@OperationLog(description = "运营指令下发")
	public void sendCommand() {
		boolean flag = true;
		String msg = "";
		try {
			Long userId = (Long) getSession().get(Constants.USER_ID);
			commandSendPOJO.setUserId(userId);
			if(commandSendPOJO.getCommandTypeId() == 52){
				commandSendPOJO.setCommandTypeId(com.chinaGPS.gtmp.util.StringUtils.getCommandTypeId(commandSendPOJO.getPHeartbeatContent()));
			}
			// 指令下发参数
			if (logger.isDebugEnabled()) {
				logger.debug("发送指令请求参数：" + objectToJson(commandSendPOJO));
			}
            if(null != commandSendPOJO.getPlockTime()){
            	//预约锁车
            	sendCommandService.addLockTime(commandSendPOJO);            	
			} else {
				Map<Integer, String> result = sendCommand.sendCommand(commandSendPOJO);
				// 加上之前已经下发的指令
				Map<Integer, String> listBefore = (Map<Integer, String>) getSession()
						.get(Constants.COMMAND_SN);
				Set<Integer> keySet = result.keySet();
				if (listBefore == null) {
					listBefore = new HashMap<Integer, String>();
				}
				listBefore.putAll(result);
				getSession().put(Constants.COMMAND_SN, listBefore);// result主要用于得到指令回应
				msg = "指令发送成功,请等待回应信息!";
				Integer[] array = keySet.toArray(new Integer[keySet.size()]);
				StringBuffer sBuffer = new StringBuffer();
				for (Integer integer : array) {
					sBuffer.append(integer).append(",");
				}
				if (sBuffer.length() > 0) {
					/*
					 * logger("下发类型为" + commandSendPOJO.getCommandTypeId() +
					 * "的指令,commandSn=" +
					 * sBuffer.deleteCharAt(sBuffer.length()-1));
					 */
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
			msg = "指令发送失败";
		}

		// 指令下发回应
		if (logger.isDebugEnabled()) {
			logger.debug("是否成功:" + flag + ",回应消息:" + msg);
		}
		renderMsgJson(flag, msg);
		
	}

	/**
	 * @Title:sendCommand
	 * @Description:发指令
	 * @throws
	 */
	@OperationLog(description = "机械测试指令下发")
	public void sendCommand4Test() {
		boolean flag = true;
		String msg = "";
		try {
			Long userId = (Long) getSession().get(Constants.USER_ID);
			commandSendPOJO.setUserId(userId);
			if(commandSendPOJO.getCommandTypeId() == 52){
				commandSendPOJO.setCommandTypeId(com.chinaGPS.gtmp.util.StringUtils.getCommandTypeId(commandSendPOJO.getPHeartbeatContent()));
			}
			// 指令下发参数
			if (logger.isDebugEnabled()) {
				logger.debug("发送指令请求参数：" + objectToJson(commandSendPOJO));
			}

			Map<Integer, String> result = sendCommand.sendCommand(commandSendPOJO);
			// 加上之前已经下发的指令
			Map<Integer, String> listBefore = (Map<Integer, String>) getSession()
					.get(Constants.COMMAND_SN_TEST);
			Set<Integer> keySet = result.keySet();
			if (listBefore == null) {
				listBefore = new HashMap<Integer, String>();
			}
			listBefore.putAll(result);
			getSession().put(Constants.COMMAND_SN_TEST, listBefore);// result主要用于得到指令回应
			msg = "指令发送成功,请等待回应信息!";
			Integer[] array = keySet.toArray(new Integer[keySet.size()]);
			StringBuffer sBuffer = new StringBuffer();
			for (Integer integer : array) {
				sBuffer.append(integer).append(",");
			}
			if (sBuffer.length() > 0) {
				/*
				 * logger("下发类型为" + commandSendPOJO.getCommandTypeId() +
				 * "的指令,commandSn=" + sBuffer.deleteCharAt(sBuffer.length()-1));
				 */
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			flag = false;
			msg = "指令发送失败";
		}

		// 指令下发回应
		if (logger.isDebugEnabled()) {
			logger.debug("是否成功:" + flag + ",回应消息:" + msg);
		}
		renderMsgJson(flag, msg);
	}

	/**
	 * @Title:sendCommand4Polling
	 * @Description:发指令
	 * @throws
	 */
	@OperationLog(description = "机械巡检指令下发")
	public void sendCommand4Polling() {
		boolean flag = true;
		String msg = "";
		try {
			Long userId = (Long) getSession().get(Constants.USER_ID);
			commandSendPOJO.setUserId(userId);
			if(commandSendPOJO.getCommandTypeId() == 52){
				commandSendPOJO.setCommandTypeId(com.chinaGPS.gtmp.util.StringUtils.getCommandTypeId(commandSendPOJO.getPHeartbeatContent()));
			}
			// 指令下发参数
			if (logger.isDebugEnabled()) {
				logger.debug("发送指令请求参数：" + objectToJson(commandSendPOJO));
			}

			Map<Integer, String> result = sendCommand.sendCommand(commandSendPOJO);
			// 加上之前已经下发的指令
			Map<Integer, String> listBefore = (Map<Integer, String>) getSession()
					.get(Constants.COMMAND_SN_POLLING);
			Set<Integer> keySet = result.keySet();
			if (listBefore == null) {
				listBefore = new HashMap<Integer, String>();
			}
			listBefore.putAll(result);
			getSession().put(Constants.COMMAND_SN_POLLING, listBefore);// result主要用于得到指令回应
			msg = "指令发送成功,请等待回应信息!";
			Integer[] array = keySet.toArray(new Integer[keySet.size()]);
			StringBuffer sBuffer = new StringBuffer();
			for (Integer integer : array) {
				sBuffer.append(integer).append(",");
			}
			if (sBuffer.length() > 0) {
				/*
				 * logger("下发类型为" + commandSendPOJO.getCommandTypeId() +
				 * "的指令,commandSn=" + sBuffer.deleteCharAt(sBuffer.length()-1));
				 */
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			flag = false;
			msg = "指令发送失败";
		}

		// 指令下发回应
		if (logger.isDebugEnabled()) {
			logger.debug("是否成功:" + flag + ",回应消息:" + msg);
		}
		renderMsgJson(flag, msg);
	}

	/**
	 * @Title:sendUpgradeCommand
	 * @Description:空中升级
	 * @throws
	 */
	@OperationLog(description = "空中升级指令下发")
	public void sendUpgradeCommand() {
		boolean flag = true;
		String msg = "指令发送成功,请等待回应信息!";
		try {
			Long userId = (Long) getSession().get(Constants.USER_ID);
			commandSendPOJO.setUserId(userId);
			//commandSendPOJO.setPUnitType("TYC01");

			// 指令下发参数
			if (logger.isDebugEnabled()) {
				logger.debug("发送指令请求参数：" + objectToJson(commandSendPOJO));
			}

			Map<Integer, String> result = sendCommand.sendUpgradeCommand(commandSendPOJO);
			// 加上之前已经下发的指令
			Map<Integer, String> listBefore = (Map<Integer, String>) getSession()
					.get(Constants.COMMAND_SN_UPGRADE);
			if (listBefore == null) {
				listBefore = new HashMap<Integer, String>();
			}
			listBefore.putAll(result);
			getSession().put(Constants.COMMAND_SN_UPGRADE, listBefore);// result主要用于得到指令回应
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			flag = false;
			msg = "指令发送失败";
		}

		// 指令下发回应
		if (logger.isDebugEnabled()) {
			logger.debug("是否成功:" + flag + ",回应消息:" + msg);
		}

		renderMsgJson(flag, msg);
	}

	/**
	 * @throws Exception
	 * @Title:分页查询
	 * @Description:
	 * @throws
	 */
	public void search() {
		int total = 0;
		List<CommandPOJO> resultList = new ArrayList<CommandPOJO>();
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		Map map = new HashMap();
		// 角色是经销商，则系统自动过滤
		UserPOJO userPOJO = (UserPOJO) getSession().get(Constants.USER_INFO);
		List<RolePOJO> roles = userPOJO.getRoles();
		boolean isDealer = false;// 是否是经销商
		DepartPOJO departPOJO = null;
		if (!roles.isEmpty()) {
			if (roles.size() == 1) {
				RolePOJO role = roles.get(0);
				if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
					isDealer = true;
				}
			}
		}

		if (isDealer) {// 经销商
			String[] dealerIds = new String[1];// 查询更多条件中的经销商数组
			departPOJO = userPOJO.getDepartInfo();
			dealerIds[0] = String.valueOf(departPOJO.getDealerId());
			commandPOJO.setDealerIds(dealerIds);
		} else {
			if (StringUtils.isNotEmpty(commandPOJO.getParam())) {
				commandPOJO.setDealerIds(com.chinaGPS.gtmp.util.StringUtils
						.split(commandPOJO.getParam(), ','));
			}
		}
		if(commandPOJO.getDealerName() != null){
            String temp = commandPOJO.getDealerName();
            if(temp.equals("")){
                
            }else{              
                String[] dealerIds=FormatUtil.strToFormat(temp).split(",");
                commandPOJO.setDealerIds(dealerIds);
                commandPOJO.setDealerName(null);
            }
            }

		map.put("command", commandPOJO);
		try {
			total = sendCommandService.countAll(map);
			resultList = sendCommandService.getByPage(map, new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		renderObject(result);
	}

	public void getCommandTypes() {
		List<Object> result = new ArrayList<Object>();
		try {
			HashMap<String, String> totalMap = new HashMap<String, String>();
			totalMap.put("commandTypeName", "全部");
			totalMap.put("commandTypeId", "");
			result.add(totalMap);
			result.addAll(sendCommandService.getCommandTypes(new DicCommandType()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}

	public String exportToExcel() {
		List<Object[]> values = new ArrayList<Object[]>();

		try {
			commandPOJO.setUserName(java.net.URLDecoder.decode(commandPOJO.getUserName()));
			commandPOJO.setVehicleDef(java.net.URLDecoder.decode(commandPOJO.getVehicleDef()));

			// 开始时间
			commandPOJO.setStamp(DateUtil.parse(java.net.URLDecoder
					.decode(startTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			commandPOJO.setStamp2(DateUtil.parse(java.net.URLDecoder
					.decode(endTimeStr), DateUtil.YMD_DASH_WITH_FULLTIME));
			// 角色是经销商，则系统自动过滤
			UserPOJO userPOJO = (UserPOJO) getSession()
					.get(Constants.USER_INFO);
			List<RolePOJO> roles = userPOJO.getRoles();
			boolean isDealer = false;// 是否是经销商
			DepartPOJO departPOJO = null;
			if (!roles.isEmpty()) {
				if (roles.size() == 1) {
					RolePOJO role = roles.get(0);
					if (role.getRoleId() == Constants.DEALER_ROLE_ID) {// 如果是经销商的话
						isDealer = true;
					}
				}
			}

			if (isDealer) {// 经销商
				String[] dealerIds = new String[1];// 查询更多条件中的经销商数组
				departPOJO = userPOJO.getDepartInfo();
				dealerIds[0] = String.valueOf(departPOJO.getDealerId());
				commandPOJO.setDealerIds(dealerIds);
			} else {
				if (StringUtils.isNotEmpty(commandPOJO.getParam())) {
					commandPOJO.setDealerIds(com.chinaGPS.gtmp.util.StringUtils
							.split(commandPOJO.getParam(), ','));
				}
			}
			if(commandPOJO.getDealerName() != null){
	            String temp = commandPOJO.getDealerName();
	            if(temp.equals("")){
	                
	            }else{              
	                String[] dealerIds=FormatUtil.strToFormat(temp).split(",");
	                commandPOJO.setDealerIds(dealerIds);
	                commandPOJO.setDealerName(null);
	            }
	            }
			List<CommandPOJO> list = sendCommandService.getList(commandPOJO);
			int i = 0;
			for (CommandPOJO command1 : list) {
				i++;
				if (StringUtils.isNotEmpty(command1.getGatewayBack())) {
					if ("00".equals(command1.getGatewayBack())) {
						command1.setGatewayBack("成功");
					} else if ("9999".equals(command1.getUnitBack())) {
						command1.setGatewayBack("失败");
					} else {
						command1.setGatewayBack("");
					}
				}
				if (StringUtils.isNotEmpty(command1.getUnitBack())) {
					if ("00".equals(command1.getUnitBack())) {
						command1.setUnitBack("成功");
					} else if ("9999".equals(command1.getUnitBack())) {
						command1.setUnitBack("失败");
					} else {
						//这里解决指令导出时终端回应出现代码的BUG BY HHF 2015-11-24
						//command1.setGatewayBack("");
						command1.setUnitBack("");
					}
				}
				if (command1.getPathFlag() != null) {
					if ((command1.getPathFlag().intValue() == 0)) {
					} else if ("01".equals(command1.getUnitBack())) {
						command1.setUnitBack("失败");
					} else {
						command1.setUnitBack("");
					}
				}
				values
						.add(new Object[] {
								i,
								command1.getVehicleDef(),
								command1.getDealerName(),
								command1.getAreaName(),
								command1.getVehicleStatus(),
								command1.getVehicleModelName(),
								command1.getVehicleCode(),
								command1.getVehicleArg(),
								command1.getUnitSn(),
								command1.getSimNo(),
								command1.getUserName(),
								command1.getDepartName(),
								command1.getCommandTypeName(),
								command1.getParam(),
								command1.getGatewayBack(),
								command1.getUnitBack(),								
								command1.getPathFlag() != null
										&& command1.getPathFlag().intValue() == 0 ? "GPRS"
										: "GSM",
								DateUtil.format(command1.getStamp(),
										DateUtil.YMD_DASH_WITH_FULLTIME24) });
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		String[] headers = new String[] { "序号", "整机编号","经销商","区域","机械状态组", "机型", "机械代号", "配置号", "终端序列号", "SIM卡号",
				"操作员","部门", "指令类型", "参数", "网关回应", "终端回应", "指令通道", "时间" };
		super.renderExcel("指令下发" + ".xls", headers, values);
		return null;
	}

	/**
	 * @Title:searchUpgrade
	 * @Description:查询空中升级的记录
	 * @throws Exception
	 * @throws
	 */
	public void searchUpgrade() throws Exception {
		pageSelect.setPage(page);
		pageSelect.setRows(rows);
		// 经销商
		if (StringUtils.isNotEmpty(upgradeOJO.getSupperierSn())) {
			upgradeOJO.setDealerIds(com.chinaGPS.gtmp.util.StringUtils.split(
					upgradeOJO.getSupperierSn(), ','));
		}
		renderObject(sendCommandService
				.getUpgradeByPage(upgradeOJO, pageSelect));
	}

	@Override
	public CommandSendPOJO getModel() {
		return commandSendPOJO;
	}

	public PageSelect getPageSelect() {
		return pageSelect;
	}

	public void setPageSelect(PageSelect pageSelect) {
		this.pageSelect = pageSelect;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public CommandPOJO getCommandPOJO() {
		return commandPOJO;
	}

	public void setCommandPOJO(CommandPOJO commandPOJO) {
		this.commandPOJO = commandPOJO;
	}

	public ISendCommandService getSendCommandService() {
		return sendCommandService;
	}

	public void setSendCommandService(ISendCommandService sendCommandService) {
		this.sendCommandService = sendCommandService;
	}

	public RemoteUpgradePOJO getUpgradeOJO() {
		return upgradeOJO;
	}

	public void setUpgradeOJO(RemoteUpgradePOJO upgradeOJO) {
		this.upgradeOJO = upgradeOJO;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}
