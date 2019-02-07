package Lesson5;

public class Hom {
    private static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    private float arr[] = new float[SIZE];

    public Hom() {
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
    }

    public void Metod1() {
        long a = System.currentTimeMillis();
        makeCalculations(arr, SIZE, 0);
        System.out.printf("Время выполнения первого метода %d: ", System.currentTimeMillis() - a);
    }

    public void Metod2() {
        long a = System.currentTimeMillis();

        //создаем два вспомогательных массива
        float arr1[] = new float[HALF];
        float arr2[] = new float[HALF];

        //делим исходный массив на два подмассива
        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        Thread thr1 = new Thread(new MyThread(arr1, 0));
        Thread thr2 = new Thread(new MyThread(arr2, HALF));
        thr1.start();
        thr2.start();

        //Прежде чем загружать обработанные массивы, дадим возможность
        //исполняющим потокам завершить свою задачу
        try {
            thr1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thr2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //объединяем два массива
        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);

        System.out.printf("\nВремя выполнения второго метода %d: ", System.currentTimeMillis() - a);
    }

    static void makeCalculations(float[] array, int size, int delta) {

        for (int i = 0; i < size; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + (i + delta) / 5) * Math.cos(0.2f + (i + delta) / 5) * Math.cos(0.4f + (i + delta) / 2));
        }
       // System.out.printf("\nПоследняя ячейка массива [%d] содержит: %f", size--, array[size]);
    }
}
