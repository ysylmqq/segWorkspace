/********************************************************************************************
 * 下面是和警情相关
 */
//处警置忙，不接受新警情（除非特殊呼号）
function SeatBusy() {
	try {
		if (client == null) {
			return;
		}
		client.setAlarmBusy(txtseatname.value, txtseatid.value, true, txtalarmcaller.value);
	} catch (err) {
		alert(err);
	}
};

//处警置闲，接受新警情
function SeatIdle() {
	try {
		if (client == null) {
			return;
		}
		client.setAlarmBusy(txtseatname.value, txtseatid.value, false);
	} catch (err) {
		alert(err);
	}
};

//接受新的警情，预处理
function AcceptAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.acceptAlarmACK(true, txtseatname.value, txtalarmcaller.value, txtalarmsn.value);
		alert(txtseatname.value+"此座席已接警");
	} catch (err) {
		alert(err);
	}
};

//拒绝新的警情，不处理
function RefuseAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.acceptAlarmACK(false, txtseatname.value, txtalarmcaller.value, txtalarmsn.value);
	} catch (err) {
		alert(err);
	}
};

//挂警
function PauseAlarm() {
	try {
		if (client == null) {
			return;
		}
		//默认继续接受警情
		client.pauseAlarm(txtseatname.value, txtalarmcaller.value, txtalarmsn.value, "等待客户回复电话");
	} catch (err) {
		alert(err);
	}
};

//结束处警
function HandleAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.HandleAlarm(txtseatname.value, txtalarmcaller.value, txtalarmsn.value,
				segseatlib.resultcode.OK, "处警结束");
		alert(txtalarmsn.value+"已结束本次处警");
	} catch (err) {
		alert(err);
	}
};

//请求坐席列表
function AskSeatList() {
	try {
		if (client == null) {
			return;
		}
		client.AskSeatList(txtseatname.value, txtalarmcaller.value);
	} catch (err) {
		alert(err);
	}
};

//请求未处理完的警情列表
function AskAlarmList() {
	try {
		if (client == null) {
			return;
		}
		client.AskAlarmList(txtseatname.value);	//只能自己处理的警情（班长或总监才能取）
	} catch (err) {
		alert(err);
	}
};

//转警
function TransferAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.TransferAlarm(txtseatname.value, txttransferseatname.value, 
				txtalarmcaller.value, txtalarmsn.value, "我不清楚怎么处理这种情况，请协助！");
	} catch (err) {
		alert(err);
	}
};

//接受转警
function AcceptTransferAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.AllotTransferAlarmACK(true, txtseatname.value, txtalarmcaller.value,
				txtalarmsn.value, txttransferseatname.value);
	} catch (err) {
		alert(err);
	}
};

//拒绝转警
function RefuseTransferAlarm() {
	try {
		if (client == null) {
			return;
		}
		client.AllotTransferAlarmACK(false, txtseatname.value, txtalarmcaller.value,
				txtalarmsn.value, txttransferseatname.value);
	} catch (err) {
		alert(err);
	}
};

//添加测试警情
function NewAlarm() {
	try {
		if (client == null) {
			return;
		}
		var callletters = txtcallletters.value.split(";");
		txtsn.value = Math.uuid();
		client.NewAlarm(txtseatname.value, callletters[0], txtsn.value, new Date().getTime(), txtalarmstatusid.value, txtalarmlevel.value,
				new Date().getTime(), true, 112000000 + Math.random() * 100000, 22000000 + Math.random() * 100000,
				10 + Math.random() * 20, 1, "1,2,5,6,201");
	} catch (err) {
		alert(err);
	}
};

