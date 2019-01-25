package ru.otus.l7.ATM.cassettes;

public abstract class Cassette {
    private int countBanknotesCassette;
    private Cassette next;
    private Cassette previous;

    public void getMoney(int countMoney) {
        int restOfMoney = countMoney;

        if(haveBanknotes()){
            getBanknoteThisNomenal (countMoney);
            restOfMoney = getRestOfMoneyThisNomenal(countMoney);
        }

        if (getNext() != null) {
            getNext().getMoney(restOfMoney);
        }
    }

    private int getBanknoteThisNomenal ( int countMoney){
        int amountBanknotes = countMoney / getNomenal();
        if(amountBanknotes > 0 && countBanknotesCassette >=amountBanknotes){
            countBanknotesCassette = countBanknotesCassette - amountBanknotes;
            System.out.println(" it was taken "+ amountBanknotes+ " banknotes of "+getNomenal()+" р.");
            countMoney = countMoney - amountBanknotes * getNomenal();
        }
        return countMoney;
    }

    private int getRestOfMoneyThisNomenal(int countMoney){
        return  countMoney%getNomenal();
    }

    public Cassette getNext() {
        return next;
    }
    public void setNext(Cassette next) {
        this.next = next;
    }

    public Cassette getPrevious() {return previous;}
    public void setPrevious(Cassette previous) {this.previous = previous; }

    abstract public int getNomenal();

    protected void setSizeCassette(int sizeCassette){
        countBanknotesCassette = sizeCassette;
    }

    private boolean haveBanknotes(){
        return countBanknotesCassette >0;
    }

    public int getCountBanknotesCassette() {
        return countBanknotesCassette;
    }



}
