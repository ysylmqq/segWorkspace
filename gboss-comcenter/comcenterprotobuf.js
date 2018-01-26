/*
 * 定义通信中心的prtotbuf通信协议, 请参考GBOSS.proto、comcenter.proto及AlarmArray.proto的注释
 * Copyright 2014 chinagps, Inc.
 */
var comcenterprotobuf = "\
message MapEntry {\
    required string key = 1;\
    required string value = 2;\
};\
\
message OBDInfo {\
	optional int32 remainOil = 1;\
	optional int32 remainPercentOil = 2;\
	optional int32 averageOil = 3;\
	optional int32 hourOil = 4;\
	optional int32 totalDistance = 5;\
	optional int32 waterTemperature = 6;\
	optional int32 reviceOil = 7;\
	optional int32 rotationSpeed = 8;\
	optional int32 interTemperature = 9;\
	optional int32 airDischange = 10;\
	repeated MapEntry otherInfoes = 11;\
    optional string callLetter = 12;\
    optional int64 gpsTime = 13;\
    optional int32 speed = 14;\
    optional int32 remainDistance = 15;\
    optional int32 unitType = 16;\
	optional int32 history = 17;\
    optional int32 batteryVoltage = 18;\
    optional int32 engineLoad = 19;\
};\
\
message GpsSimpleInfo {\
    required int64 gpsTime = 1;\
    required bool loc = 2;\
    required int32 lat = 3;\
    required int32 lng = 4;\
    required int32 speed = 5;\
    required int32 course = 6;\
    repeated int32 status = 7;\
    optional int32 totalDistance = 8;\
    optional int32 oil = 9;\
	optional int32 remainPercentOil = 10;\
    optional int32 canspeed = 11;\
    optional int32 remainDistance = 12;\
};\
\
message BaseStationInfo {\
    required string mcc = 1;\
    required string mnc = 2;\
    required int32 lac = 3;\
    required int32 cid = 4;\
    optional int32 bsss = 5;\
};\
\
message BaseStationAddress {\
	required int32 lat = 1;\
	required int32 lng = 2;\
	optional int32 precision = 3;\
	optional string addressDesc = 4;\
};\
\
message YDWInfo {\
	required int32 temperature = 1;\
	required int32 drivingTime = 2;\
	required int32 totalDistance = 3;\
	required int32 unitPower = 4;\
	required int32 bluetoothPower = 5;\
};\
\
\message NodeLostInfo {\
	optional int32 abs = 1;\
	optional int32 esp = 2;\
	optional int32 ems = 3;\
	optional int32 peps = 4;\
	optional int32 tcu = 5;\
	optional int32 bcm = 6;\
	optional int32 icm = 7;\
};\
\
message NodeFaultInfo {\
	optional int32 ebd = 1;\
	optional int32 abs = 2;\
	optional int32 esp = 3;\
	optional int32 svs = 4;\
	optional int32 mil = 5;\
	optional int32 tcu = 6;\
	optional int32 peps = 7;\
	optional int32 tbox = 8;\
};\
\
message FaultLightStatus {\
	optional NodeLostInfo nodeLostInfo = 1;\
	optional NodeFaultInfo nodeFaultInfo = 2;\
};\
\
message PengaodaGpsInfo {\
	optional int32 travelSerialNo = 1;\
	optional int32 deviceUUID = 2;\
	optional string softHardwareVersion = 3;\
	optional string gsmId = 4;\
	optional string imsi = 5;\
	optional string iccid = 6;\
	optional string vin = 7;\
	optional int32 vehicleDistance = 8;\
};\
\
message BeiJingInfo {\
	optional int32 devid = 1;\
	optional int32 rfid = 2;\
	optional int32 voltage = 3;\
	optional int32 electricity = 4;\
	optional int32 gsmvalue = 5;\
	optional int32 satellitecount = 6;\
};\
\
message GpsBaseInfo {\
    required int64 gpsTime = 1;\
    required bool loc = 2;\
    required int32 lat = 3;\
    required int32 lng = 4;\
    required int32 speed = 5;\
    required int32 course = 6;\
    repeated int32 status = 7;\
    optional int32 totalDistance = 8;\
    optional int32 oil = 9;\
    optional int32 oilPercent = 10;\
    optional int32 temperature1 = 11;\
    optional int32 temperature2 = 12;\
    repeated MapEntry appendParams = 13;\
    optional OBDInfo obdInfo = 14;\
	repeated BaseStationInfo baseStations = 15;\
	optional BaseStationAddress address = 16;\
	optional YDWInfo ydwInfo = 17;\
	optional int32 signal = 18;\
	optional FaultLightStatus faultLightStatus = 19;\
	optional int32 height = 20;\
	optional PengaodaGpsInfo padInfo = 21;\
	optional BeiJingInfo beijingInfo = 22;\
};\
\
message GpsRoadInfo {\
    required string name = 1;\
    required int32 level = 2;\
    required int32 distance = 3;\
    optional int32 id = 4;\
    optional int32 latOnRoad = 5;\
    optional int32 lngOnRoad = 6;\
};\
\
\message GpsPointInfo {\
    required string name = 1;\
    required int32 type= 2;\
    required int32 distance = 3;\
    optional int32 id = 4;\
};\
\
message GpsReferPosition {\
    required string province = 1;\
    required string city = 2;\
    required string county = 3;\
    repeated GpsRoadInfo roads = 4;\
    repeated GpsPointInfo points = 5;\
};\
\
message GpsInfo {\
    required string callLetter = 1;\
    required GpsBaseInfo baseInfo = 2;\
    optional GpsReferPosition referPosition = 3;\
    optional bytes content = 4;\
    optional int32 history = 5;\
};\
\
message AlarmInfo {\
    required string callLetter = 1;\
    required GpsBaseInfo baseInfo = 2;\
    optional GpsReferPosition referPosition = 3;\
    optional bytes content = 4;\
    optional int32 history = 5;\
    optional int32 unittype = 6;\
    optional int32 triggercond = 7;\
};\
\
message UnitVersion {\
	required string callLetter = 1;\
	required string version = 2;\
	optional int32  result = 3;\
};\
\
message OperateDataBaseInfo {\
    required int32 price = 1;\
    required int32 countTime = 2;\
    required int32 operateMoney = 3;\
    required int32 operateMile = 4;\
    optional int32 nullMile = 5;\
    optional int32 overSpeedMile = 6;\
    optional int32 overSpeedCount = 7;\
    optional int32 powerOffCount = 8;\
    optional int32 powerOffTime = 9;\
    optional int32 sequenceNo = 10;\
    repeated MapEntry appendParams = 11;\
    optional int32 perprice = 12;\
    optional string licenseno = 13;\
};\
\
message OperateData {\
    required string callLetter = 1;\
    required OperateDataBaseInfo baseInfo = 2;\
    required GpsBaseInfo startGps = 3;\
    optional GpsReferPosition startPoi = 4;\
    required GpsBaseInfo stopGps = 5;\
    optional GpsReferPosition stopPoi = 6;\
    optional bytes content = 7;\
};\
\
message ShortMessage{\
	required string callLetter = 1;\
	required string msg = 2;\
	optional int64 recvTime = 3;\
};\
\
message PengaodaTravelInfo {\
	optional int32 speed0Distance = 1;\
	optional int32 speed20Distance = 2;\
	optional int32 speed40Distance = 3;\
	optional int32 speed60Distance = 4;\
	optional int32 speed80Distance = 5;\
	optional int32 speed100Distance = 6;\
	optional int32 speed120Distance = 7;\
	optional int32 rotationRate0Distance = 8;\
	optional int32 rotationRate1000Distance = 9;\
	optional int32 rotationRate2000Distance = 10;\
	optional int32 rotationRate3000Distance = 11;\
	optional int32 rotationRate4000Distance = 12;\
	optional int32 rotationRate5000Distance = 13;\
	optional int32 throttleOpen0Distance = 14;\
	optional int32 throttleOpen20Distance = 15;\
	optional int32 throttleOpen40Distance = 16;\
	optional int32 throttleOpen60Distance = 17;\
	optional int32 throttleOpen80Distance = 18;\
};\
\
message TravelInfo {\
	required string callLetter = 1;\
	required int64 startTime = 2;\
	required int64 endTime = 3;\
	optional int32 distance = 4;\
	optional int32 maxSpeed = 5;\
	optional int32 overSpeedTime = 6;\
	optional int32 quickBrakeCount = 7;\
	optional int32 emergencyBrakeCount = 8;\
	optional int32 quickSpeedUpCount = 9;\
	optional int32 emergencySpeedUpCount = 10;\
	optional int32 averageSpeed = 11;\
	optional int32 maxWaterTemperature = 12;\
	optional int32 maxRotationSpeed = 13;\
	optional int32 voltage = 14;\
	optional int32 totalOil = 15;\
	optional int32 averageOil = 16;\
	optional int32 tiredDrivingTime = 17;\
	optional int32 serialNumber = 18;\
	optional int32 averageRotationSpeed = 19;\
	optional int32 maxOil = 20;\
	optional int32 idleTime = 21;\
    optional GpsBaseInfo startGps = 22;\
    optional GpsReferPosition startReferPos = 23;\
    optional GpsBaseInfo endGps = 24;\
    optional GpsReferPosition endReferPos = 25;\
	optional int32 history = 26;\
	optional int32 quickTurnCount = 27;\
	optional int32 overSpeedCount = 28;\
	optional int32 BrakeCount = 29;\
	optional int32 maxThrottleOpen = 30;\
	optional int32 averageThrottleOpen = 31;\
	optional PengaodaTravelInfo padInfo = 32;\
};\
\
message FaultDefine {\
	optional int32 faultType = 1;\
	repeated string faultCode = 2;\
}\
message FaultInfo {\
	required string callLetter = 1;\
	required int64 faultTime = 2;\
	repeated FaultDefine faults = 3;\
	optional int32 unitType = 4;\
}\
\
message AppNoticeInfo {\
    required string callLetter = 1;\
    required string title = 2;\
    required string content = 3;\
    optional GpsBaseInfo baseInfo = 4;\
    optional GpsReferPosition referPosition = 5;\
	optional int32 cmdId = 6;\
	optional int32 cmdretcode = 7;\
    optional string cmdretmsg = 8;\
	optional int32 noticetype = 9;\
	optional int32 alarmstatus = 10;\
    optional string cmdsn = 11;\
};\
\
message ECUConfig {\
	required string callLetter = 1;\
	required int64 updateTime = 2;\
	optional int32 checkFlag = 3;\
	optional int32 abs = 4;\
	optional int32 esp = 5;\
	optional int32 peps = 6;\
	optional int32 tpms = 7;\
	optional int32 srs = 8;\
	optional int32 eps = 9;\
	optional int32 ems = 10;\
	optional int32 immo = 11;\
	optional int32 bcm = 12;\
	optional int32 tcu = 13;\
	optional int32 icm = 14;\
	optional int32 apm = 15;\
	optional bytes configContent = 16;\
};\
\
message ComCenterMessage {\
    message ComCenterBaseMessage {\
        required int32 id = 1;\
        optional bytes content = 2;\
    };\
    repeated ComCenterBaseMessage messages = 1;\
};\
\
message Login {\
    required string username = 1;\
    required string password = 2;\
    repeated string callLetters = 3;\
    optional string seatid = 4;\
    optional string usertype = 5;\
    optional string userversion = 6;\
};\
\
message Login_ACK {\
    required int32 retcode = 1;\
	optional string retmsg = 2;\
    optional string username = 3;\
};\
\
message AddMonitor {\
    repeated string callLetters = 1;\
    repeated int32  infotypes = 2;\
    optional bool clearold = 3;\
};\
\
message AddMonitor_ACK {\
    required int32 retcode = 1;\
	optional string retmsg = 2;\
    repeated string callLetters = 3;\
};\
\
message RemoveMonitor {\
    repeated string callLetters = 1;\
    repeated int32  infotypes = 2;\
};\
\
message RemoveMonitor_ACK {\
    required int32 retcode = 1;\
	required string retmsg = 2;\
    repeated string callLetters = 3;\
};\
\
message DeliverUnitLoginOut {\
	required string callLetter = 1;\
	required int32 inorout = 2;\
	optional int32 gatewayid = 3;\
	optional int32 gatewaytype = 4;\
	optional int64 loginTime = 5;\
	optional int64 logoutTime = 6;\
	optional string unitversion = 7;\
	optional bool upgradegateway = 8;\
};\
\
message GetLastInfo {\
    required int32 infoType = 1;\
    repeated string callLetters = 2;\
	optional string sn = 3;\
};\
\
message GetLastInfo_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    repeated GpsInfo gpses = 3;\
    repeated TravelInfo travels = 4;\
    repeated FaultInfo faults = 5;\
	optional string sn = 6;\
    repeated OperateData operates = 7;\
    repeated ShortMessage sms = 8;\
    repeated AlarmInfo alarms = 9;\
    repeated OBDInfo obds = 10;\
    repeated DeliverUnitLoginOut unitloginout = 11;\
    repeated int64 timestamps = 12;\
};\
\
message GetHistoryInfo {\
    required string callLetter = 1;\
    required int32 infoType = 2;\
    required int64 starttime = 3;\
    required int64 endtime = 4;\
    optional int32 pageNumber = 5;\
    optional int32 totalNumber = 6;\
    optional bool autonextpage = 7;\
	optional string sn = 8;\
	optional bool reversed = 9;\
	optional bool norepeat = 10;\
};\
\
message GetHistoryInfoNextPage {\
    required string callLetter = 1;\
    required int32 infoType = 2;\
	optional string sn = 3;\
};\
\
message GetHistoryInfo_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required bool lastPage = 3;\
    repeated GpsInfo gpses = 4;\
    repeated TravelInfo travels = 5;\
    repeated FaultInfo faults = 6;\
	optional string sn = 7;\
    repeated OperateData operates = 8;\
    repeated ShortMessage sms = 9;\
    repeated AlarmInfo alarms = 10;\
    repeated OBDInfo obds = 11;\
    repeated DeliverUnitLoginOut unitloginout = 12;\
    repeated int64 timestamps = 13;\
};\
\
message GetHistorySimpleGpsInfo_ACK {\
    required string callLetter = 1;\
    required int32 retcode = 2;\
    optional string retmsg = 3;\
    required bool lastPage = 4;\
	optional string sn = 5;\
    repeated GpsSimpleInfo gpses = 6;\
    repeated int64 timestamps = 7;\
};\
\
message StopHistoryInfo {\
    required string callLetter = 1;\
    required int32 infoType = 2;\
	optional string sn = 3;\
};\
\
message StopHistoryInfo_ACK {\
    required string callLetter = 1;\
    required int32 infoType = 2;\
    required int32 retcode = 3;\
    optional string retmsg = 4;\
	optional string sn = 5;\
};\
\
\message SendCommand {\
	required string sn = 1;\
	repeated string callLetters = 2;\
	required int32 cmdId = 3;\
	repeated string params = 4;\
	optional string ackProxy = 5;\
	optional int32 channelId = 6;\
	optional bool addmonitor = 7;\
};\
\
message SendCommandSend_ACK {\
	required string sn = 1;\
	required string callLetter = 2;\
	required int32 cmdId = 3;\
	required int32 retcode = 4;\
    optional string retmsg = 5;\
    optional int32  unitsn = 6;\
};\
\
message SendCommand_ACK {\
	required string sn = 1;\
	required string callLetter = 2;\
	required int32 cmdId = 3;\
	required int32 retcode = 4;\
	repeated string params = 5;\
	repeated GpsInfo gpsInfo = 6;\
    optional string retmsg = 7;\
    optional int32  unitsn = 8;\
};\
\
message GetCommandHistory {\
    required string callLetter = 1;\
    required int64 starttime = 3;\
    required int64 endtime = 4;\
};\
\
message GetCommandHistory_ACK {\
    message CommandHistoryInfo {\
        required int32 cmd_id = 1;\
        required int64 cmd_time = 2;\
        optional string op_name = 3;\
        optional string op_src = 4;\
        optional string op_ip = 5;\
        optional int32 gateway_id = 6;\
        optional int32 mode = 7;\
        optional int64 send_time = 8;\
        optional int32 send_flag = 9;\
        optional int64 res_time = 10;\
        optional int32 res_flag = 11;\
        optional string res_msg = 12;\
    };\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string callLetter = 3;\
    optional string plateno = 4;\
    repeated CommandHistoryInfo commandinfoes = 5;\
};\
\
message DeliverGPS {\
	required GpsInfo gpsinfo = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
    optional int32 alarmid = 4;\
    optional string alarmname = 5;\
};\
\
message DeliverAlarm {\
	required AlarmInfo alarminfo = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverOperateData {\
	required OperateData data = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverSMS {\
	required ShortMessage msg = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverTravel {\
	required TravelInfo travelinfo = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverFault {\
	required FaultInfo faultinfo = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverOBD {\
	required OBDInfo obdinfo = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverAppNotice {\
	required AppNoticeInfo noticeinfo = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverUnitVersion {\
	required UnitVersion unitVersion = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message DeliverECUConfig {\
	required ECUConfig ecuConfig = 1;\
	optional int32 gatewayid = 2;\
	optional int32 gatewaytype = 3;\
};\
\
message TestDeliver {\
    required int32 infoType = 1;\
    repeated string callLetters = 2;\
};\
\
message SetAlarmBusy {\
    required string username = 1;\
    required string seatid = 2;\
    required bool busy = 3;\
    optional string callLetter = 4;\
};\
\
message SetAlarmBusy_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    optional string username = 3;\
    optional bool busy = 4;\
    repeated string handleseatname = 5;\
};\
\
message AllotAlarm {\
    required string username = 1;\
    required string callLetter = 2;\
    required string alarmsn = 3;\
    required int64 alarmTime = 4;\
    required int32 alarmid = 5;\
    required string alarmname = 6;\
    optional GpsSimpleInfo gpsinfo = 7;\
    optional int32 alarmcount = 8;\
    optional bool append = 9;\
};\
\
message AllotAlarm_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string username = 3;\
    required string callLetter = 4;\
    required string alarmsn = 5;\
};\
\
message PauseAlarm {\
    required string username = 1;\
    required string callLetter = 2;\
    required string alarmsn = 3;\
    optional string pausemsg = 4;\
	optional string slaver = 5;\
};\
\
message PauseAlarm_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string username = 3;\
    required string callLetter = 4;\
    required string alarmsn = 5;\
	optional string slaver = 6;\
};\
\
message HandleAlarm {\
    required string username = 1;\
    required string callLetter = 2;\
    required string alarmsn = 3;\
    required int32  handlecode = 4;\
    required string handlemsg = 5;\
	optional string slaver = 6;\
};\
\
message HandleAlarm_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string username = 3;\
    required string callLetter = 4;\
    required string alarmsn = 5;\
	optional string slaver = 6;\
};\
\
message CancelPauseAlarm {\
    required string username = 1;\
    required string callLetter = 2;\
    required string alarmsn = 3;\
	optional string slaver = 4;\
};\
\
message CancelPauseAlarm_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string username = 3;\
    required string callLetter = 4;\
    required string alarmsn = 5;\
	optional string slaver = 6;\
};\
\
message AskSeatList {\
    required string username = 1;\
    optional string callLetter = 2;\
    optional bool isidle = 3;\
    optional bool noself = 4;\
};\
\
message AskSeatList_ACK {\
    message SeatBaseInfo {\
        required string username = 1;\
        required string seatid = 2;\
        required bool isidle = 3;\
    };\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    repeated SeatBaseInfo seats = 3;\
};\
\
message TransferAlarm {\
    required string srcusername = 1;\
    required string dstusername = 2;\
    required string callLetter = 3;\
    required string alarmsn = 4;\
    optional string transfermsg = 5;\
};\
\
message TransferAlarm_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string srcusername = 3;\
    required string dstusername = 4;\
    required string callLetter = 5;\
    required string alarmsn = 6;\
};\
\
message AllotTransferAlarm {\
    required string username = 1;\
    required string callLetter = 2;\
    required string alarmsn = 3;\
    required int64 alarmTime = 4;\
    required int32 alarmid = 5;\
    required string alarmname = 6;\
    optional GpsSimpleInfo gpsinfo = 7;\
    optional int32 alarmcount = 8;\
    required string srcusername = 9;\
    optional string transfermsg = 10;\
};\
\
message AllotTransferAlarm_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string username = 3;\
    required string callLetter = 4;\
    required string alarmsn = 5;\
    required string srcusername = 6;\
};\
\
message AskAlarmList {\
    required string username = 1;\
    optional bool onlyself = 2;\
    optional bool onlycount = 3;\
};\
\
message AskAlarmList_ACK {\
    message AlarmBaseInfo {\
        required string callLetter = 1;\
        required int64 alarmTime = 2;\
        required int32 alarmid = 3;\
        required string alarmname = 4;\
        required int32 status = 5;\
        optional string seatname = 6;\
    };\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    repeated AlarmBaseInfo alarms = 3;\
    optional int32 alarmscount = 4;\
};\
\
message NewAlarm {\
    required string slaver = 1;\
    required string callLetter = 2;\
    required string alarmsn = 3;\
    required int64 alarmTime = 4;\
    required int32 alarmid = 5;\
    required int32 level = 6;\
    optional GpsSimpleInfo gpsinfo = 7;\
};\
\
message NewAlarm_ACK {\
    required int32 retcode = 1;\
    optional string retmsg = 2;\
    required string slaver = 3;\
    required string callLetter = 4;\
    required string alarmsn = 5;\
};";
