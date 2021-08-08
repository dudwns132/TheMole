package MoleServer;

import javax.swing.JOptionPane;

import Mole.Game.LoginForm;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MoleClientHandler extends ChannelInboundHandlerAdapter{
	public static String serverMessage = "";
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("클라이언트 접속 완료");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = (String)msg;
		System.out.println(readMessage);
		if(msg.equals("LOGIN")) {
			serverMessage = readMessage;
			ctx.fireChannelActive();
		}
		else if(readMessage.equals("LOGINFAIL")) {
			serverMessage = readMessage;
			ctx.close();
		}
		else if(readMessage.equals("AlreadyConnected")) {
			serverMessage = readMessage;	
			ctx.close();
		}
		else if(readMessage.equals("DUPLICATE")) {
			serverMessage = readMessage;
			ctx.close();
		}
		else if(readMessage.equals("NODUPLICATE")) {
			serverMessage = readMessage;
			ctx.close();
		}
		else if(readMessage.equals("SIGNUP")) {
			serverMessage = readMessage;
			ctx.close();
		}
		else
			ctx.fireChannelRead(readMessage);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}	
