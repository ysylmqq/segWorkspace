package com.chinaGPS.gtmp.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinaGPS.gtmp.entity.CustomerPayPOJO;
import com.chinaGPS.gtmp.entity.CustomerPayPOJOExample;
import com.chinaGPS.gtmp.entity.CustomerSimPOJO;

public interface CustomerPayPOJOMapper <T extends CustomerPayPOJO> extends BaseSqlMapper<T>  {
    int countByExample(CustomerPayPOJOExample example);

    int deleteByExample(CustomerPayPOJOExample example);

    int deleteByPrimaryKey(BigDecimal custPayId);

    int insert(CustomerPayPOJO record);

    int insertSelective(CustomerPayPOJO record);

    List<CustomerPayPOJO> selectByExample(CustomerPayPOJOExample example);

    CustomerPayPOJO selectByPrimaryKey(BigDecimal custPayId);

    int updateByExampleSelective(@Param("record") CustomerPayPOJO record, @Param("example") CustomerPayPOJOExample example);

    int updateByExample(@Param("record") CustomerPayPOJO record, @Param("example") CustomerPayPOJOExample example);

    int updateByPrimaryKeySelective(CustomerPayPOJO record);

    int updateByPrimaryKey(CustomerPayPOJO record);
    
    void insertCustomerPay(CustomerPayPOJO record);
    
    public BigDecimal selectIdx();
    
    public  List<CustomerPayPOJO> simList();
    
    public void updateIsLast(CustomerPayPOJO record);
    
    public void updateStatus (CustomerPayPOJO record);

	public List<CustomerPayPOJO> allCustomerPay(Map map);

	public List<CustomerPayPOJO> vehicleInfo();
	
	public int getAllPay(String simNo) throws Exception;
    
}