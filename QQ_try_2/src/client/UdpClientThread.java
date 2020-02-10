package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


//udp的服务端用于读取流显示到界面
public class UdpClientThread extends Thread {
    byte[] inBuff = new byte[4096];
    DatagramPacket inpacket = new DatagramPacket(inBuff, inBuff.length);
    ChatRoom privateTalk;
    DatagramSocket socket;
    String otherName;


    public UdpClientThread(DatagramSocket socket, ChatRoom privateTalk, String otherName) {
        this.socket = socket;
        this.privateTalk = privateTalk;
        privateTalk.showOnTextArea("已启动\n");
        this.otherName = otherName;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                socket.receive(inpacket);
            } catch (IOException e) {
            }
            System.out.println(new String(inBuff));
            String line = new String(inBuff, 4, inBuff.length - 4);
            privateTalk.showOnTextArea(otherName
                    +"("+NowTime.getTime()+"):\n"
                    +line+"\n");
            privateTalk.repaint();
        }
    }

}
