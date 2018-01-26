<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${basePath}js/unit.js"></script> 
<script type="text/javascript" src="${basePath}easyui/easyui.datagrid.columnMoving.js"></script> 
<div id="tabs" class="easyui-tabs" style="width:800px;height:550px;">
    <div title="用户缴费" style="padding:20px;display:none;">
        tab1
    </div>
    <div title="公司缴费" data-options="closable:true" style="overflow:auto;padding:20px;display:none;">
        tab2
    </div>
    <div title="查询" data-options="iconCls:'icon-reload',closable:true" style="display:none;">
        tab3
    </div>
</div>


<script type="text/javascript">
;(function($){
	var operaManage = {
		init:function(){
			//初始化时创建 tabs
			$('#tabs').tabs({
				tools:[{
					iconCls:'icon-add',
					handler:function(){
						console.log('add')
					}
				},{
					iconCls:'icon-save',
					handler:function(){
						console.log('save')
					}
				}]
			});		
		}
	};
	operaManage.init();

})(jQuery);
</script>