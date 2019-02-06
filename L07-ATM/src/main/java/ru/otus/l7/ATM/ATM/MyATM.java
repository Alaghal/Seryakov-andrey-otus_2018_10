package ru.otus.l7.ATM.ATM;

import ru.otus.l7.ATM.Enums.StatusATM;
import ru.otus.l7.ATM.cassettes.*;
import ru.otus.l7.ATM.interfaces.ATM;

import java.util.*;

public class MyATM implements ATM {
    private List<Cassette> listCassette;
    private StatusATM conditionATM;

    public MyATM() {
        listCassette = new ArrayList<>();
        InitCassette();
        conditionATM = StatusATM.Working;
    }

    private void InitCassette() {
        listCassette.add(new TwoThousandCassette(50));
        listCassette.add(new ThousandsCassette(50));
        listCassette.add(new FiveHundredsCassette(50));
        listCassette.add(new HundredsCassete(50));

        listCassette.sort(new CassetteComparator());
        listCassette = makingChainCassette(listCassette);


    }

    private List<Cassette> makingChainCassette(List<Cassette> list) {
        List<Cassette> listCassette = new ArrayList<>();
        Cassette[] arrayCassette = new Cassette[list.size() - 1];
        arrayCassette = list.toArray(arrayCassette);

        Cassette previousCassette = null;
        Cassette nextCassette;

        for (int i = 0; i < arrayCassette.length; i++) {
            if (i + 1 > arrayCassette.length - 1) {
                nextCassette = null;
            } else nextCassette = arrayCassette[i + 1];

            arrayCassette[i].setNext(nextCassette);
            arrayCassette[i].setPrevious(previousCassette);
            previousCassette = arrayCassette[i];
        }
        listCassette.addAll(Arrays.asList(arrayCassette));
        return listCassette;
    }


    @Override
    public void StartATM() {
        conditionATM = StatusATM.Working;
        InitCassette();
    }

    @Override
    public void StopATM() {
        conditionATM = StatusATM.Stoped;
    }

    @Override
    public void GetMoney(int amount) {
        Cassette lastCassette = listCassette.get(listCassette.size() - 1);

        if (conditionATM == StatusATM.Working) {
            Cassette cassetteWithMineBanknote = checkMinBanknoteForAmount(lastCassette);

            if (cassetteWithMineBanknote != null) {
                if (amount > 0 && checkForMultiplicity(amount, cassetteWithMineBanknote)) {
                    listCassette.stream().findFirst().get().getMoney(amount);
                } else {
                    System.out.println("sorry no money in ATM");
                    StopATM();
                 }
            }
        }
    }

    @Override
    public int GetRestMoneyATM() {
        throw new UnsupportedOperationException();
    }


    private Cassette checkMinBanknoteForAmount(Cassette lastCassette) {
        if (lastCassette.getCountBanknotesCassette() > 0) {
            return lastCassette;
        } else if (lastCassette.getPrevious() != null) {
            return checkMinBanknoteForAmount(lastCassette.getPrevious());
        }
        return null;
    }

    private boolean checkForMultiplicity(int amount, Cassette lastCassette) {
        int banknote = lastCassette.getNomenal();
        int result = amount % banknote;
        if (result == 0) {
            return true;
        }
        System.out.println("sorry this amount cannot be issued. " +
                "At the moment, the minimum banknote " + lastCassette.getNomenal() + " р.");

        return false;
    }

    class CassetteComparator implements Comparator<Cassette> {
        public int compare(Cassette o1, Cassette o2) {
            if (o1.getNomenal() < o2.getNomenal())
                return 1;
            else if (o1.getNomenal() > o2.getNomenal())
                return -1;
            else
                return 0;
        }
    }

}

