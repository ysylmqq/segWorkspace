<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/malfunction_statistic.js"></script>
<div id='loading2' style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%; height: 100%;">
	<div style="overflow: hidden;" data-options="region:'center',border:false,onResize:alarmQueryResize">
		<!-- 表格 begin -->
		<table id="malfunction_statistic_datagrid" toolbar="#toolbar"
			class="easyui-datagrid" style="width: auto; height: auto;"
			data-options="fitColumns:true,singleSelect:true,rownumbers:true,title:'故障统计',onClickCell:malfunnStatisCellClick">
			<thead>
				<tr>
				</tr>
			</thead>
		</table>
		<!-- 表格 end -->

		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						报表分类:
					</td>
					<td>
						<input id="mt" type="radio" name="report_type" value="2" checked="checked" />
						<label for="mt">
							故障类型
						</label>
						<input id="vm" type="radio" name="report_type" value="1" />
						<label for="vm">
							机械型号
						</label>
						<input id="da" type="radio" name="report_type" value="3" />
						<label for="da">
							区域
						</label>
					</td>
					<td align="right" id="td_report_type_text">
						故障类型:
					</td>
					<td>
						<div id="div_malfunctionCode_search">
							<input id="malfunctionCode_search" name="malfunctionCode_search"
								style="width: 150px;" class="easyui-combobox"
								data-options="url:'run/malfunction_getDicMalfunctionCodeInfo.action',valueField:'malfunctionCode',textField:'malfunction'" />
						</div>
						<div id="div_modelName_search" style="display: none">
							<input id="modelName_search" class="easyui-combobox"
								style="width: 150px;"
								data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
							机器代号:
							<input id="vehicleCode_search" class="easyui-combobox"
								style="width: 150px;"
								data-options="value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" />
							配置号:
							<input id="vehicleArg_search" class="easyui-combobox"
								style="width: 150px;"
								data-options="value:'全部',valueField:'vehicleArg',textField:'vehicleArg'" />
						</div>
						<div id="div_dealer_search" style="display: none;">
							<select id="dealerId_search" class="easyui-combotree" style="width: 150px;"
							data-options="onlyLeafCheck:false,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" multiple="multiple"></select>
						</div>
					</td>
					<td colspan="2"></td>
				</tr>

				<tr>
					<td>
						开始时间:
					</td>
					<td>
						<input id="start_time" type="text" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM'})">
					</td>
					<td>
						结束时间:
					</td>
					<td>
						<input id="end_time" type="text" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM'})">
					</td>
					<td colspan="2">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search" onclick="statisticMalfunction()">查询</a>
						<a href="#" id="downBtn" class="easyui-linkbutton"
							iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
						<a href="#" id="downBtn" class="easyui-linkbutton"
							iconCls="icon-baobiao" onclick="javascript:openChartWin();">图表</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 查询条件 end -->
	</div>
</div>

<!-- 故障统计--begin -->
<div closed="true" class="easyui-dialog"
	buttons="#dlg_malfunction_detail_btns" id="dlg_malfunction_detail"
	style="width: 630px; height: 300px; overflow: hidden"
	data-options="title:'故障统计详细'">
	<table id="malfunction_datagrid" class="easyui-datagrid"
		style="width: auto; height: auto;" rownumbers="true" pagination="true"
		data-options="
            singleSelect:true,rownumbers:true,pagination:true,fit:true">
		<thead>
			<tr>

				<!-- 机械信息 -->
				<th data-options="field:'vehicleDef',width:130,align:'center',sortable:true">整机编号</th>
				<th data-options="field:'typeName',width:130,align:'center',sortable:true">机械型号</th>
				<th data-options="field:'modelName',width:100,align:'center',sortable:true">机械类型</th>

				<!-- 故障信息 -->
				<th data-options="field:'malfunction',width:130,align:'center',sortable:true">故障名称</th>
				<th data-options="field:'stamp',width:130,align:'center',sortable:true">故障发生时间</th>

				<!-- 客户信息 -->
				<th data-options="field:'dealerName',width:150,align:'center',sortable:true">经销商</th>
				<th data-options="field:'ownerName',width:130,align:'center',sortable:true">客户姓名</th>
				<th data-options="field:'ownerPhoneNumber',width:100,align:'center',sortable:true">客户电话</th>
				<!-- GPS信息 -->
				<th data-options="field:'lon',width:130,align:'center',sortable:true">经度</th>
				<th data-options="field:'lat',width:130,align:'center',sortable:true">纬度</th>
				<th data-options="field:'speed',width:100,align:'center',sortable:true">速度</th>
				<th data-options="field:'direction',width:100,align:'center',sortable:true">方向</th>
				<th data-options="field:'gpsTime',width:150,align:'center',sortable:true">GPS定位时间</th>
				<th data-options="field:'vehicleState',width:150,align:'center',sortable:true">机械状态</th>
				<th data-options="field:'referencePosition',width:150,align:'center',sortable:true">参考位置</th>
				<th data-options="field:'locationState',width:150,align:'center',sortable:true">定位状态</th>

			</tr>
		</thead>
	</table>
</div>
<div id="dlg_malfunction_detail_btns">
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
		onclick="javascript:$('#dlg_malfunction_detail').dialog('close')">关闭</a>
</div>
<!--故障统计--end -->
<script type="text/javascript">
	function show() {
		$("#loading2").fadeOut(
				"normal",
				function() {
					$(this).remove();
					//修改表格的宽度
					var wid = $(document.body).width();
					var hei = $('#main').height();
					$('#malfunction_statistic_datagrid').datagrid('resize', {
						width : wid - 2,
						height : hei
					});

					//先隐藏机型
					$('#div_modelName_search').hide();
					$("input:radio[name='report_type']").bind(
							"click",
							function() {
								//设置每个参数的默认值
								var id = $(this).val();
								var td_report_type_text = "故障类型:";
								if (id == 1) {
									td_report_type_text = "机械型号:";
									$('#div_malfunctionCode_search').hide();
									$('#div_dealer_search').hide();
									$('#malfunctionCode_search').combobox('setValue', '');
									$('#div_modelName_search').show();
								} else if (id == 2) {
									$('#div_malfunctionCode_search').show();
									$('#div_modelName_search').hide();
									$('#div_dealer_search').hide();
									$('#modelName_search').combobox('setValue', '');
									$('#vehicleCode_search').combobox('setValue', '全部').combobox({disabled : false});
									$('#vehicleArg_search').combobox('setValue', '全部').combobox({disabled : false});
								} else {
									td_report_type_text = "区域/经销商:";
									$('#div_malfunctionCode_search').hide();
									$('#div_modelName_search').hide();
									$('#div_dealer_search').show();
									$('#malfunctionCode_search').combobox('setValue', '');
									$('#modelName_search').combobox('setValue', '');
									$('#vehicleCode_search').combobox('setValue', '全部').combobox({disabled : false});
									$('#vehicleArg_search').combobox('setValue', '全部').combobox({disabled : false});
								}
								$('#td_report_type_text').html(td_report_type_text);
							});
				});
	}
	var delayTime;
	$.parser.onComplete = function(obj) {
		if (delayTime)
			clearTimeout(delayTime);
		delayTime = setTimeout(show, 1);
	};
</script>
