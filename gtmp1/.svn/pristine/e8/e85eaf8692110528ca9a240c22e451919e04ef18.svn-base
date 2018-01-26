<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="js/unit_replace.js"></script>
 
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false,onResize:unitReplaceResize" style="overflow:hidden;">
	   	<!-- 表格 begin -->
	    <table id="unitreplace_datagrid" class="easyui-datagrid" style="width: auto; height: auto;" 
			data-options="url:'${basePath}run/unitreplace_search.action',fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'终端换装',toolbar:'#toolbar'">
			<thead>  
	            <tr>  
	                <th data-options="field:'vehicleDef',width:130,align:'center'">整机编号</th>  
	                <th data-options="field:'oldUnitSn',width:90,align:'center'">原终端序列号</th>
	                <th data-options="field:'newUnitSn',width:90,align:'center'">现终端序列号</th>
	                <th data-options="field:'reason',width:200,align:'center'">换装原因</th>  
	                <th data-options="field:'replaceMan',width:100,align:'center'">换装人</th>
	                <th data-options="field:'replaceTime',width:100,align:'center'">换装时间</th>  
	                <th data-options="field:'addMan',width:80,align:'center'">记录人</th>
	                <th data-options="field:'addTime',width:100,align:'center'">记录时间</th>
	            </tr>  
	        </thead>
		</table>
		<!-- 表格 end -->
	
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						整机编号:
					</td>
					<td>
						<input id="vehicleDef_search" type="text" style="width: 150px;">
					</td>
					<td align="right" style="padding-left: 20px;">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryReplaceLog()">查询</a>
						<a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="downExcel();">导出</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openReplaceLog()">终端换装</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 查询条件 end -->

		<!-- 新增或者修改弹出框 begin-->
		<div id="tb" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						整机编号:
					</td>
					<td>
						<input id="vehicledef_search" type="text" style="width: 150px;">
					</td>
					<td align="right" style="padding-left: 20px;">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryVehicleInDlg()">查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="tb1" style="padding: 5px; height: auto;">
			<table style="font-size: 12px;">
				<tr>
					<td align="right">
						终端序列号:
					</td>
					<td>
						<input id="unitsn_search" type="text" style="width: 150px;">
					</td>
					<td align="right" style="padding-left: 20px;">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryUnitInDlg()">查询</a>
					</td>
				</tr>
			</table>
		</div>
			
			<div id="dlg_operate" class="easyui-dialog" style="padding: 5px; width: 600px; height: 360px;"
				data-options="iconCls:'icon-save',modal: true,closed: true,title: '终端换装',buttons:'#dlg_operate_btns',center: true">
				<form id="operate_form" method="post" action="${basePath}/run/unitReplace_unitReplace.action">
					<table style="font-size: 12px;" width="100%">
						<tr>
							<td align="right">
								整机编号：
							</td>
							<td>
								<input id="vehicleId" type="hidden" />
								<select id="vehicleDef" class="easyui-combogrid easyui-validatebox" style="width: 160px;"
									data-options="required:true,panelWidth:425,idField:'vehicleDef',textField:'vehicleDef',
				        			url:'${basePath}vehicle/vehicle_search.action',pagination:true,toolbar:'#tb',onChange:initParam,
				        			columns:[[  
						                {field:'vehicleDef',title:'整机编号',width:100},
						                {field:'modelName',title:'机械型号',width:100},
						                {field:'unitSn',title:'终端序列号',width:100},
						                {field:'simNo',title:'SIM卡号',width:100}
						            ]]">
								</select>
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								原终端序列号：
							</td>
							<td>
								<input id="oldUnitId" type="hidden" />
								<input id="oldUnitSn" style="width: 160px;" readonly="readonly" />
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								现终端序列号：
							</td>
							<td>
								<select id="newUnitSn" class="easyui-combogrid easyui-validatebox" style="width: 160px;"
									data-options="required: true,panelWidth:500,value:'unitId',idField:'unitId',textField:'unitSn',
				        			url:'${basePath}unit/unit_search.action?state=1&isValid=0',pagination:true,toolbar:'#tb1',
				        			columns:[[  
						                {field:'unitSn',title:'终端序列号',width:100},
						                {field:'supperierName',title:'供应商',width:100},
						                {field:'unitType',title:'终端类型',width:100},
						                {field:'simNo',title:'SIM卡号',width:100},
						                {field:'materialNo',title:'物料条码',width:100},
						                {field:'steelNo',title:'钢号',width:100},
						                {field:'hardwareVersion',title:'硬件版本',width:100},
						                {field:'softwareVersion',title:'软件版本',width:100},
						                {field:'state',title:'终端状态',width:100,formatter:function(val,row,index){if(val==1){return '待安装';}}}
						            ]]">
								</select>
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								换装人：
							</td>
							<td>
								<input id="replaceMan" style="width: 160px;" class="easyui-validatebox" data-options="required: true" />
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								换装时间：
							</td>
							<td>
								<input id="replaceTime" class="Wdate easyui-validatebox" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" data-options="required: true" />
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								换装原因：
							</td>
							<td>
								<textarea id="reason" rows="4" cols="30" class="easyui-validatebox" data-options="required: true"></textarea>
								<span style="color:red">*</span>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="dlg_operate_btns">
				<a href="#" id="sava" class="easyui-linkbutton" iconCls="icon-ok" onclick="unitReplace()">换装</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_operate').dialog('close')">取消</a>
			</div>
			<iframe style="display: none" id="ifm_down"></iframe>
		</div>
	</div>
<script type="text/javascript"  >
	
    function show(){
        $("#loading2").fadeOut("normal", function(){
        	$(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
            $('#unitreplace_datagrid').datagrid('resize', {  
	            width : wid-2,
	            height:hei
            }); 
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#unitreplace_datagrid').datagrid('columnMoving');
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
  
</script> 