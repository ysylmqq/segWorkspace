package com.gboss.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gboss.comm.SystemException;
import com.gboss.pojo.UpgradeFile;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;


/**
 * @Package:com.gboss.service
 * @ClassName:UpgradeService
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:54:57
 */
public interface UpgradeFileService extends BaseService {

	public Page<HashMap<String, Object>> getUpgradeFilePage(HttpServletRequest request,Long companyId, PageSelect<Map<String, Object>> pageSelect) throws SystemException;
	
	public List<UpgradeFile> getUpgradeFileList();
	
	public UpgradeFile getUpgradeFileByname(String name);
	
}


