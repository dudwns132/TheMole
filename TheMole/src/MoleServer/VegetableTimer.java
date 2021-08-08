package MoleServer;

import javax.swing.Timer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class VegetableTimer {
	private Timer vegTimer = null;
	private int vegetableCount = 0;

	public VegetableTimer(ChannelHandlerContext ctx, String vegetableNumber, String name, int section) {
		vegTimer = new Timer(1000, e -> {
			vegetableCount++;
			if (vegetableCount == 10) {
				int n = ((int) (Math.random() * 230)) + (230 * section) + 20;
				int x = ((int) (Math.random() * 10));
				for (Channel channel : Room.roomManager.get(name)) {
					channel.writeAndFlush(vegetableNumber + "," + n + "," + x + ",");
				}
				vegTimer.stop();
			}
		});
	}
	public Timer getVegTimer() {
		return vegTimer;
	}
}
