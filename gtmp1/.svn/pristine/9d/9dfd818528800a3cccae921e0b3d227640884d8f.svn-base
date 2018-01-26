<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/reseach.js"></script>
<link type="text/css" rel="stylesheet" href="${basePath}css/style_j.css">
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:askResize"  style="overflow:hidden;">
	<div style="margin-top:10px">
	<img id="testButtonTrue1" onclick="askChecked()" style="cursor: pointer;" src="images/user/s.png">
	<img id="testButtonFalse1" onclick="askChecked()" style="cursor: pointer;display:none" src="images/user/x.png">
	</div>
	<div id="tb1" style="display: none">
    <!-- 表格 begin -->
		<table>
		<tr>
		<td width="50px">标题：</td><td> <input type="text" id="textContent" style="width:350px"/>
		</td>
		</tr>
		<tr>
		<td width="50px">正文：</td>
		<td>
		<textarea name="content" id ="content"  style="width:700px;height:200px;"></textarea>
		<!-- <textarea name="content" cols="125" rows="4"  id="content"> --></textarea></td>
		</tr>
		<tr>
		<td align="right" colspan="2"><input type="button" value="发布" onclick="askSend()" id="fb">
		<input type="button" value="编辑" onclick="askEditSend()" id="bj" style="display:none">
		<input type="button" value="返回" onclick="back()">
		</td>
		</tr>
		</table>
	</div>
	<div id="tb2">
	<table id="reseach_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="nowrap:false,url:'${basePath}userInfo/reseach_search.action',
			fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'调查问卷发布'">
             <thead>  
            <tr >  
            	<th data-options="field:'id',width:0,hidden:true"></th>
                <th data-options="field:'textContent',width:90,align:'left',formatter:formatterText">调查问卷标题</th>
                <th data-options="field:'count',width:90,align:'left',formatter:formatterCount">回复条数</th>
                <th data-options="field:'userName',width:90,align:'left'">发布人</th>
                <th data-options="field:'lastAskDate',width:90,align:'left'">发布时间</th>
            </tr>  
        </thead>
	 </table>
	 </div>
		    </div>
		</div>


 <!-- 回复条数明细 begin-->
			<div id="reseachList" class="easyui-dialog" title="详细信息" data-options="iconCls:'icon-recycle',modal:true,maximizable:true,closed:true,buttons:'#reseachList_btns',onResize:vehicleDlgResize"
				style="width:800px;height:500px;">
			<div id="reseachList_btns">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#reseachList').dialog('close')">关闭</a>
			</div>
		    <table id="reseachList_datagrid" class="easyui-datagrid" style="padding-top:0px;margin-top:0px;height:430px;width:auto"
					data-options="nowrap:false,url:'${basePath}userInfo/reseach_reseachList.action',fitColumns:false,singleSelect:false,rownumbers:true,pagination:true,fit:true">
		    	<thead>  
		            <tr> 
		                <th data-options="field:'answerName',width:100,align:'center'">回复人名字</th>
		                <th data-options="field:'textAnswer',width:520,align:'center'">回复内容</th>
		                <th data-options="field:'lastAnswerDate',width:140,align:'center'">最后回复日期</th>
		            </tr>  
		        </thead>
			</table>
			</div>
	 <!-- 回复条数明细 end-->
		  
		  
		  
		  
		  
		  
	<!-- 问题回答弹出页面--begin -->	
		<div  closed="true" class="easyui-dialog"  id="reseach_detail" 
		style="width: 800px; height: 500px; overflow: hidden" 
		data-options="title:'工程信息',maximizable:true"
		 buttons="#reseach_btns">
		 <!-- 编辑时需要用的ID -->
		 <input id="hid" name="hid" type="hidden" />
		<table  style="width: auto; height: auto;" rownumbers="true" pagination="true" >
			<tr>
			<td>
			<fieldset style="height:380px;width:740px">
			<legend style="">
				工程信息内容
			</legend>
			<div style="height:360px; width:760px; overflow-y:auto;">
			<table id="reseachs_datagrid" style="width: 740px; height: 350px;">
			<tr valign="top" id="nodetr"></tr>
			</table>
			</div>
			</fieldset>
			</td>
			</tr>
			<tr>
			<td align="right">
			<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">发布人：</span>&nbsp;<label style="font-size: 12px;font-weight:bold;color: #B55EC5;" id="Label1"></label>&nbsp;&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">发布时间：</span>&nbsp;<label style="font-size: 12px;font-weight:bold;color: #B55EC5;" id="Label2"></label>
			</td>
			</tr>
		</table>
	</div>
	<div id="reseach_btns">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="editReseach()">编辑</a>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#reseach_detail').dialog('close')">关闭</a>  
	</div>  
	<!--问题回答弹出页面--end -->	      
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#reseach_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei-40
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
