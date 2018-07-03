package com.fang.payment.paychannel.cmbpay.websocket;

/**
 * Created by zsq on 2018/7/3.
 */
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static Socket socket = null;
    private static ServerSocket serverSocket = null;
    private static List<Socket> socketList = new ArrayList<Socket>();
    private static PrintStream ps = null;
    private static String name;


    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(9527);
            System.out.println("服务开启,等待客户端连接...");
            while(true){
                socket = serverSocket.accept();
                for (int i = 0; i < socketList.size(); i++) {
                    if (socket != socketList.get(i)) {
                        try {
                            ps = new PrintStream(socketList.get(i).getOutputStream());
                            ps.println("IP地址为" + socket.getInetAddress().getHostAddress() + "的用户加入聊天");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                PrintStream ps = new PrintStream(socket.getOutputStream());
                ps.println("欢迎来到聊天室,在线人数" + (socketList.size()+1) + ",请设置你的聊天昵称:");
                socketList.add(socket);
                System.out.println("IP地址为" + socket.getInetAddress().getHostAddress() + "的用户连接成功");
                ServerInputThread st = new ServerInputThread(socketList,socket);
                st.start();
            }
        } catch (IOException e) {
            System.out.println("--------------系统消息:连接意外关闭--------------");
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("--------------系统消息:服务器关闭失败--------------");
        }
    }
}
