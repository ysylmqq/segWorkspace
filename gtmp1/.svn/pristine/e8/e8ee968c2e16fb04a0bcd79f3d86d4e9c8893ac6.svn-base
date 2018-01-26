var drawShaps = {
		circles: [],
		rects: [],
		polygons: []
	};
//查询库存区域
function queryStore(){
	$('#store_grid').datagrid('load',{    
		areapointName: $('#areapointName_search').val(),    
		remark: $('#remark_search').val()  
    });  
}
//新增/编辑库存区域弹出框
function openStoreAreaWin(areapointId,viewFlag){
	$('#drawRect_btn').show();
	$('#dlg_store_operate').dialog('open');
	if(areapointId){
		 $.post("vehicle/areaPoint_getById.action", {
			 areapointId:areapointId
			}, function(data) {
				var obj = $.parseJSON(data);
				var pointStr=obj.lon+',';
				pointStr+=obj.lat+";";
				pointStr+=obj.lon2+",";
				pointStr+=obj.lat2;
				obj.pointstr=pointStr;
				$('#store_operate_form').form('load', obj);
			});
		 
		if(viewFlag==1){//详细
			 $('#areapointName').attr('disabled',true);
		     $('#remark').attr('disabled',true);
		     $('#pointstr').attr('disabled',true);
		     $('#drawRect_btn').hide();
		     $('#save_store_btn').linkbutton('disable');
		}else{//编辑
			 $('#areapointName').attr('disabled',false);
		     $('#remark').attr('disabled',false);
		     $('#pointstr').attr('disabled',false);
		     $('#save_store_btn').linkbutton('enable');
		}
	}else{//新增
		$('#store_operate_form').form('clear');
	}
	
}
//保存库存区域
function saveStore(){
	if(!$('#store_operate_form').form('validate')){
	     return;
	}
	var pointStr=$('#pointstr').val();
	var lon=null,lat=null,lon2=null,lat2=null;
	if(pointStr){
		var point=pointStr.split(";");
		if(point&&point.length>1){
			var lonLat=point[0].split(",");
			if(lonLat&&lonLat.length>1){
				lon=lonLat[0];
				lat=lonLat[1];
			}
			lonLat=point[1].split(",");
			if(lonLat&&lonLat.length>1){
				lon2=lonLat[0];
				lat2=lonLat[1];
			}
		}
	}
	var params={
			'areapointId':$('#areapointId').val(),
			'areapointName':$('#areapointName').val(),
			'remark':$('#remark').val(),
			'shapetype':$('#shapetype').val(),
			lon:lon,
			lat:lat,
			lon2:lon2,
			lat2:lat2,
			mlevel:ifm_map_store.window.getMapLevel()
	};
	$.post('vehicle/areaPoint_saveOrUpdate.action',
			params,
			function(result){
  	  	     try {
					var r = $.parseJSON(result);
					if (r.success) {
						$('#dlg_store_operate').dialog('close');
						$('#store_grid').datagrid('reload');
					}
					$.messager.alert(tipMsgDilag, r.msg);
				} catch (e) {
					$.messager.alert(tipMsgDilag, '失败!');
				}
	}
	);
}
//显示库存区域及机械
function displayArea(){
	var rec=$('#store_grid').datagrid("getSelected");
	if(rec==null){
		$.messager.alert(tipMsgDilag, "请选择要显示的区域!");
		return ;
	}
	showExistRect(rec);
}
var RectWinClose=function(){
	if(!rect){
		if(typeof ifm_map_store.window.removeRecMarker=='function'){
			ifm_map_store.window.removeRecMarker(rect);	
		}
	}
};
//打开画完矩形后的回写窗口
function openRectWin(opts,rect){
	$('#dlg_area_rect').dialog({
		onClose:function(){
			if(rect){
				if(typeof ifm_map_store.window.removeRecMarker=='function'){
					ifm_map_store.window.removeRecMarker(rect);	
				}
    		}
		}
	});
	$('#dlg_area_rect').dialog('open');
	$('#frm_area_rect').form('load',opts);
}
//画图回写的参数设定窗口确定
function saveParams(){
	if(!$('#frm_area_rect').form('validate')){
	     return;
	}
	var params=$('#lon',$('#frm_area_rect')).val()+',';
	params+=$('#lat',$('#frm_area_rect')).val()+";";
	params+=$('#lon2',$('#frm_area_rect')).val()+",";
	params+=$('#lat2',$('#frm_area_rect')).val();
	$('#pointstr',$('#store_operate_form')).val(params);
	$('#dlg_area_rect').dialog('close');
}

//删除库存区域
function deleteStoreArea(areapointId,areapointName){
	 $.messager.confirm(tipMsgDilag,"确定删除,删除后不可恢复?",
			   function(r){  
				    if (r){ 
						$.post("vehicle/areaPoint_delete.action", {
							areapointId:areapointId
						}, function(data) {
							var r = $.parseJSON(data);
								if (r.success) {
									$('#store_grid').datagrid('reload');
								}
								$.messager.alert(tipMsgDilag, r.msg);
						});
					}});
}

function showExistRect(rec){
		//矩形			
		var lon = rec.lon;
		var lat = rec.lat;
		var lon2 = rec.lon2;
		var lat2 = rec.lat2;
		
		if(typeof ifm_map_store.window.showAreaAndVehicles=='function'){
			ifm_map_store.window.showAreaAndVehicles(lon,lat,lon2,lat2,rec.areapointId,rec.areapointName,rec.mlevel);	
		}
}
//清除
function clearArea(){
	if(typeof ifm_map_store.window.clearRects=='function'){
		ifm_map_store.window.clearRects();	
	}
}
//添加数据到机械表格中
function addVehiclesToGrid(datas,areapointName){
	//先删除所有行
	var data = $('#vehicle_grid').datagrid('getData');
	for (var index = 0; index < data.total; ) {
		var index2 = $('#vehicle_grid').datagrid('getRowIndex',data.rows[index]);//获取某行的行号
		$('#vehicle_grid').datagrid('deleteRow',index2);	//通过行号移除该行
		index=0;
	}
	//再添加
	addVehicleToGrid(datas,areapointName);
}
function addVehicleToGrid(datas,areapointName){
	for(var i=0;i<datas.length;i++){
		datas[i].areapointName=areapointName;
		$('#vehicle_grid').datagrid('appendRow',datas[i]);
	}
}