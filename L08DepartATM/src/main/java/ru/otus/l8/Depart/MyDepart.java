package ru.otus.l8.Depart;

import ru.otus.l7.ATM.interfaces.ATM;
import ru.otus.l8.Depart.interfaces.DepartATM;

import java.util.List;

public class MyDepart implements DepartATM {
    @Override
    public List<ATM> getCurrentAtms() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addATM(ATM atm) {
         throw new UnsupportedOperationException();

    }

    @Override
    public void removeATM(ATM atm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRestMoneyATMs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOriginalyStatementATM() {
        throw new UnsupportedOperationException();
    }
}
