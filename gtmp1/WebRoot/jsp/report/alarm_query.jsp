<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/alarm_query.js"></script>
<script type="text/javascript" src="${basePath}js/common/globe.js"></script> 
<div id='loading2' style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%; height: 100%;">
	<div data-options="region:'center',border:false,onResize:alarmQueryResize" style="overflow: hidden;">
		<!-- 表格 begin -->
		<table id="alarm_query_datagrid" toolbar="#toolbar"
			class="easyui-datagrid" style="width: auto; height: auto;"
			data-options="url:'${basePath}run/alarm_getAlarmInfo.action',queryParams:defQueryParams,
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'报警信息查询'">
			<thead>
				<tr>
					<th data-options="field:'vehicleDef',width:100,align:'center'">
						整机编号
					</th>
					<!--add dealerName search by huhaofeng 2015-11-24-->
					<th data-options="field:'dealerId',width:140,align:'center'">
						经销商
					</th>
					<th data-options="field:'vehicleStatus',width:75,align:'center',formatter:fmtVehicleStatus">
						机械状态组
					</th>
					<th data-options="field:'vehicleModelName',width:70,align:'center'">
						机型
					</th>
					<th data-options="field:'vehicleCode',width:70,align:'center'">
						机器代号
					</th>
					<th data-options="field:'vehicleArg',width:70,align:'center'">
						配置号
					</th>
					<th data-options="field:'alarmTypeGenre',width:100,align:'center',formatter:formmatAlarmGenre">
						警情大类
					</th>
					<th data-options="field:'alarmTypeName',width:100,align:'center'">
						警情类型
					</th>
					<th data-options="field:'stamp',width:140,align:'center'">
						报警时间
					</th>
					<th data-options="field:'alarmStatus',width:75,align:'center',formatter:function(val,row,index){if(val=='00'){ return '警情消除';}else if(val=='01'){ return '警情发生';}else{ return '';}}">
						是否消除
					</th>
					<th data-options="field:'lon',width:75,align:'center',hidden:false">
						经度
					</th>
					<th data-options="field:'lat',width:75,align:'center',hidden:false">
						纬度
					</th>
					<th data-options="field:'speed',width:75,align:'center',hidden:false">
						速度
					</th>
					<th data-options="field:'referencePosition',width:140,align:'center'">
						参考位置
					</th>
				</tr>
			</thead>
		</table>
		<!-- 表格 end -->

		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						整机编号:
					</td>
					<td>
						<input id="vehicleDef_search" style="width: 150px;">
					</td>
					<td align="right">
						经销商:
					</td>
					<!-- <td>
						<input id="name_search" style="width: 150px;">
					</td> -->
					<td>
					<input id="dealerId_search" name="dealerId_search"
							class="easyui-combotree" style="width: 150px;"
							data-options="
							url:'run/run_getDealerAreas4Tree.action',
                 multiple:true,onlyLeafCheck:false,cascadeCheck:true
                 " />
                 </td>
                   
					<td align="right">
						警情大类:
					</td>
					<td>
						<select id="alarm_big_type_search" class="easyui-combobox" name="alarm_big_type_search" style="width:150px;">
	                           <option value="">全部</option>
	                           <option value="1">GPS警情</option>
	                           <option value="2">挖机警情</option>
   						 </select>
					</td>
					<td align="right">
						警情类型:
					</td>  
					<td>
						<input id="alarmType_alarm" name="alarmType_alarm" class="easyui-combobox" style="width: 150px;"
							data-options="url:'run/alarm_getAlarmType.action',valueField:'alarmTypeId',textField:'alarmTypeName',multiple:true" />
					</td>
					</tr>
				<tr>
				<td align="right">
						机型:
					</td>
					<td>
						<input id="vehicleModel_search" class="easyui-combobox" style="width:150px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
					</td>
			         <td align="right">
			         	机械代号:
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
			    	<td align="right">
						警情是否消除:
					</td>
					<td>
						<!-- <select id="alarmStatus_search" class="easyui-combobox"
							name="alarmStatus_search" style="width: 150px;">
							<option value="">
								全部
							</option>
							<option value="00">
								警情消除
							</option>
							<option value="01">
								警情发生
							</option>
						</select> -->
						 
						  <select id="alarmStatus_search" class="easyui-combobox" name="alarmStatus_search" style="width:150px;">
	                           <option value="">全部</option>
	                           <option value="00">警情消除</option>
	                           <option value="01">警情发生</option>
   						 </select>
					</td>
					
					</tr>
				<tr>
					
					 <td align="right">
					  机械状态(组):
					  </td>
               	 	<td>
                		<select  id="vehicleStatus_search" class="easyui-combobox"  name="vehicleStatus_search"  style="width:150px;" >    
                   	 		<option value="">全部</option>
                    		<option value="1">测试组</option>
                    		<option value="2">已测组</option>
                    		<option value="3">销售组</option>
                    		<option value="8">法务组</option>
                    		<option value="9">停用组</option>      
                		</select>
					</td>
					<td align="right">
						开始时间:
					</td>
					<td>
						<input id="start_time" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 150px">
					</td>
					<td align="right">
						结束时间:
					</td>
					<td>
						<input id="end_time" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 150px">
					</td>
					<td colspan="4">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search" onclick="queryCommand()">查询</a>
						<a href="#" id="downBtn" class="easyui-linkbutton"
							iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 查询条件 end -->
	</div>
</div>

<script type="text/javascript">
	function show() {
		$("#loading2").fadeOut("normal", function() {
			$(this).remove();
			//修改表格的宽度
			var wid = $(document.body).width();
			var hei = $('#main').height();
			$('#alarm_query_datagrid').datagrid('resize', {
				width : wid - 2,
				height : hei
			});
		});
	}
	var delayTime;
	$.parser.onComplete = function(obj) {
		$('#start_time').val(getTodayZero());
		$('#end_time').val(new Date().formatDate(timeFormat));

		if (delayTime)
			clearTimeout(delayTime);
		delayTime = setTimeout(show, 1);
	};
</script>
