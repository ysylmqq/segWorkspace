package com.chinaGPS.gtmp.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.chinaGPS.gtmp.entity.SimServerPOJO;
import com.chinaGPS.gtmp.entity.UnitPOJO;
import com.chinaGPS.gtmp.entity.UnitSetUp;
import com.chinaGPS.gtmp.mapper.BaseSqlMapper;
import com.chinaGPS.gtmp.mapper.SimServerPOJOMapper;
import com.chinaGPS.gtmp.mapper.UnitMapper;
import com.chinaGPS.gtmp.service.IUnitService;
import com.chinaGPS.gtmp.util.DBTools;
import com.chinaGPS.gtmp.util.page.PageSelect;

/**
 * @Package:com.chinaGPS.gtmp.service.impl
 * @ClassName:UnitServiceImpl
 * @Description:
 * @author:zfy
 * @date:Dec 11, 2012 2:43:44 PM
 *
 */
@Service
public class UnitServiceImpl extends BaseServiceImpl<UnitPOJO> implements IUnitService{
    @Resource
    private UnitMapper<UnitPOJO> unitMapper;
    
    @Resource
    private SimServerPOJOMapper<SimServerPOJO> simServerPOJOMapper;
    
    @Override
    protected BaseSqlMapper<UnitPOJO> getMapper() {
		return this.unitMapper;
    }
    
	@Override
	public HashMap<String, Object> getUnits(UnitPOJO unit, PageSelect pageSelect) throws Exception {
		Map<String,Serializable> map = new HashMap<String, Serializable>();
		map.put("unit", unit);
		int total = unitMapper.countAll(map);
		List<UnitPOJO> resultList = unitMapper.getByPage(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}
	
	@Override
	public List<UnitPOJO> getList(UnitPOJO unit) throws Exception {
		return unitMapper.getList(unit);
	}
	
	@Override
	public UnitPOJO getUnitById(String unitId) throws Exception {
		UnitPOJO unitPOJO = unitMapper.getById(unitId);
		if(StringUtils.isNotEmpty(unitPOJO.getSimNo())){
			SimServerPOJO simServerPOJO = simServerPOJOMapper.getSimServerPOJOById(unitPOJO.getSimNo());
			if( simServerPOJO != null){
				unitPOJO.setOpenTime(simServerPOJO.getOpenTime());
				unitPOJO.setEndTime(simServerPOJO.getEndTime());
				unitPOJO.setPayAmount(simServerPOJO.getPayAmount());
			}
		}
		return unitPOJO;
	}
	
	@Override
	public boolean delUnitById(String unitId) throws Exception {
		return unitMapper.removeById(unitId) > 0 ? true : false;
	}
	
	@Override
	public boolean saveOrUpdate(UnitPOJO unit) throws Exception {
		Boolean flag = false;
		SimServerPOJO simServerPOJO = new SimServerPOJO();
		simServerPOJO.setSimNo(unit.getSimNo());
		simServerPOJO.setOpenTime(unit.getOpenTime());
		simServerPOJO.setEndTime(unit.getEndTime());
		simServerPOJO.setPayAmount(unit.getPayAmount());
		simServerPOJO.setStatus(new BigDecimal(0));
		simServerPOJO.setCreateTime(new Date());
		simServerPOJO.setOperId(unit.getUserId()+"");
		if (StringUtils.isNotEmpty(unit.getUnitId())) {
			//更新
			simServerPOJO.setUnitId(unit.getUnitId());
			//oldSIMNo
			String oldSimNo = unitMapper.getById(unit.getUnitId()).getSimNo(); 
			simServerPOJO.setRemark(oldSimNo);//使用remark字段存放oldSIMNo
			simServerPOJOMapper.updateSimNo(simServerPOJO);
			
			flag = unitMapper.edit(unit) > 0 ? true : false;
		} else {
			//新增
			flag = unitMapper.add(unit) > 0 ? true : false;
			Map map =new HashMap();
			map.put("unit", unit);
			List<UnitPOJO> list = unitMapper.getBySimNo(map);
			if(list != null && list.size() != 0){
				UnitPOJO temp = list.get(0);
				simServerPOJO.setUnitId(temp.getUnitId());
				//判断SimNo是否存在  存在更新 不存在插入
				SimServerPOJO temp2 = simServerPOJOMapper.selectByPrimaryKey(unit.getSimNo());
				if( temp2 == null){
					simServerPOJOMapper.insertSelective(simServerPOJO);
				}else{
					simServerPOJOMapper.updateByPrimaryKeySelective(simServerPOJO);
				}
			}
		}
		return flag;
	}
	
	@Override
	public boolean saveOrUpdateUtilSetUp(Map map)  {
		Boolean flag = false;
		try {
			if (StringUtils.isNotEmpty((map.get("unitId")+"")) 
					&& !(map.get("unitId")+"").equals("0")) {
				 unitMapper.editUtilSetUp(map);
				 flag =true;
			} else {
				unitMapper.addUtilSetUp(map);
				 flag =true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	
	
	@Override
	public List<UnitPOJO> getUnitBySnSID(UnitPOJO unit) throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("unit", unit);
		return unitMapper.getBySnSID(map);
	}

	@Override
	public HashMap<String, Object> addUnits(List<UnitPOJO> units) throws Exception {
		HashMap<String, Object> result=new HashMap<String, Object>();
		if (units!=null) {
			int resNum = 0;
			String resultMsg = null;
			ARRAY gpsList = null;
			//将java的ArrayList转化成oracle数组ARRAY类型
			OracleConnection connection = null;
			try {
				 connection = new DBTools().getConnection();
			
		    gpsList = getArray(connection,"TYP_UNIT","TYP_UNIT_LIST",(ArrayList)units);
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("gpsInfo", gpsList);
			map.put("resNum", resNum);
			map.put("resMsg", resultMsg);
			unitMapper.addBatchByProc(map);
			result.put("count", map.get("resNum"));
			result.put("msg", map.get("resMsg"));
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(connection!=null){
					connection.close();
				}
			}
		}
		return result;
	}
	
	
	@Override
	public HashMap<String, Object> addUnitsAndSimServer(List<UnitPOJO> units) throws Exception {
		HashMap<String, Object> result=new HashMap<String, Object>();
		if (units!=null) {
			int resNum = 0;
			String resultMsg = null;
			ARRAY gpsList = null;
			//将java的ArrayList转化成oracle数组ARRAY类型
			OracleConnection connection = null;
			try {
				 connection = new DBTools().getConnection();
			
		    gpsList = getArray(connection,"TYP_UNIT","TYP_UNIT_LIST",(ArrayList)units);
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("gpsInfo", gpsList);
			map.put("resNum", resNum);
			map.put("resMsg", resultMsg);
			unitMapper.addBatchByProc(map);
			result.put("count", map.get("resNum"));
			result.put("msg", map.get("resMsg"));
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(connection!=null){
					connection.close();
				}
			}
			
			//导入t_sim_server
			for (Iterator iterator = units.iterator(); iterator.hasNext();) {
				UnitPOJO unitPOJO = (UnitPOJO) iterator.next();
				SimServerPOJO simServerPOJO = new SimServerPOJO();
				simServerPOJO.setSimNo(unitPOJO.getSimNo());
				simServerPOJO.setOpenTime(unitPOJO.getOpenTime());
				simServerPOJO.setEndTime(unitPOJO.getEndTime());
				simServerPOJO.setPayAmount(unitPOJO.getPayAmount());
				simServerPOJO.setStatus(new BigDecimal(0));
				simServerPOJO.setCreateTime(new Date());
				//判断SimNo是否存在  存在更新 不存在插入
				SimServerPOJO temp = simServerPOJOMapper.selectByPrimaryKey(unitPOJO.getSimNo());
				if( temp == null){
					simServerPOJOMapper.insertSelective(simServerPOJO);
				}else{
					simServerPOJOMapper.updateByPrimaryKeySelective(simServerPOJO);
				}
			}
		
		}
		return result;
	}
	
	private static OracleConnection getConn() throws BeansException, SQLException{
		//获得数据库的连接
		
		/*ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring/applicationContext-*.xml"); 
		C3P0NativeJdbcExtractor cp30NativeJdbcExtractor = new C3P0NativeJdbcExtractor();
		OracleConnection con = (OracleConnection) cp30NativeJdbcExtractor.getNativeConnection(((ComboPooledDataSource) ctx.getBean("dataSource")).getConnection());
		*/
	    return  null;
	}
	
	private static ARRAY getArray(OracleConnection con, String oracleType, String oracleArray, ArrayList<UnitPOJO> objList) {
		
		
		ARRAY list = null;
		if (objList != null && objList.size() > 0) {
			StructDescriptor structDesc;
			try {
				structDesc = new StructDescriptor(oracleType,con);
			
			STRUCT[] structs = new STRUCT[objList.size()];
			Object[] result=null;
			for (int i = 0; i < objList.size(); i++) {
				result = new Object[16];
				UnitPOJO unit = (UnitPOJO)(objList.get(i));
				
				result[0] = unit.getUnitId();
				result[1] = unit.getSupperierSn(); 
				result[2] = unit.getUnitSn();
				result[3] = unit.getUnitTypeSn();
				result[4] = unit.getHardwareVersion();
				result[5] = unit.getSoftwareVersion();
				result[6] = unit.getSimNo();
				result[7] = unit.getMaterialNo();
				result[8] = unit.getSteelNo();
				result[9] = unit.getProductionDate();
				result[10] = unit.getRegistedTime();
				result[11] = unit.getUserId();
				result[12] = unit.getState();
				result[13] = unit.getRemark();
				result[14] = unit.getIsValid();
				result[15] = unit.getStamp();
				
				structs[i] = new STRUCT(structDesc,con,result);
			}
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleArray,con);
			list = new ARRAY(desc,con,structs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				
			
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oracleArray,con);
			STRUCT[] structs = new STRUCT[0];
			list = new ARRAY(desc,con,structs);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return list;
	};

	@Override
	public List<UnitPOJO> getUnitBySimNo(UnitPOJO unit) throws Exception {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("unit", unit);
		return unitMapper.getBySimNo(map);
	}

	@Override
	public int deleteUnits(List<String> unitIds) throws Exception {
		return unitMapper.deleteUnits(unitIds);
	}

	@Override
	public int updateUnitsIsValid(List<UnitPOJO> units) throws Exception {
		return unitMapper.updateUnitsIsValid(units);
	}

	@Override
	public Map findUnitSetUp(Map map, PageSelect pageSelect) throws Exception {
		int total = unitMapper.countUnitSetUp(map);
		List<UnitSetUp> resultList = unitMapper.findUnitSetUp(map,new RowBounds(pageSelect.getOffset(), pageSelect.getRows()));
		HashMap<String, Object> result=new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", resultList);
		return result;
	}

	@Override
	public List<UnitSetUp> findUnitSetUp2(Map map) throws Exception {
		List<UnitSetUp> resultList = unitMapper.findUnitSetUp(map);
		return resultList;
	}

	@Override
	public int findVehicleByvehicleDef(String vehicleDef) throws Exception {
		return unitMapper.findVehicleByvehicleDef(vehicleDef);
	}

	public int findUtilSetUpByvehicleDef(String vehicleDef) throws Exception {
		return unitMapper.findUtilSetUpByvehicleDef(vehicleDef);
	}

}