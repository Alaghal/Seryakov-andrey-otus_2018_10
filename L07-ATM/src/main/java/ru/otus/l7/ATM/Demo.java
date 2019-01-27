package ru.otus.l7.ATM;

import ru.otus.l7.ATM.ATM.ControlPanel;
import ru.otus.l7.ATM.ATM.GetCashCommand;
import ru.otus.l7.ATM.ATM.MyATM;

import java.util.Scanner;

public class Demo {
    public static void main(String... args){
        MyATM atm = new MyATM();
        ControlPanel panel = new ControlPanel();
        GetCashCommand getCash = new GetCashCommand(atm);

        panel.setCommand(0,getCash);
        panel.onButtonWasPushed(0);
    }

}
