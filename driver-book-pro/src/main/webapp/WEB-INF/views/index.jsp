<%@ page contentType="text/html; charset=UTF-8"%>

<!doctype HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>车圣互联</title>
<link rel="stylesheet" href="//libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css">
<script type="text/javascript" src="//libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript" src="//libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<style type="text/css">
.navbar-default{background-color:#428bca;border-color:#428bca;border-radius:0px;}
.navbar-default .navbar-text{color:#fff;}
.navbar-brand{width:200px;height:50px;background-image:url(dwz/themes/default/images/logo.png);}
.navbar-text{color:#fff;}
</style>
</head>
<body>
	<header class="navbar navbar-default" role="navigation">
		<div class="container">
			<div class="nav-header">
				<a class="navbar-brand" href="#"></a>
			</div>
			<p class="navbar-text">车圣互联API</p>
		</div>
	</header>
	<div class="col-md-10 col-md-offset-1 col-xs-12" style="margin-bottom:51px;">
		<div class="table-responsive">
			<table class="table table-bordered table-hover table-striped table-responsive">
				<tr>
					<th class="text-center">Address</th>
					<th class="text-center">Data</th>
					<th class="text-center">Method</th>
					<th class="text-center">Format</th>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/logininfo/new</td>
					<td>loginInfo</td>
					<td>POST</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/customer/show</td>
					<td>customerId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/customer/edit</td>
					<td>customer</td>
					<td>PUT</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/customer/vehicle</td>
					<td>customerId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/customer/bind</td>
					<td>appBind</td>
					<td>POST</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/vehicle/show</td>
					<td>vehicleId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/vehicle/edit</td>
					<td>vehicle</td>
					<td>PUT</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/order/new</td>
					<td>order</td>
					<td>POST</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/order/prepare</td>
					<td>orderId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/order</td>
					<td>customerId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/order/show</td>
					<td>orderId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/news</td>
					<td>customerId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/news/{id}</td>
					<td>N/A</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/feedback</td>
					<td>customerId</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/feedback/new</td>
					<td>feedback</td>
					<td>POST</td>
					<td>JSON</td>
				</tr>
				<tr>
					<td>{protocol}://{IP}:{port}/{project_name}/app/version</td>
					<td>origin</td>
					<td>GET</td>
					<td>JSON</td>
				</tr>
			</table>
		</div>
	</div>
	<footer class="navbar navbar-default navbar-fixed-bottom">
		<div class="container text-center" style="margin-top:5px;margin-bottom:5px;">
			深圳赛格导航版权所有 粤ICP备13001872号<br/>
			Copyright©2008-2014 All rights reserved. 
		</div>
	</footer>
</body>
</html>
