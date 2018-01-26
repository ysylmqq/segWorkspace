package cc.chinagps.seat.dao;

import java.util.List;

import cc.chinagps.seat.bean.table.ServerTypeCompanyTable;
import cc.chinagps.seat.bean.table.ServerTypeTable;
import cc.chinagps.seat.bean.table.SysValueTable;

public interface ServerTypeDao {
	List<ServerTypeTable> getServerTypeList(ServerTypeTable serverType);
	List<ServerTypeTable> getServerTypeAllList(ServerTypeTable serverType);
	List<ServerTypeTable> getBaseServerTypeList(Integer callType);
	Integer saveServerType(ServerTypeTable table);
	Integer deleteServerType(Integer id);
	List<ServerTypeCompanyTable> getServerTypeCompany(Integer Company);
	Integer saveServerTypeCompany(ServerTypeCompanyTable table);
	Integer deleteServerTypeCompany(Integer id);
	List<SysValueTable> getOrgList();
	List<ServerTypeTable>getServerByOrgList(Integer orgId,Integer calltype);
	//获取所有的服务类型当缓存用
	List<ServerTypeTable>getServertypes();
	void clearServers(Integer orgId);
}
