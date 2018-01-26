/*
 * 定义通信中心的prtotbuf通信协议, 请参考GBOSS.proto、comcenter.proto及AlarmArray.proto的注释
 * Copyright 2014 chinagps, Inc.
 */
var comcenterprotobuf = "\
message ReserveMessage{\
	required string callLetter = 1;\
};\
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
};\
\
message GpsInfo {\
    required string callLetter = 1;\
    required GpsBaseInfo baseInfo = 2;\
    optional ReserveMessage referPosition = 3;\
    optional bytes content = 4;\
    optional int32 history = 5;\
};\
\
message AlarmInfo {\
    required string callLetter = 1;\
    required GpsBaseInfo baseInfo = 2;\
    optional ReserveMessage referPosition = 3;\
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
message ShortMessage{\
	required string callLetter = 1;\
	required string msg = 2;\
	optional int64 recvTime = 3;\
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
    optional ReserveMessage startReferPos = 23;\
    optional GpsBaseInfo endGps = 24;\
    optional ReserveMessage endReferPos = 25;\
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
    optional ReserveMessage referPosition = 5;\
	optional int32 cmdId = 6;\
	optional int32 cmdretcode = 7;\
    optional string cmdretmsg = 8;\
	optional int32 noticetype = 9;\
	optional int32 alarmstatus = 10;\
    optional string cmdsn = 11;\
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
    repeated ReserveMessage operates = 7;\
    repeated ShortMessage sms = 8;\
    repeated AlarmInfo alarms = 9;\
    repeated OBDInfo obds = 10;\
    repeated DeliverUnitLoginOut unitloginout = 11;\
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
    repeated ReserveMessage operates = 8;\
    repeated ShortMessage sms = 9;\
    repeated AlarmInfo alarms = 10;\
    repeated OBDInfo obds = 11;\
    repeated DeliverUnitLoginOut unitloginout = 12;\
};\
\
message GetHistorySimpleGpsInfo_ACK {\
    required string callLetter = 1;\
    required int32 retcode = 2;\
    optional string retmsg = 3;\
    required bool lastPage = 4;\
	optional string sn = 5;\
    repeated GpsSimpleInfo gpses = 6;\
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
};"