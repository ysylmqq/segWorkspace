var bkid;

var submitkey = true;

function selectAnoNum() {// 选择其他号码
	if (document.getElementById("Numcheckbox").checked) {
		$('#AnotherNum').show();
		$("#AllNumber").attr("disabled", "disabled");
	} else {
		$('#AnotherNum').hide();
		$("#AllNumber").removeAttr("disabled");
	}
}

function searchcust() {// 头部回车搜索执行函数
	updateMsg = '';
	var content = '';
	var searchnumber = $('#searchnumber').val();
	var searchcall = $('#searchcall').val();
	var custname = $('#custname').val();
	var scphone = $('#scphone').val();
	var vin = $('#vin').val();
	var engineno = $('#engineno').val();
	if (searchnumber != null && searchnumber != '') {
		content = searchnumber;
	} else if (searchcall != null && searchcall != '') {
		content = searchcall;
	} else if (custname != null && custname != '') {
		content = custname;
	} else if (scphone != null && scphone != '') {
		content = scphone;
	} else if (vin != null && vin != '') {
		content = vin;
	} else if (engineno != null && engineno != '') {
		content = engineno;
	}
	$("#vehicleId").val("");
	$("#sign_out").hide();
	$("#repair_status").val("");
	$("#nw_private_form")[0].reset();
	$("#customer_name").html("");
	$("#call_letter").html("");
	$("#service_date").html("");
	$("#model").html("");
	$("#model").html("");
	$("#unittype_name").html("");
	$("#watermark").hide();
	$("#sign_out button").attr("disabled", "disabled");

	$.ajax({
		type : 'post',
		async : false,
		contentType : 'application/json',
		dataType : 'json',
		url : '../../getCustInfo',
		data : JSON.stringify({
			vehicle_id : $('#vehicle_id').val(),
			unit_id : $('#unit_id').val(),
			cust_id : $('#cust_id').val(),
			search : content
		}),
		success : function(data) {
			if (data) {
				document.nw_private_form.reset();
				if (data.vehicle == null) {
					$(document).sgPup({message : 'message_alert', text : "该用户已被过户!"});
					return false;
				}
				if(data.unit==null){
					$(document).sgPup({message:'message_alert',text: "该用户为导入用户,请先绑定车牌和车载终端!"});
					return false;
				}
				$('#watermark').hide();
				$("#vehicleId").val(data.vehicle.vehicle_id);

				$('#cust_id').val(data.customer.customer_id);// 姓名
				$('#vehicle_id').val(data.vehicle.vehicle_id);// 车牌号码
				$("#vehicleId").val(data.vehicle.vehicle_id);
				$('#unit_id').val(data.unit.unit_id);
				$('#cust_type').val(data.customer.cust_type);
				$('#cust_name').val(data.customer.customer_name);
				$('#customer_name').html(data.customer.customer_name);// 姓名
				// 车辆资料
				$('#vehicle_type').val(data.vehicle.vehicle_type);
				$('#model').html(data.vehicle.model_name);
				$('#model_id').val(data.vehicle.model);
				$('#vehicle_code').val(data.vehicle.vin);
				$('#engine_no').val(data.vehicle.engine_no);
				$('#plate_no').val(data.vehicle.plate_no);
				// 终端资料
				$('#unittype_name').html(data.unittype);
				$('#unittype').val(data.unittype);
				$('#unittype_id').val(data.unit.unittype_id);
				$('#reg_status').val(data.unit.reg_status);
				if (data.unit.reg_status == 1 | data.unit.reg_status == 2) {
					$('#watermark').show();
				}
				$('#call_letter').html(data.unit.call_letter);
				if (data.unit.service_date != null) {
					$('#service_date').html(data.unit.service_date.substring(0, 10));
				}

				var custphones = data.custphones;// 客户电话号码
				var flag = 0;
				$("#linkPhoneList").html('');
				$(custphones).each(function(k, v) {
					var op = $("<option></option>");
					op.val(v.phone);
					$("#linkPhoneList").append(op);
					linkPhoneList[v.phone] = v.linkman_id;
				});

				$("#tel").on('change', function() {
					var strs = this.value.split(" ");
					if (linkPhoneList[strs[strs.length - 1]]) {
						$(this).val(
								strs[strs.length - 1]);
						$("#linkman_tel")
								.val(
										linkPhoneList[strs[strs.length - 1]]);
						if ($("#linkman_tel").val().length == 0) {
							$("#tel").val("");
						}
					}// else {
					// $(this).val('');
					// $("#linkman_tel").val("");
					// }
				});

				if (data.unit.unit_id != null) {
					$.ajax({
						type : 'post',
						async : false,
						contentType : 'application/json',
						dataType : 'json',
						url : '../../repair/getBarcode',
						data : JSON.stringify({
							parameter : data.unit.unit_id
						}),
						success : function(data) {
							if (data.bcode) {
								$("#barcode").val(data.bcode.content);
							}
						}
					});
				}

				// 离网车辆不参与维修业务,
				if (data.vehicle != null && data.unit.reg_status == 0) {
					$.ajax({
						type : 'post',
						async : false,
						contentType : 'application/json',
						dataType : 'json',
						url : '../../repair/findRepairNow',
						data : JSON.stringify({vehicle_id : $('#vehicle_id').val(), search_type : 'center'}),
						success : function(data) {
							if (data) {
								if (data.repair != null) {
									$("#job_no").val(data.repair.jobNo);
									$("#reg_op_name").val(data.repair.regOpName);
									$("#reg_time").val(data.repair.regTime);
									$("#reg_type").val(data.repair.rpType);
									$("#symptom").val(data.repair.symptom);
									$("#reg_remark").val(data.repair.regRemark);
									$("#repair_status").val(data.repair.status);
									$("#place").val(data.repair.place);
									$("#tel").val(data.repair.phone);
									$("#plate_no").val(data.repair.plateNo);
									$("#repair_no").val(data.repair.jobNo);
									$("#resver_time").val(data.repair.regTime);
									if (data.repair.status == 1) {
										$("#sign_out button").removeAttr("disabled");
									} else {
										$("#sign_out button").attr("disabled", "disabled");
									}
									if (data.repair.orgId) {
										$("#companyDataList").find("option").each(function(k, v) {
											if (companyDataList[v.value] == data.repair.orgId) {
												$("#companyName").val(v.value);
												$("#subcoNo").val(data.repair.orgId);
											}
										});
									}
									$("#sign_out").show();
								} else {// 不存在维修流程车辆$("#repair_no").val();
									$("#job_no").val('');
									$("#reg_op_name").val('');
									$("#reg_time").val('');
									$("#reg_type").val('');
									$("#symptom").val('');
									$("#reg_remark").val('');
									$("#repair_status").val('');
									$("#place").val('');
									$("#repair_no").val('');
									$("#resver_time").val('');
								}
							}
						},
						error : function(res, error) {
							alert("responseText=" + res.responseText + ";error=" + error);
						}
					});
				} else {

				}

				// if(!ifie10){
				addItem('storage_vehicle', { plateNo : data.vehicle.plate_no, callLetter : data.unit.call_letter});// 本地存储，车牌、呼号
				// }
			}
		    var settings =  $('#repairGrid').data('sgDatagrid');
		    settings.query.unit_id = data.unit.unit_id;
		    settings.query.vehicle_id = data.vehicle.vehicle_id;
		    $('#repairGrid').data('sgDatagrid',settings);
			$('#repairGrid').sgDatagrid('reload', 'sgDatagrid');
		},
		error : function(res, error) {
			$(document).sgPup({message : 'message_alert',text : "responseText=" + res.responseText+ ";error=" + error});
		}
	});
}

// 判断是分公司还是总部，如果是分公司，隐藏分公司列表
$.ajax({
	type : 'POST',
	async : false,
	url : '../../getUserCompanyLevel',
	dataType : 'json',
	contentType : 'application/json',
	data : JSON.stringify({}),
	success : function(data) {
		if (data) {
			if (data.success) {
				level = data.result;
				if (level == '0') {// 总部
					// 填分公司
					$.ajax({
						type : 'post',
						async : true,
						contentType : 'application/json',
						dataType : 'json',
						url : '../../getBranchs',
						data : JSON.stringify({}),
						success : function(data) {
							if (data) {
								var companys = data;
								if (companys.length > 0) {
									$("#companyDataList").empty();
									companyDataList = {};
								}
								$.each(companys, function(key, value) {
									var op = $("<option></option>");
									op.val(value.companyname);
									$("#companyDataList").append(op);
									companyDataList[value.companyname] = value.companyno;
								});

								// $("#companyName").on('keyup',stockCompany);
								$("#companyName").on('change', function() {
									var strs = this.value.split(" ");
									if (companyDataList[strs[strs.length - 1]]) {
										$(this).val(strs[strs.length - 1]);
										$("#subcoNo").val(companyDataList[strs[strs.length - 1]]);
										if ($("#subcoNo").val().length == 0) {
											$("#companyName").val("");
										}
									} else {
										$(this).val('');
										$("#subcoNo").val("");
									}
								});
							} else {
								$(document).sgPup({message : 'message_info', text : data});
							}
						},
						error : function(res, error) {
							if (res && res.responseText) {
								$(document).sgPup({message : 'message_info', text : res.responseText});
							}
						}
					});
				} else if (level == '1') {// 分公司
					// 隐藏分公司
					$('#companyName').hide();
				}

			} else {
				$(document).sgPup({message : 'message_info', text : data.msg});
			}
		}
	}
});

// 中心维修登记保存
function repair_save() {
	if ($('#vehicle_id').val() == '' && $('#unit_id').val() == '') {
		$(document).sgPup({message : 'message_alert', text : "请先查询再进行登记!"});
		return false;
	}
	if ($('#reg_status').val() != '0') {
		$(document).sgPup({message : 'message_alert', text : "离网车辆不能进行登记!"});
		return false;
	}
	// 有记录不能添加，只做修改，如果已派发出去，则不能修改
	var repairStatus = $("#repair_status").val();
	if (repairStatus != "" && repairStatus != "1" && repairStatus != "2") {
		$(document).sgPup({message : 'message_alert', text : "该用户正在已分派维修，不能保存登记!"});
		return false;
	}
	var reg_type = $("#reg_type").val();
	if(reg_type == ""){
		$(document).sgPup({message : 'message_alert', text : "请选择业务类型!"});
		return false;
	}
	var phone = $("#tel").val().trim();
	if(phone == ""){
		$(document).sgPup({message : 'message_alert', text : "请输入联系电话!"});
		return false;
	}
	var data = {};
	data.vehicle_id = $("#vehicle_id").val();
	data.unit_id = $("#unit_id").val();
	data.customer_id = $("#cust_id").val();
	data.customer_name = $("#cust_name").val();
	data.reg_op_name = $("#reg_op_name").val();
	data.repair_type = reg_type;
	data.stamp = $("#resver_time").val();
	data.place = $("#place").val();
	data.plate_no = $("#plate_no").val();
	data.unittype = $("#unittype").val();
	data.barcode = $("#barcode").val();
	data.fix_time = $("#fix_time").val();
	data.phone = phone;
	data.jobNo = $("#repair_no").val();
	data.symptom = $("#symptom").val();
	data.reg_remark = $("#reg_remark").val();
	data.status = ($("#repair_status").val() == "" ? 1 : $("#repair_status").val());
	data.search_type = "center";
	$.ajax({
		type : 'post',
		async : false,
		contentType : 'application/json',
		dataType : 'json',
		url : '../../repair/addReserve',
		data : JSON.stringify(data),
		success : function(data) {
			if (data.success) {
				$("#sign_out").show();
				$("#repair_status").val("1");
				if (data.reserve) {
					var reserve = data.reserve;
					$("#repair_no").val(reserve.jobNo);
					$("#reg_op_name").val(reserve.regOpName);
					$("#resver_time").val(reserve.regTime);
					$("#sign_out button").removeAttr("disabled");
				}
			} else {
				$("#sign_out").hide();
				$("#repair_status").val("");
			}
			$(document).sgPup({
				message : 'message_alert',
				text : data.msg
			});
		},
		error : function(res, error) {
			alert("responseText=" + res.responseText + ";error=" + error);
		}
	});
}

function assign_out() {
	// 只分派登记任务，分派成功后，修改状态
	var suboNo = $("#subcoNo").val();
	var vehicleId = $("#vehicle_id").val();
	if (suboNo == "") {
		$(document).sgPup({message : 'message_alert', text : "请选择营业厅/分公司!"});
		return false;
	}
	if (vehicleId == "") {
		$(document).sgPup({message : 'message_alert', text : "请先查询车辆!"});
		return false;
	}
	var data = {};
	data.subco_no = suboNo;
	data.vehicle_id = vehicleId;
	data.jobNo = $("#repair_no").val();
	data.status = 2;
	$.ajax({
		type : 'post',
		async : false,
		contentType : 'application/json',
		dataType : 'json',
		url : '../../repair/sendReserve',
		data : JSON.stringify(data),
		success : function(data) {
			if (data.success) {
				$("#sign_out button").attr("disabled", "disabled");
			}
			$(document).sgPup({message : 'message_alert', text : data.msg});
		},
		error : function(res, error) {
			alert("responseText=" + res.responseText + ";error=" + error);
		}
	});
}

function repair_reset() {
	if ($('#vehicle_id').val() == '' && $('#unit_id').val() == '') {
		document.nw_private_form.reset();
		return false;
	}
	searchcust();
}

// 初始化表格
$(function() {
	var defaults = {
		title : "",
		width : '100%',
		height : 200,
		url : '../../repair/findRepairByPage',
		datatype : "json",
		isNotCheckall : true,
		usepager : true,// 包含表格底部的分页等等一行信息
		autoLoad : false,
		rownumbers : true,
		useRp : true,
		colid : 'rpId', // 主键
		colModel : [ 
		    {display : '维护单号', name : 'jobNo', width : 180 }, 
		    {display : '故障现象', name : 'symptom', width : 150}, 
		    {display : '维护电工', name : 'worker', width : 60}, 
		    {display : '维护地点', name : 'rpPlace', width : 100}, 
		    {display : '操作员', name : 'acpOpName', width : 60}, 
		    {display : '工单状态', name : 'status', width : 100, formatter : function(value, row) {
				if (value == 1) {
					value = '已登记';
				} else if (value == 2) {
					value = '已受理';
				} else if (value == 3) {
					value = '已预约';
				} else if (value == 4) {
					value = '已派工';
				} else if (value == 5) {
					value = '维修中';
				} else if (value == 6) {
					value = '维修完成';
				} else if (value == 7) {
					value = '未维修完成';
				}
				return value;
			}}, 
			{display : '处理时间', name : 'stamp', width : 120},
			{display : '备注', name : 'regRemark', width : 180}, 
			{display : '跟踪情况', name : 'traceResult',width : 180} 
		],
		searchitems : [
		    {html : '<input type="hidden" id="vehicleId" name="vehicleId" />'},
			{html : '<input type="hidden" id="qtype" name="qtype" value="1"/>'},
			{display : '开始时间', html : '<input type="text" name="startDate" class="form_datetime" style="width:150px;"/>'},
			{display : '结束时间', html : '<input type="text" name="endDate" class="form_datetime" style="width:150px;"/>'} 
		],
		exporturl : '../../repair/exportRepairDt'
	};
	$('#repairGrid').sgDatagrid(defaults);
});

$.fn.datetimepicker.dates['zh'] = {
	days : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" ],
	daysShort : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
	daysMin : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
	months : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
	monthsShort : [ "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" ],
	meridiem : [ "上午", "下午" ],
	today : "今天"
};

$(document).ready(function() {
	$("#repairGrid .hDiv-header-inner,#repairGrid_tbl").css("width", "1300px");
	$(".form_datetime").datetimepicker({
		language : "zh",
		autoclose : true,
		minView : "month",
		format : 'yyyy-mm-dd'
	});
});