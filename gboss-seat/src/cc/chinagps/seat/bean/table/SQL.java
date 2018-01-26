package cc.chinagps.seat.bean.table;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 命名查询sql语句都在此类中存放
 * @author Administrator
 *
 */
@MappedSuperclass
@NamedNativeQuery(name = "SelectCurrentTimeStamp", 
	query = "SELECT CURRENT_TIMESTAMP")
@NamedQueries({
	@NamedQuery(name = "SelectVehicleInfo", 
	query = "SELECT unit.vehicle.id, unit.vehicle.plateNo,unit.callLetter "
			+ "FROM UnitTable unit "
			+ "WHERE (unit.callLetter LIKE :callLetter "
			+ "OR unit.vehicle.plateNo LIKE :platNo)"),
	@NamedQuery(name = "SelectBasicInfoByCallLetter", 
	query = "SELECT unit, sysValue.name, sysValue2.name,sysValue3.name "
			+ "FROM UnitTable unit,SysValueTable sysValue,"
			+ "SysValueTable sysValue2,SysValueTable sysValue3 "
			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "LEFT OUTER JOIN FETCH unit.customer "
			+ "LEFT OUTER JOIN FETCH unit.unitType "
			+ "WHERE unit.callLetter LIKE :callLetter "
			+ "AND unit.vehicle.typeId=sysValue.value "
			+ "AND sysValue.type=2030 "
			+ "AND unit.vehicle.statusId=sysValue2.value "
			+ "AND sysValue2.type=2060 "
			+ "AND unit.regStatusId=sysValue3.value "
			+ "AND sysValue3.type=2050 "),
	//TODO:外连接比内连接更快？？
	//电话号码比较不能在数据库的数据中使用concat，否则会导致sql查询缓慢。
	// 比如如下sql语句不能增加 OR CONCAT('0',linkman.phone)=:callLetter		
	//只能将电话号码去掉0后再调用sql语句查询
	@NamedQuery(name = "SelectBasicInfoByPhoneNum", 
	query = "SELECT unit, sysValue.name, sysValue2.name,sysValue3.name "
			+ "FROM UnitTable unit,SysValueTable sysValue,"
			+ "SysValueTable sysValue2,SysValueTable sysValue3,"
			+ "LinkmanTable linkman "
			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "LEFT OUTER JOIN FETCH unit.customer "
			+ "LEFT OUTER JOIN FETCH unit.unitType "
			+ "WHERE (linkman.phone=:callLetter "
			+ "OR linkman.phone=CONCAT('0',:callLetter)) "
			+ "AND linkman.customerId=unit.customer.id "
			+ "AND unit.vehicle.typeId=sysValue.value "
			+ "AND sysValue.type=2030 "
			+ "AND unit.vehicle.statusId=sysValue2.value "
			+ "AND sysValue2.type=2060 "
			+ "AND unit.regStatusId=sysValue3.value "
			+ "AND sysValue3.type=2050 "),
	@NamedQuery(name = "SelectBasicInfoByVehicleId", 
	query = "SELECT unit, sysValue.name, sysValue2.name,sysValue3.name "
			+ "FROM UnitTable unit,SysValueTable sysValue,"
			+ "SysValueTable sysValue2,SysValueTable sysValue3 "
			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "LEFT OUTER JOIN FETCH unit.customer "
			+ "LEFT OUTER JOIN FETCH unit.unitType "
			+ "WHERE unit.vehicle.id=:vehicleId "
			+ "AND unit.vehicle.typeId=sysValue.value "
			+ "AND sysValue.type=2030 "
			+ "AND unit.vehicle.statusId=sysValue2.value "
			+ "AND sysValue2.type=2060 "
			+ "AND unit.regStatusId=sysValue3.value "
			+ "AND sysValue3.type=2050 "),
	@NamedQuery(name = "SelectFeeInfoByPhoneNum", 
	query = "SELECT feeInfo, sysValue.name "
			+ "FROM FeeInfoTable feeInfo,UnitTable unit,"
			+ "SysValueTable sysValue,LinkmanTable linkman "
			+ "WHERE (linkman.phone=:callLetter "
			+ "OR linkman.phone=CONCAT('0',:callLetter)) "
			+ "AND linkman.customerId=unit.customer.id "
			+ "AND feeInfo.vehicleId=unit.vehicle.id  "
			+ "AND feeInfo.typeId=sysValue.value "
			+ "AND sysValue.type=3100"),
//	@NamedQuery(name = "SelectFeePaytxtByPhoneNum", 
//	query = "SELECT feePaytxt, sysValue.name, coll, sysValue2.name,"
//			+ "SUM(bill.amount),dt.printTime "
//			+ "FROM FeePaytxtTable feePaytxt, SysValueTable sysValue,"
//			+ "CollectionTable coll,UnitTable unit,"
//			+ "SysValueTable sysValue2,FeeBill bill,FeePaymentDt dt "
//			+ "WHERE unit.callLetter=:callLetter "
//			+ "AND feePaytxt.customerId=unit.customer.id "
//			+ "AND feePaytxt.customerId=coll.customerId "
//			+ "AND feePaytxt.customerId=bill.customerId "
//			+ "AND dt.bwNo=bill.id "
//			+ "AND feePaytxt.agency=sysValue.value "
//			+ "AND sysValue.type=3000 "
//			+ "AND feePaytxt.payTag=sysValue2.value "
//			+ "AND sysValue2.type="
//			+ "CASE WHEN feePaytxt.agency='1' OR feePaytxt.agency='2' "
//			+ "THEN 3020 ELSE 3021 END"),
	@NamedQuery(name = "SelectFeePaytxtByPhoneNum", 
	query = "SELECT feePaytxt, sysValue.name, sysValue2.name,"
			+ "SUM(bill.amount),dt.printTime "
			+ "FROM FeePaytxtTable feePaytxt, SysValueTable sysValue,"
			+ "UnitTable unit,"
			+ "SysValueTable sysValue2,FeeBill bill,FeePaymentDt dt, "
			+ "LinkmanTable linkman "
			+ "WHERE (linkman.phone=:callLetter "
			+ "OR linkman.phone=CONCAT('0',:callLetter)) "
			+ "AND linkman.customerId=unit.customer.id "
			+ "AND feePaytxt.customerId=unit.customer.id "
			+ "AND feePaytxt.customerId=bill.customerId "
			+ "AND dt.bwNo=bill.id "
			+ "AND feePaytxt.agency=sysValue.value "
			+ "AND sysValue.type=3000 "
			+ "AND feePaytxt.payTag=sysValue2.value "
			+ "AND sysValue2.type="
			+ "CASE WHEN feePaytxt.agency='1' OR feePaytxt.agency='2' "
			+ "THEN 3020 ELSE 3021 END"),
	@NamedQuery(name = "SelectCollectionByPhoneNum", 
	query = "SELECT coll "
			+ "FROM CollectionTable coll,UnitTable unit,"
			+ "LinkmanTable linkman "
			+ "WHERE (linkman.phone=:callLetter "
			+ "OR linkman.phone=CONCAT('0',:callLetter)) "
			+ "AND linkman.customerId=unit.customer.id "
			+ "AND coll.customerId=unit.customer.id "),
	@NamedQuery(name = "SelectInsuranceByPhoneNum", 
	query = "SELECT insurance, sysValue.name "
			+ "FROM InsuranceTable insurance,SysValueTable sysValue,"
			+ "UnitTable unit,LinkmanTable linkman "
			+ "WHERE (linkman.phone=:callLetter "
			+ "OR linkman.phone=CONCAT('0',:callLetter)) "
			+ "AND linkman.customerId=unit.customer.id "
			+ "and insurance.vehicleId=unit.vehicle.id "
			+ "and insurance.icNo=sysValue.value "
			+ "and sysValue.type=3030"),
	@NamedQuery(name = "SelectFeeInfoByVehicleId", 
	query = "SELECT feeInfo, sysValue.name "
			+ "FROM FeeInfoTable feeInfo,SysValueTable sysValue "
			+ "WHERE feeInfo.vehicleId=:vehicleId  "
			+ "AND feeInfo.typeId=sysValue.value "
			+ "AND sysValue.type=3100"),
//	@NamedQuery(name = "SelectFeePaytxtByVehicleId", 
//	query = "SELECT feePaytxt, sysValue.name, coll,sysValue2.name,"
//			+ "SUM(bill.amount),dt.printTime "
//			+ "FROM FeePaytxtTable feePaytxt,SysValueTable sysValue,"
//			+ "CollectionTable coll,UnitTable unit,"
//			+ "SysValueTable sysValue2,FeeBill bill,FeePaymentDt dt "
//			+ "WHERE unit.vehicle.id=:vehicleId "
//			+ "AND feePaytxt.customerId=unit.customer.id "
//			+ "AND feePaytxt.customerId=coll.customerId "
//			+ "AND feePaytxt.customerId=bill.customerId "
//			+ "AND feePaytxt.agency=sysValue.value "
//			+ "AND dt.bwNo=bill.id "
//			+ "AND feePaytxt.agency=sysValue.value "
//			+ "AND sysValue.type=3000 "
//			+ "AND feePaytxt.payTag=sysValue2.value "
//			+ "AND sysValue2.type="
//			+ "CASE WHEN feePaytxt.agency='1' OR feePaytxt.agency='2' "
//			+ "THEN 3020 ELSE 3021 END"),
	@NamedQuery(name = "SelectFeePaytxtByVehicleId", 
	query = "SELECT feePaytxt, sysValue.name, sysValue2.name,"
			+ "SUM(bill.amount),dt.printTime "
			+ "FROM FeePaytxtTable feePaytxt,SysValueTable sysValue,"
			+ "UnitTable unit,"
			+ "SysValueTable sysValue2,FeeBill bill,FeePaymentDt dt "
			+ "WHERE unit.vehicle.id=:vehicleId "
			+ "AND feePaytxt.customerId=unit.customer.id "
			+ "AND feePaytxt.customerId=bill.customerId "
			+ "AND feePaytxt.agency=sysValue.value "
			+ "AND dt.bwNo=bill.id "
			+ "AND feePaytxt.agency=sysValue.value "
			+ "AND sysValue.type=3000 "
			+ "AND feePaytxt.payTag=sysValue2.value "
			+ "AND sysValue2.type="
			+ "CASE WHEN feePaytxt.agency='1' OR feePaytxt.agency='2' "
			+ "THEN 3020 ELSE 3021 END"),
	@NamedQuery(name = "SelectCollectionByVehicleId", 
	query = "SELECT coll "
			+ "FROM CollectionTable coll,UnitTable unit "
			+ "WHERE unit.vehicle.id=:vehicleId "
			+ "AND coll.customerId=unit.customer.id "),
	@NamedQuery(name = "SelectInsuranceByVehicleId", 
	query = "SELECT insurance, sysValue.name "
			+ "FROM InsuranceTable insurance,SysValueTable sysValue "
			+ "WHERE insurance.vehicleId=:vehicleId "
			+ "and insurance.icNo=sysValue.value "
			+ "and sysValue.type=3030"),
	@NamedQuery(name = "SelectCommandByPhoneNo", 
	query = "SELECT cmd "
			+ "FROM CommandTable cmd,"
			+ "LinkmanTable linkman "
			+ "LEFT OUTER JOIN cmd.unitTables unit "
			+ "WHERE (linkman.phone=:phoneNo "
			+ "OR linkman.phone=CONCAT('0',:phoneNo)) "
			+ "AND linkman.customerId=unit.customer.id "
			+ "and cmd.isEnable=1 "
			+ "ORDER BY cmd.showIndex"),
	@NamedQuery(name = "SelectCommandByVehicleId", 
	query = "SELECT cmd "
			+ "FROM CommandTable cmd "
			+ "LEFT OUTER JOIN cmd.unitTables unit "
			+ "WHERE unit.vehicle.id=:vehicleId "
			+ "and cmd.isEnable=1 "
			+ "ORDER BY cmd.showIndex"),
	@NamedQuery(name = "SelectLinkmanByCustomerId", 
	query = "SELECT linkman,sysValue.name "
			+ "FROM LinkmanTable linkman,SysValueTable sysValue "
			+ "WHERE linkman.customerId=:customerId "
			+ "AND linkman.vehicleId=:vehicleId "
			+ "AND linkman.phone IS NOT NULL "
			+ "AND sysValue.type=2100 "
			+ "AND sysValue.value=linkman.linkmanType"),
	@NamedQuery(name = "SelectDriverByCustomerId", 
	query = "SELECT driver "
			+ "FROM DriverTable driver "
			+ "WHERE driver.customerId=:customerId "
			+ "AND driver.vehicleId=:vehicleId "
			+ "AND driver.phone IS NOT NULL"),
	@NamedQuery(name = "SelectInsuranceByCustomerId", 
	query = "SELECT insurance, sysValue.name "
			+ "FROM InsuranceTable insurance,SysValueTable sysValue "
			+ "WHERE insurance.customerId=:customerId "
			+ "AND insurance.icNo=sysValue.value "
			+ "AND sysValue.type=3030 "
			+ "AND insurance.phone IS NOT NULL"),
	@NamedQuery(name = "SelectMarks", 
	query = "SELECT mark "
			+ "FROM MarkTable mark "
			+ "WHERE mark.id IN (SELECT markCompany.pk.id "
			+ "FROM MarkCompanyTable markCompany "
			+ "WHERE markCompany.pk.companyNo IN (:companyNos))"),
	@NamedQuery(name = "SelectNavByVehicleId", 
	query = "SELECT nav FROM NavTable nav WHERE nav.vehicleId=:vehicleId "
			+ "AND nav.opId=:opId"),
	@NamedQuery(name = "SelectBriefByVehicleId", 
	query = "SELECT brief FROM BriefTable brief "
			+ "WHERE brief.vehicle_id=:vehicleId "
			+ "ORDER BY brief.stamp DESC"),
	@NamedQuery(name = "SelectChinaCity", 
	query = "SELECT city FROM ChinaCityTable city"),
	@NamedQuery(name = "SelectAlarmByVehicleID", 
	query = "SELECT alarm "
			+ "FROM AlarmTable alarm,UnitTable unit "
			+ "WHERE unit.vehicle.id=:vehicleId "
			+ "AND unit.unitId=alarm.unitId "
			+ "AND alarm.deleted = 0 "
			+ "AND alarm.handleStatus=:handleStatus "
			+ "ORDER BY alarm.handleTime DESC"),
	@NamedQuery(name = "SelectAlarmStatus", 
	query = "SELECT alarmStatus "
			+ "FROM AlarmStatusTable alarmStatus "
			+ "WHERE alarmStatus.deleted=0 "
			+ "ORDER BY alarmStatus.id"),
	@NamedQuery(name = "SelectMarkCompanyByID", 
	query = "SELECT markCompany "
			+ "FROM MarkCompanyTable markCompany "
			+ "WHERE markCompany.pk.id=:id"),
	@NamedQuery(name = "SelectBriefReport", 
	query = "SELECT brief,linkman.name,unit "
			+ "FROM BriefTable brief, UnitTable unit "
			+ "LEFT OUTER JOIN unit.linkman linkman "
			+ "WHERE brief.vehicle_id=unit.vehicle.id "
			+ "AND (brief.phone = linkman.phone "
			+ "OR CONCAT('0', brief.phone) = linkman.phone "
			+ "OR brief.phone = CONCAT('0',linkman.phone)) "
			+ "AND brief.stamp >= :startTime "
			+ "AND brief.stamp <= :endTime "),
	@NamedQuery(name = "SelectBriefReportCount", 
	query = "SELECT COUNT(brief.b_id) "
			+ "FROM BriefTable brief,UnitTable unit "
			+ "LEFT OUTER JOIN unit.linkman linkman "
			+ "WHERE brief.vehicle_id=unit.vehicle.id "
			+ "AND (brief.phone = linkman.phone "
			+ "OR CONCAT('0', brief.phone) = linkman.phone "
			+ "OR brief.phone = CONCAT('0',linkman.phone)) "
			+ "AND brief.stamp >= :startTime "
			+ "AND brief.stamp <= :endTime "),
	@NamedQuery(name = "SelectResponseRatioReport", 
	query = "SELECT alarm.acceptTimeSpan,alarm.acceptTime "
			+ "FROM AlarmTable alarm, UnitTable unit "
			+ "WHERE alarm.acceptTimeSpan IS NOT NULL "
			+ "AND alarm.acceptTime >= :startTime "
			+ "AND alarm.acceptTime <= :endTime "
			+ "AND alarm.deleted = 0 "
			+ "AND unit.unitId = alarm.unitId "
			+ "ORDER BY alarm.acceptTime "),
	@NamedQuery(name = "SelectNotFinishedStolenVehicleCount", 
	query = "SELECT COUNT(stolen) "
			+ "FROM StolenVehicleTable stolen "
			+ "WHERE stolen.vehicleId = :vehicleId "
			+ "AND stolen.endTime IS NULL "),
	@NamedQuery(name = "SelectNotFinishedStolenVehicle", 
	query = "SELECT stolen,unit.unitId, unit.vehicle.plateNo "
			+ "FROM StolenVehicleTable stolen,UnitTable unit "
//			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "WHERE stolen.vehicleId = unit.vehicle.id "
			+ "AND stolen.endTime IS NULL "),
	@NamedQuery(name = "SelectContactCount", 
	query = "SELECT COUNT(contact) "
			+ "FROM AppContact contact "
			+ "WHERE contact.contactKey = :customerId "
			+ "AND contact.minitype = 5 "),
	@NamedQuery(name = "SelectContact", 
	query = "SELECT contact "
			+ "FROM AppContact contact "
			+ "WHERE contact.contactKey = :customerId "
			+ "AND contact.minitype = 5 "),
	@NamedQuery(name = "SelectStolenVehicleReport", 
	query = "SELECT DISTINCT stolenVehicle, unit "
			+ "FROM StolenVehicleTable stolenVehicle,"
			+ "UnitTable unit "
			+ "LEFT OUTER JOIN FETCH unit.customer "
			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "LEFT OUTER JOIN FETCH unit.unitType "
			+ "LEFT OUTER JOIN unit.linkman linkman "
			+ "WHERE stolenVehicle.vehicleId = unit.vehicle.id "
			+ "AND stolenVehicle.beginTime >= :beginTime "
			+ "AND stolenVehicle.beginTime <= :endTime "),
	@NamedQuery(name = "SelectStolenVehicleReportCount", 
	query = "SELECT COUNT(DISTINCT stolenVehicle) "
			+ "FROM StolenVehicleTable stolenVehicle,"
			+ "UnitTable unit "
			+ "LEFT OUTER JOIN unit.customer customer "
			+ "LEFT OUTER JOIN unit.vehicle vehicle "
			+ "LEFT OUTER JOIN unit.unitType unitType "
			+ "LEFT OUTER JOIN unit.linkman linkman "
			+ "WHERE stolenVehicle.vehicleId = unit.vehicle.id "
			+ "AND stolenVehicle.beginTime >= :beginTime "
			+ "AND stolenVehicle.beginTime <= :endTime "),
	@NamedQuery(name = "SelectUnreportStat", 
	query = "SELECT lastPosition, unit, linkman.phone "
			+ "FROM LastPositionTable lastPosition,"
			+ "LinkmanTable linkman, UnitTable unit "
			+ "LEFT OUTER JOIN FETCH unit.customer "
			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "LEFT OUTER JOIN FETCH unit.unitType "
			+ "WHERE lastPosition.callLetter = unit.callLetter "
			+ "AND linkman.vehicleId=unit.vehicle.id "
			+ "AND linkman.linkmanType = '1' "),
	@NamedQuery(name = "SelectUnreportStatCount", 
	query = "SELECT COUNT(lastPosition) "
			+ "FROM LastPositionTable lastPosition,"
			+ "LinkmanTable linkman, UnitTable unit "
			+ "WHERE lastPosition.callLetter = unit.callLetter "
			+ "AND linkman.vehicleId=unit.vehicle.id "
			+ "AND linkman.linkmanType = '1' "),
	@NamedQuery(name = "SelectLocateFault", 
	query = "SELECT lastPosition, unit, linkman.phone "
			+ "FROM LastPositionTable lastPosition,"
			+ "LinkmanTable linkman, UnitTable unit "
			+ "LEFT OUTER JOIN FETCH unit.customer "
			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "LEFT OUTER JOIN FETCH unit.unitType "
			+ "WHERE lastPosition.callLetter = unit.callLetter "
			+ "AND linkman.vehicleId=unit.vehicle.id "
			+ "AND linkman.linkmanType = '1' "
			+ "AND lastPosition.gpsTime>=:beginTime "
			+ "AND lastPosition.gpsTime<=:endTime "),
	@NamedQuery(name = "SelectLocateFaultCount", 
	query = "SELECT COUNT(lastPosition) "
			+ "FROM LastPositionTable lastPosition,"
			+ "LinkmanTable linkman, UnitTable unit "
			+ "WHERE lastPosition.callLetter = unit.callLetter "
			+ "AND linkman.vehicleId=unit.vehicle.id "
			+ "AND linkman.linkmanType = '1' "
			+ "AND lastPosition.gpsTime>=:beginTime "
			+ "AND lastPosition.gpsTime<=:endTime "),
	@NamedQuery(name = "SelectTelSearchGroup", 
	query = "SELECT g "
			+ "FROM SeatSegGroupTable g "
			+ "WHERE g.groupType=1 ORDER BY g.pyname"),
	@NamedQuery(name = "SelectTelSearchPhoneBooksByGroupId", 
	query = "SELECT phoneBooks "
			+ "FROM SeatSegGroupTable g "
			+ "LEFT OUTER JOIN g.phoneBooks phoneBooks "
			+ "WHERE g.groupType=1 "
			+ "AND g.groupId=:groupId ORDER BY phoneBooks.pyname"),
	@NamedQuery(name = "SelectTelSearchSearchPhoneBooks", 
	query = "SELECT phoneBook "
			+ "FROM SeatSegPhonebookTable phoneBook "
			+ "LEFT JOIN phoneBook.groups g "
			+ "WHERE g.groupType=1 "
			+ "AND (phoneBook.name LIKE :param "
			+ "OR phoneBook.department LIKE :param "
			+ "OR phoneBook.ophone LIKE :param "
			+ "OR phoneBook.mobile LIKE :param "
			+ "OR phoneBook.pyname LIKE :param "
			+ "OR g.pyname LIKE :param ) "
			+ "ORDER BY phoneBook.pyname "),
	@NamedQuery(name = "SelectProductLib", 
	query = "SELECT product "
			+ "FROM ProductLibTable product "
			+ "LEFT OUTER JOIN product.unitTypeTable unitType "),
	@NamedQuery(name = "SelectProductLibCount", 
	query = "SELECT COUNT(product) "
			+ "FROM ProductLibTable product "
			+ "LEFT OUTER JOIN product.unitTypeTable unitType "),
	@NamedQuery(name = "SelectSendCmd", 
	query = "SELECT cmd,cmdType,sysvalue.name "
			+ "FROM SendCmdTable cmd,UnitTable unit,SysValueTable sysvalue "
			+ "LEFT OUTER JOIN cmd.cmdType cmdType "			
			+ "WHERE unit.unitId = cmd.unitId AND unit.companyNo = sysvalue.value "
			+ " AND cmd.opSrc='android' "
			+ "AND sysvalue.type=1010 "
			+ "AND cmd.cmdTime>=:beginTime "
			+ "AND cmd.cmdTime<=:endTime "),
	@NamedQuery(name = "SelectSendCmdCount", 
	query = "SELECT COUNT(cmd) "
			+ "FROM SendCmdTable cmd "				
			+ "WHERE cmd.opSrc='android' "			
			+ "AND cmd.cmdTime>=:beginTime "
			+ "AND cmd.cmdTime<=:endTime "),
	@NamedQuery(name = "SelectAlarmAll", 
	query = "SELECT alarm,sysvalue.name,sysvalue1.name,unit,unit.customer,unit.vehicle "
			+ "FROM AlarmTable alarm,UnitTable unit,SysValueTable sysvalue,SysValueTable sysvalue1 "			
			+ "LEFT OUTER JOIN FETCH unit.vehicle "
			+ "WHERE unit.unitId = alarm.unitId "
			+ "AND unit.companyNo = sysvalue.value "
			+ "AND unit.vehicle.typeId = sysvalue1.value "
			+ "AND sysvalue.type=1010 "
			+ "AND sysvalue1.type=2030 "
			+ "AND alarm.handleStatus=4 "
			+ "AND alarm.alarmTime>=:beginTime "
			+ "AND alarm.alarmTime<=:endTime "),
	@NamedQuery(name = "SelectAlarmAllCount", 
	query = "SELECT count(alarm) "
			+ "FROM AlarmTable alarm,UnitTable unit,SysValueTable sysvalue,SysValueTable sysvalue1 "			
			+ "WHERE unit.unitId = alarm.unitId "
			+ "AND unit.companyNo = sysvalue.value "
			+ "AND unit.vehicle.typeId = sysvalue1.value "
			+ "AND sysvalue.type=1010 "
			+ "AND sysvalue1.type=2030 "
			+ "AND alarm.handleStatus=4 "
			+ "AND alarm.alarmTime>=:beginTime "
			+ "AND alarm.alarmTime<=:endTime "),
	@NamedQuery(name = "SelectStatus",
	query = "SELECT status FROM StatusTable status WHERE isDel=0")
})
public abstract class SQL {

}
