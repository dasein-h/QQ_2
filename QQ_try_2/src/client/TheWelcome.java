package client;

import server.MyServer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*

初始化的登陆界面

创建登陆界面同时会创建服务器界面
使用JDBC去核实用户名和密码是否正确匹配
当匹配成功会利用服务器的方法去创造对应用户的好友列表

 */
public class TheWelcome {
    private JFrame jFrame;
    private JLabel jlb1, jlb2, jlb3, jlb4;
    private JTextField jtf;
    private JPasswordField jp;
    private JButton jb1, jb2;
    private Box horizontal1, vertical1, horizontal2;

    private void init() throws Exception {
        //创建各组件
        jFrame = new JFrame("欢迎登陆");
        jlb1 = new JLabel(new ImageIcon("image/welcome.png"));
        jlb2 = new JLabel(new ImageIcon("image/small.png"));
        jb1 = new JButton("注册");
        jb2 = new JButton("登陆");
        jlb3 = new JLabel("用户名");
        jlb4 = new JLabel("密码");
        jtf = new JTextField();
        jp = new JPasswordField();
        horizontal1 = Box.createHorizontalBox();
        horizontal2 = Box.createHorizontalBox();
        vertical1 = Box.createVerticalBox();

        //用户名行
        horizontal1.add(jlb3);
        horizontal1.add(Box.createHorizontalStrut(10));
        horizontal1.add(jtf);
        horizontal1.add(Box.createHorizontalStrut(10));
        horizontal1.add(jb1);

        //密码行
        horizontal2.add(jlb4);
        horizontal2.add(Box.createHorizontalStrut(23));
        horizontal2.add(jp);
        horizontal2.add(Box.createHorizontalStrut(10));
        horizontal2.add(jb2);

        //用户名和密码列
        vertical1.add(Box.createVerticalStrut(10));
        vertical1.add(horizontal1);
        vertical1.add(Box.createVerticalStrut(10));
        vertical1.add(horizontal2);
        vertical1.add(Box.createGlue());

        //设置窗口
        jFrame.add(jlb1, BorderLayout.NORTH);
        jFrame.add(jlb2, BorderLayout.WEST);
        jFrame.add(vertical1);
        jFrame.setBounds(420, 230, 450, 200);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //JDBC准备
        Class.forName("com.mysql.cj.jdbc.Driver");


        //设置监听
        jb1.addActionListener(e -> {
            JOptionPane.showMessageDialog(jFrame, "无此功能，敬请期待");
        });
        jb2.addActionListener(e -> {
            if (validate(jtf.getText(), jp.getText())) {
                jtf.setText("");
                jp.setText("");
                JOptionPane.showMessageDialog(jFrame, "登陆成功");
            } else {
                JOptionPane.showMessageDialog(jFrame, "登陆失败");
            }
        });
    }

    //使用JDBC验证用户名和密码是否匹配
    private boolean validate(String name, String password) {
        var sql = "select * from qq_entry " +
                "where the_name = '" + name + "' and" +
                " the_password = '" + password + "'";
        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/qq",
                        "root",
                        "123456789");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);) {
            if (rs.next()) {
                int number = rs.getInt(1);
                try {
                    MyServer.createFriendsList(number);
                } catch (Exception e) {
                    //JOptionPane.showMessageDialog(jFrame,"服务器未开启");
                    int h = JOptionPane.showConfirmDialog(jFrame,
                            "用户允许登陆，但服务器未开启，是否开启服务器");
                    if (h == JOptionPane.YES_OPTION) {
                        new MyServer();
                    }
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //创建登陆界面同时创造服务器界面
    public TheWelcome() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(jFrame, "将自动创建服务器");
        new MyServer();
        new NowTime();
    }

    public static void main(String[] args) {
        new TheWelcome();
    }
}

