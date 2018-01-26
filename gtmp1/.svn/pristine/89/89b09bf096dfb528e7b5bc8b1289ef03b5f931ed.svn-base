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
			data-options="url:'${basePath}sim/customerPay_search.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'客户缴费'">
             <thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:150,align:'center'">整机编号</th>
                <th data-options="field:'distributor',width:240,align:'center'">经销商</th>
                <th data-options="field:'modelName',width:150,align:'center'">机械型号</th>
                <!-- 
   		        <th data-options="field:'vehicleCode',width:150,align:'center'">机械代号</th>
   		         --><th data-options="field:'vehicleArg',width:150,align:'center'">机械配置</th>
   		        <th data-options="field:'unitSn',width:150,align:'center'">终端序列号</th>
		      
		        <th data-options="field:'simNo',width:150,align:'center'">SIM卡号</th>
		        <th data-options="field:'startTime',width:150,align:'center',formatter:dateFormat">服务开始日期</th>
		        <th data-options="field:'endTime',width:150,align:'center',formatter:dateFormat">服务结束日期</th>
		        <th data-options="field:'payAmount',width:100,align:'center'">缴费金额</th><!--
		        <th data-options="field:'feeMonth',width:150,align:'center'">每月费用（元）</th>
		        --><th data-options="field:'remark',width:150,align:'center'">备注</th>
		        <th data-options="field:'userName',width:150,align:'center'">操作人</th>
		        <th data-options="field:'createTime',width:150,align:'center',formatter:dateFormat">缴费日期</th>
		        <th data-options="field:'custPayId',width:80,align:'center',formatter:operateTool">操作</th>
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
         <!--  <input id="vehicleDef"  name="vehicleDef" type="text" style="width:150px"></input>
        	 -->	<input id="vehicleDef" name="vehicleDef" class="easyui-combobox"
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
							url:'${basePath}sim/customerPay_simList.action',
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
        <td align="right">缴费金额:</td>
        <td>
          <input id="pay_amount" name="payAmount" type="text" style="width:150px"></input>
        </td>
       <!--  <td align="right">缴费日期:</td>
        <td>
          <input id="create_time" name="createTime" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:150px"></input>
        </td> -->
      </tr>
      <tr>
        <td colspan="11" align="left" style="padding-left: 20px;width:100%">
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="queryBtn">查询</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="addBtn">缴费</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-batch_import" onclick="javascript:$('#customerPayImpport').dialog('open')">批量缴费</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcelVehicle();">导出</a></td>
      </tr>
    </table>
  </div>
		    <!-- 查询条件 end -->
		    
 <!-- 批量导入弹出框 begin-->
  <div id="customerPayImpport" class="easyui-dialog" title="批量导入" data-options="iconCls:'icon-save',modal:true"
		style="padding: 5px; width: 650px; height: 380px;" closed="true" buttons="#customerPayImpportBtns">
        <form id="vehicle_impport_Form" method="post" theme="simple"
			enctype="multipart/form-data" action="${basePath}sim/customerPay_impFromExcel.action" >
			 <table style="font-size:12px;width: 100%;">
		        <tr>
						<td>选择文件：</td>
						<td>
						<input width="100px" type="file" id="upload"  name="upload"/>
						<input type="button" value="模板下载" onclick="document.getElementById('ifm_down').src='${basePath}doc/template/customer_pay_templates.xls';">
					   </td>
					</tr>
					</table>
		</form>
		<!-- 导入错误数据显示开始 -->
		<div id="errorGrid" class="out_excel_boder" style="display: none;">
		<div style="margin-bottom:10px"><span>错误数据</span><button style="margin-left:10px" id="errorData">导出</button></div>
			<table id="errorTable" >
				<thead>
					<tr>
						<th>SIM卡号</th>
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
	   
  <!-- 新增弹出框 begin-->
  <div id="customerPayWindow" class="easyui-dialog" title="客户缴费" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 600px; height: 360px;" closed="true" buttons="#operBtns">
    <form id="vehicle_operate_form" metdod="post" tdeme="simple">
      <input id="unitId" name="unitId" type="hidden" />
      <input id="vehicleId" name="vehicleId" type="hidden" />
      <table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
        <tr>
          <td align="right">整机编号:</td>
          <td align="left">
          <input id="vehicleDefAdd" name="simNo" class="easyui-combobox" 
							style="width: 200px;"
			/>
          <span style="color: red">*</span></td>
        </tr>
        <tr>
          <td align="right">SIM卡号:</td>
          <td align="left">
         <!--  <input id="simNo" name="simNo" class="easyui-combobox" 
							style="width: 200px;"
			/>
           -->
           <input id="simNo" name="simNo" type="text" readonly="readonly"/>
            <span style="color: red">*</span></td>
        </tr>
        <tr>
          <td align="right">缴费金额:</td>
          <td align="left">
            <input id="payAmount" name="payAmount" class="easyui-validatebox" type="text" onBlur="payAmountValidate(this.value)" />
            <span style="color: red">*</span></td>
        </tr>
         <tr>
         <tr>
        <td align="right">
        服务开始日期:
        </td>
        <td align="left">
        <!-- <input id="startTime" name="startTime" type="text" class="Wdate"
        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
        style="width: 150px"></input>
        <span style="color: red">*</span> -->
        <label id="startTime"></label>
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
          <td align="right">每个月收费（元）:</td>
          <td align="left">
            <input id="feeMonth" name="feeMonth" type="text" /></td>
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
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  onclick="saveCustomerPay()" id="saveCustomerPayBtn">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#customerPayWindow').dialog('close')">取消</a></div>
  <!-- 新增弹出框 end-->
  
  <!--修改弹出框 begin-->
  <div id="customerPayEditWindow" class="easyui-dialog" title="编辑客户缴费" data-options="iconCls:'icon-save',modal: true" style="padding: 5px; width: 600px; height: 460px;" closed="true" buttons="#operEditBtns">
    <form id="" metdod="post" tdeme="simple">
      <input id="custPayId" name="custPayId" type="hidden" />
      <table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
        <tr>
          <td align="right">SIM卡号:</td>
          <td align="left">
          <input id="simNoEdit" name="simNo" class="easyui-combobox" 
							style="width: 200px;"
			/>
          
           <!--  <input id="simNo" name="simNo" type="text" class="easyui-validatebox" onBlur="simNoValidate(this.value)" />
             --><span style="color: red">*</span></td>
        </tr>
        <tr>
          <td align="right">缴费金额:</td>
          <td align="left">
            <input id="payAmountEdit" name="payAmount" class="easyui-validatebox" type="text" onBlur="payAmountValidate(this.value)" />
            <span style="color: red">*</span></td>
        </tr>
         <tr>
         <tr>
        <td align="right">
        服务开始日期:
        </td>
        <td align="left">
        <input id="startTimeEdit" name="startTime" type="text" class="Wdate"
        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
        style="width: 150px"></input>
        <span style="color: red">*</span></td>
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
        <tr>
          <td align="right">每个月收费（元）:</td>
          <td align="left">
            <input id="feeMonthEdit" name="feeMonth" type="text" /></td>
        </tr>
        <tr>
          <td align="right">备注:</td>
          <td align="left">
            <input id="remarkEdit" name="remark" type="text" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="operEditBtns">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="editCustomerPayBtn">保存</a>
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
				url: '${basePath}sim/customerPay_impFromExcel.action', 
				onSubmit: function(){ 
				  return $(this).form('validate');
				}, 
				success:function(data){ 
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
				            	var endTime = errorData[i].endTime;
				            	if(endTime == "" || endTime == null){
				            		endTime="";
				            	}else{
				            		endTime = new Date(endTime.replace('T',' ')).format("yyyy-MM-dd");
				            	}
				            
				           	   html +="<tr><td>"+errorData[i].simNo+"</td><td>"+endTime+"</td><td>"+errorData[i].remark+"</td></tr>";
				            }
				            $("#errorTbody").html(html);
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
 
 function saveCustomerPay(){
	var simNo = $("#simNo").val();
	var payAmount = $("#payAmount").val();
	if($("#endTime").val() == "" || $("#endTime").val() == null ){
	    $.messager.alert(tipMsgDilag, "服务结束日期必填!");
		return; 
	}
	var flag2 = payAmountValidate(payAmount);
		if(flag2){
			var data = {
				simNo:simNo,
				payAmount:payAmount,
				feeMonth:$("#feeMonth").val(),
				remark:$("#remark").val(),
				startTime:$("#startTime").val(),
				endTime:$("#endTime").val()
			}
			if(simNo !== null && simNo != "" && payAmount != null && payAmount != ""){
 			$.post('${basePath}sim/customerPay_custPay.action',data,function(result){
 				if( result == "1"){
 					$("#queryBtn").click();
 					$("#customerPayWindow").dialog('close');
 				}else if(result == "-2"){
 					//客户没有开通SIM卡服务 -1：终端没有开通SIM卡服务，也就是注册终端时没有网t_sim_server当中添加一条记录
 					$.messager.alert(tipMsgDilag, "程序内部错误!");
 				}else if(result == "0"){
 					$.messager.alert(tipMsgDilag, "客户没有开通SIM卡服务 !申请开通！");
 				}else if(result == "-1"){
 					$.messager.alert(tipMsgDilag, "终端没有开通SIM卡服务！");
 				}
 			});
			}
		}
 }
  
  //表格中的操作
function operateTool(val,row,index){
	var isLast = row.isLast;
	if(isLast == 1){
		return'<a style="color:blue;" class="customerPayEditBtn" href="javascript:void(0)" onclick="customerPayDel(\''+val+'\')" >删除</a>';
	}else{
		return'';
	}
}
function customerPayDel(val){
	 $.messager.confirm(tipMsgDilag, "确定删除?",function(r) {
	    if (r) {
	        $.post("${basePath}sim/customerPay_delCustomerPayPOJOById.action", {custPayId:val},function(data) {
	            var obj = $.parseJSON(data);
	            if(obj.success){
	             	$("#queryBtn").click();
	            	$.messager.alert(tipMsgDilag, "删除成功");
	            }else{
	            	$.messager.alert(tipMsgDilag, "删除失败");
	            }
	        });
	    }
	});
}

 //编辑cutomerPay 
 function customerPayEdit(val){
	 $('#customerPayEditWindow').dialog('open');
	 $('#simNoEdit').combobox({
				    url:'${basePath}sim/customerPay_simList.action',
				    valueField:'simNo',  
       				textField:'simNo'
			});
	 $("#custPayId").val(val);
	 $.post('${basePath}sim/customerPay_getCustomerPayPOJOById.action',{custPayId:val},function(result){
		if(result != null && result != ""){
		    var obj = JSON.parse(result); 
		    $('#simNoEdit').combobox('setValue', obj.simNo);
			$("#payAmountEdit").val(obj.payAmount);
			$("#startTimeEdit").val(obj.startTime);
		    $("#endTimeEdit").val(obj.endTime);
		    $("#feeMonthEdit").val(obj.feeMonth);
		    $("#remarkEdit").val(obj.remark);
		}
	})
 
 }
  
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
              payAmount: $('#pay_amount').val(),
              createTime: $('#create_time').val(),
              vehicleDef:$("#vehicleDef").closest("td").find("input[type='hidden']").val(),
              dealerId:$('#dealerId_search').combobox('getValues').join(',')
            });
          });
          $("#addBtn").click(function() {
            $("#customerPayWindow").dialog('open');
            $("#startTime").text("");
            $("#remark,#simNo,#feeMonth,#endTime,#payAmount").val("");
            /*$('#simNo').combobox({
				    url:'${basePath}sim/customerPay_simList.action',
				    valueField:'simNo',  
       				textField:'simNo',
       				onChange:function(data){
       					$.post("${basePath}sim/customerPay_customerSimById.action",{simNo:data},function(result){
       						if(result != null  && result != ""){
       							var obj = JSON.parse(result); 
       							$("#startTime").text(obj.remark);
       						}else{
       						    $("#startTime").text("");
       						}
       					});
       				}
			});*/
			$('#vehicleDefAdd').combobox({
				    url:'${basePath}sim/customerPay_vehicleInfo.action',
				    valueField:'simNo',  
       				textField:'vehicleDef',
       				onChange:function(data){
       					$("#simNo").val(data);
       					$.post("${basePath}sim/customerPay_customerSimById.action",{simNo:data},function(result){
       						console.log("result ",result);
       						if(result != null  && result != "" && result != "null"){
       							var obj = JSON.parse(result); 
       							$("#startTime").text(obj.remark);
       						}else{
       						    $("#startTime").text("");
       						}
       					});
       				}
			});
			
          });
        }
      };
      customerPay.init();

    })(jQuery);
    
        
//导出Excel
function downExcelVehicle(){
	var simNo = $('#sim_no').combobox('getValue');
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
	
	var payAmount = $("#pay_amount").val();
	if(payAmount == "" || payAmount == null){
		payAmount = "";
	}
	
	var createTime = $("#create_time").val();
	if(createTime == "" || createTime == null){
		createTime = "";
	}
	
	window.location.href=encodeURI(encodeURI("${basePath}sim/customerPay_exportToExcelVehicle.action?simNo="+simNo+
							"&startTime="+startTime+
							"&endTime="+endTime+
							"&dealerId="+$('#dealerId_search').combobox('getValues').join(',')+
							"&vehicleDef="+$("#vehicleDef").closest("td").find("input[type='hidden']").val()+
						    "&payAmount="+payAmount+
							"&createTime="+createTime));
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
	  filename: "客户批量交费失败记录"
	});
});
  </script>
