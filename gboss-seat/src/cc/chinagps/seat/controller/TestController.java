package cc.chinagps.seat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

import cc.chinagps.seat.bean.QResult;
import cc.chinagps.seat.bean.QVehicleUnit;
import cc.chinagps.seat.dao.VehicleDao;

@Controller
@RequestMapping("/base/*")
public class TestController {
	@ResponseBody
	@RequestMapping("vehicleInfo")
	//url: http://localhost:8089/seat/service/base/vehicleInfo?query=123&limit=10
	public String vehicleInfo(String query, int limit) throws JSONException {
		VehicleDao dao = VehicleDao.getInstance();
		List<QVehicleUnit> list = dao.queryVehicleUnit(query, limit);
		
		QResult result = new QResult();
		result.setSuccess(true);
		result.setData(list);
		String json = JSONUtil.serialize(result);
		return json;
	}
}