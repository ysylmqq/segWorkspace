var drawShaps = {
		circles: [],
		rects: [],
		polygons: []
	};
//查询区域
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
		 $.post("run/fence_getById.action", {
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
	$.post('run/fence_saveOrUpdate.action',
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
//显示区域及机械
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
    $('#pointstr',$('#store_operate_form')).focus();
	$('#dlg_area_rect').dialog('close');
}

//删除库存区域
function deleteStoreArea(areapointId,areapointName){
	 $.messager.confirm(tipMsgDilag,"确定删除,删除后不可恢复?",
			   function(r){  
				    if (r){ 
						$.post("run/fence_delete.action", {
							areapointId:areapointId
						}, function(data) {
							var r = $.parseJSON(data);
								if (r.success) {
									$('#store_grid').datagrid('reload');
                                    $('#vehicle_grid').datagrid('load', {
                                        areapointId : areapointId,
                                        page:1,
                                        rows:10
                                     });
								}
								$.messager.alert(tipMsgDilag, r.msg);
						});
					}});
}

function bindingVehciles(areapointId,areapointName){
    $('#dlg_binding_vehciles').dialog('setTitle',"机械绑定———"+areapointName);
    $('#dlg_binding_vehciles').dialog('open');
    $('#dlg_binding_vehciles_btns').show();
    initRunTree();
    $('#binding_areapointId').attr('value',areapointId);
}
function initRunTree(){
    $('#run_tree').tree({   
                 checkbox: true,   
                 onBeforeExpand:function(node,param){  
                    if(node.attributes){//异步加载
                       if(node.attributes.dtype){
                            $('#run_tree').tree('options').url = "run/run_getDealerAreasList.action?dealerAreaPOJO.dtype="+node.attributes.dtype;
                       }
                    }
                    // 
                 },               
                onClick:function(node){
                    if(!node.state){
                    
                    }
                },
                onDblClick:function(node){
                    if(!node.state){//如果没有state属性，则表示是叶子节点
                        $('#run_tree').tree('select', node.target);
                    }else{
                        $('#run_tree').tree('expand', node.target).tree('select', node.target);
                    }
                },
                onContextMenu : function(e, node) {
			       e.preventDefault();
			       $('#run_tree').tree('select', node.target);
			       $('#run_tree').tree('check', node.target);
                   if(!node.state){
			           $('#menu_binding_right').menu('show', {
						    left : e.screenX,
						    top : e.screenY
					    });
                   }

		        },
                onCheck:function(node, checked){
                },
                onSelect:function(node){
                    
                }               
            });
}
//获得选中的机械
function getCheckedTree(){
   var nodes = $('#run_tree').tree('getChecked');
    var result=[];
    var s = '';
    for(var i=0; i<nodes.length; i++){
        if (s != '') {
        s += ',';
        }
        s += nodes[i].text;
        if(nodes[i].attributes){
            if(!nodes[i].state){//如果是机械
                result.push(nodes[i].attributes);
            }
        }
    }
    return result;
}

function add_binding_vehciles(){
    var datas = [];
    var nodes = $('#run_tree').tree('getChecked');
    for(var i=0; i<nodes.length; i++){
      if(nodes[i].attributes){
        if(!nodes[i].state){
             var obj=new Object();
            obj.vehicleId = nodes[i].attributes.vehicleId;
            obj.vehicleDef = nodes[i].attributes.vehicleDef;
            datas.push(obj);
            }
        }
        
    }
    addVehiclesToGrid(datas);
}
function showExistRect(rec){
		// 矩形
		var lon = rec.lon;
		var lat = rec.lat;
		var lon2 = rec.lon2;
		var lat2 = rec.lat2;
		
		if(typeof ifm_map_store.window.showAreaAndVehicles1=='function'){
			ifm_map_store.window.showAreaAndVehicles1(lon,lat,lon2,lat2,rec.areapointId,rec.areapointName,rec.mlevel);	
		}
}

function saveBindingvehciles(){
    var rows = $("#vehicles_grid").datagrid("getRows");
    if(!rows){
         $.messager.alert(tipMsgDilag,"请先添加机械再保存！");
         return false;
    }
    var vehicleIds=[];
    for(var i=0;i<rows.length;i++)
    {
        vehicleIds.push(rows[i].vehicleId);
    }
    
    $('#save_binding_vehciles_btn').linkbutton('disable');
    $.ajax({
        url:"run/fence_saveAreaVehicles.action",
        type: "POST",
        data : {   
            areapointId : $('#binding_areapointId').val(),
            vehicleIds:vehicleIds.toString()
        },
        success: function (result) {
           var r = $.parseJSON(result);
           $.messager.alert(tipMsgDilag, r.msg);
           $('#vehicles_grid').datagrid('load',{total:0,rows:[]});
           $('#vehicle_grid').datagrid('load', {
                            areapointId : $('#binding_areapointId').val(),
                            page:1,
                            rows:10
                        });
           $('#save_binding_vehciles_btn').linkbutton('enable');
        },
        error : function(jqXMLRequest, textStatus, errorThrown){
           $('#save_binding_vehciles_btn').linkbutton('enable');
           $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
       }
    });    
}

function relieveBinding(area_id,vehicle_id,row_index){
    $.ajax({
        url:"run/fence_relieveAreaVehicle.action",
        type: "POST",
        data : {   
            area_id : area_id,
            vehicle_id:vehicle_id
        },
        success: function (result) {
           var r = $.parseJSON(result);
           $.messager.alert(tipMsgDilag, r.msg);
           $('#vehicle_grid').datagrid('load', {
                            areapointId : area_id,
                            page:1,
                            rows:10
                        });
        },
        error : function(jqXMLRequest, textStatus, errorThrown){
           $.messager.alert(tipMsgDilag, "错误类型：" + textStatus + "<br />错误信息：" + errorThrown, 'error');
       }
    });
}
//清除
function clearArea(){
	if(typeof ifm_map_store.window.clearRects=='function'){
		ifm_map_store.window.clearRects();	
	}
}
//添加数据到机械表格中
function addVehiclesToGrid(datas){
	//先删除所有行
	var data = $('#vehicles_grid').datagrid('getData');
	for (var index = 0; index < data.total; ) {
		var index2 = $('#vehicles_grid').datagrid('getRowIndex',data.rows[index]);//获取某行的行号
		$('#vehicles_grid').datagrid('deleteRow',index2);	//通过行号移除该行
		index=0;
	}
	//再添加
	for(var i=0;i<datas.length;i++){        
        $('#vehicles_grid').datagrid('appendRow',datas[i]);
    }
}

$('#store_grid').datagrid({
			onDblClickRow : function(rowIndex, rowData) {
				showExistRect(rowData);
				$('#vehicle_grid').datagrid('load', {
							areapointId : rowData.areapointId,
                            page:1,
                            rows:10
						});
			}
		} );
  
  $('#vehicles_grid').datagrid({  
  onDblClickRow: function (rowIndex, rowData) {
      $('#vehicles_grid').datagrid('deleteRow',rowIndex);
  }
  } );