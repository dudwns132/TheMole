package Standby;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Mole.Game.Entities.EntityA;
import Mole.Game.libs.Animation;

public class Human implements EntityA {

	private Game game;
	private double x;
	private double y;

	private double velX = 0;
	private double velY = 0;
	private static int status = 0;
	
	private Textures texture;
	
	Animation leftMove, rightMove;

	BufferedImage leftStand;
	BufferedImage rightStand;
	
	public Human(double x, double y, Textures tex,Game game) {
		this.x = x;
		this.y = y;
		this.texture = tex;
		this.game = game;
		
		leftMove = new Animation(5,tex.human[6],tex.human[7],tex.human[8],tex.human[9]);
		rightMove = new Animation(5,tex.human[1],tex.human[2],tex.human[3],tex.human[4]);
	}

	public void tick() { // 메소드를 업데이트 할때 사용
		x += velX;
		y += velY;

		if (x <= 0)
			x = 0;
		if (x >= 800 - 50)
			x = 800 - 50;
		/*
		 * if(y <= 0) y = 0; if(y >= 600 - 64) y = 600 - 64;
		 */
		
		rightMove.runAnimation();
		leftMove.runAnimation();
		
		if(Physics.Collision(this,game.m))
		{
			System.out.println("COLLISION DETECTED");
		}
	}

	public void render(Graphics g) { // 이미지 그릴때 사용

		switch(status) {
		case 0: g.drawImage(texture.human[0], (int) x, (int) y, null); break;
		case 1: g.drawImage(texture.human[5], (int) x, (int) y, null); break;
		case 2: rightMove.drawAnimation(g, x, y, 0); break;
		case 3: leftMove.drawAnimation(g, x, y, 0); break;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getVelX() {
		return velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public boolean rightStand() { // 오른쪽으로 보고 서있을때 = 0
		status = 0;
		return true;
	}

	public boolean leftStand() { // 왼쪽으로 보고 서있을때 = 1
		status = 1;
		return true;
	}

	public boolean rightMove() { // 오른쪽으로 이동할때 = 2
		status = 2;
		return true;
	}

	public boolean leftMove() { // 왼쪽으로 이동할때 = 3
		status = 3;
		return true;
	}
	public int getStatus() {
		return status;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 50, 64);
	}

}