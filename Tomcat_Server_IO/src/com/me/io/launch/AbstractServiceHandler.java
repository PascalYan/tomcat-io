package com.me.io.launch;

import com.me.io.core.AbstractEndpoint;
import com.me.io.core.JIoEndpoint;
import com.me.io.core.SocketStatus;
import com.me.io.core.SocketWrapper;
import com.me.io.util.CountUtil;

import java.io.*;
import java.net.Socket;

/**
 * Created by young on 2017/4/22.
 */
abstract class AbstractServiceHandler implements JIoEndpoint.Handler {


    @Override
    public SocketState process(SocketWrapper<Socket> socket, SocketStatus status) {
        String request = "";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getSocket().getInputStream()));
            String line;
            while (!(line = reader.readLine()).equals("end")) {
                request += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        beforeService(request);
        //模拟业务方法
        String response = doService(request);

        afterService(request, response);


        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getSocket().getOutputStream(), true);
            writer.println("OK_" + response+" from client:"+socket.getSocket().getRemoteSocketAddress());
            writer.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                writer.close();
            }
        }

        return SocketState.CLOSED;
    }

    @Override
    public void beforeHandshake(SocketWrapper<Socket> socket) {

    }

    @Override
    public Object getGlobal() {
        return null;
    }

    @Override
    public void recycle() {

    }

    public void beforeService(String request) {
//        System.out.println("recv request: " + request);
    }

    public abstract String doService(String request);

    public void afterService(String request, String response) {
        CountUtil.addPV(1);
    }
}
