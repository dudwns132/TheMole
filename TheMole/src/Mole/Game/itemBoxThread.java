package Mole.Game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class itemBoxThread extends JLabel{
	private ImageIcon itemB = new ImageIcon("img/itemBox.png");
	private int itemsecond;
	private int itemcount = 0;
	private boolean timerstop = false;
	private boolean enhenceteeth = false;
	private boolean trap;
	private boolean snakepipe;
			
	public itemBoxThread() {
		setBounds(0, 0, 0, 0);
		setIcon(itemB);
	}
	public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
	
	public boolean getTimerstop() {
		return timerstop;
	}
	public void setTimerstop(boolean timerstop) {
		this.timerstop = timerstop;
	}

}