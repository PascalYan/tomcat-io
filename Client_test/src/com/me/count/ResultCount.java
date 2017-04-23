package com.me.count;

import java.io.PrintStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by young on 2017/4/23.
 */
public class ResultCount extends Thread {
    private static AtomicLong failView = new AtomicLong();
    private static AtomicLong maxOneCostTime = new AtomicLong();
    private static AtomicLong totalCostTime = new AtomicLong();
    private static Set<String> causeSet = new HashSet<>();


    public ResultCount() {
        this.setDaemon(true);
    }

    private static int maxRequests = 20000;
    private static PrintStream out = FileUtil.getPrintStream("D:\\Coder\\workspace\\Tomcat-IO\\ClientPerformances.txt", true);
    private static PrintStream  systemOut=System.out;
    @Override
    public void run() {
        print();
    }

    public static void print(){
        System.setOut(out);
        System.out.println();
        System.out.println();
        System.out.println("-------------------" + new Date() + "-----------------");
        System.out.println("fail view: " + failView.get() + " cause: " + causeSet);
        System.out.println("total cost time: " + totalCostTime.get() + " ms");
        System.out.println("averge time per request " + totalCostTime.get() / (maxRequests - failView.get()) + " ms");
        System.out.println("max cost time in one request: " + maxOneCostTime.get() + " ms");
        System.setOut(systemOut);
    }

    public static long addfailView(long nums, String cause) {
        causeSet.add(cause);
        return failView.addAndGet(nums);
    }

    public static long compareAndUpdateMaxOneCost(long time) {
        long old = maxOneCostTime.get();
        if (time > old) {
            if(!maxOneCostTime.compareAndSet(old, time)){
                compareAndUpdateMaxOneCost(time);
            }
        }
        return maxOneCostTime.get();
    }


    public static void oneRequestCostTime(long cost){
        compareAndUpdateMaxOneCost(cost);
        addTotalCostTime(cost);

    }
    public static long addTotalCostTime(long cost){
        return totalCostTime.addAndGet(cost);
    }

    public static void reStart() {
        failView.set(0);
        maxOneCostTime.set(0);

        causeSet.clear();

    }

}
