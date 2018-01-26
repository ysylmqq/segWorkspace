<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/operate_log.js"></script> 
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:operateLogResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="operate_log_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}sys/operatelog_getPage.action',queryParams:defQueryParams,
            fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'操作日志查询'">
             <thead>  
            <tr> 
                <th data-options="field:'userName',width:100,align:'center'">用户名</th>
                <th data-options="field:'logType',width:130,align:'center'">操作类型</th>  
                <th data-options="field:'logContent',width:130,align:'center'">操作描述</th>  
                <th data-options="field:'ip',width:100,align:'center'">IP</th>  
                <th data-options="field:'stamp',width:150,align:'center'">时间</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		    <table style=" font-size: 12px;">
		      <tr>
		       <td >
					用户名:
				</td>
				<td>
				 <input id="userName" type="text" style="width:150px">
				</td>
				<td>操作类型:</td>
				<td>
					<input id="logType" class="easyui-combobox" name="unitTypeSn" style="width:150px"
		                 data-options="valueField:'LOGTYPE',textField:'LOGTYPE',url:'sys/operatelog_getSelectData.action'" />
				</td>
		         <td >
					开始时间:
				</td>
				<td>
				 <input id="start_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:180px">
				</td>
				<td>	
					结束时间:</td>
				<td><input id="end_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:180px">
				</td>
			    <td> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryOpl()">查询</a>
			    </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->
		    </div>
		</div>
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#operate_log_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
            }); 
        });
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#start_time').val(getTodayZero());
    	$('#end_time').val(new Date().formatDate(timeFormat));
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script> 
