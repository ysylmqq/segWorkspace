<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- <script type="text/javascript" src="/js/autoComplete/jquery.js"></script> -->
	<script type='text/javascript' src='js/autoComplete/jquery.autocomplete.js'></script>
	<script type='text/javascript' src='js/autoComplete/localdata.js'></script>
	<link rel="stylesheet" type="text/css" href="js/autoComplete/jquery.autocomplete.css" />
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
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:vehicleResize"  style="overflow:hidden;">
 
    <!-- 表格 begin -->
    <table id="vehicle_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}vehicle/vehicle_search.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'机械注册'">
             <thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:130,align:'center'">整机编号</th>  
                <!-- <th data-options="field:'typeName',width:90,align:'center'">机械类型</th> -->
                <th data-options="field:'modelName',width:90,align:'center'">机械型号</th>
                <th data-options="field:'vehicleCode',width:65,align:'center'">机械代码</th>
                <th data-options="field:'vehicleArg',width:65,align:'center'">机械配置</th>
                <th data-options="field:'unitSn',width:100,align:'center'">终端序列号</th>  
                <th data-options="field:'simNo',width:100,align:'center'">SIM卡号</th>
                <th data-options="field:'fixMan',width:100,align:'center'">终端安装人</th>  
                <th data-options="field:'fixDate',width:150,align:'center'">终端安装日期</th>
                <th data-options="field:'statusName',width:100,align:'center'">机械状态(组)</th>
                <th data-options="field:'statusTime',width:150,align:'center'">状态修改时间</th>
                <!-- <th data-options="field:'userName',width:160,align:'center'">停用人</th> -->
                <th data-options="field:'stamp',width:150,align:'center'">最后修改时间</th>
                <th data-options="field:'vehicleId',width:280,align:'left',formatter:operate">操作</th>
            </tr>  
        </thead>
	 </table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style="font-size: 12px;">
		      <tr>
		        <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_search" style="width: 150px;"></td>
		        <!--<td align="right">机械类型:</td>
		         <td>
			  	<input id="typeName_search" name="typeName_search" style="width: 150px;" class="easyui-combobox"	
			    	data-options="url:'vehicle/vehicleType_getList.action',
			      	valueField:'typeId',  
			        textField:'typeName'"  />
                  </td> -->
		        
		        <td align="right">机械型号:</td>
		        <td>
				    <input id="modelName_search" name="modelName_search" class="easyui-combobox"
							style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
			   </td>
			   <td align="right">机械代号:</td>
		        <td>
				    <input id="vehicleCode_search" name="vehicleCode_search" class="easyui-combobox"
							style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList1.action',value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" 
							/>
			   </td>
			    <td align="right">机械配置:</td>
		        <td>
				    <select   id="vehicleArg_search" name="vehicleArg_search"  style="width:150px;height:21px" >    
						</select>
				    <!-- <input id="vehicleArg" name="vehicleArg" class="easyui-combobox" style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList2.action',valueField:'vehicleArg',textField:'vehicleArg'" /> -->
			   </td>
			   <td align="right">机械状态(组):</td>
		        <td>
			        <select id="status_search" class="easyui-combobox" name="testFlag_search" style="width:150px;">  
					<option value="">全部</option>
					<option value="1">测试组</option>
					<option value="2">已测组</option>  
					<option value="3">销售组</option>  
					<option value="8">法务组</option>  
					<option value="9">停用组</option>  
				    </select>
			   </td>
			   </tr>
		       <tr>
		       <td align="right">测试结果:</td>
		        <td>
			        <select id="testFlag_search" class="easyui-combobox" name="testFlag_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="1">测试通过</option>  
					<option value="0">测试未通过</option>  
				    </select>
			   </td>
		        <td align="right"> 安装人:</td>
		        <td><input id="fixMan_search" style="width: 150px;"></td>
		        
		        
			   <td align="right">安装开始时间:</td>
		        <td>
		        <input id="fixDateStart_search" name="fixDateStart_search" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"></input>	
		       	</td>
		       <td align="right">安装结束时间:</td>
		        <td>
		        	<input id="fixDateEnd_search" name="fixDateEnd_search" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"></input>	
		        </td>
		  
		      </tr>
		      <tr>
			   	<td align="right">SIM卡号:</td>
		        <td><input id="simNo_search" style="width: 150px;"></td>
		        <td align="right">终端序列号:</td>
			    <td><input id="unintSn_search" style="width: 150px;"></td>
			    <td colspan="4" align="right" style="padding-left: 20px;">
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryVehicle()">查询</a>
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openDlg4VehicleOperate()">新增</a>
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-batch_import" onclick="javascript:$('#dlg_vehicle_impport').dialog('open')">批量导入</a>
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcelVehicle();">导出</a>
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-recycle" onclick="openRecycleWin()">回收站</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    
		    <!-- 回收站弹出框 begin-->
			<div id="dlg_vehicle_recycle" class="easyui-dialog" title="回收站" data-options="iconCls:'icon-recycle',modal:true,maximizable:true,closed:true,buttons:'#dlg_vehicle_recycle_btns',onResize:vehicleDlgResize"
				style="width:800px;height:500px;">
			<!-- 回收站按钮 -->
			<div id="dlg_vehicle_recycle_btns">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#dlg_vehicle_recycle').dialog('close')">关闭</a>
			</div>
			<!-- 回收站查询条件 -->
			<div id="toolbar_recycle" style="padding: 5px; height: auto;">
				<table style="font-size: 12px;">
					<tr>
						<td align="right">整机编号:</td>
						<td>
							<input id="recycle_vehicleDef_search" style="width: 150px;">
						</td>
						<!-- <td align="right">机械类型:</td>
						<td>
							<input id="recycle_typeName_search" style="width: 150px;" class="easyui-combobox"
								data-options="url:'vehicle/vehicleType_getList.action',valueField:'typeId',textField:'typeName'" />
						</td> -->
						<td align="right">机械状态（组）:</td>
				        <td>
					        <select id="recycle_status_search" class="easyui-combobox" name="testFlag_search" style="width:150px;">  
							<option value="">全部</option>
							<option value="1">测试组</option>  
							<option value="2">已测组</option>  
							<option value="3">销售组</option>  
							<option value="8">法务组</option>  
							<option value="9">停用组</option>  
						    </select>
					   	</td>
						
						<td align="right">终端序列号:</td>
						<td>
							<input id="recycle_unintSn_search" style="width: 150px;">
						</td>
					</tr>
					<tr>
						<td align="right">机械型号:</td>
						<td>
							<input id="recycle_modelName_search" style="width: 150px;" class="easyui-combobox"
								data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
						</td>
						<td align="right">机械代号:</td>
		       		 <td>
				    <input id="recycle_vehicleCode_search" name="recycle_vehicleCode_search" class="easyui-combobox"
							style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList1.action',value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" 
							/>
			   </td>
			    <td align="right">机械配置:</td>
		        <td>
				    <select   id="recycle_vehicleArg_search" name="recycle_vehicleArg_search"  style="width:150px;height:21px" >    
						</select>
				    <!-- <input id="vehicleArg" name="vehicleArg" class="easyui-combobox" style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList2.action',valueField:'vehicleArg',textField:'vehicleArg'" /> -->
			   </td>
					</tr>
					<tr>
						<td align="right">安装人:</td>
						<td>
							<input id="recycle_fixMan_search" style="width: 150px;">
						</td>
						<td align="right">
							安装开始时间:
						</td>
						<td>
							<input id="recycle_fixDateStart_search" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" />
						</td>
						<td align="right">
							安装结束时间:
						</td>
						<td>
							<input id="recycle_fixDateEnd_search" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" />
						</td>
					</tr>
					<tr>
						<td align="right">SIM卡号:</td>
						<td>
							<input id="recycle_simNo_search" style="width: 150px;">
						</td>
						<td align="right">测试结果:</td>
						<td>
							<select id="recycle_testFlag_search" class="easyui-combobox" style="width: 150px;">
								<option value="">全部</option>
								<option value="1">测试通过</option>
								<option value="0">测试未通过</option>
							</select>
						</td>
						<td colspan="4" style="" align="right">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryVehicleRecycle()">查询</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteInRecycle()">删除</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-restore" onclick="updateVehiclesIsValid()">还原</a>
						</td>
					</tr>
				</table>
			</div>
			<!-- 回收站表格 -->
		    <table id="vehicle_recycle_datagrid" class="easyui-datagrid" style="padding-top:0px;margin-top:0px;height:430px;width:auto"
					data-options="url:'${basePath}vehicle/vehicle_search.action?isValid=1',fitColumns:false,singleSelect:false,rownumbers:true,pagination:true,toolbar:'#toolbar_recycle',fit:true">
		    	<thead>  
		            <tr> 
		            	<th data-options="field:'ck',checkbox : true,align:'center'">全选</th>
		            	<th data-options="field:'vehicleDef',width:100,align:'center'">整机编号</th>  
		                <!-- <th data-options="field:'typeName',width:60,align:'center'">机械类型</th> -->
		                <th data-options="field:'modelName',width:60,align:'center'">机械型号</th>
		                <th data-options="field:'vehicleCode',width:65,align:'center'">机械代码</th>
                		<th data-options="field:'vehicleArg',width:65,align:'center'">机械配置</th>
		                <th data-options="field:'fixMan',width:80,align:'center'">终端安装人</th>  
		                <th data-options="field:'simNo',width:80,align:'center'">SIM卡号</th>
		                <th data-options="field:'fixDate',width:130,align:'center'">终端安装日期</th>
		                <th data-options="field:'statusName',width:80,align:'center'">机械状态(组)</th>
		                <th data-options="field:'statusTime',width:130,align:'center'">状态修改时间</th>
		                <!-- <th data-options="field:'userName',width:160,align:'center'">停用人</th> -->
		                <th data-options="field:'stamp',width:130,align:'center'">最后修改时间</th>
		                <th data-options="field:'vehicleId',width:120,align:'left',formatter:operate">操作</th>
		            </tr>  
		        </thead>
			</table>
			</div>

			<!-- 新增或者修改弹出框 begin-->
		<div id="dlg_vehicle_operate" class="easyui-dialog" title="机械注册"
			data-options="iconCls:'icon-save',modal: true"
			style="padding: 5px; width: 600px; height: 460px;" closed="true"
			buttons="#dlg_vehicle_operate_btns">
			<form id="vehicle_operate_form" metdod="post" tdeme="simple"
				action="${basePatd}vehicle/vehicle_saveOrUpdate.action">
				<input id="unitId" name="unitId" type="hidden" />
				<input id="vehicleId" name="vehicleId" type="hidden" />
				<table cellpadding="4" cellspacing="0"
					style="font-size: 12px; width: 100%;">
					<tr>
						<td align="right">
							整机编号:
						</td>
						<td align="left">
							<input id="vehicleDef" name="vehicleDef" type="text" class="easyui-validatebox" data-options="required:true,validType:'length[10,11]'" onBlur="checkDef(this.value)" />
							<span style="color: red">*</span>
						</td>

					</tr>
					<tr>
						<td align="right">
							物料条码:
						</td>
						<td align="left">
							<input id="materialNo" name="materialNo" type="text" />
							<span style="color: red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							终端序列号:
						</td>
						<td align="left">
							<input id="unitSn" name="unitSn" type="text" readonly="readonly" />
							<span style="color: red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							SIM卡号:
						</td>
						<td align="left">
							<input id="simNo" name="simNo" type="text" readonly="readonly" />
							<span style="color: red">*</span>
						</td>
					</tr>

					<tr>
						<td align="right">
							钢号:
						</td>
						<td align="left">
							<input id="steelNo" name="steelNo" type="text" readonly="readonly" />
							<span style="color: red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							机械型号:
						</td>
						<td align="left">
							<select id="modelName" name="modelName" style="width: 150px;"
								class="easyui-validatebox" data-options="required:true">
							</select>
							<span style="color: red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							机械代号:
						</td>
						<td align="left">
							<input id="vehicleCode" name="vehicleCode" type="text"
								readonly="readonly" class="easyui-validatebox"
								data-options="required:true" />
							<span style="color: red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							机械配置:
						</td>
						<td align="left">
							<input id="vehicleArg" name="vehicleArg" type="text"
								readonly="readonly" class="easyui-validatebox"
								data-options="required:true" />
							<span style="color: red">*</span>
						</td>
					</tr>

					<tr>
						<td align="right">
							安装人:
						</td>
						<td align="left">
							<input id="fixMan" name="fixMan" type="text"
								class="easyui-validatebox" data-options="required:true" />
							<span style="color: red">*</span>
					</tr>
					<tr>
						<td align="right">
							安装时间:
						</td>
						<td align="left">
							<input id="fixDate" name="fixDate" type="text" class="Wdate"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								style="width: 150px"></input>
							<span style="color: red">*</span>
						</td>

					</tr>
				</table>
			</form>
		</div>
		<div id="dlg_vehicle_operate_btns">
			<a href="#" id="sava" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="saveVehicle()">保存</a>
			<!-- <a href="#" id="savaSuc" class="easyui-linkbutton"  disabled style="display:none"><font color="red">保存成功</font></a>   -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#dlg_vehicle_operate').dialog('close')">取消</a>
		</div>
		<!-- 新增或者修改弹出框 end-->
		    
		     <!-- 测试页面弹出框 begin-->
		<div id="dlg_vehicleTest_operate" class="easyui-dialog"  data-options="iconCls:'icon-save',modal: true,onClose:dlgTestClose"
		style="padding: 5px; width:900px; height: 550px;" closed="true" buttons="#dlg_vehicle_operateTest_btns">
	       
				 <table  cellspacing="0" style="font-size:12px;">

				 <tr>

					 <td colspan="2" width="50%">
					 <table>
					 				 <!-- <tr>
				 				 <td>
				 <fieldset style="height:40px">
			<legend>
				机械信息
			</legend>
			<table cellspacing="0" style="font-size:12px;">
			<tr>
			<td>机械编号：</td>
			<td>
			<input id="vehicleDefTest" name="vehicleDefTest"  type="text" style="width:110px" readonly="readonly"/>
			</td>
			<td>
			<a style="color:blue;text-decoration:none;" class="easyui-linkbutton" href="javascript:void(0)" onclick="openTestLog()"><b>机械测试日志</b></a>
			</td>
			</tr>
			</table>
			</fieldset>
				 </td>
				 </tr> -->
					 <tr>
					 <td>
					 <fieldset style="height:110px">
			<legend>
				测试内容
			</legend>
					  <form id="vehicle_operateTest_form" metdod="post" tdeme="simple"
				 action="${basePatd}vehicle/vehicleTest_saveOrUpdateTest.action" >
				 <input id="unitIdTest" name="unitIdTest" type="hidden" />
				 <input id="vehicleIdTest" name="vehicleIdTest" type="hidden" />
				 <input id="vehicleDefs" name="vehicleDefs" type="hidden" />
					 <table  cellspacing="0"  style="font-size:12px;">
	 			 <tr>
	                <td align="right">位置测试:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(32)"/> -->
	                <img id="testButtonTrue1" onclick="sendCommand(32)" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse1" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check0"  name="testLocation" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option>
				    	</select>	
					</td>
	                <td align="right">工况数据测试:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(81)"/> -->
	                <img id="testButtonTrue2" onclick="sendCommand(81)" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse2" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check7"  name="test_reserve1" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option>  
				    	</select>	                </td>
				    
	             </tr>
	             <tr>
	             <td align="right">下发A锁:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(52,'005BC5')" /> -->
	                <img id="testButtonTrue3" onclick="sendCommand(52,'005BC5')" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse3" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check1"  name="testAEnable" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option>   
				    	</select>	                </td>
				    	               
	              <td align="right">下发B锁:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(52,'00B55C')"/> -->
	                <img id="testButtonTrue5" onclick="sendCommand(52,'00B55C')" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse5" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check3"  name="testBEnable" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option>  
				    	</select>	                </td>
	             </tr>
	            <tr>
	  <td align="right">解除A锁:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(52,'00B500')"/> -->
	                <img id="testButtonTrue4" onclick="sendCommand(52,'00B500')" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse4" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check2"  name="testADisable" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option> 
				    	</select>	                </td>
				    	 

	                <td align="right">解除B锁:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(52,'0000C5')"/> -->
	                <img id="testButtonTrue6" onclick="sendCommand(52,'0000C5')" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse6" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check4"  name="testBDisable" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option>
				    	</select>	                </td>
	               
	             </tr>
	             <tr>
	              <td align="right">使能防拆保护:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(52,'5A0000')"/> -->
	                <img id="testButtonTrue7" onclick="sendCommand(52,'5A0000')" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse7" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check5"  name="testProtectEnable" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option>   
				    	</select>	                </td>

	                <td align="right">禁止防拆保护:</td>
	                <td align="center">
	                <!-- <input type="button" value="指令下发" onclick="sendCommand(52,'A50000')"/> -->
	                <img id="testButtonTrue8" onclick="sendCommand(52,'A50000')" style="cursor: pointer;" src="images/vehicles/button12_01_03.png">
	                <img id="testButtonFalse8" style="cursor: pointer;display:none" src="images/vehicles/button12_01_02.png">
	                </td>
	                <td align="left">
		                <select id="check6"  name="testProtectDisable" style="width:65px;">  
							<option value="3" selected>未测试</option> 
							<option value="0">正常</option>  
							<option value="1">不正常</option>   
				    	</select></td>
				    <td colspan="2"></td>
	             </tr>
	        	</table>
	        	</form>
	        	</fieldset>
					 </td>
					 </tr>
					 <tr>
				 				 <td>
				 <fieldset style="height:80px">
			<legend>
				测试指令反馈
			</legend>
			
				<table class="easyui-datagrid"
							data-options="
            fitColumns:false,singleSelect:false,rownumbers:true"
							id="test_result" style="height:70px">
							<thead>
								<tr>
								
									<th data-options="field:'unitId',width:80,align:'center',formatter:function(value,row,index){
									return '<a style=\'color:blue;text-decoration:none;\' href=\'javascript:void(0)\' onclick=\'openTestLog()\'><b>测试日志</b></a>';
										 
										}">
										
										机械测试日志
									</th>
									<th
										data-options="field:'commandTypeName',width:80,align:'center'">
										指令类型
									</th>
									<th data-options="field:'commandResult',width:60,align:'center',formatter:function(value,row,index){
										  if(value==0){
										    value='成功';
										    return value;
										  }
										  else if(value == 9){
										    value='';
										    return value;
										  }
										  else if(value!=0 && value!=9){
										    value='没有回应';
										    return '<font color=\'red\'>'+value+'</font>';
										  }
										}">
										网关回应
									</th>
									<th data-options="field:'unitBack',width:60,align:'center',formatter:function(value,row,index){
										  if(value=='0'){
										    value='成功';
										    return value;
										  }
										  else if(value == 9){
										    value='';
										    return value;
										  }
										  else if(value!=0 && value!=9){
										    value='没有回应';
										    return '<font color=\'red\'>'+value+'</font>';
										  }
										}">
										终端回应
									</th>
									<th data-options="field:'stamp',width:130,align:'center'">
										时间
									</th>
								</tr>
							</thead>
							<tbody>  
						        <tr>  
						            <td></td><td></td><td>9</td><td>9</td><td></td>  
						        </tr>  
						    </tbody> 
    
						</table>
			</fieldset>
				 </td>
				 </tr>
					 </table>
					 
				 </td>
				    
				    <td height="230px"><iframe id="ifm_map_test" name="ifm_map_test"
					src="${basePath}jsp/vehicle/vehicle_test_map.jsp"
					style="width: 100%; height: 100%; border-color: transparent; border-image: none; border-style: solid; border-width: 0 0px 0px;"></iframe>
		</td>
				 </tr>
				 <%-- <tr>
				 
				   <td> 
				    <fieldset style="height:90px">
			<legend>
				位置信息
			</legend>
				   	<form id="tLastPosition_operate_form" metdod="post" tdeme="simple"	 action="${basePatd}vehicle/vehicle_xxxxxx.action" >
				        <table  cellspacing="0"  style="font-size:12px;width: 100%;">
						 <tr>
						 <td> 经度
						 </td>
						 <td>
						  <input id="lon" name="lon"  type="text" />
						 </td>
						 <td> 纬度
						 </td>
						 <td>
						  <input id="lat" name="lat"  type="text" />
						 </td>
						 <td> 方向
						 </td>
						 <td>
						  <input id="direction" name="direction"  type="text" />
						 </td>
						 <td> 速度
						 </td>
						 <td>
						  <input id="speed" name="speed"  type="text" />
						 </td>
						 </tr>
						  <tr>
						 <td> 定位状态
						 </td>
						 <td>
						  <input id="locationState" name="locationState"  type="text" />
						 </td>
						<td> GPS时间
						 </td>
						 <td>
						  <input id="stamp" name="stamp"  type="text" />
						 </td>
						 <td>是否登录</td>
						 <td><input id="isLogin" name="isLogin"  type="text" /></td>
						 </tr>
						  <tr>
						   <td> 车辆状态
						 </td>
						 <td colspan=3>
						  <input id="vehicleState" name="vehicleState"  type="text" style="width:360px"/>
						 </td>
						 <td> 参考位置
						 </td>
						 <td colspan=3>
						  <input id="referencePosition" name="referencePosition"  type="text" style="width:360px"/>
						 </td>
						 </tr>
						 </table>
					</form>	
					</fieldset> 
				   </td>
				   </tr>--%>
				    <tr>
				 
				   <td colspan="3">
				    <fieldset style="height:285px"> 
			<legend>
				工况信息
			</legend>
				        <form id="tLastCondition_operate_form" metdod="post" tdeme="simple"	 action="${basePatd}vehicle/vehicle_xxxxxxxxx.action" >
				        <table  cellspacing="0"  style="font-size:12px;">
				        <tr>
				        <td> A类锁车
						 </td>
						 <td>
						  <input id="aLock" name="aLock"  type="text" style="width:70px;"/>
						 </td>
						 <td> B类锁车
						 </td>
						 <td>
						  <input id="bLock" name="bLock"  type="text" style="width:70px;"/>
						 </td>
						 <td> 防拆功能
						 </td>
						 <td>
						  <input id="cLock" name="cLock"  type="text" style="width:70px;"/>
						 </td>
				         <td> 累计工作时间
						 </td>
						 <td>
						  <input id="totalworkinghours" name="totalworkinghours"  type="text" style="width:70px;" />
						 </td>
						<!--  <td> 修正时间
						 </td>
						 <td>
						  <input id="correcthours" name="correcthours"  type="text" style="width:70px;" />
						 </td> -->
				        </tr>
						 <tr>
						 <td> 冷却液温度
						 </td>
						 <td>
						  <input id="enginecoolanttemperature" name="enginecoolanttemperature"  type="text" style="width:70px;"/>
						 </td>
						 <td> 液压油温度
						 </td>
						 <td>
						  <input id="hydraulicoiltemperature" name="hydraulicoiltemperature"  type="text" style="width:70px;" />
						 </td>
						 <td> 机油温度
						 </td>
						 <td>
						  <input id="engineOilTemperature" name="engineOilTemperature"  type="text" style="width:70px;" />
						 </td>
						 <td> 冷却液温度高报警设置值
						 </td>
						 <td>
						  <input id="highenginecoolanttemperval" name="highenginecoolanttemperval"  type="text" style="width:70px;" />
						 </td>
						 <td> 液压油温高报警值
						 </td>
						 <td>
						  <input id="highhydraulicoiltemperalarmval" name="highhydraulicoiltemperalarmval"  type="text" style="width:70px;" />
						 </td>
						 
						 
						 </tr>
						 <tr>
						 <td> 冷却液液位
						 </td>
						 <td>
						  <input id="enginecoolantlevel" name="enginecoolantlevel"  type="text" style="width:70px;"/>
						 </td>
						 
						 <td> 燃油油位
						 </td>
						 <td>
						  <input id="enginefuellevel" name="enginefuellevel"  type="text" style="width:70px;"/>
						 </td>
						  <td> 转速
						 </td>
						 <td>
						  <input id="enginespeed" name="enginespeed"  type="text" style="width:70px;"/>
						 </td>
						 <td> 飞轮齿数设置值
						 </td>
						 <td>
						  <input id="toothnumbervalue" name="toothnumbervalue"  type="text" style="width:70px;" />
						 </td>
						 <td> 挖机档位
						 </td>
						 <td>
						  <input id="gear" name="gear"  type="text" style="width:70px;" />
						 </td>
						 </tr>

						 <tr>
						 <td> 系统电压
						 </td>
						 <td>
						  <input id="systemvoltage" name="systemvoltage"  type="text" style="width:70px;" />
						 </td>
						 <td> 机油压力
						 </td>
						 <td>
						  <input id="engineoilpressure" name="engineoilpressure"  type="text" style="width:70px;"/>
						 </td>
						 <td> 先导压力1
						 </td>
						 <td>
						  <input id="pilotpressure1" name="pilotpressure1"  type="text" style="width:70px;" />
						 </td>
						 <td> 先导压力2
						 </td>
						 <td>
						  <input id="pilotpressure2" name="pilotpressure2"  type="text" style="width:70px;" />
						 </td>
						 <td> 前泵负压压力
						 </td>
						 <td>
						  <input id="beforepumppressure" name="beforepumppressure"  type="text" style="width:70px;" />
						 </td>
						 </tr>
						 <tr>
						 <td> 前泵主压压力
						 </td>
						 <td>
						  <input id="beforepumpmainpressure" name="beforepumpmainpressure"  type="text" style="width:70px;" />
						 </td>
						  <td> 后泵负压压力
						 </td>
						 <td>
						  <input id="afterpumppressure" name="afterpumppressure"  type="text" style="width:70px;" />
						 </td>
						<td> 后泵主压压力
						 </td>
						 <td>
						  <input id="afterpumpmainpressure" name="afterpumpmainpressure"  type="text" style="width:70px;" />
						 </td>
						 <td> 机油压力低报警设置值
						 </td>
						 <td>
						  <input id="lowengineoilpressurealarmvalue" name="lowengineoilpressurealarmvalue"  type="text" style="width:70px;" />
						 </td>
						 <td> 系统电压高报警设置值
						 </td>
						 <td>
						  <input id="highvoltagealarmvalue" name="highvoltagealarmvalue"  type="text" style="width:70px;" />
						 </td>
						 </tr>

						 <tr>
						  <td> 系统电压低报警设置值
						 </td>
						 <td>
						  <input id="lowvoltagealarmvalue" name="lowvoltagealarmvalue"  type="text" style="width:70px;" />
						 </td>
						 <td> 比例阀电流1
						 </td>
						 <td>
						  <input id="proportionalcurrent1" name="proportionalcurrent1"  type="text" style="width:70px;" />
						 </td>
						 <td> 比例阀电流2
						 </td>
						 <td>
						  <input id="proportionalcurrent2" name="proportionalcurrent2"  type="text" style="width:70px;" />
						 </td>
						 <td> 产品编号
						 </td>
						 <td>
						  <input id="productCode" name="productCode"  type="text" style="width:70px;" />
						 </td>
						 <td> 是否工作
						 </td>
						 <td>
						  <input id="isWork" name="isWork"  type="text" style="width:70px;" />
						 </td>
						 </tr>
						 <tr>
						 <td>供应商内部软件代号
						 </td>
						 <td>
						  <input id="monitorsoftwarecode" name="monitorsoftwarecode"  type="text" style="width:70px;" />
						 </td>
						 <td>玉柴内部软件版本号
						 </td>
						 <td>
						  <input id="monitorycsoftwarecode" name="monitorycsoftwarecode"  type="text" style="width:70px;" />
						 </td>
						 <td>控制器软件代号
						 </td>
						 <td>
						  <input id="controllersoftwarecode" name="controllersoftwarecode"  type="text" style="width:70px;" />
						 </td>
						 <td>控制器I/O信息
						 </td>
						 <td>
						  <input id="controllerycsoftwarecode" name="controllerycsoftwarecode"  type="text" style="width:70px;" />
						 </td>
						 <td> 故障代码
						 </td>
						 <td>
						  <input id="faultCode" name="faultCode"  type="text" style="width:70px;" />
						 </td>
						 </tr>

						 <tr>
						 
						<!--  <td> 原始数据
						 </td>
						 <td>
						  <input id="rawdata" name="rawdata"  type="text" style="width:70px;" />
						 </td> -->
						 
						 <td> 上报时间
						 </td>
						 <td colspan="3">
						  <input id="stamp" name="stamp"  type="text" style="width:150px;" />
						 </td>
						 </tr>

				 </table>
						 </form>
						 </fieldset>
						 </td>
						 </tr>
						 </table>
	 		
			
       </div>  
		<div id="dlg_vehicle_operateTest_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveVehicleTest()">确认</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_vehicleTest_operate').dialog('close')">取消</a>  
	</div>  
		    <!-- 测试页面弹出框 end-->
		    
		    
		    
		    
		    <!-- 批量导入弹出框 begin-->
		    <div id="dlg_vehicle_impport" class="easyui-dialog" title="批量导入" data-options="iconCls:'icon-save',modal:true"
		style="padding: 5px; width: 450px; height: 180px;" closed="true" buttons="#dlg_unit_impport_btns">
       
        <form id="vehicle_impport_Form" method="post" theme="simple"
			enctype="multipart/form-data" action="${basePath}vehicle/vehicle_impFromExcel.action" >
			 <table style="font-size:12px;width: 100%;">
		        <tr>
						<td>选择文件：</td>
						<td>
						<input width="100px" type="file" id="upload"  name="upload"/>
						<input type="button" value="模板下载" onclick="document.getElementById('ifm_down').src='${basePath}doc/template/vehicle_registe_templates.xls';">
					   </td>
					</tr>
					</table>
		</form>
		     
		</div>
		    <!-- 批量导入弹出框 end-->
      <div id="dlg_unit_impport_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="batchImpUnit()">上传</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_vehicle_impport').dialog('close')">取消</a>  
	   </div>  
	   
	   
	   <!-- 修改销售日期 begin-->
		    <div id=updateSaleDateWindow class="easyui-dialog" title="修改销售日期" data-options="iconCls:'icon-save',modal:true"
		style="padding: 5px; width: 450px; height: 180px;" closed="true" buttons="#updateSaleDateTools">
			 <table style="font-size:12px;width: 100%;">
			 	<input type="hidden" id="updateVehId">
	            <tr>
					<td align="right">销售日期:</td>
			        <td>
			          <input id="saleDate"  name="saleDate" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:150px"></input>
			        </td>
				</tr>
			</table>
		</div>
      <div id="updateSaleDateTools">  
	    <a href="javaScript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateSaleDateBtn()">确定</a>  
	    <a href="javaScript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateSaleDateWindow').dialog('close')">取消</a>  
	   </div> 
	   <!-- 修改销售日期 end --> 
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
             $('#vehicle_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
            }); 
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#vehicle_datagrid').datagrid('columnMoving');
    	$('#vehicle_recycle_datagrid').datagrid('columnMoving');
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 