package Mole.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import DB.DBConnection;
import MoleServer.MoleClient;
import MoleServer.MoleClientHandler;

public class LoginForm extends JFrame {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			LoginForm window = new LoginForm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		LoginForm.id = id;
	}

	public static String getSecret() {
		return secret;
	}
	
	public static void setSecret(String secret) {
		LoginForm.secret = secret;
	}

	private LoginForm log;
	private JTextField username; // 로그인창 아이디
	private JPasswordField passwordField; // 로그인창 비밀번호
	private JButton loginButton;
	private JButton signupButton; //회원가입창으로 이동하는 버튼
	private JButton nameCheckbtn;
	private JPanel loginPanel;
	private boolean idCheck = false;
	
	private static String id;
	private static String secret;

	public LoginForm() {
		setTitle("Mole Game");
		setSize(627, 401);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 아이콘 이미지 설정
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image icon = kit.getImage("img/moleicon.png");
		setIconImage(icon);

		loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBounds(0, 0, 611, 362);
		loginPanel.setLayout(null);

		loginButton = new JButton("Login");
		loginButton.getText();
		loginButton.setBackground(Color.LIGHT_GRAY);
		loginButton.setBounds(358, 302, 97, 23);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!username.getText().equals("") && !passwordField.getText().equals("")) {
					id = username.getText();
					try {
						MoleClient moleClient = new MoleClient();
						moleClient.future = moleClient.serverChannel.writeAndFlush("[LOGIN]" + "," + username.getText() + "," + passwordField.getText());
						while(MoleClientHandler.serverMessage.equals("")) {
							Thread.sleep(300);
							if(!MoleClientHandler.serverMessage.equals(""))
								break;
						}
						if(MoleClientHandler.serverMessage.equals("LOGIN")) {
							dispose();
						} else if (MoleClientHandler.serverMessage.equals("LOGINFAIL")) {
							JOptionPane.showMessageDialog(log, "Invalid username or password");
							username.setText("");
							passwordField.setText("");
						} else {
							JOptionPane.showMessageDialog(log, "AlreadyConnected");
							username.setText("");
							passwordField.setText("");
						}
					} catch (InterruptedException a) {
						a.printStackTrace();
					}
				} else
					JOptionPane.showMessageDialog(log, "Invalid username or password");
				MoleClientHandler.serverMessage = "";
			}
		});
		loginPanel.add(loginButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(368, 233, 205, 20);
		loginPanel.add(passwordField);
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if(!username.getText().equals(null) && !passwordField.getText().equals(null)) {
						id = username.getText();
						try {
							MoleClient moleClient = new MoleClient();
							moleClient.future = moleClient.serverChannel.writeAndFlush("[LOGIN]" + "," + username.getText() + "," + passwordField.getText());
							while(MoleClientHandler.serverMessage.equals("")) {
								Thread.sleep(300);
								if(!MoleClientHandler.serverMessage.equals(""))
									break;
							}
							if(MoleClientHandler.serverMessage.equals("LOGIN")) {
								dispose();
							} else {
								JOptionPane.showMessageDialog(log, "Invalid username or password");
								username.setText("");
								passwordField.setText("");
							}
						} catch (InterruptedException a) {
							a.printStackTrace();
						}
					}
				}
			}		
		});
		
		signupButton = new JButton("SignUp");
		signupButton.setBackground(Color.LIGHT_GRAY);
		signupButton.setBounds(487, 302, 97, 23);
		loginPanel.add(signupButton);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 54));
		lblNewLabel.setBounds(368, 4, 166, 82);
		loginPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(368, 108, 97, 15);
		loginPanel.add(lblNewLabel_1);

		username = new JTextField();
		username.setBounds(368, 141, 205, 21);
		loginPanel.add(username);
		username.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(368, 199, 97, 15);
		loginPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(12, 10, 320, 320);
		lblNewLabel_7.setIcon(new ImageIcon(LoginForm.class.getResource("/images/loginpages.png")));
		loginPanel.add(lblNewLabel_7);

		add(loginPanel);
		setVisible(true);

		SignUpForm sf = new SignUpForm(loginPanel);
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add(sf);
				loginPanel.setVisible(false);
				sf.setVisible(true);
			}
		});
	}
}