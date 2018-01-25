package com.gboss.service.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.spy.memcached.MemcachedClient;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.gboss.comm.SystemConst;
import com.gboss.comm.SystemException;
import com.gboss.pojo.Operator;
import com.gboss.util.DateUtil;
import com.gboss.util.MemcachedUtil;
import com.gboss.util.StringUtils;

@Service("accountsSyncStrategyServices")
public class AccountsSyncStrategyServicesImpl extends AbstractSyncServiceImpl {
	
	private MemcachedClient mc = MemcachedUtil.getClient(false);
	
	public AccountsSyncStrategyServicesImpl(){
		super.setAPI_NAME(SystemConst.HM_ACCOUNT);
	}

	/**
	 * 账户同步业务处理
	 */
	public Map<String, String> businessHandle(List<HashMap<String, Object>> results,Map<String,String> result,String msg) throws SystemException, ClientProtocolException, IOException {
		System.err.println("开始同步客户信息");
		for (HashMap<String, Object> hashMap : results) {
			// 电话
			String phone = hashMap.get("phone") == null ? null : String.valueOf(hashMap.get("phone").toString());
			// 账户
			String username = hashMap.get("username") == null ? null: hashMap.get("username").toString();
			if(StringUtils.isNullOrEmpty(username)){
				msg = " 传入的用户名为空！";
				System.out.println(DateUtil.formatNowTime() + msg );
				result.put("msg", msg);
				continue;
			}
			username = username.trim();
			String vin = username.substring(3);

			// 密码
			String servicepwd = hashMap.get("password") == null ? null : hashMap.get("password").toString();
			if(StringUtils.isNullOrEmpty(servicepwd)){
				System.out.println(DateUtil.formatNowTime() + " 传入的服务密码为空！");
				msg = " 传入的服务密码为空！";
				result.put("msg", msg);
				continue;
			}
			servicepwd = servicepwd.trim();
			//放入缓存
			String key = "username:" + username;
			int exp = 0;//永不过期
			Map<String, Object>  accountInfo  = accountService.getAccountInfoByVin(vin);
			
			if(accountInfo == null){
				System.out.println(DateUtil.formatNowTime() + " vin="+vin+"，本地库查询不到相关信息!");
				msg = " vin="+vin+"，本地库查询不到相关信息!";
				result.put("msg", msg);
				continue;
			}
			
			Set<String> keys = accountInfo.keySet();
			Iterator<String> it = keys.iterator();
			String value = servicepwd.concat(",");
			while(it.hasNext()){
				String key_temp = it.next();
				String val = String.valueOf(accountInfo.get(key_temp));
				if(StringUtils.isNullOrEmpty(val)|| "null".equals(val)){
					val = "";
				}
				value = value.concat(val).concat(",");
			}
			
			value = value.substring(0,value.length()-1);
			System.out.println(DateUtil.formatNowTime() + " mamcached保存[key="+key +  " value=" + value+"]成功!");
			mc.set(key, exp, value);
			
			//保存到数据库开始  op_id, op_name（HM_VIN), subco_no, pwd
			Operator model = new Operator();
			model.setOp_name(username);
			model.setMobile(phone);
			model.setSubco_no(String.valueOf(SystemConst.HM_SUBCO_NO));
			model.setPwd(servicepwd);
			try {
				accountService.save(model);
			} catch (Exception e) {
				SystemConst.E_LOG.error("保存t_ba_operator信息出错!" , e);
			}
			//保存到数据库结束
			System.out.println( DateUtil.formatNowTime() + " 客户资料保存成功");
			msg = "客户资料保存成功!";
			result.put("msg", msg);
		}
		return result;
	}

}
