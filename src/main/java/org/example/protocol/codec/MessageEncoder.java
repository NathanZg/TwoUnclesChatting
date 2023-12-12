package org.example.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.protocol.msg.Message;

/**
 * 消息编码器
 * @author Nathan
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(message.getLength());
        byteBuf.writeLong(message.getRequestId());
        byteBuf.writeBytes(message.getBytes());
    }
}
