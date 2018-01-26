<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/jsp/common.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>玉柴重工GPS物联网管理平台</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<script type="text/javascript">
	function login(){
		var userid = $('#loginName').val();
		var password =$('#password').val();
		var checkCode = $('#validate').val();
		if(userid == ''){
			$.messager.alert('系统信息','请输入用户名！');
			return false;
		}
		if(password == ''){
		    $.messager.alert('系统信息','请输入密码！');
			return false;
		}if(checkCode == ''){
			$.messager.alert('系统信息','请输入验证码！');
			return false;
		}
		var params={
			'loginName':userid,
			'password':password,
			'checkCode':checkCode
		}
		$.post("login.action", params, function(data){
				if(data.success){
			        window.location.href="index.jsp";
			      }else{
			        $.messager.alert('系统信息',data.msg);
			      }
		}, 'json');
	}
	function FSubmit(e)
	{
	    if(e == 13 || e == 32)
	    {
	        login();//表单提交
	        //可执行的代码
	        //……
	        e.returnValue = false; 
	       //返回false，在没有使用document.loginForm.submit()时可用于取消提交
	    }
	}
	function reset(){
		document.getElementById('form').reset();
	}
	function downMobileSoftware(){
		document.getElementById('ifm_down_mobile').src="doc/download/Android_v1.0.apk";
	}
</script>
</head>
<body style="background: none repeat scroll 0 0 #4a9ad5;">
<%-- <a style="float:right;color:white;" href="${basePath}doc/download/Android_v1.0.apk">手机客户端下载</a> --%>
	  	
<div align="center">
   <div style="float:right">
	<img alt="" src="${basePath}images/app.png" style="margin:10px 10px 0 0"><br>
	扫描二维码下载手机APP
</div>
	<form name="WebLoginForm" method="post" action="login/login_login.action" target="_top" id="form" >
	  <div class="loginbody">	  	
	 <table cellpadding="0" cellspacing="0">
	 <tbody>
			<tr>
			<td>用户名：</td>
			<td>
			<input id="loginName" class="ip138" type="text" onkeydown="if(event.keyCode==32) return false" maxlength="20"  name="loginName">
			</td>
			</tr>
			<tr>
			<td height="8">
			</td>
			</tr>
			<tr>
			<td>密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
			<td> 
			<input id="password" class="ip138" type="password" maxlength="20"  name="password">
			</td>
			</tr>
			<tr>
			<td height="8">
			</td>
			</tr>
			<tr>
			<td>验证码：</td>
			<td> 
			<input id="validate" class="ip51" type="text" maxlength="4"  value="" name="validate" onkeydown="FSubmit(event.keyCode||event.which);">
			<img src="${basePath}checkCodeSevlet?now='+new Date();" style="cursor:pointer" id="authImg" alt="点击更换验证码!"  onclick="document.getElementById('authImg').src='${basePath}checkCodeSevlet?now='+new Date();" />
			</td>
			</tr>
			<tr>
			<td height="20">
			</td>
			</tr>
			<tr >
			<td colspan="2">
			<img src="${basePath}images/login/button12_03.png" style="cursor:pointer" onclick="login()">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="${basePath}images/login/button12_05.png" style="cursor:pointer" onclick="reset()">
			</td>
			</tr>
			
			
	 </tbody>
	 </table>
	  </div>
	</form>
</div>

</body>
</html>