package com.chinagps.driverbook.dao;

import com.chinagps.driverbook.pojo.AlarmControl;
import com.chinagps.driverbook.pojo.AlarmControlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlarmControlMapper {
    long countByExample(AlarmControlExample example);

    int deleteByExample(AlarmControlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AlarmControl record);

    int insertSelective(AlarmControl record);

    List<AlarmControl> selectByExample(AlarmControlExample example);

    AlarmControl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AlarmControl record, @Param("example") AlarmControlExample example);

    int updateByExample(@Param("record") AlarmControl record, @Param("example") AlarmControlExample example);

    int updateByPrimaryKeySelective(AlarmControl record);

    int updateByPrimaryKey(AlarmControl record);
    
	public void updateAlarmControl(AlarmControl alarmControl); 
	
	public  List<AlarmControl> selectAll(); 
	
	public void  updateAlarmControlIsOpenTrue(@Param("statusIdList")List<String> statusIdList);
	
	public void  updateAlarmControlIsOpenFalse(@Param("statusIdList")List<String> statusIdList);
	
	
}