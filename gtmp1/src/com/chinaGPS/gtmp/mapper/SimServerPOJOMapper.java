package com.chinaGPS.gtmp.mapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chinaGPS.gtmp.entity.SimPayPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.SimServerPOJOExample;
import com.chinaGPS.gtmp.entity.UnitPOJO;

public interface SimServerPOJOMapper <T extends SimServerPOJO> extends BaseSqlMapper<T>  {
    int countByExample(SimServerPOJOExample example);

    int deleteByExample(SimServerPOJOExample example);

    int deleteByPrimaryKey(String simNo);

    int insert(SimServerPOJO record);

    int insertSelective(SimServerPOJO record);

    List<SimServerPOJO> selectByExample(SimServerPOJOExample example);

    SimServerPOJO selectByPrimaryKey(String simNo);

    int updateByExampleSelective(@Param("record") SimServerPOJO record, @Param("example") SimServerPOJOExample example);

    int updateByExample(@Param("record") SimServerPOJO record, @Param("example") SimServerPOJOExample example);

    int updateByPrimaryKeySelective(SimServerPOJO record);
    
    public  void updateSimNo(SimServerPOJO record);

    int updateByPrimaryKey(SimServerPOJO record);
    
    public SimServerPOJO getSimServerPOJOById(String simNo);
    
    public void updateStatus(SimServerPOJO record);
    
    public List<UnitPOJO> getUnitList();

	public List<SimServerPOJO> simServerList();
	
	public List<SimServerPOJO> allSimServer(Map map);
	
	public List<SimServerPOJO> exportSimServer(Map map);
	
	public List<SimServerPOJO> getByPageSimServer(Map<String, Serializable> map,RowBounds rowBounds) throws Exception;
	
	public int countAllSimServer(Map<String, Serializable> map) throws Exception;
	
	public List<SimServerPOJO>  outputProfit(Map map);
	
	/**
	 * 客户缴费记录总 包括换卡的情况  
	 * @return
	 */
	public List<Map> customerAllPayMap();
	
	/**
	 * 公司缴费总额   包括换卡的情况  还有SIM成本
	 * @return
	 */
	public List<Map> companyAllPayMap();
	
	/**
	 * 导出更换终端
	 * @param map
	 * @return
	 */
	public List<SimServerPOJO> exportChangeUnit(Map map);

	/**
	 * 更新t_sim_server allFee 换卡的时候用到
	 * @param newTemp
	 */
	public void updateAllFee(SimServerPOJO newTemp);

	
	

}