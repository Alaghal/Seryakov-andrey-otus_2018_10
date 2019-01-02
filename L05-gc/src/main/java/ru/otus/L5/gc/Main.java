package ru.otus.L5.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    static int countGC=0;

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        // switchOnMonitoring();
        long beginTime = System.currentTimeMillis();
        int countetSizeList = 10000;
        int counterTurn = 0;
        List<Integer> listInt = new ArrayList<Integer>();


        while (true) {



            for (int i = 0; i < countetSizeList; i++) {
                listInt.add(i);
            }
            for (int i = countetSizeList - 5000; i > 5000; i--) {
                listInt.remove(i);
            }

            countetSizeList += 1000;
            counterTurn += 1;
            System.out.println("time of turn:" + (System.currentTimeMillis() - beginTime) / 1000);
            switchOnMonitoring();
        }


    }

    private static void switchOnMonitoring() {

        try {
            System.setErr(new PrintStream(new File("log\\log.txt")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
// Выводим сообщения
    //    System.err.println("Сообщение 1");
     //   System.err.println("Сообщение 2");

        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause  = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    //loger.info("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    countGC+=1;
                    System.out.println("CountGC  " + countGC);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
