package com.me.io.launch;

/**
 * Created by young on 2017/4/22.
 */
public enum Config {

    MINSPARETHREADS("minSpareThreads",10,"最小空闲线程数"),
    MAXTHREADS("maxThreads",1000,"最大线程数"),
    MAXCONNECTIONS("maxConnections",10000,"if we have reached max connections, wait"),
    BACKLOG("backlog",1000,"请求排队数,tcp的完全连接队列"),
    ACCEPTORTHREADCOUNT("acceptorThreadCount",1,"acceptor数，最少为1"),


    ;
    private String key;
    private int value;
    private String msg;

    Config(String key, int value, String msg) {
        this.key = key;
        this.value = value;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return key+"("+msg+")"+"="+value+"\n";

    }
}
