package ru.otus.L5.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    static int countGCMinor = 0;
    static int countGCMajor = 0;
    static long summaryDuringMinor = 0;
    static long summaryDuringMajor = 0;
    static long  beginTime;
    static  long timeLastLogerWrite;

    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String... args) throws Exception {
        try {
            System.setErr(new PrintStream(new File("log\\logG1.txt")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
         beginTime = System.currentTimeMillis();
        switchOnMonitoring();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);
        mbean.setSize(100_000);
        mbean.run();
        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);

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
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    if (gcAction.equals("end of minor GC")) {
                        countGCMinor += 1;
                        summaryDuringMinor += duration;

                    } else if (gcAction.equals("end of major GC")) {
                        countGCMajor += 1;
                        summaryDuringMajor += duration;
                    }

                    long currentTime= ((System.currentTimeMillis() - beginTime) / 1000) ;
                    if ((timeLastLogerWrite - currentTime) < -60) {
                        String logStr = " GC Name " + gcName + "\n" +
                                " Start GC in program: " + startTime / 1000 + " s." + "\n" +
                                " Minor acting GC  " + countGCMinor + " Time this acting GC: " + summaryDuringMinor + " ms" + "\n" +
                                " Major acting GC  " + countGCMajor + " Time this acting GC: " + summaryDuringMajor + " ms" + "\n" +
                                " Time this acting GC for minute:  " + startTime + " ms" + "\n";
                        logger.info(logStr);
                    timeLastLogerWrite = currentTime;
                  }
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }

}
