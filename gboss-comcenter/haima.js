/*
 * 测试大平台通信中心协议
 * Copyright 2014 chinagps, Inc.
 */
//通信客户端
"use strict";

var client = null;
var txtcallletters = document.getElementById("callletter");

/********************************************************************************************
 * 登录通信中心
 */
function Connect() {
	try {
		//多个通信中心服务器，用分号隔开
		var host = location.host;
		if (client == null) {
			client = new segseatlib.jsclient(1, host, "海马巡检员", "123456", 0, "坐席巡检", "1.0");
			segseatlib.addClient(client);
		}
		//设置通信过程中压缩报文，默认不压缩
		client.setCompress(true);
		//设置通信过程中加密报文，默认不加密
		client.setEncrypt(true);
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
		var callletters = txtcallletters.value.split(";");
		var infotype = [-1];
		client.addMonitor(callletters, infotype);
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
		var callletters = txtcallletters.value.split(";");
		client.getLastInfo(segseatlib.message_type.DeliverGPS, callletters, Math.uuid(16));
	} catch (err) {
		alert(err);
	}
};

function NoParamCommand(cmdid) {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书, 查车，默认添加到
		client.sendUnitCommand(cmdid + Math.uuid(16), callletters, cmdid, null, true);
	} catch (err) {
		alert(err);
	}
}
