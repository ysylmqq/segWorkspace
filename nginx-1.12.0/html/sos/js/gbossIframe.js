/**
 * from
 * jQuery EasyUI 1.3.6
 * part of this.
 */
(function($){
$.parser={auto:true,onComplete:function(_1){
},plugins:["draggable","droppable","resizable","pagination","tooltip","linkbutton","menu","menubutton","splitbutton","progressbar","tree","combobox","combotree","combogrid","numberbox","validatebox","searchbox","numberspinner","timespinner","calendar","datebox","datetimebox","slider","layout","panel","datagrid","propertygrid","treegrid","tabs","accordion","window","dialog"],parse:function(_2){
var aa=[];
for(var i=0;i<$.parser.plugins.length;i++){
var _3=$.parser.plugins[i];
var r=$(".easyui-"+_3,_2);
if(r.length){
if(r[_3]){
r[_3]();
}else{
aa.push({name:_3,jq:r});
}
}
}
if(aa.length&&window.easyloader){
var _4=[];
for(var i=0;i<aa.length;i++){
_4.push(aa[i].name);
}
easyloader.load(_4,function(){
for(var i=0;i<aa.length;i++){
var _5=aa[i].name;
var jq=aa[i].jq;
jq[_5]();
}
$.parser.onComplete.call($.parser,_2);
});
}else{
$.parser.onComplete.call($.parser,_2);
}
},parseOptions:function(_6,_7){
var t=$(_6);
var _8={};
var s=$.trim(t.attr("data-options"));
if(s){
if(s.substring(0,1)!="{"){
s="{"+s+"}";
}
_8=(new Function("return "+s))();
}
if(_7){
var _9={};
for(var i=0;i<_7.length;i++){
var pp=_7[i];
if(typeof pp=="string"){
if(pp=="width"||pp=="height"||pp=="left"||pp=="top"){
_9[pp]=parseInt(_6.style[pp])||undefined;
}else{
_9[pp]=t.attr(pp);
}
}else{
for(var _a in pp){
var _b=pp[_a];
if(_b=="boolean"){
_9[_a]=t.attr(_a)?(t.attr(_a)=="true"):undefined;
}else{
if(_b=="number"){
_9[_a]=t.attr(_a)=="0"?0:parseFloat(t.attr(_a))||undefined;
}
}
}
}
}
$.extend(_8,_9);
}
return _8;
}};
$(function(){
var d=$("<div style=\"position:absolute;top:-1000px;width:100px;height:100px;padding:5px\"></div>").appendTo("body");
d.width(100);
$._boxModel=parseInt(d.width())==100;
d.remove();
if(!window.easyloader&&$.parser.auto){
$.parser.parse();
}
});
$.fn._outerWidth=function(_c){
if(_c==undefined){
if(this[0]==window){
return this.width()||document.body.clientWidth;
}
return this.outerWidth()||0;
}
return this.each(function(){
if($._boxModel){
$(this).width(_c-($(this).outerWidth()-$(this).width()));
}else{
$(this).width(_c);
}
});
};
$.fn._outerHeight=function(_d){
if(_d==undefined){
if(this[0]==window){
return this.height()||document.body.clientHeight;
}
return this.outerHeight()||0;
}
return this.each(function(){
if($._boxModel){
$(this).height(_d-($(this).outerHeight()-$(this).height()));
}else{
$(this).height(_d);
}
});
};
$.fn._scrollLeft=function(_e){
if(_e==undefined){
return this.scrollLeft();
}else{
return this.each(function(){
$(this).scrollLeft(_e);
});
}
};
$.fn._propAttr=$.fn.prop||$.fn.attr;
$.fn._fit=function(_f){
_f=_f==undefined?true:_f;
var t=this[0];
var p=(t.tagName=="BODY"?t:this.parent()[0]);
var _10=p.fcount||0;
if(_f){
if(!t.fitted){
t.fitted=true;
p.fcount=_10+1;
$(p).addClass("panel-noscroll");
if(p.tagName=="BODY"){
$("html").addClass("panel-fit");
}
}
}else{
if(t.fitted){
t.fitted=false;
p.fcount=_10-1;
if(p.fcount==0){
$(p).removeClass("panel-noscroll");
if(p.tagName=="BODY"){
$("html").removeClass("panel-fit");
}
}
}
}
return {width:$(p).width(),height:$(p).height()};
};
})(jQuery);
(function($){
$.fn._remove=function(){
return this.each(function(){
$(this).remove();
try{
this.outerHTML="";
}
catch(err){
}
});
};
function _1e2(node){
node._remove();
};
function _1e3(_1e4,_1e5){
var opts=$.data(_1e4,"panel").options;
var _1e6=$.data(_1e4,"panel").panel;
var _1e7=_1e6.children("div.panel-header");
var _1e8=_1e6.children("div.panel-body");
if(_1e5){
$.extend(opts,{width:_1e5.width,height:_1e5.height,left:_1e5.left,top:_1e5.top});
}
opts.fit?$.extend(opts,_1e6._fit()):_1e6._fit(false);
_1e6.css({left:opts.left,top:opts.top});
if(!isNaN(opts.width)){
_1e6._outerWidth(opts.width);
}else{
_1e6.width("auto");
}
_1e7.add(_1e8)._outerWidth(_1e6.width());
if(!isNaN(opts.height)){
_1e6._outerHeight(opts.height);
_1e8._outerHeight(_1e6.height()-_1e7._outerHeight());
}else{
_1e8.height("auto");
}
_1e6.css("height","");
opts.onResize.apply(_1e4,[opts.width,opts.height]);
$(_1e4).find(">div:visible,>form>div:visible").triggerHandler("_resize");
};
function _1e9(_1ea,_1eb){
var opts=$.data(_1ea,"panel").options;
var _1ec=$.data(_1ea,"panel").panel;
if(_1eb){
if(_1eb.left!=null){
opts.left=_1eb.left;
}
if(_1eb.top!=null){
opts.top=_1eb.top;
}
}
_1ec.css({left:opts.left,top:opts.top});
opts.onMove.apply(_1ea,[opts.left,opts.top]);
};
function _1ed(_1ee){
$(_1ee).addClass("panel-body");
var _1ef=$("<div class=\"panel\"></div>").insertBefore(_1ee);
_1ef[0].appendChild(_1ee);
_1ef.bind("_resize",function(){
var opts=$.data(_1ee,"panel").options;
if(opts.fit==true){
_1e3(_1ee);
}
return false;
});
return _1ef;
};
function _1f0(_1f1){
var opts=$.data(_1f1,"panel").options;
var _1f2=$.data(_1f1,"panel").panel;
if(opts.tools&&typeof opts.tools=="string"){
_1f2.find(">div.panel-header>div.panel-tool .panel-tool-a").appendTo(opts.tools);
}
_1e2(_1f2.children("div.panel-header"));
if(opts.title&&!opts.noheader){
var _1f3=$("<div class=\"panel-header\"><div class=\"panel-title\">"+opts.title+"</div></div>").prependTo(_1f2);
if(opts.iconCls){
_1f3.find(".panel-title").addClass("panel-with-icon");
$("<div class=\"panel-icon\"></div>").addClass(opts.iconCls).appendTo(_1f3);
}
var tool=$("<div class=\"panel-tool\"></div>").appendTo(_1f3);
tool.bind("click",function(e){
e.stopPropagation();
});
if(opts.tools){
if($.isArray(opts.tools)){
for(var i=0;i<opts.tools.length;i++){
var t=$("<a href=\"javascript:void(0)\"></a>").addClass(opts.tools[i].iconCls).appendTo(tool);
if(opts.tools[i].handler){
t.bind("click",eval(opts.tools[i].handler));
}
}
}else{
$(opts.tools).children().each(function(){
$(this).addClass($(this).attr("iconCls")).addClass("panel-tool-a").appendTo(tool);
});
}
}
if(opts.collapsible){
$("<a class=\"panel-tool-collapse\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click",function(){
if(opts.collapsed==true){
_210(_1f1,true);
}else{
_205(_1f1,true);
}
return false;
});
}
if(opts.minimizable){
$("<a class=\"panel-tool-min\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click",function(){
_216(_1f1);
return false;
});
}
if(opts.maximizable){
$("<a class=\"panel-tool-max\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click",function(){
if(opts.maximized==true){
_219(_1f1);
}else{
_204(_1f1);
}
return false;
});
}
if(opts.closable){
$("<a class=\"panel-tool-close\" href=\"javascript:void(0)\"></a>").appendTo(tool).bind("click",function(){
_1f4(_1f1);
return false;
});
}
_1f2.children("div.panel-body").removeClass("panel-body-noheader");
}else{
_1f2.children("div.panel-body").addClass("panel-body-noheader");
}
};
function _1f5(_1f6,_1f7){
var _1f8=$.data(_1f6,"panel");
var opts=_1f8.options;
if(_1f9){
opts.queryParams=_1f7;
}
if(opts.href){
if(!_1f8.isLoaded||!opts.cache){
var _1f9=$.extend({},opts.queryParams);
if(opts.onBeforeLoad.call(_1f6,_1f9)==false){
return;
}
_1f8.isLoaded=false;
_1fa(_1f6);
if(opts.loadingMessage){
$(_1f6).html($("<div class=\"panel-loading\"></div>").html(opts.loadingMessage));
}
opts.loader.call(_1f6,_1f9,function(data){
_1fb(opts.extractor.call(_1f6,data));
opts.onLoad.apply(_1f6,arguments);
_1f8.isLoaded=true;
},function(){
opts.onLoadError.apply(_1f6,arguments);
});
}
}else{
if(opts.content){
if(!_1f8.isLoaded){
_1fa(_1f6);
_1fb(opts.content);
_1f8.isLoaded=true;
}
}
}
function _1fb(_1fc){
$(_1f6).html(_1fc);
$.parser.parse($(_1f6));
};
};
function _1fa(_1fd){
var t=$(_1fd);
t.find(".combo-f").each(function(){
$(this).combo("destroy");
});
t.find(".m-btn").each(function(){
$(this).menubutton("destroy");
});
t.find(".s-btn").each(function(){
$(this).splitbutton("destroy");
});
t.find(".tooltip-f").each(function(){
$(this).tooltip("destroy");
});
t.children("div").each(function(){
$(this)._fit(false);
});
};
function _1fe(_1ff){
$(_1ff).find("div.panel:visible,div.accordion:visible,div.tabs-container:visible,div.layout:visible").each(function(){
$(this).triggerHandler("_resize",[true]);
});
};
function _200(_201,_202){
var opts=$.data(_201,"panel").options;
var _203=$.data(_201,"panel").panel;
if(_202!=true){
if(opts.onBeforeOpen.call(_201)==false){
return;
}
}
_203.show();
opts.closed=false;
opts.minimized=false;
var tool=_203.children("div.panel-header").find("a.panel-tool-restore");
if(tool.length){
opts.maximized=true;
}
opts.onOpen.call(_201);
if(opts.maximized==true){
opts.maximized=false;
_204(_201);
}
if(opts.collapsed==true){
opts.collapsed=false;
_205(_201);
}
if(!opts.collapsed){
_1f5(_201);
_1fe(_201);
}
};
function _1f4(_206,_207){
var opts=$.data(_206,"panel").options;
var _208=$.data(_206,"panel").panel;
if(_207!=true){
if(opts.onBeforeClose.call(_206)==false){
return;
}
}
_208._fit(false);
_208.hide();
opts.closed=true;
opts.onClose.call(_206);
};
function _209(_20a,_20b){
var opts=$.data(_20a,"panel").options;
var _20c=$.data(_20a,"panel").panel;
if(_20b!=true){
if(opts.onBeforeDestroy.call(_20a)==false){
return;
}
}
_1fa(_20a);
_1e2(_20c);
opts.onDestroy.call(_20a);
};
function _205(_20d,_20e){
var opts=$.data(_20d,"panel").options;
var _20f=$.data(_20d,"panel").panel;
var body=_20f.children("div.panel-body");
var tool=_20f.children("div.panel-header").find("a.panel-tool-collapse");
if(opts.collapsed==true){
return;
}
body.stop(true,true);
if(opts.onBeforeCollapse.call(_20d)==false){
return;
}
tool.addClass("panel-tool-expand");
if(_20e==true){
body.slideUp("normal",function(){
opts.collapsed=true;
opts.onCollapse.call(_20d);
});
}else{
body.hide();
opts.collapsed=true;
opts.onCollapse.call(_20d);
}
};
function _210(_211,_212){
var opts=$.data(_211,"panel").options;
var _213=$.data(_211,"panel").panel;
var body=_213.children("div.panel-body");
var tool=_213.children("div.panel-header").find("a.panel-tool-collapse");
if(opts.collapsed==false){
return;
}
body.stop(true,true);
if(opts.onBeforeExpand.call(_211)==false){
return;
}
tool.removeClass("panel-tool-expand");
if(_212==true){
body.slideDown("normal",function(){
opts.collapsed=false;
opts.onExpand.call(_211);
_1f5(_211);
_1fe(_211);
});
}else{
body.show();
opts.collapsed=false;
opts.onExpand.call(_211);
_1f5(_211);
_1fe(_211);
}
};
function _204(_214){
var opts=$.data(_214,"panel").options;
var _215=$.data(_214,"panel").panel;
var tool=_215.children("div.panel-header").find("a.panel-tool-max");
if(opts.maximized==true){
return;
}
tool.addClass("panel-tool-restore");
if(!$.data(_214,"panel").original){
$.data(_214,"panel").original={width:opts.width,height:opts.height,left:opts.left,top:opts.top,fit:opts.fit};
}
opts.left=0;
opts.top=0;
opts.fit=true;
_1e3(_214);
opts.minimized=false;
opts.maximized=true;
opts.onMaximize.call(_214);
};
function _216(_217){
var opts=$.data(_217,"panel").options;
var _218=$.data(_217,"panel").panel;
_218._fit(false);
_218.hide();
opts.minimized=true;
opts.maximized=false;
opts.onMinimize.call(_217);
};
function _219(_21a){
var opts=$.data(_21a,"panel").options;
var _21b=$.data(_21a,"panel").panel;
var tool=_21b.children("div.panel-header").find("a.panel-tool-max");
if(opts.maximized==false){
return;
}
_21b.show();
tool.removeClass("panel-tool-restore");
$.extend(opts,$.data(_21a,"panel").original);
_1e3(_21a);
opts.minimized=false;
opts.maximized=false;
$.data(_21a,"panel").original=null;
opts.onRestore.call(_21a);
};
function _21c(_21d){
var opts=$.data(_21d,"panel").options;
var _21e=$.data(_21d,"panel").panel;
var _21f=$(_21d).panel("header");
var body=$(_21d).panel("body");
_21e.css(opts.style);
_21e.addClass(opts.cls);
if(opts.border){
_21f.removeClass("panel-header-noborder");
body.removeClass("panel-body-noborder");
}else{
_21f.addClass("panel-header-noborder");
body.addClass("panel-body-noborder");
}
_21f.addClass(opts.headerCls);
body.addClass(opts.bodyCls);
if(opts.id){
$(_21d).attr("id",opts.id);
}else{
$(_21d).attr("id","");
}
};
function _220(_221,_222){
$.data(_221,"panel").options.title=_222;
$(_221).panel("header").find("div.panel-title").html(_222);
};
var TO=false;
var _223=true;
$(window).unbind(".panel").bind("resize.panel",function(){
if(!_223){
return;
}
if(TO!==false){
clearTimeout(TO);
}
TO=setTimeout(function(){
_223=false;
var _224=$("body.layout");
if(_224.length){
_224.layout("resize");
}else{
$("body").children("div.panel:visible,div.accordion:visible,div.tabs-container:visible,div.layout:visible").triggerHandler("_resize");
}
_223=true;
TO=false;
},200);
});
$.fn.panel=function(_225,_226){
if(typeof _225=="string"){
return $.fn.panel.methods[_225](this,_226);
}
_225=_225||{};
return this.each(function(){
var _227=$.data(this,"panel");
var opts;
if(_227){
opts=$.extend(_227.options,_225);
_227.isLoaded=false;
}else{
opts=$.extend({},$.fn.panel.defaults,$.fn.panel.parseOptions(this),_225);
$(this).attr("title","");
_227=$.data(this,"panel",{options:opts,panel:_1ed(this),isLoaded:false});
}
_1f0(this);
_21c(this);
if(opts.doSize==true){
_227.panel.css("display","block");
_1e3(this);
}
if(opts.closed==true||opts.minimized==true){
_227.panel.hide();
}else{
_200(this);
}
});
};
$.fn.panel.methods={options:function(jq){
return $.data(jq[0],"panel").options;
},panel:function(jq){
return $.data(jq[0],"panel").panel;
},header:function(jq){
return $.data(jq[0],"panel").panel.find(">div.panel-header");
},body:function(jq){
return $.data(jq[0],"panel").panel.find(">div.panel-body");
},setTitle:function(jq,_228){
return jq.each(function(){
_220(this,_228);
});
},open:function(jq,_229){
return jq.each(function(){
_200(this,_229);
});
},close:function(jq,_22a){
return jq.each(function(){
_1f4(this,_22a);
});
},destroy:function(jq,_22b){
return jq.each(function(){
_209(this,_22b);
});
},refresh:function(jq,href){
return jq.each(function(){
var _22c=$.data(this,"panel");
_22c.isLoaded=false;
if(href){
if(typeof href=="string"){
_22c.options.href=href;
}else{
_22c.options.queryParams=href;
}
}
_1f5(this);
});
},resize:function(jq,_22d){
return jq.each(function(){
_1e3(this,_22d);
});
},move:function(jq,_22e){
return jq.each(function(){
_1e9(this,_22e);
});
},maximize:function(jq){
return jq.each(function(){
_204(this);
});
},minimize:function(jq){
return jq.each(function(){
_216(this);
});
},restore:function(jq){
return jq.each(function(){
_219(this);
});
},collapse:function(jq,_22f){
return jq.each(function(){
_205(this,_22f);
});
},expand:function(jq,_230){
return jq.each(function(){
_210(this,_230);
});
}};
$.fn.panel.parseOptions=function(_231){
var t=$(_231);
return $.extend({},$.parser.parseOptions(_231,["id","width","height","left","top","title","iconCls","cls","headerCls","bodyCls","tools","href","method",{cache:"boolean",fit:"boolean",border:"boolean",noheader:"boolean"},{collapsible:"boolean",minimizable:"boolean",maximizable:"boolean"},{closable:"boolean",collapsed:"boolean",minimized:"boolean",maximized:"boolean",closed:"boolean"}]),{loadingMessage:(t.attr("loadingMessage")!=undefined?t.attr("loadingMessage"):undefined)});
};
$.fn.panel.defaults={id:null,title:null,iconCls:null,width:"auto",height:"auto",left:null,top:null,cls:null,headerCls:null,bodyCls:null,style:{},href:null,cache:true,fit:false,border:true,doSize:true,noheader:false,content:null,collapsible:false,minimizable:false,maximizable:false,closable:false,collapsed:false,minimized:false,maximized:false,closed:false,tools:null,queryParams:{},method:"get",href:null,loadingMessage:"Loading...",loader:function(_232,_233,_234){
var opts=$(this).panel("options");
if(!opts.href){
return false;
}
$.ajax({type:opts.method,url:opts.href,cache:false,data:_232,dataType:"html",success:function(data){
_233(data);
},error:function(){
_234.apply(this,arguments);
}});
},extractor:function(data){
var _235=/<body[^>]*>((.|[\n\r])*)<\/body>/im;
var _236=_235.exec(data);
if(_236){
return _236[1];
}else{
return data;
}
},onBeforeLoad:function(_237){
},onLoad:function(){
},onLoadError:function(){
},onBeforeOpen:function(){
},onOpen:function(){
},onBeforeClose:function(){
},onClose:function(){
},onBeforeDestroy:function(){
},onDestroy:function(){
},onResize:function(_238,_239){
},onMove:function(left,top){
},onMaximize:function(){
},onRestore:function(){
},onMinimize:function(){
},onBeforeCollapse:function(){
},onBeforeExpand:function(){
},onCollapse:function(){
},onExpand:function(){
}};
})(jQuery);
(function($){
function _2e8(_2e9){
var opts=$.data(_2e9,"tabs").options;
if(opts.tabPosition=="left"||opts.tabPosition=="right"||!opts.showHeader){
return;
}
var _2ea=$(_2e9).children("div.tabs-header");
var tool=_2ea.children("div.tabs-tool");
var _2eb=_2ea.children("div.tabs-scroller-left");
var _2ec=_2ea.children("div.tabs-scroller-right");
var wrap=_2ea.children("div.tabs-wrap");
var _2ed=_2ea.outerHeight();
if(opts.plain){
_2ed-=_2ed-_2ea.height();
}
tool._outerHeight(_2ed);
var _2ee=0;
$("ul.tabs li",_2ea).each(function(){
_2ee+=$(this).outerWidth(true);
});
var _2ef=_2ea.width()-tool._outerWidth();
if(_2ee>_2ef){
_2eb.add(_2ec).show()._outerHeight(_2ed);
if(opts.toolPosition=="left"){
tool.css({left:_2eb.outerWidth(),right:""});
wrap.css({marginLeft:_2eb.outerWidth()+tool._outerWidth(),marginRight:_2ec._outerWidth(),width:_2ef-_2eb.outerWidth()-_2ec.outerWidth()});
}else{
tool.css({left:"",right:_2ec.outerWidth()});
wrap.css({marginLeft:_2eb.outerWidth(),marginRight:_2ec.outerWidth()+tool._outerWidth(),width:_2ef-_2eb.outerWidth()-_2ec.outerWidth()});
}
}else{
_2eb.add(_2ec).hide();
if(opts.toolPosition=="left"){
tool.css({left:0,right:""});
wrap.css({marginLeft:tool._outerWidth(),marginRight:0,width:_2ef});
}else{
tool.css({left:"",right:0});
wrap.css({marginLeft:0,marginRight:tool._outerWidth(),width:_2ef});
}
}
};
function _2f0(_2f1){
var opts=$.data(_2f1,"tabs").options;
var _2f2=$(_2f1).children("div.tabs-header");
if(opts.tools){
if(typeof opts.tools=="string"){
$(opts.tools).addClass("tabs-tool").appendTo(_2f2);
$(opts.tools).show();
}else{
_2f2.children("div.tabs-tool").remove();
var _2f3=$("<div class=\"tabs-tool\"><table cellspacing=\"0\" cellpadding=\"0\" style=\"height:100%\"><tr></tr></table></div>").appendTo(_2f2);
var tr=_2f3.find("tr");
for(var i=0;i<opts.tools.length;i++){
var td=$("<td></td>").appendTo(tr);
var tool=$("<a href=\"javascript:void(0);\"></a>").appendTo(td);
tool[0].onclick=eval(opts.tools[i].handler||function(){
});
tool.linkbutton($.extend({},opts.tools[i],{plain:true}));
}
}
}else{
_2f2.children("div.tabs-tool").remove();
}
};
function _2f4(_2f5){
var _2f6=$.data(_2f5,"tabs");
var opts=_2f6.options;
var cc=$(_2f5);
opts.fit?$.extend(opts,cc._fit()):cc._fit(false);
cc.width(opts.width).height(opts.height);
var _2f7=$(_2f5).children("div.tabs-header");
var _2f8=$(_2f5).children("div.tabs-panels");
var wrap=_2f7.find("div.tabs-wrap");
var ul=wrap.find(".tabs");
for(var i=0;i<_2f6.tabs.length;i++){
var _2f9=_2f6.tabs[i].panel("options");
var p_t=_2f9.tab.find("a.tabs-inner");
var _2fa=parseInt(_2f9.tabWidth||opts.tabWidth)||undefined;
if(_2fa){
p_t._outerWidth(_2fa);
}else{
p_t.css("width","");
}
p_t._outerHeight(opts.tabHeight);
p_t.css("lineHeight",p_t.height()+"px");
}
if(opts.tabPosition=="left"||opts.tabPosition=="right"){
_2f7._outerWidth(opts.showHeader?opts.headerWidth:0);
_2f8._outerWidth(cc.width()-_2f7.outerWidth());
_2f7.add(_2f8)._outerHeight(opts.height);
wrap._outerWidth(_2f7.width());
ul._outerWidth(wrap.width()).css("height","");
}else{
var lrt=_2f7.children("div.tabs-scroller-left,div.tabs-scroller-right,div.tabs-tool");
_2f7._outerWidth(opts.width).css("height","");
if(opts.showHeader){
_2f7.css("background-color","");
wrap.css("height","");
lrt.show();
}else{
_2f7.css("background-color","transparent");
_2f7._outerHeight(0);
wrap._outerHeight(0);
lrt.hide();
}
ul._outerHeight(opts.tabHeight).css("width","");
_2e8(_2f5);
var _2fb=opts.height;
if(!isNaN(_2fb)){
_2f8._outerHeight(_2fb-_2f7.outerHeight());
}else{
_2f8.height("auto");
}
var _2fa=opts.width;
if(!isNaN(_2fa)){
_2f8._outerWidth(_2fa);
}else{
_2f8.width("auto");
}
}
};
function _2fc(_2fd){
var opts=$.data(_2fd,"tabs").options;
var tab=_2fe(_2fd);
if(tab){
var _2ff=$(_2fd).children("div.tabs-panels");
var _300=opts.width=="auto"?"auto":_2ff.width();
var _301=opts.height=="auto"?"auto":_2ff.height();
tab.panel("resize",{width:_300,height:_301});
}
};
function _302(_303){
var tabs=$.data(_303,"tabs").tabs;
var cc=$(_303);
cc.addClass("tabs-container");
var pp=$("<div class=\"tabs-panels\"></div>").insertBefore(cc);
cc.children("div").each(function(){
pp[0].appendChild(this);
});
cc[0].appendChild(pp[0]);
$("<div class=\"tabs-header\">"+"<div class=\"tabs-scroller-left\"></div>"+"<div class=\"tabs-scroller-right\"></div>"+"<div class=\"tabs-wrap\">"+"<ul class=\"tabs\"></ul>"+"</div>"+"</div>").prependTo(_303);
cc.children("div.tabs-panels").children("div").each(function(i){
var opts=$.extend({},$.parser.parseOptions(this),{selected:($(this).attr("selected")?true:undefined)});
var pp=$(this);
tabs.push(pp);
_310(_303,pp,opts);
});
cc.children("div.tabs-header").find(".tabs-scroller-left, .tabs-scroller-right").hover(function(){
$(this).addClass("tabs-scroller-over");
},function(){
$(this).removeClass("tabs-scroller-over");
});
cc.bind("_resize",function(e,_304){
var opts=$.data(_303,"tabs").options;
if(opts.fit==true||_304){
_2f4(_303);
_2fc(_303);
}
return false;
});
};
function _305(_306){
var _307=$.data(_306,"tabs");
var opts=_307.options;
$(_306).children("div.tabs-header").unbind().bind("click",function(e){
if($(e.target).hasClass("tabs-scroller-left")){
$(_306).tabs("scrollBy",-opts.scrollIncrement);
}else{
if($(e.target).hasClass("tabs-scroller-right")){
$(_306).tabs("scrollBy",opts.scrollIncrement);
}else{
var li=$(e.target).closest("li");
if(li.hasClass("tabs-disabled")){
return;
}
var a=$(e.target).closest("a.tabs-close");
if(a.length){
_321(_306,_308(li));
}else{
if(li.length){
var _309=_308(li);
var _30a=_307.tabs[_309].panel("options");
if(_30a.collapsible){
_30a.closed?_317(_306,_309):_338(_306,_309);
}else{
_317(_306,_309);
}
}
}
}
}
}).bind("contextmenu",function(e){
var li=$(e.target).closest("li");
if(li.hasClass("tabs-disabled")){
return;
}
if(li.length){
opts.onContextMenu.call(_306,e,li.find("span.tabs-title").html(),_308(li));
}
});
function _308(li){
var _30b=0;
li.parent().children("li").each(function(i){
if(li[0]==this){
_30b=i;
return false;
}
});
return _30b;
};
};
function _30c(_30d){
var opts=$.data(_30d,"tabs").options;
var _30e=$(_30d).children("div.tabs-header");
var _30f=$(_30d).children("div.tabs-panels");
_30e.removeClass("tabs-header-top tabs-header-bottom tabs-header-left tabs-header-right");
_30f.removeClass("tabs-panels-top tabs-panels-bottom tabs-panels-left tabs-panels-right");
if(opts.tabPosition=="top"){
_30e.insertBefore(_30f);
}else{
if(opts.tabPosition=="bottom"){
_30e.insertAfter(_30f);
_30e.addClass("tabs-header-bottom");
_30f.addClass("tabs-panels-top");
}else{
if(opts.tabPosition=="left"){
_30e.addClass("tabs-header-left");
_30f.addClass("tabs-panels-right");
}else{
if(opts.tabPosition=="right"){
_30e.addClass("tabs-header-right");
_30f.addClass("tabs-panels-left");
}
}
}
}
if(opts.plain==true){
_30e.addClass("tabs-header-plain");
}else{
_30e.removeClass("tabs-header-plain");
}
if(opts.border==true){
_30e.removeClass("tabs-header-noborder");
_30f.removeClass("tabs-panels-noborder");
}else{
_30e.addClass("tabs-header-noborder");
_30f.addClass("tabs-panels-noborder");
}
};
function _310(_311,pp,_312){
var _313=$.data(_311,"tabs");
_312=_312||{};
pp.panel($.extend({},_312,{border:false,noheader:true,closed:true,doSize:false,iconCls:(_312.icon?_312.icon:undefined),onLoad:function(){
if(_312.onLoad){
_312.onLoad.call(this,arguments);
}
_313.options.onLoad.call(_311,$(this));
}}));
var opts=pp.panel("options");
var tabs=$(_311).children("div.tabs-header").find("ul.tabs");
opts.tab=$("<li></li>").appendTo(tabs);
opts.tab.append("<a href=\"javascript:void(0)\" class=\"tabs-inner\">"+"<span class=\"tabs-title\"></span>"+"<span class=\"tabs-icon\"></span>"+"</a>");
$(_311).tabs("update",{tab:pp,options:opts});
};
function _314(_315,_316){
var opts=$.data(_315,"tabs").options;
var tabs=$.data(_315,"tabs").tabs;
if(_316.selected==undefined){
_316.selected=true;
}
var pp=$("<div></div>").appendTo($(_315).children("div.tabs-panels"));
tabs.push(pp);
_310(_315,pp,_316);
opts.onAdd.call(_315,_316.title,tabs.length-1);
_2f4(_315);
if(_316.selected){
_317(_315,tabs.length-1);
}
};
function _318(_319,_31a){
var _31b=$.data(_319,"tabs").selectHis;
var pp=_31a.tab;
var _31c=pp.panel("options").title;
pp.panel($.extend({},_31a.options,{iconCls:(_31a.options.icon?_31a.options.icon:undefined)}));
var opts=pp.panel("options");
var tab=opts.tab;
var _31d=tab.find("span.tabs-title");
var _31e=tab.find("span.tabs-icon");
_31d.html(opts.title);
_31e.attr("class","tabs-icon");
tab.find("a.tabs-close").remove();
if(opts.closable){
_31d.addClass("tabs-closable");
$("<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>").appendTo(tab);
}else{
_31d.removeClass("tabs-closable");
}
if(opts.iconCls){
_31d.addClass("tabs-with-icon");
_31e.addClass(opts.iconCls);
}else{
_31d.removeClass("tabs-with-icon");
}
if(_31c!=opts.title){
for(var i=0;i<_31b.length;i++){
if(_31b[i]==_31c){
_31b[i]=opts.title;
}
}
}
tab.find("span.tabs-p-tool").remove();
if(opts.tools){
var _31f=$("<span class=\"tabs-p-tool\"></span>").insertAfter(tab.find("a.tabs-inner"));
if($.isArray(opts.tools)){
for(var i=0;i<opts.tools.length;i++){
var t=$("<a href=\"javascript:void(0)\"></a>").appendTo(_31f);
t.addClass(opts.tools[i].iconCls);
if(opts.tools[i].handler){
t.bind("click",{handler:opts.tools[i].handler},function(e){
if($(this).parents("li").hasClass("tabs-disabled")){
return;
}
e.data.handler.call(this);
});
}
}
}else{
$(opts.tools).children().appendTo(_31f);
}
var pr=_31f.children().length*12;
if(opts.closable){
pr+=8;
}else{
pr-=3;
_31f.css("right","5px");
}
_31d.css("padding-right",pr+"px");
}
_2f4(_319);
$.data(_319,"tabs").options.onUpdate.call(_319,opts.title,_320(_319,pp));
};
function _321(_322,_323){
var opts=$.data(_322,"tabs").options;
var tabs=$.data(_322,"tabs").tabs;
var _324=$.data(_322,"tabs").selectHis;
if(!_325(_322,_323)){
return;
}
var tab=_326(_322,_323);
var _327=tab.panel("options").title;
var _328=_320(_322,tab);
if(opts.onBeforeClose.call(_322,_327,_328)==false){
return;
}
var tab=_326(_322,_323,true);
tab.panel("options").tab.remove();
tab.panel("destroy");
opts.onClose.call(_322,_327,_328);
_2f4(_322);
for(var i=0;i<_324.length;i++){
if(_324[i]==_327){
_324.splice(i,1);
i--;
}
}
var _329=_324.pop();
if(_329){
_317(_322,_329);
}else{
if(tabs.length){
_317(_322,0);
}
}
};
function _326(_32a,_32b,_32c){
var tabs=$.data(_32a,"tabs").tabs;
if(typeof _32b=="number"){
if(_32b<0||_32b>=tabs.length){
return null;
}else{
var tab=tabs[_32b];
if(_32c){
tabs.splice(_32b,1);
}
return tab;
}
}
for(var i=0;i<tabs.length;i++){
var tab=tabs[i];
if(tab.panel("options").title==_32b){
if(_32c){
tabs.splice(i,1);
}
return tab;
}
}
return null;
};
function _320(_32d,tab){
var tabs=$.data(_32d,"tabs").tabs;
for(var i=0;i<tabs.length;i++){
if(tabs[i][0]==$(tab)[0]){
return i;
}
}
return -1;
};
function _2fe(_32e){
var tabs=$.data(_32e,"tabs").tabs;
for(var i=0;i<tabs.length;i++){
var tab=tabs[i];
if(tab.panel("options").closed==false){
return tab;
}
}
return null;
};
function _32f(_330){
var _331=$.data(_330,"tabs");
var tabs=_331.tabs;
for(var i=0;i<tabs.length;i++){
if(tabs[i].panel("options").selected){
_317(_330,i);
return;
}
}
_317(_330,_331.options.selected);
};
function _317(_332,_333){
var _334=$.data(_332,"tabs");
var opts=_334.options;
var tabs=_334.tabs;
var _335=_334.selectHis;
if(tabs.length==0){
return;
}
var _336=_326(_332,_333);
if(!_336){
return;
}
var _337=_2fe(_332);
if(_337){
if(_336[0]==_337[0]){
_2fc(_332);
return;
}
_338(_332,_320(_332,_337));
if(!_337.panel("options").closed){
return;
}
}
_336.panel("open");
var _339=_336.panel("options").title;
_335.push(_339);
var tab=_336.panel("options").tab;
tab.addClass("tabs-selected");
var wrap=$(_332).find(">div.tabs-header>div.tabs-wrap");
var left=tab.position().left;
var _33a=left+tab.outerWidth();
if(left<0||_33a>wrap.width()){
var _33b=left-(wrap.width()-tab.width())/2;
$(_332).tabs("scrollBy",_33b);
}else{
$(_332).tabs("scrollBy",0);
}
_2fc(_332);
opts.onSelect.call(_332,_339,_320(_332,_336));
};
function _338(_33c,_33d){
var _33e=$.data(_33c,"tabs");
var p=_326(_33c,_33d);
if(p){
var opts=p.panel("options");
if(!opts.closed){
p.panel("close");
if(opts.closed){
opts.tab.removeClass("tabs-selected");
_33e.options.onUnselect.call(_33c,opts.title,_320(_33c,p));
}
}
}
};
function _325(_33f,_340){
return _326(_33f,_340)!=null;
};
function _341(_342,_343){
var opts=$.data(_342,"tabs").options;
opts.showHeader=_343;
$(_342).tabs("resize");
};
$.fn.tabs=function(_344,_345){
if(typeof _344=="string"){
return $.fn.tabs.methods[_344](this,_345);
}
_344=_344||{};
return this.each(function(){
var _346=$.data(this,"tabs");
var opts;
if(_346){
opts=$.extend(_346.options,_344);
_346.options=opts;
}else{
$.data(this,"tabs",{options:$.extend({},$.fn.tabs.defaults,$.fn.tabs.parseOptions(this),_344),tabs:[],selectHis:[]});
_302(this);
}
_2f0(this);
_30c(this);
_2f4(this);
_305(this);
_32f(this);
});
};
$.fn.tabs.methods={options:function(jq){
var cc=jq[0];
var opts=$.data(cc,"tabs").options;
var s=_2fe(cc);
opts.selected=s?_320(cc,s):-1;
return opts;
},tabs:function(jq){
return $.data(jq[0],"tabs").tabs;
},resize:function(jq){
return jq.each(function(){
_2f4(this);
_2fc(this);
});
},add:function(jq,_347){
return jq.each(function(){
_314(this,_347);
});
},close:function(jq,_348){
return jq.each(function(){
_321(this,_348);
});
},getTab:function(jq,_349){
return _326(jq[0],_349);
},getTabIndex:function(jq,tab){
return _320(jq[0],tab);
},getSelected:function(jq){
return _2fe(jq[0]);
},select:function(jq,_34a){
return jq.each(function(){
_317(this,_34a);
});
},unselect:function(jq,_34b){
return jq.each(function(){
_338(this,_34b);
});
},exists:function(jq,_34c){
return _325(jq[0],_34c);
},update:function(jq,_34d){
return jq.each(function(){
_318(this,_34d);
});
},enableTab:function(jq,_34e){
return jq.each(function(){
$(this).tabs("getTab",_34e).panel("options").tab.removeClass("tabs-disabled");
});
},disableTab:function(jq,_34f){
return jq.each(function(){
$(this).tabs("getTab",_34f).panel("options").tab.addClass("tabs-disabled");
});
},showHeader:function(jq){
return jq.each(function(){
_341(this,true);
});
},hideHeader:function(jq){
return jq.each(function(){
_341(this,false);
});
},scrollBy:function(jq,_350){
return jq.each(function(){
var opts=$(this).tabs("options");
var wrap=$(this).find(">div.tabs-header>div.tabs-wrap");
var pos=Math.min(wrap._scrollLeft()+_350,_351());
wrap.animate({scrollLeft:pos},opts.scrollDuration);
function _351(){
var w=0;
var ul=wrap.children("ul");
ul.children("li").each(function(){
w+=$(this).outerWidth(true);
});
return w-wrap.width()+(ul.outerWidth()-ul.width());
};
});
}};
$.fn.tabs.parseOptions=function(_352){
return $.extend({},$.parser.parseOptions(_352,["width","height","tools","toolPosition","tabPosition",{fit:"boolean",border:"boolean",plain:"boolean",headerWidth:"number",tabWidth:"number",tabHeight:"number",selected:"number",showHeader:"boolean"}]));
};
$.fn.tabs.defaults={width:"auto",height:"auto",headerWidth:150,tabWidth:"auto",tabHeight:27,selected:0,showHeader:true,plain:false,fit:false,border:true,tools:null,toolPosition:"right",tabPosition:"top",scrollIncrement:100,scrollDuration:400,onLoad:function(_353){
},onSelect:function(_354,_355){
},onUnselect:function(_356,_357){
},onBeforeClose:function(_358,_359){
},onClose:function(_35a,_35b){
},onAdd:function(_35c,_35d){
},onUpdate:function(_35e,_35f){
},onContextMenu:function(e,_360,_361){
}};
})(jQuery);

(function($){
function init(_3a6){
$(_3a6).appendTo("body");
$(_3a6).addClass("menu-top");
$(document).unbind(".menu").bind("mousedown.menu",function(e){
var m=$(e.target).closest("div.menu,div.combo-p");
if(m.length){
return;
}
$("body>div.menu-top:visible").menu("hide");
});
var _3a7=_3a8($(_3a6));
for(var i=0;i<_3a7.length;i++){
_3a9(_3a7[i]);
}
function _3a8(menu){
var _3aa=[];
menu.addClass("menu");
_3aa.push(menu);
if(!menu.hasClass("menu-content")){
menu.children("div").each(function(){
var _3ab=$(this).children("div");
if(_3ab.length){
_3ab.insertAfter(_3a6);
this.submenu=_3ab;
var mm=_3a8(_3ab);
_3aa=_3aa.concat(mm);
}
});
}
return _3aa;
};
function _3a9(menu){
var wh=$.parser.parseOptions(menu[0],["width","height"]);
menu[0].originalHeight=wh.height||0;
if(menu.hasClass("menu-content")){
menu[0].originalWidth=wh.width||menu._outerWidth();
}else{
menu[0].originalWidth=wh.width||0;
menu.children("div").each(function(){
var item=$(this);
var _3ac=$.extend({},$.parser.parseOptions(this,["name","iconCls","href",{separator:"boolean"}]),{disabled:(item.attr("disabled")?true:undefined)});
if(_3ac.separator){
item.addClass("menu-sep");
}
if(!item.hasClass("menu-sep")){
item[0].itemName=_3ac.name||"";
item[0].itemHref=_3ac.href||"";
var text=item.addClass("menu-item").html();
item.empty().append($("<div class=\"menu-text\"></div>").html(text));
if(_3ac.iconCls){
$("<div class=\"menu-icon\"></div>").addClass(_3ac.iconCls).appendTo(item);
}
if(_3ac.disabled){
_3ad(_3a6,item[0],true);
}
if(item[0].submenu){
$("<div class=\"menu-rightarrow\"></div>").appendTo(item);
}
_3ae(_3a6,item);
}
});
$("<div class=\"menu-line\"></div>").prependTo(menu);
}
_3af(_3a6,menu);
menu.hide();
_3b0(_3a6,menu);
};
};
function _3af(_3b1,menu){
var opts=$.data(_3b1,"menu").options;
var _3b2=menu.attr("style")||"";
menu.css({display:"block",left:-10000,height:"auto",overflow:"hidden"});
var el=menu[0];
var _3b3=el.originalWidth||0;
if(!_3b3){
_3b3=0;
menu.find("div.menu-text").each(function(){
if(_3b3<$(this)._outerWidth()){
_3b3=$(this)._outerWidth();
}
$(this).closest("div.menu-item")._outerHeight($(this)._outerHeight()+2);
});
_3b3+=40;
}
_3b3=Math.max(_3b3,opts.minWidth);
var _3b4=el.originalHeight||menu.outerHeight();
var _3b5=Math.max(el.originalHeight,menu.outerHeight())-2;
menu._outerWidth(_3b3)._outerHeight(_3b4);
menu.children("div.menu-line")._outerHeight(_3b5);
_3b2+=";width:"+el.style.width+";height:"+el.style.height;
menu.attr("style",_3b2);
};
function _3b0(_3b6,menu){
var _3b7=$.data(_3b6,"menu");
menu.unbind(".menu").bind("mouseenter.menu",function(){
if(_3b7.timer){
clearTimeout(_3b7.timer);
_3b7.timer=null;
}
}).bind("mouseleave.menu",function(){
if(_3b7.options.hideOnUnhover){
_3b7.timer=setTimeout(function(){
_3b8(_3b6);
},100);
}
});
};
function _3ae(_3b9,item){
if(!item.hasClass("menu-item")){
return;
}
item.unbind(".menu");
item.bind("click.menu",function(){
if($(this).hasClass("menu-item-disabled")){
return;
}
if(!this.submenu){
_3b8(_3b9);
var href=$(this).attr("href");
if(href){
location.href=href;
}
}
var item=$(_3b9).menu("getItem",this);
$.data(_3b9,"menu").options.onClick.call(_3b9,item);
}).bind("mouseenter.menu",function(e){
item.siblings().each(function(){
if(this.submenu){
_3bc(this.submenu);
}
$(this).removeClass("menu-active");
});
item.addClass("menu-active");
if($(this).hasClass("menu-item-disabled")){
item.addClass("menu-active-disabled");
return;
}
var _3ba=item[0].submenu;
if(_3ba){
$(_3b9).menu("show",{menu:_3ba,parent:item});
}
}).bind("mouseleave.menu",function(e){
item.removeClass("menu-active menu-active-disabled");
var _3bb=item[0].submenu;
if(_3bb){
if(e.pageX>=parseInt(_3bb.css("left"))){
item.addClass("menu-active");
}else{
_3bc(_3bb);
}
}else{
item.removeClass("menu-active");
}
});
};
function _3b8(_3bd){
var _3be=$.data(_3bd,"menu");
if(_3be){
if($(_3bd).is(":visible")){
_3bc($(_3bd));
_3be.options.onHide.call(_3bd);
}
}
return false;
};
function _3bf(_3c0,_3c1){
var left,top;
_3c1=_3c1||{};
var menu=$(_3c1.menu||_3c0);
if(menu.hasClass("menu-top")){
var opts=$.data(_3c0,"menu").options;
$.extend(opts,_3c1);
left=opts.left;
top=opts.top;
if(opts.alignTo){
var at=$(opts.alignTo);
left=at.offset().left;
top=at.offset().top+at._outerHeight();
if(opts.align=="right"){
left+=at.outerWidth()-menu.outerWidth();
}
}
if(left+menu.outerWidth()>$(window)._outerWidth()+$(document)._scrollLeft()){
left=$(window)._outerWidth()+$(document).scrollLeft()-menu.outerWidth()-5;
}
if(left<0){
left=0;
}
if(top+menu.outerHeight()>$(window)._outerHeight()+$(document).scrollTop()){
top=$(window)._outerHeight()+$(document).scrollTop()-menu.outerHeight()-5;
}
}else{
var _3c2=_3c1.parent;
left=_3c2.offset().left+_3c2.outerWidth()-2;
if(left+menu.outerWidth()+5>$(window)._outerWidth()+$(document).scrollLeft()){
left=_3c2.offset().left-menu.outerWidth()+2;
}
var top=_3c2.offset().top-3;
if(top+menu.outerHeight()>$(window)._outerHeight()+$(document).scrollTop()){
top=$(window)._outerHeight()+$(document).scrollTop()-menu.outerHeight()-5;
}
}
menu.css({left:left,top:top});
menu.show(0,function(){
if(!menu[0].shadow){
menu[0].shadow=$("<div class=\"menu-shadow\"></div>").insertAfter(menu);
}
menu[0].shadow.css({display:"block",zIndex:$.fn.menu.defaults.zIndex++,left:menu.css("left"),top:menu.css("top"),width:menu.outerWidth(),height:menu.outerHeight()});
menu.css("z-index",$.fn.menu.defaults.zIndex++);
if(menu.hasClass("menu-top")){
$.data(menu[0],"menu").options.onShow.call(menu[0]);
}
});
};
function _3bc(menu){
if(!menu){
return;
}
_3c3(menu);
menu.find("div.menu-item").each(function(){
if(this.submenu){
_3bc(this.submenu);
}
$(this).removeClass("menu-active");
});
function _3c3(m){
m.stop(true,true);
if(m[0].shadow){
m[0].shadow.hide();
}
m.hide();
};
};
function _3c4(_3c5,text){
var _3c6=null;
var tmp=$("<div></div>");
function find(menu){
menu.children("div.menu-item").each(function(){
var item=$(_3c5).menu("getItem",this);
var s=tmp.empty().html(item.text).text();
if(text==$.trim(s)){
_3c6=item;
}else{
if(this.submenu&&!_3c6){
find(this.submenu);
}
}
});
};
find($(_3c5));
tmp.remove();
return _3c6;
};
function _3ad(_3c7,_3c8,_3c9){
var t=$(_3c8);
if(!t.hasClass("menu-item")){
return;
}
if(_3c9){
t.addClass("menu-item-disabled");
if(_3c8.onclick){
_3c8.onclick1=_3c8.onclick;
_3c8.onclick=null;
}
}else{
t.removeClass("menu-item-disabled");
if(_3c8.onclick1){
_3c8.onclick=_3c8.onclick1;
_3c8.onclick1=null;
}
}
};
function _3ca(_3cb,_3cc){
var menu=$(_3cb);
if(_3cc.parent){
if(!_3cc.parent.submenu){
var _3cd=$("<div class=\"menu\"><div class=\"menu-line\"></div></div>").appendTo("body");
_3cd.hide();
_3cc.parent.submenu=_3cd;
$("<div class=\"menu-rightarrow\"></div>").appendTo(_3cc.parent);
}
menu=_3cc.parent.submenu;
}
if(_3cc.separator){
var item=$("<div class=\"menu-sep\"></div>").appendTo(menu);
}else{
var item=$("<div class=\"menu-item\"></div>").appendTo(menu);
$("<div class=\"menu-text\"></div>").html(_3cc.text).appendTo(item);
}
if(_3cc.iconCls){
$("<div class=\"menu-icon\"></div>").addClass(_3cc.iconCls).appendTo(item);
}
if(_3cc.id){
item.attr("id",_3cc.id);
}
if(_3cc.name){
item[0].itemName=_3cc.name;
}
if(_3cc.href){
item[0].itemHref=_3cc.href;
}
if(_3cc.onclick){
if(typeof _3cc.onclick=="string"){
item.attr("onclick",_3cc.onclick);
}else{
item[0].onclick=eval(_3cc.onclick);
}
}
if(_3cc.handler){
item[0].onclick=eval(_3cc.handler);
}
if(_3cc.disabled){
_3ad(_3cb,item[0],true);
}
_3ae(_3cb,item);
_3b0(_3cb,menu);
_3af(_3cb,menu);
};
function _3ce(_3cf,_3d0){
function _3d1(el){
if(el.submenu){
el.submenu.children("div.menu-item").each(function(){
_3d1(this);
});
var _3d2=el.submenu[0].shadow;
if(_3d2){
_3d2.remove();
}
el.submenu.remove();
}
$(el).remove();
};
_3d1(_3d0);
};
function _3d3(_3d4){
$(_3d4).children("div.menu-item").each(function(){
_3ce(_3d4,this);
});
if(_3d4.shadow){
_3d4.shadow.remove();
}
$(_3d4).remove();
};
$.fn.menu=function(_3d5,_3d6){
if(typeof _3d5=="string"){
return $.fn.menu.methods[_3d5](this,_3d6);
}
_3d5=_3d5||{};
return this.each(function(){
var _3d7=$.data(this,"menu");
if(_3d7){
$.extend(_3d7.options,_3d5);
}else{
_3d7=$.data(this,"menu",{options:$.extend({},$.fn.menu.defaults,$.fn.menu.parseOptions(this),_3d5)});
init(this);
}
$(this).css({left:_3d7.options.left,top:_3d7.options.top});
});
};
$.fn.menu.methods={options:function(jq){
return $.data(jq[0],"menu").options;
},show:function(jq,pos){
return jq.each(function(){
_3bf(this,pos);
});
},hide:function(jq){
return jq.each(function(){
_3b8(this);
});
},destroy:function(jq){
return jq.each(function(){
_3d3(this);
});
},setText:function(jq,_3d8){
return jq.each(function(){
$(_3d8.target).children("div.menu-text").html(_3d8.text);
});
},setIcon:function(jq,_3d9){
return jq.each(function(){
$(_3d9.target).children("div.menu-icon").remove();
if(_3d9.iconCls){
$("<div class=\"menu-icon\"></div>").addClass(_3d9.iconCls).appendTo(_3d9.target);
}
});
},getItem:function(jq,_3da){
var t=$(_3da);
var item={target:_3da,id:t.attr("id"),text:$.trim(t.children("div.menu-text").html()),disabled:t.hasClass("menu-item-disabled"),name:_3da.itemName,href:_3da.itemHref,onclick:_3da.onclick};
var icon=t.children("div.menu-icon");
if(icon.length){
var cc=[];
var aa=icon.attr("class").split(" ");
for(var i=0;i<aa.length;i++){
if(aa[i]!="menu-icon"){
cc.push(aa[i]);
}
}
item.iconCls=cc.join(" ");
}
return item;
},findItem:function(jq,text){
return _3c4(jq[0],text);
},appendItem:function(jq,_3db){
return jq.each(function(){
_3ca(this,_3db);
});
},removeItem:function(jq,_3dc){
return jq.each(function(){
_3ce(this,_3dc);
});
},enableItem:function(jq,_3dd){
return jq.each(function(){
_3ad(this,_3dd,false);
});
},disableItem:function(jq,_3de){
return jq.each(function(){
_3ad(this,_3de,true);
});
}};
$.fn.menu.parseOptions=function(_3df){
return $.extend({},$.parser.parseOptions(_3df,["left","top",{minWidth:"number",hideOnUnhover:"boolean"}]));
};
$.fn.menu.defaults={zIndex:110000,left:0,top:0,alignTo:null,align:"left",minWidth:120,hideOnUnhover:true,onShow:function(){
},onHide:function(){
},onClick:function(item){
}};
})(jQuery);
 //下面是按照sgui方式封装的实现Gboss框架功能的js 
(function($){
    var create = function(obj,settings){
        $.ajax({
        	   type: "POST",
        	   async: false,
        	   contentType : 'application/json',
        	   url: settings.url,
        	   dataType : 'json',
        	   success: function(msg){
                   var panel = $("<ul></ul>");
        		   createSubNode(msg,panel,settings);
                   obj.append(panel);
                   msg = null;
        	   },
        	   error:function(XMLHttpRequest, textStatus, errorThrown){
        	   }
        	});
    };

    var createSubNode = function(json,panel,settings){
        $.each(json,function(k,v){
        		var node = $('<li class="item"></li>');
                var node_a = $('<a href="javascript:void(0);" class="main_nav" onmouseover="divshow('+k+');"></a>');
                var node_img = $('<img src="">');
                //var node_span = $('<span class="arrow">&gt;</span>');
                var node_div = $('<div class="sub_nav_wrap single_nav" id="div'+k+'"></div>');
                var node_ul = $(' <div class="sub_nav clearfix"></div>');
                var node_b = $('<b></b>');
                var node_span = $('<span class="sub_span"></span>');

                node_img.attr('src','images/'+v.type+'.png');
                node_a.append(node_img);
                //node_a.append(node_span);
                if(v.url&&v.url!=null&&v.url!='null'){
                	node_a.addClass("cs-navi-tab");
                	node_a.attr('src',v.url);
                }
                node_span.append(v.name);
                node_a.append(node_span);
                node.append(node_a);
                
                var subjson = v.items;
                if(subjson&&subjson.length>0){
                    $.each(subjson,function(ky,va){
                        var subnode = $('<ul class="sup_nav"></ul>');
                        var sub_node_a = $('<a href="javascript:divhide('+k+');" class="sub_node_a">');
                        if(va.url&&va.url!=null&&va.url!='null'){
                			sub_node_a.addClass("cs-navi-tab");
                			sub_node_a.attr('src',va.url);
                		}
                        
                        sub_node_a.append(va.name);
                        subnode.append(sub_node_a);

                        var supjson = va.items;
                        if(supjson&&supjson.length>0){
                        	$.each(supjson,function(kp,vp){
                        		var supnode = $("<li></li>");
                        		var sup_node_a = $('<a href="javascript:divhide('+k+');" class="sup_node_a">');
                        		if(vp.url&&vp.url!=null&&vp.url!='null'){
		                			sup_node_a.addClass("cs-navi-tab");
		                			sup_node_a.attr('src',vp.url);
	                			}
	                			sup_node_a.append(vp.name);
	                			supnode.append(sup_node_a);
                        		subnode.append(supnode);
                        	});
                    	}
                        node_ul.append(subnode);
                        });
					node_div.append(node_ul);
               		node_div.append(node_b);
                	node.append(node_div);

                	var i = subjson.length;
                	if(i>6){
                		node_div.css("width","730px");
                	}else{
                		node_div.css("width",i*120+10+"px");
                	}
                }

                node.addClass("item_"+v.type);
                panel.append(node);   
        });
    };

    var methods = {
        init: function(options){
            return this.each(function(){
                var $this = $(this);
                var settings = $this.data('sgNav');

                if(typeof(settings) == 'undefined') {

                    var defaults = {
                        datatype: 'json',
                        url: 'tree.json',
                        parentMenu:null,
                        rendto:null,
                    };

                    settings = $.extend({}, defaults, options);

                    $this.data('sgNav', settings);
                } else {
                    settings = $.extend({}, settings, options);
                }
                // 代码在这里运行
                create($this,settings);
            });
        },
        destroy: function(options){
            return $(this).each(function(){
                var $this = $(this);

                $this.removeData('sgNav');
            });
        },
        val: function(options){
            var someValue = this.eq(0).html();

            return someValue;
        }
    };

    $.fn.sgNav = function(){
        var method = arguments[0];

        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.sgNav' );
            return this;
        }
        return method.apply(this, arguments);
    };
})(jQuery);

