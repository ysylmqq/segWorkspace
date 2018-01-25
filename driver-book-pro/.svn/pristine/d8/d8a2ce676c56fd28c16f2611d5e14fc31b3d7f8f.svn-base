<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath(); %>
<!doctype HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=7">
		<title>湖南赛格导航服务平台</title>
		<link href="<%= basePath %>/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen">
		<link href="<%= basePath %>/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen">
		<link href="<%= basePath %>/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print">
		<!--[if IE]>
			<link href="<%= basePath %>/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
		<![endif]-->
		<!--[if lte IE 9]>
			<script src="<%= basePath %>/dwz/js/speedup.js" type="text/javascript"></script>
		<![endif]-->
		<script src="http://cdn.staticfile.org/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
		<script src="http://cdn.staticfile.org/jquery-cookie/1.4.1/jquery.cookie.min.js" type="text/javascript"></script>
		<script src="http://cdn.staticfile.org/jquery-validate/1.12.0/jquery.validate.min.js" type="text/javascript"></script>
		<script src="<%= basePath %>/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
		<script src="<%= basePath %>/dwz/dwz.min.js" type="text/javascript"></script>
		<script src="<%= basePath %>/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
		<script src="http://cdn.staticfile.org/highcharts/4.0.1/highcharts.js" type="text/javascript"></script>
		<script src="http://cdn.staticfile.org/highcharts/4.0.1/themes/grid.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function(){
				DWZ.init("<%= basePath %>/dwz/dwz.frag.xml", {
					loginUrl:"admin/signin",
					statusCode:{ok:200, error:300, timeout:301},
					pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
					debug:false,
					callback:function(){
						initEnv();
						$("#themeList").theme({themeBase:"<%= basePath %>/dwz/themes"});
					}
				});
			});
		</script>
	</head>
	<body scroll="no">
		<div id="layout">
			<div id="header">
				<div class="headerNav">
					<a class="logo" href="<%= basePath %>">LOGO</a>
					<ul class="nav">
						<li>
							<a href="javascript:void(0)">${operator.operatorName}</a>
						</li>
						<li>
							<a href="password" target="dialog">修改密码</a>
						</li>
						<li>
							<a href="admin/signout">退出</a>
						</li>
					</ul>
					<ul class="themeList" id="themeList">
					    <li theme="default">
					        <div class="selected">蓝色</div>
					    </li>
					    <li theme="green">
					        <div>绿色</div>
					    </li>
					    <li theme="purple">
					        <div>紫色</div>
					    </li>
					    <li theme="silver">
					        <div>银色</div>
					    </li>
					    <li theme="azure">
					        <div>天蓝</div>
					    </li>
					</ul>
				</div>
			</div>
			<div id="leftside">
				<div id="sidebar_s">
					<div class="collapse">
						<div class="toggleCollapse">
							<div></div>
						</div>
					</div>
				</div>
				<div id="sidebar">
					<div class="toggleCollapse">
						<h2>主菜单</h2>
						<div>收缩</div>
					</div>
					<div class="accordion" fillSpace="sidebar">
						<div class="accordionHeader">
							<h2>
								<span>Folder</span>统计报表
							</h2>
						</div>
						<div class="accordionContent">
							<ul class="tree treeFolder">
								<li>
									<a href="<%= basePath %>/admin/activereports" target="navTab" rel="active_report">活跃用户统计</a>
								</li>
								<li>
									<a href="<%= basePath %>/admin/signinreports" target="navTab" rel="signin_report">用户登录统计</a>
								</li>
							</ul>
						</div>
						<div class="accordionHeader">
							<h2>
								<span>Folder</span>系统管理
							</h2>
						</div>
						<div class="accordionContent">
							<ul class="tree treeFolder">
								<li>
									<a href="<%= basePath %>/admin/versions" target="navTab" rel="versions">APP版本管理</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent">
							<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
							<ul class="navTab-tab">
								<li tabid="main" class="main">
									<a href="javascript:;">
										<span>
											<span class="home_icon">我的主页</span>
										</span>
									</a>
								</li>
							</ul>
						</div>
						<div class="tabsLeft">
							left
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
						<div class="tabsRight">
							right
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
						<div class="tabsMore">
							more
						</div>
					</div>
					<ul class="tabsMoreList">
						<li>
							<a href="javascript:;">我的主页</a>
						</li>
					</ul>
					<div class="navTab-panel tabsPageContent layoutBox">
						<div class="page unitBox">
							<div class="accountInfo">
								<div class="alertInfo">
									<div class="right">
										<p>欢迎您登录，${loginName}</p>
										<p id="time"></p>
									</div>
									<script type="text/javascript">
										var getTime = function(){
											$('#time').html(new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay()));
											return false;
										};
										setInterval(getTime,1000);
									</script>
								</div>
								<p>
									<span>欢迎使用赛格导航服务平台</span>
								</p>
							</div>
							<div class="pageFormContent" layoutH="80" style="margin-right: 230px"></div>
						</div>
					</div>
				</div>
			</div>
			<div id="footer">
				Copyright &copy; 2013
				<a href="demo_page2.html" target="dialog">赛格导航</a>
			</div>
		</div>
	</body>
</html>
