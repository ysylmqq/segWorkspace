<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/malfunction_query.js"></script>
<div id='loading_run' style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true,border:false"
	style="width: 100%; height: 100%;">
	<div data-options="region:'center',border:false,onResize:malFunctionMainResize" style="overflow: hidden;">
		<!-- 表格 begin -->
		<table id="malfunction_datagrid" toolbar="#tlb_malfunction" class="easyui-datagrid" style="width: auto; height: auto;"
			data-options="url:'${basePath}run/malfunction_search.action',queryParams:defQueryParams,singleSelect:true,rownumbers:true,pagination:true,title:'机械故障查询'">
			<thead>
				<tr>
					<!-- 机械信息 -->
					<th data-options="field:'vehicleDef',width:130,align:'center',sortable:true">
						整机编号
					</th>
					<th data-options="field:'modelName',width:100,align:'center',sortable:true">
						机型
					</th>
					<th data-options="field:'vehicleCode',width:100,align:'center',sortable:true">
						机器代号
					</th>
					<th data-options="field:'vehicleArg',width:100,align:'center',sortable:true">
						配置号
					</th>

					<!-- 故障信息 -->
					<th data-options="field:'malfunction',width:130,align:'center',sortable:true,formatter:operate">
						故障名称
					</th>
					<th data-options="field:'stamp',width:130,align:'center',sortable:true">
						故障发生时间
					</th>

					<!-- 客户信息 -->
					<th data-options="field:'ownerName',width:130,align:'center',sortable:true">
						客户姓名
					</th>
					<th data-options="field:'ownerPhoneNumber',width:100,align:'center',sortable:true">
						客户电话
					</th>

					<!-- GPS信息 -->
					<th data-options="field:'lon',width:130,align:'center',sortable:true">
						经度
					</th>
					<th data-options="field:'lat',width:130,align:'center',sortable:true">
						纬度
					</th>
					<th data-options="field:'speed',width:100,align:'center',sortable:true">
						速度
					</th>
					<th data-options="field:'direction',width:100,align:'center',sortable:true">
						方向
					</th>
					<th data-options="field:'gpsTime',width:150,align:'center',sortable:true">
						GPS定位时间
					</th>
					<th data-options="field:'vehicleState',width:150,align:'center',sortable:true">
						机械状态
					</th>
					<th data-options="field:'referencePosition',width:150,align:'center',sortable:true">
						参考位置
					</th>
					<th data-options="field:'locationState',width:150,align:'center',sortable:true,formatter:function(value,row,index){if(value=='1'){return '是';}else{return '否';}}">
						定位状态
					</th>
				</tr>
			</thead>
		</table>
		<!-- 表格 end -->
		<!-- 查询条件 begin -->
		<div id="tlb_malfunction" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						整机编号:
					</td>
					<td>
						<input id="vehicleDef_search" style="width: 150px;">
					</td>
					<td align="right">
						机型:
					</td>
					<td>
						<input id="vehicleModel_search" class="easyui-combobox" style="width:150px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
					</td>
					<td align="right">
						机器代号:
					</td>
					<td>
						<input id="vehicleCode_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" />
					</td>
					<td align="right">
						配置号:
					</td>
					<td>
						<input id="vehicleArg_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleArg',textField:'vehicleArg'" />
					</td>
					<td></td>
				</tr>

				<tr>
					<td align="right">
						客户姓名:
					</td>
					<td>
						<input id="owner_search" style="width: 150px;" />
					</td>
					<td align="right">
						故障码:
					</td>
					<td>
						<input id="malfunctionCode_search" style="width: 150px;" class="easyui-combobox"
							data-options="url:'run/malfunction_getDicMalfunctionCodeInfo.action',valueField:'malfunctionCode',textField:'malfunction'" />
					</td>
					<td>
						开始时间:
					</td>
					<td>
						<input id="start_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" />
					</td>
					<td>
						结束时间:
					</td>
					<td>
						<input id="end_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" />
					</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryMalfunction()">查询</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 查询条件 end -->
	</div>
</div>


<!--解决方案--begin-->
<div id="dlg_solution" class="easyui-dialog" title="解决方案" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 400px; height: 300px;" closed="true" buttons="#btns_solution">
	<form id="frm_solution" name="frm_solution" method="POST" metdod="post"
		tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				<td>
					故障名称:
				</td>
				<td>
					<input id="malfunction" style="width: 150px" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					解决方案:
				</td>
				<td>
					<div id="solution"></div>
				</td>
				<td>
			</tr>

			<tr>
				<td colspan="2">
					<span style="display: none; color: red" id="span_record_counts"></span>
				</td>
			</tr>
		</table>
	</form>
	<div id="btns_solution">
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_solution').dialog('close')">关闭</a>
	</div>
</div>
<!--解决方案--end-->
<script type="text/javascript">
	function show() {
		$("#loading_run").fadeOut("fast", function() {
			$(this).remove();
			//修改表格的宽度
			var hei = $('#main').height();
			$('#malfunction_datagrid').datagrid('resize', {
				height : hei
			});
		});

	}
	var delayTime;
	$.parser.onComplete = function(obj) {
		$('#malfunction_datagrid').datagrid('columnMoving');
		$('#start_time').val(getTodayZero());
		$('#end_time').val(new Date().formatDate(timeFormat));
		$('#malfunction_datagrid').datagrid('columnMoving');

		if (delayTime)
			clearTimeout(delayTime);
		delayTime = setTimeout(show, 1);
	};
</script>