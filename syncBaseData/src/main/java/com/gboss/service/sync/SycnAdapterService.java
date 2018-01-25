package com.gboss.service.sync;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.pojo.SyncDate;
import com.gboss.service.AccountService;
import com.gboss.service.BarcodeService;
import com.gboss.service.BrandService;
import com.gboss.service.ComboService;
import com.gboss.service.CompanyService;
import com.gboss.service.CustVehicleService;
import com.gboss.service.CustomerService;
import com.gboss.service.EquipcodeService;
import com.gboss.service.FeeInfoService;
import com.gboss.service.FeeSimMService;
import com.gboss.service.FeeSimPService;
import com.gboss.service.InsurerService;
import com.gboss.service.LinkmanService;
import com.gboss.service.ModelService;
import com.gboss.service.PreloadService;
import com.gboss.service.SeriesService;
import com.gboss.service.SyncDateService;
import com.gboss.service.SyncStrategyService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleConfService;
import com.gboss.service.VehicleService;
import com.gboss.util.DateUtil;
import com.gboss.util.TimeHelper;

@Service
public abstract class SycnAdapterService implements SyncStrategyService {

	protected static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	protected BrandService brandService;
	
	@Autowired
	protected SystemConfig systemconfig;
	
	@Autowired
	protected SyncDateService syncDateService;
	
	@Autowired
	protected SeriesService seriesService;
	
	@Autowired
	protected LinkmanService linkmanService;

	protected OpenLdap ldap = OpenLdapManager.getInstance();

	@Autowired
	protected CompanyService companyService;

	@Autowired
	protected ModelService modelService;

	@Autowired
	protected InsurerService insurerService;

	@Autowired
	protected PreloadService preloadService;

	@Autowired
	protected ComboService comboService;

	@Autowired
	protected VehicleService vehicleService;

	@Autowired
	protected UnitService unitService;

	@Autowired
	protected CustomerService customerService;

	@Autowired
	protected CustVehicleService custVehicleService;
	
	@Autowired
	protected BarcodeService barcodeService;
	
	@Autowired
	protected AccountService accountService;
	
	@Autowired
	protected FeeSimPService feeSimPService;
	
	@Autowired
	protected FeeSimMService feeSimMService;
	
	@Autowired
	protected FeeInfoService feeInfoService;
	
	@Autowired
	protected EquipcodeService equipcodeService;
	
	@Autowired
	protected VehicleConfService vehicleConfService;
	
	
	private String API_NAME;//接口名
	
	public String getAPI_NAME() {
		return API_NAME;
	}

	public void setAPI_NAME(String API_NAME) {
		this.API_NAME = API_NAME;
	}
	
	public static String getTranslation(String API_NAME){
		if(API_NAME!=null&&!"".equals(API_NAME)){
			if(API_NAME.equals(SystemConst.HM_ACCOUNT)){
				return "账户";
			}
			if(API_NAME.equals(SystemConst.HM_BANDINFO)){
				return "绑定";
			}
			if(API_NAME.equals(SystemConst.HM_BASEDASTA)){
				return "基础资料";
			}
			if(API_NAME.equals(SystemConst.HM_COMBO)){
				return "套餐";
			}
			if(API_NAME.equals(SystemConst.HM_INSURANCE)){
				return "保险";
			}
			if(API_NAME.equals(SystemConst.HM_MODEL)){
				return "车型";
			}
			if(API_NAME.equals(SystemConst.HM_SERIES)){
				return "车系";
			}
			if(API_NAME.equals(SystemConst.HM_4S)){
				return "4s店";
			}
		}
		return "";
	}
	
	/**
	 * @param API_NAME
	 * @return
	 */
	public String getRequestURI(String API_NAME){
		if(API_NAME!=null&&!"".equals(API_NAME)){
			if(API_NAME.equals(SystemConst.HM_ACCOUNT)){
				return systemconfig.getAccount();
			}
			if(API_NAME.equals(SystemConst.HM_BANDINFO)){
				return systemconfig.getBandInfo();
			}
			if(API_NAME.equals(SystemConst.HM_BASEDASTA)){
				return systemconfig.getInfo();
			}
			if(API_NAME.equals(SystemConst.HM_COMBO)){
				return systemconfig.getPackList();
			}
			if(API_NAME.equals(SystemConst.HM_INSURANCE)){
				return systemconfig.getInsuracerList();
			}
			if(API_NAME.equals(SystemConst.HM_MODEL)){
				return systemconfig.getSysnModel();
			}
			if(API_NAME.equals(SystemConst.HM_SERIES)){
				return systemconfig.getSysnSeries();
			}
			if(API_NAME.equals(SystemConst.HM_4S)){
				return systemconfig.getSsssList();
			}
		}
		return "";
	}
	
	/**
	 * 获取4S店的ID
	 * @param input
	 * @return
	 */
	protected static BigDecimal getCompanyCode(String input){
		Pattern pattern = Pattern.compile("\\d{1,}");
		Matcher matcher = pattern.matcher(input);
		while(matcher.find()){
			String findNum = matcher.group();
			if(pattern.matcher(findNum).find()){
				return new BigDecimal(findNum);
			}
		}
		return null;
	}
	
	/**
	 * 修改开始和结束时间
	 * @param syncDate
	 * @param start_date
	 * @param end_date
	 */
	protected static void changeDate(SyncDate syncDate, Long start_date, Long end_date) {
		start_date = syncDate.getEnd_time().getTime();
		end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
	}
	
	/**
	 * 1.在配置文件中增加2个参数,单位为秒；定时任务调度300秒；延时参数300秒
	 * 2.程序启动后，根据定时任务调度参数确定任务调用时间，第一次先检查数据库里没有对应接口的数据，如果没有，
		 接口调用时间为：开始时间=1970-01-01 00:00:00 ,   结束时间=(当前时间-延时参数秒数)
	 * @param syncDate
	 */
	public void setTime(SyncDate syncDate,boolean isFirstTime){
		if(syncDate != null ){
			if(isFirstTime){
				syncDate.setBegin_time(TimeHelper.getGMTBeginDate());
			}else{
				syncDate.setBegin_time(syncDate.getEnd_time());
			}
			syncDate.setEnd_time(DateUtil.offsetDate(new Date(), Calendar.SECOND, -(Integer.parseInt(systemconfig.getDalaytime()))));
		}
	}
	
	//(当前时间-对应接口的结束时间)的秒数是否大于（延时参数秒数）
	public boolean judgTime(long end_time){
		long now = new Date().getTime();
		long g = now - end_time;
		long d = Long.parseLong(systemconfig.getDalaytime())*1000;
		if(g >= d){
			return  true;
		}
		return false;
	}
	
	public static boolean hasText(Object input){
		if(input == null || "".equals(input))
		return false;
		else return true;
	}
	
}
