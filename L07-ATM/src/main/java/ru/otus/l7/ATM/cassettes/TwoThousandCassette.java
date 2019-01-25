package ru.otus.l7.ATM.cassettes;

public class TwoThousandCassette extends Cassette {

    public TwoThousandCassette(int sizeCassette){
        setSizeCassette(sizeCassette);
    }
    @Override
    public int getNomenal() {
        return 2000;
    }
}
