package MoleServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Room {
	public static final HashMap<String, ChannelGroup> roomManager = new HashMap<String, ChannelGroup>();
	//public static final HashMap<Channel, ChannelGroup> roomChannel = new HashMap<Channel, ChannelGroup>();
	
	public static void roomCreat(ChannelHandlerContext ctx, String roomHost) {
		Channel host = ctx.channel();
		ChannelGroup room = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
		room.add(host);
		roomManager.put(roomHost, room);
		ctx.writeAndFlush("CREAT," + roomHost);
		System.out.println(roomManager);
	}

	public static void roomJoin(ChannelHandlerContext ctx, String roomHostName, String myName) {
		Channel guest = ctx.channel();
		
		if (roomManager.get(roomHostName).size() > 1)
			ctx.writeAndFlush("FULL");
		else {
			roomManager.get(roomHostName).add(guest);
			
			for (Channel channel : roomManager.get(roomHostName)) {
				if(channel == guest)
					guest.writeAndFlush("JOIN," + roomHostName + "," + myName);
				else
					channel.writeAndFlush("GUEST," + roomHostName + "," + myName);
			}
		}
		System.out.println(roomManager);
	}
	
	public static void roomListSend(ChannelHandlerContext ctx) {
		ctx.write("ROOMLIST,");
		for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
			ctx.write(entry.getKey() + ",");
		ctx.flush();
	}
	
	public static void roomListRefresh(ChannelHandlerContext ctx) {		
		ctx.write("REFRESH,");
		for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
			ctx.write(entry.getKey() + ",");
		ctx.flush();
	}
	
	public static void roomDelete(ChannelHandlerContext ctx, String hostName) {
		Channel me = ctx.channel();
		
		for (Channel channel : roomManager.get(hostName)) {
			roomManager.remove(hostName);
			if(channel == me) {
				me.write("REFRESH,");
				for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
					me.write(entry.getKey() + ",");
				me.flush();
			} else {
				channel.write("BOOM,");
				for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
					channel.write(entry.getKey() + ",");
				channel.flush();
			}
		}
	}
	
	public static void roomOut(ChannelHandlerContext ctx, String hostName) {
		Channel guest = ctx.channel();

		roomManager.get(hostName).remove(guest);
		ctx.write("REFRESH,");
		for (Entry<String, ChannelGroup> entry : roomManager.entrySet())
			ctx.write(entry.getKey() + ",");
		ctx.flush();
		
		for (Channel channel : roomManager.get(hostName)) {
			if(channel != guest)
				channel.writeAndFlush("GUESTOUT," + hostName);
		}
	}
	
	public static void readyState(ChannelHandlerContext ctx, String m, String hostName) {
		Channel guest = ctx.channel();
		
		if (m.equals("[READY]")) {
			for (Channel channel : roomManager.get(hostName)) {
				if(channel != guest)
					channel.writeAndFlush("READY");
			}
		} else {
			for (Channel channel : roomManager.get(hostName)) {
				if(channel != guest)
					channel.writeAndFlush("CANSLE");
			}
		}
	}
	
	public static void roomChatting(ChannelHandlerContext ctx, String message, String name, String hostName) {
		Channel host = ctx.channel();
		roomManager.get(hostName).writeAndFlush("SENDMESSAGE," + name + ": " + message);
	}
	
	public static void startGame(ChannelHandlerContext ctx, String hostName, String guestName) {
		Channel host = ctx.channel();
		//roomChannel.put(ctx.channel(), roomManager.get(hostName));
		int r = (int)(Math.random()*2);
		int v1 = ((int) (Math.random() * 260)) + 263 * 0;
		int v2 = ((int) (Math.random() * 260)) + 263 * 1;
		int v3 = ((int) (Math.random() * 260)) + 263 * 2;
		int v4 = ((int) (Math.random() * 10));
		int v5 = ((int) (Math.random() * 10));
		int v6 = ((int) (Math.random() * 10));
		for (Channel channel : roomManager.get(hostName)) {
			if (r == 0 && channel != host) {
				host.writeAndFlush("MOLESTART," + hostName + "," + guestName + "," + hostName + "," + guestName + "," + v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5 + "," + v6);
				channel.writeAndFlush("HUMANSTART,"  + hostName + "," + guestName + "," + guestName + "," + hostName + "," + v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5 + "," + v6);
			//	ItemTimer itemTimer = new ItemTimer(ctx, host, hostName, "MOLEITEM", "HUMANITEM");
			//	itemTimer.getItemTimer().start();
			} 
			else if (r == 1 && channel != host) {
				host.writeAndFlush("HUMANSTART,"  + hostName + ","  + guestName + "," + hostName + "," + guestName + "," + v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5 + "," + v6);
				channel.writeAndFlush("MOLESTART,"  + hostName + "," + guestName + "," + guestName + "," + hostName + "," + v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5 + "," + v6);
			//	ItemTimer itemTimer = new ItemTimer(ctx, host, hostName, "HUMANITEM", "MOLEITEM");
			//	itemTimer.getItemTimer().start();
			}
		}
	}
}

