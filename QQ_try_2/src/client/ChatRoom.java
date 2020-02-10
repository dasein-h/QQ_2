package client;

import server.MyServer;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/*
聊天窗口
 */
public class ChatRoom extends JFrame {
    private JPanel jp1, jp2;
    private JScrollPane js;
    private JTextField jTextField;
    private JTextArea jTextArea;
    private JButton jButton;
    private String line;

    private void init() {
        jp1 = new JPanel(null);
        jp2 = new JPanel();
        jButton = new JButton("发送");
        jTextField = new JTextField(50);
        jTextArea = new JTextArea(5, TextArea.SCROLLBARS_BOTH);
        jTextArea.setEditable(false);
        js = new JScrollPane(jTextArea);
        js.setBounds(0, 0, 586, 320);
        jp1.add(js);
        jp1.setBounds(0, 0, 586, 320);
        jp2.add(jTextField);
        jp2.add(jButton);
        jp2.setBounds(0, 325, 586, 35);


        this.setLayout(null);
        this.add(jp1);
        this.add(jp2);
        this.setBounds(420, 100, 600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);


    }

    //创建私聊界面的构造方法
    public ChatRoom(String myName, String yourName) {
        this.setTitle(myName + "——" + "你正在和 " + yourName + " 聊天");
        init();
    }

    //创建群聊界面的构造方法
    public ChatRoom(String name) {
        this.setTitle(name + "——群聊界面");
        init();
    }

    //在jTextArea中显示消息
    public void showOnTextArea(String string) {
        this.jTextArea.append(string);
    }

    //给群聊“发送”按钮设置监听
    public void setActionListener(PrintStream ps) {
        jButton.addActionListener(e -> {
            line = getText();
            ps.println(line);
            setNull();
        });
    }

    //得到jbutton部件
    public JButton setJButton() {
        return jButton;
    }

    //清空搜索栏
    public void setNull() {
        jTextField.setText("");
    }

    //从输入栏获取String
    public String getText() {
        return jTextField.getText();
    }

    public static void main(String[] args) {
        new ChatRoom("何科伟");
    }
}
