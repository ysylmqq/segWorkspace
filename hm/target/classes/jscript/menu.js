/*
 * level:0 菜单分级显示
 * level:1 只显示一级菜单
 * level:2 只显示二级菜单
 */
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
        	if(settings.level==1||settings.level==0){
        		var node = $("<li></li>");
                var node_a = $("<a></a>");
                var node_img = $("<img>");
                node_img.attr('src','images/'+v.type+'.png');
                node_a.append(node_img);
                if(v.type==window.mainModule){
                	node.css("background",'#1b4f7b');
                }
                node_a.on("click",function(){
                	//debugger;
                	$(this).parent().parent().children().css("background",'#4791d2');
                	$(this).parent().css("background",'#1b4f7b');
                    (settings.onclick)(v);
                });
                node.append(node_a);
                if(settings.level==0){
                	var subjson = v.items;
                    if(subjson&&subjson.length>0){
                        var subpanel =  $("<ul></ul>");
                        $.each(subjson,function(ky,va){
                            var subnode = $("<li></li>");
                            var sub_node_a = $("<a></a>");
                            sub_node_a.append(va.name);
                            sub_node_a.on("click",function(){
                                (settings.onclick)(va);
                            });
                            subnode.append(sub_node_a);
                            subpanel.append(subnode);
                            var node_json = va.items;
                            if(node_json!=null&&node_json.length>0){
                                var sp =  $("<ul></ul>");
                                createSubNode(node_json,sp,settings);
                                subnode.addClass('menu-rightarrow');
                                subnode.append(sp);
                            }
                        });
                        node.append(subpanel);
        
                    }
                }
                
                panel.append(node);
        	};
        	
        	if(settings.level==2&&settings.parentMenu!=null&&settings.rendto!=null){
        		if(v.type==settings.parentMenu){
        			var subjson = v.items;
                    if(subjson&&subjson.length>0){
                        var subpanel =  $("<ul></ul>");
                        subpanel.css('margin-top','18px');
                        ismainpage = false;
                        $.each(subjson,function(ky,va){
                            var subnode = $("<li></li>");
                            var sub_node_a = $("<a></a>");
                            var sub_img = $("<img>");
                            sub_img.attr('src','images/'+va.type+'.png');
                            sub_img.attr('alt',va.name);
                            sub_node_a.css('padding','10px');
                            sub_node_a.css('cursor','pointer');
                            sub_node_a.append(sub_img);
                            sub_node_a.on("click",function(){
                                (settings.onclick)(va);
                            });
                            subnode.append(sub_node_a);
                            subnode.css('margin-top','10px');
                            subpanel.append(subnode);
                            var node_json = va.items;
                            if(node_json!=null&&node_json.length>0){
                                var sp =  $("<ul></ul>");
                                createSubNode(node_json,sp,settings);
                                subnode.addClass('menu-rightarrow');
                                subnode.append(sp);
                            }
                            if(!ismainpage){
                            	var str = "window."+v.type+"_main='"+va.url+"'";
                            	eval(str);
                            	ismainpage = true;
                            }
                        });
                        $("#"+settings.rendto).append(subpanel);
        
                    }
        		}
        		
        	};
            
        });
    };

    var methods = {
        init: function(options){
            return this.each(function(){
                var $this = $(this);
                var settings = $this.data('sgMenu');

                if(typeof(settings) == 'undefined') {

                    var defaults = {
                        datatype: 'json',
                        url: 'tree.json',
                        level:0,
                        parentMenu:null,
                        rendto:null,
                        onclick: function(){}
                    };

                    settings = $.extend({}, defaults, options);

                    $this.data('sgMenu', settings);
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

                $this.removeData('sgMenu');
            });
        },
        val: function(options){
            var someValue = this.eq(0).html();

            return someValue;
        }
    };

    $.fn.sgMenu = function(){
        var method = arguments[0];

        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.sgMenu' );
            return this;
        }
        return method.apply(this, arguments);
    };
})(jQuery);

