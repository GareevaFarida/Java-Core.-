package Lesson5;

public class MyThread implements Runnable {

    private float[] array;
    private int delta;

    MyThread(float[] array, int delta) {
        this.array = array;
        this.delta = delta;
    }

    @Override
    public void run() {
        Hom.makeCalculations(array, Hom.HALF, delta);
    }
}
