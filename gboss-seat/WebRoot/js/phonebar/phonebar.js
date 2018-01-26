$(document).ready(function() {
	initPhonebar();
});

//缓存最后通话的客户电话,用于绑定座席
var phonebar_last_customer_tel = null;

/**
 * 加载客户资料
 */
function loadCustomerData(customerTel){
	if(customerTel.indexOf("01") == 0 && customerTel.indexOf("010") != 0){
		customerTel = customerTel.substring(1, customerTel.length);
	};	
	phonebar_last_customer_tel = customerTel;
	$("#b_phoneCall").val(customerTel);
    //清空关键字
    $("#keyword").val('');
    var term = customerTel;
    search(term,1); //搜索基础资料
}

/**
 * 调用后台,绑定客户电话与座席
 * 绑定后客户下次来电会尽量分配到相同座席
 */

function bandingCustomerTelAndAgent(){
    //准备参数
    api_url = 'app/data/bindseat';   //url
    params = {phone:phonebar_last_customer_tel,seatNo:phonebarUtil.phonebar.UserID};    //参数
	if(phonebar_last_customer_tel != null){
	    //开始调用接口
	    $.getJSON(api_url, params, function(result){
	        if(result && result.success==false){
	            alert(result.message);    
	        };
	    });
		phonebar_last_customer_tel = null;
	}
}


/**
 * 开始验证服务密码
 */
function startSendCheckPasswordIVR(phone){
	var pbar = phonebarUtil.phonebar;
	try{
		var ivr = "验证密码.Nds";
		var param = "@phone=" + phone + "\n";
		pbar.ExecuteIVRFlow(pbar.Agent.CurrentConversation, pbar.Agent.CurrentCustomer, ivr, param, null, 'CheckPassword');
	}catch(e){
		alert("发送验证服务密码语音失败:" + e);
	}
}

/**
 * 验证服务密码回调
 */
function checkServicePasswordCallback(phone, pwd){
	if ($("#vehicle_id").val()){
		$("#service_password").val(pwd);
		var pwd_ser= $("#service_pwd").val();
		if (pwd!=pwd_ser){
			$("#service_password").css("color","#c32f31");
		}else{
			$("#service_password").css("color","#019b3f");
		};
	}else{
		alert('无车辆信息！');
	};
	// alert("电话:" + phone + ", 服务密码:" + pwd);
}

/**
 * 发送验证服务密码IVR
 */
function phonebar_send_pwd_ivr_clicked(){
	// var tel = $("#telegram_num").val();
	var tel = phonebar_last_customer_tel;
	startSendCheckPasswordIVR(tel);
}

/**
 * 设置监控车辆
 */
function phonebar_set_monitor_clicked(){
	var tel = $("#phonebar_monitor_callletter").val();
	phonebar_set_monitor_tel(tel);
	alert("已设置监听车辆:" + tel);
}

/**
 * 置单向
 */
function phonebar_refuse_invitations_clicked(){
	var pbar = phonebarUtil.phonebar;
	var statusCode = pbar.Agent.Status;
	if(statusCode & 0x0200){
		alert("已是单向!");
		return;
	}
	
	pbar.Agent.RefuseInvitations();
}

/**
 * 置双向
 */
function phonebar_allow_invitations_clicked(){
	var pbar = phonebarUtil.phonebar;
	var statusCode = pbar.Agent.Status;
	if(!(statusCode & 0x0200)){
		alert("已是双向!");
		return;
	}
	
	pbar.Agent.AllowInvitations();
}

/**
 * 初始化工作
 */
function initPhonebar(){
	var pbar = document.getElementById("PhoneBar20");
	phonebarUtil.phonebar = pbar;
	
	pbar.AutoPopupConversationWnd = false;	//受到邀请时是否弹出对话框
	//pbar.Color = 0x889700;				//颜色,格式0xBBGGRR
	pbar.ListVisible = false; 				//自带列表是否显示
	pbar.width = 720;						//宽度
	pbar.height = 30;						//高度
	//pbar.LoginPrompt = false; 			//是否弹出登录框
	
	try{
		var queue = new ActiveXObject("PhoneBar20.CCQueue");
		phonebarUtil.queue = queue;
		queue.ICMObjectSystem = pbar.BTChannel;
	}catch (e) {
		alert("创建等待队列失败,请调整浏览器安全设置!");
	}
	
	setConnectParams(pbar);
}

/**
 * 电话控件设置连接参数
 */
function setConnectParams(pbar){
	var BTChannel = pbar.BTChannel;
	
	//服务端参数(如果不弹出登录框需要从后台加载，弹出登录框则客服自己输入)
	var user = "1001";	//用户名
	var pwd = "";		//密码
	//服务端参数end
	
	//本地参数(电话地址)
    if (BTChannel) {
		var contact = BTChannel.Context;
		contact = contact.substring(contact.indexOf("contact=")
						+ "contact=".length, contact.length - 1);
		// 本地参数end

		BTChannel.Context = 'agentid=' + user + '\npassword=' + pwd
				+ '\ncontact=' + contact + '\n';
	}
	//自动连接
	//BTChannel.Connect();
}

/**
 * 电话控件连接成功(电话控件的事件)
 */
function OnHello() {
	loadPhonebarList();
}

/**
 * 启用/禁用 '取消邀请'按钮
 */
function setCancelInviteEnabled(enabled){
	if(enabled){
		$("#phonebar_call_out_cancel_btn").removeAttr("disabled");
	}else{
		$("#phonebar_call_out_cancel_btn").attr("disabled", "disabled");
	}
}

/**
 * 清除振铃状态及来电号码
 */
function clearRinging(){
	$("#phonebar_ring_status").val("");			//清除振铃提示
	$("#telegram_num").val("");			//清除来电号码
	
	clearTakeBackTask();
	
	bandingCustomerTelAndAgent();
}

/**
 * 更新振铃状态
 */
function updateRingingStatus(status){
	$("#phonebar_ring_status").val(status);
}

/**
 * 更新来电号码
 */
function updateRingingTel(tel){
	var telOper = tel;
	$("#telegram_num").val(tel);
	if(tel.indexOf("01") == 0 && tel.indexOf("010") != 0){
		telOper = tel.substring(1, tel.length);
	};		
	$("#telegram_num").val(tel);
	
}

/**
 * 离开会话(电话控件的事件)
 */
function OncnvLeaveX(locator, cnvOID, bMonitor, times){
	//保持-拾回 有这个事件，不能做为挂电话事件
	//呼叫-被挂断 只有这个事件
	if(cnvOID.indexOf("undefined$") == 0){
		//呼叫- 挂断/被挂断
		clearRinging();
	}
}

/**
 * 邀请结束(电话控件的事件)
 */
function OnInvitationCompleted(OID, failed, iReason, reason){
	//受邀请,拒绝
	if(failed){
		clearRinging();
	}
}

/**
 * 监听、插话、代答、主动接听(正在振铃)(电话控件的事件)
 */
function OnswiSwitchingX(locator, swiOID, cnvOID, customerContext, times){
	//customerContext为空
	var stype = null;
	var idx = swiOID.indexOf('$');
	if(idx != -1){
		stype = swiOID.substring(0, idx);
	}
	
	if(stype == "代答"){
		var tel = phonebarUtil.getAnswerForOtherSeatCustomerTel();
		updateRingingStatus("正在振铃");
		updateRingingTel(tel);
	}
}

/**
 * 监听、插话、代答、主动接听(取消)(电话控件的事件)
 */
function OnswiCancelledX(locator, swiOID, cnvOID, customerContext, iReason, reason, times){
	var stype = null;
	var idx = swiOID.indexOf('$');
	if(idx != -1){
		stype = swiOID.substring(0, idx);
	}
	
	if(stype == "代答"){
		clearRinging();
	}
}

/**
 * 监听、插话、代答、主动接听(接通)(电话控件的事件)
 */
function OnswiSucceedX(locator, swiOID, cnvOID, customerContext, times){
	var stype = null;
	var idx = swiOID.indexOf('$');
	if(idx != -1){
		stype = swiOID.substring(0, idx);
	}
	
	if(stype == "代答"){
		var customerTel = phonebarUtil.getParamFromContext(customerContext, "calloutno");
		var index = customerTel.indexOf("@");
		if(index != -1){
			customerTel = customerTel.substring(0, index);
		}
		
		//加载资料
		loadCustomerData(customerTel);
	}
}

/**
 * 邀请取消(电话控件的事件)
 */
function OninvCancelledX(locator, invOID, cnvOID, usrOID, customerOID, customerContext, flags, iReason, Reason, Time){
	var stype = null;
	var idx = invOID.indexOf('$');
	if(idx != -1){
		stype = invOID.substring(0, idx);
	}
	
	if(stype == "呼叫线路" || stype == "呼叫" || stype == "服务请求"){
		//var mflag = flags & 0x2;
		//if(mflag != 0){
		//	//呼出
		//	clearRinging();
		//}
		clearRinging();
	}
}

/**
 * 动作完成
 */
function OnOperationCompleted(oid, result,iReason, reason, appData){
	var stype = null;
	var idx = oid.indexOf('$');
	if(idx != -1){
		stype = oid.substring(0, idx);
	}
	
	if(stype == "46"){
		//保持
		startTakeBackTask();
	}else if(stype == "47"){
		//拾回
		clearTakeBackTask();
	}
}

/**
 * 更新等待数量
 */
function updateWaitCount(count){
	$("#phonebar_wait_count").val(count);
	if (count==''||count=='0'){
		$("#queueInfo").css("color","#333");
	}else{
		$("#queueInfo").css("color","#c32f31");
	};
}

/**
 * 进行保持状态后,20秒后自动拾回
 */
var phonebar_take_back_task = null;
var phonebar_take_back_time = 20000;

function startTakeBackTask(){
	if(phonebar_take_back_task != null){
		return;
	}
	
	var refreshInteval = 1000;
	var sumTime = 0;
	
	$("#phonebar_takeback_time").css({display: "block"});
	$("#phonebar_takeback_time").text(parseInt(phonebar_take_back_time/1000) + "秒后自动拾回通话");
	
	phonebar_take_back_task = setInterval(function(){
		sumTime += refreshInteval;
		var left = phonebar_take_back_time - sumTime;
		if(left <= 0){
			//时间到
			clearTakeBackTask();
			try{
				var pbar = phonebarUtil.phonebar;
				pbar.Agent.ExecOpt('47', null, null, null, null);
			}catch(e){
				alert("自动拾回通话失败!" + e);
			}
		}else{
			$("#phonebar_takeback_time").text(parseInt(left/1000) + "秒后自动拾回通话");
		}
	}, refreshInteval);
}

function clearTakeBackTask(){
	if(phonebar_take_back_task != null){
		clearInterval(phonebar_take_back_task);
		phonebar_take_back_task = null;
	}
	
	$("#phonebar_takeback_time").css({display: "none"});
}

/**
 * 更新座席状态(电话控件的事件)
 */
function OnStatus(statusCode){
	var pbar = phonebarUtil.phonebar;
	if((statusCode & 0x50) == 0x10){
		//接待客户 且 不是小休
		pbar.StartRecord(null, null);
		//alert("start record:" + statusCode);
	}

	if(!(statusCode & -1)){
		//"就绪";
		clearRinging();
		return;
	}
		
	if(statusCode & 0x0800){
		//"锁定";(不处理)
		return;
	}
		
	if(statusCode & 0x0004){
		//"正在振铃";
		updateRingingStatus("正在振铃");
		return;
	}
		
	if(statusCode & 0x0020){
		//"监听";(不处理)
		return;
	}
		
	if(statusCode & 0x0010){
		//"接待客户";
		updateRingingStatus("接待客户");
		return;
	}
		
	if(statusCode & 0x0008){
		//"正在通话";
		updateRingingStatus("正在通话");
		return;
	}
		
	if(statusCode & 0x0040){
		//"保持";
		updateRingingStatus("保持");
		return;
	}
		
	if(statusCode & 0x0100){
		//"事后处理";
		clearRinging();
		return;
	}
		
	if(statusCode & 0x0200){
		//"请勿打扰";(不处理)
		return;
	}
		
	if(statusCode & 0x0400){
		//"小休";(不处理)
		return;
	}
		
	if(statusCode & 0x0001){
		//"忙";
		//呼叫   请勿打扰-忙
		//clearRinging();
		return;
	}
}

/**
 * 振铃，在这里显示振铃状态及来电号码(电话控件的事件)
 */
var isCallOut = false;

function OninvInvitingX(Locator, invOID, cnvOID, userOID, CustomerOID, CustomerContext, CustomerContact, Flags,Dates){
	var idx = invOID.indexOf("$");
	var stype = invOID.substring(0, idx);
	var comingCall = null;
	
	isCallOut = false;

	if(stype == "服务请求"){
		//被客户呼叫
		comingCall = CustomerContact;
		var spIdx = comingCall.indexOf('@');
		if(spIdx != -1){
			comingCall = comingCall.substring(0, spIdx);
		}
	}else if(stype == "呼叫线路"){
		//外呼
		setCancelInviteEnabled(true);
		
		updateRingingStatus("对方振铃");
		comingCall = phonebarUtil.lastCallOutTel;

		isCallOut = true;
	}else if(stype == "呼叫" ){
		//呼叫
		
		//有呼入和呼出的情况
		var mflag = Flags & 0x2;
		var agent = phonebarUtil.getAgentByUserOID(userOID);
		if(agent != null){
			var name = phonebarUtil.getParamFromContext(agent.Context, "phonebar.username");
			var callType = (mflag == 0)? "被呼叫": "呼叫";
			comingCall = "[" + callType + "]" + name;
			
			if(mflag != 0){
				updateRingingStatus("对方振铃");
			}
		}
	}else if(stype == "邀请" || stype == "转接"){
		//邀请
		//转接 
		
		//都有呼入和呼出的情况(呼出不处理，保持显示当前客户信息)
		var mflag = Flags & 0x2;
		if(mflag != 0){
			//呼出
			isCallOut = true;
			return;
		}
		
		var agent = phonebarUtil.getAgentByUserOID(userOID);
		if(agent != null){
			var name = phonebarUtil.getParamFromContext(agent.Context, "phonebar.username");
			comingCall = "[座席" + stype + "]" + name;
			
			var customerTel = phonebarUtil.getTelFromContact(CustomerContact);
			if(customerTel){
				comingCall += " 客户电话:" + customerTel;
			}
		}
	}else{
		//未处理的类型
		return;
	};
	updateRingingTel(comingCall);
	var CallNum = $("#telegram_num").val();
	if ($("#example20>tbody>tr>td").hasClass("dataTables_empty")){
		$("#example20>tbody>tr>td.dataTables_empty").parent().remove();
	};	
	if (isCallOut){
		$("#callout_title").tab("show");
        $("#example20").prepend('<tr><td>'+CallNum+'</td><td>呼出</td><td>'+ReDate()+'</td></tr>');
	}else{
		$("#callin_title").tab("show");
        $("#example20").prepend('<tr><td>'+CallNum+'</td><td>呼入</td><td>'+ReDate()+'</td></tr>');
	};	
    localStorage.setItem("CallLog_list",$("#example20 tbody").html());
};

/**
 * 邀请成功(接起电话) 在这里加载客户资料(呼入时加载资料)(电话控件的事件)
 */
function OninvSucceedX(Locator,invOID,cnvOID,usrOID,CustomerOID,CustomerContext,CustomerContact,Flags,Times){
	var idx = invOID.indexOf("$");
	var stype = invOID.substring(0, idx);
	
	if(stype == "服务请求" || stype == "呼叫线路"){
		//客户呼叫
		//外呼
		var customerTel = phonebarUtil.getTelFromContact(CustomerContact);
		loadCustomerData(customerTel);
		setCancelInviteEnabled(false);
	}else if(stype == "呼叫"){
		//座席呼叫
		//有呼入和呼出的情况
	}else if(stype == "转接" || stype == "邀请"){
		//转接
		//邀请
		
		//都有呼入和呼出的情况(呼出不处理,跟振铃状态对应,振铃时不处理，这里也不处理)
		var mflag = Flags & 0x2;
		if(mflag != 0){
			//呼出
			return;
		}
		
		var customerTel = phonebarUtil.getTelFromContact(CustomerContact);
		if(customerTel){			
			loadCustomerData(customerTel);
		}
	}
}

/**
 * 语音回调
 */
 
function OnExecuteResult(responser, appID, errCode, result, appData){
	//alert("OnExecuteResult\nresponser:" + responser + "\nappID:" + appID + "\nerrCode:" + errCode + "\nresult:" + result + "\nappData:" + appData);	
	if(appData == 'CheckPassword'){
		if(errCode != 0){
			alert("IVR回调错误:" + appData);
			return;
		}else{
			var phone = phonebarUtil.getParamFromContext2(result, "phone");
			var pwd = phonebarUtil.getParamFromContext2(result, "pwd");
			
			checkServicePasswordCallback(phone, pwd);
		}
	}else{
		if(errCode != 0){
			// alert("OnExecuteResult\nresponser:" + responser + "\nappID:" + appID + "\nerrCode:" + errCode + "\nresult:" + result + "\nappData:" + appData);
		}
	}
}

/**
 * 拨打外线号码
 */
function phonebar_call_out_clicked(){
	var tel = document.getElementById("phonebar_call_out_tel").value;
	phonebarUtil.callOut(tel);
}

/**
 * 拨打外线时取消
 */
function phonebar_call_out_cancel_clicked(){
	phonebarUtil.cancelInvite();
}

/**
 * 转外线(代拨)
 */
function phonebar_change_out_clicked(){
	var tel = document.getElementById("phonebar_change_out_tel").value;
	phonebarUtil.redirectOut(tel);
}

/**
 * 邀请外线
 */
function phonebar_invite_out_clicked(){
	var tel = document.getElementById("phonebar_invite_out_tel").value;
	phonebarUtil.inviteOut(tel);
}

/**
 * 加载座席列表
 */
var loadListTask = null;										//加载列表任务
var pb_all_seat_batch_index = 0;								//加载全部座席任务批次
var phonebar_all_seat_table_id = "phonebar_all_seat_table";		//全部座席表格Id

var phonebar_monitor_table_id = "phonebar_monitor_table";		//监听队列表格Id
var phonebar_wait_table_id = "phonebar_wait_table";				//等待队列表格Id
var pb_wait_batch_index = 0;									//加载等待队列任务批次

/**
 * 加载全部座席列表,监听队列，等待队列
 */
function loadPhonebarList(){
	if(loadListTask != null){
		return;
	}
	
	//初始化列表右键菜单
	initAllSeatMenu();
	initWaitListMenu(phonebar_monitor_table_id, "phonebar_monitor_list_menu");
	initWaitListMenu(phonebar_wait_table_id, "phonebar_wait_list_menu");
	
	//开始加载列表(定时任务)
	loadListTask = setInterval(function(){
		var pbar = phonebarUtil.phonebar;
		if(pbar && pbar.connected){
			loadAllSeat();
			loadWaitList();
		}else{
			$("#" + phonebar_all_seat_table_id + " tbody tr").remove();
			$("#" + phonebar_monitor_table_id + " tbody tr").remove();
			$("#" + phonebar_wait_table_id + " tbody tr").remove();
		}
	}, 1000);
}

/**
 * 加载全部座席列表(定时运行内容)
 */
function loadAllSeat(){
	var pbar = phonebarUtil.phonebar;
	var agentList = pbar.AgentList;
	getIcmTime(pbar.BTChannel.DeltaTime);
	var time = new Date(icmTime);
	
	pb_all_seat_batch_index ++;
	
	for(var i = 0; i < agentList.count; i++){
		var agent;
		try{
			agent = agentList.Agents(i);
		}catch (e) {
			//数组越界(线程同步问题)
			break;
		}
		
		var rowId = agent.UserOID;		
		var sel = "#" + phonebar_all_seat_table_id + " tbody tr:contains(" + rowId + ")";
		var tr = $(sel);
		if(tr.length == 0){		
			var row_new = "<tr><td>" + phonebarUtil.getParamFromContext(agent.Context, "phonebar.username") + "</td><td>"
			+ agent.AgentOID + "</td><td>" + phonebarUtil.getStatusName(agent.Status) + "</td><td>"
			+ phonebarUtil.getLastTime(agent.statusTime, time) + "</td><td>" 
			+ phonebarUtil.parseGroupCN(pbar, agent.Groups) + "</td><td>" 
			+ phonebarUtil.getContacts(agent.Contacts) 
			+ "</td><td class='hide'>" + rowId + "</td><td class='hide'>" + pb_all_seat_batch_index + "</td><td class='hide'>"
			+ agent.Status + "</td></tr>";
			
			$("#" + phonebar_all_seat_table_id + " tbody").append(row_new);
		}else{
			var status_name = phonebarUtil.getStatusName(agent.Status);
			var status_last_time = phonebarUtil.getLastTime(agent.statusTime, time);
			$(tr).find("td:eq(2)").text(status_name);
			$(tr).find("td:eq(3)").text(status_last_time);
			$(tr).find("td:eq(7)").text(pb_all_seat_batch_index);
			$(tr).find("td:eq(8)").text(agent.Status);
		}
	}
	
	clearOldAllSeat();
}

function clearOldAllSeat(){
	var trs = $("#" + phonebar_all_seat_table_id + " tbody tr");
	for(var i = 0; i < trs.length; i++){
		var tri = $(trs[i]);
		var txt = tri.find("td:eq(7)").text();
		if(txt != pb_all_seat_batch_index){
			tri.remove();
		}
	}
}

/**
 * 选中table的某一行，底色改变(mapindex.js已有，合并时可以去掉)
 */
function selectTr(trNode, cancelAble){
	var seled = $(trNode).hasClass("row_selected_bc");
	if(seled){
		if(cancelAble){
			$(trNode).removeClass("row_selected_bc");
		}
    }else {
    	$(trNode).siblings("tr").removeClass("row_selected_bc");
    	$(trNode).addClass("row_selected_bc");
    }
}

/**
 * 全部座席列表右键菜单
 */
function initAllSeatMenu(){
	$("#" + phonebar_all_seat_table_id).on("contextmenu", "tbody tr", function(event){
		//选中表格的行
		var seled = $(this).hasClass("row_selected_bc");
		if(!seled){
			selectTr(this);
		}
		
		//调整菜单位置
		var menu = "#phonebar_all_seat_menu";
		var width = $(menu).width();
		var height = $(menu).height();
		var allWidth = $(document.body).width();
		var allHeight = $(document.body).height();
		var left = event.pageX;
		var top = event.pageY;
		
		if(left + width + 5 > allWidth){
			left = allWidth - width - 5;
		}

		if(top + height + 5 > allHeight){
			top = allHeight - height - 5;
		}
		
		$(menu).css({
		    display: "block",
		    left: left,
		    top: top
		});
		
		//启用禁用菜单项
		var agentId = $(this).find("td:eq(1)").text();
		var agentStatusCode = $(this).find("td:eq(8)").text();
		var flag = phonebarUtil.getAllSeatMenuStatus(agentId, agentStatusCode);
		
		for(var i = 1; i <= 6; i++){			
			if(flag.status[i - 1] == 0){
				$("#phonebar_all_seat_menu" + i).attr("disabled", "disabled");
			}else{
				$("#phonebar_all_seat_menu" + i).removeAttr("disabled");
			}
		}
		
		//解锁是否可见
		var unlockVisiable = (flag.flag_lock == 0);
		$("#phonebar_all_seat_menu4").attr("ltype", flag.flag_lock);
		if(unlockVisiable){
			$("#phonebar_all_seat_menu4").text("解锁");
		}else{
			$("#phonebar_all_seat_menu4").text("锁定");
		}
		
		return false;
	});
}

/**
 * 隐藏全部座席列表右键菜单
 */
function phonebar_all_seat_cancel_clicked(){
	$("#phonebar_all_seat_menu").css({display: "none"});
}

/**
 * 代答按钮按下
 */
function phonebar_all_seat_answer_clicked(){
	var userOID = $("#" + phonebar_all_seat_table_id + " .row_selected_bc").find("td:eq(6)").text();
	phonebarUtil.answerForOtherSeat(userOID);
	
	$("#phonebar_all_seat_menu").css({display: "none"});
}

/**
 * 监听按钮按下
 */
function phonebar_all_seat_monitor_clicked(){
	var userOID = $("#" + phonebar_all_seat_table_id + " .row_selected_bc").find("td:eq(6)").text();
	var pbar = phonebarUtil.phonebar;
	try{
		pbar.Agent.ExecOpt('25', null, null, userOID, null);
	}catch(e){
		alert("监听失败:" + e);
	}
	
	$("#phonebar_all_seat_menu").css({display: "none"});
}

/**
 * 插话按钮按下
 */
function phonebar_all_seat_chimein_clicked(){
	var userOID = $("#" + phonebar_all_seat_table_id + " .row_selected_bc").find("td:eq(6)").text();
	var pbar = phonebarUtil.phonebar;
	try{
		pbar.Agent.ExecOpt('30', null, null, userOID, null);
	}catch(e){
		alert("插话失败:" + e);
	}
	
	$("#phonebar_all_seat_menu").css({display: "none"});
}

/**
 * 锁定按钮按下
 */
function phonebar_all_seat_lock_clicked(){
	var agentOID = $("#" + phonebar_all_seat_table_id + " .row_selected_bc").find("td:eq(1)").text();
	var pbar = phonebarUtil.phonebar;
	var ltype = $("#phonebar_all_seat_menu4").attr("ltype");
	if(ltype == 1){
		//锁定
		try{
			pbar.Agent.ExecOpt('66', null, null, agentOID, null);
		}catch(e){
			alert("锁定失败:" + e);
		}
	}else{
		//解锁
		try{
			pbar.Agent.ExecOpt('67', null, null, agentOID, null);
		}catch(e){
			alert("解锁失败:" + e);
		}
	}
	
	$("#phonebar_all_seat_menu").css({display: "none"});
}

/**
 * 强制签退按钮按下
 */
function phonebar_all_seat_signout_clicked(){
	var agentOID = $("#" + phonebar_all_seat_table_id + " .row_selected_bc").find("td:eq(1)").text();
	var pbar = phonebarUtil.phonebar;
	try{
		pbar.Agent.ExecOpt('68', null, null, agentOID, null);
	}catch(e){
		alert("强制签退失败:" + e);
	}
	
	$("#phonebar_all_seat_menu").css({display: "none"});
}

/**
 * 允许小憩按钮按下
 */
function phonebar_all_seat_allownap_clicked(){
	var agentOID = $("#" + phonebar_all_seat_table_id + " .row_selected_bc").find("td:eq(1)").text();
	var pbar = phonebarUtil.phonebar;
	
	try{
		pbar.Agent.ExecOpt('65', null, null, agentOID, null);
	}catch(e){
		alert("允许小憩失败:" + e);
	}
	
	$("#phonebar_all_seat_menu").css({display: "none"});
}

/**
 * 添加或更新等待队列
 */
function updateWaitListTables(tableId, pbar, customer, skill){
	var oid = customer.OID;
	var sel = "#" + tableId + " tbody tr:contains(" + oid + ")";
	var tr = $(sel);
	if(tr.length == 0){
		//add new
		var customerName = phonebarUtil.getParamFromContext(customer.Context, "calloutno");
		var requestSkill = customer.ContextValues('@request_skill');
		var requestSkillName = phonebarUtil.parseGroupCN(pbar, requestSkill);
		var callEnd = customerName.indexOf('@');
		var comingCall = customerName.substring(0, callEnd);
		
		//提醒监听电话已到
		if(requestSkill == '100' && comingCall == phonebar_monitor_tel){
			phonebar_monitor_tel_calling(comingCall);
		}
		
		var birthTime = new Date(customer.BirthTime);
		getIcmTime(pbar.BTChannel.DeltaTime);
		var time = new Date(icmTime);
		
		var birthTimeStr = phonebarUtil.formatTime(birthTime);
		var lastTime = phonebarUtil.getLastTime(birthTime, time);
		
		var assignedContext = customer.AgentContext;
		var assignedSeat = phonebarUtil.parseNull(phonebarUtil.getParamFromContext(assignedContext, "phonebar.username"));
		
		var row = "<tr>"
			+ "<td>" + customerName + "</td>"
			+ "<td>" + requestSkillName + "</td>"
			+ "<td>" + comingCall + "</td>"
			+ "<td>" + birthTimeStr + "</td>"
			+ "<td>" + lastTime + "</td>"
			+ "<td>" + assignedSeat + "</td>"
			+ "<td class='hide'>" + oid + "</td>"
			+ "<td class='hide'>" + pb_wait_batch_index + "</td>"
			+ "</tr>";
		$("#" + tableId + " tbody").append(row);
	}else{
		//update old
		var birthTime = new Date(customer.BirthTime);
		getIcmTime(pbar.BTChannel.DeltaTime);
		var time = new Date(icmTime);
		
		var lastTime = phonebarUtil.getLastTime(birthTime, time);
		var assignedContext = customer.AgentContext;
		var assignedSeat = phonebarUtil.parseNull(phonebarUtil.getParamFromContext(assignedContext, "phonebar.username"));
		
		$(tr).find("td:eq(4)").text(lastTime);
		$(tr).find("td:eq(5)").text(assignedSeat);
		$(tr).find("td:eq(7)").text(pb_wait_batch_index);
	}
}

/**
 * 删除等待队列中已结束的
 */
function clearOldWaitList(){
	var trs = $("#" + phonebar_monitor_table_id + " tbody tr");
	for(var i = 0; i < trs.length; i++){
		var tri = $(trs[i]);
		var txt = tri.find("td:eq(7)").text();
		if(txt != pb_wait_batch_index){
			tri.remove();
		}
	}
	
	trs = $("#" + phonebar_wait_table_id + " tbody tr");
	for(var i = 0; i < trs.length; i++){
		var tri = $(trs[i]);
		var txt = tri.find("td:eq(7)").text();
		if(txt != pb_wait_batch_index){
			tri.remove();
		}
	}
}

/**
 * 加载等待队列(定时任务)
 */
function loadWaitList(){
	var pbar = phonebarUtil.phonebar;
	var queue = phonebarUtil.queue;
	if(!queue){
		return;
	}
	
	pb_wait_batch_index ++;
	updateWaitCount(queue.count);
	
	for(var i = 0; i < queue.count; i++){
		var customer;
		try{
			customer = queue.Customers(i);
		}catch (e) {
			break;
		}
		
		var requestSkill = customer.ContextValues('@request_skill');
		if(requestSkill == '100'){
			//监听队列
			updateWaitListTables(phonebar_monitor_table_id, pbar, customer, requestSkill);
		}else{
			updateWaitListTables(phonebar_wait_table_id, pbar, customer, requestSkill);
		}
	}
	
	clearOldWaitList();
}

/**
 * 监听队列接听按钮按下
 */
function phonebar_monitor_list_answer_clicked(){
	var oid = $("#" + phonebar_monitor_table_id + " .row_selected_bc").find("td:eq(6)").text();
	var pbar = phonebarUtil.phonebar;
	
	try{
		pbar.Agent.ExecOpt('23', null, null, oid, null);
	}catch(e){
		alert("接听失败:" + e);
	}
	
	$("#phonebar_monitor_list_menu").css({display: "none"});
}

/**
 * 监听队列取消按钮按下
 */
function phonebar_monitor_list_cancel_clicked(){
	$("#phonebar_monitor_list_menu").css({display: "none"});
}

/**
 * 等待队列接听按钮按下
 */
function phonebar_wait_list_answer_clicked(){
	var oid = $("#" + phonebar_wait_table_id + " .row_selected_bc").find("td:eq(6)").text();
	var pbar = phonebarUtil.phonebar;
	
	try{
		pbar.Agent.ExecOpt('23', null, null, oid, null);
	}catch(e){
		alert("接听失败:" + e);
	}
	
	$("#phonebar_wait_list_menu").css({display: "none"});
}

/**
 * 等待队列取消按钮按下
 */
function phonebar_wait_list_cancel_clicked(){
	$("#phonebar_wait_list_menu").css({display: "none"});
}

/**
 * 初始化"等待队列"右键菜单(监听队列也属于等待队列，是等待队列的一个分支)
 */
function initWaitListMenu(tableId, menuId){
	$("#" + tableId).on("contextmenu", "tbody tr", function(event){
		//选中表格的行
		var seled = $(this).hasClass("row_selected_bc");
		if(!seled){
			selectTr(this);
		}
		
		//调整菜单位置
		var width = $("#" + menuId).width();
		var height = $("#" + menuId).height();
		var allWidth = $(document.body).width();
		var allHeight = $(document.body).height();
		var left = event.pageX;
		var top = event.pageY;
		
		if(left + width + 5 > allWidth){
			left = allWidth - width - 5;
		}

		if(top + height + 5 > allHeight){
			top = allHeight - height - 5;
		}
		
		$("#" + menuId).css({
		    display: "block",
		    left: left,
		    top: top
		});
		
		return false;
	});
}

/**
 * 向车台发送监听指令后，记录监听的号码，当该号码拨打电话上来时，进行提示
 */
var phonebar_monitor_tel = null;

function phonebar_set_monitor_tel(tel){
	phonebar_monitor_tel = tel;
}

/**
 * 已发送监听指令的车台  回拨电话的事件，在这里进行提醒
 */
function phonebar_monitor_tel_calling(tel){
	phonebar_monitor_tel = null;
	
	alert("监听电话已到:" + tel);
}