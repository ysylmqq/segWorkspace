package com.chinaGPS.gtmp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaGPS.gtmp.entity.CommandPOJO;
import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.entity.DicCommandType;
import com.chinaGPS.gtmp.entity.RemoteUpgradePOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IDicCommandType
 * @Description:
 * @author:zfy
 * @date:2013-4-22 上午11:03:50
 */
public interface ISendCommandService extends BaseService<CommandPOJO>{
    public DicCommandType getCommandType(DicCommandType dicCommandType) throws Exception;
    
    //add jcb 2013-6-19
    public DicCommandType getCommandTypeTest(DicCommandType dicCommandType) throws Exception;
    
    public List<DicCommandType> getCommandTypes(DicCommandType dicCommandType) throws Exception;
    
    /**
     * @Title:getUpgradeByPage
     * @Description:获取测试信息
     * @param remoteUpgradeOJO
     * @param pageSelect
     * @return
     * @throws
     */
    public HashMap<String, Object> getUpgradeByPage(RemoteUpgradePOJO remoteUpgradeOJO,
		PageSelect pageSelect) throws Exception;
    /**
     * @Title:getByIdUpgrade
     * @Description:根据commandSn找空中升级指令
     * @param commandSn
     * @return
     * @throws DataAccessException
     * @throws
     */
    public RemoteUpgradePOJO getByIdUpgrade(Long commandSn) throws Exception;
    /**
     * @Title:getById4Test
     * @Description:根据commandSn找测试指令
     * @param commandSn
     * @return
     * @throws DataAccessException
     */
    public TestCommandPOJO getById4Test(Long commandSn) throws Exception;
    /**
     * 保存预约锁车指令
     * @param commandSendPOJO
     * @return
     * @throws Exception
     */
    public int addLockTime(CommandSendPOJO commandSendPOJO) throws Exception;
    /**
     * 取未发送预约锁车指令
     * @return
     * @throws Exception
     */
	public List<CommandSendPOJO> getTiminglock() throws Exception;
	/**
	 * 更新预约锁车指令状态
	 * @param id
	 * @throws Exception
	 */
	public void updateTiminglock(Long id) throws Exception; 
}

