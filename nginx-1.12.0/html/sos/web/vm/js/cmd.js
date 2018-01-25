//指令
var history_list_markers = [];
var history_data = [];
var history_head = {
 label : "",
 numberPlate : "",
 callLetter : ""
};

function clearHistoryList() {
 for (var i = 0; i < history_list_markers.length; i++) {
  map.removeMarker(history_list_markers[i]);
 }
 history_list_markers.splice(0, history_list_markers.length);
}

function initCmd() {
 /**
  * 查询
  */
 $("#history-start").click(function() {
  var numberPlate = contextmenu_target.numberPlate;
  var callLetter = contextmenu_target.callLetter;
  contextmenu_target.total = 0;
  var startTime = $("#play_history_start_time").val();
  if (startTime == "") {
   Util.showPopover("#play_history_start_time", "请输入开始时间");
   return;
  }
  var endTime = $("#play_history_end_time").val();
  if (endTime == "") {
   Util.showPopover("#play_history_end_time", "请输入结束时间");
   return;
  }
  if (startTime >= endTime) {
   Util.showPopover("#play_history_end_time", "结束时间不能早于开始时间");
   return;
  }
  startTime = SEGUtil_ws.string_2_date(startTime);
  endTime = SEGUtil_ws.string_2_date(endTime);
  history_head.numberPlate = numberPlate;
  history_head.label = numberPlate;
  history_head.callLetter = callLetter;
  history_data.splice(0, history_data.length);
  GetHistoryGPS(callLetter, numberPlate, startTime, endTime)
 });

 /**
  * 播放
  */
 $("#play_history_dlg_play_btn").click(function() {
  if (!history_data || history_data.length == 0) {
   $("#play_history_dlg_search_result").text("请先查询历史数据");
   return false;
  }
  map.playHistory(history_head, history_data, newHistoryCallback, playingHistoryCallback, closeHistoryCallback);
 });

 /**
  * 列点
  */
 $("#play_history_dlg_list_btn").click(function() {
  if (!history_data || history_data.length == 0) {
   $("#play_history_dlg_search_result").text("请先查询历史数据");
   return false;
  }

  $('#play_history_dlg').modal('hide');
  startHistoryList();
 });

 // var history_list_task = null;
 var cancel_history_list_task = false;

 var history_list_point_icon = {
  url : "images/circle_18.png",
  width : 18,
  height : 18,
  left : 0,
  top : 0,
  anchorx : -9,
  anchory : -9
 };

 var progressbar = $("#play_history_list_dlg .progress-bar");
 var progressbar_txt = $("#play_history_list_dlg .history-list-progress-info");

 function startNextTask(startIndex, endIndex) {
  window.setTimeout(function() {
   for (var i = startIndex; i < endIndex; i++) {
    if (cancel_history_list_task) {
     break;
    }

    var data = history_data[i];
    var show = i + 1;
    var ax = -4;
    if (show >= 10000) {
     ax = -20;
    } else if (show >= 1000) {
     ax = -16;
    } else if (show >= 100) {
     ax = -12;
    } else if (show >= 10) {
     ax = -8;
    }

    var m = map.newSimpleMarker({
     lon : data.lon,
     lat : data.lat,
     title : data.gpsTime,
     icon : history_list_point_icon,
     label : {
      text : show,
      anchorx : ax,
      anchory : -8,
      style : {
       fontSize : "12px",
       cursor : "pointer",
       color : "#000000"
      }
     }
    });

    map.addMarker(m);
    history_list_markers.push(m);
   }

   if (cancel_history_list_task) {
    clearHistoryList();
    progressbar.css("width", "0%");
    window.setTimeout(function() {
     $('#play_history_list_dlg').modal('hide');
    }, 500);
    return;
   }

   var p = parseInt(100 * endIndex / history_data.length);
   var per = p + "%";
   progressbar.css("width", per);
   progressbar_txt.text(per + "(" + endIndex + "/" + history_data.length + ")");

   if (endIndex < history_data.length) {
    var next_start = endIndex;
    var next_end = Math.min(history_data.length, next_start + 200);
    startNextTask(next_start, next_end);
   } else {
    if (history_data.length > 0) {
     var data = history_data[0];
     map.setCenter(data.lon, data.lat);
    }
    $('#play_history_list_dlg').modal('hide');
   }
  }, 200);
 }

 function startHistoryList() {
  clearHistoryList();
  progressbar.css("width", "0%");
  $('#play_history_list_dlg').modal({
   show : true,
   backdrop : "static"
  });

  cancel_history_list_task = false;
  var start = 0;
  var end = Math.min(history_data.length, 200);
  startNextTask(start, end);
 }

 function cancelHistoryList() {
  cancel_history_list_task = true;
 }

 $("#play_history_dlg_list_cancel_btn").click(function() {
  cancelHistoryList();
 });
}

/**
 * 将历史位置信息写入history_data gpses
 *
 * @array
 */
function loadHistoryData(gpses, alarmid, alarmname) {
 if (!gpses) {
  return;
 }
 for (var i = 0; i < gpses.length; i++) {
  var gps = gpses[i];
  var callLetter = gps.callLetter;
  var numberPlate = cmd.getNumberPlate(callLetter);
  var gpsTime = SEGUtil_ws.date_2_string(new Date(gpses[i].baseInfo.gpsTime.toNumber()), "yyyy-MM-dd hh:mm:ss");
  var stamp = SEGUtil_ws.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
  var _lon = parseFloat(gps.baseInfo.lng) / 1000000;
  var _lat = parseFloat(gps.baseInfo.lat) / 1000000;
  var speed = parseFloat(typeof (gps.baseInfo.speed) == 'undefined' ? '' : gps.baseInfo.speed)/10;
  var course = gps.baseInfo.course;
  var status = gps.baseInfo.status;
  var isLoc = gps.baseInfo.loc;
  var oil = gps.baseInfo.obdInfo ? gps.baseInfo.obdInfo.oilPercent + '%' : '';
  var referencePosition = gps.referPosition ? gps.referPosition.city : '';
  var temperature = gps.baseInfo.temperature1;// TODO:这里应该是1还是2？
  var alarmidE;
  if (alarmid == 0 || alarmid == null) {
   alarmidE = 0;
  } else {
   alarmidE = 1;
  }
  ;
  var isAlarm = alarmidE;
  var opts = {
   isHistory : 1,
   referencePosition : referencePosition,
   isLoc : isLoc,
   oil : oil,
   temperature : temperature,
   lon : _lon,
   lat : _lat,
   speed : speed,
   course : course,
   gpsTime : gpsTime,
   stamp : stamp,
   isAlarm : isAlarm,
   status : status
  };
  history_data.push(opts);
 }
 ;
}

//var history_console_auto_scroll = false;
//var history_console_scroll_div = null;
var history_console_records = 0;
var history_console_max_records = 1000;
//function initHistoryConsole() {
// history_console_scroll_div = document.getElementById("_GPSMsgPanel");
// $("#_GPSMsgPanel").on("scroll", function() {
//  var clientHeight = history_console_scroll_div.clientHeight;
//  var scrollTop = history_console_scroll_div.scrollTop;
//  var scrollHeight = history_console_scroll_div.scrollHeight;
//  if ((clientHeight + scrollTop) != scrollHeight) {
//   history_console_auto_scroll = false;
//  } else {
//   history_console_auto_scroll = true;
//  }
// });
//}

function playingHistoryCallback(pindex, phead, gpsInfo) {
 var row = fixTable("#_GPSMsgPanel", false, [ phead.numberPlate, phead.callLetter, gpsInfo.isLoc?"已定位":"正在定位", SEGUtil.parseVehicleStatus(gpsInfo.status), parseFloat(gpsInfo.speed), gpsInfo.gpsTime, SEGUtil.getCourseDesc(gpsInfo.course), gpsInfo.stamp ],null,true);
 // row.dblclick(function(){
 //   var rng = document.body.createTextRange;
 //   if (rng) {
 //    rng = rng.call(document.body);
 //    rng.collapse(true);
 //    rng.select();
 //   } else {
 //    window.getSelection().removeAllRanges();
 //   }
 //   var m = map.addOrUpdateVehicleMarkerById(history_data[pindex]);
 //   if (!map.isPointInView(gpsinfo.baseInfo.lng, gpsinfo.baseInfo.lat)) {
 //    map.setCenter(gpsinfo.baseInfo.lng, gpsinfo.baseInfo.lat);
 //   }
 // });
 history_console_records++;
 if (history_console_records > history_console_max_records) {
  $("#_GPSMsgPanel tbody tr:eq(0)").remove();
  history_console_records--;
 }

// if (history_console_auto_scroll) {
//  history_console_scroll_div.scrollTop = history_console_scroll_div.scrollHeight - history_console_scroll_div.clientHeight;
// }
}

/**
 * 播放新的历史回放回调函数
 */
function newHistoryCallback() {
 $("#_GPSMsgPanel tbody").empty();
 history_console_records = 0;
 $("#consolediv_tabs .nav-tabs li:last").css("display", "block");
 $("#export").css("display", "block");
 $('#consolediv_tabs .nav-tabs li:last a').tab('show');
}

/**
 * 关闭历史回放回调函数
 */
function closeHistoryCallback() {
 $("#consolediv_tabs .nav-tabs li:last").css("display", "none");
 $('#consolediv_tabs .nav-tabs li:eq(0) a').tab('show');
 $("#_GPSMsgPanel tbody").empty();
 $("#export").css("display", "none");
 history_console_records = 0;
}