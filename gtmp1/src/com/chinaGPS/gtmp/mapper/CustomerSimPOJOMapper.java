package com.chinaGPS.gtmp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinaGPS.gtmp.entity.CustomerSimPOJO;
import com.chinaGPS.gtmp.entity.CustomerSimPOJOExample;
import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;

public interface CustomerSimPOJOMapper <T extends CustomerSimPOJO> extends BaseSqlMapper<T> {
    int countByExample(CustomerSimPOJOExample example);

    int deleteByExample(CustomerSimPOJOExample example);

    int deleteByPrimaryKey(String simNo);

    int insert(CustomerSimPOJO record);

    int insertSelective(CustomerSimPOJO record);

    List<CustomerSimPOJO> selectByExample(CustomerSimPOJOExample example);

    CustomerSimPOJO selectByPrimaryKey(String simNo);
    
    CustomerSimPOJO getCustomerSimPOJOById(String simNo);


    int updateByExampleSelective(@Param("record") CustomerSimPOJO record, @Param("example") CustomerSimPOJOExample example);

    int updateByExample(@Param("record") CustomerSimPOJO record, @Param("example") CustomerSimPOJOExample example);

    int updateByPrimaryKeySelective(CustomerSimPOJO record);

    int updateByPrimaryKey(CustomerSimPOJO record);
    
    public void updateStatus(CustomerSimPOJO record);

	public List<CustomerSimPOJO> customerSimList();
	
	public List<CustomerSimPOJO> allCustomerSim(Map map); 
	
	public List<CustomerSimPOJO> exportCustomerSim(Map map); 
	
}