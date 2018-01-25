(function($){
		   $.fn.autoCheckbox = function(){
			var $checkboxs = this;	
				this.click(function(){
					//点击事件
					debugger;
					var $this = $(this);	
					var $checkeds = $($checkboxs.selector+':checked');
					
					var startIndex = 0,fac = 1;
					if($checkeds.length==2){	
						startIndex = parseInt($checkeds.eq(0).attr("mark")) < parseInt($checkeds.eq(1).attr("mark")) ? $checkeds.eq(0).attr("mark"):$checkeds.eq(1).attr("mark")
						fac = Math.abs($checkeds.eq(0).attr("mark")-$checkeds.eq(1).attr("mark"));
						
						//
						$checkboxs.prop("checked",false);
						
						for(var i=0,len=$checkboxs.length;i<len;i++){
							if(i>=startIndex){
								if(i==startIndex||(i-startIndex)%fac==0){
									$checkboxs.eq(i).prop("checked",true);
								}
							}
						}
					}else if($checkeds.length>2){
						if($this.prop("checked")){
							if(window.confirm('是否重置该项')){
								$checkboxs.prop("checked",false);
							}else{
								$this.prop("checked",false);
							}
						}else{
							//$this.prop("checked",true);
							$checkboxs.prop("checked",false);
						}				
					}		
				});
		   }
		})(jQuery)