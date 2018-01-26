<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/depart.js"></script>
<div id='loading_depart'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true"
	style="width: 100%; height: 100%;">
	<div id="depart_west_panel"
		data-options="region:'west',split:true,title:'机构管理',doSize:true"
		style="width: 300px;">
		 <div id="tb_depart_tree" >
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-refresh',plain:true" onclick="refreshTree()">刷新</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="opendepartAddoWin()">添加</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeTreeNode()">删除</a>
		</div>
		  <ul id="depart_tree" class="easyui-tree"
					data-options="url:'${basePath}permi/depart_search.action?departId=4',onClick:clickTreeNode" ></ul>
	
		</div>
	<div data-options="region:'center',doSize:true,title:'详细信息'">
			  <form id="form_depart">
	         <table style=" font-size: 12px;">
		        <input type="hidden" id="departId" name="departId"  />
  				<input type="hidden" id="pid" name="pid"  />
  				<input type="hidden" id="departType" name="departType" value="1" />
             <tr>
                <td align="right" >机构名称:</td>
                <td align="left" ><input id="departName" name="departName"  type="text" class="easyui-validatebox" data-options="required:true"  />
                <span style="color:red">*</span>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >地址:</td>
                <td align="left" ><input id="address" name="address" type="text"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >电话:</td>
                <td align="left" ><input id="tel" name="tel" type="text" value="${control1}"  /></td>
                <td align="left"></td>
            </tr>
   			<tr>
                <td align="right" >fax:</td>
                <td align="left" ><input id="fax" name="fax" type="text" value="${control2}"  /></td>
                <td align="left"></td>
            </tr>
             <tr>
               <td ></td>
               <td colspan="2" >
                  <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>  
                  </td>
            </tr>
		      </table>
	  </form>
	</div>
	  <!-- 新增弹出框 begin-->
		 <div id="dlg_depart" class="easyui-dialog" title="新增机构" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 300px; height: 220px;" closed="true" buttons="#dlg_depart_add_btns">
        <form id="form_depart_add" metdod="post" theme="simple">
	         	<table style=" font-size: 12px;">
		        <input type="hidden" id="departId" name="departId"  />
  				<input type="hidden" id="pid" name="pid"  />
  				<input type="hidden" id="departType" name="departType" value="1" />
             <tr>
                <td align="right" >机构名称:</td>
                <td align="left" ><input id="departName" name="departName"  type="text"  class="easyui-validatebox" data-options="required:true"  />
                <span style="color:red">*</span>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >地址:</td>
                <td align="left" ><input id="address" name="address" type="text"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" >电话:</td>
                <td align="left" ><input id="tel" name="tel" type="text" value="${control1}"  /></td>
                <td align="left"></td>
            </tr>
   			<tr>
                <td align="right" >fax:</td>
                <td align="left" ><input id="fax" name="fax" type="text" value="${control2}"  /></td>
                <td align="left"></td>
            </tr>
		      </table>
		</form>
       </div>  
		<div id="dlg_depart_add_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAdd()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_depart').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增弹出框 end-->
</div>

<script type="text/javascript">
    function show(){
        $("#loading_depart").fadeOut("fast", function(){
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
