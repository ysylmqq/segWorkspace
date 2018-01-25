(function($){
    var create = function(obj,settings){
        var mask = $('<div></div>');
        var win = $('<div></div>');
        var header = $('<div></div>');
        var body = $('<div></div>');
        var title = $('<div></div>');
        var tool = $('<div></div>');
        var toolClose = $('<a href="javascript:void(0);"></a>');
        var footer = $('<div></div>');

        var clientWidth =  $(window).outerWidth();
        var clientHeight = $(window).outerHeight();

        win.addClass('window');
        win.attr('id',settings.id);
        win.width(settings.width);
        win.css('left',(clientWidth-settings.width)/2);
        win.css('top',(clientHeight-settings.height-60)/2);
        win.css('z-index',settings.z_index);

        toolClose.addClass('window-tool-close');
        toolClose.click(function(){
        	var isClose=true;
        	if(settings.beforeClose){
        		isClose=settings.beforeClose();
        	}
        	if(isClose){
	        	obj.removeData('sgWindow');
	            var maskId = "#"+settings.id+"_mask";
	            $(maskId).remove();
	            var winId = "#"+settings.id;
	            $(winId).remove();
        	}
        });

        tool.addClass('window-tool');
        tool.append(toolClose);

        title.addClass('window-title');
        title.append(settings.title);

        header.addClass('window-header');
        header.width(settings.width);
        header.append(title);
        header.append(tool);
        win.append(header);

        body.addClass('window-body');
        // body.width(settings.width-6);
        body.height(settings.height-25);
        // body.css('padding','0px');
        body.load(settings.url,settings.success);
        win.append(body);

        if(settings.buttons){
        	footer.addClass('window-footer');
            footer.addClass('window-body-noheader');
            footer.addClass('window-body-noborder');
            footer.css('text-align','center');
            footer.css('padding-top','5px');
            footer.width(settings.width);
            $.each(settings.buttons,function(k,v){
                if(v.type=='submit'){
                    var sub = $('<input type="submit">');
                    sub.attr('form',settings.form);
                    sub.val(v.name);
                    sub.css('margin-right','10px');
                    if(v.onpress){
                        sub.click(function(){
                            (v.onpress)();
                        })
                    };
                    footer.append(sub);
                }else if(v.type=='reset'){
                    var sub = $('<input type="reset">');
                    sub.attr('form',settings.form);
                    sub.val(v.name);
                    sub.css('margin-right','10px');
                    if(v.onpress){
                        sub.click(function(){
                            (v.onpress)();
                        })
                    };
                    footer.append(sub);
                }else{
                    var sub = $('<input type="button">');
                    sub.attr('form',settings.form);
                    sub.val(v.name);
                    sub.css('margin-right','10px');
                    if(v.onpress){
                        sub.click(function(){
                            (v.onpress)();
                        })
                    };
                    footer.append(sub);
                };
            });
            win.append(footer);
        };

        win.show();

        mask.addClass('window-mask');
        mask.attr('id',settings.id+"_mask");
        mask.show();

        $(document.body).append(mask);
        $(document.body).append(win);

        DragAndDrop.Register(win, header);
    };

    var methods = {
        init: function(options) {
            return this.each(function(){
                var $this = $(this);
                var settings = $this.data('sgWindow');

                if(typeof(settings) == 'undefined') {

                    var defaults = {
                        id:false,
                        title:false,
                        url:false,
                        width: 300,
                        height: 200,
                        z_index:9000,
                        beforeClose:false// 关闭前的自调函数,返回true，继续执行关闭方法，返回false，则停止关闭
                    };

                    settings = $.extend({}, defaults, options);

                    $this.data('sgWindow', settings);
                } else {
                    settings = $.extend({}, settings, options);
                };

                // 代码在这里运行
                create($this,settings);
            });
        },
        close: function(options) {
        	var isClose=true;
        	if(options.beforeClose){
        		isClose=options.beforeClose();
        	}
        	if(isClose){
	            var mask = "#"+options.id+"_mask";
	            $(mask).remove();
	            $("#"+options.id).remove();
	            $(this).removeData('sgWindow');
        	}
        },
        destroy: function(options) {
            return $(this).each(function(){
                var $this = $(this);

                $this.removeData('sgWindow');
            });
        },
        val: function(options) {
            var someValue = this.eq(0).html();

            return someValue;
        }
    };

    $.fn.sgWindow= function(){
        var method = arguments[0];

        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1); // 将参数转换成真正的数组
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.sgWindow' );
            return this;
        };
        return method.apply(this, arguments);

    };

    var DragAndDrop = function(){

        // 客户端当前屏幕尺寸(忽略滚动条)
        var _clientWidth;
        var _clientHeight;

        // 拖拽控制区
        var _controlObj;
        // 拖拽对象
        var _dragObj;
        // 拖动状态
        var _flag = false;

        // 拖拽对象的当前位置
        var _dragObjCurrentLocation;

        // 鼠标最后位置
        var _mouseLastLocation;



        var getElementDocument = function(element){
            return element.ownerDocument || element.document;
        };

        // 鼠标按下
        var dragMouseDownHandler = function(evt){

            if(_dragObj){

                evt = evt || window.event;

                // 获取客户端屏幕尺寸
                _clientWidth = document.body.clientWidth;
                _clientHeight = document.documentElement.scrollHeight;

                // iframe遮罩
                $("#jd_dialog_m_b_1").css("display", "");

                // 标记
                _flag = true;

                // 拖拽对象位置初始化
                _dragObjCurrentLocation = {
                    x : $(_dragObj).offset().left,
                    y : $(_dragObj).offset().top
                };

                // 鼠标最后位置初始化
                _mouseLastLocation = {
                    x : evt.screenX,
                    y : evt.screenY
                };

                // 注：mousemove与mouseup下件均针对document注册，以解决鼠标离开_controlObj时事件丢失问题
                // 注册事件(鼠标移动)
                $(document).bind("mousemove", dragMouseMoveHandler);
                // 注册事件(鼠标松开)
                $(document).bind("mouseup", dragMouseUpHandler);

                // 取消事件的默认动作
                if(evt.preventDefault){
                    evt.preventDefault();
                }else{
                    evt.returnValue = false;
                };
                // 开启异步移动
                // _timer = setInterval(intervalMove, 10);
            };
        };

        // 鼠标移动
        var dragMouseMoveHandler = function(evt){
            if(_flag){

                evt = evt || window.event;

                // 当前鼠标的x,y座标
                var _mouseCurrentLocation = {
                    x : evt.screenX,
                    y : evt.screenY
                };

                // 拖拽对象座标更新(变量)
                _dragObjCurrentLocation.x = _dragObjCurrentLocation.x + (_mouseCurrentLocation.x - _mouseLastLocation.x);
                _dragObjCurrentLocation.y = _dragObjCurrentLocation.y + (_mouseCurrentLocation.y - _mouseLastLocation.y);

                // 将鼠标最后位置赋值为当前位置
                _mouseLastLocation = _mouseCurrentLocation;

                // 拖拽对象座标更新(位置)
                $(_dragObj).css("left", _dragObjCurrentLocation.x + "px");
                $(_dragObj).css("top", _dragObjCurrentLocation.y + "px");

                // 取消事件的默认动作
                if(evt.preventDefault){
                	evt.preventDefault();
                }else{
                	evt.returnValue = false;
                };                    
            };
        };

        // 鼠标松开
        var dragMouseUpHandler = function(evt){

            if(_flag){
                evt = evt || window.event;

                // 取消iframe遮罩
                $("#jd_dialog_m_b_1").css("display", "none");

                // 注销鼠标事件(mousemove mouseup)
                cleanMouseHandlers();

                // 标记
                _flag = false;
            };

        };

        // 注销鼠标事件(mousemove mouseup)
        var cleanMouseHandlers = function(){
            if(_controlObj){
                $(_controlObj.document).unbind("mousemove");
                $(_controlObj.document).unbind("mouseup");
            };
        };

        return {
            // 注册拖拽(参数为dom对象)
            Register : function(dragObj, controlObj){
                // 赋值
                _dragObj = dragObj;
                _controlObj = controlObj;
                // 注册事件(鼠标按下)
                $(_controlObj).bind("mousedown", dragMouseDownHandler);
            }
        };

    }();

})(jQuery);
