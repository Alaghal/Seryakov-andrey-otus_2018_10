package ru.otus.l4.framework.test;

import ru.otus.l4.framework.mytestfraemwork.MyTestFraemwork;

public class TestRunner {

    public static void main(String[] args) {
        MyTestFraemwork tester = new MyTestFraemwork();
        tester.run(TestClass.class);
    }

}
