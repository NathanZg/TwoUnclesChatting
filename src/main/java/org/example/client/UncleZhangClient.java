package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.constants.ChatConstants;
import org.example.protocol.codec.MessageDecoder;
import org.example.protocol.codec.MessageEncoder;
import org.example.protocol.msg.Message;

/**
 * 张大爷 服务端
 * @author Nathan
 */
public class UncleZhangClient {

    private static final Integer PORT = 8080;

    private static final String ADDRESS  = "localhost";

    public void run() {
        Thread thread = new Thread(() -> {
            NioEventLoopGroup workGroup = new NioEventLoopGroup(1);
            Bootstrap uncleZhangClient = new Bootstrap();
            try {
                uncleZhangClient.group(workGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                ChannelPipeline pipeline = socketChannel.pipeline();
                                pipeline.addLast(new MessageDecoder(), new MessageEncoder(), new UncleZhangHandler());
                            }
                        })
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
                long start = System.currentTimeMillis();
                ChannelFuture f = uncleZhangClient.connect(ADDRESS, PORT).sync();
                Channel channel = f.channel();
                for (int i = 0; i < 100000; i++) {
                    channel.write(new Message(ChatConstants.Z_CHAT_1));
                    if (i % 100 == 0) {
                        channel.flush();
                    }
                }
                channel.flush();
                channel.closeFuture().sync();
                System.out.println("会面完成,耗时：" + (System.currentTimeMillis() - start) + "ms");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                workGroup.shutdownGracefully();
            }
        });
        thread.start();
    }
}
