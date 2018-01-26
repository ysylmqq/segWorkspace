/*
 * 测试大平台通信中心协议
 * Copyright 2014 chinagps, Inc.
 */
//通信客户端
"use strict";

var client = null;
var txtcallletters = document.getElementById("callletter");
var txtinfotypes = document.getElementById("infotype");
var txtseatname = document.getElementById("seatname");
var txtseatid = document.getElementById("seatid");
var txtseatpwd = document.getElementById("seatpwd");
var txtsn = document.getElementById("sn");
var txtstarttime = document.getElementById("starttime");
var txtstoptime = document.getElementById("stoptime");
var txtcommandparams = document.getElementById("commandparams");

var txtalarmcaller = document.getElementById("alarmcaller");
var txtalarmsn = document.getElementById("alarmsn");
var txttransferseatname = document.getElementById("transferseatname");

var txtcancelcaller = document.getElementById("cancelcaller");
var txtcancelalarmsn = document.getElementById("cancelalarmsn");

var txtalarmstatusid = document.getElementById("alarmstatusid");
var txtalarmlevel = document.getElementById("alarmlevel");

var txtusertype = document.getElementById("usertype");
var txtuserversion = document.getElementById("userversion");
var log = document.getElementById("log");

var historytype = null;		//读历史记录时（下一页要用到上次是读那种信息）

function onbodyload(){
	txtstarttime.value = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd");
	var stopdate = new Date();
	var stoptmp = stopdate.getDate();
	stoptmp += 1;
	stopdate.setDate(stoptmp);
	txtstoptime.value = SEGUtil_WS.date_2_string(stopdate, "yyyy-MM-dd");
	txtsn.value = Math.uuid();
	document.getElementById("comcenterhost").value = location.host; 
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
			host = document.getElementById("comcenterhost").value;
			client = new segseatlib.jsclient(1, host, txtseatname.value, txtseatpwd.value, txtseatid.value, txtusertype.value, txtuserversion.value);
			//client = new segseatlib.jsclient(1, host, txtseatname.value, txtseatpwd.value, txtseatid.value);
			segseatlib.addClient(client);
		}
		//设置通信过程中压缩报文，默认不压缩
		client.setCompress(true);
		//设置通信过程中加密报文，默认不加密
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
function Logout() {
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
		//var infotype = [-1];
		var infotype = txtinfotypes.value.split(";");
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
		//var infotype = [-1];
		var infotype = txtinfotypes.value.split(";");
		client.removeMonitor(callletters, infotype);
	} catch (err) {
		alert(err);
	}
};

/********************************************************************************************
 * 最后位置
 */
function GetLastInfo(infotype) {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		txtsn.value = Math.uuid();
		client.getLastInfo(infotype, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
};
function GetLastPosition() {
    GetLastInfo(segseatlib.message_type.DeliverGPS);
};
function GetLastOBD() {
	GetLastInfo(segseatlib.message_type.DeliverOBD);
};
function GetLastTravel() {
	GetLastInfo(segseatlib.message_type.DeliverTravel);
}
function GetLastFault() {
	GetLastInfo(segseatlib.message_type.DeliverFault);
}
function GetLastOperate() {
	GetLastInfo(segseatlib.message_type.DeliverOperateData);
}
function GetLastSm() {
	GetLastInfo(segseatlib.message_type.DeliverSMS);
}
function GetLastAlarm() {
	GetLastInfo(segseatlib.message_type.DeliverAlarm);
}
function GetLastUnitLoginout() {
	GetLastInfo(segseatlib.message_type.DeliverUnitLoginOut);
}
function GetLastFaultLight() {
	GetLastInfo(segseatlib.message_type.DeliverFaultLight);
}

/********************************************************************************************
 * 下面取历史轨迹
 */
function GetHistory(infotype) {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		    historytype = infotype;
		    txtsn.value = Math.uuid();
		    client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 100, 1000, false, txtsn.value, true);
		}
	} catch (err) {
		alert(err);
	}
};
function GetHistoryGPS() {
	GetHistory(segseatlib.message_type.DeliverGPS);
};
function GetHistorySimpleGps() {
	GetHistory(segseatlib.message_type.DeliverSimpleGPS);
};
function GetHistoryOBD() {
	GetHistory(segseatlib.message_type.DeliverOBD);
}
function GetHistoryTravel() {
	GetHistory(segseatlib.message_type.DeliverTravel);
}
function GetHistoryFault() {
	GetHistory(segseatlib.message_type.DeliverFault);
}
function GetHistoryOperate() {
	GetHistory(segseatlib.message_type.DeliverOperateData);
}
function GetHistorySm() {
	GetHistory(segseatlib.message_type.DeliverSMS);
}
function GetHistoryAlarm() {
	GetHistory(segseatlib.message_type.DeliverAlarm);
}
function GetHistoryUnitLoginout() {
	GetHistory(segseatlib.message_type.DeliverUnitLoginOut);
}
function GetHistoryFaultLight() {
	GetHistory(segseatlib.message_type.DeliverFaultLight);
}

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
function GetCommandHistory() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value); //半天前
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
          client.getCommandHistory(callletters[0], starttime, endtime);
        }
	} catch (err) {
		alert(err);
	}
}

//无参数的指令
function NoParamCommand(cmdid) {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = cmdid + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		client.sendUnitCommand(txtsn.value, callletters, cmdid, null, true);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", " + cmdid + "\n";
	} catch (err) {
		alert(err);
	}
}

//循环发查车指令
function FindCar() {
	//for(var i=0; i<100000; i++) {
		NoParamCommand(0x0001);
		// Sleep(1);
	//};
}

//发动机点火，海马的TBOX带时间参数，多少分钟后熄火
function EngineOn() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0069-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = [];		//5分钟后熄火
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x0069, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 0x0069\n";
	} catch (err) {
		alert(err);
	}
};

//跟踪
function Trace(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0015-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["100", "30"];		//100次， 30秒一次
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x0015, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 0x0015\n";
	} catch (err) {
		alert(err);
	}
};
//定时上传
function IntervalDeliver(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0038-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["30"];		//30秒一次
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x0038, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 0x0038\n";
	} catch (err) {
		alert(err);
	}
};
//监听
function Listen(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0017-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["26719595"];		//监听电话
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x0017, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 0x0017\n";
	} catch (err) {
		alert(err);
	}
};
//终端短信
function UnitSMS(){
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0035-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["赛格导航欢迎您！联系电话 - 075526719888, 952100"];		//发送到终端的短信
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		for(var i=0; i<callletters.length; i++) {
			client.sendUnitCommand(txtsn.value + i, callletters[i].split(";"), 0x0035, params);
		}
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 0x0035\n";
		//client.sendUnitCommand(txtsn.value, callletters, 0x0035, params);
	} catch (err) {
		alert(err);
	}
};
//一般手机短信
function SendSMS() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x4035-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["赛格导航欢迎您！联系电话 - 075526719888, 952100"];		//发送到手机的短信
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x4035, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 0x4035\n";
	} catch (err) {
		alert(err);
	}
};
//设置SOS号码
function SetSOS() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x00A6-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = ["075526719988;120;110"];	//SOS号码用分号隔开
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x00A6, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 设置SOS\n";
	} catch (err) {
		alert(err);
	}
}
//一键导航
function Navgate() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0292-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = [1, "123.123456", "22.345678", "123.12,22.3;123.123,22.34", "113.5,10.6;113.5,10.7"];	//高速优先,没有必经点和回避点
		if (txtcommandparams != null) {
			if (txtcommandparams.value != "") {
				params = txtcommandparams.value.split(";");
			}
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x0292, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 一键导航\n";
	} catch (err) {
		alert(err);
	}
}
//升级
function Upgrade() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x00A5-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = null;
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		for(var i=0; i<callletters.length; i++) {
			client.sendUnitCommand(txtsn.value + i, callletters[i].split(";"), 0x00A5, params);
		}
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 升级\n";
	} catch (err) {
		alert(err);
	}
}
//打开空调
function OpenAirConditioner() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0065-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = [26, 1, 3, 0, 1, 1, 1, 1]; //设定温度、出风模式、风速等级设置、循环模式、A/C关闭、Auto关闭、除前霜、除后霜：
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x0065, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 打开空调\n";
	} catch (err) {
		alert(err);
	}
}
//北汽远程控制
function BeiCarControl() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x0313-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = [26, 1, 3, 0, 1, 1, 1, 1]; //设定温度、出风模式、风速等级设置、循环模式、A/C关闭、Auto关闭、除前霜、除后霜：
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x0313, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 北汽远程控制\n";
	} catch (err) {
		alert(err);
	}
}

//海马校订侧翻参数
function SetRollOverParameter() {
	try {
		if (client == null) {
			return;
		}
		txtsn.value = "0x00B6-" + Math.uuid(16);
		var callletters = txtcallletters.value.split(";");
		var params = [1, 3]; //设置终端是否进行自动校正, 最大侧翻角度，单位：度
		if (txtcommandparams != null) {
			params = txtcommandparams.value.split(";");
		}
		client.sendUnitCommand(txtsn.value, callletters, 0x00B6, params);
	    log.value += nowstring() + "下发指令:" + txtcallletters.value + ", 海马校订侧翻参数\n";
	} catch (err) {
		alert(err);
	}
}

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
		txtcancelcaller.value = txtalarmcaller.value;
		txtcancelalarmsn.value = txtalarmsn.value;
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
		client.CancelPauseAlarm(txtseatname.value, txtcancelcaller.value, txtcancelalarmsn.value);
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
		if (txtcancelcaller.value != "") {
			client.HandleAlarm(txtseatname.value, txtcancelcaller.value, txtcancelalarmsn.value,
					segseatlib.resultcode.OK, "处警结束");
			txtcancelcaller.value = "";
			txtcancelalarmsn.value = "";
		} else {
			client.HandleAlarm(txtseatname.value, txtalarmcaller.value, txtalarmsn.value,
				segseatlib.resultcode.OK, "处警结束");
		}
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
		client.AskSeatList(txtseatname.value, txtalarmcaller.value, false);
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

