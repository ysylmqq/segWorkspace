<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type="text/javascript">
	</script>
<!-- 机械统计 详细信息--begin -->	
		<div  closed="true" class="easyui-dialog"  id="dlg_distribute_detail" 
		style="width: 630px; height: 300px; overflow: hidden" 
		data-options="title:'机械分布统计详细'"
		 buttons="#dlg_distribute_detail_btns">
		<table id="distribute_detail_datagrid" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="
            singleSelect:true,rownumbers:true,pagination:true,fit:true">
             <thead>  
            <tr>  
               <th data-options="field:'vehicleDef',width:100,align:'center'">整机编号</th>  
                <th data-options="field:'dealerName',width:100,align:'center'">经销商</th>  
               <th data-options="field:'ownerName',width:100,align:'center'">客户姓名</th>  
               <th data-options="field:'ownerPhoneNumber',width:100,align:'center'">客户联系电话</th>  
                <th data-options="field:'gpsTime',width:130,align:'center'">定位时间</th>  
                <th data-options="field:'lon',width:130,align:'center'">经度</th>  
               <th data-options="field:'lat',width:130,align:'center'">纬度</th>  
               <th data-options="field:'speed',width:130,align:'center'">速度</th>  
               <th data-options="field:'referencePosition',width:130,align:'center'">参考位置</th>  
                </tr>  
        </thead>
		</table>
	</div>
	<div id="dlg_distribute_detail_btns">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_distribute_detail').dialog('close')">关闭</a>  
	</div>  
	<!--机机械统计 详细信息--end -->