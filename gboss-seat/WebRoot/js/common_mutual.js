/*
 * author:zhenglei 2014-09-09
 * 
 */

var Tel_Links;
var Tel_Phone;
var StolenLists;
var idx=-1; 
var MonitorPhoneList = '';

var isRing = 1;//静音状态：1:响铃 2:静音
var defaltNum = "952100";//默认热线
var defaultParam;//系统默认参数

$(document).ready(function(){
    $.getJSON("js/defaultParams.json", function(result){//获取默认参数
        defaultParam = result;//数据
        getCallOutNum();//热线号码初始化
    });
    onbodyload();
    LoginInfo();    //登录到座席
    getOutstandingVehicle();    //刷新案发车辆列表
    // getCallReportList();//来电简报和去电简报的选项 移至登录座席接口
    
    setInterval(function(){
        // alert("60秒清空一次");
        log.value = "";
    },60000);//60秒清空一次
    // Connect();   //连接通信中心(移至LoginInfo()Ajax返回函数内，否则座席名为空连接失败)
    //左下角指令集 绑定事件 BEGIN
    // TODO:其他指令事件。。。
    // 左下角指令集 绑定事件 END

    $("#setSilentM").click(function(){//静音
        $("#setSilentM").hide();
        $("#setRingM").show();
        isRing = 2;
        // alert(isRing);
    });
    $("#setRingM").click(function(){//响铃
        $("#setRingM").hide();
        $("#setSilentM").show();
        isRing = 1;
        // alert(isRing);
    });

    $("#CallOut_btn,#CallOut_btn_2").click(function(){//呼出号码
        $("#callOutNum").modal("show");
        setCallOutNum();//选中默认号码
    });

    $("#callOutNum_submit").click(function(){//呼出号码提交
        var num = $("input[name='callOutNums']:checked").val();
        // alert($("input[name='callOutNums']:checked").val());
        defaltNum = num;//默认热线
        phonebarUtil.centerTel = num;//设置电话控件
        $("#callOutNum").modal("hide");
    });
   
    // 本地保存
    $("#gps_table tbody").html($(window.localStorage.getItem("GPS_list")));
    $(".car_list").html($(window.localStorage.getItem("monitor_list")));
    $("#send_list").html($(window.localStorage.getItem("send_list")));
    $("#example20 tbody").html($(window.localStorage.getItem("CallLog_list")));
   
    // 高度自适应
    var $Win_height = $(window).height();
    var $Win_width = $(window).width();
    var $side_height = $Win_height - 447;
    var $sim_height = $Win_height - 419;
    var $Tool_height = $Win_height - 555;
    var $Fre_height = $Win_height - 552;
    var $Sear_height = $Win_height - 230;
    var $Le_height = $Win_height - 81;
    var $Map_width = ($Win_width - 395)/2-12;
    var $Down_height = $Win_height - 220;

    $(".orders>.tab-content_ver02>.tab-pane").css("height",$sim_height+"px");
    $("#home1 .tab-content").css("height",$side_height+"px");
    $("#messages1 .tab-content").css("height",$side_height+"px");
    $(".monitor_part .tab-content").css("height",$Tool_height+"px");
    $("#frequency").css("max-height",$Fre_height+"px");
    $("#position_search_result").css("max-height",$Sear_height+"px");
    $("#leftPanel").css("height",$Le_height+"px");
    $("#Dre01,#Dre03").css("left",$Map_width+"px");
    $(".dropdown-menu04").css("max-height",$Down_height+"px");
 
    //电话簿
    var tel_table;
    var paras;
    $("#telephone_directory").click(function(){  
        if ($("#customer_id").val()){
            $("#phonebar_change_out_tel").val('');
            $("#Phone_links").html('');
            telephone_directory();
            tel_table.fnFilter('');
            tel_table.fnReloadAjax("app/data/telbook?now=" + new Date().getTime() + "&" + paras);            
        };
    });  

    $(document).on("dblclick", "#phonebook-tab tbody tr", function(event){
        var Tel_num = $(this).children("td:last").html().replace(/ /g,'');
        var Tel_this = $(this).parent().parent().get(0).id;
        PhoneOwnership(Tel_num,Tel_this);
    });          


    // $("#b_phone").on("input propertychange", function(){
    //     $("#b_marks").val('');
    // });

    //电话置单双向
    $("#OneWay").click(function(){   
        $(this).hide();
        $("#TwoWay").show();
    });   
    $("#TwoWay").click(function(){   
        $(this).hide();
        $("#OneWay").show();
    });
    
    // 拨电话取消按钮
    // $("#phonebar_call_out_cancel_btn").hide();
    // $("#phonebar_call_out_cancel_btn").on("click",function(){
    //     phonebar_call_out_cancel_clicked();
    //     $(this).hide();
    // });

    // 电话右键菜单
    $(document).click(function(){
        $("#phonebar_all_seat_menu,#phonebar_monitor_list_menu,#phonebar_wait_list_menu").hide(); //隐藏推荐栏
    });

    // 电话队列信息

    $("#service_note").click(function(){   
        $("#service_password").val('');    
        phonebar_send_pwd_ivr_clicked(); 
    });

    // 服务密码手动验证

    $("#service_password").focus(function(){   
        $("#service_password").val('');
        $("#service_password").css("color","#555");
    });
 
    $("#service_password").keyup(function(event){
        if($("#service_password").val() && event.keyCode==13){
            var pwd= $("#service_password").val();
            var pwd_ser= $("#service_pwd").val();
            if (pwd!=pwd_ser){
                $("#service_password").css("color","#c32f31");
            }else{
                $("#service_password").css("color","#019b3f");
            };
        };
    });
    
    $("#service_password").on("input propertychange", function(){
        if(!$(this).val()){
            $("#service_password").css("color","#555");
        };
    });



    // 时间datepicker 
    $( ".form_datetime" ).each(function(){
        $(this).datepicker({
            showButtonPanel: true,
            format:'yyyy-MM-dd HH:mm:ss'//"yyyy-mm-dd"//
        });
    });
    

    //电话簿
    function telephone_directory(){
        paras = 'customerId='+$("#customer_id").val();//参数;
        //准备参数
        tel_table = $("#phonebook-tab").dataTable({
            "bSort":false,//不排序
            "filter":true,//搜索框
            "pagingType": "full_numbers",
            "processing" : true,
            "serverSide" : true,//后端处理
            "bRetrieve": true,
            "bDestroy": true,
            "language": {
                "lengthMenu": "每页显示  _MENU_ 条记录",
                "zeroRecords": "抱歉， 没有找到",
                "info": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                "infoEmpty": "没有数据",
                "infoFiltered": "(从 _MAX_ 条数据中检索)",
                "search" : "查询：",
                "paginate": {
                    "first": "首页",
                    "previous": "前一页",
                    "next": "后一页",
                    "last": "尾页"
                }, 
                "zeroRecords": "没有检索到数据",
                "processing": "加载中……"
            },
            "processing" : true,
            "serverSide" : true,//后端处理
            "ajax" : "app/data/telbook?now=" + new Date().getTime() + "&" + paras, //获取数据的API
            "columns":[
                {"data":"contactName"},
                {"data":"contactInfo"}
            ]        
        });
        return tel_table;
    };


    //防拆码气泡对话框

    $("#fang_mark").mouseenter(function(){
      $(".org_box").show();
    });

    $(".bold_code").mouseleave(function(){
      $(".org_box").hide();
    });


    // 4s车厂信息

    $("#m_fourshop").click(function(){   
        $("#S_repair").html('');
        $("#S_order").html('');
        FourFactory(); 
    });

    $("#m_factory").click(function(){  
        $("#S_repair").html('');
        $("#S_order").html(''); 
        FourFactory(); 
    });

    /*
     * 指令部分 start
     * 
     */

    //指令发送
    $("#center_last").click(function(){
        if($("#vehicle_id").val()){
            GetLastPosition();  //居中当前车辆的最后位置 
        };
    });   
    $("#cmd_n1").live("click", function(){Position();});    //查车
    $("#cmd_n4").live("click", function(){LockDoor();});    //锁车门
    $("#cmd_n7").live("click", function(){OpenOil();});    //恢复油电
    
    $("#cmd1").live("dblclick", function(){Position();});    //查车
    $("#cmd4").live("dblclick", function(){LockDoor();});    //锁车门
    $("#cmd7").live("dblclick", function(){OpenOil();});    //恢复油电
    $("#cmd15").live("dblclick", function(){FindCar();});    //寻车
    $("#cmd90").live("dblclick", function(){wakeupGPS();});    //唤醒GPS
    
    //指令弹窗
    $("#cmd2").live("dblclick", function(){$("#cmd_p2").modal('show');});    //车台复位
    $("#cmd5").live("dblclick", function(){$("#cmd_p5").modal('show');});    //开车门
    $("#cmd6").live("dblclick", function(){$("#cmd_p6").modal('show');});    //断电（油）
    $("#cmd10").live("dblclick", function(){$("#cmd_p10").modal('show');});    //允许手柄设置
    $("#cmd18").live("dblclick", function(){$("#cmd_p18").modal('show');});    //黑匣子记录
    $("#cmd21").live("dblclick", function(){$("#cmd_p21").modal('show');});    //跟踪
    $("#cmd23").live("dblclick", function(){$("#cmd_p23").modal('show');});    //监听
    $("#cmd24").live("dblclick", function(){$("#cmd_p24").modal('show');});    //通话
    $("#cmd28").live("dblclick", function(){$("#cmd_p28").modal('show');});    //设置黑匣子记录时间间隔
    $("#cmd29").live("dblclick", function(){$("#cmd_p29").modal('show');});    //限制车辆速度
    $("#cmd31").live("dblclick", function(){$("#cmd_p31").modal('show');});    //限制行驶范围
    $("#cmd33").live("dblclick", function(){$("#cmd_p33").modal('show');});    //禁止行使时间
    $("#cmd37").live("dblclick", function(){$("#cmd_p37").modal('show');});    //更改服务电话号码
    $("#cmd38").live("dblclick", function(){$("#cmd_p38").modal('show');});    //更改短信服务中心特服号码
    $("#cmd39").live("dblclick", function(){$("#cmd_p39").modal('show');});    //更改短信服务中心呼出特服号码
    $("#cmd53").live("dblclick", function(){$("#cmd_p53").modal('show');});    //发短信
    $("#cmd56").live("dblclick", function(){$("#cmd_p56").modal('show');});    //要求定时报告
    $("#cmd66").live("dblclick", function(){$("#cmd_p66").modal('show');});    //点火报告
    $("#cmd87").live("dblclick", function(){$("#cmd_p87").modal('show');});    //设置通信参数
    // $("#cmd88").live("dblclick", function(){$("#cmd_p88").modal('show');});    //通信参数
    $("#cmd244").live("dblclick", function(){$("#cmd_p244").modal('show');});    //设置关ACC进入省电模式的时间
    $("#cmd1101").live("dblclick", function(){$("#cmd_p1101").modal('show');});    //开后尾箱
    $("#cmd1102").live("dblclick", function(){$("#cmd_p1102").modal('show');});    //升车窗
    $("#cmd1104").live("dblclick", function(){$("#cmd_p1104").modal('show');});    //开空调
    $("#cmd1105").live("dblclick", function(){$("#cmd_p1105").modal('show');});    //关空调
    $("#cmd1106").live("dblclick", function(){$("#cmd_p1106").modal('show');});    //开灯
    $("#cmd1107").live("dblclick", function(){$("#cmd_p1107").modal('show');});    //关灯

    //带参数指令发送
    $("#cmd_localset").click(function(){     //设置通信参数
        // var S_id = $("#vehicle_id").val();
        var S_APN = $("#cmd_apn").val();
        var S_IP = $("#cmd_ip").val();
        var S_NUM01 = $("#cmd_num01").val();
        
        if ($.trim($("#cmd_num02").val())=='') {
            S_NUM02 = "0";
        }else{
            var S_NUM02 = $("#cmd_num02").val();
        };
        var S_type = $(".local_type input[checked='checked']").val();
        var S_mode = $(".local_mode input[checked='checked']").val();
        var S_space = $("#cmd_space").val();

        GPRS_parameter(S_APN, S_IP, S_NUM01,S_NUM02,S_type,S_mode,S_space);
        $("#cmd_p87").modal('hide');
    });    

    $("#cmd88").live("dblclick", function(){     //查询通信参数
        $("#search_apn").val('');   //清空查询参数
        $("#search_ip").val('');
        $("#search_num01").val('');
        $("#search_num02").val('');  
        $("#checkT,#checkU").removeAttr("checked");   
        $("#CheckN,#CheckB").removeAttr("checked");  
        $("#search_space").val('');  
        GPRS_inquiry();

        // $("#search_apn").val(S_APN);
        // $("#search_ip").val(S_IP);
        // $("#search_num01").val(S_NUM01);
        // $("#search_num02").val(S_NUM02);
        // // $("#search_type").val(S_type);  //本地类型
        // var checkLen = $(".search_type type=['radio']");
        // for (var i = checkLen.length - 1; i >= 0; i--) {
        //     if(S_type == checkLen[i].val())
        //         checkLen[i].attr("checked","checked");
        // };
        // // var S_mode = $("#local_mode").val();
        // $("#S_space").val(S_space);

    });    

    $("#OnekeyNav").click(function(){  //一键导航
        if($("#vehicle_id").val()){
            $("#TNnavigation").modal('show');
        };
    });   

    $("#sendNAV").click(function(){    //设置导航目的地
        var Des_search = $("#search_pattern input:checked").val();
        var Des_lon = $(".destination td:eq(2)").html(); 
        var Des_lat = $(".destination td:eq(3)").html(); 
        var pass01;
        var pass02;

        if(Des_lon=="" || Des_lat==""){//无目的地提示
            alert("请选择导航目的地");
            return;
        }

        if ($(".pass01 td:eq(2)").html()||$(".pass01 td:eq(3)").html()){
            pass01 = $(".pass01 td:eq(2)").html()+","+$(".pass01 td:eq(3)").html(); 
        }else{
            // pass01 = null;
            pass01 = ""; 
        };

        if ($(".pass02 td:eq(2)").html()||$(".pass02 td:eq(3)").html()){
            pass02 = $(".pass02 td:eq(2)").html()+","+$(".pass02 td:eq(3)").html(); 
        }else{
            // pass02 = null;
            pass02 = "";
        };   
        // alert(pass01);
        // alert(pass02);     
        var Des_pass = "";//提交的必经点字符串
        var Des_array = [];
        // if (!pass01) {//必经点1为空
        //     Des_pass = pass02;
        // }else if(!pass02){//必经点2为空
        //     Des_pass = pass01;
        // }else if(pass01&&pass02){
        //     Des_pass = pass01+";"+pass02;
        // };
        if(pass01){//1不为空
            Des_array.push(pass01);
        }
        if(pass02){//2不为空
            Des_array.push(pass02);
        }

        if(Des_array.length>0){
            $.each(Des_array,function(i,item){
                if(i==0){//第一项
                    Des_pass += item;
                } else{
                    Des_pass += ";" + item;
                }
            });
        } else{
            Des_pass = "";//空串
        }


        var Des_avoid; 
        if ($(".avoid td:eq(2)").html()||$(".avoid td:eq(3)").html()){
            Des_avoid = $(".avoid td:eq(2)").html()+","+$(".avoid td:eq(3)").html(); 
        }else{
            // Des_avoid = null;
            Des_avoid = "";
        };         
        // alert(Des_search+'+'+Des_lon+'+'+Des_lat+'+'+Des_pass+'+'+Des_avoid);
        GPRS_destination(Des_search,Des_lon,Des_lat,Des_pass,Des_avoid);
        $("#TNnavigation").modal('hide');
    });   

    $("#play_history_dlg_query_btn").click(function(){  //显示历史数据条数
        if($("#vehicle_id").val()){
            $("#play_history_dlg_search_result").show();
        };
    });   


    $("#cmd_listens").live("click", function(){ //监听
        if($("#listen_num").val()){
            Listen($("#listen_num").val());
            $("#cmd_p23").modal('hide');
            $("#cmd_listens").val();
        }else{
            alert("不能为空");
        };
    });   

    $("#cmd_listensM").live("click", function(){ //监听快捷方式
        Listen($("#listen_numM").val());
        $("#cmd_monitor").modal('hide');
    });   

    $("#cmd_Trace").click(function(){ //跟踪
        var timeSS =  $("#cmd_timeSS").val();  //获取用户所填内容s
        var timeFre =  $("#cmd_timeFre").val();  //获取用户所填内容f
        if (timeSS!='' && timeFre!='') {
            Trace(timeSS,timeFre);
            $("#cmd_p21").modal('hide');
        };  
    });   

    $("#cmd_StopTrace").click(function(){ //停止跟踪
        StopTrace();
        $("#cmd_p21").modal('hide');
    }); 
    //快捷方式操作
    $(".dropdown-menu03 li").not("#cmd_IntervalDeliver_stop02").click(function(){ //跟踪
        var timeSS =  $(this).find("b").html();  //获取用户所填内容s
        var timeFre = $(this).find("span").html();  //获取用户所填内容f
        Trace(timeSS,timeFre);
        $("#cmd_p21").modal('hide');
    });   

    $("#cmd_IntervalDeliver_stop02").click(function(){ //停止跟踪
        StopTrace();
    }); 

    $('#cmd_opendoor_pwd,#cmd_opendoor_jobNum').on('keypress',function(event){   //开车门
        if(event.keyCode ==13&&$('#cmd_opendoor_jobNum').val()&&$('#cmd_opendoor_pwd').val())    
        {
            check_jobNum_pwd($("#cmd_opendoor_jobNum").val(),$("#cmd_opendoor_pwd").val(),'OpenDoor',"#cmd_p5");
            $("#cmd_p5").modal('hide');
        }
    });

    $("#cmd_opendoor").click(function(){ //开车门
        check_jobNum_pwd($("#cmd_opendoor_jobNum").val(),$("#cmd_opendoor_pwd").val(),'OpenDoor',"#cmd_p5");
        $("#cmd_p5").modal('hide');
    });

    $('#cmd_outage_jobNum,#cmd_outage_pwd').on('keypress',function(event){   //断电（油）
        if(event.keyCode ==13&&$('#cmd_outage_jobNum').val()&&$('#cmd_outage_pwd').val())    
        {
        check_jobNum_pwd($("#cmd_outage_jobNum").val(),$("#cmd_outage_pwd").val(),'LockOil',"#cmd_p6");
        $("#cmd_p6").modal('hide');
        }
    });


    $("#cmd_outage").click(function(){ //断电（油）
        check_jobNum_pwd($("#cmd_outage_jobNum").val(),$("#cmd_outage_pwd").val(),'LockOil',"#cmd_p6");
        $("#cmd_p6").modal('hide');
    });



    $("#cmd_communicate").click(function(){ //通话
        if($("#Gcall").val()){
            telCall($("#Gcall").val());
            $("#cmd_p24").modal('hide');
            $("#Gcall").val('');
        }else{
            alert("号码不能为空！");
        };
    });

    $("#btn_SendSMS").click(function(){ //发短信
        if($("#sms_content").val()){
            UnitSMS($("#sms_content").val(),'0');
            $("#cmd_p53").modal('hide');
            $("#sms_content").val('');
        }else{
            alert("短信内容不能为空！");
        };
    });

    $('#cmd_opentrunk_jobNum,#cmd_opentrunk_pwd').on('keypress',function(event){   //开后尾箱
        if(event.keyCode ==13&&$('#cmd_opentrunk_jobNum').val()&&$('#cmd_opentrunk_pwd').val())    
        {
        check_jobNum_pwd($("#cmd_opentrunk_jobNum").val(),$("#cmd_opentrunk_pwd").val(),'OpenTrunk',"#cmd_p1101");
        $("#cmd_p1101").modal('hide');
        }
    });

    $("#btn_opentrunk").click(function(){ //开后尾箱
        check_jobNum_pwd($("#cmd_opentrunk_jobNum").val(),$("#cmd_opentrunk_pwd").val(),'OpenTrunk',"#cmd_p1101");
        $("#cmd_p1101").modal('hide');
    });

    $('#cmd_liftglass_jobNum,#cmd_liftglass_pwd').on('keypress',function(event){   //升车窗
        if(event.keyCode ==13&&$('#cmd_liftglass_jobNum').val()&&$('#cmd_liftglass_pwd').val())    
        {
        check_jobNum_pwd($("#cmd_liftglass_jobNum").val(),$("#cmd_liftglass_pwd").val(),'CloseWindow',"#cmd_p1102");
        $("#cmd_p1102").modal('hide');
        }
    });

    $("#btn_liftglass").click(function(){ //升车窗
        check_jobNum_pwd($("#cmd_liftglass_jobNum").val(),$("#cmd_liftglass_pwd").val(),'CloseWindow',"#cmd_p1102");
        $("#cmd_p1102").modal('hide');
    });

    $('#cmd_open-aircondition_jobNum,#cmd_open-aircondition_pwd').on('keypress',function(event){   //开空调
        if(event.keyCode ==13&&$('#cmd_open-aircondition_jobNum').val()&&$('#cmd_open-aircondition_pwd').val())    
        {
        check_jobNum_pwd($("#cmd_open-aircondition_jobNum").val(),$("#cmd_open-aircondition_pwd").val(),'OpenAircondition',"#cmd_p1104");
        $("#cmd_p1104").modal('hide');
        }
    });

    $("#btn_open-aircondition").click(function(){ //开空调
        check_jobNum_pwd($("#cmd_open-aircondition_jobNum").val(),$("#cmd_open-aircondition_pwd").val(),'OpenAircondition',"#cmd_p1104");
        $("#cmd_p1104").modal('hide');
    });

    $('#cmd_close-aircondition_jobNum,#cmd_close-aircondition_pwd').on('keypress',function(event){   //关空调
        if(event.keyCode ==13&&$('#cmd_close-aircondition_jobNum').val()&&$('#cmd_close-aircondition_pwd').val())    
        {
        check_jobNum_pwd($("#cmd_close-aircondition_jobNum").val(),$("#cmd_close-aircondition_pwd").val(),'CloseAircondition',"#cmd_p1105");
        $("#cmd_p1105").modal('hide');
        }
    });


    $("#btn_close-aircondition").click(function(){ //关空调
        check_jobNum_pwd($("#cmd_close-aircondition_jobNum").val(),$("#cmd_close-aircondition_pwd").val(),'CloseAircondition',"#cmd_p1105");
        $("#cmd_p1105").modal('hide');
    });

    $('#cmd_lighten_jobNum,#cmd_lighten_pwd').on('keypress',function(event){   //开灯
        if(event.keyCode ==13&&$('#cmd_lighten_jobNum').val()&&$('#cmd_lighten_pwd').val())    
        {
        check_jobNum_pwd($("#cmd_lighten_jobNum").val(),$("#cmd_lighten_pwd").val(),'lighten',"#cmd_p1106");
        $("#cmd_p1107").modal('hide');
        }
    });

    $("#btn_lighten").click(function(){ //开灯
        check_jobNum_pwd($("#cmd_lighten_jobNum").val(),$("#cmd_lighten_pwd").val(),'lighten',"#cmd_p1106");
        $("#cmd_p1107").modal('hide');
    });

    $('#cmd_putout_jobNum,#cmd_putout_pwd').on('keypress',function(event){   //关灯
        if(event.keyCode ==13&&$('#cmd_putout_jobNum').val()&&$('#cmd_putout_pwd').val())    
        {
        check_jobNum_pwd($("#cmd_putout_jobNum").val(),$("#cmd_putout_pwd").val(),'putout',"#cmd_p1107");
        $("#cmd_p1106").modal('hide');
        }
    });


    $("#btn_putout").click(function(){ //关灯
        check_jobNum_pwd($("#cmd_putout_jobNum").val(),$("#cmd_putout_pwd").val(),'putout',"#cmd_p1107");
        $("#cmd_p1106").modal('hide');
    });

    $("#btn_startblackbox").click(function(){ //回传黑匣子记录

        var timeS =  $("#cmd_starttime").val();  
        var timeE =  $("#cmd_endtime").val();
        if (timeS!='' && timeE!='') {
            startBlackbox(timeS, timeE);
            $("#cmd_p18").modal('hide');  

        };  
    });   

    $("#btn_stopblackbox").click(function(){ //停止回传黑匣子记录
        stopBlackbox();
        $("#cmd_p18").modal('hide');
    });

    $("#btn_allow-set").click(function(){ //允许手柄设置
        AllowSet();
        $("#cmd_p10").modal('hide');
    });


    $("#cmd_IntervalDeliver_start").click(function(){       //开启定时报告
        if($("#b_carPhone").val()){
            var h =  $("#cmd_IntervalDeliver_h").val(); 
            if(h) h = parseInt(h);
            var m =  $("#cmd_IntervalDeliver_m").val(); 
            if(m) m = parseInt(m);
            var s =  $("#cmd_IntervalDeliver_s").val(); 
            if(s) s = parseInt(s);
            var t = 3600*h+60*m+s;
            if (t) {
                IntervalDeliver(1,t);
                $("#cmd_p56").modal('hide');              
            }else{alert("秒数不能为空！");};
    
        }
    });     

    $("#cmd_IntervalDeliver_stop").click(function(){       //停止定时报告
        if($("#b_carPhone").val()){
            IntervalDeliver(0,0);
            $("#cmd_p56").modal('hide');
        }
    });  

    $("#ACCstart").click(function(){       //启动ACC进入省电模式的时间
        if($("#b_carPhone").val()){
            var h =  $("#ACCset_hour").val(); 
            if(h) h = parseInt(h);
            var m =  $("#ACCset_min").val(); 
            if(m) m = parseInt(m);
            var s =  $("#ACCset_sec").val(); 
            if(s) s = parseInt(s);
            var t = 3600*h+60*m+s;
            if (t) {
                //IntervalDeliver(1,t);
                $("#cmd_p244").modal('hide');              
            }else{alert("秒数不能为空！");};
    
        }
    });     

    $("#ACCstop").click(function(){   //停止ACC进入省电模式的时间
        if($("#b_carPhone").val()){
            //IntervalDeliver(0,0);
            $("#cmd_p244").modal('hide');
        }
    });  


    $("#btn-blackbox-set").click(function(){       //设置黑匣子记录时间间隔
        if($("#b_carPhone").val()){
            var h =  $("#cmd_blackbox-set_h").val(); 
            if(h) h = parseInt(h);
            var m =  $("#cmd_blackbox-set_m").val(); 
            if(m) m = parseInt(m);
            var s =  $("#cmd_blackbox-set_s").val(); 
            if(s) s = parseInt(s);
            var t = 3600*h+60*m+s;
            if (t) {
                BlackboxSet(t);
                $("#cmd_p28").modal('hide');                
            }else{
                alert('时间不能为空!');
            };

        }
    });     
    
    $("#btn-ratelimit-set-on").click(function(){       //限制车辆行驶速度
        if($("#b_carPhone").val()){
            var speed =  parseInt($("#cmd_ratelimit-set-speed").val()); 
            if (speed) {
                RatelimitSet(1,speed);
                $("#cmd_p29").modal('hide');   
                $("#cmd_ratelimit-set-speed").val('50');          
            }else{alert('速度不能为空！');};

        }
    }); 
    $("#btn-ratelimit-set-off").click(function(){       //取消限制车辆行驶速度
        if($("#b_carPhone").val()){
            RatelimitSet(0);
            $("#cmd_p29").modal('hide');
        }
    });

    $("#btn-traveltime-ban-on").click(function(){       //禁止行驶时间

        if($("#b_carPhone").val()){
            var timeS = $("#cmd_traveltime-ban-startH").val()+$("#cmd_traveltime-ban-startM").val(); 
            var timeE = $("#cmd_traveltime-ban-stopH").val()+$("#cmd_traveltime-ban-stopM").val(); 
            $("#cmd_traveltime-ban-start").val(timeS); 
            $("#cmd_traveltime-ban-stop").val(timeE); 
            if (timeS!='' && timeE!='') {
                TraveltimeBanOn(timeS,timeE);
                $("#cmd_p33").modal('hide');
            };

        }
    }); 
    $("#btn-traveltime-ban-off").click(function(){       //解除禁止行驶时间
        if($("#b_carPhone").val()){
            var timeS = $("#cmd_traveltime-ban-startH").val()+$("#cmd_traveltime-ban-startM").val(); 
            var timeE = $("#cmd_traveltime-ban-stopH").val()+$("#cmd_traveltime-ban-stopM").val(); 
            $("#cmd_traveltime-ban-start").val(timeS); 
            $("#cmd_traveltime-ban-stop").val(timeE); 
            if (timeS!='' && timeE!='') {
                TraveltimeBanOff(timeS,timeE);
                $("#cmd_p33").modal('hide');
            };
        }
    });

    $("#btn_change1").click(function(){ //更改服务电话号码
        var number =  $("#cmd_change1_number").val();  
        if (number) {
            Change1(number);
            $("#cmd_p37").modal('hide');    
            $("#cmd_change1_number").val('');        
        }
    });

    $("#btn_change2").click(function(){ //更改短信服务中心特服号码
        var number =  $("#cmd_change2_number").val();  
        if (number) {
            Change2(number);
            $("#cmd_p38").modal('hide');  
            $("#cmd_change2_number").val('');        
        }
    });

    $("#btn_change3").click(function(){ //更改短信服务中心呼出特服号码
        var number =  $("#cmd_change3_number").val();  
        if (number) {
            Change3(number);
            $("#cmd_p39").modal('hide');  
            $("#cmd_change3_number").val('');        
        }
    });

    $("#btn-engine-report-on").click(function(){ //开启点火报告
        EngineReport(1);
        $("#cmd_p66").modal('hide');
    });

    $("#btn-engine-report-off").click(function(){ //关闭点火报告
        EngineReport(0);
        $("#cmd_p66").modal('hide');
    });

    $("#cmd_reback").click(function(){ //车台复位
        Reback();
        $("#cmd_p2").modal('hide');
    });


    //指令弹窗表单验证

    $(".modal-content form").submit(function(){ //屏蔽表单提交
      return false;
    });

    $("#cmd_traveltime-ban-startH,#cmd_traveltime-ban-startM,#cmd_traveltime-ban-stopH,#cmd_traveltime-ban-stopM").blur(function () {
        var Len = $(this).val().length;
        if (Len==1) {
            $(this).val('0'+$(this).val());
        };
   
    });

    $("#cmd_traveltime-ban-startH,#cmd_traveltime-ban-startM,#cmd_traveltime-ban-stopH,#cmd_traveltime-ban-stopM").keyup(function(){
        var Hour = $(this).val();
        var Len = $(this).val().length;
        if (Len>2) {
            $(this).val(Hour.substr(0,2));
            $(this).blur();
        };        
    });

    //验证工号&密码
    function check_jobNum_pwd(S_username,S_password,fun,id4hide){
        //准备参数
        api_url = 'app/auth';
        params = {loginName:S_username,password:S_password}
        //开始调用接口 
        $.post(api_url, params, function(result){
            result = eval('(' + result + ')');
            if(result.success){
                eval(fun+'()');
                $(id4hide).modal('hide');
                $(".authority input[type='text'],.authority input[type='password']").val("");
            }else{
                alert(result.message);
            }
        });
    };


    function onlyNum() {
        if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
        if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
        event.returnValue=false;
    }

    // $("#cmd_timeSS").blur(function (){ 
    //     if ($("#cmd_timeSS").val()=='') {
    //         $(".modal-content .row input.form-control").removeClass("red");
    //         $(this).addClass("red");
    //     }
    // });

    // $("#cmd_timeSS").focus(function (){ 
    //     if ($("#cmd_timeSS").val()=='') {
    //         $(this).removeClass("red");
    //         $(this).addClass("red");
    //     }else{
    //         $(this).parents(".row").find(".red").remove();
    //     };
    // });

  
    // 海马部分新增指令

    $("#cmd1108").live("dblclick", function(){
        openReport();
    });   

    $("#cmd1109").live("dblclick", function(){
        closeReport();
    }); 

    $("#cmd1110").live("dblclick", function(){
        openShutReport();
    }); 

    $("#cmd1111").live("dblclick", function(){
        closeShutReport();
    }); 
 
    $("#cmd1112").live("dblclick", function(){
        openSleepReport();
    }); 

    $("#cmd1113").live("dblclick", function(){
        closeSleepReport();
    }); 

    $("#cmd1114").live("dblclick", function(){
        checkCallCenter();
    }); 

    $("#cmd1115").live("dblclick", function(){
        checkMessageCenter();
    }); 

    $("#cmd1116").live("dblclick", function(){
        checkDeliverParams();
    }); 

    $("#cmd1117").live("dblclick", function(){
        checkACCParams();
    }); 

    $("#cmd1118").live("dblclick", function(){
        checkSleepParams();
    }); 

    $("#cmd1119").live("dblclick", function(){
        checkShutParams();
    }); 

    $("#cmd1120").live("dblclick", function(){
        checkStatusParams();
    }); 

    $("#cmd1121").live("dblclick", function(){
        checkOBDParams();
    });

    $("#cmd1122").live("dblclick", function(){
        checkLimitParams();
    });

    $("#cmd1123").live("dblclick", function(){  // 通知终端升级
        $("#cmd_p1123").modal('show');
    });

    $("#InformTerminal").click(function(){  // 通知终端升级
        informUpdate($("#search_apns").val(),$("#search_codes").val(),$("#search_files").val());
        $("#cmd_p1123").modal('hide');
    });

    $("#cmd1126").live("dblclick", function(){
        $("#cmd_p1126").modal('show');
    });

    $("#EmergencyConfirm").click(function(){
        emergencyNumber($("#emergencyNumber").val());
        $("#cmd_p1126").modal('hide');
    });


    $("#cmd1127").live("dblclick", function(){
        checkEmergency();
        $("#cmd_p1127").modal('hide');
    });

    $("#cmd1128").live("dblclick", function(){
        openEmergency();
    });

    $("#cmd1129").live("dblclick", function(){
        closeEmergency();
    });

    $("#cmd1130").live("dblclick", function(){
        CheckHitch();
        $("#cmd_p1130").modal('hide');
    });
   
    $("#cmd1131").live("dblclick", function(){
        CheckUpdateStatus();
        $("#cmd_p1131").modal('hide');
    });


    /*
     * 指令部分 end
     * 
     */


    /*
     * 标注部分 start
     * 
     */
    //标注全选

    $("#selectAll").live("click", function(){
        if ($(this).children("input").attr("checked")=="checked") {
            $(".com_lists ul input").attr("checked","checked");            
        }else{
            $(".com_lists ul input").removeAttr("checked");            
        };
    });
    

    $(".com_lists ul label").live("click", function(){
        if (typeof($(this).children("input").attr("checked"))=="undefined") {
            $("#selectAll").children("input").removeAttr("checked");            
        }else if ($(this).children("input").attr("checked")=="checked") {
           var LaLen = $(".com_lists ul label").length; 
           var ChLen = $(".com_lists ul input:checked").length;
           if (LaLen == ChLen) {
                $("#selectAll input").attr("checked","checked");
           };
        };
    });
    
    //标注列表管理

    $("#manage_marker_btn").click(function(){
        $("#mark_tableList tbody").html('');
        ListMark();
    });   

    //显示标注到地图
    $("#Markers_show").click(function(){
        Mark_map();
        $("#manage_marker_pop").modal("hide");
    });   

    //隐藏标注
    $("#Markers_hide").click(function(){
        map.clearStaticMarkers();
        $("#manage_marker_pop").modal("hide");
    });   

    //标注删除按钮
    $(".popremove").live("click",function(){
        var faNode = $(this).parents("tr");
        var Mark_id = $(this).parents("tr").find(".Mark_id").val();
        RemoveMark(Mark_id,faNode);
    });

    /*
     * 标注部分 end
     * 
     */

	//用户手动删除监控车辆
	$(".car_list .checkbox-inline i").live("click", function(){ 
		var MVals = $(this).parent().siblings("input[type='hidden']").val();
        $("#Monitor_carPhone").val(MVals);
        var CallIndex = $(this).parent().parent(".checkbox-inline").index();
        CIndex = CallIndex;
		RemoveMonitor();	
	});

    //maps sidebar 显示隐藏

    $(".closeit").click(function(){
        $("#leftPanel").hide();
        $(".sidebar").show();
    });

    // 电话列表------------------------------------------------------------------
    $("#phone_list").click(function(){
        $("#phonebar_call_out_tel").val("");
        $("#phonebar_call_out_real").val("");
        $("#Phone_dailing").html('');
    });
   
    $(document).on("dblclick", "#phoneLinkList tbody.linkscheck tr", function(event){
        var Tel_num = $(this).children("td:last").html().replace(/ /g,'');
        var Tel_this = $(this).parent().parent().get(0).id;
        PhoneOwnership(Tel_num,Tel_this);
    });          

    $(document).on("dblclick", "#phoneLinkList tbody.linkspublic tr", function(event){
        var telSTR = $(this).children("td:last").children("input").val()+$(this).children("td:last").children("span").html();
        var Tel_num = telSTR.replace(/ /g,'');
        var Tel_this = $(this).parent().parent().get(0).id;
        PhoneOwnership(Tel_num,Tel_this);
    });          

    $(document).on("dblclick", "#phoneLinkList_four tbody.RepairPhone tr", function(event){
        var Tel_num = $(this).children("td:last").html().replace(/ /g,'');
        var Tel_this = $(this).parent().parent().get(0).id;
        PhoneOwnership(Tel_num,Tel_this);
    }); 

    $("#check_ownership").click(function(){
        var Tel_num = $("#phonebar_call_out_tel").val();
        PhoneOwnership(Tel_num,"phoneLinkList");
    });

    $("#check_ownership_t").click(function(){
        var Tel_num = $("#phonebar_change_out_tel").val();
        PhoneOwnership(Tel_num,"phonebook-tab");
    });

    $("#check_ownership_Four").click(function(){
        var Tel_num = $("#phonebar_call_out_tel_four").val();
        PhoneOwnership(Tel_num,"phoneLinkList_four");
    });

    $("#dialing").click(function(){
        phonebar_call_out_clicked();
        var linkLen = $(".linkscheck tr td");
        for(i=0;i<linkLen.length;i++)
          {
            if(linkLen[i].innerHTML==$("#phonebar_call_out_real").val()) 
            {
                linkLen[i].style.color="#9a1d17";
                break;  
            }    
        };
        // $('#callout_title').tab('show');    //去电简报显示
    });
  
    //4s店拨号  
    $("#dialing_four").click(function(){
        phonebar_call_out_clicked();
        var linkLen = $(".RepairPhone tr td");
        for(i=0;i<linkLen.length;i++)
          {
            if(linkLen[i].innerHTML==$("#phonebar_call_out_real_four").val()) 
            {
                linkLen[i].style.color="#9a1d17";
                break;  
            }    
        };
    });

  
    // $("#PhoneList").click(function(){
    //     if($("#vehicle_id").val()){
    //         get_Phone();
    //     };
    // });

    // $("#PhoneDown li a").live("click", function(){
    //     var Tel_num = $(this).children("em").html().replace(/[^0-9]/ig,"");
    //     PhoneOwnership(Tel_num);
    //     $("#b_phone").val(Tel_change);
    // });

    // $("#police_c,#ambulance_c").live("click", function(){
    //     var Phone_num = $(this).children("em").html().replace(/[^0-9]/ig,"");
    //     var Phone_code = $(this).find("input").val().replace(/[^0-9]/ig,"");
    //     $("#b_phone").val(Phone_code+Phone_num);
    //     $("#b_marks").val('marks');
    // });    

    // $("#police_num,#ambulance_num").click(function(){
    //     return false; 
    // });

    // 电话绑定------------------------------------------------------------------
   
    $("#BindPhoneLetter").click(function(){
        $("#bindNum").val($("#b_phoneCall").val());
    });

    $("#bindPhoneNum").click(function(){
        BindLinkPhone();
    });

    // 跟踪频率下拉------------------------------------------------------------------

    $(".choose_frequency li").click(function(){
        $(".choose_frequency li").removeClass("current");
        $(this).addClass("current");
        var frequency = $(this).children("a").html();
        // alert(frequency);
    });


    //判断跟踪关联
    $("#tracks_follow").click(function(){
        if($("#tracks_follow").attr("checked")=="checked"){
            $("#dot_follow").attr('checked','checked');                        
        }else{
            $("#line_follow").removeAttr('checked');
            $("#dot_follow").removeAttr('checked');                        
        }; 
    });

    $("#line_follow,#dot_follow").click(function(){
        $("#tracks_follow").attr('checked','checked');
    });

	// 编辑车辆备注信息------------------------------------------------------------------

    $(".carRemarks .glyphicon-floppy-disk").hide();
    $(".carRemarks .glyphicon-edit").click(function(){
        vehicle_id = $("#vehicle_id").val();
        if(!vehicle_id){
            alert("没有vehicle_id，不能编辑");
            return false;
        }
        $(this).hide();
        $(this).siblings("textarea").removeAttr("disabled"); 
        $(this).siblings(".glyphicon-floppy-disk").show();
    });

    $(".carRemarks .glyphicon-floppy-disk").click(function(){
        $(this).hide();
        $(this).siblings(".glyphicon-edit").show();
        var car_edit = $(this);
        car_save(car_edit);
    });
  

    function car_save(car_edit){
        vehicle_id = $("#vehicle_id").val();
        if(!vehicle_id){
            alert("没有vehicle_id");
            return false;
        }
        var car_remark = $("#b_carRemark ").val();
        //准备参数
        api_url = 'app/data/modifyVehicleRemark'; //推荐候选词API
        //api_url = 'remark.txt'; //推荐候选词API
        params = {vehicle_id:vehicle_id,remark:car_remark}
        //开始调用接口
        $.post(api_url, params, function(result){
            result = eval('(' + result + ')');
            if(result.success==false){
                alert(result.message);    
            }else{
                car_edit.siblings("textarea").attr("disabled","disabled"); 
                alert("修改成功！");    
            }
        });
    }





// 编辑隐私密码------------------------------------------------------------------

    $(".b_personalCode .glyphicon-floppy-disk").hide();
    $(".b_personalCode .glyphicon-edit").click(function(){
        customer_id = $("#customer_id").val();
        if(!customer_id){
            alert("没有customer_id，不能编辑");
            return false;
        }
        $(this).hide();
        $(this).siblings("input").removeAttr("disabled"); 
        $(this).siblings(".glyphicon-floppy-disk").show();
    });

    $(".b_personalCode .glyphicon-floppy-disk").click(function(){
        $(this).hide();
        $(this).siblings(".glyphicon-edit").show();
        var pwd_old = $("#b_personalCode_old").val();
        var pwd_new = $("#b_personalCode").val();
        code_save(pwd_old, pwd_new);

    });
  

    function code_save(pwd_old, pwd_new){
        vehicle_id = $("#vehicle_id").val();
        customer_id = $("#customer_id").val();
        if(!customer_id){
            alert("没有customer_id");
            return false;
        }
        //准备参数
        api_url = 'app/data/modifyPrivatePwd';      //推荐候选词API
        params = {vehicle_id:vehicle_id,privacy_pwd_old:pwd_old,privacy_pwd_new:pwd_new}
        //开始调用接口 
        $.post(api_url, params, function(result){
            result = eval('(' + result + ')');
            if(result.success==false){
                alert(result.message);    
            }else{
                $("#b_personalCode").attr("disabled","disabled"); 
                $("#b_personalCode_old").val(pwd_new);
                alert("修改成功！");    
            }
        });
    }



    //保存导航信息
    // $(".carRemarks .glyphicon-edit").click(function(){
    //     vehicle_id = $("#vehicle_id").val();
    //     if(!vehicle_id){
    //         alert("没有vehicle_id，不能导航");
    //         return false;
    //     }
    //     $(this).hide();
    //     $(this).siblings("textarea").removeAttr("disabled"); 
    //     $(this).siblings(".glyphicon-floppy-disk").show();
    // });

    // $(".carRemarks .glyphicon-floppy-disk").click(function(){
    //     $(this).hide();
    //     $(this).siblings(".glyphicon-edit").show();
    //     var car_edit = $(this);
    //     car_save(car_edit);
    // });
  

    // function nav_save(){
    //     vehicle_id = $("#vehicle_id").val();
    //     var maptypes=$("#position_search_maptype_sel option:selected").text();
        
    //     if(!vehicle_id){
    //         alert("没有vehicle_id");
    //         return false;
    //     }
    //     //准备参数
    //     api_url = 'app/map/nav/save'; //导航保存api
    //     params = {
    //         vehicleId:vehicle_id,
    //         mapType:maptypes,
    //         policyType:ddd,
    //         fromLon:ddd,
    //         fromLat:ddd,
    //         fromLabel:ddd,
    //         fromTitle:ddd,
    //         toLon:ddd,
    //         toLat:ddd,
    //         toLabel:ddd,
    //         toTitle:dddd
    //     }
    //     //开始调用接口
    //     $.getJSON(api_url, params, function(result){
    //         if(result.success==false){
    //             alert(result.message);    
    //         }else{
    //             $("#nav_ID").val(result.id);
    //             alert("保存成功！");    
    //         }
    //     });
    // }



    //双击监控车辆查询基本资料
    $(".car_list .checkbox-inline em").live("dblclick", function(){
       $("#keyword").val(''); 
       $("#vehicle_id").val($(this).siblings("input[type='hidden']").val());
       $('#mapInfo').tab('show');   //地图显示
       search(term,2);
    });



    /* 事件处理 BEGIN */
    //初始化
    $("#userlist").hide(); //隐藏推荐栏
    $("#keyword").focus(); //自动获取焦点

    //任何地方被点击
    $(document).click(function(){
        $("#userlist").hide(); //隐藏推荐栏
    });

    //点击搜索按钮时
    $("#submit").click(function(){
       $('#mapInfo').tab('show');   //地图显示
        search(term,2); //触发搜索
    });    

    $("#keyword").on("input", function(){
        recommend(); //触发关键词推荐，显示出推荐栏
    });

    /*
    //关键字有变化时
    $("#keyword").keyup(function(event){
        //方式一：按回车时出现推荐栏
        //if(!$("#userlist li.current").text() && event.keyCode==13){
        //方式二：按下的键是数字或字母或删除键时（即搜索框有变化时），出现推荐栏
        if((event.keyCode>47 && event.keyCode<106) || event.keyCode==8 || event.keyCode==46){
            recommend(); //触发关键词推荐，显示出推荐栏
        }
    });
    */

    //键盘上下移动光标和按回车
    idx=-1;
    $("#keyword").keyup(function(event){
        max = $('#userlist li').length - 1 //一共有max个推荐词(因为从0开始计数，所以要-1)
        if(event.keyCode == 40){ //按了下箭头
            if(idx < max) idx++;
            next_word(idx);
        }else if(event.keyCode == 38){  //按了上箭头                 
            if(idx > 0) idx--;
            next_word(idx);
        }else if(event.keyCode == 13){ //按了回车
            if($("#userlist li.current").text()){ //如果此时有高亮候选词，选中它并搜索
                select_word($("#userlist li.current"));
            }else{ //如果此时没有候选词，则直接使用搜索框内容触发搜索
                $('#mapInfo').tab('show');   //地图显示
                search(term,2); 
            }
        }
    });

    //鼠标点击推荐词被点击时
    $("#userlist li").live("click",function(){
        select_word($(this));
    }).live("mouseover", function(){ //鼠标滑过推荐词时
        idx = $(this).attr("id").replace(/[^0-9]/ig,""); //只取数字部分
        next_word(idx);
    });


    /* 事件处理 END */

    //来电start

    $("#pickup").click(function(){
        //清空关键字
        $("#keyword").val('');
        var term = $("#telegram_num").val();
        search(term,1); //触发搜索
        $("#example10 tbody,#example11 tbody").empty();//清空
        $('#callin_title').tab('show');
    });    


    //来电简报input 选项

    $("#callin_submit").click(function(){
        var SerType = $('input[name="callinradio"]:checked').val();//获取大类
        var SerContent= new Array();//具体服务内容[]
        var SerContentTit = $("#callinTit label input:checked").attr("id");
        //var SerContentArray = $("#callinList li[checklist='"+SerContentTit+"'] label input:checked").siblings("span");
        var SerContentArray = $("#callinList li[checklist='"+SerContentTit+"'] label input:checked");
        for(i=0;i<SerContentArray.length;i++)
        {
            SerContent[i] = encodeURIComponent(SerContentArray[i].value);
        }
        if (SerContent.length==0){
            SerContent='';
        };               
        // var SerContentOther = encodeURIComponent($("#elseinput_in").val());//其他
        var vehicle_id_car = $("#vehicle_id").val();
        var subco_no = $("#b_company_id").val();//分公司id取界面
        if(vehicle_id_car == ""){//基本资料栏无数据，游客来电
            vehicle_id_car = 0;

            subco_no = $("#visitor_sub_1").val();//subid
        }

        var phoneNum = $("#b_phoneCall").val();//来电号码（基本信息栏）
        var Serresult = encodeURIComponent($("#Serresult_in").val());//处理结果
        //保存来电记录（具体服务内容、其他、处理结果、来电号码、大类）
        // if ((SerContent||SerContentOther||Serresult)&&$("#b_phoneCall").val()) {
        if ( SerType && Serresult && $("#b_phoneCall").val()) {
            //alert(SerType+" "+SerContent+" "+Serresult+" "+$("#b_phoneCall").val());
            save_telreport(vehicle_id_car,1,phoneNum,SerContent,Serresult,SerType,subco_no);  
        }else{
            alert("操作信息不能为空!");
        };
    });   

    //来电 end

    //去电简报input 选项

    $("#callout_submit").click(function(){
        var SerType = $('input[name="calloutradio"]:checked').val();//获取大类
        var SerContent= new Array();
        var SerContentTit = $("#calloutTit label input:checked").attr("id");
        //var SerContentArray = $("#calloutList li[checklist='"+SerContentTit+"'] label input:checked").siblings("span");
        var SerContentArray = $("#calloutList li[checklist='"+SerContentTit+"'] label input:checked");
        // var SerContentArray = $("#messages_m label input:checked").siblings("span");
        for(i=0;i<SerContentArray.length;i++)
        {
            SerContent[i] = encodeURIComponent(SerContentArray[i].value);
        }
        if (SerContent.length==0){
            SerContent='';
        };               
        // var SerContentOther = encodeURIComponent($("#elseinput_out").val());
        var vehicle_id_car = $("#vehicle_id").val();
        var subco_no = $("#b_company_id").val();//分公司id取界面
        if(vehicle_id_car == ""){//基本资料栏无数据，游客来电
            vehicle_id_car = 0;

            subco_no = $("#visitor_sub_2").val();//subid
        }

        var phoneNum = $("#b_phoneCall").val();
        var Serresult = encodeURIComponent($("#Serresult_out").val());
        //保存去电记录
        //if ((SerContent||SerContentOther||Serresult)&&$("#b_phoneCall").val()) {
        if ( SerType && Serresult && $("#b_phoneCall").val()) {
            save_telreport(vehicle_id_car,2,phoneNum,SerContent,Serresult,SerType,subco_no);
        }else{
            alert("操作信息不能为空!");
        };
    });   


    //去电 end



    /*
    **一键导航弹窗 start
    **下发导航指令
    */
    //回车键触发搜索
    $("#pop_position_search_name_txt").keypress(function(e){
        if(e.keyCode == 13){
            pop_position_search_start();
        }
    });
    //点击按钮触发搜索
    $("#pop_position_search_btn").click(function(){
        pop_position_search_start();
    });
    //搜索按钮按下 Ajax获取
    var poi_jsonp_url_head = "http://192.110.1.174:8087/poijsonp?";
    //var poi_jsonp_url_head = "http://127.0.0.1:8087/webmap/poijsonp?";
    function pop_position_search_start(){
        $("#TNnavigation-tab1 tbody").html("");
        var maptype = $("#pop_position_search_maptype_sel").val();
        var city_txt = $("#pop_position_search_city_txt").val();
        var name_txt = $("#pop_position_search_name_txt").val();
        if(city_txt.length == 0){
            //alert("请输入城市名称");
            $("#pop_position_search_city_txt").focus();
            return;
        }
        
        if(name_txt.length == 0){
            //alert("请输入搜索名称");
            $("#pop_position_search_name_txt").focus();
            return;
        }
        
        var url = poi_jsonp_url_head + "callback=?&mapType=" + maptype + "&name=" + name_txt + "&city=" + city_txt;
        $.getJSON(url, function(arr){
            $("#pop_position_search_result_table tbody tr").remove();
            if(arr.length == 0){
                var row = "<tr><td>未找到数据!<span style='color:#FF6347'>(" + city_txt + "：" + name_txt + ")</span></td></tr>";
                $("#TNnavigation-tab1 tbody").append(row);
                return;
            }
            pop_show_search_result(arr);
        });
    }
    //显示结果
    function pop_show_search_result(arr, center_marker){
        for(var i = 0; i < arr.length; i++){
            var arri = arr[i];
            var name = arri.name;
            var address = arri.address;
            var lon = arri.decodeLng;
            var lat = arri.decodeLat;
            var row = '<tr><td>' + (i + 1) + '</td><td class="search-title">' + name + '</td><td class="search-text">'+ address + '</td></td><td class="hide">' + lon + '</td><td class="hide">' + lat + '</td></tr>';
            $("#TNnavigation-tab1 tbody").append(row);
        }
    }
    //表格内点击添加背景
    $("#TNnavigation-tab1,#TNnavigation-tab2").on("click",  "tbody tr", function(event){
        selectTr(this);
    });
    //双击后表格迁入下方参数清单
    $('#TNnavigation-tab1 tbody tr.row_selected_bc').live('dblclick', function() {
        var address = $(this).find("td").eq(1).html();
        var lon = $(this).find("td").eq(3).html();
        var _lon = parseInt(lon*1000000)/1000000;
        // alert(_lon);
        var lat = $(this).find("td").eq(4).html();
        var _lat = parseInt(lat*1000000)/1000000;
        // alert(_lat);
        var para = $("#TNnavigation-tab2 tbody tr.row_selected_bc");
        para.find("td").eq(1).html(address);
        para.find("td").eq(2).html(_lon);
        para.find("td").eq(3).html(_lat);
    });
    //清除按钮
    $(".popclean").click(function(){
        var faNode = $(this).parents("tr");
        faNode.children("td").eq(1).html("");
        faNode.children("td").eq(2).html("");
        faNode.children("td").eq(3).html("");
        var tlon = faNode.children("td").eq(2).html();
        var tlat = faNode.children("td").eq(3).html();
        var name = faNode.children("td").eq(1).html();
        var id = faNode.children("td").eq(4).find("input").val();
        map.removeMarkerID(id);
    });
    //地图点选获取经纬度
    $(".pointout").click(function(e){
        //alert("add");
        $('#TNnavigation').modal("hide");
        var faNode = $(this).parents("tr");
        var tit = faNode.children("td") .eq(0).html();
        var callback = function(lon, lat){
            //alert(33);
            faNode.children("td") .eq(1).html("地图点选("+tit+")");
            faNode.children("td") .eq(2).html(lon);
            faNode.children("td") .eq(3).html(lat);
            var id = addStaticMarkerByPop(tit,lon,lat);
            faNode.children("td") .eq(4).find("input").val(id);
            //$('#TNnavigation').modal('show');
            $('#TNnavigation').modal({
                show: true,
                backdrop: "static" 
            });
            //$("#add_static_marker_name").focus();
        };
        map.drawPoint(callback);
    });
    //地图上点出点后要做标记
    function addStaticMarkerByPop(name,lon,lat){
        var id = static_marker_id++;
        var m = map.newSimpleMarker(lon, lat, name, null, id);
        map.addMarker(m, 1);
        if(!map.isPointInView(lon, lat)){
            map.setCenter(lon, lat);
        }
        return id;
    }
    //地图居中
    $(".popSetCenter").click(function(e){
        var faNode = $(this).parents("tr");
        var lon = faNode.children("td").eq(2).html();
        var lat = faNode.children("td").eq(3).html();
        //alert(33);
        //$('#TNnavigation').modal('hide');
        if(lon&&lat){
            map.centerAndZoom(lon, lat, 12);
        }
    });
    /*队列信息*/
    //表格内点击添加背景
    $("#phonebar_wait_table,#phonebar_monitor_table,#phonebar_all_seat_table").on("click",  "tbody tr", function(event){
        selectTr(this);
    });

    /*
    **一键导航 end
    **下发导航指令
    */

    /*
    **报表 start
    **
    */

    //报表-历史回放导出

    $("#play_choose01").click(function(){
        var b_carPhone = $("#b_carPhone").val();
        var plateNumber = encodeURIComponent($("#b_gsm").val());
        var startTime = encodeURIComponent($("#play_history_start_time").val());
        var endTime = encodeURIComponent($("#play_history_end_time").val());
        $(this).attr("href","app/vm/trackexport?callLetter="+b_carPhone+"&plateNumber="+plateNumber+"&startTime="+startTime+"&endTime="+endTime+"&exportRefPos=true");
        $('#PlaybackExport').modal('hide');
    });
    $("#play_choose02").click(function(){
        var b_carPhone = $("#b_carPhone").val();
        var plateNumber = encodeURIComponent($("#b_gsm").val());
        var startTime = encodeURIComponent($("#play_history_start_time").val());
        var endTime = encodeURIComponent($("#play_history_end_time").val());
        $(this).attr("href","app/vm/trackexport?callLetter="+b_carPhone+"&plateNumber="+plateNumber+"&startTime="+startTime+"&endTime="+endTime+"&exportRefPos=false");
        $('#PlaybackExport').modal('hide');
    });
    /*
    **报表 end
    **
    */


    // datatables start

    $('#example1,#example2,#example3,#example4,#example5,#example6,#example7,#gps_table,#history_table,#example9,#example10,#example11,#example12,#example13,#example14,#example15,#p_numsearch-tab,#example20,#example22').dataTable({
        "bPaginate":false,
        "bInfo": false,
        "bFilter": false,
        "oLanguage": {
            "sUrl":"assets/data-tables/js/dataTable.cn.txt"
        },
        "aoColumnDefs": [
        {
         sDefaultContent: '',
         aTargets: [ '_all' ]
          }
        ],        
        "bSort":false
    });

    $('#user-Reg-example,#processedAlarm-1,#processedAlarm-2,#answering-tab,#answering4s-tab,#recentAlarm-tab').dataTable({
        "sPaginationType":"full_numbers",
        "bInfo": false,
        "bFilter": false,
        "oLanguage": {
            "sUrl":"assets/data-tables/js/dataTable.cn.txt"
        },
        "bSort":false
    });

    $('#TNnavigation-tab1,#TNnavigation-tab2,#LYnavigation-tab1,#LYnavigation-tab2,#phonebar_wait_table,#phonebar_monitor_table,#phonebar_all_seat_table,#detailInf-tab,#detailInf2-tab,#PDInf-tab,#PLInf-tab').dataTable({
        "bPaginate":false,
        "bInfo": false,
        "bFilter": false,
        "bSort":false,
        "oLanguage": {
            "sUrl":"assets/data-tables/js/dataTable.cn.txt"
        },
		"bAutoWidth": false
    });


 
});


//省市对应区号
function areaCode(province_name, city_name){

    // if(!$("#vehicle_id").val()){
    //     alert("没有vehicle_id");
    //     return false;
    // }
    
    //准备参数
    api_url = 'app/data/areacode';      //对应区号API
    params = {province:province_name,city:city_name}
    //开始调用接口 
    $.post(api_url, params, function(result){
        result = eval('(' + result + ')');
        if(result.success==false){
            alert(result.message);    
        }else{
            $("#AreaCode01").val(result.areacode); 
            $("#AreaCode02").val(result.areacode); 
        }
    });
}



// search function start-------------------------------------------------------------------

// 通过车牌或车载号码查询------------------------------------------------------------------

/* 搜索函数封装 BEGIN */

/**
 * Ajax：搜索下拉列表
 */

var term; 
function recommend(){
    keyword = $.trim($("#keyword").val());
    if(keyword=='') return false;
    //准备参数
    api_url = 'app/data/vehicleinfo'; //推荐候选词API（常规情况测试）
    params = {param:keyword,count:5}
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            //如果只有一个且精确匹配，则自动搜索，不再出现候选项
            if(result.info.length==1){//&& (result.info[0].plate_no==keyword || result.info[0].call_letter==keyword)
                $("#vehicle_id").val(result.info[0].vehicle_id);
                $('#mapInfo').tab('show');   //地图显示
                // search(term,2); //触发搜索
                $("#keyword").val(result.info[0].plate_no+" "+result.info[0].call_letter);//将选择的那个词填入搜索框 
                $("#userlist").hide();
                return false;
            }
            idx = -1; 
            $("#userlist").html('');
            $.each(result.info, function(i,item){
                $("#userlist").append('<li id="rec'+i+'">'+item.plate_no+' '+item.call_letter+'<input type="hidden" value="'+item.vehicle_id+'"></li>');
            });
            $("#userlist").show();
        }else{
            $("#userlist").hide();
        }
    });
}


/**
 * Ajax：搜索基本信息资料
 */
function search(term,type){
    ClearInfo();                //清空基本信息
    if (type==2) {
        term = $("#vehicle_id").val();
    };
    //准备参数
    api_url = 'app/data/basicinfo'; //搜索API
    params = {param:term, type:type};
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        $("#set_msn_btn").show();//发短信栏显示
        $("#basic_infomation").tab("show");
        if(result && result.success==true){
            $(".visitor_sub").hide();//游客登记隐藏
            // $("#PhoneList").attr("data-toggle","dropdown");
            $("#customer_id").val(result.customer.id);    //用户id
            $("#vehicle_id").val(result.vehicle.id);  //车辆id
            $("#b_company_id").val(result.vehicle.companyNo);  //分公司id
            for(i=0;i<StolenLists.length;i++)
              {
                if(result.vehicle.id==StolenLists[i]) 
                {
                    $("#b_carPhone").css({color:"#9a1d17",border:"1px solid #9a1d17"});
                    break;  
                }    
             }
            
            var b_vips = result.customer.vip;
            switch(b_vips)
            {
                case 1:
                  $("#b_vip").removeClass("icon-card1 icon-card2 icon-card3 icon-card4");
                  break;
                case 2:
                  $("#b_vip").removeClass("icon-card2 icon-card3 icon-card4");
                  $("#b_vip").addClass("icon-card1");
                  break;
                case 3:
                  $("#b_vip").removeClass("icon-card1 icon-card3 icon-card4");
                  $("#b_vip").addClass("icon-card2");
                  break;
                case 4:
                  $("#b_vip").removeClass("icon-card1 icon-card2 icon-card4");
                  $("#b_vip").addClass("icon-card3");
                  break;
                case 99:
                  $("#b_vip").removeClass("icon-card1 icon-card2 icon-card3");
                  $("#b_vip").addClass("icon-card4");
                  break;
            };
            $("#b_user").val(result.customer.name);             //用户名
            $("#b_job").val(result.customer.title);             //职位
            $("#b_gsm").val(result.vehicle.plate_no);          //车牌号
            $("#b_state").val(result.vehicle.status);          //状态
            $("#b_carPhone").val(result.unit.call_letter);      //车载电话
            $("#unit_id").val(result.unit.unit_id);             //终端id
            if (MonitorPhoneList){
                MonitorPhoneList = MonitorPhoneList +';'+$("#b_carPhone").val();
            }else{
                MonitorPhoneList =$("#b_carPhone").val();
            };
            $("#addMonitor_carPhone").val(MonitorPhoneList);      //车载电话
            $("#car_nums").html(result.unit.call_letter);      //车载电话
            $("#b_carType").val(result.vehicle.modelName);           //车型
            $("#b_color").val(result.vehicle.color);            //颜色
            $("#b_model").val(result.unit.type)                //型号
            $("#b_personalCode").val(result.vehicle.privacy_pwd);       //隐私密码修改后
            $("#b_personalCode_old").val(result.vehicle.privacy_pwd);   //隐私密码修改前
            $("#service_pwd").val(result.vehicle.service_pwd );   //服务密码
            $("#b_company").val(result.customer.subco_name);              //公司
            $("#b_setTime").val(result.unit.service_date);               //开通时间
            $("#b_updateTime").val(result.unit.update_date);              //升级日期
            $("#b_carRemark").val(result.vehicle.remark);                  //备注
            $("#b_IDcard").attr("title",result.customer.idcard);           //身份证
            $("#b_preCode").html(result.unit.tamper_code);               //防拆码    
            $("#b_preCode_con").html(result.unit.tamperCodeTranslate);    //防拆码说明    
            $("#b_reg_status").val(result.unit.reg_status);             //入网状态
            $("#b_reg_status").attr("title",result.unit.offnet_time);     //离网时间

            if(result.vehicle.is_corp=="0"){//是否从公司购买商业保险, 0=否, 1=是'
                $("#insurance_mark").css("visibility","hidden");
            } else{
                $("#insurance_mark").css("visibility","visible");
            }

            if(result.vehicle.is_pilfer=="0"){//是否享有综合盗抢险, 0=否, 1=是'
                $("#theft_mark").css("visibility","hidden");
            } else{
                $("#theft_mark").css("visibility","visible");
            }


            $("#add_vpla").html(result.vehicle.plate_no);      //填入快捷警单的车牌号
            $("#add_vid").val(result.vehicle.id);               //填入快捷警单的车辆id
            $("#addConfirm").removeAttr("disabled").removeClass("btn-defalt").addClass("btn-primary");//打开可点击模式

           var vehStatus = result.unit.reg_status;
           if(vehStatus=="在网"){
                $("#b_reg_status").css("color","blue");
                $("#offgrid").hide();//离网图片
           }else if(vehStatus=="离网" || vehStatus == "欠费离网"){
               $("#b_reg_status").css("color","red");
               $("#offgrid").show();//离网图片
           }else{
               $("#offgrid").hide();            
           }
            switch(result.unit.mode)                            //通信模式
            {
                case 1:
                  $("#b_mode").removeClass("icon-massage icon-data icon-data_massage");                           
                  $("#b_mode").addClass("icon-massage").attr("title","短信");   
                  $("#Mode_N,#Mode_B").removeAttr('checked');
                  break;
                case 2:
                  $("#b_mode").removeClass("icon-massage icon-data icon-data_massage");                           
                  $("#b_mode").addClass("icon-data").attr("title","数据流量");  
                  $("#Mode_B").removeAttr('checked');
                  $("#Mode_N").attr('checked','checked');                                           
                  break;
                case 3:
                  $("#b_mode").removeClass("icon-massage icon-data icon-data_massage");                           
                  $("#b_mode").addClass("icon-data_massage").attr("title","流量+短信");  
                  $("#Mode_N").removeAttr('checked');
                  $("#Mode_B").attr('checked','checked');                                           
                  break;
            };

            switch(result.unit.sim_type)              //SIM卡
            {
                case 1:
                  $("#b_sim_type").val("自带卡");                          
                  break;
                case 2:
                  $("#b_sim_type").val("公司卡");                          
                  break;
                case 3:
                  $("#b_sim_type").val("总部卡");                          
                  break;
              
            };

             switch(result.unit.flag)              //标志
            {
                case 0:
                  $("#b_flag").html("");                          
                  break;
                case 1:
                  $("#b_flag").html("停用");                          
                  break;
                case 2:
                  $("#b_flag").html("用户停用");                          
                  break;
                case 3:
                  $("#b_flag").html("欠费停用");                          
                  break;
                case 4:
                  $("#b_flag").html("其他原因停用");                          
                  break;
            };
            $("#FourS_id").val(result.vehicle.s4Id);
            get_Phone();
            if(vehStatus != "离网" && vehStatus != "欠费离网"){
                search_orders(term,type);
                add_monitor(); //添加监控
            }
            
            
            if (type==1) {
                Update_telhistroy();
            };
           
        }else{
            // alert(result.message);//没有关于xxx的信息
            $("#vehicle_id").val("");
            $(".visitor_sub").show();//游客登记出现
        };
    });
}

    
//清空信息
function ClearInfo(){
    //清空基本信息和缴费信息
    $("#b_reg_status").attr("title","");     //离网时间
    $("#b_carPhone").css({color:"#555",border:"1px solid #ccc"});
    $(".tab-content_ver01 input[type='text']").not("#b_phoneCall").val('');
    $(".tab-content_ver01 input[type='hidden']").not("#vehicle_id").val('');
    $(".tab-content_ver01 textarea").val('');  
    $("#b_preCode").html('');                   //防拆码
    $("#b_preCode_con").html('');               //防拆码说明    
    $("#b_marks").val('');
    $("#b_mode").removeClass('icon-massage icon-data icon-data_massage').attr("title","");
    $("#b_vip").removeClass('icon-card1 icon-card2 icon-card3 icon-card4');
    $("#b_flag").html("");    
    $("#b_IDcard").attr("title","");
    $("#m_collectionResult").html(""); 
    $("#service_password").val('');  
    $("#service_pwd").val('');  
    $("#personal_password").val('');  
    $("#personal_pwd").val('');  

    //清空电话区号
    $("#AreaCode01").val('');  
    $("#AreaCode02").val('');  
    $("#S_phone").removeAttr("data-target","#FourCall");
    $("#F_phone").removeAttr("data-target","#FourCall");
    $("#Phone_links").html('');
    $("#Phone_dailing").html('');
    $("#Phone_dailing_four").html('');
   
    $("#phonebar_call_out_tel_four").val('');  
    $("#phonebar_call_out_real_four").val('');  
    
    //清空来去电简报
    $("#profile_m label input").removeAttr("checked");
    $("#callinList li").hide();
    $("#elseinput_in").val('');
    $("#Serresult_in").val('');
    
    $("#messages_m label input").removeAttr("checked");
    $("#calloutList li").hide();
    $("#elseinput_out").val('');
    $("#Serresult_out").val('');

    $("#example10 tbody").html('<tr style="display:none;"><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
    $("#example11 tbody").html('<tr style="display:none;"><td></td><td></td><td></td><td></td><td></td><td></td></tr>');

    //清空指令和缴费信息列表
    $("#example14 tbody").html('<tr style="display:none;"><td></td><td></td><td></td><td></td></tr>');
    $("#example1 tbody").html('<tr style="display:none;"><td></td></tr>');
    $("#example3 tbody").html('<tr style="display:none;"><td></td></tr>');
    $("#MFindCar,#MFollow,#monitor_s,#MReset,#MLockDoor,#MOpenDoor,#MCutOil,#MRecoverOil,#MOpenTrunk,#MGlassUp,#MCon,#MTurnOffLight,#MCarNor,#MCarOper").css("display","none");
        
    //清空跟踪点线
    $("#tracks_follow").removeAttr('checked');
    $("#line_follow").removeAttr('checked');
    $("#dot_follow").removeAttr('checked');

    //指令tab显示
    $("#orders_lists").tab("show");

    //清空一键导航弹窗
    $("#pop_position_search_city_txt").val("深圳");
    $("#pop_position_search_name_txt").val("");
    $("#TNnavigation-tab1 tbody").html("");
    $("#search_pattern label input").removeAttr("checked");
    $("#search_pattern label input:eq(0)").attr("checked","checked");
    $(".destination td:not(:last)").not(".destination td:first").html("");
    $(".pass01 td:not(:last)").not(".pass01 td:first").html("");
    $(".pass02 td:not(:last)").not(".pass02 td:first").html("");
    $(".avoid td:not(:last)").not(".avoid td:first").html("");
    

    //清空指令弹窗内容

    $("#listen_num").val('26719995');
    $("#Mode_N,#Mode_B").removeAttr('checked');
    $("#TypeT,#TypeU").removeAttr('checked');
    $("#checkU,#checkT").val('');
    $("#checkU,#checkT").removeAttr('checked');
    $("#CheckN,#CheckB").val('');
    $("#CheckN,#CheckB").removeAttr('checked');
    $("#sms_content").val('');
    $("#Gcall").val('');
    $("#cmd_change1_number").val('');
    $("#cmd_change2_number").val('');
    $("#cmd_change3_number").val('');
    $("#cmd_timeSS").val('5');
    $("#cmd_timeFre").val('3');
    $("#cmd_blackbox-set_h").val('0');
    $("#cmd_blackbox-set_m").val('0');
    $("#cmd_blackbox-set_s").val('10');
    $("#cmd_ratelimit-set-speed").val('60');
    $("#cmd_IntervalDeliver_h").val('0');
    $("#cmd_IntervalDeliver_m").val('0');
    $("#cmd_IntervalDeliver_s").val('10');
    $("#cmd_apn").val('');
    $("#cmd_ip").val('192.168.10.1');
    $("#cmd_num01").val('');
    $("#cmd_num02").val('8090');
    $("#TypeT").attr('checked','checked');
    $("#cmd_space").val('1');

    //清空电话列表

    $("#LinkManList").html("");
    $("#DriverList").html("");
    $("#safeNum").html("");
    $("#fourSNum").html("");
    $("#carNumber").html("");
    $("#AreaCode01").val("");
    $("#AreaCode02").val("");

    //参数重置（外部json）
    // $.getJSON('js/test.json', function(data){
    //     if(data){
    //         // alert(data.i_nav_settingsite.val);
    //         $("#pop_position_search_city_txt").val(data.i_nav_settingsite.val);
    //     }
    // });

}

/**
 * 加入监控列表
 */


var CIndex; 
function add_monitor(){
    var carID = $('#vehicle_id').val(); //车辆id
    var carNum = $('#b_carPhone').val(); //车载号码
    var gsm = $('#b_gsm').val(); //车牌号

    //如果监控列表没有该车，则添加到监控列表
    if($("#List_"+carID).length == 0){ 
        var vacant = 1; //默认有空缺位置

        //监控列表已满，将未选中的移除监控(前台+后台)
        var monitorLen = $('.car_list p').length;
        if (monitorLen >= monitor_lenth) {
            vacant = 0; //无空缺了
            for (var i = monitor_lenth-1; i >= 0; i--) {
                if ($(".car_recent p:eq("+i+") input[type='checkbox']").attr("checked")!='checked') {
                    vacant = 1; //又有空缺了
                    CIndex = i;
                    var MVal = $(".car_list>p:eq("+i+")>input[type='hidden']").val();
                    $("#Monitor_carPhone").val(MVal);                   
                    RemoveMonitor();
                    break;
                };
            };
        };

        //若有空缺位置，则添加到监控列表
        if(vacant){
            // //前台
            // var addHTML= '<p class="checkbox-inline" id="List_'+carID+'"><input type="checkbox" value=""><span><em>'+gsm+' </em><i class="glyphicon glyphicon-trash cursor" title="删除"></i></span><input type="hidden" value="'+carNum+'"></p>';
            // $(".car_recent .car_list").prepend(addHTML);
            // $("#monitor_lists").tab("show");
            //后台
            AddMonitor();
        }
        localStorage.setItem("monitor_list",$(".car_list").html());
    }else{
        $("#List_"+carID).prependTo(".car_list");
        localStorage.setItem("monitor_list",$(".car_list").html());
    };
}



//电话信息
function get_Phone(){
    $("#carNumber").html($("#b_carPhone").val());
    //准备参数
    api_url = 'app/data/phoneinfo'; //搜索API
    params = {customer_id:$("#customer_id").val(),vehicleId:$("#vehicle_id").val()};

    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $.each(result.phone, function(i,item){
                // number = item.number==null ? '' : item.number; 
                if (item.number!=null) {
                    switch(item.type)
                    {
                        case 1:
                            if (item.linkmanType==1) {
                                $("#b_phone").val(item.number);
                                $("#LinkManList").append('<tr class="FirstContact"><td>'+item.name+'</td><td>'+item.number+'</td></tr>');
                            }else if (item.linkmanType==2) {
                                $("#LinkManList").append('<tr class="SecContact"><td>'+item.name+'</td><td>'+item.number+'</td></tr>');
                            };                        
                            break;
                        case 2:
                            $("#DriverList").append('<tr><td>'+item.name+'</td><td>'+item.number+'</td></tr>');
                            break;
                        case 3:
                            $("#safe_num").append('<tr><td>保险公司</td><td>'+item.number+'</td></tr>');                  //保险公司电话
                            break;
                        case 4:
                            $("#fourSNum").append('<tr><td>4s店电话</td><td>'+item.number+'</td></tr>');             //4s公司电话
                            break;
                    };                      

                };

            });
        }else{
            $("#customer_id").val("");     
        }                   
    });
}

//绑定联系人电话信息

function BindLinkPhone(){
    api_url = 'app/data/bind';   //url
    params = {customerId:$("#customer_id").val(),vehicleId:$("#vehicle_id").val(),name:$("#bindLinkman").val(),phone:$("#bindNum").val(),linkmanType:99};    //参数
    
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==false){
            alert(result.message);    
        }else{
            $("#bindPhone").modal("hide");
            alert("绑定成功！");    
            $("#bindLinkman").val('');
            $("#bindNum").val('');
        }
    });
}


/**
 * Ajax：搜索缴费信息资料
 */
function search_m(term,type){
    $("#example14 tbody").html('<tr style="display:none;"><td></td><td></td><td></td><td></td></tr>');
    //准备参数
    if (type==2) {
        term = $("#vehicle_id").val();
    };
    api_url = 'app/data/feeinfo';
    params = {param:term,type:type};
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            if (result.insurance!=null) {
                $("#m_Comp").val(result.insurance.ic_name);            //投保公司
                $("#m_Limit").val(result.insurance.amount);            //保险金额(单位万元) 
            }
         
            if (result.paytxt!=null) {  
                $("#m_bank").val(result.paytxt.bank);                   //开户行
                $("#m_holder").val(result.paytxt.cust_name);            //户主
                $("#m_account").val(result.paytxt.account_no);          //账户
                $("#m_institution").val(result.paytxt.agency);        //托收机构
                $("#m_institution").val(result.paytxt.print_time);        //发票打印日期
                $("#m_adress").val(result.paytxt.address);              //投递地址
                $("#m_Rencent").val(result.paytxt.stamp);               //最近托收时间
                $("#m_collection").val(result.paytxt.amount);      //托收金额
                $("#m_collectionResult").html(result.paytxt.pay_tag);      //托收标识

            }
            $.each(result.fee_info, function(i,item){
                html =  '<tr role="row" class="odd">';
                html += '<td>'+item.feetype+'</td>';
                html += '<td>'+item.fee_sedate+'</td>';
                html += '<td>'+item.fee_cycle+'</td>';
                html += '<td>'+item.ac_amount+'</td>';
                html += '</tr>';
                $("#example14 tbody").append(html);                           
            });

            $("#m_monthPay").val(result.sum_monthly);              //每月应缴 
            $("#m_monthYear").val(result.sum_annually);            //每年应缴

        }else{
            $("#vehicle_id").val("");  
        }
    });
}

$("#m_payment").click(function(){
    var term = $("#vehicle_id").val();
    search_m(term,2); //触发搜索
});  

//指令列表--------------------------------------------------------

function search_orders(term,type){
    if (type==2) {
        term = $("#vehicle_id").val();
    };
    //准备参数
    api_url = 'app/cmd/cmdlist';
    params = {param:term,type:type};
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $.each(result.command, function(i,item){
                html ='<tr role="row">'; 
                html += '<td id="cmd'+item.id+'">'+item.name+'</td>';
                html += '</tr>';
                switch(item.type)
                    {
                    case 1:
                        $("#example1 tbody").append(html);         
                        break;
                    case 2:
                        $("#example3 tbody").append(html);         
                        break;
                    };  

                switch(item.id)
                    {
                    case 1:
                        $("#MFindCar").css("display","block");         
                        break;
                    case 2:
                        $("#MCarNor").css("display","block");         
                        $("#MReset").css("display","block");         
                        break;
                    case 4:
                        $("#MCarNor").css("display","block");         
                        $("#MLockDoor").css("display","block");         
                        break;
                    case 5:
                        $("#MCarNor").css("display","block");         
                        $("#MOpenDoor").css("display","block");         
                        break;
                    case 6:
                        $("#MCarNor").css("display","block");         
                        $("#MCutOil").css("display","block");         
                        break;
                    case 7:
                        $("#MCarNor").css("display","block");         
                        $("#MRecoverOil").css("display","block");         
                        break;
                    case 23:
                        $("#monitor_s").css("display","block");         
                        break;
                    case 21:
                        $("#MFollow").css("display","block");         
                        break;
                    case 1101:
                        $("#MCarOper").css("display","block");         
                        $("#MOpenTrunk").css("display","block");         
                        break;
                    case 1102:
                        $("#MCarOper").css("display","block");         
                        $("#MGlassUp").css("display","block");         
                        break;
                    case 1104:
                        $("#MCarOper").css("display","block");         
                        $("#MCon").css("display","block");         
                        break;
                    case 1107:
                        $("#MCarOper").css("display","block");         
                        $("#MTurnOffLight").css("display","block");         
                        break;
                    };
            });
        }else{
            $("#vehicle_id").val("");  
        }
    });
}



/**
 * 选择推荐词
 * @param idx 推荐词索引
 */
function next_word(idx){
    $("#userlist li").removeClass("current");
    $("#rec"+idx).addClass("current");
}

/**
 * 选定推荐词
 * @param word 推荐词
 */
function select_word(word_li){
    $("#vehicle_id").val(word_li.children("input").val());
    $("#keyword").val(word_li.text());  //将选择的那个词填入搜索框 
    $("#userlist").html('').hide();     //去掉推荐栏
    $('#mapInfo').tab('show');   //地图显示
    search(term,2); //触发搜索

}

/* 函数封装 END */




// search function end-------------------------------------------------------------------------------------------


/**
 * 电话部分
 * call
 */

//来去电简报保存
function save_telreport(vehicle_id_car,type,phoneNum,SerContent,Serresult,SerType,sub_id){
    //参数
    var api_url = 'app/tel/briefs/add'; //url
    var params = {vehicle_id:vehicle_id_car, type:type,phone:phoneNum, serviceType:SerContent, result:Serresult, p_st_id:SerType, c_phone:defaltNum , flag:0, subco_no: sub_id};//参数(车辆id、type 1来电、2去电、号码、服务内容、处理结果、大类id、中心号码、游客分公司)
    var params_t = decodeURIComponent($.param(params,true));
    $.getJSON(api_url+"?"+params_t,function(result){
        if(result.success==true){
            //清空来去电简报
            $("#callinTit label input,#callinList label input").removeAttr("checked");//来电简报选项
            $("#callinList li").hide();//收起
            $("#elseinput_in").val('');
            $("#Serresult_in").val('');
            $("#calloutTit label input,#calloutList label input").removeAttr("checked");//去电简报选项
            $("#calloutList li").hide();//收起
            $("#elseinput_out").val('');
            $("#Serresult_out").val('');
            Update_telhistroy();
            $("#callout_false").modal("hide");//呼出失败弹窗消失
        }else{
            alert(result.message);
        };
    });
}

//呼出失败保存
function save_telreport_fail(vehicle_id_car,type,phoneNum,SerType,flag,Serresult){//id、呼出、号码、大类、flag接通情况、结果
    //参数
    var api_url = 'app/tel/briefs/add'; //url
    var params = {vehicle_id:vehicle_id_car, type:type,phone:phoneNum, p_st_id:SerType, flag:flag, c_phone:defaltNum, result:Serresult, subco_no:$("#b_company_id").val()};//参数(车辆id、type 1来电、2去电、号码、服务内容、处理结果、大类id、中心号码)
    var params_t = decodeURIComponent($.param(params,true));
    $.getJSON(api_url+"?"+params_t,function(result){
        if(result.success==false){
            alert(result.message);    
        }else{
            //清空来去电简报
            $("#callinTit label input,#callinList label input").removeAttr("checked");//来电简报选项
            $("#callinList li").hide();//收起
            $("#elseinput_in").val('');
            $("#Serresult_in").val('');
            $("#calloutTit label input,#calloutList label input").removeAttr("checked");//去电简报选项
            $("#calloutList li").hide();//收起
            $("#elseinput_out").val('');
            $("#Serresult_out").val('');
            Update_telhistroy();
        }
    });
}

/* 电话部分 END */

/*
**alarm
**
*/
var isAlarm = false; //处警标志
var CouldFind = true;//是否能查到车台信息
//var isAlarmhangout = false; //挂警拾回中
//警情开关 手动置
$("#alarmopt").click(function(){
    var opt = $(this).html();
    var status = $("#alarmstatus").html();
    if(opt=="就绪"){
    SeatIdle();//处警置闲
    }
    else{
    SeatBusy();//处警置忙
    }
});
//表格内点击添加背景
$("#example4,#example5,#example6").on("click",  "tbody tr", function(event){
    selectTr(this);
});
//双击接警 弹出警单
$("#example4 tbody tr").live('dblclick', function() {
    var TR = $("#example4 tbody").children("tr").eq(0);//取第一行
    txtalarmcaller.value = TR.children("td").eq(3).html();//记在页面 alarmcaller
    txtalarmsn.value = TR.children("td").eq(8).html();//记在页面 alarmsn
    txtalarmid.value = TR.children("td").eq(9).html();//记在页面 alarmid
    txtalarmname.value = TR.children("td").eq(1).html();//记在页面 alarmname
    txtalarmtime.value = TR.children("td").eq(4).html();//记在页面 alarmtime
    
    $("#keyword").val('');//清空搜索栏
    isAlarm = true;//处警标识
//    DecAlarmCount();//未处警数-1
    $("#alarmcount").html("0");//重复报的警要删除
    var numberPlate = TR.children("td").eq(0).html();//取车牌
    var alarmtime = txtalarmtime.value;//取报警时间
    var metter = txtalarmname.value;//取报警内容
    var lon = TR.children("td").eq(10).html();//取经度
    var lat = TR.children("td").eq(11).html();//取纬度
    var v_id = TR.children("td").eq(7).html();//取车辆id
    var speed = TR.children("td").eq(5).html();//取速度
    var course = TR.children("td").eq(6).html();//取方向
    var status = TR.children("td").eq(2).html();//取状态

    var loc = TR.children("td").eq(13).html();//取是否定位
    var src_course =TR.children("td").eq(14).html();//取方向码
    var src_status = TR.children("td").eq(15).html();//取状态码
    var gpstime = TR.children("td").eq(16).html();//取gps时间
    /***地图居中 写入gps信息返回栏 start***/
    var alarmidE;
    var alarmtype=TR.children("td").eq(9).html();
        if (alarmtype==0||alarmtype==null) {alarmidE=0;}else{alarmidE=1;};
    var _lon = parseFloat(lon)/1000000;
    var _lat = parseFloat(lat)/1000000;
    var Tran_lon_D = Math.floor(_lon);  //度
    var Tran_lon_M = Math.floor((_lon-Math.floor(_lon))*60);    //分
    var Tran_lon_S = Math.floor(((_lon-Math.floor(_lon))*60-Math.floor((_lon-Math.floor(_lon))*60))*60);    //秒
    var Tran_lat_D = Math.floor(_lat);  //度
    var Tran_lat_M = Math.floor((_lat-Math.floor(_lat))*60);    //分
    var Tran_lat_S = Math.floor(((_lat-Math.floor(_lat))*60-Math.floor((_lat-Math.floor(_lat))*60))*60);    //秒
    var opts = {
        id: numberPlate,
        callLetter: txtalarmcaller.value,
        label: numberPlate + " " + gpstime,
        numberPlate: numberPlate,
        lon: _lon,
        lat: _lat,
        speed:parseFloat(speed),
        course:parseInt(src_course),
        gpsTime:gpstime,
        stamp:alarmtime,
        isAlarm:parseInt(alarmidE),
        status:src_status
    };

    var m = map.addOrUpdateVehicleMarkerById(opts);
    if(!map.isPointInView(_lon, _lat)){
        map.setCenter(_lon, _lat);
    }
    
    m.target.flicker();
    if ($("#gps_table>tbody tr").length>=gpsLength){
        $("#gps_table>tbody tr:last").remove();
    };

    GPS_judge(numberPlate);//清除原gps信息
    var html = '<tr><td>'+numberPlate+'</td><td>'+txtalarmcaller.value+'</td><td>'+speed+'</td><td>'+status+'</td><td>'+gpstime+'</td><td>'+alarmtime+'</td><td>'+loc+'</td><td>'+course+'</td><td>'+Tran_lon_D+'°'+Tran_lon_M+'′'+Tran_lon_S+'″,'+Tran_lat_D+'°'+Tran_lat_M+'′'+Tran_lat_S+'″</td><td class="hide LonVal">'+_lon+','+_lat+'</td><td class="hide">'+_lon+'</td><td class="hide">'+_lat+'</td><td class="hide">'+src_course+'</td><td class="hide">'+alarmtime+'</td><td class="hide">'+1+'</td><td class="hide">'+src_status+'</td></tr>';
    $("#gps_table>tbody").prepend(html);

    localStorage.setItem("GPS_list",$("#gps_table>tbody").html());  
    Code_city();
    /***地图居中 写入gps信息返回栏 end***/

    var presenttime = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");//生成接警时间
    var row = '<tr><td>'+alarmtime+'</td><td>'+metter+'</td><td class="hide">'+presenttime+'</td><td class="hide">'+lon+'</td><td class="hide">'+lat+'</td><td class="hide">'+v_id+'</td><td class="hide">'+speed+'</td><td class="hide">'+course+'</td><td>'+status+'</td></tr>';
    $("#example12 tbody").empty().prepend(row);//写入警单的表格
    $("#alarmlistcaller").html(numberPlate);//写入当前车辆车牌
    SeatBusy();//处警置忙

    $("#vehicle_id").val(v_id);//把车辆id赋到页面上 查询用
    search(term,2);//查询

    var istrans = TR.children("td").eq(12).html();//取转警标志 1为转警
    if(istrans==1){//是转警
        AcceptTransferAlarm();//接受转警
        //alert("发送接受转警请求");
        save_transferalarm();//转警成功保存数据库
    }else{//不是转警
        AcceptAlarm(); //接警
        //alert("发送接警请求");
    }

    alarmbegin();//警灯亮（正常接警）
    Update_alarmhistroy();//更新警情历史记录
    AskSeatList();//申请座席列表（转警用）
    //removeCar($("#example4 tbody tr"));//原表格移除车辆（未处理警情列表）
    $("#example4 tbody").empty();
    $('#alarmlist_title').tab('show');//显示警单
    $("#callOut").modal("show");//弹窗显示
});

//双击挂警拾回 弹出警单
$("#example5 tbody tr").live('dblclick', function() {
    txtalarmcaller.value = $(this).children("td").eq(3).html();//记在页面 alarmcaller
    txtalarmsn.value = $(this).children("td").eq(8).html();//记在页面 alarmsn
    txtalarmid.value = $(this).children("td").eq(9).html();//记在页面 alarmid
    txtalarmname.value = $(this).children("td").eq(1).html();//记在页面 alarmname
    txtalarmtime.value = $(this).children("td").eq(4).html();//记在页面 alarmtime

    var numberPlate = $(this).children("td").eq(0).html();//取车牌
    var alarmtime = txtalarmtime.value;//取报警时间
    var metter = txtalarmname.value;//取报警内容
    var lon = $(this).children("td").eq(10).html();//取经度
    var lat = $(this).children("td").eq(11).html();//取纬度
    var v_id = $(this).children("td").eq(7).html();//取车辆id

    var speed = $(this).children("td").eq(5).html();//取速度
    var course = $(this).children("td").eq(6).html();//取方向
    var status = $(this).children("td").eq(2).html();//取状态

    var presenttime = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");//生成接警时间（生成现在时间）

    var row = '<tr><td>'+alarmtime+'</td><td>'+metter+'</td><td class="hide">'+presenttime+'</td><td class="hide">'+lon+'</td><td class="hide">'+lat+'</td><td class="hide">'+v_id+'</td><td class="hide">'+speed+'</td><td class="hide">'+course+'</td><td>'+status+'</td></tr>';
    $("#example12 tbody").empty().prepend(row);//写入警单的表格
    $("#alarmlistcaller").html(numberPlate);//写入当前车辆车牌
//  $('#alarmlist_title').tab('show');//显示警单 此操作现已移至取消挂警回调函数里
    SeatBusy();//处警置忙
    $("#vehicle_id").val(v_id);//把车辆id赋到页面上 查询用
    search(term,2);//查询
    
    alarmreg();//拾回警灯亮
    CancelPauseAlarm(); //取消挂警
    removeCar($("#example5 tbody tr"));//原表格移除车辆（挂警列表）
});

//双击已处理警情列表  查出资料
$("#example6 tbody tr").live('dblclick', function() {
    var vid = $("#vehicle_id");
    vid.val($(this).children("td").eq(5).html());//把车辆id赋到页面上 查询用
    search(term,2);//查询
});

//双击案发车辆列表  查出资料
$("#example7 tbody tr").live('dblclick', function() {
    var vid = $("#vehicle_id");
    vid.val($(this).children("td").eq(2).html());//把车辆id赋到页面上 查询用
    search(term,2);//查询
});

//获取警单单选框的内容
function getAlarmopt(){
    var obj = document.getElementsByName("options7");
    var temp ="";
    for(var i=0; i<obj.length; i ++){
        if(obj[i].checked){
            //alert(obj[i].value);
            temp = obj[i].value;
        }
    }
    return temp;
}

//移除警单单选框的选项 清空车牌号 清空简报
function removeAlarmopt(){
    var obj = document.getElementsByName("options7");
    for(var i=0; i<obj.length; i++){
        obj[i].checked = false; 
        obj[i].removeAttribute("checked");
    }
    $("#alarmlistcaller").html("");//清空车牌号
    $(".tiny_notes textarea").val("");//清空简报
}

//未处警数+1
function addAlarmCount(){
    var count = $("#alarmcount").html();//获取未处理警情数（小红灯）
    count ++;
    $("#alarmcount").html(count);//加1
}

//未处警数-1
function DecAlarmCount(){
    var count = $("#alarmcount").html();//获取未处理警情数（小红灯）
    count --;
    $("#alarmcount").html(count);//减1
}

//挂警数+1
function addhangAlarmCount(){
    var count = $("#hangalarmcount").html();//获取挂警数
    count ++;
    $("#hangalarmcount").html(count);//加1
}

//挂警数-1
function DechangAlarmCount(){
    var count = $("#hangalarmcount").html();//获取挂警数
    count --;
    $("#hangalarmcount").html(count);//减1
}

//结束处警
$("#finishAlarm_btn").click(function(){
    if (isAlarm==false) {
        return;
    };
    var alarmdetail = encodeURIComponent(getAlarmopt()); //取警单报警核实内容
    var jb = $(".tiny_notes textarea");//取来电简报节点
    var alarmresult = encodeURIComponent(jb.val()); //记录报警内容

    if(alarmdetail == ""){
        alert("请选择核实报警项");
        return;
    }
    /*
    if(alarmresult == ""){
        alert("请填写接警简报");
        jb.focus();
        return;
    }
    */
    HandleAlarm();//结束处警
});
//处警结束移除原处警列表中该行记录 未处理警情和挂警栏
function removeCar(TrNode){
    var caller = txtalarmcaller.value;
    TrNode.each(function(){
        var itcaller = $(this).children("td").eq(3).html();
        if(itcaller == caller){
            $(this).remove();
        }
    });
}
//警灯亮
function alarmbegin(){
    $("#alarmbulb").html("（接警中）");//显示接警中
}
//警灯灭
function alarmend(){
    $("#alarmbulb").html("");//显示接警中
}
//拾回警灯亮
function alarmreg(){
    $("#alarmregained").html("（挂警拾回）");//显示接警中
}
//拾回警灯灭
function alarmregend(){
    $("#alarmregained").html("");//显示接警中
}
//挂警
$("#hangalarm_btn").click(function(){
    if (isAlarm==false) {
        return;
    };
    PauseAlarm();//申请挂警
});
//转警
$("#transferAlarm_btn").click(function(){
    if (isAlarm==false) {
        return;
    };
    var dst=$("#transfer_dstseat option:selected").val();//获取下拉选的值，座席名
    TransferAlarm(dst);//转警
});

//快捷转警
$('#transfer_list li a').live('click', function() {
    if (isAlarm==false) {
        return;
    };
//    alert("下拉转警");
    var dst=$(this).html();//获取下拉选的值，座席名
    TransferAlarm(dst);//转警
});

//警情第一步查询车牌号
function search_pla(alarmname,callLetter,alarmsn,gpsTime,alarmTime,lon,lat,istrs,alarmid,speed,course,status,loc,src_course,src_status){
//  alert("警情第一步查询"+" 事件名："+alarmname+" 呼号："+callLetter+" 警情sn："+alarmsn+" gps时间："+gpsTime+" 经度："+lon+" 纬度："+lat+"警情id："+alarmid);
    //准备参数
    var api_url = 'app/data/vehicleinfo';
    var params = {param:callLetter, count:1};
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            if(result.info.length==0){
                CouldFind = false;//不能找到车
                txtalarmcaller.value = callLetter;//记在页面 alarmcaller
                txtalarmsn.value = alarmsn;//记在页面 alarmsn
                HandleAlarm();//直接结束
                $("#alarmcount").html("0");//处警过程中重复报的警要删除
                if($("#autoSetfree").attr("checked")=="checked"){//自动置闲
                    setTimeout(SeatIdle,2000);//2秒后自动置闲
                }
                return;
            }
            CouldFind = true;//查询到车台
            if(isAlarm == false){//未处警时播放
                if(isRing == 1){//判断是否响铃：1为响铃标识
                    audio.play();//播放警情音效
                }
            }
            $.each(result.info, function(i,item){
//              alert("正在写第一步的列表");
                var row = "";
                if (istrs == 1) {
                    row = '<tr class="transfer_list"><td>'+item.plate_no+'</td><td>'+alarmname+'</td><td>'+status+'</td><td>'+callLetter+'</td><td>'+alarmTime+'</td><td>'+speed+'</td><td>'+course+'</td><td class="hide">'+item.vehicle_id+'</td><td class="hide">'+alarmsn+'</td><td class="hide">'+alarmid+'</td><td class="hide">'+lon+'</td><td class="hide">'+lat+'</td><td class="hide">'+istrs+'</td><td class="hide">'+loc+'</td><td class="hide">'+src_course+'</td><td class="hide">'+src_status+'</td><td class="hide">'+gpsTime+'</td></tr>';//转警列表背景
                } else {
                    row = '<tr><td>'+item.plate_no+'</td><td>'+alarmname+'</td><td>'+status+'</td><td>'+callLetter+'</td><td>'+alarmTime+'</td><td>'+speed+'</td><td>'+course+'</td><td class="hide">'+item.vehicle_id+'</td><td class="hide">'+alarmsn+'</td><td class="hide">'+alarmid+'</td><td class="hide">'+lon+'</td><td class="hide">'+lat+'</td><td class="hide">'+istrs+'</td><td class="hide">'+loc+'</td><td class="hide">'+src_course+'</td><td class="hide">'+src_status+'</td><td class="hide">'+gpsTime+'</td></tr>';//正常警列表
                };
                $("#example4 tbody").prepend(row);
            });
            $('#processingAlarm').tab('show');//显示未处理警情列表
            $('#alarming').tab('show');//显示报警列表
        }
    });
}
/*
**警情、电话存取  数据库Ajax start
*/

//更新电话历史记录
function Update_telhistroy(){//来电简报和去电简报中的一样
    $("#example10 tbody,#example11 tbody").empty();//清空

    var v_id = $("#vehicle_id").val();//获取页面中车辆id
    var v_phone = "";//来电
    if(v_id == ""){
        v_phone = $("#b_phoneCall").val();//获取页面中来电
    }
    var api_url = 'app/tel/briefs';//url
    var params = {vehicleId:v_id, phoneNo: v_phone, count:5};//参数(车辆id、返回条数)
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            var row = '';
            if (result.info!=null) {//时间、服务大类、服务类型、操作员id、操作员姓名、处理结果
                if(result.info.length==0){
                    row = '<tr><td colspan="6">无电话历史记录</td></tr>';
                    $("#example10 tbody,#example11 tbody").append(row);//写入提示无记录
                } else{
                    $.each(result.info, function(i,item){
                        if (item.type==1) {
                            typeCall = "来电";
                        }else if(item.type==2){
                            typeCall = "去电";
                        };
                        var row = '<tr><td>'+item.stamp+'</td><td>'+typeCall+'</td><td>'+item.content+'</td><td >'+item.op_id+'</td><td>'+item.op_name+'</td><td>'+item.result+'</td><td style="display:none;"><input type="text" value="'+item.id+'" /></td></tr>';
                        $("#example10 tbody,#example11 tbody").append(row);//写入
                    });
                }  
            }
        }
    });
}

$("#callin_title,#callout_title").click(function(){//查询电话历史记录
    if ($("#vehicle_id").val()){
       Update_telhistroy();
    };
});
$("#alarmlist_title").click(function(){//查询警情历史记录
    Update_alarmhistroy();//刷新警情历史记录
    AskSeatList();//请求座席列表
});

$("#addCrimeCar_btn").click(function(){//打开快捷警单
    //var vpla = $("#b_gsm").val();//获取车牌
    //$("#add_vpla").html(vpla);//填入界面快捷警单
    //var vid = $("#vehicle_id").val();//获取车辆id
    //$("#add_vid").val(vid);//填入界面隐藏起来
    //GetAlarmStatus();//写入警情状态（下拉列表）//暂时取消
    $("#addCrimeCar_div").slideToggle();
});

$("#crimecar_btn").click(function(){	// 请求案发车辆列表
    getOutstandingVehicle();
});


$("#cancelAdd").click(function(){//取消添加
    $("#addCrimeCar_div").slideUp();
});

$("#addConfirm").click(function(){//提交添加
    $("#addCrimeCar_div").slideUp();;
});

//更新警情历史记录
function Update_alarmhistroy(){
    var v_id = $("#vehicle_id").val();//获取页面中车辆id
//    alert("页面车辆id："+v_id+" 更新警情历史记录中");
    var api_url = 'app/alarm';
    var params = { now: new Date().getTime(), vehicleId: v_id, count: 5};//参数(车辆id、返回条数)
    $.getJSON(api_url, params, function(result){
//        alert("警情历史记录回调函数开始执行");
        $("#example13 tbody").empty();
        if(result && result.success==true){
//            alert("success为true");
            var row = '';
            if (result.info!=null) {
//                alert("存在info字段:");
                if(result.info.length==0){
                    row = '<tr><td colspan="8">无警情历史记录</td></tr>';
                    $("#example13 tbody").append(row);//写入提示无记录
                } else{
                    $.each(result.info, function(i,item){
                        row = '<tr><td>'+item.alarm_time+'</td><td>'+item.accept_time+'</td><td>'+item.alarmName+'</td><td>'+item.result+'</td><td>'+item.opName+'</td><td>'+item.handle_time+'</td><td>'+replaceLog_res(item.log_res)+'</td><td>'+replaceLog_flag(item.log_flag)+'</td><td style="display:none">'+item.log_flag+'</td><td style="display:none">'+item.alarmSn+'</td></tr>';
                        $("#example13 tbody").append(row);//写入信息
                    });
                }
                $("#AlarmTrace_btn").attr("disabled","disabled");//按钮不可用
            }
        }
    });
}

function replaceLog_res(num){//转换
    var temp ="";
    switch(num){
        case 0: temp = "";break;
        case 1: temp = "来电解除警情"; break;
        case 2: temp = "呼出解除警情"; break;
        case 3: temp = "24小时未联系到"; break;
        default: temp = "";
    }
    return temp;
}

function replaceLog_flag(num){//转换
    var temp ="";
    switch(num){
        case 0: temp = "";break;
        case 1: temp = "<i class='glyphicon glyphicon-flag'></i>"; break;
        default: temp = "";
    }
    return temp;
}

//结束处警保存信息
function save_alarm(a_con,a_bri,isRealAlarm,numberPlate,vid){//报警内容、接警简报内容
//    alert("正在执行保存警情函数！");
    var a_sn = txtalarmsn.value;//获取页面中警情sn
    var as_id = txtalarmid.value;//获取页面中警情状态id
    var api_url = 'app/alarm/finish';//url
    var params = {alarmSn:a_sn, alarmStatusId:as_id, content:a_con, brief:a_bri, realAlarm:isRealAlarm};//参数(警情sn、警情id、报警内容、接警简报、是否是真实警情)
    //alert("警情sn:"+a_sn+"警情状态id:"+as_id+"报警内容:"+a_con+"接警简报:"+a_bri+"是否真实警情:"+isRealAlarm);
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            //alert("结束处警保存成功！");
            ProcessedAlarm_judge(numberPlate);//删除已处理警情列表中同一车台
            Update_alarmhistroy();//刷新警情历史记录
            var row = '<tr><td>'+numberPlate+'</td><td>'+txtalarmname.value+'</td><td>'+a_con+"。"+a_bri+'</td><td>'+txtalarmtime.value+'</td><td>'+result.stamp+'</td><td class="hide">'+vid+'</td></tr>';
            if ($("#example6>tbody>tr>td").hasClass("dataTables_empty")){
                $("#example6>tbody>tr>td.dataTables_empty").parent().remove();
            };            
            $("#example6 tbody").prepend(row);//添加已处理警情列表
    		$('#alreadyAlarm').tab('show');//已处理警情栏显示

    		if (isRealAlarm==true) {//判断是真实警情
		        var row1 = '<tr><td>'+numberPlate+'</td><td>'+result.stamp+'</td><td class="hide">'+vid+'</td><td><a class="CrimeCardel_btn">结案</a></td><td class="hide">'+result.id+'</td></tr>';
		        $("#example7 tbody").prepend(row1);//添加案发车辆表格
		        $('#crimecar_btn').tab('show');//案发车辆栏显示
		    } else{
		        $('#alarming').tab('show');//报警栏显示
		    }
            /*下移
            $("#example4 tbody").empty();//清空列表
            $("#alarmcount").html("0");//处警过程中重复报的警要删除
            removeAlarmopt();//清空车牌、核实报警选项、简报
            if($("#autoSetfree").attr("checked")=="checked"){//自动置闲
                setTimeout(SeatIdle,2000);//2秒后自动置闲
            }
            */
        } else if(result.success==false){
            alert("处警保存失败："+result.message);
        }
        $("#example4 tbody").empty();//清空列表
        $("#alarmcount").html("0");//处警过程中重复报的警要删除
        removeAlarmopt();//清空车牌、核实报警选项、简报
        if($("#autoSetfree").attr("checked")=="checked"){//自动置闲
            setTimeout(SeatIdle,2000);//2秒后自动置闲
        }
    });       
};

//挂警保存信息
function save_hangalarm(){
    var a_sn = txtalarmsn.value;//获取页面中警情sn
    var api_url = 'app/alarm/suspend';//url
    var params = {alarmSn:a_sn};//参数：警情sn
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            //alert("数据库挂警成功！");
        } else if(result.success==false){
            alert("挂警保存失败："+result.message);
        }
    });
}

//取消挂警保存信息
function save_pickalarm(){
    var a_sn = txtalarmsn.value;//获取页面中警情sn
    var api_url = 'app/alarm/resume';//url
    var params = {alarmSn:a_sn};//参数：警情sn
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
//            alert("数据库挂警拾回成功！");
        } else if(result.success==false){
            alert(result.message);
        }
    });
}

//转警成功保存信息（被转座席执行）
function save_transferalarm(){
    var a_sn = txtalarmsn.value;//获取页面中警情sn
    var api_url = 'app/alarm/transfer';//url
    var params = {alarmSn:a_sn};//参数：警情sn
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
//            alert("数据库转警保存成功！");
        } else if(result.success==false){
            alert("数据库转警保存失败信息："+result.message);
        }
    });
}

//手动添加案发车辆 提交
function addCrimeCar(){
    var v_id = $("#unit_id").val();//获取警单内的车辆id
    var ala_sta = $("#alarm_add option:selected").val();//警情状态
    var ala_caseType = $("#caseType option:selected").val();//案件类型
    var ala_isCallPolice = $("#isCallPolice option:selected").val();//是否报警
    var api_url = 'app/alarm/vehicle/add';//url
    var params = {unitId:v_id, statusId:ala_sta, flag:0,caseType:ala_caseType,isCallPolice:ala_isCallPolice};//参数(车辆id、警情状态id、警情标记、结束时间)
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            getOutstandingVehicle();//刷新案发车辆列表
        } else if(result.success==false){
            alert("手动添加失败信息："+result.message);
        }
    });       
};

//提交添加案发车辆
$("#addConfirm").click(function(){
    addCrimeCar();
});

//结案(删除一条案发车辆)
$('.CrimeCardel_btn').live('click', function() {
    var v_id = $(this).parent().parent().children("td").eq(2).html();//获取车辆id
    var c_id = $(this).parent().parent().children("td").eq(4).html();//获取案件id
    var c_time = $(this).parent().parent().children("td").eq(1).html();//获取案发时间

    FillcasesWindow(v_id,c_id,c_time);//填充并打开结案窗口
});

//查询并填充结案弹窗
function FillcasesWindow(vid,cid,ctime){
    cleanCaseWindow()//清空所有内容的操作
    var api_url = 'app/data/basicinfo';//url
    var params = {param:vid,type:2};//参数(车辆id)
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $("#Caseregistration_vehicleid").val(vid);//填充车辆id
            $("#Caseregistration_caseid").val(cid);//填充案件编号
            $("#Casereg_name").html(result.customer.name);//姓名
            $("#Casereg_pla").html(result.vehicle.plate_no);//车牌
            $("#Casereg_callletter").html(result.unit.call_letter);//车载
            $("#Casereg_vehtype").html(result.vehicle.modelName);//车辆类型
            $("#Casereg_gpstype").html(result.unit.type);//产品型号
            $("#Casereg_filiale").html(result.unit.place);//安装地点
            $("#Casereg_stime").html(result.unit.service_date);//入网时间
            $("#caseTime").val(ctime);//案发时间
            var nowtime = SEGUtil_WS.date_2_string(new Date, "yyyy-MM-dd hh:mm:ss");//生成现在时间
            $("#closingTime").val(nowtime);//结案时间
            if(result.unit.buyTp==1){//是否含盗抢险  0:否 1:是
                $("#isInsurance").attr('checked','checked');
            }

            if(result.unit.tamperBox==1){//是否加装防拆盒  0:否 1:是
                $("#isTamperBox").attr('checked','checked');
            }
            if(result.unit.tamperSmart==1){//是否智能防拆  0:否 1:是
                $("#isSpot").attr('checked','checked');
            }
            if(result.unit.tamperWireless==1){//是否无线防拆  0:否 1:是
                $("#isWireless").attr('checked','checked');
            }

            $("#isInsurance,#isTamperBox,#isSpot,#isWireless").attr('disabled','disabled');//4个checkbox不可点
            $("#Caseregistration").modal("show");//弹窗显示
        }
    });
}

//案发车辆报警记录提交
$("#CaseSubmit").click(function(){
    var cType = getCaseType();//案件类型
    if(cType==""){
        $(".blank_mark").show();
        return;
    }
    var cid = $("#Caseregistration_caseid").val();//获取案件编号
    var vid = $("#Caseregistration_vehicleid").val();//获取车辆id
    var isCP = "";//是否报警 1:是 0:否
    if($("#isCallPolice").attr("checked")=="checked"){
        isCP = 1;//已报警
    }else{
        isCP = 0;//未报警
    }
    var bTime = $("#caseTime").val();//案发时间
    var eTime = $("#closingTime").val();//结案时间
    var isCatch = "";//是否截获 1:是 0:否
    if($("#isCatchIt").attr("checked")=="checked"){
        isCatch = 1;//已截获
    }else{
        isCatch = 0;//未截获
    }
    var api_url = 'app/alarm/vehicle/caseFinish';//url
    var params = {unitId:vid,id:cid,caseType:cType,isCallPolice:isCP,beginTime:bTime,endTime:eTime,isCapture:isCatch};//参数(车辆id、案件编号、案件类型、是否报警、案发时间、结案时间、是否截获)
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $('#Caseregistration').modal("hide");//隐藏弹窗
            //removeCase(cid)//把列表里的结案改为已结案，并不可点击
            getOutstandingVehicle(); //刷新案发车辆列表
        }else if(result.success==false){
            alert("案发车辆报警记录提交失败信息："+result.message);
        }
    });
});

/*
//把列表里的结案改为已结案，并不可点击
function removeCase(cid){
    $("#example7 tbody tr").each(function(){
        var c_id = $(this).children("td").eq(4).html();//获取案件编号
        if(c_id == cid){//案件编号匹配成功
            $(this).children("td").eq(3).html("已结案").removeClass("CrimeCardel_btn").addClass("CrimeCarClosed");
        }
    });
}
*/

//获取结案单案件类型的编号
function getCaseType(){
    var obj = document.getElementsByName("Casereg_option1");
    var temp ="";
    for(var i=0; i<obj.length; i ++){
        if(obj[i].checked){
            //alert(obj[i].value);
            temp = obj[i].value;
        }
    }
    return temp;
}

//为空提示隐藏
$("#caseType input").live('click', function(){
    $(".blank_mark").hide();
});

//清空案发车辆报警记录列表
function  cleanCaseWindow(){
    $("#Caseregistration_caseid,#Caseregistration_vehicleid").val("");//案件编号、车辆id
    $("#Casereg_name,#Casereg_pla,#Casereg_callletter,#Casereg_vehtype,#Casereg_gpstype,#Casereg_filiale,#Casereg_stime").html("");//客户姓名、车牌号、车载电话、车型、产品型号、安装地点、入网时间
    $("#caseTime,#closingTime").val("");//案发时间、结案时间 
    $("#isInsurance,#isTamperBox,#isSpot,#isWireless").removeAttr("disabled");//取消disabled
    $("#Caseregistration label input").removeAttr("checked");//去掉单选、多选
    $(".blank_mark").hide();
}

//刷新案发车辆列表
function getOutstandingVehicle(){
    $("#example7 tbody").html('');//清空
    var api_url = 'app/alarm/vehicle';//url
    var params = {};//参数无
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            var row = '';
            StolenLists = [];
            $.each(result.stolenVehicle, function(i,item){
                row = '<tr><td>'+item.plateNo+'</td><td>'+item.beginTime+'</td><td style="display:none;">'+item.unitId+'</td><td><a class="CrimeCardel_btn">结案</a></td><td style="display:none;">'+item.id+'</td></tr>';
                $("#example7 tbody").prepend(row);//添加案发车辆表格
                StolenLists.push(item.vehicleId);
            });
            // console.log(StolenLists);
            // console.log(StolenLists.length);
            return StolenLists;
        }
    });
}

//刷新警情总数列表
function updatealarmlistSum(){
    setInterval(AskAlarmList,5000);
}

/*
**警情、电话存取  数据库Ajax end
*/

/*电话检索*/
function phoneNumSearchByBox(){//搜索框搜索
    var itparam = $("#numSearch").val();
    var api_url = 'app/nl/telsearch/search';//url
    var params = {param:itparam};//参数无
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            var row = '';
            $.each(result.data, function(i,item){
                row = '<tr><td>'+item.name+'</td><td>'+item.pyname+'</td><td>'+item.sex+'</td><td>'+item.headship+'</td><td>'+item.department+'</td><td>'+item.ophone+'</td><td>'+item.mobile+'</td><td>'+item.fax+'</td><td>'+item.address+'</td><td>'+item.email+'</td></tr>';
                $("#detailInf-tab tbody").prepend(row);//写入表格
            });
        }
    });
}

$("#numsearch_btn").click(function(){
    phoneNumSearchByBox();//搜索框搜索
});

function phoneNumSearchByID(id){//通过群组id搜索
    var api_url = 'app/nl/telsearch/phonebook';//url
    var params = {groupId:id};//参数
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $("#detailInf-tab tbody").empty();//写入表格
            if(result.data!==[null]){
                var row = '';
                $.each(result.data, function(i,item){
                    row = '<tr><td>'+item.name+'</td><td>'+item.pyname+'</td><td>'+item.sex+'</td><td>'+item.headship+'</td><td>'+item.department+'</td><td>'+item.ophone+'</td><td>'+item.mobile+'</td><td>'+item.fax+'</td><td>'+item.address+'</td><td>'+item.email+'</td></tr>';
                    $("#detailInf-tab tbody").prepend(row);//写入表格
                });
            }
                
        }
    });
}

function phoneNumSearchByClick(a){//点击列表查询
    var node = $(a);//获取当前对象a节点
    $("#p_numsearch-tab tbody").empty();//清空
    var row ='';
    var firstid = '';
    if(node.siblings("ul").length!==0){//若具有兄弟节点，则有下级群组
        var ita = node.siblings("ul");//多个a
        firstid = ita[0].getElementsByTagName("span")[0].innerHTML;
        //alert(ita[0].getElementsByTagName("span")[0].innerHTML);
        ita.find("a").each(function(){
            if($(this).attr("class")=="detail"){
                row = '<tr><td>'+$(this).html()+'</td></tr>';
                $("#p_numsearch-tab tbody").append(row);
            }
        });
    } else{//若无兄弟节点，则无下级群组，直接导入本身
        firstid = node.find("span").html();
        //row = '<tr><td>'+node.html()+'</td></tr>';
        row = '<tr><td><b>'+node.find("b").html()+'</b><span class="hide">'+node.find("span").html()+'</span></td></tr>';//<tr><td><b></b><span class="hide"></span></td></tr>
        $("#p_numsearch-tab tbody").append(row);
    }
    phoneNumSearchByID(firstid)//查询第一条记录
}

$("#phone_retrieve .group_box a").live('click', function(){
    phoneNumSearchByClick(this);//点击列表查询
});

//中间框点击查询
$("#p_numsearch-tab tbody tr").live('click', function(){
    var id = $(this).find("span").html();
    phoneNumSearchByID(id);//通过id查
});

//点击背景变蓝并填充入下方格子
$("#detailInf-tab").on("click",  "tbody tr", function(event){
    selectTr(this);
    var xm = $(this).children("td").eq(0).html();//姓名
    var py = $(this).children("td").eq(1).html();//拼音
    var xb = $(this).children("td").eq(2).html();//性别
    var zw = $(this).children("td").eq(3).html();//职务
    var bm = $(this).children("td").eq(4).html();//部门
    var bgdh = $(this).children("td").eq(5).html();//办公电话
    var yddh = $(this).children("td").eq(6).html();//移动电话
    var cz = $(this).children("td").eq(7).html();//传真
    var dz = $(this).children("td").eq(8).html();//地址
    var dzyx = $(this).children("td").eq(9).html();//电子邮箱
    $("#phone_retrieve_xm").val(xm);//姓名
    $("#phone_retrieve_py").val(py);//拼音
    $("#phone_retrieve_zw").val(zw);//职务
    $("#phone_retrieve_bm").val(bm);//部门
    $("#phone_retrieve_bgdh").val(bgdh);//办公电话
    $("#phone_retrieve_cz").val(cz);//传真
    $("#phone_retrieve_xb").val(xb);//性别
    $("#phone_retrieve_yddh").val(yddh);//移动电话
    $("#phone_retrieve_dzyx").val(dzyx);//电子邮箱
    $("#phone_retrieve_dz").val(dz);//地址
});
$("#p_numsearch-tab").on("click",  "tbody tr", function(event){
    selectTr(this);
});

$("#numsearch_page_btn").click(function(){
    initNumList();//初始化电话列表
    $("#p_numsearch-tab tbody,#detailInf-tab tbody").empty();//两个列表
    $(".detailshow input").val("");//清空下方格子
});

//初始化电话列表
function initNumList(){
    var api_url = 'app/nl/telsearch/group';//url
    var params = {};//参数无
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $("#numGroupList").empty();//清空
            $.each(result.data, function(i,item){
                var row = '';
                var group1 = item.children;//数组
                if(group1.length==0){
                    row = returnleaf(item.name,item.groupId);
                } else{
                    row = returnbranch(item.name,item.groupId);
                    //var a=0;
                    $.each(group1, function(i,item){
                        var group2 = item.children;//数组
                        if(group2.length==0){
                            row += returnleaf(item.name,item.groupId);
                        } else{
                            row += returnbranch(item.name,item.groupId);
                            $.each(group2, function(i,item){
                                var group3 = item.children;//数组
                                if(group3.length==0){
                                    row += returnleaf(item.name,item.groupId);
                                } else{
                                    row += returnbranch(item.name,item.groupId);
                                    $.each(group3, function(i,item){
                                        row += returnleaf(item.name,item.groupId);
                                    });
                                    row +='</ul></li>';
                                }
                            });
                            row +='</ul></li>';
                        }
                    });
                    row +='</ul></li>';
                }
                $("#numGroupList").append(row);//写入表格
            });
        }
    });
    function returnleaf(name,groupid){
        var str = '<li><a href="#" role="leaf" class="detail"><b>'+name+'</b><span class="hide">'+groupid+'</span></a><li>';
        return str;
    }
    function returnbranch(name,groupid){
        var str = '<li><a href="#" role="branch" class="tree-toggle closed" data-toggle="branch" data-value="Bootstrap_Tree"><b>'+name+'</b><span class="hide">'+groupid+'</span></a><ul  class="branch">';
        return str;
    }
}



/*电话检索、信息检索点击背景颜色变灰*/
$(".retrieve a.tree-toggle,.retrieve a.detail").click(function(){
    $(".retrieve a.tree-toggle b,.retrieve a.detail b").css("background-color","transparent");
    $(this).find("b").css("background-color","#e8e6d9");
});

/*
** 筛选条件 end
*/

// $(function () {
//     //选中filter下的所有a标签，为其添加hover方法，该方法有两个参数，分别是鼠标移上和移开所执行的函数。
//     $("#filter a").hover(
//         function () {
//             $(this).addClass("seling");
//         },
//         function () {
//             $(this).removeClass("seling");
//         }
//     );


//     //选中filter下所有的dt标签，并且为dt标签后面的第一个dd标签下的a标签添加样式seled。(感叹jquery的强大)
//     $("#filter dt+dd a").attr("class", "seled"); /*注意：这儿应该是设置(attr)样式，而不是添加样式(addClass)，
//                                                   不然后面通过$("#filter a[class='seled']")访问不到class样式为seled的a标签。*/       

//     //为filter下的所有a标签添加单击事件
//     $("#filter a").click(function () {
//         $(this).parents("dl").children("dd").each(function () {
//             //下面三种方式效果相同（第三种写法的内部就是调用了find()函数，所以，第二、三种方法是等价的。）
//             //$(this).children("div").children("a").removeClass("seled");
//             //$(this).find("a").removeClass("seled");
//             $('a',this).removeClass("seled");
//         });

//         $(this).attr("class", "seled");

//         alert(RetSelecteds()); //返回选中结果
//     });
//     alert(RetSelecteds()); //返回选中结果
// });

// function RetSelecteds() {
//     var result = "";
//     $("#filter a[class='seled']").each(function () {
//         result += $(this).html()+"\n";
//     });
//     return result;
// }


/*
**筛选条件 end
*/

//登陆后获取信息（座席名、密码、座席号、分公司权限）
function LoginInfo(){
    $(".com_lists ul").html('');
    var api_url = 'app/user';   //url
    var params = {};    //无参数
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $("#IdCode").html(result.user.login_name);
            $("#seatname").val(result.user.login_name);
            $("#seatpwd").val(result.user.userPassword);
            $("#seatid").val(result.user.opId);

            var subselected = '<option value="0">全部</option>';//未上报统计、故障通知、催收、营销归属公司
            $.each(result.user.company, function(i,item){
                var coms = '<li><label><input type="checkbox" /><span>'+item.companyname+'</span><b style="display:none;">'+item.companyno+'</b></label></li>';
                $(".com_lists ul").append(coms);

                var sub_visitor = "<option value='"+ item.companyno +"'>"+ item.companyname +"</option>";
                $("#visitor_sub_1,#visitor_sub_2").append(sub_visitor);

                subselected += '<option value="'+ item.companyno +'">'+ item.companyname +'</option>';
            });
            $("#recentReported_sub,#locatefault_sub,#serviceCharge_sub,#marketing_sub").html(subselected);

            getCCParam();//获取通信中心配置参数
            getCallReportList();//来电简报和去电简报的选项
        }else{
            alert("座席登录失败！");
        }
    });
}

//获取通信中心配置参数（客户端id、通信中心ip:端口）
function getCCParam(){
    var api_url = 'app/alarm/config';   //url
    var params = {};    //无参数
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            host = result.hosts;//通信中心ip:端口
            Connect();//连接通信中心
        }else{
            alert("获取通信中心配置参数失败！");
        }
    });
}

//获取来电简报和去电简报的选项
function getCallReportList(){
    var api_url = 'app/servertype/query';
    $.getJSON(api_url, function(result){
        if(result && result.success==true){
            /* 来电简报 start */
            var html_callin_tit = "";
            var html_callin_list = "";
            if(result.callininfo.length > 0){
                $.each(result.callininfo,function(i,item){
                    html_callin_tit += "<label class='checkbox-inline'><input type='radio' name='callinradio' id='callinCheckbox"+ i +"' value='"+ item.st_id +"'><span>"+ item.st_name +"</span></label>";
                    if(item.childList !== null && item.childList.length > 0){
                        var children = "";
                        $.each(item.childList,function(k,k_item){
                            children += "<label class='checkbox-inline'><input type='checkbox' value='"+ k_item.st_id +"'><span>"+ k_item.st_name +"</span></label>";
                        });
                        html_callin_list += "<li style='display:none' checklist='callinCheckbox"+ i +"'>"+ children +"</li>";
                    }
                });
            }
            $("#callinTit").empty().append(html_callin_tit);
            $("#callinList").empty().append(html_callin_list);
            $("#callinTit label input").click(function(){//来电简报点击事件
                var id = $(this).attr("id");
                $("#callinList li").hide();
                $.each($("#callinList li"), function(i,item){
                    if($(item).attr("checklist") == id){
                        $(item).show();
                    }
                });
            });
            /* 来电简报 end */
            /* 去电简报 start */
            var html_callout_tit = "";
            var html_callout_list = "";
            if(result.calloutinfo.length > 0){
                $.each(result.calloutinfo,function(i,item){
                    html_callout_tit += "<label class='checkbox-inline'><input type='radio' name='calloutradio' id='calloutCheckbox"+ i +"' value='"+ item.st_id +"'><span>"+ item.st_name +"</span></label>";
                    if(item.childList !== null && item.childList.length > 0){
                        var children = "";
                        $.each(item.childList,function(k,k_item){
                            children += "<label class='checkbox-inline'><input type='checkbox' value='"+ k_item.st_id +"'><span>"+ k_item.st_name +"</span></label>";
                        });
                        html_callout_list += "<li style='display:none' checklist='calloutCheckbox"+ i +"'>"+ children +"</li>";
                    }
                });
            }
            $("#calloutTit").empty().append(html_callout_tit);
            $("#calloutList").empty().append(html_callout_list);
            $("#calloutTit label input").click(function(){//来电简报点击事件
                var id = $(this).attr("id");
                $("#calloutList li").hide();
                $.each($("#calloutList li"), function(i,item){
                    if($(item).attr("checklist") == id){
                        $(item).show();
                    }
                });
            });
            /* 去电简报 end */
        }else{
            alert("获取来电简报和去电简报选项失败！");
        }
    });
}

//电话热线设置初始化
function getCallOutNum(){
    if(defaultParam.calloutNum.length>0){
        var htmls = "";
        $.each(defaultParam.calloutNum, function(i,item){
            htmls += "<span><label><input type='radio' value='"+item.num+"' name='callOutNums'>"+item.num+"</label></span>";
        });
        $(".callOutNum_area").append(htmls);
    };
}
// function getCallOutNum(){
//     $.getJSON("js/default.json", function(result){
//         var htmls = "";
//         $.each(result.calloutNum, function(i,item){
//             htmls += "<span><label><input type='radio' value='"+item.num+"' name='callOutNums'>"+item.num+"</label></span>";
//         });
//         $(".callOutNum_area").append(htmls);
//     });
// }

function setCallOutNum(){//选中默认号码
    $.each($(".callOutNum_area span label input"), function(i,item){
        if($(item).val()==defaltNum){
            $(item).attr("checked","checked");
        }
    });
}

//保存标注
function SaveMark(){
    var add_static_marker_name = encodeURIComponent($("#add_static_marker_name").val());
    var add_static_marker_lon = $("#add_static_marker_lon").val();
    var add_static_marker_lat = $("#add_static_marker_lat").val();
    var add_static_marker_ID = $(".com_lists ul input:checked").siblings("b");
    var Com_array = new Array();
    add_static_marker_ID.each(function(){
        Com_array.push($(this).html());
    });
    //准备参数
    api_url = 'app/map/marks/add'; //推荐候选词API（常规情况测试）
    params = {name:add_static_marker_name,lon:add_static_marker_lon,lat:add_static_marker_lat,companyNo:Com_array};
    var params_t = decodeURIComponent($.param(params,true));
    
    //开始调用接口
    $.getJSON(api_url+"?"+params_t, function(result){
        if(result && result.success==true){
            $('#add_static_marker_config_dlg').modal('hide');
            var M_id = "M"+result.id;   
            var name = $("#add_static_marker_name").val();
            var lon = $("#add_static_marker_lon").val();
            var lat = $("#add_static_marker_lat").val();
            addStaticMarkerBySerPop(name,lon,lat,M_id);  
            $("#selectAll input").removeAttr("checked");
            $(".com_lists ul input").removeAttr("checked");

        }else{
            alert(result.message);    
        }
    });
}

//加载标注列表
function ListMark(){
    //准备参数
    api_url = 'app/map/marks';   //url
    params = {};    //参数
    
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $.each(result.mark, function(i,item){
                // $("#mark_tableList tbody").append('<tr class="" role="row"><td>'+item.name+'</td><td>'+item.lon+'</td><td>'+item.lat+'</td><td><i class="glyphicon glyphicon-trash cursor popremove" title="删除"></i><i class="glyphicon glyphicon-hand-up cursor pop_points" title="点选"></i><i class="glyphicon glyphicon-screenshot cursor popCenters" title="居中"></i></td><td><input style="display:none;" value="'+item.id+'" /></td></tr>');
                $("#mark_tableList tbody").append('<tr class="" role="row"><td>'+item.name+'</td><td>'+item.lon+'</td><td>'+item.lat+'</td><td><i class="glyphicon glyphicon-trash cursor popremove" title="删除"></i></td><td><input type="text" class="Mark_id" style="display:none;" value="'+item.id+'" /><input type="text" class="M_id" style="display:none;" value="M'+item.id+'" /></td></tr>');
            });
        }else{
            alert(result.message);    
        }
    });
}

//删除标注
function RemoveMark(Mark_id,faNode){
    //准备参数
    api_url = 'app/map/marks/del';   //url
    params = {id:Mark_id};    //参数
    
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        if(result && result.success==false){
            alert(result.message);    
        }else{
        faNode.remove();
        var id = faNode.children("td").find(".M_id").val();
        map.removeMarkerID(id);
        }
    });
}



//显示标注到地图
function Mark_map(){
    var Mark_array = new Array();
    Mark_array = $("#mark_tableList tbody").find("tr");
    $.each(Mark_array, function(i){
        name = $(this).children("td").eq(0).html();
        lon = $(this).children("td").eq(1).html();
        lat = $(this).children("td").eq(2).html();
        id = $(this).find(".M_id").val();
        var m = map.newSimpleMarker(lon, lat, name, null, id);
        map.addMarker(m, 1);
    });
}


//手机号归属地查询

function PhoneOwnership(Tel_num,Tel_this){
    $.ajax({
        type: "get",
        async: false,
        data: {
            "m":Tel_num,
            "output":"json"
        },
        url: "http://192.110.1.174:8087/mcontact/region",
        dataType: "jsonp",
        jsonp: "callback",
        success: function(json) {
            Tel_change = '';
            if(json.AreaCode!=''&&json.AreaCode!='0755'&&Tel_num.substr(0,1)!=='0'){
                Tel_change = '0'+Tel_num;
            }else{
                Tel_change = Tel_num;
            };

            switch(Tel_this)
            {
                case "phonebook-tab":
                    $("#phonebar_change_out_tel").val(Tel_change);
                    $("#Phone_links").html(json.City);
                    break;
                case "phoneLinkList":
                    $("#phonebar_call_out_tel").val(Tel_change);
                    $("#phonebar_call_out_real").val(Tel_num);
                    $("#Phone_dailing").html(json.City);
                    break;
                case "phoneLinkList_four":
                    $("#phonebar_call_out_tel_four").val(Tel_change);
                    $("#phonebar_call_out_real_four").val(Tel_num);
                    $("#Phone_dailing_four").html(json.City);
                    break;
            };      

        },
        error: function(json) {
            alert("获取归属地失败");
        }
    });
}



//日期格式转换
var DateFinal;
function DateTranslate(DateOrigin){
    var DateMonth;
    var DateDay;
    var DateHours;
    var DateMinutes;
    var DateSeconds;

    //月
    if ((DateOrigin.getMonth()+1)<10){
        DateMonth = '0'+(DateOrigin.getMonth()+1);
    }else{
        DateMonth =DateOrigin.getMonth()+1;
    };
    //日
    if (DateOrigin.getDate()<10){
        DateDay = '0'+DateOrigin.getDate();
    }else{
        DateDay =DateOrigin.getDate();
    };
    //时
    if (DateOrigin.getHours()<10){
        DateHours = '0'+DateOrigin.getHours();
    }else{
        DateHours =DateOrigin.getHours();
    };
    //分
    if (DateOrigin.getMinutes()<10){
        DateMinutes = '0'+DateOrigin.getMinutes();
    }else{
        DateMinutes = DateOrigin.getMinutes();
    };
    //秒
    if (DateOrigin.getSeconds()<10){
        DateSeconds = '0'+DateOrigin.getSeconds();
    }else{
        DateSeconds = DateOrigin.getSeconds();
    };
    DateFinal =DateOrigin.getFullYear()+ "-"+DateMonth+"-"+DateDay+" "+DateHours+":"+DateMinutes+":"+DateSeconds;
    return DateFinal;
}



//获取4s或车厂信息
function FourFactory(){
    var FoursId = $("#FourS_id").val();
    //准备参数
    api_url = 'app/data/4sshop';   //url
    params = {s4Id:FoursId};    //参数
    
    //开始调用接口
    $.getJSON(api_url, params, function(result){
        $("#S_phone").removeAttr("data-target","#FourCall");
        $("#F_phone").removeAttr("data-target","#FourCall");
        if(result && result.success){
        	if(result.data.phone && result.data.phone.trim() != ""){
        	    var repair_phones = result.data.phone.split(",");
        	    for(var i=0;i<repair_phones.length;i++){
        	    	$("#S_repair").append('<tr class="Repair"><td>维修电话'+(i+1)+':</td><td>'+repair_phones[i]+'</td></tr>');
        	    }
                
        	}
        	if(result.data.phone2 && result.data.phone2.trim() != ""){
        	    var order_phones = result.data.phone2.split(",")[0];
        	    for(var i=0;i<order_phones.length;i++){
        	    	$("#S_order").append('<tr class="OrderF"><td>预约电话'+(i+1)+':</td><td>'+order_phones[i]+'</td></tr>');
        	    }
                
        	}
            if (result.data.s4Shop){
                $("#S_shop").val(result.data.companyName);
                $("#S_address").val(result.data.address);
                $("#4sS_phone").val(result.data.phone);
                $("#S_phone").attr("data-target","#FourCall");
            }else{
                $("#F_company").val(result.data.companyName);
                $("#F_address").val(result.data.address);
                $("#4sF_phone").val(result.data.phone);
                $("#F_phone").attr("data-target","#FourCall");
            };
        };
    });
};

// 坐席退录
function logout() {
	// $(document).sgConfirm({
	// 	title:'提示',
	// 	text: '确认退出系统?',
	// 	cfn:function(r){
	// 		if(r){

	location.href = "logout.jsp";
		// 	}
		// }});
}

$("#loginout").click(function(){
	LogoutCC();//退出通信中心
    logout();// 坐席退录
});


// GPS信息新增删除判断
function GPS_judge(GPS_back_number){
    var NumberPlateList=new Array();
    $("#gps_table tbody tr").each(function(){
        NumberPlateList.push($(this).children("td:eq(0)"));
    });
    for(i=0;i<NumberPlateList.length;i++)
      {
        // if(NumberPlateList[i].innerHTML==GPS_back_number) 
        if(NumberPlateList[i].html()==GPS_back_number) 
        {
            NumberPlateList[i].parent().remove();
            break;  
        }
    };
};

// 已处理警情列表新增删除判断
function ProcessedAlarm_judge(pla){//车牌
    var pla_array = $("#example6 tbody tr");
    //alert("总数"+pla_array.length);
    for(i=0;i<pla_array.length;i++)
      {
        //alert(pla_array[i].getElementsByTagName("td")[0].innerHTML);
        if(pla_array[i].getElementsByTagName("td")[0].innerHTML==pla) 
        {
            $(pla_array[i]).remove();
            break;  
        }
    };
};

// 警情分至删除判断
function Alarm_judge(calleter,alarmid){//呼号、警情id
    var Alarm_array = $("#example4 tbody tr");
    for(i=0;i<Alarm_array.length;i++)
      {
        if(Alarm_array[i].getElementsByTagName("td")[3].innerHTML==calleter)//呼号同
        {
            if(Alarm_array[i].getElementsByTagName("td")[9].innerHTML==alarmid){//警情id同
                //alert("呼号："+calleter+" 警情id："+alarmid);
                return 1;//列表有相同的警情，不分配
            }
        }
    };
};

/**
**当前系统时间 start
**/

//当前系统时间
function ReDate(){
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
    return NowTime;
}

/**
**当前系统时间 end
**/


// 通过车载号码查询车牌号码

function LetterPlate(CarLetter){
    keyword = CarLetter;
    if(keyword=='') return false;
    $.ajax({
        type : 'get',
        async : false,
        contentType : 'application/json',
        dataType : 'json',
        url : 'app/data/vehicleinfo',
        data :{param:keyword,count:5},
        success : function(data) {
            //如果只有一个且精确匹配，则自动搜索，不再出现候选项
            if(data.info.length==1 &&data.info[0].call_letter==keyword){
              GPS_Plate = data.info[0].plate_no;
            }else{
                GPS_Plate='';
            };
        },
        error : function(res, error) {
            alert(data.message);
        }
    })
    return GPS_Plate;
}

/* 警情跟踪 */
$("#AlarmTrace_btn").click(function(){
    $("#AlarmTrace").modal("show");
    var alarmsn = $("#example13 tr.row_selected_bc").children("td").eq(9).html();//sn
    $("#follow_alarmsn").val(alarmsn);//赋值到弹窗
});

$("#callout_false_btn").click(function(){//外呼失败
    $("#callout_false_time").val(ReDate());
    var num = $("#phonebar_call_out_real").val();//获取原始号码
    $("#callout_false_num").val(num);
    $("#callOut").modal("hide");
    $("#callout_false").modal("show");
});

$("#callout_false_submit").click(function(){//外呼失败弹窗提交
    var time = $("#callout_false_time").val();
    var num = $("#callout_false_num").val();
    var content = $('input[name="callout_false1"]:checked').val();
    var flag = $('input[name="callout_false2"]:checked').val();
    var flag_title = encodeURIComponent($('input[name="callout_false2"]:checked').siblings("span").html());//文字
    if(content == "" || content == undefined){
        alert("请选择服务内容！");
        return;
    } else if(flag == "" || flag == undefined){
        alert("请选择接通情况！");
        return;
    }
    save_telreport_fail($("#vehicle_id").val(),2,num,content,flag,flag_title);// 车辆id、呼出2、号码、服务项[]、结果、服务大类
    // alert("time:"+time+" num:"+num+" content:"+content+" flag:"+flag);
});

$("#set_msn_btn").click(function(){//发短信
    $("#msn_phonenum").val($("#b_phone").val());//号码
    $("#msnEditBox").val("");//编辑框清空
    $("#sentMSN").modal("show");
    getSmsList();//获取短信列表
});

//清空框
function cleanMsnList(){
	var st_types = [1000,2000,3000,4000,5000];
    $.each(st_types,function(i,item){
    	$("#msnlist_"+item).empty();//框
    });
}

function getSmsList(){//获取短信列表
    // $("#msnlistTab").empty();//标题清空
    // $("#msnlistTabContent").empty();//内容清空
	cleanMsnList();//清空内容框
    var api_url = 'app/sms_temp/list';
    $.getJSON(api_url,function(result){//1000~5000
        if(result.success==true){
            var st_type = "";//大类id
            var stCode = "";//小类id
            var sms = "";//内容
            var msnlist = "";//短信类别框
            if(result.data.length>0){//不为空，则循环取值
                $.each(result.data,function(i,item){
                    st_type = item.stType;//大类
                    msnlist = $("#msnlist_"+st_type);//框
                    if(item.sms.length>0){
                        $.each(item.sms,function(k,v){
                            msnlist.append('<label sC="' + v.stCode + '" pCt="'+ v.PCount +'"><input type="radio" name="msnlist">' + v.sms + '</label>');
                        });
                    };
                    
                });
            };

            /* 绑定点击事件 st */
            $("#msnlistTabContent label").click(function(){//短信选择
                $("#msnEditBox").html("").attr("stCode",$(this).attr("sC"));//编辑框先清空

                var customer_name = $("#b_user").val();//客户名
                var callLetter = $("#b_carPhone").val();//呼号
                var pla = $("#b_gsm").val();//车牌

                var str = $(this).text();//取短信模板
                var re_n=new RegExp("%C","g");//客户名
                var re_c=new RegExp("%M","g");//呼号
                var re_p=new RegExp("%V","g");//车牌号

                var re_input=new RegExp("%P","g");//自定义内容

                str = str.replace(re_n,customer_name).replace(re_c,callLetter).replace(re_p,pla).replace(re_input,"<span style='width:50px;display:inline-block;height:26px;vertical-align:middle;'><input type='text' class='form-control'></span>");
                $("#msnEditBox").html(str);//
            });
            /* 绑定点击事件 nd */
        };
    });
}

/* 警情历史记录 警情跟踪 */
$("#example13").on("click",  "tbody tr", function(event){
    selectTr(this);
    // console.log($(this).find("td").length);
    if($(this).find("td").length==1 || $(this).children("td").eq(8).html()==1){//无警情记录||已结束跟踪
        $("#AlarmTrace_btn").attr("disabled","disabled");//按钮不可用
    } else{
        $("#AlarmTrace_btn").removeAttr("disabled");//按钮可用
    }
});

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

function save_follow_res(flag){//保存警情跟踪
    var sn = $("#follow_alarmsn").val();//隐藏域sn
    var phone = $("#follow_phone").val();//号码
    var res = getcomtent("follow_res");

    var api_url = 'app/alarm/log';
    var params = {alarmSn:sn,log_phone:phone,log_flag:flag,log_res:res};//无参数
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $("#AlarmTrace").modal("hide");
            Update_alarmhistroy();//刷新警情历史记录
            $("#follow_phone").val("");//号码
        }
    });
}

$("#follow_submit").click(function(){//提交本次跟踪记录
    save_follow_res(0);
});

$("#follow_ending").click(function(){//结束跟踪
    save_follow_res(1);
});

$("#msn_clean").click(function(){//清空
    $("#msnEditBox").html("").attr("stCode","");
});

$("#msn_submit").click(function(){//发送
    if($("#msn_phonenum").val() == ""){
        $("#msn_phonenum").focus();
        return;
    };

    if($("#msnEditBox").html() == ""){
        alert("请选择一条短信模板");
        return;
    };

    var content = "";
    if($("#msnEditBox input").length>0){//包含自定义框
        var isSubmit = 1;//1信息完整可提交 0信息缺漏不可提交
        $.each($("#msnEditBox input"),function(i,item){//参数检测
            if(item.value==""){
                isSubmit = 0;
                item.focus();
                return false;
            };
        });
        if(isSubmit == 1){//可提交
            var zhengwen = $('input:radio[name="msnlist"]:checked').parent().text();
            var array = zhengwen.split("%P");//所有的分割字符数组
            var input_p = $("#msnEditBox input");//所有input
            content = "";//正文
            for(k=0;k<array.length;k++){//以正文分割字符数组为循环，input数会少1
                if(input_p.eq(k).val() == undefined){//最末一个为undefined
                    content += array[k];
                }else{
                    content += array[k]+ input_p.eq(k).val();//完整拼接
                };
            }

            var re_n=new RegExp("%C","g");//客户名
            var re_c=new RegExp("%M","g");//呼号
            var re_p=new RegExp("%V","g");//车牌号
            content = content.replace(re_n,$("#b_user").val()).replace(re_c,$("#b_carPhone").val()).replace(re_p,$("#b_gsm").val());//客户名、呼号、车牌替换
        }else{
            return;//缺漏返回
        };
    } else{//不含自定义框
        content = $("#msnEditBox").html();//正文
    };

    // sentSMS_success($("#msn_phonenum").val(),content);//sms成功浮窗显示

    api_url = 'app/sms/send/';
    params = {op_name:$("#seatname").val(),op_src:1,mobile:$("#msn_phonenum").val(),content:content,st_code:$("#msnEditBox").attr("stCode")};
    //开始调用接口 
    $.post(api_url, params, function(result){
        result = eval('(' + result + ')');
        if(result.returnstatus=="Success"){
            $("#sentMSN").modal("hide");
            sentSMS_success($("#msn_phonenum").val(),content);//sms成功浮窗显示
        }else{
            alert("短信发送失败："+result.message);
        }
    });
});

/* 产品库 */
$("#ProductLibrary_btn").click(function(){
    var api_url = 'app/nl/productlib';
    var params = {};//无参数
    $.getJSON(api_url, params, function(result){
        if(result && result.success==true){
            $("#PLInf-tab tbody").empty();
            if(result.data.length>0){
                $.each(result.data,function(i,item){
                    var htmls = "<tr><td>"+ item.unitType +"</td><td>"+ item.classType +"</td><td>" + item.cname + "</td><td>"+ item.memo +"</td></tr>";
                    $("#PLInf-tab tbody").append(htmls);
                });
            }

            $("#prodetail_xh,#prodetail_lx,#prodetail_mc").val("");
            $("#prodetail_gn").html("");
        }
    });
});

$("#PLInf-tab").on("click","tbody tr",function(event){
    selectTr(this);
    var tr = $(this);
    $("#prodetail_xh").val(tr.children("td").eq(0).html());
    $("#prodetail_lx").val(tr.children().eq(1).html());
    $("#prodetail_mc").val(tr.children().eq(2).html());
    $("#prodetail_gn").html(tr.children().eq(3).html());
});

function sentSMS_success(num,content){//sms成功浮窗显示
    $("#sms_callnum_w").html(num);
    $("#sms_content_w").html(content);
    $("#sms_win_bottom").fadeIn("slow");//出现
    setTimeout(function(){
        $("#sms_win_bottom").fadeOut("slow");//消失
    },5000);
}