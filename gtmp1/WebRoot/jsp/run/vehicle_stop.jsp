<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/vehicle_stop.js"></script>
<script type="text/javascript" src="${basePath}js/common/globe.js"></script>
<div id='loading_run'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
    <!-- 表格 begin -->
    <table id="vehicle_datagrid" class="easyui-datagrid" style="width: auto; height: auto;"
    		data-options="url:'${basePath}vehicle/vehicle_search.action?materialNo=3,8,9',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'机械转组',toolbar:'#toolbar'">
		<thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:130,align:'center'">整机编号</th>
                <th data-options="field:'dealerId',width:150,align:'center'">经销商</th>  
                <th data-options="field:'modelName',width:100,align:'center'">机械型号</th>  
                <th data-options="field:'fixMan',width:120,align:'center'">终端安装人</th>
                <th data-options="field:'fixDate',width:150,align:'center'">终端安装日期</th>
                <th data-options="field:'status',width:140,align:'center',formatter:fmtVehicleStatus">机械状态(组)</th>
                <th data-options="field:'unitSn',width:100,align:'center'">终端序列号</th>
                <th data-options="field:'simNo',width:100,align:'center'">SIM卡号</th>
                <th data-options="field:'userName',width:160,align:'center'">停用人</th>
                <th data-options="field:'leaseFlag',width:100,align:'center',formatter:function(val,row,index){if(val==0){ return '无';}else{ return '<font color=green>有</font>';}}">融资租赁权限</th>
                <th data-options="field:'stamp',width:150,align:'center'">最后修改时间</th>
                <th data-options="field:'vehicleId',width:150,align:'center',formatter:operate">操作</th>
            </tr>  
        </thead>
	</table>
	<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_search" style="width: 150px;"></td>
		       <td align="right">
						经销商:
					</td>
					<td>
						<input id="dealerId_search" name="dealerId_search"
							class="easyui-combotree" style="width: 150px;"
							data-options="
							url:'run/run_getDealerAreas4Tree.action',
                 multiple:true,onlyLeafCheck:false,cascadeCheck:true
                 " />
					</td>
					  <td align="right">
					  机械状态(组):
					  </td>
               	 	<td>
                		<select  id="status_search" class="easyui-combobox"  name="status_search"  style="width:150px;" >    
                   	 		<option value="">全部</option>
                    		<option value="1">测试组</option>
                    		<option value="2">已测组</option>
                    		<option value="3">销售组</option>
                    		<option value="8">法务组</option>
                    		<option value="9">停用组</option>      
                		</select>
					</td>
		        <td align="right">机械型号:</td>
		        <td colspan="2">
				     <input id="modelName_search" name="modelName_search" class="easyui-combobox"
							style="width: 150px;"
							data-options="
							url:'vehicle/vehicleModel_getList.action',
                 valueField:'modelId',
                 textField:'modelName'
                 " />
			   </td>
		        <td></td>
		      </tr>
		      
		       <tr>
		        <td align="right">终端序列号:</td>
		        <td><input id=unitSn_search style="width: 150px;"></td>
		       
		       	<td align="right">SIM卡号:</td>
		        <td><input id="simNo_search" style="width: 150px;"></td>
		        
		        <td align="right">融资租赁权限:</td>
		        <td>
		        	<select id="leaseFlag_search" class="easyui-combobox" data-options="editable:false" style="width: 150px;">
		        		<option value="">全部</option>
		        		<option value="1">有</option>
						<option value="0">无</option>
		        	</select>
		        </td>
		        
			    <td colspan="4">
			    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryVehicle()">查询</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->

<!-- 机械转弹出框 begin-->
<div id="dlg_vehicle_operate" class="easyui-dialog" title="机械转组" data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 300px; height: 150px;" closed="true" buttons="#dlg_vehicle_operate_btns">
	<form id="vehicle_operate_form" method="post" action="${basePath}vehicle/vehicle_saveOrUpdate.action">
		<input id="vehicleId" name="vehicleId" type="hidden" />
		<input id="unitId" name="unitId" type="hidden"/>
		<table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
			<tr>
				<td align="right">
					机械状态(组):
				</td>
				<td align="left">
					<select id="status" class="easyui-combobox" name="status" data-options="editable:false" style="width: 150px;">
						<option value="3">销售组</option>
						<option value="8">法务组</option>
						<option value="9">停用组</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="dlg_vehicle_operate_btns">  
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="vehicleTransfer()">保存</a>  
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_vehicle_operate').dialog('close')">取消</a>  
</div>

<!-- 融资租赁权限 -->
<div id="dlg_leasehold_operate" class="easyui-dialog" title="融资租赁权限" data-options="iconCls:'icon-save',modal: true,buttons:'#dlg_leasehold_operate_btns'"
	style="padding: 5px; width: 300px; height: 150px;" closed="true">
	<form id="vehicle_operate_form" method="post" action="${basePath}vehicle/vehicle_saveOrUpdate.action">
		<input id="vId" type="hidden" />
		<table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
			<tr>
				<td align="right">
					融资租赁权限:
				</td>
				<td align="left">
					<select id="leaseFlag" class="easyui-combobox" data-options="editable:false" style="width: 150px;">
						<option value="1">有</option>
						<option value="0">无</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="dlg_leasehold_operate_btns">  
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="leaseHold()">保存</a>  
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_leasehold_operate').dialog('close')">取消</a>  
</div>

<script type="text/javascript">
    function show(){
        $("#loading_run").fadeOut("fast", function(){
            $(this).remove();
             //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#vehicle_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
            }); 
        });
        
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#vehicle_datagrid').datagrid('columnMoving');
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script>