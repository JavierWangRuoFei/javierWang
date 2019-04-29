package javier;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/22 17:40
 * @Version 1.0
 */
public class ThreadApp extends Thread {
    private Queue<String> priorityQueue = new PriorityQueue<>();
    private String value;
    public ThreadApp(String value){
        this.value = value;
    }
    public void test1(){
        priorityQueue.add(value);
    }

    @Override
    public void run() {
        while (true){
            String threadName = Thread.currentThread().getName();
            test1();
            for (String s : priorityQueue){
                System.out.println(s);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
