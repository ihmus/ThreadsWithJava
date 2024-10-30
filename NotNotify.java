
/*
 * @auth : Azad 
 * Semaphore with sleep metod
 */
public class NotNotify {
    public static void main(String[] args) {
        Data data = new Data();
        data.semaphore = false;
        data.data=100;
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
                if (!data.semaphore) {
                    data.data++;
                    System.err.println("Uretim: " + data.data);
                    data.semaphore = true;
                }
            }
            try {
                Thread.sleep(100);  // Küçük bir gecikme ekliyoruz
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
                if (data.semaphore) {
                    data.data--;
                    System.err.println("Tuketim: " + data.data);
                    data.semaphore = false;
                }
            }
            try {
                Thread.sleep(100);  // Küçük bir gecikme ekliyoruz
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Data {
    int data;
    boolean semaphore;
}
