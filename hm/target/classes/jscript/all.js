window.mainUrl=null;window.mainModule=null;(function($){$.fn._mainchange=function(a){$("#main").load(a)};Date.prototype.format=function(a){var o={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),"S":this.getMilliseconds()};if(/(y+)/.test(a))a=a.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length));for(var k in o)if(new RegExp("("+k+")").test(a))a=a.replace(RegExp.$1,RegExp.$1.length==1?o[k]:("00"+o[k]).substr((""+o[k]).length));return a}})(jQuery);(function($){var q=function(d,e){$.ajax({type:"POST",async:false,contentType:'application/json',url:e.url,dataType:'json',success:function(a){var b=$("<ul></ul>");r(a,b,e);d.append(b);a=null},error:function(a,b,c){}})};var r=function(n,o,p){$.each(n,function(k,v){if(p.level==1||p.level==0){var h=$("<li></li>");var i=$("<a></a>");var j=$("<img>");j.attr('src','images/'+v.type+'.png');i.append(j);i.on("click",function(){(p.onclick)(v)});h.append(i);if(p.level==0){var l=v.items;if(l&&l.length>0){var m=$("<ul></ul>");$.each(l,function(a,b){var c=$("<li></li>");var d=$("<a></a>");d.append(b.name);d.on("click",function(){(p.onclick)(b)});c.append(d);m.append(c);var e=b.items;if(e!=null&&e.length>0){var f=$("<ul></ul>");r(e,f,p);c.addClass('menu-rightarrow');c.append(f)}});h.append(m)}}o.append(h)};if(p.level==2&&p.parentMenu!=null&&p.rendto!=null){if(v.type==p.parentMenu){var l=v.items;if(l&&l.length>0){var m=$("<ul></ul>");m.css('margin-top','18px');$.each(l,function(a,b){var c=$("<li></li>");var d=$("<a></a>");var e=$("<img>");e.attr('src','images/'+b.type+'.png');e.attr('alt',b.name);d.css('padding','10px');d.css('cursor','pointer');d.append(e);d.on("click",function(){(p.onclick)(b)});c.append(d);c.css('margin-top','10px');m.append(c);var f=b.items;if(f!=null&&f.length>0){var g=$("<ul></ul>");r(f,g,p);c.addClass('menu-rightarrow');c.append(g)}});$("#"+p.rendto).append(m)}}}})};var s={init:function(d){return this.each(function(){var a=$(this);var b=a.data('sgMenu');if(typeof(b)=='undefined'){var c={datatype:'json',url:'tree.json',level:0,parentMenu:null,rendto:null,onclick:function(){}};b=$.extend({},c,d);a.data('sgMenu',b)}else{b=$.extend({},b,d)}q(a,b)})},destroy:function(b){return $(this).each(function(){var a=$(this);a.removeData('sgMenu')})},val:function(a){var b=this.eq(0).html();return b}};$.fn.sgMenu=function(){var a=arguments[0];if(s[a]){a=s[a];arguments=Array.prototype.slice.call(arguments,1)}else if(typeof(a)=='object'||!a){a=s.init}else{$.error('Method '+a+' does not exist on jQuery.sgMenu');return this}return a.apply(this,arguments)}})(jQuery);(function($){var l=function(b,c){$.ajax({type:"POST",async:false,url:c.url,dataType:"json",success:function(a){m(a,b,c)}});b.css("width",c.width);n(b,c)};var m=function(h,i,j){$.each(h,function(k,v){var e=$("<li class='accordion_li'></li>");var f=$("<ul></ul>");e.append(v.title);if(v.type==2){e.append("("+v.items.length+")");$.each(v.items,function(a,b){var c=$("<li class='accordion_ul_li'></li>");if(b.isurl){var d=$("<a></a>");d.append(b.content);d.attr("href","javascript:void(0);");d.attr("title","点击查看详情");d.on("click",function(){(j.onclick)(b)});c.append(d)};f.append(c)})}else if(v.type==3){$.each(v.items,function(a,b){var c=$("<div></div>");c.css("width",j.width-2);c.css("height",60);c.css("border","1px solid #999");c.css("overflow","auto");c.append(b.content);f.append(c)})}else if(v.type==4){var g=$("<div></div>");g.load(v.src);g.css("width",j.width-2);g.css("height",j.div_height);g.css("overflow-x","hidden");f.append(g)}else{$.each(v.items,function(a,b){var c=$("<li class='accordion_ul_li'></li>");c.append(b.key+"：");if(b.isurl){var d=$("<a></a>");d.append(b.value);d.attr("href","javascript:void(0);");d.attr("title","点击查看详情");d.on("click",function(){(j.onclick)(b)});c.append(d)}else{c.append(b.value)};f.append(c)})};i.append(e);i.append(f)})};var n=function(b,c){if(!c.isexpend){b.children("ul").hide();b.children("ul:first").show();b.children("li:first").removeClass();b.children("li:first").addClass('accordion_li_open');$("li",b).click(function(){var a=$(this).next();if((a.is("ul"))&&(a.is(":visible"))){return false};if((a.is("ul"))&&(!a.is(":visible"))){b.children("ul:visible").slideUp("normal");a.slideDown("normal");b.children("li").removeClass();b.children("li").addClass("accordion_li");$(this).removeClass();$(this).addClass("accordion_li_open");return false}})}};var o={init:function(d){return this.each(function(){var a=$(this);var b=a.data("sgAccordion");if(typeof(b)=="undefined"){var c={url:"accordion.json",width:300,div_height:200,isexpend:true,onclick:function(){}};b=$.extend({},c,d);a.data("sgAccordion",b)}else{b=$.extend({},b,d)}l(a,b)})},destroy:function(b){return $(this).each(function(){var a=$(this);a.removeData("sgAccordion")})},val:function(a){var b=this.eq(0).html();return b}};$.fn.sgAccordion=function(){var a=arguments[0];if(o[a]){a=o[a];arguments=Array.prototype.slice.call(arguments,1)}else if(typeof(a)=="object"||!a){a=o.init}else{$.error("Method "+a+" does not exist on jQuery.sgAccordion");return this};return a.apply(this,arguments)}})(jQuery);(function($){var C=function(g,h){var i=$('<div></div>');var j=$('<div></div>');var l=$('<div></div>');var m=$('<div></div>');var n=$('<div></div>');var o=$('<div></div>');g.css('width',h.width+"px");if(h.title){i.addClass('mDiv');i.html(h.title);g.append(i)};if(h.searchitems){n.addClass('sDiv');var p=$('<form></form>');$.each(h.searchitems,function(k,v){var a=$("<div></div>");a.addClass("pGroup");var b=$('<span class="label"></span>');b.append(v.display+":");a.append(b);if(v.type){var c=$("<input type='"+v.type+"' name='"+v.name+"'>");if(v.width){c.css('width',v.width)};a.append(c);if(v.value){c.val(v.value)}}else{a.append(v.html)};p.append(a)});n.append(p);n.append("<div class='pGroup'><a type='button' class='button'>"+"<span class='button_span'><span class='pSearch'>查询</span>"+"</span></a></div>");if(h.exporturl){n.append("<div class='pGroup'><a type='button' class='button'>"+"<span class='button_span'><span>导出Excel</span></span></a></div>")};$('.pGroup a.button:first',n).click(function(){var a=$(g).data('sgDatagrid');var b={};b=a.query;var c=$("form",n).serializeArray();$.each(c,function(k,v){if(v.value!=null&&v.value.trim().length>0&&v.value!='null')b[v.name]=v.value;else b[v.name]=null});h.query=b;E(g,h)});if($('.pGroup a.button',n).length>1){$('.pGroup a.button:last',n).click(function(){var a=$("form",n).serializeArray();var b=[];$.each(a,function(k,v){if(v.value!=null&&v.value.trim().length>0&&v.value!='null'){b.push(v.name+"="+v.value)}});var c=$(g).data('sgDatagrid');var d=c.query;for(var e in d){b.push(e+"="+d[e])};var f=h.exporturl+"?"+b.join('&');window.location.href=f})};g.append(n)};if(h.buttons){m.addClass('tDiv');$.each(h.buttons,function(k,v){if(!v.separator){var a=$('<div></div>');a.addClass("fbutton");var b=$('<span></span>');b.addClass(v.bclass);b.html(v.name);a.on('click',function(){(v.onpress)(this)});a.append(b);m.append(a)}else{m.append("<div class='btnseparator'></div>")}});g.append(m)};if(h.colModel){j.addClass('hDiv');var q=$('<table></table>');q.attr('cellpadding',0);q.attr('cellspacing',0);var r=$('<thead></thead>');var s=$('<tr></tr>');if(h.colid){var t=$('<th axis="checkbox"></th>');var u=$('<input type="checkbox" name="checkall" style="margin-top:8px;" />');if(!h.isNotCheckall){t.append(u)}else{t.attr('width','13px')};u.change(function(){if(!u.attr("checked")){u.attr("checked",true);$('input[type=checkbox]',l).prop("checked",true);$('input[type=checkbox]',l).attr("checked","checked")}else if(u.attr("checked")){u.removeAttr("checked");$('input[type=checkbox]',l).prop("checked",null);$('input[type=checkbox]',l).removeAttr("checked")}});s.append(t)};if(h.rownumbers){var w=$('<th axis="rownumbers">序号</th>');w.width(34);s.append(w)};$.each(h.colModel,function(k,v){var a=$('<th></th>');a.attr('axis',v.name);a.width(v.width);var b=$('<div></div>');if(v.sortable){a.addClass('sorted');b.attr('axis',v.name);if(v.name==h.sortname){if(h.sortname=='desc'){b.attr('title','降序');b.attr('sort','desc')}else{b.attr('title','升序');b.attr('sort','asc')};b.addClass('s'+h.sortorder);b.addClass('thOver')};b.hover(function(){if(!$(this).attr('class')){$(this).addClass('sdesc')}},function(){if($(this).attr('class').indexOf('thOver')>=0){}else{$(this).removeClass()}});b.click(function(){if($(this).attr('class').indexOf('thOver')>=0){if($(this).attr('class')=='sdesc thOver'){$(this).removeClass();$(this).attr('title','升序');$(this).attr('sort','asc');$(this).addClass('sasc thOver')}else{$(this).removeClass();$(this).attr('title','降序');$(this).attr('sort','desc');$(this).addClass('sdesc thOver')}}else{$('.sorted .thOver',j).each(function(){$(this).removeClass();$(this).removeAttr('title')});$(this).attr('title','降序');$(this).attr('sort','desc');$(this).addClass('thOver')};K(g,h,$(this))})};if(v.editable){a.attr('editable',v.editable)};if(v.disabled){a.attr('disabled',v.disabled)};if(v.isnum){a.attr('isnum',v.isnum)};if(v.isrequest){a.attr('isrequest',v.isrequest)};if(v.html){a.attr('ishtml',true)};b.append(v.display);a.append(b);s.append(a)});r.append(s);q.append(r);j.append(q);g.append(j)};l.addClass('bDiv');l.css('height',h.height+'px');var x=$('<table></table>');x.attr('id',g.attr("id")+"_tbl");x.attr('cellpadding',0);x.attr('cellspacing',0);x.attr('border',0);l.append(x);g.append(l);if(h.usepager){o.addClass('pDiv');var y=Math.ceil(h.total/h.rp);if(h.useRp){var z=$('<div></div>');z.addClass('pGroup');var A=$('<select></select>');A.attr('id',g.attr("id")+'_rp');A.attr('name','rp');$.each(h.rpOptions,function(k,v){var a=$('<option></option>');if(h.rp==v){a.attr('selected','selected')};a.attr('value',v);a.html(v+'&nbsp;&nbsp;');A.append(a)});z.append(A);o.append(z);o.append("<div class='btnseparator'></div>");$('select',o).change(function(){h.rp=+this.value;E(g,h)})};var B=$('<div class="pGroup"><div class="pFirst button"></div><div class="pPrev button"></div></div><div class="btnseparator"></div>'+'<div class="pGroup"><span class="pControl">第<input type="text" size="4" value="'+h.page+'">页&nbsp;&nbsp;共<span>'+y+'</span>页</span></div><div class="btnseparator"></div>'+'<div class="pGroup"><div class="pNext button"></div><div class="pLast button"></div></div><div class="btnseparator"></div>'+'<div class="pGroup"><div class="pReload button"></div></div><div class="btnseparator"></div><div class="pGroup"><span class="pPageStat"></span></div>');o.append(B);$('.pReload',o).click(function(){$('input[type=checkbox]',j).removeAttr("checked");E(g,h)});$('.pFirst',o).click(function(){$('input[type=checkbox]',j).removeAttr("checked");J('first',g,h)});$('.pPrev',o).click(function(){$('input[type=checkbox]',j).removeAttr("checked");J('prev',g,h)});$('.pNext',o).click(function(){$('input[type=checkbox]',j).removeAttr("checked");J('next',g,h)});$('.pLast',o).click(function(){$('input[type=checkbox]',j).removeAttr("checked");J('last',g,h)});$('.pControl input',o).keydown(function(e){$('input[type=checkbox]',j).removeAttr("checked");if(e.keyCode==13){J('input',g,h)}});g.append(o)};E(g,h)};var D=function(a,b){$('.pControl input',a).val(b.page);$('.pControl span',a).html(b.pages);var c=(b.page-1)*b.rp+1;var d=c+b.rp-1;if(b.total<d){d=b.total};var e=b.pagestat;e=e.replace(/{from}/,c);e=e.replace(/{to}/,d);e=e.replace(/{total}/,b.total);$('.pPageStat',a).html(e)};var E=function(b,c){$('.pPageStat',b).html(c.procmsg);$('.pReload',b).addClass('loading');var d={};if(c.usepager){d['pageNo']=c.page;d['pageSize']=c.rp;d['filter']=c.query}else{d=c.query};if(c.order){d['order']=c.sortname;d['is_desc']=(c.sortorder=="desc"?true:false)};b.data('sgDatagrid',c);$.ajax({type:"POST",async:false,contentType:"application/json",url:c.url,data:JSON.stringify(d),dataType:"json",success:function(a){if(c.usepager){if(a){c.total=a.total;c.page=a.index};F(a,b,c)}else{G(a,b,c)}}})};var F=function(r,s,t){$('.pReload',s).removeClass('loading');if(!r){$('.pPageStat',s).html(t.errormsg);return false};t.pages=Math.ceil(t.total/t.rp);D(s,t);var u=$('<tbody></tbody>');var w=$('.hDiv',s);$.each(r.items,function(i,o){var p=$('<tr></tr>');if(t.hiddenId){var q=$("<input type='hidden' id='"+t.hiddenId+"' name='"+t.hiddenId+"' />");q.val(o[t.hiddenId]);p.append(q)};$('thead tr:first th',w).each(function(){var a=$('<td></td>');var b=$(this).attr('editable');var c=$(this).attr('disabled');var d=$(this).attr('isnum');var e=$(this).attr('isrequest');var f=$(this).attr('axis');if(f=='checkbox'){var g=$('<input type="checkbox" style="margin-top:8px;" />');g.attr('axis',o[t.colid]);g.attr('name',s.attr("id")+"_chx");g.attr('value',o[t.colid]);g.data('data',o);a.append(g);g.change(function(){if(!g.attr("checked")){g.attr("checked","checked")}else if(g.attr("checked")){g.removeAttr("checked")}})}else if(f=='rownumbers'){var h=$(this).css('width');a.css('width',h);var j=$("#"+s.attr("id")+'_rp').val()*(t.page-1)+i+1;a.append(j)}else{var h=$(this).css('width');a.css('width',h);var l=$('<div></div>');l.css('padding-top','5px');l.css('line-height','16px');if(b){a.css('margin',0);a.css('padding',2);var m=$("<input type='text' />");m.attr('name',f);m.width(h);m.css('margin',0);m.css('padding',0);if(c){m.attr('disabled','disabled');m.val(o[f])};if(d){m.attr('type','number')};if(e){m.attr('required',e)};if(ishtml){$.each(t.colModel,function(k,v){if(v.name==f&&v.html!=null){l.html(v.html)}})}else{l.append(m)}}else{var n=false;$.each(t.colModel,function(k,v){if(v.name==f&&v.formatter!=null){n=true;l.html((v.formatter)(o[f],o))}});if(!n)l.html(o[f])};a.append(l)};p.append(a);a=null});u.append(p)});var x="#"+s.attr("id")+"_tbl";var y=$(x);$('tr',y).unbind();$(y).empty();y.append(u);$('tr:odd',y).addClass('normalRow');u=null;w=null;y=null;x=null;r=null};var G=function(s,t,u){var w=$('<tbody></tbody>');var x=$('.hDiv',t);$.each(s,function(i,p){var q=$('<tr></tr>');if(u.hiddenId){var r=$("<input type='hidden' id='"+u.hiddenId+"' name='"+u.hiddenId+"' />");r.val(p[u.hiddenId]);q.append(r)};$('thead tr:first th',x).each(function(){var a=$('<td></td>');var b=$(this).attr('editable');var c=$(this).attr('disabled');var d=$(this).attr('isnum');var e=$(this).attr('isrequest');var f=$(this).attr('ishtml');var g=$(this).attr('axis');if(g=='checkbox'){var h=$('<input type="checkbox" style="margin-top:8px;" />');h.attr('axis',p[u.colid]);h.attr('name',t.attr("id")+"_chx");h.attr('value',p[u.colid]);h.data('data',p);a.append(h);h.change(function(){if(!h.attr("checked")){h.attr("checked","checked")}else if(h.attr("checked")){h.removeAttr("checked")};if(u.chkChange){u.chkChange(i,p,h.attr("checked"))}})}else if(g=='rownumbers'){var j=$(this).css('width');a.css('width',j);var l=i+1;a.append(l)}else{var j=$(this).css('width');a.css('width',j);var m=$('<div></div>');m.css('padding-top','5px');m.css('line-height','16px');if(b){a.css('margin',0);a.css('padding',2);var n=$("<input type='text' />");n.attr('name',g);n.width(j);n.css('margin',0);n.css('padding',0);if(c){n.attr('disabled','disabled');n.val(p[g])};if(d){n.attr('type','number')};if(e){n.attr('required',e)};if(f){$.each(u.colModel,function(k,v){if(v.name==g&&v.html!=null){m.html(v.html)}})}else{m.append(n)}}else{var o=false;$.each(u.colModel,function(k,v){if(v.name==g&&v.formatter!=null){o=true;m.html((v.formatter)(p[g],p))}});if(!o)m.html(p[g])};a.append(m)};q.append(a);a=null});w.append(q)});var y="#"+t.attr("id")+"_tbl";var z=$(y);$('tr',z).unbind();$(z).empty();z.append(w);$('tr:odd',z).addClass('normalRow');w=null;x=null;z=null;y=null;s=null};var H=function(s,t,u){$('.pReload',t).removeClass('loading');if(!s){$('.pPageStat',t).html(u.errormsg);return false};u.pages=Math.ceil(u.total/u.rp);D(t,u);var w="#"+t.attr("id")+"_tbl";var x=$(w);$('tr',x).unbind();var y=$('tbody',x);if(!y.get(0)){y=$('<tbody></tbody>');x.append(y)};var z=$('.hDiv',t);$.each(s.items,function(i,p){var q=$('<tr></tr>');if(u.hiddenId){var r=$("<input type='hidden' id='"+u.hiddenId+"' name='"+u.hiddenId+"' />");r.val(p[u.hiddenId]);q.append(r)};$('thead tr:first th',z).each(function(){var a=$('<td></td>');var b=$(this).attr('editable');var c=$(this).attr('disabled');var d=$(this).attr('isnum');var e=$(this).attr('isrequest');var f=$(this).attr('ishtml');var g=$(this).attr('axis');if(g=='checkbox'){var h=$('<input type="checkbox" style="margin-top:8px;" />');h.attr('axis',p[u.colid]);h.attr('name',t.attr("id")+"_chx");h.attr('value',p[u.colid]);h.data('data',p);a.append(h);h.change(function(){if(!h.attr("checked")){h.attr("checked","checked")}else if(h.attr("checked")){h.removeAttr("checked")}})}else if(g=='rownumbers'){var j=$(this).css('width');a.css('width',j);var l=$("#"+t.attr("id")+'_rp').val()*(u.page-1)+i+1;a.append(l)}else{var j=$(this).css('width');a.css('width',j);var m=$('<div></div>');m.css('padding-top','5px');m.css('line-height','16px');if(b){a.css('margin',0);a.css('padding',2);var n=$("<input type='text' />");n.attr('name',g);n.width(j);n.css('margin',0);n.css('padding',0);if(c){n.attr('disabled','disabled');n.val(p[g])};if(d){n.attr('type','number')};if(e){n.attr('required',e)};if(f){$.each(u.colModel,function(k,v){if(v.name==g&&v.html!=null){m.html(v.html)}})}else{m.append(n)}}else{var o=false;$.each(u.colModel,function(k,v){if(v.name==g&&v.formatter!=null){o=true;m.html((v.formatter)(p[g],p))}});if(!o)m.html(p[g])};a.append(m)};q.append(a);a=null});y.append(q)});$('tr:odd',x).addClass('normalRow');y=null;z=null;x=null;w=null;s=null};var I=function(s,t,u){var w="#"+t.attr("id")+"_tbl";var x=$(w);$('tr',x).unbind();var y=$('tbody',x);if(!y.get(0)){y=$('<tbody></tbody>');x.append(y)};var z=$('.hDiv',t);$.each(s,function(i,p){var q=$('<tr></tr>');if(u.hiddenId){var r=$("<input type='hidden' id='"+u.hiddenId+"' name='"+u.hiddenId+"' />");r.val(p[u.hiddenId]);q.append(r)};$('thead tr:first th',z).each(function(){var a=$('<td></td>');var b=$(this).attr('editable');var c=$(this).attr('disabled');var d=$(this).attr('isnum');var e=$(this).attr('isrequest');var f=$(this).attr('ishtml');var g=$(this).attr('axis');if(g=='checkbox'){var h=$('<input type="checkbox" style="margin-top:8px;" />');h.attr('axis',p[u.colid]);h.attr('name',t.attr("id")+"_chx");h.attr('value',p[u.colid]);h.data('data',p);a.append(h);h.change(function(){if(!h.attr("checked")){h.attr("checked","checked")}else if(h.attr("checked")){h.removeAttr("checked")};if(u.chkChange){u.chkChange(i,p,h.attr("checked"))}})}else if(g=='rownumbers'){var j=$(this).css('width');a.css('width',j);var l=i+1;a.append(l)}else{var j=$(this).css('width');a.css('width',j);var m=$('<div></div>');m.css('padding-top','5px');m.css('line-height','16px');if(b){a.css('margin',0);a.css('padding',2);var n=$("<input type='text' />");n.attr('name',g);n.width(j);n.css('margin',0);n.css('padding',0);n.val(p[g]);if(c){n.attr('disabled','disabled')};if(d){n.attr('type','number')};if(e){n.attr('required',e)};if(f){$.each(u.colModel,function(k,v){if(v.name==g&&v.html!=null){m.html(v.html)}})}else{m.append(n)}}else{var o=false;$.each(u.colModel,function(k,v){if(v.name==g&&v.formatter!=null){o=true;m.html((v.formatter)(p[g],p))}});if(!o){m.html(p[g])}};a.append(m)};q.append(a);a=null});y.append(q)});$('tr:odd',x).addClass('normalRow');y=null;z=null;x=null;w=null;s=null};var J=function(a,b,c){c.newp=c.page;switch(a){case'first':c.newp=1;break;case'prev':if(c.page>1){c.newp=parseInt(c.page)-1};break;case'next':if(c.page<c.pages){c.newp=parseInt(c.page)+1};break;case'last':c.newp=c.pages;break;case'input':var d=parseInt($('.pControl input',b).val());if(isNaN(d)){d=1};if(d<1){d=1}else if(d>c.pages){d=c.pages};$('.pControl input',b).val(d);c.newp=d;break};if(c.newp==c.page){return false};c.page=c.newp;E(b,c)};var K=function(a,b,c){b.sortname=c.attr('axis');b.sortorder=c.attr("sort");E(a,b)};var L={init:function(d){return this.each(function(){var a=$(this);var b=a.data('sgDatagrid');if(typeof(b)=='undefined'){var c={title:false,width:600,height:240,url:'datagrid.json',exporturl:false,method:'POST',dataType:'json',errormsg:'连接出错！',usepager:false,page:1,total:1,pages:1,useRp:false,rp:10,rpOptions:[10,15,20,30,50],pagestat:'显示 {from} 到 {to} 条&nbsp;&nbsp;&nbsp;&nbsp;共 {total} 条纪录',procmsg:'正在读取数据……！',query:{},nomsg:'没有数据！',onChangeSort:false,onSuccess:false,onError:false,onSubmit:false,order:false,isNotCheckall:false};b=$.extend({},c,d);a.data('sgDatagrid',b)}else{b=$.extend({},b,d)};C(a,b)})},reload:function(a){var b=$(this).data('sgDatagrid');b=$.extend({},b,a);if(b.isFixed==false){}else{$(this).data('sgDatagrid',b)};E($(this),b)},destroy:function(b){return $(this).each(function(){var a=$(this);a.removeData('sgDatagrid');a.empty()})},val:function(a){var b=this.eq(0).html();return b},appendData:function(a){var b=$(this).data('sgDatagrid');if(b.isFixed==false){}else{$(this).data('sgDatagrid',b)};if(b.usepager){if(json){b.total=json.total;b.page=json.index};H(a,$(this),b)}else{I(a,$(this),b)}}};$.fn.sgDatagrid=function(){var a=arguments[0];if(L[a]){a=L[a];arguments=Array.prototype.slice.call(arguments,1)}else if(typeof(a)=='object'||!a){a=L.init}else{$.error('Method '+a+' does not exist on jQuery.sgDatagrid');return this};return a.apply(this,arguments)}})(jQuery);(function($){var f=function(c,d){c.css("width",d.width);$("#"+c.attr("id")+" .tab_content").hide();$("#"+c.attr("id")+" ul.tabs li:first").addClass("active").show();var e=$("#"+c.attr("id")+" .tab_content:first");e.load(e.attr("src"));e.attr("loaded",true);e.css("height",d.height);e.show();$("#"+c.attr("id")+" ul.tabs li").click(function(){$("#"+c.attr("id")+" ul.tabs li").removeClass("active");$(this).addClass("active");$("#"+c.attr("id")+" .tab_content").hide();var a=$(this).find("a").attr("href");var b=$(a);if(!b.attr("loaded")){b.load(b.attr("src"));b.attr("loaded",true);b.css("height",d.height)};b.fadeIn();return false})};var g={init:function(d){return this.each(function(){var a=$(this);var b=a.data('sgTab');if(typeof(b)=='undefined'){var c={width:300,height:200,onclick:function(){}};b=$.extend({},c,d);a.data('sgTab',b)}else{b=$.extend({},b,d)};f(a,b)})},destroy:function(b){return $(this).each(function(){var a=$(this);a.removeData('sgTab')})},val:function(a){var b=this.eq(0).html();return b}};$.fn.sgTab=function(){var a=arguments[0];if(g[a]){a=g[a];arguments=Array.prototype.slice.call(arguments,1)}else if(typeof(a)=='object'||!a){a=g.init}else{$.error('Method '+a+' does not exist on jQuery.sgTab');return this};return a.apply(this,arguments)}})(jQuery);(function($){var p=function(c,d){var e=$('<div></div>');var f=$('<div></div>');var g=$('<div></div>');var h=$('<div></div>');var i=$('<div></div>');var j=$('<div></div>');var l=$('<a href="javascript:void(0);"></a>');var m=$('<div></div>');var n=$(window).outerWidth();var o=$(window).outerHeight();f.addClass('window');f.attr('id',d.id);f.width(d.width);f.css('left',(n-d.width)/2);f.css('top',(o-d.height-60)/2);f.css('z-index',d.z_index);l.addClass('window-tool-close');l.click(function(){c.removeData('sgWindow');var a="#"+d.id+"_mask";$(a).remove();var b="#"+d.id;$(b).remove()});j.addClass('window-tool');j.append(l);i.addClass('window-title');i.append(d.title);g.addClass('window-header');g.width(d.width);g.append(i);g.append(j);f.append(g);h.addClass('window-body');h.height(d.height-25);h.load(d.url,{},d.success);f.append(h);if(d.buttons){m.addClass('window-footer');m.addClass('window-body-noheader');m.addClass('window-body-noborder');m.css('text-align','center');m.css('padding-top','5px');m.width(d.width);$.each(d.buttons,function(k,v){if(v.type=='submit'){var a=$('<input type="submit">');a.attr('form',d.form);a.val(v.name);a.css('margin-right','10px');if(v.onpress){a.click(function(){(v.onpress)()})};m.append(a)}else if(v.type=='reset'){var a=$('<input type="reset">');a.attr('form',d.form);a.val(v.name);a.css('margin-right','10px');if(v.onpress){a.click(function(){(v.onpress)()})};m.append(a)}else{var a=$('<input type="button">');a.attr('form',d.form);a.val(v.name);a.css('margin-right','10px');if(v.onpress){a.click(function(){(v.onpress)()})};m.append(a)}});f.append(m)};f.show();e.addClass('window-mask');e.attr('id',d.id+"_mask");e.show();$(document.body).append(e);$(document.body).append(f);r.Register(f,g)};var q={init:function(d){return this.each(function(){var a=$(this);var b=a.data('sgWindow');if(typeof(b)=='undefined'){var c={id:false,title:false,url:false,width:300,height:200,z_index:9000};b=$.extend({},c,d);a.data('sgWindow',b)}else{b=$.extend({},b,d)};p(a,b)})},close:function(a){var b="#"+a.id+"_mask";$(b).remove();$("#"+a.id).remove();$(this).removeData('sgWindow')},destroy:function(b){return $(this).each(function(){var a=$(this);a.removeData('sgWindow')})},val:function(a){var b=this.eq(0).html();return b}};$.fn.sgWindow=function(){var a=arguments[0];if(q[a]){a=q[a];arguments=Array.prototype.slice.call(arguments,1)}else if(typeof(a)=='object'||!a){a=q.init}else{$.error('Method '+a+' does not exist on jQuery.sgWindow');return this};return a.apply(this,arguments)};var r=function(){var c;var d;var e;var f;var g=false;var h;var i;var j=function(a){return a.ownerDocument||a.document};var k=function(a){if(f){a=a||window.event;c=document.body.clientWidth;d=document.documentElement.scrollHeight;$("#jd_dialog_m_b_1").css("display","");g=true;h={x:$(f).offset().left,y:$(f).offset().top};i={x:a.screenX,y:a.screenY};$(document).bind("mousemove",l);$(document).bind("mouseup",m);if(a.preventDefault){a.preventDefault()}else{a.returnValue=false}}};var l=function(a){if(g){a=a||window.event;var b={x:a.screenX,y:a.screenY};h.x=h.x+(b.x-i.x);h.y=h.y+(b.y-i.y);i=b;$(f).css("left",h.x+"px");$(f).css("top",h.y+"px");if(a.preventDefault){a.preventDefault()}else{a.returnValue=false}}};var m=function(a){if(g){a=a||window.event;$("#jd_dialog_m_b_1").css("display","none");n();g=false}};var n=function(){if(e){$(e.document).unbind("mousemove");$(e.document).unbind("mouseup")}};return{Register:function(a,b){f=a;e=b;$(e).bind("mousedown",k)}}}()})(jQuery);(function($){var e=null;function clear(b){$('input,select,textarea',b).each(function(){var t=this.type,tag=this.tagName.toLowerCase();if(t=='text'||t=='hidden'||t=='password'||tag=='textarea'){this.value=''}else if(t=='file'){var a=$(this);a.after(a.clone().val(''));a.remove()}else if(t=='checkbox'||t=='radio'){this.checked=false}else if(tag=='select'){this.selectedIndex=-1}})}function init(a){if($.fn.validatebox){var t=$(a);var b=t.find('[validtype]');b.focus(function(){$('.show_validate_message').remove();$(this).validatebox()})}}function validate(a){if($.fn.validatebox){var t=$(a);t.find('[validtype]').validatebox('validate');var b=t.find('[isvalid=false]');if(b!=null&&b.length>0){b.filter(':first').focus();e=b.filter(':first')}else{$('.show_validate_message').remove()};return b.length==0};return true}var f={init:function(d){return this.each(function(){var a=$(this);var b=a.data('sgForm');if(typeof(b)=='undefined'){var c={url:null,data:null,success:function(){}};b=$.extend({},c,d);a.data('sgFrom',b)}else{b=$.extend({},b,d)}init(a)})},destroy:function(b){return $(this).each(function(){var a=$(this);a.removeData('sgForm')})},val:function(a){var b=this.eq(0).html();return b},onSubmit:function(b){if(validate(this)){$.post(b.url,$(this).serialize(),function(a){(b.success)(a)},"json")}else{return false}},loadForm:function(b){$.post(b.url,b.data,function(a){(b.success)(a)},"json");init(this[0])},validate:function(){return validate(this[0])},clear:function(){return this.each(function(){clear(this)})}};$.fn.form=function(){var a=arguments[0];if(f[a]){a=f[a];arguments=Array.prototype.slice.call(arguments,1)}else if(typeof(a)=='object'||!a){a=f.init}else{$.error('Method '+a+' does not exist on jQuery.sgForm');return this};return a.apply(this,arguments)}})(jQuery);(function($){var m="images/";var n=m+"base.gif";var o=m+"join.gif";var p=m+"joinbottom.gif";var q=m+"minus.gif";var r=m+"minusbottom.gif";var s=m+"plus.gif";var t=m+"plusbottom.gif";var u=m+"empty.gif";var w=m+"line.gif";var x=m+"folderopen.gif";var y=m+"page.gif";var z=m+"folder.gif";var A=new Array();A[true]=q;A[false]=s;var B=new Array();B[true]=r;B[false]=t;var C=new Array();C[true]=x;C[false]=z;var D=function(a){n=a+"base.gif";o=a+"join.gif";p=a+"joinbottom.gif";q=a+"minus.gif";r=a+"minusbottom.gif";s=a+"plus.gif";t=a+"plusbottom.gif";u=a+"empty.gif";w=a+"line.gif";x=a+"folderopen.gif";y=a+"page.gif";z=a+"folder.gif";A=new Array();A[true]=q;A[false]=s;B=new Array();B[true]=r;B[false]=t;C=new Array();C[true]=x;C[false]=z};var E=function(d,e){D(e.picpath);var f=$("<ul></ul>");f.level=0;var g=$("<img align='absmiddle' />");g.attr('src',n);var h=F('根节点',null,e);var i=d;f.append(g).append(h);i.append(f);var j=$("<div></div>");j.parent=f;i.append(j);var k=e.isexpend;var l={};$.ajax({type:"POST",contentType:'application/json',url:e.url,data:JSON.stringify(l),dataType:'json',success:function(a){if(a){G(a,f.level,j,k,e)}},error:function(a,b,c){alert(a.status);alert(a.readyState);alert(b)}})};var F=function(a,b,c){var d=$("<span></span>");d.append(a).addClass("caption");if(b!=null){d.on('mouseover',function(){d.removeClass();d.addClass("captionActive")});d.on('mouseout',function(){d.removeClass();d.addClass("caption")});d.on('click',function(){(c.onclick)(b)});d.css('cursor','pointer')};return d};var G=function(e,f,g,h,i){var j=e.length;var l=1;$.each(e,function(k,v){var b=$("<ul></ul>");var c=null;if(v.items!=null&&v.items.length>=1){c=$("<div></div>");c.parent=b};b.level=f+1;b.isFirstChild=(l==1?true:false);b.isLastChild=(l==j?true:false);b.parent=g;l=l+1;g.append(b);H(v,b,h);I(v,b,h);var d=F(v.name,v,i);b.append(d);if(v.items!=null&&v.items.length>=1){b.append(c);G(v.items,b.level,c,h,i);b.expand=function(a){if(a==null){if(c.css('display')=="block"){c.css('display','none');return false}else{c.css('display','block');return true}}else{if(a){c.css('display','block')}else{c.css('display','none')}}};b.expand(h)}})};var H=function(b,c,d){var e=(b.items!=null&&b.items.length>=1);for(var i=1;i<c.level;i++){var f=c;for(var j=c.level;j>i;j--){f=f.parent.parent};var g=null;if(f.isLastChild){g=$("<img align='absmiddle' />");g.attr('src',u)}else{g=$("<img align='absmiddle' />");g.attr('src',w)};c.append(g)};var h;if(e){if(!c.isLastChild){h=$("<img align='absmiddle' />");h.attr('src',A[d])}else{h=$("<img align='absmiddle' />");h.attr('src',B[d])};c.append(h);h.on('click',function(){var a=c.expand();if(!c.isLastChild){if(a){c.children("img[src*='folder.gif']").attr('src',C[a]);c.children("img[src*='plus.gif']").attr('src',A[a])}else{c.children("img[src*='open.gif']").attr('src',C[a]);c.children("img[src*='minus.gif']").attr('src',A[a])}}else{if(a){c.children("img[src*='folder.gif']").attr('src',C[a]);c.children("img[src*='plusbottom.gif']").attr('src',B[a])}else{c.children("img[src*='open.gif']").attr('src',C[a]);c.children("img[src*='minusbottom.gif']").attr('src',B[a])}}});c.expandBtn=h}else{if(!c.isLastChild){h=$("<img align='absmiddle' />");h.attr('src',o)}else{h=$("<img align='absmiddle' />");h.attr('src',p)};c.append(h)}};var I=function(a,b,c){var d=(a.items!=null&&a.items.length>=1);var e=null;if(d){e=$("<img align='absmiddle' />");e.attr('src',C[d])}else{e=$("<img align='absmiddle' />");e.attr('src',y)};b.append(e)};var J={init:function(d){return this.each(function(){var a=$(this);var b=a.data('sgTree');if(typeof(b)=='undefined'){var c={isexpend:true,datatype:'json',url:'tree.json',onclick:function(){},picpath:'images/'};b=$.extend({},c,d);a.data('sgTree',b)}else{b=$.extend({},b,d)};E(a,b)})},reload:function(a){var b=$(this).data('sgTree');b=$.extend({},b,a);if(b.isFixed==false){}else{$(this).data('sgTree',b)};$(this).empty();E($(this),b)},destroy:function(b){return $(this).each(function(){var a=$(this);a.removeData('sgTree')})},val:function(a){var b=this.eq(0).html();return b}};$.fn.sgTree=function(){var a=arguments[0];if(J[a]){a=J[a];arguments=Array.prototype.slice.call(arguments,1)}else if(typeof(a)=='object'||!a){a=J.init}else{$.error('Method '+a+' does not exist on jQuery.sgTree');return this};return a.apply(this,arguments)}})(jQuery);