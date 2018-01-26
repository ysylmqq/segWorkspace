<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/unit_type.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:unit_typeMainResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="unit_type_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}sys/dicUnitType_search.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'终端类型管理'">
             <thead>  
            <tr>  
                <th data-options="field:'unitTypeSn',width:130,align:'center'">终端类型编号</th>  
                <th data-options="field:'unitType',width:100,align:'center'">终端类型名称</th>
                <th data-options="field:'supperierName',width:100,align:'center'">终端供应商</th>
                <th data-options="field:'isValid',width:100,align:'center',formatter:function(val,row,index){if(val==0){ return '有效';}else{ return '无效';}}">是否有效</th> 
                <th data-options="field:'unitTypeSn1',width:150,align:'center',formatter:operate">操作</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">终端类型编号:</td>
		        <td><input id="unitTypeSn_search" style="width: 150px;"></td>
		        
		        <td align="right">终端类型名称:</td>
		        <td><input id="unitType_search" style="width: 150px;"></td>
		        
			    <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryunit_type()">查询</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openDlg4unit_typeOperate()">新增</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
		    <!-- 新增或者修改弹出框 begin-->
		 <div id="dlg_unit_type_operate" class="easyui-dialog" title="终端类型" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 300px; height: 240px;" closed="true" buttons="#dlg_unit_type_operate_btns">
        <form id="unit_type_operate_form" metdod="post" tdeme="simple"
			 action="${basePath}sys/unit_type_saveOrUpdate.action" >
<input id="updateId" name="updateId" type="hidden" />
 <table cellpadding="4" cellspacing="0" style="font-size:12px;width: 100%;">
            <tr>
		        <td align="right">终端类型编号:</td>
		        <td align="left"><input id="unitTypeSn" name="unitTypeSn"  
		        class="easyui-validatebox" data-options="required:true"
		                   style="width:150px" validType="length[0,10]" /> 
		                  <span style="color:red">*</span>
		        </td>
		      </tr>
            <tr>    
		          <td align="right">终端类型名称:</td>
                  <td align="left">
                  <input id="unitType" name="unitType" type="text"   class="easyui-validatebox" data-options="required:true" validType="length[0,20]" />
                   <!--  <input id="unitTypeSn" name="unitTypeSn" type="text"   class="easyui-validatebox" data-options="required:true"  onblur="checkunitTypeSnOrSimNo(1)"/>-->
                   <span style="color:red">*</span>
                 </td>
            </tr>
            <tr>
               <td align="right">供应商编号:</td>
		        <td align="left"><input id="supperierSn" name="supperierSn"  
		                 data-options="url:'${basePatd}sys/dicSupperier_getList.action?isValid=0',valueField:'supperierSn',textField:'supperierName'"   style="width:150px"  /> 
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
		<div id="dlg_unit_type_operate_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveunit_type()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_unit_type_operate').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增或者修改弹出框 end-->
		    
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#unit_type_datagrid').datagrid('resize', {  
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
