package com.me.io.launch;

import com.me.io.core.*;
import com.me.io.util.CountUtil;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

import static com.me.io.launch.Config.*;

/**
 * Created by young on 2017/4/22.
 */
public class SimpleStart {

    private static AbstractEndpoint<Socket> bioServer = new JIoEndpoint();

    private static Service serviceBean = new Service(500);


    public static void main(String[] args) {

        init();

    }


    public static void init() {
        try {
            bioServer.setPort(8888);
            bioServer.setDaemon(false);
            bioServer.setAcceptorThreadCount(ACCEPTORTHREADCOUNT.getValue());
            bioServer.setMaxConnections(MAXCONNECTIONS.getValue());
            bioServer.setMinSpareThreads(MINSPARETHREADS.getValue());
            bioServer.setBacklog(BACKLOG.getValue());
            bioServer.setMaxThreads(MAXTHREADS.getValue());
            ((JIoEndpoint) bioServer).setHandler(serviceBean);
            bioServer.init();
            bioServer.start();
            CountUtil.setServer(bioServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
