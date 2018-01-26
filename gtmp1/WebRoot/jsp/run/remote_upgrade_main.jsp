<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/remote_upgrade.js"></script>
<div id='loading_run'
	style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #DDDDDB; text-align: center; padding-top: 20%;">
	<img src='images/loading.gif' />
</div>
    <!-- 表格 begin -->
    <table id="upgrade_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="url:'${basePath}run/command_searchUpgrade.action',queryParams:defQueryParams,
            singleSelect:true,rownumbers:true,pagination:true,title:'空中升级记录查询'">
             <thead>  
            <tr>  
                <th data-options="field:'vehicleDef',width:130,align:'center'">整机编号</th>  
                <th data-options="field:'unitSn',width:100,align:'center'">终端序列号</th>
                <th data-options="field:'upgradeType',width:100,align:'center',formatter:function(val,row,index){if(val=='00'){ return '普通升级';}else{ return '强制升级';}}">升级类型</th>  
                <th data-options="field:'deviceType',width:150,align:'center',formatter:function(val,row,index){if(val=='00'){ return 'GPS终端';}else if(val=='01'){ return '控制器';}else{ return '显示器';}}">设备类型</th>
                <th data-options="field:'softwareVersion',width:100,align:'center'">程序版本号</th>
                <th data-options="field:'ip',width:150,align:'center'">IP</th>
                <th data-options="field:'apn',width:160,align:'center'">APN</th>
                <th data-options="field:'serverPort',width:90,align:'center'">服务器端口号</th>
                <th data-options="field:'localPort',width:90,align:'center'">本地端口号</th>
                <th data-options="field:'userName',width:90,align:'center'">操作人</th>
               <th data-options="field:'pathFlag',width:120,align:'center',formatter:function(val,row,index){if(val==0){ return 'GPRS';}else if(val==1){ return 'GSM';}
                }">通道</th>
                <th data-options="field:'upgradeState',width:120,align:'center',formatter:function(val,row,index){
                if(val=='00'){ return '接收成功';}else if(val=='01'){ return '校验错';}
                else{ return '其他';}
                }">终端应答</th>
                <th data-options="field:'result',width:120,align:'center',formatter:function(val,row,index){if(val=='00'){ return '文件格式正确';}else if(val=='01'){ return '不存在任何文件';}else if(val=='02'){ return '供应商标识不对';}
                else if(val=='03'){ return '终端型号不对';}
                else if(val=='04'){ return '程序版本不对';}
                else if(val=='05'){ return 'GPS终端升级成功';}
                else if(val=='06'){ return '控制器升级成功';}
                else if(val=='07'){ return '显示器升级成功';}
                else{ return '';}
                }">结果</th>
                <th data-options="field:'stamp',width:170,align:'center'">最后修改时间</th>
            </tr>  
        </thead>
		</table>
		<!-- 表格 end -->
		
		<!-- 查询条件 begin -->
		<div id="toolbar" style="padding: 5px; height: auto;">
		
		    <table style=" font-size: 12px;">
		      <tr>
		        <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_search" style="width: 150px;"></td>
		        
		        <td align="right">
						经销商:
					</td>
					<td align="left">
						<select id="dealerId" name="dealerId" class="easyui-combotree" style="width: 150px;"
							data-options="onlyLeafCheck:true,cascadeCheck:true,url:'run/run_getDealerAreas4Tree.action'" multiple></select>
					</td>
		        <td align="right">软件版本号:</td>
		        <td><input id="software_search" style="width: 150px;"></td>
                  <td> 
			    </td>
                 </tr>
                 
                 <tr>
                 <td align="right">设备类型:</td>
                 <td>
      				<select id="deviceType_search" class="easyui-combobox" name="deviceType_search" style="width:150px;">  
					<option value="" >全部</option>  
					<option value="00">GPS终端</option>  
					<option value="01">控制器</option> 
					<option value="02">显示器</option> 
					<option value="03">监控器</option>  
			    	</select> 
                  </td>
                  <td align="right">终端应答:</td>
		        <td>
      				<select id="upgradeState_search" class="easyui-combobox" name="upgradeState_search" style="width:150px;">  
					<option value="" >全部</option>  
					<option value="00">接收成功</option>  
					<option value="01">校验错</option> 
					<option value="02">其他</option> 
			    	</select> 
                 
		        <td align="right">升级结果:</td>
		        <td>
				   <select id="result_search" class="easyui-combobox" name="deviceType_search" style="width:150px;">  
					<option value="">全部</option>
					<option value="00">文件格式正确</option>  
					<option value="01">不存在任何文件</option> 
					<option value="02">供应商标识不对</option> 
					<option value="03">终端型号不对</option>  
					<option value="04">程序版本不对</option> 
					<option value="05">GPS终端升级成功</option>   
					<option value="06">控制器升级成功</option>  
					<option value="07">显示器升级成功</option> 
			    	</select> 
			   </td>
		        <td> 
			    </td>
		      </tr>
		      
		       <tr>
		      <td align="right">
					开始时间:
				</td>
				<td>
				 <input id="start_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
				</td>
		      <td align="right">	
					结束时间: </td>
				<td><input id="end_time" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px">
				</td>
		      <td align="right">指令通道:</td>
		        <td>
			        <select id="pathFlag_search" class="easyui-combobox" name="pathFlag_search" style="width:150px;">  
					<option value="">全部</option> 
					<option value="0">GPRS</option>  
					<option value="1">GSM</option>  
				    </select>
			   </td>
			   <td>
			   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryUpgrade()">查询</a>
		       </td>
		      </tr>
		    </table> 
		    </div>
		    <!-- 查询条件 end -->

<script type="text/javascript">
    function show(){
        $("#loading_run").fadeOut("fast", function(){
            $(this).remove();
        });
         //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#upgrade_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei
            }); 
        
    }    
    var delayTime;
    $.parser.onComplete = function(obj){
    	$('#start_time').val(getTodayZero());
    	$('#end_time').val(new Date().formatDate(timeFormat));
    	$('#upgrade_datagrid').datagrid('columnMoving');
    	
        if(delayTime) 
            clearTimeout(delayTime);
        delayTime = setTimeout(show,1);
    };
</script>		    
