package MoleServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MoleServerGameHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = (String) msg;
		String[] s = readMessage.split(",");
		Channel myChannel = ctx.channel();
		 if (s[0].equals("[START]")) {
				Room.startGame(ctx, s[1], s[2]);
				ServerTimer serverTimer = new ServerTimer(s[1]);
				ServerTimer.gameTimer.put(s[1], serverTimer);
				ServerTimer.gameTimer.get(s[1]).getPlayTimer().start();
			}
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals("[LEFT]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("LEFT,");
				}
			} else if (s[i].equals("[RIGHT]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("RIGHT,");
				}
			} else if (s[i].equals("[LEFTSTOP]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("LEFTSTOP,");
				}
			}  else if (s[i].equals("[RIGHTSTOP]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("RIGHTSTOP,");
				}
			}else if (s[i].equals("[v1EAT]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("v1EAT,");
				}
				VegetableTimer vegetableTimer = new VegetableTimer(ctx, "v1", s[i+1], 0);
				vegetableTimer.getVegTimer().start();
			} else if (s[i].equals("[v2EAT]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("v2EAT,");
				}
				VegetableTimer vegetableTimer = new VegetableTimer(ctx, "v2", s[i+1], 1);
				vegetableTimer.getVegTimer().start();
			} else if (s[i].equals("[v3EAT]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("v3EAT,");
				}
				VegetableTimer vegetableTimer = new VegetableTimer(ctx, "v3", s[i+1], 2);
				vegetableTimer.getVegTimer().start();
			} else if (s[i].equals("[MOLEITEM1EAT]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("MOLEITEM1EAT,");
				}
			} else if (s[i].equals("[MOLEITEM2EAT]")) {
				for (Channel channel : Room.roomManager.get(s[i+1])) {
						channel.writeAndFlush("MOLEITEM2EAT,");
				}
			}

			else if (s[i].equals("[HUMANITEM1EAT]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("HUMANITEM1EAT,");
				}
			} else if (s[i].equals("[HUMANITEM2EAT]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("HUMANITEM2EAT,");
				}
			} else if (s[i].equals("[HUMANSPEEDUP]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("HUMANSPEEDUP,");
				}
			} else if (s[i].equals("[HUMANSPEEDDOWN]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("HUMANSPEEDDOWN,");
				}
			} else if (s[i].equals("[MOLEMOVE]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
					if (channel != myChannel)
						channel.writeAndFlush("MOLEMOVE," + s[i + 2] + "," + s[i + 3] + "," + s[i + 4] + ",");
				}
			} else if (s[i].equals("[MOLETRAP]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("MOLETRAP,");
				}
			} else if (s[i].equals("[MOLETRAPSTOP]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("MOLETRAPSTOP,");
				}
			} else if (s[i].equals("[HUMANTRAP]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
					if (channel != myChannel)
						channel.writeAndFlush("HUMANTRAP,");
				}
			} else if (s[i].equals("[HUMANTRAPSTOP]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
					if (channel != myChannel)
						channel.writeAndFlush("HUMANTRAPSTOP,");
				}
			} else if (s[i].equals("[SNAKE]")) {
				int status = (int) (Math.random() * 2);
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("SNAKE," + status + ",");
				}
				ServerTimer.gameTimer.get(s[i + 1]).getSnakeTimer().start();
			} else if (s[i].equals("[BULLET]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
						channel.writeAndFlush("BULLET," + s[i + 2] + "," + s[i + 3] + ",");
				}
			} else if (s[i].equals("[MOLEDIE]")) {
				Message m = new Message();
				m.moleDieMessage(ctx, s[i + 1], s[i + 2]);
			} else if (s[i].equals("[SNAKEDIE]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
					channel.writeAndFlush("SNAKEDIE,");
				}
				ServerTimer.gameTimer.get(s[i + 1]).getSnakeTimer().stop();
			} else if (s[i].equals("[MINUSLIFE]")) {
				for (Channel channel : Room.roomManager.get(s[i + 1])) {
					channel.writeAndFlush("MINUSLIFE,");
				}
				ServerTimer.gameTimer.get(s[i + 1]).getSnakeTimer().stop();
			}
		}
		if (s[0].equals("[HUMANWIN]")) {
			DBConnect.humanWin(s[2]);
			ServerTimer.gameTimer.get(s[1]).getPlayTimer().stop();
			ServerTimer.gameTimer.remove(s[1]);
		}
		else if (s[0].equals("[MOLELOSE]")) {
			DBConnect.gameLose(s[1]);
		}
		else if (s[0].equals("[MOLEWIN]")) {
			DBConnect.moleWin(s[2]);
			ServerTimer.gameTimer.get(s[1]).getPlayTimer().stop();
			ServerTimer.gameTimer.remove(s[1]);
		}
		else if (s[0].equals("[HUMANLOSE]")) {
			DBConnect.gameLose(s[1]);
		}
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
	}
}
