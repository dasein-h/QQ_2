package server;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpServer extends Thread {
    private int SERVER_PORT = 30000;
    @Override
    public void run() {
        try (
                ServerSocket ss = new ServerSocket(SERVER_PORT)
        ) {
            MyServer.showOnJa1("\nTCP服务器......打开\n");
            int n = 0;
            while (true) {
                var socket = ss.accept();
                MyServer.showOnJa1("连接成功...数量:" + ++n + "\n");
                new TcpThread(socket).start();
            }
        } catch (IOException ex) {
            MyServer.showOnJa1("服务器启动失败，是否端口" +
                    SERVER_PORT + "已被占用？" +
                    "或者重复打开\n");
        }
    }
}
