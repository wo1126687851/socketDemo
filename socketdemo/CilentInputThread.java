package com.fang.payment.paychannel.cmbpay.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by zsq on 2018/7/3.
 */
public class CilentInputThread extends Thread{

    BufferedReader br = null;
    Socket socket = null;
    String content;

    public CilentInputThread(Socket socket) throws IOException {
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        super.run();
        while(null != (content = readFromServer())){
            System.out.println(content);
        }
    }

    public String readFromServer(){
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("服务器已关闭");
        }
        return null;
    }

}
