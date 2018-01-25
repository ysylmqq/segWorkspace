package com.gboss.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import ldap.objectClasses.CommonCompany;
import ldap.objectClasses.CommonOperator;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.ComboDao;
import com.gboss.dao.ObdFaultDao;
import com.gboss.dao.PreloadDao;
import com.gboss.dao.UnitDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Combo;
import com.gboss.pojo.ObdFault;
import com.gboss.pojo.Preload;
import com.gboss.pojo.PreloadBk;
import com.gboss.pojo.Vehicle;
import com.gboss.service.PreloadService;
import com.gboss.util.DateUtil;
import com.gboss.util.HibernateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.ReflectUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SimServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-23 上午11:14:18
 */
@Service("preloadService")
@Transactional
public class PreloadServiceImpl extends BaseServiceImpl implements
		PreloadService {

	@Autowired
	@Qualifier("preloadDao")
	private PreloadDao preloadDao;

	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao VehicleDao;

	@Autowired
	@Qualifier("obdFaultDao")
	private ObdFaultDao obdFaultDao;
	
	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;
	
	@Autowired
	@Qualifier("comboDao")
	private ComboDao comboDao;

	@Autowired
	private SystemConfig systemconfig;

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	private HibernateUtil hibernateUtil;
	private OpenLdap ldap = OpenLdapManager.getInstance();

	private Connection conn;
	
	public String getLocalFilePath() {
		/*WebApplicationContext webContext = ContextLoaderListener
				.getCurrentWebApplicationContext();
		String cl_path = webContext.getServletContext().getRealPath("/");
		cl_path = cl_path.concat("/obdFile/");*/
		String cl_path =systemconfig.getObdlinuxUrl();
		cl_path = cl_path.concat("obdFile/");
		return cl_path;
	}

	public void testTask() throws Exception {

		// 先检查网络连接是否正常
		if (checkConnect()) {
			Date date = DateUtil.getBeforeDay();
			String str_date = DateUtil.formatBeforeday();
			String companyno = SystemConst.TOPCOMPANYNO;
			List<CommonCompany> list = ldap.getChildsByCompanyId(companyno);
			String obdUrl = systemconfig.getObdconnectUrl() + "sosobd/";
			String obdlinuxUrl = systemconfig.getObdlinuxUrl();
			File file_check = new File(obdlinuxUrl);
			if (file_check.exists()) { 
				System.out.println("该文件存在---------------");
				String localFilePath = getLocalFilePath();
				checkFileIsExist(localFilePath);
				String downLoadPath = localFilePath + str_date + "_downLoad/";
				localFilePath = localFilePath + str_date + "/";
				// 创建原文件目录和提供下载目录文件夹
				checkFileIsExist(localFilePath);
				checkFileIsExist(downLoadPath);
				// 检查对应分公司的obd文件是否存在，若存在则下载到本地
				for (CommonCompany commonCompany : list) {
					String company_id = commonCompany.getCompanyno();
					String file_name = str_date + "_" + company_id;
					String obdExcel_path = obdUrl + file_name + ".xls";
					if (isExistFile(obdExcel_path)) {
						downloadFile(obdExcel_path, localFilePath + file_name + ".xls");
					}
				}
				// 处理远程下载过来的excel文件
				readfile(date, localFilePath, downLoadPath, str_date);
				
			}else{
				System.out.println("该文件存不存在———————————————————");
			}
		}
	}

	public void checkFileIsExist(String path) {
		File file_dir = new File(path);
		if (!file_dir.exists()) { // 判断路径是否存在，不存在则创建
			file_dir.mkdirs();
		}
	}

	// 修改excel，填充车架号
	public void updaeExcel(Date date, File readfile, String obdfilepath,
			String downLoadPath, String str_date) throws Exception {
		WritableWorkbook book = null;
		boolean flag = false;
		try {
			Workbook wb = Workbook.getWorkbook(new File(obdfilepath
					+ readfile.getName()));
			book = Workbook.createWorkbook(
					new File(downLoadPath + readfile.getName()), wb);
			Sheet sheet = book.getSheet(0);
			WritableSheet wsheet = book.getSheet(0);
			if (sheet.getRows() > 4) {
				for (int i = 4; i < sheet.getRows(); i++) {
					WritableCell cell = wsheet.getWritableCell(0, i);// 获取第一个单元格
					jxl.format.CellFormat cf = cell.getCellFormat();// 获取第一个单元格的格式
					String vin = "";
					String call_letter = sheet.getCell(0, i).getContents();
					if (com.gboss.util.StringUtils.isNotBlank(call_letter)) {
						call_letter = call_letter.trim();
						Vehicle vehicle = VehicleDao.getVehicleByCallLetter(call_letter);
						if (vehicle != null) {
							vin = vehicle.getVin();
						}
					}
					Label label = new Label(3, i, vin, cf);
					wsheet.addCell(label);
				}
				flag = true;
			}
		book.write();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*// 检查提供给海马的下载文件是否已经生成，若生成则同时保存对应的文件信息到数据库*/
		File checkFile = new File(downLoadPath + readfile.getName());
		//若文件存在但是没有数据，则删除
		if(checkFile.exists() && !flag){
			checkFile.delete(); 
		}
		if (checkFile.exists() && flag) {
			ObdFault obd = new ObdFault();
			String file_name = readfile.getName();
			int begin = file_name.indexOf("_");
			int end = file_name.indexOf(".xls");
			if (begin + 1 <= end) {
				String company_id = file_name.substring(begin + 1, end);
				if (StringUtils.isNotBlank(company_id)) {
					obd.setSubco_no(Long.valueOf(company_id));
					obd.setSt_date(date);
					String name = str_date + "故障码信息汇总";
					String url = "../../obdFile/" + str_date + "_downLoad/";
					obd.setUrl(url + readfile.getName());
					String file_url = "<a href='" + url + file_name
							+ "' style='color:green'>" + name + "</a>";
					obd.setFile_name(name);
					obd.setUrl(file_url);
					obdFaultDao.save(obd);
				}
			}
		}
		
		
	}

	/**
	 * 
	 * @Title:readfile
	 * @Description:遍历该文件夹下excel文件进行excel文件车架号填充
	 * @param filepath
	 * @param downLoadPath
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public boolean readfile(Date date, String filepath, String downLoadPath,
			String str_date) throws Exception {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("文件路径，不是文件夹路径");
			} else if (file.isDirectory()) {
				System.out.println("文件夹路径");
				// 遍历该文件夹下excel文件进行excel文件车架号填充
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + filelist[i]);
					System.out.println(filepath + "----$$$$$$---------" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println("path=" + readfile.getPath()
								+ "文件name=" + readfile.getName());
						updaeExcel(date, readfile, filepath, downLoadPath,
								str_date);
					}
					// 暂时文件夹下不存在子目录
					/*
					 * else if (readfile.isDirectory()) { readfile(filepath +
					 * "\\" + filelist[i], downLoadPath); }
					 */
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @Title:isExistFile
	 * @Description:检查服务器文件是否存在
	 * @param fileUrl
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public boolean isExistFile(String fileUrl) throws Exception {
		boolean falg = false;
		URL url = new URL(fileUrl);
		HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
		String message = urlcon.getHeaderField(0);
		// System.out.println(message);
		// 文件存在‘HTTP/1.1 200 OK’ 文件不存在 ‘HTTP/1.1 404 Not Found’
		/*
		 * Long TotalSize = Long.parseLong(urlcon
		 * .getHeaderField("Content-Length"));
		 */
		if (message.contains("200")) {
			falg = true;
		}
		return falg;
	}

	/**
	 * 下载远程文件并保存到本地
	 * 
	 * @param remoteFilePath
	 *            远程文件路径
	 * @param localFilePath
	 *            本地文件路径
	 */
	public void downloadFile(String remoteFilePath, String localFilePath) {
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checkConnect() {
		String ip = systemconfig.getObdconnectIp();
		boolean flag = false;
		String obd_port = systemconfig.getObdconnectPort();
		int port = Integer.valueOf(obd_port);
		Socket my = new Socket(); // 实例化socket对象
		try {
			InetSocketAddress mySock = new InetSocketAddress(
					InetAddress.getByName(ip), port);// 假设连接目标的80端口
			my.connect(mySock, 100);// 100毫秒超时
			System.out.println("连接OBD查询系统成功!");
			flag = true;
		} catch (SocketTimeoutException e) {// 捉到了!
			System.out.println("cannot connect ...");
		} catch (Exception e) {// 其他有IOException等
			System.out.println("Exception,cannot connect ...");
		}
		return flag;
	}

	@Override
	public Page<HashMap<String, Object>> findSimByPage(Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = preloadDao.countPreload(companyId, pageSelect.getFilter());
		List<HashMap<String, Object>> list = preloadDao.findPreloadList(
				companyId, pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

	@Override
	public int operate(List<Long> ids, Integer type) throws Exception {
		int result = 1;
		if (ids == null || ids.isEmpty()) {
			result = -1;
		} else {
			for (Long id : ids) {
				Preload sim = preloadDao.get(Preload.class, id);
				if (null != sim) {
					PreloadBk bk = new PreloadBk();
					BeanUtils.copyProperties(bk, sim);
					// 保存到历史表
					preloadDao.save(bk);
				}
			}
			preloadDao.operate(ids, type);
		}
		return result;
	}

	@Override
	public int operateCombo(List<Long> ids, Integer type)
			throws SystemException {
		int result = 1;
		if (ids == null || ids.isEmpty()) {
			result = -1;
		} else {
			preloadDao.operateCombo(ids, type);
		}
		return result;
	}

	@Override
	public int addSim(Preload sim) throws SystemException {
		int result = 0;
		if (preloadDao.isExist_phone(sim)) {
			result = 1;
			return result;
		} else if (preloadDao.isExist_barcode(sim)) {
			result = 2;
			return result;
		} else if (preloadDao.isExist_vin(sim)) {
			result = 3;
			return result;
		} else if (preloadDao.isExist_imei(sim)) {
			result = 4;
			return result;
		}
		return result;
	}

	// 从excel取值列设置
	private Map<String, String> colsMap = new HashMap<String, String>();
	{
		colsMap.put("vin", "1");// 车架号
		colsMap.put("engine_no", "2");// 发动机号
		colsMap.put("color", "3"); // 颜色
		colsMap.put("create_date", "4");// 生产日期
		colsMap.put("barcode", "5");// TBOX条码
	}
	
	private Map<String, String> updaeSimcolsMap = new HashMap<String, String>();
	{
		updaeSimcolsMap.put("call_letter", "1");
		updaeSimcolsMap.put("barcode", "2");
		updaeSimcolsMap.put("vin", "3");
	}

	private Map<String, String> colsMap_sim = new HashMap<String, String>();
	{
		colsMap_sim.put("iccid", "1");
		colsMap_sim.put("imsi", "2");
		colsMap_sim.put("akey", "3");
		colsMap_sim.put("esn", "4");
		colsMap_sim.put("w_user", "5");
		colsMap_sim.put("w_password", "6");
		colsMap_sim.put("call_letter", "7");
		colsMap_sim.put("imei", "8");
		/*colsMap_sim.put("telco", "9");*/

	}

	private Map<String, String> colsMap_sim_tbox = new HashMap<String, String>();
	{
		colsMap_sim_tbox.put("iccid", "1");
		colsMap_sim_tbox.put("imsi", "2");
		colsMap_sim_tbox.put("akey", "3");
		colsMap_sim_tbox.put("esn", "4");
		colsMap_sim_tbox.put("w_user", "5");
		colsMap_sim_tbox.put("w_password", "6");
		colsMap_sim_tbox.put("call_letter", "7");
		colsMap_sim_tbox.put("imei", "8");
		colsMap_sim_tbox.put("barcode", "9");

	}
	
	
	private Map<String, String> colsMap_newsim_tbox = new HashMap<String, String>();
	{
		colsMap_newsim_tbox.put("iccid", "1");
		colsMap_newsim_tbox.put("imsi", "2");
		colsMap_newsim_tbox.put("akey", "3");
		colsMap_newsim_tbox.put("esn", "4");
		colsMap_newsim_tbox.put("w_user", "5");
		colsMap_newsim_tbox.put("w_password", "6");
		colsMap_newsim_tbox.put("call_letter", "7");
		colsMap_newsim_tbox.put("s_date", "8");
		colsMap_newsim_tbox.put("remark", "9");
		colsMap_newsim_tbox.put("barcode", "10");
		colsMap_newsim_tbox.put("imei", "11");

	}
	
	private Map<String, String> cols_ubi_sim = new HashMap<String, String>();
	{
		cols_ubi_sim.put("call_letter", "1");
		cols_ubi_sim.put("imei", "2");
	}
	
	
	
	// 读取excel时默认设置值
	private Map<String, Object> defValMap = new HashMap<String, Object>();
	{

	}

	private Map<String, Map<String, String>> transfMap = new HashMap<String, Map<String, String>>();
	{
		/*Map<String, String> sim_Map = new HashMap<String, String>();
		sim_Map.put("移动", "1");
		sim_Map.put("联通", "2");
		sim_Map.put("电信", "3");
		transfMap.put("telco", sim_Map);*/
	}

	@Override
	public Map<String, Object> importSimNew(List<String[]> dataList,
			Long compannyId, Long userId, Integer type, Integer telco) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = dataList.size();
		defValMap.put("subco_no", compannyId);
		defValMap.put("op_id", userId);
		defValMap.put("stamp",DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME24));
		List<Preload> simList = new ArrayList<Preload>();

		try {
			if (type == 3) {
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap, defValMap, transfMap, new Preload());
			} else if (type == 1) {
				if(telco != null){
					defValMap.put("telco", telco);
				}
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap_sim, defValMap, transfMap,
						new Preload());
			} else if (type == 2) {
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap_sim_tbox, defValMap, transfMap,
						new Preload());
			}else if(type == 4){
				if(telco != null){
					defValMap.put("telco", telco);
				}
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap_newsim_tbox, defValMap, transfMap,
						new Preload());
			}

		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "解析excel出错！");
			return map;
		}
		if (simList.isEmpty()) {
			map.put("success", false);
			map.put("msg", "读取excel失败！");
			return map;
		}  
		int addNum = 0;
		int upadteNum = 0;
		int failNum = 0;
		String message = "";
		if (type == 3) {
			// 插入更新车辆
			for (Preload sim : simList) {
				int a = simList.indexOf(sim);
				a = a + 2;
				try {

					if (StringUtils.isBlank(sim.getBarcode())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>TBOX条码</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>为空";
						continue;
					}
					if (StringUtils.isBlank(sim.getVin())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>车架号</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>为空";
						continue;
					} else {
						if (sim.getVin().length() != 17) {
							message += "第<span style=color:green>"
									+ a
									+ "</span>行<span style=color:green>车架号</span>为：<span style=color:red>"
									+ sim.getVin() + "</span>不为17位";
							continue;
						}
					}
					Boolean isExist_barcode = preloadDao.isExist_barcode(sim);
					Boolean isExist_vin = preloadDao.isExist_vin(sim);
					if (isExist_vin) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>车架号</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>已存在";
						continue;
					} else {
						if (!isExist_barcode) {
							message += "第<span style=color:green>"
									+ a
									+ "</span>行<span style=color:green>TBOX条码</span>为：<span style=color:red>"
									+ sim.getBarcode() + "</span>不存在";
							continue;
						} else {
							Preload old_sim = preloadDao.getPreloadByTbox(sim
									.getBarcode());
							if (old_sim != null && old_sim.getVin() == null) {
								PreloadBk bk = new PreloadBk();
								BeanUtils.copyProperties(bk, old_sim);
								// 保存到历史表
								preloadDao.save(bk);
								old_sim.setVin(sim.getVin());
								old_sim.setColor(sim.getColor());
								old_sim.setEngine_no(sim.getEngine_no());
								old_sim.setProduction_date(sim
										.getProduction_date());
								preloadDao.update(old_sim);
								upadteNum++;
							} else if (old_sim != null
									&& old_sim.getVin() != null) {
								message += "第<span style=color:green>"
										+ a
										+ "</span>行<span style=color:green>TBOX号</span>为：<span style=color:red>"
										+ sim.getBarcode()
										+ "</span>的SIM卡车辆信息已存在";
							}
						}
					}
				} catch (Exception e) {
				}
				message = message + "<br />";
				failNum = total - upadteNum;
				message = "共<span style=color:green>" + total
						+ "</span>条数据，其中成功更新<span style=color:green>"
						+ upadteNum + "</span>条，失败<span style=color:red>"
						+ failNum + "</span>条<br />";
			}
		} else if (type == 1) {
			// 插入SIM卡信息
			for (Preload sim : simList) {
				int a = simList.indexOf(sim);
				a = a + 2;
				try {
					if (StringUtils.isBlank(sim.getImei())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>IMEI</span>为：<span style=color:red>"
								+ sim.getImei() + "</span>为空";
						continue;
					}
					if (StringUtils.isBlank(sim.getCall_letter())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>为空";
						continue;
					}
					boolean isExist_imei = preloadDao.isExist_imei(sim);
					boolean isExist_ct = preloadDao.isExist_call_letter(sim);
					if (isExist_ct) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>已存在";
						continue;
					} else if (isExist_imei) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>IMEI</span>为：<span style=color:red>"
								+ sim.getImei() + "</span>已存在";
						continue;
					} else {
						preloadDao.save(sim);
						addNum++;
					}
				} catch (Exception e) {
				}
				message = message + "<br />";
			}

			failNum = total - addNum;
			message = "共<span style=color:green>" + total
					+ "</span>条数据，其中成功新增<span style=color:green>" + addNum
					+ "</span>条，失败<span style=color:red>" + failNum
					+ "</span>条<br />";

		} else if (type == 2) {
			// SIM卡+TBOX，更新TBOX信息
			for (Preload sim : simList) {
				int a = simList.indexOf(sim);
				a = a + 2;
				try {
					if (StringUtils.isBlank(sim.getBarcode())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>TBOX条码</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>为空";
						continue;
					}
					if (StringUtils.isBlank(sim.getCall_letter())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>为空";
						continue;
					}
					Preload sm = preloadDao
							.getPreloadByCl(sim.getCall_letter());
					if (sm != null) {
						Boolean isExist_barcode = preloadDao
								.isExist_barcode(sim);
						if (!isExist_barcode) {
							PreloadBk bk = new PreloadBk();
							BeanUtils.copyProperties(bk, sm);
							// 保存到历史表
							preloadDao.save(bk);
							sm.setBarcode(sim.getBarcode());
							preloadDao.update(sm);
							upadteNum++;
						}
					} else {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>不存在";
						continue;
					}
				} catch (Exception e) {
				}
				message = message + "<br />";
			}
			failNum = total - upadteNum;
			message = "共<span style=color:green>" + total
					+ "</span>条数据，其中成功更新<span style=color:green>" + upadteNum
					+ "</span>条，失败<span style=color:red>" + failNum
					+ "</span>条<br />";
		} else if (type == 4) {		
			Date sDate = new Date();
			CommonOperator  user = ldap.getOperatorById(userId.toString());
			String name = user.getOpname();	
			List<Combo> list = comboDao.getComboList();			
			Map<String,Long> comboMap = new HashMap<String,Long>();
			for(Combo entity:list){
				comboMap.put(entity.getCombo_name(), entity.getCombo_id());
			}
			//获取Sim卡
			List<Preload> listPreloads = preloadDao.getAll();
			HashMap<String, Preload> sim_map = new HashMap<String, Preload>();
			HashMap<String, Preload> imei_map = new HashMap<String, Preload>();
			HashMap<String, Preload> barcode_map = new HashMap<String, Preload>();
			for(Preload preload:listPreloads){
				if (preload.getCall_letter() != null)
					sim_map.put(preload.getCall_letter(), preload);
				if (preload.getImei() != null)
					imei_map.put(preload.getImei(), preload);
				if (preload.getBarcode() != null)
					barcode_map.put(preload.getBarcode(), preload);			
			}
			
			// 插入最新海马SIM卡信息
			int i=0;
			List<Preload> errorList = new ArrayList<Preload>();
			for (Preload sim : simList) {
				message = "";
				int a = simList.indexOf(sim);
				a = a + 2;
				try {
					//call_letter  imei barcode  不为空时插入
					
					if ( !sim_map.containsKey(sim.getCall_letter())  && !imei_map.containsKey(sim.getImei()) && 
							!barcode_map.containsKey(sim.getBarcode()) ){
						sim.setSubco_no(compannyId);
						sim.setStamp(new Date());
						sim.setFlag(1);
						sim.setCombo_status(1);
						sim.setTelco(telco);
						sim.setOp_id(userId);
						sim.setCombo_id(comboMap.get(sim.getRemark()));
						sim.setRemark(name + ",SIM卡导入");
						sim.setE_date(sim.getS_date());
						addNum++;
						preloadDao.save(sim);
					}else{
						if ( sim_map.containsKey(sim.getCall_letter()) ) {  //对应表当中的 设备号
							/*message += "第<span style=color:red>"
									+ a
									+ "</span>行<span style=color:green>呼号为空</span>&nbsp;";
									*/
							message += "呼号已存在！";
						}
						if ( imei_map.containsKey(sim.getImei()) ) {  //对应表当中的 MEID
							/*message += "第<span style=color:red>"
									+ a
									+ "</span>行<span style=color:green>IMEI为空</span>&nbsp;";
									*/
							message += "IMEI已存在！";
						}
						if ( barcode_map.containsKey(sim.getBarcode()) ) {  //序列号
							/*message += "第<span style=color:red>"
									+ a
									+ "</span>行<span style=color:green>序列号</span>&nbsp;";
									*/
							message += "序列号已存在!";
						}
						sim.setRemark(message);
						errorList.add(sim);
					}
				} catch (Exception e) {
					e.printStackTrace();
					map.put("success", false);
					map.put("msg", "操作失败");
					return map;
				}
			}
           Date eDate = new Date();
           long runTime =  (eDate.getTime()-sDate.getTime())/1000;
			map.put("success", true);
			map.put("total", total);
			map.put("addNum", addNum);
			map.put("runTime", runTime);
			map.put("failNum",  total - addNum);
			map.put("errorData", errorList);
			return map;
		} 

		map.put("success", true);
		map.put("msg", message);
		return map;
	}
	
	
	
	
	@Override
	public Map<String, Object> importSim(List<String[]> dataList,
			Long compannyId, Long userId, Integer type, Integer telco) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = dataList.size();
		defValMap.put("subco_no", compannyId);
		defValMap.put("op_id", userId);
		defValMap.put("stamp",DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME24));
		List<Preload> simList = new ArrayList<Preload>();
		
		//转换成对象  Preload SIM信息表
		try {
			if (type == 3) {
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap, defValMap, transfMap, new Preload());
			} else if (type == 1) {
				if(telco != null){
					defValMap.put("telco", telco);
				}
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap_sim, defValMap, transfMap,
						new Preload());
			} else if (type == 2) {
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap_sim_tbox, defValMap, transfMap,
						new Preload());
			}else if(type == 4){
				if(telco != null){
					defValMap.put("telco", telco);
				}
				simList = (List<Preload>) ReflectUtils.buildListForImport(
						dataList, colsMap_newsim_tbox, defValMap, transfMap,
						new Preload());
			}

		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "解析excel出错！");
			return map;
		}
		if (simList.isEmpty()) {
			map.put("success", false);
			map.put("msg", "读取excel失败！");
			return map;
		}  
		int addNum = 0;
		int upadteNum = 0;
		int failNum = 0;
		String message = "";
		if (type == 3) {
			// 插入更新车辆
			for (Preload sim : simList) {
				int a = simList.indexOf(sim);
				a = a + 2;
				try {

					if (StringUtils.isBlank(sim.getBarcode())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>TBOX条码</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>为空";
						continue;
					}
					if (StringUtils.isBlank(sim.getVin())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>车架号</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>为空";
						continue;
					} else {
						if (sim.getVin().length() != 17) {
							message += "第<span style=color:green>"
									+ a
									+ "</span>行<span style=color:green>车架号</span>为：<span style=color:red>"
									+ sim.getVin() + "</span>不为17位";
							continue;
						}
					}
					//根据条形码判断是否存在
					Boolean isExist_barcode = preloadDao.isExist_barcode(sim);
					//车架号  有海马产生
					Boolean isExist_vin = preloadDao.isExist_vin(sim);
					//车架号已存在
					if (isExist_vin) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>车架号</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>已存在";
						continue;
					} else {
						if (!isExist_barcode) {
							message += "第<span style=color:green>"
									+ a
									+ "</span>行<span style=color:green>TBOX条码</span>为：<span style=color:red>"
									+ sim.getBarcode() + "</span>不存在";
							continue;
						} else {
							Preload old_sim = preloadDao.getPreloadByTbox(sim
									.getBarcode());
							if (old_sim != null && old_sim.getVin() == null) {
								PreloadBk bk = new PreloadBk();
								BeanUtils.copyProperties(bk, old_sim);
								// 保存到历史表
								preloadDao.save(bk);
								old_sim.setVin(sim.getVin());
								old_sim.setColor(sim.getColor());
								old_sim.setEngine_no(sim.getEngine_no());
								old_sim.setProduction_date(sim
										.getProduction_date());
								preloadDao.update(old_sim);
								upadteNum++;
							} else if (old_sim != null
									&& old_sim.getVin() != null) {
								message += "第<span style=color:green>"
										+ a
										+ "</span>行<span style=color:green>TBOX号</span>为：<span style=color:red>"
										+ sim.getBarcode()
										+ "</span>的SIM卡车辆信息已存在";
							}
						}
					}
				} catch (Exception e) {
				}
				message = message + "<br />";
				failNum = total - upadteNum;
				message = "共<span style=color:green>" + total
						+ "</span>条数据，其中成功更新<span style=color:green>"
						+ upadteNum + "</span>条，失败<span style=color:red>"
						+ failNum + "</span>条<br />";
			}
		} else if (type == 1) {
			// 插入SIM卡信息
			for (Preload sim : simList) {
				int a = simList.indexOf(sim);
				a = a + 2;
				try {
					if (StringUtils.isBlank(sim.getImei())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>IMEI</span>为：<span style=color:red>"
								+ sim.getImei() + "</span>为空";
						continue;
					}
					if (StringUtils.isBlank(sim.getCall_letter())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>为空";
						continue;
					}
					boolean isExist_imei = preloadDao.isExist_imei(sim);
					boolean isExist_ct = preloadDao.isExist_call_letter(sim);
					if (isExist_ct) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>已存在";
						continue;
					} else if (isExist_imei) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>IMEI</span>为：<span style=color:red>"
								+ sim.getImei() + "</span>已存在";
						continue;
					} else {
						preloadDao.save(sim);
						addNum++;
					}
				} catch (Exception e) {
				}
				message = message + "<br />";
			}

			failNum = total - addNum;
			message = "共<span style=color:green>" + total
					+ "</span>条数据，其中成功新增<span style=color:green>" + addNum
					+ "</span>条，失败<span style=color:red>" + failNum
					+ "</span>条<br />";

		} else if (type == 2) {
			// SIM卡+TBOX，更新TBOX信息
			for (Preload sim : simList) {
				int a = simList.indexOf(sim);
				a = a + 2;
				try {
					if (StringUtils.isBlank(sim.getBarcode())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>TBOX条码</span>为：<span style=color:red>"
								+ sim.getVin() + "</span>为空";
						continue;
					}
					if (StringUtils.isBlank(sim.getCall_letter())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>为空";
						continue;
					}
					Preload sm = preloadDao
							.getPreloadByCl(sim.getCall_letter());
					if (sm != null) {
						Boolean isExist_barcode = preloadDao
								.isExist_barcode(sim);
						if (!isExist_barcode) {
							PreloadBk bk = new PreloadBk();
							BeanUtils.copyProperties(bk, sm);
							// 保存到历史表
							preloadDao.save(bk);
							sm.setBarcode(sim.getBarcode());
							preloadDao.update(sm);
							upadteNum++;
						}
					} else {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>不存在";
						continue;
					}
				} catch (Exception e) {
				}
				message = message + "<br />";
			}
			failNum = total - upadteNum;
			message = "共<span style=color:green>" + total
					+ "</span>条数据，其中成功更新<span style=color:green>" + upadteNum
					+ "</span>条，失败<span style=color:red>" + failNum
					+ "</span>条<br />";
		} else if (type == 4) {		
			Date sDate = new Date();
			CommonOperator  user = ldap.getOperatorById(userId.toString());
			String name = user.getOpname();	
			//获取套餐
			List<Combo> list = comboDao.getComboList();			
			Map<String,Long> comboMap = new HashMap<String,Long>();
			for(Combo entity:list){
				comboMap.put(entity.getCombo_name(), entity.getCombo_id());
			}
			//获取Sim卡
			List<Preload> listPreloads = preloadDao.getAll();
			HashMap<String, Preload> sim_map = new HashMap<String, Preload>();
			HashMap<String, Preload> imei_map = new HashMap<String, Preload>();
			HashMap<String, Preload> barcode_map = new HashMap<String, Preload>();
			for(Preload preload:listPreloads){
				if (preload.getCall_letter() != null)
					sim_map.put(preload.getCall_letter(), preload);
				if (preload.getImei() != null)
					imei_map.put(preload.getImei(), preload);
				if (preload.getBarcode() != null)
					barcode_map.put(preload.getBarcode(), preload);			
			}
			
			// 插入最新海马SIM卡信息
			int i=0;
			for (Preload sim : simList) {
				int a = simList.indexOf(sim);
				a = a + 2;
				try {
					if (StringUtils.isBlank(sim.getImei())) {
						message += "第<span style=color:red>"
								+ a
								+ "</span>行<span style=color:green>IMEI为空</span>&nbsp;";
						continue;
					}
					if (StringUtils.isBlank(sim.getCall_letter())) {
						message += "第<span style=color:red>"
								+ a
								+ "</span>行<span style=color:green>呼号为空</span>&nbsp;";
						continue;
					}
					//根据callLetter获取sim信息
					Preload sm = sim_map.get(sim.getCall_letter());
					if (null != sm) {
						// Preload sm =
						// preloadDao.getPreloadByCl(sim.getCall_letter());
						PreloadBk bk = new PreloadBk();
						BeanUtils.copyProperties(bk, sm);
						// 更新
						if (null != sm) {
							i=i+2;
							sm.setBarcode(sim.getBarcode());
							sm.setImei(sim.getImei());
							// boolean isExist_imei_s =
							// preloadDao.isExist_imei(sm);
							// boolean isExist_barcode_s =
							// preloadDao.isExist_barcode(sm);
							// if(!isExist_imei_s && !isExist_barcode_s){							
							sm.setCombo_id(comboMap.get(sim.getRemark()));
							sm.setAkey(sim.getAkey());
							sm.setS_date(sim.getS_date());
							sm.setE_date(sim.getS_date());
							sm.setEsn(sim.getEsn());
							sm.setImsi(sim.getImsi());// 2015-04-14添加
							sm.setImei(sim.getImei());// 2015-04-14添加
							sim.setBarcode(sim.getBarcode());// 2015-04-14添加
							sm.setIccid(sim.getIccid());
							sm.setTelco(telco);
							sm.setW_password(sim.getW_password());
							sm.setW_user(sim.getW_user());
							sm.setRemark(name + ",SIM卡导入,资料更新");
							
							// 保存到历史表
							preloadDao.save(bk);
							preloadDao.update(sm);
							//Utils.putSimMap(sm);
							upadteNum++;
						}
					}else{
						Preload preload = imei_map.get(sim.getImei());	
						if (preload !=null) {
							message += "第<span style=color:green>"
									+ a
									+ "</span>行<span style=color:green>IMEI</span>为：<span style=color:red>"
									+ sim.getImei() + "</span>已存在</br>";
							continue;
						}else{
							preload = barcode_map.get(sim.getBarcode());
							if (preload != null) {
								message += "第<span style=color:green>"
										+ a
										+ "</span>行<span style=color:green>机身条码</span>为：<span style=color:red>"
										+ sim.getImei() + "</span>已存在</br>";
								continue;
							} else {
								i++;
								sim.setCombo_id(comboMap.get(sim.getRemark()));
								sim.setRemark(name + ",SIM卡导入");
								sim.setE_date(sim.getS_date());
								preloadDao.save(sim);
								//Utils.putSimMap(sim);
								addNum++;
							}
						}	
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					map.put("success", false);
					map.put("msg", "操作失败");
					return map;
				}
			}
           Date eDate = new Date();
           long runTime =  (eDate.getTime()-sDate.getTime())/1000;
			failNum = total - addNum - upadteNum ;
			message = "共<span style=color:green>" + total
					+ "</span>条数据，其中成功新增<span style=color:green>" + addNum
					+ "</span>条，成功更新<span style=color:green>" + upadteNum
					+ "</span>条，失败<span style=color:red>" + failNum
					+ "</span>条<br /> 耗时："+runTime+"秒  "+message;

		} 

		map.put("success", true);
		map.put("msg", message);
		return map;
	}
	
	@Override
	public Preload getPreloadByCl(String call_letter) throws SystemException {
		return preloadDao.getPreloadByCl(call_letter);
	}

	@Override
	public void gethmCall_letter() throws Exception {
		Map<String, String> call_letterMap = SystemConst.call_letterMap;
		String call_letter = unitDao.getHmCall_letters(201L);
		call_letterMap.put("call_letter", call_letter);
	}

	@Override
	public List<HashMap<String, Object>> findSimList(Long companyId,
			Map<String, Object> conditionMap) throws SystemException {
		List<HashMap<String, Object>> list = preloadDao.findPreloadList(companyId, conditionMap, null, false, 0, 0);
		return list;
	}

	@Override
	public List<Preload> getAll() throws SystemException {
		return preloadDao.getAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importUBI(List<String[]> dataList, Long compannyId, Long userId, Integer type, Integer telco) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = dataList.size();
		defValMap.put("subco_no", compannyId);
		defValMap.put("op_id", userId);
		defValMap.put("stamp",DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME24));
		if(telco != null){
			defValMap.put("telco", telco);
		}
		List<Preload> simList = new ArrayList<Preload>();
		try{
			simList = (List<Preload>) ReflectUtils.buildListForImport(dataList, cols_ubi_sim, defValMap, transfMap, new Preload(),DateUtil.YMD_DASH_WITH_FULLTIME24);
			if (simList.isEmpty()) {
				map.put("success", false);
				map.put("msg", "读取excel失败！");
				return map;
			}  
			int addNum = 0;
			int upadteNum = 0;
			int failNum = 0;
			String message = "";
			
			for (Preload sim : simList) {
//				System.out.println("=====================>"+DateUtil.format(sim.getStamp(),DateUtil.YMD_DASH_WITH_FULLTIME24));
				int a = simList.indexOf(sim);
				a = a + 2;
				try {
					if (StringUtils.isBlank(sim.getImei())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>IMEI</span>为：<span style=color:red>"
								+ sim.getImei() + "</span>为空<br />";
						continue;
					}
					if (StringUtils.isBlank(sim.getCall_letter())) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>为空<br />";
						continue;
					}
					boolean isExist_imei = preloadDao.isExist_imei(sim);
					boolean isExist_ct = preloadDao.isExist_call_letter(sim);
					if (isExist_ct) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>呼号</span>为：<span style=color:red>"
								+ sim.getCall_letter() + "</span>已存在<br />";
						continue;
					} else if (isExist_imei) {
						message += "第<span style=color:green>"
								+ a
								+ "</span>行<span style=color:green>IMEI</span>为：<span style=color:red>"
								+ sim.getImei() + "</span>已存在<br />";
						continue;
					} else {
						preloadDao.save(sim);
						addNum++;
					}
				} catch (Exception e) {
					
				}
				message = message + "<br />";
			}

			failNum = total - addNum;
			message = "共<span style=color:green>" + total
					+ "</span>条数据，其中成功新增<span style=color:green>" + addNum
					+ "</span>条，失败<span style=color:red>" + failNum
					+ "</span>条<br />" + message;
			
			map.put("success", true);
			map.put("msg", message);
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "解析excel出错！");
			return map;
		}
		return map;
	}

	@Override
	public Page<Map<String, Object>> findUbiByPage(PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = preloadDao.countUbiPage(pageSelect.getFilter());
		List<Map<String, Object>> list = preloadDao.findUbiByPage(pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(), pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public Map<String, Object> verifySim(List<String[]> dataList, Long compannyId, Long userId) throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		String callLetterStr = "";
		String msg = "";
		for(int i = 0; i < dataList.size(); i++){
			String[] tempStrs = dataList.get(i);
			String phone = tempStrs[6];
			if(StringUtils.isBlank(callLetterStr)){
				if(StringUtils.isNotBlank(phone)){
					callLetterStr = "'" + phone + "'";	//呼号
					continue;
				}
			}else{
				if(StringUtils.isNotBlank(phone)){
					callLetterStr += ", '" + phone + "'";	//呼号
				}
			}
		}
		List<Preload> simList = preloadDao.listByCallLetters(callLetterStr);	//呼号查询结果
		Map<String, Integer> locMap = new HashMap<String, Integer>();	//记录呼号对应的数据在list中位置
		if(simList != null && !simList.isEmpty()){
			for(int j = 0; j < simList.size(); j++){
				Preload p = simList.get(j);
				locMap.put(p.getCall_letter(), j);
			}
		}
		if(simList.isEmpty()){
			map.put("success", false);
			map.put("msg", "验证的数据尚未保存到数据库");
			return map;
		}
		boolean pass = true;
		Integer successCount = 0, failureCount = 0;
		Integer total = dataList.size();
		for(int k = 0; k < total; k++){
			String[] tempStrs = dataList.get(k);
			String redCol = "";
			Map<String, Object> temp = new HashMap<String, Object>();//存放临时数据，转存list
			temp.put("iccid", tempStrs[0]);
			temp.put("imsi", tempStrs[1]);
			temp.put("akey", tempStrs[2]);
			temp.put("esn", tempStrs[3]);
			temp.put("w_user", tempStrs[4]);
			temp.put("w_password", tempStrs[5]);
			temp.put("call_letter", tempStrs[6]);
			temp.put("barcode", tempStrs[7]);
			temp.put("imei", tempStrs[8]);
			String call_letter = tempStrs[6];
			if(StringUtils.isBlank(call_letter)){
				temp.put("result", "设备号(呼号)为空!");
				redCol += "call_letter"+",";
				temp.put("redCol", redCol);
				returnList.add(temp);
				failureCount++;
				continue;
			}
			Integer loc = locMap.get(call_letter);
			if(loc == null){
				temp.put("result", "数据尚未导入到数据库!");
				redCol += "call_letter"+",";
				temp.put("redCol", redCol);
				returnList.add(temp);
				failureCount++;
				continue;
			}
			Preload sim = simList.get(loc);
			String result = "";
			if(!tempStrs[0].equals(sim.getIccid())){
				result = "ICCID不一致,";
				redCol += "iccid"+",";
			}
			if(!tempStrs[1].equals(sim.getImsi())){
				result = "IMSI不一致,";
				redCol += "imsi"+",";
			}
			if(!tempStrs[2].equals(sim.getAkey())){
				result = "AKEY不一致,";
				redCol += "akey"+",";
			}
			if(!tempStrs[3].equals(sim.getEsn())){
				result = "ESN不一致,";
				redCol += "esn"+",";
			}
			if(!tempStrs[4].equals(sim.getW_user())){
				result = "EVDO账号不一致,";
				redCol += "w_user"+",";
			}
			if(!tempStrs[5].equals(sim.getW_password())){
				result = "EVDO密码不一致,";
				redCol += "w_password"+",";
			}
			if(!tempStrs[7].equals(sim.getBarcode())){
				result = "序列号（机身条码）不一致,";
				redCol += "barcode"+",";
			}
			if(!tempStrs[8].equals(sim.getImei())){
				result = "MEID/IMEI(3G模块)不一致,";
				redCol += "imei"+",";
			}
			if(StringUtils.isNotBlank(result)){	//结果不为空添加内容
				result = result.substring(0, result.length());
				temp.put("result", result);
				temp.put("redCol", redCol);
				returnList.add(temp);
				failureCount++;
			}else{
				successCount++;
			}
		}
		msg = "总共验证<span style=color:green>" + total
					+ "</span>条数据，其中成功通过<span style=color:green>" + successCount
					+ "</span>条，失败<span style=color:red>" + failureCount
					+ "</span>条";
		map.put("msg", msg);
		map.put("success", true);
		map.put("compareList", returnList);
		return map;
	}

	@Override
	public Map<String, Object> batchUpdateSimFlag(List<String[]> dataList,
			Long compannyId, Long userId, Integer type) throws SystemException, IllegalAccessException, InvocationTargetException {

		Map<String, Object> map = new HashMap<String, Object>();
		int total = dataList.size();
		defValMap.put("subco_no", compannyId);
		defValMap.put("op_id", userId);
		defValMap.put("stamp",DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME24));
		List<Preload> simList = new ArrayList<Preload>();

		try {
			simList = (List<Preload>) ReflectUtils.buildListForImport(
					dataList, updaeSimcolsMap, defValMap, transfMap, new Preload());
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "解析excel出错！");
			return map;
		}
		if (simList.isEmpty()) {
			map.put("success", false);
			map.put("msg", "读取excel失败！");
			return map;
		}  
		Date sDate = new Date();
		List<Preload> errorList = new ArrayList<Preload>();

		for (Preload sim : simList) {
			if (StringUtils.isBlank(sim.getCall_letter())) {
				sim.setRemark("车载呼号为空");
				errorList.add(sim);
				continue;
			}
			
			if (StringUtils.isBlank(sim.getBarcode())) {
				sim.setRemark("序列号(机身条码)为空");
				errorList.add(sim);
				continue;
			}
			
			Preload tempPreload = preloadDao.getPreloadByCl(sim.getCall_letter());
			if(tempPreload == null){
				sim.setRemark("车载呼号不存在");
				errorList.add(sim);
				continue;
			}
			
			if( !StringUtils.isBlank(sim.getVin()) ){
					if(!sim.getVin().equals(tempPreload.getVin())){
						sim.setRemark("车载呼号和VIN码不匹配");
						errorList.add(sim);
						continue;
					}else{
						if(!sim.getBarcode().equals(tempPreload.getBarcode())){
							sim.setRemark("车载呼号和机身条码不匹配");
							errorList.add(sim);
							continue;
						}
					}
			}else{
				if(!sim.getBarcode().equals(tempPreload.getBarcode())){
					sim.setRemark("车载呼号和机身条码不匹配");
					errorList.add(sim);
					continue;
				}
			}

			PreloadBk bk = new PreloadBk();
			BeanUtils.copyProperties(bk, tempPreload);
			preloadDao.save(bk);
			
			tempPreload.setFlag(type);
			preloadDao.update(tempPreload);
		}
	    Date eDate = new Date();
        long runTime =  (eDate.getTime()-sDate.getTime())/1000;
		map.put("success", true);
		map.put("total", total);
		map.put("runTime", runTime);
		map.put("failNum", errorList.size());
		map.put("errorData", errorList);
		return map;
	}
	
	/*class CallAbleTest implements Callable<List<Preload> >{
		

		private List<Preload> list;
		CallAbleTest(List<Preload> list){
			this.list = list;
		}
		
		@Override
		public List<Preload>  call() throws Exception {
			List<Preload> errorList = new ArrayList<Preload>();
			for (Preload sim : list) {
					if (StringUtils.isBlank(sim.getCall_letter())) {
						sim.setRemark("车载呼号为空");
						errorList.add(sim);
						continue;
					}
					
					Preload tempPreload = preloadDao.getPreloadByCl(sim.getCall_letter());
					if(tempPreload == null){
						sim.setRemark("车载呼号不存在");
						errorList.add(sim);
						continue;
					}
					
					if( !StringUtils.isBlank(sim.getVin()) ){
							if(!sim.getVin().equals(tempPreload.getVin())){
								sim.setRemark("车载呼号和VIN码不匹配");
								errorList.add(sim);
								continue;
							}
					}

					PreloadBk bk = new PreloadBk();
					BeanUtils.copyProperties(bk, tempPreload);
					Session session = sessionFactory.openSession();
					Transaction tran =  session.beginTransaction();
					session.save(bk);
					tempPreload.setFlag(3);
					session.update(tempPreload);
					tran.commit();
					session.close();
			}
			return errorList;
		}
	}*/
	
	@Override
	public Map<String, Object> batchUpdateSimFlagThread(List<String[]> dataList,
			Long compannyId, Long userId, Integer type) throws SystemException {
/*
		Map<String, Object> map = new HashMap<String, Object>();
		int total = dataList.size();
		defValMap.put("subco_no", compannyId);
		defValMap.put("op_id", userId);
		defValMap.put("stamp",DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME24));
		List<Preload> simList = new ArrayList<Preload>();

		try {
			simList = (List<Preload>) ReflectUtils.buildListForImport(
					dataList, updaeSimcolsMap, defValMap, transfMap, new Preload());
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "解析excel出错！");
			return map;
		}
		if (simList.isEmpty()) {
			map.put("success", false);
			map.put("msg", "读取excel失败！");
			return map;
		}  
		int upadteNum = 0;
		int failNum = 0;
		String message = "";
		Date sDate = new Date();
		List<List<Preload>> groupList = new ArrayList<List<Preload>>();
		//使用的多线程进行更新
		 int length = simList.size();
		 for(int i = 0; i < length; i++){
				List<Preload> tempList = new ArrayList<Preload>();
		    	int j = i*20; // 4 
		    	int endLoop = j+20;
		    	if(endLoop > length){
		    		endLoop = length;
		    	}
		    	
		    	for(; j < endLoop; j++){  // 
		    		tempList.add(simList.get(j));
		    	}
		    	if(j == endLoop){
			    	groupList.add(tempList);
		    	}
		  }
		 
	    List<Preload> errorList = new ArrayList<Preload>();

		ExecutorService executorService = Executors.newFixedThreadPool(groupList.size());
		System.err.println("线程个数  "+groupList.size());
		
		List<Future<List<Preload>>> futureList = new ArrayList<Future<List<Preload>>>();
		
		for (int i = 0; i < groupList.size(); i++) {
			futureList.add( executorService.submit(this.new CallAbleTest(groupList.get(i))) );
		}
		
		for (int i = 0; i < futureList.size(); i++) {
			Future<List<Preload>> futureTemp = futureList.get(i);
			List<Preload> resTemp = null;
			try {
				resTemp = futureTemp.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			errorList.addAll(resTemp);
		}
		
	    Date eDate = new Date();
        long runTime =  (eDate.getTime()-sDate.getTime())/1000;
		map.put("success", true);
		map.put("total", total);
		map.put("runTime", runTime);
		map.put("failNum", errorList.size());
		map.put("errorData", errorList);*/
		return null;
	}

}
