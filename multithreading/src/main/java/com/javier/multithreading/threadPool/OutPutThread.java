package com.javier.multithreading.threadPool;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/11/23 13:31
 * @Version 1.0
 */
public class OutPutThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(System.currentTimeMillis()+" run "+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
