<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/ask.js"></script>
<link type="text/css" rel="stylesheet" href="${basePath}css/style_j.css">
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:askResize"  style="overflow:hidden;">
	<div style="margin-top:10px">
	<img id="testButtonTrue1" onclick="askChecked()" style="cursor: pointer;" src="images/user/s.png">
	<img id="testButtonFalse1" onclick="askChecked()" style="cursor: pointer;display:none" src="images/user/x.png">
<!-- 	<input type="button" value="问题发布" onclick="askChecked()"/> -->
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
		<tr><td align="right" colspan="2"><input type="button" value="发布" onclick="askSend()"></td></tr>
		</table>
	</div>
	<div id="tb2">
	<table id="question_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="nowrap:false,url:'${basePath}userInfo/userInfo_search.action',fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'问题反馈'">
             <thead>  
            <tr >  
            	<th data-options="field:'id',width:0,hidden:true"></th>
                <th data-options="field:'textContent',width:90,align:'left',formatter:formatterText">问题标题</th>
                <th data-options="field:'answerName',width:90,align:'left',formatter:formatterAnswerName">是否有回复</th>
                <th data-options="field:'userName',width:90,align:'left'">提问人</th>
                <th data-options="field:'lastAskDate',width:90,align:'left'">提问时间</th>
            </tr>  
        </thead>
	 </table>
	 </div>
		    </div>
		</div>

	
		  
		  
		  
		  
		  
		  
	<!-- 问题回答弹出页面--begin -->	
		<div  closed="true" class="easyui-dialog"  id="question_detail" 
		style="width: 800px; height: 500px; overflow: hidden" 
		data-options="title:'问题返馈',maximizable:true"
		 buttons="#question_btns">
		<table  style="width: auto; height: auto;" rownumbers="true" pagination="true" >
			<tr>
			<td>
			<fieldset style="height:180px;width:740px">
			<legend >
				问题内容
			</legend>
			<div style="height:160px; width:740px; overflow-y:auto;">
			<table id="questions_datagrid" style="width: 720px; height: auto;">
			<tr id="nodetr">
			<td></td>
			</tr>
			</table>
			</div>
			</fieldset>
			</td>
			</tr>
			<tr>
			<td align="right">
			<span style="font-size: 12px;font-weight:bold;color: blue;border: 1px solid blue">提问人：</span>&nbsp;<span style="font-size: 12px;font-weight:bold;color: blue;" id="Label1"></span>&nbsp;&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">提问时间：</span>&nbsp;<span style="font-size: 12px;font-weight:bold;color: blue;" id="Label2"></span>
			</td>
			</tr>
			<tr>
			<td>
			<fieldset style="height:170px;width:740px">
			<legend>
				回复内容
			</legend>
			<div style="height:130px; width:740px; overflow-y:auto;">
			<table id="answer_datagrid" style="width: 740px; height: 110px;">
			<tr><td>
			<textarea name="textAnswer" cols="85" rows="6"  id="textAnswer"></textarea>
			<td>
			<tr>
			</table>
			</div>
			</fieldset>
			</td>
			</tr>
			<tr>
			<td align="right">
			<span style="font-size: 12px;font-weight:bold;color: blue;border: 1px solid blue">回复人：</span>&nbsp;<span style="font-size: 12px;font-weight:bold;color: blue;" id="Label3"></span>&nbsp;&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">回复时间：</span>&nbsp;<span style="font-size: 12px;font-weight:bold;color: blue;" id="Label4"></span>
			</td>
			</tr>
		</table>
	</div>
	<div id="question_btns">
	
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#question_detail').dialog('close')">关闭</a>  
	</div>  
	<!--问题回答弹出页面--end -->	
	    
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#question_datagrid').datagrid('resize', {  
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
