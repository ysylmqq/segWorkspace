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
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:vehicleResize"  style="overflow:hidden;">
 
    <!-- 表格 begin -->
    <table id="customer_pay_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}sim/companySim_allProfit.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'机械收益统计'">
             <thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:150,align:'center'">整机编号</th>
   		        <th data-options="field:'distributor',width:240,align:'center'">经销商</th>
                <th data-options="field:'modelName',width:150,align:'center'">机械型号</th>
   		        <th data-options="field:'unitSn',width:150,align:'center'">终端序列号</th>
		        <th data-options="field:'simNo',width:150,align:'center'">SIM卡号</th>
		        <th data-options="field:'isServer',width:180,align:'center',formatter:statusTips">服务状态</th>
		        <th data-options="field:'openTime',width:150,align:'center',formatter:dateFormat">服务开始日期</th>
		        <th data-options="field:'endTime',width:150,align:'center',formatter:dateFormat">服务结束日期</th>
		        <th data-options="field:'status',width:100,align:'center',formatter:tips">公司开卡情况</th>
		        <th data-options="field:'custStatus',width:100,align:'center',formatter:custTips">客户开卡情况</th>
		        <th data-options="field:'stopTime',width:150,align:'center',formatter:dateFormat">注销时间</th>
		        <th data-options="field:'stopReason',width:150,align:'center'">注销原因</th>
		        <th data-options="field:'remark',width:150,align:'center'">备注</th>
		        <th data-options="field:'userName',width:150,align:'center'">操作人</th>
		        <th data-options="field:'createTime',width:150,align:'center',formatter:dateFormat">创建日期</th>
		         <th data-options="field:'customerSimAllPay',width:150,align:'center'">客户SIM缴费总额</th>
		         <th data-options="field:'customerStopAllFee',width:150,align:'center'">客户停机保号总额</th>
		         <th data-options="field:'customerAllPay',width:150,align:'center'">客户总缴费</th>
		         <th data-options="field:'payAmount',width:150,align:'center'">SIM成本</th>
 		         <th data-options="field:'operId',width:150,align:'center',formatter:companySIMAllFee">公司SIM缴费总额</th>
 		         <th data-options="field:'companyAllPay',width:150,align:'center'">公司总缴费</th>
		         <th data-options="field:'allProfit',width:150,align:'center'">收益</th>
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
          <!-- <input id="vehicleDef"  name="vehicleDef" type="text" style="width:150px"></input>
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
					label: '公司SIM开通',
					value: '0'
				},{
					label: '公司SIM未开通',
					value: '2'
				},{
					label: '公司SIM注销',
					value: '1'
				},{
					label: '客户SIM开通',
					value: '3'
				},{
					label: '客户SIM未开通',
					value: '5'
				},{
					label: '客户SIM停机保号',
					value: '4'
				}]" />
        </td>
      </tr>
      <tr>
        <td colspan="11" align="left" style="padding-left: 20px;width:100%">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="queryBtn">查询</a>
         <!--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="addBtn">新增</a>
           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-batch_import" onclick="javascript:$('#customerPayImpport').dialog('open')">批量导入</a>
          --><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcelVehicle();">导出</a></td>
      </tr>
    </table>
  </div>
		    <!-- 查询条件 end -->
		    
 
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
function companySIMAllFee(val,row){
	return row.companyAllPay - row.payAmount;
}
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
 
 function custTips(val,row){
	if(typeof val == "number"){
		if(val =='1') 
	    	return '停机保号';
	    else 
	    	return '开通';
	}else{
			return '未开通';
	}
}
function tips(val,row){
	if(typeof val == "number"){
		if(val =='1') 
	    	return '注销';
	    else 
	    	return '开通';
	}else{
			return '未开通';
	}
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
              vehicleDef:$("#vehicleDef").closest("td").find("input[type='hidden']").val(),
              dealerId:$('#dealerId_search').combobox('getValues').join(',')
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
	
	var vehicleDef = $("#vehicleDef").closest("td").find("input[type='hidden']").val();
	if(vehicleDef == "" || vehicleDef == null){
		vehicleDef = "";
	}
	
	window.location.href=encodeURI(encodeURI("${basePath}sim/companySim_exportToExcelVehicleProfit.action?simNo="+simNo+
							"&openTime="+startTime+
							"&endTime="+endTime+
							"&vehicleDef="+vehicleDef+
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
    </script>
