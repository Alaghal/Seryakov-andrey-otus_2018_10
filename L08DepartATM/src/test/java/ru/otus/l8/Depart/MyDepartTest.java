package ru.otus.l8.Depart;

import org.junit.jupiter.api.Test;
import ru.otus.l7.ATM.interfaces.ATM;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyDepartTest {

    @Test
    void getCurrentAtms() {
        ATM a = mock(ATM.class);
        ATM b = mock(ATM.class);
        ATM c = mock(ATM.class);
        List<ATM>  actual = new ArrayList<>( );
        actual.add(a);
        actual.add(b);
        actual.add(c);
        actual.remove(b);

        MyDepart departmentATM = new MyDepart();
        departmentATM.addATM(a);
        departmentATM.addATM(b);
        departmentATM.addATM(c);
        departmentATM.removeATM(b);
        List<ATM> expected =departmentATM.getCurrentAtms();

        assertEquals( expected, actual );
    }

    @Test
    void addATM() {
        ATM atm =  mock(ATM.class);
        List<ATM> actual = new ArrayList<>();
        actual.add( atm );

        MyDepart departExpected = new MyDepart();
        departExpected.addATM( atm );
        List<ATM> expected = departExpected.getCurrentAtms();

        assertEquals( expected,actual );
    }

    @Test
    void removeATM() {
        ATM a =  mock(ATM.class);
        ATM b = mock( ATM.class );
        List<ATM> actual = new ArrayList<>();
        actual.add( a );
        actual.add( b );
        actual.remove( b );

        MyDepart departExpected = new MyDepart();
        departExpected.addATM( a );
        departExpected.addATM( b );
        departExpected.removeATM( b );
        List<ATM> expected = departExpected.getCurrentAtms();

        assertEquals( expected,actual );
    }

    @Test
    void getRestMoneyATMs() {
        ATM atm = mock(ATM.class);
        when( atm.getRestMoneyATM()).thenReturn( 200 );
        MyDepart depart = new MyDepart();
        depart.addATM( atm );

        int actual =200;
        int expected = depart.getRestMoneyATMs();

        assertEquals( expected,actual);
    }

    @Test
    void setOriginalyStatementATM() {
        ATM atm = mock(ATM.class);
        doNothing().when(atm).update( "SetOriginalConditionATM" );

        MyDepart depart = new MyDepart();
        depart.addATM(atm);
        depart.setOriginalyStatementATM();

        verify(atm,times( 1 )).update( "SetOriginalConditionATM" );
    }
}