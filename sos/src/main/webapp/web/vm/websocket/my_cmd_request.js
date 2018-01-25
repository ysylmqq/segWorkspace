/*
 * 测试大平台通信中心协议
 * Copyright 2014 chinagps, Inc.
 */
//通信客户端
"use strict";
var client = null;

var txtcallletters = compatibleArray();
var txtnumberPlates = compatibleArray();
// 默认的错误提示语
var defalut_msg = "服务器忙，请稍后再试";
var txtseatname = {
 value : "sos"
};
var txtseatid = {
 value : "1001"
};
var txtseatpwd = {
 value : "123456"
};
var txtsn = {};

var historytype = null; // 读历史记录时（下一页要用到上次是读那种信息）

var cmd = new (function() {
 var _sn;
 var _name;
 var _numberPlates;
 var _callLetters;
 var _sendTime;
 var _list = {size:0};
 /**
  * 登记一个已发送的指令
  */
 this.mark = function(sn, name, numberPlates, callLetters) {
  _sendTime = SEGUtil_ws.date_2_string(new Date, "hh:mm:ss");
  _sn = sn;
  _name = name;
  if (typeof (numberPlates) == "string") {
   _numberPlates = [numberPlates];
  } else {
   _numberPlates = numberPlates;
  }
  if (typeof (callLetters) == "string") {
   _callLetters = [callLetters];
  } else {
   _callLetters = callLetters;
  }
  for (var i = 0; i < _callLetters.length; i++) {
    _list["c_" + _callLetters[i]] = _numberPlates[i];
    _list.size ++;
  };
 }

 this.getNumberPlate = function(callLetter) {
  if (typeof(callLetter) == "object") { // 如果传入了一个数组，也只取第一个
   callLetter = callLetter[0];
  }
  return _list["c_" + callLetter];
 }

 this.getCallLetters = function(){
  return _callLetters;
 }

 this.getName = function() {
  return _name;
 }

 this.showOrderMsg = function() {
  for (var int = _callLetters.length - 1; int > -1; int--) {
   var panel = $("#_orderMsgPanel>.list-group");
   if (panel.find("#orderlist_" + _callLetters[int]).length == 0) {
    var li = "<li class='list-group-item noborder'><i class='glyphicon glyphicon-chevron-up'></i> <span>";
    li += cmd.getNumberPlate(_callLetters[int]) + "</span><ul class='list-group nogap' id='orderlist_";
    li += _callLetters[int] + "'></ul>";
    panel.prepend(li);
   }
   var order = "<li class='list-group-item noborder' id='order_";
   order += _sn + _callLetters[int] + "'><span class='wait'>" + _sendTime + "</span> <span>";
   order += _name + "</span><div class='hidden'></div>"
   panel.find("#orderlist_" + _callLetters[int]).prepend(order);
  }
 }
});

/*******************************************************************************
 * 登录通信中心
 */
function Connect() {
 try {
  // 多个通信中心服务器，用分号隔开
  var host = location.host;
  if (client == null) {
   // segseatlib中同时可以有多个客户端
   /*
    * 1: 每个客户端有不同ID, 客户端自定义，事件中可区分
    *
    * 登录用户名(坐席操作员名称)、密码、坐席编号
    */
   // host = "192.110.10.164:8070;192.110.10.163:8070;192.110.10.162:8070;192.110.10.161:8070;202.105.139.92:8070";
   host = "202.105.139.92:8070";
   client = new segseatlib.jsclient(1, host, txtseatname.value, txtseatpwd.value, txtseatid.value);
   segseatlib.addClient(client);
  }
  // 废黜通信过程中压缩报文，默认不压缩
  client.setCompress(true);
  // 废黜通信过程中加密报文，默认不加密
  client.setEncrypt();
  // 连接服务器, 连接成功后自动登录
  client.connect();
 } catch (msg) {
  console.error(msg);
 }
};
Connect();
/*******************************************************************************
 * 下面取历史轨迹
 */
function GetHistoryGPS(callletters, numberPlates, starttime, endtime, interval, minSpeed) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  if (callletters.length > 0) {
   historytype = segseatlib.message_type.DeliverGPS;
   txtsn.value = Math.uuid();
   client.getHistoryInfo(callletters, historytype, starttime, endtime, 300, 5000, false, txtsn.value);
   cmd.mark(txtsn.value, "历史回放", numberPlates, callletters);
  }
 } catch (msg) {
  console.error(msg);
 }
};

function GetHistoryNextPage() {
 if (client == null) {
  throw "通信中心连接失败";
 }
 var callletters = cmd.getCallLetters();
 if (callletters.length > 0) {
  client.getHistoryNextPage(callletters, historytype, txtsn.value);
 }
};

function StopHistory() {
 if (client == null) {
  throw "通信中心连接失败";
 }
 var callletters = txtcallletters;
 if (callletters.length > 0) {
  client.stopHistory(callletters[0], historytype, txtsn.value);
 }
};

/*******************************************************************************
 * 最后位置
 */
function GetLastPosition(callletters, numberPlates) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  txtsn.value = Math.uuid();
  var _result = client.getLastInfo(segseatlib.message_type.DeliverGPS, callletters, txtsn.value);
  if (_result != 0) {
   throw "通信中心连接失败";
  }
  cmd.mark(txtsn.value, "最后位置", numberPlates, callletters);
 } catch (msg) {
  console.error(msg);
  Util.showMsg(defalut_msg);
 }
};

// 添加和取消监控列表
function AddMonitor(callletters, numberPlates) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  // ALLUNIT表示全部终端，请参考《大平台通信中心通信协议》
  // var callletters = ["18922819616", "13912345678", "13922222222",
  // "1393333333", "ALLUNIT"];
  var infotype = [ -1 ];
  // var infotype = txtinfotypes.value.split(";");
  var _result = client.addMonitor(callletters, infotype);
  if (_result != 0) {
   throw "通信中心连接失败";
  }
  cmd.mark(txtsn.value, "添加监控列表", numberPlates, callletters);
 } catch (msg) {
  console.error(msg);
 }
};

function RemoveMonitor(callletters) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  // ALLUNIT表示全部终端，请参考《大平台通信中心通信协议》
  // var callletters = ["1393333333", "ALLUNIT"];
  var infotype = [ -1 ];
  // var infotype = txtinfotypes.value.split(";");
  var _result = client.removeMonitor(callletters, infotype);
  if (_result != 0) {
   throw "通信中心连接失败";
  };
 } catch (msg) {
  console.error(msg);
 }
};
// 指令部分

// 查车---------------------------------------------
function Position(callletters, numberPlates) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  // var sn = "0x0001" + new Date().getTime();
  txtsn.value = "0x0001-" + Math.uuid(16);
  // 命令字请参考大平台指令及应答参数说明书, 查车，默认添加到
  var _result = client.sendUnitCommand(txtsn.value, callletters, 0x0001, null, true);
  if (_result != 0) {
   throw "通信中心连接失败";
  };
  Util.showMsg("查车指令已发送");
  cmd.mark(txtsn.value, "查车", numberPlates, callletters);
  cmd.showOrderMsg();
 } catch (msg) {
  console.error(msg);
  Util.showMsg(defalut_msg);
 }
};

// 跟踪---------------------------------------------
function Trace(callletters, numberPlates, timeSS, timeFre) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  txtsn.value = "0x0015-" + Math.uuid(16);
  var params = [ timeFre, timeSS ]; // 100次， 30秒一次
  // 命令字请参考大平台指令及应答参数说明书
  var _result = client.sendUnitCommand(txtsn.value, callletters, 0x0015, params);
  if (_result != 0) {
   throw "通信中心连接失败";
  };
  Util.showMsg("开始跟踪指令已发送");
  cmd.mark(txtsn.value, "跟踪", numberPlates, callletters);
  cmd.showOrderMsg();
 } catch (msg) {
  console.error(msg);
  Util.showMsg(defalut_msg);
 }
};

// 停止跟踪---------------------------------------------
function StopTrace(callletters, numberPlates) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  txtsn.value = "0x0016-" + Math.uuid(16);
  // 命令字请参考大平台指令及应答参数说明书
  var _result = client.sendUnitCommand(txtsn.value, callletters, 0x0016);
  if (_result != 0) {
   throw "通信中心连接失败";
  };
  Util.showMsg("停止跟踪指令已发送");
  cmd.mark(txtsn.value, "停止跟踪", numberPlates, callletters);
  cmd.showOrderMsg();
 } catch (msg) {
  console.error(msg);
  Util.showMsg(defalut_msg);
 }
};

// 车台复位------------------------------------------------------
function Reback(callletters, numberPlates) {
 try {
  if (client == null) {
   throw "通信中心连接失败";
  }
  // var sn = "0x0004" + new Date().getTime();
  txtsn.value = "0x0002-" + Math.uuid(16);
  // 命令字请参考大平台指令及应答参数说明书
  var _result = client.sendUnitCommand(txtsn.value, callletters, 0x0002);
  if (_result != 0) {
   throw "通信中心连接失败";
  };
  Util.showMsg("车台复位指令已发送");
  cmd.mark(txtsn.value, "车台复位", numberPlates, callletters);
  cmd.showOrderMsg();
  // for (var i = 0; i < callletters.length; i++) {
  //  onsendcommandsend(null, txtsn.value, callletters[i], null, 0, null);
  // };
 } catch (msg) {
  console.error(msg);
  Util.showMsg(defalut_msg);
 }
};