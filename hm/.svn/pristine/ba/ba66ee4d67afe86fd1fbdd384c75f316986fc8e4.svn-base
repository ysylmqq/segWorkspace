(function($) {
	var create = function(obj, settings) {
		obj.css("width", settings.width);
		$("#" + obj.attr("id") + " .tab_content").hide(); 
		$("#" + obj.attr("id") + " ul.tabs li:first").addClass("active").show();
		var firstTab = $("#" + obj.attr("id") + " .tab_content:first");
		firstTab.load(firstTab.attr("src"));
		firstTab.attr("loaded", true);
		firstTab.css("height", settings.height);
		firstTab.show(); 

		$("#" + obj.attr("id") + " ul.tabs li").click(function() {
			$("#" + obj.attr("id") + " ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$("#" + obj.attr("id") + " .tab_content").hide();
			var activeTabId = $(this).find("a").attr("href");
			var activeTab = $(activeTabId);
			if (!activeTab.attr("loaded")) {
				activeTab.load(activeTab.attr("src"));
				activeTab.attr("loaded", true);
				activeTab.css("height", settings.height);
			};
			activeTab.fadeIn(); 
			return false;
		});
	};

	var methods = {
		init : function(options){
			return this.each(function(){
				var $this = $(this);
				var settings = $this.data('sgTab');

				if (typeof (settings) == 'undefined') {

					var defaults = {
						width : 300,
						height : 200,
						onclick : function(){}
					};

					settings = $.extend({}, defaults, options);

					$this.data('sgTab', settings);
				} else {
					settings = $.extend({}, settings, options);
				};

				create($this, settings);
			});
		},
		destroy : function(options){
			return $(this).each(function() {
				var $this = $(this);

				$this.removeData('sgTab');
			});
		},
		val : function(options){
			var someValue = this.eq(0).html();

			return someValue;
		}
	};

	$.fn.sgTab = function(){
		var method = arguments[0];

		if (methods[method]) {
			method = methods[method];
			arguments = Array.prototype.slice.call(arguments, 1);
		} else if (typeof (method) == 'object' || !method) {
			method = methods.init;
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.sgTab');
			return this;
		};
		return method.apply(this, arguments);

	}
})(jQuery);
