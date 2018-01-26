<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${basePath}js/composite_query.js"></script>
<style type="text/css">
fieldset div ul {
	margin: 0px;
	padding: 0px;
	list-style-type: none;
	vertical-align: middle;
}
fieldset div li {
	float: left;
	display: block;
	width: 150px;
	height: 20px;
	line-height: 20px;
	font-size: 12px;
	/*font-weight: bold;*/
	color: #00000;
	text-decoration: none;
	text-align: left;
	background: #ffffff;
}
</style>
<div id='loading_run'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:compositeMainResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="composite_datagrid" toolbar="#tlb_composite" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'run/run_compositeQuery.action',
            singleSelect:true,rownumbers:true,pagination:true,title:'综合查询',onLoadSuccess:composite_datagridLoad">
             <thead>  
            <tr>  
                <!-- 机械信息 -->
                <th data-options="field:'vehicleId',hidden:true">整机ID</th>
                <th data-options="field:'vehicleDef',width:130,align:'center',sortable:true,formatter:function(value, row, index) {
                return '<a onclick=openDetailWin(\''+row.vehicleId+'\')  style=\'color: blue;text-decoration: underline;cursor: pointer;\'>'+value+'</a>';
                }">整机编号</th>
                <th data-options="field:'typeName',width:130,align:'center',sortable:true,hidden:true">机械型号</th>  
                <th data-options="field:'modelName',width:100,align:'center',sortable:true,hidden:true">机械类型</th>
                <th data-options="field:'fixMan',width:100,align:'center',sortable:true,hidden:true">安装人</th>  
                <th data-options="field:'fixDate',width:150,align:'center',sortable:true,hidden:true">终端安装时间</th>
                <!-- <th data-options="field:'status',width:150,align:'center',sortable:true,formatter:function(val,row,index){if(val==1){ return '测试组';}else if(val==2){ return '已测组';}else if(val==3){ return '销售组';}else if(val==9){ return '停用组';}}">机械状态(组)</th>
                 --> 
                 <!-- 终端信息 -->
                 <th data-options="field:'unitId',hidden:true">终端ID</th>
                <th data-options="field:'unitSn',width:130,align:'center',sortable:true">终端序列号</th>  
                <th data-options="field:'supperierName',width:100,align:'center',sortable:true,hidden:true">终端供应商</th>
                <th data-options="field:'materialNo',width:100,align:'center',sortable:true,hidden:true">物料条码</th>  
                <th data-options="field:'simNo',width:150,align:'center',sortable:true,hidden:true">SIM卡号</th>
                <th data-options="field:'steelNo',width:150,align:'center',sortable:true,hidden:true">钢号</th>
                <th data-options="field:'unitType',width:100,align:'center',sortable:true,hidden:true">终端类型</th>
              
                <!-- 销售信息 -->
                <th data-options="field:'saleDate',width:100,align:'center',sortable:true,hidden:true">销售时间</th>  
                <th data-options="field:'dealerName',width:150,align:'center',sortable:true,hidden:true">经销商</th>
                <th data-options="field:'ownerName',width:150,align:'center',sortable:true,hidden:true">机主名称</th>
                <th data-options="field:'ownerPhone',width:100,align:'center',sortable:true,hidden:true">机主电话</th>
              
                <!-- 固定列 -->
                <th data-options="field:'position',width:50,align:'center',sortable:true,formatter:position">定位</th>
                <th data-options="field:'condition',width:80,align:'center',sortable:true,formatter:function(value, row, index) {
                  return '<img src=\'${basePath}images/history_conditions.png\' style=\'cursor: pointer;\'  onclick=openWorkInfoWin(\''+row.unitId+'\')/>';
                }">历史工况</th>  
               
                <!-- GPS信息 -->
                <th data-options="field:'lon',width:130,align:'center',sortable:true,hidden:true">经度</th>
                <th data-options="field:'lat',width:130,align:'center',sortable:true,hidden:true">纬度</th>  
                <th data-options="field:'speed',width:100,align:'center',sortable:true,hidden:true,formatter:fmtSpeed">速度</th>
                <th data-options="field:'direction',width:100,align:'center',sortable:true,hidden:true,formatter:fmtDegree">方向</th>  
                <th data-options="field:'gpsTime',width:150,align:'center',sortable:true,hidden:true">GPS定位时间</th>
                <th data-options="field:'vehicleState',width:150,align:'center',sortable:true,hidden:true">机械状态</th>
                <th data-options="field:'province',width:130,align:'center',sortable:true,hidden:true">省</th>  
                <th data-options="field:'city',width:100,align:'center',sortable:true,hidden:true">市</th>
                <th data-options="field:'county',width:100,align:'center',sortable:true,hidden:true">县</th>  
                <th data-options="field:'referencePosition',width:150,align:'left',sortable:true">参考位置</th>
                <th data-options="field:'locationState',width:150,align:'center',sortable:true,hidden:true,formatter:function(value, row, index) { if(value==1){return '是';}else{ return '否'}}">是否定位</th>
                <th data-options="field:'isLogin',width:150,align:'center',sortable:true,hidden:true,formatter:function(value, row, index) { if(value==0){return '是';}else{ return '否'}}">是否在线</th>
                
                <!-- 最新工况信息 -->
                 <th data-options="field:'engineCoolantTemperature',width:130,align:'center',sortable:true,formatter:fmtTemperature,hidden:true">发动机冷却液温度</th>
                <th data-options="field:'engineCoolantLevel',width:130,align:'center',sortable:true,formatter:fmtPercent,hidden:true">发动机冷却液液位</th>  
                <th data-options="field:'engineOilPressure',width:100,align:'center',sortable:true,formatter:fmtPressure,hidden:true">发动机机油压力</th>
                <th data-options="field:'engineFuelLevel',width:100,align:'center',sortable:true,formatter:fmtPercent,hidden:true">发动机燃油油位</th>  
                <th data-options="field:'engineSpeed',width:150,align:'center',sortable:true,formatter:fmtRotateSpeed,hidden:true">发动机转速</th>
                <th data-options="field:'hydraulicOilTemperature',width:150,align:'center',sortable:true,formatter:fmtTemperature,hidden:true">液压油温度</th>
                <th data-options="field:'systemVoltage',width:130,align:'center',sortable:true,formatter:fmtVoltagePressure,hidden:true">系统电压</th>  
                <th data-options="field:'beforePumpMainPressure',width:150,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机前泵主压压力</th>
                <th data-options="field:'afterPumpMainPressure',width:150,align:'center',sortable:true,formatter:fmtPressure2,hidden:true">挖掘机后泵主压压力</th>  
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
        <!--   	<th data-options="field:'productCode',width:150,align:'center',sortable:true,hidden:true">产品编号</th>-->
			    <th data-options="field:'engineOilTemperature',width:150,align:'center',sortable:true,formatter:fmtTemperature,hidden:true">发动机机油温度</th>
                <th data-options="field:'faultCode',width:150,align:'center',sortable:true,hidden:true">故障码</th>
                <th data-options="field:'alock',width:150,align:'center',sortable:true,hidden:true,formatter:lockabFormatter">A锁</th>
			    <th data-options="field:'block',width:150,align:'center',sortable:true,formatter:fmtTemperature,hidden:true,formatter:lockabFormatter">B锁</th>
                <th data-options="field:'clock',width:150,align:'center',sortable:true,hidden:true,formatter:lockcFormatter">使能状态</th>
                
                <th data-options="field:'isWork',width:150,align:'center',sortable:true,hidden:true,formatter:function(val,row,index){if(val==0){ return '否';}else{ return '是';}}">是否工作</th>
               <th data-options="field:'stamp',width:150,align:'center',sortable:true,hidden:true">上报工况时间</th>
                </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		<!-- 查询条件 begin -->
		<div id="tlb_composite" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
			    <td> 
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="openQueryConditionsWin()">综合条件</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-filter_search" onclick="openColumnsFilterWin()">显示列过滤</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
		
<!-- 显示列过滤窗口--begin -->
<div id="dlg_composite_columns" class="easyui-dialog"   data-options="modal:true,closed:true,resizable:false" title="过滤显示列"
		style="width: 800px; height: 480px;"
		buttons="#btns_composite_columns">
		<br/>
		<b><font color="red">提示 : 选中复选框即可在表格中显示列信息</font></b>
		是否全选<input type="checkbox" id="chk_isSelectAll" onclick="changeChk()">
		<br/>
		<fieldset>
			<legend>
				终端信息
			</legend>
			<div id="div_chk_units"></div>
		</fieldset>
		
		<fieldset>
			<legend>
				机械信息
			</legend>
			<div id="div_chk_vehicles"></div>
		</fieldset>
		
		<fieldset>
			<legend>
				GPS信息
			</legend>
			<div id="div_chk_gps"></div>
		</fieldset>
		
		<fieldset>
			<legend>
				工况信息
			</legend>
			<div id="div_chk_conditions"></div>
		</fieldset>
		
		<div id="btns_composite_columns">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="getCheckedValues()">设置</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_composite_columns').dialog('close')">取消</a>
	</div>
	</div>
<!-- 显示列过滤窗口--end  -->

<!-- 查询条件窗口--begin -->
<div id="dlg_composite_query_conditions" class="easyui-dialog" data-options="modal:true,closed:true,resizable:false" title="查询条件"
		style="width: 730px; height: 400px;" buttons="#btns_composite_query_conditions">
		<form action="" id="frm_composite_query_conditions">
			 <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">物料条码:</td>
		        <td>
		        	<input id="materialNo" style="width: 150px;">
		        </td>
		        
		        <td align="right">钢号:</td>
		        <td>
		        	<input id="steelNo" style="width: 150px;">
		        </td>
		        
		        <td align="right">SIM卡号:</td>
		        <td>
		        	<input id="simNo" style="width: 150px;">
		        </td>
		      </tr>
		      
		       <tr>
		        <td align="right"> 终端序列号:</td>
		        <td>
		        	<input id="unitSn" style="width: 150px;">
		        </td>
		        <td align="right">终端供应商:</td>
		        <td align="left">
		        	<input id="supperierSn" class="" name="supperierSn" style="width:150px"
		                 data-options="valueField:'supperierSn',textField:'supperierName',url:'${basePatd}sys/dicSupperier_getList.action'" /> 
		        </td>
		         <td align="right">终端类型:</td>
                <td align="left">
                	<input id="unitTypeSn" class="" name="unitTypeSn" style="width:150px"
		                 data-options="valueField:'unitTypeSn',textField:'unitType'" /> 
                </td>
		        </tr>
		      <tr> 
		        <td align="right">整机编号:</td>
		        <td><input id="vehicleDef" style="width: 150px;"></td>
		       
		        <td align="right">机械类型:</td>
		        <td><input id="typeId" style="width: 150px;"></td>
		        
		        <td align="right">机械型号:</td>
		        <td>
		        	<input id="modelId" name="modelId" class="easyui-combobox" style="width: 150px;"
						data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
                </td>
		      </tr>
		      
		        <tr>
		        
                 <td align="right">
						机械状态(组):
					</td>
					<td align="left">
						 <select id="status" class="easyui-combobox" name="status" style="width:150px;">  
							<option value="">全部</option> 
							<option value="1">测试组</option>  
							<option value="2">已测组</option>  
							<option value="3">销售组</option> 
							<option value="8">法务组</option>
							<option value="9">停用组</option>  
						 </select>
				</td>
		           
		        <td align="right">安装日期:</td>
		        <td align="left" colspan="3">从<input id="fixDate" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:160px"  /> 
		       到<input id="fixDate2"type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:160px"  /> 
		        </td> 
		        </tr>
		        <tr>
					<td align="right">
						经销商:
					</td>
					<td align="left">
					<select id="dealerId" name="dealerId" style="width: 150px;height:40px"
							data-options="url:'run/run_getDealerAreas4Tree.action',onlyLeafCheck:true,cascadeCheck:true" multiple="multiple"></select>
					</td>
					
					<td align="right">销售日期:</td>
		        <td align="left" colspan="3">从<input id="saleDate" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:160px"  /> 
		       到<input id="saleDate2"type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:160px"  /> 
		        </td>
		        
		      
		        </tr>
		        
		        <tr>
		        <td align="right">客户名称:</td>
		        <td><input id="ownerName" style="width: 150px;"></td>
				   
		        <td align="right">最后位置时间:</td>
		        <td align="left" colspan="3">从<input id="gpsTime" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"  /> 
		       到<input id="gpsTime2"type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px"  /> 
		        </td>
		        </tr>
		        
		        <tr>
		         <td align="right">速度:</td>
		       <td>从<input id="speed"class="easyui-numberbox" style="width: 60px;">到<input id="speed2"class="easyui-numberbox" style="width: 60px;"></td>
		      
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
					
					<td align="right">是否工作:</td>
					<td align="left">	
					<select id="isWork" class="easyui-combobox" name="isWork" style="width:150px;">  
						<option value="">全部</option> 
						<option value="0">否</option>  
						<option value="1">是</option>   
			    	</select> </td>
		        </tr>
		        
		         <tr>
		            <td align="right">累计工作小时:</td>
		       <td>从<input id="totalWorkingHours"class="easyui-numberbox" style="width: 60px;">到<input id="totalWorkingHours2"class="easyui-numberbox" style="width: 60px;"></td>
		      
		       <td align="right">
						省:
					</td>
					<td align="left">
						<select id="province" name="province" class="easyui-combobox" style="width: 150px;"
							data-options="url:'sys/maparea_getProvinceList.action' ,
							valueField:'name',
                 			textField:'name',onSelect:provinceChange">
                 			</select>
					</td>
					  <td align="right">
						市:
					</td>
					<td align="left">
						<select id="city" name="city" class="easyui-combobox" style="width: 150px;"
							data-options="url:'sys/maparea_getCityList.action',
							valueField:'name',
                 			textField:'name'"></select>
					</td> 
				</tr>
				<tr>
				 <td align="right">
						锁车状态:
					</td>
					<td align="left">
						<select id="lock" class="easyui-combobox" name="lock" style="width:150px;">  
						<option value="">全部</option> 
						<option value="a_lock_2">A锁已锁</option>  
						<option value="b_lock_2">B锁已锁</option> 
						<option value="a_lock_1">A锁未执行</option>  
						<option value="b_lock_1">B锁未执行</option>
						<option value="a_lock_3">A锁已解锁</option>  
						<option value="b_lock_3">B锁已解锁</option>
						<option value="c_lock_1">开启防拆</option>  
						<option value="c_lock_2">禁止防拆</option>   
				    </select>
					</td>
				<td align="right">是否有警:</td>
					<td align="left">	
					<select id="alarmFlag" class="easyui-combobox" name="isWork" style="width:150px;">  
						<option value="">全部</option> 
						<option value="0">有</option>  
						<option value="1">无</option>   
			    	</select> </td>
					<td align="right">n天未上报:</td>
		        <td>
      				<select id="daysUnuplad" class="easyui-combobox" name="days_search" style="width:150px;">  
					<option value="">请选择</option> 
					<option value="1">1天</option>  
					<option value="2">2天</option> 
					<option value="3">3天</option>  
					<option value="4">4天</option> 
					<option value="5">5天</option>  
					<option value="6">6天</option> 
					<option value="7">7天</option>  
					<option value="8">7天以上</option>  
			    	</select> 
                  </td>
                 </tr>
                 <tr>
					<td align="right">n天未工作:</td>
		        <td>
      				<select id="daysUnwork" class="easyui-combobox" name="days_search" style="width:150px;">  
					<option value="">请选择</option> 
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
					</tr>
		        </table>
		</form>
		<div id="btns_composite_query_conditions">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="queryComposite()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-no"
			onclick="javascript:$('#frm_composite_query_conditions').form('clear')">重置</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_composite_query_conditions').dialog('close')">取消</a>
	</div>
	</div>
<!-- 查询条件窗口--end  -->

<script type="text/javascript">
    function show(){
        $("#loading_run").fadeOut("fast", function(){
            $(this).remove();
             //修改表格的宽度
            var hei =$('#main').height();
            $('#composite_datagrid').datagrid('resize', {height:hei});
        });
        
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#composite_datagrid').datagrid('columnMoving');
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script>