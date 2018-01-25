/*
 * 测试大平台通信中心协议
 * Copyright 2014 chinagps, Inc.
 */
//通信客户端
"use strict";

var client = null;
var txtcallletters = document.getElementById("callletter");
var txtseatname = document.getElementById("seatname");
var txtseatid = document.getElementById("seatid");
var txtseatpwd = document.getElementById("seatpwd");
var txtsn = document.getElementById("sn");
var txtstarttime = document.getElementById("starttime");
var txtstoptime = document.getElementById("stoptime");

var txtalarmcaller = document.getElementById("alarmcaller");
var txtalarmsn = document.getElementById("alarmsn");
var txttransferseatname = document.getElementById("transferseatname");

var txtalarmstatusid = document.getElementById("alarmstatusid");
var txtalarmlevel = document.getElementById("alarmlevel");

var historytype = null;		//读历史记录时（下一页要用到上次是读那种信息）

function onbodyload(){
	txtstarttime.value = SEGUtil.date_2_string(new Date, "yyyy-MM-dd");
	txtstoptime.value = SEGUtil.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
	txtsn.value = Math.uuid();
};

/********************************************************************************************
 * 登录通信中心
 */
function Connect() {
	try {
		//多个通信中心服务器，用分号隔开
		var host = location.host;
		if (client == null) {
			//segseatlib中同时可以有多个客户端
			/* 1: 每个客户端有不同ID, 客户端自定义，事件中可区分
			 * 
			 * 登录用户名(坐席操作员名称)、密码、坐席编号
			 */
			host = "202.105.139.92:8070";
			client = new segseatlib.jsclient(1, host, txtseatname.value, txtseatpwd.value, txtseatid.value);
			segseatlib.addClient(client);
		}
		//废黜通信过程中压缩报文，默认不压缩
		client.setCompress(true);
		//废黜通信过程中加密报文，默认不加密
		client.setEncrypt();
		//连接服务器, 连接成功后自动登录
		client.connect();
	} catch (err) {
		alert(err);
	}
};

/********************************************************************************************
 * 添加和取消监控列表
 */
function AddMonitor() {
	try {
		if (client == null) {
			return;
		}
		//ALLUNIT表示全部终端，请参考《大平台通信中心通信协议》
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333", "ALLUNIT"];
		var callletters = txtcallletters.value.split(";");
		var infotype = [-1];
		//var infotype = txtinfotypes.value.split(";");
		client.addMonitor(callletters, infotype);
	} catch (err) {
		alert(err);
	}
};

function RemoveMonitor() {
	try {
		if (client == null) {
			return;
		}
		//ALLUNIT表示全部终端，请参考《大平台通信中心通信协议》
		//var callletters = ["1393333333", "ALLUNIT"];
		var callletters = txtcallletters.value.split(";");
		var infotype = [-1];
		//var infotype = txtinfotypes.value.split(";");
		client.removeMonitor(callletters, infotype);
	} catch (err) {
		alert(err);
	}
};

/********************************************************************************************
 * 最后位置
 */
function GetLastPosition() {
	try {
		if (client == null) {
			return;
		}
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333", "13612345678", "13112345678", "13212345678", "13312345678", "13412345678"];
		var callletters = txtcallletters.value.split(";");
		txtsn.value = Math.uuid();
		client.getLastInfo(segseatlib.message_type.DeliverGPS, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
};
function GetLastOBD() {
	try {
		if (client == null) {
			return;
		}
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333", "13612345678", "13112345678", "13212345678", "13312345678", "13412345678"];
		var callletters = txtcallletters.value.split(";");
		txtsn.value = Math.uuid();
		client.getLastInfo(segseatlib.message_type.DeliverOBD, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
};

/********************************************************************************************
 * 下面取历史轨迹
 */
function GetHistoryGPS() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil.string_2_date(txtstoptime.value);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverGPS;
		  txtsn.value = Math.uuid();
		  client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 300, 5000, false, txtsn.value);
		}
	} catch (err) {
		alert(err);
	}
};

function GetHistorySimpleGps() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil.string_2_date(txtstoptime.value);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverSimpleGPS;
 		  txtsn.value = Math.uuid();
		  client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 300, 5000, false, txtsn.value);
		}
	} catch (err) {
		alert(err);
	}
};

function GetHistoryNextPage() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
			//txtsn的值不能变
    		client.getHistoryNextPage(callletters[0], historytype, txtsn.value);
		}
	} catch (err) {
		alert(err);
	}
};

function StopHistory() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
			//txtsn的值不能变
    		client.stopHistory(callletters[0], historytype, txtsn.value);
		}
	} catch (err) {
		alert(err);
	}
};

/********************************************************************************************
 * 下面是指令
 */
function Position(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0001" + new Date().getTime();
		txtsn.value = "0x0001-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书, 查车，默认添加到
		client.sendUnitCommand(txtsn.value, callletters, 0x0001, null, true);
	} catch (err) {
		alert(err);
	}
};

function FindCar(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0013" + new Date().getTime();
		txtsn.value = "0x0013-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0013);
	} catch (err) {
		alert(err);
	}
};

function OpenDoor(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0005" + new Date().getTime();
		txtsn.value = "0x0005-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0005);
	} catch (err) {
		alert(err);
	}
};

function LockDoor(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0004-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0004);
	} catch (err) {
		alert(err);
	}
};

function LockOil(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0006" + new Date().getTime();
		txtsn.value = "0x0006-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0006);
	} catch (err) {
		alert(err);
	}
};

function OpenOil(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0007" + new Date().getTime();
		txtsn.value = "0x0007-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0007);
	} catch (err) {
		alert(err);
	}
};

function Trace(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0015" + new Date().getTime();
		txtsn.value = "0x0015-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["100", "30"];		//100次， 30秒一次
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0015, params);
	} catch (err) {
		alert(err);
	}
};

function IntervalDeliver(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0038" + new Date().getTime();
		txtsn.value = "0x0038-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["30"];		//30秒一次
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0038, params);
	} catch (err) {
		alert(err);
	}
};

function Listen(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0017" + new Date().getTime();
		txtsn.value = "0x0017-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["26719595"];		//监听电话
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0017, params);
	} catch (err) {
		alert(err);
	}
};

function UnitSMS(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0035" + new Date().getTime();
		txtsn.value = "0x0035-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["赛格导航欢迎您！联系电话 - 075526719888, 952100"];		//发送到终端的短信
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0035, params);
	} catch (err) {
		alert(err);
	}
};

function SendSMS() {
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x4035" + new Date().getTime();
		txtsn.value = "0x4035-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333"];
		var params = ["赛格导航欢迎您！联系电话 - 075526719888, 952100"];		//发送到手机的短信
		client.sendUnitCommand(txtsn.value, callletters, 0x4035, params);
	} catch (err) {
		alert(err);
	}
};

/********************************************************************************************
 * 下面是和警情相关
 */
//处警置忙，不接受新警情（除非特殊呼号）
function SeatBusy() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		var callletter = null;
		if (callletters.length > 0) {
			callletter = callletters[0];
		}
		client.setAlarmBusy(txtseatname.value, txtseatid.value, true, callletter);
	} catch (err) {
		alert(err);
	}
};

//处警置闲，接受新警情
function SeatIdle() {
	try {
		if (client == null) {
			return;
		}
		client.setAlarmBusy(txtseatname.value, txtseatid.value, false);
	} catch (err) {
		alert(err);
	}
};

//接受新的警情，预处理
function AcceptAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.acceptAlarmACK(true, txtseatname.value, txtalarmcaller.value, txtalarmsn.value);
	} catch (err) {
		alert(err);
	}
};

//拒绝新的警情，不处理
function RefuseAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.acceptAlarmACK(false, txtseatname.value, txtalarmcaller.value, txtalarmsn.value);
	} catch (err) {
		alert(err);
	}
};

//挂警
function PauseAlarm() {
	try {
		if (client == null) {
			return;
		}
		//默认继续接受警情
		client.pauseAlarm(txtseatname.value, txtalarmcaller.value, txtalarmsn.value, "等待客户回复电话");
	} catch (err) {
		alert(err);
	}
};

//结束处警
function HandleAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.HandleAlarm(txtseatname.value, txtalarmcaller.value, txtalarmsn.value,
				segseatlib.resultcode.OK, "处警结束");
	} catch (err) {
		alert(err);
	}
};

//请求坐席列表
function AskSeatList() {
	try {
		if (client == null) {
			return;
		}
		client.AskSeatList(txtseatname.value, txtalarmcaller.value);
	} catch (err) {
		alert(err);
	}
};

//请求未处理完的警情列表
function AskAlarmList() {
	try {
		if (client == null) {
			return;
		}
		client.AskAlarmList(txtseatname.value);	//只能自己处理的警情（班长或总监才能取）
	} catch (err) {
		alert(err);
	}
};

//转警
function TransferAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.TransferAlarm(txtseatname.value, txttransferseatname.value, 
				txtalarmcaller.value, txtalarmsn.value, "我不清楚怎么处理这种情况，请协助！");
	} catch (err) {
		alert(err);
	}
};

//接受转警
function AcceptTransferAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.AllotTransferAlarmACK(true, txtseatname.value, txtalarmcaller.value,
				txtalarmsn.value, txttransferseatname.value);
	} catch (err) {
		alert(err);
	}
};

//拒绝转警
function RefuseTransferAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.AllotTransferAlarmACK(false, txtseatname.value, txtalarmcaller.value,
				txtalarmsn.value, txttransferseatname.value);
	} catch (err) {
		alert(err);
	}
};

//添加测试警情
function NewAlarm() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		txtsn.value = Math.uuid();
		client.NewAlarm(txtseatname.value, callletters[0], txtsn.value, new Date().getTime(), txtalarmstatusid.value, txtalarmlevel.value,
				new Date().getTime(), true, 112000000 + Math.random() * 100000, 22000000 + Math.random() * 100000,
				10 + Math.random() * 20, 1, "1,2,5,6,201");
	} catch (err) {
		alert(err);
	}
};

