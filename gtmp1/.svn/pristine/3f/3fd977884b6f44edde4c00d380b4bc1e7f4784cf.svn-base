<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/vehicle_type.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:vehicle_typeMainResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="vehicle_type_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}vehicle/vehicleType_search.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'机械代号配置管理'">
             <thead>  
            <tr>  
                <th data-options="field:'modelId',width:130,align:'center'">机械编号</th>  
                <th data-options="field:'modelName',width:100,align:'center'">机械类型名称</th>
                <th data-options="field:'vehicleCode',width:100,align:'center'">机械代号</th>
                <th data-options="field:'vehicleArg',width:100,align:'center'">机械配置</th>
                <th data-options="field:'listNo',width:100,align:'center'">排序号</th> 
                <th data-options="field:'isValid',width:100,align:'center',formatter:function(val,row,index){if(val==0){ return '有效';}else{ return '无效';}}">是否有效</th>
                <th data-options="field:'indexId',width:150,align:'center',formatter:operate">操作</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">机械代号:</td>
		        <td><input id="vehicleCode" style="width: 150px;"></td>
		         <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryvehicle_type()">查询</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openDlg4vehicle_typeOperate()">新增</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
		    <!-- 新增或者修改弹出框 begin-->
		 <div id="dlg_vehicle_type_operate" class="easyui-dialog" title="机械类型" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 300px; height: 240px;" closed="true" buttons="#dlg_vehicle_type_operate_btns">
        <form id="vehicle_type_operate_form" metdod="post" tdeme="simple"
			 action="${basePath}vehicle/vehicleType_seachEdit.action" >
		<input id="indexId" name="indexId" type="hidden" />
 <table cellpadding="4" cellspacing="0" style="font-size:12px;width: 100%;">
            <tr>    
		          <td align="right">机械型号:</td>
                  <td align="left">
                  <input id="modelId" name="modelId" class="easyui-combobox"
							style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
                   <span style="color:red">*</span>
                 </td>
            </tr>
                        <tr>
                <td align="right">机械代号:</td>
                <td align="left">
                <input id="vehicleCode" name="vehicleCode"  
		                 data-options="required:true"   style="width:150px"/> 
		                 <span style="color:red">*</span>
                </td>
                 
            </tr>
                        <tr>
                <td align="right">机械配置:</td>
                <td align="left">
                <input id="vehicleArg" name="vehicleArg"  
		                 data-options="required:true"   style="width:150px"/> 
		                 <span style="color:red">*</span>
                </td>
                 
            </tr>
            <tr>
                <td align="right">排序号:</td>
                <td align="left">
                <input id="listNo" name="listNo"  
		                class="easyui-numberbox" data-options="required:true"   style="width:150px"/> 
		                 <span style="color:red">*</span>
                </td>
                 
            </tr>
             <tr>
                <td align="right">是否有效:</td>
                <td align="left">
                 <select id="isValid" class="easyui-combobox" name="isValid" style="width:150px;">  
					<option value="0">有效</option>  
					<option value="1">无效</option>  
				    </select>
                 
            </tr>
        </table>
		</form>
       </div>  
		<div id="dlg_vehicle_type_operate_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savevehicle_type()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_vehicle_type_operate').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增或者修改弹出框 end-->
		    
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#vehicle_type_datagrid').datagrid('resize', {  
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
