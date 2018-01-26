package cc.chinagps.seat.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.CommandTable;
import cc.chinagps.seat.dao.CmdDao;
import cc.chinagps.seat.service.CmdService;
import cc.chinagps.seat.util.Consts;

@Service
public class CmdServiceImpl implements CmdService {
	
	private final Logger LOGGER = Logger.getLogger(getClass());
	@Autowired
	private CmdDao cmdDao;
	
	@Override
	public List<CommandTable> getCmdList(String param, int type) {
		if (param == null) {
			LOGGER.error("Wrong parameter!!!");
			return new ArrayList<CommandTable>();
		} 
		if (type == Consts.TYPE_PHONENO) {
			return cmdDao.getCmdListByPhoneNo(param);
		} else if (type == Consts.TYPE_VEHICLEID) {
			try {
				BigInteger vehicleId = new BigInteger(param);
				return cmdDao.getCmdListByVehicleId(vehicleId);
			} catch (NumberFormatException e) {
				LOGGER.error("VehicleId is not a valid number!!", e);
				return new ArrayList<CommandTable>();
			}
		} else {
			LOGGER.error("Wrong type!!!");
			return new ArrayList<CommandTable>();
		}
	}

}
