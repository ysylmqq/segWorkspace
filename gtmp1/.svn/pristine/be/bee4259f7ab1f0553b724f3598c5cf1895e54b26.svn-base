<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/command_query.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:commandResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="command_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}run/command_search.action',queryParams:defQueryParams,
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'指令下发查询'">
             <thead>  
            <tr>
                <th data-options="field:'commandSn',width:130,align:'center', hidde:'true'">指令流水号</th>  
                <th data-options="field:'vehicleDef',width:100,align:'center'">整机编号</th>
                <th data-options="field:'dealerName',width:150,align:'left'">经销商</th>
				<th data-options="field:'areaName',width:100,align:'center'">区域</th>
				<th data-options="field:'vehicleStatus',width:100,align:'center'">机械状态</th>  
                <th data-options="field:'vehicleModelName',width:100,align:'center'">机型</th>  
                <th data-options="field:'vehicleCode',width:100,align:'center'">机械代号</th>  
                <th data-options="field:'vehicleArg',width:100,align:'center'">配置号</th>  
                <th data-options="field:'unitSn',width:100,align:'center'">终端序列号</th>  
                <th data-options="field:'simNo',width:120,align:'center'">SIM卡号</th>  
                <th data-options="field:'userName',width:100,align:'center'">操作员</th>
                <th data-options="field:'departName',width:100,align:'center'">部门</th>                
                <th data-options="field:'commandTypeName',width:130,align:'center'">指令类型</th>  
               <th data-options="field:'param',width:130,align:'center'">参数</th>  
               <th data-options="field:'gatewayBack',width:130,align:'center',formatter:function(val,row,index){if(val=='00' || val=='0'){ return '成功';}else if(val=='9999'){ return '失败';}else{ return '';}}">网关回应</th>  
               <th data-options="field:'unitBack',width:130,align:'center',formatter:function(val,row,index){if(val=='00' || val=='0'){ return '成功';}else if(val=='9999'){ return '失败';}else{ return '';}}">终端回应</th>  
               <th data-options="field:'rawData',width:130,align:'center', hidde:'true'">原始数据</th> 
                <th data-options="field:'pathFlag',width:120,align:'center',formatter:function(val,row,index){if(val==0){ return 'GPRS';}else if(val==1){ return 'GSM';}
                }">通道</th> 
                <th data-options="field:'stamp',width:150,align:'center'">时间</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		    <table style=" font-size: 12px;">
		      <tr>
				 <td align="right">
					整机编号：
				</td>
				<td>
				 <input id="vehicleDef_search" type="text" style="width:150px">
				</td>
				<td align="right">
					机型：
				</td>
				<td>
					<input id="vehicleModel_search" class="easyui-combobox" style="width:150px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
				</td>
		         <td align="right">
		         	机械代号：
		         </td>
		         <td>
		         	<input id="vehicleCode_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" />
		         </td>
		         <td align="right">
		         	配置号：
		         </td>
		         <td>
		         	<input id="vehicleArg_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleArg',textField:'vehicleArg'" />
		         </td>
		         <td></td>
				</tr>
				
				<tr>
				<td align="right">
					用户名：
				</td>
				<td>
				 <input id="userName" type="text" style="width:150px">
				</td>
				<td align="right">
						经销商：
				</td>
				<!-- <td align="left">
					<select id="dealerId" name="dealerId" class="easyui-combotree" style="width: 150px;"
						data-options="onlyLeafCheck:true,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" multiple></select>
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
					开始时间：
				</td>
				<td>
				 <input id="start_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
				</td>
		      <td align="right">	
					结束时间：</td>
				<td><input id="end_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
				</td>
			   <td ></td>
		      </tr>
		      
		      <tr>
		      <td align="right">
					指令类型：
				</td>
				<td>
				 <input id="commandTypeId" name="commandTypeId" class="easyui-combobox"
							style="width: 150px;"
							data-options="
							url:'run/command_getCommandTypes.action',
                 valueField:'commandTypeId',
                 textField:'commandTypeName'
                 " />
				</td>
		      <td align="right">指令通道：</td>
		        <td>
			        <select id="pathFlag_search" class="easyui-combobox" name="pathFlag_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="0">GPRS</option>  
					<option value="1">GSM</option>  
				    </select>
			   </td>
			   <td align="right">网关回应：</td>
		        <td>
			        <select id="gatewayBack_search" class="easyui-combobox" name="gatewayBack_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="00">成功</option>  
					<option value="01">失败</option>  
				    </select>
			   </td>
				<td align="right">终端回应：</td>
		        <td>
			        <select id="unitBack_search" class="easyui-combobox" name="unitBack_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="00">成功</option>  
					<option value="01">失败</option>  
				    </select>
			   </td>
			    <td >
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryCommand()">查询</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#command_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
            }); 
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#command_datagrid').datagrid('columnMoving');
    	$('#start_time').val(getTodayZero());
    	$('#end_time').val(new Date().formatDate(timeFormat));
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 
