package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.CommandPOJO;
import com.chinaGPS.gtmp.entity.CommandSendPOJO;
import com.chinaGPS.gtmp.entity.DicCommandType;
import com.chinaGPS.gtmp.entity.RemoteUpgradePOJO;
import com.chinaGPS.gtmp.entity.TestCommandPOJO;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.CommandMapper;
import com.chinaGPS.gtmp.service.ISendCommandService;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:DealerAreaServiceImpl
 * @Description:
 * @author:lxj
 * @date:Dec 11, 2012 2:43:44 PM
 *
 */
@Service
public class SendCommandServiceImpl extends BaseServiceImpl<CommandPOJO> implements ISendCommandService{
    @Resource
    private CommandMapper<CommandPOJO> commandMapper;
    
    @Override
    protected BaseSqlMapper<CommandPOJO> getMapper() {
		return this.commandMapper;
    }
    
    @Override
    public DicCommandType getCommandType(DicCommandType dicCommandType) throws Exception {
		List<DicCommandType> diccomList = commandMapper.getCommandType(dicCommandType);
		if (diccomList != null && !diccomList.isEmpty()) {
			return diccomList.get(0);
		}
		return null;
	}
    
    //add jcb 2013-6-19
    @Override
    public DicCommandType getCommandTypeTest(DicCommandType dicCommandType) throws Exception {
		List<DicCommandType> diccomList = commandMapper.getCommandTypeTest(dicCommandType);
		if (diccomList != null && !diccomList.isEmpty()) {
			return diccomList.get(0);
		}
		return null;
	}
    
    
    @Override
    public List<DicCommandType> getCommandTypes(DicCommandType dicCommandType) throws Exception {
    	return commandMapper.getCommandType(dicCommandType);
    }
    @Override
    public HashMap<String, Object> getUpgradeByPage(
			RemoteUpgradePOJO remoteUpgradeOJO, PageSelect pageSelect)
			throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("command", remoteUpgradeOJO);
		int total = commandMapper.countUpgrade(map);
		List<RemoteUpgradePOJO> resultList = commandMapper.getByPageUpgrade(
				map,
				new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
    
    @Override
    public RemoteUpgradePOJO getByIdUpgrade(Long commandSn) throws Exception {
    	return commandMapper.getByIdUpgrade(commandSn);
    }

	@Override
	public TestCommandPOJO getById4Test(Long commandSn) throws Exception {
		return commandMapper.getById4Test(commandSn);
	}

	@Override
	public int addLockTime(CommandSendPOJO commandSendPOJO) throws Exception {
		return commandMapper.addLockTime(commandSendPOJO);
	}

	@Override
	public List<CommandSendPOJO> getTiminglock() throws Exception {
		return commandMapper.getTiminglock();
	}

	@Override
	public void updateTiminglock(Long id) throws Exception {
		commandMapper.updateTiminglock(id);
	}
}