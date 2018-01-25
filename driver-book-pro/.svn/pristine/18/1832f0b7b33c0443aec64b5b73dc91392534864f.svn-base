<%@page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%
	String basePath = request.getContextPath();
%>
<html>
	<head>
		<title>湖南赛格导航技术研究服务后台</title>
		<meta http-equiv="content-Type" main="text/html; charset=utf-8">
		<link href="<%= basePath %>/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="<%= basePath %>/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
		<script src="http://cdn.staticfile.org/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
		<script src="http://cdn.staticfile.org/jquery-validate/1.12.0/jquery.validate.min.js" type="text/javascript"></script>
		<script src="<%= basePath %>/dwz/dwz.min.js" type="text/javascript"></script>
		<script src="<%= basePath %>/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
		<style type="text/css">
			body {
				margin: 0px auto;
				font-family: "宋体";
				font-size: 12px;
				text-align: center;
				background: url('<%=basePath%>/images/background.jpg')
			}
			
			.main {
				margin: 150px auto;
				height: 296px;
				width: 536px;
				position: relative;
				background: url('<%=basePath%>/images/login.png') no-repeat center top;
			}
			
			.main h2,.main span {
				font-size: 14px;
				color: #e9f1ff;
				position: absolute;
			}
			
			.main span {
				top: 80px;
				right: 42px;
			}
			
			.main input {
				position: absolute;
				/*border:none; 
				outline:none;*/
				width: 53px;
			}
			
			.content {
				position: absolute;
				width: 333px;
				height: 228px;
			}
			
			#username {
				position: absolute;
				top: 72px;
				left: 250px;
				width: 126px;
				height: 18px;
			}
			
			#password {
				position: absolute;
				left: 249px;
				top: 112px;
				width: 126px;
				height: 18px;
			}
			
			#checkcode {
				position: absolute;
				width: 75px;
				left: 229px;
				top: 152px;
				height: 18px;
			}
			
			#login {
				position: absolute;
				/*border:1px #009966 solid;*/
				background: none;
				left: 172px;
				top: 192px;
				height: 29px;
				width: 67px;
			}
			
			#reset {
				position: absolute;
				/*border:1px #009966 solid;*/
				background: none;
				left: 258px;
				top: 192px;
				height: 29px;
				width: 67px;
			}
			
			#img {
				position: absolute;
				left: 322px;
				top: 148px;
				width: 70px;
				height: 24px;
			}
			
			#login:hover,#login1:hover,#img:hover {
				cursor: pointer;
			}
		</style>
	</head>
	<body>
		<div class="main">
			<form action="<%=basePath%>/admin/signin" method="post" onsubmit="return validateSubmit();">
				<input id="username" type="text" name="loginName" title="用户名" autofocus="autofocus" />
				<input id="password" type="password" name="password" title="密码" />
				<input id="checkcode" type="text" name="checkCode" />
				<img id="img" src="<%=basePath%>/admin/checkCode" onclick="document.getElementById('img').src='<%=basePath%>/admin/checkCode?now='+new Date();">
				<input id="login" type="submit" title="登录" value="" />
				<input id="reset" type="reset" value="" title="重置" />
			</form>
		</div>
		<script type="text/javascript">
			$(function(){
				DWZ.init("<%=basePath%>/dwz/dwz.frag.xml",{
					callback: function() {
						var msg = '${message}';
						if (msg != '' && msg != 'null') {
							alertMsg.error(msg);
						}
					}	
				});
			});
			
			function validateSubmit(){
				var username = document.getElementById('username').value;
				var password = document.getElementById('password').value;
				var checkcode = document.getElementById('checkcode').value;
				if(username == ''){
					alertMsg.warn('请输入用户名');
					return false;
				} else if(password == ''){
					alertMsg.warn('请输入密码');
					return false;
				} else if(checkcode == ''){
					alertMsg.warn('请输入验证码');
					return false;
				} else {
					return true;
				}
			}
		</script>
	</body>
</html>
