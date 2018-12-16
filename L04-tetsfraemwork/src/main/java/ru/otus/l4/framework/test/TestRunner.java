package ru.otus.l4.framework.test;

import ru.otus.l4.framework.mytestframework.MyTestFramework;

public class TestRunner {

    public static void main(String[] args) {
        MyTestFramework tester = new MyTestFramework(TestClass.class);
        tester.run();
    }

}
