package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy {

	int x, y, speed;
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.speed = 2;
	}
	public void drawEnemy(Graphics2D g2) {
//		g2.setColor(new Color(255,0,0));
//		g2.fillOval(x, y, 130, 80);	

		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("C:\\Users\\sune9\\Pictures\\Saved Pictures\\ufo.png"));
		} catch (IOException e) {
		}
		g2.drawImage(img, x,y, 128, 128, null);
	}
	public void move() {
		y+= speed;
		
	}
	public boolean checkIfOutOfBounds(Dimension dim) {
		if(y > dim.getHeight() || y < -80 || x > dim.getWidth() || x < -80) {
			return true;
		}else{
			return false;
		}
	}
	public boolean checkIfhit(Bullet bullet) {
		int distX = (bullet.getX() - this.x);
		int distY = (bullet.getY() - this.y);
		if(distX <= 128 & distY <= 80 & distX >= 0 & distY >= 0) {
			return true;
		}else {
			return false;
		}
		
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}	
}
