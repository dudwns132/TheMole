package Mole.Game.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface EntityA { // ÀÎ°£°ú ÃÑ¾Ë
	
	public void tick();
	public void render(Graphics g);
	public Rectangle getBounds();
	
	public double getX();
	public double getY();
}
