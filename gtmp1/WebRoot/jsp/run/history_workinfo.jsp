<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史工况</title>
<script type="text/javascript" src="${basePath}js/common/jshash.js"></script> 
<script type="text/javascript" src="${basePath}js/common/globe.js"></script> 
<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${basePath}js/history_workinfo.js"></script>

  </head>
  
  <body>
  <div id='loading' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='${basePath}images/loading.gif'/> 
</div>
  <div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:historyWorkInfoResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="work_info_datagrid" toolbar="#tlb_work_info" 
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="
            singleSelect:true,rownumbers:true,pagination:true">
             <thead>  
            <tr>  
            
                <th data-options="field:'vehicleDef',width:100,align:'center',sortable:true">整机编号</th>
                <th data-options="field:'engineCoolantTemperature',width:104,align:'center',sortable:true,formatter:fmtTemperature1">发动机冷却液温度</th>
                <th data-options="field:'engineCoolantLevel',width:104,align:'center',sortable:true,formatter:fmtPercent">发动机冷却液液位</th>  
                <th data-options="field:'engineOilPressure',width:91,align:'center',sortable:true,formatter:fmtPressure">发动机机油压力</th>
                <th data-options="field:'engineFuelLevel',width:91,align:'center',sortable:true,formatter:fmtPercent">发动机燃油油位</th>  
                <th data-options="field:'engineSpeed',width:100,align:'center',sortable:true,formatter:fmtRotateSpeed">发动机转速</th>
                <th data-options="field:'hydraulicOilTemperature',width:65,align:'center',sortable:true,formatter:fmtTemperature">液压油温度</th>
                <th data-options="field:'systemVoltage',width:55,align:'center',sortable:true,formatter:fmtVoltagePressure">系统电压</th>  
                <th data-options="field:'beforePumpMainPressure',width:117,align:'center',sortable:true,formatter:fmtPressure_2">挖掘机前泵主压压力</th>
                <th data-options="field:'afterPumpMainPressure',width:117,align:'center',sortable:true,formatter:fmtPressure_2">挖掘机后泵主压压力</th>  
                <th data-options="field:'pilotPressure1',width:104,align:'center',sortable:true,formatter:fmtPressure_03">挖掘机先导压力1</th>
                <th data-options="field:'pilotPressure2',width:104,align:'center',sortable:true,formatter:fmtPressure_03">挖掘机先导压力2</th>
                <th data-options="field:'beforePumpPressure',width:117,align:'center',sortable:true,formatter:fmtPressure_03">挖掘机前泵负压压力</th>
                <th data-options="field:'afterPumpPressure',width:117,align:'center',sortable:true,formatter:fmtPressure_03">挖掘机后泵负压压力</th>
			    <th data-options="field:'proportionalCurrent1',width:117,align:'center',sortable:true,formatter:fmtCurrent">挖掘机比例阀电流1</th>
                <th data-options="field:'proportionalCurrent2',width:117,align:'center',sortable:true,formatter:fmtCurrent">挖掘机比例阀电流2</th>
                <th data-options="field:'gear',width:42,align:'center',sortable:true,formatter:function(val,row,index){if(val==0){ return '--';}else{ return val;}} ">挖机档位</th>
                <th data-options="field:'totalWorkingHours',width:78,align:'center',sortable:true">累计工作小时</th>
        		<th data-options="field:'highEngineCoolantTemperVal',width:156,align:'center',sortable:true,formatter:fmtTemperature">发动机冷却液温度高报警值</th>
                <th data-options="field:'lowEngineOilPressureAlarmValue',width:143,align:'center',sortable:true,formatter:fmtPressure">发动机机油压力低报警值</th>
			    <th data-options="field:'highVoltageAlarmValue',width:130,align:'center',sortable:true,formatter:fmtVoltagePressure">系统电压高报警设置值</th>
                <th data-options="field:'lowVoltageAlarmValue',width:130,align:'center',sortable:true,formatter:fmtVoltagePressure">系统电压低报警设置值</th>
                <th data-options="field:'highHydraulicOilTemperAlarmVal',width:104,align:'center',sortable:true,formatter:fmtTemperature">液压油温高报警值</th>
                <th data-options="field:'toothNumberValue',width:65,align:'center',sortable:true">飞轮齿数值</th>
    			<th data-options="field:'monitorSoftwareCode',width:208,align:'center',sortable:true">监控器（显示器）供应商内部软件代号</th>
                <th data-options="field:'monitorYcSoftwareCode',width:208,align:'center',sortable:true">监控器（显示器）玉柴内部软件版本号</th>
			    <th data-options="field:'controllerSoftwareCode',width:156,align:'center',sortable:true">控制器供应商内部软件代号</th>
                <th data-options="field:'controllerYcSoftwareCode',width:143,align:'center',sortable:true">控制器玉柴内部软件代号</th>
                
			    <th data-options="field:'engineOilTemperature',width:91,align:'center',sortable:true,formatter:fmtTemperature">发动机机油温度</th>
                <th data-options="field:'faultCode',width:39,align:'center',sortable:true,formatter:fmtFaultCode">故障码</th>
                
                <th data-options="field:'isWork',width:52,align:'center',sortable:true,formatter:function(val,row,index){if(val==0){ return '否';}else{ return '是';}}">是否工作</th>
                <th data-options="field:'stamp',width:150,align:'center',sortable:true">上报工况时间</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="tlb_work_info" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      
		       <tr>
		       <td >
					开始时间:
				</td>
				<td>
				 <input id="start_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px">
				</td>
				<td>	
					结束时间: </td>
				<td><input id="end_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px">
				</td>
		        
			    <td>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			    </td>
			    <td>图表类型</td>
			    <td>
			    <select id="chart_type" class="easyui-combobox" name="chart_type" style="width:120px;">  
						    <option	value="1">发动机冷却液温度</option>  
						    <option value="2">液压油温度</option>  
						    <option value="3">发动机燃油油位</option> 
						    <option value="4">发动机机油压力</option>  
						    <option value="5">发动机转速</option> 
						    <option value="6">档位</option> 
						</select>
			     </td>  			    
			    <td>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-baobiao" onclick="javascript:openChartWin();">图表</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
  </body>
  <script type="text/javascript"  >
    function show(){
        $("#loading").fadeOut("normal", function(){
            $(this).remove();
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#start_time').val(getTodayZero());
    	$('#end_time').val(new Date().formatDate(timeFormat));
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 
</html>
