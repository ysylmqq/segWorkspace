function initDetail(){
 $("wo_detail")
}

$(function(){
 /* 点击右键出现下拉菜单 */
 $("#w_tree").on("contextmenu", ".leaf", function(e) {
  var _top = e.clientY + $(document).scrollTop();
  if (_top + $("#w_tree").scrollTop() + $("#wlist_contextmenu").height() > $(window).height()) {
   _top = $(window).height() - $("#wlist_contextmenu").height() - $("#w_tree").scrollTop();
  }
  $("#wlist_contextmenu").offset({
   left : e.clientX,
   top : _top
  });
  $(this).addClass("selected");
  $("#wlist_contextmenu_link").dropdown('toggle');
  return false;
 }).on("click", ".leaf", function(e) {
  if(!e.ctrlKey){
   $("#w_tree .leaf.selected").removeClass("selected");
  }
  if(e.shiftKey) {

  }
  $(this).toggleClass("selected");
 }).on("selectstart", ".leaf", function(e) {
  return false;
 }).on("click", ".list-group-item", function(e){
  var ws = $(this).find(".leaf");
  if (ws.not(".selected").length == 0) {
   ws.removeClass("selected");
  } else {
   ws.addClass("selected");
   ws.parent().slideDown().siblings(".glyphicon-chevron-right").removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-up");
  }
  $("#wlist_contextmenu_link").dropdown('hidden');
  e.stopPropagation();
  e.preventDefault();
  return false;
 });
 $('#wlist_contextmenu').on('show.bs.dropdown', function () {
  $("#pg,#rwd").removeClass("disabled");
  if ($("#w_tree .leaf.selected").length != 1) {
   $("#pg,#rwd").addClass("disabled");
  }
 });

 /* 派工 */
 var pg_func = function(e){
  var ws = $("#w_tree .leaf.selected");
  if (ws.length != 1) {return;}
  $("#wman").val(ws.attr("wman"));
  $("#wo_detail").fadeIn();
 };
 $("#pg").click(pg_func);
 $("#w_tree").on("dblclick", ".leaf", function(e){
  $("#w_tree .leaf.selected").removeClass("selected");
  $(this).addClass("selected");
  pg_func(e);
 });

 /* 某个电工任务列表 */
 $("#rwd").click(function(e){
  var ws = $("#w_tree .leaf.selected");
  if (ws.length != 1) {return;}
  $("#wolist_temp_wname").text(ws.attr("wman"));
  $('#wolist_temp_link').tab('show');
 });

 /* 设置值班 */
 $("#zb").click(function(e){
  var ws = $("#w_tree .leaf.selected");
  if (ws.length == 0) {return;}
  Util.showMsg("设置成功");
  ws.find("u").css("color", "green");
 });

 /* 设置休息 */
 $("#xx").click(function(e){
  var ws = $("#w_tree .leaf.selected");
  if (ws.length == 0) {return;}
  Util.showMsg("设置成功");
  ws.find("u").css("color", "gray");
 });

 $("#wo_detail_plate").keypress(function(e){
  if (e.keyCode != 13) {
   e.stopPropagation();
   e.preventDefault();
   return;
  }
  /* 发送ajax */
  Util.showList("#wo_detail_plate",[{a:1,b:2},{a:2,b:3}],"b",function(p){
   // alert(p.b);
  });
 });

 /* 点击任务单 */
 $("#wowaitting").on("click", "span", function(e){
  $(this).siblings().removeClass("selected");
  $(this).addClass("selected");
  $("#wo_detail").fadeIn();
  // document.getElementById("urgency").play();
  // document.getElementById("urgency").pause();
  Util.showNotification("msg");
 });

 /* 设置为可以拖动 */
 $("#wo_detail>.panel-heading").mousedown(function(e){
  $("#wo_detail").attr("startX", e.clientX);
  $("#wo_detail").attr("startY", e.clientY);
  document.body.unselectable = "on";
  document.body.style = "-moz-user-select: none; -webkit-user-select: none;";
  document.body.onselectstart = function() {
   return false;
  };
  $(window).one("mouseup",function(e){
   $("#wo_detail").attr("startX", null);
   $("#wo_detail").attr("startY", null);
   document.body.unselectable = "";
   document.body.style = "-moz-user-select: ; -webkit-user-select: ;";
   document.body.onselectstart = null;
  });
 });
 $(window).mousemove(function(e){
  var _x = $("#wo_detail").attr("startX");
  var _y = $("#wo_detail").attr("startY");
  if (!_x || !_y) {return;}
  $("#wo_detail").offset({
   left:e.clientX - _x + $("#wo_detail").offset().left,
   top:e.clientY - _y + $("#wo_detail").offset().top
  });
  $("#wo_detail").attr("startX", e.clientX);
  $("#wo_detail").attr("startY", e.clientY);
 });

});