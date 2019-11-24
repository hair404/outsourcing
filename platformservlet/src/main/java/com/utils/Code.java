package com.utils;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Component;

@Component
public class Code {
	private Random ran = new Random();
	public String code = "";

	
	private Color getRandomColor() {
		int red = ran.nextInt(256);
		int green = ran.nextInt(256);
		int blue = ran.nextInt(256);
		return new Color(red, green, blue);
	}

	public BufferedImage buildCode() {
		int width = 100, height = 50;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = img.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		for (int i = 0; i < 4; i++) {
			graphics.setColor(getRandomColor());
			graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, 19));
			char c = (char) (ran.nextInt(25) + 65);
			graphics.drawString(String.valueOf(c), 10 + (i * 20), 20);
			code+=c;
		}
		for (int i = 0; i < 8; i++) {
			int x1 = ran.nextInt(width);
			int y1 = ran.nextInt(height);
			int x2 = ran.nextInt(width);
			int y2 = ran.nextInt(height);
			graphics.setColor(getRandomColor());
			graphics.drawLine(x1, y1, x2, y2);
		}
		return img;
	}
	public String getCode() {
		return code;
	}
}
