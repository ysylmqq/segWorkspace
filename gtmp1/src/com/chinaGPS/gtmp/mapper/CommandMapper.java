package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.business.memcache.GatewayBack;
import com.chinaGPS.gtmp.entity.CommandPOJO;
import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.entity.CommandUpgradePOJO;
import com.chinaGPS.gtmp.entity.DicCommandType;
import com.chinaGPS.gtmp.entity.RemoteUpgradePOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;

@Component
public interface CommandMapper<T extends CommandPOJO> extends BaseSqlMapper<T> {

	public int add(CommandPOJO command) throws Exception;

	public List<DicCommandType> getCommandType(DicCommandType dicCommandType) throws Exception;

	public List<DicCommandType> getCommandTypeTest(DicCommandType dicCommandType) throws Exception;

	public int addUpgrade(CommandUpgradePOJO commandUpgrade) throws Exception;

	public int updateTab(GatewayBack back) throws Exception;

	public int updateTab1(GatewayBack back) throws Exception;

	public int getCommandSn(Long num) throws Exception;

	public int addTestCommand(CommandPOJO command) throws Exception;

	public void updateTestCommand(GatewayBack back) throws Exception;
	
	public int addLockTime(CommandSendPOJO commandSendPOJO) throws Exception;

	/**
	 * @Title:countUpgrade
	 * @Description:查询空中升级记录数
	 * @param map
	 * @return
	 * @throws
	 */
	public int countUpgrade(Map<String, Serializable> map) throws Exception;

	/**
	 * @Title:getByPageUpgrade
	 * @Description:查询空中升级记录
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws
	 */
	public List<RemoteUpgradePOJO> getByPageUpgrade(Map<String, Serializable> map, RowBounds rowBounds) throws Exception;

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
	 * @throws
	 */
	public TestCommandPOJO getById4Test(Long commandSn) throws Exception;
    
	public List<CommandSendPOJO> getTiminglock() throws Exception;
	
	public void updateTiminglock(Long id) throws Exception; 
}