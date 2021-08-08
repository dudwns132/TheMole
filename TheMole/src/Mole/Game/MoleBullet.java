package Mole.Game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MoleBullet extends JLabel {
	
	private int x;
	private int y = 255;
	private int direction = 0;

	private MoleInHumanPerformance hum;
	private Timer shoottimer;
	private Timer dshoottimer;
	private int shootsec = 0;
	private int dshootsec = 0;
	private MoleUI mainpanel;
	
	private ImageIcon bulletR = new ImageIcon("img/bulletR.png");
	private ImageIcon bulletL = new ImageIcon("img/bulletL.png");
	private ImageIcon bulletD = new ImageIcon("img/bulletD.png");
	
	public MoleBullet(int x, int direction, int status, MoleUI pan) {
		mainpanel = pan;
		if (direction == 1) {
			this.x = x + 50;
		} else if(direction == 2){
			this.x = x;
		} else
			this.x = x+15;
		System.out.println(y);
		this.direction = direction;
		
		
		if (direction == 1 && status == 1) {
			setBounds((int) x, (int) y, 16, 16);
			setIcon(bulletR);
			pan.add(this);
			this.setVisible(true);
			shooting(this);
			shoottimer.start();
		} else if (direction == 2 && status == 2) { 
			setBounds((int) x, (int) y, 16, 16);
			setIcon(bulletL);
			pan.add(this);
			this.setVisible(true);
			shooting(this);
			shoottimer.start();
		} else if (direction == 3) {
			setBounds((int) x , (int) y, 16, 16);
			setIcon(bulletD);
			pan.add(this);
			this.setVisible(true);
			dshooting();
			dshoottimer.start();
		}
	}
	public void dshooting() {
		dshoottimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dshootsec++;
				System.out.println(x + "     " + y);
				for (int i = 0; i < 5; i++) {
					bulMoveD(1);
					setBounds(x,y, 16, 16);
					/*if (mainpanel.getM1().getMoleButton().getX() >= getX() - 20 && mainpanel.getM1().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM1().getMoleButton().getY() == y && mainpanel.getM1().getLife() == true) {
						mainpanel.getM1().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM2().getMoleButton().getX() >= getX() - 20 && mainpanel.getM2().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM2().getMoleButton().getY() == y && mainpanel.getM2().getLife() == true) {
						mainpanel.getM2().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM3().getMoleButton().getX() >= getX() - 20 && mainpanel.getM3().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM3().getMoleButton().getY() == y && mainpanel.getM3().getLife() == true) {
						mainpanel.getM3().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM4().getMoleButton().getX() >= getX() - 20 && mainpanel.getM4().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM4().getMoleButton().getY() == y && mainpanel.getM4().getLife() == true) {
						mainpanel.getM4().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM5().getMoleButton().getX() >= getX() - 20 && mainpanel.getM5().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM5().getMoleButton().getY() == y && mainpanel.getM5().getLife() == true) {
						mainpanel.getM5().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM6().getMoleButton().getX() >= getX() - 20 && mainpanel.getM6().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM6().getMoleButton().getY() == y && mainpanel.getM6().getLife() == true) {
						mainpanel.getM6().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM7().getMoleButton().getX() >= getX() - 20 && mainpanel.getM7().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM7().getMoleButton().getY() == y && mainpanel.getM7().getLife() == true) {
						mainpanel.getM7().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM8().getMoleButton().getX() >= getX() - 20 && mainpanel.getM8().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM8().getMoleButton().getY() == y && mainpanel.getM8().getLife() == true) {
						mainpanel.getM8().moleDie();
						setVisible(false);
						dshoottimer.stop();
					} else if (mainpanel.getM9().getMoleButton().getX() >= getX() - 20 && mainpanel.getM9().getMoleButton().getX() <= getX() + 20
							&& mainpanel.getM9().getMoleButton().getY() == y && mainpanel.getM9().getLife() == true) {
						mainpanel.getM9().moleDie();
						setVisible(false);
						dshoottimer.stop();
					}*/
				}if (dshootsec == 30) {
					setVisible(false);
					mainpanel.remove(mainpanel.getMoleInHumanPerformance().getB());
					dshoottimer.stop();
				}
			}
		});
	}
	public void shooting(JLabel bul) {
		shoottimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shootsec++;
				for (int i = 0; i < 5; i++) {
					if (direction == 1) { 
						bulMove(1);
						setBounds(((int) bul.getX()), (int) y, 16, 16);
						if (shootsec == 30) {
							bul.setVisible(false);
							mainpanel.remove(bul);
							shoottimer.stop();
						}
					} else if (direction == 2) { 
						bulMove(-1);
						setBounds(((int) bul.getX()), (int) y, 16, 16);
						if (shootsec == 30) {
							bul.setVisible(false);
							mainpanel.remove(bul);
							shoottimer.stop();
						}
					}
				/*	if (mainpanel.getM1().getMoleButton().getX() == bul.getX() && mainpanel.getM1().getMoleButton().getY() <= 275
							&& mainpanel.getM1().getLife() == true) {
						bul.setVisible(false);
						shoottimer.stop();
						mainpanel.getM1().moleDie();
					} else if (mainpanel.getM2().getMoleButton().getX() == bul.getX() && mainpanel.getM2().getMoleButton().getY() <= 275
							&& mainpanel.getM2().getLife() == true) {
						bul.setVisible(false);
						shoottimer.stop();
						mainpanel.getM2().moleDie();
					} else if (mainpanel.getM3().getMoleButton().getX() == bul.getX() && mainpanel.getM3().getMoleButton().getY() <= 275
							&& mainpanel.getM3().getLife() == true) {
						bul.setVisible(false);
						shoottimer.stop();
						mainpanel.getM3().moleDie();
					} else if (mainpanel.getM4().getMoleButton().getX() == bul.getX() && mainpanel.getM4().getMoleButton().getY() <= 275
							&& mainpanel.getM4().getLife() == true) {
						mainpanel.getM4().moleDie();
						bul.setVisible(false);
						shoottimer.stop();
					} else if (mainpanel.getM5().getMoleButton().getX() == bul.getX() && mainpanel.getM5().getMoleButton().getY() <= 275
							&& mainpanel.getM5().getLife() == true) {
						mainpanel.getM5().moleDie();
						bul.setVisible(false);
						shoottimer.stop();
					} else if (mainpanel.getM6().getMoleButton().getX() == bul.getX() && mainpanel.getM6().getMoleButton().getY() <= 275
							&& mainpanel.getM6().getLife() == true) {
						mainpanel.getM6().moleDie();
						bul.setVisible(false);
						shoottimer.stop();
					} else if (mainpanel.getM7().getMoleButton().getX() == bul.getX() && mainpanel.getM7().getMoleButton().getY() <= 275
							&& mainpanel.getM7().getLife() == true) {
						mainpanel.getM7().moleDie();
						bul.setVisible(false);
						shoottimer.stop();
					} else if (mainpanel.getM8().getMoleButton().getX() == bul.getX() && mainpanel.getM8().getMoleButton().getY() <= 275
							&& mainpanel.getM8().getLife() == true) {
						mainpanel.getM8().moleDie();
						bul.setVisible(false);
						shoottimer.stop();
					} else if (mainpanel.getM9().getMoleButton().getX() == bul.getX() && mainpanel.getM9().getMoleButton().getY() <= 275
							&& mainpanel.getM9().getLife() == true) {
						mainpanel.getM9().moleDie();
						bul.setVisible(false);
						shoottimer.stop();
					} else if (mainpanel.getIsSnake() == true){
						if (mainpanel.getSnake().getX() == bul.getX() && mainpanel.getSnake().getMoving() == true) {
							mainpanel.getSnake().snakeDie();
							bul.setVisible(false);
							shoottimer.stop();
						}
					}*/
				}
			}

		});
	}
	public void bulMove(int x) {
		this.x = this.x + x;
	}
	
	public void bulMoveD(int y) {
		this.y = this.y + y;
	}
	
	public int getX() {
		return (int)x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 16, 16);
	}
}
