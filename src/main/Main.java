package main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main extends JComponent implements ActionListener{

	private static int x, y, bulletCounter, maxNumberOfEnemies, enemyCounter, timeBetweenEnemySpawn, timeBetweenBullets, speed, explosionTimeout,
					   hitpoints, state, currentButton;
	private static long score;
	private static boolean w,s,a,d,space, enter;
	private static ArrayList<Bullet> bullets;
	private static ArrayList<Enemy> enemies;
	private static ArrayList<Explosion> explosions;
	private static Random rand;
	private static Font font;


	public static void main(String[] args) {
		Main game = new Main();
		initGame();
		initKeyboard(game);
		JFrame frame = new JFrame("TestGame");
		JLabel scoreLabel = new JLabel("test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.add(game, 1);
		layeredPane.add(scoreLabel, 0);
		frame.getContentPane().add(game);
		frame.pack();
		frame.setVisible(true);		
		Music music = new Music();
		music.play();
		Timer timer = new Timer(20,game);
		timer.start();
	}

	public static void initGame() {
		x = 600;
		y = 880;
		w = false;
		s = false;
		a = false;
		d = false;
		enter = false;
		space = false;
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		explosions = new ArrayList<Explosion>();
		bulletCounter = 5;
		maxNumberOfEnemies = 15;
		enemyCounter = 25;
		timeBetweenBullets = 7;
		timeBetweenEnemySpawn = 25;
		rand = new Random();
		speed = 10;
		explosionTimeout = 10;
		score= 0;
		hitpoints = 10;
		currentButton = 0;
		state = 0;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1280,960);
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		//background image
		BufferedImage background = null;
		try {
		    background = ImageIO.read(new File("C:\\Users\\sune9\\Pictures\\Saved Pictures\\space_1280x960.jpg"));
		} catch (IOException e) {
		}
		g2.drawImage(background, 0,0, (int)(getWidth()), (int)(getHeight()), null);
		
		//background tint
		g2.setColor(new Color(100,100,100,75));
		g2.fillRect(0,0, (int)(getWidth()), (int)(getHeight()));
		
		//Player
		BufferedImage player = null;
		try {
		    player = ImageIO.read(new File("C:\\Users\\sune9\\Pictures\\Saved Pictures\\space-shuttle.png"));
		} catch (IOException e) {
		}
		g2.drawImage(player, x, y, 64, 64, null);
		
		//bullets
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).drawBullet(g2);
		}
		
		//enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).drawEnemy(g2);
		}
		
		//Explosion
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).drawExplosion(g2);
		}
		
		if(state == 1) { // TITLE SCREEN
			
			g2.setColor(Color.GREEN);
			Font font = new Font("Play", Font.BOLD, 25);
			drawCenteredString(g2, "HITPOINTS : " + hitpoints, new Rectangle(10,10,(int)(getWidth()),50), font);
			drawCenteredString(g2, "SCORE : " + score, new Rectangle(10,50,(int)(getWidth()),50), font);
			
		}else if(state == 0) { // TITLE SCREEN
			
			//BACKGROUND TINT
			g2.setColor(new Color(50,50,50,150));
			g2.fillRect(0, 0, (int)(getWidth()), (int)(getHeight()));
			
			// TITLE
			g2.setColor(Color.GREEN);
			font = new Font("Play", Font.BOLD, 175);
			drawCenteredString(g2, "ALIEN", new Rectangle(0,10,(int)(getWidth()),150), font);
			drawCenteredString(g2, "INVASION", new Rectangle(0,160,(int)(getWidth()),150), font);
			
			// MENU
			g2.setColor(new Color(255,255,255));
			font = new Font("Play", Font.BOLD, 40);
			drawCenteredString(g2, "NEW GAME", new Rectangle(0,350,(int)(getWidth()),75), font);
			drawCenteredString(g2, "SETTINGS", new Rectangle(0,425,(int)(getWidth()),75), font);
			drawCenteredString(g2, "LEADERBOARD", new Rectangle(0,500,(int)(getWidth()),75), font);
			
			if(currentButton == 0) { // 0 == NEW GAME
				g2.setColor(new Color(0,255,0,150));
				drawCenteredString(g2, "NEW GAME", new Rectangle(0,350,(int)(getWidth()),75), font);
			}else if(currentButton == 1) { // 1 == SETTINGS
				g2.setColor(new Color(0,255,0,150));
				drawCenteredString(g2, "SETTINGS", new Rectangle(0,425,(int)(getWidth()),75), font);
			}else if(currentButton == 2) { // 2 == LEADERBOARD
				g2.setColor(new Color(0,255,0,150));
				drawCenteredString(g2, "LEADERBOARD", new Rectangle(0,500,(int)(getWidth()),75), font);
			}
			
		}else if(state == 2) { // GAME OVER SCREEN
			
			//BACKGROUND TINT
			g2.setColor(new Color(75,75,75,150));
			g2.fillRect(0, 0, (int)(getWidth()), (int)(getHeight()));
			
			// GAME OVER
			g2.setColor(Color.GREEN);
			font = new Font("Play", Font.BOLD, 175);
			drawCenteredString(g2, "GAME", new Rectangle(0,0,(int)(getWidth()),150), font);
			drawCenteredString(g2, "OVER", new Rectangle(0,100,(int)(getWidth()),250), font);
		}
	}
	
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
		
		FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
	
	public void addBullet() {
		bullets.add(new Bullet(x+28,y-10));
	}
	
	public void addEnemy() {
		int enemyX = rand.nextInt(1080) + 100;
		enemies.add(new Enemy(enemyX,-80));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {  //runs on the timer
		
		if(state == 1) { // game screen

			score++;

			if(hitpoints <= 0) {
				state = 2;
			}

			if(w) {
				y-=speed;
			}
			if(s) {
				y+=speed;
			}
			if(a) {
				x-=speed;
			}
			if(d) {
				x+=speed;
			}

			//Shooting
			if(space) {
				if(bulletCounter == timeBetweenBullets) {
					addBullet();
					bulletCounter=0;
				}else {
					bulletCounter++;
				}

			}else {
				bulletCounter = timeBetweenBullets;
			}

			//Checks if bullets is outside of the screen
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).move();
				if(bullets.get(i).checkIfOutOfBounds(getPreferredSize())) {
					bullets.remove(i);
				}
			}
			
			for(int i = 0; i < explosions.size(); i++) {
				if(explosions.get(i).getTimeoutCounter() >= explosionTimeout) {
					explosions.remove(i);
				}else {
					explosions.get(i).increaseTimeoutCounter();
				}
			}
			
		}else if(state == 0) { // Title Screen
			
			if(w) {
				if(currentButton > 0) {
					currentButton--;
				}
				w = false;
			}
			if(s) {
				if(currentButton < 2) {
					currentButton++;
				}
				s = false;
			}
			if(enter) {
				if(currentButton == 0) { // new game
					initGame();
					state = 1;
				}else if(currentButton == 1) { // Settings
					state = 3;
				}else { // leaderboard
					state = 4;
				}
				enter = false;
			}
		}else {  // Game over screen
			if(enter) {
				state = 4;
			}
		}
		
		//enemies
		if(enemies.size() <= maxNumberOfEnemies) {
			if(enemyCounter == timeBetweenEnemySpawn) {
				addEnemy();
				enemyCounter=0;
			}else {
				enemyCounter++;
			}
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).move();
			if(enemies.get(i).checkIfOutOfBounds(getPreferredSize())) {
				enemies.remove(i);
				hitpoints--;
			}else {
				for(int j = 0; j < bullets.size(); j++) {
					if(enemies.get(i).checkIfhit(bullets.get(j))) {
						explosions.add(new Explosion(enemies.get(i).getX()-90, enemies.get(i).getY()-50));
						enemies.remove(i);
						bullets.remove(j);
						break;
					}
				}
			}
		}
		
		repaint();	
	}
	
	public static void initKeyboard(Main game) {
		
		//W pressed
		Action WPressed = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				w = true;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("W"),"pressedW");
		game.getActionMap().put("pressedW",WPressed);
		
		//W released 
		Action WReleased  = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				w = false;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("released W"),"releasedW");
		game.getActionMap().put("releasedW",WReleased);		
		
		//S pressed
		Action SPressed = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				s = true;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("S"),"pressedS");
		game.getActionMap().put("pressedS",SPressed);

		//S released 
		Action SReleased = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				s = false;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("released S"),"releasedS");
		game.getActionMap().put("releasedS",SReleased);
		
		//A pressed
		Action APressed = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				a = true;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("A"),"pressedA");
		game.getActionMap().put("pressedA",APressed);

		//A released 
		Action AReleased = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				a = false;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("released A"),"releasedA");
		game.getActionMap().put("releasedA",AReleased);
		
		//D pressed
		Action DPressed = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				d = true;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("D"),"pressedD");
		game.getActionMap().put("pressedD",DPressed);	

		//D released 
		Action DReleased = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				d = false;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("released D"),"releasedD");
		game.getActionMap().put("releasedD",DReleased);	
		
		//SPACE pressed
		Action SPACEPressed = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				space = true;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"pressedSPACE");
		game.getActionMap().put("pressedSPACE",SPACEPressed);	

		//SPACE released 
		Action SPACEReleased = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				space = false;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"),"releasedSPACE");
		game.getActionMap().put("releasedSPACE",SPACEReleased);		
		
		//ENTER pressed
		Action ENTERPressed = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				enter = true;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"pressedENTER");
		game.getActionMap().put("pressedENTER",ENTERPressed);	

		//ENTER released 
		Action ENTERReleased = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				enter = false;			
			}
		};
		game.getInputMap().put(KeyStroke.getKeyStroke("released ENTER"),"releasedENTER");
		game.getActionMap().put("releasedENTER",ENTERReleased);	
	}		
}	