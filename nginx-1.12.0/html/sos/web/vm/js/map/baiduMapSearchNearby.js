function baiduMapSearchNearby(lon, lat, radius, name, callback){
	var options = {
		pageCapacity: 99,
		onSearchComplete: function(result){
			if(!callback){
				return;
			}
			
			var currentNumPois = result.getCurrentNumPois();
			var repois = [];
			for(var i = 0; i < currentNumPois; i++){
				var poi = result.getPoi(i);
				var title = poi.title;
				var address = poi.address;
				var point = poi.point;
				
				var bd_lon = point.lng;
		    	var bd_lat = point.lat;
		    	var p1 = BaiduConverter.decrypt(bd_lon, bd_lat);
		    	var p2 = Deconverter.decode(p1.x, p1.y);
		    	
		    	var repoi = {
		    		decodeLng: p2.x,
		    		decodeLat: p2.y,
		    		name: title,
		    		address: address
		    	};
		    	
		    	repois.push(repoi);
			}
			
			callback(repois);
		}
	};
	
	var c = new Converter();
	var p1 = c.getEncryPoint(parseFloat(lon), parseFloat(lat));
	var p2 = BaiduConverter.encrypt(p1.x, p1.y);
	var point = new BMap.Point(p2.x, p2.y);
	
	var search = new BMap.LocalSearch(point, options);
	search.searchNearby(name, point, radius);
	
//	var search = new BMap.LocalSearch(point, options);
//	search.searchNearby("银行", point, radius);
}