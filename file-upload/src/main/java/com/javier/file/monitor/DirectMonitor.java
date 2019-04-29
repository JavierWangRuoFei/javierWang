package com.javier.file.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/11/22 20:49
 * @Version 1.0
 */
public class DirectMonitor extends Thread {
     public String fileDirectory;
     public static final Logger logger = LoggerFactory.getLogger(DirectMonitor.class);
     public DirectMonitor(String fileDirectory){
         this.fileDirectory = fileDirectory;
     }

    @Override
    public void run() {
        WatchService service = null;
        try {
            //获取当前文件系统的监控对象
            service = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //获取文件目录下的Path对象注册到 watchService中。
            //监听的事件类型，有创建，删除，以及修改
            Paths.get(fileDirectory).register(service, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);

        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            WatchKey key = null;
            try {
                //获取可用key.没有可用的就wait
                key = service.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (WatchEvent<?> event : key.pollEvents() ){
                // TODO
                logger.info(event.context()+"文件:"+event.kind()+"次数"+ event.count());
            }
            //重置，这一步很重要，否则当前的key就不再会获取将来发生的事件
            boolean valid = key.reset();
            //失效状态，退出监听
            if (!valid){
                break;
            }
        }
    }

    public static void main(String[] args) {
        //要监控的文件目录
        String propFileName = "E:\\opt";
        DirectMonitor directMonitor = new DirectMonitor(propFileName);
        //因为是线程安全的所以可以放入ThreadPool中使用
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,6,5, TimeUnit.SECONDS,new SynchronousQueue<>());
        executor.execute(directMonitor);
    }
}
