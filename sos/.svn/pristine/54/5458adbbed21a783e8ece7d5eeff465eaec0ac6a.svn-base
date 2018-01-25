$(function(){
 bindInputEvent($("#searchnumber"),function(value){
  if (!value) {
   Util.showPopover("#searchnumber","请输入车牌号");
   return false;
  }
  if (value.length < 3) {
   Util.showPopover("#searchnumber","请输入三个以上字符");
   return false;
  }
  return true;
 });
});

function bindInputEvent(input,checkFun) {
 input.keypress(function(e) {
  if (e.keyCode != 13) {
   return;
  }
  var _value = input.val();
  /* 校验 */
  if (!checkFun(_value)){return;}
  /* input中的内容未发生改变，且下拉框被隐藏，直接显示上次搜索的结果 */
  if ($("#vehicles_ul").attr("target") == input.selector && $("#vehicles_ul").attr("search") == _value) {
   if ($("#vehicles_ul").is(':hidden')) {
    $("#vehicles_ul").stop(true).fadeIn();
    return;
   }
   getCustInfo();
   return;
  }
  var data = {
   pageNo: 1,
   pageSize: 10
  };
  var parameter = {
   type : "post",
   contentType : 'application/json',
   async : true,
   dataType : "json",
   success : function(vehicles){
    initVehiclesSelection(input.selector,vehicles);
   },
   error : function() {
    Util.showMsg("未找到记录");
   }
  };

  /* 设置不同的参数 */
  switch (input.selector) {
   case "#searchnumber" :
   data.filter = {plate_no : _value};
   parameter.url = "/sos/getVehicles";
   break;
  }
  parameter.data = JSON.stringify(data);
  $.ajax(parameter);

 });

 input.keyup(function(e){
  selectNextVehicles(e.keyCode);
 });
}


/* 开始搜索 */
function getCustInfo(){
 var p = $("#vehicles_ul>.active");
 Util.showMsg(p.attr("search"));
 $("#vehicles_ul").fadeOut();
}

