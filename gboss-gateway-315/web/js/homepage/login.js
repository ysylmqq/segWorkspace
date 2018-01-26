function login(){
	var loginBtn = document.getElementById('loginBtn');
	var userName = document.getElementById('userName').value;
	var passWord = document.getElementById('passWord').value;
	var loginCode = document.getElementById('loginCode').value;

	if(!userName || !passWord || userName=="" || passWord == ""){
		alert("用户名或密码不能为空!");
		return;
	}
	if(!loginCode){
		alert("请输入验证码!");
		return;
	}
	
	loginBtn.disabled  = true;
	Ext.Ajax.request({
		url: "login",
		method: "post",
		timeout: 10000,
		params:{
			userName: userName,
			passWord: passWord,
			loginCode: loginCode,
			type: 'login'
		},success:function(res){
			loginBtn.disabled  = false;
			showResponse(res);
		},failure:function(res){
			loginBtn.disabled  = false;
			alert("连接服务器失败,请稍后再试!");
		}
	});
}
function reset(){
	document.getElementById('userName').value="";
	document.getElementById('passWord').value="";
	document.getElementById('loginCode').value="";
}

function showResponse(res){
	var txt = res.responseText;
	if(!txt){
		alert('服务器内部错误,请稍候再试!');
		return;
	}
	
	var json = Ext.decode(txt);
	if(json.success){
		gotoIndex();
	}else{
		refresh();
		var reason = json.reason;
		var msg = "登录失败！";
		if(reason){
			msg += getReasonInfo(reason);
		}
		
		alert(msg);
	}
}

document.onkeydown=function(e){
	var keyCode;
	if(!e){
		keyCode = event.keyCode;
	}else{
		keyCode = e.which;
	}
	if(keyCode == 13){
		login();
	}
};

function gotoIndex(){ 
	location.href = "index.html";
}

function checkSessionFail(){ 
	document.body.style.display = "inline";
	refresh();
	document.getElementById('userName').focus();
}

function refresh(){
	var loginCodeImg = document.getElementById('loginCodeImg');
	loginCodeImg.src = "login?type=getCertPic&width=55&height=22&key=" + Math.random();
}

Ext.onReady(function(){
	checkSession(gotoIndex, checkSessionFail);
});