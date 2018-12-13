package ru.otus.l3.Collection;

import java.lang.management.ManagementFactory;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
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

        long size = 0;
        long mem1 = getMem();
        try {
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
        }
        catch (Exception e){

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

    private static final int COPY_THRESHOLD           =   10;

    public  <T> void copy (List<? super  T> dest, List<? extends  T> src){
        int srcSize = src.size();
        if (srcSize > dest.size())
            throw new IndexOutOfBoundsException("Source does not fit in dest");

        if (srcSize < COPY_THRESHOLD ||
                (src instanceof RandomAccess && dest instanceof RandomAccess)) {
            for (int i=0; i<srcSize; i++)
                dest.set(i, src.get(i));
        } else {
            ListIterator<? super T> di=dest.listIterator();
            ListIterator<? extends T> si=src.listIterator();
            for (int i=0; i<srcSize; i++) {
                di.next();
                di.set(si.next());
            }
        }
    }

    public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);
    }
}