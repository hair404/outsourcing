package com.utils;

import java.io.IOException;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Code extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Random ran = new Random();
	private static String code = "";

	// 写一个方法随机生成一种颜色
	private Color getRandomColor() {
		// 随机生成0~255之间的数
		int red = ran.nextInt(256);
		int green = ran.nextInt(256);
		int blue = ran.nextInt(256);
		// 红，绿，蓝
		return new Color(red, green, blue);
	}

	private void buildCode(char c) {
		code += c;
	}

	public static void setCode() {
		code = "";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int width = 100, height = 50;
		// 参数：宽，高，图片模式
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 2. 获取画笔对象
		Graphics graphics = img.getGraphics();
		// 3. 设置画笔颜色
		graphics.setColor(Color.WHITE);
		// 4. 填充矩形区域
		graphics.fillRect(0, 0, width, height);
		// 6. 循环4次，画4个字符
		for (int i = 0; i < 4; i++) {
			// 7. 设置字的颜色为随机
			graphics.setColor(getRandomColor());
			// 8. 设置字体，大小为18
			graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD + Font.ITALIC, 19));
			// 随机得到一个字符
			char c = (char) (ran.nextInt(25) + 65);
			buildCode(c);
			// 9. 将每个字符画到图片，x增加，y不变。
			graphics.drawString(String.valueOf(c), 10 + (i * 20), 20);
		}
		// 11. 画8条干扰线，每条线的颜色不同
		for (int i = 0; i < 8; i++) {
			// 10. 线的位置是随机的，x范围在width之中，y的范围在height之中。
			int x1 = ran.nextInt(width);
			int y1 = ran.nextInt(height);
			int x2 = ran.nextInt(width);
			int y2 = ran.nextInt(height);
			graphics.setColor(getRandomColor());
			graphics.drawLine(x1, y1, x2, y2);
		}
		// 12. 将缓存的图片输出到响应输出流中
		// 参数：图片对象,图片格式，输出流
		ImageIO.write(img, "jpg", response.getOutputStream());
	}

	public static String getCode() {
		return code;
	}

	public static void setCode(String code) {
		Code.code = code;
	}

}
