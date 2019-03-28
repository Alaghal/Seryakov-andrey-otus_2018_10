package ru.otus.l13;

public class DemoThread {
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
        action( nameThread, +1 );
        action( nameThread, -1 );
    }

    public void action(String nameThread, int actionType) {

        if (actionType > 0) {
            int counter = 0;
            while (counter < 10) {
                counter++;
                printCounterNumberOfThread( nameThread, counter );
            }

        } else if (actionType < 0) {
            int counter = 10;
            while (counter > 1) {
                counter--;
                printCounterNumberOfThread( nameThread, counter );
            }
        }
    }

    public synchronized void printCounterNumberOfThread(String nameThread, int counter) {
        System.out.println( "Thread " + nameThread + " print number " + counter );

        if (lastNameThreadWorked == null) {
            lastNameThreadWorked = nameThread;
        }

        while (lastNameThreadWorked.equals( nameThread )) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        lastNameThreadWorked = nameThread;
        notify();
    }

}
