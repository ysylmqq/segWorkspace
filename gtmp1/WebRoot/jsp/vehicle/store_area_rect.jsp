<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--画完矩形框后弹出的窗口-->

<!--画完矩形框后弹出的窗口--begin-->
<div id="dlg_area_rect"  title="参数设定"
	data-options="iconCls:'icon-save',modal: true"
	style="padding: 5px; width: 300px; height: 270px;" closed="true"
	buttons="#btns_area_rect">
	<form id="frm_area_rect" name="frm_area_rect" method="POST"
		metdod="post" tdeme="simple">
		<table cellpadding="4" cellspacing="0"
			style="font-size: 12px; width: 100%;">
			<tr>
				 <td align="right">经度(左上角):</td>
                  <td align="left">
                  <input id="lon" name="lon" type="text"   class="easyui-validatebox" data-options="required:true" />
                   <span style="color:red">*</span>
                 </td>
			</tr>
			<tr>
				 <td align="right">纬度(左上角):</td>
                  <td align="left">
                  <input id="lat" name="lat" type="text"   class="easyui-validatebox" data-options="required:true" />
                   <span style="color:red">*</span>
                 </td>
			</tr>
			<tr>
				 <td align="right">经度(右下角):</td>
                  <td align="left">
                  <input id="lon2" name="lon2" type="text"   class="easyui-validatebox" data-options="required:true" />
                   <span style="color:red">*</span>
                 </td>
			</tr>
			<tr>
				 <td align="right">经度(右下角):</td>
                  <td align="left">
                  <input id="lat2" name="lat2" type="text"   class="easyui-validatebox" data-options="required:true" />
                   <span style="color:red">*</span>
                 </td>
			</tr>
		</table>
	</form>
	<div id="btns_area_rect">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveParams()">确定</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg_area_rect').dialog('close')">取消</a>
	</div>
</div>
<!--参数设置--end-->
