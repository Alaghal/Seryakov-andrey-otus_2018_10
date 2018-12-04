package ru.otus.l2.memory;


import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;


public class Main {

  // static long mem;
    public static void main(String... args) throws InterruptedException {

        System.out.println("int:" + MeasuringSize.measureInt());
        System.out.println("int[0] :" + MeasuringSize.measure(() -> new int[0]));
        System.out.println("int[10] :" + MeasuringSize.measure(() -> new int[10]));

        System.out.println("long:" + MeasuringSize.measureLong());
        System.out.println("long[0] :" + MeasuringSize.measure(() -> new long[0]));
        System.out.println("long[10] :" + MeasuringSize.measure(() -> new long[10]));

        System.out.println("double: " + MeasuringSize.measureDouble());
        System.out.println("double[0] :" + MeasuringSize.measure(() -> new double[0]));
        System.out.println("double[10] :" + MeasuringSize.measure(() -> new double[10]));

        System.out.println("byte: " + MeasuringSize.measureByte());
        System.out.println("byte[0] :" + MeasuringSize.measure(() -> new byte[0]));
        System.out.println("byte[10] :" + MeasuringSize.measure(() -> new byte[10]));


        System.out.println("String char :" + MeasuringSize.measure(() -> new String(new char[0])));
        System.out.println("String byte :" + MeasuringSize.measure(() -> new String(new byte[0])));

        System.out.println("String is empty: " + MeasuringSize.measure(null));

        System.out.println("Collection 2 element: " + MeasuringSize.measure(()->{
           List<Integer> ls = new ArrayList<Integer>();
            ls.add(15);
            ls.add(12);

            return ls; }));

        System.out.println("Collection 0 element: " + MeasuringSize.measure(()->{
            List<Integer> ls = new ArrayList<Integer>();

            return ls; }));

    }










}
