(function($){
    var focusobj = null;
	
	/**
	 * clear the form fields
	 */
	function clear(target){
		$('input,select,textarea', target).each(function(){
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == 'text' || t == 'hidden' || t == 'password' || tag == 'textarea'){
				this.value = '';
			} else if (t == 'file'){
				var file = $(this);
				file.after(file.clone().val(''));
				file.remove();
			} else if (t == 'checkbox' || t == 'radio'){
				this.checked = false;
			} else if (tag == 'select'){
				this.selectedIndex = -1;
			};
			
		});
	}
	
	function init(target){
		if ($.fn.validatebox){
			var t = $(target);
			var child = t.find('[validtype]');
			child.focus(function(){
				$('.show_validate_message').remove();
				$(this).validatebox();
				});
		}
	}
	
	function validate(target){
		if ($.fn.validatebox){
			var t = $(target);
			t.find('[validtype]').validatebox('validate');
            var invalidbox = t.find('[isvalid=false]');
			if(invalidbox!=null&&invalidbox.length>0){
                invalidbox.filter(':first').focus();
                focusobj = invalidbox.filter(':first');
            }else{
                $('.show_validate_message').remove();
            };
			return invalidbox.length == 0;
		};
		return true;
	}
	
	var methods = {
        init: function(options) {
            return this.each(function(){
				var $this = $(this);
                var settings = $this.data('sgForm');
 
                if(typeof(settings) == 'undefined') {
 
                    var defaults = {
                        url: null,
						data: null,
						success: function(){}
                    };
 
                    settings = $.extend({}, defaults, options);
 
                    $this.data('sgFrom', settings);
                } else {
                    settings = $.extend({}, settings, options);
                }
 
                // 代码在这里运行
				init($this);
            });
        },
        destroy: function(options) {
            return $(this).each(function(){
                var $this = $(this);
 
                $this.removeData('sgForm');
            });
        },
        val: function(options) {
            var someValue = this.eq(0).html();
 
            return someValue;
        },
		onSubmit : function(options){
            if(validate(this)){
                $.post(options.url, $(this).serialize(),
                    function(data){
                        (options.success)(data);
                    }, "json");
            }else{
                return false;
            }

		},
		loadForm : function(options){
			$.post(options.url, options.data,
				   function(data){
					 (options.success)(data);
				   }, "json");
			init(this[0]);
		},
		validate: function(){
			return validate(this[0]);
		},
		clear : function(){
			return this.each(function(){
				clear(this);
			});
		}
    };
	
	$.fn.form = function(){
		var method = arguments[0];
 
        if(methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if( typeof(method) == 'object' || !method ) {
            method = methods.init;
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.sgForm' );
            return this;
        };
        return method.apply(this, arguments);
	}

})(jQuery);
