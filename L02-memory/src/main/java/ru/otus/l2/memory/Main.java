package ru.otus.l2.memory;


import java.lang.management.ManagementFactory;


public class Main {

  // static long mem;
    public static void main(String... args) throws InterruptedException {


        System.out.println("int:" + MeasuringSize.measureInt());
        System.out.println("long:" + MeasuringSize.measureLong());
        System.out.println("double: " + MeasuringSize.measureDouble());
        System.out.println("byte: " + MeasuringSize.measureByte());

        System.out.println("String char :" + MeasuringSize.measure(() -> new String(new char[0])));
        System.out.println("String byte :" + MeasuringSize.measure(() -> new String(new byte[0])));
        System.out.println("String is empty: " + MeasuringSize.measure(null));

    }










}
