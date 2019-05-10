/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: ThreadUtil.java
 * Author: 邱刘军
 * Date: 2014年11月18日上午11:18:32
 * History： 
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** 
 * 线程管理工具类
 * 
 * @author 邱刘军
 * @date 2014年11月18日
 * @version 1.0
 */
public class ThreadUtil {

    /**
     * 线程执行器（最大线程数2000个）
     */
    private static final ThreadPoolExecutor threadExecutor = new ThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors() * 5, 
        Runtime.getRuntime().availableProcessors() * 10, 
        3000L, 
        TimeUnit.MILLISECONDS, 
        new ArrayBlockingQueue<Runnable>(5000)
    );
    
    /**
     * 执行一个线程
     * 
     * @param task 线程对象
     */
    public static void execute(Runnable task){
        try {
            threadExecutor.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 执行一个线程
     * 
     * @param task 线程对象
     * @return
     */
    public static Object submit(Callable<?> task){
        try {
            Future<?> future = threadExecutor.submit(task);
            if(future != null){
                return future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
