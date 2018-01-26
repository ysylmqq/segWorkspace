<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/workhours_year_statistic.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:workhoursQueryResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="workhours_statistic_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true"
			data-options="
            singleSelect:true,rownumbers:true,title:'机械工作时间统计',onClickCell:malfunnStatisCellClick">
             <thead>  
            <tr>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		    <table style=" font-size: 12px;">
		      <tr>
		       <!-- <td align="right">报表分类:</td>
		        <td>
				<input type = "radio" name = "report_type" value = "1" checked="checked"/>月报
		        <input type = "radio" name = "report_type" value = "2"  />年报
				</td> -->
		       <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_search" style="width: 150px;"></td>
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
		      <td align="right">
			         	机械代号：
			         </td>
			         <td>
			         	<input id="vehicleCode_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleCode',textField:'vehicleCode'" />
			         </td>
			         <td align="right">
			         	机械配置：
			         </td>
			         <td>
			         	<input id="vehicleArg_search" class="easyui-combobox" style="width: 150px;" data-options="value:'全部',valueField:'vehicleArg',textField:'vehicleArg'" />
			         </td>
		      
		        </tr>
				<tr>
			<!-- 	 <td align="right">区域:</td>
		        <td>
		         <input id="areaName_search" name="areaName_search" class="easyui-combobox"
							style="width: 150px;"
							data-options="
							url:'vehicle/vehicleModel_getAreaOrDealer.action?dealerAreaPOJO.dtype=1',
                 valueField:'id',
                 textField:'name'
                 " />
		       </td>
		       <td align="right">经销商:</td>
		        <td>
				       <select   id="dealer_search" name="dealer_search"  style="width:150px;height:21px" >    
						</select>
						<input id="dealer_search" name="dealer_search" class="easyui-combobox"
							style="width: 150px;"
							/>
		       </td> -->
		       <td align="right">
						经销商:
					</td>
					<!-- <td>
						<input id="dealerId_search" class="easyui-combobox" style="width: 200px;"
							data-options="url:'run/run_getDealers.action',valueField:'id',textField:'name'" />
					</td> -->
					<td>
						<input id="dealerId_search" name="dealerId_search"
							class="easyui-combotree" style="width: 150px;"
							data-options="
							url:'run/run_getDealerAreas4Tree.action',
                 multiple:true,onlyLeafCheck:false,cascadeCheck:true
                 " />
					</td>
					 <td align="right">机械状态:</td>
		        <td>
		        <select  class="easyui-combobox"  id="vehicleStatus_search" name="vehicleStatus_search"  style="width:150px;height:21px" >    
				    <option value="">全部</option>
                    <option value="1">测试组</option>
                    <option value="2">已测组</option>
                    <option value="3">销售组</option>
                    <option value="9">停用组</option>		
				</select>
		         
		       </td>
		       
				<td align="right">
					年份: </td>
				<td>
				<div style="display: block">
					<input id="year_time"  type="text" class="Wdate" onFocus="WdatePicker({ dateFmt: 'yyyy'})" style="width:150px" />
				</div>
				</td>
			    <td colspan="4">
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:statistic_workhours_year()">查询</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-baobiao" onclick="javascript:openChartWin();">图表</a>
			</td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
		
	<!-- 机械工作时间统计--begin -->	
		<div  closed="true" class="easyui-dialog"  id="dlg_workhours_detail" 
		style="width: 630px; height: 300px; overflow: hidden" data-options="title:'机械工作时间统计详细'"
		 buttons="#dlg_workhours_detail_btns">
		<table id="workhours_detail_datagrid" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="
            singleSelect:true,rownumbers:true,fit:true">
             <thead>  
            <tr>  
                 <th data-options="field:'vehicleDef',width:130,align:'center'">整机编号</th>  
                <th data-options="field:'typeName',width:100,align:'center'">机械类型</th>
                <th data-options="field:'modelName',width:100,align:'center'">机械型号</th>  
                <th data-options="field:'fixMan',width:100,align:'center'">终端安装人</th>
                <th data-options="field:'fixDate',width:100,align:'center'">终端安装日期</th>
                <th data-options="field:'status',width:100,align:'center'">累计工作时间</th>
            </tr>  
        </thead>
		</table>
	</div>
		<div id="dlg_workhours_detail_btns">  
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_workhours_detail').dialog('close')">关闭</a>  
	</div> 
	<!--机械工作时间统计--end -->
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#workhours_statistic_datagrid').datagrid('resize', {  
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
