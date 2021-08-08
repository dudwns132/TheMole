package MoleServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class Message {

	public void moleDieMessage(ChannelHandlerContext ctx, String name, String moleNumber) {
		if (moleNumber.equals("1")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		} else if (moleNumber.equals("2")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		}  else if (moleNumber.equals("3")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		}  else if (moleNumber.equals("4")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		}  else if (moleNumber.equals("5")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		}  else if (moleNumber.equals("6")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		}  else if (moleNumber.equals("7")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		}  else if (moleNumber.equals("8")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		}  else if (moleNumber.equals("9")) {
			for (Channel channel : Room.roomManager.get(name)) {
				channel.writeAndFlush("MOLEDIE," + moleNumber + ",");
			}
		} 
	}
}
