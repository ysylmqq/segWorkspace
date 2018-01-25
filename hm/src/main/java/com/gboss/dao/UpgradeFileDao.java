package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.UpgradeFile;


/**
 * @Package:com.gboss.dao
 * @ClassName:UpgradeDao
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:52:09
 */
public interface UpgradeFileDao extends BaseDao {

	public List<HashMap<String, Object>> getUpgradeFileList(Long companyId, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countpgradeFile(Long companyId, Map<String, Object> conditionMap) throws SystemException;
	
	public List<UpgradeFile> getUpgradeFileList();
	
	public UpgradeFile getUpgradeFileByname(String name) ;
	
}

