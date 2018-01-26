<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/sysparam.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:sysparamMainResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="sysparam_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}sys/sysparam_search.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'系统参数设置'">
             <thead>  
            <tr>  
                <th data-options="field:'code',width:130,align:'center'">参数编码</th>  
                <th data-options="field:'name',width:100,align:'center'">参数名称</th>
                <th data-options="field:'value',width:100,align:'center'">参数值</th> 
                <th data-options="field:'unitId',width:150,align:'center',formatter:operate">操作</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">参数编码:</td>
		        <td><input id="code_search" style="width: 150px;"></td>
		        
		        <td align="right">参数名称:</td>
		        <td><input id="name_search" style="width: 150px;"></td>
		        
		        <td align="right">参数值:</td>
		        <td><input id="value_search" style="width: 150px;"></td>
			    <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="querysysparam()">查询</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openDlg4sysparamOperate()">新增</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
		    <!-- 新增或者修改弹出框 begin-->
		 <div id="dlg_sysparam_operate" class="easyui-dialog" title="系统参数设置" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 300px; height: 240px;" closed="true" buttons="#dlg_sysparam_operate_btns">
        <form id="sysparam_operate_form" metdod="post" tdeme="simple"
			 action="${basePath}sys/sysparam_saveOrUpdate.action" >
			 <input id="paramId" name="paramId" type="hidden" />
 <table cellpadding="4" cellspacing="0" style="font-size:12px;width: 100%;">
            <tr>
		        <td align="right">参数编码:</td>
		        <td align="left"><input id="code" name="code"  
		        class="easyui-validatebox" data-options="required:true"
		                   style="width:150px" validType="length[0,10]" /> 
		                  <span style="color:red">*</span>
		        </td>
		      </tr>
            <tr>    
		          <td align="right">参数名称:</td>
                  <td align="left">
                  <input id="name" name="name" type="text"   class="easyui-validatebox" data-options="required:true" validType="length[0,20]" />
                   <!--  <input id="sysparamSn" name="sysparamSn" type="text"   class="easyui-validatebox" data-options="required:true"  onblur="checksysparamSnOrSimNo(1)"/>-->
                   <span style="color:red">*</span>
                 </td>
            </tr>
            <tr>
                <td align="right">参数值:</td>
                <td align="left">
                <input id="value" name="value"  
		                class="easyui-validatebox" data-options="required:true" validType="length[0,20]"  style="width:150px"/> 
		                 <span style="color:red">*</span>
                </td>
                 
            </tr>
        </table>
		</form>
       </div>  
		<div id="dlg_sysparam_operate_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savesysparam()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_sysparam_operate').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增或者修改弹出框 end-->
		    
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#sysparam_datagrid').datagrid('resize', {  
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
