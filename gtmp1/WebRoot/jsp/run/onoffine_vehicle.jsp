<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--在线、不在线窗口-->
<!-- 在线、离线--begin -->
	<div closed="true" id="dlg_online_run" class="easyui-dialog" title="在线"
		style="width: 800px; height: 500px; overflow: hidden" buttons="#dlg_online_run_btns" data-options="maximizable:true,onResize:dlgOnlineResize">
		<table id="online_run_datagrid" 
			toolbar="#online_run_toobar"
			style="width:auto; height: 430px;padding-top:0px;margin-top:0px;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}run/run_getOnOffLineVechile.action',
            singleSelect:true,rownumbers:true,pagination:true">
             <thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:150,align:'center'">整机编号</th>
                <th data-options="field:'gpsTime',width:150,align:'center',sortable:true">GPS定位时间</th>
                <th data-options="field:'vehicleState',width:200,align:'center',sortable:true">机械状态</th>
                <th data-options="field:'referencePosition',width:150,align:'center',sortable:true">参考位置</th>
		</table>
	</div>
	<div id="online_run_toobar"  
		style="height: auto; display: none">
			整机编号： <input id="vehicleDef_online_run"  type="text"	style="width: 120px;" />
				<a href="#" class="easyui-linkbutton"
				iconCls="icon-search" onclick="queryOnline()">查询</a> 
	</div>
	
	<div id="dlg_online_run_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_online_run').dialog('close')">关闭</a>  
	   </div> 
	<!-- 在线、不在线--end -->