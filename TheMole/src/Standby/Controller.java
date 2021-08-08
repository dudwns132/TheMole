package Standby;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Controller { // 리스트가 필요한것들의 통제 총알,아이템같은?

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
		// 엔티티 A
		for(int i =0; i < b.size(); i++) {
			tempBullet = b.get(i);
			if(game.buldirection == true && (tempBullet.getX() - game.getPlayer().getX()) > 150) // 총알의 사정거리 150을 넘어가면 지우도록 설정
				removeEntity(tempBullet);
			else if(game.buldirection == false && Math.abs(game.getPlayer().getX() - tempBullet.getX()) > 100)
				removeEntity(tempBullet);
			tempBullet.tick();
		}
		// 엔티티 B
		for(int i =0; i < m.size(); i++) {
			tempMole = m.get(i);
			
			tempMole.tick();
		}
	}
	
	public void render(Graphics g) {
		// 엔티티 A
		for(int i = 0; i<b.size(); i++) {
			tempBullet = b.get(i);
			
			tempBullet.render(g);
		}
		// 엔티티 B
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
	
	public void addEntity(Bullet block) {  // add와 remove Entity 중복정의 - 파라미터값으로 어떤 것을 추가/삭제하는지 알 수 있음
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
