package com.hm.bigdata.service;

import com.hm.bigdata.comm.SystemException;
import com.hm.bigdata.entity.po.sys.Operatelog;

/**
 * @Package:com.chinagps.fee.service
 * @ClassName:OperatelogService
 * @Description:操作日志业务层接口
 * @author:zfy
 * @date:2014-5-27 上午10:29:44
 */
public interface OperatelogService extends BaseService{
	/**
	 * @Title:add
	 * @Description:添加操作日志
	 * @param operatelog
	 * @throws SystemException
	 */
	public void add(Operatelog operatelog) throws SystemException;
}

