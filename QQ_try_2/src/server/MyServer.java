package server;

import client.TheFriendList;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

/*

服务器界面

可以打开UDP的服务端与TCP的服务端
拥有两个界面，分布显示 状态 和 在线情况
内含UDP和TCP的服务端

 */
public class MyServer {

    public static CrazyitMap<String, PrintStream> clients = new CrazyitMap<>();

    private JFrame jFrame;
    private JMenuBar jMenuBar;
    private JMenu jm1, jm2;
    private static JTextArea ja1, ja2;
    private JMenuItem jm1_1, jm1_2, jm1_3, jm2_1;
    //    public static ArrayList<String> names = new ArrayList<>();
    public static CrazyitMap<Integer, String> names = new CrazyitMap<>();
    private static CrazyitMap<Integer, TheFriendList> friendsList = new CrazyitMap<>();
//    private static ArrayList<TheFriendList> friendLists = new ArrayList<>();

    public MyServer() {
        init();
    }

    //服务器布局
    private void init() {
        jFrame = new JFrame("服务器");
        jMenuBar = new JMenuBar();
        jm1 = new JMenu("状态");
        jm2 = new JMenu("在线情况");
        jm1_1 = new JMenuItem("打开TCP-群聊服务器");
        jm1_2 = new JMenuItem("打开UDP-私聊服务器");
        jm1_3 = new JMenuItem("查看");
        jm2_1 = new JMenuItem("查看");

        ja1 = new JTextArea("Tcp——群聊服务端未打开\n\n" +
                "Udp——私聊服务器未打开\n\n");
        ja2 = new JTextArea("在线状态：\n" +
                "一无所有...不在线\n" +
                "二氧化硫...不在线\n" +
                "三心二意...不在线\n\n\n");
        ja1.setBounds(0, 0, 586, 320);
        ja2.setBounds(0, 0, 586, 320);
        ja1.setEditable(false);
        ja2.setEditable(false);

        jm1.add(jm1_3);
        jm1.addSeparator();
        jm1.add(jm1_1);
        jm1.add(jm1_2);

        jm2.add(jm2_1);

        jMenuBar.add(jm1);
        jMenuBar.add(jm2);
        clickOn();

        jFrame.setLayout(null);
        jFrame.add(ja1);
        jFrame.setJMenuBar(jMenuBar);
        jFrame.setBounds(420, 100, 600, 400);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
    }

    //创建好友列表，同时刷新好友在线状态
    public static void createFriendsList(int number) {
        friendsList.put(number, new TheFriendList(number));
        for (var friend : friendsList.valueSet()) {
            friend.repaintIt("");
        }
//        friendLists.add(new TheFriendList(number));
//        for (var friend : friendLists) {
//            friend.repaintIt();
//        }
    }


    //删除好友列表，同时刷新状态
    public static void removeFriendsList(int number) {
        friendsList.remove(number);
        ja1.append(names.getValueByKey(number) + "——已下线\n\n");
        ja2.append(names.getValueByKey(number) + "...离线\n\n");
        for (var friend : friendsList.valueSet()) {
            friend.repaintIt(names.getValueByKey(number));
        }
    }

    //监听打开TCP服务端和UDP服务端，以及刷新显示界面
    private void clickOn() {
        jm1_1.addActionListener(e -> {
            new TcpServer().start();
        });

        jm1_2.addActionListener(e -> {
            ja1.append("UDP服务器......打开\n\n");
            new UdpServer().start();
        });

        jm1_3.addActionListener(e -> {
            jFrame.remove(ja2);
            jFrame.add(ja1);
            jFrame.repaint();
        });

        jm2_1.addActionListener(e -> {
            jFrame.remove(ja1);
            jFrame.add(ja2);
            jFrame.repaint();
        });
    }

    //服务器显示在线状态
    public static void changeStatus(int number, String name) {
        names.put(number, name);
        ja1.append(name + "——已上线\n\n");
        ja2.append(name + "...在线\n\n");
    }

    public static void showOnJa1(String string) {
        ja1.append(string);
    }

    public static void showOnJa2(String string) {
        ja2.append(string);
    }
    //TCP服务端
//    class TCPServer extends Thread {
//
//        @Override
//        public void run() {
//            try (
//                    ServerSocket ss = new ServerSocket(SERVER_PORT)
//            ) {
//                ja1.append("\nTCP服务器......打开\n");
//                int n = 0;
//                while (true) {
//                    var socket = ss.accept();
//                    ja1.append("连接成功...数量:" + ++n + "\n");
//                    new TcpThread(socket).start();
//                }
//            } catch (IOException ex) {
//                ja1.append("服务器启动失败，是否端口" +
//                        SERVER_PORT + "已被占用？" +
//                        "或者重复打开\n");
//            }
//        }
//    }

    //UDP服务端
//    class UDPServer extends Thread {
//        byte[] inBuff = new byte[4096];
//        DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
//
//        String IP = "127.0.0.1";
//        DatagramPacket outPacket;
//
//        @Override
//        public void run() {
//            try (
//                    var socket_in = new DatagramSocket(40000);
//                    var socket_out = new DatagramSocket()
//            ) {
//                while (true) {
//                    socket_in.receive(inPacket);
//                    ja1.append("UDP服务器收到消息\n");
//                    new UdpThread(inBuff).start();
//                    String port = new String(inBuff, 0, 4);
//                    outPacket = new DatagramPacket(new byte[0], 0,
//                            InetAddress.getByName(IP), Integer.valueOf(port));
//                    outPacket.setData(inBuff);
//                    socket_out.send(outPacket);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    public static void main(String[] args) {
        new MyServer();
    }
}
