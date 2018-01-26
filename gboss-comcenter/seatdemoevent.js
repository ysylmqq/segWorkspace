﻿/*!
 * 
 * Copyright 2014 chinagps, Inc. zhangxz
 * 
 */
"use strict";
/*
 * 
 */
//记录日志

var log = document.getElementById("log");
//保存处理流程过程中的呼号和警情序列号
var txtalarmcaller = document.getElementById("alarmcaller");
var txtalarmsn = document.getElementById("alarmsn");

var statusname = [
    [1, "申请医疗服务"],
    [2, "申请故障服务"],
    [3, "电瓶欠压"],
    [4, "车台主电被切断"],
    [5, "劫警"],
    [6, "盗警"],
    [7, "申请信息服务"],
    [8, "遥控报警"],
    [9, "手柄故障"],
    [10, "GPS定位时间过长"],
    [11, "二重密码锁报警"],
    [12, "碰撞报警"],
    [13, "越界报警"],
    [14, "超速报警"],
    [15, "遥控器电池电压过低"],
    [16, "GPS接收机没有输出"],
    [17, "拖吊报警"],
    [18, "震动报警"],
    [19, "后厢车门开启"],
    [20, "遥控器故障"],
    [21, "车门打开"],
    [22, "车辆设防"],
    [23, "车辆熄火"],
    [24, "禁止时间行使报警"],
    [25, "车门关闭"],
    [26, "油路断开"],
    [32, "车辆撤防"],
    [33, "车辆发动"],
    [34, "发动机故障"],
    [35, "事故疑点"],
    [36, "疲劳驾驶"],
    [37, "备电故障"],
    [38, "油压过低"],
    [39, "刹车"],
    [40, "左转向灯亮"],
    [41, "右转向灯亮"],
    [42, "大灯亮"],
    [43, "大灯关"],
    [44, "水温异常"],
    [45, "CAN通讯故障"],
    [50, "GSM故障"],
    [51, "LCD故障"],
    [52, "键盘故障"],
    [53, "计价器故障"],
    [54, "轮胎在缓慢漏气"],
    [55, "胎压低"],
    [56, "胎压高"],
    [57, "胎温高"],
    [58, "超声波报警"],
    [59, "重车"],
    [60, "空车"],
    [61, "任务车"],
    [62, "开门盗警"],
    [63, "点火盗警"],
    [64, "GPS天线开路"],
    [65, "GPS天线短路"],
    [66, "后尾箱开"],
    [67, "后尾箱关"],
    [68, "终端休眠"],
    [69, "终端关机"],
	[70, "终端维修"],
    [72, "空调开"],
    [73, "空调关"],
    [74, "车窗开"],
    [75, "车窗关"],
    [78, "侧翻报警"],
    [79, "SOS救援"],
    [80, "偏离路线报警"],
    [83, "车灯亮"],
    [84, "车灯关"],
    [85, "后雾灯亮"],
    [86, "后雾灯关"],
    [87, "前雾灯亮"],
    [88, "前雾灯关"],
    [89, "近光灯亮"],
    [90, "近光灯关"],
    [91, "小灯亮"],
    [92, "小灯关"],
    [93, "电源ON"],
    [94, "电源START"],
    [96, "总线故障"],
    [97, "锁车电路故障"],
    [98, "越界报警(入)"],
    [101, "右后门开"],
    [102, "右后门关"],
    [103, "左后门开"],
    [104, "左后门关"],
    [105, "右前门开"],
    [106, "右前门关"],
    [107, "左前门(司机)开"],
    [108, "左前门(司机)关"],
    [111, "档位P"],
    [112, "档位N"],
    [113, "档位R"],
    [114, "档位D"],
    [115, "转向灯关"],
    [116, "右转向灯开"],
    [117, "左转向灯开"],
    [118, "危险灯开"],
	[120, "子母机连接断开报警"],
    [161, "远程诊断读故障报警"],
    [185, "远程诊断模块故障"],
    [200, "点火上报"],
    [201, "熄火上报"],
    [202, "熄火未关灯"],
    [203, "熄火未关门"],
    [204, "熄火未锁门"],
	[205, "发动机熄火上报"],
	[206, "发动机点火上报"],
	[207, "故障灯上报"],
	[208, "车辆防盗异常熄火上报"],
	[209, "远程热车定时熄火上报"],
	[210, "退出远程启动模式上报"],
	[211, "SOS紧急救援服务请求上报"],
	[212, "OBD故障码上报"],
    [1042, "酒后驾驶"],
];

var commandname = [
    [1, "查车"],
	[2, "车台复位"],
	[3, "密码清零"],
	[4, "锁车门"],
	[5, "开车门"],
	[6, "断油电"],
	[7, "恢复供油电"],
	[9, "允许手柄设置"],
	[10, "禁止电话打入"],
	[11, "禁止电话打出"],
	[12, "恢复电话功能"],
	[13, "开车窗"],
	[14, "关车窗"],
	[15, "鸣喇叭"],
	[16, "双闪灯"],
	[18, "回传黑匣子记录"],
	[19, "寻车"],
	[20, "停止回传黑匣子记录"],
	[21, "跟踪"],
	[22, "停止跟踪"],
	[23, "监听"],
	[24, "通话"],
	[25, "设置监听号码"],
	[26, "设置集群通话"],
	[28, "设置黑匣子记录时间间隔"],
	[29, "限制车辆行驶速度"],
	[30, "解除车辆限速"],
	[31, "限制车辆行驶范围"],
	[32, "解除车辆行驶范围"],
	[33, "限制车辆行驶时间"],
	[34, "解除车辆行驶时间"],
	[37, "设置服务电话号码"],
	[38, "设置短消息服务中心号码"],
	[39, "查询服务电话号码"],
	[47, "设置短信特服号"],
	[48, "查询短信特服号"],
	[53, "发送终端短信"],
	[56, "开启定时报告"],
	[57, "停止定时报告"],
	[58, "开启盲区补报"],
	[59, "关闭盲区补报"],
	[64, "开启熄火连续报告"],
	[65, "关闭熄火连续报告"],
	[66, "开启点火熄火报告"],
	[67, "关闭点火熄火报告"],
	[68, "开启定距上传"],
	[69, "关闭定距上传"],
	[70, "开启拐点上报"],
	[71, "关闭拐点上报"],
	[72, "开启车身状态变化报告"],
	[73, "关闭车身状态变化报告"],
	[74, "开启关机报告"],
	[75, "关闭关机报告"],
	[76, "开启休眠报告"],
	[77, "关闭休眠报告"],
	[81, "设置APN"],
	[82, "查询APN"],
	[87, "设置GPRS通信参数"],
	[88, "查询GPRS通信参数"],
	[90, "唤醒GPS接收模块"],
	[91, "设置GPRS重连间隔"],
	[97, "打开车灯"],
	[98, "关闭车灯"],
	[99, "打开后尾箱"],
	[100, "查询空调参数"],
	[101, "打开空调"],
	[102, "关闭空调"],
	[103, "打开维修模式"],
	[104, "关闭维修模式"],
	[105, "启动发动机"],
	[106, "熄火发动机"],
	[112, "查询定时报告参"],
	[113, "查询点火熄火报告参数"],
	[114, "查询休眠报告参数"],
	[115, "查询关机报告参数"],
	[116, "查询车身状态变化报告参数"],
	[160, "读取车型"],
	[161, "标定车型"],
	[162, "清除故障码"],
	[163, "查询故障码"],
	[164, "查询呼入呼出限制"],
	[165, "通知终端升级"],
	[166, "设置SOS故障/救援服务号码"],
	[167, "查询SOS故障/救援服务号码"],
	[170, "开启故障上报"],
	[171, "关闭故障上报"],
	[172, "查询故障是否上报"],
	[173, "查询超速参数"],
	[174, "设置用户密码"],
	[175, "查询用户密码"],
	[176, "设置报警手机号码"],
	[177, "查询报警手机号码"],
	[179, "写配置"],
	[180, "查询配置"],
	[181, "读取终端（TBOX）信息"],
	[182, "设置侧翻校正参数"],
	[183, "查询侧翻校正参数"],
	[209, "投标（出租车）"],
	[658, "设置导航目的地"],
	[768, "设置一动网报警参数"],
	[769, "查询一动网报警参数"],
	[16437, "发送一般手机短信"],
];

function nowstring() {
	return SEGUtil_WS.date_2_string(new Date(), "[yyyy-MM-dd hh:mm:ss]");
};

function getstatusname(statusid) {
	for(var i=0; i<statusname.length; i++) {
		if (statusid == statusname[i][0]) {
			return statusname[i][1];
		}
	}
	return "状态未定义";
};

function getcommandname(cmdid) {
	for(var i=0; i<commandname.length; i++) {
		if (cmdid == commandname[i][0]) {
			return commandname[i][1];
		}
	}
	return "未知指令";
};

//websocket通信错误事件
function onerror(cli, event) {
    log.value += "websocket出现错误:" + cli.id + ", " + event.type + ", " + event.target.url + "\n";
};

//websocket连接成功事件
function onopen(cli, event) {
    log.value += "websocket连接成功:" + cli.id + ", " + event.type + ", " + event.target.url + "\n";
};

//websocket连接关闭事件
function onclose(cli, event) {
    log.value += "websocket断开连接:" + cli.id + ", " + event.type + ", " + event.target.url + "\n";
};

//websocket客户端登录(连接成功后自动登录，不用人工登录) 事件
function onlogin(cli, retcode, retmsg) {
	log.value += "登录结果: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	//AddMonitor();
};

//添加监控列表结果事件
function onaddmonitor(cli, retcode, retmsg, callLetters) {
	log.value += "添加监控列表结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", all callletter:\n";
	for(var i=0; i<callLetters.length; i++) {
		log.value += callLetters[i] + "\n";
	}
};

//取消监控列表结果事件
function onremovemonitor(cli, retcode, retmsg, callLetters) {
	log.value += "取消监控列表结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", remain callletter:\n";
	for(var i=0; i<callLetters.length; i++) {
		log.value += callLetters[i] + "\n";
	}
};

//命令发送成功回应
function onsendcommandsend(cli, sn, callLetter, cmdId, retcode, retmsg){
	log.value += "发送指令: " + callLetter + ", Id=" + cmdId + ", sn=" + sn;
	log.value += ", retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
};

//命令结果事件
function onsendcommand(cli, sn, callLetter, cmdId, retcode, retmsg, params, gpsInfo){
	log.value += "指令结果: " + callLetter + ", Id=" + cmdId + ", sn=" + sn;
	log.value += ", retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	if (params != null) {
		log.value += params;
	}
};

//显示指令记录
function ongetcommandhistory(cli, retcode, retmsg, callLetter, plateno, commandinfoes) {
	log.value += "指令历史记录: " + callLetter + ", 车牌号=" + plateno + ", retcode=";
	log.value += retcode  + ", retmsg=" + retmsg + ", 指令条数=" + commandinfoes.length + "\n";
	for(var i=0; i<commandinfoes.length; i++) {
		log.value += getcommandname(commandinfoes[i].cmd_id);
		log.value += ": 时间:";
		var cmdTime = new Date(commandinfoes[i].cmd_time.toNumber());
		log.value += SEGUtil_WS.date_2_string(cmdTime, "yyyy-MM-dd hh:mm:ss");
		log.value += ", 操作员:";
		log.value += commandinfoes[i].op_name;
		log.value += ",";
		log.value += commandinfoes[i].op_src;
		log.value += ",";
		log.value += commandinfoes[i].op_ip;
		log.value += ", 网关:";
		log.value += commandinfoes[i].gateway_id;
		log.value += commandinfoes[i].mode==1 ? ",短信" : ",流量";
		if (commandinfoes[i].send_time != null) {
			cmdTime = commandinfoes[i].send_time.toNumber();
			if (cmdTime > 0) {
				log.value += ", 发送(";
				if (commandinfoes[i].send_flag == 0) {
					log.value += "成功";
				} else {
					log.value += commandinfoes[i].send_flag + "," + commandinfoes[i].res_msg;					
				}
				log.value += "):";
				log.value += SEGUtil_WS.date_2_string(new Date(cmdTime), "yyyy-MM-dd hh:mm:ss");
			}
		} else {
			log.value += ", 未发送";
		}
		if (commandinfoes[i].res_time != null) {
			cmdTime = commandinfoes[i].res_time.toNumber();
			if (cmdTime > 0) {
				log.value += ", 回应(";
				if (commandinfoes[i].res_flag == 0) {
					log.value += "成功";
				} else {
					log.value += commandinfoes[i].res_flag + "," + commandinfoes[i].res_msg;
				}
				log.value += "): ";
				log.value += SEGUtil_WS.date_2_string(new Date(cmdTime), "yyyy-MM-dd hh:mm:ss");
			}
		} else {
			log.value += ", 无回应";
		}
		log.value += "\n";
	}
};

//显示入库时间
function displaystamptime(timestamp) {
	if (timestamp > 0) {
		var stampTime = new Date(timestamp.toNumber());
		log.value += "入库时间:";
		log.value += SEGUtil_WS.date_2_string(stampTime, "yyyy-MM-dd hh:mm:ss") + ",  ";
	}
}
//显示gps信息
function displaygpsinfo(gpsinfo, showstatus, timestamp) {
	displaystamptime(timestamp);
	var gpsTime = new Date(gpsinfo.baseInfo.gpsTime.toNumber());
	log.value += "gpstime:";
	log.value += SEGUtil_WS.date_2_string(gpsTime, "yyyy-MM-dd hh:mm:ss");
	//0、或不存在表示是实时GPS，1:表示是黑匣子记录, 2:表示盲点补传
	if (gpsinfo.history == 1) {
		log.value += ", 黑匣子"; 
	} else if (gpsinfo.history == 2) {
		log.value += ", 盲区补报"; 
	}
	log.value += ", " + gpsinfo.baseInfo.lng + ", " + gpsinfo.baseInfo.lat + ", " +	gpsinfo.baseInfo.speed;
	if (gpsinfo.baseInfo.loc) {
		log.value += ", 定位\n"; 
	} else {
		log.value += ", 不定位\n"; 
	}
	for(var i=0; i<gpsinfo.baseInfo.status.length; i++) {
		if (showstatus) {
			log.value += getstatusname(gpsinfo.baseInfo.status[i]);
		} else {
			log.value += gpsinfo.baseInfo.status[i];
		}
		if (i < gpsinfo.baseInfo.status.length - 1) {
			log.value += ", ";
		} else {
			log.value += "\n";
		}
	}
	if (gpsinfo.baseInfo.obdInfo != null) {
		log.value += "总量程:" + gpsinfo.baseInfo.obdInfo.totalDistance + ",剩油:" + gpsinfo.baseInfo.obdInfo.remainOil + ",:" + gpsinfo.baseInfo.obdInfo.remainPercentOil;
		log.value +=  "%,水温:" + gpsinfo.baseInfo.obdInfo.waterTemperature + ",速度:" + gpsinfo.baseInfo.obdInfo.speed + ",续航:" + gpsinfo.baseInfo.obdInfo.remainDistance + "\n";
	}
	if (gpsinfo.referPosition != null) {
		log.value += gpsinfo.referPosition.province + ", " + gpsinfo.referPosition.city + ", " + gpsinfo.referPosition.county + "\n";
	}
	if (gpsinfo.baseInfo.faultLightStatus != null) {
	   if (gpsinfo.baseInfo.faultLightStatus.nodeLostInfo != null) {
		  log.value += "丢失信息:";
          log.value += "abs=" + gpsinfo.baseInfo.faultLightStatus.nodeLostInfo.abs;
          log.value += ", esp=" + gpsinfo.baseInfo.faultLightStatus.nodeLostInfo.esp;
          log.value += ", ems=" + gpsinfo.baseInfo.faultLightStatus.nodeLostInfo.ems;
          log.value += ", peps=" + gpsinfo.baseInfo.faultLightStatus.nodeLostInfo.peps;
          log.value += ", tcu=" + gpsinfo.baseInfo.faultLightStatus.nodeLostInfo.tcu;
          log.value += ", bcm=" + gpsinfo.baseInfo.faultLightStatus.nodeLostInfo.bcm;
          log.value += ", icm=" + gpsinfo.baseInfo.faultLightStatus.nodeLostInfo.icm + "\n";
	   }
	   if (gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo != null) {
		  log.value += "故障信息:";
	      log.value += "ebd=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.ebd;
	      log.value += ", abs=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.abs;
	      log.value += ", esp=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.esp;
          log.value += ", svs=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.svs;
          log.value += ", mil=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.mil;
          log.value += ", tcu=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.tcu;
          log.value += ", peps=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.peps;
          log.value += ", tbox=" + gpsinfo.baseInfo.faultLightStatus.nodeFaultInfo.tbox + "\n";
	   }
	}
};

//显示主要gpsw信息
function displaysimplegpsinfo(gpsinfo, showstatus, timestamp) {
	displaystamptime(timestamp);
	var gpsTime = new Date(gpsinfo.gpsTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(gpsTime, "yyyy-MM-dd hh:mm:ss") + ", " + gpsinfo.lng + ", " + gpsinfo.lat + ", " + gpsinfo.speed +"\n";
	for(var i=0; i<gpsinfo.status.length; i++) {
		if (showstatus) {
			log.value += getstatusname(gpsinfo.status[i]);
		} else {
			log.value += gpsinfo.status[i];
		}
		if (i < gpsinfo.status.length - 1) {
			log.value += ", ";
		} else {
			log.value += "\n";
		}
	}
	log.value += "总量程:" + gpsinfo.totalDistance + ",剩油:" + gpsinfo.oil + "\n";
};

//显示OBD信息
function displayobd(obdinfo, timestamp) {
	log.value += obdinfo.callLetter +": ";
	displaystamptime(timestamp);
	if (obdinfo.gpsTime != null) {
		var gpsTime = new Date(obdinfo.gpsTime.toNumber());
		log.value += SEGUtil_WS.date_2_string(gpsTime, "yyyy-MM-dd hh:mm:ss") + ", ";
	};
	log.value += obdinfo.remainOil + ", ";
	log.value += obdinfo.remainPercentOil + ", ";
	log.value += obdinfo.totalDistance + "\n";
};

//显示故障信息
function displayfault(faultinfo, timestamp) {
	log.value += faultinfo.callLetter + " 故障: ";
	displaystamptime(timestamp);
	var faultTime = new Date(faultinfo.faultTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(faultTime, "yyyy-MM-dd hh:mm:ss") + "\n";
	for(var k=0; k<faultinfo.faults.length; k++) {
		log.value += faultinfo.faults[k].faultType + ": ";
		for(var j=0; j<faultinfo.faults[k].faultCode.length; j++) {
			log.value += faultinfo.faults[k].faultCode[j] + ", ";
		}
		log.value += "\n";
	}
};

//显示行程信息
function displaytravel(travelinfo, timestamp) {
	displaystamptime(timestamp);
	log.value += travelinfo.callLetter + " 行程: starttime: ";
	var startTime = new Date(travelinfo.startTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(startTime, "yyyy-MM-dd hh:mm:ss") + ", ";
	if (travelinfo.startGps) {
	    log.value += travelinfo.startGps.lng + ", " + travelinfo.startGps.lat +"\n";
	}
	if (travelinfo.endTime > 0) {
	    var endTime = new Date(travelinfo.endTime.toNumber());
	    log.value += "stoptime:" + SEGUtil_WS.date_2_string(endTime, "yyyy-MM-dd hh:mm:ss") + ": ";
	}
	if (travelinfo.endGps) {
		log.value += travelinfo.endGps.lng + ", " + travelinfo.endGps.lat +"\n";
	} else {
		log.value += "\n";
	}
};

//显示报警信息
function displayalarm(alarminfo, timestamp) {
	log.value += alarminfo.callLetter + " 报警: ";
	displaystamptime(timestamp);
	var alarmTime = new Date(alarminfo.baseInfo.gpsTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(alarmTime, "yyyy-MM-dd hh:mm:ss") + ", " +  alarminfo.unittype + ", " + alarminfo.trigger + ", ";
	log.value += alarminfo.baseInfo.lng + ", " + alarminfo.baseInfo.lat +"\n";
	for(var i=0; i<alarminfo.baseInfo.status.length; i++) {
		log.value += getstatusname(alarminfo.baseInfo.status[i]);
		if (i < alarminfo.baseInfo.status.length - 1) {
			log.value += ", ";
		} else {
			log.value += "\n";
		}
	}
	if (alarminfo.referPosition != null) {
		log.value += alarminfo.referPosition.province + ", " + alarminfo.referPosition.city + ", " + alarminfo.referPosition.county + "\n";
	} else {
		log.value += "\n";
	};
};

//显示出租车运营信息
function displayoperatedata(operatedata, timestamp) {
	log.value += operatedata.callLetter + "\n";
	displaystamptime(timestamp);
	var operateTime = new Date(operatedata.startGps.gpsTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(operateTime, "yyyy-MM-dd hh:mm:ss") + ", " + operatedata.startGps.lng + ", " + operatedata.startGps.lat +"\n";
	if (operatedata.startPoi != null) {
		log.value += operatedata.startPoi.province + ", " + operatedata.startPoi.city + ", " + operatedata.startPoi.county + "\n";
	} else {
		log.value += "\n";
	}
};

//显示短信
function displaysm(sm, timestamp) {
	log.value += sm.callLetter + ": ";
	displaystamptime(timestamp);
	var recvTime = new Date(sm.recvTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(recvTime, "yyyy-MM-dd hh:mm:ss") + ", " + sm.msg +"\n";
};

//显示终端登退录信息
function displayunitloginout(unitloginout, timestamp) {
	displaystamptime(timestamp);
	log.value += unitloginout.callLetter + ": 登录时间:";
	var loginTime = new Date(unitloginout.loginTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(loginTime, "yyyy-MM-dd hh:mm:ss");
	if (unitloginout.inorout==0 && unitloginout.logoutTime != null) {
		var logoutTime = new Date(unitloginout.logoutTime.toNumber());
		log.value += "退录时间:" + SEGUtil_WS.date_2_string(logoutTime, "yyyy-MM-dd hh:mm:ss");
	}
	log.value += "\n" + "gatewayid:" + unitloginout.gatewayid;
	log.value += ", gatewaytype:" + unitloginout.gatewaytype;
	if (unitloginout.upgradegateway != null) {
		log.value += ", upgateway:" + unitloginout.upgradegateway;
	}
	if (unitloginout.unitversion != null) {
		log.value += ", unitversion:" + unitloginout.unitversion;
	}
	log.value += "\n";
};

//读最后位置信息
function ongetlastgps(cli, retcode, retmsg, gpses, timestamps) {
	log.value += "最后位置: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<gpses.length; i++) {
		log.value += gpses[i].callLetter + "\n";
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displaygpsinfo(gpses[i], true, timestamps[i]);
		} else {
			displaygpsinfo(gpses[i], true, null);
		}
	}
};

//读最后位置信息
function ongetlastobd(cli, retcode, retmsg, obds, timestamps) {
	log.value += "最后OBD: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<obds.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayobd(obds[i], timestamps[i], timestamps[i]);
		} else {
			displayobd(obds[i], null);
		}
	};
};

//读最后车辆行程信息
function ongetlasttravel(cli, retcode, retmsg, travels, timestamps) {
	log.value += "最后行程: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<travels.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displaytravel(travels[i], timestamps[i]);
		} else {
			displaytravel(travels[i], null);
		}
	}
};

//读最后车辆故障信息
function ongetlastfault(cli, retcode, retmsg, faults, timestamps) {
	log.value += "最后故障: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<faults.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayfault(faults[i], timestamps[i]);
		} else {
			displayfault(faults[i], null);
		}
	}
};

//读最后警情信息
function ongetlastalarm(cli, retcode, retmsg, alarms, timestamps) {
	log.value += "最后警情: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<alarms.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayalarm(alarms[i], timestamps[i]);
		} else {
			displayalarm(alarms[i], null);
		}
	}
};

//读最后短信信息
function ongetlastsm(cli, retcode, retmsg, sms, timestamps) {
	log.value += "最后短信: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<sms.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displaysm(sms[i], timestamps[i]);
		} else {
			displaysm(sms[i], null);
		}
	}
};

//读最后运营数据
function ongetlastoperate(cli, retcode, retmsg, operates, timestamps) {
	log.value += "最后运营数据: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<operates.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayoperatedata(operates[i], timestamps[i]);
		} else {
			displayoperatedata(operates[i], null);
		}
	}
};

function ongetlastunitloginout(cli, retcode, retmsg, unitloginout, timestamps) {
	log.value += "最后登退录: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<unitloginout.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayunitloginout(unitloginout[i], timestamps[i]);
		} else {
			displayunitloginout(unitloginout[i], null);
		}
	}
};

//显示读最后信息错误
function ongetlasterror(cli, retcode, retmsg) {
	log.value += "*****取最后信息失败:(retcode=" + retcode  + ", retmsg=" + retmsg + "*****\n";
};

//读历史位置信息
function ongethistorygps(cli, retcode, retmsg, lastpage, gpses, timestamps) {
	log.value += "历史轨迹:(" + gpses.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<gpses.length; i++) {
		log.value += gpses[i].callLetter + "\n";
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displaygpsinfo(gpses[i], true, timestamps[i]);
		} else {
			displaygpsinfo(gpses[i], true, null);
		}
	}
};

//读历史位置主要信息
function ongethistorysimplegps(cli, callLetter, retcode, retmsg, lastpage, gpses, timestamps) {
	log.value += "历史轨迹主要信息:(" + gpses.length + ") callletter=" + callLetter + ", retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<gpses.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displaysimplegpsinfo(gpses[i], true, timestamps[i]);
		} else {
			displaysimplegpsinfo(gpses[i], true, null);
		}
	}
};
					
//读历史OBD
function ongethistoryobd(cli, retcode, retmsg, lastpage, obds, timestamps) {
	log.value += "历史OBD: (" + obds.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<obds.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayobd(obds[i], timestamps[i]);
		} else {
			displayobd(obds[i], null);
		}
	}
};

//读历史行程信息
function ongethistorytravel(cli, retcode, retmsg, lastpage, travels, timestamps) {
	log.value += "历史行程:(" + travels.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<travels.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displaytravel(travels[i], timestamps[i]);
		} else {
			displaytravel(travels[i], null);
		}
	}
};

//读历史故障信息
function ongethistoryfault(cli, retcode, retmsg, lastpage, faults, timestamps) {
	log.value += "历史故障:(" + faults.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<faults.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayfault(faults[i], timestamps[i]);
		} else {
			displayfault(faults[i], null);
		}
	}
};

//读历史运营数据
function ongethistoryoperate(cli, retcode, retmsg, lastpage, operatedataes, timestamps) {
	log.value += "历史运营数据: (" + operatedataes.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<operates.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayoperatedata(operates[i], timestamps[i]);
		} else {
			displayoperatedata(operates[i], null);
		}
	}
};

//读历史短信
function ongethistorysm(cli, retcode, retmsg, lastpage, sms, timestamps) {
	log.value += "历史短信: (" + sms.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<sms.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displaysm(sms[i], timestamps[i]);
		} else {
			displaysm(sms[i], null);
		}
	}
};

//读历史警情
function ongethistoryalarm(cli, retcode, retmsg, lastpage, alarms, timestamps) {
	log.value += "历史警情: (" + alarms.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<alarms.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayalarm(alarms[i], timestamps[i]);
		} else {
			displayalarm(alarms[i], null);
		}
	}
};

function ongethistoryunitloginout(cli, retcode, retmsg, lastpage, unitloginout, timestamps) {
	log.value += "历史登退录: (" + unitloginout.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<unitloginout.length; i++) {
		if (typeof(timestamps) != "undefined" && timestamps.length >= (i-1)) {
			displayunitloginout(unitloginout[i], timestamps[i]);
		} else {
			displayunitloginout(unitloginout[i], null);
		}
	}
};

//显示读历史信息错误
function ongethistoryerror(cli, retcode, retmsg) {
	log.value += "*****读历史信息失败:(retcode=" + retcode  + ", retmsg=" + retmsg + "*****\n";
};

//结束读历史信息
function onstophistory(cli, retcode, retmsg){
	log.value += "结束读历史信息结果: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
};

//车辆上传实时位置信息
function ondelivergps(cli, gpsinfo, gatewayid, gatewaytype, alarmid, alarmname) {
	log.value += nowstring() + "实时位置: caller=" + gpsinfo.callLetter + ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype;
	if (alarmid != null && alarmname != null) {
		log.value += ", 发生警情:" + alarmid + ", " + alarmname;
	}
	log.value += "\n";
	displaygpsinfo(gpsinfo, true);
};

//实时OBD
function ondeliverobd(selfclient, obdinfo, gatewayid, gatewaytype) {
	log.value += nowstring() + "实时OBD: caller=" + obdinfo.callLetter + ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype;
	displayobd(obdinfo);
};

//终端登退录事件
function ondeliverunitloginout(selfclient, callLetter, inorout, gatewayid, unitversion){
	log.value += nowstring() + "车台（终端）登退录事件: " + callLetter;
	log.value += inorout == 0 ? "退录" : "登录";
	log.value += ", gatewayid=" + gatewayid;
	log.value += ", unitversion=" + unitversion + "\n";
};

//终端上报警情，递送到手机APP
//车辆上传终端警情
function ondeliveralarm(cli, alarminfo, gatewayid, gatewaytype) {
	log.value += nowstring() + "递交警情: caller=" + alarminfo.callLetter + ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
	log.value += alarminfo.unittype + ", " + alarminfo.trigger +"\n";
	displayalarm(alarminfo);
};

//车辆上传终端故障
function ondeliverfault(cli, faultinfo, gatewayid, gatewaytype) {
	log.value += nowstring() + "递交故障: caller=" + faultinfo.callLetter + ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
	var faultTime = new Date(faultinfo.faultTime.toNumber());
	log.value += SEGUtil_WS.date_2_string(faultTime, "yyyy-MM-dd hh:mm:ss") + "\n";
	displayfault(faultinfo);
};

//海马终端上报警情，递送通知到手机APP
function ondeliverappnotice(cli, noticeinfo, gatewayid, gatewaytype) {
	log.value += nowstring() + "递交APP通知(海马): caller=" + noticeinfo.callLetter + ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
	log.value += noticeinfo.title + ", " + noticeinfo.content +"\n";
};

//车辆上传实时运营数据
function ondeliveroperatedata(cli, datainfo, gatewayid, gatewaytype){
	log.value += nowstring() + "递交运营数据: ";
	displayoperatedata(datainfo);
};

//上传实时短信（包括车辆和一般手机）
function ondeliversms(cli, smsinfo, gatewayid, gatewaytype){
	log.value += nowstring() + "递交短信: caller=" + smsinfo.callLetter + ", msg=" + smsinfo.msg;
	log.value += ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
};

//车辆上传实时行程
function ondelivertrival(cli, travelinfo, gatewayid, gatewaytype) {
	log.value += nowstring() + "递交行程: caller=" + travelinfo.callLetter + ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
	var startTime = new Date(travelinfo.startTime.toNumber());
	var endTime = new Date(travelinfo.endTime.toNumber());
	log.value += startTime.toString() + ", ";
    log.value += endTime.toString() + "\n";
    displaytravel(travelinfo);
};

//终端升级成功，上传软件版本号
function ondeliverunitversion(cli, unitVersion, gatewayid, gatewaytype) {
	log.value += nowstring() + "终端版本: caller=" + unitVersion.callLetter;
	log.value += ", version=" + unitVersion.version;
	log.value += ", 升级成功=" + unitVersion.result;
	log.value += ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
};

//终端升级成功，上传软件版本号
function ondeliverecuconfig(cli, ecuconfig, gatewayid, gatewaytype) {
	log.value += nowstring() + "ECU配置: caller=" + ecuconfig.callLetter;
	var updateTime = new Date(ecuconfig.updateTime.toNumber());
	log.value += ", updateTime=" + SEGUtil_WS.date_2_string(updateTime, "yyyy-MM-dd hh:mm:ss") + "\n";
	log.value += ", abs=" + ecuconfig.abs;
	log.value += ", esp=" + ecuconfig.esp;
	log.value += ", srs=" + ecuconfig.srs;
	log.value += ", ems=" + ecuconfig.ems;
	log.value += ", immo=" + ecuconfig.immo;
	log.value += ", peps=" + ecuconfig.peps + "\n";
	log.value += ", bcm=" + ecuconfig.bcm;
	log.value += ", tcu=" + ecuconfig.tcu;
	log.value += ", tpms=" + ecuconfig.tpms;
	log.value += ", apm=" + ecuconfig.apm;
	log.value += ", icm=" + ecuconfig.icm;
	log.value += ", eps=" + ecuconfig.eps + "\n";
	log.value += ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
};

/*******************************************************************************************************
 * 下面的和处警相关的事件
 */
//坐席设置忙闲，通信中心应答
function onsetalarmbusy(selfclient, retcode, retmsg, username, busy, handleseatname) {
	if (busy) {
		log.value += "处警置忙";
	} else {
		log.value += "处警置闲";
	}
	log.value += ": retcode=" + retcode  + ", retmsg=" + retmsg;
	if (handleseatname) {
		log.value += ", " + handleseatname;
	}
	log.value += "\n";
};

//通信中心分配警情
function onallotalarm(selfclient, seatname, callLetter, alarmsn, alarmTime, alarmid, alarmname, gpsinfo, alarmcount, append) {
	log.value += "通信中心分配警情: callletter=" + callLetter  + ", alarmsn=" + alarmsn + ", alarmname=" + alarmname + ", alarmcount=" + alarmcount + ", append=" + append + "\n";
	log.value += "警情时间：" + SEGUtil_WS.date_2_string(new Date(alarmTime.toNumber()), "yyyy-MM-dd hh:mm:ss") + "\n"; 
	log.value += SEGUtil_WS.date_2_string(new Date(gpsinfo.gpsTime.toNumber()), "yyyy-MM-dd hh:mm:ss") + ", " + gpsinfo.lng + ", " + gpsinfo.lat + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心应答挂警
function onpausealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "挂警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtcancelcaller.value = callLetter;
	txtcancelalarmsn.value = alarmsn;
};

//通信中心应答取消挂警
function oncancelpausealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "取消挂警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtcancelcaller.value = callLetter;
	txtcancelalarmsn.value = alarmsn;
};

//通信中心应答处警结果
function onhandlealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "处警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
};

//申请坐席列表结果
function onaskseatlistack(selfclient, retcode, retmsg, seats){
	log.value += "坐席列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + seats.length + "\n";
	for(var i=0; i<seats.length; i++) {
		log.value += seats[i].username + ", " + seats[i].seatid + ", " + seats[i].isidle +"\n";
	}
};

//服务器返回转警请求结果, 只有等到已经转到目的坐席才返回
function ontransferalarmack(selfclient, retcode, retmsg, srcusername, dstusername, callLetter, alarmsn){
	log.value += "转警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", srcseat=" + srcusername  + ", dstseat=" + dstusername;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//服务器向转警目的坐席分配转警
function onallottransferalarm(selfclient, username, callLetter, alarmsn, alarmTime, alarmid,
		alarmname, gpsinfo, alarmcount, srcusername, transfermsg) {
	log.value += "分配转警: callletter=" + callLetter  + ", alarmsn=" + alarmsn + ", alarmname=" + alarmname + ", alarmcount=" + alarmcount + "\n";
	log.value += "srcseat=" + srcusername  + ", transfermsg=" + transfermsg + "\n";
	var gpsTime = new Date(gpsinfo.gpsTime.toNumber());
	log.value += gpsTime.toString() + ", " + gpsinfo.lng + ", " + gpsinfo.lat + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心返回警情列表
function onaskalarmlistack(selfclient, retcode, retmsg, alarms, alarmscount) {
	log.value += "警情列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + alarmscount + "\n";
	if (alarms != null) {
		var alarmstatuslist = ["新警情", "正在分配", "已经分配", "申请挂起", "处理完"];
		for(var i=0; i<alarms.length; i++) {
			var alarmtime = new Date(alarms[i].alarmTime.toNumber());
			log.value += alarmtime.toString() + ", " + alarms[i].callLetter + ", " + alarms[i].alarmname + ", 状态=" + alarmstatuslist[alarms[i].status] + ", " + alarms[i].seatname + "\n";
		}
	}
};

//通信中心返回添加新警情结果
function onnewalarmack(selfclient, retcode, retmsg, slaver, callLetter, alarmsn) {
	log.value += "添加警情结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", 坐席=" + slaver + "\n";
	log.value += "呼号=" + callLetter + ", SN=" + alarmsn + "\n";
};

