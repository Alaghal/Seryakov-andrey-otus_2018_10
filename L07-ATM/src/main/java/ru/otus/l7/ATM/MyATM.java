package ru.otus.l7.ATM;

import java.util.Scanner;
import ru.otus.l7.ATM.cassettes.*;
import ru.otus.l7.ATM.interfaces.ATM;

public class MyATM  implements ATM {
    private  boolean statusATM;
   private Cassette firstCassette;
   private Cassette secondCassette;
   private Cassette thirdCassette;
   private Cassette fourthCassette;

    @Override
    public void StartATM() {
        Scanner in = new Scanner(System.in);

        WellcomeMessage();
        InitCassette();

        while (statusATM) {
            Cassette cassetteWithMineBanknote = checkMinBanknoteForAmount(fourthCassette);

            if (cassetteWithMineBanknote != null){
                System.out.println("Please enter the required amount");
                int amount = in.nextInt();
                if (amount > 0 && checkForMultiplicity(amount,cassetteWithMineBanknote)){
                    firstCassette.getMoney(amount);
                }
            } else StopATM();
        }

    }

    @Override
    public void StopATM() {
        statusATM = false;
    }

    private void  WellcomeMessage(){
        System.out.println("Start ATM");
    }

    private void InitCassette(){
         firstCassette = new TwoThousandCassette(50);
         secondCassette = new ThousandsCassette(50);
         thirdCassette = new FiveHundredsCassette(50);
         fourthCassette = new HundredsCassete(50);

         firstCassette.setNext(secondCassette);
         secondCassette.setPrevious(firstCassette);
         secondCassette.setNext(thirdCassette);
         thirdCassette.setPrevious(secondCassette);
         thirdCassette.setNext(fourthCassette);
         fourthCassette.setPrevious(thirdCassette);

         statusATM=true;
    }
    private Cassette checkMinBanknoteForAmount(Cassette lastCassette){
        if(lastCassette.getCountBanknotesCassette()>0){
            return lastCassette;
        } else if(lastCassette.getPrevious() != null){
            return checkMinBanknoteForAmount(lastCassette.getPrevious());
        }
      System.out.println("sorry no money in ATM");
        return  null;
    }

    private boolean checkForMultiplicity(int amount, Cassette lastCassette){
            int banknote = lastCassette.getNomenal();
            int result = amount % banknote;
            if(result==0){
                return true;
            }
            System.out.println("sorry this amount cannot be issued. " +
                    "At the moment, the minimum banknote "+lastCassette.getNomenal()+" р.");

            return false;
    }

}
