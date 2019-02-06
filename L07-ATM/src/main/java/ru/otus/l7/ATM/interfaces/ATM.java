package ru.otus.l7.ATM.interfaces;

public interface ATM {
    public void StartATM();
    public void StopATM();
    public void GetMoney(int money);
    public  int GetRestMoneyATM();
}
