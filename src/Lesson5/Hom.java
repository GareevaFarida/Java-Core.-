package Lesson5;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Hom{
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    float arr[] = new float[SIZE];

    public Hom() {
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
    }

    public void Metod1() {
        long a = System.currentTimeMillis();
        makeArifmetics(arr,SIZE);

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

        new Thread(new MyThread(arr1)).start();
        new Thread(new MyThread(arr2)).start();

        //объединяем два массива
        System.arraycopy(arr1,0,arr,0,HALF);
        System.arraycopy(arr2,0,arr,HALF,HALF);
        System.out.printf("Время выполнения второго метода %d: ", System.currentTimeMillis() - a);
    }
    public static class MyThread implements Runnable{

        float[] array;
        MyThread(float[] array){
         this.array = array;
        }

        @Override
        public void run(){
          makeArifmetics(array,HALF);
         }
    }

    private static void makeArifmetics(float[] array, int size) {
        for (int i = 0; i < size; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }


}
