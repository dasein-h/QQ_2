package client;

import server.MyServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/*

好友列表

可以创建UDP和TCP的客户端，聊天界面是客户端创建的
含有刷新的方法，配合服务器可以显示在线的用户，限制只能与在线的用户进行私聊

 */
public class TheFriendList {
    private JFrame jFrame;
    private JLabel jLabel, jLabel_name;
    private JPanel jp1, jp2;
    private JButton jb1, jb2, jb3;
    private ArrayList<String> names = new ArrayList<>();
    private int thisPort, number;

    public TheFriendList(int number) {
        this.number = number;
        this.thisPort = number + 30000;
        init(number);
    }


    /*
    好友列表的布局和监听等
     */
    private void init(int number) {
        addNames();

        MyServer.changeStatus(number, names.get(number - 1));

        jLabel = new JLabel(new ImageIcon("image/" + number + ".jpg"));
        jLabel_name = new JLabel(names.get(number - 1));
        names.remove(number - 1);

        jb1 = new JButton(names.get(0));
        jb2 = new JButton(names.get(1));
        jb3 = new JButton("群聊");

        jb1.setBounds(0, 0, 190, 50);
        jb2.setBounds(0, 50, 190, 50);
        jb1.setBackground(Color.GRAY);
        jb2.setBackground(Color.GRAY);

        jp1 = new JPanel();
        jp1.add(jLabel);
        jp1.add(jLabel_name);
        jp2 = new JPanel(null);
        jp2.add(jb1);
        jp2.add(jb2);

        jFrame = new JFrame("好友列表");
        jFrame.add(jp1, BorderLayout.NORTH);
        jFrame.add(jp2);
        jFrame.add(jb3, BorderLayout.SOUTH);
        jFrame.setBounds(1000, 0, 50, 470);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
        clickOnGroupTalk();
        clickOnPrivateTalk(jb1);
        clickOnPrivateTalk(jb2);
        setclose();
    }

    private void addNames() {
        names.add("一无所有");
        names.add("二氧化硫");
        names.add("三心二意");
    }

    /*
    根据JButton来创建监听
    点击后创建UDP的私聊
    通过按钮颜色判断用户在线状态
    在线即可创建UDP的客户端
     */
    private void clickOnPrivateTalk(JButton jButton) {
        jButton.addActionListener(e -> {
            if (jButton.getBackground() == Color.GRAY) {
                JOptionPane.showMessageDialog(jFrame, "抱歉，该用户未上线无法与他聊天");
            } else {
                new UdpClient(jLabel_name.getText(), jButton.getText());
            }
        });
    }

    /*
    群聊按钮的监听
    创建TCP的客户端
     */
    private void clickOnGroupTalk() {
        jb3.addActionListener(e -> {
            //new ChatRoom();
            //JOptionPane.showMessageDialog(jFrame,"进入群聊功能");
            new TcpClient(jLabel_name.getText());
        });
    }

    //给俩私聊用户按钮刷新
    public void repaintIt(String name) {
        if (name.equals("")) {
            repaintEntry(jb1);
            repaintEntry(jb2);
        } else {
            repaintExit(jb1, name);
            repaintExit(jb2, name);
        }
    }

    //通过查看好友是否登录来改变按钮的颜色
    private void repaintEntry(JButton jButton) {
        for (var friendName : MyServer.names.valueSet()) {
            if (jButton.getText().equals(friendName)) {
                jButton.setBackground(null);
            }
        }
    }

    //通过查看好友是否退出来改变按钮颜色
    private void repaintExit(JButton jButton, String name) {
        if (jButton.getText().equals(name)) {
            jButton.setBackground(Color.GRAY);
        }
    }

    //设置关闭的监听
    private void setclose() {
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MyServer.removeFriendsList(number);
            }
        });
    }

    //调试使用
    public static void main(String[] args) {
        new TheFriendList(2);
        new TheFriendList(1);
    }

}
