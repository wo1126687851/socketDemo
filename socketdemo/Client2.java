package com.fang.payment.paychannel.cmbpay.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by zsq on 2018/7/3.
 */
public class Client2 {

    private static Socket socket = null;
    private static String s = null;
    private static String content = null;
    private static PrintStream ps = null;

    public static void main(String[] args) {
        try {
            socket = new Socket("10.2.131.167",9527);
            BufferedReader info = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(info.readLine());

            //设置昵称
            BufferedReader nakeName = new BufferedReader(new InputStreamReader(System.in));
            ps = new PrintStream(socket.getOutputStream());
            ps.println(nakeName);
            //新建进程 启动进程 接收消息
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            CilentInputThread cit = new CilentInputThread(socket);
            cit.start();
            //客户端发送消息
            while(null != (content = br.readLine())){
                ps = new PrintStream(socket.getOutputStream());
                ps.println(content);
            }
            socket.shutdownInput();
        } catch (UnknownHostException e) {
            System.out.println("--------------系统消息:未找到对应的服务--------------");
        } catch (IOException e) {
            System.out.println("--------------系统消息:管理员关闭了服务器--------------");
        }
    }
}

