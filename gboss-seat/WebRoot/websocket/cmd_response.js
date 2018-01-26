/*!
 * 
 * Copyright 2014 chinagps, Inc. zhangxz
 * 
 */
"use strict";
/*
 * 
 */
var gpsLength=30;

//记录日志
//alert("websocketevent.js");
var log = document.getElementById("log");
//保存处理流程过程中的呼号和警情序列号
var txtalarmcaller = document.getElementById("alarmcaller");
var txtalarmsn = document.getElementById("alarmsn");

var websocketUrl = "";//连接地址
//websocket通信错误事件
function onerror(cli, event) {
    log.value += "websocket出现错误:" + cli.id + ", " + event.type + ", " + event.target.url + "\n";
    $("#Communication_Center_mark").css("color","#ccc").attr("title","通信错误:"+event.target.url);//添加提示
    $("#alarmsSum").html("0");//警情总数归0
    //alert("websocket出现错误:" + cli.id + ", " + event.type + ", " + event.target.url);
    websocketUrl = event.target.url;
};

//websocket连接成功事件
function onopen(cli, event) {
    log.value += "websocket连接成功:" + cli.id + ", " + event.type + ", " + event.target.url + "\n";
    //AskAlarmList();//请求警情数
    updatealarmlistSum();//刷新警情数
    $("#Communication_Center_mark").attr("title","连接成功:"+event.target.url);//添加提示
    websocketUrl = event.target.url;
};

//websocket连接关闭事件
function onclose(cli, event) {
    log.value += "websocket断开连接:" + cli.id + ", " + event.type + ", " + event.target.url + "\n";
    $("#Communication_Center_mark").css("color","#ccc").attr("title","连接关闭:"+event.target.url);//添加提示
    $("#alarmsSum").html("0");//警情总数归0
    //alert("websocket断开连接:" + cli.id + ", " + event.type + ", " + event.target.url);
    websocketUrl = event.target.url;
};

//websocket客户端登录(连接成功后自动登录，不用人工登录) 事件
function onlogin(cli, retcode, retmsg) {
	log.value += "通信中心返回登录结果: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	if(retcode==0){//连接成功
		$("#Communication_Center_mark").css("color","#81c36a").attr("title","客户端登录:"+retmsg+"。 连接地址:"+websocketUrl);//添加提示
		AskAlarmList();//请求警情数
	} else{//登录失败
		$("#Communication_Center_mark").css("color","#ccc").attr("title","客户端登录失败信息:"+retmsg+"。 连接地址:"+websocketUrl+"retcode:"+retcode);//添加提示
		$("#alarmsSum").html("0");//警情总数归0
	}
	//AddMonitor();
};

//添加监控列表结果事件
function onaddmonitor(cli, retcode, retmsg, callLetters) {
	log.value += "通信中心返回添加监控列表结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", all callletter:\n";
	for(var i=0; i<callLetters.length; i++) {
		log.value += callLetters[i] + "\n";
	};
  
    var carID = $('#vehicle_id').val(); //车辆id
    var carNum = $('#b_carPhone').val(); //车载号码
    var gsm = $('#b_gsm').val(); //车牌号
	if(retcode){//error
		alert(retmsg);
	}else{//correct
        //前台
        var addHTML= '<p class="checkbox-inline" id="List_'+carID+'"><input type="checkbox" value=""><span><em>'+gsm+' </em><i class="glyphicon glyphicon-trash cursor" title="删除"></i><input type="hidden" value="'+carID+'"></span><input type="hidden" value="'+carNum+'"></p>';
        $(".car_recent .car_list").prepend(addHTML);
        $("#monitor_lists").tab("show");
	}
    localStorage.setItem("monitor_list",$(".car_list").html());
	
};

//取消监控列表结果事件
function onremovemonitor(cli, retcode, retmsg, callLetters) {
	log.value += "通信中心返回取消监控列表结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", remain callletter:\n";
	for(var i=0; i<callLetters.length; i++) {
		log.value += callLetters[i] + "\n";
	};

	if(retcode){//error
		alert(retmsg);
	}else{//correct
        $(".car_recent p:eq("+CIndex+")").remove();     
	};
	localStorage.setItem("monitor_list",$(".car_list").html());		
};


//指令发送结果

function onsendcommandsend(cli, sn, callLetter, cmdId, retcode, retmsg){
	var class_span = 'wait';
	if(retcode){//error
		$("#"+sn+" span").removeClass('wait');
		$("#"+sn+" span").addClass('error');
		$("#"+sn+">a").append('<p class="cmd_result">'+retmsg+'</p>');
	}else{//correct
		$("#"+sn+" span").removeClass('wait');
		$("#"+sn+" span").addClass('correct');
	}
    localStorage.setItem("send_list",$("#send_list").html());
}

//车台回应
function onsendcommand(cli, sn, callLetter, cmdId, retcode, retmsg, params, gpsInfo){
	var cmdtime = SEGUtil_WS.date_2_string(new Date, "hh:mm:ss"); 
	$('#'+sn).attr('role','branch').attr('class','tree-toggle').attr('data-toggle','branch').attr('data-value','Bootstrap_Tree');
	var class_span = 'wait';
	if(retcode){
		class_span = 'error';
        switch(retcode)
        {
	        case 6:
				retmsg = '车台回复为失败';
				$("#"+sn+" span").removeClass('wait');
				$("#"+sn+" span").addClass('correct');
	            break;
	        case 10:
				retmsg = '车台不在线';
				$("#"+sn+" span").removeClass('wait');
				$("#"+sn+" span").addClass('error');
	            break;
	        case 17:
				retmsg = '发送数据失败';
				$("#"+sn+" span").removeClass('wait');
				$("#"+sn+" span").addClass('correct');
	            break;
	        case 18:
				retmsg = '等待车台回应超时';
				$("#"+sn+" span").removeClass('wait');
				$("#"+sn+" span").addClass('correct');
	            break;
	        case 16:
				retmsg = '参数错误';
				$("#"+sn+" span").removeClass('wait');
				$("#"+sn+" span").addClass('correct');
	            break;
	        case -1:
				retmsg = '其他错误';
				$("#"+sn+" span").removeClass('wait');
				$("#"+sn+" span").addClass('correct');
	            break;          
        };  

	}else{
		class_span = 'correct';
		$("#"+sn+" span").removeClass('wait');
		$("#"+sn+" span").removeClass('error');
		$("#"+sn+">a .cmd_result").remove();
		$("#"+sn+" span").addClass('correct');
		retmsg = '车台已回应';
	}	
	//retparams = JSON.stringify(params);
	// alert(params);
	//$('body').append('<input type="hidden" id=ret"'+sn+'" value="'+retparams+'"/>');
	$('#'+sn).append('<ul class="branch in"><li><a href="#" role="leaf"><span class="'+class_span+'">'+cmdtime+'</span><em>'+retmsg+'</em></a></li></ul>');
	ondelivergpsWhole(gpsInfo);
	
	//查询通信参数
	var Rsn = $("#sn").val().substr(0,6);
	// if (Rsn=='0x0058') {
	// 	// params=[0,0,0,0,'U',1,1] //test
	// 	// alert(params);
	// 	// alert(params[0]+'+'+params[1]+'+'+params[2]+'+'+params[3]+'+'+params[4]+'+'+params[5]+'+'+params[6]);
	// 	$("#search_apn").val(params[0]);
 //        $("#search_ip").val(params[1]);
 //        $("#search_num01").val(params[2]);
 //        $("#search_num02").val(params[3]);
 //        switch(params[4])
 //        {
 //            case "T":
 //              $("#checkT").attr("checked","checked");
 //              break;
 //            case "U":
 //              $("#checkU").attr("checked","checked");
 //              break;
 //        };
 //        switch(params[5])
 //        {
 //            case "1":
 //              $("#CheckN").attr("checked","checked");
 //              break;
 //            // case "2":
 //            //   $("#CheckS").attr("checked","checked");
 //            //   break;
 //            case "3":
 //              $("#CheckB").attr("checked","checked");
 //              break;
 //        };
 //        $("#search_space").val(params[6]);
 //        $("#cmd_p88").modal('show');    //弹窗显示
	// };

    switch(Rsn)
    {
        case "0x0058":
        	$("#search_apn,#search_ip,#search_num01,#search_num02,#search_space").val(''); // 清空input
			$("#search_apn").val(params[0]);
	        $("#search_ip").val(params[1]);
	        $("#search_num01").val(params[2]);
	        $("#search_num02").val(params[3]);
	        switch(params[4])
	        {
	            case "T":
				  $("#checkT,#checkU").removeAttr("checked");
	              $("#checkT").attr("checked","checked");
	              break;
	            case "U":
				  $("#checkT,#checkU").removeAttr("checked");
	              $("#checkU").attr("checked","checked");
	              break;
	        };
	        switch(params[5])
	        {
	            case "1":
				  $("#CheckN,#CheckB").removeAttr("checked");
	              $("#CheckN").attr("checked","checked");
	              break;
	            case "3":
				  $("#CheckN,#CheckB").removeAttr("checked");
	              $("#CheckB").attr("checked","checked");
	              break;
	        };
	        $("#search_space").val(params[6]);
	        $("#cmd_p88").modal('show');    //弹窗显示
            break;
        case "0x0027": 			//查询呼叫中心
        	$("#callCenter_number").val(''); // 清空input
			$("#callCenter_number").val(params[0]);
	        $("#cmd_p1114").modal('show');    //弹窗显示
            break;
        case "0x0030": 			//查询短信业务中心号码
        	$("#downStream_number,#upStream_number").val(''); // 清空input
			$("#downStream_number").val(params[0]);
	        $("#upStream_number").val(params[1]);
	        $("#cmd_p1115").modal('show');    //弹窗显示
            break;
        case "0x0070": 			//查询定时上报参数
        	$("#report_space,#report_fre").val(''); // 清空input
			$("#report_space").val(params[0]);
	        $("#report_fre").val(params[1]);
	        $("#cmd_p1116").modal('show');    //弹窗显示
            break;
        case "0x0071": 			//查询ACC变化上报参数
			if(params[0]==0){
				$("#checkUn,#checkDo").removeAttr("checked");
				$("#checkUn").attr("checked","checked");
			}else{
				$("#checkUn,#checkDo").removeAttr("checked");
				$("#checkDo").attr("checked","checked");
			};
	        $("#cmd_p1117").modal('show');    //弹窗显示
            break;    
        case "0x0072": 			//查询休眼上报参数
			if(params[0]==0){
				$("#checkCheckUn,#checkCheckDo").removeAttr("checked");
				$("#checkCheckUn").attr("checked","checked");
			}else{
				$("#checkCheckUn,#checkCheckDo").removeAttr("checked");
				$("#checkCheckDo").attr("checked","checked");
			};
	        $("#cmd_p1118").modal('show');    //弹窗显示
            break;   
        case "0x0073": 			//查询关机上报参数
			if(params[0]==0){
				$("#checkSUn,#checkSDo").removeAttr("checked");
				$("#checkSUn").attr("checked","checked");
			}else{
				$("#checkSUn,#checkSDo").removeAttr("checked");
				$("#checkSDo").attr("checked","checked");
			};
	        $("#cmd_p1119").modal('show');    //弹窗显示
            break;    
        case "0x0074": 			//查询车身状态变化上报参数
			if(params[0]==0){
				$("#checkEUn,#checkEDo").removeAttr("checked");
				$("#checkEUn").attr("checked","checked");
			}else{
				$("#checkEUn,#checkEDo").removeAttr("checked");
				$("#checkEDo").attr("checked","checked");
			};
	        $("#cmd_p1120").modal('show');    //弹窗显示
            break;   
        case "0x00A3": 			//查询OBD故障
        	// alert('OBD');
			// for(i=0;i<params[0].length;i++)
			// {
			//   	var HTML= '<div class="row"><span class="col-xs-3 control-label"><input class="form-control" type="text" value="'+matchExplain(params[0][i].faultType)+'"></span><span class="col-xs-5"><input class="form-control" type="text" value="'+JoinCode(params[0][i].faultCode)+'"></span></div>';
			// 	$("#OBD_detail").append(HTML);
			// }
	        $("#cmd_p1121").modal('show');    //弹窗显示
            break;
        case "0x00A4": 			//查询呼入呼出限制
			if(params[0]==0){
				$("#LimitUn,#LimitDo").removeAttr("checked");
				$("#LimitUn").attr("checked","checked");
			}else{
				$("#LimitUn,#LimitDo").removeAttr("checked");
				$("#LimitDo").attr("checked","checked");
			}
			if(params[1]==65535){
				$("#LimitOutUn,#LimitOutDo").removeAttr("checked");
				$("#LimitOutDo").attr("checked","checked");
			}else if(params[1]==0){
				$("#LimitOutUn,#LimitOutDo").removeAttr("checked");
				$("#LimitOutUn").attr("checked","checked");
				$("#LimitOutTime").val('');
			}else{
				$("#LimitOutUn,#LimitOutDo").removeAttr("checked");
				$("#LimitOutUn").attr("checked","checked");
				$("#LimitOutTime").val(params[1]);
			}

	        $("#cmd_p1122").modal('show');    //弹窗显示
            break;     
        case "0x00A7": 			//查询故障/救援服务号码
	        $("#emergencyNumbers").val(params[0]);    //弹窗显示
	        $("#cmd_p1127").modal('show');    //弹窗显示
            break;    
        case "0x00AC": 			//查询故障是否上报
			if(params[0]==0){
				$("#reportProUn,#reportPro").removeAttr("checked");
				$("#reportProUn").attr("checked","checked");
			}else{
				$("#reportProUn,#reportPro").removeAttr("checked");
				$("#reportPro").attr("checked","checked");
			};
	        $("#cmd_p1130").modal('show');    //弹窗显示
            break;  
        case "0x00B2": 			//查询故障是否上报
	        $("#updateStatus,#currentVersion,#updateVersion,#updateCubage,#receiveCubage").val('');  //情空
	        switch(params[0])
	        {
	            case 32:
	              $("#updateStatus").val("升级完成");
	              break;
	            case 33:
	              $("#updateStatus").val("正确接收到升级指令");
	              break;
	            case 34:
	              $("#updateStatus").val("接收到升级指令，但是升级文件信息错误");
	              break;
	            case 35:
	              $("#updateStatus").val("正在接收升级文件");
	              break;
	            case 36:
	              $("#updateStatus").val("升级文件接收完成");
	              break;
	            case 37:
	              $("#updateStatus").val("接收的升级文件校验失败");
	              break;
	        }
	        $("#currentVersion").val(params[1]);
	        $("#updateVersion").val(params[2]);
	        $("#updateCubage").val(params[3]);
	        $("#receiveCubage").val(params[4]);
	        $("#cmd_p1131").modal('show');    //弹窗显示
            break;    
    }     
    localStorage.setItem("send_list",$("#send_list").html());
}

function matchExplain(n){
	switch(n)
	{
	case 1:
	  return 'ABS';
	  break;
	case 2:
	  return 'ESP';
	  break;
	case 3:
	  return 'SRS';
	  break;  
	case 4:
	  return 'EMS';
	  break;
	case 5:
	  return 'IMMO';
	  break;
	case 6:
	  return 'PEPS';
	  break;	        
	case 7:
	  return 'BCM';
	  break;
	case 8:
	  return 'TCU';
	  break;
	case 9:
	  return 'TPMS';
	  break;
	case 10:
	  return 'TBOX';
	  break;
	case 11:
	  return 'APM';
	  break;
	case 12:
	  return 'ICM';
	  break;
	}
}

function JoinCode(code){
	var TotalJoin='';
	for(var i=0; i<code.length; i++) {
		TotalJoin = code[i] + ',';
	}
	return TotalJoin;
}




//读最后位置信息
function ongetlastgps(cli, retcode, retmsg, gpses,alarmid, alarmname) {
	//log.value += "通信中心返回最后位置: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	for(var i=0; i<gpses.length; i++) {
		//var gpsTime = new Date(gpses[i].baseInfo.gpsTime.toNumber());
		//if (gpses[i].baseInfo.obdInfo)
		//	log.value += gpses[i].baseInfo.obdInfo.totalDistance + ", " + gpses[i].baseInfo.obdInfo.remainOil + ", " + gpses[i].baseInfo.obdInfo.remainPercentOil +"%\n";
		//if (gpses[i].referPosition != null) {
		//	log.value += gpses[i].referPosition.province + ", " + gpses[i].referPosition.city + ", " + gpses[i].referPosition.county + "\n";
		//}
		//在地图中显示并居中
		if(!gpses[i].baseInfo) return; //如果缺少gps信息则忽略
		var callLetter 	= gpses[i].callLetter;
		var numberPlate = LetterPlate($.trim(callLetter));
		var speed = typeof(gpses[i].baseInfo.speed)=='undefined' ? '' : gpses[i].baseInfo.speed;
		//var speed 		= gpses[i].baseInfo.speed;
		var gpsTime 	= new Date(parseInt(gpses[i].baseInfo.gpsTime));
		var gpsTimeF 	= DateTranslate(gpsTime);
		var lon 		= gpses[i].baseInfo.lng;
		var lat 		= gpses[i].baseInfo.lat;
		var course 		= gpses[i].baseInfo.course;	
		var cn_course   = SEGUtil.getCourseDesc(course);
		var stamp 		= SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
		var alarmidE;
		if (alarmid==0||alarmid==null) {alarmidE=0;}else{alarmidE=1;};
		var isAlarm 	= alarmidE;
		var status 		= gpses[i].baseInfo.status;	
		var cn_status   = SEGUtil.parseVehicleStatus(status);
	    for(i=0;i<status.length;i++)
	      {
	        if(status[i]==23) 
	        {
	            speed = 0;
	            break;  
	        };    
	    };
		var _lon = parseFloat(lon)/1000000;
		var _lat = parseFloat(lat)/1000000;
		var opts = {
			id: numberPlate,
			callLetter: callLetter,
			label: numberPlate + " " + gpsTimeF,
			numberPlate: numberPlate,
			lon: _lon,
			lat: _lat,
			speed: parseFloat(speed),
			course: parseInt(course),
			gpsTime: gpsTimeF,
			stamp: stamp,
			isAlarm: parseInt(isAlarm),
			status: status
		};

		
		var m = map.addOrUpdateVehicleMarkerById(opts);
		// if(!map.isPointInView(_lon, _lat)){
		map.setCenter(_lon, _lat);
		// }
        // map.centerAndZoom(lon, lat, 12);
		m.target.flicker();
		Code_city();
	}
}



//显示读最后信息错误
function ongetlasterror(cli, retcode, retmsg) {
	log.value += "*****通信中心返回取最后信息失败:(retcode=" + retcode  + ", retmsg=" + retmsg + "*****\n";
};

//读历史位置信息
function ongethistorygps(cli, retcode, retmsg, lastpage, gpses) {
	var newgpses = [];//筛选后的gps信息条目，过滤经纬度为0的点
	for(var i=0; i<gpses.length; i++) {
		var gps = gpses[i];
		var _lon = parseFloat(gps.baseInfo.lng)/1000000;//经度
		var _lat = parseFloat(gps.baseInfo.lat)/1000000;//纬度
		if(_lon!==0&&_lat!==0){
			newgpses.push(gpses[i]);//经纬度不为0则有效，加入新数组
		}
	}
	loadHistoryData(newgpses);//该函数在js/cmd.js
	// loadHistoryData(gpses); //该函数在js/cmd.js
	var total = parseInt($("#play_history_dlg_search_result").html().replace(/[^0-9]/ig,""));//之前有多少条
	total += newgpses.length;//加上这一页的条数
	// total += gpses.length;//加上这一页的条数
	if(!lastpage){//如果不是最后一页
		$("#play_history_dlg_search_result").html('共'+total+'条数据');
		GetHistoryNextPage();//取下一页
	}else{//如果是最后一页
		$("#play_history_dlg_play_btn").removeClass("disabled");
		$("#play_history_dlg_list_btn").removeClass("disabled");
		$("#play_history_dlg_search_result").html('共'+total+'条数据').css("color","#47a447").css("display","block");
	}
}

//读历史位置主要信息
function ongethistorysimplegps(cli, callLetter, retcode, retmsg, lastpage, gpses) {
	log.value += "通信中心返回历史轨迹主要信息:(" + gpses.length + ") callletter=" + callLetter + ", retcode=" + retcode  + ", retmsg=" + retmsg + ", lastpage=" + lastpage + "\n";
	for(var i=0; i<gpses.length; i++) {
		var gpsTime = new Date(gpses[i].gpsTime.toNumber());
		log.value += gpsTime.toString() + ", " + gpses[i].lng + ", " + gpses[i].lat + "\n";
	};
	// alert(log.value);
}
					
//显示读历史信息错误
function ongethistoryerror(cli, retcode, retmsg) {
	log.value += "*****通信中心返回读历史信息失败:(retcode=" + retcode  + ", retmsg=" + retmsg + "*****\n";
	// alert(log.value);
};

//结束读历史信息
function onstophistory(cli, retcode, retmsg){
	log.value += "通信中心返回结束读历史信息: retcode=" + retcode  + ", retmsg=" + retmsg + "\n";
	// alert(log.value);
};

//车辆上传实时位置信息
function ondelivergps(cli, gpsinfo, gatewayid, gatewaytype, alarmid, alarmname) {
	ondelivergpsWhole(gpsinfo);
}


//车辆上传GPS信息

var ps = [];  //画线
var last_polyline = null; //画线
var show = 1;

function ondelivergpsWhole(gpsinfo, alarmid, alarmname) {
	if(!gpsinfo.baseInfo) return; //如果缺少gps信息则忽略
	// console.log(JSON.stringify(gpsinfo.baseInfo));
	var callLetter 	= gpsinfo.callLetter;
	var numberPlate = LetterPlate($.trim(callLetter));
	var speed = typeof(gpsinfo.baseInfo.speed)=='undefined' ? '' : gpsinfo.baseInfo.speed/10;
	//var speed 		= gpsinfo.baseInfo.speed;
	var gpsTime 	= new Date(parseInt(gpsinfo.baseInfo.gpsTime));
	var gpsTimeF 	= DateTranslate(gpsTime);
	var lon 		= gpsinfo.baseInfo.lng;
	var lat 		= gpsinfo.baseInfo.lat;
	var course 		= gpsinfo.baseInfo.course;	
	var cn_course   = SEGUtil.getCourseDesc(course);
	var stamp 		= SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");
	var alarmidE;
	if (alarmid==0||alarmid==null) {alarmidE=0;}else{alarmidE=1;};
	var isAlarm 	= alarmidE;
	var status 		= gpsinfo.baseInfo.status;	
	var cn_status   = SEGUtil.parseVehicleStatus(status);

    for(i=0;i<status.length;i++)
      {
        if(status[i]==23) 
        {
            speed = 0;
            break;  
        };    
    };

	var loc 		= gpsinfo.baseInfo.loc;
	if (loc) {
		loc="已定位";
	}else{
		loc="正在定位";
	};
	var _lon = parseFloat(lon)/1000000;
	var _lat = parseFloat(lat)/1000000;
	var Tran_lon_D = Math.floor(_lon);	//度
	var Tran_lon_M = Math.floor((_lon-Math.floor(_lon))*60);	//分
	var Tran_lon_S = Math.floor(((_lon-Math.floor(_lon))*60-Math.floor((_lon-Math.floor(_lon))*60))*60);	//秒
	var Tran_lat_D = Math.floor(_lat);	//度
	var Tran_lat_M = Math.floor((_lat-Math.floor(_lat))*60);	//分
	var Tran_lat_S = Math.floor(((_lat-Math.floor(_lat))*60-Math.floor((_lat-Math.floor(_lat))*60))*60);	//秒	
	var opts = {
		id: numberPlate,
		callLetter: callLetter,
		label: numberPlate + " " + gpsTimeF,
		numberPlate: numberPlate,
		lon: _lon,
		lat: _lat,
		speed:parseFloat(speed),
		course:parseInt(course),
		gpsTime:gpsTimeF,
		stamp:stamp,
		isAlarm:parseInt(isAlarm),
		status:status
	};
	
	var m = map.addOrUpdateVehicleMarkerById(opts);
	// if(!map.isPointInView(_lon, _lat)){
	// map.setCenter(_lon, _lat);
	// }
	
	m.target.flicker();
	if ($("#gps_table>tbody tr").length>=gpsLength){
		$("#gps_table>tbody tr:last").remove();
	};
	GPS_judge(numberPlate);
	var html = '<tr><td>'+numberPlate+'</td><td>'+callLetter+'</td><td>'+speed+'</td><td>'+cn_status+'</td><td>'+gpsTimeF+'</td><td>'+stamp+'</td><td>'+loc+'</td><td>'+cn_course+'</td><td>'+Tran_lon_D+'°'+Tran_lon_M+'′'+Tran_lon_S+'″,'+Tran_lat_D+'°'+Tran_lat_M+'′'+Tran_lat_S+'″</td><td class="hide LonVal">'+_lon+','+_lat+'</td><td class="hide">'+_lon+'</td><td class="hide">'+_lat+'</td><td class="hide">'+course+'</td><td class="hide">'+stamp+'</td><td class="hide">'+isAlarm+'</td><td class="hide">'+status+'</td></tr>';
	if ($("#gps_table>tbody>tr>td").hasClass("dataTables_empty")){
		$("#gps_table>tbody>tr>td.dataTables_empty").parent().remove();
	};
	$("#gps_table>tbody").prepend(html);

	localStorage.setItem("GPS_list",$("#gps_table>tbody").html());	
	Code_city();

	//跟踪=点
	var history_list_point_icon = {
		url: "images/circle_18.png",
		width: 18,
		height: 18,
		left: 0,
		top: 0,
		anchorx: -9,
		anchory: -9
	};
	var m = map.newSimpleMarker({
		lon: _lon,
		lat: _lat,
		title: gpsTime,
		icon: history_list_point_icon,
		label: {
			text: show,
			// anchorx: ax,
			anchory: -8,
			style: {
				fontSize: "12px",
				cursor: "pointer",
				color: "#000000"
			}
		}
	});


	if ($('#dot_follow').attr('checked')=='checked'){
		map.addMarker(m);	
		show++;	
		return show;		
	};	    	

	if ($('#line_follow').attr('checked')=='checked'){

		//每次来一个点
		ps.push(new SEGPoint(_lon,_lat)); //本次的点放入ps
		if(ps.length>1){ //点够了，可以划线
		    if(last_polyline != null){ 
		        map.removeMarker(last_polyline);//清除之前的线
		    }
		    last_polyline = map.newPolyline(ps);
		    map.addMarker(last_polyline);
		}

	};	    	 
};


//经纬度转换成城市
function Code_city(){		
	var center = map.getCenter();
	var lon = center.lon;
	var lat = center.lat;

	var c = new Converter();
	var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
	var p2 = BaiduConverter.encrypt(p1.x, p1.y);
	var point = new BMap.Point(p2.x, p2.y);

	var geocoder = new BMap.Geocoder();
	geocoder.getLocation(point, function(result){
		// alert("province:" + result.addressComponents.province + ", city:" + result.addressComponents.city );
		$("#phone_list").click(function(){ 
			areaCode(result.addressComponents.province,result.addressComponents.city);
		});     
	});
}

//终端登退录事件
function ondeliverunitloginout(selfclient, callLetter, inorout, gatewayid){
	log.value += "车台（终端）登退录事件: " + callLetter;
	log.value += inorout == 0 ? "退录" : "登录";
	log.value += ", gatewayid=" + gatewayid + "\n";
};

/*******************************************************************************************************
 * 下面的和处警相关的事件
 */
//坐席设置忙闲，通信中心应答
function onsetalarmbusy(selfclient, retcode, retmsg, username, busy, handleseatname) {
	if (busy) {
		$("#alarmstatus").html("忙碌");
		$("#alarmopt").html("就绪");
		log.value += "处警置忙";
//		alert("通信中心返回 忙");
	} else {
		$("#alarmstatus").html("就绪");
		$("#alarmopt").html("忙碌");
		log.value += "处警置闲";
//		alert("通信中心返回 闲");
	}
	log.value += ": retcode=" + retcode  + ", retmsg=" + retmsg;
	if (handleseatname) {
		log.value += ", " + handleseatname;
	}
	log.value += "\n";
};

//通信中心分配警情
function onallotalarm(selfclient, seatname, callLetter, alarmsn, alarmTime, alarmid, alarmname, gpsinfo, alarmcount, append) {
	log.value += "通信中心分配警情: callletter=" + callLetter  + ", alarmsn=" + alarmsn + ", alarmname=" + alarmname + ", alarmcount=" + alarmcount + ", append=" + append + "\n";
	var gpsTime = DateTranslate(new Date(parseInt(gpsinfo.gpsTime)));//gps时间
	var alarmTimeF = DateTranslate(new Date(parseInt(alarmTime)));//接收时间,报警时间
				//new Date(parseInt(gpsinfo.gpsTime)).toLocaleString().replace(/[\/]/g, "-");//转成-间隔
	log.value += alarmTimeF + ", " + gpsinfo.lng + ", " + gpsinfo.lat + "\n";

	var src_course = gpsinfo.course;
	var course   = SEGUtil.getCourseDesc(gpsinfo.course);//方向
	var src_status = gpsinfo.status;
	var status   = SEGUtil.parseVehicleStatus(gpsinfo.status);//状态

	var loc = gpsinfo.loc;
	if (loc) {
		loc="已定位";
	}else{
		loc="正在定位";
	};
	if ($("#example4>tbody>tr>td").hasClass("dataTables_empty")){
		$("#example4>tbody>tr>td.dataTables_empty").parent().remove();
	};
	if(Alarm_judge(callLetter,alarmid)==1){//列表是否已含本条警情(呼号同、警情id同)
		return;
	}
	//alert("通信中心返回的gps信息：报警时间："+alarmTimeF+"gps时间："+gpsTime+"是否定位："+gpsinfo.loc+"经度："+gpsinfo.lng+"纬度："+gpsinfo.lat+"速度："+gpsinfo.speed+"方向："+gpsinfo.course+"状态："+gpsinfo.status);
	/****日志打印分隔***/
	search_pla(alarmname,callLetter,alarmsn,gpsTime,alarmTimeF,gpsinfo.lng,gpsinfo.lat,0,alarmid,gpsinfo.speed/10,course,status,loc,src_course,src_status);//查询车牌后显示基本资料(速度、方向、状态)
	addAlarmCount();//未处警数+1
};

//通信中心应答挂警
function onpausealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "通信中心返回挂警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += "caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
//	alert("通信中心应答挂警")
	/****应答后的操作***/
	var alarmTabTr = $("#example12 tbody tr") //警单内表
	var numberPlate = $("#alarmlistcaller").html();//车牌
	var alarmtime = alarmTabTr.children("td").eq(0).html();//报警时间

	var metter = alarmTabTr.children("td").eq(1).html();//警情描述 事件名
	var lon = alarmTabTr.children("td").eq(3).html();//经度
	var lat = alarmTabTr.children("td").eq(4).html();//纬度
	var v_id = alarmTabTr.children("td").eq(5).html();//获取车辆id
	var as_id = txtalarmid.value;//获取页面中警情状态id
	var speed = alarmTabTr.children("td").eq(6).html();//速度
	var course = alarmTabTr.children("td").eq(7).html();//方向
	var status = alarmTabTr.children("td").eq(8).html();//车辆状态

	$("#example12 tbody tr").remove(); //警单内表格清空
	var row = '<tr><td>'+numberPlate+'</td><td>'+metter+'</td><td>'+status+'</td><td>'+txtalarmcaller.value+'</td><td>'+alarmtime+'</td><td>'+speed+'</td><td>'+course+'</td><td class="hide">'+v_id+'</td><td class="hide">'+txtalarmsn.value+'</td><td class="hide">'+as_id+'</td><td class="hide">'+lon+'</td><td class="hide">'+lat+'</td></tr>';

	removeCar($("#example4 tbody tr"));//原表格移除车辆
	removeCar($("#example5 tbody tr"));//原表格移除车辆（挂警的拾回再次点击挂警）
	if ($("#example5>tbody>tr>td").hasClass("dataTables_empty")){
		$("#example5>tbody>tr>td.dataTables_empty").parent().remove();
	};

	$("#example5 tbody").prepend(row);//添加挂警表格
	
	addhangAlarmCount();//挂警数+1
	alarmend();//警灯灭
	alarmregend();//拾回警灯灭
	isAlarm = false;//恢复未处警
	$('#hangAlarm').tab('show');//显示挂警列表
	$('#alarming').tab('show');//报警栏显示
	AskSeatList();//申请座席列表（转警用）

	if($("#autoSetfree").attr("checked")=="checked"){//自动置闲
        setTimeout(SeatIdle,2000);//2秒后自动置闲
        //setTimeout(SeatIdle,2000);//2秒后处警置闲alert($("#autoSetfree").attr("checked")=="checked")
    }
	
	removeAlarmopt();//清空车牌、核实报警选项、简报
//	$("#transfer_a").addClass("disabled setgrey");//关闭转警快捷下拉菜单
	/****数据库操作在这里****/
	save_hangalarm();//挂警更新数据库
};

//通信中心应答取消挂警
function oncancelpausealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "通信中心返回取消挂警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
	/****日志打印分隔****/
	isAlarm = true;//处警标识
	$('#alarmlist_title').tab('show');//显示警单
	DechangAlarmCount();//挂警数-1
	AskSeatList();//申请座席列表（转警用）
	Update_alarmhistroy();//更新警情历史记录
	/****数据库操作在这里****/
	save_pickalarm();//取消挂警更新数据库
};

//通信中心应答处警结果
function onhandlealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "通信中心返回处警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
	if(CouldFind==true){//能处理
	/****界面操作****/
	var alarmdetail = getAlarmopt(); //取警单报警核实内容
    var jb = $(".tiny_notes textarea");//取来电简报节点
    var alarmresult = jb.val(); //记录报警内容
    
    var vehicleTab = $("#example12 tbody tr"); //警单内表格
    var numberPlate = $("#alarmlistcaller").html();//获取车牌
    var v_id = $("#example12 tbody tr").children("td").eq(5).html();//获取车辆id

    isAlarm = false;//恢复未处警
    vehicleTab.remove();//警单中删除那一条记录
    alarmregend();//拾回警灯灭
    alarmend();//警灯灭
    var isRealAlarm = false;//是否是真实警情标识
    if (alarmdetail=="真实警情") {//判断是否是真实警情
        isRealAlarm = true;//置为真实警情
    }
    
    AskSeatList();//申请座席列表（转警用）
//    $("#transfer_a").addClass("disabled setgrey");//关闭转警快捷下拉菜单
    /****数据库操作在这里****/
    save_alarm(alarmdetail,alarmresult,isRealAlarm,numberPlate,v_id);//保存数据库
	}
};

//申请坐席列表结果
function onaskseatlistack(selfclient, retcode, retmsg, seats){
	log.value += "通信中心返回坐席列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + seats.length + "\n";
	if(seats.length== 0){
		$("#transfer_dstseat").empty();//清空转警座席列表
		$("#transfer_list").empty();//清空
		$("#transfer_a").addClass("disabled setgrey");//关闭转警快捷下拉菜单
	}
	for(var i=0; i<seats.length; i++) {
		log.value += seats[i].username + ", " + seats[i].seatid + ", " + seats[i].isidle +"\n";
		var row = '<option>'+seats[i].username+'</option>';
		$("#transfer_dstseat").empty().append(row);//写入警单内的下拉选

		var row1 = '<li><a href="#">'+seats[i].username+'</a></li>';
		$("#transfer_list").empty().append(row1);//写入导航栏的下拉选<li><a href="#">zhangxz</a></li>
		if (isAlarm==false) {
	        $("#transfer_a").addClass("disabled setgrey");//关闭转警快捷下拉菜单;
	    } else {
	    	$("#transfer_a").removeClass("disabled setgrey");//打开转警快捷下拉菜单
	    };
	};
};

//服务器返回转警请求结果, 只有等到已经转到目的坐席才返回
function ontransferalarmack(selfclient, retcode, retmsg, srcusername, dstusername, callLetter, alarmsn){
	log.value += "通信中心返回转警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", srcseat=" + srcusername  + ", dstseat=" + dstusername;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
	/**日志打印**/
	removeCar($("#example4 tbody tr"));//原表格移除车辆（未处理警情列表）
	removeCar($("#example5 tbody tr"));//原表格移除车辆（挂警列表）
	alert("转警成功！接警座席工号："+dstusername);
	isAlarm = false;//取消处警标识
	var nowtime = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");//生成现在时间
	var row = '<tr><td>'+$("#alarmlistcaller").html()+'</td><td>'+txtalarmname.value+'</td><td class="transfer_flag">已成功转至座席：'+dstusername+'</td><td>'+txtalarmtime.value+'</td><td>'+nowtime+'</td><td class="hide">'+$("#vehicle_id").val()+'</td></tr>';
	alarmend();//警灯灭
	alarmregend();//拾回警灯灭
	if ($("#example6>tbody>tr>td").hasClass("dataTables_empty")){
		$("#example6>tbody>tr>td.dataTables_empty").parent().remove();
	};	
	$("#example6 tbody").prepend(row);//添加到已处理警情里
	$("#example12 tbody").empty();//警单的表格清空
	$('#alarming').tab('show');//报警栏显示
	$('#alreadyAlarm').tab('show');//已处理警情栏显示
	//DecAlarmCount();//未处警数-1
	AskSeatList();//申请座席列表（转警用）
	removeAlarmopt();//清空车牌、核实报警选项、简报
	SeatIdle();//处警置闲
//	$("#transfer_a").addClass("disabled setgrey");//关闭转警快捷下拉菜单
};

//服务器向转警目的坐席分配转警
function onallottransferalarm(selfclient, username, callLetter, alarmsn, alarmTime, alarmid,
		alarmname, gpsinfo, alarmcount, srcusername, transfermsg) {
	log.value += "通信中心分配转警: callletter=" + callLetter  + ", alarmsn=" + alarmsn + ", alarmname=" + alarmname + ", alarmcount=" + alarmcount + "\n";
	log.value += "srcseat=" + srcusername  + ", transfermsg=" + transfermsg + "\n";
	var gpsTime = DateTranslate(new Date(parseInt(gpsinfo.gpsTime)));//gps时间
	var alarmTimeF = DateTranslate(new Date(parseInt(alarmTime)));//接收时间,报警时间
	log.value += gpsTime + ", " + gpsinfo.lng + ", " + gpsinfo.lat + "\n";

	txttransferseatname.value = srcusername;//页面记下转警原座席
	var src_course = gpsinfo.course;
	var course   = SEGUtil.getCourseDesc(gpsinfo.course);//方向
	var src_status = gpsinfo.status;
	var status   = SEGUtil.parseVehicleStatus(gpsinfo.status);//状态

	var loc = gpsinfo.loc;
	if (loc) {
		loc="已定位";
	}else{
		loc="正在定位";
	};
	/**日志打印**/
	search_pla(alarmname,callLetter,alarmsn,gpsTime,alarmTimeF,gpsinfo.lng,gpsinfo.lat,1,alarmid,gpsinfo.speed,course,status,loc,src_course,src_status);//查询车牌后显示基本资料(速度、方向、状态)
	addAlarmCount();//未处警数+1
	AskSeatList();//申请座席列表（转警用）
//	$("#transfer_a").removeClass("disabled setgrey");//打开转警快捷下拉菜单
};

//通信中心返回警情列表
function onaskalarmlistack(selfclient, retcode, retmsg, alarms, alarmscount) {
	// log.value += "通信中心返回警情列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + alarms.length + "\n";
	// var alarmstatuslist = ["新警情", "正在分配", "已经分配", "申请挂起", "处理完"];
	// for(var i=0; i<alarms.length; i++) {
	// 	var alarmtime = new Date(alarms[i].alarmTime.toNumber());
	// 	log.value += alarmtime.toString() + ", " + alarms[i].callLetter + ", " + alarms[i].alarmname + ", 状态=" + alarmstatuslist[alarms[i].status] +"\n";
	// };
	$("#alarmsSum").html(alarmscount);
};

//通信中心返回添加新警情结果
function onnewalarmack(selfclient, retcode, retmsg, slaver, callLetter, alarmsn) {
	log.value += "通信中心返回添加警情结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", 坐席=" + slaver + "\n";
	log.value += "呼号=" + callLetter + ", SN=" + alarmsn + "\n";
};



