
/*******************************************************************************************************
 * 下面的和处警相关的事件
 */
//坐席设置忙闲，通信中心应答
function onsetalarmbusy(selfclient, retcode, retmsg, username, busy, handleseatname) {
	if (busy) {
		alert("处警置忙");
		$("#alarmstatus").html("忙碌");
		$("#alarmopt").html("就绪");
	} else {
		alert("处警置闲");
		$("#alarmstatus").html("就绪");
		$("#alarmopt").html("忙碌");
	}
	log.value += ": retcode=" + retcode  + ", retmsg=" + retmsg;
	if (handleseatname) {
		log.value += ", " + handleseatname;
	}
	log.value += "\n";
};

//通信中心分配警情
function onallotalarm(selfclient, seatname, callLetter, alarmsn, alarmTime, alarmid, alarmname, gpsinfo, alarmcount, append) {
	api_url = 'app/data/vehicleinfo';//搜索API
	params = {param:callLetter, type:1};
	$.getJSON(api_url, params, function(result){
        if(result && result.success==true){
        	$.each(result.info, function(i,item){
        		var gpsTime = new Date(gpsinfo.gpsTime.toNumber());
        		var row = '<tr><td>'+item.plate_no+'</td><td>'+alarmname+'</td><td class="hide">'+callLetter+'</td><td class="hide">'+alarmsn+'</td><td class="hide">'+gpsTime.toString()+'</td><td class="hide">'+gpsinfo.lng+'</td><td class="hide">'+gpsinfo.lat+'</td></tr>';
                $("#example4 tbody").prepend(row);
            });
        }
    };
    
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心应答挂警
function onpausealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	log.value += "通信中心返回挂警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += "caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心应答处警结果
function onhandlealarmack(selfclient, retcode, retmsg, seatname, callLetter, alarmsn) {
	
	log.value += "通信中心返回处警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//申请坐席列表结果
function onaskseatlistack(selfclient, retcode, retmsg, seats){
	log.value += "通信中心返回坐席列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + seats.length + "\n";
	for(var i=0; i<seats.length; i++) {
		log.value += seats[i].username + ", " + seats[i].seatid + ", " + seats[i].isidle +"\n";
	};
};

//服务器返回转警请求结果, 只有等到已经转到目的坐席才返回
function ontransferalarmack(selfclient, retcode, retmsg, srcusername, dstusername, callLetter, alarmsn){
	log.value += "通信中心返回转警结果: retcode=" + retcode  + ", retmsg=" + retmsg;
	log.value += ", srcseat=" + srcusername  + ", dstseat=" + dstusername;
	log.value += ", caller=" + callLetter  + ", alarmsn=" + alarmsn + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//服务器向转警目的坐席分配转警
function onallottransferalarm(selfclient, username, callLetter, alarmsn, alarmTime, alarmid,
		alarmname, gpsinfo, alarmcount, srcusername, transfermsg) {
	log.value += "通信中心分配转警: callletter=" + callLetter  + ", alarmsn=" + alarmsn + ", alarmname=" + alarmname + ", alarmcount=" + alarmcount + "\n";
	log.value += "srcseat=" + srcusername  + ", transfermsg=" + transfermsg + "\n";
	var gpsTime = new Date(gpsinfo.gpsTime.toNumber());
	log.value += gpsTime.toString() + ", " + gpsinfo.lng + ", " + gpsinfo.lat + "\n";
	txtalarmcaller.value = callLetter;
	txtalarmsn.value = alarmsn;
};

//通信中心返回警情列表
function onaskalarmlistack(selfclient, retcode, retmsg, alarms) {
	log.value += "通信中心返回警情列表: retcode=" + retcode  + ", retmsg=" + retmsg + ", 总数=" + alarms.length + "\n";
	var alarmstatuslist = ["新警情", "正在分配", "已经分配", "申请挂起", "处理完"];
	for(var i=0; i<alarms.length; i++) {
		var alarmtime = new Date(alarms[i].alarmTime.toNumber());
		log.value += alarmtime.toString() + ", " + alarms[i].callLetter + ", " + alarms[i].alarmname + ", 状态=" + alarmstatuslist[alarms[i].status] +"\n";
	};
};

//通信中心返回添加新警情结果
function onnewalarmack(selfclient, retcode, retmsg, slaver, callLetter, alarmsn) {
	log.value += "通信中心返回添加警情结果: retcode=" + retcode  + ", retmsg=" + retmsg + ", 坐席=" + slaver + "\n";
	log.value += "呼号=" + callLetter + ", SN=" + alarmsn + "\n";
};

