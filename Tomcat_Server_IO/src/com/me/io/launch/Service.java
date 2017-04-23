package com.me.io.launch;

import com.me.io.util.CountUtil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by young on 2017/4/22.
 */
public class Service extends AbstractServiceHandler{
    private int delay;

    public Service(int delay) {
        this.delay = delay;
    }

    @Override
    public String doService(String request) {
        String response="bio response for "+request;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
