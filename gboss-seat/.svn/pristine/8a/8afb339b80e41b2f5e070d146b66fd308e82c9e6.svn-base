/*
 * 测试大平台通信中心协议
 * Copyright 2014 chinagps, Inc.
 */
//通信客户端
"use strict";
var client = null;
var txtcallletters = document.getElementById("b_carPhone");
var txtcalllettersAdd = document.getElementById("addMonitor_carPhone");
var txtcalllettersMo = document.getElementById("Monitor_carPhone");
var txtseatname = document.getElementById("seatname");
var txtseatid = document.getElementById("seatid");
var txtseatpwd = document.getElementById("seatpwd");
var txtsn = document.getElementById("sn");
var txtstarttime = document.getElementById("starttime");
var txtstoptime = document.getElementById("stoptime");

var txtalarmcaller = document.getElementById("alarmcaller");//警情呼号
var txtalarmvehicleid = document.getElementById("alarmvehicleid");//警情车辆id
var txtalarmsn = document.getElementById("alarmsn");//警情序列号
var txtalarmid = document.getElementById("alarmid");//警情id
var txtalarmname = document.getElementById("alarmname");//警情名
var txtalarmtime = document.getElementById("alarmtime");//报警时间
var txttransferseatname = document.getElementById("transferseatname");//转警原座席
var txtalarmstatusid = document.getElementById("alarmstatusid");//测试警情的
var txtalarmlevel = document.getElementById("alarmlevel");//测试警情的
var historytype = null;		//读历史记录时（下一页要用到上次是读那种信息）
var monitor_lenth = 10;
var SentList_lenth = 10;

var audio = document.getElementById('alarmMedia');//警情音效


function onbodyload(){
	txtstarttime.value = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd");
	txtstoptime.value = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
	txtsn.value = Math.uuid();
};

/********************************************************************************************
 * 登录通信中心
 */
var host = location.host;//通信中心ip:端口，多个通信中心服务器，用分号隔开
function Connect() {
	$("#Communication_Center_mark").attr("title","正在连接通信中心...");//添加提示
	try {
		//多个通信中心服务器，用分号隔开
		//var host = location.host;
		if (client == null) {
			//segseatlib中同时可以有多个客户端
			/* 1: 每个客户端有不同ID, 客户端自定义，事件中可区分
			 * 
			 * 登录用户名(坐席操作员名称)、密码、坐席编号
			 */
			//host已通过接口配置，目前默认配置为测试警情接口：90.0.12.203:18090,连接其他地址需在下面重置
			// host = "90.0.12.203:8076";//湖南测试警情
			// host = "202.105.139.92:8070";//指令测试
			// host = "90.0.12.68:18070"; //本地测试正式
			// host = "90.0.12.68:18070"; //多个
			// host = "192.110.10.164:8070;192.110.10.163:8070;192.110.10.162:8070;192.110.10.161:8070";//龙岗连接地址
			// console.log(defaultParam.communicationsCenter);
			host = defaultParam.communicationsCenter;//龙岗连接地址
			client = new segseatlib.jsclient(1, host, txtseatname.value, txtseatpwd.value, txtseatid.value, "seatclient", "1.0");
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
 * 退出通信中心
 */
function LogoutCC() {
	try {
		if (client == null) {
			return;
		}
		//退出
		client.close();
	} catch (err) {
		alert(err);
	}
};

//重连
function Reconnect() {
	try {
		if (client == null) {
			return;
		}
		client.reconnect();
		AddMonitor();
	} catch (err) {
		alert(err);
	}
}

function CloseConnect() {
	try {
		if (client == null) {
			return;
		}
		client.closeconnect();
	} catch (err) {
		alert(err);
	}
}

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
		// beenSent('居中当前车辆的最后位置');
	} catch (err) {
		alert(err);
	}
};

//添加和取消监控列表

function AddMonitor() {
	try {
		if (client == null) {
			return;
		}
		//ALLUNIT表示全部终端，请参考《大平台通信中心通信协议》
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333", "ALLUNIT"];
		var callletters = txtcalllettersAdd.value.split(";");
		var infotype = [-1];
		// alert(callletters);
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
		var callletters = txtcalllettersMo.value.split(";");
		var infotype = [-1];
		// alert(callletters);
		//var infotype = txtinfotypes.value.split(";");
		client.removeMonitor(callletters, infotype);
	} catch (err) {
		alert(err);
	}
};


//添加到已发送列表--------------------------------------------
function beenSent(orderName){
	//添加到“已发送指令”
	var carPhone = $('#b_carPhone').val();						    //车载号码
	var gsm = $('#b_gsm').val(); 									//车牌号
	var cmdtime = SEGUtil_WS.date_2_string(new Date, "hh:mm:ss");      //指令发送时间

	//该车没有发送过指令，则新建一个车牌
	if($("#cmd_"+carPhone).length == 0){ 
		if ($("#send_list>li").length>=SentList_lenth){
			$("#send_list>li:last").remove();
		};
		html =  '<li id="cmd_'+carPhone+'">';
		html += '<a href="#" role="branch" class="tree-toggle" data-toggle="branch" data-value="Bootstrap_Tree">'+gsm+'</a>';
		html += '<ul class="branch in">';
		html += '</ul>';
		html += '</li>';
		$("#profile1 .tree").prepend(html);
	}else{
		$("#cmd_"+carPhone).prependTo("#send_list");
	}
	
	html = '<li id="'+txtsn.value+'"><a href="#" role="leaf"><span class="wait">'+cmdtime+'</span><em>'+orderName+'</em></a></li>'; 
	$("#cmd_"+carPhone+">ul").append(html);
    $("#mesg_sentitmbo").tab("show");
}



//指令部分

// 锁车门------------------------------------------------------

function LockDoor(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0004-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0004);

		beenSent('锁车门')


	} catch (err) {
		alert(err);
	}
};

//开车门-----------------------------------------

function OpenDoor(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0005-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0005);
		beenSent('开车门')
	} catch (err) {
		alert(err);
	}
};

//恢复供电（油）---------------------------------------------------------

function OpenOil(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0007" + new Date().getTime();
		txtsn.value = "0x0007-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0007);

		beenSent('恢复供电（油）')

	} catch (err) {
		alert(err);
	}
};

//断油电---------------------------------------------
function LockOil(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0006" + new Date().getTime();
		txtsn.value = "0x0006-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0006);
		beenSent('断电（油）')
	} catch (err) {
		alert(err);
	}
};

//监听---------------------------------------------

function Listen(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0017" + new Date().getTime();
		txtsn.value = "0x0017-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = ["26719995"];		//监听电话
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0017, params);
		beenSent('监听')

	} catch (err) {
		alert(err);
	}
};


//寻车---------------------------------------------
function FindCar(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0013" + new Date().getTime();
		txtsn.value = "0x0013-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0013);
		beenSent('寻车')
	} catch (err) {
		alert(err);
	}
};

//查车---------------------------------------------

function Position(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0001" + new Date().getTime();
		txtsn.value = "0x0001-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书, 查车，默认添加到
		client.sendUnitCommand(txtsn.value, callletters, 0x0001, null, true);
		beenSent('查车');
	} catch (err) {
		alert(err);
	}
};

//跟踪---------------------------------------------
function Trace(timeSS, timeFre){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0015-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = [timeFre,timeSS];		//100次， 30秒一次
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0015, params);
		beenSent('跟踪');
	} catch (err) {
		alert(err);
	}
};

//停止跟踪---------------------------------------------
function StopTrace(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0016-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0016);
		beenSent('停止跟踪');
	} catch (err) {
		alert(err);
	}
};

//定时报告---------------------------------------------
function IntervalDeliver(flag, t){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var code = null;
		var name = '';
		if(flag){
			var params = [t]; //t秒一次
			code = 0x0038;
			name = '开启定时报告';
			txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
			client.sendUnitCommand(txtsn.value, callletters, code, params);
		}else{
			code = 0x0039;
			name = '停止定时报告';
			txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
			client.sendUnitCommand(txtsn.value, callletters, code);
		}
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};

//设置黑匣子记录时间间隔---------------------------------------------
function BlackboxSet(t){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var code = null;
		var name = '';
		var params = [t]; //t秒一次
		code = 0x001C;
		name = '设置黑匣子记录时间间隔';
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		client.sendUnitCommand(txtsn.value, callletters, code, params);
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};

//通话---------------------------------------------------------
function telCall(Phone_num){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0018-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = [Phone_num];		//电话号码
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0018);

		beenSent('通话')

	} catch (err) {
		alert(err);
	}
};

//发送到终端的短信---------------------------------------------------------
function UnitSMS(content){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0035" + new Date().getTime();
		txtsn.value = "0x0035-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = [content,'0'];		//发送到终端的短信
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0035, params);
		beenSent('发短信')
	} catch (err) {
		alert(err);
	}
};

//发送到手机的短信---------------------------------------------------------
function SendSMS(content) {
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x4035" + new Date().getTime();
		txtsn.value = "0x4035-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333"];
		var params = [content,'0'];		//发送到手机的短信
		client.sendUnitCommand(txtsn.value, callletters, 0x4035, params);
		beenSent('发短信')
	} catch (err) {
		alert(err);
	}
};

//唤醒GPS---------------------------------------------------------
function wakeupGPS() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x005A-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		client.sendUnitCommand(txtsn.value, callletters, 0x005A);
		beenSent('唤醒GPS')
	} catch (err) {
		alert(err);
	}
};

//开后尾箱-----------------------------------------
function OpenTrunk(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0063-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0063);
		beenSent('开后尾箱')
	} catch (err) {
		alert(err);
	}
};


//升车窗-----------------------------------------
function CloseWindow(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x000E-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x000E);
		beenSent('升车窗')
	} catch (err) {
		alert(err);
	}
};

//降车窗-----------------------------------------
function OpenWindow(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x000D-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x000D);
		beenSent('降车窗')
	} catch (err) {
		alert(err);
	}
};

//开空调-----------------------------------------
function OpenAircondition(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0065-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0065);
		beenSent('开空调')
	} catch (err) {
		alert(err);
	}
};

//关空调-----------------------------------------
function CloseAircondition(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0066-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0066);
		beenSent('关空调')
	} catch (err) {
		alert(err);
	}
};

//开灯-----------------------------------------
function lighten(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0061-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0061);
		beenSent('开灯')
	} catch (err) {
		alert(err);
	}
};

//关灯-----------------------------------------
function putout(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0062-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0062);
		beenSent('关灯')
	} catch (err) {
		alert(err);
	}
};

//允许手柄设置---------------------------------------------------------
function AllowSet() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0009-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		client.sendUnitCommand(txtsn.value, callletters, 0x0009);
		beenSent('允许手柄设置')
	} catch (err) {
		alert(err);
	}
};

//回传黑匣子记录---------------------------------------------
function startBlackbox(timeS, timeE){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0012-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = [timeS,timeE];	
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0012, params);
		beenSent('回传黑匣子记录');
	} catch (err) {
		alert(err);
	}
};

//停止回传黑匣子记录---------------------------------------------
function stopBlackbox(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0014-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0014);
		beenSent('停止回传黑匣子记录');
	} catch (err) {
		alert(err);
	}
};



//限制车辆行驶速度---------------------------------------------
function RatelimitSet(flag, speed){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var code = null;
		var name = '';
		if(flag){
			var params = [speed]; //公里/小时
			code = 0x001D;
			name = '限制车辆行驶速度';
			txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
			client.sendUnitCommand(txtsn.value, callletters, code, params);
		}else{
			code = 0x001E;
			name = '解除车辆限速';
			txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
			client.sendUnitCommand(txtsn.value, callletters, code);
		}
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};

//禁止行驶时间---------------------------------------------
function TraveltimeBanOn(timeS, timeE){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var params = [timeS, timeE];
		var code = 0x0021;
		var name = '禁止行使时间';
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		client.sendUnitCommand(txtsn.value, callletters, code, params);
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};

//解除禁止行驶时间---------------------------------------------
function TraveltimeBanOff(timeS, timeE){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var params = ['', timeS, timeE];
		var code = 0x0022;
		var name = '解除禁止行使时间';
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		client.sendUnitCommand(txtsn.value, callletters, code,params);
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};

//更改服务电话号码---------------------------------------------
function Change1(number){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0025-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = [number];	
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0025, params);
		beenSent('更改服务电话号码');
	} catch (err) {
		alert(err);
	}
};
//更改短信服务中心特服号码---------------------------------------------
function Change2(number){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x002F-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = [number,1];	
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x002F, params);
		beenSent('更改短信服务中心特服号码');
	} catch (err) {
		alert(err);
	}
};
//更改短信服务中心呼出特服号码---------------------------------------------
function Change3(number){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x002F-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		var params = [number,2];	
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x002F, params);
		beenSent('更改短信服务中心呼出特服号码');
	} catch (err) {
		alert(err);
	}
};

//开启/关闭点火报告---------------------------------------------
function EngineReport(flag){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		var code = null;
		var name = '';
		if(flag){
			code = 0x0042;
			name = '开启点火报告';
		}else{
			code = 0x0043;
			name = '关闭点火报告';
		}
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		client.sendUnitCommand(txtsn.value, callletters, code);
		beenSent(name);
	} catch (err) {
		alert(err);
	}
};


// 车台复位------------------------------------------------------

function Reback(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0002-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0002);

		beenSent('车台复位')


	} catch (err) {
		alert(err);
	}
};


//设置通信参数---------------------------------------------
function GPRS_parameter(S_APN, S_IP, S_NUM01,S_NUM02,S_type,S_mode,S_space){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var params = [S_APN, S_IP,S_NUM01,S_NUM02,S_type,S_mode,S_space];
		var code = 0x0057;
		var name = '设置通信参数';
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		client.sendUnitCommand(txtsn.value,callletters,code,params);
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};


// 查询通信参数------------------------------------------------------

function GPRS_inquiry(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0058-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0058);
		beenSent('查询通信参数');
	} catch (err) {
		alert(err);
	}
};



//设置导航目的地---------------------------------------------
function GPRS_destination(Des_search,Des_lon,Des_lat,Des_pass,Des_avoid){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		// var params = [Des_search,Des_lon,Des_lat];
		var params = [Des_search,Des_lon,Des_lat,Des_pass,Des_avoid];
        // if(Des_pass){
        //     params.push(Des_pass);
        // }
        // if(Des_avoid){
        //     params.push(Des_avoid);
        // }
		var code = 0x0292;
		var name = '设置导航目的地';
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		// console.log(params);
 		// alert("txtsn.value="+txtsn.value+"  callletters="+callletters+" params="+params);
       
		client.sendUnitCommand(txtsn.value,callletters,code,params);
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};


/********************************************************************************************
 * 下面取历史轨迹
 */
function GetHistoryGPS(starttime, stoptime) {
	try {
		if (client == null) {
			return;
		}
		//var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		//var endtime = SEGUtil_WS.string_2_date(txtstoptime.value);
		var starttime = SEGUtil_WS.string_2_date(starttime); 
		var endtime = SEGUtil_WS.string_2_date(stoptime);
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

//读历史位置主要信息
function GetHistorySimpleGps(starttime, stoptime) {
	try {
		if (client == null) {
			return;
		}
		//var starttime = SEGUtil.string_2_date(txtstarttime.value); 
		//var endtime = SEGUtil.string_2_date(txtstoptime.value);
		var starttime = SEGUtil_WS.string_2_date(starttime); 
		var endtime = SEGUtil_WS.string_2_date(stoptime);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverSimpleGPS;
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

// function StopHistory() {
// 	try {
// 		if (client == null) {
// 			return;
// 		}
// 		var callletters = txtcallletters.value.split(";");
// 		if (callletters.length > 0) {
// 			//txtsn的值不能变
//     		client.stopHistory(callletters[0], historytype, txtsn.value);
// 		}
// 	} catch (err) {
// 		alert(err);
// 	}
// };
 
// 海马部分新增指令

// 开启车身状态变化报告
function openReport(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0048-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0048);
		beenSent('开启车身状态变化报告')
	} catch (err) {
		alert(err);
	}
};

// 关闭车身状态变化报告
function closeReport(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0049-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0049);
		beenSent('关闭车身状态变化报告')
	} catch (err) {
		alert(err);
	}
};

// 开启关机报告
function openShutReport(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x004A-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x004A);
		beenSent('开启关机报告')
	} catch (err) {
		alert(err);
	}
};

// 关闭关机报告
function closeShutReport(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x004B-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x004B);
		beenSent('关闭关机报告')
	} catch (err) {
		alert(err);
	}
};

// 开启休眠报告
function openSleepReport(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x004C-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x004C);
		beenSent('开启休眠报告')
	} catch (err) {
		alert(err);
	}
};

// 关闭休眠报告
function closeSleepReport(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x004D-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x004D);
		beenSent('关闭休眠报告')
	} catch (err) {
		alert(err);
	}
};

// 查询呼叫中心
function checkCallCenter(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0027-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0027);
		beenSent('查询呼叫中心')
	} catch (err) {
		alert(err);
	}
};

// 查询短信业务中心号码
function checkMessageCenter(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0030-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0030);
		beenSent('查询短信业务中心号码')
	} catch (err) {
		alert(err);
	}
};

// 查询定时上报参数
function checkDeliverParams(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0070-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0070);
		beenSent('查询定时上报参数')
	} catch (err) {
		alert(err);
	}
};

// 查询ACC变化上报参数
function checkACCParams(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0071-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0071);
		beenSent('查询ACC变化上报参数')
	} catch (err) {
		alert(err);
	}
};

// 查询休眠上报参数
function checkSleepParams(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0072-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0072);
		beenSent('查询休眠上报参数')
	} catch (err) {
		alert(err);
	}
};

// 查询关机上报参数
function checkShutParams(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0073-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0073);
		beenSent('查询关机上报参数')
	} catch (err) {
		alert(err);
	}
};

// 查询车身状态变化上报参数
function checkStatusParams(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x0074-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0074);
		beenSent('查询车身状态变化上报参数')
	} catch (err) {
		alert(err);
	}
};

// 查询OBD故障
function checkOBDParams(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x00A3-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x00A3);
		beenSent('查询OBD故障')
	} catch (err) {
		alert(err);
	}
};

// 查询呼入呼出限制
function checkLimitParams(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x00A4-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x00A4);
		beenSent('查询呼入呼出限制')
	} catch (err) {
		alert(err);
	}
};

// 查询故障/救援服务号码
function checkEmergency(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x00A7-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x00A7);
		beenSent('查询救援服务号码')
	} catch (err) {
		alert(err);
	}
};

// 开启故障上报
function openEmergency(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x00AA-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x00AA);
		beenSent('开启故障上报')
	} catch (err) {
		alert(err);
	}
};

// 关闭故障上报
function closeEmergency(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x00AB-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x00AB);
		beenSent('关闭故障上报')
	} catch (err) {
		alert(err);
	}
};

// 查询故障是否上报
function CheckHitch(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x00AC-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x00AC);
		beenSent('查询故障是否上报')
	} catch (err) {
		alert(err);
	}
};

// 查询升级状态
function CheckUpdateStatus(){
	try {
		if (client == null) {
			return;
		}
		//var sn = "0x0004" + new Date().getTime();
		txtsn.value = "0x00B2-" + Math.uuid(16)+new Date().getTime();
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x00B2);
		beenSent('查询升级状态')
	} catch (err) {
		alert(err);
	}
};

//通知终端升级
function informUpdate(U_ip,U_port,U_files){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var params = [U_ip,U_port,U_files];
		var code = 0x00A5;
		var name = '通知终端升级';
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		client.sendUnitCommand(txtsn.value,callletters,code,params);
		beenSent(name)
	} catch (err) {
		alert(err);
	}
};


// 设置故障/救援服务号码
function emergencyNumber(S_number){
	try {
		if (client == null) {
			return;
		}		
		var callletters = txtcallletters.value.split(";");
		var params = [S_number];
		var code = 0x00A6;
		var name = '设置救援服务号码';
		txtsn.value = code + "-" + Math.uuid(16)+new Date().getTime();
		client.sendUnitCommand(txtsn.value,callletters,code,params);
		beenSent(name)
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
		var callletter = null;//暂时不传呼号，若传，该呼号的警情可分至
		/*
		var callletters = txtcallletters.value.split(";");
		var callletter = null;
		if (callletters.length > 0) {
			callletter = callletters[0];
		}
		*/
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

//取消挂警
function CancelPauseAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.CancelPauseAlarm(txtseatname.value, txtalarmcaller.value, txtalarmsn.value);
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
		client.AskAlarmList(txtseatname.value, true, true);	//只能自己处理的警情（班长或总监才能取）
	} catch (err) {
		alert(err);
	}
};

//转警
function TransferAlarm(dst) {
	try {
		if (client == null) {
			return;
		}
		client.TransferAlarm(txtseatname.value, dst, txtalarmcaller.value, txtalarmsn.value, "我不清楚怎么处理这种情况，请协助！");
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
//		var callletters = txtcallletters.value.split(";");
		var cr = $("#tj").val();
		txtsn.value = Math.uuid(); 
		client.NewAlarm(txtseatname.value, cr, txtsn.value, new Date().getTime(), txtalarmstatusid.value, txtalarmlevel.value,
				new Date().getTime(), true, 22000000 + Math.random() * 100000, 112000000 + Math.random() * 100000,
				10 + Math.random() * 20, 45, "1,2,5,6,201");
	} catch (err) {
		alert(err);
	}
};

