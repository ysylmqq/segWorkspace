package cc.chinagps.seat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.chinagps.seat.bean.table.ServerTypeCompanyTable;
import cc.chinagps.seat.bean.table.ServerTypeTable;
import cc.chinagps.seat.bean.table.SysValueTable;
import cc.chinagps.seat.dao.ServerTypeDao;
import cc.chinagps.seat.service.ServerTypeService;

@Service("serverTypeService")
public class ServerTypeServiceImpl implements ServerTypeService {

	@Autowired
	private ServerTypeDao serverTypeDao;

	@Override
	public List<ServerTypeTable> getServerTypeList(ServerTypeTable serverType) {
		List<ServerTypeTable> baseList= serverTypeDao.getBaseServerTypeList((int)serverType.getCall_type());
		List<ServerTypeTable> childList = serverTypeDao.getServerTypeList(serverType);
		for(ServerTypeTable child:childList){
			for(ServerTypeTable base:baseList){
				if(base.getSt_id() == child.getP_id()){
					if(base.getChildList() == null){
						base.setChildList(new ArrayList<ServerTypeTable>());
					}
					base.getChildList().add(child);
				}
			}
		}
		return baseList;
	}
	
	@Override
	public List<ServerTypeTable> getServerTypeAllList(ServerTypeTable serverType) {
		List<ServerTypeTable> baseList= serverTypeDao.getBaseServerTypeList((int)serverType.getCall_type());
		List<ServerTypeTable> childList = serverTypeDao.getServerTypeAllList(serverType);
		for(ServerTypeTable child:childList){
			for(ServerTypeTable base:baseList){
				if(base.getSt_id() == child.getP_id()){
					if(base.getChildList() == null){
						base.setChildList(new ArrayList<ServerTypeTable>());
					}
					base.getChildList().add(child);
				}
			}
		}
		return baseList;
	}

	@Override
	public Integer saveServerType(ServerTypeTable table) {
		return serverTypeDao.saveServerType(table);
	}

	@Override
	public Integer deleteServerType(Integer id) {
		return serverTypeDao.deleteServerType(id);
	}

	@Override
	public List<ServerTypeCompanyTable> getServerTypeCompany(Integer Company) {
		return serverTypeDao.getServerTypeCompany(Company);
	}

	@Override
	public Integer saveServerTypeCompany(ServerTypeCompanyTable table) {
		return serverTypeDao.saveServerTypeCompany(table);
	}

	@Override
	public Integer deleteServerTypeCompany(Integer companyId) {
		return serverTypeDao.deleteServerTypeCompany(companyId);
	}

	@Override
	public List<SysValueTable> getOrgList() {
		return serverTypeDao.getOrgList();
	}

	@Override
	public List<ServerTypeTable> getServerByOrgList(Integer orgId ,Integer calltype) {
		List<ServerTypeTable> baseList= serverTypeDao.getBaseServerTypeList(calltype);
		List<ServerTypeTable> childList = serverTypeDao.getServerByOrgList(orgId,calltype);
		for(ServerTypeTable child:childList){
			for(ServerTypeTable base:baseList){
				if(base.getSt_id() == child.getP_id()){
					if(base.getChildList() == null){
						base.setChildList(new ArrayList<ServerTypeTable>());
					}
					base.getChildList().add(child);
				}
			}
		}
		return baseList;
	}

	@Override
	public void clearServers(Integer orgId) {
		serverTypeDao.clearServers(orgId);
	}

	@Override
	public List<ServerTypeTable> getServertypes() {
		return serverTypeDao.getServertypes();
	}
	

}
