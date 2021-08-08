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

public class Game extends Canvas implements Runnable { // 다른 클래스,자바파일에서 new 키워드로 Game을 생성하지 말 것.

	private static final long serialVersionUID = 1L;
	// 프레임 설정 정보
	public static final int WIDTH = 800;
	public static final int HEIGHT = (int) (WIDTH / 12 * 9);
	public static final int SCALE = 1;
	public final String TITLE = "Mole Game";

	private static JLabel bulcount;
	public static int BULLETCOUNT = 5;
	private boolean is_shooting = false; // 총알 발사버튼을 꾹눌러서 줄줄이 나오는거 방지
	public static boolean buldirection = true; // 총알방향, true는 오른쪽, false는 왼쪽
	
	private int mole_count = 9; // 두더지 생성숫자

	private boolean running = false; // 게임의 실행여부
	private Thread thread;

	private BufferedImage background = null; // 배경출력하는 버퍼이미지
	private BufferedImage humSpriteSheet = null; // 인간출력하는 버퍼이미지
	private BufferedImage bulSpriteSheet = null; // 총알출력하는 버퍼이미지
	private BufferedImage molSpriteSheet = null; // 두더지출력하는 버퍼이미지
	private BufferedImage snaSpriteSheet = null; // 두더지출력하는 버퍼이미지

	// 캐릭터 생성
	private Human humanP;
	private Controller c; // 컨트롤러
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

		texture = new Textures(this); // 생성전에 텍스처를 생성
		humanP = new Human(200, 205, texture,this);
		snake = new Snake(texture,this);
		c = new Controller(this, texture);
		
		b = c.getBullet(); // 메소드를 부르기전에 Controller를 초기화 해주어야한다.
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
			thread.join(); // 모든 스레드가 끝날때까지 기다렸다가 다음(System.exit(1))으로 실행
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	@Override
	public void run() { // Runnable의 기본으로 구현된 메소드
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

	private void render() { // bufferstrategy는 장면 뒤의 모든 버퍼링을 관리한다.

		BufferStrategy bs = this.getBufferStrategy(); // this는 Canvas 객체를 가리킴

		if (bs == null) {
			createBufferStrategy(3); // 3개의 버퍼를 생성한다는 뜻, 바로 필요한것(사용하는중) 뒤에 두개의 이미지를 로딩하고 있을 것이다.
										// 30개의 버퍼를 생성한다면, 그만큼 CPU의 자원을 잡아먹게 된다. 적절한 갯수로 사용,운용
			return;
		}
		Graphics g = bs.getDrawGraphics(); // graphic을 여기에 가져오고, 버퍼를 그리기 위한 graphics context를 만든다, 외부 버퍼를 그림
		/////////////// 그림 그리는 공간 으로 추정//////////////////////////

		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

		humanP.render(g); // 인간 그리기
		c.render(g);
		// moleP.render(g);
		snake.render(g);

		///////////////////////////////////////////////////////////
		g.dispose(); // 계속 루프를 하는데 dispose로 지워주지 않는다면..?
		bs.show();
	}

	public void keyPressed(KeyEvent e) { // Key와 key 주의
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			ctx.writeAndFlush("[RIGHT]," + hostName);
			System.out.println("a");
			 // 오른쪽 방향키
			humanP.setVelX(3);
			humanP.rightMove();
		} else if (key == KeyEvent.VK_LEFT) {
			// 왼쪽 방향키
			humanP.setVelX(-3);
			humanP.leftMove();
		} else if (key == KeyEvent.VK_A && (humanP.getStatus() == 1 || humanP.getStatus() == 3) && (BULLETCOUNT > 0) && !is_shooting) {
			// 왼쪽을 보고있는 상태에서 A키
			this.buldirection = false;
			is_shooting = true;
			c.addEntity(new Bullet(humanP.getX(), humanP.getY() + 35, texture, this));
			bulcount.setText(String.format("남은 총알 수 : %d", --BULLETCOUNT));
		} else if (key == KeyEvent.VK_D && (humanP.getStatus() == 0 || humanP.getStatus() == 2) && (BULLETCOUNT > 0) && !is_shooting) { 
			// 오른쪽을 보고있는 상태에서 D키
			this.buldirection = true;
			is_shooting = true;
			c.addEntity(new Bullet(humanP.getX() + 50, humanP.getY() + 35, texture, this));
			bulcount.setText(String.format("남은 총알 수 : %d", --BULLETCOUNT));
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
		bulcount = new JLabel(String.format("남은 총알 수 : %d", BULLETCOUNT));
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

	public BufferedImage getHumSpriteSheet() { // Game 클래스의 내부 메소드 - spriteSheet를 가져오기
		return humSpriteSheet;
	}

	public BufferedImage getBulSpriteSheet() { // Game 클래스의 내부 메소드 - spriteSheet를 가져오기
		return bulSpriteSheet;
	}

	public BufferedImage getMolSpriteSheet() { // Game 클래스의 내부 메소드 - spriteSheet를 가져오기
		return molSpriteSheet;
	}
	public BufferedImage getSnaSpriteSheet() { // Game 클래스의 내부 메소드 - spriteSheet를 가져오기
		return snaSpriteSheet;
	}

	public Human getPlayer() { // Game 클래스의 내부 메소드 - spriteSheet를 가져오기, Controller 클래스에서 사용
		return humanP;
	}
}