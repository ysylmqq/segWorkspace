<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/vehicle.js"></script> 
<style type="text/css">
fieldset legend {
	line-height: 20px;
	font-size: 14px;
	font-weight: bold;
	color: #666666;
}
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



 <div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:vehicleResize"  style="overflow:hidden;">
 
    <!-- 表格 begin -->
    <table id="vehicleTest_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}vehicle/vehicle_testsearch.action',queryParams:defQueryParams,
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'机械测试指令查询'">
             <thead>  
            <tr>  
                <th data-options="field:'commandSn',width:130,align:'center'">流水号</th>  
                <th data-options="field:'vehicleDef',width:100,align:'center'">整机编号</th>
                <th data-options="field:'userName',width:100,align:'center'">指令下发操作人</th>  
                <th data-options="field:'commandTypeName',width:100,align:'center'">指令类型</th>
                <th data-options="field:'param',width:100,align:'center'">指令类型参数</th>
                <th data-options="field:'commandResult',width:100,align:'center',formatter:function(val,row,index){if(val==0){ return '成功';}else{ return '失败';}}">网关回应</th>
                <th data-options="field:'unitBack',width:100,align:'center',formatter:function(val,row,index){if(val==0){ return '成功';}else{ return '失败';}}">终端回应</th>
                <th data-options="field:'stamp',width:150,align:'center'">测试时间</th>
            </tr>  
        </thead>
	 </table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		       <tr>
		        <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_test" name="vehicleDef_test" style="width: 150px;"></td>
		        <td align="right">指令类型:</td>
		        <td>
		        <input id="vehicleType_test" class="easyui-combobox" style="width: 150px;"
					data-options=" url:'vehicle/vehicle_getCommandType.action',
                 	valueField:'commandTypeId', textField:'commandTypeName'" />
				</td>
		        
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
		   
        
<iframe style="display: none" id="ifm_down"></iframe>
</div>
</div>
<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#vehicleTest_datagrid').datagrid('resize', {
             	width : wid-2,
             	height:hei
            }); 
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