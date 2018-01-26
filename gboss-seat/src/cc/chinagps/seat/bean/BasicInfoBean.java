package cc.chinagps.seat.bean;

import java.util.HashMap;
import java.util.Map;

import cc.chinagps.seat.bean.table.CustomerTable;
import cc.chinagps.seat.bean.table.UnitTable;
import cc.chinagps.seat.bean.table.VehicleTable;

/**
 * 基本信息
 * @author Administrator
 *
 */
public class BasicInfoBean {
	private UnitTable unit;
	
	public BasicInfoBean(UnitTable unit) {
		this.unit = unit;
	}

	public CustomerTable getCustomer() {
		return this.unit.getCustomer();
	}

	public UnitTable getUnit() {
		return unit;
	}

	public VehicleTable getVehicle() {
		return this.unit.getVehicle();
	}
	
	public Map<String, String> convertToMap() {
		Map<String, String> map = new HashMap<String, String>();
		
		return map;
	}
}
