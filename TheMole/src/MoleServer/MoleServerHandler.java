package MoleServer;

import java.util.Map.Entry;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MoleServerHandler extends ChannelInboundHandlerAdapter{

	private boolean loginId = true;
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ÇÏ¿°");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = (String)msg;
		   String[] s = readMessage.split(",");
		   System.out.println(readMessage);
	       if (s[0].equals("[LOGIN]")) {
	    	   for (Entry<Channel, String> entry : MoleServerMainHandler.onlineId.entrySet()) {
	    		   if (entry.getValue().equals(s[1])) {
	    			   ctx.writeAndFlush("AlreadyConnected");
	    			   loginId = false;
	    			   break;
	    		   }
	    	   }
	    	   if (loginId)
	    		   LoginServer.login(s[1], s[2], ctx);
	       }
	       else if (s[0].equals("[DUPLICATE]"))
	    	   LoginServer.duplicateCheck(s[1], ctx);
	       else if (s[0].equals("[SIGNUP]"))
	    	   LoginServer.signUpComplete(s[1], s[2], ctx);
	       else
	    	   ctx.fireChannelRead(readMessage);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelUnregistered();
	}
}
