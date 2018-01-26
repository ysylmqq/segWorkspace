<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/reseach_answer.js"></script>
<link type="text/css" rel="stylesheet" href="${basePath}css/style_j.css">
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:askResize"  style="overflow:hidden;">
	<div id="tb2">
	<table id="reseachAnswer_datagrid"  toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true" pagination="true" 
			data-options="nowrap:false,url:'${basePath}userInfo/reseachAnswer_search.action',fitColumns:true,singleSelect:true,rownumbers:true,pagination:true,title:'调查问卷'">
             <thead>  
            <tr >  
            	<th data-options="field:'id',width:0,hidden:true"></th>
                <th data-options="field:'textContent',width:90,align:'left',formatter:formatterText">调查问卷标题</th>
                <th data-options="field:'userName',width:90,align:'left'">发布人</th>
                <th data-options="field:'lastAskDate',width:90,align:'left'">发布时间</th>
               <!--  <th data-options="field:'answerName',width:90,align:'left'">回复人</th>
                <th data-options="field:'lastAnswerDate',width:90,align:'left'">最后回复时间</th> -->
            </tr>  
        </thead>
	 </table>
	 </div>
		    </div>
		</div>
		  
	<!-- 问题回答弹出页面--begin -->	
		<div  closed="true" class="easyui-dialog"  id="reseachAnswer_detail" 
		style="width: 800px; height: 500px; overflow: hidden" 
		data-options="title:'调查问卷',maximizable:true"
		 buttons="#reseachAnswer_btns">
		 <input id="hId" name="hId" type="hidden" />
		<table  style="width: auto; height: auto;" rownumbers="true" pagination="true" >
			<tr>
			<td>
			<fieldset style="height:180px;width:740px">
			<legend>
				调查问卷内容
			</legend>
			<div style="height:160px; width:740px; overflow-y:auto;">
			<table id="question_datagrid" style="width: 720px; height: auto;">
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
			<span style="font-size: 12px;font-weight:bold;color: blue;border: 1px solid blue">发布人：</span>&nbsp;<label style="font-size: 12px;font-weight:bold;color: blue;" id="Label1"></label>&nbsp;&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">发布时间：</span>&nbsp;<label style="font-size: 12px;font-weight:bold;color: blue;" id="Label2"></label>
			</td>
			</tr>
			<tr>
			<td>
			<fieldset style="height:170px;width:740px">
			<legend>
				调查问卷回复
			</legend>
			<div style="height:130px; width:740px; overflow-y:auto;">
			<table id="reseachAnswers_datagrid" style="width: 740px; height: 110px;">
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
			<span style="font-size: 12px;font-weight:bold;color: blue;border: 1px solid blue">回复人：</span>&nbsp;<label style="font-size: 12px;font-weight:bold;color: blue;" id="Label3"></label>&nbsp;&nbsp;<span style="font-size: 12px;font-weight:bold;color: #B55EC5;border: 1px solid #B55EC5">回复时间：</span>&nbsp;<label style="font-size: 12px;font-weight:bold;color: blue;" id="Label4"></label>
			</td>
			</tr>
		</table>
	</div>
	<div id="reseachAnswer_btns">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="edSave()">保存</a>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#reseachAnswer_detail').dialog('close')">关闭</a>  
	</div>  
	<!--问题回答弹出页面--end -->	    
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#reseachAnswer_datagrid').datagrid('resize', {  
             width : wid-2  ,
              height:hei-5
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
