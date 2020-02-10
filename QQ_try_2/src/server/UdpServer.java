package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer extends Thread {
    byte[] inBuff = new byte[4096];
    DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);

    String IP = "127.0.0.1";
    DatagramPacket outPacket;

    @Override
    public void run() {
        try (
                var socket_in = new DatagramSocket(40000);
                var socket_out = new DatagramSocket()
        ) {
            socket_in.receive(inPacket);
//                System.out.println(inBuff==inPacket.getData());
//                System.out.println(new String(inBuff));
            String port = new String(inBuff, 0, 4);
            outPacket = new DatagramPacket(new byte[0], 0,
                    InetAddress.getByName(IP), Integer.valueOf(port));
            outPacket.setData(inBuff);
            socket_out.send(outPacket);
            new UdpServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
