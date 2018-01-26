<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/distribute_statistic.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   
    <div id="north_index" data-options="region:'north'" style="height:82px;width:100%;overflow:hidden" class="datagrid-panel" title="机械分布统计">
	 <div class="datagrid-toolbar" style="height:100%;">
	  <table style=" font-size: 12px;">
		      <tr>
		      <!-- <td align="right">
						省:
					</td>
					<td align="left">
						<select id="province" name="province" class="easyui-combobox" style="width: 150px;"
							data-options="url:'sys/maparea_getProvinceList.action' ,
							valueField:'name',
                 			textField:'name',onSelect:provinceChange">
                 			</select>
					</td>
					  <td align="right">
						市:
					</td>
					<td align="left">
						<select id="city" name="city" class="easyui-combobox" style="width: 150px;"
							data-options="url:'sys/maparea_getCityList.action',
							valueField:'name',
                 			textField:'name'"></select>
					</td> -->
				 <td align="right">机械型号:</td>
		        <td>
						<input id="vehicleModel_search" class="easyui-combobox" style="width: 150px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
					</td>
					<td align="right">
						机器代号:
					</td>
					<td>
						<input id="vehicleCode_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" />
					</td>
					<td align="right">
						配置号:
					</td>
					<td>
						<input id="vehicleArg_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleArg',textField:'vehicleArg'" />
					</td>
		        <td align="right">
						区域、经销商:
					</td>
					<td align="left">
						<select id="dealerId" name="dealerId" class="easyui-combotree" style="width: 150px;"
							data-options="onlyLeafCheck:false,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" multiple></select>
					</td>
		       </tr>
		       <tr>
		       
					 <td align="right">
						机械状态(组):
					</td>
					<td align="left">
						  <select id="status" class="easyui-combobox" name="status" style="width:150px;">  
					<option value="">全部</option> 
					<option value="1">测试组</option>  
					<option value="2">已测组</option>  
					<option value="3">销售组</option>
					<option value="8">法务组</option>   
					<option value="9">停用组</option>  
				    </select>
				    	</td> 
			    <td colspan="2">
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="statisticDistribute()">查询</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			   <!--  <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-baobiao" onclick="javascript:openChartWin();">图表</a> -->
			</td>
		      </tr>
		    </table> 
	 </div>
	 </div>
   <div data-options="region:'center',border:false,tools:'#map_tb'"   id="distribute_map_div"
				title="我的地图" style="overflow: hidden">
				<iframe id="ifm_map_distribute" name="ifm_map_distribute"
					src="${basePath}jsp/report/distribute_map.jsp"
					style="width: 100%; height: 100%; border-color: transparent; border-image: none; border-style: solid; border-width: 0 0px 0px;"></iframe>
		    </div>
		    
<div id="map_tb">
		<a href="#" class="icon-move"
			onclick="javascript:ifm_map_distribute.window.operateMap(3)" title="恢复默认工具"></a>
		<a href="#" class="icon-bigger"
			onclick="javascript:ifm_map_distribute.window.operateMap(1)" title="放大"></a>
		<a href="#" class="icon-smaller"
			onclick="javascript:ifm_map_distribute.window.operateMap(2)" title="缩小"></a>

		<a href="#" class="icon-ceju"
			onclick="javascript:ifm_map_distribute.window.operateMap(4)" title="测距"></a>
		<a href="#" class="icon-center"
			onclick="javascript:ifm_map_distribute.window.operateMap(5)" title="居中"></a>
			<a href="#" class="icon-clear"
			onclick="javascript:ifm_map_distribute.window.operateMap(7)" title="清除机械信息"></a>
		<input type="checkbox" name='isShowPlateNumber' id="isShowPlateNumber" checked="checked" onclick="javascript:ifm_map_distribute.window.isShowPlateNumber(this)"><label style="font-size:12px;color:black;font-weight:normal">显示机械数量</label>
	</div>
		</div>
		
	<jsp:include page="vehicle_detail_win.jsp"></jsp:include>
	
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
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
