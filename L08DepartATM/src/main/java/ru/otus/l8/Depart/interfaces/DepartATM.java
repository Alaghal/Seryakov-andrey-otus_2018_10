package ru.otus.l8.Depart.interfaces;

import ru.otus.l7.ATM.interfaces.ATM;

import java.util.List;

public interface DepartATM  {
    public List<ATM> getCurrentAtms();
    public  void addATM(ATM atm);
    public  void removeATM(ATM atm);
    public  int getRestMoneyATMs();
    public  void setOriginalyStatementATM();




}
