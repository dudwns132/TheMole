package Mole.Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB.DBConnection;
import MoleServer.MoleClient;
import MoleServer.MoleClientHandler;

public class SignUpForm extends JPanel {
	
	private LoginForm log;
	private JTextField idField; // 가입창 아이디
	private JPasswordField passwordField_1; // 가입창 비밀번호
	private JPasswordField passwordField_2; // 가입창 비밀번호 재입력
	private JButton signUpButton;
	private JButton backButton;
	private boolean idcheck = false;
	
	public SignUpForm(JPanel logP) {
		setBackground(Color.WHITE);
		setBounds(0, 0, 611, 362);
		setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Username");
		lblNewLabel_3.setBounds(352, 37, 95, 15);
		add(lblNewLabel_3);
		
		idField = new JTextField();
		idField.setBounds(352, 73, 201, 21);
		add(idField);
		idField.setColumns(10);
		
		JButton nameCheckbtn = new JButton("Check");
        nameCheckbtn.setBounds(480, 100, 72, 23);
        JLabel checkLabel = new JLabel("");
        checkLabel.setForeground(Color.RED);
        checkLabel.setBounds(420,85, 80, 50);
        add(checkLabel);
        nameCheckbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					MoleClient moleclient = new MoleClient();
					moleclient.future = moleclient.serverChannel.writeAndFlush("[DUPLICATE]" + "," + idField.getText());
					while(MoleClientHandler.serverMessage.equals("")) {
						Thread.sleep(300);
						if(!MoleClientHandler.serverMessage.equals(""))
							break;
					}
					if(MoleClientHandler.serverMessage.equals("DUPLICATE"))
						checkLabel.setText("중복");
					else 
						checkLabel.setText("중복아님");
					
					MoleClientHandler.serverMessage = "";
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
            }
        });
        add(nameCheckbtn);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(352, 125, 95, 15);
		add(passwordLabel);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(352, 156, 201, 21);
		add(passwordField_1);
		
		JLabel repeatPasswordLabel = new JLabel("Repeat Password");
		repeatPasswordLabel.setBounds(352, 206, 116, 15);
		add(repeatPasswordLabel);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(352, 236, 201, 21);
		add(passwordField_2);
		
		signUpButton = new JButton("SignUp");
		signUpButton.setBackground(Color.LIGHT_GRAY);
		signUpButton.setBounds(358, 302, 97, 23);
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = idField.getText();
				String password = passwordField_1.getText();
				String passwordcheck = passwordField_2.getText();
				if (password.equals(passwordcheck) && (name != "" && password != "")) {
					if(checkLabel.getText().equals("중복아님")) {
						try {
							MoleClient moleClient = new MoleClient();
							moleClient.future = moleClient.serverChannel.writeAndFlush("[SIGNUP]" + "," + name + "," + password);
							while(MoleClientHandler.serverMessage.equals("")) {
								Thread.sleep(300);
								if(!MoleClientHandler.serverMessage.equals(""))
									break;
							}
							if(MoleClientHandler.serverMessage.equals("SIGNUP")) {
								JOptionPane.showMessageDialog(log, "회원가입이 완료 되었습니다.");
								setVisible(false);
								logP.setVisible(true);
								
							}
							MoleClientHandler.serverMessage = "";
							idField.setText("");
							passwordField_1.setText("");
							passwordField_2.setText("");
							checkLabel.setText("");
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else
						JOptionPane.showMessageDialog(log, "정보가 일치하지 않습니다.\n fail to register");
				} else
					JOptionPane.showMessageDialog(log, "정보가 일치하지 않습니다.\n fail to register");
			}
		});
		add(signUpButton);
		
		backButton = new JButton("Back");
		backButton.setBackground(Color.LIGHT_GRAY);
		backButton.setBounds(487, 302, 97, 23);
		add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				logP.setVisible(true);
			}
		});
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(12, 10, 320, 320);
		lblNewLabel_6.setIcon(new ImageIcon(LoginForm.class.getResource("/images/signpages.png")));
		add(lblNewLabel_6);
		
	}
}