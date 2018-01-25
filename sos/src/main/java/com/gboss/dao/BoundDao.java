package com.gboss.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Renew;
import com.gboss.pojo.Unit;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:BoundDao
 * @Description:导出海马绑定列表
 * @author:hansm
 * @date:2014-3-24 下午3:07:06
 */
public interface BoundDao extends BaseDao {
	
	/**
	 * @Title:findBoundInNets
	 * @Description:查询入网车辆列表  服务开通日报表
	 * @param 前端搜索数据,排序,正序倒序,页数,页码
	 * @param conditionMap
	 * @param order
	 * @param isDesc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SystemException
	 */
	public List<HashMap<String, Object>> findBoundInNets(Map<String, Object> conditionMap, String order,boolean isDesc, int pageNo, int pageSize) throws SystemException;
	
	/**
	 * @Title:countBoundInNets
	 * @Description:查询入网车辆记录数  服务开通日报表
	 * @param conditionMap
	 * @return
	 * @throws SystemException
	 */
	public int countBoundInNets(Map<String,Object> conditionMap) throws SystemException;

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
	 * @Title:add
	 * @Description:保存海马TBOX续费表单
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

}

