/*!
 * 
 * Copyright 2014 chinagps, Inc. zhangxz
 * 
 */
"use strict";
/*
 * 
 */
//记录日志
//alert("websocketevent.js");
var log = document.getElementById("log");
//保存处理流程过程中的呼号和警情序列号
var txtalarmcaller = document.getElementById("alarmcaller");
var txtalarmsn = document.getElementById("alarmsn");

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
	log.value += "通信中心返回登录结果: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	//AddMonitor();
};

//添加监控列表结果事件
function onaddmonitor(cli, retcode, retmsg, callLetters) {
	log.value += "通信中心返回添加监控列表结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", all callletter:\n";
	for(var i=0; i<callLetters.length; i++) {
		log.value += callLetters[i] + "\n";
	}
};

//取消监控列表结果事件
function onremovemonitor(cli, retcode, retmsg, callLetters) {
	log.value += "通信中心返回取消监控列表结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", remain callletter:\n";
	for(var i=0; i<callLetters.length; i++) {
		log.value += callLetters[i] + "\n";
	}
};

//命令发送成功回应
function onsendcommandsend(cli, sn, callLetter, cmdId, retcode, retmsg){
	log.value += "网关发送指令成功: " + callLetter + ", Id=" + cmdId + ", sn=" + sn;
	log.value += ", retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
}

//命令结果事件
function onsendcommand(cli, sn, callLetter, cmdId, retcode, retmsg, params, gpsInfo){
	log.value += "通信中心返回下发指令结果: " + callLetter + ", Id=" + cmdId + ", sn=" + sn;
	log.value += ", retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
}

//读最后位置信息
function ongetlastgps(cli, retcode, retmsg, gpses) {
	log.value += "通信中心返回最后位置: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<gpses.length; i++) {
		log.value += gpses[i].callLetter + "\n";
		var gpsTime = new Date(gpses[i].baseInfo.gpsTime.toNumber());
		log.value += gpsTime.toString() + ", " + gpses[i].baseInfo.lng + ", " + gpses[i].baseInfo.lat +"\n";
		if (gpses[i].baseInfo.obdInfo)
			log.value += gpses[i].baseInfo.obdInfo.totalDistance + ", " + gpses[i].baseInfo.obdInfo.remainOil + ", " + gpses[i].baseInfo.obdInfo.remainPercentOil +"%\n";
		if (gpses[i].referPosition != null) {
			log.value += gpses[i].referPosition.province + ", " + gpses[i].referPosition.city + ", " + gpses[i].referPosition.county + "\n";
		}
	}
}

//显示读最后信息错误
function ongetlasterror(cli, retcode, retmsg) {
	log.value += "*****通信中心返回取最后信息失败:(retcode=" + retcode  + ", retmsg=" + retmsg + "*****\n";
};

//读历史位置信息
function ongethistorygps(cli, retcode, retmsg, lastpage, gpses) {
	log.value += "通信中心返回历史轨迹:(" + gpses.length + ") retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<gpses.length; i++) {
		log.value += gpses[i].callLetter + "\n";
		var gpsTime = new Date(gpses[i].baseInfo.gpsTime.toNumber());
		log.value += gpsTime.toString() + ", " + gpses[i].baseInfo.lng + ", " + gpses[i].baseInfo.lat + "\n";
		if (gpses[i].baseInfo.obdInfo)
			log.value += gpses[i].baseInfo.obdInfo.totalDistance + ", " + gpses[i].baseInfo.obdInfo.remainOil + ", " + gpses[i].baseInfo.obdInfo.remainPercentOil +"%\n\n";
		if (gpses[i].referPosition != null) {
			log.value += gpses[i].referPosition.province + ", " + gpses[i].referPosition.city + ", " + gpses[i].referPosition.county + "\n";
		};
	};
}

//读历史位置主要信息
function ongethistorysimplegps(cli, callLetter, retcode, retmsg, lastpage, gpses) {
	log.value += "通信中心返回历史轨迹主要信息:(" + gpses.length + ") callletter=" + callLetter + ", retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<gpses.length; i++) {
		var gpsTime = new Date(gpses[i].gpsTime.toNumber());
		log.value += gpsTime.toString() + ", " + gpses[i].lng + ", " + gpses[i].lat + "\n";
	};
}
					
//显示读历史信息错误
function ongethistoryerror(cli, retcode, retmsg) {
	log.value += "*****通信中心返回读历史信息失败:(retcode=" + retcode  + ", retmsg=" + retmsg + "*****\n";
};

//结束读历史信息
function onstophistory(cli, retcode, retmsg){
	log.value += "通信中心返回结束读历史信息: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
};

//车辆上传实时位置信息
function ondelivergps(cli, gpsinfo, gatewayid, gatewaytype, alarmid, alarmname) {
	log.value += "通信中心推送实时位置: caller=" + gpsinfo.callLetter + ", gatewayid=" + gatewayid + ", gatewaytype=" + gatewaytype + "\n";
	log.value += gpsinfo.baseInfo.gpsTime + ", " + gpsinfo.baseInfo.lng + ", " + gpsinfo.baseInfo.lat +"\n";
	if (gpsinfo.baseInfo.obdInfo)
		log.value += gpsinfo.baseInfo.obdInfo.totalDistance + ", " + gpsinfo.baseInfo.obdInfo.remainOil + ", " + gpsinfo.baseInfo.obdInfo.remainPercentOil +"%\n";
	if (gpsinfo.referPosition != null) {
		log.value += gpsinfo.referPosition.province + ", " + gpsinfo.referPosition.city + ", " + gpsinfo.referPosition.county + "\n";
	}
}

//终端登退录事件
function ondeliverunitloginout(selfclient, callLetter, inorout, gatewayid){
	log.value += "车台（终端）登退录事件: " + callLetter;
	log.value += inorout == 0 ? "退录" : "登录";
	log.value += ", gatewayid=" + gatewayid + "\n";
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
	var gpsTime = new Date(gpsinfo.gpsTime.toNumber());
	log.value += gpsTime.toString() + ", " + gpsinfo.lng + ", " + gpsinfo.lat + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心应答挂警
function onpausealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "通信中心返回挂警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += "caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心应答处警结果
function onhandlealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "通信中心返回处警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//申请坐席列表结果
function onaskseatlistack(selfclient, retcode, retmsg, seats){
	log.value += "通信中心返回坐席列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + seats.length + "\n";
	for(var i=0; i<seats.length; i++) {
		log.value += seats[i].username + ", " + seats[i].seatid + ", " + seats[i].isidle +"\n";
	};
};

//服务器返回转警请求结果, 只有等到已经转到目的坐席才返回
function ontransferalarmack(selfclient, retcode, retmsg, srcusername, dstusername, callLetter, alarmsn){
	log.value += "通信中心返回转警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", srcseat=" + srcusername  + ", dstseat=" + dstusername;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//服务器向转警目的坐席分配转警
function onallottransferalarm(selfclient, username, callLetter, alarmsn, alarmTime, alarmid,
		alarmname, gpsinfo, alarmcount, srcusername, transfermsg) {
	log.value += "通信中心分配转警: callletter=" + callLetter  + ", alarmsn=" + alarmsn + ", alarmname=" + alarmname + ", alarmcount=" + alarmcount + "\n";
	log.value += "srcseat=" + srcusername  + ", transfermsg=" + transfermsg + "\n";
	var gpsTime = new Date(gpsinfo.gpsTime.toNumber());
	log.value += gpsTime.toString() + ", " + gpsinfo.lng + ", " + gpsinfo.lat + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心返回警情列表
function onaskalarmlistack(selfclient, retcode, retmsg, alarms) {
	log.value += "通信中心返回警情列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + alarms.length + "\n";
	var alarmstatuslist = ["新警情", "正在分配", "已经分配", "申请挂起", "处理完"];
	for(var i=0; i<alarms.length; i++) {
		var alarmtime = new Date(alarms[i].alarmTime.toNumber());
		log.value += alarmtime.toString() + ", " + alarms[i].callLetter + ", " + alarms[i].alarmname + ", 状态=" + alarmstatuslist[alarms[i].status] +"\n";
	};
};

//通信中心返回添加新警情结果
function onnewalarmack(selfclient, retcode, retmsg, slaver, callLetter, alarmsn) {
	log.value += "通信中心返回添加警情结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", 坐席=" + slaver + "\n";
	log.value += "呼号=" + callLetter + ", SN=" + alarmsn + "\n";
};

