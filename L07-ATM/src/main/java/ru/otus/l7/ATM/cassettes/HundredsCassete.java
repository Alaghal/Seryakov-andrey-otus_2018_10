package ru.otus.l7.ATM.cassettes;

public class HundredsCassete extends Cassette {

   public HundredsCassete (int sizeCassette){
      setSizeCassette(sizeCassette);
   }

    @Override
    public int getNomenal() {
        return 100;
    }
}
