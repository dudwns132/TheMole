package Mole.Game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;

public class InRoom extends JPanel {
	private BufferedImage background;
	
	public static JButton testStart;
	JButton testOut;
	JButton host;
	JButton guest;
	JTextField chatField;
	private JTextArea chatArea;
	JScrollPane scroll;
	public static JLabel ready;
	public InRoom(ChannelHandlerContext ctx, String hostName, String guestName) {
		try {
		background = ImageIO.read(new File("img/moleroommade.jpg"));
		//setLayout(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		testStart = new JButton("시작");
		testStart.setBackground(Color.DARK_GRAY);
		testStart.setForeground(Color.WHITE);
		testOut = new JButton("나가기");
		testOut.setBackground(Color.DARK_GRAY);
		testOut.setForeground(Color.WHITE);
		//testOut.setBounds(680, 20, 100, 25);
		host = new JButton(hostName);
		host.setBackground(Color.DARK_GRAY);
		host.setForeground(Color.RED);
		host.addActionListener(e -> {
			ctx.writeAndFlush("[INFORMATION]" + "," + hostName);
		});
		guest = new JButton(guestName);
		guest.setBackground(Color.DARK_GRAY);
		guest.setForeground(Color.RED);
		guest.addActionListener(e -> {
			ctx.writeAndFlush("[INFORMATION]" + "," + guestName);
		});
		ready = new JLabel("");
		testOut.addActionListener(e -> {
			setVisible(false);
			if(LoginForm.getId().equals(hostName))
				ctx.writeAndFlush("[HOSTOUT]," + hostName);
			else
				ctx.writeAndFlush("[GUESTOUT]," + hostName);
		});	
		
		testStart.addActionListener(e -> {
			if (testStart.getText().equals("준비")) { 
				testStart.setText("준비취소");
				ctx.writeAndFlush("[READY]," + hostName);
			} 
			else if (testStart.getText().equals("준비취소")) {
				testStart.setText("준비");
				ctx.writeAndFlush("[CANSLE]," + hostName);
			}
			else
				if (ready.getText().equals("준비완료"))
					ctx.writeAndFlush("[START]," + hostName + "," + guestName);
				else
					JOptionPane.showMessageDialog(MoleClientMainHandler.mainFrame, "상대방이 준비하지 않았습니다.");
		});
		chatField = new JTextField(30);
		chatField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if (!chatField.getText().equals("")) {
						ctx.writeAndFlush("[SENDMESSAGE]," + chatField.getText() + "," + LoginForm.getId() + "," + hostName);
						chatField.setText("");
					}
				}
			}
		});
		chatArea = new JTextArea(10, 30);
		chatArea.setEditable(false);
		scroll = new JScrollPane(chatArea);
		add(ready);
		add(host);
		add(guest);
		add(testStart);
		add(testOut);
		add(chatField);
		add(scroll);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
	public JTextArea getChatArea() {
		return chatArea;
	}
	public void setChatArea(JTextArea chatArea) {
		this.chatArea = chatArea;
	}
	public JScrollPane getScroll() {
		return scroll;
	}
}
