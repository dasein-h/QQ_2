package client;

import server.MyServer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class TcpClient {
    private static final int SERVER_PORT = 30000;
    private Socket socket;
    private PrintStream ps;
    private BufferedReader brServer;
    private String line = null;




    public TcpClient(String name) {
        ChatRoom groupTalk = new ChatRoom(name);
        //groupTalk.jTextArea.append("\n"+name+"进入群聊\n");
        try {

            socket = new Socket("127.0.0.1", SERVER_PORT);
            ps = new PrintStream(socket.getOutputStream());
            brServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            MyServer.showOnJa2("(new)" + name + "...已上线\n");
            ps.println("&" + name + "&");

            groupTalk.setActionListener(ps);

            groupTalk.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    closeRs();
                }
            });
//            groupTalk.jButton.addActionListener(e -> {
//                String line = groupTalk.jTextField.getText();
//                ps.println(line);
//                groupTalk.jTextField.setText("");
//            });
        } catch (Exception ex) {
            closeRs();
//            ex.printStackTrace();
        }

        new TcpClientThread(brServer, groupTalk).start();
    }

    public void closeRs() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (brServer != null) {
                brServer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
