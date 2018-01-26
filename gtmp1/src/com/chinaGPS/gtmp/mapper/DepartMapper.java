package com.chinaGPS.gtmp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinaGPS.gtmp.entity.DepartPOJO;


/**
 * @Package:com.chinaGPS.gtmp.mapper
 * @ClassName:DepartMapper
 * @Description:机构dao层 Mapper
 * @author:zfy
 * @date:2012-12-17 下午04:12:19
 *
 */
@Component
public interface DepartMapper<T extends DepartPOJO> extends BaseSqlMapper<T> {
  /**
   * @Title:hasDepartByName
   * @Description:判断机构名是否重复
   * @param departPOJO
   * @return
   * @throws
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