package Standby;

import java.awt.Graphics;
import java.awt.Rectangle;

import Mole.Game.Entities.EntityA;

public class Bullet implements EntityA {

	private Game game;
	private Mole mole;
	private double x;
	private double y;
	
	private Textures texture;

	SpriteSheet spr = null;

	public Bullet(double x, double y, Textures texture, Game game) {
		this.x = x;
		this.y = y;
		this.texture = texture; 
		this.game = game;
	}

	public void tick() {
		if (game.buldirection)
			x += 5;
		else
			x -= 5;
		
		if(Physics.Collision(this,game.m))
		{
			System.out.println("COLLISION DETECTED");
		}
	}

	public void render(Graphics g) {
		if (game.buldirection)
			g.drawImage(texture.bullet[0], (int) x, (int) y, null);
		else
			g.drawImage(texture.bullet[1], (int) x, (int) y, null);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 16, 16);
	}
}
