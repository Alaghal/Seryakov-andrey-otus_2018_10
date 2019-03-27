package ru.otus.l13;

public class DemoThread {
    volatile boolean printPossibleFlag = false;
    volatile String lastNameThreadWorked = null;

    public static void main(String[] arg) throws InterruptedException {
        new DemoThread().go();
    }

    public void go() throws InterruptedException {
        Thread thread1 = new Thread( () -> operationForThread( "1" ) );
        Thread thread2 = new Thread( () -> operationForThread( "2" ) );

        thread1.start();
        thread2.start();
    }

    private void operationForThread(String nameThread) {
        increment( nameThread, 0 );
        decrement( nameThread, 10 );
    }

    public synchronized void increment(String nameThread, int counter) {
        while (counter < 10) {
            printPossibleFlag = false;

            counter++;
            System.out.println( "Thread " + nameThread + " print number " + counter );
            printPossibleFlag = true;
            if (lastNameThreadWorked == null) {
                lastNameThreadWorked = nameThread;
            }

            while (printPossibleFlag && lastNameThreadWorked == nameThread) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            lastNameThreadWorked = nameThread;
            notify();

        }
    }

    public synchronized void decrement(String nameThread, int counter) {
        while (counter > 1) {
            printPossibleFlag = false;

            counter--;
            System.out.println( "Thread " + nameThread + " print number " + counter );
            printPossibleFlag = true;
            if (lastNameThreadWorked == null) {
                lastNameThreadWorked = nameThread;
            }


            while (printPossibleFlag && lastNameThreadWorked == nameThread) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            lastNameThreadWorked = nameThread;
            notify();

        }
    }

}
