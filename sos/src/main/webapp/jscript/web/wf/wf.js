	function buttonChange(){
		var status = $("#repair_status").val();
		if(status == "" && status == "1"){
			hideAll();
		}else if(status == "2"){
			$("#yy_span").show();
			$("#pg_span").hide();
			$("#dy_span").hide();
			$("#wx_span").hide();
			$("#wcwx_span").hide();
			$("#wwcwx_span").hide();
		}else if(status == "3"){
			$("#yy_span").hide();
			$("#pg_span").show();
			$("#dy_span").hide();
			$("#wx_span").hide();
			$("#wcwx_span").hide();
			$("#wwcwx_span").hide();
		}else if(status == "4"){
			$("#yy_span").hide();
			$("#pg_span").hide();
			$("#dy_span").show();
			$("#wx_span").show();
			$("#wcwx_span").hide();
			$("#wwcwx_span").hide();
		}else if(status == "5"){
			$("#yy_span").hide();
			$("#pg_span").hide();
			$("#dy_span").hide();
			$("#wx_span").hide();
			$("#wcwx_span").show();
			$("#wwcwx_span").show();
		}else{
			$("#yy_span").hide();
			$("#pg_span").hide();
			$("#dy_span").hide();
			$("#wx_span").hide();
			$("#wcwx_span").hide();
			$("#wwcwx_span").hide();
		}
	}
	function hideAll(){
		$("#yy_span").hide();
		$("#pg_span").hide();
		$("#dy_span").hide();
		$("#wx_span").hide();
		$("#wcwx_span").hide();
		$("#wwcwx_span").hide();
	}
	
	function yy_ajax(){
		if(!getVehicleStatus()){
			return false;
		}
		var time = $("#apt_time").val();
		var name = $("#apt_name").val();
		var phone = $("#apt_phone").val();
		var place = $("#rp_place").val();
		if(time == ""){
			$(document).sgPup({message : 'message_alert', text : "该选择预约时间!"});
			$("#apt_time").focus();
			return false;
		}
		if(name == ""){
			$(document).sgPup({message : 'message_alert', text : "该填写联系人姓名!"});
			$("#apt_name").focus();
			return false;
		}
		if(phone == ""){
			$(document).sgPup({message : 'message_alert', text : "该输入预约电话!"});
			$("#apt_phone").focus();
			return false;
		}
		if(place == ""){
			$(document).sgPup({message : 'message_alert', text : "该填写维护地点!"});
			$("#rp_place").focus();
			return false;
		}
		$.ajax({
			type : 'post',
			async : false,
			contentType : 'application/json',
			dataType : 'json',
			url : '../../repair/saveRepairReserve',
			data : JSON.stringify({vehicle_id : $('#vehicle_id').val(), apt_time : time, apt_name : name, apt_phone : phone, rp_place : place,
				jobNo:$("#job_no").val(), status : 3,trace_result : $("#trace_result").val()
			}),
			success : function(data) {
				if (data.success) {
					$(document).sgPup({message : 'message_alert', text : "预约成功!"});
					$("#repair_status").val(3);
					$("#rp_serv_place").html(place);
					$("#rp_serv_time").html(time);
					$("#rp_remark").html($("#trace_result").val());
					$("#rp_apt_name").html(name);
					$("#rp_apt_phone").html(phone);
					buttonChange();
				}
			},
			error : function(res, error) {
				alert("responseText=" + res.responseText + ";error=" + error);
			}
		});
	}

	function pg_ajax(){
		if(!getVehicleStatus()){
			return false;
		}
		var worker = $("#worker").val();
		if(worker == ""){
			$(document).sgPup({message : 'message_alert', text : "该填写电工姓名!"});
			$("#worker").focus();
			return false;
		}
		$.ajax({
			type : 'post',
			async : false,
			contentType : 'application/json',
			dataType : 'json',
			url : '../../repair/saveRepairPaiGong',
			data : JSON.stringify({vehicle_id : $('#vehicle_id').val(), worker : worker, 
				jobNo:$("#job_no").val(), status : 4, trace_result : $("#trace_result").val()
			}),
			success : function(data) {
				if (data.success) {
					$(document).sgPup({message : 'message_alert', text : "派工成功!"});
					$("#repair_status").val(4);
					$("#rp_remark").html($("#trace_result").val());
					buttonChange();
				}
			},
			error : function(res, error) {
				alert("responseText=" + res.responseText + ";error=" + error);
			}
		});
	}
	
	function wx_ajax(){
//		var fee = $("#fitting_fee").val();
//		var amount = $("#update_amount").val();
		if(!getVehicleStatus()){
			return false;
		}
/*		if(fee == ""){
			$(document).sgPup({message : 'message_alert', text : "该填写配件费用!"});
			$("#fitting_fee").focus();
			return false;
		}
		if(amount == ""){
			$(document).sgPup({message : 'message_alert', text : "该填写升级费用!"});
			$("#update_amount").focus();
			return false;
		}*/
		$.ajax({
			type : 'post',
			async : false,
			contentType : 'application/json',
			dataType : 'json',
			url : '../../repair/saveRepairDoing',
			data : JSON.stringify({vehicle_id : $('#vehicle_id').val(), //fee : fee, amount : amount, 
				jobNo:$("#job_no").val(), status : 5, trace_result : $("#trace_result").val()
			}),
			success : function(data) {
				if (data.success) {
					$("#repair_status").val(5);
					buttonChange();
				}
			},
			error : function(res, error) {
				alert("responseText=" + res.responseText + ";error=" + error);
			}
		});
	}
	
	function wcwx_ajax(){
		var result1 = $("#worker_result").val();
		var result2 = $("#trace_result").val();
		if(!getVehicleStatus()){
			return false;
		}
		$.ajax({
			type : 'post',
			async : false,
			contentType : 'application/json',
			dataType : 'json',
			url : '../../repair/saveRepairSuccess',
			data : JSON.stringify({vehicle_id : $('#vehicle_id').val(), worker_result : result1, 
				jobNo:$("#job_no").val(), status : 6, trace_result : result2
			}),
			success : function(data) {
				if (data.success) {
					$(document).sgPup({message : 'message_alert', text : "维修完成!"});
					$("#repair_status").val(6);
					buttonChange();
				}
			},
			error : function(res, error) {
				alert("responseText=" + res.responseText + ";error=" + error);
			}
		});
	}

	function wwcwx_ajax(){
		var result1 = $("#worker_result").val();
		var result2 = $("#trace_result").val();
		if(!getVehicleStatus()){
			return false;
		}
		$.ajax({
			type : 'post',
			async : false,
			contentType : 'application/json',
			dataType : 'json',
			url : '../../repair/saveRepairUndo',
			data : JSON.stringify({vehicle_id : $('#vehicle_id').val(), worker_result : result1, 
				jobNo:$("#job_no").val(), status : 7, trace_result : result2
			}),
			success : function(data) {
				if (data.success) {
					$(document).sgPup({message : 'message_alert', text : "未维修完成!"});
					$("#repair_status").val(7);
					buttonChange();
				}
			},
			error : function(res, error) {
				alert("responseText=" + res.responseText + ";error=" + error);
			}
		});
	}
	
	function getVehicleStatus(){
		var vehicleId = $('#vehicle_id').val();
		var jobNo = $("#job_no").val();
		var regStatus = $("#reg_status").val();
		if(vehicleId == ""){
			$(document).sgPup({message : 'message_alert', text : "请先查询在处理!"});
			return false;
		}
		if(jobNo == ""){
			$(document).sgPup({message : 'message_alert', text : "该车辆未进行维修!"});
			return false;
		}
		if(regStatus != "0"){
			$(document).sgPup({message : 'message_alert', text : "该车辆已离网!"});
			return false;
		}
		return true;
	}
	
	var bkid;
	var submitkey = true;
	
	function resetDyTable(){
		var spans = $("#table span");
		for(var i = 0; i < spans.length; i++){
			var sid = $(spans[i]).attr("id");
			if(sid != ""){
				$(spans[i]).html("");
			}
		}
	}

	function searchcust() {//头部回车搜索执行函数
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
		resetDyTable();	//重置打印表格
		buttonChange();

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

					$('#cust_id').val(data.customer.customer_id);//姓名
					$('#vehicle_id').val(data.vehicle.vehicle_id);//车牌号码
					$("#vehicleId").val(data.vehicle.vehicle_id);
					$('#unit_id').val(data.unit.unit_id);
					$('#cust_type').val(data.customer.cust_type);
					$('#cust_name').val(data.customer.customer_name);
					$('#customer_name').html(data.customer.customer_name);//姓名
					//车辆资料
					$('#vehicle_type').val(data.vehicle.vehicle_type);
					$('#model').html(data.vehicle.model_name);
					$('#model_id').val(data.vehicle.model);
					$('#vehicle_code').val(data.vehicle.vin);
					$('#engine_no').val(data.vehicle.engine_no);
					$('#plate_no').html(data.vehicle.plate_no);
					$('#rp_car_type').html(data.vehicle.model_name);
					//终端资料
					$("#rp_worker").html(data.unit.worker);
					$("#rp_fix_time").html(data.unit.fix_time);
					$("#rp_call_letter").html(data.unit.call_letter);
					$("#rp_sales").html(data.unit.sales);
					$("#rp_unittype").html(data.unittype);
					$("#rp_fix_place").html(data.unit.place);
					$('#unittype_name').html(data.unittype);
					$('#unittype').val(data.unittype);
					$('#unittype_id').val(data.unit.unittype_id);
					$('#reg_status').val(data.unit.reg_status);
					if (data.unit.reg_status == 1 || data.unit.reg_status == 2) {
						$('#watermark').show();
					}
					$('#call_letter').html(data.unit.call_letter);
					if (data.unit.service_date != null) {
						$('#service_date').html(data.unit.service_date.substring(0, 10));
					}

					var custphones = data.custphones;//客户电话号码
					var flag = 0;
					$("#linkPhoneList").html('');
	                $(custphones).each(function(k,v){
						var op = $("<option></option>");
						op.val(v.phone);
						$("#linkPhoneList").append(op);
						linkPhoneList[v.phone] = v.linkman_id;
						$("#rp_links").append(v.phone+","+v.linkman+",");
	                });

					$("#apt_phone").on('change', function() {
						var strs = this.value.split(" ");
						if (linkPhoneList[strs[strs.length - 1]]) {
							$(this).val(strs[strs.length - 1]);
							$("#linkman_tel").val(linkPhoneList[strs[strs.length - 1]]);
							if ($("#linkman_tel").val().length == 0) {
								$("#apt_phone").val("");
							}
						} else {
						}
					});
					

					$("#linkNameList").html('');
	                $(custphones).each(function(k,v){
						var op = $("<option></option>");
						op.val(v.linkman);
						$("#linkNameList").append(op);
						linkNameList[v.linkman] = v.linkman_id;
	                });

					$("#apt_name").on('change', function() {
						var strs = this.value.split(" ");
						if (linkNameList[strs[strs.length - 1]]) {
							$(this).val(strs[strs.length - 1]);
							$("#linkman_name").val(linkNameList[strs[strs.length - 1]]);
							if ($("#linkman_name").val().length == 0) {
								$("#apt_name").val("");
							}
						}// else {
					//		$(this).val('');
					//		$("#linkman_name").val("");
					//	}
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

					//离网车辆不参与维修业务, 
					if (data.vehicle != null && data.unit.reg_status == 0) {
						$.ajax({
							type : 'post',
							async : false,
							contentType : 'application/json',
							dataType : 'json',
							url : '../../repair/findRepairNow',
							data : JSON.stringify({vehicle_id : $('#vehicle_id').val(), search_type : 'office'}),
							success : function(data) {
								if (data) {
									if (data.repair != null) {
										$("#rp_id").val(data.repair.rpId);
										$("#rp_plate_no").html(data.repair.plateNo);
										$("#acp_op_name").val(data.repair.acpOpName);
										$("#rp_customer_name").html(data.repair.customerName);
										$("#rp_serv_place").html(data.repair.rpPlace);
										$("#rp_serv_time").html(data.repair.aptTime);
										$("#rp_apt_name").html(data.repair.aptName);
										$("#rp_apt_phone").html(data.repair.aptPhone);
										$("#rp_before_symptom").html(data.beforeSym);
										$("#rp_now_symptom").html(data.repair.symptom);
										$("#rp_remark").html(data.repair.traceResult);
										$("#rp_remark").html(data.repair.traceResult);
										$("#job_no").val(data.repair.jobNo);
										$("#apt_time").val(data.repair.aptTime);
										$("#apt_name").val(data.repair.aptName);
										$("#apt_phone").val(data.repair.aptPhone);
										$("#rp_place").val(data.repair.rpPlace);
										$("#phone").html(data.repair.phone);
										$("#worker").val(data.repair.worker);
										$("#repair_status").val(data.repair.status);
										$("#trace_result").val(data.repair.traceResult);
										$("#worker_result").html(data.repair.workerResult);
										buttonChange();
									} else {//不存在维修流程车辆$("#repair_no").val();
										$("#job_no").val('');
										$("#apt_time").val('');
										$("#apt_name").val('');
										$("#apt_phone").val('');
										$("#rp_place").val('');
										$("#worker").val('');
										$("#repair_status").val('');
										$("#trace_result").val('');
										$("#worker_result").val('');
										buttonChange();
									}
								}
							},
							error : function(res, error) {
								alert("responseText=" + res.responseText + ";error=" + error);
							}
						});
					} 
					// if(!ifie10){
					addItem('storage_vehicle', {plateNo : data.vehicle.plate_no,callLetter : data.unit.call_letter});//本地存储，车牌、呼号
					// }
				}
			    var settings =  $('#repairGrid').data('sgDatagrid');
			    settings.query.unit_id = data.unit.unit_id;
			    settings.query.vehicle_id = data.vehicle.vehicle_id;
			    $('#repairGrid').data('sgDatagrid',settings);
				$('#repairGrid').sgDatagrid('reload', 'sgDatagrid');
			},
			error : function(res, error) {
				$(document).sgPup({message : 'message_alert', text : "responseText=" + res.responseText + ";error=" + error});
			}
		});
	}

	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	function dy_ajax(){
		var rpId = $("#rp_id").val();
		$("#rp_remark").html($("#trace_result").val());
		var date = new Date().Format("yyyy-MM-dd hh:mm:ss");
		var acOpName = $("#acp_op_name").val();
		var LODOP=getLodop();  
		LODOP.PRINT_INIT("维修单打印");
		var strStyle="<style> table,td,th {border-width: 1px;border-style: solid;border-collapse: collapse}</style>";
		LODOP.ADD_PRINT_HTM("5mm","5mm","10mm","10mm","<span><b>NO.</b><span>"+rpId+"</span></span>");
		LODOP.ADD_PRINT_HTM("5mm","75mm","120mm","10mm","<strong><span style='font-size: 26px;'>赛格车圣服务单</span><strong>");
		LODOP.ADD_PRINT_HTM("5mm","162mm","50mm","10mm","<span>卡类型：</span>");
		LODOP.ADD_PRINT_HTM("14mm","80mm","60mm","8mm","<span style='font-size: 13px;'>联系电话：0731-85478563</span>");
		LODOP.ADD_PRINT_HTM("22mm","5mm","60mm","8mm","接单人："+acOpName);
		LODOP.ADD_PRINT_HTM("22mm","148mm","60mm","8mm","制单时间："+date);

		LODOP.ADD_PRINT_TABLE(125,"5mm","95%",514,strStyle+document.getElementById("print_table").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"LinkedItem",1);
		LODOP.PREVIEW();
	}
	
	function repair_reset() {
		if ($('#vehicle_id').val() == '' && $('#unit_id').val() == '') {
			document.nw_private_form.reset();
			return false;
		}
		searchcust();
	}

	//初始化表格
	$(function() {
		var defaults = {
			title : "",
			width : '100%',
			height : 200,
			url : '../../repair/findRepairByPage',
			datatype : "json",
			isNotCheckall : true,
			usepager : true,//包含表格底部的分页等等一行信息
			autoLoad : false,
			rownumbers : true,
			useRp : true,
			colid : 'repair_id', //主键
			colModel : [ 
				{display : '维护单号',name : 'jobNo',width : 130},
				{display : '故障现象',name : 'symptom',width : 150},
				{display : '维护电工',name : 'worker',width : 60},
				{display : '维护地点',name : 'rpPlace',width : 100},
				{display : '操作员',name : 'acpOpName',width : 60},
				{display: '工单状态', name : 'status', width :100,
					formatter: function(value, row) {
						if (value == 1) {
							value = '已登记';
						} else if (value == 2) {
							value = '已受理';
						}else if (value == 3) {
							value = '已预约';
						}else if (value == 4) {
							value = '已派工';
						}else if (value == 5) {
							value = '维修中';
						}else if (value == 6) {
							value = '维修完成';
						}else if (value == 7) {
							value = '未维修完成';
						}
						return value;
				}},
				{display : '处理时间',name : 'stamp',width : 120},
				{display : '备注',name : 'regRemark',width : 200},
				{display : '跟踪情况',name : 'traceResult',width : 180}
			],
			searchitems : [
				{html : '<input type="hidden" id="vehicleId" name="vehicleId" />'},
	            {html : '<input type="hidden" id="qtype" name="qtype" value="1"/>'},
				{display : '开始时间', html : '<span class="ltext"><input type="text" name="startDate" class="form-control form_datetime" /></span>'},
				{display : '结束时间', html : '<span class="ltext"><input type="text" name="endDate" class="form-control form_datetime" /></span>'}
			]
			,exporturl:'../../repair/exportRepairDt'
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