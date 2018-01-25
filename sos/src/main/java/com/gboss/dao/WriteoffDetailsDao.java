package com.gboss.dao;

import com.gboss.pojo.WriteoffDetails;

public interface WriteoffDetailsDao extends BaseDao {
	
	public void delByWriteoffId(Long writeoff_id);
	
	public void updateBorrow(WriteoffDetails writeoffDetails);
	
}
