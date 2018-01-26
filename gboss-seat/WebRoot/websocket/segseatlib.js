/*!
 * 
acceptAlarmACK * Copyright 2014 chinagps, Inc. zhangxz
 * 通信中心websocket协议 
 * 类中var函数调this变量，用this不行，要自定义一个变量selfclient
 * 
 * zhangxz 2014-09-30 添加坐席新功能
 * 
 */
"use strict";

//alert("segseatlib");

if (typeof Zlib === "undefined") { throw new Error("segseatlib requires zlib"); };
if (typeof SEGUtil_WS === "undefined") { throw new Error("segseatlib requires segutil_ws"); };
if (typeof dcodeIO === 'undefined' || !dcodeIO.ByteBuffer || !dcodeIO.ProtoBuf || comcenterprotobuf == null) { throw(new Error("ProtoBuf.js is not present"));}

/*
 * segseatlib是容，中可能包含多个jsclient客户端, 所以前面是segseatlib的属性，后面定义jsclient属性
 */
var segseatlib = {
	timetask: null,		//定时任务
	clientes: [],		//客户端队列
	heartbeatinterval: 10000,	//心跳间隔10秒
	STX: 0xFE,
	
	//proto编解码用
	bytebuffer: null,
	protobuf: null,
	builder: null,
	centermessage: null,
	basemessage: null,
	
	//消息类型定义
	message_type: {
	    Login: 1001,			   //登录
	    Login_ACK: 8001,           //登录应答
	    Logout: 1002,              //退录
	    Logout_ACK: 8002,          //退录应答
	    ActiveLink: 1003,          //链路检测（心跳）
	    ActiveLink_ACK: 8003,      //链路检测（心跳）应答
	    
	    AddMonitor: 1011,          //添加监控列表
	    AddMonitor_ACK: 8011,      //添加监控列表应答
	    RemoveMonitor: 1012,       //删除监控列表
	    RemoveMonitor_ACK: 8012,   //删除监控列表应答
	    
	    GetLastInfo: 1013,                //取最后位置、取最后行程、取最后故障
	    GetLastInfo_ACK: 8013,            //取最后位置、取最后行程、取最后故障的应答
	    GetHistoryInfo: 1014,             //取历史位置、历史行程、历史故障（如果换了一辆车，前一辆车的历史查询自动结束）
	    GetHistoryInfoNextPage: 1015,     //取下一页历史位置、历史行程、历史故障
	    GetHistoryInfo_ACK: 8014,         //取历史位置、历史行程、历史故障应答（1014, 1015共用一个应答）
	    StopHistoryInfo: 1016,            //结束读历史位置、历史行程、历史故障（如果分页全部取完了，自动结束）
	    StopHistoryInfo_ACK: 8016,        //结束读历史位置、历史行程、历史故障应答
        GetHistorySimpleGpsInfo_ACK: 8017,   //取历史位置主要信息应答
	    
	    SendCommand: 1051,            //下发指令
	    SendCommand_ACK: 8051,        //下发指令结果
	    SendCommandSend_ACK: 8052,    //网关指令发送成功结果
	    GetCommandHistory: 1053,      //查询历史指令
	    GetCommandHistory_ACK: 8053,  //查询历史指令回应
	    
	    TestDeliver: 2000,			  //测试用。要求服务器模拟终端上传数据
	    DeliverGPS: 2001,             //上传GPS（包括OBD数据）
	    DeliverOperateData: 2002,     //上传运营数据
	    DeliverSMS: 2003,             //上传短消息
	    DeliverUnitLoginOut: 2004,    //上传终端登退录消息
	    DeliverTravel: 2005,          //上传终端行程消息
	    DeliverFault: 2006,           //上传终端故障消息
	    DeliverAlarm: 2007,           //上传终端警情消息
        DeliverSimpleGPS: 2008,       //GPS主要信息(历史查询时用，减少传输字节)
        DeliverOBD: 2009,             //上传终端OBD数据
        DeliverAppNotice: 2010,       //APP通知类消息
        DeliverUnitVersion: 2011,    //终端升级成功上报版本号

        SetAlarmBusy: 3001,           //坐席设置处理警情忙闲状态
	    SetAlarmBusy_ACK: 10001,      //坐席设置处理警情忙闲状态, 服务器返回结果
	    AllotAlarm: 3002,             //服务器分配警情给某坐席（包括追加警情）
	    AllotAlarm_ACK: 10002,        //服务器分配警情给某坐席, 坐席应答结果
	    PauseAlarm: 3003,             //坐席请求挂警（暂时不处理这个警情），可以接收其他警情
	    PauseAlarm_ACK: 10003,        //服务器返回请求挂警结果
	    CancelPauseAlarm: 3013,       //坐席请求取消挂警（继续处理这个警情）
	    CancelPauseAlarm_ACK: 10013,  //服务器返回请求取消挂警结果
	    HandleAlarm: 3004,            //坐席向服务器报告处理警情结果（已经追加的警情也作相同的处理）
	    HandleAlarm_ACK: 10004,       //服务器返回处理警情结果
	    
	    AskSeatList: 3005,            //坐席向服务器请求坐席列表
	    AskSeatList_ACK: 10005,       //服务器返回坐席列表
	    TransferAlarm: 3006,          //坐席向服务器请求转警
	    TransferAlarm_ACK: 10006,     //服务器返回转警请求结果, 只有等到已经转到目的坐席才返回
	    AllotTransferAlarm: 3007,     //服务器向转警目的坐席分配转警
	    AllotTransferAlarm_ACK: 10007,//目的坐席回复是否收到转警
	    
	    AskAlarmList: 3008,           //坐席向服务器请求警情列表（未处理和正在处理的）
	    AskAlarmList_ACK: 10008,      //服务器返回警情列表
	    
	    //下面二个是测试用的，坐席不可能生成警情通知通信中心
	    NewAlarm: 4002,               
    	NewAlarm_ACK: 12002,          
	},
	
	//返回结果定义
	resultcode: {
	    OK: 0,                	 //成功
	    UserName: 1,             //用户不存在
	    Password: 2,             //密码错误
	    UserExist: 3,            //用户已经存在
	    LoginNameExist: 4,       //登录名已经存在
	    MobileExist: 5,          //手机号已经存在
	    EmailExist: 6,           //Email已经存在
	    UserNoExist: 7,          //用户不存在
	    LoginNameEdit: 8,        //登录名不能修改
	    VehicleNoExist: 9,       //车辆不存在，车牌号错误
	    UnitNoExist: 10,         //车台不存在, 呼号错误
	    UserNoVehicle: 11,       //用户没有该车辆
	    VehicleNoUnit: 12,       //车辆没有该车台
	    CallLetterExist: 13,     //车载号码已经存在
	    UnitNoAck: 14,           //指令发送成功但车台无反应
	    CommandID: 15,           //命令ID错误
	    Parameters: 16,          //参数错误 
	    Send: 17,                //发送失败
	    Timeout: 18,             //超时失败
	    CompanyNoExist: 20,      //商户不存在
	    NoLogin: 21,             //没有登录
	    DataBase: 22,            //数据库错误
	    ConnectFail: 23,         //连接不成功
	    Encode: 24,              //编码（打包）错误
	    Decode: 25,              //解码（解包）失败
    	Format: 26,              //格式错误
	    TimeError: 27,           //时间错误
	    NoRequest: 28,           //没有申请错误
		Shutdowm: 29,            //终端已经关机错误
		SeatNoLogin: 40,         //坐席没有登录
		AlarmNoExist: 41,        //警单不存在
		AlarmHandled: 42,        //警单已经处理
		SeatExist: 43,           //坐席已经存在
		SeatBusy: 44,            //坐席忙碌
		Seat: 45,                //坐席错误

		Hbase: 101,              //Hbase存储错误
	    LastPosition: 102,           //没有最后位置
	    HistoryPosition: 103,        //没有历史位置
	    HistoryPositionNoStart: 104, //没有开始历史位置（取下一页时）
	    LastTravel: 105,             //没有最后行程
	    HistoryTravel: 106,          //没有历史行程
	    HistoryTravelNotStart: 107,  //没有开始历史行程（取下一页时）
	    LastFault: 108,             //没有最后故障
	    HistoryFault: 109,          //没有历史故障
	    HistoryFaultNotStart: 110,  //没有开始历史故障（取下一页时）
		LastAlarm: 111,           		//没有最后警情
		HistoryAlarm: 112,        		//没有历史警情
		HistoryAlarmNoStart: 113, 		//没有开始历史警情（取下一页时）
		LastOperateData: 114,           //没有最后运营数据
		HistoryOperateData: 115,        //没有历史运营数据
		HistoryOperateDataNoStart: 116, //没有开始历史运营数据（取下一页时）
		LastSm: 117,           			//没有最后短信
		HistorySm: 118,        			//没有历史短信
		HistorySmNoStart: 119, 			//没有开始历史短信（取下一页时）
		
		RefuseAlarm: 201, 			//拒绝处理警情
		AppendAlarm: 211, 			//添加警情到警情队列失败
	    Other: -1,                  //其他错误
	},
	
	//浏览器是否支持websocket
	isWebSocketSupport: function() {
		var supportWebSocket = typeof(WebSocket) != "undefined";
		var supportDataView = typeof(DataView) != "undefined";
		var supportArrayBuffer = typeof(ArrayBuffer) != "undefined";
		var supportInt8Array = typeof(Int8Array) != "undefined";
		return supportWebSocket && supportDataView && supportArrayBuffer && supportInt8Array;
	},

	//初始化ProtoBuf
	init: function() {
		if (!segseatlib.isWebSocketSupport()){
			return;
		}
		if (segseatlib.protobuf == null) {
		    segseatlib.bytebuffer = dcodeIO.ByteBuffer;
			segseatlib.protobuf = dcodeIO.ProtoBuf;
			segseatlib.builder = segseatlib.protobuf.protoFromString(comcenterprotobuf);
			comcenterprotobuf = null;	//释放comcenterprotobuf内存，原来是字符串常量
			segseatlib.centermessage = segseatlib.builder.build("ComCenterMessage");
			segseatlib.basemessage = segseatlib.builder.build("ComCenterMessage.ComCenterBaseMessage");
			//messageType不用枚举，直接用常量
			//var messageType = builder.build("MessageType");
		}
	},
	
	//每个连接10秒执行一次定时任务，检查是否需求重连或发链路检测报文
	setTimeoutTask: function(){
		if (segseatlib.timetask != null) {
			return;
		}
		segseatlib.timetask = setInterval(function(){
			try {
				var arr = segseatlib.clientes;
				for(var i=0; i<arr.length; i++){
					if ((typeof arr[i] === "object") && (typeof arr[i].timeout === "function")) {
						arr[i].timeout();
					}
				}
			} catch (err) {
				alert("timeout error:" + err);
			}
		}, 10000);
	},

	//添加jsclient客户端
	addClient: function(segclient){
		var index = SEGUtil_WS.indexOfArray(segseatlib.clientes, segclient);
		if (index == -1){
			segseatlib.clientes.push(segclient);
			segseatlib.setTimeoutTask();
		}
	},

	//释放jsclient客户端
	removeClient: function(segclient) {
		var index = SEGUtil_WS.indexOfArray(segseatlib.clientes, segclient);
		if (index != -1){
			segseatlib.clientes.splice(index, 1);
			segclient.close();
			if (segseatlib.clientes.length == 0) {
				clearInterval(segseatlib.timetask);
				segseatlib.timetask = null;
			}
		}
	},
	
	//释放客户端byID
	removeClientByID: function(_id) {
		var arr = segseatlib.clientes;
		for(var i=0; i<arr.length; i++){
			if(arr[i].id == _id){
				segseatlib.clientes.splice(i, 1);
				return;
			}
		}
	},

	//编码(通信中心websocket协议)
	encode: function(src, compress, encrypt) {
		try {
			if (src.byteLength == 0) {
				return null;
			}
		    if (src.byteLength < 256)	//小于256不压缩
		    	compress = false;
			
		    //压缩
		    if (compress) {
		    	src = segseatlib.Utils.compress(src);
		    }
		    //超过协议总长度, 压缩可能返回null
		    if (src == null || src.byteLength > 65520){
		    	return null;
		    }
		    //加密
		    if (encrypt) {
		        src = segseatlib.Utils.encrypt(src);
		    }
		    //准备报文头
		    var totallen = src.byteLength + 12;
		    var buff = new Uint8Array(totallen);
		    buff[0] = segseatlib.STX;
		    buff[1] = 0x10;
		    buff[2] = compress ? 1 : 0;
		    buff[3] = encrypt ? 1 : 0;
		    buff[4] = (totallen >>> 8) & 0xFF;
			buff[5] = (totallen) & 0xFF;
			buff[6] = 0;
			buff[7] = 0;
			
			segseatlib.Utils.byteArrayCopy(buff, 8, src);
			var crc32 = SEGUtil_WS.crc32(buff, totallen - 4);
			segseatlib.Utils.int32ToByteArray(buff, totallen - 4, crc32);
			return buff;
		} catch (err) {
			SEGUtil_WS.showError("websocket协议编码失败:" + err);
			return null;
	    }
		return null;
	},

	//解码(通信中心websocket协议)
	//recv是Uint8Array
	decode: function(recv) {
		try {
			if (recv.byteLength <= 12) {
				return null;	//格式错误
			}
			//判断报文头
			if (recv[0] != segseatlib.STX) {
				return null;	//格式错误
			}
			//判断长度
			if (SEGUtil_WS.getShortFromByteArray(recv, 4) != recv.byteLength) {
				return null;
			}
			//判断crc
			var crc32c = SEGUtil_WS.crc32(recv, recv.byteLength - 4);
			var crc32s = SEGUtil_WS.getIntFromByteArray(recv, recv.byteLength - 4);
			if (crc32c != crc32s) {
				return null;
			}
			//先保留压缩和加密标志
			var iscompress = recv[2];
			var isencrypt = recv[3];
			var content = new Uint8Array(recv.byteLength-12);
			segseatlib.Utils.byteArrayCopy(content, 0, recv, 8, recv.byteLength-4);
		    //解密
		    if (isencrypt == 1) {
		        content = segseatlib.Utils.decrypt(content);
		    }
		    //压缩
		    if (iscompress == 1) {
		        var inflate = new Zlib.Inflate(content);
		        content = inflate.decompress();
		    }
		    return content;
		} catch (err) {
			SEGUtil_WS.showError("websocket协议解码码失败:" + err);
			return null;
	    }
		return null;
	},
	
	/*
	* 根据ByteBuffer.js中函数，增加一个int8Array的转换函数, 原代码中不提供
	*/
    int8ArrayToByteBuffer: function(arr, littleEndian) {
        var k = arr.length;
        var dst = new ArrayBuffer(k);
        var view = new DataView(dst);
        for (var i=0; i<k; ++i) {
            view.setUint8(i, arr[i]);
        }
        var bb = new segseatlib.bytebuffer(k, littleEndian, true);
        bb.array = dst;
        bb.view = view;
        bb.length = k;
        return bb;
    },

	//客户端定义
    //_id: 这个库可以有多个通信中心客户端, 每个WEB可以同时有多个，用不同的_id表示
    //_hosts: 通信中心服务器，格式90.0.12.135:8080;90.0.12.203:8080，多个通信中心用半角分号隔开
    //_username, _password: 登录名及其密码
    //_seatid: 可以为空，坐席编号，或者登录名编号
    //_usertype，_userversion: 客户端类型，为了跟踪和统计用
    //                       手机APP分别是：ANDROIDAPP， ISOAPP
    //						  大平台坐席是：大平台坐席
    //						  门店系统: 
	jsclient: function(_id, _hosts, _username, _password, _seatid, _usertype, _userversion) {
		segseatlib.init();
		
		this.id = _id;		//客户端id, 可能有多个客户端同时运行, 事件中参数调用, 容器要调用，所以公共不私有
		
		var socket = null;
		var server = null;	//支持多个服务器IP和端口（集群）
		var serverid = 0;	//连接第几个
		var username = "";	//登录用户名
		var password = "";	//登录口令
		var usertype = "";	//客户端类型
		var userversion = "";	//客户端版本
		var seatid = null;	//坐席编号
		var compress = false;	//不压缩
		var encrypt = false;	//不加密
		var lasttime = new Date().getTime();
		var loginstatus = 0;		//0: 未登录, 1:正在登录, 2:已登录 
		var ismanualclose = false;	//是否手工断开连接，人工断开，只能人工重连
		var selfclient = this;		//局部变量（函数）调用全局变量时，不能用this(因为this可能是其他类), 只能用变量
		//以下三项是重新登录时，自动发往通信中心
		var seatbusy = null;		//坐席处警忙标识
		var seatbusycaller = null;	//坐席处警忙时，能处理的车机呼号
		var monitorcaller = null;	//监控呼号，重新登录时，自动上传
		
		if (typeof _hosts === 'string') {
			server = _hosts.split(';'); 
		} else if (typeof _hosts === 'object') {	//数组
			server = _hosts;
		};
		if (typeof _username === 'string') {
			username = _username;
		};
		if (typeof _password === 'string') {
			password = _password;
		};
		if (typeof _seatid === 'string') {
			seatid = _seatid;
		};
		if (typeof _usertype === 'string') {
			usertype = _usertype;
		};
		if (typeof _userversion === 'string') {
			userversion = _userversion;
		};
		//是否连接
		this.isConnected = function(){
			return (socket != null) && (socket.readyState == WebSocket.OPEN);
		};
		
		//是否登录
		this.isLogined = function(){
			return (loginstatus==2) && (socket != null) && (socket.readyState == WebSocket.OPEN);
		};

		//设置是否压缩传输
		this.setCompress = function(b) {
			if (typeof b === "boolean") {
				compress = b;
			} else {
				compress = true;
			}
		}; 
		
		//设置是否加密传输
		this.setEncrypt = function(b) {
			if (typeof b === "boolean") {
				encrypt = b;
			} else {
				encrypt = true;
			}
		};
		
		//发送字符串 或 字节串
		//socket只有发送Int8Array, 或者Uint8Array, 或者 protobuf的toArrayBuffer结果， 才是二进制到服务器
		this.send = function(buff) {
			if (!selfclient.isConnected()) {
				return segseatlib.resultcode.ConnectFail;
			}
			//如果不是字节串, 直接发送
			if (typeof(buff.byteLength) != "undefined"){
			    var int8arr = segseatlib.encode(buff, compress, encrypt);
			    if (int8arr == null) {
			    	return segseatlib.resultcode.Encode;
			    }
				socket.send(int8arr);
			} else {
				socket.send(buff);
			}
	    	return segseatlib.resultcode.OK;
		};
		
		//发登录报文, 因为只要内部调用，不要外部调用，所以用var, 不能用this
		//this.login = function() {
		var login = function() {
			console.log("logining");
			if (!selfclient.isConnected()) {
				return;
			}
			var loginbuilder = segseatlib.builder.build("Login");
			var loginobj = new loginbuilder(username, password, monitorcaller, seatid, usertype, userversion);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.Login, loginobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			selfclient.send(new Uint8Array(msg.toArrayBuffer()));
			loginstatus = 1;	//正在登录
			ismanualclose = false;
		};
		
		//发退录报文
		var logout = function() {
			console.log("logouting");
			if (!selfclient.isLogined()) {
				return;
			}
			var logoutmsg = new segseatlib.basemessage(segseatlib.message_type.Logout);
			var msg = new segseatlib.centermessage({"messages":[logoutmsg]});
			ismanualclose = true;
			selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		//链路检测
		this.activelink = function() {
			console.log("activelink");
			var activelinkmsg = new segseatlib.basemessage(segseatlib.message_type.ActiveLink);
			var msg = new segseatlib.centermessage({"messages":[activelinkmsg]});
			selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		/*************************************************************************************
		 * 下面是坐席（或APP客户端）向通信中心发请求 
		 */
		//添加监控列表
		this.addMonitor = function(callletterlist, infotypelist, clearold) {
			console.log("addmonitor");
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("AddMonitor");
			var tmpobj = new tmpbuilder(callletterlist, infotypelist);
			if (clearold) {
				tmpobj = new tmpbuilder(callletterlist, infotypelist, true);
			}
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.AddMonitor, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		//添加监控列表
		this.removeMonitor = function(callletterlist, infotypelist) {
			console.log("removemonitor");
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("RemoveMonitor");
			var tmpobj = new tmpbuilder(callletterlist, infotypelist);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.RemoveMonitor, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		this.getLastInfo = function(infotype, callletterlist, sn) {
			console.log("getlastinfo: " + infotype);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("GetLastInfo");
			var tmpobj = new tmpbuilder(infotype, callletterlist, sn);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.GetLastInfo, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		/*
		 *starttime, endtime: Date 
		 */
		this.getHistoryInfo = function(callletter, infotype, starttime, endtime, pageNumber, totalNumber, autonextpage, sn, reversed) {
			console.log("gethistoryinfo: " + infotype);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var startseconds = starttime.getTime();
			var endseconds = endtime.getTime();
			var tmpbuilder = segseatlib.builder.build("GetHistoryInfo");
			var tmpobj = new tmpbuilder(callletter, infotype, startseconds, endseconds, pageNumber, totalNumber, autonextpage, sn, reversed);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.GetHistoryInfo, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		//类型在读第一页时已经决定
		this.getHistoryNextPage = function(callletter, infotype, sn) {
			console.log("GetHistoryInfoNextPage");
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("GetHistoryInfoNextPage");
			var tmpobj = new tmpbuilder(callletter, infotype, sn);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.GetHistoryInfoNextPage, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		//结束读历史记录
		this.stopHistory = function(callletter, infotype, sn) {
			console.log("StopHistoryInfo");
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("StopHistoryInfo");
			var tmpobj = new tmpbuilder(callletter, infotype, sn);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.StopHistoryInfo, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		//模拟终端上传一条信息
		//this.testDeliver = function(infotype, callletterlist) {
		//	console.log("TestDeliver");
		//	if (!selfclient.isLogined()) {
		//		return segseatlib.resultcode.NoLogin;
		//	}
		//	var tmpbuilder = segseatlib.builder.build("TestDeliver");
		//	var tmpobj = new tmpbuilder(infotype, callletterlist);
		//	var basemsg = new segseatlib.basemessage(segseatlib.message_type.TestDeliver, tmpobj.toArrayBuffer());
		//	var msg = new segseatlib.centermessage({"messages":[basemsg]});
		//	return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		//};
		
		//取历史记录
		this.getCommandHistory = function(callletter, starttime, endtime) {
			console.log("getcommandhistory: " + callletter);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var startseconds = starttime.getTime();
			var endseconds = endtime.getTime();
			var tmpbuilder = segseatlib.builder.build("GetCommandHistory");
			var tmpobj = new tmpbuilder(callletter, startseconds, endseconds);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.GetCommandHistory, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		//发送终端命令
		this.sendUnitCommand = function(sn, callletterlist, cmdid, params, addmonitor) {
			console.log("SendCommand " + cmdid);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("SendCommand");
			var tmpobj = new tmpbuilder(sn, callletterlist, cmdid, params);
			if (addmonitor) {
				tmpobj = new tmpbuilder(sn, callletterlist, cmdid, params, null, null, true);
			}
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.SendCommand, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		//坐席警情处理置忙(或置闲)
		this.setAlarmBusy = function(seatname, seatid, busy, callletter) {
			console.log("SetAlarmBusy " + seatname);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("SetAlarmBusy");
			var tmpobj = new tmpbuilder(seatname, seatid, busy);
			seatbusycaller = null;
			if (busy == true && callletter != null) {
				seatbusycaller = callletter;
				tmpobj = new tmpbuilder(seatname, seatid, busy, callletter);
			}
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.SetAlarmBusy, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		//坐席是否接受警情(或拒绝)
		this.acceptAlarmACK = function(accept, seatname, callletter, alarmsn) {
			console.log("AcceptAlarmACK " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("AllotAlarm_ACK");
			var retcode = segseatlib.resultcode.OK;
			var retmsg = "接受处警";
			if (accept != true) {
				retcode = segseatlib.resultcode.RefuseAlarm;
				retmsg = "拒绝处警";
			}
			var tmpobj = new tmpbuilder(retcode, retmsg, seatname, callletter, alarmsn);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.AllotAlarm_ACK, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		//坐席向服务器请求挂警（暂时不处理这个警情），可以接收其他警情
		this.pauseAlarm = function(seatname, callletter, alarmsn, pausemsg) {
			console.log("PauseAlarm " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("PauseAlarm");
			var tmpobj = new tmpbuilder(seatname, callletter, alarmsn, pausemsg);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.PauseAlarm, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		//坐席向服务器请求挂警（暂时不处理这个警情），可以接收其他警情
		this.CancelPauseAlarm = function(seatname, callletter, alarmsn) {
			console.log("CancelPauseAlarm " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("CancelPauseAlarm");
			var tmpobj = new tmpbuilder(seatname, callletter, alarmsn);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.CancelPauseAlarm, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		/*************************************************************************************
		* 坐席向服务器报告处理警情结果（已经追加的警情也作相同的处理）
		*************************************************************************************/
		this.HandleAlarm = function(seatname, callletter, alarmsn, handlecode, handlemsg) {
			console.log("HandleAlarm " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("HandleAlarm");
			var tmpobj = new tmpbuilder(seatname, callletter, alarmsn, handlecode, handlemsg);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.HandleAlarm, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		/*************************************************************************************
		* 坐席向服务器请求坐席列表
		*************************************************************************************/
		this.AskSeatList = function(seatname, callletter, isidle) {
			console.log("AskSeatList " + seatname);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			if (isidle != false) {
				isidle = true;
			}
			var tmpbuilder = segseatlib.builder.build("AskSeatList");
			var tmpobj = new tmpbuilder(seatname, callletter, isidle, true);	//不包括自己
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.AskSeatList, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		/*************************************************************************************
		* 坐席申请转警, 转出坐席向服务器请求转警
		*************************************************************************************/
		this.TransferAlarm = function(srcseatname, dstseatname, callletter, alarmsn, transfermsg) {
			console.log("TransferAlarm " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("TransferAlarm");
			var tmpobj = new tmpbuilder(srcseatname, dstseatname, callletter, alarmsn, transfermsg);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.TransferAlarm, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		/*************************************************************************************
		* 转警目的坐席向服务器应答是否接受转警
		*************************************************************************************/
		this.AllotTransferAlarmACK = function(accept, seatname, callletter, alarmsn, srcseatname) {
			console.log("AllotTransferAlarmACK " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var retcode = segseatlib.resultcode.OK;
			var retmsg = "同意转警";
			if (accept != true) {
				retcode = segseatlib.resultcode.RefuseAlarm;
				retmsg = "拒绝转警";
			}
			var tmpbuilder = segseatlib.builder.build("AllotTransferAlarm_ACK");
			var tmpobj = new tmpbuilder(retcode, retmsg, seatname, callletter, alarmsn, srcseatname);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.AllotTransferAlarm_ACK, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};

		/*************************************************************************************
		* 坐席向服务器请求警情列表（未处理和正在处理的）
		*************************************************************************************/
		this.AskAlarmList = function(seatname, onlyself, onlycount) {
			console.log("AskAlarmList " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			if (onlyself != false) {
				onlyself = true;
			}
			if (onlycount != false) {
				onlycount = true;
			}
			var tmpbuilder = segseatlib.builder.build("AskAlarmList");
			var tmpobj = new tmpbuilder(seatname, onlyself, onlycount);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.AskAlarmList, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		/*************************************************************************************
		* 测试用
		* 添加测试警情，运行时可以屏蔽, 坐席用websocket协议向通信中心添加警情
		* seatname: 坐席名称
    	* callLetter: 终端呼号
    	* alarmsn: 警情唯一序列号
    	* alarmTime: 警情发生时间，不一定是GPS时间, 以系统判断时间为准(LONG)
    	* alarmid: 警情类别
    	* level: 警情级别(1-3)
    	* 
    	* gpsTime: GPS时间(从1970-1-1 0:0:0开始的秒数), 为了提高处理速度,不用字符串
    	* loc: 是否定位
    	* lat: 纬度(单位：百万分之一度，北正南负）
    	* lng: 经度(单位：百万分之一度，东正西负）
    	* speed: 速度（单位：百米每小时）
    	* course: 方向（单位：度，正北0，顺时针增加）
    	* status: 状态（多个）
    	* totalDistance: 总里程（单位：米）optional
    	* oil: 油箱中油量（单位：0.01升）optional
		*************************************************************************************/
		this.NewAlarm = function(seatname, callletter, alarmsn, alarmTime, alarmid, level,
		    	gpsTime, loc, lat, lng, speed, course, status) {
			console.log("NewAlarm " + alarmsn);
			if (!selfclient.isLogined()) {
				return segseatlib.resultcode.NoLogin;
			}
			var tmpbuilder = segseatlib.builder.build("GpsSimpleInfo");
			var tmpgps = new tmpbuilder(gpsTime, loc, lat, lng, speed, course, status.split(','));
			tmpbuilder = segseatlib.builder.build("NewAlarm");
			var tmpobj = new tmpbuilder(seatname, callletter, alarmsn, alarmTime, alarmid, level, tmpgps);
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.NewAlarm, tmpobj.toArrayBuffer());
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			return selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		/*************************************************************************************
		* WEBSOCKET 事件
		*************************************************************************************/
		//websocket连接成功事件, 马上发登录报文
		this.openEvent = function(evt) {
			try {
				lasttime = new Date().getTime();
				onopen(selfclient, evt);	//selfclient最好不用this, this有可能是socket, 如果socket事件直接用=, 不是间接调用 
				login();	//登录
			} catch (err) {
				SEGUtil_WS.showError(err);
		    }
		};

		this.errorEvent = function(event) {
			try {
			    onerror(selfclient, event);
			} catch (err) {
				SEGUtil_WS.showError(err);
		    }
		};
		
		//websocket断开连接事件
		this.closeEvent = function(event) {
			try {
				loginstatus = 0;
			    onclose(selfclient, event);
			    if (!ismanualclose) {	//人工断开不用重连
			    	//断开以后重连，不用再定时了重连，每次重连，都有断开事件（不管服务器是否打开）
			    	selfclient.connect();
			    }
			} catch (err) {
				SEGUtil_WS.showError(err);
		    };
		};
		
		//接收事件
		this.receiveEvent = function(event) {
		    try {
		    	lasttime = new Date().getTime();
		    	//先解码
		    	var msg = segseatlib.decode(new Uint8Array(event.data));
		    	if (msg != null) {
		    	    var buff = segseatlib.int8ArrayToByteBuffer(msg);
					var msg = segseatlib.centermessage.decode(buff);
					//buff.destroy();
					buff = null;
					//循环处理消息
					for(var i=0; i<msg.messages.length; i++){
						processMessage(msg.messages[i]);
					};
		    	};
		    } catch (err) {
		    	SEGUtil_WS.showError("receive error:" +err);
		    };
		};
		
		/*************************************************************************************
		* 处理接收的消息
		*************************************************************************************/
		var processMessage = function(basemsg) {
			switch(basemsg.id) {
			case segseatlib.message_type.DeliverGPS:
				processDeliverGPS(basemsg.content);
				break;
			case segseatlib.message_type.SendCommand_ACK:	//发送指令回复(指令发送分二次应答)
				processSendCommandACK(basemsg.content);
				break;
			case segseatlib.message_type.SendCommandSend_ACK: //网关发送指令成功
				processSendCommandSendACK(basemsg.content);
				break;
			case segseatlib.message_type.GetLastInfo_ACK:
				processGetLastInfoACK(basemsg.content);
				break;
			case segseatlib.message_type.DeliverOperateData:
				processDeliverOperateData(basemsg.content);
				break;
			case segseatlib.message_type.DeliverSMS:
				processDeliverSMS(basemsg.content);
				break;
			case segseatlib.message_type.DeliverUnitLoginOut:
				processDeliverUnitLoginOut(basemsg.content);
				break;
			case segseatlib.message_type.DeliverTravel:
				processDeliverTravel(basemsg.content);
				break;
			case segseatlib.message_type.DeliverFault:
				processDeliverFault(basemsg.content);
				break;
			case segseatlib.message_type.DeliverAlarm:
				processDeliverAlarm(basemsg.content);
				break;
			case segseatlib.message_type.DeliverAppNotice:
				processDeliverAppNotice(basemsg.content);
				break;
			case segseatlib.message_type.DeliverOBD:
				processDeliverOBD(basemsg.content);
				break;
			case segseatlib.message_type.DeliverUnitVersion:
				processDeliverUnitVersion(basemsg.content);
				break;
			case segseatlib.message_type.GetHistoryInfo_ACK:
				processGetHistoryInfoACK(basemsg.content);
				break;
			case segseatlib.message_type.GetHistorySimpleGpsInfo_ACK:
				processGetHistorySimpleGpsInfoACK(basemsg.content);
				break;
			case segseatlib.message_type.GetCommandHistory_ACK:
				processGetCommandHistoryACK(basemsg.content);
				break;
			case segseatlib.message_type.Login_ACK:
				processLoginACK(basemsg.content);
				break;
			//case segseatlib.message_type.Logout_ACK:	//单元测试用
				//processLogoutACK(basemsg.content);
				//break;
			//case segseatlib.message_type.ActiveLink_ACK: //单元测试用
				//processActiveLinkACK(basemsg.content);
				//break;
			case segseatlib.message_type.ActiveLink:
				processActiveLink(basemsg.content);
				break;
			case segseatlib.message_type.AddMonitor_ACK:
				processAddMonitorACK(basemsg.content);
				break;
			case segseatlib.message_type.RemoveMonitor_ACK:
				processRemoveMonitorACK(basemsg.content);
				break;
			case segseatlib.message_type.StopHistoryInfo_ACK:
				processStopHistoryInfoACK(basemsg.content);
				break;
			//下面是和处理警情相关
			case segseatlib.message_type.SetAlarmBusy_ACK:	//设置忙闲的应答
				processSetAlarmBusyACK(basemsg.content);
				break;
			case segseatlib.message_type.AllotAlarm:	//通信中心分配警情
				processAllotAlarm(basemsg.content);
				break;
			case segseatlib.message_type.PauseAlarm_ACK:	//通信中心应答挂警结果
				processPauseAlarmACK(basemsg.content);
				break;
			case segseatlib.message_type.CancelPauseAlarm_ACK:	//通信中心应答挂警结果
				processCancelPauseAlarmACK(basemsg.content);
				break;
			case segseatlib.message_type.HandleAlarm_ACK:	//通信中心应答处警结果
				processHandleAlarmACK(basemsg.content);
				break;
			case segseatlib.message_type.AskSeatList_ACK:	//通信中心应答坐席列表
				processAskSeatListACK(basemsg.content);
				break;
			case segseatlib.message_type.TransferAlarm_ACK:	//通信中心应答转警请求结果, 只有等到已经转到目的坐席才返回
				processTransferAlarmACK(basemsg.content);
				break;
			case segseatlib.message_type.AllotTransferAlarm://通信中心向转警目的坐席分配转警
				processAllotTransferAlarm(basemsg.content);
				break;
			case segseatlib.message_type.AskAlarmList_ACK://通信中心回复警情列表
				processAskAlarmListACK(basemsg.content);
				break;
			case segseatlib.message_type.NewAlarm_ACK:	//测试用，添加警情到通信中心，通信中心应答
				processNewAlarmACK(basemsg.content);
				break;
			default:
				break;
			}
		};

		//处理登录返回
		var processLoginACK = function(content) {
			try {
				var loginack = segseatlib.builder.build("Login_ACK");
				loginack = loginack.decode(content);
				if (loginack.retcode == segseatlib.resultcode.OK) {
					loginstatus = 2;
				} else {
					loginstatus = 0;
				}
				//如果登录成功，自动把原来的监控呼号添加
				if (loginack.retcode == segseatlib.resultcode.OK) {
					//登录时已经重新上传
					if (monitorcaller != null) {
						selfclient.addMonitor(monitorcaller, -1, false);
					}
					if (seatbusy != null) {
						selfclient.setAlarmBusy(username, seatid, seatbusy, seatbusycaller);
					}
				}
				//调用外部函数
				if (typeof(onlogin) != "undefined") {
					onlogin(selfclient, loginack.retcode, loginack.retmsg);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("Login ACK error:" +err);
		    }
		};
		//处理登录返回
		/*
		var processLogoutACK = function(content) {
			try {
				onlogout(selfclient);
				selfclient.close();
		    } catch (err) {
		    	SEGUtil_WS.showError("Logout ACK error:" +err);
		    }
		};*/
		//处理登录返回, 单元测试用
		/*
		var processActiveLinkACK = function(content) {
			try {
				onactivelink(selfclient);
		    } catch (err) {
		    	SEGUtil_WS.showError("ActiveLink ACK error:" +err);
		    }
		};*/
		
		//发送链路检测应答报文
		var processActiveLink = function(content) {
			var basemsg = new segseatlib.basemessage(segseatlib.message_type.ActiveLink_ACK);
			var msg = new segseatlib.centermessage({"messages":[basemsg]});
			selfclient.send(new Uint8Array(msg.toArrayBuffer()));
		};
		
		//添加监控列表应答
		var processAddMonitorACK = function(content) {
			try {
				var addmonitorack = segseatlib.builder.build("AddMonitor_ACK");
				addmonitorack = addmonitorack.decode(content);
				if (addmonitorack.retcode == segseatlib.resultcode.OK) {
					monitorcaller = addmonitorack.callLetters;
					if (monitorcaller != null && monitorcaller.length == 0) {
						monitorcaller = null;
					}
				}
				//调用外部函数
				if (typeof(onaddmonitor) != "undefined") {
					onaddmonitor(selfclient, addmonitorack.retcode, addmonitorack.retmsg, addmonitorack.callLetters);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("AddMonitor ACK error:" +err);
		    }
		};

		//取消监控列表应答
		var processRemoveMonitorACK = function(content) {
			try {
				var removeack = segseatlib.builder.build("RemoveMonitor_ACK");
				removeack = removeack.decode(content);
				if (removeack.retcode == segseatlib.resultcode.OK) {
					monitorcaller = removeack.callLetters;
					if (monitorcaller != null && monitorcaller.length == 0) {
						monitorcaller = null;
					}
				}
				//调用外部函数
				if (typeof(onremovemonitor) != "undefined") {
					onremovemonitor(selfclient, removeack.retcode, removeack.retmsg, removeack.callLetters);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("RemoveMonitor ACK error:" +err);
		    }
		};
	
		var processGetLastInfoACK = function(content) {
			try {
				var lastack = segseatlib.builder.build("GetLastInfo_ACK");
				lastack = lastack.decode(content);
				//调用外部函数
				if (lastack.gpses.length > 0 && typeof(ongetlastgps) != "undefined"){
					ongetlastgps(selfclient, lastack.retcode, lastack.retmsg, lastack.gpses);
				}
				if (lastack.travels.length > 0 && typeof(ongetlasttravel) != "undefined"){
					ongetlasttravel(selfclient, lastack.retcode, lastack.retmsg, lastack.travels);
				}
				if (lastack.faults.length > 0 && typeof(ongetlastfault) != "undefined"){
					ongetlastfault(selfclient, lastack.retcode, lastack.retmsg, lastack.faults);
				}
				if (lastack.operates.length > 0 && typeof(ongetlastoperate) != "undefined"){
					ongetlastoperate(selfclient, lastack.retcode, lastack.retmsg, lastack.operates);
				}
				if (lastack.sms.length > 0 && typeof(ongetlastsm) != "undefined"){
					ongetlastsm(selfclient, lastack.retcode, lastack.retmsg, lastack.sms);
				}
				if (lastack.alarms.length > 0 && typeof(ongetlastalarm) != "undefined"){
					ongetlastalarm(selfclient, lastack.retcode, lastack.retmsg, lastack.alarms);
				}
				if (lastack.obds.length > 0 && typeof(ongetlastobd) != "undefined"){
					ongetlastobd(selfclient, lastack.retcode, lastack.retmsg, lastack.obds);
				}
				if (lastack.retcode != 0 && typeof(ongetlasterror) != "undefined") {
					ongetlasterror(selfclient, lastack.retcode, lastack.retmsg);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("GetLastInfo ACK error:" +err);
		    }
		};

		var processGetHistoryInfoACK = function(content) {
			try {
				var historyack = segseatlib.builder.build("GetHistoryInfo_ACK");
				historyack = historyack.decode(content);
				//调用外部函数
				if (historyack.gpses.length > 0 && typeof(ongethistorygps) != "undefined"){
					ongethistorygps(selfclient, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.gpses);
				}
				if (historyack.travels.length > 0 && typeof(ongethistorytravel) != "undefined"){
					ongethistorytravel(selfclient, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.travels);
				}
				if (historyack.faults.length > 0 && typeof(ongethistoryfault) != "undefined"){
					ongethistoryfault(selfclient, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.faults);
				}
				if (historyack.operates.length > 0 && typeof(ongethistoryoperate) != "undefined"){
					ongethistoryoperate(selfclient, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.operates);
				}
				if (historyack.sms.length > 0 && typeof(ongethistorysm) != "undefined"){
					ongethistorysm(selfclient, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.sms);
				}
				if (historyack.alarms.length > 0 && typeof(ongethistoryalarm) != "undefined"){
					ongethistoryalarm(selfclient, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.alarms);
				}
				if (historyack.obds.length > 0 && typeof(ongethistoryobd) != "undefined"){
					ongethistoryobd(selfclient, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.obds);
				}
				if (historyack.retcode != 0 && typeof(ongethistoryerror) != "undefined") {
					ongethistoryerror(selfclient, historyack.retcode, historyack.retmsg);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("GetHistoryInfo ACK error:" +err);
		    }
		};
		
		var processGetHistorySimpleGpsInfoACK = function(content) {
			try {
				var historyack = segseatlib.builder.build("GetHistorySimpleGpsInfo_ACK");
				historyack = historyack.decode(content);
				//调用外部函数
				if (historyack.gpses.length > 0 && typeof(ongethistorysimplegps) != "undefined"){
					ongethistorysimplegps(selfclient, historyack.callLetter, historyack.retcode, historyack.retmsg, historyack.lastPage, historyack.gpses);
				}
				if (historyack.retcode != 0 && typeof(ongethistoryerror) != "undefined") {
					ongethistoryerror(selfclient, historyack.retcode, historyack.retmsg);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("GetHistoryInfo ACK error:" +err);
		    }
		};
		
		var processStopHistoryInfoACK =function(content) {
			try {
				var stopack = segseatlib.builder.build("StopHistoryInfo_ACK");
				stopack = stopack.decode(content);
				if (typeof(onstophistory) != "undefined") {
					onstophistory(selfclient, stopack.retcode, stopack.retmsg);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("GetHistoryInfo ACK error:" +err);
		    }
		};

		//
		var processDeliverGPS = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverGPS");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondelivergps) != "undefined") {
					ondelivergps(selfclient, deliver.gpsinfo, deliver.gatewayid, deliver.gatewaytype, deliver.alarmid, deliver.alarmname);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverGPS error:" +err);
		    }
		};
		
		var processDeliverAlarm = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverAlarm");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliveralarm) != "undefined") {
					ondeliveralarm(selfclient, deliver.alarminfo, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverAlarm error:" +err);
		    }
		};

		var processDeliverAppNotice = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverAppNotice");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliverappnotice) != "undefined") {
					ondeliverappnotice(selfclient, deliver.noticeinfo, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverAppNotice error:" +err);
		    }
		};
		
		var processDeliverOBD = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverOBD");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliverobd) != "undefined") {
					ondeliverobd(selfclient, deliver.obdinfo, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverOBD error:" +err);
		    }
		};
		
		var processDeliverUnitVersion = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverUnitVersion");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliverunitversion) != "undefined") {
					ondeliverunitversion(selfclient, deliver.unitVersion, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverUnitVersion error:" +err);
		    }
		};
		
		//
		var processDeliverOperateData = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverOperateData");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliveroperatedata) != "undefined") {
					ondeliveroperatedata(selfclient, deliver.data, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverOperateData error:" +err);
		    }
		};
	
		//
		var processDeliverSMS = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverSMS");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliversms) != "undefined") {
					ondeliversms(selfclient, deliver.msg, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverSMS error:" +err);
		    }
		};

		//
		var processDeliverUnitLoginOut = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverUnitLoginOut");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliverunitloginout) != "undefined") {
					ondeliverunitloginout(selfclient, deliver.callLetter, deliver.inorout, deliver.gatewayid);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverUnitLoginOut error:" +err);
		    }
		};

		//
		var processDeliverTravel = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverTravel");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondelivertrival) != "undefined") {
					ondelivertrival(selfclient, deliver.travelinfo, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverTravel error:" +err);
		    }
		};

		//
		var processDeliverFault = function(content) {
			try {
				var deliver = segseatlib.builder.build("DeliverFault");
				deliver = deliver.decode(content);
				//调用外部函数
				if (typeof(ondeliverfault) != "undefined") {
					ondeliverfault(selfclient, deliver.faultinfo, deliver.gatewayid, deliver.gatewaytype);
				}
		    } catch (err) {
		    	SEGUtil_WS.showError("DeliverFault error:" +err);
		    }
		};
		
		var processSendCommandACK = function(content) {
			try {
				var cmdack = segseatlib.builder.build("SendCommand_ACK");
				cmdack = cmdack.decode(content);
				//调用外部函数
				if (typeof(onsendcommand) != "undefined") {
					onsendcommand(selfclient, cmdack.sn, cmdack.callLetter, cmdack.cmdId,
						cmdack.retcode, cmdack.retmsg, cmdack.params, cmdack.gpsInfo);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("SendCommand ACK error:" +err);
		    };
		};
		
		var processSendCommandSendACK = function(content) {
			try {
				var cmdack = segseatlib.builder.build("SendCommandSend_ACK");
				cmdack = cmdack.decode(content);
				//调用外部函数
				if (typeof(onsendcommandsend) != "undefined") {
					onsendcommandsend(selfclient, cmdack.sn, cmdack.callLetter,
						cmdack.cmdId, cmdack.retcode, cmdack.retmsg);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("SendCommand ACK error:" +err);
		    };
		};
		
		var processGetCommandHistoryACK = function(content) {
			try {
				var historyack = segseatlib.builder.build("GetCommandHistory_ACK");
				historyack = historyack.decode(content);
				//调用外部函数
				if (typeof(ongetcommandhistory) != "undefined") {
					ongetcommandhistory(selfclient, historyack.retcode, historyack.retmsg,
							historyack.callLetter, historyack.plateno, historyack.commandinfoes);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("SendCommand ACK error:" +err);
		    };
		};
		
		var processSetAlarmBusyACK = function(content) {
			try {
				var ack = segseatlib.builder.build("SetAlarmBusy_ACK");
				ack = ack.decode(content);
				if (ack.retcode == segseatlib.resultcode.OK) {
					seatbusy = ack.busy;
					if (!seatbusy) {
						seatbusycaller = null;
					}
				}
				//调用外部函数
				if (typeof(onsetalarmbusy) != "undefined") {
					onsetalarmbusy(selfclient, ack.retcode, ack.retmsg, ack.username,
						ack.busy, ack.handleseatname);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("SetAlarmBusy ACK error:" +err);
		    };
		};
		
		//通信中心分配警情
		var processAllotAlarm = function(content) {
			try {
				var pkg = segseatlib.builder.build("AllotAlarm");
				pkg = pkg.decode(content);
				//部署环境下，自动接收处警要求
				selfclient.acceptAlarmACK(true, pkg.username, pkg.callLetter, pkg.alarmsn);
				//调用外部函数
				if (typeof(onallotalarm) != "undefined") {
					onallotalarm(selfclient, pkg.username, pkg.callLetter, pkg.alarmsn,
						pkg.alarmTime, pkg.alarmid, pkg.alarmname, pkg.gpsinfo,
						pkg.alarmcount, pkg.append);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("AllotAlarm error:" +err);
		    };
		};

		//通信中心应答挂警结果
		var processPauseAlarmACK = function(content) {
			try {
				var pkg = segseatlib.builder.build("PauseAlarm_ACK");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(onpausealarmack) != "undefined") {
					onpausealarmack(selfclient, pkg.retcode, pkg.retmsg, pkg.username,
						pkg.callLetter, pkg.alarmsn);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("PauseAlarm ACK error:" +err);
		    };
		};
		
		//通信中心应答挂警结果
		var processCancelPauseAlarmACK = function(content) {
			try {
				var pkg = segseatlib.builder.build("CancelPauseAlarm_ACK");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(oncancelpausealarmack) != "undefined") {
					oncancelpausealarmack(selfclient, pkg.retcode, pkg.retmsg, pkg.username,
						pkg.callLetter, pkg.alarmsn);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("PauseAlarm ACK error:" +err);
		    };
		};
		
		//通信中心应答处警结果
		var processHandleAlarmACK = function(content) {
			try {
				var pkg = segseatlib.builder.build("HandleAlarm_ACK");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(onhandlealarmack) != "undefined") {
					onhandlealarmack(selfclient, pkg.retcode, pkg.retmsg, pkg.username,
						pkg.callLetter, pkg.alarmsn);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("HandleAlarm ACK error:" +err);
		    };
		};
		
		//通信中心应答坐席列表
		var processAskSeatListACK = function(content) {
			try {
				var pkg = segseatlib.builder.build("AskSeatList_ACK");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(onaskseatlistack) != "undefined") {
					onaskseatlistack(selfclient, pkg.retcode, pkg.retmsg, pkg.seats);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("AskSeatList ACK error:" +err);
		    };
		};
		
		//通信中心应答转警请求结果, 只有等到已经转到目的坐席才返回
		var processTransferAlarmACK = function(content) {
			try {
				var pkg = segseatlib.builder.build("TransferAlarm_ACK");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(ontransferalarmack) != "undefined") {
					ontransferalarmack(selfclient, pkg.retcode, pkg.retmsg, pkg.srcusername, 
						pkg.dstusername, pkg.callLetter, pkg.alarmsn);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("TransferAlarm ACK error:" +err);
		    };
		};
		
		//通信中心向转警目的坐席分配转警
		var processAllotTransferAlarm = function(content) {
			try {
				var pkg = segseatlib.builder.build("AllotTransferAlarm");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(onallottransferalarm) != "undefined") {
					onallottransferalarm(selfclient, pkg.username, pkg.callLetter, pkg.alarmsn,
						pkg.alarmTime, pkg.alarmid, pkg.alarmname, pkg.gpsinfo,
						pkg.alarmcount, pkg.srcusername, pkg.transfermsg);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("AllotTransferAlarm error:" +err);
		    };
		};
		
		//通信中心回复警情列表
		var processAskAlarmListACK = function(content) {
			try {
				var pkg = segseatlib.builder.build("AskAlarmList_ACK");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(onaskalarmlistack) != "undefined") {
					onaskalarmlistack(selfclient, pkg.retcode, pkg.retmsg, pkg.alarms, pkg.alarmscount);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("AskAlarmList ACK error:" +err);
		    };
		};

		//通信中心回复坐席添加警情（测试用，坐席不主动添加警情）
		var processNewAlarmACK = function(content) {
			try {
				var pkg = segseatlib.builder.build("NewAlarm_ACK");
				pkg = pkg.decode(content);
				//调用外部函数
				if (typeof(onnewalarmack) != "undefined") {
					onnewalarmack(selfclient, pkg.retcode, pkg.retmsg, pkg.slaver, pkg.callLetter, pkg.alarmsn);
				};
		    } catch (err) {
		    	SEGUtil_WS.showError("AskAlarmList ACK error:" +err);
		    };
		};
		
		/*************************************************************************************
		* 连接服务器。多个服务器循环连接
		*************************************************************************************/
		this.connect = function() {
			ismanualclose = false;
		    if (!this.isConnected()) {
		        //websocket 重连，只有重新new
		    	socket = null;
		    	//有多个服务器可以连接 
		    	if (server == null || server.length == 0) {
		    		return;
		    	}
		    	if (serverid < 0 || serverid >= server.length) {
		    		serverid = 0;	
		    	}
		    	var url = "ws://" + server[serverid] + "/websocket/";
		    	if (server.length > 1) {
		    		serverid ++;
		    	}
		    	socket = new WebSocket(url);
		    	socket.binaryType = "arraybuffer";
		    	socket.onerror = function(event){
		    		//这里client如果用this, 表示是socket
					selfclient.errorEvent(event);
				};
				/*最好不要用这种直接=的方式，因为selfclient.errorEvent中的变量是socket环境，而不是jsclient
				socket.onerror = selfclient.errorEvent;
				socket.onopen = selfclient.openEvent;
				socket.onclose = selfclient.closeEvent;
				socket.onmessage = selfclient.receiveEvent;
				*/
				socket.onopen = function(event){				
		    		//这里client如果用this, 表示是socket
		    		selfclient.openEvent(event);
				};
				socket.onclose = function(event){				
		    		//这里client如果用this, 表示是socket
		    		selfclient.closeEvent(event);
				};
				socket.onmessage = function(event){				
		    		//这里client如果用this, 表示是socket
		    		selfclient.receiveEvent(event);
				};
		    };
		};
		
		this.timeout = function() {
			//定时只要发送心跳报文，不用定时重连
			var nowtime = new Date().getTime() - lasttime;
			if (selfclient.isLogined()) {
				if (nowtime > segseatlib.heartbeatinterval) {
					selfclient.activelink(); //测试时不发链路检测报文
				};
			};
			//有时可能连接上了。但登录没有返回, 或者连接失败
			//如果前面login 用了this, 而不是var, login则必须是selfclient.login
			if (selfclient.isConnected() && (loginstatus == 0) && nowtime > 2000) {
				lasttime = new Date().getTime();
				login();
			};
		};
		
		// 人工断开连接
		this.close = function() {
			ismanualclose = true;
			if (this.isConnected()) {
				logout();
				socket.close();
			}
		};
		
		//重新连接(单元测试用)
		//this.reconnect = function() {
		//	if (this.isConnected()) {
		//		//logout();     //不发退出报文
		//		socket.close();	//断开后会自动重连
		//	}
		//};

		//断开连接(不发退录报文，单元测试用)
		//this.closeconnect = function() {
		//	ismanualclose = true;
		//	if (this.isConnected()) {
		//		socket.close();
		//	}
		//};
		
		//结尾自动添加到
		segseatlib.addClient(selfclient);
	},
	
	/*************************************************************************************
	* 加密，压缩工具
	*************************************************************************************/
	Utils: {
		///-----------------------------------------------------------------------------
		//赛格通信中心WEBSOCKET协议加解密
		//DE_KEY[EN_KEY[i]] = i
		//-----------------------------------------------------------------------------
		seg_EN_KEY: [7, 2, 5, 4, 0, 1, 3, 6],	
		seg_DE_KEY: [4, 5, 1, 6, 3, 2, 7, 0],	
		encrypt: function(buff) {
			var length = buff.byteLength || buff.length;
			for(var i=0; i<length; i++) {
				var tmp = 0;
				var bit = 0;
				for(var j=0; j<8; j++) {
					bit = (1 << this.seg_EN_KEY[j]);
					if((buff[i] & bit) != 0) {
						tmp |= (1 << j);
					}
				}
				buff[i] = tmp;
			}
			return buff;
		},
		decrypt: function (buff) {
		  //var SEG_DE_KEY = [4, 5, 1, 6, 3, 2, 7, 0];	
		  var length = buff.byteLength || buff.length;
		  for(var i=0; i<length; i++) {
			  var tmp = 0;
			  var bit = 0;
			  for(var j=0; j<8; j++) {
				  bit = (1 << this.seg_DE_KEY[j]);
				  if((buff[i] & bit) != 0) {
					  tmp |= (1 << j);
				  }
			  }
			  buff[i] = tmp;
		  }
		  return buff;
		},
		
		compress: function(src) {
			try {
		    	var arr = new Uint8Array(src);
		        var deflate = new Zlib.Deflate(arr);
		        arr = deflate.compress();
		        return arr;
			}catch(err) {
				SEGUtil_WS.showError("压缩失败:" + err);
			}
			return null;
		},

		decompress: function(src) {
			try {
		    	var arr = new Uint8Array(src);
		        var inflate = new Zlib.Inflate(arr);
		        arr = inflate.decompress();
		        return arr;
			}catch(err) {
				SEGUtil_WS.showError("解压缩失败:" + err);
			}
			return null;
		},
		
		byteArrayCopy: function(dst, start, src, begin, end) {
			if (typeof(begin) === "undefined") begin = 0;
			if (typeof(end) === "undefined") end = src.byteLength;
			for(var i=begin; i<end && i<src.byteLength; i++) {
				dst[start+i-begin] = src[i];
			};
		},

		int32ToByteArray: function(buff, start, v) {
			buff[start] = (v >>> 24) & 0xFF;
			buff[start + 1] = (v >>> 16) & 0xFF;
			buff[start + 2] = (v >>> 8) & 0xFF;
			buff[start + 3] = (v) & 0xFF;
		},
	},
};	//end of segseatlib
