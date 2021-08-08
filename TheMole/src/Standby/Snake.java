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
	private static int status = -1; //�����ɶ� ���� ���ϰ�, ���� ���� ���� ������ �޶�����.

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

	public void tick() { // �޼ҵ带 ������Ʈ �Ҷ� ���
		y += velY;

		/*// �ΰ��� �̵����� - x
		if (x <= 0)
			x = 0;
		if (x >= 800 - 50)
			x = 800 - 50;
		*/
		if (status == 0) {
			rightMove.runAnimation();
			x += velX;
		} else { // 1�϶�
			leftMove.runAnimation();
			x -= velX;
		}
		/* //�浹 ����
		 * if(Physics.Collision(this,game.m)) {
		 * System.out.println("COLLISION DETECTED"); }
		 */
	}

	public void render(Graphics g) { // �̹��� �׸��� ���

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

	public boolean rightStand() { // ���������� ���� �������� = 0
		status = 0;
		return true;
	}

	public boolean leftStand() { // �������� ���� �������� = 1
		status = 1;
		return true;
	}

	public boolean rightMove() { // ���������� �̵��Ҷ� = 2
		status = 2;
		return true;
	}

	public boolean leftMove() { // �������� �̵��Ҷ� = 3
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