<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- 输入密码 --begin -->
		<div id="dlg_inputpwd"  class="easyui-dialog"
			data-options="iconCls:'icon-save',closed:true,modal:true,title:'密码确认'"
			buttons="#btns_inputpwd"
			style="padding: 5px; width: 300px; height: 150px;">
			<form id="inputpwd_form" method="POST">
			    <input id="commandType" type="hidden">
				<table class="tableForm"
					style="widht: 100%; height: 80%; font-size: 12px;">
					<tr>
						<td widht="100px">
							请输入登陆密码：
						</td>
						<td>
							<input type="password" id="password" name="password"
								class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
				</table>
			</form>
			
			<div id="btns_inputpwd">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="inputpwd()">确定</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_inputpwd').dialog('close')">取消</a>
	</div>
		</div>
		<!-- 输入密码 --end -->