package Standby;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Mole.Game.Entities.EntityC;
import Mole.Game.libs.Animation;

public class Snake implements EntityC {

	private Game game;
	private double x;
	private double y;

	private double velX = 3;
	private double velY = 0;
	private static int status = -1; //생성될때 값을 지니고, 값에 따라 오는 방향이 달라진다.

	private Textures texture;

	Animation leftMove, rightMove;

	public Snake(Textures tex, Game game) {
		this.y = 250;
		this.texture = tex;
		this.game = game;

		status = (int) (Math.random() * 2);
		if(status == 0)
			this.x = 20;
		else
			this.x = 780;

		leftMove = new Animation(3, tex.snake[2], tex.snake[3]);
		rightMove = new Animation(3, tex.snake[0], tex.snake[1]);
	}

	public void tick() { // 메소드를 업데이트 할때 사용
		y += velY;

		/*// 인간의 이동제한 - x
		if (x <= 0)
			x = 0;
		if (x >= 800 - 50)
			x = 800 - 50;
		*/
		if (status == 0) {
			rightMove.runAnimation();
			x += velX;
		} else { // 1일때
			leftMove.runAnimation();
			x -= velX;
		}
		/* //충돌 감지
		 * if(Physics.Collision(this,game.m)) {
		 * System.out.println("COLLISION DETECTED"); }
		 */
	}

	public void render(Graphics g) { // 이미지 그릴때 사용

		switch (status) {
		case 0:
			rightMove.drawAnimation(g, x, y, 0);
			break;
		case 1:
			leftMove.drawAnimation(g, x, y, 0);
			break;
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