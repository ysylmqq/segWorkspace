package cc.chinagps.seat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.chinagps.seat.bean.GpsBasicInformation;
import cc.chinagps.seat.bean.HistoryTrack;
import cc.chinagps.seat.service.AlarmService;
import cc.chinagps.seat.service.VehicleMonitorService;

@Controller
@RequestMapping("/vm")
public class VehicleMonitorController extends BasicController {
	
	//@Autowired
	private VehicleMonitorService monitorService;
	
	//@Autowired
	private AlarmService alarmService;
	
	@RequestMapping("/trackexport")
	public ModelAndView exportHistoryTrack(
			@Valid HistoryTrack historyTrack, 
			HttpServletRequest request) {
		List<GpsBasicInformation> infoList;
		try {
			infoList = monitorService.
					getGpsBasicInfoList(historyTrack);
		} catch (Exception e) {
			LOGGER.error("", e);
			infoList = new ArrayList<GpsBasicInformation>();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gpsInfo", infoList);
		model.put("historyTrack", historyTrack);
		return new ModelAndView("historyTrack", model);
	}
}
