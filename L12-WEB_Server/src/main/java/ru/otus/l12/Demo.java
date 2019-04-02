package ru.otus.l12;

import ru.otus.l12.server.MyServer;

public class Demo {

    public static void main(String[] args) {
        try {
            MyServer server = new MyServer(3800,"/static" );
            server.start();

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
