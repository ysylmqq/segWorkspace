<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/message_main.js"></script>
<link type="text/css" rel="stylesheet" href="${basePath}css/style_j.css">
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:askResize"  style="overflow:hidden;">
	<div style="margin-top:10px">
	<table style="font-size: 12px;">
	<tr>
	
	<!-- <td>工作省份：</td><td><select name="lz_sf" id="s1"> <option> </option> </select> </td>
	<td>工作城市：</td><td><select name="lz_sx" id="s2"> <option> </option> </select></td> -->
	<td><img id="testButtonTrue1" onclick="askChecked()" style="cursor: pointer;" src="images/user/t_s.png">
	<img id="testButtonFalse1" onclick="askChecked()" style="cursor: pointer;display:none" src="images/user/t_x.png"></td></tr>
	</table>
	
	</div>
	<div id="tb1" style="display: none">
    <!-- 表格 begin -->
		<table>
		<tr>
		<td width="50px">标题：</td><td> <input type="text" id="title" style="width:350px"/>
		</td>
		</tr>
		<tr>
		<td width="50px">正文：</td>
		<td>
		<textarea name="content" id ="content"  style="width:700px;height:200px;"></textarea>
		<!-- <textarea name="content" cols="125" rows="4"  id="content"> --></textarea></td>
		</tr>
		<tr>
		<td align="right" colspan="2">
		<input type="button" value="发布" onclick="askSend()" id="fb">
		<input type="button" value="编辑" onclick="askEditSend()" id="bj" style="display:none">
		<input type="button" value="返回" onclick="back()">
		</td>
		</tr>
		</table>
	</div>
	<div id="tb2">
	<table id="projectInfo_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="nowrap:false,url:'${basePath}userInfo/message_search.action',fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'消息推送发布'">
             <thead>  
            <tr >  
            	<th data-options="field:'id',width:0,hidden:true"></th>
                <th data-options="field:'title',width:90,align:'left',formatter:formatterText">消息推送标题</th>
                <th data-options="field:'content',width:150,align:'left'">内容</th>
                <th data-options="field:'createName',width:60,align:'left'">发布人</th>
                <th data-options="field:'createTime',width:60,align:'left'">发布时间</th>
            </tr>  
        </thead>
	 </table>
	 </div>
		    </div>
		</div>

	
		  
		  
		  
		  
		  
		  
	<!-- 问题回答弹出页面--begin -->	
		<div  closed="true" class="easyui-dialog"  id="projectInfo_detail" 
		style="width: 800px; height: 500px; overflow: hidden" 
		data-options="title:'工程信息',maximizable:true"
		 buttons="#question_btns">
		 <!-- 编辑时需要用的ID -->
		 <input id="hid" name="hid" type="hidden" />
		<table class="jcbLegend"  style="width: auto; height: auto;" rownumbers="true" pagination="true" >
			<tr>
			<td>
			<fieldset style="height:380px;width:740px">
			<legend class="jcb212">
				工程信息内容
			</legend>
			<div style="height:360px; width:760px; overflow-y:auto;">
			<table id="projectInfos_datagrid" style="width: 740px; height: 350px;">
			<tr valign="top" id="nodetr"></tr>
			</table>
			</div>
			</fieldset>
			</td>
			</tr>
			<tr>
			<td align="right">
			<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">发布人：</span>&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;" id="Label1"></span>&nbsp;&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">发布时间：</span>&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;" id="Label2"></span>
			</td>
			</tr>
		</table>
	</div>
	<div id="question_btns">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="editProjectInfo()">编辑</a>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#projectInfo_detail').dialog('close')">关闭</a>  
	</div>  
	<!--问题回答弹出页面--end -->	    
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#projectInfo_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei-45
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
