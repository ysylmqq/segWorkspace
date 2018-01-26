package com.chinaGPS.gtmp.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.SimPayPOJO;
import com.chinaGPS.gtmp.entity.SimPayPOJOExample;

public interface SimPayPOJOMapper <T extends SimPayPOJO> extends BaseSqlMapper<T>  {
    int countByExample(SimPayPOJOExample example);

    int deleteByExample(SimPayPOJOExample example);

    int deleteByPrimaryKey(BigDecimal simPayId);

    int insert(SimPayPOJO record);

    int insertSelective(SimPayPOJO record);

    List<SimPayPOJO> selectByExample(SimPayPOJOExample example);

    SimPayPOJO selectByPrimaryKey(BigDecimal simPayId);

    int updateByExampleSelective(@Param("record") SimPayPOJO record, @Param("example") SimPayPOJOExample example);

    int updateByExample(@Param("record") SimPayPOJO record, @Param("example") SimPayPOJOExample example);

    int updateByPrimaryKeySelective(SimPayPOJO record);

    int updateByPrimaryKey(SimPayPOJO record);
    
    public  List<SimPayPOJO> simList();
    
    public void updateIsLast(SimPayPOJO record);
    
    public void updateStatus (SimPayPOJO simPayPOJO);

    
	List<SimPayPOJO> allCustomerPay(Map map);
	
	public int getAllPay(String simNo)throws Exception;

}