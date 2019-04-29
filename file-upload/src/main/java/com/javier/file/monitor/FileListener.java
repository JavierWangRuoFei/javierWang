package com.javier.file.monitor;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.TimeUnit;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2018/11/23 16:13
 * @Version 1.0
 */
public class FileListener extends FileAlterationListenerAdaptor {
    public static final Logger logger = LoggerFactory.getLogger(FileListener.class);

    @Override
    public void onFileCreate(File file) {
        //文件创建执行
        super.onFileCreate(file);
        logger.info("[新建]:" + file.getAbsolutePath());
    }

    @Override
    public void onFileChange(File file) {
        super.onFileChange(file);
        logger.info("[修改]:" + file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file) {
        //文件删除
        super.onFileDelete(file);
        logger.info("[删除]:" + file.getAbsolutePath());
    }

    @Override
    public void onDirectoryCreate(File directory) {
        //目录创建
        super.onDirectoryCreate(directory);
        logger.info("[新建]" + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File directory) {
        //目录修改
        super.onDirectoryChange(directory);
        logger.info("[修改]:" + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        //目录删除
        super.onDirectoryDelete(directory);
        logger.info("[删除]：" + directory.getAbsolutePath());
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }

    public static void main(String[] args) {
        //监控目录
        String rootDir = "E:\\opt";
        //轮询间隔5秒
        long interval = TimeUnit.SECONDS.toMillis(1);
        //创建过滤器
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".txt"));
        IOFileFilter filter = FileFilterUtils.or(directories,files);
        //使用过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir), filter);
        //不使用过滤器
        FileAlterationObserver observer1 = new FileAlterationObserver(new File(rootDir));
        observer.addListener(new FileListener());
        //创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        //开始监听
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
