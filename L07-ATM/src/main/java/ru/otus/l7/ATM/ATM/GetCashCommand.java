package ru.otus.l7.ATM.ATM;

import ru.otus.l7.ATM.interfaces.Command;

import java.util.Scanner;

public class GetCashCommand implements Command {
    MyATM atm;
    Scanner in;

    public GetCashCommand(MyATM atm){
        this.atm =atm;
        in = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println("Please enter the required withdrawal amount");
        int amount = in.nextInt();
        atm.getMoney(amount);
    }
}
