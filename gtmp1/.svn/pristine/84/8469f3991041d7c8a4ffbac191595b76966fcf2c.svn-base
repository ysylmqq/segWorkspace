<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/unit.js"></script>
<script type="text/javascript" src="${basePath}js/service_station.js"></script>
<script type="text/javascript" src="${basePath}easyui/easyui.datagrid.columnMoving.js"></script>
<div id='loading2' style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%; height: 100%;">
	<div data-options="region:'center',border:false,onResize:serviceStationCenterResize" style="overflow: hidden;">
		<!-- 表格 begin -->
		<table id="ss_datagrid" class="easyui-datagrid" style="width: auto; height: auto;"
			data-options="url:'${basePath}servicemanage/service_station!index.action',toolbar:'#toolbar',
            fit:true,singleSelect:true,rownumbers:true,pagination:true,title:'服务站管理'">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true">ID</th>
					<th data-options="field:'stationName',width:120">服务站名称</th>
					<th data-options="field:'address',width:200">地址</th>
					<th data-options="field:'contact',width:100">联系人</th>
					<th data-options="field:'phoneNumber',width:80">联系方式</th>
					<th data-options="field:'longitude',width:100,align:'right'">经度</th>
					<th data-options="field:'latitude',width:100,align:'right'">纬度</th>
					<th data-options="field:'createMan',width:100">创建人</th>
					<th data-options="field:'createTime',width:130,align:'center'">创建时间</th>
					<th data-options="field:'operate',width:120,align:'center',formatter:operateFormatter">操作</th>
				</tr>
			</thead>
		</table>
		<!-- 表格 end -->
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">服务站名称：</td>
					<td>
						<input id="stationName_search" style="width: 150px;" />
					</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryStation()">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addStation()">新增</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 查询条件 end -->
	</div>
</div>
<!-- 新增或者修改弹出框 begin-->
<div id="dlg_ss_operate" class="easyui-dialog" title="添加服务站" style="padding: 5px; width: 600px; height: 360px;"
	data-options="iconCls:'icon-save',modal:true,closed:true,buttons:'#ss_operate_btns'">
	<form id="ss_operate_form" metdod="post" action="${basePath}unit/unit_saveOrUpdate.action">
		<input id="id" name="id" type="hidden" />
		<table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
			<tr>
				<td align="right">
					服务站名称：
				</td>
				<td align="left">
					<input id="stationName" name="stationName" />
					<span style="color: red">*</span>
				</td>
			</tr>	
			
			<tr>
				<td align="right">
					地址：
				</td>
				<td align="left">
					<input id="address" name="address" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					联系人：
				</td>
				<td align="left">
					<input id="contact" name="contact" />
					<span style="color: red">*</span>
				</td>
			</tr>	
			
			<tr>
				<td align="right">
					联系方式：
				</td>
				<td align="left">
					<input id="phoneNumber" name="phoneNumber" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					经度：
				</td>
				<td align="left">
					<input id="longitude" name="longitude" />
				</td>
			</tr>	
			
			<tr>
				<td align="right">
					纬度：
				</td>
				<td align="left">
					<input id="latitude" name="latitude" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="openMapSelect()">选取经纬度</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="ss_operate_btns">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveStation()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_ss_operate').dialog('close')">取消</a>
</div>
<!-- 新增或者修改弹出框 end-->

<!-- 回收站弹出框 begin-->
<div id="dlg_unit_recycle" class="easyui-dialog" title="回收站"
	style="width: 800px; height: 500px;"
	data-options="iconCls:'icon-recycle',modal:true,maximizable:true,closed:true,buttons:'#dlg_unit_recycle_btns',onResize:dlgResize">
	<!-- 表格 begin -->
	<table id="unit_recycle_datagrid" class="easyui-datagrid"
		style="padding-top: 0px; margin-top: 0px; height: 430px;"
		data-options="url:'${basePath}servicemanage/service_station!index.action?isValid=0',singleSelect:false,rownumbers:true,pagination:true,toolbar:'#toolbar_recycle',fit:true">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox : true,align:'center'">
					全选
				</th>
				<th data-options="field:'unitSn',width:130,align:'center'">
					终端序列号
				</th>
				<th data-options="field:'unitType',width:100,align:'center'">
					终端类型
				</th>
				<th data-options="field:'materialNo',width:100,align:'center'">
					物料条码
				</th>
				<th data-options="field:'simNo',width:150,align:'center'">
					SIM卡号
				</th>
				<th data-options="field:'steelNo',width:150,align:'center'">
					钢号
				</th>
				<th data-options="field:'hardwareVersion',width:100,align:'center'">
					硬件版本号
				</th>
				<th data-options="field:'softwareVersion',width:100,align:'center'">
					软件版本号
				</th>
				<th data-options="field:'registedTime',width:160,align:'center'">
					注册时间
				</th>
				<th
					data-options="field:'state',width:90,align:'center',formatter:function(val,row,index){if(val==1){ return '待安装';}else if(val==2){ return '已安装';}else if(val==3){ return '解绑定';}else if(val==9){ return '返修';}}">
					终端状态
				</th>
				<th
					data-options="field:'unitId',width:150,align:'center',formatter:operate">
					操作
				</th>
			</tr>
		</thead>
	</table>
</div>
<!-- 表格 end -->
<script type="text/javascript">
	function show() {
		$("#loading2").fadeOut("normal", function() {
			$(this).remove();
			//修改表格的宽度
			var wid = $(document.body).width();
			var hei = $('#main').height();
			$('#unit_datagrid').datagrid('resize', {
				width : wid - 2,
				height : hei
			});
		});
	}
	var delayTime;
	$.parser.onComplete = function(obj) {
		$('#unit_datagrid').datagrid('columnMoving');
		$('#unit_recycle_datagrid').datagrid('columnMoving');

		if (delayTime)
			clearTimeout(delayTime);
		delayTime = setTimeout(show, 1);
	};
</script>
