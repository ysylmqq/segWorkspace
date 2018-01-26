/*
 * 测试大平台通信中心协议
 * Copyright 2014 chinagps, Inc.
 */
//通信客户端
var client = null;
var txtcallletters = document.getElementById("callletter");
var txtinfotypes = document.getElementById("infotype");
var txtsn = document.getElementById("sn");
var txtstarttime = document.getElementById("starttime");
var txtstoptime = document.getElementById("stoptime");

function onbodyload(){
	txtstarttime.value = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd");
	txtstoptime.value = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
};

function Connect() {
	try {
		//多个通信中心服务器，用分号隔开
		var host = location.host;
		if (client == null) {
			//segseatlib中同时可以有多个客户端
			/* 1: 每个客户端有不同ID, 客户端自定义，事件中可区分
			 * 
			 * 登录用户名和密码
			 */
			//host = "90.0.12.135:28090";
			client = new segseatlib.jsclient(1, host, "CHINAGPS", "abc123");
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

function Logout() {
	try {
		if (client == null) {
			return;
		}
		client.logout();
	} catch (err) {
		alert(err);
	}
};

function ActiveLink() {
	try {
		if (client == null) {
			return;
		}
		client.activelink();
	} catch (err) {
		alert(err);
	}
};

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
		var infotype = txtinfotypes.value.split(";");
		client.removeMonitor(callletters, infotype);
	} catch (err) {
		alert(err);
	}
};

function GetLastPosition() {
	try {
		if (client == null) {
			return;
		}
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333", "13612345678", "13112345678", "13212345678", "13312345678", "13412345678"];
		var callletters = txtcallletters.value.split(";");
		client.getLastInfo(segseatlib.message_type.DeliverGPS, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

function GetLastTravel() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.getLastInfo(segseatlib.message_type.DeliverTravel, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

function GetLastFault() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.getLastInfo(segseatlib.message_type.DeliverFault, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

function GetLastOperate() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.getLastInfo(segseatlib.message_type.DeliverOperateData, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

function GetLastSm() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.getLastInfo(segseatlib.message_type.DeliverSMS, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

function GetLastAlarm() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.getLastInfo(segseatlib.message_type.DeliverAlarm, callletters, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

var historytype = null;

function GetHistoryGPS() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverGPS;
		  client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 100, 1000, false, txtsn.value);
		}
	} catch (err) {
		alert(err);
	}
}

function GetHistorySimpleGps() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverSimpleGPS;
		  client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 300, 5000, false, txtsn.value);
		}
	} catch (err) {
		alert(err);
	}
}

function GetHistoryTravel() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverTravel;
    	  client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 97, 5000, false, txtsn.value, true);
    	}
	} catch (err) {
		alert(err);
	}
}

function GetHistoryFault() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value);
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverFault;
          client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 100, 5000, false, txtsn.value);
        }
	} catch (err) {
		alert(err);
	}
}

function GetHistoryOperate() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value); //半天前
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverOperateData;
          client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 100, 5000, false, txtsn.value);
        }
	} catch (err) {
		alert(err);
	}
}

function GetHistorySm() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value); //半天前
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverSMS;
          client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 100, 5000, false, txtsn.value);
        }
	} catch (err) {
		alert(err);
	}
}

function GetHistoryAlarm() {
	try {
		if (client == null) {
			return;
		}
		var starttime = SEGUtil_WS.string_2_date(txtstarttime.value); 
		var endtime = SEGUtil_WS.string_2_date(txtstoptime.value); //半天前
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0) {
		  historytype = segseatlib.message_type.DeliverAlarm;
          client.getHistoryInfo(callletters[0], historytype, starttime, endtime, 100, 5000, false, txtsn.value);
        }
	} catch (err) {
		alert(err);
	}
}

function GetHistoryNextPage() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0)
    		client.getHistoryNextPage(callletters[0], historytype, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

function StopHistory() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		if (callletters.length > 0)
    		client.stopHistory(callletters[0], historytype, txtsn.value);
	} catch (err) {
		alert(err);
	}
}

function TestDeliverGPS(){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333"];
		client.testDeliver(segseatlib.message_type.DeliverGPS, callletters);
	} catch (err) {
		alert(err);
	}
}

function TestDeliverOperateData(){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.testDeliver(segseatlib.message_type.DeliverOperateData, callletters);
	} catch (err) {
		alert(err);
	}
}

function TestDeliverSMS(){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.testDeliver(segseatlib.message_type.DeliverSMS, callletters);
	} catch (err) {
		alert(err);
	}
}

function TestDeliverUnitLoginOut(){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.testDeliver(segseatlib.message_type.DeliverUnitLoginOut, callletters);
	} catch (err) {
		alert(err);
	}
}

function TestDeliverTravel(){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.testDeliver(segseatlib.message_type.DeliverTravel, callletters);
	} catch (err) {
		alert(err);
	}
}

function TestDeliverFault(){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.testDeliver(segseatlib.message_type.DeliverFault, callletters);
	} catch (err) {
		alert(err);
	}
}

function TestDeliverAlarm(){
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		client.testDeliver(segseatlib.message_type.DeliverAlarm, callletters);
	} catch (err) {
		alert(err);
	}
}

function Position(){
	try {
		if (client == null) {
			return;
		}
		var sn = "0x0001" + new Date().getTime();
		txtsn.value = sn;
		var callletters = txtcallletters.value.split(";");
		//命令字请参考大平台指令及应答参数说明书
		client.sendUnitCommand(txtsn.value, callletters, 0x0001);
	} catch (err) {
		alert(err);
	}
};

function FindCar(){
	try {
		if (client == null) {
			return;
		}
		var sn = "0x0013" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0005" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0004" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0006" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0007" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0015" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0038" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0017" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x0035" + new Date().getTime();
		txtsn.value = sn;
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
		var sn = "0x4035" + new Date().getTime();
		txtsn.value = sn;
		var callletters = txtcallletters.value.split(";");
		//var callletters = ["18922819616", "13912345678", "13922222222", "1393333333"];
		var params = ["赛格导航欢迎您！联系电话 - 075526719888, 952100"];		//发送到手机的短信
		client.sendUnitCommand(txtsn.value, callletters, 0x4035, params);
	} catch (err) {
		alert(err);
	}
}
