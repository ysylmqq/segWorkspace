<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.map_toolbar{
 position:relative;
 top:-2px !important;
 font-size:11px; 
 vertical-align: center;
 background-color: #e0edfe;
 cursor: pointer;
 }
</style>
<script type="text/javascript" src="${basePath}js/store_area.js"></script>

<div id='loading_store'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
<div class="easyui-layout" data-options="fit:true"
	style="width: 100%; height: 100%;">
	<div id="store_west_panel" 
		data-options="region:'west',title:'库存区域管理',doSize:true"
		style="width: 330px;">
			<table class="easyui-datagrid" toolbar="#toolbar"
							style="width:auto,height:200px" 
							data-options="url:'${basePath}vehicle/areaPoint_getList.action',
            				fitColumns:false,singleSelect:true,rownumbers:true"
							id="store_grid">
							<thead>
								<tr>
									<th data-options="field:'areapointName',width:100,align:'center'">
										区域名称
									</th>
									<th
										data-options="field:'remark',width:80,align:'center'">
										备注
									</th>
									<th
										data-options="field:'userId',width:100,align:'center',formatter:function(val,row,index){
										 	var str_a='';
											str_a+='<a style=\'color:blue;\' href=\'javascript:void(0)\' onclick=openStoreAreaWin(\''+row.areapointId+'\',0)>编辑</a>';
											str_a+='&nbsp;<a style=\'color:blue;\' href=\'javascript:void(0)\' onclick=deleteStoreArea(\''+row.areapointId+'\',\''+row.areapointName+'\')>删除</a>';
											return str_a;
										}">
										操作
									</th>
								</tr>
							</thead>
						</table>
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">区域名称:</td>
		        <td><input id="areapointName_search"></td>
			  </tr>
			 <tr>
		        <td align="right">备注:</td>
		        <td>
		        <div>
		         <input id="remark_search" >
		         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryStore()">查询</a>
		        </div>
		        </td>
			  </tr>
			  <tr>
			  <td colspan="2">
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="openStoreAreaWin()">新增</a>
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-display" onclick="displayArea()">显示</a>
			  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear2" onclick="clearArea()">清除</a>
			  <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="editStore()">编辑</a>
			  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="deleteStore()">删除</a> -->
			  </td>
			 	  </tr>
		      </table>
		</div>
		<!-- 查询条件 end -->
		<table id="vehicle_grid" class="easyui-datagrid" style="width:auto;height:auto"  
		       	 data-options="title:'机械信息'">  
				    <thead>  
				        <tr>  
				            <th data-options="field:'areapointName',width:100,align:'center'">区域名称</th>  
				            <th data-options="field:'vehicleDef',width:200,align:'center'">整机编号</th>  
				        </tr>  
				    </thead>  
				</table>
	</div>
	<div data-options="region:'center',doSize:true">
		<div class="easyui-layout" data-options="fit:true"
			style="width: 100%; height: 100%;">
			<div data-options="region:'center',tools:'#map_tb'" id="store_map_div"
				title="我的地图" style="overflow: hidden">
				<iframe id="ifm_map_store" name="ifm_map_store"
					src="${basePath}jsp/vehicle/store_area_map.jsp"
					style="width: 100%; height: 100%; border-color: transparent; border-image: none; border-style: solid; border-width: 0 0px 0px;"></iframe>
			</div>
		</div>
	</div>
	<div id="map_tb">
		<a href="#" class="icon-move"
			onclick="javascript:ifm_map_store.window.operateMap(3)" title="恢复默认工具"></a>
		<a href="#" id="afullscrenn" class="icon-fullscreen"
			onclick="javascript:fullscreen();" title="全屏"></a>
		<!--<a href="#" class="icon-alarm" onclick="javascript:alarmopen()"title="警情"></a> -->
		<a href="#" class="icon-bigger"
			onclick="javascript:ifm_map_store.window.operateMap(1)" title="放大"></a>
		<a href="#" class="icon-smaller"
			onclick="javascript:ifm_map_store.window.operateMap(2)" title="缩小"></a>

		<a href="#" class="icon-ceju"
			onclick="javascript:ifm_map_store.window.operateMap(4)" title="测距"></a>
		<a href="#" class="icon-center"
			onclick="javascript:ifm_map_store.window.operateMap(5)" title="居中"></a>
			<a href="#" class="icon-clear"
			onclick="javascript:ifm_map_store.window.operateMap(7)" title="清除机械信息"></a>
		<input type="checkbox" name='isShowPlateNumber' id="isShowPlateNumber" checked="checked" onclick="javascript:ifm_map_store.window.isShowPlateNumber(this)"><label style="font-size:12px;color:black;font-weight:normal">显示整机编号</label>
	</div>

	 <!-- 新增或者修改弹出框 begin-->
		 <div id="dlg_store_operate" class="easyui-dialog" title="库存区域" data-options="iconCls:'icon-save',modal: false"
		style="padding: 5px; width: 250px; height: 310px;" closed="true" buttons="#dlg_store_operate_btns">
        <form id="store_operate_form" metdod="post" theme="simple"
			 action="${basePath}store/store_saveOrUpdate.action" >
			 <input id="areapointId" name="areapointId" type="hidden" />
			 <input id="shapetype" name="shapetype" type="hidden"type="2" />
 			<table  cellspacing="0" style="font-size:12px;width: 100%;">
            <tr>
		          <td align="right">名称:</td>
                  <td align="left">
                  <input id="areapointName" name="areapointName" type="text"   class="easyui-validatebox" data-options="required:true"  />
                   <span style="color:red">*</span>
                 </td>
            </tr>
            <tr>
                <td align="right">备注:</td>
                <td align="left">
                <textarea rows="3"id="remark" name="remark"  style="width: 154px" ></textarea>
                </td>
            </tr>
               
            <tr>
             <td colspan="2"><input type="button" value="选择矩形区域" onclick="ifm_map_store.window.operateMap(8)" id="drawRect_btn"></input></td>
            </tr>
            
             <tr>
                 <td colspan="2">
                 <textarea style="width: 200px" rows="4"  id="pointstr" name="pointstr" class="easyui-validatebox" data-options="required:true" >
                 </textarea>
                 </td>
                 </tr>
        </table>
		</form>
       </div>  
		<div id="dlg_store_operate_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveStore()" id="save_store_btn">保存</a>  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_store_operate').dialog('close')">关闭</a>  
	</div>  
		    <!-- 新增或者修改弹出框 end-->
		    
	<!--画完矩形框后弹出的窗口-->	    
	<jsp:include page="store_area_rect.jsp"></jsp:include>
</div>

<script type="text/javascript">
    function show(){
        $("#loading_store").fadeOut("fast", function(){
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
