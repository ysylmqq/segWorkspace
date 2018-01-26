package cc.chinagps.gateway.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cc.chinagps.gateway.util.IOUtil;


public class MakePicUtil {
	private static final char []MAP_TABLE= {'a','b','c','d','e','f',
		'g','h','i','j','k','l',
		'm','n','o','p','q','r',
		's','t','u','v','w','x',
		'y','z','0','1','2','3',
		'4','5','6','7','8','9'};
	
	private static final int NUM_SIZE = 4;
	
	private static final int NUM_RANDOM_POINT = 10;
	
	public static void outPutCertPic(int width, int height, HttpServletResponse response, int imgType, HttpSession session){
		if(width <= 0){
			width = 60;
		}
		if(height <= 0){
			height = 20;
		}
		//随机验证码
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < NUM_SIZE; i++){
			sb.append(MAP_TABLE[random.nextInt(MAP_TABLE.length)]);
		}
		session.setAttribute(Constants.SESSION_LOGIN_CODE, sb.toString());
		
		BufferedImage img;
		if(imgType == 1){
			img = drawNormalImage(sb.toString(), width, height);
		}else{
			img = drawDefaultImage(sb.toString(), width, height);
		}
		
		
		OutputStream os = null;
		response.setContentType("image/png");
		try {
			os = response.getOutputStream();
			ImageIO.write(img, "png", os);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeOS(os);
		}
	}
	
	private static Color getRandomColor(){
		Color []colorTable = {
				Color.red,
				Color.black,
				Color.blue,
				Color.green
		};
		
		int key =  (int) (Math.random() * colorTable.length);
		return colorTable[key];
	}
	
	private static BufferedImage drawDefaultImage(String num, int width, int height){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图形上下文
		Graphics g = img.getGraphics();
		//设定背景色
		g.setColor(new Color(0xE2F3FD));
		g.fillRect(0, 0, width, height);
		//画边框
		g.setColor(new Color(0xB4BBC3));
		g.drawRect(0, 0, width - 1, height - 1);

		//显示验证码
		g.setFont(new Font("宋体", Font.BOLD, 18));
		for(int i = 0; i < num.length(); i++){
			g.setColor(getRandomColor());
			g.drawString(num.substring(i, i+1), 2 + 13 * i, 17);
		}
		
		//随机10个干扰点
		g.setColor(getRandomColor());
		Random ran = new Random();
		for(int i = 0; i < NUM_RANDOM_POINT; i++){
			int x = ran.nextInt(width);
			int y = ran.nextInt(height);
			g.drawOval(x, y, 1, 1);
		}
		g.dispose();
		return img;
	}
	
	private static BufferedImage drawNormalImage(String num, int width, int height){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();
		img = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = img.createGraphics();
		Color c = new Color(255, 255, 255, 0);
		g2d.setColor(c);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Atlantic", Font.PLAIN, 18));
		g2d.drawString(num, 5, 15);
		g2d.dispose();
		return img;
	}
}