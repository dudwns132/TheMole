package Mole.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStart extends JPanel {
//	public static void main(String[] args) {
//		new A();
//	}
	private BufferedImage background;
	public JLabel molePlayer;
	public JLabel humanPlayer;
	public GameStart(String myName, String enemyName) {
		try {
			background = ImageIO.read(new File("img/gamestart.jpg"));
			setLayout(null);
			molePlayer = new JLabel("molePlayer");
			humanPlayer = new JLabel("humanPlayer");
			molePlayer.setBounds(390, 20, 130, 130);
			humanPlayer.setBounds(400, 440, 130, 130);
			molePlayer.setFont(new Font("San Serif", Font.PLAIN, 20));
			humanPlayer.setFont(new Font("San Serif", Font.PLAIN, 20));
			molePlayer.setForeground(Color.WHITE);
			humanPlayer.setForeground(Color.WHITE);
				
			add(molePlayer);
			add(humanPlayer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void paintComponent(Graphics g) {// 그리는 함수
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);// background를 그려줌
	}
}
class A extends JFrame	{
	public A() {
		setTitle("Mole Game");
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameStart gameStart= new GameStart("", "");
		add(gameStart);
		
		setVisible(true);
	}
}
