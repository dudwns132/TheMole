package MoleServer;

import java.util.HashMap;
import java.util.Map.Entry;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MoleServerMainHandler extends ChannelInboundHandlerAdapter {
	public static final HashMap<Channel, String> onlineId = new HashMap<Channel, String>();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = (String) msg;
		String[] s = readMessage.split(",");
		if (s[0].equals("[INFORMATION]"))
			DBConnect.informationDB(s[1], ctx);
		else if (readMessage.equals("[RANKING]"))
			DBConnect.leaderBoardDB(ctx);
		else if (s[0].equals("[CREAT]"))
			Room.roomCreat(ctx, s[1]);
		else if (s[0].equals("[JOIN]"))
			Room.roomJoin(ctx, s[1], s[2]);
		else if (readMessage.equals("[LIST]"))
			Room.roomListSend(ctx);
		else if (readMessage.equals("[REFRESH]"))
			Room.roomListRefresh(ctx);
		else if (s[0].equals("[HOSTOUT]"))
			Room.roomDelete(ctx, s[1]);
		else if (s[0].equals("[GUESTOUT]"))
			Room.roomOut(ctx, s[1]);
		else if (s[0].equals("[SENDMESSAGE]"))
			Room.roomChatting(ctx, s[1], s[2], s[3]);
		else if (s[0].equals("[READY]"))
			Room.readyState(ctx, s[0], s[1]);
		else if (s[0].equals("[CANSLE]"))
			Room.readyState(ctx, s[0], s[1]);
		else
			ctx.fireChannelRead(readMessage);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		Channel logoutUser = ctx.channel();
		for (Entry<String, ChannelGroup> entry : Room.roomManager.entrySet()) {
			entry.getValue().remove(logoutUser);
			if (entry.getValue().size() == 0)
				Room.roomManager.remove(entry.getKey());
		}
		//Room.roomChannel.remove(ctx.channel());//테스트중에만 잠시	
		onlineId.remove(logoutUser);
		
		ctx.fireChannelUnregistered();
	}
}
