package ru.otus.l4.Framework;

import ru.otus.l4.Framework.MyTestFraemwork.MyTestFraemWork;

public class TestRunner {

    public static void main(String[] args) {
        MyTestFraemWork tester = new MyTestFraemWork();
        tester.Run(TestClass.class);
    }

}
