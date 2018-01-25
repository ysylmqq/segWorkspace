package com.gboss.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Renew;
import com.gboss.pojo.RenewHistory;
import com.gboss.pojo.Unit;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:RenewDao
 * @Description:导出海马TBOX续费报表
 * @author:hansm
 * @date:2014-3-24 下午3:07:06
 */
public interface RenewDao extends BaseDao {
	
	/**
	 * @Title:findHmTBoxRenew
	 * @Description:查询TBOX续费报表
	 * @param 前端搜索数据,排序,正序倒序,页数,页码
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findHmTBoxRenew(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	
	/**
	 * @Title:countHmTBoxRenew
	 * @Description:查询TBOX续费报表记录数 TBOX续费报表报表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countHmTBoxRenew(Map<String,Object> conditionMap) throws SystemException;

	/**
	 * @Title:findBoundInNets
	 * @Description:海马TBOX服务期满的客户报表
	 * @param 前端搜索数据,排序,正序倒序,页数,页码
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findTBoxServerExpire(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	
	/**
	 * @Title:countBoundInNets
	 * @Description:海马TBOX服务期满的客户数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countTBoxServerExpire(Map<String,Object> conditionMap) throws SystemException;
	
	/**
	 * @Title:addHistory
	 * @Description:保存海马TBOX续费表单到历史表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public Integer addHistory(RenewHistory renew)throws SystemException;

	/**
	 * @Title:add
	 * @Description:保存海马TBOX续费表单到t_fee_hm_renew表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public Integer add(Renew renew)throws SystemException;

	/**
	 * @Title:updateFeeSedateByVin
	 * @Description:根据Vin修改t_fee_info服务截止时间
	 * @param checkId
	 * @return
	 * @throws SystemException
	 */
	public void updateFeeSedateByVin(String sVin,String isUpdateService_end_newdate) throws SystemException;
	
	public Integer countTBOXPage(Map<String, Object> params)throws SystemException;

	public List<Map<String, Object>> findTBOXByPage(Map<String, Object> params, String order, boolean is_desc, int pageNo, int pageSize)throws SystemException;

	/**
	 * @Title:vinIsExist
	 * @Description:查询导入表的Vin是否存在于t_fee_hm_renew表
	 * @param String
	 * @return
	 * @throws SystemException
	 */
	public Integer vinIsExist(String vin) throws SystemException;

	/**
	 * @Title:updateFeeSedateByVin
	 * @Description:导入表的Vin存在时修改t_fee_hm_renew表
	 * @param checkId
	 * @return
	 * @throws SystemException
	 */
	public void updateFeeRenew(String customer_name,String  barCode,String call_letter,Date isCombo_change_date,String combo_type,String engine_no,String equip_code,String plate_no,Date isService_end_date,Date isService_end_newdate,Date isService_start_date,String telPhone,String vin) throws SystemException;
	
	/**
	 * @Title:findHistoryRenew
	 * @Description:查询TBOX续费报表历史记录
	 * @param 前端搜索数据,排序,正序倒序,页数,页码
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findHistoryRenew(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize,String vin) throws SystemException;
	
	/**
	 * @Title:countHistoryRenew
	 * @Description:查询TBOX续费报表历史记录数
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countHistoryRenew(Map<String,Object> conditionMap,String vin) throws SystemException;
}

