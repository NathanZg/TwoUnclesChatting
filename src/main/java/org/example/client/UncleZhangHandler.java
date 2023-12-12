package org.example.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.constants.ChatConstants;
import org.example.protocol.msg.Message;

/**
 * 张大爷处理器
 * @author Nathan
 */
public class UncleZhangHandler extends SimpleChannelInboundHandler<Message> {

    private int cnt1 = 0;

    private int cnt2 = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        String data = message.getData();
        // System.out.println("收到李大爷的消息: " + message);
        Long requestId = message.getRequestId();
        if (ChatConstants.L_CHAT_1.equals(data)) {

        } else if (ChatConstants.L_CHAT_2.equals(data)) {
            cnt1++;
            channelHandlerContext.write(new Message(requestId, ChatConstants.Z_CHAT_2));
            if (cnt1 % 100 == 0) {
                channelHandlerContext.flush();
            }
        } else if (ChatConstants.L_CHAT_3.equals(data)) {
            cnt2++;
            channelHandlerContext.writeAndFlush(new Message(requestId, ChatConstants.Z_CHAT_3));
            if (cnt2 % 100 == 0) {
                channelHandlerContext.flush();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
