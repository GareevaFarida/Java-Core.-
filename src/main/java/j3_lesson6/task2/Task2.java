package j3_lesson6.task2;

import j3_lesson6.task1.Task1;

public class Task2 {
    public final int value1 = 1;
    public final int value2 = 4;

    public boolean checkList(int[] list) {
        boolean itIsValue1 = false;
        boolean itIsValue2 = false;

        for (int i = 0; i < list.length; i++) {
            switch (list[i]){
                case value1: {itIsValue1 = true;
                break;}
                case value2: {itIsValue2 = true;
                break;}
                default: return false;
            }

        }
        return itIsValue1&itIsValue2;
    }
}
