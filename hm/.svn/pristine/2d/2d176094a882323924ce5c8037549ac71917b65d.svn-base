package com.gboss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Vehicle;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:VehicleDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-3-24 下午2:56:45
 */
public interface VehicleDao extends BaseDao {
	
	public Long add(Vehicle vehicle);
	
	public Vehicle getVehicleByid(Long id);
	
	public Vehicle getVehicleByCallLetter(String call_letter);
	
	public Long getIdByPlate(String plate_no);
	
	public boolean is_repeat(Vehicle vehicle);
	
	public Page<Vehicle> search(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public Page<Vehicle> search2(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public Page<Vehicle> search3(PageSelect<Vehicle> pageSelect, Long subco_no);
	
	public List<Vehicle> getVehiclesByCustid(Long cust_id);
	
	public List<HashMap<String, Object>> getVehicleInfo(Map<String, Object> conditionMap, Long companyno, String order,boolean isDesc,int pn, int pageSize) throws SystemException;
	
	public int countVehicleInfo(Map<String, Object> conditionMap, Long commpanyNo)throws SystemException ;
	
	public boolean is_exist(Vehicle vehicle);
	
	public List<HashMap<String, Object>> findServiceFeePage(Long companyno, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countServiceFee(Long companyno, Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> countServiceFeePage(Long companyno,Map map ) throws SystemException;

	public List<HashMap<String, Object>> findOnlineNoFeePage(Long companyno, Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public int countOnlineNoFee(Long companyno, Map<String, Object> conditionMap) throws SystemException;
	
	public List<HashMap<String, Object>> webgisList(String parameter, Long subco_no);
	
}

