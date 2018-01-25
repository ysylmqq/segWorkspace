package com.gboss.dao;

import java.util.List;

import com.gboss.pojo.Combo;
import com.gboss.pojo.Simpack;
import com.gboss.pojo.Subcoft;

/**
 * @Package:com.gboss.dao
 * @ClassName:SimpackDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-12-11 下午4:11:31
 */
public interface SimpackDao extends BaseDao {
	
	public List<Subcoft> getSubsofts(Long subco_no);
	
	public List<Simpack> getSimpacks(Long subco_no,Integer feetype_id);
	
	public Combo getComboByid(Long combo_id);
	
	public Simpack getSimpackByid(Long pack_id);

}

