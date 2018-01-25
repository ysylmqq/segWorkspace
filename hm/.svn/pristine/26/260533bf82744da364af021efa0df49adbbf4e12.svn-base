package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.UpgradeFileDao;
import com.gboss.pojo.UpgradeFile;
import com.gboss.service.UpgradeFileService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:UpgradeServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2015-1-27 上午8:55:42
 */
@Service("upgradeFileService")
@Transactional
public class UpgradeFileServiceImpl extends BaseServiceImpl implements
		UpgradeFileService {

	@Autowired
	@Qualifier("upgradeFileDao")
	private UpgradeFileDao upgradeFileDao;

	@Override
	public Page<HashMap<String, Object>> getUpgradeFilePage(
			HttpServletRequest request, Long companyId,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total = upgradeFileDao.countpgradeFile(companyId, pageSelect.getFilter());
		List<HashMap<String, Object>> list = upgradeFileDao.getUpgradeFileList(
				companyId, pageSelect.getFilter(), pageSelect.getOrder(),
				pageSelect.getIs_desc(), pageSelect.getPageNo(),
				pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list,
				pageSelect.getPageSize());
	}

	@Override
	public List<UpgradeFile> getUpgradeFileList() {
		return upgradeFileDao.getUpgradeFileList();
	}

	@Override
	public UpgradeFile getUpgradeFileByname(String name) {
		return upgradeFileDao.getUpgradeFileByname(name);
	}
	

}

