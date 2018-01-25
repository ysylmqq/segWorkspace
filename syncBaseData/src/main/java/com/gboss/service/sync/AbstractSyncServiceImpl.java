package com.gboss.service.sync;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.SyncDate;
import com.gboss.util.DataFetcher;
import com.gboss.util.DateUtil;

@Service
public abstract class AbstractSyncServiceImpl extends SycnAdapterService {
	
	private final Lock  lock = new ReentrantLock(); 
	
	/**
	 * 自定义计划执行任务方法
	 */
	public void run(){
		try {
			lock.lock();
			execute() ;
		} catch (Exception e) {
			SystemConst.E_LOG.error(getTranslation(getAPI_NAME()) + "同步程序出错!", e);
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * spring-task 同步执行方法
	 */
	public void execute() {
		SystemConst.D_LOG.debug(getAPI_NAME() + "->execute() start：##");
		Long start_date = null;
		Long end_date = null;
		boolean flag = false;//数据库是否能访问
		SyncDate syncDate = null;
		String url = systemconfig.getSysnDadaUrl();
		url = url.concat(getRequestURI(getAPI_NAME())).concat("?1=1");
		int i = 0;// 接口调用次数
		// 获取车系同步时间对象
		try{
			syncDate = syncDateService.getSyncDateByName(getAPI_NAME());
			flag = true;
		}catch(RecoverableDataAccessException e){
			SystemConst.E_LOG.error("数据库访问失败，同步结束");
			return;
		}catch(CannotGetJdbcConnectionException e){
			SystemConst.E_LOG.error("获取JDBC链接失败，同步结束");
			return;
		}catch (Throwable e) {
			SystemConst.E_LOG.error("获取JDBC链接失败，同步结束");
			return;
		}
		
		if (syncDate != null && syncDate.getEnd_time() != null) {
			System.out.println(DateUtil.formatNowTime() + " " + getTranslation(getAPI_NAME()) + " 同步开始");
			Date s_date = syncDate.getBegin_time();
			Date e_date = syncDate.getEnd_time();
			try {
				end_date = syncDate.getEnd_time().getTime();
				boolean flag_execute = judgTime(end_date);
				SystemConst.D_LOG.info("<<judgTime>>");
				if (!flag_execute) {
					System.out.println(DateUtil.formatNowTime() + " 未到同步执行时间!");
					return ;
				}
				setTime(syncDate, false);
				SystemConst.D_LOG.info("<<setTime>>");
				start_date = syncDate.getBegin_time().getTime();
				end_date = syncDate.getEnd_time().getTime();
				sycnHandler(start_date, end_date, url, flag, i);
				SystemConst.D_LOG.info("<<sycnHandler>>");
				// 更新数据库T_IF_SYNC时间
				syncDateService.update(syncDate);
				SystemConst.D_LOG.info("<<syncDateService.update(syncDate)>>");
				System.out.println(DateUtil.formatNowTime() + " " + getTranslation(getAPI_NAME()) + " 同步结束");
				System.out.println();
			} catch (ClientProtocolException e) {
				syncDate.setBegin_time(s_date);
				syncDate.setEnd_time(e_date);
				syncDateService.update(syncDate);
				System.out.println(DateUtil.formatNowTime() + " " + getTranslation(getAPI_NAME()) + " 同步失败  ClientProtocolException " + e.getMessage());
			} catch (ConnectTimeoutException e) {
				syncDate.setBegin_time(s_date);
				syncDate.setEnd_time(e_date);
				syncDateService.update(syncDate);
				System.out.println(DateUtil.formatNowTime() + " " + getTranslation(getAPI_NAME()) + "同步失败  ConnectTimeoutException "+ e.getMessage());
			} catch (SystemException e) {
				syncDate.setBegin_time(s_date);
				syncDate.setEnd_time(e_date);
				syncDateService.update(syncDate);
				System.out.println(DateUtil.formatNowTime() + " " + getTranslation(getAPI_NAME()) + " 同步失败  SystemException "+ e.getMessage());
			} catch (IOException e) {
				syncDate.setBegin_time(s_date);
				syncDate.setEnd_time(e_date);
				syncDateService.update(syncDate);
				System.out.println(DateUtil.formatNowTime() + " " + getTranslation(getAPI_NAME()) + " 同步失败  IOException "+ e.getMessage());
			}
		} else {
			try {
				// 首次执行
				System.out.println(DateUtil.formatNowTime() + " 首次" + getTranslation(getAPI_NAME()) + " 首次执行开始");
				System.out.println(DateUtil.formatNowTime() + " 请求:" + url);
				syncDate = new SyncDate();
				setTime(syncDate, true);
				// 同步数据
				start_date = syncDate.getBegin_time().getTime();
				end_date = syncDate.getEnd_time().getTime();
				sycnHandler(start_date, end_date, url, flag, i);
				syncDate.setIf_name(getAPI_NAME());
				syncDate.setSubco_no(201L);
				syncDateService.save(syncDate);
				System.out.println(DateUtil.formatNowTime() + " 首次" + getTranslation(getAPI_NAME()) + "首次同步结束");
				System.out.println();
			} catch (ClientProtocolException e) {
				SystemConst.E_LOG.error("首次" + getTranslation(getAPI_NAME()) + "同步失败  ClientProtocolException "+ e.getMessage());
				System.out.println();
			} catch (ConnectTimeoutException e) {
				SystemConst.E_LOG.error("首次" + getTranslation(getAPI_NAME()) + "同步失败  ConnectTimeoutException "+ e.getMessage(),e);
				System.out.println();
			} catch (SystemException e) {
				SystemConst.E_LOG.error("首次" + getTranslation(getAPI_NAME()) + "同步失败  SystemException "+ e.getMessage(),e);
				System.out.println();
			} catch (IOException e) {
				SystemConst.E_LOG.error("首次" + getTranslation(getAPI_NAME()) + "同步失败 IOException " + e.getMessage(),e);
				System.out.println();
				System.out.println();
			} catch (Exception e) {
				SystemConst.E_LOG.error("首次" + getTranslation(getAPI_NAME()) + "同步失败 Exception ",e);
				System.out.println();
			}
		}
		SystemConst.D_LOG.debug(getAPI_NAME() + "->execute() end：##");
		SystemConst.D_LOG.debug("-----------------分----割----线-----------------");
	}
	
	/**
	 * 同步方法
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws Exception 
	 */
	public Map<String,String> sycnHandler(Long start_date, Long end_date,String url,boolean flag,int i) throws ConnectTimeoutException, ClientProtocolException, IOException, SystemException{
		SystemConst.D_LOG.info(">> sycnHandler start:");
		Map<String,String> result = new HashMap<String, String>(); 
		String msg = "";
		List<HashMap<String, Object>> results = DataFetcher.getSyncData(start_date, end_date, url, flag, i);
		if(results == null){
			msg = " "+getTranslation(getAPI_NAME())+"接口没有同步数据";
			System.out.println(DateUtil.formatNowTime() + msg );
			result.put("msg", msg);
			SystemConst.D_LOG.info(">> sycnHandler end:" + msg);
			return result;
		}
		System.out.println(DateUtil.formatNowTime() + " "+ getAPI_NAME() +  "["+results.size()+"]条,内容:" + results);
		Map<String,String> ret_result = businessHandle(results,result,msg);
		SystemConst.D_LOG.info(">> sycnHandler end:");
		return  ret_result;
	}
	
	/**
	 * 业务处理方法
	 * @param start_date
	 * @param end_date
	 * @param url
	 * @param flag
	 * @param i
	 * @return
	 * @throws Exception 
	 */
	public abstract Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws ClientProtocolException, IOException, SystemException ;

	/**
	 * 人工同步
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> syncByMan(String url) throws Exception {
		return sycnHandler(null, null, url,false, 0);
	}
	
}
