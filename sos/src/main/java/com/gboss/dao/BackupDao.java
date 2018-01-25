package com.gboss.dao;

import java.util.HashMap;
import java.util.List;

import com.gboss.comm.SystemException;
import com.gboss.pojo.Backup;
import com.gboss.pojo.Collection;
import com.gboss.pojo.Collectionbk;
import com.gboss.pojo.Customer;
import com.gboss.pojo.Customerbk;
import com.gboss.pojo.Driver;
import com.gboss.pojo.Driverbk;
import com.gboss.pojo.FeeInfo;
import com.gboss.pojo.FeeInfobk;
import com.gboss.pojo.Unit;
import com.gboss.pojo.Unitbk;
import com.gboss.pojo.Vehicle;
import com.gboss.pojo.Vehiclebk;
import com.gboss.util.PageSelect;
import com.gboss.util.page.Page;

/**
 * @Package:com.gboss.dao
 * @ClassName:BackupDao
 * @Description:TODO
 * @author:xiaoke
 * @date:2014-7-10 下午3:39:50
 */
public interface BackupDao extends BaseDao {
	
	public Backup getBackupByid(Long bk_id)throws SystemException;
	
	public Page<Backup> findBackup(PageSelect<Backup> pageSelect)throws SystemException;
	
	public Page<Backup> findStopAndOpen(PageSelect<Backup> pageSelect)throws SystemException;
	
	public Customerbk getCustomerbkbyid(Long bkc_id)throws SystemException;
	
	public Vehiclebk getVehiclebkbyid(Long bkv_id)throws SystemException;
	
	public Unitbk getUnitbkbyid(Long bku_id)throws SystemException;
	
	public Collectionbk getCollectionbkbyid(Long bkfc_id)throws SystemException;
	
	public FeeInfobk getFeeInfobkbyid(Long bkf_id)throws SystemException;
	
	public Driverbk getDriverbkbyid(Long bkd_id)throws SystemException;
	
	public Customerbk getCustomerbk(Customer customer)throws SystemException;
	
	public Vehiclebk getVehiclebk(Vehicle vehicle)throws SystemException;
	
	public Unitbk getUnitbk(Unit unit)throws SystemException;
	
	public Collectionbk getCollectionbk(Collection collection)throws SystemException;
	
	public FeeInfobk getFeeInfobk(FeeInfo feeInfo)throws SystemException;
	
	public Driverbk getDriverbk(Driver driver)throws SystemException;
	
	public Long addBackup(Backup backup)throws SystemException;
	
	public Long addCustomerbk(Customerbk customer)throws SystemException;
	
	public Long addVehiclebk(Vehiclebk vehicle)throws SystemException;
	
	public Long addUnitbk(Unitbk unit)throws SystemException;
	
	public Long addCollectionbk(Collectionbk collection)throws SystemException;
	
	public Long addFeeInfobk(FeeInfobk feeInfo)throws SystemException;
	
	public Long addDriverbk(Driverbk driverbk)throws SystemException;
	
	public List<HashMap<String, Object>> getListBk(HashMap<String, Object> map)throws SystemException;

}
