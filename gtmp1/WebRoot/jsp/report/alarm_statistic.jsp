<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/alarm_statistic.js"></script>
<div id='loading2' style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%; height: 100%;">
	<div data-options="region:'center',border:false,onResize:alarmQueryResize" style="overflow: hidden;">
		<!-- 表格 begin -->
		<table id="alarm_statistic_datagrid" class="easyui-datagrid" style="width: auto; height: auto;"
			data-options="toolbar:'#toolbar',fitColumns:true,singleSelect:true,rownumbers:true,title:'报警信息统计',onClickCell:malfunnStatisCellClick">
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
						<input id="at" type="radio" name="report_type" value="2" checked="checked" />
						<label for="at">警情类型</label>
						<input id="vm" type="radio" name="report_type" value="1" />
						<label for="vm">机械型号</label>
					</td>
					<td align="right" id="td_report_type_text">
						警情大类:
					</td>
					<td>
						<div id="div_alarmCode_search">
							<select id="alarmTypeGenre_search" class="easyui-combobox" name="alarmTypeGenre_search" style="width:150px;">
	                           <option value="">全部</option>
	                           <option value="1">GPS警情</option>
	                           <option value="2">挖机警情</option>
   						 </select>
							警情类型:
   						 <input id="alarmType_search" class="easyui-combobox" style="width: 150px;"
								data-options="url:'run/alarm_getAlarmType.action',valueField:'alarmTypeId',textField:'alarmTypeName'" />
						</div>
						
						<div id="div_modelName_search" style="display: none">
							<input id="vehicleModel_search" class="easyui-combobox" style="width: 150px;"
								data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
							机器代号:
                 			<input id="vehicleCode_search" class="easyui-combobox" style="width: 150px;" 
                 				data-options="value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" />
                 			配置号:
                 			<input id="vehicleArg_search" class="easyui-combobox" style="width: 150px;" 
                 				data-options="value:'全部',valueField:'vehicleArg',textField:'vehicleArg'" />
						</div>
					</td>
					<td></td>
				</tr>

				<tr>
					<td>
						开始时间:
					</td>
					<td>
						<input id="start_time" type="text" class="Wdate"
							onFocus="WdatePicker({dateFmt: 'yyyy-MM'})" />
					</td>
					<td>
						结束时间:
					</td>
					<td>
						<input id="end_time" type="text" class="Wdate"
							onFocus="WdatePicker({dateFmt:'yyyy-MM'})">
					</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search" onclick="statisticalarm()">查询</a>
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

<!-- 警情统计--begin -->
<div closed="true" class="easyui-dialog" id="dlg_alarm_detail" buttons="#dlg_alarm_detail_btns" style="width: 800px; height: 500px; overflow: hidden" data-options="title:'警情统计详细',maximizable:true">
	<table id="alarm_detail_datagrid" class="easyui-datagrid"
		style="width: auto; height: auto;" pagination="true"
		data-options="singleSelect:true,rownumbers:true,fit:true">
		<thead>
			<tr>
				<th data-options="field:'vehicleDef',width:100,align:'center'">
					整机编号
				</th>
				<th data-options="field:'alarmTypeName',width:100,align:'center'">
					报警类型
				</th>
				<th data-options="field:'alarmTypeGenre',width:100,align:'center',formatter:formmatAlarmGenre">
					警情大类
					</th>
				<th data-options="field:'stamp',width:130,align:'center'">
					报警时间
				</th>
				<th
					data-options="field:'alarmStatus',width:130,align:'center',formatter:function(val,row,index){if(val=='00'){ return '警情消除';}else if(val=='01'){ return '警情发生';}else{ return '';}}">
					是否消除
				</th>
				<th data-options="field:'lon',width:130,align:'center'">
					经度
				</th>
				<th data-options="field:'lat',width:130,align:'center'">
					纬度
				</th>
				<th data-options="field:'speed',width:130,align:'center'">
					速度
				</th>
				<th
					data-options="field:'referencePosition',width:130,align:'center'">
					参考位置
				</th>
			</tr>
		</thead>
	</table>
</div>
<div id="dlg_alarm_detail_btns">
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
		onclick="javascript:$('#dlg_alarm_detail').dialog('close')">关闭</a>
</div>
<!--警情统计--end -->
<script type="text/javascript">
	function show() {
		$("#loading2").fadeOut("normal", function() {
			$(this).remove();
			//修改表格的宽度
			var wid = $(document.body).width();
			var hei = $('#main').height();
			$('#alarm_statistic_datagrid').datagrid('resize', {
				width : wid - 2,
				height : hei
			});

			//先隐藏机型
			$("input:radio[name='report_type']").bind("click", function() {
				//设置每个参数的默认值
				var id = $(this).val();
				var td_report_type_text = "警情类型:";
				if (id == 1) {
					td_report_type_text = "机械型号:";
					$('#div_alarmCode_search').hide();
					$('#alarmType_search').combobox('setValue', '');
					$('#div_modelName_search').show();
				} else {
					$('#div_alarmCode_search').show();
					$('#div_modelName_search').hide();
					$('#vehicleModel_search').combobox('setValue', '');
					$('#vehicleCode_search').combobox('setValue', '全部').combobox({disabled: false});
					$('#vehicleArg_search').combobox('setValue', '全部').combobox({disabled: false});
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
