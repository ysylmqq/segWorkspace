<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type='text/javascript' src='js/autoComplete/jquery.autocomplete.js'></script>
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
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:vehicleResize"  style="overflow:hidden;">
 
    <!-- 表格 begin -->
    <table id="customer_pay_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}sim/companySim_changeUnitsearch.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'终端换装'">
             <thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:150,align:'center'">整机编号</th>
   		        <th data-options="field:'distributor',width:240,align:'center'">经销商</th>
                <th data-options="field:'modelName',width:150,align:'center'">机械型号</th>
   		        <th data-options="field:'vehicleArg',width:120,align:'center'">机械配置</th>
   		        <th data-options="field:'unitSn',width:150,align:'center'">终端序列号</th>
		        <th data-options="field:'simNo',width:150,align:'center'">SIM卡号</th>
		        <th data-options="field:'openTime',width:150,align:'center',formatter:dateFormat">服务开始日期</th>
		        <th data-options="field:'endTime',width:150,align:'center',formatter:dateFormat">服务结束日期</th>
		        <th data-options="field:'status',width:100,align:'center',formatter:tips">卡号情况</th>
		        <th data-options="field:'stopTime',width:150,align:'center',formatter:dateFormat">注销时间</th>
		        <th data-options="field:'stopReason',width:150,align:'center'">注销原因</th>
		        <th data-options="field:'remark',width:150,align:'center'">备注</th>
		        <th data-options="field:'userName',width:150,align:'center'">操作人</th>
		        <th data-options="field:'createTime',width:150,align:'center',formatter:dateFormat">创建日期</th>
		        <th data-options="field:'operId',width:80,align:'center',formatter:operateTool">操作</th>
	            </tr>  
        </thead>
	 </table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
    <table style="font-size: 12px;">
     <td align="right">整机编号:</td>
    	<td>
         <input id="vehicleDefTemp" name="vehicleDef" class="easyui-combobox"
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
							url:'${basePath}sim/companySim_simServerList.action',
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
					label: '开通',
					value: '0'
				},{
					label: '注销',
					value: '1'
				}]" />
        </td>
      </tr>
      <tr>
      	<td colspan="11" align="left" style="padding-left: 20px;width:100%">
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="queryBtn">查询</a>
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcelChangeUnit();">导出</a>
          </td>
      </tr>
    </table>
  </div>
  <!-- 查询条件 end -->
  
  
<!-- 新增或者修改弹出框 begin-->
		<div id="tb" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						整机编号:
					</td>
					<td>
						<input id="vehicledef_search" type="text" style="width: 150px;">
					</td>
					<td align="right" style="padding-left: 20px;">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryVehicleInDlg()">查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="tb1" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						终端序列号:
					</td>
					<td>
						<input id="unitsn_search" type="text" style="width: 150px;">
					</td>
					<td align="right" style="padding-left: 20px;">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryUnitInDlg()">查询</a>
					</td>
				</tr>
			</table>
		</div>
			
			<div id="dlg_operate" class="easyui-dialog" style="padding: 5px; width: 600px; height: 360px;"
				data-options="iconCls:'icon-save',modal: true,closed: true,title: '终端换装',buttons:'#dlg_operate_btns',center: true">
				<form id="operate_form" method="post" action="${basePath}/run/unitReplace_unitReplace.action">
					<table style="font-size: 12px;" width="100%">
						<tr>
							<td align="right">
								原终端SIM卡号：
							</td>
							<td>
								<input id="oldSimNo" style="width: 160px;" readonly="readonly" />
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								整机编号：
							</td>
							<td>
								<select id="vehicleDef" class="easyui-combogrid" style="width: 160px;"
									data-options="panelWidth:425,idField:'vehicleDef',textField:'vehicleDef',
				        			url:'${basePath}vehicle/vehicle_search.action',pagination:true,toolbar:'#tb',onChange:initParam,
				        			columns:[[  
						                {field:'vehicleDef',title:'整机编号',width:100},
						                {field:'modelName',title:'机械型号',width:100},
						                {field:'unitSn',title:'终端序列号',width:100},
						                {field:'simNo',title:'SIM卡号',width:100}
						            ]]">
								</select>
								<span style="color:red">*</span>
							</td>
						</tr>
						<!-- <tr>
							<td align="right">
								新终端序列号：
							</td>
							 <td>
								<select id="newUnitSn" class="easyui-combogrid" style="width: 160px;"
									data-options="panelWidth:500,value:'unitId',idField:'unitId',textField:'unitSn',
				        			url:'${basePath}unit/unit_search.action?state=1&isValid=0',pagination:true,toolbar:'#tb1',
				        			onChange:getUnitDate,
				        			columns:[[  
				        				{field:'unitId',title:'终端ID',width:100},
						                {field:'unitSn',title:'终端序列号',width:100},
						                {field:'supperierName',title:'供应商',width:100},
						                {field:'unitType',title:'终端类型',width:100},
						                {field:'simNo',title:'SIM卡号',width:100},
						                {field:'materialNo',title:'物料条码',width:100},
						                {field:'steelNo',title:'钢号',width:100},
						                {field:'state',title:'终端状态',width:100,formatter:function(val,row,index){if(val==1){return '待安装';}}}
						            ]]">
								</select>
								<span style="color:red">*</span>
							</td>
						</tr>-->
						<tr>
							<td align="right">
								新SIM卡号：
							</td>
							<td>
								<input id="newSimNo"  name="simNo" type="text" readonly="readonly" style="width:150px"></input>
								<input id="newUnitId"  name="unitId" type="hidden"></input>
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								服务开始时间：
							</td>
							<td>
								<input id="openTime"  name="openTime" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:150px"></input>
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								服务结束时间：
							</td>
							<td>
								<input id="endTime"  name="endTime" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:150px"></input>
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								SIM已交金额：
							</td>
							<td>
								<input id="payAmount"  name="payAmount" type="text" style="width:150px"></input>
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								换装原因：
							</td>
							<td>
								<textarea id="reason" rows="4" cols="30" ></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="dlg_operate_btns">
				<a href="javaScript:void(0)" id="saveCustomerPayBtn"  onclick="changeUnitSure()" class="easyui-linkbutton" iconCls="icon-ok">换装</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_operate').dialog('close')">取消</a>
			</div>		    



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
    
//导出Excel
function downExcelChangeUnit(){
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
	window.location.href=encodeURI(encodeURI("${basePath}sim/companySim_exportToExcelChangeUnit.action?simNo="+simNo+
							"&openTime="+startTime+
							"&endTime="+endTime+
							"&vehicleDef="+$("#vehicleDefTemp").closest("td").find("input[type='hidden']").val()+
							"&dealerId="+$('#dealerId_search').combobox('getValues').join(',')+
							"&status="+statusCon));
	   return;
}

//查询终端
function queryUnitInDlg() {
	$("#vehicleDef").combogrid("grid").datagrid("load", {unitSn:$("#unitsn_search").val()});
}

function initParam() {
	var row = $("#vehicleDef").combogrid("grid").datagrid("getSelected");
	console.log("row ",row);
	$('#newSimNo').val(row.simNo);
	$("#newUnitId").val(row.unitId);
}
function tips(val,row){
	if(val =='1') 
    	return '注销';
    else 
    	return '开通';
}
  //表格中的操作
function operateTool(val,row,index){
	var simNo = row.simNo;
	var status = row.status;
	var vehicleDef = row.vehicleDef;
	var unitSn = row.unitSn;
	return'<a style="color:blue;" class="customerPayEditBtn" href="javascript:void(0)" onclick="changeUnit(\''+simNo+'\')" >更换终端</a>';
}

function getUnitDate(unitId){
    /*$.post('${basePath}unit/unit_getById.action',{unitId:unitId},function(result){
    	if( result != null && result != "" ){
    	    var obj = $.parseJSON(result);
    	    $("#newSimNo").val(obj.simNo);
    	}
    });*/
    var row = $("#newUnitSn").combogrid("grid").datagrid("getSelected");
	$('#newSimNo').val(row.simNo);
    
}
//窗口大小变化
function unitReplaceResize(w, h) {
	if ($("#unitreplace_datagrid")) {
		try {
			$("#unitreplace_datagrid").datagrid("options");
			$("#unitreplace_datagrid").datagrid("resize", {width:w - 2, height:h});
		}
		catch (e) {}
	}
}
 //更换终端
 function changeUnitSure(){
	var vehicleDef = $("#vehicleDef").closest("td").find("input[type='hidden']").val();
  		if(vehicleDef == "" || vehicleDef == null ){
	    $.messager.alert(tipMsgDilag, "整机编号必填!");
		return; 
	}
	
	var oldSimNo = $("#oldSimNo").val();
  		if(oldSimNo == "" || oldSimNo == null ){
	    $.messager.alert(tipMsgDilag, "原终端SIM卡号必填!");
		return; 
	}
	
	var simNo = $("#newSimNo").val();
	if(simNo == "" || simNo == null ){
	    $.messager.alert(tipMsgDilag, "新SIM号必填!");
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
		simNo:$("#newSimNo").val(),
		unitId:$("#unitId").val(),
		openTime:$("#openTime").val(),
		payAmount:$("#payAmount").val()||'0',
		remark:$("#remark").val(),
		endTime:$("#endTime").val(),
		status:0,
		feeMonth:0,
		stopReason:$("#oldSimNo").val() //stopReason里面存放时 oldSimNo
	}
	$.post('${basePath}sim/companySim_changeUnit.action',data,function(result){
  				var obj = JSON.parse(result);
		if( obj.success){
			if(obj.msg == "成功"){
				$("#queryBtn").click();
				$("#dlg_operate").dialog('close');
			}else{
				$.messager.alert(tipMsgDilag, obj.msg );
			}
		}
	});
 }
 
 function changeUnit(val){
	  $('#operate_form').form('clear');
	  $("#dlg_operate").dialog("open");
	  $("#oldSimNo").val(val);
 }
  
    ; (function($) {
      var customerPay = {
        init: function() {
          $("#queryBtn").click(function() {
            $('#customer_pay_datagrid').datagrid('load', {
              simNo: $("#sim_no").closest("td").find("input[type='hidden']").val(),
              endTime: $('#end_time').val(),
              openTime: $('#start_time').val(),
              status:$("#statusCon").closest("td").find("input[type='hidden']").val(),
              dealerId:$('#dealerId_search').combobox('getValues').join(','),
              vehicleDef:$("#vehicleDefTemp").closest("td").find("input[type='hidden']").val()
            });
          });
        }
      };
      customerPay.init();

    })(jQuery);
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
    </script>
