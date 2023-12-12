package org.example;

import org.example.client.UncleZhangClient;
import org.example.server.UncleLiServer;

/**
 * 张大爷和李大爷全双工通信 100000 次耗时测试
 * @author Nathan
 */
public class TwoUnnclesChattingApplication {
    public static void main(String[] args) {
        UncleLiServer uncleLiServer = new UncleLiServer();
        UncleZhangClient uncleZhangClient = new UncleZhangClient();
        uncleLiServer.run();
        uncleZhangClient.run();
    }
}
