package cc.chinagps.gps.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import cc.chinagps.gps.dao.VehicleDao;
import cc.chinagps.gps.pojo.CarTempInfo;
import cc.chinagps.gps.pojo.GpsTempInfo;
import cc.chinagps.gps.util.SqlSessionFactoryUtil;

public class VehicleService extends CommonService{

	private static final Logger log = Logger.getLogger(SqlSessionFactoryUtil.class);
	
	public List<CarTempInfo> selectHaimaCars(Map<String,String> map) {
		List<CarTempInfo> cars = null;
		SqlSession sqlSession = getSqlSession(false);
		
		VehicleDao vehicleDao = (VehicleDao) getOperatorDao(sqlSession, VehicleDao.class);
		if (vehicleDao != null) {
			try {
				//获得车辆信息
				cars = vehicleDao.selectHaimaCars(map);
				sqlSession.commit();
			} catch (Exception e) {
				log.error(null, e);
			} finally {
				sqlSession.close();
			}
		} else {
			log.warn("vehicleDao is null !");
		}
		return cars;
	}
	
	/**
	 * 查询入网的所有海马车辆信息
	 * @return
	 */
	public List<CarTempInfo> selectEnterNetHaimaCars() {
		List<CarTempInfo> cars = null;
		SqlSession sqlSession = getSqlSession(false);
		VehicleDao vehicleDao = (VehicleDao) getOperatorDao(sqlSession, VehicleDao.class);
		if (vehicleDao != null) {
			try {
				cars = vehicleDao.selectEnterNetHaimaCars();
				sqlSession.commit();
			} catch (Exception e) {
				log.error(null, e);
			} finally {
				sqlSession.close();
			}
		} else {
			log.warn("vehicleDao is null !");
		}
		return cars;
	}
	
	public void saveHaimaTempGPS(List<GpsTempInfo> gpsTempInfo) {
		SqlSession sqlSession = getSqlSession(true);
		VehicleDao vehicleDao = (VehicleDao) getOperatorDao(sqlSession, VehicleDao.class);
		if (vehicleDao != null) {
			if (gpsTempInfo != null && !gpsTempInfo.isEmpty()) {
				log.info("gpsTempInfo size: " + gpsTempInfo.size());
				try {
					vehicleDao.saveTempHaimaGps(gpsTempInfo);
					sqlSession.commit();
				} catch (Exception e) {
					sqlSession.rollback();
					log.error(null, e);
				} finally {
					sqlSession.close();
				}
			} else {
				log.warn("gpsTempInfo is null !");
			}
		} else {
			log.warn("vehicleDao is null !");
		}
	}

}