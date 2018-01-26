<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- <script type="text/javascript" src="/js/autoComplete/jquery.js"></script> -->
	<script type='text/javascript' src='js/autoComplete/jquery.autocomplete.js'></script>
	<script type='text/javascript' src='js/autoComplete/localdata.js'></script>
	<link rel="stylesheet" type="text/css" href="js/autoComplete/jquery.autocomplete.css" />
	<script type="text/javascript" src="${basePath}js/vehicle_sale.js"></script>
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
    <table id="vehicle_sale_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}run/run_getVehicleSaleByPage.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'机械销售信息录入'">
             <thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:100,align:'center'">整机编号</th>  
                <th data-options="field:'dealerName',width:100,align:'center'">经销商</th>
                <th data-options="field:'ownerName',width:100,align:'center'">机主名称</th>
                <th data-options="field:'ownerPhone',width:100,align:'center'">机主手机</th>
                <!-- <th data-options="field:'vehicleId',width:180,align:'left',formatter:operate">操作</th> -->
            </tr>  
        </thead>
	 </table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">整机编号:</td>
		        <td>
		        <input id="vehicleDef_search" style="width: 150px;">
			     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="querySale()">查询</a>
			  
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openDlg4VehicleOperate()">新增</a>
			    <a href="#"  class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:importExcel();">导入</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    
		    <!-- 新增或者修改弹出框 begin-->
		 <div id="dlg_vehicle_sale_operate" class="easyui-dialog" title="机械销售信息" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 400px; height: 260px;" closed="true" buttons="#dlg_vehicle_sale_operate_btns">
        <form id="vehicle_sale_operate_form" metdod="post" tdeme="simple" action="${basePatd}vehicle/vehicle_sale_saveOrUpdate.action" >
			 <input id="id" name="id" type="hidden" />
 <table cellpadding="4" cellspacing="0" style="font-size:12px;width: 100%;">
            <tr>
		        <td >整机编号:</td>
		        <td align="left">
		            <input id="vehicleDef" name="vehicleDef" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'run/run_getVehicle4sale.action?vehicleDef=${vehicleDef}',
								valueField:'VEHICLE_DEF',  
       							textField:'VEHICLE_DEF',required:true" 
       							
				/>
		           	<span style="color:red">*</span>
		        </td>
		       
		         </tr>
            <tr>
		           <td >
					经销商:
				</td>
				<td>
				  <input id="dealerId" name="dealerId" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'run/run_getDealers.action',
								valueField:'id',  
       							textField:'name',required:true" 
				/>
				<span style="color:red">*</span>
            </tr>
            <tr > <td >
					机主:
				</td>
				<td>
		        <input id="ownerId" name="ownerId" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'run/run_getOwner4sale.action',
								valueField:'OWNER_ID',  
       							textField:'OWNER_NAME',required:true" 
				/>
				<span style="color:red">*</span>
                </td>
             </tr>
        </table>
		</form>
       </div>  
		<div id="dlg_vehicle_sale_operate_btns">  
	    <a href="#" id="sava" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveVehicle()">保存</a>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_vehicle_sale_operate').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增或者修改弹出框 end-->
		    
		    <!--销售信息导入--begin-->
<div id="dlg_saleInfoImport" class="easyui-dialog" title="销售信息导入"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 400px; height: 100px;" closed="true"
	buttons="#btns_saleInfoImport">
	<form id="frm_saleInfoImport" name="frm_saleInfoImport" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
			    <td><input type="file" title="请选择文件" name="workInfoFile" id="workInfoFile"/></td>                	
		    </tr>
		</table>
	</form>
	<div id="btns_saleInfoImport">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveSaleInfo()">导入</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_saleInfoImport').dialog('close')">关闭</a>
	</div>
</div>
<!--销售信息导入--end-->
</div>
</div>
<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#vehicle_sale_datagrid').datagrid('resize', {  
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