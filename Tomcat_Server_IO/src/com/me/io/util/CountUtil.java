package com.me.io.util;

import com.me.io.core.AbstractEndpoint;
import com.me.io.core.JIoEndpoint;
import com.me.io.launch.Config;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by young on 2017/4/22.
 */
public class CountUtil {
    private static AtomicLong pv = new AtomicLong();
    private static AtomicLong accept = new AtomicLong();
    private static volatile long connections = 0;
    private static volatile int activeThread = 0;
    private static volatile int waitTask = 0;

    private static AbstractEndpoint server;

    static {
        Thread log = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) server.getExecutor();
                        CountUtil.activeThreadNums(threadPoolExecutor.getActiveCount());
                        CountUtil.waitTaskNums(threadPoolExecutor.getQueue().size());
                        connections = server.getConnectionCount();

                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    print(false);
                }
            }
        }, "count thread");
        log.setDaemon(true);

        log.start();

    }


    public static long addaccept(long num) {
        return accept.addAndGet(num);

    }

    public static long addPV(int num) {
        return pv.addAndGet(num);
    }

    public static void activeThreadNums(int num) {
        activeThread = num;
    }

    public static void waitTaskNums(int num) {
        waitTask = num;
    }


    private static PrintStream systemOut = System.out;
    private static PrintStream fileOut = FileUtil.getPrintStream("D:\\Coder\\workspace\\Tomcat-IO\\ServerPerformances.txt", true);

    public static void print(boolean useSystemOut) {
        if (useSystemOut) {
            print(systemOut);
        } else {
            print(fileOut);
        }
    }

    private static boolean printHader = true;

    private static void printHader() {
        System.out.println();
        System.out.println();
        System.out.println("-------------------" + new Date() + "-----------------");
        System.out.println("----------------------config--------------------");
        System.out.println(Arrays.toString(Config.values()));
        System.out.println("----------------------config--------------------");
        printHader=false;
    }

    private static void print() {
        if (printHader) {
            printHader();
        }
        System.out.println("pv: " + pv.get() + " accepted:" + accept.get() + " current connections:" + connections + " activeThreadNums:" + activeThread + " waitTaskNums:" + waitTask);
    }

    private static void print(PrintStream out) {
        System.setOut(out);
        print();
    }


    public static void setServer(AbstractEndpoint server) {
        CountUtil.server = server;
    }
}
