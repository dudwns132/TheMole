package Mole.Game.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface EntityA { // �ΰ��� �Ѿ�
	
	public void tick();
	public void render(Graphics g);
	public Rectangle getBounds();
	
	public double getX();
	public double getY();
}
