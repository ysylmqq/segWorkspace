package com.gboss.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gboss.dao.WriteoffDetailsDao;
import com.gboss.pojo.BorrowDetails;
import com.gboss.pojo.WriteoffDetails;

@Repository("WriteoffDetailsDao")  
@Transactional
public class WriteoffDetailsDaoImpl extends BaseDaoImpl implements WriteoffDetailsDao {

	@Override
	public void delByWriteoffId(Long writeoff_id) {
		String hql = "delete from WriteoffDetails where writeoff_id = " + writeoff_id ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void updateBorrow(WriteoffDetails writeoffDetails) {
		String hql = "from BorrowDetails where id = " + writeoffDetails.getBorrowId();
		List<BorrowDetails> list = listAll(hql);
		if(list.size()>0){
			BorrowDetails borrow = list.get(0);
			int num = borrow.getNum();
			int writeoffsNum = borrow.getWriteoffsNum();
			Integer writeoffsNum2 = borrow.getWriteoffsNum2();
			if(writeoffsNum2==null){
				writeoffsNum2=0;
			}
			int offQuantity = writeoffDetails.getOffQuantity();
			writeoffsNum = writeoffsNum + writeoffsNum2+ offQuantity;
			borrow.setWriteoffsNum(writeoffsNum);
			if(num == writeoffsNum ){
				borrow.setStatus(1);
			}
			update(borrow);
		}
		
	}

}
