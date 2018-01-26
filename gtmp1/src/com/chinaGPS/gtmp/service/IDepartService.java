package com.chinaGPS.gtmp.service;

import java.util.List;

import com.chinaGPS.gtmp.entity.DepartPOJO;

/**
 * @Package:com.chinaGPS.gtmp.service
 * @ClassName:IDepartService
 * @Description:机构Service层接口
 * @author:zfy
 * @date:2012-12-17 下午04:10:12
 *
 */
public interface IDepartService extends BaseService<DepartPOJO> {
	/**
	 * 获得机构列表
	 * @param departPOJO
	 * @return
	 */
	public List<DepartPOJO> getDeparts(DepartPOJO departPOJO) throws Exception;
	
	/**
	 * 根据机构ID查询出机构信息
	 * @param id
	 * @return
	 */
	public DepartPOJO getById(Long id) throws Exception;
	
	/**
	 * 根据机构id，删除机构
	 * @param id
	 * @return
	 */
	public boolean deleteDepartById(Long id) throws Exception;
	
	/**
	 * 保存机构信息
	 * @param departPOJO
	 * @return
	 */
	public boolean saveOrUpdate(DepartPOJO departPOJO) throws Exception;
	
	/**
	 * 判断机构名称是否存在
	 * @param departPOJO
	 * @return
	 */
	public int hasDepartByName(DepartPOJO departPOJO) throws Exception;
	
	  /**
	   * @Title:getList4User
	   * @Description:得到机构列表，且是用户已选中的机构
	   * @param departPOJO
	   * @return
	   * @throws
	   */
	  public List<DepartPOJO> getList4User(DepartPOJO departPOJO) throws Exception;
	
	  /**
	   * @Title:removeBySupperierSn
	   * @Description:根据终端供应商编号删除机构
	   * @param supperierSn
	   * @return
	   * @throws Exception
	   */
	  public int removeBySupperierSn(String supperierSn) throws Exception;
	 
}