package ru.otus.l8.Depart;

import ru.otus.l7.ATM.interfaces.ATM;
import ru.otus.l7.ATM.interfaces.EventListener;
import ru.otus.l8.Depart.interfaces.CommandDepartment;

public class SetOriginalStatementAtms implements CommandDepartment {
    private ATM atm;

    @Override
    public void execute() {
        atm.startATM();
    }

    @Override
    public void setListnerForCommand(EventListener listner) {
        this.atm= (ATM)listner;
    }
}
