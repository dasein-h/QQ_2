package server;

import client.ChatRoom;
import client.NowTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/*

TCP接收线程

 */
public class TcpThread extends Thread {
    private Socket socket;
    private BufferedReader br = null;
    private PrintStream ps = null;

    public TcpThread(Socket socket) {
        this.socket = socket;
    }

    /*
    接收TCP的消息
    如果是名字，则显示该用户进入群聊
    如果是内容，则显示内容
    出现异常就关闭流等
     */
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("&") && line.startsWith("&")) {
                    String name = line.substring(1, line.length() - 1);
                    MyServer.clients.put(name, ps);
                    for (var clientPs : MyServer.clients.valueSet()) {
                        clientPs.println(name + "已加入群聊\n");
                    }

                } else {
                    for (var clientPs : MyServer.clients.valueSet()) {
                        clientPs.println(MyServer.clients.getKeyByValue(ps) + "(" + NowTime.getTime()
                                + "):\n" + line);
                    }
                }
            }
        } catch (IOException e) {
            MyServer.clients.removeByValue(ps);
            try {
                if (br != null) {
                    br.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
