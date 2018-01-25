var Deconverter = {
		decode0: function(enlng, enlat){
			var c1 = new Converter();
			var p1 = c1.getEncryPoint(enlng, enlat);
			var dx = p1.x - enlng;
			var dy = p1.y - enlat;
			var p0x = enlng - dx;
			var p0y = enlat - dy;

			var point = new Point();
			point.setX(p0x);
			point.setY(p0y);
			return point;
		},
		
		
		decode: function(enlng, enlat){
			var p1 = this.decode0(enlng, enlat);
			
			var tempX = p1.x;
			var tempY = p1.y;
		
			var count = 0;
			var maxCount = 10;
			var pd = 10000000;
			while(true){
				if(count > maxCount){
					break;
				}
				
				//System.out.println("[" + count + "]tempX:" + tempX + ", tempY:" + tempY);
				var c = new Converter();
				var en2 = c.getEncryPoint(tempX, tempY);
				var dx = en2.x  - enlng;
				var dy = en2.y - enlat;
				
				var ddx = Math.abs(en2.x * pd - enlng * pd);
				var ddy = Math.abs(en2.y * pd - enlat * pd);

				if(ddx < 5 && ddy < 5){
					break;
				}
				
				tempX -= dx;
				tempY -= dy;
				
				count++;
			}
			
			var p = new Point();
			p.setX(tempX);
			p.setY(tempY);
			
			return p;
		}
};