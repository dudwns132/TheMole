package MoleServer;

import java.nio.charset.Charset;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MoleServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
			//pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
			pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
			pipeline.addLast(new StringEncoder(Charset.forName("UTF-8")));
			pipeline.addLast(new MoleServerHandler());
			pipeline.addLast(new MoleServerMainHandler());
			pipeline.addLast(new MoleServerGameHandler());
	}
}
