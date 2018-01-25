package com.gboss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import ldap.oper.OpenLdap;
import ldap.oper.OpenLdapManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.chinagps.gboss.comcenter.buff.AlarmArrayDataBuff.NewAlarm;
import cc.chinagps.gboss.comcenter.interprotocol.clienttest.CenterClientHandler;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.dao.UpgradeDao;
import com.gboss.dao.UpgradeFileDao;
import com.gboss.pojo.Preload;
import com.gboss.pojo.Upgrade;
import com.gboss.pojo.UpgradeFile;
import com.gboss.service.UpgradeService;
import com.gboss.util.DateUtil;
import com.gboss.util.PageSelect;
import com.gboss.util.ReflectUtils;
import com.gboss.util.StringUtils;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:UpgradeServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:55:42
 */
@Service("upgradeService")
@Transactional
public class UpgradeServiceImpl extends BaseServiceImpl implements
		UpgradeService {

	@Autowired
	@Qualifier("upgradeDao")
	private UpgradeDao upgradeDao;
	
	@Autowired 
	@Qualifier("upgradeFileDao")
	private UpgradeFileDao upgradeFileDao;
	
	private OpenLdap ldap = OpenLdapManager.getInstance();
	
	@Override
	public Page<HashMap<String, Object>> getSimUpgradePage(HttpServletRequest request, Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = upgradeDao.countSimUpgrade(companyId, pageSelect.getFilter());
		List<HashMap<String, Object>> list = upgradeDao.getSimUpgradeList(
				companyId, pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		//把查询最后的升级数据保存
		String call_letter = upgradeDao.getSimUpgradeletters(companyId,pageSelect.getFilter());
		request.getSession().setAttribute("call_letters", call_letter);
		
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

	@Override
	public List<Upgrade> getUpgradeList(){
		return upgradeDao.getUpgradeList();
	}
	
	@Override
	public List<Upgrade> getUpgradeAllList(){
		return upgradeDao.getUpgradeAllList();
	}

	@Override
	public Upgrade getUpgradeBycall_letter(String call_letter) {
		return upgradeDao.getUpgradeBycall_letter(call_letter);
	}

	@Override
	public List<HashMap<String, Object>> getSimUpgradeList(Long companyId,
			Map<String, Object> conditionMap) throws SystemException {
		List<HashMap<String, Object>> list = upgradeDao.getSimUpgradeList(companyId, conditionMap, null, false, 0, 0);
		return list;
	}

	@Override
	public List<Upgrade> getUpgradeListforCur_ver() {
		return upgradeDao.getUpgradeListforCur_ver();
	}
	
	@Override
	public void removeAndInputupgrade() {/*
		ConcurrentHashMap <String, Upgrade>wait_map=SystemConst.hm_call_letter_upgrade;
		CenterClientHandler handeler = SystemConst.clienthandler;
		Iterator<String> wait_keys = wait_map.keySet().iterator();
		while(wait_keys.hasNext()) {
			if(SystemConst.clienthandler.isLogined()){
			   String key = (String) wait_keys.next();
			   Upgrade up=wait_map.get(key);  
			   ArrayList<String> callletterList=new ArrayList<String>();			  
			   callletterList.add(key);
			   if((up.getFlag()==1 || up.getFlag()==2) && up.getSend_total()<10 && (StringUtils.isNullOrEmpty(up.getSend_searchcar()) || is_overtime(up.getSend_searchcar(),1))){
				   //查车指令总发送次数少于4次，间隔5分钟以上
				   String result=handeler.SendCommand(callletterList, 0x1, null,true);
				   if(!result.equals(""))
				   {//设置状态查车指令已发下
					   up.setFlag(2);
					   System.out.println(DateUtil.formatNowTime() +" 呼号："+up.getCall_letter()+"---第"+(up.getSend_total()+1)+"次查车指令下发，上次下发时间："+DateUtil.format(up.getSend_searchcar(), "yyyy-mm-dd hh:mm:ss")+"  cmd_sn="+result);
				   }
				   up.setSend_searchcar(new Date());
				   up.setSend_total(up.getSend_total()+1);
				   upgradeDao.update(up);
				   try {
					Thread.sleep(10000);//10秒发一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			   }
		}
		}
   */}
	
	public  boolean is_overtime(Date stamp,int n){
		   Date now = new Date();
		   long between = (now.getTime() - stamp.getTime())/1000;
		   if(between >= n*60){
			   return true;
		   }
		   return false;
	}
	
	
	private Map<String, String> colsMap = new HashMap<String, String>();
	{
		colsMap.put("vin", "2");
		colsMap.put("barcode", "3");
		colsMap.put("ug_ver", "4");
	}
	
	// 读取excel时默认设置值
	private Map<String, Object> defValMap = new HashMap<String, Object>();
	{

	}
	private Map<String, Map<String, String>> transfMap = new HashMap<String, Map<String, String>>();
	{}
	@Override
	public String importUpgrade(List<String[]> dataList,
			Long compannyId, Long userId,String upgradeName,ArrayList<String> params) throws SystemException {
		defValMap.put("subco_no", compannyId);
		defValMap.put("op_id", userId);
		defValMap.put("upgradeName", upgradeName);
		List<Upgrade> simList = new ArrayList<Upgrade>();
		try {
			simList = (List<Upgrade>) ReflectUtils.buildListForImport(dataList,
					colsMap, defValMap, transfMap, new Upgrade());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (simList.isEmpty()) {			
			return null;
		}	
		ConcurrentHashMap <String, Upgrade> table = SystemConst.hm_call_letter_upgrade;
		ConcurrentHashMap <String, Upgrade> tableAll = SystemConst.hm_call_letter_upgradeAll;
		
		Map<String, UpgradeFile> fileMap = this.getFileName();
		Map<String, Preload> preloadMap = this.getPreloadAll();
		Map<String, Upgrade> upgradeMap = this.getUpgradeAll();
		
		
		int i=0,j=0,k=0;
		StringBuffer sb_j = new StringBuffer();
		StringBuffer sb_k = new StringBuffer();
		for (Upgrade up : simList) {						
			try {
				//UpgradeFile upgradeFile = upgradeFileDao.getUpgradeFileByname(up.getUg_ver());
				UpgradeFile upgradeFile = this.getFile(up.getUg_ver(),fileMap);
				if(StringUtils.isNotNullOrEmpty(upgradeFile)){
					up.setUg_ver(upgradeFile.getFilename());
					params.add(upgradeFile.getFilename());
					//Preload preload= upgradeDao.getSimInfo(up.getVin(), up.getBarcode());
					Preload preload = this.getPreload(up.getVin(), up.getBarcode(), preloadMap);
					if(StringUtils.isNotNullOrEmpty(preload)){
						String call_letter = preload.getCall_letter();
						if(call_letter.equals("14910477981")){
							System.out.println(i+"fadfa");
						}
						//Upgrade old_ug = upgradeDao.getUpgradeBycall_letter(call_letter);
						Upgrade old_ug = upgradeMap.get(call_letter);
						ArrayList<String> callList = new ArrayList<String>();
						callList.add(call_letter);
						if(null != old_ug){
							old_ug.setIp(params.get(0));
							old_ug.setPort(Integer.parseInt(params.get(1)));
							old_ug.setUg_ver(params.get(2));
							old_ug.setFlag(1);
							old_ug.setS_time(null);
							old_ug.setE_time(null);
							old_ug.setUpgradeName(upgradeName);
							upgradeDao.update(old_ug);
						}else{
							old_ug = new Upgrade();
							old_ug.setIp(params.get(0));
							old_ug.setPort(Integer.parseInt(params.get(1)));
							old_ug.setUg_ver(params.get(2));
							old_ug.setCall_letter(call_letter);
							old_ug.setFlag(1);
							old_ug.setCur_ver("");
							old_ug.setUpgradeName(upgradeName);
							upgradeDao.save(old_ug);
						}
						table.put(call_letter, old_ug);						
						tableAll.put(call_letter, old_ug);	
						i++;
				   }else{
					   if(StringUtils.isNotNullOrEmpty(up.getBarcode())){
						   sb_k.append(""+up.getBarcode()+" ");
					   }else{
						   sb_k.append(up.getVin()+up.getVin()+" ");
					   }
					   k++;
				   }
				}else{
					 if(StringUtils.isNotNullOrEmpty(up.getBarcode())){
						   sb_j.append(""+up.getBarcode()+" ");
					   }else{
						   sb_j.append(up.getVin()+up.getVin()+" ");
					   }
					j++;
				}
				
			} catch (Exception e) {
				e.printStackTrace();				
				return null;
				
			}

		}	
		String str = "提交总数："+(i+j+k)+"台，成功："+i+"台，失败"+(j+k)+"台 \\n";
		if(j>0){
			str += sb_j.toString()+" "+j+"台没有相应的升级文件！\\n";
		}
		if(k>0){
			str += sb_k.toString()+" "+k+"台Sim卡表中没数据！";
		}
		return str;

	}
	
	private Map<String, UpgradeFile> getFileName(){
		List<UpgradeFile> list = upgradeFileDao.getUpgradeFileList();
		Map<String, UpgradeFile> map = new HashMap<String, UpgradeFile>();
		for(UpgradeFile upgradeFile:list){
			map.put(upgradeFile.getFilename(), upgradeFile);
		}
		return map;
	}
	
	private Map<String, Preload> getPreloadAll() {
		List<Preload> list = upgradeDao.getSimAll();
		Map<String, Preload> map = new HashMap<String, Preload>();		
		for(Preload preload:list){
			if(StringUtils.isNotNullOrEmpty(preload.getVin()))
			    map.put(preload.getVin(), preload);
			if(StringUtils.isNotNullOrEmpty(preload.getBarcode()))
			    map.put(preload.getBarcode(), preload);
		}
		return map;
	}
	
	private Map<String, Upgrade> getUpgradeAll() {
		List<Upgrade> list = upgradeDao.getUpgradeAllList();
		Map<String, Upgrade> map = new HashMap<String, Upgrade>();
		for(Upgrade upgrade:list){
			map.put(upgrade.getCall_letter(), upgrade);
		}
		return map;
	}
	
	private Preload getPreload(String vin, String barcode,Map<String, Preload> preloadMap){
		if(StringUtils.isNotNullOrEmpty(preloadMap.get(vin))){
			return preloadMap.get(vin);
		}
		if(StringUtils.isNotNullOrEmpty(preloadMap.get(barcode))){
			return preloadMap.get(barcode);
		}
		return null;
	}
	
	private UpgradeFile getFile (String ug,Map<String, UpgradeFile> fileMap){
		 for (String key : fileMap.keySet()){
             if(StringUtils.isNotNullOrEmpty(ug) && key.contains(ug)){
            	 return fileMap.get(key);
             }
	     }
		return null;
	}
}

