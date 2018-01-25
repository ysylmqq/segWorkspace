package bhz.mst.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import bhz.mst.dao.MstSiteDao;
import bhz.mst.facade.MstSiteFacade;

@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass=bhz.mst.facade.MstSiteFacade.class,
 protocol = {"dubbo"})
public class MstSiteService implements MstSiteFacade {

	@Autowired
	private MstSiteDao mstSiteDao;

	@Override
	public List<JSONObject> getList() throws Exception {
		return mstSiteDao.getList();
	}
}
