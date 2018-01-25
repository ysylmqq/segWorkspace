function baiduNavigation(from_lon, from_lat, to_lon, to_lat, policy, callback){
	function indexOfPoint(p, ps){
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			if(p.lng == psi.lng && p.lat == psi.lat){
				return i;
			}
		}
		
		for(var i = 0; i < ps.length; i++){
			var psi = ps[i];
			var dx = Math.abs(p.lng - psi.lng);
			var dy = Math.abs(p.lat - psi.lat);
			if(dx <= 0.000005 && dy <= 0.000005){
				return i;
			}
		}
		
		return -1;
	}
	
	var options = {
		policy: policy,
		
		onSearchComplete: function(results){
			var status = driving.getStatus();
		    if (status == BMAP_STATUS_SUCCESS){
		    	// 获取第一条方案
		    	var plan = results.getPlan(0);
		    	var distance = plan.getDistance(false);
		    	var time = plan.getDuration(false);
	
		    	// 获取方案的驾车线路
		    	var route = plan.getRoute(0);		
		    	var path = route.getPath();
	
		    	// 获取每个关键步骤,并输出到页面
		    	var steps = [];
		    	var steps_pos = [];
		    	for (var i = 0; i < route.getNumSteps(); i ++){
		    		var stepi = route.getStep(i);
		    		steps.push(stepi.getDescription(false));
		    		
		    		var pos = stepi.getPosition();
		    		var index = indexOfPoint(pos, path);
		    		
		    		
		    		if(index == -1){
		    			//var l = path[path.length - 1];
		    			//alert("i:" + i + ", pos:(" + pos.lng + ", " + pos.lat + ", last:(" + l.lng + ", " + l.lat + ")");	    			
		    			if(i == route.getNumSteps() - 1){
		    				//alert("last");
		    				steps_pos.push(path.length - 1);
		    			}else{
		    				steps_pos.push(index);
		    			}
		    		}else{
		    			steps_pos.push(index);
		    		}
		    	}
		    	
		    	var points = [];
			    for(var i = 0; i < path.length; i++){
			    	var pathi = path[i];
			    	var bd_lon = pathi.lng;
			    	var bd_lat = pathi.lat;
			    	var p1 = BaiduConverter.decrypt(bd_lon, bd_lat);
			    	var p2 = Deconverter.decode(p1.x, p1.y);
			    	
			    	var point = {
			    			lon: p2.x,
			    			lat: p2.y
			    	};
			    	
			    	points.push(point);
			    }
			    
			    callback(true, distance, time, points, steps, steps_pos);
		    }else{
		    	callback(false, status);
		    }
		}
	};
	
	var c_f = new Converter();
	var p1_f = c_f.getEncryPoint(parseFloat(from_lon), parseFloat(from_lat));
	var p2_f = BaiduConverter.encrypt(p1_f.x, p1_f.y);
	var point_f = new BMap.Point(p2_f.x, p2_f.y);
	
	var c_t = new Converter();
	var p1_t = c_t.getEncryPoint(parseFloat(to_lon), parseFloat(to_lat));
	var p2_t = BaiduConverter.encrypt(p1_t.x, p1_t.y);
	var point_t = new BMap.Point(p2_t.x, p2_t.y);
	
	var driving = new BMap.DrivingRoute("中国", options);
	driving.search(point_f, point_t);
}