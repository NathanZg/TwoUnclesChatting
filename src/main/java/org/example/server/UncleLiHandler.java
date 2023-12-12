package org.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.constants.ChatConstants;
import org.example.protocol.msg.Message;

/**
 * @author Nathan
 */
public class UncleLiHandler extends SimpleChannelInboundHandler<Message> {
    private int meetCount;

    private int cnt1 = 0;

    private int cnt2 = 0;

    private int cnt3 = 0;
    public UncleLiHandler(int meetCount) {
        this.meetCount = meetCount;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        String data = message.getData();
        // System.out.println("收到张大爷的消息: " + message);
        Long requestId = message.getRequestId();
        if (ChatConstants.Z_CHAT_1.equals(data)) {
            cnt1++;
            channelHandlerContext.write(new Message(requestId, ChatConstants.L_CHAT_1));
            channelHandlerContext.write(new Message(ChatConstants.L_CHAT_2));
            if (cnt1 % 100 == 0) {
                channelHandlerContext.flush();
            }
        } else if (ChatConstants.Z_CHAT_2.equals(data)) {
            cnt2++;
            channelHandlerContext.writeAndFlush(new Message(ChatConstants.L_CHAT_3));
            if (cnt2 % 100 == 0) {
                channelHandlerContext.flush();
            }
        } else if (ChatConstants.Z_CHAT_3.equals(data)) {
            cnt3++;
            if (cnt3 == meetCount) {
                channelHandlerContext.channel().close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
