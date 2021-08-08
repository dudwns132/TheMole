package Standby;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Controller { // ����Ʈ�� �ʿ��Ѱ͵��� ���� �Ѿ�,�����۰���?

	private LinkedList<Bullet> b = new LinkedList<>();
	private LinkedList<Mole> m = new LinkedList<>();

	Bullet tempBullet;
	Mole tempMole;
	
	Game game;
	Textures texture;
	
	Random r = new Random();
	public Controller(Game game, Textures texture) {
		this.game = game;
		this.texture = texture;
	}
	
	public void tick() {
		// ��ƼƼ A
		for(int i =0; i < b.size(); i++) {
			tempBullet = b.get(i);
			if(game.buldirection == true && (tempBullet.getX() - game.getPlayer().getX()) > 150) // �Ѿ��� �����Ÿ� 150�� �Ѿ�� ���쵵�� ����
				removeEntity(tempBullet);
			else if(game.buldirection == false && Math.abs(game.getPlayer().getX() - tempBullet.getX()) > 100)
				removeEntity(tempBullet);
			tempBullet.tick();
		}
		// ��ƼƼ B
		for(int i =0; i < m.size(); i++) {
			tempMole = m.get(i);
			
			tempMole.tick();
		}
	}
	
	public void render(Graphics g) {
		// ��ƼƼ A
		for(int i = 0; i<b.size(); i++) {
			tempBullet = b.get(i);
			
			tempBullet.render(g);
		}
		// ��ƼƼ B
		for(int i = 0; i<m.size(); i++) {
			tempMole = m.get(i);
			
			tempMole.render(g);
		}
	}
	
	
	public void addMole(int mole_count) {
		for(int i = 0; i < mole_count; i++) {
			addEntity(new Mole((100+r.nextInt(Game.WIDTH*Game.SCALE-100)), 350+r.nextInt(Game.HEIGHT*Game.SCALE-350), false ,texture,game));
		}
	}
	
	public void addEntity(Bullet block) {  // add�� remove Entity �ߺ����� - �Ķ���Ͱ����� � ���� �߰�/�����ϴ��� �� �� ����
		b.add(block);
	}
	public void removeEntity(Bullet block) {
		b.remove(block);
	}
	public void addEntity(Mole block) {
		m.add(block);
	}
	public void removeEntity(Mole block) {
		m.remove(block);
	}
	
	public LinkedList<Bullet> getBullet() {
		return b;
	}
	public LinkedList<Mole> getMole() {
		return m;
	}
}
