import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	static final int SC_W =600;
	static final int SC_H=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS = (SC_W*SC_H)/UNIT_SIZE;
	static int DELAY = 75;
	static final int wallsOffDuration = 3750;
	final int x[] = new int [GAME_UNITS];
	final int y[] = new int [GAME_UNITS];
	int bodyParts =6;
	int applesEten;
	int appleX;
	int appleY;
	int wallsoffX=-100;
	int wallsoffY=-100;
	int slowDownX=-200;
	int slowDownY=-200;
	int changeColorX =-300;
	int changeColorY =-300;
	int changeColorHeadX=-400;
	int changeColorHeadY=-400;
	int wallsOffTimer;
	char direction = 'R';
	boolean running = false;
	boolean startingScreen = true;
	boolean wallsoff = false;
	boolean wallsOffSpawn = true;
	Color kolor = new Color(45,180,0);
	Color kolor2 = Color.white;
	Color kolorHead=Color.green;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
	//	kolor2 = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
		this.setPreferredSize(new Dimension(SC_W,SC_H));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		
//		startGame();
	}
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running==true) {
			/*
			for(int i=0;i<SC_H/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SC_H);
				g.drawLine(0, i*UNIT_SIZE, SC_W, i*UNIT_SIZE);
			}
			*/
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(kolorHead);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(kolor);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			if(wallsoff==false) {
				g.setColor(Color.blue);
				g.drawLine(0, 0, 0, SC_H);
				g.drawLine(1, 0, 1, SC_H);
				g.drawLine(0, 0, SC_W,0);
				g.drawLine(0, 1, SC_W,1);
				g.drawLine(0, 2, SC_W,2);
				g.drawLine(0, 3, SC_W,3);
				g.drawLine(0, SC_H-1, SC_W, SC_H-1);
				g.drawLine(0, SC_H-2, SC_W, SC_H-2);
				g.drawLine(SC_W-1, 0, SC_W-1, SC_H);
				g.drawLine(SC_W-2, 0, SC_W-2, SC_H);
			}
			

			g.setColor(Color.blue);
			g.fillRect(wallsoffX, wallsoffY, UNIT_SIZE, UNIT_SIZE);
			g.setColor(Color.yellow);
			g.fillRoundRect(slowDownX, slowDownY, UNIT_SIZE, UNIT_SIZE, 10, 10);
			g.setColor(Color.white);
			g.fillOval(changeColorX, changeColorY, UNIT_SIZE, UNIT_SIZE);
			g.fillRect(changeColorHeadX, changeColorHeadY, UNIT_SIZE, UNIT_SIZE);
			

			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEten, (SC_W - metrics1.stringWidth("Score: "+applesEten))/2, g.getFont().getSize());
		}
		else if(startingScreen==true) {
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,50));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("Press s to start game", (SC_W - metrics2.stringWidth("Press s to start game"))/2, SC_H/2);
		}
			
		
		else {
			gameOver(g);
		}
		
	}
	public void newApple() {
		appleX = random.nextInt((int)(SC_W/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SC_H/UNIT_SIZE))*UNIT_SIZE;
	}
	public void newWallsOff() {
		wallsoffX = random.nextInt((int)(SC_W/UNIT_SIZE))*UNIT_SIZE;
		wallsoffY = random.nextInt((int)(SC_H/UNIT_SIZE))*UNIT_SIZE;
	}
	public void newSlowDown() {
		slowDownX = random.nextInt((int)(SC_W/UNIT_SIZE))*UNIT_SIZE;
		slowDownY = random.nextInt((int)(SC_H/UNIT_SIZE))*UNIT_SIZE;
	}
	public void newChangeColor() {
		changeColorX = random.nextInt((int)(SC_W/UNIT_SIZE))*UNIT_SIZE;
		changeColorY = random.nextInt((int)(SC_H/UNIT_SIZE))*UNIT_SIZE;
	}
	public void newChangeColorHead() {
		changeColorHeadX = random.nextInt((int)(SC_W/UNIT_SIZE))*UNIT_SIZE;
		changeColorHeadY = random.nextInt((int)(SC_H/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() {
		for(int i = bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
//			System.out.println(i + " - i");
//			System.out.println(x[i] + "- x");
//			System.out.println(y[i] + "- y");
		}
		
		switch(direction) {
		case 'U':
			if(wallsoff==true&&y[0]- UNIT_SIZE<0)
				y[0]=575;
			else
			y[0]= y[0] - UNIT_SIZE;
			break;
		case 'D':
			if(wallsoff==true&&y[0]+ UNIT_SIZE>575)
				y[0]=0;
			else
			y[0]= y[0] + UNIT_SIZE;
			break;
		case 'L':
			if(wallsoff==true&&x[0]- UNIT_SIZE<0) 
				x[0]=575;
			else
			x[0]= x[0] - UNIT_SIZE;
			break;
		case 'R':
			if(wallsoff==true&&x[0]+ UNIT_SIZE>575)
				x[0]=0;
			else
			x[0]= x[0] + UNIT_SIZE;
			break;
		}
	}
	public void checkApple() {
		if((x[0] == appleX)&&(y[0] == appleY)) {
			bodyParts++;
			applesEten++;
			DELAY--;
			timer.setDelay(DELAY);
			newApple();
		}
	}
	public void checkWallsOff() {
		if((x[0] == wallsoffX)&&(y[0] == wallsoffY)) {
			wallsoff =true;
			wallsoffX=2000;
			wallsoffY=2000;
		}
	}
	public void checkSlowDown() {
		if((x[0] == slowDownX)&&(y[0] == slowDownY)) {
			slowDownX=-200;
			slowDownY=-200;
			DELAY++;
			timer.setDelay(DELAY);
		}
	}
	public void checkChangeColor() {
		if((x[0] == changeColorX)&&(y[0] == changeColorY)) {
			changeColorX=-300;
			changeColorY=-300;
			kolor=new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
		}
	}
	public void checkChangeColorHead() {
		if((x[0] == changeColorHeadX)&&(y[0] == changeColorHeadY)) {
			changeColorHeadX=-400;
			changeColorHeadY=-400;
			kolorHead=new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
		}
	}
	public int chance() {
		int chanceForEvents=(int)(Math.random()*200);
		return chanceForEvents;
	}
	public void checkCollisions() {
		//checks if head colliedes with body
		for(int i = bodyParts;i>0;i--) {
			if((x[0]==x[i])&&(y[0]==y[i])) {
				running = false;
			}
			//check if head touches left border
			if(x[0]<0) {
				running = false;
			}
			//check if head touches top border
			if(y[0]<0) {
				running = false;
			}
			//check if head touches right border
			if(x[0]>SC_W) {
				running = false;
			}
			//check if head touches bottom border
			if(y[0]>SC_H) {
				running = false;
			}
			if(running==false)
				timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEten, (SC_W - metrics1.stringWidth("Score: "+applesEten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SC_W - metrics2.stringWidth("Game Over"))/2, SC_H/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
			int chanceForEvents=chance();
			if(chanceForEvents==1&&wallsoffX<0&&wallsoffY<0)
				newWallsOff();
			if((chanceForEvents==2||chanceForEvents==3)&&slowDownX<0)
				newSlowDown();
			if((chanceForEvents==4||chanceForEvents==5)&&changeColorX<0)
				newChangeColor();
			if((chanceForEvents==6||chanceForEvents==7)&&changeColorHeadX<0)
				newChangeColorHead();
			checkWallsOff();
			checkSlowDown();
			checkChangeColor();
			checkChangeColorHead();
//			System.out.println(wallsoffX);
//			System.out.println(wallsoffY);
			System.out.println(changeColorX + " - x changeColor");
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!= 'R') {
					direction = 'L';
					break;
				}
			case KeyEvent.VK_RIGHT:
				if(direction!= 'L') {
					direction = 'R';
					break;
				}
			case KeyEvent.VK_UP:
				if(direction!= 'D') {
					direction = 'U';
					break;
				}
			case KeyEvent.VK_DOWN:
				if(direction!= 'U') {
					direction = 'D';
					break;
				}
			case KeyEvent.VK_S:
				startGame();
				startingScreen=false;
				
			}
		}
	}
}
