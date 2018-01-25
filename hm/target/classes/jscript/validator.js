(function($) {
	var validate = function(obj) {
		var rule = eval('$.fn.validatebox.rules.' + obj.attr('validtype'));
		var param = eval("{" + obj.attr('param') + "}");
		var flag = rule.validator(obj.val(), param);
		if (flag) {
			obj.attr('isvalid', 'true');
		}
		return flag;
	}

	var init = function(obj) {
		var rule = eval('$.fn.validatebox.rules.' + obj.attr('validtype'));
		var param = eval("{" + obj.attr('param') + "}");

		var ad_div = $("<div class='show_validate_message'></div>");
		obj.parent().append(ad_div);
		if (param)
			ad_div.html($.fn.validatebox.format(rule.message, param));
		else
			ad_div.html($.fn.validatebox.format(rule.message));
	}

	var methods = {
		init : function() {
			return $(this).each(function() {
				var $this = $(this);

				init($this);
			});
		},
		validate : function() {
			return $(this).each(function() {
				var $this = $(this);

				if (!validate($this)) {
					return false;
				}
			});
		}

	};

	$.fn.validatebox = function() {
		var method = arguments[0];

		if (methods[method]) {
			method = methods[method];
			arguments = Array.prototype.slice.call(arguments, 1);
		} else if (typeof (method) == 'object' || !method) {
			method = methods.init;
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.sgForm');
			return this;
		}
		return method.apply(this, arguments);
	}

	$.fn.validatebox.rules = {};

	$
			.extend(
					$.fn.validatebox.rules,
					{
						required : {
							validator : function(value) {
								if (value == null)
									return false;
								else if (value.length > 0)
									return true;
								else
									return false;
							},
							message : '输入内容不能为空。'
						},
						minLength : { // 判断最小长度
							validator : function(value, param) {
								return value.length >= param[0];
							},
							message : '最少输入 {0} 个字符。'
						},
						onlyLength : {
							validator : function(value, param) {
								return value.length == param[0];
							},
							message : '请输入{0}个数字.'
						},
						length : {
							validator : function(value, param) {
								var len = $.trim(value).length;
								return len >= param[0] && len <= param[1];
							},
							message : "输入内容长度必须介于{0}和{1}之间."
						},
						phone : {// 验证电话号码
							validator : function(value) {
								return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
										.test(value);
							},
							message : '请输入有效电话号码（例：020-88888888）'
						},
						mobile : {// 验证手机号码
							validator : function(value) {
								return /^(13|15|18)\d{9}$/i.test(value);
							},
							message : '请输入有效手机号码'
						},
						idcard : {// 验证身份证
							validator : function(value) {
								return /^\d{15}(\d{2}[A-Za-z0-9])?$/i
										.test(value);
							},
							message : '请输入有效身份证号码!'
						},
						intOrFloat : {// 验证整数或小数
							validator : function(value) {
								return /^\d+(\.\d+)?$/i.test(value);
							},
							message : '请输入数字，并确保格式正确'
						},
						currency : {// 验证货币
							validator : function(value) {
								return /^\d+(\.\d+)?$/i.test(value);
							},
							message : '请输入有效货币格式!'
						},
						qq : {// 验证QQ,从10000开始
							validator : function(value) {
								return /^[1-9]\d{4,9}$/i.test(value);
							},
							message : '请输入有效QQ号码格式!'
						},
						integer : {// 验证整数
							validator : function(value) {
								return /^[+]?[1-9]+\d*$/i.test(value);
							},
							message : '请输入整数'
						},
						chinese : {// 验证中文
							validator : function(value) {
								return /^[\u0391-\uFFE5]+$/i.test(value);
							},
							message : '请输入中文'
						},
						english : {// 验证英语
							validator : function(value) {
								return /^[A-Za-z]+$/i.test(value);
							},
							message : '请输入英文'
						},
						unnormal : {// 验证是否包含空格和非法字符
							validator : function(value) {
								return /.+/i.test(value);
							},
							message : '输入值不能为空和包含其他非法字符'
						},
						username : {// 验证用户名
							validator : function(value) {
								return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i
										.test(value);
							},
							message : '请输入有效用户名（字母开头，允许6-16字节，允许字母数字下划线）'
						},
						faxno : {// 验证传真
							validator : function(value) {
								// return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[
								// ]){1,12})+$/i.test(value);
								return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
										.test(value);
							},
							message : '请输入有效传真号码!'
						},
						zip : {// 验证邮政编码
							validator : function(value) {
								return /^[1-9]\d{5}$/i.test(value);
							},
							message : '请输入有效邮政编码格式!'
						},
						ip : {// 验证IP地址
							validator : function(value) {
								return /d+.d+.d+.d+/i.test(value);
							},
							message : '请输入有效IP地址格式!'
						},
						name : {// 验证姓名，可以是中文或英文
							validator : function(value) {
								return /^[\u0391-\uFFE5]+$/i.test(value)
										| /^\w+[\w\s]+\w+$/i.test(value);
							},
							message : '请输入姓名(可以是中文或英文)'
						},
						carNo : {
							validator : function(value) {
								return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/
										.test(value);
							},
							message : '请输入有效车牌号码（例：粤J12350）'
						},
						carenergin : {
							validator : function(value) {
								return /^[a-zA-Z0-9]{16}$/.test(value);
							},
							message : '请输入有效发动机型号(例：FG6H012345654584)'
						},
						email : {
							validator : function(value) {
								return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
										.test(value);
							},
							message : '请输入有效的电子邮件账号(例：abc@126.com)'
						},
						msn : {
							validator : function(value) {
								return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
										.test(value);
							},
							message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
						},
						same : {
							validator : function(value, param) {
								if ($("#" + param[0]).val() != ""
										&& value != "") {
									return $("#" + param[0]).val() == value;
								} else {
									return true;
								}
							},
							message : '两次输入的密码必须一致！'
						}
					})

	// 格式化错误提醒信息
	$.fn.validatebox.format = function(source, params) {
		if (arguments.length === 1) {
			return function() {
				var args = $.makeArray(arguments);
				args.unshift(source);
				return $.fn.validatebox.format.apply(this, args);
			};
		}
		if (arguments.length > 2 && params.constructor !== Array) {
			params = $.makeArray(arguments).slice(1);
		}
		if (params.constructor !== Array) {
			params = [ params ];
		}
		$.each(params, function(i, n) {
			source = source.replace(new RegExp("\\{" + i + "\\}", "g"),
					function() {
						return n;
					});
		});
		return source;
	};

})(jQuery)