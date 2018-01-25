package com.chinagps.driverbook.controller.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/admin/checkCode")
public class CheckCodeController {
	private static Logger logger = LoggerFactory.getLogger(CheckCodeController.class);

	@RequestMapping(method = RequestMethod.GET)
	public void execute(HttpSession session, HttpServletResponse response) {
		Random random = new Random();
		BufferedImage image = new BufferedImage(80, 30, BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Arial", Font.PLAIN, 24);
		int distance = 18;
		Graphics d = image.getGraphics();
		d.setColor(Color.WHITE);
		d.fillRect(0, 0, image.getWidth(), image.getHeight());
		d.setColor(new Color(random.nextInt(100) + 100, random.nextInt(100) + 100, random.nextInt(100) + 100));
		for (int i = 0; i < 10; i++) {
			d.drawLine(random.nextInt(image.getWidth()), random.nextInt(image.getHeight()), random.nextInt(image.getWidth()), random.nextInt(image.getHeight()));
		}
		d.setColor(Color.BLACK);
		d.setFont(font);
		String checkCode = "";
		char tmp;
		int x = -distance;
		for (int i = 0; i < 4; i++) {
			tmp = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789".charAt(random.nextInt("ABCDEFGHJKLMNPQRSTUVWXYZ123456789".length() - 1));
			checkCode = checkCode + tmp;
			x = x + distance;
			d.setColor(new Color(random.nextInt(100) + 50, random.nextInt(100) + 50, random.nextInt(100) + 50));
			d.drawString(tmp + "", x, random.nextInt(image.getHeight() - (font.getSize())) + (font.getSize()));
		}
		d.dispose();
		try {
			ImageIO.write(image, "jpg", response.getOutputStream());
			session.setAttribute("checkCode", checkCode);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}