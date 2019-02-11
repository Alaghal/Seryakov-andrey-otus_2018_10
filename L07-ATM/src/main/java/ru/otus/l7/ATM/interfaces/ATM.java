package ru.otus.l7.ATM.interfaces;

import ru.otus.l7.ATM.cassettes.Cassette;

import java.util.List;

public interface ATM extends  EventListener {
    public void startATM();
    public void stopATM();
    public void getMoney(int money);
    public  int getRestMoneyATM();
    public List<Cassette> getListCassette();
    public  void setRestMoneyATM(int rest);
}
