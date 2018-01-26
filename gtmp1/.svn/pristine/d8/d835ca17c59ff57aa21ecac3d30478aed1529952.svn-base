// 请将有用的按使用顺序向前放，没有用的暂时放后面，确认不会用到的直接删除
var maplet;
var userlon = 110.17064; // 用户初始经度：如深圳113.93721,22.53727
var userlat = 22.56116;// 用户初始纬度
var zoomlevel = 3;// 用户地图初始层级
var monitorlist = [];// 需要定时刷新的设备
var refreshRunGridTimeoutId=null;// 运营监管页面从memcache刷新gps、工况、指令回应数据线程Id
var refreshRunGpsWorkOnLineTimeoutId=null;// 运营监管页面刷新gps、工况，在线、不在线
var refreshRunTrackTimeOutId=null;// 运营监管页面刷新跟踪信息
var dateFormat = 'yyyy-MM-dd';
var timeFormat = 'yyyy-MM-dd hh:mm:ss';
var tipMsgDilag = "提示";

var trackInfo=[];// 跟踪的信息

// 工况单位
var unitsSymbol=[];
unitsSymbol['temperature']="℃";// 温度单位
unitsSymbol['pressure']="MPa";// 压力
unitsSymbol['percent']="%";// 油位
unitsSymbol['rotateSpeed']="r/min";// 转速
unitsSymbol['voltagePressure']="V";// 电压
unitsSymbol['pressure2']="MPa";// 挖掘机前泵主压压力
unitsSymbol['current']="mA";// 电流
unitsSymbol['degree']="度";// 度
unitsSymbol['speed']="千米/小时";// 速度
function getFangXiang(icourse) {
	try {
		var scourse = "";
		if ((icourse == 0) || (icourse == 360)) {
			scourse = "向北";
		} else if ((icourse > 0) && (icourse <= 30)) {
			scourse = "北偏东";
		} else if ((icourse > 30) && (icourse <= 60)) {
			scourse = "东北";
		} else if ((icourse > 60) && (icourse < 90)) {
			scourse = "东偏北";
		} else if (icourse == 90) {
			scourse = "向东";
		} else if ((icourse > 90) && (icourse <= 120)) {
			scourse = "东偏南";
		} else if ((icourse > 120) && (icourse <= 150)) {
			scourse = "东南";
		} else if ((icourse > 150) && (icourse < 180)) {
			scourse = "南偏东";
		} else if (icourse == 180) {
			scourse = "向南";
		} else if ((icourse > 180) && (icourse <= 210)) {
			scourse = "南偏西";
		} else if ((icourse > 210) && (icourse <= 240)) {
			scourse = "西南";
		} else if ((icourse > 240) && (icourse < 270)) {
			scourse = "西偏南";
		} else if (icourse == 270) {
			scourse = "向西";
		} else if ((icourse > 270) && (icourse <= 300)) {
			scourse = "西偏北";
		} else if ((icourse > 300) && (icourse <= 330)) {
			scourse = "西北";
		} else if ((icourse > 330) && (icourse < 360)) {
			scourse = "北偏西";
		}
		return scourse;
	} catch (ex) {
		alert(ex.number + "\n\n" + ex.description);
	}
}

var mouseOut = function(obj) {
	obj.children[1].style.display = 'none';
};

var mouseOver = function(obj) {
	obj.children[1].style.display = 'block';
};

/*
 * 重写js的数组方法 目的是为了根据元素值删除指定元素
 */

Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) {
			return i;
		}
	}
	return -1;
};
Array.prototype.removevalue = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
// 数组判断是否已存在
Array.prototype.contains = function(value) {
	var chr = String.fromCharCode(5);
	return (chr + this.join(chr) + chr).indexOf(chr + value + chr) == -1
			? false
			: true;
};

function formatDateText(date) {
	return date.formatDate("yyyy-MM-dd");
}
function formatDateTimeText(date) {
	return date.formatDate("yyyy-MM-dd HH:mm:ss");
}

Date.prototype.formatDate = function(format) {
	var date = this;
	var o = {
		"M+" : date.getMonth() + 1, // month
		"d+" : date.getDate(), // day
		"h+" : date.getHours(), // hour
		"m+" : date.getMinutes(), // minute
		"s+" : date.getSeconds(), // second
		"q+" : Math.floor((date.getMonth() + 3) / 3), // quarter
		"S" : date.getMilliseconds()
			// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4
				- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
					? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};

function parseDate(dateStr) {
	if (dateStr == "") {
		return new Date();
	}
	var regexDT = null;
	if (dateStr.indexOf("-") > 0)
		regexDT = /(\d{4})-?(\d{2})?-?(\d{2})?\s?(\d{2})?:?(\d{2})?:?(\d{2})?/g;
	else
		regexDT = /(\d{4}).+(\d{1,2})\D+(\d{1,2}).+\s+(\d{1,2}):(\d{1,2}):(\d{1,2})/g;
	var matchs = regexDT.exec(dateStr);
	var date = new Array();
	for (var i = 1; i < matchs.length; i++) {
		if (matchs[i] != undefined) {
			date[i] = matchs[i];
		} else {
			if (i <= 3) {
				date[i] = '01';
			} else {
				date[i] = '00';
			}
		}
	}
	return new Date(date[1], date[2] - 1, date[3], date[4], date[5], date[6]);
}

// 判断父窗口是否关闭
function isCloseOpener() {
	var isWindowClose = true;
	try {
		if (opener != null && !opener.closed) {
			isWindowClose = false;
		} else {
			isWindowClose = true;
		}
	} catch (e) {
		isWindowClose = true;
	}
	return isWindowClose;
}

// 获取地址栏的参数数组
function getUrlParams() {
	var search = window.location.search;
	// 写入数据字典
	var tmparray = search.substr(1, search.length).split("&");
	var paramsArray = new Array;
	if (tmparray != null) {
		for (var i = 0; i < tmparray.length; i++) {
			var reg = /[=|^==]/; // 用=进行拆分，但不包括==
			var set1 = tmparray[i].replace(reg, '&');
			var tmpStr2 = set1.split('&');
			var array = new Array;
			array[tmpStr2[0]] = unescape(tmpStr2[1]);
			paramsArray.push(array);
		}
	}
	// 将参数数组进行返回
	return paramsArray;
}

// 根据参数名称获取参数值
function getParamValue(name) {
	var paramsArray = getUrlParams();
	if (paramsArray != null) {
		for (var i = 0; i < paramsArray.length; i++) {
			for (var j in paramsArray[i]) {
				if (j == name) {
					return paramsArray[i][j];
				}
			}
		}
	}
	return null;
}
// 获得前一天的时间
function getYesterday() {
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	return yesterday.formatDate(timeFormat);;
}
// 获得前一天零点的时间
function getYesterdayZero() {
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	yesterday.setHours(0);
	yesterday.setMinutes(0);
	yesterday.setSeconds(0);
	return yesterday.formatDate(timeFormat);;
}
// 获得今天零点的时间
function getTodayZero() {
	var yesterday = new Date();
	yesterday.setHours(0);
	yesterday.setMinutes(0);
	yesterday.setSeconds(0);
	return yesterday.formatDate(timeFormat);;
}
// 获得前一天的日期
function getYesterdayDate() {
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	return yesterday.formatDate(dateFormat);;
}

// JS操作cookies方法
// js写cookies
function setCookie(name, value) {
	var strsec = getsec("d30");// 默认30天
	var exp = new Date();
	exp.setTime(exp.getTime() + strsec * 1);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}

// js读取cookies
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return (arr[2]);
	else
		return null;
}

// js删除cookies
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
// 使用示例
// setCookie("name","hayden");
// alert(getCookie("name"));
// 如果需要设定自定义过期时间
// 那么把上面的setCookie 函数换成下面两个函数就ok;
function setCookieInTime(name, value, time) {
	var strsec = getsec(time);
	var exp = new Date();
	exp.setTime(exp.getTime() + strsec * 1);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}

function getsec(str) {
	var str1 = str.substring(1, str.length) * 1;
	var str2 = str.substring(0, 1);
	if (str2 == "s") {
		return str1 * 1000;
	} else if (str2 == "h") {
		return str1 * 60 * 60 * 1000;
	} else if (str2 == "d") {
		return str1 * 24 * 60 * 60 * 1000;
	}
}

// 计算天数差
  function getDayNumber(date1,date2){
  // 默认格式为"2003-03-03",根据自己需要改格式和方法
  	var date1split=date1.split('-');
  	var date2split=date2.split('-');
 var year1 =  date1split[0];
  var year2 = date2split[0]; 
  var month1 =date1split[1];
  var month2 =date2split[1];
  
  var day1 = date1split[2];
  var day2 = date2split[2];
  
  var temp1 = year1+"/"+month1+"/"+day1;
  var temp2 = year2+"/"+month2+"/"+day2;
  
  var dateaa= new Date(temp1); 
  var datebb = new Date(temp2); 
  var date = datebb.getTime() - dateaa.getTime(); 
  var time = Math.floor(date / (1000 * 60 * 60 * 24));
  // alert(time);
 return time;
 }
 
 // 计算月份差
  function getMonthNumber(date1,date2){
  // 默认格式为"2003-03-03",根据自己需要改格式和方法
  var year1 =  date1.split('-')[0];
  var year2 =  date2.split('-')[0]; 
  var month1 =date1.split('-')[1];
  var month2 = date2.split('-')[1];
  
  var len=(year2-year1)*12+(month2-month1);
  return len;

 }
  // 工况单位拼接
// 温度
  function fmtTemperature(val){
  	// if(val!=''&&val!=null){
	  if(val == 0 || val=='' ||val==null){
		  return "--";
	  }else{
  		  return val+unitsSymbol['temperature'];
  	  }
  	// }
  }
//发动机冷却液温度
  function fmtTemperature1(val){
  	// if(val!=''&&val!=null){
	  if( val=='' ||val==null){
		  return "--";
	  }else{
  		  return val+unitsSymbol['temperature'];
  	  }
  	// }
  }

// 压力
  function fmtPressure(val){
  	// if(val!=''&&val!=null){
	  if(val == 0  || val=='' ||val==null){
		  return "--";
	  }else{
  		return val+unitsSymbol['pressure'];
  	}
  	// }
  }
// 油位
  function fmtPercent(val){
  	// if(val!=''&&val!=null){
	  if(val == 0  || val=='' ||val==null){
		  return "--";
	  }else{
  		return val+unitsSymbol['percent'];
	  }
  		// }}
  }
// 转速
  function fmtRotateSpeed(val){
  	// if(val!=''&&val!=null){
	  if(val == 0  || val=='' ||val==null){
		  return "--";
	  }else{
  		return val+unitsSymbol['rotateSpeed'];
	  }
  		// }
  }
// 电压
  function fmtVoltagePressure(val){
  	// if(val!=''&&val!=null){
	  if(val == 0  || val=='' ||val==null){
		  return "--";
	  }else{
  		return val+unitsSymbol['voltagePressure'];
	  }
  		// }
  }
  // 挖掘机前泵主压压力
  function fmtPressure2(val){
	  	// if(val!=''&&val!=null){
	  if(val == 0 || val == 51 || val==51.0 || val=='' ||val==null){
		  return "--";
	  }else{
	  		return val+unitsSymbol['pressure2'];
	  
	  }// }
	  } 
 
  function fmtPressure_2(val){
	  	// if(val!=''&&val!=null){
	  if(val == 0 || val == 51 || val==51.0 || val=='' ||val==null){
		  return "--";
	  }else{
	  		return val+unitsSymbol['pressure2'];
	  
	  }// }
	  }
  function fmtPressure_03(val){
	  	// if(val!=''&&val!=null){
	  if(val == 0 || val == 7.65  || val=='' ||val==null){
		  return "--";
	  }else{
	  		return val+unitsSymbol['pressure2'];
	  
	  }// }
	  }
  
// 电流
  function fmtCurrent(val){
  	// if(val!=''&&val!=null){
	  if(val == 0 || val == 65535  || val=='' ||val==null){
		  return "--";
	  }else{
  		return val+unitsSymbol['current'];
	  }// }
  }
// 度
  function fmtDegree(val){
  	// if(val!=''&&val!=null){
	  if(val == 0  || val=='' ||val==null){
		  return "--";
	  }else{
  		return val+unitsSymbol['degree'];
	  }// }
  }
// 速度
  function fmtSpeed(val){
  	// if(val!=''&&val!=null){
	  if(val=='' ||val==null){
		  return"--";
	  }else{
  		return val+unitsSymbol['speed'];
	  }
  	// }
  }
  //故障码
  function fmtFaultCode(val){
	  if(val == '' || val == null|| val == '65535'){
		  return '--'
	  }else{
		  return val
	  }
  }
  function fmtVehicleStatus(val,row){
		if(val==1){
			return '测试组';
		}
		else if(val==2){
			return '已测组';
		}
		else if(val==3){
			return '销售组';
		}
		else if(val==8){
			return '法务组';
		}
		else if(val==9){
			return '停用组';
		}
		else
			return '';
	}
