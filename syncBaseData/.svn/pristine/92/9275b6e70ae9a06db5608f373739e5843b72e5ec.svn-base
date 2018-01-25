package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.UnitDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.service.VehicleService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:VehicleServiceImpl
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午3:22:09
 */

@Service("vehicleService")
public class VehicleServiceImpl extends BaseServiceImpl implements VehicleService {

	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao vehicleDao;

	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;

	 
	 
	public Long add(Vehicle vehicle) {
		try {
			save(vehicle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicle.getVehicle_id();
	}

	 
	public boolean is_repeat(Vehicle vehicle) {
		return vehicleDao.is_repeat(vehicle);
	}

	 
	 
	public void delete(Long id) {
		delete(Vehicle.class, id);
	}

	 
	public List<Vehicle> getVehiclesByCustid(Long cust_id) {
		return vehicleDao.getVehiclesByCustid(cust_id);
	}

	 
	public Vehicle getVehicleByid(Long id) {
		return vehicleDao.getVehicleByid(id);
	}

	 
	public Long getIdByPlate(String plate_no) {
		return vehicleDao.getIdByPlate(plate_no);
	}

	 
	public List<HashMap<String, Object>> getVehicleTree(Customer customer) {
		List<HashMap<String, Object>> treelist = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", customer.getCustomer_id());
		map.put("name", customer.getCustomer_name());
		List<Vehicle> list = vehicleDao.getVehiclesByCustid(customer
				.getCustomer_id());
		for (Vehicle vehicle : list) {
			HashMap<String, Object> child = new HashMap<String, Object>();
			Unit unit = unitDao.getByCustAndVehicle(customer.getCustomer_id(),
					vehicle.getVehicle_id());
			child.put("id", vehicle.getVehicle_id());
			String edate = "";
		/*	// 这里的pay_model指的是需要显示哪种服务截止时间
			if (stime != null && stime.getService_edate() != null
					&& customer.getPay_model() == 1) {
				edate = DateUtil.format(stime.getService_edate(),
						DateUtil.YMD_DASH);
				child.put("name", vehicle.getPlate_no() + "(服务截止时间:" + edate
						+ ")");
			} else if (stime != null && stime.getSim_edate() != null
					&& customer.getPay_model() == 2) {
				edate = DateUtil
						.format(stime.getSim_edate(), DateUtil.YMD_DASH);
				child.put("name", vehicle.getPlate_no() + "(SIM截止时间:" + edate
						+ ")");
			} else {
				child.put("name", vehicle.getPlate_no());
			}*/
			items.add(child);
		}
		map.put("items", items);
		treelist.add(map);
		return treelist;
	}
	
	public static void main(String[] args) {
		ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		VehicleServiceImpl  v = (VehicleServiceImpl) beanFactory.getBean("vehicleService", VehicleService.class);
//		System.out.println("v:" + v);
		System.out.println(v.saveVehicle("01","01", "01", "01", null, null, 0, "01", 0, 0L, 0L, 0L, 0, "01").getVehicle_id());
	}

	 
	public boolean is_exist(Vehicle vehicle) {
		return vehicleDao.is_exist(vehicle);
	}

	 
	public Long getIdByVin(String vin) {
		return vehicleDao.getIdByVin(vin);
	}
	
	private Vehicle saveVehicle(String vin, String color, String engine_no, String factory, Date production_date, Date buy_date, 
			Integer vload, String plate_no1, Integer i_id, Long s_no,Long series_id,Long model_id,Integer vehicle_type,String equip_code) {
			Vehicle vehicle = new Vehicle();
			// 海马暂时默认为201
			vehicle.setSubco_no(SystemConst.HM_SUBCO_NO);// LDAP分公司根节点ID, //对应我们的分公司, // 内部机构
			vehicle.setPlate_no(plate_no1);// 车牌号码
			vehicle.setPlate_color(1);// 车牌颜色, 系统值2110, 1=蓝, 2=黄, 3=黑, 4=白, 9=其他
			vehicle.setVehicle_type(vehicle_type);// 车辆类型, 系统值2030, 1=小型轿车
			vehicle.setVehicle_status(0);// 车辆状态, 系统值2060, 0=正常, 1=故障, 2=维修, 3=警情
			vehicle.setVin(vin);// 车辆识别号码/车架号, 17位
			vehicle.setColor(color);// 车辆颜色
			vehicle.setEngine_no(engine_no);// 发动机编号
			vehicle.setFactory(factory);// 生产厂家
			vehicle.setProduction_date(production_date);// 生产日期
			vehicle.setBuy_date(buy_date);// 购买日期
			vehicle.setFlag(1);//
			vehicle.setVload(vload);// 载重/乘客数
			vehicle.setOp_id(1L);// 操作人员
			vehicle.setId_4s(s_no);// 车辆4s店, 对应前装的4s店机构ID
			vehicle.setInsurance_id(i_id);// 商业保险公司,与综合盗抢险不同
			vehicle.setEquip_code(equip_code);//简码配置
			vehicle.setModel(model_id);
			vehicle.setSeries(series_id);
			
			try {
				vehicle.setVehicle_id(save(vehicle));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vehicle;
		}

}
