package org.example.protocol.msg;

import cn.hutool.core.util.IdUtil;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * 消息
 *
 * @author Nathan
 */
public class Message implements Serializable {
    private Integer length;

    private Long requestId;

    private String data;

    private byte[] bytes;

    public Message(String data) {
        this.data = data;
        this.requestId = IdUtil.getSnowflakeNextId();
        this.bytes = data.getBytes(Charset.forName("GBK"));
        this.length = bytes.length + 8;
    }

    public Message(Long requestId, String data) {
        this.requestId = requestId;
        this.data = data;
        this.bytes = data.getBytes(Charset.forName("GBK"));
        this.length = bytes.length + 8;
    }

    @Override
    public String toString() {
        return "Message{" +
                "requestId=" + requestId +
                ", data='" + data + '\'' +
                '}';
    }

    public int getLength() {
        return length;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getData() {
        return data;
    }

    public byte[] getBytes() {
        return this.bytes;
    }
}
