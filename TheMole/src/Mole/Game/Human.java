package Mole.Game;


import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import io.netty.channel.ChannelHandlerContext;

public class Human extends JLabel{
	
	private int x;
	private int y;
	private boolean timerstop = false;
	private int status = 0; // 1은 오른쪽, 2는 왼쪽,
	public int humsecond = 0;
	public int shosecond = 15;
	private Font font1 = new Font("Arial", Font.BOLD, 30);
	private boolean shooting = false;
			
	private Timer moleTrapTimer;
	private int moleTrapCount;
	private Timer mover;
	private Timer humtraptimer;
	private int humtrapsecond = 10;
	private Timer shoesTimer;
	private boolean moving = false;
	private HumanUI humanUi;
	
	private JLabel itembox1;
	private JLabel itembox2;
	private int humanspeed = 5;
	
	private ImageIcon shoes = new ImageIcon("img/shoes.png");
	private ImageIcon bullets = new ImageIcon("img/bullet.png");
	private ImageIcon trapM = new ImageIcon("img/trapH.png");
	private ChannelHandlerContext ctx;
	private String name;
	private boolean moleKill = true;
	private boolean humanItem = true;
	
	public boolean getHumanItem() {
		return humanItem;
	}
	public void setHumanItem(boolean humanItem) {
		this.humanItem = humanItem;
	}
	private int humanLife = 2;

	private int bulletCount = 5;
	
	private Bullet b;

	private ImageIcon human[] = {  new ImageIcon("img/humanResource/human1.png"),
			new ImageIcon("img/humanResource/human2.png"), new ImageIcon("img/humanResource/human3.png"),
			new ImageIcon("img/humanResource/human4.png"), new ImageIcon("img/humanResource/human5.png"),
			new ImageIcon("img/humanResource/human6.png"), new ImageIcon("img/humanResource/human7.png"),
			new ImageIcon("img/humanResource/human8.png"), new ImageIcon("img/humanResource/human9.png"),
			new ImageIcon("img/humanResource/human10.png") };

	public Human(HumanUI pan,int x, int y, ChannelHandlerContext ctx, String name) {
		this.ctx = ctx;
		this.x = x;
		this.y = y;
		this.humanUi = pan;
		this.name = name;
		
		setBounds(x, y, 50, 64);
		setIcon(human[0]);
		pan.setFocusable(true);
		
		itembox1 = new JLabel();
		itembox2 = new JLabel();
		itembox1.setBounds(62, 7, 36, 36);
		itembox2.setBounds(104, 7, 36, 36);

		pan.add(itembox1);
		pan.add(itembox2);
		itembox1.setVisible(false);
		
		pan.addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) { // 왼쪽 방향키
					ctx.writeAndFlush("[LEFT]," + name + ",");
					/*moving = true;
					status = 2;
					setX(-humanspeed);
					if(timerstop==false) {
						humsecond = 0;
						timerstop=true;
						timerstart();
					}
					setBounds(getX(),y,50,64);		*/
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // 오른쪽 방향키
					ctx.writeAndFlush("[RIGHT]," + name + ",");
					/*moving = true;
					status = 1;
					setX(humanspeed);
					if(timerstop==false) {
						humsecond = 0;
						timerstop=true;
						timerstart();
					}
					setBounds(getX(),y,50,64);*/
				}
				if (e.getKeyCode() == KeyEvent.VK_A && shooting == false  && pan.getHumStop() == false && bulletCount != 0 && status == 2) {
					ctx.writeAndFlush("[BULLET]," + name + "," + 2 + "," + status + ",");
					setIcon(human[6]);
					shooting = true;
					//Bullet a = new Bullet(getX(), 2, status, pan);
				//	minusBcount();
				}
				if (e.getKeyCode() == KeyEvent.VK_D && shooting == false && pan.getHumStop() == false && bulletCount != 0 && status == 1) {
					ctx.writeAndFlush("[BULLET]," + name + "," + 1 + "," + status + ",");
					setIcon(human[1]);
					shooting = true;
				//	Bullet b =new Bullet(getX(), 1, status, pan);
				//	minusBcount();
				}
				if (e.getKeyCode() == KeyEvent.VK_S && shooting == false  && pan.getHumStop() == false && bulletCount != 0) {
					ctx.writeAndFlush("[BULLET]," + name + "," + 3 + "," + status + ",");
					shooting = true;
				//	Bullet b =new Bullet(getX(), 3, status, pan);
				//	minusBcount();
				}
				
				if (pan.getI1().getX() > getX() - 10 && pan.getI1().getX() < getX() + 3 && pan.getI1().getTimerstop() == false && humanItem == true) {
					humanItem = false;
					ctx.writeAndFlush("[HUMANITEM1EAT]," + name + ",");
				//	pan.getI1().setTimerstop(true);
				//	pan.getI1().setVisible(false);
					humangetitem();
				}
				if (pan.getI2().getX() > getX() - 10 && pan.getI2().getX() < getX() + 10 && pan.getI2().getTimerstop() == false && humanItem == true) {
					humanItem = false;
					ctx.writeAndFlush("[HUMANITEM2EAT]," + name + ",");
				//	pan.getI2().setTimerstop(true);
				//	pan.getI2().setVisible(false);
					humangetitem();
				}
			}

			public void keyReleased(KeyEvent e) {
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) { // 왼쪽 방향키
					ctx.writeAndFlush("[LEFTSTOP]," + name + ",");
					//moving = false;
					//setIcon(human[5]);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // 오른쪽 방향키
					ctx.writeAndFlush("[RIGHTSTOP]," + name + ",");
					//moving = false;
					//setIcon(human[0]);
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					setIcon(human[5]);
					shooting = false;
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					setIcon(human[0]);
					shooting = false;
				}
				else if (e.getKeyCode() == KeyEvent.VK_S) {
					shooting = false;
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});
	}
	public void humanMove(String m) {
		if (m.equals("LEFT")) {
		moving = true;
		status = 2;
		setX(-humanspeed);
		if(timerstop==false) {
			humsecond = 0;
			timerstop=true;
			timerstart();
		}
		setBounds(getX(),y,50,64);	
		} else if (m.equals("RIGHT")) {
			moving = true;
			status = 1;
			setX(humanspeed);
			if(timerstop==false) {
				humsecond = 0;
				timerstop=true;
				timerstart();
			}
			setBounds(getX(),y,50,64);
		} else if (m.equals("LEFTSTOP")) {
			moving = false;
			setIcon(human[5]);
			
		} else if (m.equals("RIGHTSTOP")) {
			moving = false;
			setIcon(human[0]);
		}
	}
	public void timerstart() {
		mover();
		mover.start();
	}
	public void bullet(int x, int direction, int status) {
		b = new Bullet(x, direction, status, humanUi, ctx, name);
	}
	public Bullet getB() {
		return b;
	}
	public boolean getMoleKill() {
		return moleKill;
	}

	public void setMoleKill(boolean moleKill) {
		this.moleKill = moleKill;
	}
	public int getHumanLife() {
		return humanLife;
	}
	public void minushumanlife() {
		humanLife --;
		System.out.println("뱀닿음" );
	}
	public int getHumanspeed() {
		return humanspeed;
	}
	public void setHumanspeed(int humanspeed) {
		this.humanspeed = humanspeed;
	}

	public void shoesTimer() {
		shoesTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shosecond--;
				System.out.println(shosecond);

				if (itembox1.getIcon() == shoes) {
					//humanspeed = 7;
					itembox1.setFont(font1);
					itembox1.setText(shosecond + "");
					itembox1.setVerticalTextPosition(JLabel.CENTER);
					itembox1.setHorizontalTextPosition(JLabel.CENTER);
					itembox1.setForeground(Color.cyan);
				} else {
					//humanspeed = 7;
					itembox2.setText(shosecond + "");
					itembox2.setFont(font1);
					itembox2.setVerticalTextPosition(JLabel.CENTER);
					itembox2.setHorizontalTextPosition(JLabel.CENTER);
					itembox2.setForeground(Color.cyan);
				}
				if (shosecond == 0) {
					//humanspeed = 5;
					ctx.writeAndFlush("[HUMANSPEEDDOWN]," + name + ",");
					System.out.println("사람속도 하향");
					if (itembox1.getIcon() == shoes) {
						itembox1.setIcon(null);
						itembox1.setText(null);
						itembox1.setVisible(false);
					} else {
						itembox2.setIcon(null);
						itembox2.setText(null);
					}
					humanspeed = 5;
					shosecond = 15;
					itembox1.setVisible(false);
					shoesTimer.stop();
				}
			}
		});
	}
	public void humtraptimer() {
		humanUi.sethumtrap(true);
		humtraptimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				humtrapsecond--;
				System.out.println(humanUi.gethumtrap());
				if (itembox1.getIcon() == trapM) {
					itembox1.setText(humtrapsecond + "");
					itembox1.setFont(font1);
					itembox1.setVerticalTextPosition(JLabel.CENTER);
					itembox1.setHorizontalTextPosition(JLabel.CENTER);
					itembox1.setForeground(Color.cyan);
				}
				if (humanUi.gethumtrap() == false) {
					humtrapsecond = 0;
				}
				if (humtrapsecond == 0) {
					if (itembox1.getIcon() == trapM) {
						itembox1.setIcon(null);
						itembox1.setText(null);
						itembox1.setVisible(false);
					} else {
						itembox2.setIcon(null);
						itembox2.setText(null);
					}
					ctx.writeAndFlush("[HUMANTRAPSTOP]," + name + "," );
					humtrapsecond = 10;
					humanUi.sethumtrap(false);
					moleKill = true;
					humtraptimer.stop();
				}
			}
		});
	}
	
	public void humangetitem() {
		System.out.println("아이템 섭취");
		int itemnum = ((int)(Math.random()*10));
		switch (itemnum) {
		case 0:
		case 9:
			if (itembox1.isVisible() == false) {
				ctx.writeAndFlush("[HUMANTRAP]," + name + ",");
				itembox1.setVisible(true);
				itembox1.setIcon(trapM);
				humtraptimer();
				humtraptimer.start();
				moleKill = false;
			} else if (itembox1.getIcon() == trapM || itembox2.getIcon() == trapM) {
				ctx.writeAndFlush("[HUMANTRAP]," + name + ",");
				humtrapsecond += 10;
				moleKill = false;
			} else {
				ctx.writeAndFlush("[HUMANTRAP]," + name + ",");
				humtraptimer();
				humtraptimer.start();
				moleKill = false;
				itembox2.setIcon(trapM);
			}
			break;
		case 1:
		case 8:
			System.out.println("신발");
			if (itembox1.isVisible() == false) {
				itembox1.setVisible(true);
				itembox1.setIcon(shoes);
				shoesTimer();
				ctx.writeAndFlush("[HUMANSPEEDUP]," + name + ",");
				shoesTimer.start();
			} else if (itembox1.getIcon() == shoes || itembox2.getIcon() == shoes) {
				shosecond += 15;
			} else {
				shoesTimer();
				ctx.writeAndFlush("[HUMANSPEEDUP]," + name + ",");
				shoesTimer.start();
				itembox2.setIcon(shoes);
			}
			break;
		default:
			System.out.println("총알 획득");
			bulletCount += 2;
			humanUi.getBulletLabel().setText(bulletCount + "");
			break;
		}
	}



	public void mover() {
		mover = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				humsecond++;
				humsecond = humsecond % 5;
		//		System.out.println(humsecond);
		//		System.out.println(moving + "  " + status);
				if (moving == true && status == 1) { // 오른쪽방향으로 움직일때 -누름
				//	System.out.println("오른쪽이동중");
					if (humsecond == 1) 
						setIcon(human[1]);

					else if (humsecond == 2) 
						setIcon(human[2]);
					
					else if (humsecond == 3) 
						setIcon(human[3]);
					
					else if (humsecond == 4) 
						setIcon(human[4]);
					
				}
				if (moving == true && status == 2) { // 왼쪽방향으로 움직일때 -누름
					if (humsecond == 1) 
						setIcon(human[6]);
					
					else if (humsecond == 2) 
						setIcon(human[7]);
					
					else if (humsecond == 3) 
						setIcon(human[8]);
					
					else if (humsecond == 4) 
						setIcon(human[9]);
					
				}
				if (moving == false) {
					mover.stop();
					timerstop = false;
				}
				
				if(humanUi.getM1().getX() >= x - 5 && humanUi.getM1().getX() <= x + 5 && humanUi.getM1().getY()<= 275 && humanUi.getM1().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 1 + ",");			
					humanUi.getM1().setMoleKill(false);
				} 
				else if(humanUi.getM2().getX() >= getX() - 5 && humanUi.getM2().getX() <= getX() + 5&& humanUi.getM2().getY()<= 275 && humanUi.getM2().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 2 + ",");	
					humanUi.getM2().setMoleKill(false);
				} 
				else if(humanUi.getM3().getX() >= getX() - 5 && humanUi.getM3().getX() <= getX() + 5 && humanUi.getM3().getY()<= 275 && humanUi.getM3().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 3 + ",");
					humanUi.getM3().setMoleKill(false);
				}
				else if(humanUi.getM4().getX() >= getX() - 5 && humanUi.getM4().getX() <= getX() + 5 && humanUi.getM4().getY()<= 275 && humanUi.getM4().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 4 + ",");		
					humanUi.getM4().setMoleKill(false);
				}
				else if(humanUi.getM5().getX() >= getX() - 5 && humanUi.getM5().getX() <= getX() + 5 && humanUi.getM5().getY()<= 275 && humanUi.getM5().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 5 + ",");	
					humanUi.getM5().setMoleKill(false);
				}
				else if(humanUi.getM6().getX() >= getX() - 5 && humanUi.getM6().getX() <= getX() + 5 && humanUi.getM6().getY()<= 275 && humanUi.getM6().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 6 + ",");	
					humanUi.getM6().setMoleKill(false);
				}
				else if(humanUi.getM7().getX() >= getX() - 5 && humanUi.getM7().getX() <= getX() + 5 && humanUi.getM7().getY()<= 275 && humanUi.getM7().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 7 + ",");	
					humanUi.getM7().setMoleKill(false);
				}
				else if(humanUi.getM8().getX() >= getX() - 5 && humanUi.getM8().getX() <= getX() + 5 && humanUi.getM8().getY()<= 275 && humanUi.getM8().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 8 + ",");	
					humanUi.getM8().setMoleKill(false);
				}
				else if(humanUi.getM9().getX() >= getX() - 5 && humanUi.getM9().getX() <= getX() + 5 && humanUi.getM9().getY()<= 275 && humanUi.getM9().getMoleKill() == true) {
					ctx.writeAndFlush("[MOLEDIE]," + name + "," + 9 + ",");		
					humanUi.getM9().setMoleKill(false);
				}
			}
		});
	}
	
	
	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}

	public void setX(int x) {
		this.x = this.x + x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getStatus() {
		return status;
	}
	public int getBulletCount() {
		return bulletCount;
	}
	public void setBulletCount(int bulletCount) {
		this.bulletCount = bulletCount;
	}
	public int plusBcount() {
		return bulletCount += 2;
	}
	public void setHumanLife(int humanLife) {
		this.humanLife = humanLife;
	}
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}