package com.gboss.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.CompanyDao;
import com.gboss.pojo.Barcode;
import com.gboss.pojo.Brand;
import com.gboss.pojo.Combo;
import com.gboss.pojo.CustVehicle;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Insurer;
import com.gboss.pojo.Model;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Series;
import com.gboss.pojo.SyncDate;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Vehicle;
import com.gboss.service.BarcodeService;
import com.gboss.service.BrandService;
import com.gboss.service.ComboService;
import com.gboss.service.CustVehicleService;
import com.gboss.service.CustomerService;
import com.gboss.service.InsurerService;
import com.gboss.service.ModelService;
import com.gboss.service.PreloadService;
import com.gboss.service.SeriesService;
import com.gboss.service.SyncBaseDataService;
import com.gboss.service.SyncDateService;
import com.gboss.service.UnitService;
import com.gboss.service.VehicleService;
import com.gboss.util.DateUtil;
import com.gboss.util.StringUtils;
import com.gboss.util.TimeHelper;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SyncBaseDataServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2015-2-28 上午8:58:27
 */
@Service("syncBaseDataService")
public class SyncBaseDataServiceImpl extends BaseServiceImpl implements SyncBaseDataService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SyncBaseDataServiceImpl.class);

	@Autowired
	private SystemConfig systemconfig;

	@Autowired
	private SeriesService seriesService;

	private OpenLdap ldap = OpenLdapManager.getInstance();

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ModelService modelService;

	@Autowired
	private InsurerService insurerService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private PreloadService preloadService;

	@Autowired
	private ComboService comboService;

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private SyncDateService syncDateService;
	
	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustVehicleService custVehicleService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public boolean checkConnect() {

		String ip = systemconfig.getSysnDadaUrl();
		boolean flag = false;
		String obd_port = systemconfig.getSysnDadaPort();
		int port = Integer.valueOf(obd_port);
		Socket my = new Socket(); // 实例化socket对象
		try {
			InetSocketAddress mySock = new InetSocketAddress(InetAddress.getByName(ip), port);// 假设连接目标的80端口
			my.connect(mySock, 100);// 100毫秒超时
			System.out.println("同步数据连接成功!");
			flag = true;
		} catch (SocketTimeoutException e) {// 捉到了!
			System.out.println("cannot connect ...");
		} catch (Exception e) {// 其他有IOException等
			System.out.println("Exception,cannot connect ...");
		}
		return flag;
	}

	/**
	 * 同步车系数据
	 */
	 
	public boolean syncSeries() throws Exception  {
		Long series_id = null;
		Long start_date = null;
		Long end_date = null;

		boolean flag = false;

		SyncDate syncDate = null;
		String url = systemconfig.getSysnDadaUrl();
		url = url.concat(systemconfig.getSysnSeries());
		
		int i = 0 ;//接口调用次数
		if (series_id != null) {
			url = url.concat("?series_id=").concat(series_id.toString());
		} else {
			// 获取车系同步时间对象
			syncDate = syncDateService.getSyncDateByName(SystemConst.HM_SERIES);
			if (syncDate != null && syncDate.getBegin_time() != null && syncDate.getEnd_time() != null) {
				
				 //本次开始时间为上次结束时间
				 start_date = syncDate.getEnd_time().getTime();
				 //本次结束时间：上次结束时间 +（上次结束时间 - 上次开始时间）
				 end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();

				boolean isTure = false;// 是否停止执行任务标志
				
				// 本次结束时间 与当前时间比较 判断是否需要执行任务
				if (end_date <= new Date().getTime()) {
					isTure = true;
				}

				while (isTure && i < 600) {
					try {
						i++;
						flag =  sycnSeriesHandler(start_date/1000, end_date/1000, url, flag,i);
						//下次任务起止时间更新
						// 每次同步结束后设置下次同步任务的开始时间和结束时间
						
						System.out.println(SystemConst.HM_SERIES  + "-->[start_date:::" + start_date + ",end_date:" + end_date+"]");
						syncDate.setBegin_time(new Date(start_date));//
						syncDate.setBegin_time(new Date(end_date));//
						
						// 下次任务的开始时间
						start_date = syncDate.getEnd_time().getTime();
						System.out.println("下次任务的开始时间:" + sdf.format(new Date(start_date+10*60*1000) ));
						
						// 下次任务的结束时间
						end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
						
						// 若下次任务的结束时间比当前时间要大则停止执行任务
						if (end_date > new Date().getTime()) {
							isTure = false;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {// 首次执行
				try {
					System.out.println("首次请求地址:" + url );
					System.out.println("##车系信息首次同步开始!##");
					i++;
					flag = sycnSeriesHandler(null, null, url, flag,i);
					syncDate = new SyncDate();
					//第一次执行完后更新时间
					syncDate.setBegin_time(DateUtil.getBeforTenMinute());	//昨天开始时间
					syncDate.setEnd_time(DateUtil.getCurTimeTenMinute());		//今天开始时间
					syncDate.setIf_name(SystemConst.HM_SERIES);
					syncDate.setSubco_no(201L);
					syncDateService.save(syncDate);
					System.out.println("##车系信息首次同步结束!##");
					start_date= null;//防止更新
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (null != start_date) {
			syncDateService.update(syncDate);
		}
		return flag;
	}

	/**
	 * 同步车型数据
	 */
	 
	public boolean syncModel() throws Exception{

		Long model_id = null;
		Long start_date = null;
		Long end_date = null;
		
		boolean flag = false;
		SyncDate syncDate = null;
		String url = systemconfig.getSysnDadaUrl();
		url = url.concat(systemconfig.getSysnModel());

		int i = 0;// 接口调用次数
		if (model_id != null) {
			url = url.concat("?model_id=").concat(model_id.toString());
		} else {
			// 获取车系同步时间对象
			syncDate = syncDateService.getSyncDateByName(SystemConst.HM_MODEL);
			if (syncDate != null && syncDate.getBegin_time() != null && syncDate.getEnd_time() != null) {
				// 本次开始时间为上次结束时间
				start_date = syncDate.getEnd_time().getTime();
				// 本次结束时间：上次结束时间 +（上次结束时间 - 上次开始时间）
				end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() -  syncDate.getBegin_time().getTime();

				boolean isTure = false;// 是否停止执行任务标志
				// 本次结束时间 与当前时间比较 判断是否需要执行任务
				if (end_date <= new Date().getTime()) {
					isTure = true;
				}

				while (isTure && i < 600) {
					try {
						i++;
						flag = sycnModelHandler(start_date / 1000, end_date / 1000, url, flag, i);
						// 下次任务起止时间更新
						// 每次同步结束后设置下次同步任务的开始时间和结束时间
						System.out.println(SystemConst.HM_MODEL+ "-->[start_date:::" + start_date + ",end_date:" + end_date+"]");
						syncDate.setBegin_time(new Date(start_date));//
						syncDate.setBegin_time(new Date(end_date));//
						
						// 下次任务的开始时间
						start_date = syncDate.getEnd_time().getTime();
						
						System.out.println("下次任务的开始时间:" + sdf.format(new Date(start_date+10*60*1000) ));
						
						// 下次任务的结束时间
						end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();						
						// 若下次任务的结束时间比当前时间要大则停止执行任务
						if (end_date > new Date().getTime()) {
							isTure = false;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			} else {// 首次执行
				try {
					System.out.println("##车型信息首次同步开始!##");
					System.out.println("本次请求url:" + url );
					i++;
					flag = sycnModelHandler(null, null, url, flag, i);
					syncDate = new SyncDate();
					// 第一次执行完后更新时间
					syncDate.setBegin_time(DateUtil.getBeforTenMinute());	//昨天开始时间
					syncDate.setEnd_time(DateUtil.getCurTimeTenMinute());		//今天开始时间
					syncDate.setIf_name(SystemConst.HM_MODEL);
					syncDate.setSubco_no(201L);
					syncDateService.save(syncDate);
					start_date = null;//防止更新
					System.out.println("##车型信息首次同步结束!##");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (null != start_date) {
			syncDateService.update(syncDate);
		}
		return flag;
	}

	/**
	 * 同步4S店数据
	 */
	 
	public boolean syncCommonCompany() throws Exception{
		
		Long companyno = null;
		Long start_date = null;
		Long end_date = null;
		SyncDate syncDate = null;
		HttpClient httpclient = new DefaultHttpClient();
		boolean flag = false;
		String url = systemconfig.getSysnDadaUrl();
		url += systemconfig.getSsssList();
		
		boolean isTure = false;
		if (companyno != null) {
			url = url.concat("?companyno=").concat(companyno.toString());
		} else {
			syncDate = syncDateService.getSyncDateByName(SystemConst.HM_4S);
			int i = 0;
			if (syncDate != null && syncDate.getBegin_time() != null && syncDate.getEnd_time() != null) {
				
				// 本次开始时间为上次结束时间
				start_date = syncDate.getEnd_time().getTime();
				// 本次结束时间：上次结束时间 +（上次结束时间 - 上次开始时间）
				end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
				if (end_date <= new Date().getTime()) {
					isTure = true;
				}
				while (isTure && i < 600) {
					try {
						i++;
						flag = sycnCompanyHandler(start_date / 1000, end_date / 1000, url, flag, i);
						
						// 下次任务起止时间更新
						// 每次同步结束后设置下次同步任务的开始时间和结束时间
						System.out.println(SystemConst.HM_4S+ "-->[start_date:::" + start_date + ",end_date:" + end_date+"]");
						syncDate.setBegin_time(new Date(start_date));//
						syncDate.setBegin_time(new Date(end_date));//
						// 下次任务的开始时间
						start_date = syncDate.getEnd_time().getTime();
						System.out.println("下次任务的开始时间:" + sdf.format(new Date(start_date+10*60*1000)));
						// 下次任务的结束时间
						end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
						// 若下次任务的结束时间比当前时间要大则停止执行任务
						if (end_date > new Date().getTime()) {
							isTure = false;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SystemException e) {
						e.printStackTrace();
					} 
				}
			} else {
				// 首次执行
				try {
					System.out.println("##4s店信息首次同步开始!##");
					System.out.println("本次请求url:" + url);
					i++;
					flag = sycnCompanyHandler(null, null, url, flag, i);
					syncDate = new SyncDate();
					// 第一次执行完后更新时间
					syncDate.setBegin_time(DateUtil.getBeforTenMinute());//
					syncDate.setEnd_time(DateUtil.getCurTimeTenMinute());//
					syncDate.setIf_name(SystemConst.HM_4S);
					syncDate.setSubco_no(201L);
					syncDateService.save(syncDate);
					start_date = null;// 防止更新
					System.out.println("##4s店信息首次同步结束!##");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
		if (null != start_date) {
			 syncDateService.update(syncDate);
		}
		return flag;
	}

	/**
	 * 同步保险公司 
	 */
	 
	public boolean syncInsuracer() throws Exception  {
		Long start_date = null;
		Long end_date = null;
		Long ic_id = null;// 保险公司ID
	
		boolean flag = false;
	
		SyncDate syncDate = null;
		String url = systemconfig.getSysnDadaUrl();
		url = url.concat(systemconfig.getInsuracerList());
	
		int i = 0;// 接口调用次数
		if (ic_id != null) {
			url = url.concat("?ic_id=").concat(String.valueOf(ic_id));
		} else {
			// 获取车系同步时间对象
			syncDate = syncDateService.getSyncDateByName(SystemConst.HM_INSURANCE);
			if (syncDate != null && syncDate.getBegin_time() != null && syncDate.getEnd_time() != null) {
				// 本次开始时间为上次结束时间
				start_date = syncDate.getEnd_time().getTime();
				// 本次结束时间：上次结束时间 +（上次结束时间 - 上次开始时间）
				end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
	
				boolean isTure = false;// 是否停止执行任务标志
				// 本次结束时间 与当前时间比较 判断是否需要执行任务
				if (end_date <= new Date().getTime()) {
					isTure = true;
				}
	
				while (isTure && i < 600) {
					i++;
					try {
						flag = sycnInsurerHandler(start_date / 1000, end_date / 1000, url, flag, i);
						// 下次任务起止时间更新
						// 每次同步结束后设置下次同步任务的开始时间和结束时间
						System.out.println(SystemConst.HM_INSURANCE+ "-->[start_date:::" + start_date + ",end_date:" + end_date+"]");
						syncDate.setBegin_time(new Date(start_date));//
						syncDate.setBegin_time(new Date(end_date));//
						
						// 下次任务的开始时间
						start_date = syncDate.getEnd_time().getTime();
						
						System.out.println("下次任务的开始时间:" + sdf.format(new Date(start_date+10*60*1000)));
						
						// 下次任务的结束时间
						end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
						
						// 若下次任务的结束时间比当前时间要大则停止执行任务
						if (end_date > new Date().getTime()) {
							isTure = false;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			} else {// 首次执行
				try {
					System.out.println("##保险公司信息首次同步结束!##");
					System.out.println("本次请求url:" + url);
					i++;
					flag = sycnInsurerHandler(null, null, url, flag, i);
					syncDate = new SyncDate();
					// 第一次执行完后更新时间
					syncDate.setBegin_time(DateUtil.getBeforTenMinute()); //
					syncDate.setEnd_time(DateUtil.getCurTimeTenMinute()); //
					syncDate.setIf_name(SystemConst.HM_INSURANCE);
					syncDate.setSubco_no(201L);
					syncDateService.save(syncDate);
					start_date = null;// 防止更新
					System.out.println("##保险公司信息首次同步结束!##");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (null != start_date) {
			syncDateService.update(syncDate);
		}
		return flag;
	}

	/**
	 * 同步套餐信息
	 */
	 
	public boolean syncCombo() throws Exception  {

		Long combo_id = null;
		Long start_date = null;
		Long end_date = null;
		SyncDate syncDate = null;

		HttpClient httpclient = new DefaultHttpClient();
		boolean flag = false;
		String url = systemconfig.getSysnDadaUrl();
		url += (systemconfig.getPackList());
		
		int i = 0;// 接口调用次数
		if (combo_id != null) {
			url = url.concat("?combo_id=").concat(combo_id.toString());
		}  else {
			// 获取车系同步时间对象
			syncDate = syncDateService.getSyncDateByName(SystemConst.HM_COMBO);
			if (syncDate != null && syncDate.getBegin_time() != null && syncDate.getEnd_time() != null) {
				// 本次开始时间为上次结束时间
				start_date = syncDate.getEnd_time().getTime();
				// 本次结束时间：上次结束时间 +（上次结束时间 - 上次开始时间）
				end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();

				boolean isTure = false;// 是否停止执行任务标志
				// 本次结束时间 与当前时间比较 判断是否需要执行任务
				if (end_date <= new Date().getTime()) {
					isTure = true;
				}

				while (isTure && i < 600) {
					i++;
					try {
						flag = sycnComboHandler(start_date / 1000, end_date / 1000, url, flag, i);
						// 下次任务起止时间更新
						// 每次同步结束后设置下次同步任务的开始时间和结束时间
						System.out.println(SystemConst.HM_COMBO+ "-->[start_date:::" + start_date + ",end_date:" + end_date+"]");
						syncDate.setBegin_time(new Date(start_date));//
						syncDate.setBegin_time(new Date(end_date));//
						// 下次任务的开始时间
						start_date = syncDate.getEnd_time().getTime();
						System.out.println("下次任务的开始时间:" + sdf.format(new Date(start_date+10*60*1000) ));
						// 下次任务的结束时间
						end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
						// 若下次任务的结束时间比当前时间要大则停止执行任务
						if (end_date > new Date().getTime()) {
							isTure = false;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (SystemException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			} else {
				try {
					// 首次执行
					System.out.println("##套餐信息首次同步开始!##");
					System.out.println("本次请求url:" + url);
					i++;
					flag = sycnComboHandler(null, null, url, flag, i);
					syncDate = new SyncDate();
					// 第一次执行完后更新时间
//					syncDate.setBegin_time(DateUtil.getDayStartTime(-1)); // 昨天开始时间
//					syncDate.setEnd_time(DateUtil.getDayStartTime(0)); // 今天开始时间
					syncDate.setBegin_time(DateUtil.getBeforTenMinute()); //
					syncDate.setEnd_time(DateUtil.getCurTimeTenMinute()); //
					syncDate.setIf_name(SystemConst.HM_COMBO);
					syncDate.setSubco_no(201L);
					syncDateService.save(syncDate);
					start_date = null;// 防止更新
					System.out.println("##套餐信息首次同步结束!##");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (null != start_date) {
			syncDateService.update(syncDate);
		}
		return flag;
	}
	

	/**
	 * 同步车辆基础资料
	 * 	终端类型为unittype_id=1033(HM专用),
		通信模式mode=3, 
		流量网关data_node=0, 
		短信网关sms_node=5
	 */
	 
	public boolean syncInfo() throws Exception {

		String vin = null;
		Long start_date = null;
		Long end_date = null;
		SyncDate syncDate = null;
		HttpClient httpclient = new DefaultHttpClient();
		boolean flag = false;
		String url = systemconfig.getSysnDadaUrl();
		url += (systemconfig.getInfo());
		int i = 0;
		boolean isTure = false;//是否执行当前任务的标志 false 表示默认不执行，true时表示可以执行
		if (vin != null) {
			url = url.concat("?vin=").concat(vin);
		} else {
			// 获取车系同步时间对象
			syncDate = syncDateService.getSyncDateByName(SystemConst.HM_BASEDASTA);
			
			if (syncDate != null && syncDate.getBegin_time() != null && syncDate.getEnd_time() != null) {
				
				
				// 本次开始时间为上次结束时间
				start_date = syncDate.getEnd_time().getTime();
				// 本次结束时间：上次结束时间 +（上次结束时间 - 上次开始时间）
				end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
				
				System.out.println("s:"+ sdf.format(new Date(start_date))+"-->e:"+sdf.format(new Date(end_date)) );

				// 本次结束时间 与当前时间比较 判断是否需要执行任务
				if (end_date <= new Date().getTime()) {
					isTure = true;
				}

				while (isTure && i < 600) {
					i++;
					try {
						flag = sycnBasedataHandler(start_date / 1000, end_date / 1000, url, flag, i);
						// 下次任务起止时间更新
						System.out.println(SystemConst.HM_BASEDASTA+ "-->[start_date:::" + start_date + ",end_date:" + end_date+"]");
						// 每次同步结束后设置下次同步任务的开始时间和结束时间
						syncDate.setBegin_time(new Date(start_date));//
						syncDate.setBegin_time(new Date(end_date));//

						// 下次任务的开始时间
						start_date = syncDate.getEnd_time().getTime();

						System.out.println("下次任务的开始时间:" + sdf.format(new Date(start_date+10*60*1000) ));

						// 下次任务的结束时间
						end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
						// 若下次任务的结束时间比当前时间要大则停止执行任务
						if (end_date > new Date().getTime()) {
							isTure = false;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (SystemException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			} else {
				// 首次执行
				try {
					System.out.println("##车辆基本信息首次执行开始!##");
					System.out.println("本次请求url:" + url);
					i++;
					flag = sycnBasedataHandler(null, null, url, flag, i);
					syncDate = new SyncDate();
					// 第一次执行完后更新时间
					syncDate.setBegin_time(DateUtil.getBeforTenMinute()); // 昨天开始时间
					syncDate.setEnd_time(DateUtil.getCurTimeTenMinute()); // 今天开始时间
					syncDate.setIf_name(SystemConst.HM_BASEDASTA);
					syncDate.setSubco_no(201L);
					syncDateService.save(syncDate);
					start_date = null;// 防止更新
					System.out.println("##车辆基本信息首次执行结束!##");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (null != start_date) {
			 syncDateService.update(syncDate);
		}

		return flag;
	}

	/**
	 * 同步vin tbox信息
	 */
	public boolean syncBindInfo() throws Exception {
		String bar_code = null;// 条形码
		Long start_date = null;
		Long end_date = null;
		SyncDate syncDate = null;
		HttpClient httpclient = new DefaultHttpClient();
		boolean flag = false;
		String url = systemconfig.getSysnDadaUrl();
		url += (systemconfig.getBandInfo());
		boolean isTure = false;// 是否停止执行任务标志
		if (bar_code != null) {
			url = url.concat("?bar_code=").concat(bar_code);
		} else {
			syncDate = syncDateService.getSyncDateByName(SystemConst.HM_BANDINFO);
			int i = 0;
			if (syncDate != null && syncDate.getBegin_time() != null && syncDate.getEnd_time() != null) {
				// 本次开始时间为上次结束时间
				start_date = syncDate.getEnd_time().getTime();
				// 本次结束时间：上次结束时间 +（上次结束时间 - 上次开始时间）
				end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
				// 本次结束时间 与当前时间比较 判断是否需要执行任务
				if (end_date <= new Date().getTime()) {
					isTure = true;
				}

				while (isTure && i < 600) {
					try {
						i++;
						flag = sycnBandinfoHandler(start_date / 1000, end_date / 1000, url, flag, i);
						// 下次任务起止时间更新
						// 每次同步结束后设置下次同步任务的开始时间和结束时间
						System.out.println(SystemConst.HM_BANDINFO+ "-->[start_date:::" + start_date + ",end_date:" + end_date+"]");
						syncDate.setBegin_time(new Date(start_date));//
						syncDate.setBegin_time(new Date(end_date));//

						// 下次任务的开始时间
						start_date = syncDate.getEnd_time().getTime();
						System.out.println("下次任务的开始时间:" + sdf.format(new Date(start_date+10*60*1000)));
						// 下次任务的结束时间
						end_date = syncDate.getEnd_time().getTime() + syncDate.getEnd_time().getTime() - syncDate.getBegin_time().getTime();
						// 若下次任务的结束时间比当前时间要大则停止执行任务
						if (end_date > new Date().getTime()) {
							 isTure = false;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SystemException e) {
						e.printStackTrace();
					} 
				}
			} else {// 首次执行
				try{
					System.out.println("##车辆绑定信息首次执行开始!##");
					System.out.println("本次请求url:" + url);
					i++;
					flag = sycnBandinfoHandler(null, null, url, flag, i);
					syncDate = new SyncDate();
					// 第一次执行完后更新时间
					syncDate.setBegin_time(DateUtil.getBeforTenMinute()); //
					syncDate.setEnd_time(DateUtil.getCurTimeTenMinute()); //
					syncDate.setIf_name(SystemConst.HM_BANDINFO);
					syncDate.setSubco_no(201L);
					syncDateService.save(syncDate);
					start_date = null;// 防止更新
					System.out.println("##车辆绑定信息首次执行结束!##");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				} 
			}
		}
		if (null != start_date) {
			 syncDateService.update(syncDate);
		}
		
		return false;
	}

	public static HashMap<String, Object> parseJSON2Map(String jsonStr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	
	public static List<HashMap<String, Object>> parseJSON2List(Object jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	
	private static String getStringFromJson(JSONObject adata) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (Object key : adata.keySet()) {
			sb.append("\"" + key + "\":\"" + adata.get(key) + "\",");
		}
		String rtn = sb.toString().substring(0, sb.toString().length() - 1) + "}";
		return rtn;
	}


	
	/**
	 * 获取url地址
	 * 
	 * @param url
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	private String getUrl(String url, Long start_date, Long end_date) {
		if(start_date != null && end_date != null){
			if (url.indexOf("start_date") == -1 && url.indexOf("end_date") == -1) {
				url = url.concat("?start_date=").concat(start_date.toString());
				url = url.concat("&end_date=").concat(end_date.toString());
			} else {
				url = url.substring(0, url.indexOf("?"));
				url = url.concat("?start_date=").concat(start_date.toString());
				url = url.concat("&end_date=").concat(end_date.toString());
			}
		}
		return url;
	}
	
	/**
	 * 同步套餐信息
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws SystemException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private boolean sycnComboHandler(Long start_date, Long end_date, String url, boolean flag, int i) throws SystemException, ClientProtocolException, IOException {
		url = getUrl(url, start_date, end_date);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String strResult = "";

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		JSONObject jobj = new JSONObject();
		nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);
			System.out.println("第" + i + "次:" + sdf.format(new Date()) + SystemConst.HM_COMBO + " 返回内容" + sobj.toString());
			if (sobj.containsKey("data")) {
				flag = true;
				String datas = sobj.getString("data");
				if (null != datas && !datas.toString().equals("null")) {
					List<HashMap<String, Object>> results = parseJSON2List(datas);
					for (HashMap<String, Object> hashMap : results) {
						// 套餐ID
						Long id = hashMap.get("combo_id") == null ? null : Long.valueOf(hashMap.get("combo_id").toString());
						// 状态
						Integer flag1 = hashMap.get("flag") == null ? 1 : Integer.valueOf(hashMap.get("flag").toString());
						// 运营商
						Integer telco = hashMap.get("telco") == null ? 3 : Integer.valueOf(hashMap.get("telco").toString());

						// 套餐名称
						// String combo_code =
						// hashMap.get("combo_code") == null ? "" :
						// hashMap.get("combo_code").toString();

						// 套餐名称
						String combo_name = hashMap.get("combo_name") == null ? null : hashMap.get("combo_name").toString();

						// 套餐费用
						Float month_fee = hashMap.get("month_fee") == null ? 0 : Float.valueOf(hashMap.get("month_fee").toString());
						// 通话时长
						Float voice_time = hashMap.get("voice_time") == null ? null : Float.valueOf(hashMap.get("voice_time").toString());
						// 总流量
						Float data = hashMap.get("data") == null ? null : Float.valueOf(hashMap.get("data").toString());

						String remark1 = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();

						Combo combo = new Combo();
						combo = comboService.getComboBySync_id(id);
						if (combo == null) {
							combo = comboService.getComboByName(combo_name);
							if (null == combo) {
								combo = new Combo();
								combo.setCombo_code("");
								combo.setCombo_name(combo_name);
								combo.setVoice_time(voice_time);
								combo.setData(data);
								
								combo.setMonth_fee(month_fee);//0 每月价格(元)
								combo.setFlag(flag1);//标志, 1=正常, 0=删除(对新用户不可选)
								combo.setTelco(telco);//运营商
								combo.setFeetype_id(101);//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
								combo.setOp_id(0L);//操作员id 
								combo.setSubco_no(201L);//分公司, 0=总部定义, 大于0=分公司自定义
								combo.setSim_type(2);//卡类型, 1=语音卡, 2=流量卡
								
								combo.setSync_id(id);
								combo.setRemark(remark1);
								try {
									comboService.save(combo);
								} catch (Exception e) {
									SystemConst.E_LOG.error("保存套餐出错",e);
								}
								System.out.println("套餐" + combo_name + "保存成功！");
							} else {
								combo.setCombo_code("");
								combo.setCombo_name(combo_name);
								combo.setFlag(flag1);
								combo.setMonth_fee(month_fee);
								combo.setTelco(telco);
								combo.setVoice_time(voice_time);
								combo.setData(data);
								combo.setSync_id(id);
								combo.setRemark(remark1);
								
								combo.setFeetype_id(101);//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
								combo.setOp_id(0L);//操作员id 
								combo.setSubco_no(201L);//分公司, 0=总部定义, 大于0=分公司自定义
								combo.setSim_type(2);//卡类型, 1=语音卡, 2=流量卡
								
								comboService.update(combo);
								System.out.println("套餐" + combo_name + "更新成功！");
							}
						} else {
							combo = new Combo();
							combo.setCombo_code("");
							combo.setCombo_name(combo_name);
							combo.setFlag(flag1);
							combo.setMonth_fee(month_fee);
							combo.setTelco(telco);
							combo.setVoice_time(voice_time);
							combo.setData(data);
							combo.setOp_id(1L);
							combo.setSync_id(id);
							combo.setRemark(remark1);
							combo.setFeetype_id(101);//计费类型, 系统值3100, 1=服务费, 2=SIM卡, 3=盗抢险, 4=网上查车, 101=前装服务费
							combo.setOp_id(0L);//操作员id 
							combo.setSubco_no(201L);//分公司, 0=总部定义, 大于0=分公司自定义
							combo.setSim_type(2);//卡类型, 1=语音卡, 2=流量卡
							try {
								comboService.save(combo);
							} catch (Exception e) {
								SystemConst.E_LOG.error("保存套餐出错",e);
							}
							System.out.println("套餐" + combo_name + "保存成功！");
						}
					}
				} else {
					System.out.println("同步套餐信息没有数据！");
				}
			} else {
				System.out.println("同步套餐信息没有数据！");
			}

		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println("同步套餐信息获取数据" + strResult);
		}
		return flag;
	}
	
	/**
	 * 同步绑定信息处理方法 
	 * @param start_date	开始时间
	 * @param end_date		结束时间
	 * @param url			同步url
	 * @param flag			是否成功
	 * @param i				统计次数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SystemException
	 */
	private boolean sycnBandinfoHandler(Long start_date, Long end_date, String url, boolean flag, int i) throws ClientProtocolException, IOException, SystemException {
		url = getUrl(url, start_date, end_date);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String strResult = "";

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		JSONObject jobj = new JSONObject();
		nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);
			System.out.println("第" + i + "次:" + sdf.format(new Date()) + SystemConst.HM_BANDINFO + " 返回内容" + sobj.toString());
			if (sobj.containsKey("data")) {
				flag = true;
				String datas = sobj.getString("data");
				if (null != datas && !datas.toString().equals("null")) {
					List<HashMap<String, Object>> results = parseJSON2List(datas);
					List<Preload>  sims = new ArrayList<Preload>();
					for (HashMap<String, Object> hashMap : results) {
						String barcode = hashMap.get("bar_code") == null ? null : hashMap.get("bar_code").toString();
						if (StringUtils.isNullOrEmpty(barcode)) {
							System.out.println("本地库中不存在barcode=" + barcode + "的sim信息");
							continue;
						}

						// Long sn = hashMap.get("sn") == null ? null :
						// Long.valueOf(hashMap.get("sn").toString());改字段没有用到
						String vin = hashMap.get("vin") == null ? null : hashMap.get("vin").toString();
						
						if(StringUtils.isNullOrEmpty(vin)){
							System.out.println("vin值为:" + vin);
							continue;
						}
						
						String scan_time_s = (String) hashMap.get("scan_time");
						Date scan_time = TimeHelper.getDate(scan_time_s);
						
						/**
						 * 根据给定的 barcode 在 t_ba_sim 表中查找有没有数据 ，barcode值唯一
						 * 有：update； vin 扫描时间字段 无：报错日志
						 */
						Preload sim = preloadService.getPreloadByBarCode(barcode);
						if (sim == null) {
							System.out.println("本地库中不存在barcode=" + barcode + "的sim信息! vin:::" + vin);
							continue;
						}
						sim.setVin(vin);
						sim.setScan_time(scan_time);
						sims.add(sim);
						// 存在则进行更新操作
						preloadService.update(sim);
						System.out.println("sim更新成功!barcode:"+barcode);
					}
				} else {
					System.out.println("同步车辆绑定信息没有数据！");
				}
			} else {
				System.out.println("同步车辆绑定信息没有数据！");
			}

		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println("同步车辆绑定信息获取数据" + strResult);
		}
		return flag;
	}
	
	
	/**
	 * 同步车辆基础信息资料的处理方法
	 * @param start_date	开始时间
	 * @param end_date		结束时间
	 * @param url			同步url
	 * @param flag			是否成功
	 * @param i				统计次数
	 * @return
	 * @throws SystemException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private boolean sycnBasedataHandler(Long start_date, Long end_date, String url, boolean flag, int i) throws SystemException, ClientProtocolException, IOException {
		url = getUrl(url, start_date, end_date);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String strResult = "";

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		JSONObject jobj = new JSONObject();
		nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);

			System.out.println("第" + i + "次:" + sdf.format(new Date()) + SystemConst.HM_BASEDASTA + " 返回内容" + sobj.toString());

			if (sobj.containsKey("data")) {
				flag = true;
				String datas = sobj.getString("data");
				if (null != datas && !datas.toString().equals("null")) {
					List<HashMap<String, Object>> results = parseJSON2List(datas);
					for (HashMap<String, Object> hashMap : results) {

						// k++;
						// System.out.println("第" + k + "次");
						
						/**
						 * 
						 */

						// 车辆识别号码/车架号,17位
						String vin = hashMap.get("vin") == null ? null : hashMap.get("vin").toString();
						if (vin == null || "".equals(vin)) {
							System.out.println("海马接口传入的vin码为空!");
							continue;
						}

						// 根据VIN查询 t_ba_sim 是否存在卡 没有则不进行下面的操作：
						Preload sim_temp = preloadService.getPreloadByVin(vin);// 存在返回true
																				// 否则false
						if (sim_temp == null) {
							System.out.println("本地库中不存在vin=" + vin + "的sim信息! ");
							continue;
						}

						// 客戶ID
						Long customer_id = hashMap.get("customer_id") == null ? null : Long.valueOf(hashMap.get("customer_id").toString());
						// 客戶名稱
						String customer_name = hashMap.get("customer_name") == null ? "" : hashMap.get("customer_name").toString();
						// 地址
						String address = hashMap.get("address") == null ? "" : hashMap.get("address").toString();
						// 证件类型,1=居民身份证,2=士官证,3=学生证,4=驾驶证,5=护照,6=港澳通行证,99=其它
						Integer idtype = hashMap.get("idtype") == null ? 1 : Integer.valueOf(hashMap.get("idtype").toString());
						// 身份证号
						String id_no = hashMap.get("id_no") == null ? "" : hashMap.get("id_no").toString();
						// 性别,1=男,2=女
						Integer sex = hashMap.get("sex") == null ? 1 : Integer.valueOf(hashMap.get("sex").toString());
						// 邮箱
						String email = hashMap.get("email") == null ? "" : hashMap.get("email").toString();

						// 车辆颜色
						String color = hashMap.get("color") == null ? "" : hashMap.get("color").toString();
						// 发动机号
						String engine_no = hashMap.get("engine_no") == null ? "" : hashMap.get("engine_no").toString();
						// 生产厂家
						String factory = hashMap.get("factory") == null ? "" : hashMap.get("factory").toString();
						// 车辆生产日期
						String production_date_s = hashMap.get("production_date") == null ? null : (String) hashMap.get("production_date");
						Date production_date = TimeHelper.getDate(production_date_s);

						// 购车日期
						String buy_date_s = hashMap.get("buy_date") == null ? null : (String) hashMap.get("buy_date");
						Date buy_date = TimeHelper.getDate(buy_date_s);

						// 载重/乘客数
						Integer vload = hashMap.get("vload") == null ? null : Integer.valueOf(hashMap.get("vload").toString());

						// 车辆4s店ID,由海马给出定义列表 -->这个没有值
						Long s_id = hashMap.get("4s_id") == null ? null : Long.valueOf(hashMap.get("4s_id").toString());

						// 商业保险公司ID,由海马给出定义列表
						Long insurance_id = hashMap.get("insurance_id") == null ? null : Long.valueOf(hashMap.get("insurance_id").toString());

						// 车牌
						// plaate_no:车牌号 文档中的内容 ??????????
						// -->文档有误返回实际值 plate_no键
						String plate_no1 = hashMap.get("plate_no") == null ? "" : hashMap.get("plate_no").toString();

						// 车载电话
						// call_letter:终端条形码 接口文档 -> 文档有误 唯一值
						String call_letter = hashMap.get("call_letter") == null ? "" : hashMap.get("call_letter").toString();

						// 套餐
						Long pack_id = hashMap.get("pack_id") == null ? null : Long.valueOf(hashMap.get("pack_id").toString());

						// 入网时间 这个值不变
//						String create_date_s = hashMap.get("create_date") == null ? null : (String) hashMap.get("create_date");
						String create_date_s = hashMap.get("create_time") == null ? null : (String) hashMap.get("create_time");
						Date create_date = TimeHelper.getDate(create_date_s);
						
						if(create_date == null){
							create_date = DateUtil.getNow();
						}

						// 服务开通时间/计费时间,停用后可能又开通,时间会不同
						String service_date_s = hashMap.get("service_date") == null ? null : (String) hashMap.get("service_date");
						Date service_date = TimeHelper.getDate(service_date_s);
						
						if(service_date == null){
							service_date = create_date;
						}

						// 停用时间
						String stop_date_s = hashMap.get("stop_date") == null ? null : (String) hashMap.get("stop_date");
						Date stop_date = TimeHelper.getDate(stop_date_s);

						//
						Insurer insurer = insurerService.getInsurerBySync_id(insurance_id);// 如果没有该id需要先调用保险信息的接口
						Integer i_id = 0;
						if (null != insurer) {
							i_id = insurer.getIc_id();
						} else {
							// 没有该保险信息则先保存该保险信息
						}

						Long s_no = null;// 4s店ID为空
						//
						if (s_id != null && !"".equals(s_id)) {
							CommonCompany com = ldap.getSyncCommonCompany(s_id.toString());// 4s店
							s_no = com == null ? null : Long.valueOf(com.getCompanyno());
						}

						// 新车没有车牌号 需要根据车载电话 ，设备更换?
						// Long vehicle_id =
						// vehicleService.getIdByPlate(plate_no1);//
						// 根据车牌码查询车辆信息 新车没车牌
						Long vehicle_id = vehicleService.getIdByVin(vin);// 根据vin码查询
						Vehicle vehicle = vehicleService.get(Vehicle.class, vehicle_id);// 车辆基础资料

						if (null == vehicle) {

							// 查询是否已经有了这个车载电话 cell_letter
							if (unitService.getUnitByCL(call_letter) != null) {
								System.out.println("车载号码[" + call_letter + "]在本地终端信息库中已经存在!");
								continue;
							}

							vehicle = new Vehicle();
							// 海马暂时默认为201
							vehicle.setSubco_no(201L);// LDAP分公司根节点ID, // 对应我们的分公司, // 内部机构
							vehicle.setPlate_no(plate_no1);// 车牌号码
							vehicle.setPlate_color(1);// 车牌颜色,
														// 系统值2110,
														// 1=蓝, 2=黄,
														// 3=黑, 4=白,
														// 9=其他
							vehicle.setVehicle_type(1);// 车辆类型,
														// 系统值2030,
														// 1=小型轿车
							vehicle.setVehicle_status(0);// 车辆状态,
															// 系统值2060,
															// 0=正常,
															// 1=故障,
															// 2=维修,
															// 3=警情
							vehicle.setVin(vin);// 车辆识别号码/车架号, 17位
							vehicle.setColor(color);// 车辆颜色
							vehicle.setEngine_no(engine_no);// 发动机编号
							vehicle.setFactory(factory);// 生产厂家
							vehicle.setProduction_date(production_date);// 生产日期
							vehicle.setBuy_date(buy_date);// 购买日期
							vehicle.setFlag(1);//
							vehicle.setVload(vload);// 载重/乘客数
							vehicle.setOp_id(1L);// 操作人员
							vehicle.setId_4s(s_no);// 车辆4s店,
													// 对应前装的4s店机构ID
							vehicle.setInsurance_id(i_id);// 商业保险公司,与综合盗抢险不同
							try {
								vehicleService.save(vehicle);
							} catch (Exception e) {
								SystemConst.E_LOG.error("保存车辆信息出错",e);
							}

							Customer customer = new Customer();
							// 分公司代码 201 海马
							customer.setSubco_no(201L);//
							customer.setCust_type(0);// 客户类型,
														// 0=私家车客户,
														// 1=集团客户,
														// 2=担保公司
							customer.setCustomer_name(customer_name);
							customer.setCustco_code("");// 'LDAP客户子机构代码,
														// 用于集团客户车辆的归属关系,
														// 无填''0'',
														// 每级2位字符',
							customer.setAddress(address);
							customer.setCustco_no(0L);// LDAP客户根节点ID,
														// 集团客户有效 ;
														// 默认给什么值
														// 不能为空
							customer.setVip(1);//
							customer.setIdtype(idtype);
							customer.setSex(sex);
							customer.setEmail(email);
							customer.setTrade(0);// '所属行业(网上查车行业版本),
													// 系统值2040,
													// 0=私家车, 1=物流车,
													// 2=出租车,
													// 3=混凝土',
							customer.setFlag(1);// '标志, 0=未审核,
												// 1=审核通过, 2=删除',
							customer.setPay_model(0);// 预留, 付费方式,
														// 冗余,
														// 系统值3050,
														// 0=托收,
														// 1=现金,
														// 2=转账,
														// 3=刷卡
							customer.setOp_id(1L);// 操作员ID
							try {
								customerService.save(customer);
							} catch (Exception e) {
								SystemConst.E_LOG.error("保存客户信息出错",e);
							}

							// 客户和车辆关联表
							CustVehicle cvmodel = new CustVehicle();
							cvmodel.setCustomer_id(customer.getCustomer_id());
							cvmodel.setVehicle_id(vehicle.getVehicle_id());
							try {
								custVehicleService.save(cvmodel);
							} catch (Exception e) {
								SystemConst.E_LOG.error("保存客户车辆关联信息出错",e);
							}

							// 终端信息实体
							Unit unit = new Unit();
							unit.setVehicle_id(vehicle.getVehicle_id());// 车辆
							unit.setCustomer_id(customer.getCustomer_id());// 客户ID
							unit.setCall_letter(call_letter);// 车载号码 //
																// ，是唯一索引，本身具有约束性，如果该字段已经存在相同的只的话，就不能再插入该数据了，当然也插不进去，比普通索引快。
							unit.setSubco_no(201L);// 分公司
							unit.setUnittype_id(1033l);// 车台类型,
													// 关联t_ba_unittype,
													// 考虑商品名称分类太多,
													// 操作员录入复杂,
													// 故沿用老系统
							unit.setMode(3);// 通信模式, 1=短信, 2=数据流量,
											// 3=流量+短信
							unit.setData_node(0);// 流量通道网关编号, 无填0,
													// 预留,
													// 见t_sys_node,
													// 流量网关节点,
													// 界面上需显示网关别名
							unit.setSms_node(5);// 短信通道网关编号, 无填0,
												// 多个短信通道时需要,
												// 见t_sys_node,
												// 短信网关节点,
												// 界面上需显示网关别名
							unit.setTelecom(3);// SIM卡运营商, 1=移动,
												// 2=联通, 3=电信
							unit.setSim_type(1);// SIM卡类型, 1=自带卡,
												// 2=公司卡, 3=总部卡
							unit.setTrade(0);// 所属行业(网上查车行业版本),
												// 系统值2040, 0=私家车,
												// 1=物流车, 2=出租车,
												// 3=混凝土, 默认与客户相同,
												// 考虑客户有多个行业情况

							unit.setPay_model(1);// '付费方式,
													// 集团客户可能每车不同,
													// 系统值3050,
													// 0=托收, 1=现金,
													// 2=转账, 3=刷卡',
							unit.setReg_status(0);// 入网状态, 系统值2050,
													// 0=在网, 1=离网,
													// 2=欠费离网,
													// 3=非入网,
													// 4=研发测试,
													// 5=电工测试,
													// 6=重新开通, 7=不开通
							
							unit.setCreate_date(create_date);
							unit.setService_date(service_date);
							unit.setFlag(0);

							try {
								unitService.save(unit);
							} catch (Exception e) {
								SystemConst.E_LOG.error("保存车台信息出错",e);
							}

							Barcode model = new Barcode();
							model.setContent(sim_temp.getBarcode());
							model.setType(1);// 1终端条形码2导航条形码
							model.setUnit_id(unit.getUnit_id());
							try {
								barcodeService.save(model);
							} catch (Exception e) {
								SystemConst.E_LOG.error("保存box信息出错",e);
							}

							Preload sim = preloadService.getPreloadByCl(call_letter);
							if (sim != null) {
								sim.setVin(vin);// 车架号
								sim.setEngine_no(engine_no);// 引擎编号
								sim.setColor(color);// 车辆颜色
								sim.setProduction_date(production_date);// 生产日期
								sim.setUnit_id(unit.getUnit_id());// 终端编码
								sim.setUnittype_id(1033L);
								preloadService.update(sim);
							}

							System.out.println("vin码：" + vin + "的车辆基础资料同步保存成功!");

						} else {
							vehicle.setEngine_no(engine_no);
							vehicle.setColor(color);
							vehicle.setFactory(factory);
							vehicle.setProduction_date(production_date);
							vehicle.setBuy_date(buy_date);
							vehicle.setVload(vload);
							vehicle.setId_4s(s_no);
							vehicle.setVin(vin);
							vehicle.setInsurance_id(i_id);
							vehicleService.update(vehicle);

							Unit unit = unitService.getUnitByCL(call_letter);

							if (unit != null ) {
//								unit.setCall_letter(call_letter);
								unit.setCreate_date(create_date);//该值不更新
								unit.setService_date(service_date);
								unit.setStop_date(stop_date);
								unit.setPack_id(pack_id);
								unit.setUnittype_id(1033L);// 车台类型,
								unit.setMode(3);// 通信模式, 1=短信, 2=数据流量, // 3=流量+短信
								unit.setData_node(0);// 流量通道网关编号, 无填0, // 预留, // 见t_sys_node, // 流量网关节点, // 界面上需显示网关别名
								unit.setSms_node(5);// 短信通道网关编号,
								unit.setFlag(0);
								unitService.update(unit);
								
								List<Barcode> barcodeList = barcodeService.getByUnit_id(unit.getUnit_id());
								if (barcodeList != null && barcodeList.size() > 0) {
									Barcode model = barcodeList.get(0);
									model.setContent(sim_temp.getBarcode());
									model.setType(1);// 1终端条形码2导航条形码
									model.setUnit_id(unit.getUnit_id());
									barcodeService.update(model);
								}
								Customer cus = customerService.get(Customer.class, unit.getCustomer_id());
								cus.setCustomer_name(customer_name);
								cus.setAddress(address);
								cus.setId_no(id_no);
								cus.setIdtype(idtype);
								cus.setSex(sex);
								cus.setEmail(email);
								customerService.update(cus);

								Preload sim = preloadService.getPreloadByCl(call_letter);
								if (sim != null) {
									sim.setVin(vin);
									sim.setEngine_no(engine_no);
									sim.setColor(color);
									sim.setProduction_date(production_date);
									sim.setUnit_id(unit.getUnit_id());
									sim.setUnittype_id(1033L);
									preloadService.update(sim);
								}
							}
							System.out.println("vin码：" + vin + "的车辆基础资料同步更新成功!");
						}
					}
				} else {
					System.out.println("同步车辆基本信息没有数据！");
				}
			} else {
				System.out.println("同步车辆基本信息没有数据！");
			}

		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println("同步车辆基本信息获取数据" + strResult);
		}
		return flag;
	}

	
	
	/**
	 * 同步4S店信息处理方法
	 * @param start_date	开始时间
	 * @param end_date		结束时间
	 * @param url			同步url
	 * @param flag			是否成功
	 * @param i				统计次数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SystemException
	 */
	private boolean sycnCompanyHandler(Long start_date, Long end_date, String url, boolean flag, int i) throws ClientProtocolException, IOException, SystemException {
		System.err.println("SyncBaseDataServiceImpl 这是个测试程序");
		
		url = getUrl(url, start_date, end_date);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String strResult = "";

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		JSONObject jobj = new JSONObject();
		nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);

			System.out.println("第" + i + "次:" + sdf.format(new Date()) + SystemConst.HM_4S + " 返回内容" + sobj.toString());

			if (sobj.containsKey("data")) {
				flag = true;
				String datas = sobj.getString("data");
				if (null != datas && !datas.toString().equals("null")) {
					List<HashMap<String, Object>> results = parseJSON2List(datas);
					for (HashMap<String, Object> hashMap : results) {
						CommonCompany commonCompany = new CommonCompany();
						String id = hashMap.get("company") == null ? null : hashMap.get("company").toString();// 同步ID
						// 机构名称，简称，用于显示
						String companyname = hashMap.get("companyname").toString();
						// 机构层级
						String companylevel = hashMap.get("companylevel") == null ? null : hashMap.get("companylevel").toString();
						// 父机构id
						String parentcompany = hashMap.get("parentcompany") == null ? null : hashMap.get("parentcompany").toString();
						// 排序
						String order = hashMap.get("order") == null ? null : hashMap.get("order").toString();
						// 机构管理人id
						String opid = hashMap.get("opid") == null ? null : hashMap.get("opid").toString();
						// 地址
						String address = hashMap.get("address") == null ? null : hashMap.get("address").toString();
						// 成立时间
						String time = hashMap.get("time") == null ? null : hashMap.get("time").toString();
						// 中文全名
						String cnfullname = hashMap.get("cnfullname") == null ? null : hashMap.get("cnfullname").toString();
						// 机构code
						String companycode = hashMap.get("companycode") == null ? null : hashMap.get("companycode").toString();
						// 备注
						String remark = hashMap.get("remark") == null ? null : hashMap.get("remark").toString();
						// 经纬度
						String coordinates = hashMap.get("coordinates") == null ? null : hashMap.get("coordinates").toString();
						// 电话(维修电话)
						String phone = hashMap.get("phone") == null ? null : hashMap.get("phone").toString();
						// 电话2(预约电话)
						String phone2 = hashMap.get("phone2") == null ? null : hashMap.get("phone2").toString();
						// 主营业务
						String major = hashMap.get("major") == null ? null : hashMap.get("major").toString();
						// 资质
						String aptitude = hashMap.get("aptitude") == null ? null : hashMap.get("aptitude").toString();
						// 原图URL
						String image = hashMap.get("image") == null ? null : hashMap.get("image").toString();
						// 缩略图URL
						String image2 = hashMap.get("image2") == null ? null : hashMap.get("image2").toString();

						parentcompany = "201";//

						commonCompany = ldap.getSyncCommonCompany(id);// 查询本地库中的同步4s店

						if (null == commonCompany) {
							commonCompany = ldap.getCompanyByname(companyname);
							if (commonCompany == null) {
								commonCompany = new CommonCompany();
								commonCompany.setAddress(address);
								commonCompany.setCnfullname(cnfullname);
								commonCompany.setCompanycode(companycode);
								commonCompany.setCompanylevel(companylevel);
								commonCompany.setCompanyname(companyname);
								// commonCompany.setCompanyno(companyno.toString());
								commonCompany.setOrder(order);
								commonCompany.setParentcompanyno(parentcompany);
								commonCompany.setRemark(remark);
								commonCompany.setTime(time);
								commonCompany.setOpid(opid);
								commonCompany.setCompanytype(5 + "");
								commonCompany.setCoordinates(coordinates);
								commonCompany.setPhone(phone);
								commonCompany.setPhone2(phone2);
								commonCompany.setMajor(major);
								commonCompany.setAptitude(aptitude);
								commonCompany.setImage(image);
								commonCompany.setImage2(image2);
								commonCompany.setSno(id);// 同步ID

								// String parentcompanyno =
								// company.getParentcompanyno();
								List<CommonCompany> list = companyDao.getCompanysByPid(parentcompany);
								CommonCompany pcompany = companyDao.getById(parentcompany);
								int codeint = 0;
								if (list.size() > 0) {
									for (CommonCompany c : list) {
										if (StringUtils.isBlank(c.getCompanycode())) {
											continue;
										}
										int swap = 0;
										if (c.getCompanycode().length() > 2) {
											swap = Integer.valueOf(c.getCompanycode().substring(c.getCompanycode().length() - 2));
										} else {
											swap = Integer.valueOf(c.getCompanycode());
										}
										if (swap > codeint) {
											codeint = swap;
										}
									}
								}
								companycode = String.valueOf((codeint + 1));
								if (companycode.length() == 1) {
									companycode = "0" + companycode;
								}
								String pcompanycode = "";
								if (StringUtils.isNotBlank(pcompany.getCompanycode())) {
									pcompanycode = pcompany.getCompanycode();
								}
								commonCompany.setCompanycode(pcompanycode + companycode);

								ldap.add(commonCompany);
								System.out.println("4S店：" + companyname + "同步保存成功！");
							} else {
								commonCompany.setAddress(address);// 地址
								commonCompany.setCnfullname(cnfullname);// 中文全写
								commonCompany.setCompanycode(companycode);// 机构ID
								commonCompany.setCompanylevel(companylevel);// 机构层级
								commonCompany.setCompanyname(companyname);// 机构名称
								// commonCompany.setCompanyno(companyno.toString());
								commonCompany.setOrder(order);// 排序
								commonCompany.setParentcompanyno(parentcompany);// 父类机构
								commonCompany.setRemark(remark);// 备注
								commonCompany.setTime(time);// 成立时间
								commonCompany.setOpid(opid);// //机构管理人id

								commonCompany.setCompanytype(5 + "");// 机构类型,1:一般部门,2:营业处,3:投资公司,4:仓库,10外部机构

								commonCompany.setCoordinates(coordinates);
								commonCompany.setPhone(phone);
								commonCompany.setPhone2(phone2);
								commonCompany.setMajor(major);
								commonCompany.setAptitude(aptitude);
								commonCompany.setImage(image);
								commonCompany.setImage2(image2);
								commonCompany.setSno(id);

								companyDao.modifyCompany(commonCompany);
								System.out.println("4SSSSSSSSSSSSSS店：" + companyname + "同步更新成功！");
							}
						} else {
							commonCompany = new CommonCompany();
							commonCompany.setAddress(address);
							commonCompany.setCnfullname(cnfullname);
							commonCompany.setCompanycode(companycode);
							commonCompany.setCompanylevel(companylevel);
							commonCompany.setCompanyname(companyname);
							// commonCompany.setCompanyno(companyno.toString());
							commonCompany.setOrder(order);
							commonCompany.setParentcompanyno(parentcompany);
							commonCompany.setRemark(remark);
							commonCompany.setTime(time);
							commonCompany.setOpid(opid);
							commonCompany.setCompanytype(5 + "");
							commonCompany.setCoordinates(coordinates);
							commonCompany.setPhone(phone);
							commonCompany.setPhone2(phone2);
							commonCompany.setMajor(major);
							commonCompany.setAptitude(aptitude);
							commonCompany.setImage(image);
							commonCompany.setImage2(image2);
							commonCompany.setSno(id);
							companyDao.modifyCompany(commonCompany);
							System.out.println("4S店：" + companyname + "同步更新成功！");
						}
					}
				} else {
					System.out.println("同步同步4s店信息没有数据！");
				}
			} else {
				System.out.println("同步4s店信息没有数据！");
			}

		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println("同步4s信息获取数据" + strResult);
		}
		return flag;
	}
	
	/**
	 * 同步保险公司信息
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private boolean sycnInsurerHandler(Long start_date, Long end_date, String url, boolean flag, int i) throws ClientProtocolException, IOException {
		url = getUrl(url, start_date, end_date);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String strResult = "";

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		JSONObject jobj = new JSONObject();
		nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);

			System.out.println("第" + i + "次:" + sdf.format(new Date()) + SystemConst.HM_INSURANCE + " 返回内容" + sobj.toString());

			if (sobj.containsKey("data")) {
				flag = true;
				String datas = sobj.getString("data");
				if (null != datas && !datas.toString().equals("null")) {
					List<HashMap<String, Object>> results = parseJSON2List(datas);
					for (HashMap<String, Object> hashMap : results) {
						// 保险公司ID 同步ID
						Long id = hashMap.get("ic_id") == null ? null : Long.valueOf(hashMap.get("ic_id").toString());
						// 联系电话
						String tel = hashMap.get("tel") == null ? "" : hashMap.get("tel").toString();

						// 多出来的
						// String tel_400 = hashMap.get("tel_400")
						// == null ? "" :
						// hashMap.get("tel_400").toString();
						// String tel_800 = hashMap.get("tel_800")
						// == null ? "" :
						// hashMap.get("tel_800").toString();

						// 名称简称,下拉选择名称
						String s_name = hashMap.get("s_name") == null ? "" : hashMap.get("s_name").toString();
						// 中文全称
						String c_name = hashMap.get("c_name") == null ? "" : hashMap.get("c_name").toString();
						// 备注
						String remark = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();

						Insurer insurer = new Insurer();
						insurer = insurerService.getInsurerBySync_id(id);
						if (insurer == null) {
							insurer = insurerService.getInsurerByName(c_name);
							if (null == insurer) {
								insurer = new Insurer();
								insurer.setTel(tel);
								insurer.setS_name(s_name);
								insurer.setRemark(remark);
								insurer.setSync_id(id);
								insurer.setTel_400("");
								insurer.setTel_800("");
								insurer.setC_name(c_name);
								try {
									insurerService.save(insurer);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println("保险公司" + c_name + "保存成功！");
							} else {
								insurer.setTel(tel);
								insurer.setS_name(s_name);
								insurer.setRemark(remark);
								insurer.setTel_400("");
								insurer.setTel_800("");
								insurer.setSync_id(id);
								insurer.setC_name(c_name);
								insurerService.update(insurer);
								System.out.println("保险公司" + c_name + "更新成功！");
							}
						} else {
							insurer.setTel(tel);
							insurer.setS_name(s_name);
							insurer.setRemark(remark);
							insurer.setTel_400("");
							insurer.setTel_800("");
							insurer.setSync_id(id);
							insurer.setC_name(c_name);
							insurerService.update(insurer);
							System.out.println("保险公司" + c_name + "更新成功！");
						}
					}
				} else {
					System.out.println("保险公司信息没有数据！");
				}
			} else {
				System.out.println("保险公司信息没有数据！");
			}

		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println("保险公司信息获取数据" + strResult);
		}
		return flag;
	}
	
	/**
	 * 同步车型处理
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private boolean sycnModelHandler(Long start_date, Long end_date, String url, boolean flag, int i) throws ClientProtocolException, IOException {
		url = getUrl(url, start_date, end_date);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String strResult = "";
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		JSONObject jobj = new JSONObject();
		nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);
			
			System.out.println("第" + i + "次:" + sdf.format(new Date()) + SystemConst.HM_MODEL + " 返回内容" + sobj.toString());

			if (sobj.containsKey("data")) {
				flag = true;
				String datas = sobj.getString("data");
				if (null != datas && !datas.toString().equals("null")) {
					List<HashMap<String, Object>> results = parseJSON2List(datas);
					for (HashMap<String, Object> hashMap : results) {
						// 车型ID--同步ID
						Long id = hashMap.get("model_id") == null ? null : Long.valueOf(hashMap.get("model_id").toString());
						// 车系ID
						Long series_id = hashMap.get("series_id") == null ? null : Long.valueOf(hashMap.get("series_id").toString());
						// 车型名称
						String model_name = hashMap.get("model_name") == null ? null : hashMap.get("model_name").toString();
						// 车型标准油耗
						Float standart_oil = hashMap.get("standart_oil") == null ? null : Float.valueOf(hashMap.get("standart_oil").toString());
						// 排量
						Float displacement = hashMap.get("displacement") == null ? null : Float.valueOf(hashMap.get("displacement").toString());
						// 车型图片URL路径
						String img = hashMap.get("img") == null ? "" : hashMap.get("img").toString();
						// 备用图片URL路径(IPAD)
						String img1 = hashMap.get("img1") == null ? "" : hashMap.get("img1").toString();
						// 车型年份
						String car_type_year = hashMap.get("car_type_year") == null ? "" : hashMap.get("car_type_year").toString();
						// 车型级别
						Integer car_type_level = hashMap.get("car_type_level") == null ? null : Integer.valueOf(hashMap.get("car_type_level").toString());
						// 是否涡轮增压
						String is_turbo = hashMap.get("is_turbo") == null ? "" : hashMap.get("is_turbo").toString();
						// 变速箱类型
						String gearbox_type = hashMap.get("gearbox_type") == null ? "" : hashMap.get("gearbox_type").toString();
						// 档位
						Integer gear_num = hashMap.get("gear_num") == null ? null : Integer.valueOf(hashMap.get("gear_num").toString());
						// 车门
						Integer car_door_num = hashMap.get("car_door_num") == null ? null : Integer.valueOf(hashMap.get("car_door_num").toString());

						// 车身结构
						String car_bodywork = hashMap.get("car_bodywork") == null ? "" : hashMap.get("car_bodywork").toString();

						Integer car_long = hashMap.get("car_long") == null ? null : Integer.valueOf(hashMap.get("car_long").toString());
						Integer car_width = hashMap.get("car_width") == null ? null : Integer.valueOf(hashMap.get("car_width").toString());
						Integer car_height = hashMap.get("car_height") == null ? null : Integer.valueOf(hashMap.get("car_height").toString());
						// 最大速度
						Float car_max_speed = hashMap.get("car_max_speed") == null ? null : Float.valueOf(hashMap.get("car_max_speed").toString());
						// 平均油耗
						Float avg_oil = hashMap.get("avg_oil") == null ? null : Float.valueOf(hashMap.get("avg_oil").toString());
						// 备注
						String remark = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();
						// 是否支持中控控制,0=√,1=*,2=O,3=其它,4=支持,5=不支持
						Integer center_control = hashMap.get("center_control") == null ? null : Integer.valueOf(hashMap.get("center_control").toString());
						// 是否支持喇叭控制,:0=√,1=*,2=O,3=其它,4=支持,5=不支持
						Integer horn_control = hashMap.get("horn_control") == null ? null : Integer.valueOf(hashMap.get("horn_control").toString());
						// 是否支持车窗控制,0=√,1=*,2=O,3=其它,4=支持,5=不支持
						Integer window_control = hashMap.get("window_control") == null ? null : Integer.valueOf(hashMap.get("window_control").toString());

						// light_control
						// 是否支持危险灯控制,0=√,1=*,2=O,3=其它,4=支持,5=不支持
						Integer light_control = hashMap.get("light_control") == null ? null : Integer.valueOf(hashMap.get("light_control").toString());

						// 油箱容量,单位L,默认50L
						Integer oil_volume = hashMap.get("oil_volume") == null ? null : Integer.valueOf(hashMap.get("oil_volume").toString());
						// 是否有效
						Integer is_valid = hashMap.get("is_valid") == null ? null : Integer.valueOf(hashMap.get("is_valid").toString());

						Model model = modelService.getModelBySync_id(id);// 车型
						Series series = seriesService.getSeriesBySync_id(series_id);// 车系
						if (series != null) {
							if (model == null) {
								if (modelService.isExist(model_name)) {
									model = modelService.getModelByName(model_name);
									model.setAve_oil(avg_oil);
									model.setCar_bodywork(car_bodywork);
									model.setCar_height(car_height);
									model.setCar_long(car_long);
									model.setCar_max_speed(car_max_speed);
									model.setCar_type_level(car_type_level);
									model.setCar_type_year(car_type_year);
									model.setCar_width(car_width);
									model.setCarDoorNum(car_door_num);
									model.setCenter_control(center_control);
									model.setDisplacement(displacement);
									model.setGearbox_type(gearbox_type);
									model.setGearNum(gear_num);
									model.setHorn_control(horn_control);
									model.setImg(img);
									model.setImg1(img1);
									model.setIs_turbo(is_turbo);
									model.setName(model_name);
									model.setOil_volume(oil_volume);
									model.setRemark(remark);
									model.setStandart_oil(standart_oil);
									model.setWindow_control(window_control);
									model.setSeriesId(series.getSeries_id());
									model.setSync_id(id);
									model.setIs_alid(is_valid);
									model.setLight_control(light_control);
									modelService.update(model);
									System.out.println("车型：" + model_name + "同步更新成功！");
								} else {
									model = new Model();
									model.setAve_oil(avg_oil);
									model.setCar_bodywork(car_bodywork);
									model.setCar_height(car_height);
									model.setCar_long(car_long);
									model.setCar_max_speed(car_max_speed);
									model.setCar_type_level(car_type_level);
									model.setCar_type_year(car_type_year);
									model.setCar_width(car_width);
									model.setCarDoorNum(car_door_num);
									model.setCenter_control(center_control);
									model.setDisplacement(displacement);
									model.setGearbox_type(gearbox_type);
									model.setGearNum(gear_num);
									model.setHorn_control(horn_control);
									model.setImg(img);
									model.setImg1(img1);
									model.setIs_turbo(is_turbo);
									model.setName(model_name);
									model.setOil_volume(oil_volume);
									model.setRemark(remark);
									model.setStandart_oil(standart_oil);
									model.setWindow_control(window_control);
									model.setSeriesId(series.getSeries_id());
									model.setSync_id(id);
									model.setLight_control(light_control);
									try {
										modelService.save(model);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println("车型：" + model_name + "同步保存成功！");
								}
							} else {
								model.setAve_oil(avg_oil);
								model.setCar_bodywork(car_bodywork);
								model.setCar_height(car_height);
								model.setCar_long(car_long);
								model.setCar_max_speed(car_max_speed);
								model.setCar_type_level(car_type_level);
								model.setCar_type_year(car_type_year);
								model.setCar_width(car_width);
								model.setCarDoorNum(car_door_num);
								model.setCenter_control(center_control);
								model.setDisplacement(displacement);
								model.setGearbox_type(gearbox_type);
								model.setGearNum(gear_num);
								model.setHorn_control(horn_control);
								model.setImg(img);
								model.setImg1(img1);
								model.setIs_turbo(is_turbo);
								model.setName(model_name);
								model.setOil_volume(oil_volume);
								model.setRemark(remark);
								model.setStandart_oil(standart_oil);
								model.setWindow_control(window_control);
								model.setLight_control(light_control);
								model.setSeriesId(series.getSeries_id());
								model.setSync_id(id);
								model.setIs_alid(is_valid);
								modelService.update(model);
								System.out.println("车型：" + model_name + "同步更新成功！");
							}
						} else {
							System.out.println("车系不存在，不能同步该车型！");
						}
					}
				} else {
					System.out.println("没有车型数据！");
				}
			} else {
				System.out.println("没有车型数据！");
			}

		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println("同步车型获取数据" + strResult);
		}

		return flag;
	}
	
	/**
	 * 同步车系处理方法
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private boolean sycnSeriesHandler(Long start_date, Long end_date,String url,boolean flag,int i) throws ClientProtocolException, IOException  {
		url = getUrl(url, start_date, end_date);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String strResult = "";
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		
		if (response.getStatusLine().getStatusCode() == 200) {
			i++;
			String conResult = EntityUtils.toString(response.getEntity());
			JSONObject sobj = new JSONObject();
			sobj = sobj.fromObject(conResult);
			
			System.out.println("第" +i+"次:" + sdf.format(new Date()) +"->"+ SystemConst.HM_SERIES + " 返回内容：" + sobj.toString());
			
			if (sobj.containsKey("data")) {
				String datas = sobj.getString("data");
				flag = true;
				if (null != datas && !datas.toString().equals("null")) {
					// 解析json数据
					List<HashMap<String, Object>> results = parseJSON2List(datas);
					for (HashMap<String, Object> hashMap : results) {
						Long id = hashMap.get("series_id") == null ? null : Long.valueOf(hashMap.get("series_id").toString());
						String series_name = hashMap.get("series_name").toString();
						String producer = hashMap.get("producer") == null ? "" : hashMap.get("producer").toString();
						Integer is_valid = hashMap.get("is_valid") == null ? null : Integer.valueOf(hashMap.get("is_valid").toString());
						String remark = hashMap.get("remark") == null ? "" : hashMap.get("remark").toString();

						// 使用同步ID查询本地库是否存在该车系
						Series series = seriesService.getSeriesBySync_id(id);

						// 查询品牌信息
						Brand brand = brandService.getBrandName("海马");
						// 品牌ID
						Long brandId = brand == null ? 0L : brand.getBrand_id();

						if (series == null) {
							// 判断是否存在该名称车系
							if (seriesService.isExist(series_name)) {
								series = seriesService.getSeriesByName(series_name);
								series.setBrand_id(brandId);
								series.setName(series_name);
								series.setProducer(producer);
								series.setRemark(remark);
								series.setSync_id(id);
								seriesService.update(series);
								// 存在则更新
								System.out.println("车系：" + series_name + "同步更新成功！");
							} else {
								series = new Series();
								series.setBrand_id(brandId);
								series.setName(series_name);
								series.setProducer(producer);
								series.setRemark(remark);
								series.setSync_id(id);
								try {
									seriesService.save(series);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println("车系：" + series_name + "同步保存成功！");
							}
						} else {
							series.setBrand_id(brandId);
							series.setName(series_name);
							series.setProducer(producer);
							series.setRemark(remark);
							series.setSync_id(id);
							series.setIs_valid(is_valid);
							seriesService.update(series);
							System.out.println("车系：" + series_name + "同步更新成功！");
						}
					}
				} else {
					System.out.println("同步车系没有数据！");
				}
			} else {
				System.out.println("同步车系没有数据！");
			}
			
		} else {
			String err = response.getStatusLine().getStatusCode() + "";
			strResult += "发送失败:" + err;
			System.out.println("同步车系获取数据" + strResult);
		}
		return flag;
	}

	
}