package ru.otus.l8.Depart;

import ru.otus.l7.ATM.interfaces.ATM;
import ru.otus.l8.Depart.interfaces.CommandDepartment;

public class PrepareATMSForGetAmount implements CommandDepartment {
    private ATM atm;

    @Override
    public void execute() {
        int  restMoneyATM =0;
            for (var cassette : atm.getListCassette()) {
                restMoneyATM = cassette.getNomenal() * cassette.getCountBanknotesCassette();
            }
            atm.setRestMoneyATM( restMoneyATM );
    }

    @Override
    public void setLisnerForCommand(Object listner) {
        this.atm=(ATM)listner;
    }
}
