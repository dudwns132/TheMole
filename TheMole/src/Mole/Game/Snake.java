package Mole.Game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Snake extends JLabel {

	private int x;
	private int y;
	private int status; // 생성될때 값을 지니고, 값에 따라 오는 방향이 달라진다.

	private MoleUI molePanel;
	
	private Timer move;
	private int snakeTempo = 0;
	private boolean moving = true;

	// 4개의 스프라이트 이미지를 가짐
	private ImageIcon snake[] = { new ImageIcon("img/snakeResource/snakeL1.png"),
			new ImageIcon("img/snakeResource/snakeL2.png"), new ImageIcon("img/snakeResource/snakeR1.png"),
			new ImageIcon("img/snakeResource/snakeR2.png") };

	public Snake(MoleUI molePanel, int status) {
		this.y = 250;
		this.molePanel = molePanel;
		this.status = status;
		
		if (status == 0) // 왼쪽으로 보고 이동하는 뱀
			this.x = 760;
		else // 오른쪽으로 보고 이동하는 뱀
			this.x = 40;

		setBounds(x, y, 32, 32);
		if (status == 0) // 왼쪽 스프라이트
			setIcon(snake[0]);
		else
			setIcon(snake[2]); // 오른쪽 스프라이트
	//	moveStart();
	}
	public int getStatus() {
		return status;
	}
	public boolean getMoving () {
		return moving;
	}
	
/*	public void moveStart() {
		move();
		move.start();
	}*/
	public void snakeDie() {
		setVisible(false);
		//move.stop();
		moving = false;
	}

	public void move(int moveCount) {
	//	move = new Timer(20, new ActionListener() {
			//@Override
			//public void actionPerformed(ActionEvent e) {
				//snakeTempo++;
				//snakeTempo = snakeTempo % 2;
				if (status == 0) { // 왼쪽 방향으로 움직임
					moveX(-3);
					if (moveCount % 2 == 0)
						setIcon(snake[0]);
					if (moveCount % 2 == 1)
						setIcon(snake[1]);
					setBounds(getX(),y,32,32);	
				} else if (status == 1) { // 왼쪽 방향으로 움직임
					moveX(3);
					if (moveCount % 2 == 0)
						setIcon(snake[2]);
					if (moveCount % 2 == 1)
						setIcon(snake[3]);
					setBounds(getX(),y,32,32);	
				}
			}
		//});
	//}
     //   });
//	public void collisionCheck(JLabel human) {
//		if(this.getBounds().intersects(human.getBounds()))
//			this.setVisible(false);
//	}

	public void moveX(int x) {
		this.x = this.x + x;
		//collisionCheck(human);
		if (status == 0) {
			if (this.x < 20)
				this.setVisible(false);
		} else {
			if(this.x > 780)
				this.setVisible(false);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
}