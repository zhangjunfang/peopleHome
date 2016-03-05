package com.jujin.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;

/**
 * 生成验证码服务
 * 
 * @author w8 2015-04-08
 *
 */
public class MakeVerifyCodeService implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 验证码*/
	private String code;
	/** 图片流*/
	private byte[] image;

	/**
	 * 构造方法
	 * 
	 * @param code
	 * @param image
	 */
	public MakeVerifyCodeService(String code, byte[] image) {
		this.code = code;
		this.image = image;
	}

	/**
	 * 取得code
	 * @return
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * 赋值code
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 取得图片流
	 * @return
	 */
	public byte[] getImage() {
		return this.image;
	}

	/**
	 * 赋值图片流
	 * @param image
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * 验证码字符
	 */
	private static char[] mapTable = { 'a', 'b', 'c', 'd', 'e', 'h', 'j', 'k',
			'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 生成验证码
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public static MakeVerifyCodeService getVerifyCode(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, 1);
		// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		Graphics graphics = image.getGraphics();

		graphics.setColor(Color.WHITE);

		graphics.fillRect(0, 0, width, height);

		graphics.setFont(new Font("Times?New?Roman", 0, 18));

		graphics.setColor(getRandColor(160, 200));

		// 绘制干扰线
		Random random = new Random();
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);

			int xl = random.nextInt(12);
			int yl = random.nextInt(12);

			graphics.drawLine(x, y, x + xl, y + yl);
		}

		// 绘制随机字符
		String strEnsure = "";

		for (int i = 0; i < 4; i++) {
			char c = mapTable[((int) (mapTable.length * Math.random()))];

			strEnsure = strEnsure + c;

			graphics.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			graphics.drawString(String.valueOf(c), 13 * i + 6, 16);
		}

		graphics.dispose();

		// 图片转图片流
		ByteArrayOutputStream imOut = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", imOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new MakeVerifyCodeService(strEnsure, imOut.toByteArray());
	}

	/**
	 * 图片颜色随机
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();

		if (fc > 255) {
			fc = 255;
		}

		if (bc > 255) {
			bc = 255;
		}

		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}
}
