<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.map_toolbar{
 position:relative;
 top:-2px !important;
 font-size:11px; 
 vertical-align: center;
 background-color: #e0edfe;
 cursor: pointer;
 }
</style>
<script type="text/javascript" src="${basePath}js/run.js"></script>

<div id='loading_run'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true" style="width: 100%; height: 100%;">
	<div id="run_west_panel"
		data-options="region:'west',split:true,title:'监控面板',doSize:true"
		style="width: 300px;">
		<!-- 整机编号/SIM卡号 -->
		<input type="text" id="run_tree_text"
			style="font-size: 12px; color: #888; background: none; border: 1px solid #CEDFEF"
			value="整机编号/SIM卡号" onblur="searchTextOnblur()"
			onfocus="searchTextOnFocus()">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" onclick="queryRunTree()">查询</a>
		<c:if test="${userInfo.roles[0].roleId!=84}">
			<a href="javascript:void(0)" onclick="openQueryDialog()">更多条件</a>
	    </c:if>
		<div id="tabs_tree" class="easyui-tabs" data-options="fit2:true">
			<div title="机械列表">
				<ul id="run_tree"
					data-options="url:'${basePath}run/run_getDealerAreasList.action',onlyLeafCheck:false"></ul>
			</div>
			<div title="监控列表">
				<table class="easyui-datagrid"
					data-options="url:'${basePath}run/run_getWatchVehicle.action',
                     fitColumns:true,rownumbers:true,onRowContextMenu:monitorRightClick"
					id="grid_run_monitor">
					<thead>
						<tr>
							<th
								data-options="field:'ck',width:50,align:'center',checkbox:true"></th>
							<th
								data-options="field:'vehicleId',width:130,align:'center',hidden:true"></th>
							<th
								data-options="field:'unitId',width:130,align:'center',hidden:true">
								unitId
							</th>
							<th
								data-options="field:'supperierSn',width:130,align:'center',hidden:true">
								supperierSn
							</th>
							<th
								data-options="field:'unitSn',width:130,align:'center',hidden:true">
								unitSn
							</th>
							<th data-options="field:'vehicleDef',width:130,align:'center'">
								整机编号
							</th>
							<th
								data-options="field:'simNo',width:130,align:'center',hidden:true"></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>

	</div>
	<div data-options="region:'center',doSize:true">
		<div class="easyui-layout" data-options="fit:true"
			style="width: 100%; height: 100%;">
			<div data-options="region:'center',tools:'#map_tb'" id="run_map_div"
				title="我的地图" style="overflow: hidden">
				<iframe id="ifm_map_run" name="ifm_map_run"
					src="${basePath}jsp/run/run_map.jsp"
					style="width: 100%; height: 100%; border-color: transparent; border-image: none; border-style: solid; border-width: 0 0px 0px;"></iframe>
			</div>

			<div data-options="region:'south',split:true,onResize: southResize" style="height: 160px;">
				<div id="tabs_south" class="easyui-tabs" data-options="fit:true"  style="overflow: hidden">
					<!-- gps信息刷新表格--begin -->
					<div title="GPS信息" style="overflow: visible;">
						<table class="easyui-datagrid"
							data-options="
            fitColumns:false,singleSelect:false,rownumbers:true,onRowContextMenu:gpsRightClick,onDblClickRow:gpsDblClick"
							id="grid_gpsInfo">
							<thead>
								<tr>
									<th data-options="field:'vehicleDef',width:100,align:'center',formatter:vehicleStateFormat">
										整机编号
									</th>
									<th
										data-options="field:'locationState',width:70,align:'center',formatter:function(value,row,index){
										  if(value=='1'){
										    value='是';
										  }else{
										    value='否';
										  }
										  if(row.alarmFlag==1){//警情上报
											  return '<font color=\'red\'>'+value+'</font>';
											}else{
												return value;
											}
										}">
										是否定位
									</th>
									<th data-options="field:'speed',width:100,align:'center',formatter:vehicleStateFormat">
										速度(千米/小时)
									</th>
									<th data-options="field:'direction',width:50,align:'center',formatter:vehicleStateFormat">
										方向
									</th>
									<th data-options="field:'gpsTime',width:130,align:'center',formatter:vehicleStateFormat">
										GPS定位时间
									</th>
									<th
										data-options="field:'vehicleState',width:200,align:'center',formatter:vehicleStateFormat">
										机械状态
									</th>
									<th
										data-options="field:'lockState',width:220,align:'center',formatter:vehicleStateFormat">
										锁车状态
									</th>
									<th data-options="field:'referencePosition',width:300,align:'center',formatter:function(value,row,index){
										  if(value=='null'){
										     value='';
										  }
										  if(value){
										      if(row.alarmFlag==1){//警情上报
											   return '<font color=\'red\'>'+value+'</font>';
											 }else{
											   return value;
											 }
											}
										}">
										参考位置
									</th>
									
									<th
										data-options="field:'nowTime',width:130,align:'center',formatter:vehicleStateFormat">
										上报时间
									</th>
								</tr>
							</thead>
						</table>
					</div>
					<!-- gps信息刷新表格--end -->

					<!-- 工况信息刷新表格--begin -->
					<div title="工况信息" style="overflow: visible;">
						<table class="easyui-datagrid"
							data-options="fitColumns:false,
            singleSelect:false,rownumbers:true,onRowContextMenu:workInfoRightClick"
							id="grid_workInfo">
							<thead>
								<tr>
								<th data-options="field:'vehicleDef',width:100,align:'center'">
										整机编号
									</th>
									   <th data-options="field:'engineCoolantTemperature',width:130,align:'center',sortable:true,formatter:fmtTemperature">发动机冷却液温度</th>
                <th data-options="field:'engineCoolantLevel',width:130,align:'center',sortable:true,formatter:fmtPercent,hidden:false">发动机冷却液液位</th>  
                <th data-options="field:'engineOilPressure',width:100,align:'center',sortable:true,formatter:fmtPressure,hidden:false">发动机机油压力</th>
                <th data-options="field:'engineFuelLevel',width:100,align:'center',sortable:true,formatter:fmtPercent,hidden:false">发动机燃油油位</th>  
                <th data-options="field:'engineSpeed',width:150,align:'center',sortable:true,formatter:fmtRotateSpeed,hidden:false">发动机转速</th>
                <th data-options="field:'hydraulicOilTemperature',width:150,align:'center',sortable:true,formatter:fmtTemperature,hidden:false">液压油温度</th>
                <th data-options="field:'systemVoltage',width:130,align:'center',sortable:true,formatter:fmtVoltagePressure,hidden:false">系统电压</th>  
                <th data-options="field:'beforePumpMainPressure',width:100,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机前泵主压压力</th>
                <th data-options="field:'afterPumpMainPressure',width:100,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机后泵主压压力</th>  
                <th data-options="field:'pilotPressure1',width:150,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机先导压力1</th>
                <th data-options="field:'pilotPressure2',width:150,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机先导压力2</th>
                <th data-options="field:'beforePumpPressure',width:150,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机前泵负压压力</th>
                <th data-options="field:'afterPumpPressure',width:150,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机后泵负压压力</th>
			    <th data-options="field:'proportionalCurrent1',width:150,align:'center',sortable:true,formatter:fmtCurrent,hidden:true">挖掘机比例阀电流1</th>
                <th data-options="field:'proportionalCurrent2',width:150,align:'center',sortable:true,formatter:fmtCurrent,hidden:true">挖掘机比例阀电流2</th>
                <th data-options="field:'gear',width:150,align:'center',sortable:true,hidden:true">挖机档位</th>
                <th data-options="field:'totalWorkingHours',width:150,align:'center',sortable:true,hidden:true">累计工作小时</th>
        		<!-- <th data-options="field:'correctHours',width:150,align:'center',sortable:true">修正工作小时</th> -->
        		<th data-options="field:'highEngineCoolantTemperVal',width:150,align:'center',sortable:true,formatter:fmtTemperature,hidden:true">发动机冷却液温度高报警设置值</th>
                <th data-options="field:'lowEngineOilPressureAlarmValue',width:150,align:'center',sortable:true,formatter:fmtPressure,hidden:true">发动机机油压力低报警设置值</th>
			    <th data-options="field:'highVoltageAlarmValue',width:150,align:'center',sortable:true,formatter:fmtVoltagePressure,hidden:true">系统电压高报警设置值</th>
                <th data-options="field:'lowVoltageAlarmValue',width:150,align:'center',sortable:true,formatter:fmtVoltagePressure,hidden:true">系统电压低报警设置值</th>
                <th data-options="field:'highHydraulicOilTemperAlarmVal',width:150,align:'center',sortable:true,formatter:fmtTemperature,hidden:true">液压油温高报警值</th>
                <th data-options="field:'toothNumberValue',width:150,align:'center',sortable:true,hidden:true">飞轮齿数设置值</th>
    			<th data-options="field:'monitorSoftwareCode',width:150,align:'center',sortable:true,hidden:true">监控器（显示器）供应商内部软件代号</th>
                <th data-options="field:'monitorYcSoftwareCode',width:150,align:'center',sortable:true,hidden:true">监控器（显示器）玉柴内部软件版本号</th>
			    <th data-options="field:'controllerSoftwareCode',width:150,align:'center',sortable:true,hidden:true">控制器供应商内部软件代号</th>
                <th data-options="field:'controllerYcSoftwareCode',width:150,align:'center',sortable:true,hidden:true">控制器玉柴内部软件代号</th>
                <th data-options="field:'productCode',width:150,align:'center',sortable:true">产品编号</th>
			    <th data-options="field:'engineOilTemperature',width:150,align:'center',sortable:true,formatter:fmtTemperature">发动机机油温度</th>
                <th data-options="field:'faultCode',width:150,align:'center',sortable:true">故障码</th>
                
                <th data-options="field:'isWork',width:150,align:'center',sortable:true,formatter:function(val,row,index){if(val==0){ return '否';}else{ return '是';}}">是否工作</th>
               <th data-options="field:'nowTime',width:150,align:'center',sortable:true">上报工况时间</th>
 
								</tr>
							</thead>
						</table>
					</div>
					<!-- 工况信息刷新表格--end -->

					<!-- 指令信息--begin -->
					<div title="指令信息" style="overflow: visible;">
						<table class="easyui-datagrid"
							data-options="
            fitColumns:true,singleSelect:false,rownumbers:true,onRowContextMenu:responseRightClick"
							id="grid_response">
							<thead>
								<tr>
									<th data-options="field:'responseType',width:80,align:'center'">
										回应类型
									</th>
									<th data-options="field:'remark',width:130,align:'center'">
										消息内容
									</th>
								</tr>
							</thead>
						</table>
					</div>
					<!-- 指令信息--end -->
				</div>
			</div>
		</div>
	</div>
	<div id="map_tb">
		<input type="button" id="online_run_btn" value="在线(0)" class="map_toolbar" onclick="openOnlineWin()"/>
		<input type="button" id="offline_run_btn"value="离线(0)" class="map_toolbar" onclick="openOfflineWin()"/>
		<input type="button" id="alarm_run_btn"value="警情(0)" class="map_toolbar" onclick="alarmopen()"/>
		
		<a href="#" class="icon-map_area"
			onclick="javascript:ifm_map_run.window.operateMap(8)" title="拉框搜索"></a>
		
		<a href="#" class="icon-move"
			onclick="javascript:ifm_map_run.window.operateMap(3)" title="恢复默认工具"></a>
		<a href="#" id="afullscrenn" class="icon-fullscreen"
			onclick="javascript:fullscreen();" title="全屏"></a>
		<!--<a href="#" class="icon-alarm" onclick="javascript:alarmopen()"title="警情"></a> -->
		<a href="#" class="icon-bigger"
			onclick="javascript:ifm_map_run.window.operateMap(1)" title="放大"></a>
		<a href="#" class="icon-smaller"
			onclick="javascript:ifm_map_run.window.operateMap(2)" title="缩小"></a>

		<a href="#" class="icon-ceju"
			onclick="javascript:ifm_map_run.window.operateMap(4)" title="测距"></a>
		<a href="#" class="icon-center"
			onclick="javascript:ifm_map_run.window.operateMap(5)" title="居中"></a>
			<a href="#" class="icon-clear"
			onclick="javascript:ifm_map_run.window.operateMap(7)" title="清除机械信息"></a>
		<a href="#" class="icon-biaozhu"
			onclick="javascript:ifm_map_run.window.operateMap(6)" title="添加标注"></a>
		<input type="checkbox" name='isShowPlateNumber' id="isShowPlateNumber" checked="checked" onclick="javascript:ifm_map_run.window.isShowPlateNumber(this)">
		<label for="isShowPlateNumber" style="font-size:12px;color:black;font-weight:normal">显示整机编号</label>
	</div>

	<div id="menu_distribute" class="easyui-menu" style="width: 130px;">
	 	 <div onclick="distributeInDealer()">
			机械分布
		</div>
	</div>
	<div id="menu_run_right" class="easyui-menu" style="width: 150px;">
		<c:forEach var="module" items="${sessionScope.modules}"> 
		<c:if test="${module.moduleType==2 && not empty module.parentId}">
  			<div onclick="${module.url}">
			 ${module.moduleName}
		   </div>
		</c:if>
   </c:forEach>  
   <!-- 开发时写死的菜单，正式部署时，可删除注释的代码
	 <div onclick="sendCommand(32)">
			实时定位
		</div>
		<div onclick="sendCommand(34)">
			终端复位
		</div>
		<div onclick="openParamsSetWin()">
			参数设置
		</div>
		<div onclick="remove()">
			工况数据采集
		</div>
		<div onclick="sendCommand(53)">
			工作时间清零
		</div>
		<div onclick="openLockWin()">
			锁车管理
		</div> -->
		<div class="menu-sep"></div>
		<div onclick="openHistoryPlay()">
			历史轨迹回放
		</div>
		<div onclick="openWorkInfoWin()">
			历史工况查询
		</div>
		<div onclick="insertWorkInfoWin()">
			工况信息导入
		</div>
		<div onclick="openWorkHoursWin()">
			累计工作时间
		</div>
		<div class="menu-sep"></div>
		<div onclick="addMonitor()" id="menu_add_monitor">
			加入监控列表
		</div>
		<div onclick="clearMonitorData(1)" id="menu_remove_monitor">
			从监控列表中移除
		</div>
		<div onclick="clearMonitorData(2)">
			清空监控列表
		</div>
		<!-- <div onclick="openDetailWin()">
			机械详细信息
		</div> -->
	</div>

	<div id="menu_grid_gps" class="easyui-menu" style="width: 130px;">
		<div onclick="removeGrid(1,'grid_gpsInfo')">
			删除选中记录
		</div>
		<div onclick="removeGrid(2,'grid_gpsInfo')">
			删除全部
		</div>
	</div>

	<div id="menu_grid_work" class="easyui-menu" style="width: 130px;">
		<div onclick="removeGrid(1,'grid_workInfo')">
			删除选中记录
		</div>
		<div onclick="removeGrid(2,'grid_workInfo')">
			删除全部
		</div>
	</div>

	<div id="menu_grid_response" class="easyui-menu" style="width: 130px;">
		<div onclick="removeGrid(1,'grid_response')">
			删除选中记录
		</div>
		<div onclick="removeGrid(2,'grid_response')">
			删除全部
		</div>
	</div>
	<!--指令相关窗口-->
	<jsp:include page="run_command.jsp"></jsp:include>

	<!--密码确认窗口-->
	<jsp:include page="input_pwd.jsp"></jsp:include>
	
	<!-- 更多条件窗口 --begin-->
	<div id="dlg_more_condition" class="easyui-dialog" title="更多查询条件"
		data-options="iconCls:'icon-search',modal:true"
		style="padding: 5px; width: 600px; height: 230px;" closed="true"
		buttons="#btns_more_condition">
		<form id="frm_run_query" name="frm_run_query" method="POST"
			metdod="post" tdeme="simple"
			action="${basePath}run/run_getVehilces.action">
			<table cellpadding="4" cellspacing="0"
				style="font-size: 12px; width: 100%;">
				<tr>
					<td align="right">
						经销商:
					</td>
					<td align="left">
						<select id="dealerId" name="dealerId" style="width: 150px;"
							data-options="onlyLeafCheck:true,cascadeCheck:true" multiple></select>
					</td>
					<td align="right">
						机械类型:
					</td>
					<td align="left">
						<input id="typeId" name="typeId" style="width: 150px;" />
					</td>
				</tr>
				<tr>
					<td align="right">
						机械型号:
					</td>
					<td align="left">
						<input id="modelId" name="modelId" class="easyui-combobox"
							style="width: 150px;"
							data-options="
							url:'vehicle/vehicleModel_getList.action',
                 valueField:'modelId',
                 textField:'modelName'
                 " />
					</td>
					<td align="right">
						终端序列号:
					</td>
					<td align="left">
						<input id="unitSn" name="unitSn" type="text" />
					</td>
				</tr>
				<tr>
					<td align="right">
						钢号:
					</td>
					<td align="left">
						<input id="steelNo" name="steelNo" type="text" />
					</td>

					<td align="right">
						物料编号:
					</td>
					<td align="left">
						<input id="materialNo" name="materialNo" type="text" />
					</td>
				</tr>
				
				<tr>
					<td align="right">
						是否在线:
					</td>
					<td align="left">
						<select id="isLogin" class="easyui-combobox" name="isLogin" style="width:150px;">  
						<option value="">全部</option> 
						<option value="0">在线</option>  
						<option value="1">离线</option>  
				    </select>
					</td>

					<td align="right">
						是否工作:
					</td>
					<td align="left">
							<select id="isWork" class="easyui-combobox" name="isWork" style="width:150px;">  
						<option value="">全部</option> 
						<option value="1">是</option>  
						<option value="0">否</option>  
				    </select>
					</td>
				</tr>
			</table>

		</form>
	</div>
	<div id="btns_more_condition">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="queryRunTree()">查询</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_more_condition').dialog('close')">取消</a>
	</div>
	<!-- 更多条件窗口 --end-->
	
	<!-- 在线、离线 窗口 -->
	<jsp:include page="onoffine_vehicle.jsp"></jsp:include>
	<jsp:include page="offline_vehicle.jsp"></jsp:include>
</div>

<script type="text/javascript">
	function southResize(w, h) {
		try {
			$('#grid_gpsInfo').datagrid('options');
			$('#grid_gpsInfo').datagrid('resize', {width:w-2,height:h-35});
			$('#grid_workInfo').datagrid('options');
			$('#grid_workInfo').datagrid('resize',{width:w-2,height:h-35});
			$('#grid_response').datagrid('options');
			$('#grid_response').datagrid('resize',{width:w-2,height:h-35});
		} catch(e) {}
	}

    function show(){
        $("#loading_run").fadeOut("fast", function(){
            $(this).remove();
            
            initRunTree();
            
            //定时刷新指令信息 3秒
            refreshResponsInfo();
            
            //定时刷新GPS,工况,在线、不在线  4分钟
            refreshGpsWorkOnLine();
            
            //定时刷新警情 1分钟
            refreshAlarm();
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	var w = $('#tabs_south').width();
    	var h = $('#tabs_south').height();
    	$('#grid_gpsInfo').datagrid('resize', {width:w-2,height:h-35});
    	$('#grid_workInfo').datagrid('resize',{width:w-2,height:h-35});
    	$('#grid_response').datagrid('resize',{width:w-2,height:h-35});
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script>
