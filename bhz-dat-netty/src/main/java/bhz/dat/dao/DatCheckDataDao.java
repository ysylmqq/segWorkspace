package bhz.dat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import bhz.dat.protocol.RequestData;

@Repository
public class DatCheckDataDao  {

	@Autowired
    private JdbcTemplate jdbcTemplate;  

	public int insert(RequestData data) throws Exception{
        String sql = " INSERT INTO DAT_CHECK_DATA (CHECK_NO, CHECK_TYPE, SITE_ID, EQUIP_ID, LINE, VEHICLE_NO, VEHICLE_TYPE, AXLES, TYRES, CHECK_RESULT, CHECK_BY, CHECK_TIME, SPEED, LIMIT_TOTAL, OVER_TOTAL, TOTAL, DESC_INFO, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = { 
        		data.getCheckNo(), data.getCheckType(), data.getSiteId(), data.getEquipId(), data.getLine(),
        		data.getVehicleNo(), data.getVehicleType(), data.getAxles(), data.getTyres(), data.getCheckResult(), 
        		data.getCheckBy(), data.getCheckTime(), data.getSpeed(), data.getLimitTotal(), data.getOverTotal(), data.getTotal(), data.getDescInfo(),
        		data.getCreateBy(), data.getCreateTime(), data.getUpdateBy(), data.getUpdateTime()
        };
        return jdbcTemplate.update(sql, args);
	}
	
	public boolean exist(String checkNo){
		String sql = "SELECT COUNT(*) FROM DAT_CHECK_DATA DCD WHERE DCD.CHECK_NO = ? ";
        int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, checkNo);
        if (count > 0) {
            return true;
        }
        return false;  
	}
	
	public int update(RequestData data) throws Exception{
        String sql = " UPDATE DAT_CHECK_DATA SET CHECK_TYPE = ?, SITE_ID = ?, EQUIP_ID = ?, LINE = ?, VEHICLE_NO = ?, VEHICLE_TYPE = ?, AXLES = ?, TYRES = ?, CHECK_RESULT = ?, CHECK_BY = ?, CHECK_TIME = ?, SPEED = ?, LIMIT_TOTAL = ?, OVER_TOTAL = ?, TOTAL = ?, DESC_INFO = ?, CREATE_BY = ?, CREATE_TIME = ?, UPDATE_BY = ?, UPDATE_TIME = ? WHERE CHECK_NO = ?)";
        Object[] args = { 
        		data.getCheckType(), data.getSiteId(), data.getEquipId(), data.getLine(),
        		data.getVehicleNo(), data.getVehicleType(), data.getAxles(), data.getTyres(), data.getCheckResult(), 
        		data.getCheckBy(), data.getCheckTime(), data.getSpeed(), data.getLimitTotal(), data.getOverTotal(), data.getTotal(), data.getDescInfo(),
        		data.getCreateBy(), data.getCreateTime(), data.getUpdateBy(), data.getUpdateTime(), data.getCheckNo()
        };
        return jdbcTemplate.update(sql, args);
	}	
	
	
}
