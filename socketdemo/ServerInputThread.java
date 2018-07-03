package com.fang.payment.paychannel.cmbpay.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by zsq on 2018/7/3.
 */
public class ServerInputThread extends Thread {

    private List<Socket> sockets;
    private Socket socket;
    private BufferedReader br = null;
    private BufferedReader br_name = null;
    private String content;
    private PrintStream ps = null;
    private String name;
    private String temp;

    public ServerInputThread(List<Socket> sockets, Socket socket)
            throws IOException {
        this.sockets = sockets;
        this.socket = socket;
        // this.name = name;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        br_name = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        ps = new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        super.run();

        // 服务器循环等待客户端发来消息
        while ((content = readFromCilent()) != null) {
            if (name == null) {
                if ((name = readFromCilent()) != null) {
                    System.out.println("IP为"
                            + socket.getInetAddress().getHostAddress()
                            + "的用户将名称设置为" + name);
                    continue;
                }
            }
            if (null != this.name){
                temp = name + " 说:      " + content;
                System.out.println(name + " 说:      " + content);
            }
            else{
                temp = socket.getInetAddress().getHostAddress()
                        + " 说:      " + content;
                System.out.println(socket.getInetAddress().getHostAddress()
                        + " 说:      " + content);
            }

            //广播消息
            for (int i = 0; i < sockets.size(); i++) {
                if (socket != sockets.get(i)) {
                    try {
                        ps = new PrintStream(sockets.get(i).getOutputStream());
                        ps.println(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String readFromCilent() {
        try {
            return br.readLine();
        } catch (Exception e) {
            System.out.println("IP地址为"+socket.getInetAddress().getHostAddress()+"的客户退出聊天");
            sockets.remove(socket);
        }
        return null;
    }
}
