(function($){
    var create = function(obj,settings){
        $.ajax({type: "POST",
            async:false,
            url: settings.url,
            dataType : "json",
            success: function(json){createNode(json,obj,settings);}
        });
        obj.css("width",settings.width);
        main(obj,settings);
    };

    var createNode = function(json,obj,settings){
        $.each(json,function(k,v){
            var node = $("<li class='accordion_li'></li>");
            var panel = $("<ul></ul>");
            node.append(v.title);
            if(v.type==2){
                node.append("("+v.items.length+")");
                $.each(v.items,function(ki,vi){
                    var content_div =  $("<li class='accordion_ul_li'></li>");
                    if(vi.isurl){
                        var node_a = $("<a></a>");
                        node_a.append(vi.content);
                        node_a.attr("href","javascript:void(0);");
                        node_a.attr("title","点击查看详情");
                        node_a.attr("id",vi.id);
                        node_a.on("click",function(){
                            (settings.onclick)(vi);
                            }) ;
                        content_div.append(node_a);
                    };

                    panel.append(content_div);
                });
            }else if(v.type==3){
                $.each(v.items,function(kd,vd){
                    var content_div =  $("<div></div>");
                    content_div.css("width",settings.width-2);
                    content_div.css("height",60);
                    content_div.css("border","1px solid #999");
                    content_div.css("overflow","auto");
                    content_div.append(vd.content);                    
                    panel.append(content_div);

                });
            }else if(v.type==4){
                    var content_div =  $("<div></div>");
                    content_div.load(v.src); 
                    content_div.css("width",settings.width-2);
                    content_div.css("height",settings.div_height);
                    content_div.css("overflow-x","hidden");
                    panel.append(content_div);                                    

            }else{
                $.each(v.items,function(kk,vv){
                    var content_div =  $("<li class='accordion_ul_li'></li>");
                    content_div.append(vv.key+"：");
                    if(vv.isurl){
                        var node_a = $("<a></a>");
                        node_a.append(vv.value);
                        node_a.attr("href","javascript:void(0);");
                        node_a.attr("title","点击查看详情");
                        node_a.on("click",function(){
                            (settings.onclick)(vv);
                        }) ;
                        content_div.append(node_a);
                    }else{
                        content_div.append(vv.value);
                    };

                    panel.append(content_div);
                });
            };
            obj.append(node);
            obj.append(panel);
        });
    };



    var main = function(obj,settings){
    	if(!settings.isexpend){
    		obj.children("ul").hide();
    		obj.children("ul:first").show();
            obj.children("li:first").removeClass();
            obj.children("li:first").addClass('accordion_li_open');
            $("li",obj).click(
                function(){
                    var checkElement = $(this).next();
                    if((checkElement.is("ul")) && (checkElement.is(":visible"))) {
                        return false;
                    };
                    if((checkElement.is("ul")) && (!checkElement.is(":visible"))) {
                    	obj.children("ul:visible").slideUp("normal");
                        checkElement.slideDown("normal");
                        obj.children("li").removeClass();
                        obj.children("li").addClass("accordion_li");
                        $(this).removeClass();
                        $(this).addClass("accordion_li_open");
                        return false;
                    };
                }
            );
    	};
        
    };

    var methods = {
        init: function(options){
            return this.each(function(){
                var $this = $(this);
                var settings = $this.data("sgAccordion");

                if(typeof(settings) == "undefined") {

                    var defaults = {
                        url:"accordion.json",
                        width: 300,
                        div_height: 200,
                        isexpend:true,
                        onclick: function(){}
                    };

                    settings = $.extend({}, defaults, options);

                    $this.data("sgAccordion", settings);
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

                $this.removeData("sgAccordion");
            });
        },
        val: function(options){
            var someValue = this.eq(0).html();

            return someValue;
        }
    };

    $.fn.sgAccordion= function(){
        var method = arguments[0];

        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == "object" || !method ) {
            method = methods.init;
        } else {
            $.error( "Method " +  method + " does not exist on jQuery.sgAccordion" );
            return this;
        };
        return method.apply(this, arguments);

    };
})(jQuery);
