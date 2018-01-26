<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-scrollview.js"></script>
<div class="easyui-layout" style="width: 100%; height: 100%;">
	<div data-options="region:'north',title:'终端测试'"></div>
	<div data-options="region:'west',split:false" title="终端列表" style="width: 228px;height:100%">
		<!-- 搜索 -->
		<input id="ss" class="easyui-searchbox" style="width:228px;padding:2px;"  
		        data-options="searcher:queryUnitBySimNoOrUnitSn,prompt:'SIM卡号或终端序列号',menu:'#ops'"></input>  
		<div id="ops" style="width:120px">  
			<div data-options="name:'all_unit'">所有</div>
		    <div data-options="name:'simNo_search'">SIM卡号</div>  
		    <div data-options="name:'unitSn_search'">终端序列号</div>  
		</div> 		
		<div class="easyui-tabs" style="width:218px;height:auto" fit="true">
			<%-- 为datagrid运用虚拟滚动特性,'view' 属性应该设置为 'scrollview' 需要引入js扩展文件 --%> 
	        <div title="终端搜索" >
			   <table id="unitListTab"  class="easyui-datagrid"  style="width:216px;height:auto;padding:1px;" fit="true" rownumbers="true" 
				data-options="remoteSort:false,view:scrollview,rownumbers:true,url:'${basePath}unit/unit_search.action',singleSelect:true,autoRowHeight:false,pageSize:150,title:'终端列表'">  
				    <thead>    
				        <tr>
				           <th data-options="field:'unitSn',width:100" >终端序列号</th>    
				           <th data-options="field:'simNo',width:100" >SIM卡</th>
				           <th data-options="field:'unitTypeSn',width:0,hidden:true"></th>
				        </tr>    
				    </thead>    
				</table>
	        </div>  
    	</div>  
	</div>
	<div data-options="region:'center',title:'',iconCls:'icon-ok'">
		<div id="p" class="easyui-panel" title="调试相关" style="width:auto;height:auto;border:false"  fit="true" 
        data-options="closable:true,  
                collapsible:true,minimizable:true,maximizable:true">
			<div class="easyui-accordion" style="width:auto;height:auto;" fit="true">    
			    <div title="添加调试信息" selected="true">    
			        <!-- 表单 -->
			    </div>    
			    <div title="调试记录"  style="padding:2px;">    
             		<div class="easyui-tabs" style="width:218px;height:auto" fit="true">
						<%-- 为datagrid运用虚拟滚动特性,'view' 属性应该设置为 'scrollview' 需要引入js扩展文件 --%> 
				        <div title="调试信息" >
						   <table id="unitListTab"  class="easyui-datagrid"  border="false" style="height:auto;padding:1px;" fit="true" rownumbers="true" 
							data-options="remoteSort:false,view:scrollview,rownumbers:true,url:'',singleSelect:true,autoRowHeight:false,pageSize:150,title:''">  
							    <thead>    
							        <tr>
							           <th data-options="field:'unitSn',width:50" >测试分类</th>    
							           <th data-options="field:'simNo',width:120" >测试人</th>
							           <th data-options="field:'simNo',width:100" >测试时间</th>
							           <th data-options="field:'simNo',width:50" >锁车</th>
							           <th data-options="field:'simNo',width:50" >解锁</th>
							           <th data-options="field:'simNo',width:80" >定位</th>
							           <th data-options="field:'simNo',width:100" >短信</th>
							           <th data-options="field:'simNo',width:200" >ACC变化上报</th>
							           <th data-options="field:'simNo',width:30" >登录</th>
							           <th data-options="field:'simNo',width:50" >备电功能</th>
							           <th data-options="field:'simNo',width:100" >备注</th>
							        </tr>    
							    </thead>    
							</table>
				        </div>  
				        
				        <div title="测试指令列表" >
						   <table id="unitListTab"  class="easyui-datagrid" border="false"  style="height:auto;padding:1px;" fit="true" rownumbers="true" 
							data-options="remoteSort:false,view:scrollview,rownumbers:true,url:'',singleSelect:true,autoRowHeight:false,pageSize:150,title:''">  
							    <thead>    
							        <tr>
							           <th data-options="field:'unitSn'" >测试项</th>    
							           <th data-options="field:'simNo'" >返回/发送结果</th>
							        </tr>    
							    </thead>    
							</table>
				        </div>  
			    	</div>   
			    </div>    
			</div> 
		</div> 
	</div>
</div>
