package org.example.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.example.protocol.msg.Message;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 消息解密器
 * @author Nathan
 */
public class MessageDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            if (byteBuf.readableBytes() < 4) {
                return;
            }

            // 标记readIndex
            byteBuf.markReaderIndex();
            // 读取长度
            int length = byteBuf.readInt();

            if (length <= 0) {
                channelHandlerContext.close();
                return;
            }

            if (byteBuf.readableBytes() <= 8) {
                byteBuf.resetReaderIndex();
                return;
            }

            // 读取 requestId
            long requestId = byteBuf.readLong();

            if (byteBuf.readableBytes() < length - 8) {
                byteBuf.resetReaderIndex();
                return;
            }

            byte[] bytes = new byte[length - 8];
            byteBuf.readBytes(bytes);
            String data = new String(bytes, Charset.forName("GBK"));
            list.add(new Message(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
