package com.gboss.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gboss.comm.SystemException;
import com.gboss.pojo.FlowCtrolCmd;
import com.gboss.pojo.FlowCtrolCmdHistory;
import com.gboss.pojo.SimCardInfo;

public interface FCCmdManageDao extends BaseDao {
	/**
	 * @Description:查询所有的sim卡信息;
	 * @Params:
	 * @Return:List<HashMap<String,Object>>
	 * @Author:cj
	 * @Date:2017年7月17日 上午9:30:43
	 */
	public List<HashMap<String, Object>> getAllSimPage(Long companyId,Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	/**
	 * @Description:统计指定查询条件的结果数量;
	 * @Params:
	 * @Return:int
	 * @Author:cj
	 * @Date:2017年7月17日 上午10:23:38
	 */
	public int countAllSim(Long companyId,Map<String, Object> condition)throws SystemException;

	/**
	 * @Description:更新远程控制状态；
	 * @Params:
	 * @Return:void
	 * @Author:cj
	 * @Date:2017年7月17日 下午3:24:37
	 */
	public void updateRemoteCtrlStatus(String simIds,String opType);

	/**
	 * 
	 * @Description:根据指令id获取指令表数据;
	 * @Params:
	 * @Return:List<FlowCtrolCmd>
	 * @Author:cj
	 * @Date:2017年7月20日 下午2:13:43
	 */
	public List<FlowCtrolCmd> getFlowCtrlList(Integer cmdId);

	/**
	 * 
	 * @Description:根据指令id获取指令表所有数据;
	 * @Params:
	 * @Return:List<FlowCtrolCmd>
	 * @Author:cj
	 * @Date:2017年7月20日 下午2:14:13
	 */
	public List<FlowCtrolCmd> getFlowCtrlAllList(Integer cmdId);

	/**
	 * 
	 * @Description:根据指令id和呼号获取相应的流量控制指令;
	 * @Params:
	 * @Return:FlowCtrolCmd
	 * @Author:cj
	 * @Date:2017年7月20日 下午2:14:36
	 */
	public FlowCtrolCmd getFlowCtrolByCallLetter(Integer cmdId, String call_letter);
	
	public SimCardInfo getSimByCallLetter(String call_letter);

	public int countAllLogInfo(Long companyId, Map filter);

	public List<HashMap<String, Object>> getAllCmdLogInfo(Long companyId,Map<String, Object> conditionMap, String order,boolean isDesc,int pn, int pageSize) throws SystemException;

	public ConcurrentHashMap<String, ArrayList<FlowCtrolCmd>> getAllSendSearchMap();
	
	public ArrayList<String> getDiffCallLetterList();
	
	public ArrayList<FlowCtrolCmd> getFlowCmdByCallLetter(String callLetter);
	
	/**
	 * 导出执行指令失败的记录
	 * @param batchSeq
	 * @return
	 */
	public List<HashMap<String, Object>> exportExcelFailCmd(String batchSeq,String type);

	/**
	 * 是否存在该批次
	 * @param batchSeq
	 * @return
	 */
	public boolean isExistBatchSeq(String batchSeq);
}

