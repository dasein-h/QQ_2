package client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    private String thisName;
    private String anotherName;
    private int thisPORT, anotherPORT;
    private ChatRoom privateTalk;
    private byte[] outBuff = new byte[4096];
    private DatagramPacket outPacket = null;
    DatagramSocket socket;
    private String IP = "127.0.0.1";

    public UdpClient(String thisName, String anotherName) {
        this.thisName = thisName;
        this.anotherName = anotherName;
        thisPORT = getPort(thisName) * 100 + getPort(anotherName);
        anotherPORT = getPort(anotherName) * 100 + getPort(thisName);
        privateTalk = new ChatRoom(thisName, anotherName);
        init();

    }

    private void init() {
        try {
            socket = new DatagramSocket(thisPORT);
            outPacket = new DatagramPacket(new byte[0], 0,
                    InetAddress.getByName(IP), 40000);

            privateTalk.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    close();
                    //JOptionPane.showMessageDialog(privateTalk, "socket已关闭");
                }
            });

            privateTalk.setJButton().addActionListener(e -> {
                String line = anotherPORT + privateTalk.getText();
                privateTalk.showOnTextArea(thisName
                        + "(" + NowTime.getTime() + "):\n"
                        + privateTalk.getText() + "\n");
                privateTalk.setNull();
                outBuff = line.getBytes();
                outPacket.setData(outBuff);
                try {
                    socket.send(outPacket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            new UdpClientThread(socket, privateTalk, anotherName).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(privateTalk, "请勿重复点击");
        }
    }


    public void close() {
        socket.close();
    }

    private int getPort(String name) {
        if (name.equals("一无所有")) {
            return 91;
        } else if (name.equals("二氧化硫")) {
            return 92;
        } else if (name.equals("三心二意")) {
            return 93;
        }
        return 0;
    }

    public static void main(String[] args) {
        new UdpClient("一无所有", "二氧化硫");
        new UdpClient("二氧化硫", "一无所有");
    }
}
