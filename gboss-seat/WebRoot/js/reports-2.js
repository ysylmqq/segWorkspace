$(document).ready(function(){
    var add_table11,add_table22,add_table33,add_table44;
    var callparas1,callparas2,callparas3,callparas4;
	/* 未上报 已上报报表*/
	$("#beenreported_btn").click(function(){
	    $("#ysb").show();
	    $("#wsb").hide();
	});
	$("#underreport_btn").click(function(){
	    $("#wsb").show();
	    $("#ysb").hide();
	});

	/* 时间初始化 */
	$("#wsb_start,#ysb_start,#locatefault_start,#marketing_start").val(OnlyDate()+" 00:00:00");
	$("#wsb_end,#ysb_end,#locatefault_end,#serviceCharge_time,#marketing_end").val(ReDate());

	//未上报统计报表 start

    //未上报统计报表报表查询
    $("#recRSearch").click(function(){    
        if ( typeof(add_table11) != "undefined") {
            recReport();
            add_table11.fnReloadAjax("app/report/unreportstat?" + callparas1);
        }
        else{
            recReport();
        };   
     });

    //未上报统计报表导出

    $("#recRExport").click(function(){
        $(this).attr("href","app/report/unreportstat/export?"+callparas1);
    });

    //未上报统计查询
    function recReport(){
        var ValcompanyNo = [];//所属公司id
        if ($("#recentReported_sub").val()=='0'){
            ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#recentReported_sub").val();
        };
        var Valname = encodeURI($("#recRName").val());//客户名称
        var ValcardType = $("#recRCardType").val();//卡类型(0:全部 1:自带卡 2.公司卡 3.总部卡 ) 如果选择全部，则不传此参数
        var ValcommType = $("#comm-mode").val();//通信模式(1:显示GSM 3.全部 )

        var ValRepType = getcomtent("recentRep_option1");//大类(0:未上报 1.已上报 )
        var ValTimeInt = getcomtent("recentRep_wsb_time");//未上报时间间隔(1:当天未上报,3:3天未上报,5:5天未上报,0:选择时间段)

        var ValbeginTime = '';//开始时间
        var ValendTime = '';//结束时间

        if (ValcompanyNo == []){//所属公司拼接
            recRcompanyNo='';
        }else{
            recRcompanyNo = 'companyNo='+ ValcompanyNo +'&';
        };

        if (Valname==''){//客户名称拼接
            recRname='';
        }else{
            recRname = 'name='+Valname+'&';
        };

        if (ValcardType == '0'){//卡类型拼接
            recRcaseType='';
        }else{
            recRcaseType = 'simType='+ValcardType+'&';
        };

        if (ValcommType == '3') {//通信模式拼接
            recRcommType='';
        } else{
            recRcommType = 'mode='+ValcommType+'&';
        };

        isRep = 'report='+ValRepType +'&';//是否上报(0=未上报，1=已上报)

        recTime = '';//时间间隔
        if(ValRepType==0){//若为未上报
            var timel = getcomtent("recentRep_wsb_time");//获取所选项
            if(timel == 3){//3天未上报
                recTime = 'interval=3';
            } else if(timel == 5){//5天未上报
                recTime = 'interval=5';
            } else if(timel == 0){//选择时间段
                if($("#wsb_start").val() >= $("#wsb_end").val()){
                    alert("起始时间不能大于结束时间");
                    return false;
                }
                recTime = 'beginTime='+$("#wsb_start").val() +'&'+'endTime='+$("#wsb_end").val();
            }
        } else if(ValRepType==1){//已上报
            if($("#beenRStartTime").val() >= $("#beenREndTime").val()){
                alert("起始时间不能大于结束时间");
                return false;
            }
            recTime = 'beginTime='+$("#ysb_start").val() +'&'+'endTime='+$("#ysb_end").val();
        }

        callparas1 = recRcompanyNo+recRname+recRcaseType+recRcommType+isRep+recTime;//所属公司、客户名称、SIM卡类型、通信模式、是否上报、时间间隔

        // paras = callparas;//参数;
        add_table11 = $("#recentReported-table").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/unreportstat?" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"app/report/unreportstat?now=" + new Date().getTime() + "&" + callparas1 //获取数据的API
            },             
            "columns":[
                {"data":"sn"},//序列号
                {"data":"customer.name"},//客户名称
                {"data":"phone"},//客户电话
                {"data":"vehicle.plate_no"},//车牌号
                {"data":"unit.call_letter"},//车载电话
                {"data":"vehicle.modelName"},//车型
                {"data":"lastPosition.status"},//车辆状态
                {"data":"lastPosition.gpsTime"},//最后上报时间
                {"data":"unit.date"},//安装时间
                {"data":"unit.mode"},//通信模式
                {"data":"unit.sales"},//营销经理
                {"data":"vehicle.id"}//vehicle_id
            ],
            "rowCallback": function(row,data) {
                $('td:eq(9)',row).html(replaceMode(data.unit.mode));
            }
        });
        return add_table11;
    }

    //获取单选框的内容
    function getcomtent(tagname){
        var obj = document.getElementsByName(tagname);
        var temp ="";
        for(var i=0; i<obj.length; i ++){
            if(obj[i].checked){
                //alert(obj[i].value);
                temp = obj[i].value;
            }
        }
        return temp;
    }

    //通信模式转换（1=短信, 2=数据流量, 3=流量+短信 ）
    function replaceMode(num){
        var temp ="";
        switch(num){
            case 1: temp = "短信"; break;
            case 2: temp = "数据流量"; break;
            default: temp = "";
        }
        return temp;
    }

    $("#recentReported-table").on("click",  "tbody tr", function(event){
        selectTr(this);
    });

    $("#recentReported-table").on("dblclick",  "tbody tr", function(event){
        selectTr(this);
        var v_id = $(this).children("td").eq(11).html();
        $("#vehicle_id").val(v_id);
        search(term,2);//查询
    });

    //定位故障通知报表 start

    //定位故障通知报表查询
    $("#LocaFSearch").click(function(){    
        if($("#locatefault_start").val()==''||$("#locatefault_end").val()==''){
            alert('请选择时间段!');
        }else if($("#locatefault_start").val() >= $("#locatefault_end").val()){
            alert("起始时间不能大于结束时间");
        }else{
            if ( typeof(add_table22) != "undefined") {
                LocaFReport();
                add_table22.fnReloadAjax("app/report/locatefault?" + callparas2);
            }
            else{
                LocaFReport();
            };
        };
     });

    //定位故障通知报表导出

    $("#LocaFExport").click(function(){
        $(this).attr("href","app/report/locatefault/export?"+callparas2);
    });

    //定位故障通知查询
    function LocaFReport(){
        var ValcompanyNo = [];//所属公司id
        if ($("#locatefault_sub").val()=='0'){
            ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#locatefault_sub").val();
        };
        var Valname = encodeURI($("#LocaFCosName").val());//客户名称
        var Vallon = $("#LocaFLon").val();//经度
        var Vallat = $("#LocaFLat").val();//纬度
        var ValtimeDif = $("#LocaFtimeDif").val();//天数差
        var ValFaultType = [];//故障类型
        var faultType = $("#LocaFdefaltType input:checked");
        for (var i=0;i<faultType.length;i++){
            ValFaultType[i] = faultType[i].value;
        };

        var Valdate = getcomtent("loca_option1");//日期

        if (ValcompanyNo == []){//所属公司拼接
            LocaFcompanyNo='';
        }else{
            LocaFcompanyNo = 'companyNo='+ValcompanyNo+'&';
        };

        if (Valname==''){//客户名称拼接
            LocaFname='';
        }else{
            LocaFname = 'name='+Valname+'&';
        };

        if (ValFaultType==''){//故障类型拼接
            LocaFFaultType='';
        }else{
            var valueType='';
            for (var i=0;i<ValFaultType.length;i++){
                valueType += 'status='+ValFaultType[i]+'&';
            };        
            LocaFFaultType = valueType;
        };

        LocaFTime = 'beginTime='+$("#locatefault_start").val()+'&'+'endTime='+$("#locatefault_end").val()+'&';

        if (ValtimeDif==''){//天数差
            TimeDif='';
        }else{
            TimeDif = 'diff='+ValtimeDif+'&';
        };

        if (Vallon==''){//经度拼接
            LocaFlon='';
        }else{
            LocaFlon = 'lon='+Vallon+'&';
        };

        if (Vallat==''){//纬度拼接
            LocaFlat='';
        }else{
            LocaFlat = 'lat='+Vallat;
        };

        callparas2 = LocaFcompanyNo+LocaFname+LocaFFaultType+LocaFTime+TimeDif+LocaFlon+LocaFlat;//所属公司、客户名称、故障类型、时间、天数差、经度、纬度
        // paras = callparas;//参数;
        add_table22 = $("#locatefault-table").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/locatefault?" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"app/report/locatefault?now=" + new Date().getTime() + "&" + callparas2 //获取数据的API
            },             
            "columns":[
                {"data":"sn"},//序列号
                {"data":"customer.name"},//客户名称
                {"data":"phone"},//客户电话
                {"data":"vehicle.plate_no"},//车牌号
                {"data":"unit.call_letter"},//车载电话
                {"data":"unit.mode"},//通讯模式
                {"data":"lastPosition.status"},//车辆状态
                {"data":"lastPosition.stamp"},//接收时间
                {"data":"lastPosition.gpsTime"},//定位时间
                {"data":"lastPosition.lon"},//经度
                {"data":"lastPosition.lat"},//纬度
                {"data":"vehicle.id"}//vehicle_id
            ],
            "rowCallback": function(row,data) {
                $('td:eq(5)',row).html(replaceMode(data.unit.mode));
            }
        });
        return add_table22;
    }

    $("#locatefault-table").on("click",  "tbody tr", function(event){
        selectTr(this);
    });

    $("#locatefault-table").on("dblclick",  "tbody tr", function(event){
        selectTr(this);
        var v_id = $(this).children("td").eq(11).html();
        $("#vehicle_id").val(v_id);
        search(term,2);//查询
    });

    //服务费催缴 start

    //服务费催缴查询
    $("#serviceChargeSearch").click(function(){
        if($("#serviceCharge_time").val()==''){
            alert('请选择截止时间!');
        }else{
            if ( typeof(add_table33) != "undefined") {
                serviceChargeReport();
                add_table33.fnReloadAjax("app/callout/service/fee?" + callparas3);
            }
            else{
                serviceChargeReport();
            };
        };
     });

    //服务费催缴导出

    $("#serviceChargeExport").click(function(){
        $(this).attr("href","app/callout/service/fee/export?"+callparas3);
    });

    //服务费催缴
    function serviceChargeReport(){
        var ValcompanyNo = [];//所属公司id
        if ($("#serviceCharge_sub").val()=='0'){
            ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#serviceCharge_sub").val();
        };
        var Valname = encodeURI($("#serviceChargeCosName").val());//客户名称
        var Valpla = encodeURI($("#serviceChargepla").val());//车牌号码
        var Valcallleter = encodeURI($("#serviceChargecallleter").val());//车载电话

        if (ValcompanyNo == []){//所属公司拼接
            serviceChargecompanyNo='';
        }else{
            serviceChargecompanyNo = 'subco_no='+ValcompanyNo+'&';
        };

        if (Valname==''){//客户名称拼接
            serviceChargename='';
        }else{
            serviceChargename = 'linkman='+Valname+'&';
        };

        if (Valpla==''){//车牌号码拼接
            serviceChargepla='';
        }else{
            serviceChargepla = 'plate_no='+Valpla+'&';
        };

        if (Valcallleter==''){//车载电话拼接
            serviceChargecallleter='';
        }else{
           serviceChargecallleter = 'call_letter='+Valcallleter+'&';
        };

        serviceChargeTime = 'fee_sedate='+$("#serviceCharge_time").val();

        callparas3 = serviceChargecompanyNo+serviceChargename+serviceChargepla+serviceChargepla+serviceChargeTime;//所属公司、客户名称、车牌号码、车载电话、时间
        // paras = callparas;//参数;
        add_table33 = $("#serviceCharge-table").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/locatefault?" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"app/callout/service/fee?now=" + new Date().getTime() + "&" + callparas3 //获取数据的API
            },             
            "columns":[
                {"data":"sn"},//0序列号
                {"data":"customer_name"},//1客户名称
                {"data":"plate_no"},//2车牌号码
                {"data":"call_letter"},//3车载电话
                {"data":"unittype"},//4终端类型
                {"data":"sales"},//5销售经理
                {"data":"service_date"},//6开通日期
                {"data":"ac_amount"},//7费用总额
                {"data":"month_fee"},//8服务费
                {"data":"pay_model"},//9托收标记
                {"data":"fee_sedate"},//10服务费截止时间
                {"data":"subco_name"},//11归属公司
                {"data":"vehicle_id"}//12vehicle_id
            ],
            "rowCallback": function(row,data) {
                $('td:eq(9)',row).html(replaceCollect(data.pay_model));
            }
        });
        return add_table33;
    }

    function replaceCollect(num){//托收标记 0=托收, 1=现金, 2=转账, 3=刷卡
        var temp ="";
        switch(num){
            case 0: temp = "托收"; break;
            case 1: temp = "现金"; break;
            case 2: temp = "转账"; break;
            case 3: temp = "刷卡"; break;
            default: temp = "";
        }
        return temp;
    }

    $("#serviceCharge-table").on("click",  "tbody tr", function(event){
        selectTr(this);
    });

    $("#serviceCharge-table").on("dblclick",  "tbody tr", function(event){
        selectTr(this);
        var v_id = $(this).children("td").eq(12).html();
        $("#vehicle_id").val(v_id);
        search(term,2);//查询
    });

    //营销报表 start

    //营销报表查询
    $("#marketingSearch").click(function(){
        if($("#marketing_start").val()==''||$("#marketing_end").val()==''){
            alert('请选择时间段!');
        }else if($("#marketing_start").val() >= $("#marketing_end").val()){
            alert("起始时间不能大于结束时间");
        }else{
            if ( typeof(add_table44) != "undefined") {
                MarketingReport();
                add_table44.fnReloadAjax("app/callout/service/marketing?" + callparas4);
            } else{
                MarketingReport();
            };
        };
    });

    //营销报表导出

    $("#marketingExport").click(function(){
        $(this).attr("href","app/callout/service/marketing/export?"+callparas4);
    });

    //营销查询
    function MarketingReport(){
        var ValcompanyNo = [];//所属公司id
        if ($("#marketing_sub").val()=='0'){
            ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#marketing_sub").val();
        };
        var Valname = encodeURI($("#MarkFCosName").val());//客户名称
        var Valpla = encodeURI($("#MarkFpla").val());//车牌号码
        var Valcallleter = encodeURI($("#MarkFcallleter").val());//车载电话

        if (ValcompanyNo == []){//所属公司拼接
            MarkFcompanyNo='';
        }else{
            MarkFcompanyNo = 'subco_no='+ValcompanyNo+'&';
        };

        if (Valname==''){//客户名称拼接
            MarkFname='';
        }else{
            MarkFname = 'linkman='+Valname+'&';
        };

        if (Valpla==''){//车牌号码拼接
            MarkFpla='';
        }else{
            MarkFpla = 'plate_no='+Valpla+'&';
        };

        if (Valcallleter==''){//车载电话拼接
            MarkFcallleter='';
        }else{
            MarkFcallleter = 'call_letter='+Valcallleter+'&';
        };

        MarkFTime = 'beginTime='+$("#marketing_start").val()+'&'+'endTime='+$("#marketing_end").val();

        callparas4 = MarkFcompanyNo+MarkFname+MarkFpla+MarkFcallleter+MarkFTime;//所属公司、客户名称、车牌号码、车载电话、时间
        // paras = callparas;//参数;
        add_table44 = $("#marketing-table").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/locatefault?" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"app/callout/service/marketing?now=" + new Date().getTime() + "&" + callparas4 //获取数据的API
            },             
            "columns":[
                {"data":"sn"},//0序列号
                {"data":"customer_name"},//1客户名称
                {"data":"plate_no"},//2车牌号码
                {"data":"call_letter"},//3车载电话
                {"data":"color"},//4车辆颜色
                {"data":"id_no"},//5用户身份证
                {"data":"service_date"},//6服务开通时间
                {"data":"linkman"},//7用户电话
                {"data":"model_name"},//8车辆型号
                {"data":"engine_no"},//9引擎号
                {"data":"vin"},//10车架号
                {"data":"is_pilfer"},//11是否享有综合盗抢险
                {"data":"is_corp"},//12是否从公司购买保险
                {"data":"register_date"},//13车辆初次登记日期
                {"data":"subco_name"},//14归属公司
                {"data":"vehicle_id"}//15vehicle_id
            ],
            "rowCallback": function(row,data) {
                $('td:eq(7)',row).html(replaceLinkman(data.linkman));
                $('td:eq(11)',row).html(replaceIs(data.is_pilfer));
                $('td:eq(12)',row).html(replaceIs(data.is_corp));
            }
        });
        return add_table44;
    }

    function replaceLinkman(param){
        var p = String(param);
        var num = p.split("|");
        return num[0];
    }

    function replaceIs(num){//判断是否
        var temp ="";
        switch(num){
            case 0: temp = "否"; break;
            case 1: temp = "是"; break;
            default: temp = "";
        }
        return temp;
    }

    $("#marketing-table").on("click",  "tbody tr", function(event){
        selectTr(this);
    });

    $("#marketing-table").on("dblclick",  "tbody tr", function(event){
        selectTr(this);
        var v_id = $(this).children("td").eq(15).html();
        $("#vehicle_id").val(v_id);
        search(term,2);//查询
    });

});




/**
**当前系统时间 start
**/
function ReDate(){
    //当前系统时间
    var NowTime = new Date();
    var NowTimeM;
    var NowMonth;
    var NowDate;
    var NowHours;
    var NowMinutes;
    var NowSeconds;

    //月
    if ((NowTime.getMonth()+1)<10){
        NowMonth = '0'+(NowTime.getMonth()+1);
    }else{
        NowMonth =NowTime.getMonth()+1;
    };
    //日
    if (NowTime.getDate()<10){
        NowDate = '0'+NowTime.getDate();
    }else{
        NowDate =NowTime.getDate();
    };
    //时
    if (NowTime.getHours()<10){
        NowHours = '0'+NowTime.getHours();
    }else{
        NowHours =NowTime.getHours();
    };
    //分
    if (NowTime.getMinutes()<10){
        NowMinutes = '0'+NowTime.getMinutes();
    }else{
        NowMinutes = NowTime.getMinutes();
    };
    //秒
    if (NowTime.getSeconds()<10){
        NowSeconds = '0'+NowTime.getSeconds();
    }else{
        NowSeconds = NowTime.getSeconds();
    };

    NowTime =NowTime.getFullYear()+ "-"+NowMonth+"-"+NowDate+" "+NowHours+":"+NowMinutes+":"+NowSeconds; 
    // NowTimeM = NowTime.substr(0,10)+" 00:00:00";
    return NowTime;
}

function OnlyDate(){
    //当前系统时间
    var NowTime = new Date();
    var NowTimeM;
    var NowMonth;
    var NowDate;

    //月
    if ((NowTime.getMonth()+1)<10){
        NowMonth = '0'+(NowTime.getMonth()+1);
    }else{
        NowMonth =NowTime.getMonth()+1;
    };
    //日
    if (NowTime.getDate()<10){
        NowDate = '0'+NowTime.getDate();
    }else{
        NowDate =NowTime.getDate();
    };

    NowTime =NowTime.getFullYear()+ "-"+NowMonth+"-"+NowDate; 
    return NowTime;
}

function NextDate(){//第二天年月日
    var NowTime = new Date();
    var NowTimeM;
    var NowMonth;
    var NowDate;

    //月
    if ((NowTime.getMonth()+1)<10){
        NowMonth = '0'+(NowTime.getMonth()+1);
    }else{
        NowMonth =NowTime.getMonth()+1;
    };
    //日
    if (NowTime.getDate()<10){
        NowDate = '0'+ (NowTime.getDate()+1);
    }else{
        NowDate =NowTime.getDate()+1;
    };

    NowTime =NowTime.getFullYear()+ "-"+NowMonth+"-"+NowDate; 
    return NowTime;
}


//起始时间判断

var startT;
var endT;
$('.endTime').blur(function(){
    startT = $('.starTime').val();
    endT = $('.endTime').val();
    if(startT >= endT){
        alert("起始时间不能大于结束时间");
    }    
});

$('.starTime').blur(function(){
    startT = $('.starTime').val();
    endT = $('.endTime').val();
    if(endT && (startT >= endT)){
        alert("起始时间不能大于结束时间");
    }    
});