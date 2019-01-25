package ru.otus.l7.ATM.cassettes;

public class ThousandsCassette extends Cassette {

    public ThousandsCassette(int sizeCassette){
        setSizeCassette(sizeCassette);
    }

    @Override
    public int getNomenal() {
        return 1000;
    }
}
