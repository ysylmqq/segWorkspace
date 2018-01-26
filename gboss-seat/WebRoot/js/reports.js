/*
 * author:zhenglei 2014-12-09
 * 
 */

$(document).ready(function(){
    /*呼入呼出报表新增 start*/
    $(".starTime").val(OnlyDate()+" 00:00:00");
    $(".endTime").val(ReDate());
    var api_url = '../app/servertype/query';//来去电简报选项
    $.getJSON(api_url, function(result){
        if(result && result.success==true){
            /* 来电简报 start */
            var html_callin1 = "";
            var html_callin2 = "";

            if(result.callininfo.length > 0){
                $.each(result.callininfo,function(i,item){
                    html_callin1 += "<label class='cursor'><input type='checkbox' value='"+ item.st_id +"'><span>"+ item.st_name +"</span></label>";
                    if(item.childList !== null && item.childList.length > 0){
                        $.each(item.childList,function(k,k_item){
                            if(k_item.st_name !== "其他"){
                                html_callin2 += "<label class='cursor'><input type='checkbox' value='"+ k_item.st_id +"'><span>"+ k_item.st_name +"</span></label>";
                            }
                        });
                    }
                });
            }
            $("#TOS_opt_in1").empty().append(html_callin1);
            $("#TOS_opt_in2").empty().append(html_callin2);
            /* 来电简报 end */
            /* 去电简报 start */
            var html_callout1 = "";
            var html_callout2 = "";

            if(result.calloutinfo.length > 0){
                $.each(result.calloutinfo,function(i,item){
                    html_callout1 += "<label class='cursor'><input type='checkbox' value='"+ item.st_id +"'><span>"+ item.st_name +"</span></label>";
                    if(item.childList !== null && item.childList.length > 0){
                        $.each(item.childList,function(k,k_item){
                            if(k_item.st_name !== "其他"){
                                html_callout2 += "<label class='cursor'><input type='checkbox' value='"+ k_item.st_id +"'><span>"+ k_item.st_name +"</span></label>";
                            }
                        });
                    }
                });
            }
            $("#TOS_opt_out1").empty().append(html_callout1);
            $("#TOS_opt_out2").empty().append(html_callout2);
            /* 去电简报 end */
        }
    });
    /*呼入呼出报表新增 end*/

    $('.reports_search_1_list').hide(); //隐藏推荐栏
    $('.reports_search_4_list').hide(); //隐藏推荐栏
    var startTD = OnlyDate()+" 00:00:00";
    var endTD = OnlyDate()+" 23:59:59";
    $('.startTD').val(startTD);
    $('.endTD').val(endTD);

    /**
    **电话呼入查询报表 start
    **/

    var add_table;
    var callparas;
    var paras;
    //呼入报表查询
    $("#callSearch").click(function(){    
        if ($(".search_filiale_id").val()=='') {
            alert('没有找到匹配的公司，请重新输入！');  
            $(".reports_search_4_engine").focus();  
        }else if ($("#CallDateQuantum").attr("checked")=="checked" && ($("#CallStartTime").val()==''||$("#CallEndTime").val()=='')){
            alert('请选择时间段!');     
        }else if($("#CallDateQuantum").attr("checked")=="checked" && ($("#CallStartTime").val() >= $("#CallEndTime").val())){
            alert("起始时间不能大于结束时间");
        }else{
            if ( typeof(add_table) != "undefined") {
                CallReport();
                add_table.fnReloadAjax("../app/report/brief?now=" + new Date().getTime() + "&" + paras);
            }
            else{
                CallReport();
            };
            // CallReport();
            // add_table.fnReloadAjax();
            // add_table.fnReloadAjax();

            // // CallReport();
        };    
     });

    //呼入报表导出

    $("#callExport").click(function(){
        $(this).attr("href","../app/report/brief/export?"+callparas);
    });



    function CallReport(){
        var ValvehicleId = $("#callCarID").val();
        var ValpartNo =  encodeURIComponent($("#callCarNum").val());
        var Valnumber =  $("#callin_num").val();
        var ValcompanyNo = [];
        if ($("#callID_search").val()=='0'){
            var ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#callID_search").val();
        };
        var ValserviceType = [];
        var serType =  $(".TOS_opt input:checked").siblings("span");
        for (var i=0;i<serType.length;i++){
            ValserviceType[i] = encodeURIComponent(serType[i].innerHTML);
        };
        var ValserviceTypeOther = encodeURIComponent($("#TOS_others").val());
        var Valtype = 1;
        if ($("#callType input:checked").length==1){
            Valtype = $("#callType input:checked").val();
        }else{
            Valtype = '';
        };
        var ValbeginTime;
        var ValendTime;
        if ($("#CallDateNow").attr("checked")=="checked"){
            ValendTime = ReDate();
            ValbeginTime = ValendTime.substr(0,10)+" 00:00:00";
        }else if ($("#CallDateQuantum").attr("checked")=="checked"){
            ValbeginTime = $("#CallStartTime").val();
            ValendTime = $("#CallEndTime").val();
        };

        if (ValvehicleId==''){
            CallvehicleId='';
            if (ValpartNo==''){
                CallpartNo='';
            }else{
                CallpartNo = 'partNo='+ValpartNo+'&';
            };
        }else{
            CallvehicleId = 'vehicleId='+ValvehicleId+'&';
            CallpartNo='';
        };

        if (Valnumber==''){
            Callnumber='';
        }else{
            Callnumber = 'number='+Valnumber+'&';
        };
     
        if (ValcompanyNo==''){
            CallcompanyNo='';
        }else{
            CallcompanyNo = 'companyNo='+ValcompanyNo+'&';
        };

        if (ValserviceType==''){
            CallserviceType='';
        }else{
            var valueService='';
            for (var i=0;i<ValserviceType.length;i++){
                valueService += 'serviceType='+ValserviceType[i]+'&';
            };        
            CallserviceType = valueService;
        };

        if (ValserviceTypeOther==''){
            CallserviceTypeOther='';
        }else{
            CallserviceTypeOther = 'serviceTypeOther='+ValserviceTypeOther+'&';
        };

        if (Valtype==''){
            Calltype='';
        }else{
            Calltype = 'type='+Valtype+'&';
        };

        if (ValbeginTime==''){
            CallbeginTime='';
        }else{
            CallbeginTime = 'beginTime='+ValbeginTime+'&';
        };

        if (ValendTime==''){
            CallendTime='';
        }else{
            CallendTime = 'endTime='+ValendTime;
        };

        // if ( typeof(add_tables) != "undefined") {
        //     add_table.fnClearTable( 0 );
        //     add_table.fnDraw();
        //     //add_table.fnDestroy();   
        //     $('#callinQuery-example').dataTable({"bDestroy":true});
        // };

        callparas = CallvehicleId+CallpartNo+Callnumber+CallcompanyNo+CallserviceType+CallserviceTypeOther+Calltype+CallbeginTime+CallendTime;
        paras = callparas;//参数;
        add_table = $("#callinQuery-example").dataTable({
            "bSort": false,//不排序
            // 'sEcho': 1,
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/brief?now=" + new Date().getTime() + "&" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"../app/report/brief?now=" + new Date().getTime() + "&" + paras //获取数据的API
            },             
            "columns":[
                {"data":"sn"},
                {"data":"vuInfo.plate_no"},
                {"data":"vuInfo.call_letter"},
                {"data":"brief.stamp"},
                {"data":"brief.phoneNo"},
                {"data":"brief.type"},
                {"data":"brief.serviceContent"},
                {"data":"customerName"},
                {"data":"brief.opName"}
            ],
            "rowCallback": function(row,data) {
                $('td:eq(5)',row).html(analyzeType(data.brief.type));
            }           
        });
        return add_table;
    }

    // 呼叫类型（1:呼入,2:呼出）
    function analyzeType(type){
        var temp ="";
        switch(type){
            case 1: temp = "呼入"; break;
            case 2: temp = "呼出"; break;
        };
        return temp;
    }

    /**
    **电话呼入查询报表 end
    **/


    //案发车辆记录报表查询
    $("#stolenSearch").click(function(){    
        if ($(".search_filiale_id").val()=='') {
            alert('没有找到匹配的公司，请重新输入！');  
            $(".reports_search_4_engine").focus();  
        }else if(checkTimeBox()==0){//判断时间是否为空
            return;
        }else if($('#stolenRStartTime').val() >= $('#stolenREndTime').val()){
            alert("起始时间不能大于结束时间");
            return;
        }else{
            if ( typeof(add_table) != "undefined") {
                stolenReport();
                add_table.fnReloadAjax("../app/report/stolenvehicle?now=" + new Date().getTime() + "&" + paras);
            }
            else{
                stolenReport();
            };
        };    
     });

    //案发车辆记录报表导出

    $("#stolenExport").click(function(){
        $(this).attr("href","../app/report/stolenvehicle/export?"+callparas);
    });

    //案发车辆记录查询
    function stolenReport(){
        var ValvehicleId = $("#stolenRCarID").val();//车辆id（隐藏）
        var ValpartNo =  $("#stolenCarNum").val();//部分车牌或车载
        var Valname =  encodeURI($("#stolenRCosName").val());//客户名称
        var Valphone =  $("#stolenRPhone").val();//联系电话
        var ValcompanyNo = [];//所属公司id
        if ($("#stolenR_ID_search").val()=='0'){
            var ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#stolenR_ID_search").val();
        };
        var ValcaseType = $("#stolenCaseType").val();//案件类型(1:被盗 2.被劫 3.被撬 4.玻璃被砸) 如果选择全部，则不传此参数
        
        var ValbeginTime = $("#stolenRStartTime").val();//开始时间
        var ValendTime = $("#stolenREndTime").val();//结束时间

        var ValisCallPolice = 0;//是否报警(1:是 0:否)
        if($("#iscallPolice").attr("checked")=="checked"){
            ValisCallPolice = 1;
        }
        var ValbuyTp = 0;//是否含盗抢险(1:是 0:否)
        if($("#containTP").attr("checked")=="checked"){
            ValbuyTp = 1;
        }

        if (ValvehicleId==''){//车辆id或部分车牌车载拼接
            stolenRvehicleId='';
            if (ValpartNo==''){
                stolenRpartNo='';
            }else{
                stolenRpartNo = 'partNo='+ValpartNo+'&';
            };
        }else{
            stolenRvehicleId = 'vehicleId='+ValvehicleId+'&';
            stolenRpartNo='';
        };

        if (Valname==''){//客户名称拼接
            stolenRname='';
        }else{
            stolenRname = 'name='+Valname+'&';
        };
     
        if (Valphone==''){//联系电话拼接
            stolenRphone = '';
        }else{
            stolenRphone = 'phone='+Valphone+'&';
        };

        if (ValcompanyNo==''){//所属公司拼接
            stolenRcompanyNo='';
        }else{
            stolenRcompanyNo = 'companyNo='+ValcompanyNo+'&';
        };

        if (ValcaseType == '0'){//案件类型拼接
            stolenRcaseType='';
        }else{
            stolenRcaseType = 'caseType='+ValcaseType+'&';
        };

        if (ValbeginTime==''){//开始时间拼接
            stolenRbeginTime='';
        }else{
            stolenRbeginTime = 'beginTime='+ValbeginTime+'&';
        };

        if (ValendTime==''){//结束时间拼接
            stolenRendTime='';
        }else{
            stolenRendTime = 'endTime='+ValendTime+'&';
        };

        stolenRisCallPolice = 'isCallPolice='+ValisCallPolice+'&';//是否报警
        stolenRbuyTp = 'buyTp='+ValbuyTp+'&';//是否含盗抢险

        if($("#tamperType").val()==0){//防拆类型(0：全部 1:防拆盒 2.智能防拆 3.无线防拆) 如果选择全部，则不传此参数
            stolenRtamperType='';
        }else if($("#tamperType").val()==1){//1:防拆盒
            stolenRtamperType='tamperBox=1';
        }else if($("#tamperType").val()==2){//2.智能防拆
            stolenRtamperType='tamperSmart=1';
        }else if($("#tamperType").val()==3){//3.无线防拆
            stolenRtamperType='tamperWireless=1';
        }
        //alert(stolenRtamperType);
        callparas = stolenRvehicleId+stolenRpartNo+stolenRname+stolenRphone+stolenRcompanyNo+stolenRcaseType+stolenRbeginTime+stolenRendTime+stolenRisCallPolice+stolenRbuyTp+stolenRtamperType;//车辆id、部分车牌或车载号、客户名称、联系电话、所属公司id、案件类型、开始时间、结束时间、是否报警、是否含盗抢险、防拆类型

        paras = callparas;//参数;
        add_table = $("#stolenRecords-example").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/stolenvehicle?now=" + new Date().getTime() + "&" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"../app/report/stolenvehicle?now=" + new Date().getTime() + "&" + paras //获取数据的API
            },             
            "columns":[
                {"data":"sn"},//序列号
                {"data":"customer.name"},//客户名称
                {"data":"vehicle.plate_no"},//车牌号
                {"data":"unit.call_letter"},//车载电话
                {"data":"vehicle.modelName"},//车型
                {"data":"unit.type"},//产品型号
                {"data":"unit.place"},//安装地点
                {"data":"unit.createDate"},//入网时间
                {"data":"stolenVehicle.caseType"},//案件类型
                {"data":"stolenVehicle.beginTime"},//案发时间
                {"data":"stolenVehicle.isCallPolice"},//是否报警
                {"data":"stolenVehicle.isCapture"},//是否截获
                {"data":"unit.buyTp"},//是否含盗抢险
                {"data":"stolenVehicle.endTime"},//结案时间
                {"data":"unit.tamperBox"},//是否加装防拆盒
                {"data":"unit.tamperSmart"},//智能防拆
                {"data":"unit.tamperWireless"},//无线防拆
                {"data":"vehicle.id"},//车辆id
                {"data":"stolenVehicle.id"}//案件编号
            ],
            "rowCallback": function(row,data) {
                $('td:eq(8)',row).html(replaceCaseType(data.stolenVehicle.caseType));
                $('td:eq(10)',row).html(replaceType(data.stolenVehicle.isCallPolice));
                $('td:eq(11)',row).html(replaceType(data.stolenVehicle.isCapture));
                $('td:eq(12)',row).html(replaceType(data.unit.buyTp));

                $('td:eq(14)',row).html(replaceType(data.unit.tamperBox));
                $('td:eq(15)',row).html(replaceType(data.unit.tamperSmart));
                $('td:eq(16)',row).html(replaceType(data.unit.tamperWireless));

                $('td:eq(17)',row).css("display","none");
                $('td:eq(18)',row).css("display","none");
            }
        });
        return add_table;
    }

    //（是或否）类型转换（1是、2否）
    function replaceType(num){
        var temp ="";
        switch(num){
            case 0: temp = "否"; break;
            case 1: temp = "是"; break;
            default: temp = "";
        }
        return temp;
    }

    //案件类型转换（1被盗、2被劫、3被撬、4玻璃被砸）
    function replaceCaseType(num){
        var temp ="";
        switch(num){
            case 1: temp = "被盗"; break;
            case 2: temp = "被劫"; break;
            case 3: temp = "被撬"; break;
            case 4: temp = "玻璃被砸"; break;
            default: temp = "";
        }
        return temp;
    }


    //未上报统计报表 start

    //未上报统计报表报表查询
    $("#recRSearch").click(function(){    
        if ($(".search_filiale_id").val()=='') {
            alert('没有找到匹配的公司，请重新输入！');  
            $(".reports_search_4_engine").focus();  
        }else{
            if ( typeof(add_table) != "undefined") {
                recReport();
                add_table.fnReloadAjax("../app/report/unreportstat?" + paras);
            }
            else{
                recReport();
            };
        };    
     });

    //未上报统计报表导出

    $("#recRExport").click(function(){
        $(this).attr("href","../app/report/unreportstat/export?"+callparas);
    });

    //未上报统计查询
    function recReport(){
        var ValcompanyNo = [];//所属公司id
        if ($("#recR_ID_search").val()=='0'){
            var ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#recR_ID_search").val();
        };
        var Valname = encodeURI($("#recRName").val());//客户名称
        var ValcardType = $("#recRCardType").val();//卡类型(0:全部 1:自带卡 2.公司卡 3.总部卡 ) 如果选择全部，则不传此参数
        var ValcommType = getcomtent("recentRep_option2");//通信模式(1:显示GSM 3.全部 )
        var ValRepType = getcomtent("recentRep_option1");//大类(0:未上报 1.已上报 )
        var ValTimeInt = getcomtent("recentRep_option3");//未上报时间间隔(1:当天未上报,3:3天未上报,5:5天未上报,0:选择时间段)

        var ValbeginTime = '';//开始时间
        var ValendTime = '';//结束时间

        if (ValcompanyNo==''){//所属公司拼接
            recRcompanyNo='';
        }else{
            recRcompanyNo = 'companyNo='+ValcompanyNo+'&';
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

        if (ValcommType == '0') {//通信模式拼接
            recRcommType='';
        } else{
            recRcommType = 'mode='+ValcommType+'&';
        };

        isRep = 'report='+ValRepType +'&';//是否上报(0=未上报，1=已上报)

        recTime = '';//时间间隔
        if(ValRepType==0){//若为未上报
            var timel = getcomtent("recentRep_option3");//获取所选项
            if(timel == 1){//当天未上报
                recTime = 'interval=1';
            } else if(timel == 3){//3天未上报
                recTime = 'interval=3';
            } else if(timel == 5){//5天未上报
                recTime = 'interval=5';
            } else if(timel == 0){//选择时间段
                if($("#unRStartTime").val() >= $("#unREndTime").val()){
                    alert("起始时间不能大于结束时间");
                    return false;
                }
                recTime = 'beginTime='+$("#unRStartTime").val() +'&'+'endTime='+$("#unREndTime").val();
            }
        } else if(ValRepType==1){//若为已上报
            if($("#beenRStartTime").val() >= $("#beenREndTime").val()){
                alert("起始时间不能大于结束时间");
                return false;
            }
            recTime = 'beginTime='+$("#beenRStartTime").val() +'&'+'endTime='+$("#beenREndTime").val();//alert(recTime);
        }

        callparas = recRcompanyNo+recRname+recRcaseType+recRcommType+isRep+recTime;//所属公司、客户名称、SIM卡类型、通信模式、是否上报、时间间隔

        paras = callparas;//参数;
        add_table = $("#recentReported-example").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/unreportstat?" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"../app/report/unreportstat?now=" + new Date().getTime() + "&" + paras //获取数据的API
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
                {"data":"unit.sales"}//营销经理
            ],
            "rowCallback": function(row,data) {
                $('td:eq(9)',row).html(replaceMode(data.unit.mode));
            }
        });
        return add_table;
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

    //定位故障通知报表 start

    //定位故障通知报表查询
    $("#LocaFSearch").click(function(){    
        if ($(".search_filiale_id").val()=='') {
            alert('没有找到匹配的公司，请重新输入！');  
            $(".reports_search_4_engine").focus();  
        }else if($("#locateFDate").attr("checked")=="checked" && ($("#LocaFStartTime").val()==''||$("#LocaFEndTime").val()=='')){
            alert('请选择时间段!');
        }else if($("#locateFDate").attr("checked")=="checked" && ($("#LocaFStartTime").val() >= $("#LocaFEndTime").val())){
            alert("起始时间不能大于结束时间");
        }else{
            if ( typeof(add_table) != "undefined") {
                LocaFReport();
                add_table.fnReloadAjax("../app/report/locatefault?" + paras);
            }
            else{
                LocaFReport();
            };
        };    
     });

    //定位故障通知报表导出

    $("#LocaFExport").click(function(){
        $(this).attr("href","../app/report/locatefault/export?"+callparas);
    });

    //定位故障通知查询
    function LocaFReport(){
        var ValcompanyNo = [];//所属公司id
        if ($("#LocaF_ID_search").val()=='0'){
            var ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#LocaF_ID_search").val();
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

        if (ValcompanyNo==''){//所属公司拼接
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
        };//alert(LocaFFaultType);

        if (Valdate == 1){//当天
            LocaFTime = 'interval=1';
        } else if(Valdate == 2){//若为选择时间段
            LocaFTime = 'beginTime='+$("#LocaFStartTime").val()+'&'+'endTime='+$("#LocaFEndTime").val()+'&';
        };//alert(LocaFTime);

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

        callparas = LocaFcompanyNo+LocaFname+LocaFFaultType+LocaFTime+TimeDif+LocaFlon+LocaFlat;//所属公司、客户名称、故障类型、时间、天数差、经度、纬度

        paras = callparas;//参数;
        add_table = $("#locatefault-example").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/locatefault?" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"../app/report/locatefault?now=" + new Date().getTime() + "&" + paras //获取数据的API
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
                {"data":"lastPosition.lat"}//纬度
            ],
            "rowCallback": function(row,data) {
                $('td:eq(5)',row).html(replaceMode(data.unit.mode));
            }
        });
        return add_table;
    }

    //处警响应率报表 start

    //处警响应率报表查询
    $("#alarmResRateSearch").click(function(){    
        if ($(".search_filiale_id").val()=='') {
            alert('没有找到匹配的公司，请重新输入！');  
            $(".reports_search_4_engine").focus();  
        }else if(checkTimeBox()==0){//判断是否为空
            return;
        }else if($('#alarmRStartTime').val() >= $('#alarmREndTime').val()){
            alert("起始时间不能大于结束时间");
            return;
        }else{
            if ( typeof(add_table) != "undefined") {
                AlarmReport();
                add_table.fnReloadAjax("../app/report/respratio?" + paras);
            }
            else{
                AlarmReport();
            };
        };    
     });

    //处警响应率报表导出

    $("#alarmResRateExport").click(function(){
        $(this).attr("href","../app/report/respratio/export?"+callparas);
    });

    //处警响应率查询
    function AlarmReport(){
        var ValcompanyNo = [];//所属公司id
        if ($("#alarmR_ID_search").val()=='0'){
            ValcompanyNo = [];
        }else{
            ValcompanyNo[0] = $("#alarmR_ID_search").val();
        };
        
        var ValbeginTime = $("#alarmRStartTime").val();//开始时间
        var ValendTime = $("#alarmREndTime").val();//结束时间

        var ValInterval= $("#alarmInterval").val();//时间间隔

        if (ValcompanyNo==''){//所属公司拼接
            AlarmFcompanyNo='';
        }else{
            AlarmFcompanyNo = 'companyNo='+ValcompanyNo+'&';
        };

        if (ValbeginTime==''){//开始时间
            AlarmFbeginTime='';
        }else{
            AlarmFbeginTime = 'beginTime='+ValbeginTime+'&';
        };

        if (ValendTime==''){//结束时间
            AlarmFendTime='';
        }else{
            AlarmFendTime = 'endTime='+ValendTime+'&';
        };

        AlarmFinterval = 'interval='+ValInterval;//时间间隔

        callparas = AlarmFcompanyNo+AlarmFbeginTime+AlarmFendTime+AlarmFinterval;//所属公司、开始时间、结束时间、时间间隔
        
        paras = callparas;//参数;
        add_table = $("#alarmResponseRate-example").dataTable({
            "bSort": false,//不排序
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            // "ajax" : "../app/report/respratio?" + paras, //获取数据的API
            "ajax" : {
                "type":"POST",
                "url":"../app/report/respratio?now=" + new Date().getTime() + "&" + paras //获取数据的API
            },             
            "columns":[
                {"data":"sn"},//序列号
                {"data":"beginTime"},//时间段
                {"data":"sum"},//总数
                {"data":"statItems.5sec"},//5秒
                {"data":"statItems.5sec%"},//5秒%
                {"data":"statItems.10sec"},//10秒
                {"data":"statItems.10sec%"},//10秒%
                {"data":"statItems.before10sec"},//前10秒
                {"data":"statItems.before10sec%"},//前10秒%
                {"data":"statItems.before30sec"},//前30秒
                {"data":"statItems.before30sec%"},//前30秒%
                {"data":"statItems.1min"},//1分
                {"data":"statItems.1min%"},//1分%
                {"data":"statItems.2min"},//2分
                {"data":"statItems.2min%"},//2分%
                {"data":"statItems.3min"},//3分
                {"data":"statItems.3min%"},//3分%
                {"data":"statItems.before3min"},//前3分
                {"data":"statItems.before3min%"},//前3分%
                {"data":"statItems.5min"},//5分
                {"data":"statItems.5min%"},//5分%
                {"data":"statItems.10min"},//10分
                {"data":"statItems.10min%"},//10分%
                {"data":"statItems.20min"},//20分
                {"data":"statItems.20min%"}//20分%
            ],
            "rowCallback": function(row,data) {
                $('td:eq(1)',row).html(data.beginTime+"-"+data.endTime);
            }
        });
        return add_table;
    }


    /**
    **操作日志报表 start
    **/

    //操作日志查询
    $("#operatorSearch").click(function(){    
        if (($("#OperationStartTime").val()&&$("#OperationEndTime").val())==''){
            alert('请选择时间段');  
        }else{
            if (typeof(add_table) != "undefined") {
                OperationReport();
                add_table.fnReloadAjax("../app/report/sendcmd?now=" + new Date().getTime() + "&" + paras);
            }
            else{
                OperationReport();
            };
        };    
     });

    //操作日志导出

    $("#operatorExport").click(function(){
        $(this).attr("href","../app/report/sendcmd/export?"+callparas);
    });

    function OperationReport(){
        var ValpartNo;
        var OperpartNo;
        if($("#OperationCarID").val()){
            ValpartNo = $("#operCallLetter").val();
        }else{
            ValpartNo = encodeURIComponent($("#operCarNum").val());
        };

        if (ValpartNo==''){
            OperpartNo='';
        }else{
            OperpartNo = 'partNo='+ValpartNo+'&';
        };       

        var ValcmdName =  encodeURIComponent($("#orderName").val());
        var OpercmdName;

        if (ValcmdName==''){
            OpercmdName='';
        }else{
            OpercmdName = 'cmdName='+ValcmdName+'&';
        };  

        var ValopName =  encodeURIComponent($("#operator").val());
        var OperopName;

        if (ValopName==''){
            OperopName='';
        }else{
            OperopName = 'opName='+ValopName+'&';
        };  

        var ValbeginTime =  $("#OperationStartTime").val();
        var OperbeginTime;

        if (ValbeginTime==''){
            OperbeginTime='';
        }else{
            OperbeginTime = 'beginTime='+ValbeginTime+'&';
        };

        var ValendTime =  $("#OperationEndTime").val();
        var OperendTime;

        if (ValendTime==''){
            OperendTime='';
        }else{
            OperendTime = 'endTime='+ValendTime+'&';
        };

        callparas = OperpartNo+OpercmdName+OperopName+OperbeginTime+OperendTime;
        paras = callparas;//参数;
        add_table = $("#OperationQuery").dataTable({
            "bSort": false,//不排序
            // 'sEcho': 1,
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            "ajax" : {
                "type":"POST",
                "url":"../app/report/sendcmd?now=" + new Date().getTime() + "&" + paras //获取数据的API
            },            
            // "ajax" : "../app/report/sendcmd?now=" + new Date().getTime() + "&" + paras, //获取数据的API
            "columns":[
                {"data":"sn"},
                {"data":"sendCmd.companyName"}, 
                {"data":"sendCmd.op_name"},
                {"data":"sendCmd.plate_no"},
                {"data":"sendCmd.callLetter"},
                {"data":"sendCmd.cmd_time"},
                {"data":"sendCmd.mode"},
                {"data":"sendCmd.cmdName"},
                {"data":"sendCmd.send_params"},
                {"data":"sendCmd.send_flag"},
                {"data":"sendCmd.op_ip"}
            ],
            "rowCallback": function(row,data) {
                $('td:eq(6)',row).html(CommunicationMode(data.sendCmd.mode));
                $('td:eq(9)',row).html(SendResult(data.sendCmd.send_flag));

            }           
        });
        return add_table;
    }

    // 通信模式（1:短信,2:数据流量）
    function CommunicationMode(mode){
        var temp ="";
        switch(mode){
            case 1: temp = "短信"; break;
            case 2: temp = "数据流量"; break;
        };
        return temp;
    }

    // 是否发送成功（0:发送成功,>0:发送失败）
    function SendResult(code){
        var temp ="";
        if(code>0) {
            temp = "发送失败";
        }else if(code==0) {
            temp = "发送成功";
        };
        return temp;
    }

    /**
    **操作日志报表 end
    **/



    /**
    **警情查询报表 start
    **/

    //警情查询查询
    $("#AlarmSearch").click(function(){    
        if (($("#AlarmStartTime").val()&&$("#AlarmEndTime").val())==''){
            alert('请选择时间段');  
        }else{
            if (typeof(add_table) != "undefined") {
                AlarmReportWhole();
                add_table.fnReloadAjax("../app/report/queryalarm?now=" + new Date().getTime() + "&" + paras);
            }
            else{
                AlarmReportWhole();
            };
        };    
     });

    //警情查询导出

    $("#AlarmExport").click(function(){
        $(this).attr("href","../app/report/queryalarm/export?"+callparas);
    });

    function AlarmReportWhole(){
        var ValpartNo;
        var AlarmpartNo;
        if($("#AlarmCarID").val()){
            ValpartNo = $("#AlarmCarLetter").val();
        }else{
            ValpartNo = encodeURIComponent($("#AlarmCarNum").val());
        };

        if (ValpartNo==''){
            AlarmpartNo='';
        }else{
            AlarmpartNo = 'partNo='+ValpartNo+'&';
        };       


        var ValstatusIds = $("#AlarmStatusId").val();
        var AlarmstatusIds;

        if (ValstatusIds==''){
            AlarmstatusIds='';
        }else{
            AlarmstatusIds = 'statusIds='+ValstatusIds+'&';
        };       


        var ValopName =  encodeURIComponent($("#Alarmoperator").val());
        var AlarmopName;
        if (ValopName==''){
            AlarmopName='';
        }else{
            AlarmopName = 'opName='+ValopName+'&';
        };  

        var ValbeginTime =  $("#AlarmStartTime").val();
        var AlarmbeginTime;
        if (ValbeginTime==''){
            AlarmbeginTime='';
        }else{
            AlarmbeginTime = 'beginTime='+ValbeginTime+'&';
        };

        var ValendTime =  $("#AlarmEndTime").val();
        var AlarmendTime;
        if (ValendTime==''){
            AlarmendTime='';
        }else{
            AlarmendTime = 'endTime='+ValendTime+'&';
        };

        callparas = AlarmpartNo+AlarmstatusIds+AlarmopName+AlarmbeginTime+AlarmendTime;
        paras = callparas;//参数;
        add_table = $("#AlarmPolling").dataTable({
            "bSort": false,//不排序
            // 'sEcho': 1,
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            "ajax" : {
                "type":"POST",
                "url":"../app/report/queryalarm?now=" + new Date().getTime() + "&" + paras //获取数据的API
            },             
            // "ajax" : "../app/report/queryalarm?now=" + new Date().getTime() + "&" + paras, //获取数据的API
            "columns":[
                {"data":"sn"},
                {"data":"companyName"}, 
                {"data":"customer"},
                {"data":"plateNo"},
                {"data":"alarm.callLetter"},
                {"data":"vehicleType"},
                {"data":"mode"},
                {"data":"status"},
                {"data":"alarm.opName"},
                {"data":"alarm.alarm_time"},
                {"data":"alarm.accept_time"},
                {"data":"alarm.handle_time"},
                {"data":"alarm.result"}
            ],
            "rowCallback": function(row,data) {
                $('td:eq(6)',row).html(CommunicationMode(data.mode));
            }           
        });
        return add_table;
    }


    /**
    **警情查询报表 end
    **/


    /**
    **车台通信 start
    **/
    $("#vehicleCommSearch").click(function(){    
        if (($("#vehicleCommStartTime").val()&&$("#vehicleCommEndTime").val())==''){
            alert('请选择时间段');  
        }else{
            if (typeof(add_table) != "undefined") {
                vehicleCommReportWhole();
                add_table.fnReloadAjax("../app/report/queryUnitCom?now=" + new Date().getTime() + "&" + paras);
            }
            else{
                vehicleCommReportWhole();
            };
        };    
     });

    //车台通信导出

    $("#vehicleCommExport").click(function(){
        $(this).attr("href","../app/report/queryUnitCom/export?"+callparas);
    });

    function vehicleCommReportWhole(){
        var Valcallletter = $("#vehicleCommCallletter").val();//部分车牌或车载   
        var ValbeginTime = $("#vehicleCommStartTime").val();//开始时间
        var ValendTime = $("#vehicleCommEndTime").val();//结束时间

        var vehCallletter;
        if (Valcallletter==''){
            vehCallletter='';
        }else{
            vehCallletter = 'callLetter='+Valcallletter+'&';
        };

        var vehstartTime;
        if (ValbeginTime==''){
            vehstartTime='';
        }else{
            vehstartTime = 'beginTime='+ValbeginTime+'&';
        };

        var vehendTime;
        if (ValendTime==''){
            vehendTime='';
        }else{
            vehendTime = 'endTime='+ValendTime;
        };

        callparas = vehCallletter+vehstartTime+vehendTime;
        paras = callparas;//参数;
        add_table = $("#vehicleComm-example").dataTable({
            "bSort": false,//不排序
            // 'sEcho': 1,
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            "ajax" : {
                "type":"POST",
                "url":"../app/report/queryUnitCom?now=" + new Date().getTime() + "&" + paras //获取数据的API
            },             
            // "ajax" : "../app/report/queryalarm?now=" + new Date().getTime() + "&" + paras, //获取数据的API
            "columns":[
                {"data":"sn"},
                {"data":"customer"},//用户名
                {"data":"plateNo"},//车牌号
                {"data":"callLetter"},//车载电话
                {"data":"mode"},//通信模式
                {"data":"gpstime"},//接收时间
                {"data":"status"},//车辆状态
                {"data":"companyName"}//所属公司
            ],
            "rowCallback": function(row,data) {
                $('td:eq(4)',row).html(CommunicationMode(data.mode));
            }           
        });
        return add_table;
    }

    /**
    **车台通信 end
    **/

    /**
    **大量上报前50名 start
    **/

    //大量上报前50名查询
    $("#TopSearch").click(function(){    
        if (($("#TopStartTime").val()&&$("#TopEndTime").val())==''){
            alert('请选择时间段');  
        }else{
            if (typeof(add_table) != "undefined") {
                AmountReportWhole();
                add_table.fnReloadAjax("../app/report/queryManyGps?now=" + new Date().getTime() + "&" + paras);
            }
            else{
                AmountReportWhole();
            };
        };    
     });

    //大量上报前50名导出

    $("#TopExport").click(function(){
        $(this).attr("href","../app/report/queryManyGps/export?"+callparas);
    });

    function AmountReportWhole(){

        var ValbeginTime =  $("#TopStartTime").val();
        var TopbeginTime;
        if (ValbeginTime==''){
            TopbeginTime='';
        }else{
            TopbeginTime = 'beginTime='+ValbeginTime+'&';
        };

        var ValendTime =  $("#TopEndTime").val();
        var TopendTime;
        if (ValendTime==''){
            TopendTime='';
        }else{
            TopendTime = 'endTime='+ValendTime+'&';
        };

        var ValGPSMode;
        var TopGSM = $("#TopGSM");
        var TopGPRS = $("#TopGPRS");
        var TopMode = $("input[name='TopMode']:checked");
        if (TopMode.length==1){
            ValGPSMode = TopMode.val();
        }else{
            ValGPSMode='';
        };        
        var TopGPSMode;
        if (ValGPSMode==''){
            TopGPSMode='';
        }else{
            TopGPSMode = 'mode='+ValGPSMode+'&';
        };

        callparas = TopGPSMode+TopbeginTime+TopendTime;
        paras = callparas;//参数;
        add_table = $("#TopTableExport").dataTable({
            "bSort": false,//不排序
            // 'sEcho': 1,
            "filter": false,//去掉搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "oLanguage": {
                "sUrl":"../assets/data-tables/js/dataTable.cn.txt"
            },
            "ajax" : {
                "type":"POST",
                "url":"../app/report/queryManyGps?now=" + new Date().getTime() + "&" + paras //获取数据的API
            },             
            // "ajax" : "../app/report/queryManyGps?now=" + new Date().getTime() + "&" + paras, //获取数据的API
            "columns":[
                {"data":"sn"},
                {"data":"plateNo"},
                {"data":"callLetter"},
                {"data":"fix_time"},
                {"data":"gps_time"},
                {"data":"alarmcount"},
                {"data":"mode"}
            ],
            "rowCallback": function(row,data) {
                $('td:eq(6)',row).html(CommunicationMode(data.mode));
            }           
        });
        return add_table;
    }


    /**
    **大量上报前50名 end
    **/

    
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

/**
**当前系统时间 end
**/

//任何地方被点击
$(document).click(function(){
    $('.reports_search_1_list').hide(); //隐藏推荐栏
    $('.reports_search_4_list').hide(); //隐藏推荐栏
});

/**
**搜索车牌和车载号
**/
$(".reports_search_1_engine").on("input", function(){
        $('.search_plate_no').val('');
        $('.search_call_letter').val('');
        $('.search_vehicle_id').val('');
        reports_recommend1(); //触发关键词推荐，显示出推荐栏
    });
function reports_recommend1(){
    var keyword = $.trim($(".reports_search_1_engine").val());
    if(keyword=='') {
        //$(".reports_search_1_list").hide();
        return false;
    }
    //准备参数
    api_url = '../app/data/vehicleinfo'; //推荐候选词API（常规情况测试）
    params = {param:keyword,count:5}
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            if(result.info.length==1 && (result.info[0].plate_no==keyword || result.info[0].call_letter==keyword)){
                //$(".reports_search_1_engine").css("color","#555");
                //$(".mark_ok").show();
                $(".reports_search_1_engine").val(result.info[0].plate_no+" "+result.info[0].call_letter);//填入搜索框
                $(".search_plate_no").val(result.info[0].plate_no);//写入车牌
                $(".search_call_letter").val(result.info[0].call_letter);//写入车载
                $(".search_vehicle_id").val(result.info[0].vehicle_id);//写入车辆id
                $(".reports_search_1_list").hide();
                return false;
            }
            idx = -1; 
            $(".reports_search_1_list").html('');
            $.each(result.info, function(i,item){
                $(".reports_search_1_list").append('<li id="pla_rec'+i+'"><span class="p_plate_no">'+item.plate_no+'</span> <span class="p_call_letter">'+item.call_letter+'</span><span class="hide p_vehicle_id">'+item.vehicle_id+'</span></li>');
            });
            $(".reports_search_1_list").show();
        }else{
            $(".reports_search_1_list").hide();
        }
    });
}
//键盘上下移动光标和按回车
var idx = -1; //初始化高亮项
$(".reports_search_1_engine").keyup(function(event){
    max = $('.reports_search_1_list li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
    if(event.keyCode == 40){ //按了下箭头
        if(idx < max) idx++;
        next_word1(idx);
    }else if(event.keyCode == 38){  //按了上箭头                 
        if(idx > 0) idx--;
        next_word1(idx);
    }else if(event.keyCode == 13){ //按了回车
        if($(".reports_search_1_list li.current").text()){ //如果此时有高亮候选词，选中它并搜索
            select_word1($(".reports_search_1_list li.current"));
        }
    }
});

//鼠标点击推荐词被点击时
$(".reports_search_1_list li").live("click",function(){
    select_word1($(this));
}).live("mouseover", function(){ //鼠标滑过推荐词时
    idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
    next_word1(idx);
});

function next_word1(idx){
    $(".reports_search_1_list li").removeClass("current");
    $("#pla_rec"+idx).addClass("current");
}

function select_word1(word_li){
    //$(".reports_search_1_engine").css("color","#555");
    var c_pla = word_li.find(".p_plate_no").html();//车牌
    var c_latter = word_li.find(".p_call_letter").html();//车载号码
    var c_id = word_li.find(".p_vehicle_id").html();//车辆id
    $(".reports_search_1_engine").val(c_pla+" "+c_latter);//将选择的那个词填入搜索框
    $(".search_plate_no").val(c_pla);//写入车牌
    $(".search_call_letter").val(c_latter);//写入车载
    $(".search_vehicle_id").val(c_id);//写入车辆id
    $(".reports_search_1_list").html('').hide();     //去掉推荐栏
}


/**
**搜索分公司
**/

$(".filialeDown").click(function(){
    var Cthis = $(this);
    reports_recommend4(Cthis);//查询分公司
    $(this).siblings(".reports_search_4_list").toggle();
});

$(".reports_search_4_engine").on("input propertychange", function(){
    $(this).siblings(".search_filiale_id").val('');
    var Cthis = $(this);
    reports_recommend_d(Cthis); //触发关键词推荐，显示出推荐栏
});



//鼠标点击推荐词被点击时
$(".reports_search_4_list li").live("click",function(){
    select_word4($(this));
}).live("mouseover", function(){ //鼠标滑过推荐词时
    idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
    next_word4(idx);
});

function reports_recommend4(Cthis){//列出全部公司
    Cthis.siblings(".reports_search_4_list").html('');
    //准备参数
    api_url = '../app/user'; //推荐候选词API（常规情况测试）
    params = {}//参数无
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $(".reports_search_4_list").append('<li id="fil_rec0"><span class="p_filiale_name">全部</span><span class="hide p_filiale_id">0</span></li>');
            $.each(result.user.company, function(i,item){
                $(".reports_search_4_list").append('<li id="fil_rec'+(i+1)+'"><span class="p_filiale_name">'+item.companyname+'</span><span class="hide p_filiale_id">'+item.companyno+'</span></li>');
            });
            $(".reports_search_4_list").show();
        }else{
            $(".reports_search_4_list").hide();
        }
    });
}


function reports_recommend_d(Cthis){//筛选公司
    var BranchCOM=[];
    var BranchNow=[];
    Cthis.siblings(".reports_search_4_list").html('');
    //准备参数
    api_url = '../app/user'; //推荐候选词API（常规情况测试）
    params = {}//参数无
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            idx = -1; 
            Cthis.siblings(".reports_search_4_list").html('');
            $.each(result.user.company, function(i,item){
                BranchCOM[i]=[item.companyname,item.companyno];
            });
            var checks = Cthis.val();
            var count = -1;//计数器
            for (var i=0;i<BranchCOM.length;i++){
                var BranchRe = BranchCOM[i][0].indexOf(checks);
                if (BranchRe!=-1) {
                    count++;//累计增1
                    BranchNow[i]=BranchCOM[i];
                    Cthis.siblings(".reports_search_4_list").append('<li id="fil_rec'+count+'"><span class="p_filiale_name">'+BranchNow[i][0]+'</span><span class="hide p_filiale_id">'+BranchNow[i][1]+'</span></li>');
                };
            }
            Cthis.siblings(".reports_search_4_list").show();
        }else{
            Cthis.siblings(".reports_search_4_list").hide();
        }            
    });
}


function recommend_filter(){
    var con = $("#vehicle_id").val;
    if(result && result.success==true){
        //如果只有一个且精确匹配，则自动搜索，不再出现候选项
        if(result.info.length==1 && (result.info[0].plate_no==keyword || result.info[0].call_letter==keyword)){
          $("#vehicle_id").val(result.info[0].vehicle_id);
            return false;
        }
        idx = -1; 
        $("#branch_list").html('');
        $.each(result.info, function(i,item){
            $("#branch_list").append('<li id="com'+i+'">'+item.plate_no+' '+item.call_letter+'<input type="hidden" value="'+item.vehicle_id+'"></li>');
        });
        $("#branch_list").show();
    }else{
        $("#branch_list").hide();
    }
}


//键盘上下移动光标和按回车
$(".reports_search_4_engine").keyup(function(event){
    max = $('.reports_search_4_list li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
    if(event.keyCode == 40){ //按了下箭头
        if(idx < max) idx++;
        next_word4(idx);
    }else if(event.keyCode == 38){  //按了上箭头                 
        if(idx > 0) idx--;
        next_word4(idx);
    }else if(event.keyCode == 13){ //按了回车
        if($(".reports_search_4_list li.current").text()){ //如果此时有高亮候选词，选中它并搜索
            select_word4($(".reports_search_4_list li.current"));
        }
    }
});

//鼠标点击推荐词被点击时
$(".reports_search_4_list li").live("click",function(){
    select_word4($(this));
}).live("mouseover", function(){ //鼠标滑过推荐词时
    idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
    next_word4(idx);
});

function next_word4(idx){
    $(".reports_search_4_list li").removeClass("current");
    $("#fil_rec"+idx).addClass("current");
}

function select_word4(word_li){
    var filiale_name = word_li.find(".p_filiale_name").html();//获取公司名
    var filiale_id = word_li.find(".p_filiale_id").html();//获取公司id
    $(".reports_search_4_engine").val(filiale_name);//将选择的那个词填入搜索框
    $(".search_filiale_id").val(filiale_id);
    $(".reports_search_4_list").hide();     //去掉推荐栏
}

//电话呼入查询报表内全选按钮
$(".TOS_checkall").live("click", function(){
    if ($(this).children("input").attr("checked")=="checked") {
        $(this).parent().siblings(".TOS_opt").find("input").attr("checked","checked");
        // $(".TOS_opt input").attr("checked","checked");            
    }else{
        $(this).parent().siblings(".TOS_opt").find("input").removeAttr("checked");
        // $(".TOS_opt input").removeAttr("checked");            
    };
});

$(".TOS_opt label").live("click", function(){
    if (typeof($(this).children("input").attr("checked"))=="undefined") {
        $(this).parent().siblings("span").find("input").removeAttr("checked");
        // $(".TOS_checkall").children("input").removeAttr("checked");
    }else if ($(this).children("input").attr("checked")=="checked") {
        // var LaLen = $(".TOS_opt label").length; 
        // var ChLen = $(".TOS_opt input:checked").length;
        var LaLen = $(this).parent().find("label").length;
        var ChLen = $(this).parent().find("input:checked").length;
        if (LaLen == ChLen) {
            $(this).parent().siblings("span").find("input").attr("checked","checked");
            // $(".TOS_checkall input").attr("checked","checked");
        };
    };
});

//为特定Id的table添加选择功能
function selectTr(trNode, cancelAble){
    var seled = $(trNode).hasClass("row_selected_bc");
    if(seled){
        if(cancelAble){
            $(trNode).removeClass("row_selected_bc");
        }
    }else {
        $(trNode).siblings("tr").removeClass("row_selected_bc");
        $(trNode).addClass("row_selected_bc");
    }
}

$("#stolenRecords-example").on("click",  "tbody tr", function(event){
    selectTr(this);
});

//案发车辆报警记录修改
$("#stolenRevise").click(function(){
    if($("#stolenRecords-example tbody tr.row_selected_bc").length==1){
        $("#p_isInsurance,#p_isTamperBox,#p_isSpot,#p_isWireless").removeAttr('disabled');//取消不可点
        var TR = $("#stolenRecords-example tbody tr.row_selected_bc");//选中的行

        $("#p_Caseregistration_vehicleid").val(TR.children("td").eq(17).html());//填充车辆id
        $("#p_Caseregistration_caseid").val(TR.children("td").eq(18).html());//填充案件编号

        $("#p_Casereg_name").html(TR.children("td").eq(1).html());//姓名
        $("#p_Casereg_pla").html(TR.children("td").eq(2).html());//车牌
        $("#p_Casereg_callletter").html(TR.children("td").eq(3).html());//车载电话
        $("#p_Casereg_vehtype").html(TR.children("td").eq(4).html());//车辆类型
        $("#p_Casereg_gpstype").html(TR.children("td").eq(5).html());//导航类型
        $("#p_Casereg_filiale").html(TR.children("td").eq(6).html());//分公司
        $("#p_Casereg_Jtime").html(TR.children("td").eq(7).html());//入网时间
        var caseType = replaceCaseType2(TR.children("td").eq(8).html());//案件类型
        $("#p_caseType").children("label").eq(caseType-1).find("input").attr('checked','checked');

        $("#p_caseTime").val(TR.children("td").eq(9).html());//案发时间
        var isCallPolice = replaceType2(TR.children("td").eq(10).html());//是否报警
        if(isCallPolice==1){
            $("#p_isCallPolice").attr('checked','checked');
        } else{
            $("#p_isCallPolice").removeAttr('checked');
        }
        var isCatchIt = replaceType2(TR.children("td").eq(11).html());//是否截获
        if(isCatchIt==1){
            $("#p_isCatchIt").attr('checked','checked');
        } else{
            $("#p_isCatchIt").removeAttr('checked');
        }
        var isInsurance = replaceType2(TR.children("td").eq(12).html());;//是否含盗抢险
        if(isInsurance==1){
            $("#p_isInsurance").attr('checked','checked');
        } else{
            $("#p_isInsurance").removeAttr('checked');
        }
        $("#p_closingTime").val(TR.children("td").eq(13).html());//结案时间

        var isTamperBox = replaceType2(TR.children("td").eq(14).html());//是否加装防拆盒
        if(isTamperBox==1){
            $("#p_isTamperBox").attr('checked','checked');
        } else{
            $("#p_isTamperBox").removeAttr('checked');
        }

        var isSpot = replaceType2(TR.children("td").eq(15).html());//多点防拆
        if(isSpot==1){
            $("#p_isSpot").attr('checked','checked');
        } else{
            $("#p_isSpot").removeAttr('checked');
        }

        var isWireless = replaceType2(TR.children("td").eq(16).html());//无线防拆
        if(isWireless==1){
            $("#p_isWireless").attr('checked','checked');
        } else{
            $("#p_isWireless").removeAttr('checked');
        }
        
        $("#p_isInsurance,#p_isTamperBox,#p_isSpot,#p_isWireless").attr('disabled','disabled');//4个checkbox不可点
        $("#p_Caseregistration").modal("show");//弹窗显示
    } else{
        alert("请选择要修改的条目")
    }    
})

//修改提交
$("#p_CaseSubmit").click(function(){
    var vid = $("#p_Caseregistration_vehicleid").val();//获取车辆id
    var cid = $("#p_Caseregistration_caseid").val();//获取案件编号
    var cType = pgetCaseType();//案件类型
    var isCP = "";//是否报警 1:是 0:否
    if($("#p_isCallPolice").attr("checked")=="checked"){
        isCP = 1;//已报警
    }else{
        isCP = 0;//未报警
    }
    var bTime = $("#p_caseTime").val();//案发时间
    var eTime = $("#p_closingTime").val();//结案时间
    var isCatch = "";//是否截获 1:是 0:否
    if($("#p_isCatchIt").attr("checked")=="checked"){
        isCatch = 1;//已截获
    }else{
        isCatch = 0;//未截获
    }
    $('#p_Caseregistration').modal("hide");//隐藏弹窗
    var api_url = '../app/alarm/vehicle/modify';//url
    var params = {unitId:vid,id:cid,caseType:cType,isCallPolice:isCP,beginTime:bTime,endTime:eTime,isCapture:isCatch,finishCase:0};//参数(车辆id、案件编号、案件类型、是否报警、案发时间、结案时间、是否截获、是否结案：0)
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            alert("修改案发车辆报警记录提交成功！");
            removeCase(cid)//把列表里的结案改为已结案，并不可点击
        }else if(result.success==false){
            alert("修改案发车辆报警记录提交失败信息："+result.message);
        }
    });
});

//案发车辆报警记录删除
$("#stolendelete").click(function(){
    if($("#stolenRecords-example tbody tr.row_selected_bc").length==1){
        var TR = $("#stolenRecords-example tbody tr.row_selected_bc");//选中的行

        $("#d_Caseregistration_vehicleid").val(TR.children("td").eq(17).html());//填充车辆id
        $("#d_Caseregistration_caseid").val(TR.children("td").eq(18).html());//填充案件编号
        $("#d_Casereg_name").html(TR.children("td").eq(1).html());//姓名
        $("#d_Casereg_pla").html(TR.children("td").eq(2).html());//车牌
        $("#d_Casereg_callletter").html(TR.children("td").eq(3).html());//车载电话
        $("#d_Casereg_type").html(TR.children("td").eq(8).html());//案件类型
        $("#d_caseTime").html(TR.children("td").eq(9).html());//案发时间
        $("#d_closingTime").html(TR.children("td").eq(13).html());//结案时间
        $("#d_Caseregistration").modal("show");//弹窗显示
    } else{
        alert("请选择要删除的条目")
    }      
})

//删除案发车辆提交
$("#d_CaseSubmit").click(function(){
    var vid = $("#d_Caseregistration_vehicleid").val();//获取车辆id
    var cid = $("#d_Caseregistration_caseid").val();//获取案件编号
    
    $('#d_Caseregistration').modal("hide");//隐藏弹窗
    var api_url = '../app/alarm/vehicle/del';//url
    var params = {unitId:vid,id:cid};//参数(车辆id、案件编号)
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            alert("删除案发车辆报警记录提交成功！");
        }else if(result.success==false){
            alert("删除案发车辆报警记录提交失败信息："+result.message);
        }
    });
});
    
//获取结案单案件类型的编号
function pgetCaseType(){
    var obj = document.getElementsByName("pCasereg_option1");
    var temp ="";
    for(var i=0; i<obj.length; i ++){
        if(obj[i].checked){
            //alert(obj[i].value);
            temp = obj[i].value;
        }
    }
    return temp;
}

//（是或否）类型转换（是：1、否：2）
function replaceType2(str){
    var temp ="";
    switch(str){
        case "否": temp = 0; break;
        case "是": temp = 1; break;
        default: temp = "";
    }
    return temp;
}

//案件类型转换（被盗：1、被劫：2、被撬：3、玻璃被砸：4）
function replaceCaseType2(str){
    var temp ="";
    switch(str){
        case "被盗": temp = 1; break;
        case "被劫": temp = 2; break;
        case "被撬": temp = 3; break;
        case "玻璃被砸": temp = 4; break;
        default: temp = "";
    }
    return temp;
}

//时间控件
// 时间datepicker 
$( ".form_datetime" ).each(function(){
    $(this).datepicker({
        showButtonPanel: true,
        format:'yyyy-MM-dd HH:mm:ss'//"yyyy-mm-dd"//
    });
});




/**
**搜索警情状态 start
**/


//任何地方被点击
$(document).click(function(){
    $(".reports_search_alarm_list").hide(); //隐藏推荐栏
});


$(".AlarmStatus").click(function(){
    var Cthis = $(this);
    reports_recommend_A(Cthis);//查询分公司
    $(this).siblings(".reports_search_alarm_list").toggle();
});

$(".reports_search_alarm_engine").on("input propertychange", function(){
    $(this).siblings(".search_filiale_id").val('');
    var Cthis = $(this);
    reports_recommend_alarm(Cthis); //触发关键词推荐，显示出推荐栏
});

$('.reports_search_alarm_engine').blur(function(){
    if($(".AlarmStatusId").val()=='' && $.trim($(".reports_search_alarm_engine").val())){
        alert("请选择车辆状态");
    }    
});

//鼠标点击推荐词被点击时
$(".reports_search_alarm_list li").live("click",function(){
    select_word_alarm($(this));
}).live("mouseover", function(){ //鼠标滑过推荐词时
    idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
    next_word_alarm(idx);
});

function reports_recommend_A(Cthis){//列出全部公司
    Cthis.siblings(".reports_search_alarm_list").html('');
    //准备参数
    api_url = '../app/alarmstatus'; //推荐候选词API（常规情况测试）
    params = {}//参数无
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $.each(result.alarmStatus, function(i,item){
                $(".reports_search_alarm_list").append('<li id="fil_rec'+(i+1)+'"><span class="p_filiale_name">'+item.name+'</span><span class="hide p_filiale_id">'+item.id+'</span></li>');
            });
            $(".reports_search_alarm_list").show();
        }else{
            $(".reports_search_alarm_list").hide();
        }
    });
}


function reports_recommend_alarm(Cthis){//筛选公司
    var BranchCOM=[];
    var BranchNow=[];
    Cthis.siblings(".reports_search_alarm_list").html('');
    //准备参数
    api_url = '../app/alarmstatus'; //推荐候选词API（常规情况测试）
    params = {}//参数无
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            idx = -1; 
            Cthis.siblings(".reports_search_alarm_list").html('');
            $.each(result.alarmStatus, function(i,item){
                BranchCOM[i]=[item.name,item.id];
            });
            var checks = Cthis.val();
            var count = -1;//计数器
            for (var i=0;i<BranchCOM.length;i++){
                var BranchRe = BranchCOM[i][0].indexOf(checks);
                if (BranchRe!=-1) {
                    count++;//累计增1
                    BranchNow.push(BranchCOM[i]);
                    Cthis.siblings(".reports_search_alarm_list").append('<li id="fil_rec'+count+'"><span class="p_filiale_name">'+BranchCOM[i][0]+'</span><span class="hide p_filiale_id">'+BranchCOM[i][1]+'</span></li>');
                }
            }
            if (BranchNow.length==1 && BranchNow[0][0]==$(".reports_search_alarm_engine").val()){
                Cthis.siblings(".reports_search_alarm_list").hide();
                $(".AlarmStatusId").val(BranchNow[0][1]);
            }else{
                Cthis.siblings(".reports_search_alarm_list").show();
            }
        }else{
            Cthis.siblings(".reports_search_alarm_list").hide();
        }            
    });
}




//键盘上下移动光标和按回车
$(".reports_search_alarm_engine").keyup(function(event){
    max = $('.reports_search_alarm_list li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
    if(event.keyCode == 40){ //按了下箭头
        if(idx < max) idx++;
        next_word_alarm(idx);
    }else if(event.keyCode == 38){  //按了上箭头                 
        if(idx > 0) idx--;
        next_word_alarm(idx);
    }else if(event.keyCode == 13){ //按了回车
        if($(".reports_search_alarm_list li.current").text()){ //如果此时有高亮候选词，选中它并搜索
            select_word_alarm($(".reports_search_alarm_list li.current"));
        }
    }
});

//鼠标点击推荐词被点击时
$(".reports_search_alarm_list li").live("click",function(){
    select_word_alarm($(this));
}).live("mouseover", function(){ //鼠标滑过推荐词时
    idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
    next_word_alarm(idx);
});

function next_word_alarm(idx){
    $(".reports_search_alarm_list li").removeClass("current");
    $("#fil_rec"+idx).addClass("current");
}

function select_word_alarm(word_li){
    var filiale_name = word_li.find(".p_filiale_name").html();//获取公司名
    var filiale_id = word_li.find(".p_filiale_id").html();//获取公司id
    $(".reports_search_alarm_engine").val(filiale_name);//将选择的那个词填入搜索框
    $(".search_filiale_id").val(filiale_id);
    $(".reports_search_alarm_list").hide();     //去掉推荐栏
}

//检测案发时间是否为空
function checkTimeBox(){
    var st = $(".starTime").val();
    var et = $(".endTime").val();
    if(st.length==0||et.length==0){
        alert("时间段不能为空！")
        return 0;
    }
}


/**
**搜索警情状态 end
**/

function calloutSituation(type){
    var temp ="";
    switch(type){
        case 0: temp = "接通"; break;
        case 1: temp = "忙线"; break;
        case 2: temp = "无人接听"; break;
        case 3: temp = "关机"; break;
        case 4: temp = "空号"; break;
        case 5: temp = "断线"; break;
        case 6: temp = "拒接"; break;
        case 7: temp = "错号"; break;
        case 8: temp = "故障"; break;
        case 9: temp = "其他"; break;
        default:temp = ""; break;
    };
    return temp;
}

