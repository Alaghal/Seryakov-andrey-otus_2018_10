package ru.otus.L5.gc;

import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {
    private int size;


    public Benchmark() {

    }

    void run() throws InterruptedException {
        int local = size;
        List<Object> listObj = new ArrayList<Object>();

        while(true) {
            for (int i = 0; i < local; i++) {
                listObj.add(i);
            }

            for(int i = local-1000; i > local-2000; i--){
                listObj.remove(i);
            }

            local+=50_000;
        }
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }
}
