// JavaScript Document

(function($) {
	var PICPATH	=	"images/"	//图片文件所在的文件夹，可见public，可改
	//-----------------------------------------------------------------------------
	//不可见private。
	//常量
	var BASE        =   PICPATH +   "base.gif";
	var JOIN		=	PICPATH +	"join.gif";
	var JOINBOTTOM	=	PICPATH +	"joinbottom.gif";
	var MINUS		=	PICPATH +	"minus.gif";
	var MINUSBOTTOM	=	PICPATH +	"minusbottom.gif";
	var PLUS		=	PICPATH +	"plus.gif";
	var PLUSBOTTOM	=	PICPATH +	"plusbottom.gif";
	var EMPTY		=	PICPATH +	"empty.gif";
	var LINE		=	PICPATH +	"line.gif";
	var FOLDEROPEN  =   PICPATH +  "folderopen.gif";
	var LEAFICON	=	PICPATH +	"page.gif";
	var NODEICON	=	PICPATH + 	"folder.gif";

	var OPEN		=new Array();
		OPEN[true]	=MINUS;
		OPEN[false]	=PLUS;

	var OPENBOTTOM			=new Array();
		OPENBOTTOM[true]	=MINUSBOTTOM;
		OPENBOTTOM[false]	=PLUSBOTTOM;

	var FOLDER       = new Array();
		FOLDER[true] =FOLDEROPEN;
		FOLDER[false]=NODEICON;

	var setPicPath=function(pPath){//设置pic路径
		BASE        =   pPath + "base.gif";
		JOIN		=	pPath +	"join.gif";
		JOINBOTTOM	=	pPath +	"joinbottom.gif";
		MINUS		=	pPath +	"minus.gif";
		MINUSBOTTOM	=	pPath +	"minusbottom.gif";
		PLUS		=	pPath +	"plus.gif";
		PLUSBOTTOM	=	pPath +	"plusbottom.gif";
		EMPTY		=	pPath +	"empty.gif";
		LINE		=	pPath +	"line.gif";
		FOLDEROPEN  =   pPath + "folderopen.gif";
		LEAFICON	=	pPath +	"page.gif";
		NODEICON	=	pPath + "folder.gif";

		OPEN		=new Array();
		OPEN[true]	=MINUS;
		OPEN[false]	=PLUS;

		OPENBOTTOM			=new Array();
		OPENBOTTOM[true]	=MINUSBOTTOM;
		OPENBOTTOM[false]	=PLUSBOTTOM;

		FOLDER       = new Array();
		FOLDER[true] =FOLDEROPEN;
		FOLDER[false]=NODEICON;
	}

    var create = function(obj,settings) {
		setPicPath(settings.picpath);
        // 代码在这里运行
		var root = $("<ul></ul>");
			root.level = 0;
        var rootitem = $('<li></li>');
		var rooticon = $("<img align='absmiddle' />");
		rooticon.attr('src',BASE);
		var rootcaption = createCaption(settings.root,null,settings);
		var perContainer = obj;
		rootitem.append(rooticon).append(rootcaption);
        root.append(rootitem);
		perContainer.append(root);


		var subContainer = $("<div></div>");
		subContainer.parent = root;
		perContainer.append(subContainer);

		var isexpend = settings.isexpend;

		var params = settings.data;
		if(params == null){
			params = JSON.stringify({});
		}
		$.ajax({
            type: "POST",
            async: false,
            contentType : 'application/json',
            url: settings.url,
            data: params,
            dataType : 'json',
            success: function(json){
                if(json){
                	createSubNode(json,root.level,subContainer,isexpend,settings);
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
     		    alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
     	   }
        });

    }

	var createCaption=function(titlename,node,settings){
		var tmp=$("<span></span>");
		tmp.append(titlename).addClass("caption");
		if(node!=null){
			tmp.on('mouseover',function(){
				tmp.removeClass();
				tmp.addClass("captionActive");
			});
			tmp.on('mouseout',function(){
				tmp.removeClass();
				tmp.addClass("caption");
			});
			tmp.on('click',function(){
				(settings.onclick)(node);
			});
			tmp.css('cursor','pointer');
		}
		return tmp;
	}

	var createSubNode = function(json,level,container,isexpend,settings){
		var len =json.length;
		var num = 1;
		$.each(json,function(k,v){
			var node = $("<ul></ul>");
			var newdiv = null;

			if(v.items!=null&&v.items.length>=1){
				 newdiv = $("<div></div>");
				 newdiv.parent = node;
			}
			node.level = level+1;
			node.isFirstChild	=(num==1?true:false);
			node.isLastChild	=(num==len?true:false);
			node.parent = container;
			num= num+1;

			container.append(node);

			createTreeLine(v,node,isexpend);//创建树节点连接线
			createIcon(v,node,isexpend,settings.hasbox);//创建树节点图标
			var caption = createCaption(v.name,v,settings);

			node.append(caption);

			if(v.items!=null&&v.items.length>=1){
				node.append(newdiv);
				createSubNode(v.items,node.level,newdiv,isexpend,settings);

				node.expand=function(pFlag){
					if(pFlag==null){
						if(newdiv.css('display')=="block"){
							newdiv.css('display','none');
							return false;
						}else{
							newdiv.css('display','block');
							return true;
						}
					}else{
						if(pFlag) newdiv.css('display','block');
						else newdiv.css('display','none');
					}

				};

				node.expand(isexpend);
			}

		});
	}

	var createTreeLine = function(json,node,expend){
		var haschild = (json.items!=null&&json.items.length>=1);
		for(var i=1;i<node.level;i++){
			var tmpNode=node;
			for(var j=node.level;j>i;j--){
				tmpNode=tmpNode.parent.parent;//每级有包含两个标签div、ul，所以这里要两次取parentNode
			}

			var newimg = null;
			if(tmpNode.isLastChild){//是否外层最后一个节点
				newimg = $("<img align='absmiddle' />");	//如果是外层最后一个节点，则不需要添加竖线
				newimg.attr('src',EMPTY);
			}else{
				newimg = $("<img align='absmiddle' />");	//如果不是外层最后一个节点，则需要添加
				newimg.attr('src',LINE);
			}
			node.append(newimg);
		}

		//处理当前节点的UL
		var childShowBtn;
		if(haschild){//有孩子
			if(!node.isLastChild){
				childShowBtn=$("<img align='absmiddle' />");
				childShowBtn.attr('src',OPEN[expend]);
			}else{
				childShowBtn=$("<img align='absmiddle' />");
			    childShowBtn.attr('src',OPENBOTTOM[expend]);
			}
			node.append(childShowBtn);
			childShowBtn.on('click',function(){//给图片添加事件
				var isExpand=node.expand();

				if(!node.isLastChild){
					if(isExpand){
						node.children("img[src*='folder.gif']").attr('src',FOLDER[isExpand]);
                        node.children("img[src*='plus.gif']").attr('src',OPEN[isExpand]);

					}else{
						node.children("img[src*='open.gif']").attr('src',FOLDER[isExpand]);
                        node.children("img[src*='minus.gif']").attr('src',OPEN[isExpand]);
					}

				}else{
					if(isExpand){
						node.children("img[src*='folder.gif']").attr('src',FOLDER[isExpand]);
                        node.children("img[src*='plusbottom.gif']").attr('src',OPENBOTTOM[isExpand]);

					}else{
						node.children("img[src*='open.gif']").attr('src',FOLDER[isExpand]);
                        node.children("img[src*='minusbottom.gif']").attr('src',OPENBOTTOM[isExpand]);
					}
				}
			})
			node.expandBtn=childShowBtn;//新增的
		}else{//无孩子。
			if(!node.isLastChild){
				childShowBtn=$("<img align='absmiddle' />");
				childShowBtn.attr('src',JOIN);
			}else{
				childShowBtn=$("<img align='absmiddle' />");
				childShowBtn.attr('src',JOINBOTTOM);
			}
			node.append(childShowBtn);
		}

	}

	var createIcon = function(json,node,isexpend,hasbox){
		var haschild = (json.items!=null&&json.items.length>=1);
		var newimg = null;
		if(haschild){
			if(hasbox){
				newimg = $("<img align='absmiddle' /><input type='checkbox' style='vertical-align:middle;' name='treeCheckbox' id='"+json.id+"' value='"+json.name+"'>");
			}else{
				newimg = $("<img align='absmiddle' />");
			}
			newimg.attr('src',FOLDER[haschild]);
		}else{
			if(hasbox){
				newimg = $("<img align='absmiddle' /><input type='checkbox' style='vertical-align:middle;' name='treeCheckbox' id='"+json.id+"' value='"+json.name+"'>");
			}else{
				newimg = $("<img align='absmiddle' />");
			}
			newimg.attr('src',LEAFICON);
		}
		node.append(newimg);
	}

	var methods = {
        init: function(options) {
            return this.each(function() {
				var $this = $(this);
                var settings = $this.data('sgTree');

                if(typeof(settings) == 'undefined') {

                    var defaults = {
                        isexpend: true,
                        datatype: 'json',
						url: 'tree.json',
						onclick: function(){},
						picpath: '../../images/'
                    }

                    settings = $.extend({}, defaults, options);

                    $this.data('sgTree', settings);
                } else {
                    settings = $.extend({}, settings, options);
                }

                // 代码在这里运行
				create($this,settings);

				// 创建完成添加checkbox父子关系控制
				$("#"+this.id+" input:checkbox").on('change',function() {
					//子目录
					var c = $(this).parent().find("input");
					var b = $(this).prop('checked');
					var fa = $(this).parent().parent().parent();
					c.each(function(){
						$(this).prop('checked',b);
					});
					//父目录
					var m = fa.find('input');
					var count = fa.find('input:checked').length;
					var s = fa.children('input');
					if(s.prop('checked')){//如果子勾满勾，父勾将被勾上，此时的count=子勾数+1。
						count--;//当再次去掉一个子勾时，count=子勾数！=子勾数-1，所以不减1父勾就去不掉了
					}
					var len = m.length-1;
					if(count==len){
						s.prop('checked',true);
						s.css('backgroundColor','#fff');
					}
				});

            });
        },
        reload: function(options){
            var settings = $(this).data('sgTree');
            settings = $.extend({}, settings, options);
            if(settings.isFixed==false){//如果条件不固定，则不做任何处理

            }else{//否则把条件固定到数据中
         	   $(this).data('sgTree', settings);
            }
            $(this).empty();

            create($(this),settings);
         },
        destroy: function(options) {
            return $(this).each(function() {
                var $this = $(this);

                $this.removeData('sgTree');
            });
        },
        val: function(options) {
            var someValue = this.eq(0).html();

            return someValue;
        }
    };

	$.fn.sgTree = function() {
        var method = arguments[0];

        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.sgTree' );
            return this;
        }

        return method.apply(this, arguments);
    }

})(jQuery);
