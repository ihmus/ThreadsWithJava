/*
 * @auth : Azad 
 * Semaphore thread with wait,notifyAll
 */


public class ThreadWithSemaphore {

    public static void main(String[] args) {
        Data data=new Data();
        data.semaphore=false;
        Uretim uretim= new Uretim(data);
        Tuketim t = new  Tuketim(data);
        Thread tuketim=new Thread(t);
        uretim.start();
        tuketim.start();

    }
}
class Uretim extends Thread{
    Data data;
    public Uretim(Data data){
        this.data=data;
    }
    public void run(){
        for (int i=0;i< 10;i++) {
            synchronized (data) {
                while (data.semaphore) {
                    //data.wait() metodu, thread'in beklemesini sağlar ve kilidi bırakır
                     try { data.wait(); 
                    } 
                    //wait() metodunun bir InterruptedException fırlatabileceği için bu olası hatayı yakalar
                    catch (InterruptedException e) {
                         e.printStackTrace(); 
                        } 
                    }
                    data.data++;
                    System.err.println("Uretim: " + data.data);
                    data.semaphore=true;
                    data.notifyAll();
            }
        }
    }
}
class Tuketim implements  Runnable{
    Data data;
    public Tuketim(Data data){
        this.data=data;
    }
    public void run(){
        for (int i=0;i< 10;i++) {
            synchronized (data) {
                while (!data.semaphore) {
                    try { data.wait(); 
                   } 
                   catch (InterruptedException e) {
                        e.printStackTrace(); 
                       } 
                   }
                   data.data--;
                   System.err.println("Tuketim: " + data.data);
                   data.semaphore=false;
                   data.notifyAll();
            }
        }
    }
}

/*
 * Bu kısımda data adında bir obje tanımlıyoruz
 */
class Data{
    /*
     * objede bir veri ve boolen değeri tanımlıyoruz
     */
    int data;
    /*
     * bayraklama işlemi için diye düşünün birden fazla atomik işlemimiz varsa 
     * kontrol değişkenlerinin sayısını arttırabiliriz veya öncelik sırası belirleyebiliriz
     */
    boolean semaphore;
}