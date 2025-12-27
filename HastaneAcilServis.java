import java.util.*;
import java.util.concurrent.Semaphore;

public class HastaneAcilServis {

    static final int ODA_SAYISI = 2;
    static final int TOPLAM_HASTA = 5;

    static Semaphore odaSemaforu = new Semaphore(ODA_SAYISI);
    static Semaphore mutex = new Semaphore(1);

    static Queue<Hasta> acilKuyruk = new LinkedList<>();
    static Queue<Hasta> normalKuyruk = new LinkedList<>();

    static int hastaID = 1;
    static int tamamlananHasta = 0;

    static class Hasta extends Thread {
        int id;
        boolean acilMi;

        Hasta(boolean acilMi) {
            this.id = hastaID++;
            this.acilMi = acilMi;
        }

        @Override
        public void run() {
            try {
                System.out.println(tip() + " acil servise geldi.");

                mutex.acquire();
                if (acilMi) {
                    acilKuyruk.add(this);
                    System.out.println(tip() + " ACİL kuyruğuna alındı.");
                } else {
                    normalKuyruk.add(this);
                    System.out.println(tip() + " NORMAL kuyruğuna alındı.");
                }
                mutex.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void muayeneOl() throws InterruptedException {
            System.out.println(tip() + " muayene odasına alındı.");
            Thread.sleep(2000);
            System.out.println(tip() + " muayeneyi tamamladı ve çıktı.");

            mutex.acquire();
            tamamlananHasta++;
            mutex.release();
        }

        String tip() {
            return (acilMi ? "Acil Hasta " : "Normal Hasta ") + id;
        }
    }

    static class Planlayici extends Thread {
        public void run() {
            try {
                while (true) {
                    mutex.acquire();
                    boolean bitir =
                            acilKuyruk.isEmpty() &&
                                    normalKuyruk.isEmpty() &&
                                    tamamlananHasta == TOPLAM_HASTA;
                    mutex.release();

                    if (bitir) {
                        System.out.println("=== Tüm hastalar muayene edildi, sistem kapanıyor ===");
                        break;
                    }

                    odaSemaforu.acquire();
                    mutex.acquire();

                    Hasta siradaki = null;
                    if (!acilKuyruk.isEmpty()) {
                        siradaki = acilKuyruk.poll();
                    } else if (!normalKuyruk.isEmpty()) {
                        siradaki = normalKuyruk.poll();
                    }

                    mutex.release();

                    if (siradaki != null) {
                        siradaki.muayeneOl();
                    }

                    odaSemaforu.release();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Hastane Acil Servis Simülasyonu Başladı ===");

        new Planlayici().start();

        new Hasta(false).start();
        new Hasta(true).start();
        new Hasta(false).start();
        new Hasta(true).start();
        new Hasta(false).start();
    }
}
