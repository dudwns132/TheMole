package MoleServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MainServer {
	private static final int PORT = 9829;
	
	public static void main(String[] args) throws Exception {
		new MainServer().serverStart();
	}
	
	public void serverStart() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverbootstrap = new ServerBootstrap();
			serverbootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new MoleServerInitializer());
		
		ChannelFuture future = serverbootstrap.bind(PORT).sync();
        future.channel().closeFuture().sync();
		} finally {
        bossGroup.shutdownGracefully().sync();
        workerGroup.shutdownGracefully().sync();
		}
	}
}
