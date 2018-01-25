/*!
 *
 * Copyright 2014 chinagps, Inc. zhangxz
 *
 */
"use strict";

// websocket通信错误事件
function onerror(cli, event) {
};

// websocket连接成功事件
function onopen(cli, event) {
};

// websocket连接关闭事件
function onclose(cli, event) {
};

// websocket客户端登录(连接成功后自动登录，不用人工登录) 事件
function onlogin(cli, retcode, retmsg) {
 // AddMonitor();
};

// 添加监控列表结果事件
function onaddmonitor(cli, retcode, retmsg, callLetters) {
 txtcallletters = compatibleArray(callLetters);
};

// 取消监控列表结果事件
function onremovemonitor(cli, retcode, retmsg, callLetters) {
 txtcallletters = compatibleArray(callLetters);
};

// 指令发送结果
function onsendcommandsend(cli, sn, callLetter, cmdId, retcode, retmsg) {
 var panel = $("#order_" + sn + callLetter + ">span.wait");
 panel.removeClass("wait");
 if(retcode == 0) {
  panel.addClass("correct");
 }else {
  panel.addClass("error");
  if (retmsg) {
   var cmdtime = SEGUtil_ws.date_2_string(new Date, "hh:mm:ss");
   $("#order_" + sn + callLetter + ">div.hidden").removeClass("hidden").addClass("error").text(cmdtime + " " + retmsg);
  }
 }
 $("#orderMsg-panel").tab('show');
}

// 车台回应
function onsendcommand(cli, sn, callLetter, cmdId, retcode, retmsg, params, gpsInfo) {
 var cmdtime = SEGUtil_ws.date_2_string(new Date, "hh:mm:ss");
 var panel = $("#order_" + sn + callLetter + ">div.hidden");
 if (retcode == 0) {
  retmsg = '车台已回应';
  panel.addClass("correct");
 } else {
  // $("#order_" + sn + callLetter + ">span.wait").removeClass("wait").addClass("error");
  panel.addClass("error");
 }
 if (retmsg) {
  panel.removeClass("hidden").text(cmdtime + " " + retmsg);
 }
 $("#orderMsg-panel").tab('show');
 //ondelivergpsWhole(gpsInfo);
}

// 读最后位置信息
function ongetlastgps(cli, retcode, retmsg, gpses, alarmid, alarmname) {
 if (!gpses) {
  Util.showMsg(retmsg);
  return;
 }
 for (var i = 0; i < gpses.length; i++) {
  // 在地图中显示并居中
  if (!gpses[i].baseInfo)
   return; // 如果缺少gps信息则忽略
  var callLetter = gpses[i].callLetter;
  var numberPlate = cmd.getNumberPlate(callLetter);
  var speed = parseFloat(typeof (gpses[i].baseInfo.speed) == 'undefined' ? '' : gpses[i].baseInfo.speed)/10;
  var gpsTime = SEGUtil_ws.date_2_string(new Date(gpses[i].baseInfo.gpsTime.toNumber()), "yyyy-MM-dd hh:mm:ss");
  var isLoc = gpses[i].baseInfo.loc;
  var lon = gpses[i].baseInfo.lng;
  var lat = gpses[i].baseInfo.lat;
  var course = gpses[i].baseInfo.course;
  var cn_course = SEGUtil.getCourseDesc(course);
  var stamp = SEGUtil_ws.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
  var alarmidE;
  if (alarmid == 0 || alarmid == null) {
   alarmidE = 0;
  } else {
   alarmidE = 1;
  }
  ;
  var isAlarm = alarmidE;
  var status = gpses[i].baseInfo.status;
  var cn_status = SEGUtil.parseVehicleStatus(status);

  var _lon = parseFloat(lon) / 1000000;
  var _lat = parseFloat(lat) / 1000000;
  var opts = {
   id : numberPlate,
   callLetter : callLetter,
   label : numberPlate + " " + gpsTime,
   numberPlate : numberPlate,
   isLoc : isLoc,
   lon : _lon,
   lat : _lat,
   speed : speed,
   course : parseInt(course),
   gpsTime : gpsTime,
   stamp : stamp,
   isAlarm : parseInt(isAlarm),
   status : status
  };

  var m = map.addOrUpdateVehicleMarkerById(opts);
  if (!map.isPointInView(_lon, _lat)) {
   map.setCenter(_lon, _lat);
  }

  m.target.flicker();
  var row = fixTable("#_GPSMsgPanel", false, [ numberPlate, callLetter, isLoc?"已定位":"正在定位", cn_status, speed, gpsTime, cn_course, stamp ],null,true);
  row.dblclick(function(){
   var m = map.addOrUpdateVehicleMarkerById(opts);
   if (!map.isPointInView(_lon, _lat)) {
    map.setCenter(_lon, _lat);
   }
   m.target.flicker(500);
 });
 }
}

// 显示读最后信息错误
function ongetlasterror(cli, retcode, retmsg) {
  Util.showMsg(retmsg);
};

// 读历史位置信息
function ongethistorygps(cli, retcode, retmsg, lastpage, gpses) {
 loadHistoryData(gpses); // 该函数在js/cmd.js
 contextmenu_target.total += gpses.length;// 加上这一页的条数
 if (!lastpage) {// 如果不是最后一页
  GetHistoryNextPage();// 取下一页
 } else {// 如果是最后一页
  if (history_data && history_data.length > 0) {
   $("#play_history_dlg_play_btn").removeClass("disabled");
   $("#play_history_dlg_list_btn").removeClass("disabled");
   $("#play_history_dlg_search_result").text('共 ' + contextmenu_target.total + ' 条数据');
  } else {
   $("#play_history_dlg_play_btn").addClass("disabled");
   $("#play_history_dlg_list_btn").addClass("disabled");
   $("#play_history_dlg_search_result").text('');
  }
 }
}

// 读历史位置主要信息
function ongethistorysimplegps(cli, callLetter, retcode, retmsg, lastpage, gpses) {
}

// 显示读历史信息错误
function ongethistoryerror(cli, retcode, retmsg) {
 Util.showMsg(retmsg);
 $("#play_history_dlg_play_btn").addClass("disabled");
 $("#play_history_dlg_list_btn").addClass("disabled");
 $("#play_history_dlg_search_result").text('');
};

// 结束读历史信息
function onstophistory(cli, retcode, retmsg) {
};

// 车辆上传实时位置信息
function ondelivergps(cli, gpsinfo, gatewayid, gatewaytype, alarmid, alarmname) {
 ondelivergpsWhole(gpsinfo);
}

// 车辆上传GPS信息
function ondelivergpsWhole(gpsinfo, alarmid, alarmname) {
 if (!gpsinfo.baseInfo)
  return; // 如果缺少gps信息则忽略
 var callLetter = gpsinfo.callLetter;
 var numberPlate = cmd.getNumberPlate(callLetter);
 var speed = parseFloat(typeof (gpsinfo.baseInfo.speed) == 'undefined' ? '' : gpsinfo.baseInfo.speed)/10;
 var gpsTime = SEGUtil_ws.date_2_string(new Date(gpsinfo.baseInfo.gpsTime.toNumber()), "yyyy-MM-dd hh:mm:ss");
 var isLoc = gpsinfo.baseInfo.loc;
 var lon = gpsinfo.baseInfo.lng;
 var lat = gpsinfo.baseInfo.lat;
 var course = gpsinfo.baseInfo.course;
 var cn_course = SEGUtil.getCourseDesc(course);
 var stamp = SEGUtil_ws.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
 var alarmidE;
 if (alarmid == 0 || alarmid == null) {
  alarmidE = 0;
 } else {
  alarmidE = 1;
 }
 ;
 var isAlarm = alarmidE;
 var status = gpsinfo.baseInfo.status;
 var cn_status = SEGUtil.parseVehicleStatus(status);

 var _lon = parseFloat(lon) / 1000000;
 var _lat = parseFloat(lat) / 1000000;
 var opts = {
  id : numberPlate,
  callLetter : callLetter,
  label : numberPlate + " " + gpsTime,
  numberPlate : numberPlate,
  isLoc : isLoc,
  lon : _lon,
  lat : _lat,
  speed : speed,
  course : parseInt(course),
  gpsTime : gpsTime,
  stamp : stamp,
  isAlarm : parseInt(isAlarm),
  status : status
 };

 var m = map.addOrUpdateVehicleMarkerById(opts);
 if (!map.isPointInView(_lon, _lat)) {
  map.setCenter(_lon, _lat);
 }

 m.target.flicker();
 var row = fixTable("#_GPSMsgPanel", false, [ numberPlate, callLetter, isLoc?"已定位":"正在定位", cn_status, speed, gpsTime, cn_course, stamp ],null,true);
 row.dblclick(function(){
   var m = map.addOrUpdateVehicleMarkerById(opts);
   if (!map.isPointInView(_lon, _lat)) {
    map.setCenter(_lon, _lat);
   }
   m.target.flicker(500);
 });
}

// 终端登退录事件
function ondeliverunitloginout(selfclient, callLetter, inorout, gatewayid) {
};

/*******************************************************************************
 * 下面的和处警相关的事件
 */
// 坐席设置忙闲，通信中心应答
function onsetalarmbusy(selfclient, retcode, retmsg, username, busy, handleseatname) {
};

// 通信中心分配警情
function onallotalarm(selfclient, seatname, callLetter, alarmsn, alarmTime, alarmid, alarmname, gpsinfo, alarmcount, append) {
};

function ondeliveralarm() {
};

// 通信中心应答挂警
function onpausealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
};

// 通信中心应答处警结果
function onhandlealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
};

// 申请坐席列表结果
function onaskseatlistack(selfclient, retcode, retmsg, seats) {
};

// 服务器返回转警请求结果, 只有等到已经转到目的坐席才返回
function ontransferalarmack(selfclient, retcode, retmsg, srcusername, dstusername, callLetter, alarmsn) {
};

// 服务器向转警目的坐席分配转警
function onallottransferalarm(selfclient, username, callLetter, alarmsn, alarmTime, alarmid, alarmname, gpsinfo, alarmcount, srcusername, transfermsg) {
};

// 通信中心返回警情列表
function onaskalarmlistack(selfclient, retcode, retmsg, alarms) {
};

// 通信中心返回添加新警情结果
function onnewalarmack(selfclient, retcode, retmsg, slaver, callLetter, alarmsn) {
};

