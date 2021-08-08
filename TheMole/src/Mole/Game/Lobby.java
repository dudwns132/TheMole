package Mole.Game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;

public class Lobby extends JPanel {
	
	private BufferedImage background;
	private Font font1 = new Font("SansSerif", Font.BOLD, 30);
	
	JPanel panel;
	JButton[] button;
	JButton testButton;
	JButton testButtonB;
	JButton outButton;

	public Lobby(ChannelHandlerContext ctx, LinkedList<String> roomList) {
		int j = 0;
		
			try {
				background = ImageIO.read(new File("img/moleroom.jpg"));
				setLayout(null);
				
		button = new JButton[roomList.size()];
		for(int i = 0; i < roomList.size(); i++) {
			button[i] = new JButton(roomList.get(i) + "님의 방");
			if (i % 3 == 0) {
				j += 1;
			}
			button[i].setBounds(130 + ((i % 3) * 200), 150 + (j * 75), 150, 25);
			
			add(button[i]);
			
			button[i].setBackground(Color.RED);
			button[i].setForeground(Color.WHITE);
			//button.setLayout(FlowLayout());
			
			button[i].addActionListener(e -> {
				String n = e.getActionCommand();
				String s[] = n.split("님");
				ctx.writeAndFlush("[JOIN]" + "," + s[0] + "," + LoginForm.getId());
			});
		}
		
		testButton = new JButton("방생성");
		add(testButton);
		testButton.setBounds(325, 30, 150, 75);
		testButton.setBackground(Color.DARK_GRAY);
		testButton.setForeground(Color.WHITE);
		testButton.setFont(font1);
		testButton.addActionListener(e -> {
			ctx.writeAndFlush("[CREAT]," + LoginForm.getId());
			
		});
		testButtonB = new JButton("새로고침");
		add(testButtonB);
		testButtonB.setBounds(20, 20, 100, 25);
		testButtonB.setBackground(Color.DARK_GRAY);
		testButtonB.setForeground(Color.WHITE);
		testButtonB.addActionListener(e -> {
			setVisible(false);
			ctx.writeAndFlush("[REFRESH]");
		});
		outButton = new JButton("나가기");
		add(outButton);
		outButton.setBounds(680, 20, 100, 25);
		outButton.setBackground(Color.DARK_GRAY);
		outButton.setForeground(Color.WHITE);
		outButton.addActionListener(e -> {
			setVisible(false);
			MoleClientMainHandler.homePanel.setVisible(true);
		});
		
		//add(p0);
		//setVisible(true);
		
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
}

