package ru.otus.l8.Depart;

import ru.otus.l7.ATM.interfaces.ATM;
import ru.otus.l7.ATM.interfaces.EventListener;
import ru.otus.l8.Depart.interfaces.DepartATM;
import ru.otus.l8.Depart.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

public class MyDepart implements DepartATM {
    private List<ATM> atmsList;
    public EventManager events;

    public MyDepart(){
        atmsList = new ArrayList<>( );
        this.events = new MyEventManager( "PrepareForGetAmountMoneyATM", "SetOriginalConditionATM" );
    }

    @Override
    public List<ATM> getCurrentAtms() {
        return atmsList;
    }

    @Override
    public void addATM(ATM atm) {
         atmsList.add( atm );
         events.subscribe("PrepareForGetAmountMoneyATM",  atm );
         events.subscribe("SetOriginalConditionATM",  atm );
    }

    @Override
    public void removeATM(ATM atm) {
        atmsList.remove( atm );
        events.unsubscribe("PrepareForGetAmountMoneyATM",  atm );
        events.unsubscribe("SetOriginalConditionATM",  atm );
    }

    @Override
    public int getRestMoneyATMs() {
        events.notify( "PrepareForGetAmountMoneyATM" );

        int collectorAmountMoneyATM=0;
        for (var atm : atmsList) {
            collectorAmountMoneyATM+=atm.getRestMoneyATM();
        }

        return collectorAmountMoneyATM;
    }

    @Override
    public void setOriginalyStatementATM() {
        events.notify( "SetOriginalConditionATM" );
    }
}
