<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type="text/javascript" src="${basePath}js/role.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:roleMainResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="role_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}permi/role_search.action',
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'角色管理'">
             <thead>  
            <tr>  
                <th data-options="field:'roleName',width:130,align:'center'">角色名称</th>  
                <th data-options="field:'stamp',width:100,align:'center'">创建时间</th>
                <th data-options="field:'roleId',width:150,align:'center',formatter:operate">操作</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">角色名称:</td>
		        <td><input id="roleName_search" style="width: 150px;"></td>
			    <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryrole()">查询</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openDlg4roleOperate()">新增</a>
			     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-sum" onclick="assignModeles()">分配模块</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
		    <!-- 新增或者修改弹出框 begin-->
		 <div id="dlg_role_operate" class="easyui-dialog" title="角色新增" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 350px; height: 240px;" closed="true" buttons="#dlg_role_operate_btns">
        <form id="role_operate_form" metdod="post" tdeme="simple"
			 action="${basePath}role/role_saveOrUpdate.action" >
			 <input id="roleId" name="roleId" type="hidden" />
			  <input id="isValid" name="isValid" type="hidden"  value="0"/>
 <table cellpadding="4" cellspacing="0" style="font-size:12px;width: 100%;">
            <tr>
		        <td align="right">角色名称:</td>
		        <td align="left"><input id="roleName" name="roleName" class="easyui-validatebox" data-options="required:true" 
		                  style="width:150px"  /> 
		                  <span style="color:red">*</span>
		        </td>
		       </tr>
             <tr>
                <td align="right">创建人:</td>
                <td align="left">
                <input id="username" disabled="disabled" name="username" type="text" value="${userName}" style="width:150px"></input>
                </td>
            </tr>
        </table>
		</form>
       </div>  
		<div id="dlg_role_operate_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverole()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_role_operate').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增或者修改弹出框 end-->
		    
		       <!-- 分配模块弹出框 begin-->
		 <div id="dlg_role_module" class="easyui-dialog" title="模块分配" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 400px; height: 440px;" closed="true" buttons="#dlg_role_module_btns">
		 <ul id="module_tree" 
					data-options="url:'${basePath}permi/module_search.action',checkbox:true,cascadeCheck:false" ></ul>
	
       </div>  
		<div id="dlg_role_module_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveModules()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_role_module').dialog('close')">取消</a>  
	</div>  
		    <!-- 分配模块弹出框 end-->
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#role_datagrid').datagrid('resize', {  
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
