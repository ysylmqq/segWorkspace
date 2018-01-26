<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/unupload_query.js"></script>
<div id='loading2' style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%; height: 100%;">
	<div data-options="region:'center',border:false,onResize:upUploadResize" style="overflow: hidden;">
		<!-- 表格 begin -->
		<table id="upUpload_query_datagrid" toolbar="#toolbar" class="easyui-datagrid" style="width: auto; height: auto;"
			data-options="fitColumns:false,singleSelect:true,rownumbers:true,pagination:true,title:'未上报机械查询'">
			<thead>
				<tr>
					<th data-options="field:'vehicleDef',width:100,align:'center'">整机编号</th>
					<th data-options="field:'dealerName',width:150,align:'left'">经销商</th>
					<th data-options="field:'areaName',width:100,align:'center'">区域</th>
					<th data-options="field:'vehicleStatus',width:100,align:'center'">机械状态</th>
					<th data-options="field:'modelName',width:100,align:'center'">机型</th>
					<th data-options="field:'vehicleCode',width:100,align:'center'">机器代号</th>
					<th data-options="field:'vehicleArg',width:80,align:'center'">配置号</th>					
					<th data-options="field:'ownerName',width:100,align:'center'">客户姓名</th>
					<th data-options="field:'ownerPhoneNumber',width:120,align:'center'">客户联系电话</th>
					<th data-options="field:'gpsTime',width:130,align:'center'">定位时间</th>
					<th data-options="field:'lon',width:100,align:'center'">经度</th>
					<th data-options="field:'lat',width:100,align:'center'">纬度</th>
					<th data-options="field:'speed',width:50,align:'center'">速度</th>
					<th data-options="field:'referencePosition',width:200,align:'center'">参考位置</th>
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
					<td>
						<input id="dealerId_search" name="dealerId_search" class="easyui-combotree" style="width: 150px;"
							data-options="url:'run/run_getDealerAreas4Tree.action',multiple:true ,onlyLeafCheck:false,cascadeCheck:true" />
					</td>
					 <td align="right">
					  机械状态:
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
						超过N天未上报:
					</td>
					<td>
						<select id="days_search" class="easyui-combobox" name="days_search" style="width: 150px;">
							<option value="1">1天</option>
							<option value="2">2天</option>
							<option value="3">3天</option>
							<option value="4">4天</option>
							<option value="5">5天</option>
							<option value="6">6天</option>
							<option value="7">7天</option>
							<option value="30">1个月</option>
							<option value="60">2个月</option>
							<option value="90">3个月</option>
							<option value="180">6个月</option>
							<option value="360">12个月</option>
						</select>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">
						机型:
					</td>
					<td>
						<input id="vehicleModel_search" class="easyui-combobox" style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
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
					<td colspan="4">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search" onclick="queryUpUpload()">查询</a>
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
			$('#upUpload_query_datagrid').datagrid('resize', {
				width : wid - 2,
				height : hei
			});
		});
	}
	var delayTime;
	$.parser.onComplete = function(obj) {
		$('#upUpload_query_datagrid').datagrid('columnMoving');
		if (delayTime)
			clearTimeout(delayTime);
		delayTime = setTimeout(show, 1);
	};
</script>
