package ru.otus.l8.Depart.Interface;

import ru.otus.l7.ATM.interfaces.ATM;

import java.util.List;

public interface DepartATM {
    public List<ATM> getCurrentAtms();
    public  void addATM();
    public  void removeATM();
    public  int getRestMoneyATMs();
    public  void setOriginalyStatementATM();




}
