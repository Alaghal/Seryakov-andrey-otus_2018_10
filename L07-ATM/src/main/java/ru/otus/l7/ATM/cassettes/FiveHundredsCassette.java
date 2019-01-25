package ru.otus.l7.ATM.cassettes;

public class FiveHundredsCassette extends Cassette {

     public FiveHundredsCassette(int sizeCassette){
         setSizeCassette(sizeCassette);
     }

    @Override
    public int getNomenal() {
        return 500;
    }


}
