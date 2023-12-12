package org.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.example.protocol.codec.MessageDecoder;
import org.example.protocol.codec.MessageEncoder;

/**
 * @author Nathan
 */
public class UncleLiServer {

    private static final Integer PORT = 8080;

    private static final String ADDRESS  = "localhost";

    public void run() {
        Thread thread = new Thread(() -> {
            ServerBootstrap uncleLiServer = new ServerBootstrap();
            NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
            NioEventLoopGroup workGroup = new NioEventLoopGroup(1);
            try {
                uncleLiServer.group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline()
                                        .addLast(new MessageDecoder(), new MessageEncoder(), new UncleLiHandler(100000));
                            }
                        })
                        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                        .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                        .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
                ChannelFuture f = uncleLiServer.bind(ADDRESS, PORT).sync();
                System.out.println("李大爷在胡同口等待......");
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        });
        thread.start();
    }
}
