package com.chinaGPS.gtmp.action.run;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.chinaGPS.gtmp.action.common.BaseAction;
import com.chinaGPS.gtmp.entity.HistoryPOJO;
import com.chinaGPS.gtmp.service.IHistoryService;
import com.chinaGPS.gtmp.util.DateUtil;
import com.chinaGPS.gtmp.util.HttpURLConnectionUtil;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Package:com.chinaGPS.gtmp.action.run
 * @ClassName:HistoryAction
 * @Description:历史位置控制器
 * @author:zfy
 * @date:2013-4-19 下午04:21:41
 */
@Scope("prototype")
@Controller
public class HistoryAction extends BaseAction implements ModelDriven<HistoryPOJO> {
	private static final long serialVersionUID = -8635959693986214979L;
	private static Logger logger = LoggerFactory.getLogger(HistoryAction.class);
	
	@Resource
    private HistoryPOJO historyPOJO;
    @Resource
    private IHistoryService historyService;
    
    public void getList() {
		List<HistoryPOJO> result = new ArrayList<HistoryPOJO>();
		try {
			if (historyPOJO != null) {
				if (historyPOJO.getVehicleState() == null) {
					historyPOJO.setVehicleState("GPS_TIME");
				}
			}
			result = historyService.getList(historyPOJO);
			for(HistoryPOJO position:result){
				if(position.getLat()!=null && position.getLon()!=null&&!position.getLat().equals("0")&&!position.getLon().equals("0")){
					String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+position.getLon()+","+position.getLat()));
					if(ret!=null && !ret.equals("")){
						position.setReferencePosition(ret);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		renderObject(result);
	}
    
    public void exportToExcel() {
		List<Object[]> values = new ArrayList<Object[]>();
		try {
			historyPOJO.setUnitId(java.net.URLDecoder.decode(historyPOJO
					.getUnitId()));
			if (historyPOJO != null) {
				if (historyPOJO.getVehicleState() == null) {
					historyPOJO.setVehicleState("GPS_TIME");
				}
			}
			// 开始时间
			historyPOJO.setStartTime(DateUtil.parse(java.net.URLDecoder
					.decode(historyPOJO.getRawData()),
					DateUtil.YMD_DASH_WITH_FULLTIME));
			historyPOJO.setEndTime(DateUtil.parse(java.net.URLDecoder
					.decode(historyPOJO.getReferencePosition()),
					DateUtil.YMD_DASH_WITH_FULLTIME));

			List<HistoryPOJO> resultList = historyService.getList(historyPOJO);
			String locationState = null;

			for (HistoryPOJO historyPOJO1 : resultList) {
				if (historyPOJO1.getLocationState() != null) {
					if (historyPOJO1.getLocationState().intValue() == 0) {
						locationState = "否";
					} else {
						locationState = "是";
					}
				}
				
				
				if(historyPOJO1.getLat()!=null && historyPOJO1.getLon()!=null&&!historyPOJO1.getLat().equals("0")&&!historyPOJO1.getLon().equals("0")){
						String ret = HttpURLConnectionUtil.getAddress(HttpURLConnectionUtil.getBaiduLonlat(""+historyPOJO1.getLon()+","+historyPOJO1.getLat()));
						if(ret!=null && !ret.equals("")){
							historyPOJO1.setReferencePosition(ret);
						}
				}
				

				values.add(new Object[] {
						historyPOJO1.getVehicleDef(),
						historyPOJO1.getHistoryId(),
						historyPOJO1.getUnitId(),
						historyPOJO1.getLon(),
						historyPOJO1.getLat(),
						historyPOJO1.getDirection(),
						historyPOJO1.getSpeed(),
						locationState,
						historyPOJO1.getVehicleState(),
						historyPOJO1.getReferencePosition(),
						DateUtil.format(historyPOJO1.getGpsTime(),
								DateUtil.YMD_DASH_WITH_FULLTIME24) });
			}
			String[] headers = new String[] { "整机编号","序列号", "终端编号", "经度", "纬度", "方向",
					"速度", "是否定位", "车辆状态", "参考位置", "GPS定位时间" };
			super.renderExcel("历史轨迹" + ".xls", headers, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
    
    @Override
    public HistoryPOJO getModel() {
    	return historyPOJO;
    }

}

