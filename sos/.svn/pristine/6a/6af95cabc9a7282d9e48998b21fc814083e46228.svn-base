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

function subVerify(){
	if ($('#sim_file').val() == '') {
        $(document).sgPup({message:'message_info',text: '请选择上传导入文件!'});
        $('#sim_file').focus();
        return false;
    }else{
        var file=$('#sim_file')[0];
        var files=file.files;
        if(files){
            var filesLength=files.length;
            if(filesLength>50){
                $(document).sgPup({message:'message_info',text: '一次性最多上传50个文件!'});
                    $('#sim_file').focus();
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
    }
    var mask=$('<div id="imp_mask"></div>');
    mask.addClass('window-mask');
    mask.css('z-index',Number($('div.window').css('z-index'))+1);//如果有弹出窗口，则将遮罩层置为最上层
    var span=$('<span></span>');
    span.css({position:'absolute',left:$(window).outerWidth()/2-80,top:$(window).outerHeight()/2-60,color:'red','font-size':'x-large','font-weight':'bold'});
    span.text('正在导入中...请稍作休息！');
    mask.append(span);
    $(document.body).append(mask);
    var form = $("#sim_verify");
	var options  = {
		url:'../../../preload/verifySim', 
		type:'post',
		success:function(data){    
			//移除遮罩层
			$('div#imp_mask',window.document.body).remove();
			$(document).sgPup({message:'message_info',text: data.msg});
			if(data.success){
				$(document).sgWindow('close',{id:'verify_sim'});
				$('#dgd_simverify_dv').sgDatagrid('reload','sgDatagrid');
				if(data.compareList != null && data.compareList.length() > 0){
					window.open (window.prefixUrl+'preload/downSimVerify', 
							"newwindow", "height=100, width=400, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
				}
			}
		}    
	};    
	form.ajaxSubmit(options);
}

function winClose_verify(){
	$(document).sgWindow('close', {id : 'verify_sim'});
}

importVerify = function(){
	var defaults = {
		title : 'SIM卡校验',
		id : 'verify_sim',
		form : 'sim_verify_form',
		url : 'sim_upload.html',
		width : 300,
		height : 140,
		buttons : [ 
			{name: '确定', type: 'submit', onpress : subVerify},
			{name : '关闭',onpress : winClose_verify}
		]
	};
	$(document).sgWindow(defaults);
};

//初始化表格
var defaults = {
	title : "SIM卡效验",
	width : '100%',
	fitColumn : true,
	height : 450,
	url : '../../../preload/listVerifySimResult',
//	usepager : true,
	rownumbers : true,
	useRp : true,
	autoLoad : false,
	colid : 'call_letter', // 主键
	colModel : [
		{display: '呼号', name : 'call_letter', width : 100, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("call_letter,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: 'IMEI/MEID', name : 'imei', width : 150, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("imei,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: '序列号', name : 'barcode', width : 120, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("barcode,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: 'ICCID', name : 'iccid', width : 120, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("iccid,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: 'IMSI', name : 'imsi', width : 120, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("imsi,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: 'AKEY', name : 'akey', width : 120, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("akey,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: 'ESN', name : 'esn', width : 80, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("esn,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: 'EVDO账号', name : 'w_user', width : 180, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("w_user,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: 'EVDO密码', name : 'w_password', width : 150, sortable : false, formatter:function(value,row){
			var redCol = row.redCol;
			if(redCol.indexOf("w_password,") > -1){
				return "<span style=color:red>" + value + "</span>";
			}
			return value;
		}},
		{display: '比对结果', name : 'result', width : 350, sortable : false}
	],
	buttons : [
	    {name: '导入校验',bclass : 'import',onpress : importVerify}
	],
	query : {}
};
$('#dgd_simverify_dv').sgDatagrid(defaults);