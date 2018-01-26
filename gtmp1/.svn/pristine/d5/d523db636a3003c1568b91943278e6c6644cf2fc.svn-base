<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机械测试日志</title>
<script type="text/javascript" src="${basePath}js/common/jshash.js"></script> 
<script type="text/javascript" src="${basePath}js/common/globe.js"></script> 
<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>	
<script type="text/javascript">
  var pdata=null;
$(function(){
		    var wid = $(document.body).width();
			var hei = $(document.body).height();
			
			//初始化地图数据
			if(window.parent){
			   pdata=window.parent.unitid;
			   velDef = window.parent.vehicleDefs;
			   $('#vehicleDef_test').val(velDef);
			   var url='';
			   var params={};
			  if(pdata){
			   url='${basePath}vehicle/vehicle_testsearch.action';
			   params={
					   unitId:pdata,
					   vehicleDef:velDef,
					   //startTime:getTodayZero(),
					   //endTime:new Date().formatDate(timeFormat)
					   
			   };
			  }
			  $('#work_info_datagrid').datagrid({
					width : wid,
					height :hei,
					url:url,
					queryParams:params
			   });
			}
	});
//窗口大小变化
function historyWorkInfoResize(w,h){
	if($('#work_info_datagrid')){
		try{
		  $('#work_info_datagrid').datagrid('options');
		  $('#work_info_datagrid').datagrid('resize', {  
				width : w-1,
				height:h
		 }); 
		}catch(e){
		}
	}
}
function queryTest(){
    $('#work_info_datagrid').datagrid('load',{    
		vehicleDef: $('#vehicleDef_test').val(),    
		fixDateStart: $('#fixDateStart_test').val(),    
		fixDateEnd: $('#fixDateEnd_test').val(),
		typeId: $('#vehicleType_test').datebox('getValue')
    });  
}
function downExcel(){
	 var vehicleDef =  $('#vehicleDef_test').val();    
	 var vehicleType =   $('#vehicleType_test').datebox('getValue');
	 var startTime= $('#fixDateStart_test').val();
	 var endTime= $('#fixDateEnd_test').val();
	window.location.href=encodeURI(encodeURI("${basePath}vehicle/vehicle_exportToExcel.action?vehicleDef="+$('#vehicleDef_test').val()+"&startTime="+startTime+"&endTime="+endTime+"&vehicleType="+vehicleType));
	   return;
}



		 
function initPage(){
	
}

function addPram(){

}
</script>

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
                <th data-options="field:'commandSn',width:70,align:'center'">流水号</th>  
                <th data-options="field:'vehicleDef',width:100,align:'center'">整机编号</th>
                <th data-options="field:'userName',width:100,align:'center'">指令下发操作人</th>  
                <th data-options="field:'commandTypeName',width:100,align:'center'">指令类型</th>
                <th data-options="field:'param',width:100,align:'center'">指令类型参数</th>
                <th data-options="field:'commandResult',width:100,align:'center',formatter:function(val,row,index){if(val=='00' || val==0){ return '成功';}else{ return '失败';}}">网关回应</th>
                <th data-options="field:'unitBack',width:100,align:'center',formatter:function(val,row,index){if(val=='00' || val==0){ return '成功';}else{ return '失败';}}">终端回应</th>
                <th data-options="field:'stamp',width:150,align:'center'">测试时间</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="tlb_work_info" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		       <tr>
		        <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_test" name="vehicleDef_test" style="width: 150px;"></td>
		        <td align="right">指令类型:</td>
		        <td>
		        <input id="vehicleType_test" class="easyui-combobox" style="width: 150px;"
					data-options=" url:'${basePath}vehicle/vehicle_getCommandType.action',
                 	valueField:'commandTypeId', textField:'commandTypeName'" />
				</td>
		        </tr>
		        <tr>
			   <td align="right">测试开始时间:</td>
		        <td>
		        <input id="fixDateStart_test" name="fixDateStart_test" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />	
		       	</td>
		       <td align="right">测试结束时间:</td>
		        <td>
		        <input id="fixDateEnd_test" name="fixDateEnd_test" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />	
		        	
		        </td>
		          <td colspan="2"> 
		          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryTest()">查询</a>
		          <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
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
    	$('#fixDateStart_test').val(getTodayZero());
    	$('#fixDateEnd_test').val(new Date().formatDate(timeFormat));
    	$('#vehicleTest_datagrid').datagrid('columnMoving');
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 
</html>
