package client;

import java.io.BufferedReader;
import java.io.IOException;

//tcp的客户端用于读取流显示到界面上
public class TcpClientThread extends Thread {
    BufferedReader br = null;
    ChatRoom groupTalk;

    public TcpClientThread(BufferedReader br, ChatRoom groupTalk) {
        this.br = br;
        this.groupTalk = groupTalk;
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                groupTalk.showOnTextArea("\n" + line);
            }
        } catch (IOException ex) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
