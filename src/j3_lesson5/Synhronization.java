package j3_lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Synhronization {
    public static final int CARS_COUNT = 4;
    public static final int CARS_IN_TUNNEL = (int)CARS_COUNT/2;
    public static Semaphore semaphore = new Semaphore(CARS_IN_TUNNEL);

    //используется для блокировки участников после завершения подготовки, чтобы
    // все потоки дождались друг друга и могли стартовать все вместе
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);

    //используется в основном классе для ожидания подготовки всех участников
    //чтобы вывести сообщения о начале гонки и о завершении
    public static CountDownLatch countDownLatchStart = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch countDownLatchFinish;

    //используется для блокировки записи победителя
    public static ReentrantLock lock = new ReentrantLock();
}
