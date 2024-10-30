/*
 * @auth : Azad 
 * Semaphore thread with wait,notifyAll
 */
public class OurWaitMetod {
    public static void main(String[] args) {
        Data data = new Data();
        data.semaphore = false;
        Uretim uretim = new Uretim(data);
        Tuketim tuketim = new Tuketim(data);
        Thread tuketimThread = new Thread(tuketim);
        uretim.start();
        tuketimThread.start();
    }
}

class Uretim extends Thread {
    Data data;

    public Uretim(Data data) {
        this.data = data;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (data) {
                while (data.semaphore) {
                    selfWait();
                }
                data.data++;
                System.err.println("Uretim: " + data.data);
                data.semaphore = true;
                selfNotifyAll();
            }
        }
    }

    private void selfWait() {
        try {
            data.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void selfNotifyAll() {
        data.notifyAll();
    }
}

class Tuketim implements Runnable {
    Data data;

    public Tuketim(Data data) {
        this.data = data;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (data) {
                while (!data.semaphore) {
                    selfWait();
                }
                data.data--;
                System.err.println("Tuketim: " + data.data);
                data.semaphore = false;
                selfNotifyAll();
            }
        }
    }

    private void selfWait() {
        try {
            data.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void selfNotifyAll() {
        data.notifyAll();
    }
}

class Data {
    int data;
    boolean semaphore;
}
