var Util = {
 formatDate: function(date, formatStr){
  var fs = formatStr || "yyyy-MM-dd HH:mm:ss";
  var timeStr = fs;
  var fullYear = date.getFullYear();
  timeStr = timeStr.replace("yyyy", fullYear);

  var year = date.getFullYear().toString().substring(2);
  timeStr = timeStr.replace("yy", year);

  var month = date.getMonth() + 1;
  timeStr = timeStr.replace("MM", month < 10? "0" + month: month);

  var dd = date.getDate();
  timeStr = timeStr.replace("dd", dd < 10? "0" + dd: dd);

  var hours = date.getHours();
  timeStr = timeStr.replace("HH", hours < 10? "0" + hours: hours);

  var minutes = date.getMinutes();
  timeStr = timeStr.replace("mm", minutes < 10? "0" + minutes: minutes);

  var seconds = date.getSeconds();
  timeStr = timeStr.replace("ss", seconds < 10? "0" + seconds: seconds);

  return timeStr;
 },

 parseDate: function(dateStr, formatStr){
  if(!dateStr){
   return new Date();
  }

  var year = 1979;
  var month = 1;
  var date = 1;
  var hours = 8;
  var minutes = 0;
  var seconds = 0;
  var yyyy = formatStr.indexOf("yyyy");
  if(yyyy != -1){
   year = parseInt(dateStr.substring(yyyy, yyyy + 4));
  }else{
   var yy = formatStr.indexOf("yy");
   year = 2000 + parseInt(dateStr.substring(yy, yy + 2));
  }

  var MM = formatStr.indexOf("MM");
  if(MM != -1){
   month = parseInt(dateStr.substring(MM, MM + 2));
  }

  var dd = formatStr.indexOf("dd");
  if(dd != -1){
   date = parseInt(dateStr.substring(dd, dd + 2));
  }

  var HH = formatStr.indexOf("HH");
  if(HH != -1){
   hours = parseInt(dateStr.substring(HH, HH + 2));
  }

  var mm = formatStr.indexOf("mm");
  if(mm != -1){
   minutes = parseInt(dateStr.substring(mm, mm + 2));
  }

  var ss = formatStr.indexOf("ss");
  if(ss != -1){
   seconds = parseInt(dateStr.substring(ss, ss + 2));
  }

  return new Date(year, month - 1, date, hours, minutes, seconds);
 },

 getTodayStart: function(){
  var d = new Date();
  d.setHours(0);
  d.setMinutes(0);
  d.setSeconds(0);
  d.setMilliseconds(0);
  return d;
 },

 getLastDay: function(){
  var d = new Date();
  d.setHours(0);
  d.setMinutes(0);
  d.setSeconds(0);
  d.setMilliseconds(0);
  return new Date(d.getTime() - 86400000);
 },

 setCookie: function(key, value, expDate){
  var str = key + "=" + escape(value);
  if(expDate){
   str += "; expires=" + expDate.toGMTString();
  }

  document.cookie = str;
 },

 getCookie: function(key){
  var aCookie = document.cookie.split("; ");
  for (var i=0; i < aCookie.length; i++){
   var aCrumb = aCookie[i].split("=");
   if (key == aCrumb[0])
    return unescape(aCrumb[1]);
  }

  return null;
 },

 deleteCookie: function(key){
  document.cookie = key + "=v; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
 },

/**
* jquery在右下角弹出提示信息
*/
showMsg: function(content, title) {
 if ($("#msg-panel").length == 0) {
  $("body").append('<div class="panel panel-default" id="msg-panel" style="position:fixed;bottom:0;right:10px;width:300px;display:none;z-index:9999"><div class="panel-heading"><span></span> <button type="button" class="close closeit" aria-hidden="true" onclick="$(&quot;#msg-panel&quot;).unbind(),$(&quot;#msg-panel&quot;).stop(!0).fadeOut(100)">&times;</button></div><div class="panel-body"></div></div>');
 }
 if (!content) {
  return;
 }
 window.focus();
 if (title) {
  $("#msg-panel>.panel-heading>span").text(title);
 } else {
  $("#msg-panel>.panel-heading>span").text("提示");
 }
 $("#msg-panel>.panel-body").text(content);
 $("#msg-panel").stop(true).hide().fadeIn().fadeTo(3000, 1).fadeOut(2000);
 $("#msg-panel").unbind().mouseover(function() {
  $("#msg-panel").stop(true).fadeTo(100, 1);
 }).mouseout(function() {
  $("#msg-panel").fadeTo(3000, 1).fadeOut(2000);
 });
},

/**
* jquery在指定元素上方弹出提示信息
*/
showPopover: function(selector, content, placement) {
 if ($("#popover-panel").length == 0) {
  $("body").append('<div class="popover" id="popover-panel" style="position:absolute;z-index:9999"><div class="arrow" style="left:50%"></div><div class="popover-content nobreak"></div></div>');
 }
// placement = "top";
placement = placement || "top";
$("#popover-panel>.popover-content").html(content);
$("#popover-panel").stop(true).hide().removeClass("top").addClass(placement).css("top", $(selector).offset().top - $(selector).outerHeight() - 10)
.css("left", $(selector).offset().left).fadeIn().fadeTo(3000, 1).fadeOut(2000);
$(selector).focus();
},

/**
* 显示下拉列表
* selector,lis,show,clickFun
*/
showList: function(selector,lis,show,clickFun){
 var pl = $("#popover_list");
 if (pl.length == 0) {
  pl = $("<div tabindex='-1' class='list-group' id='popover_list' style='position: absolute;display: none;z-index: 9;min-width: 142px;'></div>");
  $("body").append(pl);
  pl.on("click",">.list-group-item",function(e){
   pl.find(".active").removeClass("active");
   $(this).addClass("active");
   pl.stop(true).fadeOut();
  }).blur(function(e){
   pl.fadeOut();
  }).keypress(function(e) {
   if (e.keyCode != 13) {return;}
   pl.find(".active").click();
  }).keyup(function(e){
   var _current = pl.find(".active");
   var _new;
   if (e.keyCode == 38) {
    _new = _current.prev();
   } else if (e.keyCode == 40) {
    _new = _current.next();
   }
   if (!_new || _new.length == 0) {return;}
   _new.addClass("active");
   _current.removeClass("active");
  });
 }
 pl.empty().stop(true).fadeIn().offset({
  top:$(selector).offset().top + $(selector).outerHeight(),
  left:$(selector).offset().left
 });
 for (var i = 0; i < lis.length; i++) {
  var item = $("<a href='#' class='list-group-item'></a>");
  if (i==0) {item.addClass("active");}
  item.attr("data",i);
  item.text(lis[i][show]);
  item.click(function(){
   var p = lis[this.getAttribute("data")];
   $(selector).val(p[show]);
   clickFun(p);
   $(selector).focus();
  });
  pl.append(item);
 }
 pl.focus();
},

/**
* 设置指定元素在双击时不会选中文本内容
*/
noSelecterOnDblclick: function(element){
 element.dblclickFlag = 0;
 $(element).mouseup(function(){
  element.dblclickFlag = new Date().getTime();
  element.unselectable = "";
  element.style = "-moz-user-select: ; -webkit-user-select: ;";
  element.onselectstart = null;
 }).mousedown(function(){
  if (new Date().getTime() - element.dblclickFlag < 400) {
   element.unselectable = "on";
   element.style = "-moz-user-select: none; -webkit-user-select: none;";
   element.onselectstart = function() {
    return false;
   };
  };
 });
},
showNotification: function(msg){
 if(window.webkitNotifications){
  if(webkitNotifications.checkPermission==0){
   var WebkitNotification = webkitNotifications.createNotification("http://s.952100.com/", "提示", msg);
   WebkitNotification.show();
  }else{
   webkitNotifications.requestPermission();
  }
 }else if (window.Notification){
  if (window.Notification){
   var notification = new Notification('提示',{body:msg});
  } else {
   Notification.requestPermission();
  }
 } else {
  Util.showMsg(msg);
  $("#msg-panel").stop(true).hide().fadeIn();
 }
}
};