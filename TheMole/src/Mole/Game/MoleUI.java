package Mole.Game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;
import javazoom.jl.decoder.JavaLayerException;

public class MoleUI extends JPanel {
	/*
	 * public static void main(String[] args) throws IOException,
	 * InterruptedException { new f(); }
	 */

	private BufferedImage backImage, humanHud, moleHud, humanInv, moleInv, intHuman, intMole;
	private JLabel counterLabel;
	private JLabel vegcountLabel;
	private JLabel moleCountLabel;
	private int vegcount = 15;
	private int moleCount = 9;
	private boolean moleKill = true;

	private Font font1 = new Font("Arial", Font.BOLD, 30);
	private Font font2 = new Font("Arial", Font.BOLD, 15);

	Timer timer;

	private MoleThread m1;
	private MoleThread m2;
	private MoleThread m3;
	private MoleThread m4;
	private MoleThread m5;
	private MoleThread m6;
	private MoleThread m7;
	private MoleThread m8;
	private MoleThread m9;

	private vegetableThread v1;
	private vegetableThread v2;
	private vegetableThread v3;

	private ImageIcon itemteeth = new ImageIcon("img/strongteeth.png");
	private ImageIcon itemtrap = new ImageIcon("img/trapM.png");
	private ImageIcon itemsnakepipe = new ImageIcon("img/Snakepipe.png");

	private JLabel itembox1;
	private JLabel itembox2;
	private JLabel lifeLabel;
	
	private itemBoxThread i1;
	private itemBoxThread i2;
	Human hum;
	// itemBoxThread i2;
	// itemBoxThread i3;
	public int snakesecond = 15;
	Snake snake;
	private Timer snakeTimer;
	private boolean isSnake = false;
	int second, minute;
	String ddSecond, ddMinute;
	DecimalFormat dFormat = new DecimalFormat("00");
	private MoleInHumanPerformance moleInHumanPerformance;
	private ChannelHandlerContext ctx;
	private int sx, sy, ex, ey;
	
	private SoundJLayer soundToPlay = new SoundJLayer("sound/ingameBG_Lisport.mp3");
	private boolean musicStatus = true;
	
	private JButton music;
    ImageIcon music_img = new ImageIcon("img/music.png");

	public SoundJLayer getSoundToPlay() {
		return soundToPlay;
	}
	public void setSoundToPlay(SoundJLayer soundToPlay) {
		this.soundToPlay = soundToPlay;
	}
	public boolean getMusicStatus() {
		return musicStatus;
	}
	public void setMusicStatus(boolean musicStatus) {
		this.musicStatus = musicStatus;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MoleUI(ChannelHandlerContext ctx, String name, int v1Location, int v2Location, int v3Location, int crop1,
			int crop2, int crop3) {
		this.name = name;
		this.ctx = ctx;
		try {
			setLayout(null);
			backImage = ImageIO.read(new File("img/Back4.png"));
			humanHud = ImageIO.read(new File("img/humanHud.png"));
			moleHud = ImageIO.read(new File("img/moleHud.png"));
			humanInv = ImageIO.read(new File("img/inventory.png"));
			moleInv = ImageIO.read(new File("img/inventory.png"));
			intHuman = ImageIO.read(new File("img/humanint.png"));
			intMole = ImageIO.read(new File("img/moleint.png"));

			soundToPlay.play();
			
			m1 = new MoleThread(ctx, name, 50, 400, this);
			m1.moleButton.addActionListener(e -> {
				if (e.getSource() == m1.moleButton && m1.eating == false) {
					m1.moleButton.setIcon(m1.getMole()[13]);
				}
			});
			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (m1.moleButton.getIcon().equals(m1.getMole()[13]))
							m1.moleButton.setIcon(m1.getMole()[12]);
						if (m1.getDirection() == 1)
							m1.setDirection(3);
						else if (m1.getDirection() == 2)
							m1.setDirection(4);
					}
				}
			});

			m2 = new MoleThread(ctx, name, 100, 400, this);
			m2.moleButton.addActionListener(e -> {
				if (e.getSource() == m2.moleButton && m2.eating == false) {
					m2.moleButton.setIcon(m2.getMole()[13]);
				}
			});

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (m2.moleButton.getIcon().equals(m2.getMole()[13]))
							m2.moleButton.setIcon(m2.getMole()[12]);
						if (m2.getDirection() == 1)
							m2.setDirection(3);
						else if (m2.getDirection() == 2)
							m2.setDirection(4);
					}
				}
			});

			m3 = new MoleThread(ctx, name, 150, 400, this);
			m3.moleButton.addActionListener(e -> {
				if (e.getSource() == m3.moleButton && m3.eating == false) {
					m3.moleButton.setIcon(m3.getMole()[13]);
				}
			});

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (m3.moleButton.getIcon().equals(m3.getMole()[13]))
							m3.moleButton.setIcon(m3.getMole()[12]);
						if (m3.getDirection() == 1)
							m3.setDirection(3);
						else if (m3.getDirection() == 2)
							m3.setDirection(4);
					}
				}
			});

			m4 = new MoleThread(ctx, name, 50, 450, this);
			m4.moleButton.addActionListener(e -> {
				if (e.getSource() == m4.moleButton && m4.eating == false) {
					m4.moleButton.setIcon(m4.getMole()[13]);
				}
			});
			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (m4.moleButton.getIcon().equals(m4.getMole()[13]))
							m4.moleButton.setIcon(m4.getMole()[12]);
						if (m4.getDirection() == 1)
							m4.setDirection(3);
						else if (m4.getDirection() == 2)
							m4.setDirection(4);
					}
				}
			});

			m5 = new MoleThread(ctx, name, 100, 450, this);
			m5.moleButton.addActionListener(e -> {
				if (e.getSource() == m5.moleButton && m5.eating == false) {
					m5.moleButton.setIcon(m5.getMole()[13]);
				}
			});

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (m5.moleButton.getIcon().equals(m5.getMole()[13]))
							m5.moleButton.setIcon(m5.getMole()[12]);
						if (m5.getDirection() == 1)
							m5.setDirection(3);
						else if (m5.getDirection() == 2)
							m5.setDirection(4);
					}
				}
			});

			m6 = new MoleThread(ctx, name, 150, 450, this);
			m6.moleButton.addActionListener(e -> {
				if (e.getSource() == m6.moleButton && m6.eating == false) {
					m6.moleButton.setIcon(m6.getMole()[13]);
				}
			});

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					 if (e.getButton() == MouseEvent.BUTTON1) {
						if (m6.moleButton.getIcon().equals(m6.getMole()[13]))
							m6.moleButton.setIcon(m6.getMole()[12]);
						if (m6.getDirection() == 1)
							m6.setDirection(3);
						else if (m6.getDirection() == 2)
							m6.setDirection(4);
					}
				}
			});

			m7 = new MoleThread(ctx, name, 50, 500, this);
			m7.moleButton.addActionListener(e -> {
				if (e.getSource() == m7.moleButton && m7.eating == false) {
					m7.moleButton.setIcon(m7.getMole()[13]);
				}
			});

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (m7.moleButton.getIcon().equals(m7.getMole()[13]))
							m7.moleButton.setIcon(m7.getMole()[12]);
						if (m7.getDirection() == 1)
							m7.setDirection(3);
						else if (m7.getDirection() == 2)
							m7.setDirection(4);
					}
				}
			});

			m8 = new MoleThread(ctx, name, 100, 500, this);
			m8.moleButton.addActionListener(e -> {
				if (e.getSource() == m8.moleButton && m8.eating == false) {
					m8.moleButton.setIcon(m8.getMole()[13]);
				}
			});
			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					 if (e.getButton() == MouseEvent.BUTTON1) {
						if (m8.moleButton.getIcon().equals(m8.getMole()[13]))
							m8.moleButton.setIcon(m8.getMole()[12]);
						if (m8.getDirection() == 1)
							m8.setDirection(3);
						else if (m8.getDirection() == 2)
							m8.setDirection(4);
					}
				}
			});

			m9 = new MoleThread(ctx, name, 150, 500, this);
			m9.moleButton.addActionListener(e -> {
				if (e.getSource() == m9.moleButton && m9.eating == false) {
					m9.moleButton.setIcon(m9.getMole()[13]);
				}
			});

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (m9.moleButton.getIcon().equals(m9.getMole()[13]))
							m9.moleButton.setIcon(m9.getMole()[12]);
						if (m9.getDirection() == 1)
							m9.setDirection(3);
						else if (m9.getDirection() == 2)
							m9.setDirection(4);
					}
				}
			});
			 

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (m1.moleButton.getIcon().equals(m1.getMole()[13])
								|| m1.moleButton.getIcon().equals(m1.getMole()[11])
								|| m1.moleButton.getIcon().equals(m1.getMole()[10])
								|| m1.moleButton.getIcon().equals(m1.getMole()[9])
								|| m1.moleButton.getIcon().equals(m1.getMole()[5])
								|| m1.moleButton.getIcon().equals(m1.getMole()[4])
								|| m1.moleButton.getIcon().equals(m1.getMole()[3])) {
							if (m1.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 1 + ",");
								m1.getTimer().stop();
								m1.calculateChampionMovement(e.getX(), e.getY(), m1.getChampion());
								m1.setStartTime(System.currentTimeMillis());
								m1.getTimer().start();
							}
						}
						if (m2.moleButton.getIcon().equals(m2.getMole()[13])
								|| m2.moleButton.getIcon().equals(m2.getMole()[11])
								|| m2.moleButton.getIcon().equals(m2.getMole()[10])
								|| m2.moleButton.getIcon().equals(m2.getMole()[9])
								|| m2.moleButton.getIcon().equals(m2.getMole()[5])
								|| m2.moleButton.getIcon().equals(m2.getMole()[4])
								|| m2.moleButton.getIcon().equals(m2.getMole()[3])) {
							if (m2.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 2 + ",");
								m2.getTimer().stop();
								m2.calculateChampionMovement(e.getX(), e.getY(), m2.getChampion());
								m2.setStartTime(System.currentTimeMillis());
								m2.getTimer().start();
							}
						}
						if (m3.moleButton.getIcon().equals(m3.getMole()[13])
								|| m3.moleButton.getIcon().equals(m3.getMole()[11])
								|| m3.moleButton.getIcon().equals(m3.getMole()[10])
								|| m3.moleButton.getIcon().equals(m3.getMole()[9])
								|| m3.moleButton.getIcon().equals(m3.getMole()[5])
								|| m3.moleButton.getIcon().equals(m3.getMole()[4])
								|| m3.moleButton.getIcon().equals(m3.getMole()[3])) {
							if (m3.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 3 + ",");
								m3.getTimer().stop();
								m3.calculateChampionMovement(e.getX(), e.getY(), m3.getChampion());
								m3.setStartTime(System.currentTimeMillis());
								m3.getTimer().start();
							}
						}
						if (m4.moleButton.getIcon().equals(m4.getMole()[13])
								|| m4.moleButton.getIcon().equals(m4.getMole()[11])
								|| m4.moleButton.getIcon().equals(m4.getMole()[10])
								|| m4.moleButton.getIcon().equals(m4.getMole()[9])
								|| m4.moleButton.getIcon().equals(m4.getMole()[5])
								|| m4.moleButton.getIcon().equals(m4.getMole()[4])
								|| m4.moleButton.getIcon().equals(m4.getMole()[3])) {
							if (m4.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 4 + ",");
								m4.getTimer().stop();
								m4.calculateChampionMovement(e.getX(), e.getY(), m4.getChampion());
								m4.setStartTime(System.currentTimeMillis());
								m4.getTimer().start();
							}
						}

						if (m5.moleButton.getIcon().equals(m5.getMole()[13])
								|| m5.moleButton.getIcon().equals(m5.getMole()[11])
								|| m5.moleButton.getIcon().equals(m5.getMole()[10])
								|| m5.moleButton.getIcon().equals(m5.getMole()[9])
								|| m5.moleButton.getIcon().equals(m5.getMole()[5])
								|| m5.moleButton.getIcon().equals(m5.getMole()[4])
								|| m5.moleButton.getIcon().equals(m5.getMole()[3])) {
							if (m5.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 5 + ",");
								m5.getTimer().stop();
								m5.calculateChampionMovement(e.getX(), e.getY(), m5.getChampion());
								m5.setStartTime(System.currentTimeMillis());
								m5.getTimer().start();
							}
						}

						if (m6.moleButton.getIcon().equals(m6.getMole()[13])
								|| m6.moleButton.getIcon().equals(m6.getMole()[11])
								|| m6.moleButton.getIcon().equals(m6.getMole()[10])
								|| m6.moleButton.getIcon().equals(m6.getMole()[9])
								|| m6.moleButton.getIcon().equals(m6.getMole()[5])
								|| m6.moleButton.getIcon().equals(m6.getMole()[4])
								|| m6.moleButton.getIcon().equals(m6.getMole()[3])) {
							if (m6.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 6 + ",");
								m6.getTimer().stop();
								m6.calculateChampionMovement(e.getX(), e.getY(), m6.getChampion());
								m6.setStartTime(System.currentTimeMillis());
								m6.getTimer().start();
							}
						}

						if (m7.moleButton.getIcon().equals(m7.getMole()[13])
								|| m7.moleButton.getIcon().equals(m7.getMole()[11])
								|| m7.moleButton.getIcon().equals(m7.getMole()[10])
								|| m7.moleButton.getIcon().equals(m7.getMole()[9])
								|| m7.moleButton.getIcon().equals(m7.getMole()[5])
								|| m7.moleButton.getIcon().equals(m7.getMole()[4])
								|| m7.moleButton.getIcon().equals(m7.getMole()[3])) {
							if (m7.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 7 + ",");
								m7.getTimer().stop();
								m7.calculateChampionMovement(e.getX(), e.getY(), m7.getChampion());
								m7.setStartTime(System.currentTimeMillis());
								m7.getTimer().start();
							}
						}

						if (m8.moleButton.getIcon().equals(m8.getMole()[13])
								|| m8.moleButton.getIcon().equals(m8.getMole()[11])
								|| m8.moleButton.getIcon().equals(m8.getMole()[10])
								|| m8.moleButton.getIcon().equals(m8.getMole()[9])
								|| m8.moleButton.getIcon().equals(m8.getMole()[5])
								|| m8.moleButton.getIcon().equals(m8.getMole()[4])
								|| m8.moleButton.getIcon().equals(m8.getMole()[3])) {
							if (m8.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 8 + ",");
								m8.getTimer().stop();
								m8.calculateChampionMovement(e.getX(), e.getY(), m8.getChampion());
								m8.setStartTime(System.currentTimeMillis());
								m8.getTimer().start();
							}
						}

						if (m9.moleButton.getIcon().equals(m9.getMole()[13])
								|| m9.moleButton.getIcon().equals(m9.getMole()[11])
								|| m9.moleButton.getIcon().equals(m9.getMole()[10])
								|| m9.moleButton.getIcon().equals(m9.getMole()[9])
								|| m9.moleButton.getIcon().equals(m9.getMole()[5])
								|| m9.moleButton.getIcon().equals(m9.getMole()[4])
								|| m9.moleButton.getIcon().equals(m9.getMole()[3])) {
							if (m9.m) {
								ctx.write("[MOLEMOVE]," + name + "," + e.getX() + "," + e.getY() + "," + 9 + ",");
								m9.getTimer().stop();
								m9.calculateChampionMovement(e.getX(), e.getY(), m9.getChampion());
								m9.setStartTime(System.currentTimeMillis());
								m9.getTimer().start();
							}
						}
						ctx.flush();
					}
				}
			});
			
			music = new JButton(music_img);
            music.setBorderPainted(false);
            music.setFocusPainted(false);
            music.setContentAreaFilled(false);
            music.setBounds(580, 0, 50, 50);
            music.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {

                    try {
                        if (musicStatus == true) {
                            try {
                                soundToPlay.pause();
                            } catch (InterruptedException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            musicStatus = false;
                        } else {
                            soundToPlay.play();
                            musicStatus = true;
                        }
                    } catch (JavaLayerException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            add(music);

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

			moleInHumanPerformance = new MoleInHumanPerformance(200, 225, this);
			add(moleInHumanPerformance);
			// add(new MoleInHumanPerformance(200, 225));
			// i2 = new itemBoxThread(2);
			// i3 = new itemBoxThread(3);

			// add(v0);
			// add(v1);
			// add(v2);

			counterLabel = new JLabel("");
			counterLabel.setBounds(345, 0, 100, 50);
			counterLabel.setHorizontalAlignment(JLabel.CENTER);
			counterLabel.setFont(font1);

			add(counterLabel);

			counterLabel.setText("03:00");

			second = 0;
			minute = 3;

		//	normalTimer();
		//	timer.start();

			vegcountLabel = new JLabel(vegcount + "");
			vegcountLabel.setBounds(758, 90, 20, 20);
			vegcountLabel.setHorizontalAlignment(JLabel.CENTER);
			vegcountLabel.setFont(font2);

			add(vegcountLabel);

			moleCountLabel = new JLabel(moleCount + "");
			moleCountLabel.setBounds(758, 70, 20, 20);
			moleCountLabel.setHorizontalAlignment(JLabel.CENTER);
			moleCountLabel.setFont(font2);

			add(moleCountLabel);

			lifeLabel = new JLabel("2");
			lifeLabel.setBounds(21, 70, 20, 20);
			lifeLabel.setHorizontalAlignment(JLabel.CENTER);
			lifeLabel.setFont(font2);
			
			add(lifeLabel);
			
			itembox1 = new JLabel();
			itembox2 = new JLabel();
			itembox1.setBounds(655, 6, 36, 36);
			itembox2.setBounds(696, 6, 36, 36);
			itembox1.setVisible(false);
			add(itembox1);
			add(itembox2);

			sx = sy = ex = ey = 0;
			Drager drager = new Drager();
			addMouseListener(drager);
			addMouseMotionListener(drager);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public JLabel getLifeLabel() {
		return lifeLabel;
	}
	public void setLifeLabel(JLabel lifeLabel) {
		this.lifeLabel = lifeLabel;
	}
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
	public JLabel getCounterLabel() {
		return counterLabel;
	}

	public void setCounterLabel(JLabel counterLabel) {
		this.counterLabel = counterLabel;
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

	public DecimalFormat getdFormat() {
		return dFormat;
	}

	public void setdFormat(DecimalFormat dFormat) {
		this.dFormat = dFormat;
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

	public MoleThread getM1() {
		return m1;
	}

	public MoleThread getM2() {
		return m2;
	}

	public MoleThread getM3() {
		return m3;
	}

	public MoleThread getM4() {
		return m4;
	}

	public MoleThread getM5() {
		return m5;
	}

	public MoleThread getM6() {
		return m6;
	}

	public MoleThread getM7() {
		return m7;
	}

	public MoleThread getM8() {
		return m8;
	}

	public MoleThread getM9() {
		return m9;
	}

	public MoleInHumanPerformance getMoleInHumanPerformance() {
		return moleInHumanPerformance;
	}

	public boolean getIsSnake() {
		return isSnake;
	}

	public void setIsSnake(boolean isSnake) {
		this.isSnake = isSnake;
	}
	public Snake getSnake() {
		return snake;
	}

	public JLabel getMoleCountLabel() {
		return moleCountLabel;
	}

	public int getMoleCount() {
		return moleCount;
	}

	public void setMoleCount(int moleCount) {
		this.moleCount = moleCount;
	}

	public boolean getMoleKill() {
		return moleKill;
	}

	public void setMoleKill(boolean moleKill) {
		this.moleKill = moleKill;
	}

	public void makeSnake(int status) {
		snake = new Snake(this, status);
		add(snake);
		isSnake = true;
	}

	/*public void normalTimer() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				second--;

				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);

				counterLabel.setText(ddMinute + ":" + ddSecond);
				// vegcountLabel.setText(15 - (v0.getvegcount() + v1.getvegcount() +
				// v2.getvegcount()) + "");

				if (second == -1) {
					second = 59;
					minute--;

					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					counterLabel.setText(ddMinute + ":" + ddSecond);
				}
				if (counterLabel.getText().equals("00:00")) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "인간 승리!!", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[MOLELOSE]," + LoginForm.getId() + ",");
					setVisible(false);
					if (MoleClientMainHandler.inRoom.testStart.getText().equals("준비취소"))
						MoleClientMainHandler.inRoom.testStart.setText("준비");
					MoleClientMainHandler.roomTest.ready.setText("");
					MoleClientMainHandler.roomTest.setVisible(true);
					if (musicStatus == true)
						try {
							soundToPlay.pause();
							MoleClientMainHandler.homePanel.getSoundToPlay().play();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				} else if (vegcount == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "두더지는 자유다 두더지 만만세", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[MOLEWIN]," + LoginForm.getId() + ",");
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
				} else if (moleCount == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "두더지 패배", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[MOLELOSE]," + LoginForm.getId() + ",");
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
				} else if (moleInHumanPerformance.getHumanlife() == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "두더지 승리", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[MOLEWIN]," + LoginForm.getId() + ",");
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

	class Drager extends MouseAdapter {
		private int status;

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				setStartPoint(e.getX(), e.getY());
				status = 1;
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (status == 1) {
				setEndPoint(e.getX(), e.getY());
				repaint();
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (getRectD().contains(m1.champion))
				m1.moleButton.setIcon(m1.mole[13]);
			if (getRectD().contains(m2.champion))
				m2.moleButton.setIcon(m2.mole[13]);
			if (getRectD().contains(m3.champion))
				m3.moleButton.setIcon(m3.mole[13]);
			if (getRectD().contains(m4.champion))
				m4.moleButton.setIcon(m4.mole[13]);
			if (getRectD().contains(m5.champion))
				m5.moleButton.setIcon(m5.mole[13]);
			if (getRectD().contains(m6.champion))
				m6.moleButton.setIcon(m6.mole[13]);
			if (getRectD().contains(m7.champion))
				m7.moleButton.setIcon(m7.mole[13]);
			if (getRectD().contains(m8.champion))
				m8.moleButton.setIcon(m8.mole[13]);
			if (getRectD().contains(m9.champion))
				m9.moleButton.setIcon(m9.mole[13]);
			status = 0;
			setStartPoint(e.getX(), e.getY());
			setEndPoint(e.getX(), e.getY());
			repaint();
		}
	}

	public void setStartPoint(int sx, int sy) {
		this.sx = sx;
		this.sy = sy;
	}

	public void setEndPoint(int ex, int ey) {
		this.ex = ex;
		this.ey = ey;
	}

	public void drawPerfectRect(Graphics g, int sx, int sy, int ex, int ey) {
		int px = Math.min(sx, ex);
		int py = Math.min(sy, ey);
		int pw = Math.abs(sx - ex);
		int ph = Math.abs(sy - ey);
		g.drawRect(px, py, pw, ph);
	}

	public Rectangle getRectD() {
		int px = Math.min(sx, ex);
		int py = Math.min(sy, ey);
		int pw = Math.abs(sx - ex);
		int ph = Math.abs(sy - ey);

		return new Rectangle(px, py, pw, ph);
	}

	public class MoleThread extends Thread {
		private int x, y;
		private JButton moleButton;
		private Rectangle champion;
		private boolean m = true;
		private boolean life = true;

		private Timer deadTime;
		private int deadSec;

		public Rectangle getChampion() {
			return champion;
		}

		private int molesecond = 0;
		private int direction = 0;

		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		private ImageIcon mole[] = { new ImageIcon("img/moleResource/moleL1.png"),
				new ImageIcon("img/moleResource/moleL2.png"), new ImageIcon("img/moleResource/moleL3.png"),
				new ImageIcon("img/moleResource/moleLS1.png"), new ImageIcon("img/moleResource/moleLS2.png"),
				new ImageIcon("img/moleResource/moleLS3.png"), new ImageIcon("img/moleResource/moleR1.png"),
				new ImageIcon("img/moleResource/moleR2.png"), new ImageIcon("img/moleResource/moleR3.png"),
				new ImageIcon("img/moleResource/moleRS1.png"), new ImageIcon("img/moleResource/moleRS2.png"),
				new ImageIcon("img/moleResource/moleRS3.png"), new ImageIcon("img/moleResource/moleS.png"),
				new ImageIcon("img/moleResource/moleSS.png") };
		private ImageIcon moleD = new ImageIcon("img/moleD.png");

		private Timer timer;
		private double speed = 0.15;
		private Long startTime;

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public ImageIcon[] getMole() {
			return mole;
		}

		public Timer getTimer() {
			return timer;
		}

		public Long getStartTime() {
			return startTime;
		}

		private Timer eattimer;
		private int eatsecond;
		private boolean eating = false;

		private double targetX, targetY;
		private double startX, startY;
		private double runTime;

		private boolean enhenceteeth = false;
		private ChannelHandlerContext ctx;
		private String name;
		private MoleUI panel;

		public int getx() {
			return x;
		}

		public int gety() {
			return y;
		}

		public JButton getMoleButton() {
			return moleButton;
		}

		public boolean getLife() {
			return life;
		}

		public MoleThread(ChannelHandlerContext ctx, String name, int x, int y, MoleUI panel) {
			this.panel = panel;
			this.ctx = ctx;
			this.x = x;
			this.y = y;
			this.name = name;

			champion = new Rectangle(x, y, 32, 32);
			moleButton = new JButton(mole[12]);
			moleButton.setBorderPainted(false);
			moleButton.setFocusPainted(false);
			moleButton.setContentAreaFilled(false);
			moleButton.setBounds(x, y, 32, 32);
			add(moleButton);

			timer = new Timer(30, e -> {
				if (!moleButton.getIcon().equals(moleD)) {
					if (eating == false) {

						molesecond++;
						molesecond = molesecond % 4;

						if (direction == 3) {
							if (molesecond == 1)
								moleButton.setIcon(mole[6]);
							else if (molesecond == 2)
								moleButton.setIcon(mole[7]);
							else if (molesecond == 3)
								moleButton.setIcon(mole[8]);
						}
						if (direction == 4) {
							if (molesecond == 1)
								moleButton.setIcon(mole[0]);
							else if (molesecond == 2)
								moleButton.setIcon(mole[1]);
							else if (molesecond == 3)
								moleButton.setIcon(mole[2]);
						}

						if (direction == 1) { // 오른쪽방향으로 움직일때 -누름
							if (molesecond == 1)
								moleButton.setIcon(mole[9]);
							else if (molesecond == 2)
								moleButton.setIcon(mole[10]);
							else if (molesecond == 3)
								moleButton.setIcon(mole[11]);
						}
						if (direction == 2) { // 왼쪽방향으로 움직일때 -누름
							if (molesecond == 1)
								moleButton.setIcon(mole[3]);
							else if (molesecond == 2)
								moleButton.setIcon(mole[4]);
							else if (molesecond == 3)
								moleButton.setIcon(mole[5]);
						}
						try {
							TimeMove();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else
						timer.stop();
					/*
					 * if (getBounds().intersects(human.getBounds()))
					 * this.moleButton.setVisible(false);
					 */
				}
			});
		}

		public void moleDie() {
			timer.stop();
			moleDeadTimer();
			deadTime.start();
			moleButton.setIcon(moleD);
			m = false;
			moleCount = moleCount - 1;
            moleCountLabel.setText(moleCount + "");
		}

		public void moleDeadTimer() {
			deadTime = new Timer(500, e -> {
				deadSec++;
				if (deadSec == 1) {
					deadTime.stop();
					moleButton.setVisible(false);
					moleButton.setBounds(0, 0, 0, 0);
					System.out.println("zz");
					champion.setBounds(0, 0, 0, 0);
					life = false;
				}
			});
		}

	/*	public void snakeTimer() {
			snakeTimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					snakesecond--;
					System.out.println(snakesecond);
					if (itembox1.getIcon() == itemsnakepipe) {
						itembox1.setFont(font1);
						itembox1.setText(snakesecond + "");
						itembox1.setVerticalTextPosition(JLabel.CENTER);
						itembox1.setHorizontalTextPosition(JLabel.CENTER);
						itembox1.setForeground(Color.cyan);
					} else {
						itembox2.setText(snakesecond + "");
						itembox2.setFont(font1);
						itembox2.setVerticalTextPosition(JLabel.CENTER);
						itembox2.setHorizontalTextPosition(JLabel.CENTER);
						itembox2.setForeground(Color.cyan);
					}
					if (snakesecond == 0) {
						if (itembox1.getIcon() == itemsnakepipe) {
							itembox1.setIcon(null);
							itembox1.setText(null);
							itembox1.setVisible(false);
						} else {
							itembox2.setIcon(null);
							itembox2.setText(null);
						}
						snakesecond = 15;
						itembox1.setVisible(false);
						snakeTimer.stop();
					}
				}
			});
		}*/

		private Timer humanTrapTimer;
		private int trapCount = 3;

		public void humanTrapTimer() {
			humanTrapTimer = new Timer(1000, e -> {
				trapCount--;
				if (itembox1.getIcon() == itemtrap) {
					itembox1.setFont(font1);
					itembox1.setText(trapCount + "");
					itembox1.setVerticalTextPosition(JLabel.CENTER);
					itembox1.setHorizontalTextPosition(JLabel.CENTER);
					itembox1.setForeground(Color.cyan);
				} else {
					itembox2.setText(trapCount + "");
					itembox2.setFont(font1);
					itembox2.setVerticalTextPosition(JLabel.CENTER);
					itembox2.setHorizontalTextPosition(JLabel.CENTER);
					itembox2.setForeground(Color.cyan);
				}
				if (trapCount == 0) {
					if (itembox1.getIcon() == itemsnakepipe) {
						itembox1.setIcon(null);
						itembox1.setText(null);
						itembox1.setVisible(false);
					} else {
						itembox2.setIcon(null);
						itembox2.setText(null);
					}
					ctx.writeAndFlush("[MOLETRAPSTOP]," + name + ",");
					trapCount = 3;
					itembox1.setVisible(false);
					humanTrapTimer.stop();
				}
			});
		}

		public void molegetitem() {
			int itemnum = ((int) (Math.random() * 10));
			switch (itemnum) {
			case 0:
			case 9:
			case 3:
			case 6:
				System.out.println(itemnum);
				System.out.println("뱀피리 획득");
				System.out.println(itembox1.isVisible());
				if (isSnake == false)
					ctx.writeAndFlush("[SNAKE]," + name + ",");
				break;			
			case 1:
			case 5:
			case 8:
				System.out.println(itemnum);
				System.out.println("사람 정지");
				System.out.println(itembox1.isVisible());
				if (itembox1.isVisible() == false) {
					itembox1.setVisible(true);
					itembox1.setIcon(itemtrap);
					ctx.writeAndFlush("[MOLETRAP]," + name + ",");
					humanTrapTimer();
					humanTrapTimer.start();
				} else { 
					itembox2.setIcon(itemtrap);
					System.out.println("2");
					ctx.writeAndFlush("[MOLETRAP]," + name + ",");
					humanTrapTimer();
					humanTrapTimer.start();
				}
				break;
			default:
				enhenceteeth = true;
				System.out.println("강철이빨 획득");
				System.out.println(itembox1.isVisible());
				if (itembox1.isVisible() == false) {
					itembox1.setVisible(true);
					itembox1.setIcon(itemteeth);
				} else {
					itembox2.setIcon(itemteeth);
					System.out.println("2");
				}
				break;
			}
			//eating = false;
		}

		public void v1EatTimer() {
			eating = true;
			eattimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eatsecond++;
					if (eatsecond == 1) {
						eattimer.stop();
						eating = false;
						ctx.writeAndFlush("[v1EAT]," + name + ",");
					//	v1.setBounds(0, 0, 0, 0);
					//	v1.setVisible(false);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
						moleButton.setIcon(mole[13]);
					}
				}
			});
		}

		public void v2EatTimer() {
			eating = true;
			eattimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eatsecond++;
					if (eatsecond == 1) {
						eattimer.stop();
						eating = false;
						ctx.writeAndFlush("[v2EAT]," + name + ",");
						//v2.setBounds(0, 0, 0, 0);
					//	v2.setVisible(false);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
						moleButton.setIcon(mole[13]);
					}
				}
			});
		}

		public void v3EatTimer() {
			eating = true;
			eattimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eatsecond++;
					if (eatsecond == 1) {
						eattimer.stop();
						eating = false;
						ctx.writeAndFlush("[v3EAT]," + name + ",");
					//	v3.setBounds(0, 0, 0, 0);
						//v3.setVisible(false);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
						moleButton.setIcon(mole[13]);
					}
				}
			});
		}

		public void run() {
		}

		public void TimeMove() throws InterruptedException {
			long duration = System.currentTimeMillis() - startTime;
			double progress = duration / runTime;

			if (progress >= 1.0) {
				progress = 1.0;
				timer.stop();
				if (moleButton.getIcon().equals(mole[11]) || moleButton.getIcon().equals(mole[10])
						|| moleButton.getIcon().equals(mole[9]) || moleButton.getIcon().equals(mole[5])
						|| moleButton.getIcon().equals(mole[4]) || moleButton.getIcon().equals(mole[3]))
					moleButton.setIcon(mole[13]);
				else
					moleButton.setIcon(mole[12]);
			}

			double x = (int) (startX + ((targetX - startX) * progress));
			double y = (int) (startY + ((targetY - startY) * progress));

			repaint();
			if (y >= 270 && x >= 12 && x <= 800) {
				moleButton.setBounds((int) x - 15, (int) y - 15, 30, 30);
				champion.setRect(x - 5, y - 5, 10, 10);
			}
			if (m1.moleButton.getY() < 300 && moleKill == false && m1.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 1 + ",");
				moleKill = true;
			}else if (m2.moleButton.getY() < 300 && moleKill == false && m2.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 2 + ",");
				moleKill = true;
			}else if (m3.moleButton.getY() < 300 && moleKill == false && m3.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 3 + ",");
				moleKill = true;
			}else if (m4.moleButton.getY() < 300 && moleKill == false && m4.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 4 + ",");
				moleKill = true;
			}else if (m5.moleButton.getY() < 300 && moleKill == false && m5.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 5 + ",");
				moleKill = true;
			}else if (m6.moleButton.getY() < 300 && moleKill == false && m6.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 6 + ",");
				moleKill = true;
			}else if (m7.moleButton.getY() < 300 && moleKill == false && m7.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 7 + ",");
				moleKill = true;
			}else if (m8.moleButton.getY() < 300 && moleKill == false && m8.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 8 + ",");
				moleKill = true;
			}else if (m9.moleButton.getY() < 300 && moleKill == false && m9.moleButton.isVisible()) {
				System.out.println("받음");
				ctx.writeAndFlush("[MOLEDIE]," + name + "," + 9 + ",");
				moleKill = true;
			}

			if (i1.getBounds().intersects(champion) && i1.getTimerstop() == false && eating == false) {
				//eating = true;
				ctx.writeAndFlush("[MOLEITEM1EAT]," + name + ",");
				/*i1.setBounds(0, 0, 0, 0);
				i1.setVisible(false);*/
				molegetitem();

			} else if (i2.getBounds().intersects(champion) && i2.getTimerstop() == false && eating == false) {
				//eating = true;
				ctx.writeAndFlush("[MOLEITEM2EAT]," + name + ",");
				/*i2.setBounds(0, 0, 0, 0);
				i2.setVisible(false);*/
				molegetitem();
			}

			if (v1.getBounds().intersects(champion) && v1.timerstop == false && eating == false) {
				eatsecond = 0;
				if (enhenceteeth == false) {
					v1EatTimer();
					eattimer.start();
					moleButton.setIcon(mole[12]);
				} else {
					enhenceteeth = false;
					if (itembox1.getIcon() == itemteeth && itembox2.getIcon() == itemteeth) {
						itembox1.setIcon(null);
						enhenceteeth = true;
						ctx.writeAndFlush("[v1EAT]," + name + ",");
						//v1.setVisible(false);
						//v1.setBounds(0, 0, 0, 0);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
					} else if (itembox1.getIcon() == itemteeth) {
						itembox1.setIcon(null);
						ctx.writeAndFlush("[v1EAT]," + name + ",");
					//	v1.setVisible(false);
					//	v1.setBounds(0, 0, 0, 0);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
					} else {
						itembox2.setIcon(null);
						ctx.writeAndFlush("[v1EAT]," + name + ",");
					//	v1.setVisible(false);
					//	v1.setBounds(0, 0, 0, 0);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
					}
				}

				v1.plusvegcount();
			} else if (v2.getBounds().intersects(champion) && v2.timerstop == false && eating == false) {
				/*
				 * v1.setVisible(false); v1.setsecond(0); v1.vegtimer(); v1.vegtimer.start();
				 */
				eatsecond = 0;
				if (enhenceteeth == false) {
					v2EatTimer();
					eattimer.start();
					moleButton.setIcon(mole[12]);
				} else {
					enhenceteeth = false;
					if (itembox1.getIcon() == itemteeth && itembox2.getIcon() == itemteeth) {
						itembox1.setIcon(null);
						enhenceteeth = true;
						ctx.writeAndFlush("[v2EAT]," + name + ",");
					//	v2.setVisible(false);
					//	v2.setBounds(0, 0, 0, 0);
					//	vegcount--;
						//vegcountLabel.setText(vegcount + "");
					} else if (itembox1.getIcon() == itemteeth) {
						itembox1.setIcon(null);
						ctx.writeAndFlush("[v2EAT]," + name + ",");
					//	v2.setVisible(false);
					//	v2.setBounds(0, 0, 0, 0);
					//	vegcount--;
						//vegcountLabel.setText(vegcount + "");
					} else {
						itembox2.setIcon(null);
						ctx.writeAndFlush("[v2EAT]," + name + ",");
					//	v2.setVisible(false);
					//	v2.setBounds(0, 0, 0, 0);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
					}
				}
				v2.plusvegcount();

			} else if (v3.getBounds().intersects(champion) && v3.timerstop == false && eating == false) {
				/*
				 * v2.setVisible(false); v2.setsecond(0); v2.vegtimer(); v2.vegtimer.start();
				 */
				eatsecond = 0;
				if (enhenceteeth == false) {
					v3EatTimer();
					eattimer.start();
					moleButton.setIcon(mole[12]);
				} else {
					enhenceteeth = false;
					if (itembox1.getIcon() == itemteeth && itembox2.getIcon() == itemteeth) {
						itembox1.setIcon(null);
						enhenceteeth = true;
						ctx.writeAndFlush("[v3EAT]," + name + ",");
					//	v3.setVisible(false);
						//v3.setBounds(0, 0, 0, 0);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
					} else if (itembox1.getIcon() == itemteeth) {
						itembox1.setIcon(null);
						ctx.writeAndFlush("[v3EAT]," + name + ",");
					//	v3.setVisible(false);
					//	v3.setBounds(0, 0, 0, 0);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
					} else {
						itembox2.setIcon(null);
						ctx.writeAndFlush("[v3EAT]," + name + ",");
					//	v3.setVisible(false);
					//	v3.setBounds(0, 0, 0, 0);
						//vegcount--;
						//vegcountLabel.setText(vegcount + "");
					}
				}
				v3.plusvegcount();
			}
		}

		public boolean getEating() {
			return eating;
		}

		public void setEating(boolean eating) {
			this.eating = eating;
		}

		public void calculateChampionMovement(double x, double y, Rectangle champion) {

			if (x != champion.getCenterX() || y != champion.getCenterY()) {

				targetX = x;
				targetY = y;

				System.out.println(targetX + "     " + targetY);
				startX = champion.getCenterX();
				startY = champion.getCenterY();

				if (targetX - startX > 0) {
					direction = 1;
				} else if (targetX - startX < 0) {
					direction = 2;
				}

				double distance = Math
						.sqrt((startX - targetX) * (startX - targetX) + (startY - targetY) * (startY - targetY));

				runTime = distance / (double) speed;
//				if ((x <= v0.getx() + 10 && x > v0.getx() - 10) || (x <= v1.getx() + 10 && x > v1.getx() - 10)
//						|| (x <= v2.getx() + 10 && x > v2.getx() - 10) && y < 290) {
//					eat = true;
//				}
			}
		}
	}

	public JLabel getVegcountLabel() {
		return vegcountLabel;
	}
	public void setVegcountLabel(JLabel vegcountLabel) {
		this.vegcountLabel = vegcountLabel;
	}
	public int getVegcount() {
		return vegcount;
	}
	public void setVegcount(int vegcount) {
		this.vegcount = vegcount;
	}
	public void paintComponent(Graphics g) {// 그리는 함수
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, null);
		g.drawImage(humanHud, 0, 70, null);
		g.drawImage(moleHud, 715, 70, null);
		// g.drawImage(humanInv, 55, 0, null);
		g.drawImage(moleInv, 650, 0, null);
		g.drawImage(intHuman, 0, 0, null);
		g.drawImage(intMole, 740, 0, null);

		drawPerfectRect(g, sx, sy, ex, ey);
	}
}
