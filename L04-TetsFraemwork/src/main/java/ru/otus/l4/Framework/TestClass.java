package ru.otus.l4.Framework;

import ru.otus.l4.Framework.MyTestFraemwork.After;
import ru.otus.l4.Framework.MyTestFraemwork.Before;
import ru.otus.l4.Framework.MyTestFraemwork.Test;

public class TestClass {


    @After
    public  void Hello(){
        System.out.println("Hello");
    }

    @Test
     public  void Sum(int a, int b){
         System.out.println(a+b);
     }

     @Test
     public void GetName (){
         System.out.println("GetName");
     }

     @Test
     public  void  GetData (){
         System.out.println("GetData");
     }

     @Test
     public  void GetPuls(){
         System.out.println("GetPuls");
     }

     @Before
     public void WriteMessage (){
         System.out.println("GetPuls");
     }
}
