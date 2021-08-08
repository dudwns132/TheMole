package MoleServer;

import java.nio.charset.Charset;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MoleClientInitializer extends ChannelInitializer {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
			//pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
			pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
			pipeline.addLast(new StringEncoder(Charset.forName("UTF-8")));
			pipeline.addLast(new MoleClientHandler());
			pipeline.addLast(new MoleClientMainHandler());
			pipeline.addLast(new MoleClientGameHandler());
	}
	
}
