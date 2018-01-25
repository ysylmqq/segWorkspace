package com.gboss.service;

import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Serviceitem;

/**
 * @Package:com.gboss.service
 * @ClassName:ServiceItemService
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-2 下午4:46:19
 */
public interface ServiceItemService extends BaseService {
	
	public List<Serviceitem> findServiceitem(Serviceitem serviceitem) throws SystemException ;

}

