package ru.otus.l8.Depart.interfaces;

import ru.otus.l7.ATM.interfaces.ATM;
import ru.otus.l7.ATM.interfaces.Command;

public interface CommandDepartment extends Command {
    public void setLisnerForCommand(Object listner);
}
