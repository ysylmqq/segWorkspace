<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/Unit_SetUp.js"></script>
<div id='loading2' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
    <img src='images/loading.gif'/> 
</div>
<div  class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
   <div data-options="region:'center',border:false,onResize:workhoursQueryResize"  style="overflow:hidden;">
    <!-- 表格 begin -->
    <table id="unit_detail_datagrid" toolbar="#toolbar" class="easyui-datagrid"
			style="width: auto; height: auto;" rownumbers="true"
			data-options="singleSelect:true,rownumbers:true,title:'终端设置'">
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
		       <td align="right">整机编号:</td>
		        <td><input id="vehicleDef_search" style="width: 100px;"></td>
		        <td align="right">终端编号:</td>
		        <td>
		         <input id="unitId" name="unitId" style="width: 100px;"/>
		       </td>
		      <td align="right">SIM卡号： </td>
			  <td><input id="simNo" name="simNo" style="width: 100px;"  /> </td>
			  <!-- 
			  <td align="right">车手手机号码： </td>
			  <td><input id="driverNumber" style="width: 100px;" name="driverNumber"  /> </td>
		      -->
			   <td align="right">机械状态:</td>
		        <td>
			        <select  class="easyui-combobox"  id="unitStatus" name="unitStatus"  style="width:100px;height:21px" >    
					    <option value="">全部</option>
	                    <option value="-1">停机保号</option>
	                    <option value="1">开通</option>
	                    <option value="2">续费</option>
	                    <option value="3">注销</option>		
					</select>
		       </td>
			   <td colspan="4">
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addunitSetUp()">新增</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editunitSetUp()">编辑</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:findUnitSetUp()">查询</a>
			    <a href="#" id="downBtn" class="easyui-linkbutton" iconCls="icon-exportexcel" onclick="javascript:downExcel();">导出</a>
			  </td>
		      </tr>
		    </table> 
		    </div>
		    </div>
		</div>
		
		<!-- 新增或者修改弹出框 begin-->
		<div id="dlg_unitSetUp" class="easyui-dialog" title="机械注册"
			data-options="iconCls:'icon-save',modal: true"
			style="padding: 5px; width: 600px; height: 460px;" closed="true"
			buttons="#dlg_unitSetUp_btns">
			<form id="unitSetUp_form" metdod="post" tdeme="simple"
				action="${basePatd}unit/unit_saveOrUpdate.action">
				<input id="add_unitId" name="unitId" type="hidden" />
				<table cellpadding="4" cellspacing="0" style="font-size: 12px; width: 100%;">
					<tr>
						<td align="right">
							整机编号:
						</td>
						<td align="left">
							<input id="add_vehicleDef" name="vehicleDef" type="text" class="easyui-validatebox" data-options="required:true,validType:'length[10,11]'" onBlur="checkDef(this.value)" />
							<span style="color: red">*</span>
						</td>

					</tr>
					
					<tr>
						<td align="right">
							机械状态:
						</td>
						<td align="left">
							<select  class="easyui-combobox"  id="add_unitStatus" name="unitStatus"  style="width:150px;height:21px" >    
							    <option value="">全部</option>
			                    <option value="-1">停机保号</option>
			                    <option value="1">开通</option>
			                    <option value="2">续费</option>
			                    <option value="3">注销</option>		
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="dlg_unitSetUp_btns">
			<a href="#" id="sava" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="saveunitSetUp()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#dlg_unitSetUp').dialog('close')">取消</a>
		</div>	
		
		
		
	<script type="text/javascript"  >
    function show(){
        $("#loading2").fadeOut("normal", function(){
            $(this).remove();
            //修改表格的宽度
            var wid =$(document.body).width();
            var hei =$('#main').height();
             $('#unit_detail_datagrid').datagrid('resize', {  
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
