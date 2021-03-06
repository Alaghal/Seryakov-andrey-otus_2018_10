package ru.otus.l7.ATM.ATM;

import ru.otus.l7.ATM.interfaces.Command;


public class ControlPanel {
  private   Command[] onCommands;

    public ControlPanel(){
        onCommands = new Command[2];

        for(int i=0; i<onCommands.length;i++){
            onCommands[i] = ()->{ };
        }
    }

    public void setCommand(int slot,Command onCommand){
        onCommands[slot]=onCommand;
    }

    public void onButtonWasPushed(int slot){
        onCommands[slot].execute();

    }
}
