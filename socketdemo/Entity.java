package com.fang.payment.paychannel.cmbpay.websocket;

import java.net.Socket;

/**
 * Created by zsq on 2018/7/3.
 */
public class Entity{
    private String name;
    private Socket socket;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}