package cc.chinagps.seat.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.BriefTable;
import cc.chinagps.seat.bean.table.ServerTypeTable;
import cc.chinagps.seat.dao.TelDao;
import cc.chinagps.seat.service.TelService;
import cc.chinagps.seat.util.Consts;

@Service
public class TelServiceImpl implements TelService {

	@Autowired
	private TelDao telDao;
	
	@Override
	public List<BriefTable> getBriefList(BigInteger vehicleId,String phone, int count) {
		List<BriefTable> first_ret = telDao.getBriefList(vehicleId,phone, count);
		List<BriefTable> last_ret = new ArrayList<BriefTable>();
		for(BriefTable bt : first_ret){
			String contents = bt.getContent();
			StringBuffer ret_content = new StringBuffer();
			if(contents!=null&& !"".equals(contents)){
				String[] st_ids = contents.split(",");
				for(String st_id:st_ids){
					ServerTypeTable stt = Consts.SERVICE_TYPE_CACHE.get(st_id);
					if(stt != null){
						ret_content.append(stt.getSt_name()).append(",");
					}
				}
				bt.setContent(ret_content.toString());
			}
			last_ret.add(bt);
		}
		return last_ret;
	}

	@Override
	public long saveBrief(BriefTable table) {
		table.generateServiceContent();
		return telDao.saveBrief(table);
	}

}
