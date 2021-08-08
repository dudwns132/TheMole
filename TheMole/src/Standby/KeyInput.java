package Standby;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	Game game;
	
	KeyInput(Game game) {
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e) { // Key�� key ����
		game.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
}
