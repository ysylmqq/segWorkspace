var BaiduConverter = {
		x_pi: 3.14159265358979324 * 3000.0 / 180.0,
		
		encrypt: function(gg_lon, gg_lat){
			var x = gg_lon, y = gg_lat;  
			var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * this.x_pi);  
			var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * this.x_pi);  
			var bd_lon = z * Math.cos(theta) + 0.0065;  
			var bd_lat = z * Math.sin(theta) + 0.006;
			
			var point = new Point();
			point.setX(bd_lon);
			point.setY(bd_lat);
			return point;
		},
		
		decrypt: function(bd_lon, bd_lat){
			var x = bd_lon - 0.0065, y = bd_lat - 0.006;  
			var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * this.x_pi);  
			var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * this.x_pi);  
			var gg_lon = z * Math.cos(theta);  
			var gg_lat = z * Math.sin(theta);
			
			var point = new Point();
			point.setX(gg_lon);
			point.setY(gg_lat);
			return point;
		}
};