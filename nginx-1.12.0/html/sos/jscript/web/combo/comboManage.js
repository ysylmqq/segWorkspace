(function($) {
	
	
	var loadSuccess = function() {
		// 填充修改页面编辑前的值
		if (editId && editObj) {
			$("#id", $("#combo_add")).val(editObj.id);
			$("#name", $("#combo_add")).val(editObj.name);
			$("#code", $("#combo_add")).val(editObj.code);
			$("#type", $("#combo_add")).val(editObj.type);
			$("#talk_time", $("#combo_add")).val(editObj.talk_time);
			$("#flow", $("#combo_add")).val(editObj.flow);
			$("#fee", $("#combo_add")).val(editObj.fee);

		}

		editId = null;
		editObj = null;
	}

	var winClose1 = function() {// 关闭添加页面
		$(document).sgWindow('close', {
			id : 'add_combo'
		});
	}

	var winClose2 = function() {// 关闭查看页面
		$(document).sgWindow('close', {
			id : 'modify_combo'
		});
	}

	var winClose3 = function() {// 关闭修改页面
		$(document).sgWindow('close', {
			id : 'modify_service'
		});
	}
	
	
	var save = function() {
		// 保存服务项
		var params = {};
		var url = '../../../combo/addCombo';
		var id = $('#id', '#combo_add').val();
		if(id){
			params.id = id;
		    url = '../../../combo/updateCombo';
		}
		params.name = $('#name', '#combo_add').val();
		params.code = $('#code', '#combo_add').val();
		params.type = $('#type', '#combo_add').val();
		params.talk_time = $('#talk_time', '#combo_add').val();
		params.flow = $('#flow', '#combo_add').val();
		params.fee = $('#fee', '#combo_add').val();
		$(document).sgConfirm(
				{
					text : '确定保存该套餐吗?',
					cfn : function(r) {
						if (r) {
							$.ajax({
								type : 'post',
								async : false,
								contentType : 'application/json',
								dataType : 'json',
								url : url,
								data : JSON.stringify(params),
								success : function(data) {
									if (data) {
										if (data.success) {

											$('#monitoring').sgDatagrid(
													'reload', 'sgDatagrid');

										}
										$(document).sgPup({
											message : 'message_alert',
											text : data.msg
										});
									}
								},
								error : function(res, error) {
									alert(res.responseText);
								}
							});
						}
					}
				});

		$('#modify_combo').unbind(); // 以下两行可以阻止提交2次，暂时注释，也不会提交2次
		$('#add_combo').unbind(); 
		$(document).sgWindow('close', {
			id : 'add_combo'
		});
		$(document).sgWindow('close', {
			id : 'modify_combo'
		});
		return false;
	};
	
	
	
	
	var winClose1 = function() {// 关闭添加页面
		$(document).sgWindow('close', {
			id : 'add_combo'
		});
	}
	

	var winClose2 = function() {// 关闭添加页面
		$(document).sgWindow('close', {
			id : 'modify_combo'
		});
	}
	
	var addItem = function() {// 添加服务项
		var defaults = {
			title : '添加套餐',
			id : 'add_combo',
			form : 'combo_form',
			url : 'combo_add.html',
			width : 340,
			height : 230,
			buttons : [ {
				name : '保存',
				type : 'submit',
				onpress : save,
			}, {
				name : '关闭',
				onpress : winClose1,
			} ]
		};
		$(document).sgWindow(defaults);
	}
	
	
	
    var endItem = function(){
        var obj = $('#monitoring');
        var bDiv = $('.bDiv',obj);

        if($('input[type=checkbox][checked=checked]',bDiv)!=null&&$('input[type=checkbox][checked=checked]',bDiv).length==0){
            $(document).sgPup({message:'message_info',text: "请选择一个选项！"});
        }else if($('input[type=checkbox][checked=checked]',bDiv)!=null&&$('input[type=checkbox][checked=checked]',bDiv).length>0){
       	 $(document).sgConfirm({text: '确定删除吗?',cfn:function(r){ 
		    if(r){
     		var flag = false;
     		$('input[type=checkbox][checked=checked]',bDiv).each(function(){
	                if($(this).attr("checked")){    
	                	editId=this.value;
	                	editobj = $(this).data('data');
	                		var pids=[];
	                		pids.push(editobj.id);
		                	//打开窗口
		                	$.ajax( {
           					  type : 'post', 
			            		  async: false,   
			            		  dataType : 'json',     
			            		  url : '../../../combo/delete',   
			            		  data:{ids:pids},
			            		  success : function(data) {
			            			  if(data){
			            				 if(data.success){
			            					 $(document).sgPup({message:'message_info',text: "操作成功！"});
			            				 }else{
			            					 $(document).sgPup({message:'message_info',text: "操作失败！"});
			            				 }
			            			  }
			            		  } ,     
			            		  error : function(res,error) { 
			            		  	     if(res && res.responseText){ $(document).sgPup({message:'message_info',text: res.responseText});}     
			            		  }    
			            		}); 
	                }
	            });
     		$('#monitoring').sgDatagrid('reload','sgDatagrid');
     	}	
     	 }});
       	 
        }
    }
        

	
	
	var modifyItem = function() {
		var obj = $('#monitoring');
		var bDiv = $('.bDiv', obj);
		if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length > 1) {
			$(document).sgPup({
				message : 'message_info',
				text : "只能选择一条记录！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 0) {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		} else if ($('input[type=checkbox][checked=checked]', bDiv) != null
				&& $('input[type=checkbox][checked=checked]', bDiv).length == 1) {
			$('input[type=checkbox][checked=checked]', bDiv).each(function() {
				if ($(this).attr("checked")) {
					editId = this.value;
					editObj = $(this).data('data');
					var defaults = {
						title : '套餐修改',
						id : 'modify_combo',
						form : 'modify_combo_form',
						url : 'combo_add.html',
						success : loadSuccess,
						width : 420,
						height : 220,
						buttons : [ {
							name : '保存',
							type : 'submit',
							onpress : save
						}, {
							name : '关闭',
							onpress : winClose2
						} ]
					};
					$(document).sgWindow(defaults);
				}
			});
		} else {
			$(document).sgPup({
				message : 'message_info',
				text : "请选择一个选项！"
			});
		}

	}


 	var height = 476;
 	//初始化表格
 	var defaults = {
 		        title: "套餐管理",
 		        width:  970,
 		        height: height,
 		        rownumbers:true,
 		        usepager: true,
 		        url:'../../../combo/findComboByPage',
 		        useRp: true,
 		        colid:'id',  //主键
 		        colModel : [
 		                {display: '套餐编号', name : 'code', width : '18%', sortable : false},
						{display: '套餐名', name : 'name', width : '18%', sortable : false},
						{display: '通话时长', name : 'talk_time', width : '18%', sortable : false},
						{display: '流量', name : 'flow', width : '18%', sortable : false},
						{display: '套餐费用', name : 'fee', width : '18%', sortable : false}
 		        ],
 		       buttons : [ {
 					name : '添加',
 					bclass : 'add',
 					onpress : addItem
 				},{
 					name : '编辑',
 					bclass : 'edit',
 					onpress : modifyItem
 				},{name: '删除', bclass: 'delete', onpress : endItem}
	             ],
 		        searchitems :[
 		                    {display:'套餐编号',name:'code',type:'text',width:'100'},
 		                    {display:'套餐名称',name:'name',type:'text',width:'100'}
 		    		        ],
 		    		       // exporturl:'../../policy/exportExcelPolicysNew',
 		      		     	order:true,
 		      		        sortname: "stamp",
 		      		        sortorder: "desc"
 		    };
 		    $('#monitoring').sgDatagrid(defaults);
	
	
})(jQuery);