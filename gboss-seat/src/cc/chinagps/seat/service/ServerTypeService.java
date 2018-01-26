package cc.chinagps.seat.service;

import java.util.List;

import cc.chinagps.seat.bean.table.ServerTypeCompanyTable;
import cc.chinagps.seat.bean.table.ServerTypeTable;
import cc.chinagps.seat.bean.table.SysValueTable;

public interface ServerTypeService {
	List<ServerTypeTable> getServerTypeList(ServerTypeTable serverType);
	List<ServerTypeTable> getServerTypeAllList(ServerTypeTable serverType);
	Integer saveServerType(ServerTypeTable table);
	Integer deleteServerType(Integer id);
	List<ServerTypeCompanyTable> getServerTypeCompany(Integer Company);
	Integer saveServerTypeCompany(ServerTypeCompanyTable table);
	Integer deleteServerTypeCompany(Integer companyId);
	List<SysValueTable> getOrgList();
	List<ServerTypeTable>getServerByOrgList(Integer orgId,Integer calltype);
	void clearServers(Integer orgId);
	
	//获取所有的服务类型当缓存用
	List<ServerTypeTable>getServertypes();
}
