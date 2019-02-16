package ru.otus.l8.Depart.interfaces;

import ru.otus.l7.ATM.interfaces.ATM;
import ru.otus.l7.ATM.interfaces.Command;
import ru.otus.l7.ATM.interfaces.EventListener;

public interface CommandDepartment extends Command {
    public void setListnerForCommand(EventListener listner);

}
