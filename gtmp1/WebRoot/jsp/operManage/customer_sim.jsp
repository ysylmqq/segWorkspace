<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type='text/javascript' src='js/autoComplete/jquery.autocomplete.js'></script>
	<script type='text/javascript' src='js/autoComplete/localdata.js'></script>
	<script type='text/javascript' src='js/jquery.table2excel.js'></script>
	<link rel="stylesheet" type="text/css" href="js/autoComplete/jquery.autocomplete.css" />
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
.out_excel_boder{
	max-height: 300px;
    overflow-y: scroll;
}
.out_excel_boder table{
	width:100%;
	text-align: center;
    font-size: 14px;
    border-collapse:collapse;
    border-spacing:0;
    font-family:"微软雅黑";
}
.out_excel_boder table tr th{
	    font-weight: normal;
	    border:1px solid #999999
}
.out_excel_boder table tr td{
	height: 25px;
    border:1px solid #999999
}
</style>



 <div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:vehicleResize"  style="overflow:hidden;">
 
    <!-- 表格 begin -->
    <table id="customer_pay_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}sim/customerSim_search.action',
            fitColumns:true,rownumbers:true,pagination:true,title:'客户SIM管理'">
             <thead>  
            <tr>  
                <!-- <th data-options="field:'ck',width:50,align:'center',checkbox:true">全选</th>
                 -->
                <th data-options="field:'vehicleDef',width:150,align:'center'">整机编号</th>
   		         <th data-options="field:'distributor',width:240,align:'center'">经销商</th>
                <th data-options="field:'modelName',width:150,align:'center'">机械型号</th>
   		        <th data-options="field:'vehicleArg',width:120,align:'center'">机械配置</th>
   		        <th data-options="field:'unitSn',width:150,align:'center'">终端序列号</th>
		        <th data-options="field:'simNo',width:150,align:'center'">SIM卡号</th>
		        <th data-options="field:'isServer',width:180,align:'center',formatter:statusTips">服务状态</th>
		        <th data-options="field:'startTime',width:150,align:'center',formatter:dateFormat">服务开始日期</th>
		        <th data-options="field:'endTime',width:150,align:'center',formatter:dateFormat">服务结束日期</th>
		        <th data-options="field:'status',width:100,align:'center',formatter:tips">状态</th>
		        <th data-options="field:'stopStartTime',width:150,align:'center',formatter:dateFormat">停机保号开始时间</th>
		        <th data-options="field:'stopEndTime',width:150,align:'center',formatter:dateFormat">停机保号结束时间</th>
		        <th data-options="field:'stopSaveFee',width:150,align:'center'">停机保号累计费用</th>
		        <th data-options="field:'stopReason',width:150,align:'center'">停机保号原因</th>
		        <th data-options="field:'remark',width:150,align:'center'">备注</th>
		        <th data-options="field:'userName',width:150,align:'center'">操作人</th>
		        <th data-options="field:'createTime',width:150,align:'center',formatter:dateFormat">开通时间</th>
		        <th data-options="field:'operId',width:200,align:'center',formatter:operateTool">操作</th>
	            </tr>  
        </thead>
	 </table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
    	<table style="font-size: 12px;">
      <tr>
        <td align="right">整机编号:</td>
    	<td>
         <!--<input id="vehicleDef"  name="vehicleDef" type="text" style="width:150px"></input>
          --><input id="vehicleDef" name="vehicleDef" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'${basePath}sim/customerPay_vehicleInfo.action',
								valueField:'vehicleDef',  
       							textField:'vehicleDef',
       							onChange:function(data){$('#customer_pay_datagrid').datagrid('reload',{vehicleDef:data,dealerId:$('#dealerId_search').combobox('getValues').join(',')})}
       							" 
			/>
        </td>
        <td  align="right">
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
        <td align="right">SIM卡:</td>
        <td>
        	<input id="sim_no" name="simNo" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'${basePath}sim/customerSim_customerSimList.action',
								valueField:'simNo',  
       							textField:'simNo',
       							onChange:function(data){$('#customer_pay_datagrid').datagrid('reload',{simNo:data,dealerId:$('#dealerId_search').combobox('getValues').join(',')})}
       							" 
			/>
        <td align="right">服务开始时间:</td>
        <td>
          <input id="start_time"  name="startTime" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:150px"></input>
        </td>
        <td align="right">服务结束时间:</td>
        <td>
          <input id="end_time" name="endTime" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:150px"></input>
        </td>
        <td align="right">状态:</td>
        <td>
            <input id="statusCon" name="status" class="easyui-combobox" data-options="
				valueField: 'value',
				textField: 'label',
				onChange:function(val){
					$('#queryBtn').click();
				},
				data: [{
					label: '已开通',
					value: '0'
				},{
					label: '未开通',
					value: '2'
				},{
					label: '停机保号',
					value: '1'
				}]" />
        </td>
      </tr>
      <tr>
        <td colspan="11" align="left" style="padding-left: 20px;width:100%">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="queryBtn">查询</a>
         <!--   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="addBtn">新增</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-batch_import" onclick="javascript:$('#customerPayImpport').dialog('open')">批量导入</a>
         --> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcelVehicle();">导出</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#batchStopImpport').dialog('open');">批量停机保号</a></td>
      </tr>
    </table>
  </div>
<!-- 查询条件 end -->
		    
 <!-- 批量导入弹出框 begin-->
  <div id="customerPayImpport" class="easyui-dialog" title="批量导入" data-options="iconCls:'icon-save',modal:true"
		style="padding: 5px; width: 650px; height: 300px;overflow: hidden;" closed="true" buttons="#customerPayImpportBtns">
        <form id="vehicle_impport_Form" method="post" theme="simple"
			enctype="multipart/form-data" action="${basePath}sim/customerSim_impFromExcel.action" >
			 <table style="font-size:12px;width: 100%;">
		        <tr>
						<td>选择文件：</td>
						<td>
						<input width="100px" type="file" id="upload"  name="upload"/>
						<input type="button" value="模板下载" onclick="document.getElementById('ifm_down').src='${basePath}doc/template/customer_sim_templates.xls';">
					   </td>
					</tr>
					</table>
		</form>
		<!-- 导入错误数据显示开始 -->
		<div id="errorGrid"  class="out_excel_boder" style="display:none">
		<div style="margin-bottom:10px"><span>错误数据</span><button style="margin-left:10px" id="errorData">导出</button></div>
			<table id="errorTable">
				<thead>
					<tr>
						<th>SIM卡号</th>
						<th>服务开始日期</th>
						<th>服务结束时间</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody id="errorTbody">
					
				</tbody>
			</table>
		</div>
		
		<!-- 导入错误数据显示结束 -->
	</div>
		<!-- 批量导入弹出框 end-->
  	    <!-- 批量导入弹出框 end-->
      <div id="customerPayImpportBtns">  
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="batchImpCustomerPay()">上传</a>  
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#customerPayImpport').dialog('close')">取消</a>  
	   </div>  
	   
<!-- 批量停机保号导入弹出框 begin-->
  <div id="batchStopImpport" class="easyui-dialog" title="批量停机保号导入" data-options="iconCls:'icon-save',modal:true"
		style="padding: 5px; width: 650px; height: 300px;overflow: hidden;" closed="true" buttons="#batchStopImpportBtns">
        <form id="stop_impport_Form" method="post" theme="simple"
			enctype="multipart/form-data" action="${basePath}sim/customerSim_impFromExcel.action" >
			 <table style="font-size:12px;width: 100%;">
		        <tr>
						<td>选择文件：</td>
						<td>
						<input width="100px" type="file" id="upload"  name="upload"/>
						<input type="button" value="模板下载" onclick="document.getElementById('ifm_down').src='${basePath}doc/template/batch_stop_server.xls';">
					   </td>
					</tr>
					</table>
		</form>
		<!-- 导入错误数据显示开始 -->
		<div id="errorStopGrid"  class="out_excel_boder" style="display:none">
		<div style="margin-bottom:10px"><span>错误数据</span><button style="margin-left:10px" id="errorData">导出</button></div>
			<table id ="errorStopTable">
				<thead>
					<tr>
						<th>SIM卡号</th>
						<th>停机保号开始日期</th>
						<th>停机保号结束时间</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody id="errorStopTbody">
					
				</tbody>
			</table>
		</div>
		
		<!-- 导入错误数据显示结束 -->
	</div>
		<!-- 批量导入弹出框 end-->
  	    <!-- 批量导入弹出框 end-->
      <div id="batchStopImpportBtns">  
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="batchStop()">上传</a>  
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#batchStopImpport').dialog('close')">取消</a>  
	   </div>  
 <!-- 批量停机保号导入弹出框 begin -->  
 
 
  <!-- 新增弹出框 begin-->
  <div id="customerPayWindow" class="easyui-dialog" title="添加公司SIM" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 600px; height: 460px;" closed="true" buttons="#operBtns">
    <form id="vehicle_operate_form" metdod="post" tdeme="simple">
      <input id="unitId" name="unitId" type="hidden" />
      <input id="vehicleId" name="vehicleId" type="hidden" />
      <table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
        <tr>
          <td align="right">终端号:</td>
          <td align="left">
          <input id="unitIds" style="width: 200px;"/>
         <span style="color: red">*</span></td>
        </tr>
        <tr>
          <td align="right">SIM卡号:</td>
          <td align="left">
          <input id="simNo" type="text" style="width: 200px;" readonly="readonly"/>
         <span style="color: red">*</span></td>
        </tr>
     
         <tr>
        <td align="right">
        服务开始日期:
        </td>
        <td align="left">
        <input id="openTime" name="startTime" type="text" class="Wdate"
        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
        style="width: 150px"></input>
        <span style="color: red">*</span>
        </td>
        </tr>
        <td align="right">
      	  服务结束日期:
        </td>
        <td align="left">
        <input id="endTime" name="endTime" type="text" class="Wdate"
        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
        style="width: 150px"></input>
        <span style="color: red">*</span></td>
        </tr>
        <tr>
          <td align="right">备注:</td>
          <td align="left">
            <input id="remark" name="remark" type="text" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="operBtns">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="saveCustomerPayBtn">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#customerPayWindow').dialog('close')">取消</a></div>
  <!-- 新增弹出框 end-->
  
  <!-- 注销SIM原因弹出框 begin-->
  <div id="simReasonWindow" class="easyui-dialog" title="停机保号" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 450px; height: 280px;" closed="true" buttons="#operBtns">
    <form id="vehicle_operate_form" metdod="post" tdeme="simple">
      <input id="cancelSimNo" type="hidden" />
      <table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;padding-top: 15px">
       <tr>
          <td align="right">停机保号开始日期:</td>
          <td align="left">
          	<input id="stopStartTime" name="endTime" type="text" class="Wdate"
		        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
		        style="width: 150px"></input>
		        <span style="color: red">*</span>
          </td>
        </tr>
        
         <tr>
          <td align="right">停机保号结束日期:</td>
          <td align="left">
          	<input id="stopEndTime" name="endTime" type="text" class="Wdate"
		        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
		        style="width: 150px"></input>
		        <span style="color: red">*</span>
          </td>
        </tr>
       
        <tr>
          <td align="right">停机保号费用:</td>
          <td align="left">
          	<input id="stopFeeMonth" name="stopFeeMonth" type="text" value="5" style="width: 150px"></input>
		        <span style="color: red">*</span>
          </td>
        </tr>
       
       <tr>
          <td align="right">停机保号原因:</td>
          <td align="left">
          	<textarea id="stopReasons" rows="3" cols="40"></textarea>
          </td>
        </tr>  
      </table>
    </form>
  </div>
  <div id="operBtns">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="cancelSimServerBtn">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#simReasonWindow').dialog('close')">取消</a></div>
  <!-- 注销SIM原因弹出框 end-->
  
  <!-- 批量注销SIM原因弹出框 begin-->
  <div id="batchSimReasonWindow" class="easyui-dialog" title="停机保号" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 450px; height: 240px;" closed="true" buttons="#operBtnsBatch">
    <form id="vehicle_operate_form" metdod="post" tdeme="simple">
      <table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;padding-top: 15px">
       <tr>
          <td align="right">停机保号开始日期:</td>
          <td align="left">
          	<input id="batchStopStartTime" name="endTime" type="text" class="Wdate"
		        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
		        style="width: 150px"></input>
		        <span style="color: red">*</span>
          </td>
        </tr>
        
         <tr>
          <td align="right">停机保号结束日期:</td>
          <td align="left">
          	<input id="batchStopEndTime" name="endTime" type="text" class="Wdate"
		        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
		        style="width: 150px"></input>
		        <span style="color: red">*</span>
          </td>
        </tr>
       
       <tr>
          <td align="right">停机保号原因:</td>
          <td align="left">
          	<textarea id="batchStopReasons" rows="3" cols="40"></textarea>
          </td>
        </tr>  
      </table>
    </form>
  </div>
  <div id="operBtnsBatch">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="batchCancelSimServerBtn">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#batchSimReasonWindow').dialog('close')">取消</a></div>
  <!-- 批量SIM原因弹出框 end-->
  
  
  <!-- 申请开通弹出框 begin-->
  <div id="applayOpenReasonWindow" class="easyui-dialog" title="申请开通" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 450px; height: 240px;" closed="true" buttons="#applayOperBtns">
    <form id="appleOpen" metdod="post" tdeme="simple">
      <input id="cancelSimNo" type="hidden" />
      <table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;padding-top: 15px">
       <tr>
          <td align="right">服务开始日期:</td>
          <td align="left">
          	<input id="applyStartTime" name="endTime" type="text" class="Wdate"
		        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
		        style="width: 150px"></input>
		        <span style="color: red">*</span>
          </td>
        </tr>
        
         <tr>
          <td align="right">服务结束日期:</td>
          <td align="left">
          	<input id="applyEndTime" name="endTime" type="text" class="Wdate"
		        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
		        style="width: 150px"></input>
		        <span style="color: red">*</span>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div id="applayOperBtns">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"   onclick="applyServerSim()" id="applyServerBtn">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#applayOpenReasonWindow').dialog('close')">取消</a></div>
  <!-- 申请开通弹出框 end-->
  
  
  <!--修改弹出框 begin-->
  <div id="customerPayEditWindow" class="easyui-dialog" title="编辑客户SIM信息" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 600px; height: 460px;" closed="true" buttons="#operEditBtns">
    <form id="" metdod="post" tdeme="simple">
      <input id="custPayId" name="custPayId" type="hidden" />
      <table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
        <tr>
          <td align="right">SIM卡号:</td>
          <td align="left">
          	<label id="simNoEdit" style="width: 200px;"></label>
          </td>
        </tr>
        <!-- <tr>
          <td align="right">终端ID:</td>
          <td align="left">
            <label id="unitIdEdit"></label>
            </td>
        </tr> -->
         <tr>
         <tr>
        <td align="right">
        服务开始日期:
        </td>
        <td align="left">
        <label id="openTimeEdit"></label> </td>
        </tr>
        <td align="right">
      	  服务结束日期:
        </td>
        <td align="left">
        <input id="endTimeEdit" name="endTime" type="text" class="Wdate"
        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
        style="width: 150px"></input>
        <span style="color: red">*</span></td>
        </tr>
        <!-- <tr>
          <td align="right">状态:</td>
          <td align="left">
            <input id="statusEdit" name="status" class="easyui-combobox" data-options="
				valueField: 'value',
				textField: 'label',
				data: [{
					label: '正常',
					value: '0'
				},{
					label: '停机保号',
					value: '1'
				}]" />
            </td>
        </tr>
        <tr>
            <td align="right">停机保号开始日期:</td>
	        <td align="left">
	        <input id="stopStartTimeEdit" type="text" class="Wdate"
	        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
	        style="width: 150px"></input>
	       </td>
         </tr>
         <tr>
            <td align="right">停机保号结束日期:</td>
	        <td align="left">
	        <input id="stopEndTimeEdit" type="text" class="Wdate"
	        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
	        style="width: 150px"></input>
	       </td>
         </tr> -->
        <!--  <tr>
             <td align="right">注销原因:</td>
	         <td align="left">
	      	 <input id="stopReasonEdit" name="stopReason" type="text" />
	        </td>
        </tr> -->
        <tr>
          <td align="right">备注:</td>
          <td align="left">
            <input id="remarkEdit" name="remark" type="text" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="operEditBtns">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="editPayBtn">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#customerPayEditWindow').dialog('close')">取消</a></div>
  <!-- 修改弹出框 end-->
  <iframe style="display: none" id="ifm_down"></iframe>
		    
		    
		    
	 
</div>
</div>
<script type="text/javascript">
//窗口大小变化
function vehicleResize(w,h){
	if($('#customer_pay_datagrid')){
		try{
		  $('#customer_pay_datagrid').datagrid('options');
		  $('#customer_pay_datagrid').datagrid('resize', {  
				width : w-2 ,
				height:h
		 }); 
		}catch(e){
		}
	}
}
</script>

<script type="text/javascript"  >
//申请开通 
function applyServerSim(){
	var val = $("#applyServerBtn").data("simNo");
	var simNo = val.split(",")[0];
  	var unitId = val.split(",")[1];
	var startTime = $("#applyStartTime").val();
	var endTime = $("#applyEndTime").val();
	if(startTime == null || startTime == ""){
		 $.messager.alert(tipMsgDilag, "服务开始日期必填");
		 return ;
	}
	
	if(endTime == null || endTime == ""){
		 $.messager.alert(tipMsgDilag, "服务结束日期必填");
		 return ;
	}
	
	$.post("${basePath}sim/customerSim_insert.action",{
 		simNo:simNo,
 		status:0,
 		startTime:startTime,
 		endTime:endTime
 	},function(data) {
 		var obj = $.parseJSON(data);
         if(obj == "1"){
          	$("#queryBtn").click();
          	$('#applayOpenReasonWindow').dialog('close');
         	$.messager.alert(tipMsgDilag, "成功");
         }else if(obj =="2"){
         	$.messager.alert(tipMsgDilag, "已经开通，不能重复开通");
         }else if(obj =="3"){
         	$.messager.alert(tipMsgDilag, "请先开通公司SIM卡功能");
         }else{
         	$.messager.alert(tipMsgDilag, "失败");
         }
     });
}

function openSimServer(val){
  	$('#applayOpenReasonWindow').dialog('open');
  	$("#applyServerBtn").data("simNo",val);
  	/*
  	var simNo = val.split(",")[0];
  	var unitId = val.split(",")[1];
  	$("#applyServerBtn").click(function(){
    		var startTime = $("#applyStartTime").val();
	  		var endTime = $("#applyEndTime").val();
	  		if(startTime == null || startTime == ""){
	  			 $.messager.alert(tipMsgDilag, "服务开始日期必填");
	  			 return ;
	  		}
	  		
	  		if(endTime == null || endTime == ""){
	  			 $.messager.alert(tipMsgDilag, "服务结束日期必填");
	  			 return ;
	  		}
    	
    		$.post("${basePath}sim/customerSim_insert.action",{
	    		simNo:simNo,
	    		status:0,
	    		startTime:startTime,
	    		endTime:endTime
	    	},function(data) {
	    		var obj = $.parseJSON(data);
	            if(obj == "1"){
	             	$("#queryBtn").click();
	             	$('#applayOpenReasonWindow').dialog('close');
	            	$.messager.alert(tipMsgDilag, "成功");
	            }else if(obj =="2"){
	            	$.messager.alert(tipMsgDilag, "已经开通，不能重复开通");
	            }else if(obj =="3"){
	            	$.messager.alert(tipMsgDilag, "请先开通公司SIM卡功能");
	            }else{
	            	$.messager.alert(tipMsgDilag, "失败");
	            }
	        });
    	});*/
  }

    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#customer_pay_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
            }); 
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#customer_pay_datagrid').datagrid('columnMoving');
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 

<script type="text/javascript">
 function statusTips(val){
 	if(val=='' || val == null){
 		return '未开通';
 	}else if( parseInt(val)> 0 ){
 	 	return '再有'+parseInt(val)+'天到期';
 	}else if(parseInt(val)< 0){
 		return '欠费'+Math.abs(parseInt(val))+'天';
 	}else if(parseInt(val) == 0){
 		return '今天到期';
 	}
 }
function tips(val,row){
	if(row.isServer == '' || row.isServer == null){
		return '未开通';
	}else{
		if(val =='1') 
	    	return '停机保号';
	    else 
	    	return '开通';
	}
}

 //批量注销
 function batchStopServer(){
 	var nodes = $("#customer_pay_datagrid").datagrid("getChecked");
 	var simNoList = [];
    for(var i=0;i<nodes.length;i++){
   	  simNoList.push(nodes[i].simNo);
    }
    var simNo = simNoList.join(",");
    if( simNo != "" && simNo != null){
    	//弹框
    	$('#batchSimReasonWindow').dialog('open');
    	$("#batchCancelSimServerBtn").click(function(){
    		var stopStartTime = $("#batchStopStartTime").val();
	  		var stopEndTime = $("#batchStopEndTime").val();
	  		if(stopStartTime == null || stopStartTime == ""){
	  			 $.messager.alert(tipMsgDilag, "停机保号开始日期必填");
	  			 return ;
	  		}
	  		
	  		if(stopEndTime == null || stopEndTime == ""){
	  			 $.messager.alert(tipMsgDilag, "停机保号结束日期必填");
	  			 return ;
	  		}
    	
    		$.post("${basePath}sim/customerSim_batchStopSimServer.action",{
	    		simNo:simNo,
	    		status:1,
	    		stopStartTime:stopStartTime,
	    		stopEndTime:stopEndTime,
	    		stopReason:$("#batchStopReasons").val()
	    	},function(data) {
	    		var obj = $.parseJSON(data);
	            if(obj.success){
	             	$("#queryBtn").click();
	             	$('#batchSimReasonWindow').dialog('close');
	            	$.messager.alert(tipMsgDilag, "成功");
	            }else{
	            	$.messager.alert(tipMsgDilag, "失败");
	            }
	        });
    	});
    }
 }
 //批量导入
function batchStop(){
   //判断文件类型
	var objtype=$('#upload',$('#stop_impport_Form')).val();
	 var fileType=new Array(".xls",".xlsx");
	 var flag=false;//判断是否是excel格式
	if(objtype){
		objtype=objtype.substring(objtype.lastIndexOf(".")).toLowerCase();
		 for(var i=0; i<fileType.length; i++){
        if(objtype==fileType[i])
        {
             flag=true;
             break;
        }
       }
       if(flag){
           $('#stop_impport_Form').form('submit', { 
				url: '${basePath}sim/customerSim_batchStopServer.action', 
				onSubmit: function(){ 
				  return $(this).form('validate');
				}, 
				success:function(data){ 
					$("#queryBtn").click();
				  var r=null;
				  try{
				          r = $.parseJSON(data);
				          if(r.flag == "0"){
				          	$.messager.alert(tipMsgDilag, r.tips);
				          }else if(r.flag == "1"){
				          	$("#queryBtn").click();
				            $.messager.alert(tipMsgDilag, r.tips);
				          	$('#batchStopImpport').dialog('close');
				          }else if(r.flag == "2"){
				          	var  errorData = r.errorList;
				          	$("#errorStopGrid").show();
				          	//导入的错误数据 
				            var html = "";
				            for(var i = 0 ; i < errorData.length;i++){
				            	var startTime = errorData[i].stopStartTime;
				            	if( startTime != null && startTime != ""){
				            		startTime = new Date(startTime.replace('T',' ')).format("yyyy-MM-dd");
				            	}else{
				            		startTime = "";
				            	}
				            	
				            	var endTime = errorData[i].stopEndTime;
				            	if( endTime != null && endTime != ""){
				            		endTime = new Date(endTime.replace('T',' ')).format("yyyy-MM-dd");
				            	}else{
				            		endTime = "";
				            	}
				           	   html +="<tr><td>"+errorData[i].simNo+"</td><td>"+startTime+"</td><td>"+endTime+"</td><td>"+errorData[i].remark+"</td></tr>";
				            }
				            $("#errorStopTbody").html(html);
				          }else if(r.flag == "3"){
				          	$.messager.alert(tipMsgDilag,"程序内部数据错误");
				          }
					  }catch (e) {
						  $.messager.alert(tipMsgDilag,"程序内部数据错误");
					  }
				   } 
				}); 
       }else{
         $.messager.alert(tipMsgDilag, '请选择Excel文件!');
       }
	}else{
		$.messager.alert(tipMsgDilag, '请选择文件!');
	}
}
 
//批量导入
function batchImpCustomerPay(){
   //判断文件类型
	var objtype=$('#upload',$('#vehicle_impport_Form')).val();
	 var fileType=new Array(".xls",".xlsx");
	 var flag=false;//判断是否是excel格式
	if(objtype){
		objtype=objtype.substring(objtype.lastIndexOf(".")).toLowerCase();
		 for(var i=0; i<fileType.length; i++){
        if(objtype==fileType[i])
        {
             flag=true;
             break;
        }
       }
       if(flag){
           $('#vehicle_impport_Form').form('submit', { 
				url: '${basePath}sim/customerSim_impFromExcel.action', 
				onSubmit: function(){ 
				  return $(this).form('validate');
				}, 
				success:function(data){ 
					$("#queryBtn").click();
				  var r=null;
				  try{
				          r = $.parseJSON(data);
				          if(r.flag == "0"){
				          	$.messager.alert(tipMsgDilag, r.tips);
				          }else if(r.flag == "1"){
				          	$("#queryBtn").click();
				            $.messager.alert(tipMsgDilag, r.tips);
				          	$('#customerPayImpport').dialog('close');
				          }else if(r.flag == "2"){
				          	var  errorData = r.errorList;
				          	$("#errorGrid").show();
				          	//导入的错误数据 
				            var html = "";
				            for(var i = 0 ; i < errorData.length;i++){
				            	var startTime = errorData[i].startTime;
				            	if( startTime != null && startTime != ""){
				            		startTime = new Date(startTime.replace('T',' ')).format("yyyy-MM-dd");
				            	}else{
				            		startTime = "";
				            	}
				            	
				            	var endTime = errorData[i].endTime;
				            	if( endTime != null && endTime != ""){
				            		endTime = new Date(endTime.replace('T',' ')).format("yyyy-MM-dd");
				            	}else{
				            		endTime = "";
				            	}
				           	   html +="<tr><td>"+errorData[i].simNo+"</td><td>"+startTime+"</td><td>"+endTime+"</td><td>"+errorData[i].remark+"</td></tr>";
				            }
				            $("#errorTbody").html(html);
				          }else if(r.flag == "3"){
				          	$.messager.alert(tipMsgDilag,"程序内部数据错误");
				          }
					  }catch (e) {
						  $.messager.alert(tipMsgDilag,"程序内部数据错误");
					  }
				   } 
				}); 
       }else{
         $.messager.alert(tipMsgDilag, '请选择Excel文件!');
       }
	}else{
		$.messager.alert(tipMsgDilag, '请选择文件!');
	}
}
  

  //表格中的操作
function operateTool(val,row,index){
	if( row.simNo == "" || row.simNo == null){
		return "";
	}else{
		if(row.isServer == "" ||row.isServer == null){
			//申请开通不要 
			return'<a style="color:blue;" class="customerPayEditBtn" href="javascript:void(0)" onclick="openSimServer(\''+row.simNo+','+row.unitId+'\')" >申请开通</a>';
		}else{
			var simNo = row.simNo;
			var status = row.status;
			if(status == "1"){
				return'<a style="color:blue;" class="customerPayEditBtn" href="javascript:void(0)" onclick="cancelStopServer(\''+simNo+'\')" >取消停机保号</a>';
			}else{
				return'<a style="color:blue;" class="customerPayEditBtn" href="javascript:void(0)" onclick="stopServer(\''+simNo+'\')" >停机保号</a><a style="color:blue;margin-left:5px" class="customerPayEditBtn" href="javascript:void(0)" onclick="customerPayEdit(\''+simNo+'\')" >编辑</a>';
			}
		}
	}
}

function cancelStopServer(val){
	 $.messager.confirm(tipMsgDilag, "取消停机保号?",function(r) {
	    if (r) {
	        $.post("${basePath}sim/customerSim_stopCustomerSim.action", {simNo:val,status:0,stopFeeMonth:0},function(data) {
	            var obj = $.parseJSON(data);
	            if(obj.success){
	             	$("#queryBtn").click();
	            	$.messager.alert(tipMsgDilag, "操作成功");
	            }else{
	            	$.messager.alert(tipMsgDilag, "操作失败");
	            }
	        });
	    }
	});
}
function stopServer(val){
	  $('#simReasonWindow').dialog('open');
	  $("#stopStartTime,#stopEndTime,#stopReasons").val("");
	  $("#cancelSimServerBtn").click(function(){
	  		var stopStartTime = $("#stopStartTime").val();
	  		var stopEndTime = $("#stopEndTime").val();
	  		var stopFeeMonth = $("#stopFeeMonth").val();
	  		if(stopStartTime == null || stopStartTime == ""){
	  			 $.messager.alert(tipMsgDilag, "停机保号开始日期必填");
	  			 return ;
	  		}
	  		
	  		if(stopEndTime == null || stopEndTime == ""){
	  			 $.messager.alert(tipMsgDilag, "停机保号结束日期必填");
	  			 return ;
	  		}
	  		
	  		if(stopFeeMonth == null || stopFeeMonth == ""){
	  			 $.messager.alert(tipMsgDilag, "停机保号月费用必填");
	  			 return ;
	  		}
	  		
	  		 $.post("${basePath}sim/customerSim_stopCustomerSim.action",{
	  					simNo:val,
	  					status:1,
	  					stopStartTime:stopStartTime,
	  					stopEndTime:stopEndTime,
	  					stopReason:$("#stopReasons").val(),
	  					stopFeeMonth:stopFeeMonth
	  		 },function(data) {
	            var obj = $.parseJSON(data);
	            if(obj.success){
	             	$("#queryBtn").click();
	             	$('#simReasonWindow').dialog('close');
	            	$.messager.alert(tipMsgDilag, "操作成功");
	            }else{
	            	$.messager.alert(tipMsgDilag, "操作失败");
	            }
	        });
	  });
	  
	 /*$.messager.confirm(tipMsgDilag, "确定注销?",function(r) {
	    if (r) {
	        $.post("${basePath}sim/companySim_stopSimServer.action",{simNo:val,status:1},function(data) {
	            var obj = $.parseJSON(data);
	            if(obj.success){
	             	$("#queryBtn").click();
	            	$.messager.alert(tipMsgDilag, "操作成功");
	            }else{
	            	$.messager.alert(tipMsgDilag, "操作失败");
	            }
	        });
	    }
	});*/
}

 //编辑
 function customerPayEdit(val){
	 $('#customerPayEditWindow').dialog('open');
	 $("#custPayId").val(val);
	 $.post('${basePath}sim/customerSim_getCustomerSimPOJOById.action',{simNo:val},function(result){
		if(result != null && result != ""){
		    var obj = JSON.parse(result); 
		    $('#simNoEdit').html(obj.simNo);
			/*$("#unitIdEdit").html(obj.unitId);*/
			if(obj.startTime!="" && obj.startTime!=null){
				$("#openTimeEdit").html(new Date(obj.startTime.replace('T',' ')).format("yyyy-MM-dd"));
			}
			if(obj.endTime!="" && obj.endTime!=null){
		    	$("#endTimeEdit").val(new Date(obj.endTime.replace('T',' ')).format("yyyy-MM-dd"));
		    }
		    /*if(obj.stopStartTime !=""  && obj.stopStartTime!=null){
		    	$("#stopStartTimeEdit").val(new Date(obj.stopStartTime.replace('T',' ')).format("yyyy-MM-dd"));
			}
			if(obj.stopEndTime !=""  && obj.stopEndTime!=null){
		    	$("#stopEndTimeEdit").val(new Date(obj.stopEndTime.replace('T',' ')).format("yyyy-MM-dd"));
			}
		    $('#statusEdit').combobox('setValue',obj.status);*/
		    $("#remarkEdit").val(obj.remark);
		}
	})
 
 }
  
  $("#editPayBtn").click(function(){
  	var simNo  =$("#simNoEdit").html();
  	//var unitId  =$("#unitIdEdit").html();
  	var startTime  =$("#openTimeEdit").html();
  	var endTime  =$("#endTimeEdit").val();
  	/*var status  =$("#statusEdit").closest("td").find("input[type='hidden']").val();
  	var stopStartTime  =$("#stopStartTimeEdit").val();
  	var stopEndTime  =$("#stopEndTimeEdit").val();
  	*/var remark  =$("#remarkEdit").val();
  	if(endTime == null || endTime == ""){
  		$.messager.alert(tipMsgDilag, "服务结束时间必填!");
        return;
  	}
  	
  
  	//如果是停机保号，要判断停机保号的开始和结束时间必须填写 
  	/*if(status == "1"){
  		if( stopStartTime == null || stopStartTime == "" ){
	  		$.messager.alert(tipMsgDilag, "停机保号开始时间必填!");
	        return;
	  	} 
	  	if( stopEndTime == null || stopEndTime == "" ){
	  		$.messager.alert(tipMsgDilag, "停机保号结束时间必填!");
	        return;
	  	} 
  	}
  	*/
	var data = {
		simNo:simNo,
		//unitId:unitId,
		startTime:startTime,
		endTime:endTime,
		//status:status,
		//stopStartTime:stopStartTime,
		//stopEndTime:stopEndTime,
		remark:remark
  	};
  	
		$.post("${basePath}sim/customerSim_updateCustomerServer.action", data,function(data) {
          var obj = $.parseJSON(data);
          if(obj.success){
           	$("#queryBtn").click();
           	$('#customerPayEditWindow').dialog('close');
          	$.messager.alert(tipMsgDilag, "更新成功");
          }else{
          	$.messager.alert(tipMsgDilag, "更新失败");
          }
        });
  });
  
  
  
  //验证SIMNO
    function simNoValidate(obj) {
      var flag = true;
      if (obj == null || obj == "") {
        $.messager.alert(tipMsgDilag, "SIM卡号必填!");
        flag = false;
      } else {
        if (! (/^\d{11}$/.test(obj))) {
          $.messager.alert(tipMsgDilag, "SIM卡号有误!");
          flag = false;
        } else {
          flag = true;
        }
      }
      return flag;
    }
    //缴费金额 
    function payAmountValidate(obj) {
      var flag = true;
      if (obj == null || obj == "") {
        $.messager.alert(tipMsgDilag, "缴费金额必填!");
        flag = false;
      } else {
        var reg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
        if (! (reg.test(obj))) {
          $.messager.alert(tipMsgDilag, "缴费金额有误!");
          flag = false;
        } else {
          flag = true;
        }
      }
      return flag;
    };

    ; (function($) {
      var customerPay = {
        init: function() {
          $("#queryBtn").click(function() {
            $('#customer_pay_datagrid').datagrid('load', {
              simNo: $("#sim_no").closest("td").find("input[type='hidden']").val(),
              endTime: $('#end_time').val(),
              startTime: $('#start_time').val(),
              status:$("#statusCon").closest("td").find("input[type='hidden']").val(),
              vehicleDef:$("#vehicleDef").closest("td").find("input[type='hidden']").val(),
              dealerId:$('#dealerId_search').combobox('getValues').join(',')
            });
          });
          $("#addBtn").click(function() {
            $("#customerPayWindow").dialog('open');
            $("#simNo").text("");
            $("#endTime,#openTime").val("");
            $("#remark").val("");
            $('#unitIds').combobox({
				    url:'${basePath}sim/customerSim_getUnitList.action',
				    valueField:'simNo',  
       				textField:'unitId',
       				onChange:function(data){
       				$("#simNo").val(data);
       					/*$.post("${basePath}sim/customerPay_customerSimById.action",{simNo:data},function(result){
       						if(result != null  && result != ""){
       							var obj = JSON.parse(result); 
       							$("#startTime").text(obj.remark);
       						}else{
       						    $("#startTime").text("");
       						}
       					});*/
       				}
			});
          });
		  $("#saveCustomerPayBtn").click(function(){
		  		var simNo = $("#simNo").val();
		  		if($("#simNo").val() == "" || $("#simNo").val() == null ){
		  		    $.messager.alert(tipMsgDilag, "终端号必填!");
		  			return; 
		  		}
		  		if($("#openTime").val() == "" || $("#openTime").val() == null ){
		  		    $.messager.alert(tipMsgDilag, "服务开始日期必填!");
		  			return; 
		  		}
		  		
	  			if($("#endTime").val() == "" || $("#endTime").val() == null ){
		  		    $.messager.alert(tipMsgDilag, "服务结束日期必填!");
		  			return; 
		  		}
		  		
		  		var data = {
	  				simNo:$("#simNo").val(),
	  				unitId:$("#unitIds").closest("td").find("input[type='hidden']").val(),
	  				startTime:$("#openTime").val(),
	  				remark:$("#remark").val(),
	  				endTime:$("#endTime").val(),
	  				status:0,
	  				feeMonth:0
	  			}
	  			$.post('${basePath}sim/customerSim_saveCustomerSim.action',data,function(result){
       				var obj = JSON.parse(result);
	  				if( obj.success){
	  					if(obj.msg == "成功"){
	  						$("#queryBtn").click();
	  						$("#customerPayWindow").dialog('close');
	  					}else{
	  						$.messager.alert(tipMsgDilag, "已经存在该SIM信息");
	  					}
	  				}
	  			});
		  });
        }
      };
      customerPay.init();

    })(jQuery);
    
//导出Excel
function downExcelVehicle(){
	var simNo = $('#sim_no').combobox('getValue') ;
	if(simNo == "" || simNo == null){
		simNo = "";
	}
	var startTime = $('#start_time').val();
	if(startTime == "" || startTime == null){
		startTime = "";
	}
	var endTime = $('#end_time').val();
	if(endTime == "" || endTime == null){
		endTime = "";
	}
	var statusCon = $("#statusCon").combobox("getValue");
	if(statusCon == "" || statusCon == null){
		statusCon = "";
	}
	window.location.href=encodeURI(encodeURI("${basePath}sim/customerSim_exportToExcelVehicle.action?simNo="+simNo+
							"&openTime="+startTime+
							"&endTime="+endTime+
							"&vehicleDef="+$("#vehicleDef").closest("td").find("input[type='hidden']").val()+
							"&dealerId="+$('#dealerId_search').combobox('getValues').join(',')+
							"&status="+statusCon));
	   return;
}
    
    Date.prototype.format = function(fmt) { 
     var o = { 
        "M+" : this.getMonth()+1,                 //月份 
        "d+" : this.getDate(),                    //日 
        "h+" : this.getHours(),                   //小时 
        "m+" : this.getMinutes(),                 //分 
        "s+" : this.getSeconds(),                 //秒 
        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        "S"  : this.getMilliseconds()             //毫秒 
    }; 
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
    return fmt; 
}        
    
    //日期格式化
 function dateFormat(val,row,index){
 	if( val != "" && val != null ){
 		return new Date(val.replace('T',' ')).format("yyyy-MM-dd");
 	}
 	return "";
 } 
 
 //js 导出错误table
$("#errorData").click(function(){
	$("#errorTable").table2excel({
	  // 导出的Excel文档的名称
	  name: "Excel Document Name",
	  // Excel文件的名称
	  filename: "客户批量申请开通SIM失败记录"
	});
});

 //js 导出错误table
$("#errorStopData").click(function(){
	$("#errorStopTable").table2excel({
	  // 导出的Excel文档的名称
	  name: "Excel Document Name",
	  // Excel文件的名称
	  filename: "批量停机保号失败记录"
	});
});
    </script>
    
   