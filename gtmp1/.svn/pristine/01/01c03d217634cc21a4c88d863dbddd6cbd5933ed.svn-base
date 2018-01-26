<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/user.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false,onResize:userMainResize"  style="overflow:hidden;">
   	
	<div class="easyui-tabs" style="">
   
	<div title="玉柴用户" data-options="" style="overflow:auto;">
	    <!-- 表格 begin -->
	    <table id="user_datagrid" toolbar="#toolbar" class="easyui-datagrid"
				style="width: auto; height: auto;" rownumbers="true" pagination="true" 
				data-options="url:'${basePath}permi/user_search.action',
	            fitColumns:true,singleSelect:true">
	             <thead>  
	            <tr> 
	           		<th data-options="field:'loginName',width:130,align:'left'">登录名称</th>
	                <th data-options="field:'userName',width:130,align:'left'">用户名称</th>  
	                 <th data-options="field:'sex',width:50,align:'center'">性别</th>
	                  <th data-options="field:'tel',width:100,align:'center'">电话</th>
	                   <th data-options="field:'email',width:100,align:'left'">邮箱地址</th>
	                    <th data-options="field:'departName',width:150,align:'left'">所属机构</th>
	                    <th data-options="field:'isValid',width:80,align:'center',formatter:function(val,row,index){if(val==0){ return '有效';}else{ return '<font color=\'red\'>无效</font>';}}">是否有效</th>
	                <th data-options="field:'stamp',width:100,align:'center'">创建时间</th>
	                <th data-options="field:'userId',width:150,align:'center',formatter:operate">操作</th>
	            </tr>  
	        </thead>
			</table>
			<!-- 表格 end -->
			
			<!-- 查询条件 begin -->
			<div id="toolbar" style="padding: 5px; height: auto;">
			
			    <table style=" font-size: 12px;">
			      <tr>
			        <td align="right">用户名称:</td>
			        <td><input id="userName_search" style="width: 150px;"></td>
			        
			        <td align="right">是否有效:</td>
			        <td><select id="isValid_search" class="easyui-combobox" name="isValid_search" style="width:120px;">  
						     <option	></option>  
						    <option value="0">有效</option>  
						    <option value="1">无效</option>  
						</select>  
					</td>
				  
				    <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryuser()">查询</a>
				    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openDlg4userOperate()">新增</a>
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-sum" onclick="assignRoles()">分配角色</a>
				     <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
				    </td>
			      </tr>
			    </table> 
			    </div>
			    <!-- 查询条件 end -->
		    </div>
		    <div title="机主用户" data-options="" style="overflow:auto;">
		    	<!-- 表格 begin -->
	    		<table id="owner_datagrid" toolbar="#owner_toolbar" class="easyui-datagrid"
				style="width: auto; height: auto;" rownumbers="true" pagination="true" 
				data-options="url:'${basePath}permi/ownerUser_search.action',fitColumns:true,singleSelect:true">
	            	<thead>  
	           			<tr> 
	           				<th data-options="field:'loginName',width:100,align:'center'">登录名称</th>
	                		<th data-options="field:'ownerName',width:100,align:'center'">用户名称</th>  
	                 		<th data-options="field:'sex',width:50,align:'center'">性别</th>
	                  		<th data-options="field:'ownerPhoneNumber',width:100,align:'center'">电话</th>
	                    	<th data-options="field:'isValid',width:50,align:'center',formatter:function(val,row,index){if(val==0){ return '有效';}else{ return '<font color=\'red\'>无效</font>';}}">是否有效</th>
			                <th data-options="field:'stamp',width:100,align:'center'">创建时间</th>
			                <th data-options="field:'ownerId',width:150,align:'center',formatter:ownerOperate">操作</th>
	            		</tr>  
	        		</thead>
				</table>
				<!-- 表格 end -->
				<!-- 查询条件 begin -->
				<div id="owner_toolbar" style="padding: 5px; height: auto;">
					<table style="font-size: 12px;">
						<tr>
							<td align="right">
								用户名称:
							</td>
							<td>
								<input id="ownerName_search" style="width: 150px;">
							</td>
							<td>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryOwner()">查询</a>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addOwner()">新增</a>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-sum" onclick="assignOwnerRoles()">分配角色</a>
								<a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel2();">导出</a>
							</td>
						</tr>
					</table>
				</div>
				<!-- 查询条件 end -->
		    </div>
		</div>
	</div>
</div>
		<!-- 新增或者修改弹出框 begin-->
		<div id="dlg_user_operate" class="easyui-dialog" title="用户新增" data-options="iconCls:'icon-save',modal: true"
		style="padding: 5px; width: 360px; height: 345px;" closed="true" buttons="#dlg_user_operate_btns">
        <form id="user_operate_form" metdod="post" theme="simple"
			 action="${basePath}user/user_saveOrUpdate.action" >
			 <input id="userId" name="userId" type="hidden" />
 <table cellpadding="4" cellspacing="0" style="font-size:12px;width: 100%;">
           <tr>
		        <td align="right">登录名称:</td>
		        <td align="left"><input id="loginName" name="loginName" class="easyui-validatebox" data-options="required:true" 
		                  style="width:150px"  /> 
		                  <span style="color:red">*</span>
		                  <input id="password" name="password" type="hidden" value="123456" /> 
		        </td>
		    </tr>
            <tr>
		        <td align="right">用户名称:</td>
		        <td align="left"><input id="userName" name="userName" class="easyui-validatebox" data-options="required:true" 
		                  style="width:150px"  /> 
		                  <span style="color:red">*</span>
		        </td>
		       </tr>
		     <tr>
		        <td align="right">性别:</td>
		        <td align="left"><select id="sex" class="easyui-combobox" name="sex" style="width:120px;">  
					    <option value="男">男</option>  
					    <option value="女">女</option>  
					</select>  
		        </td>
		       </tr>
		       
		     <tr>
		        <td align="right">联系电话:</td>
		        <td align="left"><input id="tel" name="tel" class="easyui-validatebox" data-options=""  validType="telOrMoible['格式是固定电话或者手机号码!']"
		                  style="width:150px"  /> 
		        </td>
		       </tr>
		       
		     <tr>
		        <td align="right">邮箱地址:</td>
		        <td align="left"><input id="email" name="email" class="easyui-validatebox" data-options="validType:'email'" 
		                  style="width:150px"  /> 
		        </td>
		       </tr>
             <tr>
                <td align="right">所属机构:</td>
                <td align="left">
                <select id="departId" name="departId" style="width:150px;"  
        			data-options="required:true"></select> 
        			<span style="color:red">*</span>
                </td>
            </tr>
            <tr>
              <td align="right">是否有效:</td>
		        <td><select id="isValid" class="easyui-combobox" name="isValid" style="width:120px;">  
					    <option value="0">有效</option>  
					    <option value="1">无效</option>  
					</select>  
				</td>
            </tr>
             <tr>
              <td colspan="2">
              <font color="red">
                 注意:用户初始密码为123456!
                 </font>
				</td>
            </tr>
        </table>
		</form>
       </div>  
		<div id="dlg_user_operate_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveuser()">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_user_operate').dialog('close')">取消</a>  
	</div>  
		    <!-- 新增或者修改弹出框 end-->
		    
<!-- 机主用户编辑框 begin -->
<div id="dlg_owner_operate" class="easyui-dialog" title="新增机主用户" data-options="iconCls:'icon-save',modal:true" style="padding: 5px; width: 360px; height: 300px;" closed="true" buttons="#dlg_owner_operate_btns">
    <form id="owner_operate_form" metdod="post" action="${basePath}permi/ownerUser_saveOrUpdate.action">
        <input id="ownerId" name="ownerId" type="hidden" value="" />
        <table cellpadding="4" cellspacing="0" style="font-size:12px;width: 100%;">
            <tr>
                <td align="right">手机号码：</td>
                <td align="left">
                    <input id="ownerPhoneNumber" name="ownerPhoneNumber" class="easyui-validatebox" data-options="required:true" validType="mobile['格式是手机号码!']" style="width:150px" />
                    <span style="color:red">*</span>
                </td>
            </tr>
            <tr>
                <td align="right">机主名称：</td>
                <td align="left">
                    <input id="ownerName" name="ownerName" class="easyui-validatebox" data-options="required:true" style="width:150px" />
                    <span style="color:red">*</span>
                </td>
            </tr>
            <tr>
                <td align="right">性别：</td>
                <td align="left">
                    <select id="sex" name="sex" class="easyui-combobox" style="width:150px;">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <font color="red">注意：</font>
                </td>
                <td>
                	<font color="red">
                		登录名为手机号码！<br/>
                		用户初始密码为123456！
                	</font>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg_owner_operate_btns">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOwner()">保存</a> 
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_owner_operate').dialog('close')">取消</a> 
</div>
<!-- 机主用户编辑框 end -->
		    
		       <!-- 分配模块弹出框 begin-->
		 <div id="dlg_user_role" class="easyui-dialog"  title="角色分配" data-options="iconCls:'icon-save',modal: true"
		style="padding: 0px; width: 600px; height: 440px;" closed="true" buttons="#dlg_user_role_btns">
		 <!-- 表格 begin -->
		    <table id="role_datagrid"  class="easyui-datagrid" toolbar="#toolbar_role"
					style="width: auto; height: auto;" rownumbers="true" pagination="true" 
					data-options="url:'${basePath}permi/role_search.action',
		            fitColumns:true,singleSelect:false,rownumbers:true,pagination:true,idField:'roleId'">
		             <thead>  
		            <tr>  
		                <th data-options="field:'ck',width:130,align:'center',checkbox:true"></th>
		                <th data-options="field:'roleName',width:130,align:'center'">角色名称</th>  
		                <th data-options="field:'stamp',width:100,align:'center'">创建时间</th>
		            </tr>  
		        </thead>
				</table>
				
				<!-- 查询条件 begin -->
		<div id="toolbar_role" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">角色名称:</td>
		        <td><input id="roleName_search" style="width: 150px;"></td>
			    <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryrole()">查询</a>
			     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-sum" onclick="saveRoles()">确定分配</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		<!-- 表格 end -->
       </div>  
		    <!-- 分配角色弹出框 end-->
<!-- 机主用户角色分配弹出框 begin-->
<div id="dlg_owner_role" class="easyui-dialog" title="机主用户角色分配" data-options="iconCls:'icon-save',modal:true,closed:true" style="padding: 0px; width: 600px; height: 440px;" buttons="#dlg_owner_role_btns">
    <!-- 表格 begin -->
    <table id="owner_role_datagrid" class="easyui-datagrid" toolbar="#owner_role_toolbar" style="width: auto; height: auto;" rownumbers="true" pagination="true" data-options="url:'${basePath}permi/role_search.action',
		fit:true,fitColumns:true,singleSelect:false,rownumbers:true,pagination:true,idField:'roleId'">
        <thead>
            <tr>
                <th data-options="field:'ck',width:130,align:'center',checkbox:true"></th>
                <th data-options="field:'roleName',width:130,align:'center'">角色名称</th>
                <th data-options="field:'stamp',width:100,align:'center'">创建时间</th>
            </tr>
        </thead>
    </table>

    <!-- 查询条件 begin -->
    <div id="owner_role_toolbar" style="padding: 5px; height: auto;">
        <table style=" font-size: 12px;">
            <tr>
                <td align="right">角色名称:</td>
                <td>
                    <input id="owner_roleName_search" style="width: 150px;">
                </td>
                <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryOwnerRole()">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-sum" onclick="saveOwnerRoles()">确定分配</a>
                </td>
            </tr>
        </table>
    </div>
    <!-- 查询条件 end -->
    <!-- 表格 end -->
</div>
<!-- 机主用户角色分配弹出框 end-->
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
            $('#user_datagrid').datagrid('resize', {  
             	width : wid-2,
            	height : hei-30
            });
            $('#owner_datagrid').datagrid('resize',{
            	width : wid-2,
            	height : hei-30
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
