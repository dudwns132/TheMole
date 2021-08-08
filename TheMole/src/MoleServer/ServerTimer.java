package MoleServer;

import java.util.HashMap;
import javax.swing.Timer;

import io.netty.channel.Channel;

public class ServerTimer {
	public static final HashMap<String, ServerTimer> gameTimer = new HashMap<String, ServerTimer>();
	private Timer playTimer;
	private Timer snakeTimer;
	private int gameTime = 0;
	private int itemCount = 0;
	private int moveCount = 0;
	
	public ServerTimer(String name) {
		playTimer = new Timer(1000, e -> {
			++itemCount;
			++gameTime;
			if (itemCount % 20 == 0) {
				int a = ((int) (Math.random() * 350)) + (400 * 0) + 50;
				int b = ((int) (Math.random() * 350)) + (400 * 1);
				for (Channel channel : Room.roomManager.get(name)) {
						channel.writeAndFlush("ITEM" + "," + a + "," + b + ",");
				}
			}
			for (Channel channel : Room.roomManager.get(name)) {
					channel.writeAndFlush("GAMECOUNT,");
			}
		});
		
		snakeTimer = new Timer(100, e -> {
			moveCount++;
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("SNAKEMOVE," + moveCount + ",");
			}
		});
	}
	
	public Timer getPlayTimer() {
		return playTimer;
	}
	
	public Timer getSnakeTimer() {
		return snakeTimer;
	}

}
