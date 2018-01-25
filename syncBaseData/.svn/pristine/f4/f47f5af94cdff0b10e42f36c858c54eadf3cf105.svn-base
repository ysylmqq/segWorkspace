package com.gboss.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ldap.objectClasses.CommonCompany;
import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConfig;
import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.PreloadDao;
import com.gboss.dao.UnitDao;
import com.gboss.dao.VehicleDao;
import com.gboss.pojo.Preload;
import com.gboss.service.PreloadService;
import com.gboss.util.DateUtil;
import com.gboss.util.ReflectUtils;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:SimServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-9-23 上午11:14:18
 */
@Service("preloadService")
public class PreloadServiceImpl extends BaseServiceImpl implements
		PreloadService {

	@Autowired
	@Qualifier("preloadDao")
	private PreloadDao preloadDao;

	@Autowired
	@Qualifier("VehicleDao")
	private VehicleDao VehicleDao;

	@Autowired
	@Qualifier("UnitDao")
	private UnitDao unitDao;

	@Autowired
	private SystemConfig systemconfig;

	private OpenLdap ldap = OpenLdapManager.getInstance();

	public String getLocalFilePath() {
		/*
		 * WebApplicationContext webContext = ContextLoaderListener
		 * .getCurrentWebApplicationContext(); String cl_path =
		 * webContext.getServletContext().getRealPath("/"); cl_path =
		 * cl_path.concat("/obdFile/");
		 */
		String cl_path = "";
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
			String obdUrl = "" + "sosobd/";
			String obdlinuxUrl = "";
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
						downloadFile(obdExcel_path, localFilePath + file_name
								+ ".xls");
					}
				}
				// 处理远程下载过来的excel文件
				readfile(date, localFilePath, downLoadPath, str_date);

			} else {
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
					System.out.println(filepath + "----$$$$$$---------"
							+ filelist[i]);
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
		String ip = "";
		boolean flag = false;
		String obd_port = "";
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
		/* colsMap_sim.put("telco", "9"); */

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

	// 读取excel时默认设置值
	private Map<String, Object> defValMap = new HashMap<String, Object>();
	{

	}

	private Map<String, Map<String, String>> transfMap = new HashMap<String, Map<String, String>>();
	{
		/*
		 * Map<String, String> sim_Map = new HashMap<String, String>();
		 * sim_Map.put("移动", "1"); sim_Map.put("联通", "2"); sim_Map.put("电信",
		 * "3"); transfMap.put("telco", sim_Map);
		 */
	}

	 
	public Map<String, Object> importSim(List<String[]> dataList,
			Long compannyId, Long userId, Integer type, Integer telco)
			throws SystemException {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = dataList.size();
		defValMap.put("subco_no", compannyId);
		defValMap.put("op_id", userId);
		defValMap.put("stamp",
				DateUtil.format(new Date(), DateUtil.YMD_DASH_WITH_FULLTIME24));
		List<Preload> simList = new ArrayList<Preload>();
		try {
			if (type == 3) {
				simList = ReflectUtils.buildListForImport(
						dataList, colsMap, defValMap, transfMap, new Preload());
			} else if (type == 1) {
				if (telco != null) {
					defValMap.put("telco", telco);
				}
				simList = ReflectUtils.buildListForImport(
						dataList, colsMap_sim, defValMap, transfMap,
						new Preload());
			} else if (type == 2) {
				simList = ReflectUtils.buildListForImport(
						dataList, colsMap_sim_tbox, defValMap, transfMap,
						new Preload());
			} else if (type == 4) {
				simList = ReflectUtils.buildListForImport(
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
							/*	PreloadBk bk = new PreloadBk();
								BeanUtils.copyProperties(bk, old_sim);
								// 保存到历史表
								preloadDao.save(bk);*/
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
						/*	PreloadBk bk = new PreloadBk();
							BeanUtils.copyProperties(bk, sm);
							// 保存到历史表
							preloadDao.save(bk);*/
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
		}
		/*
		 * message = "导入新增<span style=color:green>" + addNum +
		 * "</span>条，更新<span style=color:green>"+ upadteNum +"</span>条<br />"
		 * +message;
		 */
		map.put("success", true);
		map.put("msg", message);
		return map;
	}

	 
	public Preload getPreloadByCl(String call_letter) throws SystemException {
		return preloadDao.getPreloadByCl(call_letter);
	}

	 
	public void gethmCall_letter() throws Exception {
	/*	Map<String, String> call_letterMap = SystemConst.call_letterMap;
		String call_letter = unitDao.getHmCall_letters(201L);
		call_letterMap.put("call_letter", call_letter);*/
	}

	 
	public Preload getPreloadByBarCode(String barcode) throws SystemException {
		return preloadDao.getPreloadByBarcode(barcode);
	}

	 
	public Preload getPreloadByVin(String vin) throws SystemException {
		return preloadDao.getPreloadByVin(vin);
	}

	 
	public Preload getPreloadByVinBarcode(String vin, String barcode) throws SystemException {
		return preloadDao.getPreloadByVinBarcode(vin, barcode);
	}

	 
	public void batchUpdateSim(List<Preload> sims) throws SystemException {
		preloadDao.batchUpdateSim(sims);
	}

	 
	public void updateSim(Preload sim) {
		preloadDao.updateSim(sim);
	}

	 
	public void saveSim(Preload sim) {
		preloadDao.saveSim(sim);
	}

	 
	public List<Preload> getPreloadsByVin(String vin) throws SystemException {
		return preloadDao.getPreloadsByVin(vin);
	}

}
