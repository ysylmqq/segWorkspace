<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/total_workhours.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',fit:true,border:false,onResize:totalWorkHoursResize"  style="overflow:hidden;">
    <div id="tab_total_workhours" class="easyui-tabs" style="">  
    <div title="工作时间汇总" data-options="" style="overflow:auto;">  
        <!-- 表格 begin -->
  	  <table id="total_workhours_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" 
			data-options="
            fitColumns:true,singleSelect:true,rownumbers:true">
             <thead>  
            <tr>
                <th data-options="field:'DEALERNAME',width:100,align:'center'">经销商</th> 
                <th data-options="field:'AREANAME',width:100,align:'center'">区域</th> 
                <th data-options="field:'MODELNAME',width:100,align:'center'">机械型号</th> 
                <th data-options="field:'VEHICLECODE',width:100,align:'center'">机械代号</th>  
                <th data-options="field:'VEHICLEARG',width:100,align:'center'">机械配置</th>   
               	<th data-options="field:'VEHICLECOUNT',width:100,align:'center',formatter:vehicleCountformatter">机械数量</th>  
               	<th data-options="field:'TOTALWORKHOUR',width:100,align:'center'">累计工作时间(小时)</th>  
                <th data-options="field:'AVGWORKHOUR',width:130,align:'center'">平均工作时间(小时)</th>  
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		    <table style=" font-size: 12px;">
		      <tr>
		       <!-- <td align="right">
						经销商:
					</td>
					<td align="left">
						<select id="dealerId" name="dealerId" class="easyui-combotree" style="width: 160px;"
							data-options="checkbox:false,onlyLeafCheck:true,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" ></select>
							<input id="dealerId" name="dealerId" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'run/run_getDealers.action',
								valueField:'id',  
       							textField:'name'" 
				       />
					</td> -->
					<td align="right">
						区域/经销商:
					</td>
					<td>
						<input id="dealerId_search" name="dealerId_search" class="easyui-combotree" style="width: 150px;"
							data-options="url:'run/run_getDealerAreas4Tree.action',multiple:true ,onlyLeafCheck:false,cascadeCheck:true" />
					</td>
		        <td align="right">机械型号:</td>
		        <td>
		         <input id="modelName_search" name="modelName_search" class="easyui-combobox"
							style="width: 150px;"
							data-options="
							url:'vehicle/vehicleModel_getList.action',
                 valueField:'modelId',
                 textField:'modelName'
                 " />
		       </td>
		       <td align="right">机械代号:</td>
		        <td>
				        <select  onclick="argSel()"   id="vehicleCode_search" name="vehicleCode_search"  style="width:150px;height:21px" >    
						</select>
		       </td>
		       <td align="right">机械配置:</td>
		        <td>
		        <select   id="vehicleArg_search" name="vehicleArg_search"  style="width:150px;height:21px" >    
						</select>
		       </td>
		       
		       
					 <td align="right">
						机械状态(组):
					</td>
					<td align="left">
						  <select id="status" class="easyui-combobox" name="status" style="width:150px;">  
					<option value="">全部</option> 
					<option value="3">销售组</option>
					<option value="8">法务组</option>   
					<option value="9">停用组</option>  
				    </select>
				    	</td> 
				    	<td>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="querytotalWorkHours()">查询</a>
			     <a href="#" id="downBtnSummary" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:summaryDownExcel();">导出</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>  
    <div title="工作时间明细" data-options="fit:true,doSize:true" style="overflow:auto;">  
         <!-- 表格 begin -->
  	  <table id="total_workhours_detail2_datagrid" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			 toolbar="#toolbar_detail" 
			data-options="fitColumns:true,
            singleSelect:true,rownumbers:true">
             <thead>  
            <tr>  
                <th data-options="field:'VEHICLEDEF',width:130,align:'center'">整机编号</th>  
                <th data-options="field:'DEALERNAME',width:130,align:'center'">经销商</th>
                <th data-options="field:'AREANAME',width:100,align:'center'">区域</th>
				<th data-options="field:'VEHICLESTATUS',width:100,align:'center'">机械状态</th>
                <th data-options="field:'MODELNAME',width:100,align:'center'">机械型号</th>
                <th data-options="field:'VEHICLECODE',width:100,align:'center'">机械代号</th>  
                <th data-options="field:'VEHICLEARG',width:100,align:'center'">机械配置</th>   
                <th data-options="field:'TOTALWORK',width:100,align:'center'">累计工作时间(小时)</th>
                </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar_detail" style="padding: 5px; height: auto;">
		    <table style=" font-size: 12px;">
		      <tr>
		       <!-- <td align="right">
						经销商:
					</td>
					<td align="left">
						<select id="dealerId" name="dealerId" class="easyui-combotree" style="width: 160px;"
							data-options="checkbox:false,onlyLeafCheck:true,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" ></select>
							<input id="dealerId2" name="dealerId2" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'run/run_getDealers.action',
								valueField:'id',  
       							textField:'name'" 
				       />
					</td> -->
					<td align="right">
						区域/经销商:
					</td>
					<td>
						<input id="dealerId2_search" name="dealerId2_search" class="easyui-combotree" style="width: 150px;"
							data-options="url:'run/run_getDealerAreas4Tree.action',multiple:true ,onlyLeafCheck:false,cascadeCheck:true" />
					</td>
		        <td align="right">机械型号:</td>
		        <td>
		         <input id="modelName2_search" name="modelName2_search" class="easyui-combobox"
							style="width: 150px;"
							data-options="
							url:'vehicle/vehicleModel_getList.action',
                 valueField:'modelId',
                 textField:'modelName'
                 " />
		       </td>
		       <td align="right">机械代号:</td>
		        <td>
				        <select  onclick="argSel2()"   id="vehicleCode2_search" name="vehicleCode2_search"  style="width:150px;height:21px" >    
						</select>
		       </td>
		       <td align="right">机械配置:</td>
		        <td>
		        <select   id="vehicleArg2_search" name="vehicleArg2_search"  style="width:150px;height:21px" >    
						</select>
		       </td>
		       <td></td>
		       </tr>
		       <tr>
					 <td align="right">
						机械状态(组):
					</td>
					<td align="left">
						  <select id="status2" class="easyui-combobox" name="status2" style="width:150px;">  
					<option value="">全部</option> 
					<option value="3">销售组</option>
					<option value="8">法务组</option>   
					<option value="9">停用组</option>  
				    </select>
				    	</td> 
				    	
				  <td align="right">累计工作小时:</td>
		       <td>从<input id="totalWorkingHours"class="easyui-numberbox" style="width: 60px;">到<input id="totalWorkingHours2"class="easyui-numberbox" style="width: 60px;"></td>
		      <td>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="querytotalWorkHoursDetail()">查询</a>
			    <a href="#" id="downBtnDetailed" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:detailedDownExcel();">导出</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->  
    </div> 
    <div title="每日工作时间" data-options="fit:true,doSize:true" style="overflow:auto;">  
        <input type="hidden" id="vehicledef_daily">
         <!-- 表格 begin -->
  	  <table id="daily_workhours_datagrid" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			 toolbar="#toolbar_daily" 
			data-options="fitColumns:true,
            singleSelect:true,rownumbers:true">
             <thead>  
            <tr>  
                <th data-options="field:'VEHICLEDEF',width:130,align:'center'">整机编号</th>  
                <th data-options="field:'DEALERNAME',width:130,align:'center'">经销商</th>
                <th data-options="field:'AREANAME',width:100,align:'center'">区域</th>
				<th data-options="field:'VEHICLESTATUS',width:100,align:'center'">机械状态</th>
                <th data-options="field:'MODELNAME',width:100,align:'center'">机械型号</th>
                <th data-options="field:'VEHICLECODE',width:100,align:'center'">机械代号</th>  
                <th data-options="field:'VEHICLEARG',width:100,align:'center'">机械配置</th> 
                <th data-options="field:'TOTALWORK',width:100,align:'center',formatter:dailyWorkhoursformatter">阶段工作时间(小时)</th>
                </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar_daily" style="padding: 5px; height: auto;">
		    <table style=" font-size: 12px;">
		      <tr>
		      <!--  <td align="right">
						经销商:
					</td>
					<td align="left">
						<select id="dealerId" name="dealerId" class="easyui-combotree" style="width: 160px;"
							data-options="checkbox:false,onlyLeafCheck:true,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" ></select>
							<input id="dealerId3" name="dealerId3" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'run/run_getDealers.action',
								valueField:'id',  
       							textField:'name'" 
       						
				       />
					</td> -->
					<td align="right">
						区域/经销商:
					</td>
					<td>
						<input id="dealerId3_search" name="dealerId3_search" class="easyui-combotree" style="width: 150px;"
							data-options="url:'run/run_getDealerAreas4Tree.action',multiple:true ,onlyLeafCheck:false,cascadeCheck:true" />
					</td>
		        <td align="right">机械型号:</td>
		        <td>
		         <input id="modelName3_search" name="modelName3_search" class="easyui-combobox"
							style="width: 150px;"
							data-options="
							url:'vehicle/vehicleModel_getList.action',
                 valueField:'modelId',
                 textField:'modelName'
                 " />
		       </td>
		       <td align="right">机械代号:</td>
		        <td>
				        <select  onclick="argSel3()"   id="vehicleCode3_search" name="vehicleCode3_search"  style="width:150px;height:21px" >    
						</select>
		       </td>
		       <td align="right">机械配置:</td>
		        <td>
		        <select   id="vehicleArg3_search" name="vehicleArg3_search"  style="width:150px;height:21px" >    
						</select>
		       </td>
		        
		       
		       <td></td>
		       </tr>
		       <tr>
		       <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_search" style="width: 150px;"></td>
					 <td align="right">
						机械状态(组):
					</td>
					<td align="left">
						  <select id="status3" class="easyui-combobox" name="status3" style="width:150px;">  
					<option value="">全部</option> 
					<option value="3">销售组</option>
					<option value="8">法务组</option>   
					<option value="9">停用组</option>  
				    </select>
				    	</td> 
				<td align="right">	
					开始日期: </td>    	
				  <td>
				 <input id="start_time" class="Wdate" onfocus="WdatePicker()" style="width:150px">
				</td>
		      <td align="right">	
					结束日期: </td>
				<td><input id="end_time"  class="Wdate" onfocus="WdatePicker()" style="width:150px">
				</td>
				<td>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryDailyWorkHours()">查询</a>
			    <a href="#" id="downBtnDay" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:dayDownExcel();">导出</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->  
    </div>  
</div>
    
		</div>
</div>		

		<!--第一个tab 累计工作时间 详细 窗口--begin -->	
		<div  closed="true" class="easyui-dialog"  id="dlg_total_workhours_detail" 
		style="width: 630px; height: 300px; overflow: hidden" 
		data-options="title:'累计工作时间统计详细'"
		 buttons="#dlg_total_workhours_detail_btns">
		<table id="total_workhours_detail_datagrid" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="fitColumns:true,
            singleSelect:true,rownumbers:true,pagination:true,fit:true">
             <thead>  
            <tr>  
                 <th data-options="field:'VEHICLEDEF',width:130,align:'center'">整机编号</th>  
                <th data-options="field:'DEALERNAME',width:130,align:'center'">经销商</th>
                <th data-options="field:'MODELNAME',width:100,align:'center'">机械型号</th>  
                <th data-options="field:'TOTALWORK',width:100,align:'center'">累计工作时间(小时)</th>
                </tr>  
        </thead>
		</table>
	</div>
	<div id="dlg_total_workhours_detail_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_total_workhours_detail').dialog('close')">关闭</a>  
	</div>  
	<!--第一个tab 累计工作时间 详细 窗口--end -->
	
	<!--第二个tab 每日工作时间 详细 窗口--begin -->	
		<div  closed="true" class="easyui-dialog"  id="dlg_daily_workhours_detail" 
		style="width: 630px; height: 300px; overflow: hidden" 
		data-options="title:'每日工作时间统计详细'"
		 buttons="#dlg_daily_workhours_detail_btns">
		<table id="daily_workhours_detail_datagrid" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="fitColumns:true,
            singleSelect:true,rownumbers:true,pagination:true,fit:true">
             <thead>  
            <tr>  
                 <th data-options="field:'VEHICLEDEF',width:100,align:'center'">整机编号</th> 
                 <th data-options="field:'DATE_YMD',width:100,align:'center'">日期</th>  
                <th data-options="field:'DEALERNAME',width:180,align:'center'">经销商</th>
                <th data-options="field:'MODELNAME',width:80,align:'center'">机械型号</th>  
                <th data-options="field:'WORKED_HOURS',width:130,align:'center'">当日工作时间(小时)</th>
                </tr>  
        </thead>
		</table>
	</div>
	<div id="dlg_daily_workhours_detail_btns">  
	    <a href="#" id="downBtnDayDetail" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:dayDetailDownExcel();">导出</a>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_daily_workhours_detail').dialog('close')">关闭</a>  
	</div>  
	<!--第二个tab 每日工作时间 详细 窗口--end -->
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#total_workhours_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei-30
            }); 
             $('#total_workhours_detail2_datagrid').datagrid('resize', {  
                 width : wid-2  ,
                  height:hei-30
                }); 
             $('#daily_workhours_datagrid').datagrid('resize', {  
                 width : wid-2  ,
                  height:hei-30
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
