package ru.otus.l2.memory;

import java.lang.management.ManagementFactory;
import java.util.function.Supplier;

public class MeasuringSize {

    private final static int ARRAY_SIZE = 20_000_000;

    static long measureInt() {
        long mem1 = getMem();
        int[] array = new int[ARRAY_SIZE];
        System.out.println("after array:" + getMem());

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("int size: " + size + " len:" + array.length);
        return size;
    }


    static long measureLong() {
        long mem1 = getMem();
        long[] array = new long[ARRAY_SIZE];
        System.out.println("after array:" + getMem());

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("Long size: " + size + " len:" + array.length);
        return size;
    }

    static  long measureDouble() {
        long mem1 = getMem();
        double[] array = new double[ARRAY_SIZE];
        System.out.println("after array:" + getMem());

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("double size: " + size + " len:" + array.length);
        return size;

    }

    static  long measureByte() {
        long mem1 = getMem();
        byte[] array = new byte[ARRAY_SIZE];
        System.out.println("after array:" + getMem());

        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) i;
        }

        long size = (getMem() - mem1) / array.length;
        System.out.println("Byte size: " + size + " len:" + array.length);
        return size;

    }

    static <T> long measure(Supplier<T> objectGetter) {
        Object[] array = new Object[ARRAY_SIZE];
        long size;
        long mem1 = getMem();
        if (objectGetter == null) {
            size = (getMem() - mem1) / array.length;
            System.out.println("Element size: " + size + " len:" + array.length);
        } else {
            for (int i = 0; i < array.length; i++) {
                array[i] = objectGetter.get();
            }

            size = (getMem() - mem1) / array.length;
            System.out.println("Element size: " + size + " len:" + array.length);
        }
        return size;
    }

    private static long getMem() {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}