package Standby;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import io.netty.channel.ChannelHandlerContext;

public class Game extends Canvas implements Runnable { // �ٸ� Ŭ����,�ڹ����Ͽ��� new Ű����� Game�� �������� �� ��.

	private static final long serialVersionUID = 1L;
	// ������ ���� ����
	public static final int WIDTH = 800;
	public static final int HEIGHT = (int) (WIDTH / 12 * 9);
	public static final int SCALE = 1;
	public final String TITLE = "Mole Game";

	private static JLabel bulcount;
	public static int BULLETCOUNT = 5;
	private boolean is_shooting = false; // �Ѿ� �߻��ư�� �ڴ����� ������ �����°� ����
	public static boolean buldirection = true; // �Ѿ˹���, true�� ������, false�� ����
	
	private int mole_count = 9; // �δ��� ��������

	private boolean running = false; // ������ ���࿩��
	private Thread thread;

	private BufferedImage background = null; // �������ϴ� �����̹���
	private BufferedImage humSpriteSheet = null; // �ΰ�����ϴ� �����̹���
	private BufferedImage bulSpriteSheet = null; // �Ѿ�����ϴ� �����̹���
	private BufferedImage molSpriteSheet = null; // �δ�������ϴ� �����̹���
	private BufferedImage snaSpriteSheet = null; // �δ�������ϴ� �����̹���

	// ĳ���� ����
	private Human humanP;
	private Controller c; // ��Ʈ�ѷ�
	// private Mole moleP;
	private Snake snake;
	private Textures texture;
	
	public LinkedList<Bullet> b;
	public LinkedList<Mole> m;
	ChannelHandlerContext ctx;
	String hostName;
	public Game(ChannelHandlerContext ctx, String hostName) {
		this.ctx = ctx;
		this.hostName = hostName;
	}
	public void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			humSpriteSheet = loader.loadImage("/humanSpr.png");
			bulSpriteSheet = loader.loadImage("/bulimg.png");
			molSpriteSheet = loader.loadImage("/moleSpr.png");
			snaSpriteSheet = loader.loadImage("/snakeSpr.png");
			background = loader.loadImage("/Backgrounds.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		texture = new Textures(this); // �������� �ؽ�ó�� ����
		humanP = new Human(200, 205, texture,this);
		snake = new Snake(texture,this);
		c = new Controller(this, texture);
		
		b = c.getBullet(); // �޼ҵ带 �θ������� Controller�� �ʱ�ȭ ���־���Ѵ�.
		m = c.getMole();
		
		addKeyListener(new KeyInput(this));
		c.addMole(mole_count);
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			thread.join(); // ��� �����尡 ���������� ��ٷȴٰ� ����(System.exit(1))���� ����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	@Override
	public void run() { // Runnable�� �⺻���� ������ �޼ҵ�
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (running) { // Game Loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now; // OldTime = CurTime,

			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
				
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " Ticks, Fps - " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		humanP.tick();
		c.tick();
		snake.tick();
	}

	private void render() { // bufferstrategy�� ��� ���� ��� ���۸��� �����Ѵ�.

		BufferStrategy bs = this.getBufferStrategy(); // this�� Canvas ��ü�� ����Ŵ

		if (bs == null) {
			createBufferStrategy(3); // 3���� ���۸� �����Ѵٴ� ��, �ٷ� �ʿ��Ѱ�(����ϴ���) �ڿ� �ΰ��� �̹����� �ε��ϰ� ���� ���̴�.
										// 30���� ���۸� �����Ѵٸ�, �׸�ŭ CPU�� �ڿ��� ��Ƹ԰� �ȴ�. ������ ������ ���,���
			return;
		}
		Graphics g = bs.getDrawGraphics(); // graphic�� ���⿡ ��������, ���۸� �׸��� ���� graphics context�� �����, �ܺ� ���۸� �׸�
		/////////////// �׸� �׸��� ���� ���� ����//////////////////////////

		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

		humanP.render(g); // �ΰ� �׸���
		c.render(g);
		// moleP.render(g);
		snake.render(g);

		///////////////////////////////////////////////////////////
		g.dispose(); // ��� ������ �ϴµ� dispose�� �������� �ʴ´ٸ�..?
		bs.show();
	}

	public void keyPressed(KeyEvent e) { // Key�� key ����
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			ctx.writeAndFlush("[RIGHT]," + hostName);
			System.out.println("a");
			 // ������ ����Ű
			humanP.setVelX(3);
			humanP.rightMove();
		} else if (key == KeyEvent.VK_LEFT) {
			// ���� ����Ű
			humanP.setVelX(-3);
			humanP.leftMove();
		} else if (key == KeyEvent.VK_A && (humanP.getStatus() == 1 || humanP.getStatus() == 3) && (BULLETCOUNT > 0) && !is_shooting) {
			// ������ �����ִ� ���¿��� AŰ
			this.buldirection = false;
			is_shooting = true;
			c.addEntity(new Bullet(humanP.getX(), humanP.getY() + 35, texture, this));
			bulcount.setText(String.format("���� �Ѿ� �� : %d", --BULLETCOUNT));
		} else if (key == KeyEvent.VK_D && (humanP.getStatus() == 0 || humanP.getStatus() == 2) && (BULLETCOUNT > 0) && !is_shooting) { 
			// �������� �����ִ� ���¿��� DŰ
			this.buldirection = true;
			is_shooting = true;
			c.addEntity(new Bullet(humanP.getX() + 50, humanP.getY() + 35, texture, this));
			bulcount.setText(String.format("���� �Ѿ� �� : %d", --BULLETCOUNT));
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			humanP.setVelX(0);
			humanP.rightStand();
		} else if (key == KeyEvent.VK_LEFT) {
			humanP.setVelX(0);
			humanP.leftStand();
		} else if (key == KeyEvent.VK_A) {
			is_shooting = false;
		} else if (key == KeyEvent.VK_D) {
			is_shooting = false;
		}
	}

	/*public static void main(String args[]) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		bulcount = new JLabel(String.format("���� �Ѿ� �� : %d", BULLETCOUNT));
		//bulcount.setBounds(1, 1, 120, 30);
		frame.add(bulcount);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//frame.setLayout(null);
		//frame.setSize(800,600);
		game.start();
	}*/

	public BufferedImage getHumSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return humSpriteSheet;
	}

	public BufferedImage getBulSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return bulSpriteSheet;
	}

	public BufferedImage getMolSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return molSpriteSheet;
	}
	public BufferedImage getSnaSpriteSheet() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������
		return snaSpriteSheet;
	}

	public Human getPlayer() { // Game Ŭ������ ���� �޼ҵ� - spriteSheet�� ��������, Controller Ŭ�������� ���
		return humanP;
	}
}