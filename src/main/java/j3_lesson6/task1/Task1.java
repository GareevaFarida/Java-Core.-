package j3_lesson6.task1;

import java.util.*;

public class Task1 {
    private final int VALUE = 4;

    public static void main(String args[]) {
        Task1 t = new Task1();
        int[]res = t.dataAfterLast4(new int[]{1,2,3,4,5,5,6});
        for (int i:res){
            System.out.println(i);
        }
    }

    public int[] dataAfterLast4(int[]list) {

        for (int i = list.length - 1; i >= 0; i--) {
            if (list[i] == VALUE) {
                return Arrays.copyOfRange(list,i+1,list.length);
            }
        }

       throw new RuntimeException("Massive not contains value "+VALUE);
    }
}
