<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBtdC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<title>海马数据同步</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/easyui/themes/color.css">

<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/comm/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
	form {
	  margin-bottom: 0em;
	}
</style>
</head>
<body>
		<div class="easyui-panel" style="padding:5px;">
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1',selected:true" style="width:120px" onclick="showDiv('0')">同步日志</a>
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('1')">同步车系</a>
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('2')">同步车型</a>
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('3')">同步4s店</a>
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('4')">同步保险公司</a>
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('5')">同步套餐</a>
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('6')">同步基本资料</a>
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('7')">同步账户信息</a> 
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('8')">同步绑定信息</a> 
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('9')">车载号码</a> 
			<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'" style="width:90px" onclick="showDiv('10')">服务密码</a> 
		</div>
		<div class="show" id="view_0">
			<table id="dg0" title="同步日志" class="easyui-datagrid" style="height:590px;" data-options="
				url:'<%=request.getContextPath() %>/logs/synclogs.do',
				rownumbers:true,
				pagination:true,
	            pageSize:20,
				singleSelect:true,
				autoRowHeight:false">
				<thead>
					<tr>
						<th field="if_name" width="80">同步接口名称</th>
						<th field="begin_time" 	width="150" formatter="localtimefmt">同步开始时间</th>
						<th field="end_time" 	width="150" formatter="localtimefmt">同步结束时间</th>
						<th field="stamp"  		width="150" formatter="localtimefmt" align="right">同步时间戳</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="view_1" style="display:none">
			<table id="dg1" title="同步车系"  toolbar='#tb1' style="height:590px;" data-options="
				rownumbers:true,
				singleSelect:true,
				 pagination:true,
	            pageSize:20,
				autoRowHeight:false">
				<thead>
					<tr>
						<th field="11" formatter="doSeries_fmt">操作</th>
						<th field="is_valid" width="80">是否有效</th>
						<th field="series_id" width="150" >车系ID</th>
						<th field="series_name" width="150" >车系名称</th>
						<th field="producer"  width="150"  >厂商</th>
						<th field="stamp"  width="150" align="right">最后操作时间</th>
						<th field="remark"  width="150" >备注</th>
					</tr>
				</thead>
			</table>
			<div id="tb1" style="padding:3px">
				<form id="cxForm" name="cxForm" >
					<span>车系ID:</span>
					<input id="series_id" name="series_id" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_1" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_1\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_1" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_1\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg1','cxForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#cxForm')">重置</a>
				</form>
			</div>
		</div>
		<div  id="view_2" style="display:none">
			<table id="dg2" title="同步车型" toolbar='#tb2' style="height:590px;" data-options="
				rownumbers:true,
				singleSelect:true,
				 pagination:true,
	            pageSize:20,
				autoRowHeight:false">
				<thead>
					<tr>
						<th field="11" formatter="doModels_fmt">操作</th>
						<th field="is_valid" width="80">是否有效</th>
						<th field="model_id" width="80" >车型ID</th>
						<th field="series_id" width="80" >车系ID</th>
						<th field="model_name"  width="80"  >车型名称</th>
						<th field="stamp"  width="120" align="right">时间戳</th>
						<th field="remark"  width="120" >备注</th>
						<th field="standart_oil"  width="100" >车型标准油耗</th>
						<th field="displacement"  width="40" >排量</th>
						<th field="img"  width="140" >车型图片URL路径</th>
						<th field="img1"  width="140" >备用图片URL路径(IPAD)</th>
						<th field="car_type_year"  width="100" >车型年份</th>
						<th field="car_type_level"  width="100" >车型级别</th>
						<th field="is_turbo"  width="100" >是否涡轮增压</th>
					</tr>
				</thead>
			</table>
			
			<div id="tb2" style="padding:3px">
				<form id="xxForm" name="xxForm" >
					<span>车型ID:</span>
					<input id="model_id" name="model_id" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_2" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_2" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_2\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg2','xxForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#xxForm')">重置</a>
				</form>
			</div>
		</div>
		<div  id="view_3" style="display:none;">
			<table id="dg3" title="同步4S店" toolbar='#tb3'  style="height:590px;"  data-options="
				rownumbers:true,
                pagination:true,
	            pageSize:20,
				singleSelect:true">
				<thead>
					<tr>
						<th field="11" formatter="doSsss_fmt">操作</th>
						<th field="company" width="100">机构id</th>
						<th field="companyname" width="100" >机构名称</th>
						<th field="parentcompany" width="100" >父机构id</th>
						<th field="order"  width="30"  >排序</th>
						<th field="companylevel"  width="100"  align="right">机构层级</th>
						<th field="opid"  width="100" >机构管理人id</th>
						<th field="address"  width="200" >地址</th>
						<th field="time"  width="100"  >成立时间</th>
						<th field="cnfullname"  width="200" >中文全名</th>
						<th field="companycode"  width="60" >机构code</th>
						<th field="remark"  width="200" >备注</th>
						<th field="coordinates"  width="150" >经纬度</th>
						<th field="phone"  width="200" >电话(维修电话)</th>
						<th field="phone2"  width="80" >电话2(预约电话)</th>
						<th field="major"  width="80" >主营</th>
						<th field="aptitude"  width="80" >资质</th>
						<th field="image"  width="80" >原图URL</th>
						<th field="image2"  width="80" >缩略图URL</th>
						<th field="stamp"  width="80"  >时间戳</th>
					</tr>
				</thead>
			</table>
				
			<div id="tb3" style="padding:3px">
				<form id="ssssForm" name="ssssForm" >
					<span>机构id:</span>
					<input id="companyno" name="companyno" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_3" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_3\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_3" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_3\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg3','ssssForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#ssssForm')">重置</a>
				</form>
			</div>
				
		</div>
		<div id="view_4" style="display:none">
			<table id="dg4" title="同步保险公司"  toolbar='#tb4' style="height:590px;" data-options="
				rownumbers:true,
				singleSelect:true,
				 pagination:true,
	            pageSize:20,
				autoRowHeight:false">
				<thead>
					<tr>
						<th field="11" formatter="doInsurances_fmt">操作</th>
						<th field="ic_id" width="140">保险公司ID</th>
						<th field="tel" width="140" >联系电话</th>
						<th field="s_name" width="140" >名称简称</th>
						<th field="c_name"  width="140"  >中文全称</th>
						<th field="stamp"  width="120"  align="right">操作时间</th>
						<th field="remark"  width="140" >备注</th>
					</tr>
				</thead>
			</table>
			
			<div id="tb4" style="padding:3px">
				<form id="bxForm" name="bxForm" >
					<span>保险公司ID:</span>
					<input id="ic_id" name="ic_id" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_4" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_4\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_4" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_4\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="doSearch('dg4','bxForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#bxForm')">重置</a>
				</form>
			</div>
		</div>
		<div id="view_5" style="display:none">
			<table id="dg5" title="同步套餐"  toolbar='#tb5' style="height:590px;" data-options="
				rownumbers:true,
				singleSelect:true,
				 pagination:true,
	            pageSize:20,
				autoRowHeight:false">
				<thead>
					<tr>
						<th field="11" formatter="doCombos_fmt">操作</th>
						<th field="combo_id" width="80">套餐ID</th>
						<th field="flag" width="40" >状态</th>
						<th field="telco" width="80" >运营商</th>
						<th field="combo_name"  width="180"  >套餐名称</th>
						<th field="month_fee"  width="80" >套餐费用</th>
						<th field="data"  width="80" >总流量</th>
						<th field="voice_time"  width="80" >通话时长</th>
						<th field="stamp"  width="150" align="right">操作时间</th>
						<th field="remark"  width="150" >备注</th>
					</tr>
				</thead>
			</table>
			
			<div id="tb5" style="padding:3px">
				<form id="tcForm" name="tcForm" >
					<span>套餐ID:</span>
					<input id="combo_id" name="combo_id" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_5" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_5\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_5" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_5\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg5','tcForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#tcForm')">重置</a>
				</form>
			</div>
		</div>
		<div id="view_6" style="display:none">
			<table id="dg6" title="同步车辆基本资料" toolbar='#tb6'  style="height:590px;"  data-options="
					rownumbers:true,
	                pagination:true,
	                pageSize:20,
					singleSelect:true">
					<thead>
						<tr>
							<th field="11" formatter="doInfo_fmt">操作</th>
							<th field="vin"  width="130">车架号</th>
							<th field="vehicle_id" width="50">车辆id</th>
							<th field="plate_no"  width="80">车牌号</th>
							<th field="call_letter"  width="80">车载号码</th>
							<th field="stamp"  width="120"  >时间戳</th>
							<th field="create_time"  width="120" >入网时间</th>
							<th field="service_date"  width="120" >服务开通时间</th>
							<th field="phone" width="120" >联系电话</th>
							<th field="customer_id"  width="80">客户ID</th>
							<th field="customer_name"  width="80">客户姓名</th>
							<th field="address"  width="180">联系地址</th>
							<th field="idtype"  width="60" >证件类型</th>
							<th field="id_no"  width="120">身份证号</th>
							<th field="sex"  width="30">性别</th>
							<th field="email"  width="100">邮箱</th>
							<th field="series"  width="40">车系</th>
							<th field="model"  width="40">车型</th>
							<th field="color"  width="40">颜色</th>
							<th field="engine_no"  width="80">发动机号</th>
							<th field="factory"  width="80">生产厂家</th>
							<th field="production_date"  width="120" formatter="timefmt"   >生产日期</th>
							<th field="vload"  width="100">载重/乘客数</th>
							<th field="4s_id"  width="100">4s店ID</th>
							<th field="insurance_id"  width="100">保险公司ID</th>
							<th field="pack_id"  width="100">套餐ID</th>
							<th field="stop_date"  width="120"  formatter="timefmt"   >停用时间</th>
							<th field="equip_code"  width="120"  >配置代码</th>
						</tr>
					</thead>
				</table>
				
				<div id="tb6" style="padding:3px">
					<form id="infoForm" name="infoForm" >
						<span>车架号:</span>
						<input id="vin" name="vin" style="height:22px;border:1px solid #ccc">
						<span>联系电话:</span>
						<input id="phone" name="phone" style="height:22px;border:1px solid #ccc">
						<span>开始时间:</span>
						<input id="start_date_6" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_6\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
						<span>结束时间:</span>
						<input id="end_date_6" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_6\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg6','infoForm')">查询</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#infoForm')">重置</a>
					</form>
				</div>
		</div>
		
		<div id="view_7" style="display:none">
			<table id="dg7" title="同步客户信息" toolbar='#tb7'  style="height:590px;"  data-options="
					rownumbers:true,
	                pagination:true,
	                pageSize:20,
					singleSelect:true">
					<thead>
						<tr>
							<th field="11" formatter="doAccounts_fmt">操作</th>
							<th field="username" width="180">账号</th>
							<th field="password" width="80" >密码</th>
							<th field="unix_timestamp(stamp)"  width="120" formatter="timefmt"   >变更时间</th>
						</tr>
					</thead>
				</table>
				
				<div id="tb7" style="padding:3px">
					<form id="accountForm" name="accountForm" >
						<span>车架号:</span>
						<input id="vin" name="vin" style="height:22px;border:1px solid #ccc">
						<span>开始时间:</span>
						<input id="start_date_7" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_7\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
						<span>结束时间:</span>
						<input id="end_date_7" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_7\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="doSearch('dg7','accountForm')">查询</a>
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#accountForm')">重置</a>
					</form>
				</div>
		</div>
		<div id="view_8" style="display:none">
			<table id="dg8" title="同步绑定信息" toolbar='#tb8'  style="height:590px;"  data-options="
				rownumbers:true,
	               pagination:true,
	               pageSize:20,
				singleSelect:true">
				<thead>
					<tr>
						<th field="11" formatter="doBindinfo_fmt">操作</th>
						<th field="vin" width="180">车架号</th>
						<th field="bar_code" width="180" >TBOX条码</th>
						<th field="scan_time"  width="150" formatter="timefmt"  >扫描时间</th>
					</tr>
				</thead>
			</table>
			<div id="tb8" style="padding:3px">
				<form id="bindInfoForm" name="bindInfoForm" >
					<span>车架号:</span>
					<input id="vin" name="vin" style="height:22px;border:1px solid #ccc">
					<span>TBOX条码:</span>
					<input id="bar_code" name="bar_code" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_8" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_8\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_8" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_8\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg8','bindInfoForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#bindInfoForm')">重置</a>
				</form>
			</div>
		</div>
		<div id="view_9" style="display:none">
			<table id="dg9" title="车载号码"  toolbar='#tb9' style="height:590px;" data-options="
				rownumbers:true,
				singleSelect:true,
				 pagination:true,
	            pageSize:20,
				autoRowHeight:false">
				<thead>
					<tr>
						<th field="barcode" width="180">barcode</th>
						<th field="call_letter" width="120" >车载电话</th>
					</tr>
				</thead>
			</table>
			<div id="tb9" style="padding:3px">
				<form id="callletterForm" name="callletterForm" >
					<span>barcode:</span>
					<input id="barcode" name="barcode" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_9" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_9\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_9" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_9\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg9','callletterForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#callletterForm')">重置</a>
				</form>
			</div>
		</div>
		<div id="view_10" style="display:none">
			<table id="dg10" title="服务密码"  toolbar='#tb10' style="height:590px;" data-options="
				rownumbers:true,
				singleSelect:true,
				 pagination:true,
	            pageSize:20,
				autoRowHeight:false">
				<thead>
					<tr>
						<th field="11" formatter="doCheckPwd_fmt">操作</th>
						<th field="vin" width="180">车架号</th>
						<th field="servicepwd" width="150" >服务密码</th>
					</tr>
				</thead>
			</table>
			<div id="tb10" style="padding:3px">
				<form id="pwdsForm" name="pwdsForm" >
					<span>车架号:</span>
					<input id="vin" name="vin" style="height:22px;border:1px solid #ccc">
					<span>开始时间:</span>
					<input id="start_date_10" name="start_date" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_date_10\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<span>结束时间:</span>
					<input id="end_date_10" name="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_date_10\')}'})" class="Wdate"  style="width:140px;height:22px;border:1px solid #ccc">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="doSearch('dg10','pwdsForm')">查询</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true" onclick="formRest('#pwdsForm')">重置</a>
				</form>
			</div>
		    <div id="mydialog" style="display:none;padding:5px;width:400px;height:150px;" title="验证服务密码"> 
		    	<table style="font-size:12px;">
		    		<tr>
		    			<td style="width:70px;">密码明文：</td>
		    			<td><input id="inputpswd" type="text" class="easyui-textbox"  data-options="required:true" /></td>
		    		</tr>
		    		<tr >
		    			<td style="width:70px;font-size:12px;">sha密文：</td>
		    			<td><input id="servicePswd_gen" name="servicePswd_gen" type="text" class="easyui-textbox" style="width:300px;border:0px;" /></td>
		    		</tr>
		    	</table>
			</div> 
		</div>
</body>
</html>