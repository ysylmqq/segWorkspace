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
	}
};