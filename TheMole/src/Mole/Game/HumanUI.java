package Mole.Game;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;
import javazoom.jl.decoder.JavaLayerException;

public class HumanUI extends JPanel {

	private BufferedImage backImage;
	private BufferedImage humanHud;
	private BufferedImage humanInv;
	private BufferedImage intHuman;
	private BufferedImage moleHud;
	private BufferedImage intMole;
	private Human human;
	
	private vegetableThread v1;
	private vegetableThread v2;
	private vegetableThread v3;
	
	private itemBoxThread i1;
	private itemBoxThread i2;
	
	private HumanInMole m1, m2, m3, m4, m5, m6, m7, m8, m9;
	
	private boolean humtrap = false;
	private boolean timerstop = false;
	
	private JLabel counterLabel;
	private String ddSecond, ddMinute;
	private DecimalFormat dFormat = new DecimalFormat("00");
	private int second, minute;
	private Timer timer;
	private Font font1 = new Font("Arial", Font.BOLD, 30);
	private Font font2 = new Font("Arial", Font.BOLD, 15);
	private ChannelHandlerContext ctx;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int vegcount = 15;
	private int snakesecond = 15;
	private HumanSnake snake;
	private Timer snakeTimer;
	private boolean isSnake = false;
	private boolean humStop = false;
	private JLabel bulletLabel;
	private JLabel lifeLabel;
	private JLabel vegCountLabel;
	private JLabel moleCountLabel;
	private int moleCount = 9;
	
	private SoundJLayer soundToPlay = new SoundJLayer("sound/ingameBG_Lisport.mp3");
	public SoundJLayer getSoundToPlay() {
		return soundToPlay;
	}
	public void setSoundToPlay(SoundJLayer soundToPlay) {
		this.soundToPlay = soundToPlay;
	}
	private boolean musicStatus = true;
	public boolean getMusicStatus() {
		return musicStatus;
	}
	public void setMusicStatus(boolean musicStatus) {
		this.musicStatus = musicStatus;
	}
	public HumanUI(ChannelHandlerContext ctx, String name, int v1Location, int v2Location, int v3Location, int crop1, int crop2, int crop3) throws IOException {
		this.ctx = ctx;
		this.name = name;
		setLayout(null);
		backImage = ImageIO.read(new File("img/Back4.png"));
		humanHud = ImageIO.read(new File("img/humanHud.png"));
		humanInv = ImageIO.read(new File("img/inventory.png"));
		intHuman = ImageIO.read(new File("img/humanint.png"));
		moleHud = ImageIO.read(new File("img/moleHud.png"));
		intMole = ImageIO.read(new File("img/moleint.png"));
		human = new Human(this, 200, 225, ctx, name);
		add(human);
		
		soundToPlay.play();
		
		v1 = new vegetableThread(v1Location, crop1);
		v2 = new vegetableThread(v2Location, crop2);
		v3 = new vegetableThread(v3Location, crop3);
		add(v1);
		add(v2);
		add(v3);
		
		i1 = new itemBoxThread();
		i2 = new itemBoxThread();
		add(i1);
		add(i2);
		i1.setVisible(false);
		i2.setVisible(false);
		
		m1 = new HumanInMole(this, 50, 400);
		m2 = new HumanInMole(this, 100, 400);
		m3 = new HumanInMole(this, 150, 400);
		m4 = new HumanInMole(this ,50, 450);
		m5 = new HumanInMole(this, 100, 450);
		m6 = new HumanInMole(this, 150, 450);
		m7 = new HumanInMole(this, 50, 500);
		m8 = new HumanInMole(this, 100, 500);
		m9 = new HumanInMole(this, 150, 500);
		add(m1);
		add(m2);
		add(m3);
		add(m4);
		add(m5);
		add(m6);
		add(m7);
		add(m8);
		add(m9);
		
		counterLabel = new JLabel("");
		counterLabel.setBounds(345, 0, 100, 50);
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(font1);

		add(counterLabel);

		counterLabel.setText("03:00");
		second = 0;
		minute = 3;		
	//	normalTimer();
		//timer.start();
		
		bulletLabel = new JLabel("5");
		bulletLabel.setBounds(21, 90, 20, 20);
		bulletLabel.setHorizontalAlignment(JLabel.CENTER);
		bulletLabel.setFont(font2);
		
		lifeLabel = new JLabel("2");
		lifeLabel.setBounds(21, 70, 20, 20);
		lifeLabel.setHorizontalAlignment(JLabel.CENTER);
		lifeLabel.setFont(font2);
		
		vegCountLabel = new JLabel("15");
		vegCountLabel.setBounds(758, 90, 20, 20);
		vegCountLabel.setHorizontalAlignment(JLabel.CENTER);
		vegCountLabel.setFont(font2);
		
		moleCountLabel = new JLabel(moleCount + "");
		moleCountLabel.setBounds(758, 70, 20, 20);
		moleCountLabel.setHorizontalAlignment(JLabel.CENTER);
		moleCountLabel.setFont(font2);
		
		add(bulletLabel);
		add(lifeLabel);	
		add(vegCountLabel);
		add(moleCountLabel);
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_M) {
					if (musicStatus == true) {
						try {
							soundToPlay.pause();
							musicStatus = false;
						} catch (JavaLayerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						soundToPlay.play();
						musicStatus = true;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
	}
	public JLabel getLifeLabel() {
		return lifeLabel;
	}
	public void setLifeLabel(JLabel lifeLabel) {
		this.lifeLabel = lifeLabel;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public JLabel getVegCountLabel() {
		return vegCountLabel;
	}
	public vegetableThread getV1() {
		return v1;
	}
	public vegetableThread getV2() {
		return v2;
	}
	public vegetableThread getV3() {
		return v3;
	}
	public itemBoxThread getI1() {
		return i1;
	}
	public itemBoxThread getI2() {
		return i2;
	}
	public Human getHuman() {
		return human;
	}
	public HumanInMole getM1() {
		return m1;
	}
	public HumanInMole getM2() {
		return m2;
	}
	public HumanInMole getM3() {
		return m3;
	}
	public HumanInMole getM4() {
		return m4;
	}
	public HumanInMole getM5() {
		return m5;
	}
	public HumanInMole getM6() {
		return m6;
	}
	public HumanInMole getM7() {
		return m7;
	}
	public HumanInMole getM8() {
		return m8;
	}
	public HumanInMole getM9() {
		return m9;
	}
	public JLabel getCounterLabel() {
		return counterLabel;
	}
	public void setCounterLabel(JLabel counterLabel) {
		this.counterLabel = counterLabel;
	}
	public String getDdSecond() {
		return ddSecond;
	}
	public void setDdSecond(String ddSecond) {
		this.ddSecond = ddSecond;
	}
	public String getDdMinute() {
		return ddMinute;
	}
	public void setDdMinute(String ddMinute) {
		this.ddMinute = ddMinute;
	}
	public void makeSnake(int x) {
		snake = new HumanSnake(ctx, name, this, x);
		add(snake);
		isSnake = true;
	}
	public void sethumtrap(boolean a) {
        humtrap = a;
    }
    public boolean gethumtrap() {
        return humtrap;
    }
	public boolean getTimerstop() {
		return timerstop;
	}
	public void setTimerstop(boolean timerstop) {
		this.timerstop = timerstop;
	}
	public int getVegcount() {
		return vegcount;
	}
	public void setVegcount(int vegcount) {
		this.vegcount = vegcount;
	}
	public boolean getIsSnake() {
		return isSnake;
	}
	public void setIsSnake(boolean isSnake) {
		this.isSnake = isSnake;
	}
	public HumanSnake getSnake() {
		return snake;
	}
	public boolean getHumStop() {
		return humStop;
	}
	public void setHumStop(boolean humStop) {
		this.humStop = humStop;
	}
	public int getMoleCount() {
		return moleCount;
	}
	public void setMoleCount(int moleCount) {
		this.moleCount = moleCount;
	}
	public JLabel getMoleCountLabel() {
		return moleCountLabel;
	}
	public DecimalFormat getdFormat() {
		return dFormat;
	}
	public void setdFormat(DecimalFormat dFormat) {
		this.dFormat = dFormat;
	}
	public JLabel getBulletLabel() {
		return bulletLabel;
	}
	public void setBulletLabel(JLabel bulletLabel) {
		this.bulletLabel = bulletLabel;
	}
	public void paintComponent(Graphics g) {// 그리는 함수
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, null);
		g.drawImage(humanHud, 0, 70, null);
		g.drawImage(humanInv, 55, 0, null);
		g.drawImage(intHuman, 0, 0, null);
		g.drawImage(moleHud, 715, 70, null);
		g.drawImage(intMole, 740, 0, null);
		
		
	}
	
	public void moleMessage(String message, int x, int y) {
		if (message.equals("1"))
			m1.humanInMoleMove(message, x, y);
		else if (message.equals("2"))
			m2.humanInMoleMove(message, x, y);
		else if (message.equals("3"))
			m3.humanInMoleMove(message, x, y);
		else if (message.equals("4"))
			m4.humanInMoleMove(message, x, y);
		else if (message.equals("5"))
			m5.humanInMoleMove(message, x, y);
		else if (message.equals("6"))
			m6.humanInMoleMove(message, x, y);
		else if (message.equals("7"))
			m7.humanInMoleMove(message, x, y);
		else if (message.equals("8"))
			m8.humanInMoleMove(message, x, y);
		else if (message.equals("9"))
			m9.humanInMoleMove(message, x, y);
	}
	/*public void normalTimer() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				second--;

				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);
				bulletLabel.setText(human.getBulletCount() + "");
				lifeLabel.setText(human.getHumanLife() + "");
				counterLabel.setText(ddMinute + ":" + ddSecond);

				/*if (second == -1) {
					second = 59;
					minute--;

					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					counterLabel.setText(ddMinute + ":" + ddSecond);
				}*/
		/*		if (counterLabel.getText().equals("00:00")) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "인간 승리!!", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[HUMANWIN]," + LoginForm.getId() + ",");
					setVisible(false);
					if (MoleClientMainHandler.roomTest.testStart.getText().equals("준비취소"))
						MoleClientMainHandler.roomTest.testStart.setText("준비");
					MoleClientMainHandler.roomTest.ready.setText("");
					MoleClientMainHandler.roomTest.setVisible(true);
					if (musicStatus == true)
						try {
							soundToPlay.pause();
							MoleClientMainHandler.homePanel.getSoundToPlay().play();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
				else if (vegcount == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "두더지는 자유다 두더지 만만세", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[HUMANLOSE]," + LoginForm.getId() + ",");
					setVisible(false);
					if (MoleClientMainHandler.roomTest.testStart.getText().equals("준비취소"))
						MoleClientMainHandler.roomTest.testStart.setText("준비");
					MoleClientMainHandler.roomTest.ready.setText("");
					MoleClientMainHandler.roomTest.setVisible(true);
					if (musicStatus == true)
						try {
							soundToPlay.pause();
							MoleClientMainHandler.homePanel.getSoundToPlay().play();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
				else if (moleCount == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "인간 승리", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[HUMANWIN]," + LoginForm.getId() + ",");
					setVisible(false);
					if (MoleClientMainHandler.roomTest.testStart.getText().equals("준비취소"))
						MoleClientMainHandler.roomTest.testStart.setText("준비");
					MoleClientMainHandler.roomTest.ready.setText("");
					MoleClientMainHandler.roomTest.setVisible(true);
					if (musicStatus == true)
						try {
							soundToPlay.pause();
							MoleClientMainHandler.homePanel.getSoundToPlay().play();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
				else if (human.getHumanLife() == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "인간 패배", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[HUMANLOSE]," + LoginForm.getId() + ",");
					setVisible(false);
					if (MoleClientMainHandler.roomTest.testStart.getText().equals("준비취소"))
						MoleClientMainHandler.roomTest.testStart.setText("준비");
					MoleClientMainHandler.roomTest.ready.setText("");
					MoleClientMainHandler.roomTest.setVisible(true);
					if (musicStatus == true)
						try {
							soundToPlay.pause();
							MoleClientMainHandler.homePanel.getSoundToPlay().play();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		});
	}*/
	public void moleDie (String moleNumber) {
		if (moleNumber.equals("1"))
			m1.moleDie();
		else if (moleNumber.equals("2"))
			m2.moleDie();
		else if (moleNumber.equals("3"))
			m3.moleDie();
		else if (moleNumber.equals("4"))
			m4.moleDie();
		else if (moleNumber.equals("5"))
			m5.moleDie();
		else if (moleNumber.equals("6"))
			m6.moleDie();
		else if (moleNumber.equals("7"))
			m7.moleDie();
		else if (moleNumber.equals("8"))
			m8.moleDie();
		else if (moleNumber.equals("9"))
			m9.moleDie();
	}
}



