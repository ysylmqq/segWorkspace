package com.gboss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.comm.SystemException;
import com.gboss.dao.PackDao;
import com.gboss.pojo.Pack;
import com.gboss.service.PackService;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;
import com.gboss.util.page.PageUtil;

/**
 * @Package:com.gboss.service.impl
 * @ClassName:PackServiceImpl
 * @Description:TODO
 * @author:bzhang
 * @date:2014-11-5 下午3:57:46
 */
@Service("packService")
@Transactional
public class PackServiceImpl extends BaseServiceImpl implements PackService {

	@Autowired  
	@Qualifier("packDao")
	private PackDao packDao;

	@Override
	public Page<HashMap<String, Object>> findPackPage(Long companyno,
			PageSelect<Map<String, Object>> pageSelect) throws SystemException {
		int total=packDao.countPack(companyno,pageSelect.getFilter());
		List<HashMap<String, Object>> list=packDao.findPackList(companyno,pageSelect.getFilter(), pageSelect.getOrder(), pageSelect.getIs_desc(),pageSelect.getPageNo(),pageSelect.getPageSize());
		return PageUtil.getPage(total, pageSelect.getPageNo(), list, pageSelect.getPageSize());
	}

	@Override
	public int addPack(Pack pack) {
		int result=1;
		try {
			if(packDao.isExist(pack)){
				result = 2;
			}else{
				packDao.save(pack);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean isExist(Pack pack) throws SystemException {
		return packDao.isExist(pack);
	}
	
	@Override
	public int delete(List<Long> ids) throws SystemException {
		int result=1;
		if(ids==null || ids.isEmpty()){
			result=-1;
		}else{
			packDao.delete(ids);
		}
		return result;
	}
	
}

