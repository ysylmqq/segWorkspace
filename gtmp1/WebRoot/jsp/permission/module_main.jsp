<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/module.js"></script>	

<div id='loading_module'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true"
	style="width: 100%; height: 100%;">
	<div id="module_west_panel"
		data-options="region:'west',split:true,title:'模块管理',doSize:true"
		style="width: 300px;">
		 <div id="tb_module_tree" >
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-refresh',plain:true" onclick="refreshTree()">刷新</a>
			<!-- 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="openModuleAddoWin()">添加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeTreeNode()">删除</a>
			 -->
		</div>
		  <ul id="module_tree" class="easyui-tree"
					data-options="url:'${basePath}permi/module_search.action',onClick:clickTreeNode" ></ul>
	
		</div>
	<div data-options="region:'center',doSize:true,title:'详细信息'">
			  <form id="form_module">
	         	<table style=" font-size: 12px;">
		      <input type="hidden" id="moduleId" name="moduleId"  />
  				<input type="hidden" id="parentId" name="parentId"  />
             <tr>
                <td align="right" >模块名:</td>
                <td align="left" ><input id="moduleName" name="moduleName"  type="text"  data-options="required:true"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >url:</td>
                <td align="left" ><input id="url" name="url" type="text"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >模块参数1:</td>
                <td align="left" ><input id="control1" name="control1" type="text" value="${control1}"  /></td>
                <td align="left"></td>
            </tr>
   			<tr>
                <td align="right" >模块参数2:</td>
                <td align="left" ><input id="control2" name="control2" type="text" value="${control2}"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >模块参数3:</td>
                <td align="left" ><input id="control3" name="control3" type="text" value="${control3}"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >模块类型:</td>
                <td align="left" ><input id="moduleType" name="moduleType" type="text" class="easyui-numberbox"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >排序:</td>
                <td align="left" ><input id="listNo" name="listNo" type="text" class="easyui-numberbox"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
            <td colspan="3" ><br></td>
            </tr>
             <tr>
               <td ></td>
               <td colspan="2" >
               <!-- 
                  <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>  
                  --></td>
            </tr>
		      </table>
		        
	  </form>
	</div>
	  <!-- 新增弹出框 begin-->
		 <div id="dlg_module" class="easyui-dialog" title="新增模块" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 300px; height: 280px;" closed="true" buttons="#dlg_module_add_btns">
        <form id="form_module_add" metdod="post" tdeme="simple">
	         	<table style=" font-size: 12px;">
		        <input type="hidden" id="moduleId" name="moduleId"  />
  				<input type="hidden" id="parentId" name="parentId"  />
  				<input type="hidden" id="moduleType" name="moduleType" value="1" />
             <tr>
                <td align="right" >模块名:</td>
                <td align="left" ><input id="moduleName" name="moduleName"  type="text"  data-options="required:true"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >url:</td>
                <td align="left" ><input id="url" name="url" type="text"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >模块参数1:</td>
                <td align="left" ><input id="control1" name="control1" type="text" value="${control1}"  /></td>
                <td align="left"></td>
            </tr>
   			<tr>
                <td align="right" >模块参数2:</td>
                <td align="left" ><input id="control2" name="control2" type="text" value="${control2}"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >模块参数3:</td>
                <td align="left" ><input id="control3" name="control3" type="text" value="${control3}"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >排序:</td>
                <td align="left" ><input id="listNo" name="listNo" type="text" class="easyui-numberbox"  /></td>
                <td align="left"></td>
            </tr>
		      </table>
		</form>
       </div>  
		<div id="dlg_module_add_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAdd()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_module').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增弹出框 end-->
</div>

<script type="text/javascript">
    function show(){
        $("#loading_module").fadeOut("fast", function(){
            $(this).remove();
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script>
