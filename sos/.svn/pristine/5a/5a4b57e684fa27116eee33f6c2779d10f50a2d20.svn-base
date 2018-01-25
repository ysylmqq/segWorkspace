var selCityHideFlag = null; /* 地图-选择城市下拉项隐藏标记 */
var contextmenu_target = {};

window.alert = function(msg) {
}
// 加入监控面板
function addToWatchList(data) {
 window.focus();
 if (!data) {
  data = {
   list : []
  };

  // 获取所有已选择的行，加入到数组中
  $("#searchResultPanel tbody>tr>td>input:checked").parent().parent().each(function(index, ele) {
   // 检查是否已经在列表中
   if ($("#watchListPanel table>tbody>tr[plate_no='" + ele.getAttribute("plate_no") + "']").length != 0) {
    return;
   }
   data.list.push({
    unit_id : ele.getAttribute("unit_id"),
    vehicle_id : ele.getAttribute("vehicle_id"),
    plate_no : ele.getAttribute("plate_no"),
    call_letter : ele.getAttribute("call_letter")
   });
   data.count++;
  });
 } else {
  /* 处理单条数据 */
  if (!data.list) {
   data.list = [ data ];
  }
  /* 处理多条数据 */
  for (var int = 0; int < data.list.length; int++) {
   var vehicle = data.list[int];
   if ($("#watchListPanel table>tbody>tr[plate_no='" + vehicle.plate_no + "']").length != 0) {
    data.list[int] = null;
   }
  }
 }
 // 加入监控列表
 var callLetters = [];
 var numberPlates = [];
 for (var int = 0; int < data.list.length; int++) {
  if (data.list[int] == null) {
   continue;
  }
  callLetters.push(data.list[int].call_letter);
  numberPlates.push(data.list[int].plate_no);
 }
 if (callLetters.length != 0) {
  fixTable("#watchListPanel table", false, [ "<input type='checkbox'/>", "plate_no", "call_letter" ], data, true);
  $("#watch-panel").tab('show');
  // 更新表头选中状态
  var table = $("#carList-tabPanel>.active table");
  table.find("thead>tr>th>:checkbox").prop("checked", table.find("tbody>[class!=selected]").length == 0);
  AddMonitor(callLetters, numberPlates);
 }
};

// 从监控面板删除
function removeFromWatchList(data) {
 var callLetters
 if (data == "all") {
  callLetters = [];
  $('#watchListPanel tbody>tr').each(function(index, ele) {
   callLetters.push($(ele).attr("call_letter"));
   $(ele).remove();
  });
 } else {
  callLetters = getSelectRow("#watchListPanel table", "call_letter");
  $('#watchListPanel tbody>tr>td>input:checked').parent().parent().remove();
  // 更新表头选中状态
  var table = $("#carList-tabPanel>.active table");
  table.find("thead>tr>th>:checkbox").prop("checked", table.find("tbody>[class!=selected]").length == 0);
 }
 if (callLetters.length != 0) {
  RemoveMonitor(callLetters);
 }
};

// 初始化填充表格： table选择器；是否先清空原有内容；data数据
function fixTable(table, clean, showAttrs, data, atStart) {
 if (clean) {
  $(table).find("tbody>tr").remove();
 }
 if (!showAttrs) {
  return;
 }
 if (data) {
  for (var i = 0; i < data.list.length; i++) {

   /* 处理每一条数据 */
   var info = data.list[i];
   if (!info) {
    continue;
   }
   /* 遍历info的属性，写入到<tr>中 */
   var str = "<tr ";
   for ( var attr in info) {
    str += attr + "=" + info[attr] + " ";
   }
   str += ">";
   for (var index = 0; index < showAttrs.length; index++) {
    var show;
    if (!showAttrs[index] || showAttrs[index] == "") {
     show = "";
    } else if (showAttrs[index].renderer) {
     show = showAttrs[index].renderer(info[showAttrs[index].header]);
    } else {
     show = info[showAttrs[index]];
     if (!show && show != 0) {
      show = showAttrs[index];
     }
    }
    str += "<td>" + show + "</td>";
   }

   str += "</tr>";
   if (atStart) {
    $(table).find("tbody").prepend(str);
    if ($(table).scrollTop() != 0)
     $(table).scrollTop($(table).scrollTop() + 29);
   } else {
    $(table).find("tbody").prepend(str);
   }

  }
 } else {
  var str = "<tr ";
  str += ">";
  for (var index = 0; index < showAttrs.length; index++) {
   var show = showAttrs[index];
   if (!show && show != 0) {
    show = "";
   }
   str += "<td>" + show + "</td>";
  }

  str += "</tr>";
  str = $(str);
  if (atStart) {
   $(table).find("tbody").prepend(str);
   if ($(table).scrollTop() != 0)
    $(table).scrollTop($(table).scrollTop() + 29);
  } else {
   $(table).find("tbody").append(str);
  }
 }
 window.onresize();
 return str;
};

/**
 * 遍历table的row，获取选中行的某个参数，返回数组
 */
function getSelectRow(table, parament) {
 var rows = $(table).find("tbody>tr>td>input:checked").parent().parent();
 if (!parament) {
  return rows;
 }
 var result = [];
 rows.each(function(index, ele) {
  result.push(ele.getAttribute(parament));
 });
 return result;
}

/*
 * 左侧查询面板
 */
// table checkbox 全选功能
$("table>thead>tr>th>input[type='checkbox']").each(function(index, ele) {
 ele.onclick = function() {
  var cellIndex = $(this).parent()[0].cellIndex;
  var checked = this.checked;
  var trs = $(this).parent().parent().parent().parent().find("tbody>tr"); // tbody下所有tr
  trs.each(function(index, ele) {
   $(ele).find("td:eq(" + cellIndex + ")").find("input[type='checkbox']").prop("checked", checked);
   // 变色
   if (checked) {
    $(ele).addClass("selected");
   } else {
    $(ele).removeClass("selected");
   }
  });
 };
});

$("#searchResultPanel table,#watchListPanel table").on("click", "tbody>tr>td>:checkbox", function(e) {
 // 改变选中行的背景色
 var box = $(this);
 if (box.prop("checked")) {
  box.parent().parent().addClass("selected");
 } else {
  box.parent().parent().removeClass("selected");
 }
 // 更新表头选中状态
 var table = $("#carList-tabPanel>.active table");
 table.find("thead>tr>th>:checkbox").prop("checked", table.find("tbody>[class!=selected]").length == 0);
}).on("dblclick", "tbody>tr", function(e) {
 var box = $(this).find("td:first>input:checkbox");
 box.prop("checked", !box.prop("checked"));
 $(this).toggleClass("selected");
 // 更新表头选中状态
 var table = $("#carList-tabPanel>.active table");
 table.find("thead>tr>th>:checkbox").prop("checked", table.find("tbody>[class!=selected]").length == 0);
 // 查询最后位置
 GetLastPosition(this.getAttribute("call_letter"), this.getAttribute("plate_no"));
});

/* 弹出右键菜单 */
$("#searchResultPanel table").on("contextmenu", "tbody>tr", function(e) {
 // 历史查询一次只能查一台车
 contextmenu_target.total = 0;
 contextmenu_target.numberPlate = this.getAttribute("plate_no");
 contextmenu_target.callLetter = this.getAttribute("call_letter");
 // 弹出右键菜单 start
 var _top = e.clientY + $(document).scrollTop();
 if (_top + $("_searchResultPanel").scrollTop() + 240 > $(window).height()) {
  _top = $(window).height() - 240 - $("_searchResultPanel").scrollTop();
 }
 $("#contextmenu-searchResultPanel").offset({
  left : e.clientX,
  top : _top
 });
 $("#searchResultPanel-link").dropdown('toggle');

 var box = $(this).find("td:first>input:checkbox");
 if (box.prop("checked")) {
  return false;
 }
 box.prop("checked", true);
 box.parent().parent().addClass("selected");
 var table = $(this).parent().parent();
 table.find("thead>tr>th>:checkbox").prop("checked", table.find("tbody>[class!=selected]").length == 0);
 return false;
});
$("#watchListPanel table").on("contextmenu", "tbody>tr", function(e) {
 // 历史查询一次只能一台车
 contextmenu_target.total = 0;
 contextmenu_target.numberPlate = this.getAttribute("plate_no");
 contextmenu_target.callLetter = this.getAttribute("call_letter");
 var _top = e.clientY + $(document).scrollTop();
 if (_top + $("_searchResultPanel").scrollTop() + 240 > $(window).height()) {
  _top = $(window).height() - 240 - $("_searchResultPanel").scrollTop();
 }
 $("#contextmenu-watchListPanel").offset({
  left : e.clientX,
  top : _top
 });
 $("#watchListPanel-link").dropdown('toggle');
 var box = $(this).find("td:first>input:checkbox");
 if (box.prop("checked")) {
  return false;
 }
 box.prop("checked", true);
 box.parent().parent().addClass("selected");
 var table = $(this).parent().parent();
 table.find("thead>tr>th>:checkbox").prop("checked", table.find("tbody>[class!=selected]").length == 0);
 return false;
});
/* 弹出右键菜单 end */

/* 收缩指令信息 */
$("#_orderMsgPanel>.list-group").on("click", ".list-group-item>i", function(e) {
 $(this).siblings("ul").slideToggle();
 $(this).toggleClass("glyphicon-chevron-right").toggleClass("glyphicon-chevron-up");
});

/* 实时跟踪 */
$("#trailModal").on(
  "show.bs.modal",
  function() {
   var str = getSelectRow("#carList-tabPanel>.active table", "plate_no")
   if (str.length == 0) {
    Util.showMsg("请先勾选车辆");
    return false;
   }
   $("#trail-cars").text(str.join());

   // 开始实时跟踪
   var trailStartBtn = function() {
    $("#trail-start").removeClass("disabled");
    $("#trail-start").unbind("click").one(
      "click",
      function() {
       $("#trail-start").addClass("disabled");
       var trailTimes = $("#trail-times").val();
       if (trailTimes == "") {
        Util.showPopover("#trail-times", "请输入跟踪次数");
        trailStartBtn();
        return false;
       }
       if (!/^\d+$/.test(trailTimes)) {
        Util.showPopover("#trail-times", "请输入正整数");
        trailStartBtn();
        return false;
       }
       var trailInterval = $("#trail-interval").val();
       if (trailInterval == "") {
        Util.showPopover("#trail-interval", "请输入时间间隔");
        trailStartBtn();
        return false;
       }
       if (!/^\d+$/.test(trailInterval)) {
        Util.showPopover("#trail-interval", "请输入正整数");
        trailStartBtn();
        return false;
       }
       Trace(getSelectRow("#carList-tabPanel>.active table", "call_letter"), getSelectRow("#carList-tabPanel>.active table", "plate_no"),
         trailInterval, trailTimes);
      });
   };
   trailStartBtn();
   // 结束实时跟踪
   var trailStopBtn = function() {
    $("#trail-stop").removeClass("disabled");
    $("#trail-stop").unbind("click").one("click", function() {
     $("#trail-stop").addClass("disabled");
     // sendCommand({
     // commandId : "22"
     // }, trailStopBtn);
     StopTrace(getSelectRow("#carList-tabPanel>.active table", "call_letter"), getSelectRow("#carList-tabPanel>.active table", "plate_no"));
    });
   };
   trailStopBtn();
  });

/* 车台复位 */
$("#rebackModal").on("show.bs.modal", function() {
 var str = getSelectRow("#carList-tabPanel>.active table", "plate_no")
 if (str.length == 0) {
  Util.showMsg("请先勾选车辆");
  return false;
 }
 $("#reback-cars").text(str.join());
});

// 插件初始化

/* 历史回放 */
$("#historyModal").on("show.bs.modal", function() {
 if (!contextmenu_target.numberPlate) {
  var _target = $("#carList-tabPanel>.active table>tbody>tr>td>:checked:first").parent().parent();
  if (_target.length == 0) {
   Util.showMsg("请先选择车辆");
   return false;
  } else {
   contextmenu_target.total = 0;
   contextmenu_target.numberPlate = _target.attr("plate_no");
   contextmenu_target.callLetter = _target.attr("call_letter");
  }
 }
 if (!contextmenu_target.numberPlate) {
  Util.showMsg("请选择车辆");
  return false;
 }
 $("#history-car").text(contextmenu_target.numberPlate);
 var timeFormat = "yyyy-MM-dd HH:mm:ss";
 if ($('#play_history_start_time').val().length == 0) {
  $('#play_history_start_time').val(Util.formatDate(Util.getTodayStart(), timeFormat));
 }

 if ($('#play_history_end_time').val().length == 0) {
  var now = new Date();
  now.setHours(now.getHours() + 1);
  $('#play_history_end_time').val(Util.formatDate(now, timeFormat));
 }
});
$("#historyModal").on("hide.bs.modal", function() {
 contextmenu_target.numberPlate = false;
});
initCmd();
//initHistoryConsole();

// 查询按钮
$("#search-btn").click(function(e) {
 // check 输入
 var searchKey = $("#search-input").val();
 if (!searchKey || searchKey == "") {
  Util.showPopover("#search-input", "请输入要查询的车牌或呼号");
  return;
 }
 if (searchKey.length < 4 && /^\d+$/.test(searchKey)) {
  Util.showPopover("#search-input", "输入纯数字进行查询时请至少输入四位");
  return;
 }
 if (searchKey.length < 3) {
  Util.showPopover("#search-input", "请输入三个以上字符");
  return;
 }
 var callback = function(data) {
  if (data.no_session) {
   Util.showMsg("登录超时，请重新登录系统");
  }
  if (data.list.length > 10) {
   Util.showMsg("查询得到的数据过多，部分数据未显示，请细化查询条件。");
   data.list.length = 10;
  } else if (data.list == 0) {
   Util.showMsg("未找到记录");
  }
  ;
  fixTable($("#searchResultPanel table"), true, [ "<input type='checkbox'/>", "plate_no", "call_letter" ], data);
  // 切换到查询结果面板
  $("#search-panel").tab('show');
 };
 $.ajax({
  type : "post",
  url : "/webgisList",
  contentType : 'application/json',
  data : JSON.stringify({
   parameter : searchKey
  }),
  // traditional : true,
  async : true,
  dataType : "json",
  success : callback,
  error : function() {
   Util.showMsg("未找到记录");
  }
 });
});

/* 按回车自动搜索 */
$("#search-input").keypress(function(e) {
 if (e.keyCode == 13) {
  $("#search-btn").click();
 }
});

// 选择城市
$("#sel-city").on("hide.bs.dropdown", function(e) {
 // 当 标记为 true 时不隐藏选择菜单
 if (selCityHideFlag) {
  selCityHideFlag = null;
  return false;
 }
 return true;
});

// 改变城市
$("a[mapvalue]").click(function() {
 var ele = $(this);
 $("#sel-city-link").text(ele.text());
 $("#sel-city-link").attr("mapvalue", ele.attr("mapvalue"));
});

// 点击下拉的导航时不隐藏
$("a[notoggle],#map_toolbar-city-sel-by-province,#map_toolbar-city-sel-by-city").click(function() {
 selCityHideFlag = true;
});

$("#map_toolbar-city-sel-by-province").addClass("selected");
/* 切换城市、省份选择方式 */
$("#map_toolbar-city-sel-by-province").click(function() {
 $("#map_toolbar-city-sel-by-province").addClass("selected");
 ;
 $("#map_toolbar-city-sel-by-city").removeClass("selected");
 $("#sel-province-div").show();
 $("#sel-city-div").hide();
});
$("#map_toolbar-city-sel-by-city").click(function() {
 $("#map_toolbar-city-sel-by-province").removeClass("selected");
 $("#map_toolbar-city-sel-by-city").addClass("selected");
 ;
 $("#sel-province-div").hide();
 $("#sel-city-div").show();
});

var ps = location.search.substr(1).split("&");
if (ps.length > 1) {
 var vi = {};
 for (var int = 0; int < ps.length; int++) {
  var p = ps[int].split("=");
  vi[p[0]] = p[1];
 }
 vi.plate_no = unescape(vi.plate_no);
 addToWatchList(vi);
}

// 初始化监控列表
var watchListCookie = Util.getCookie("watch_list");
if (watchListCookie) {
 var watchList = watchListCookie.split("||");
 var watch_List = [];
 for (var i = 0; i < watchList.length; i++) {
  var call_letter = watchList[i++]
  var plate_no = watchList[i]
  if (call_letter != "" && plate_no != "") {
   watch_List.push({
    call_letter : call_letter,
    plate_no : plate_no
   })
  }
 }
 addToWatchList({
  list : watch_List
 });
};
Util.showMsg("如需监控车辆，请将车辆加入监控列表。默认为不监控车辆", "功能Tips");

// 滑块
var slideHelper = {
 target : null,
 keyDown : null,
 startX : null,
 startY : null,
 disableSelect : function() {
  document.body.unselectable = "on";
  document.body.style = "-moz-user-select: none; -webkit-user-select: none;";
  document.body.onselectstart = function() {
   return false;
  };
 },
 init : function() {
  $(".left-panel,.right-panel").css("cursor", "auto");
  slideHelper.keyDown = null;
  slideHelper.startX = null;
  slideHelper.startY = null;
  slideHelper.target = null;
  document.body.unselectable = "";
  document.body.style = "-moz-user-select: ; -webkit-user-select: ;";
  document.body.onselectstart = null;
  $(document).unbind("mousemove");
 }
};
// 左滑块
$("#left_panel").mousemove(function(e) {
 if (slideHelper.keyDown) {
  return;
 } // 正在滑动
 if ($("#left_panel").offset().left + $("#left_panel").width() - e.clientX < 11) {
  if (slideHelper.target != "#left_panel") {
   slideHelper.target = "#left_panel";
   $(".left-panel,.right-panel").css("cursor", "col-resize");
   slideHelper.disableSelect();
   $(document).unbind("mousemove").mousemove(function(e) {
    if (slideHelper.keyDown) {
     var _x = $("#left_panel").width() + e.clientX - slideHelper.startX;
     if (_x < 290) {
      return;
     } else if (_x > 550) {
      return;
     }
     slideHelper.startX = e.clientX;
     $("#left_panel").width(_x);
     $(".right-panel").css("margin-left", _x + 5 + "px");
    }
    ;
   });
  }
 } else {
  if (!slideHelper.keyDown) {
   slideHelper.init();
  }
 }
}).mouseout(function(e) {
 if (!slideHelper.keyDown) {
  slideHelper.init();
 }
});
// 底部滑块
$("#foot_panel").mousemove(function(e) {
 if (slideHelper.keyDown) {
  return;
 } // 正在滑动
 if (e.clientY - $("#foot_panel").offset().top < 11) {
  if (slideHelper.target != "#foot_panel") {
   slideHelper.target = "#foot_panel";
   $(".left-panel,.right-panel").css("cursor", "row-resize");
   slideHelper.disableSelect();
   $(document).unbind("mousemove").mousemove(function(e) {
    if (slideHelper.keyDown) {
     var _y = $("#map").height() + e.clientY - slideHelper.startY;
     var _y_ = $("#_GPSMsgPanel").height() - e.clientY + slideHelper.startY;
     if (_y < 370) {
      return;
     } else if (_y_ < 145) {
      return;
     }
     slideHelper.startY = e.clientY;
     $("#map").height(_y);
     $("#_GPSMsgPanel").height(_y_);
    }
    ;
   });
  }
 } else {
  if (!slideHelper.keyDown) {
   slideHelper.init();
  }
 }
}).mouseout(function(e) {
 if (!slideHelper.keyDown) {
  slideHelper.init();
 }
});
$(document).mousedown(function(e) {
 slideHelper.keyDown = true;
 slideHelper.startX = e.clientX;
 slideHelper.startY = e.clientY;
}).mouseup(function(e) {
 slideHelper.init();
});

// 双击时不选择文本
var dblclickFlag = 0;
$(window).mouseup(function() {
 dblclickFlag = new Date().getTime();
 document.body.unselectable = "";
 document.body.style = "-moz-user-select: ; -webkit-user-select: ;";
 document.body.onselectstart = null;
}).mousedown(function() {
 if (new Date().getTime() - dblclickFlag < 400) {
  document.body.unselectable = "on";
  document.body.style = "-moz-user-select: none; -webkit-user-select: none;";
  document.body.onselectstart = function() {
   return false;
  };
 }
 ;
});