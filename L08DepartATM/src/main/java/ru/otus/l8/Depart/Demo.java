package ru.otus.l8.Depart;

import ru.otus.l7.ATM.ATM.MyATM;
import ru.otus.l7.ATM.interfaces.ATM;
import ru.otus.l8.Depart.interfaces.DepartATM;

public class Demo {
    public static void main(String ...arg){
        DepartATM depart = new MyDepart();
        ATM atmA = new MyATM();
        ATM atmB = new MyATM();
        ATM atmC = new MyATM();

        depart.addATM( atmA );
        depart.addATM( atmB );
        depart.addATM( atmC );

        System.out.println( "Get rest money of all ATM " );
         int amount = depart.getRestMoneyATMs();
         System.out.println( "Amount = " + amount );
         System.out.println( "Set original statement" );
         depart.setOriginalyStatementATM();


    }
}
