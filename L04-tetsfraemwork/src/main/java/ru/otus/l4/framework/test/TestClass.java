package ru.otus.l4.framework.test;

import ru.otus.l4.framework.mytestfraemwork.annotations.After;
import ru.otus.l4.framework.mytestfraemwork.annotations.Before;
import ru.otus.l4.framework.mytestfraemwork.annotations.Test;

public class TestClass {

    @Before
    public void hello() {
        System.out.println("hello");
    }

    @Test
    public void sum() {
        System.out.println("Summ");
    }

    @Test
    public void getName() {
        System.out.println("getName");
    }

    @Test
    public void getData() {
        System.out.println("getData");
    }

    @Test
    public void getPuls() {
        System.out.println("GetTest");
    }

    @After
    public void writeMessage() {
        System.out.println("getPuls");
    }
}
