package org.nil1one;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args)  {
//        new JDWindowProcessor("100020942953").process();
        new JDWindowProcessor("100012043978");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.schedule(()-> System.out.println("........."),1000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.schedule(()-> System.out.println("........."),1000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.schedule(()-> System.out.println("........."),1000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.schedule(()-> System.out.println("........."),1000, TimeUnit.MILLISECONDS);
        System.out.println("-----------");
    }
}
