<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/worked_years_statis.js"></script>
<div id='loading2'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%; height: 100%;">
	<div
		data-options="region:'center',border:false,onResize:workedYearsStatisResize"
		style="overflow: hidden;">
		<!-- 表格 begin -->
		<table id="workedYearsStatis_datagrid" toolbar="#toolbar"
			class="easyui-datagrid" style="width: auto; height: auto;"
			rownumbers="true"
			data-options="
            fitColumns:true,singleSelect:true,rownumbers:true,title:'机械使用年数统计',onClickCell:workedYearsCellClick">
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
						经销商:
					</td>
					<!-- <td>
						<input id="dealerId_search" class="easyui-combobox" style="width: 200px;"
							data-options="url:'run/run_getDealers.action',valueField:'id',textField:'name'" />
					</td> -->
					<td>
						<input id="dealerId_search" name="dealerId_search"
							class="easyui-combotree" style="width: 200px;"
							data-options="
							url:'run/run_getDealerAreas4Tree.action',
                 multiple:true,onlyLeafCheck:false,cascadeCheck:true
                 " />
					</td>
					<td align="right">
						机械型号:
					</td>
					<td>
						<input id="vehicleModel_search" class="easyui-combobox" style="width: 100px;"
							data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
					</td>
					<td align="right">
			         	机械代号：
			        </td>
			        <td>
			        	<input id="vehicleCode_search" class="easyui-combobox" style="width: 100px;" data-options="value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" />
			        </td>
			        <td align="right">
			        	配置号：
			        </td>
			        <td>
			        	<input id="vehicleArg_search" class="easyui-combobox" style="width: 100px;" data-options="value:'全部',valueField:'vehicleArg',textField:'vehicleArg'" />
			        </td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search" onclick="statisticWorkedYears()">查询</a>
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

<!-- 机械工作时间段统计--begin -->
<div closed="true" class="easyui-dialog" id="dlg_worked_years_detail"
	style="width: 800px; height: 500px; overflow: hidden"
	data-options="title:'机械使用年数统计详细',maximizable:true,onResize:workedYearsDetailResize" buttons="#dlg_worked_years_detail_btns">
	<table id="worked_years_detail_datagrid" class="easyui-datagrid"
		style="width: auto; height: auto;" data-options="singleSelect:true,rownumbers:true,pagination:true,fit:true">
		<thead>
			<tr>
				<th data-options="field:'vehicleDef',width:120,align:'center'">整机编号</th>
				<!-- <th data-options="field:'typeName',width:80,align:'center'">机械类型</th> -->
				<th data-options="field:'modelName',width:80,align:'center'">机械型号</th>
				<th data-options="field:'fixMan',width:80,align:'center'">终端安装人</th>
				<th data-options="field:'fixDate',width:130,align:'center'">终端安装日期</th>
				<th data-options="field:'saleDate',width:130,align:'center'">销售时间</th>
				<th data-options="field:'status',width:100,align:'right'">累计工作时间</th>
			</tr>
		</thead>
	</table>
</div>
<div id="dlg_worked_years_detail_btns">
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
		onclick="javascript:$('#dlg_worked_years_detail').dialog('close')">关闭</a>
</div>
<!--机械工作时间段统计--end -->
<script type="text/javascript">
	function show() {
		$("#loading2").fadeOut("normal", function() {
			$(this).remove();
			//修改表格的宽度
			var wid = $(document.body).width();
			var hei = $('#main').height();
			$('#workedYearsStatis_datagrid').datagrid('resize', {
				width : wid - 2,
				height : hei
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
