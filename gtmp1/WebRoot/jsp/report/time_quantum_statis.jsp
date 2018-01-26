<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/time_quantum_statis.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:timeQuantumStatisResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="timeQuantumStatis_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true"
			data-options="url:'${basePath}report/statistic_statisticTimeQuantum.action',
            fitColumns:true,singleSelect:true,rownumbers:true,title:'机械工作时间段统计'">
             <thead>  
            <tr>
             <!-- <th data-options="field:'vcount',width:130,align:'center',sortable:true,formatter:formatterVcount">机械数量</th>   -->
             <th data-options="field:'vehicleModel',width:0,hidden:true"></th>
             <th data-options="field:'modelName',width:130,align:'center',sortable:true">机械类型</th>
             <th data-options="field:'aCount',width:130,align:'center',sortable:true,formatter:formatterVcount1">0-100(小时)</th>
             <th data-options="field:'bCount',width:130,align:'center',sortable:true,formatter:formatterVcount2">100-200(小时)</th>
             <th data-options="field:'cCount',width:130,align:'center',sortable:true,formatter:formatterVcount3">200-500(小时)</th>
             <th data-options="field:'dCount',width:130,align:'center',sortable:true,formatter:formatterVcount4">500-1000(小时)</th>
             <th data-options="field:'eCount',width:130,align:'center',sortable:true,formatter:formatterVcount5">1000-2000(小时)</th>
             <th data-options="field:'fCount',width:130,align:'center',sortable:true,formatter:formatterVcount6">2000-3000(小时)</th>
             <th data-options="field:'gCount',width:130,align:'center',sortable:true,formatter:formatterVcount7">3000(小时)以上</th>
              
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
				<td>
				 <input id="dealerId_search" name="dealerId_search" class="easyui-combobox"
							style="width: 200px;"
							data-options="
							url:'run/run_getDealers.action',
								valueField:'id',  
       							textField:'name'" 
				/>
				</td>
				
		        <td align="right">工作时间段:</td>
		        <td>
		        <select   id="workTime_search" name="workTime_search"  style="width:150px;height:21px" > 
		        	<option value="100">0-100小时</option>
		        	<option value="200">100-200小时</option>
		        	<option value="500">200-500小时</option>
		        	<option value="1000">500-1000小时</option>
		        	<option value="2000">1000-2000小时</option>
		        	<option value="3000">2000-3000小时</option>
		        	<option value="3001">3000小时以上</option>
						</select>
		       </td> 
			   
		      </tr>
		      <tr>-->
		      <td align="right">
						机械型号:
					</td>
					<td>
						<input id="vehicleModel_search" class="easyui-combobox" style="width: 200px;" data-options="url:'vehicle/vehicleModel_getList.action',valueField:'modelId',textField:'modelName'" />
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
		       </tr>
		       <tr>
		       <td align="right">
						区域、经销商:
					</td>
					<td align="right">
						<select id="dealerId_search" name="dealerId_search" class="easyui-combotree" style="width: 200px;"
							data-options="onlyLeafCheck:false,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" multiple></select>
					</td>
		       <td align="right">机械状态:</td>
		        <td>
		        <select  class="easyui-combobox"  id="vehicleStatus_search" name="vehicleStatus_search"  style="width:150px;height:21px" >    
				    <option value="">全部</option>
                    <option value="1">测试组</option>
                    <option value="2">已测组</option>
                    <option value="3">销售组</option>
                    <option value="8">法务组</option>
                    <option value="9">停用组</option>		
				</select>
		         
		       </td>
		        <td colspan="2">
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="statistictime_quantum()">查询</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-baobiao" onclick="javascript:openChartWin();">图表</a>
			</td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
		
	<!-- 机械工作时间段统计--begin -->	
		<div  closed="true" class="easyui-dialog"  id="dlg_time_quantum_detail" 
		style="width: 830px; height: 400px; overflow: hidden" 
		data-options="title:'机械工作时间段统计详细'"
		 buttons="#dlg_time_quantum_detail_btns">
		<table id="time_quantum_detail_datagrid" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="
            singleSelect:true,rownumbers:true,pagination:true,fit:true">
             <thead>  
            <tr>  
                 <th data-options="field:'vehicleDef',width:95,align:'center'">整机编号</th>  
                <!-- <th data-options="field:'typeName',width:100,align:'center'">机械类型</th> -->
                <th data-options="field:'dealerName',width:150,align:'left'">经销商</th>
				<th data-options="field:'areaName',width:100,align:'center'">区域</th>
				<th data-options="field:'vehicleStatus',width:100,align:'center'">机械状态</th>
                <th data-options="field:'modelName',width:65,align:'center'">机械型号</th>  
                <th data-options="field:'vehicleCode',width:60,align:'center'">机械代号</th>  
                <th data-options="field:'vehicleArg',width:60,align:'center'">机械配置</th>  
                <th data-options="field:'fixMan',width:65,align:'center'">终端安装人</th>
                <th data-options="field:'fixDate',width:150,align:'center'">终端安装日期</th>
                <th data-options="field:'totalworkinghours',width:84,align:'center'">累计工作时间</th>
                </tr>  
        </thead>
		</table>
	</div>
	<div id="dlg_time_quantum_detail_btns">
	
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_time_quantum_detail').dialog('close')">关闭</a>  
	</div>  
	<!--机械工作时间段统计--end -->
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#timeQuantumStatis_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
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
