package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Explosion {

	int x, y, counter;
	ExplosionSound sound;
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
		counter = 0;
		sound = new ExplosionSound();
		sound.play();
	}

	public void drawExplosion(Graphics2D g2) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("C:\\Users\\sune9\\Pictures\\Saved Pictures\\explosion.png"));
		} catch (IOException e) {
		}
		g2.drawImage(img, x,y, 256, 256, null);
		
		BufferedImage img2 = null;
		try {
		    img2 = ImageIO.read(new File("C:\\Users\\sune9\\Pictures\\Saved Pictures\\explosion2.png"));
		} catch (IOException e) {
		}
		g2.drawImage(img2, x+75,y+64, 128, 128, null);
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public int getTimeoutCounter() {
		return counter;
	}
	public void increaseTimeoutCounter() {
		counter++;
	}

}
