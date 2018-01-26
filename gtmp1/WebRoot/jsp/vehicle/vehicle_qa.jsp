<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type='text/javascript' src='${basePath}js/autoComplete/jquery.autocomplete.js'></script>
	<script type='text/javascript' src='${basePath}js/autoComplete/localdata.js'></script>
	<link rel="stylesheet" type="text/css" href="js/autoComplete/jquery.autocomplete.css" />
	<script type="text/javascript" src="${basePath}js/vehicle_qa.js"></script> 
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
   <div data-options="region:'center',border:false,onResize:vehicleQaResize"  style="overflow:hidden;">
 
    <!-- 表格 begin -->
    <table id="vehicle_qa_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}vehicle/vehicleTest_searchTest.action',queryParams:defQueryParams,
            rownumbers:true,pagination:true,title:'机械质检'">
             <thead>
             <tr>
              <th  colspan="1"  data-options="align:'center'" >全选</th>  
              <th colspan="1"  data-options="align:'center'" >整机信息</th> 
              <th colspan="3"  data-options="align:'center'" >质检信息</th>  
              <th colspan="11" data-options="align:'center'" >测试信息</th>   
             </tr>  
            <tr>  
                <th data-options="field:'ck',width:50,align:'center',checkbox:true"></th>
                <th data-options="field:'vehicleDef',align:'center',styler:function(value,row,index){ if(row.qaResult==1){return 'background-color:#ffee00;color:red;';}}">整机编号</th>  
                <th data-options="field:'qaTime',align:'center',styler:function(value,row,index){ if(row.qaResult==1){return 'background-color:#ffee00;color:red;';}}">质检时间</th>
                <th data-options="field:'qaUserName',align:'center',styler:function(value,row,index){ if(row.qaResult==1){return 'background-color:#ffee00;color:red;';}}">质检人</th>
                <th data-options="field:'qaResult',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}else{return '';}},styler:function(value,row,index){ if(value==1){return 'background-color:#ffee00;color:red;';}}">质检结果</th>
                <th data-options="field:'testUserName',align:'center'">测试人</th>
                <th data-options="field:'testTime',align:'center'">测试时间</th>  
                <th data-options="field:'testResult',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">测试结果</th>
                <th data-options="field:'testLocation',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">定位测试结果</th>
                <th data-options="field:'testAEnable',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">使能A锁测试结果</th>
                <th data-options="field:'testADisable',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">解除A锁</th>
                <th data-options="field:'testBEnable',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">使能B锁</th>
                <th data-options="field:'testBDisable',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">解除B锁</th>
                <th data-options="field:'testProtectEnable',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">使能防拆保护</th>
                <th data-options="field:'testProtectDisable',align:'center',formatter:function(val,row,index){if(val==0){ return '正常';}else if(val==1){ return '不正常';}}">禁止防拆保护</th>
                <th data-options="field:'unitId',align:'center',formatter:commandFormatter">指令详细</th>
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
		        <!-- 
		        <td align="right">测试结果:</td>
		        <td>
					<select id="testResult_search" class="easyui-combobox" name="test_result_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="0">正常</option>  
					<option value="1">不正常</option> 
			    	</select> 
                  </td> -->
		       <td align="right">是否已质检:</td>
		        <td>
					<select id="isQa_search" class="easyui-combobox" name="isQa_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="1" selected="selected">否</option>  
					<option value="2">是</option> 
			    	</select> 
                  </td>
                   <td align="right">质检结果:</td>
		        <td>
					<select id="qaResult_search" class="easyui-combobox" name="qa_result_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="0">正常</option>  
					<option value="1">不正常</option> 
			    	</select> 
                  </td>
               <td colspan="2"></td>
		      </tr>
		      
		      <tr>
		      
		          <td align="right">供应商编号:</td>
		        <td align="left"><input id="supperierSn_search" class="easyui-combobox" name="supperierSn"  
		                 data-options="valueField:'supperierSn',textField:'supperierName',url:'${basePatd}sys/dicSupperier_getList.action'"   style="width:150px"  /> 
		        </td>
		     
                   <td align="right">测试开始时间:</td>
		        <td> <input id="testTime_search" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
			 	</td>
			 	
			 	
			 <td align="right">测试结束时间:</td>
		        <td>
		    <input id="testTime2_search" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
			</td>
		      </tr>
		       <tr>
		        
		         <td align="right">质检开始时间:</td>
		        <td> <input id="qaTime_search" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
			 	</td>
		        
		        
			   <td align="right">质检结束时间:</td>
		        <td>
		    <input id="qaTime2_search" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
			</td>
			
			    <td colspan="4"> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryQa()">查询</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" onclick="setQaPass()">设置质检结果</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    
		   
		  
</div>
</div>

   <div data-options="region:'center',border:false"  style="overflow:hidden;">
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
					src="${basePath}jsp/vehicle/vehicle_test_map2.jsp"
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
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok"  onclick="javascript:$('#dlg_vehicleTest_operate').dialog('close')" >确认</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_vehicleTest_operate').dialog('close')">取消</a>  
	</div>  

</div>


 <!-- 详细指令信息 --> 
		    <div  closed="true" id="dlg_test_command" class="easyui-dialog"
		     data-options="title:'指令详细信息',maximizable:true,closed:true" 
		style="width: 750px; height: 308px; overflow: hidden">
		<table id="test_command_datagrid" style="padding-top:20px;margin-top:20px;">
		</table>
		</div>
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#vehicle_qa_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
            }); 
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 
