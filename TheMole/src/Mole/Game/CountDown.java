package Mole.Game;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CountDown extends JLabel {

	private Font font1 = new Font("Arial", Font.BOLD, 30);
	private JLabel counterLabel;
	private Timer timer;
	private int second, minute;
	private String ddSecond, ddMinute;
	private DecimalFormat dFormat = new DecimalFormat("00");

	public CountDown() {
		//setBounds(345, -30, 100, 100);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(font1);
		setText("03:00"); // 시간 설정 : 3:00
		/*
		counterLabel = new JLabel("");
		//counterLabel.setBounds(345, -30, 100, 100);
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(font1);
		counterLabel.setText("03:00"); // 시간 설정 : 3:00
		add(counterLabel);
		*/
		
		second = 0;
		minute = 1;
		normalTimer(this);
		timer.start();
	}
	public void normalTimer(JLabel label) {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				second--;

				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);

				label.setText(ddMinute + ":" + ddSecond);

				if (second == -1) {
					second = 59;
					minute--;

					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					label.setText(ddMinute + ":" + ddSecond);
				}
				if (minute == 0 && second == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "인간 승리!!", "Result", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
	}
}
