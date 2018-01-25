var isGoOn = true;

function isvalidatefile(obj){
    var extend = obj.substring(obj.lastIndexOf(".") + 1);
    //$(document).sgPup({message:'message_info',text: extend);
    if (extend == "") {
    } else {
        if (!(extend.toLocaleLowerCase() == "xls")) {
            $(document).sgPup({message:'message_info',text: "请上传后缀名为xls的文件!"});
            return false;
        }
    }
    return true;
}

var subImport = function(){
	 var file=$('#cv_file')[0];
     var files=file.files;
     if(files){
         var filesLength=files.length;
         if(filesLength>50){
             $(document).sgPup({message:'message_info',text: '一次性最多上传50个文件!'});
                 $('#cv_file').focus();
             return false;
         }
         var isGoOn=true;
         for(var i=0;i<filesLength;i++){
             if(!isvalidatefile(files[i].name)){
                 isGoOn=false;
                 break;
             }
         }
         if(!isGoOn){
           return false;
         }

     }
	var mask=$('<div id="imp_mask"></div>');
	mask.addClass('window-mask');
	mask.css('z-index',Number($('div.window').css('z-index'))+1);//如果有弹出窗口，则将遮罩层置为最上层
	var span=$('<span></span>');
	span.css({position:'absolute',left:$(window).outerWidth()/2-80,top:$(window).outerHeight()/2-60,color:'red','font-size':'x-large','font-weight':'bold'});
	span.text('正在导入中...请稍作休息！');
	mask.append(span);
	$(document.body).append(mask);
	var form = $("#ubi_import");
	var options  = {
		url:'../../../preload/importCVFile', 
		type:'post',
		success:function(data){    
			//移除遮罩层
			$('div#imp_mask',window.document.body).remove();
			$(document).sgPup({message:'message_info',text: data.msg});
			if(data.success){
				$(document).sgWindow('close',{id:'import_ubi'});
				$('#ubi_datagrid').sgDatagrid('reload','sgDatagrid');
			}
		}    
	};    
	form.ajaxSubmit(options);
//     if(isGoOn){
//         $("#ubi_import").submit();
//     }
};

var winClose_import = function(){
	$(document).sgWindow('close', {id : 'import_ubi'});
};

var importItem = function() {// 导入ubi资料
	var defaults = {
		title : 'UBI资料导入',
		id : 'import_ubi',
		form : 'ubi_import_form',
		url : 'ubi_import.html',
		width : 300,
		height : 140,
		buttons : [ 
			{name: '确定', type: 'submit', onpress : subImport},
			{name : '关闭',onpress : winClose_import}
		]
	};
	$(document).sgWindow(defaults);
}

var downFile = function(){
	window.open ("../../../excelFile/CUST_VEHICLE.xls", "newwindow", "height=100, width=400, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
}

var lastMonthDay=new Date();//上个月的今天
lastMonthDay.setDate(1);
lastMonthDay=lastMonthDay.format('yyyy-MM-dd');
var today=new Date().format('yyyy-MM-dd');

//修改表格的宽度
var height =$('#main_bd',window.parent.document).height()-288;
var startDateHTML = '<span class="ltext"><input type="text" name="startDate" class="form-control form_datetime" value='+lastMonthDay+' /></span>';//开始日期
var endDateHTML = '<span class="ltext"><input type="text" name="endDate" class="form-control form_datetime" value='+today+' /></span>';//结束日期
// 初始化表格
var defaults = {
	title : "UBI资料导入",
	width : '100%',
	height : height,
	usepager: true,
	rownumbers:true,
	url : '../../../preload/findUbiByPage',
	useRp : true,
	autoLoad : false,// 不自动查询
	colid : 'vehicle_id', // 主键
	colModel : [
	    {display: '客户姓名', name : 'customer_name', width : 80, sortable : false},
	    {display: '车牌号码', name : 'plate_no', width : 80, sortable : false},
	    {display: '联系方式', name : 'phones', width : 100, sortable : false},
	    {display: '车型', 	name : 'model_name', width : 80, sortable : false},
	    {display: '车架号', 	name : 'vin', width : 100, sortable : false},
	    {display: '发动机号', name : 'engine_no', width : 100, sortable : false},
	    {display: '登记日期', name : 'register_date', width : 80, sortable : false},
	    {display: '是否从公司购买商业保险', name : 'is_corp', width : 80, sortable : false, formatter:function(value,row){
	    	if(value == "0"){
	    		return "否";
	    	}
	    	if(value == "1"){
	    		return "是";
	    	}
	    }},
	    {display: '是否享有综合盗抢险', name : 'is_pilfer', width : 80, sortable : false, formatter:function(value,row){
	    	if(value == "0"){
	    		return "否";
	    	}
	    	if(value == "1"){
	    		return "是";
	    	}
	    }},
	    {display: '保险起保时间', name : 'is_bdate', width : 80, sortable : false},
	    {display: '保险截止时间', name : 'is_edate', width : 80, sortable : false}
	],
	buttons : [
//	    {name: '添加',bclass : 'add',onpress : addItem},
//	    {name: '编辑',bclass : 'edit',onpress : modifyItem},
	    {name: '下载模板',bclass : 'download',onpress : downFile},
	    {name: '导入',bclass : 'import',onpress : importItem}
	],
	searchitems : [
		{display:'生成开始日期',	html:startDateHTML},
		{display:'生成结束日期',	html:endDateHTML},
	    {display:'客户名称',html:'<span class="ltext"><input type="text" name="custName" class="form-control"/></span>'},
	    {display:'车牌号码',html:'<span class="ltext"><input type="text" name="plateNo" class="form-control"/></span>'},
	    {display:'车架号',html:'<span class="ltext"><input type="text" name="vin" class="form-control"/></span>'},
	    {display:'发动机号',html:'<span class="ltext"><input type="text" name="engine" class="form-control"/></span>'}
	],
	order : true,
	sortname : "",
	sortorder : ""
};
$('#ubi_datagrid').sgDatagrid(defaults);