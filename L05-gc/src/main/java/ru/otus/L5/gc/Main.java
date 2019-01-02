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

    static int countGCMinor =0;
    static int countGCMajor =0;
    static long timeStartGCOperations=0;
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String... args) throws Exception {
        try {
            System.setErr(new PrintStream(new File("log\\logSerial.txt")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       logger.info("MainER");

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
            Thread.sleep(1500);
/*            for (int i = countetSizeList - 5000; i > 5000; i--) {
                listInt.remove(i);
           }*/

            countetSizeList += 1000;
            counterTurn += 1;
            System.out.println("time of turn:" + (System.currentTimeMillis() - beginTime) / 1000);
            switchOnMonitoring();
        }


    }

    private static void switchOnMonitoring() {

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

                      if(startTime!=timeStartGCOperations){
                          if(gcAction.equals("end of minor GC")){
                              countGCMinor +=1;
                          }else if(gcAction.equals("end of major GC")) {
                                    countGCMajor += 1;
                                }
                          logger.info("Minor acting GC  " + countGCMinor + " Time this acting GC: "+duration+" ms");
                          logger.info("Major acting GC  " + countGCMajor + " Time this acting GC: "+duration+" ms");
                      }

                    timeStartGCOperations =startTime;
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }

}
