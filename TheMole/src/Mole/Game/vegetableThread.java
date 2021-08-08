package Mole.Game;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class vegetableThread extends JLabel{
	public static ImageIcon[] vegetables = { new ImageIcon("img/vegetableResource/vegetable0.png"),
			new ImageIcon("img/vegetableResource/vegetable1.png"), new ImageIcon("img/vegetableResource/vegetable2.png"),
			new ImageIcon("img/vegetableResource/vegetable3.png"), new ImageIcon("img/vegetableResource/vegetable4.png"),
			new ImageIcon("img/vegetableResource/vegetable5.png"), new ImageIcon("img/vegetableResource/vegetable6.png"),
			new ImageIcon("img/vegetableResource/vegetable7.png"), new ImageIcon("img/vegetableResource/vegetable8.png"),
			new ImageIcon("img/vegetableResource/vegetable9.png") };
	private int section;
	public Timer vegtimer;
	public int vegsecond;
	private int vegcount = 0;
	public boolean timerstop = false;
	

	public vegetableThread(int section, int crop)  {
		setBounds(section, 240, 32, 32);
		setIcon(vegetables[crop]);
	}
	public ImageIcon[] getVegetables() {
		return vegetables;
	}
	public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), 32, 32);
    }

	public int plusvegcount() {
		return vegcount++;
	}
}