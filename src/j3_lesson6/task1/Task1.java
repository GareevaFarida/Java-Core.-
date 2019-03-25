package j3_lesson6.task1;

import java.awt.*;
import java.util.*;

public class Task1 {
    private final int VALUE = 4;
    private int[] list = {0, 1, 2, 4, 3, 6, 5, 4, 7, 8,9,10};

    public static void main(String args[]) {
        Task1 t = new Task1();
        t.dataAfterLast4();
    }

    public int[] dataAfterLast4() {

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = list.length - 1; i >= 0; i--) {
            if (list[i] != VALUE) {
                linkedList.addFirst(list[i]);
            } else break;
        }
         
        int[] res;
        res = new int[linkedList.size()];
        for (int i = 0; i < linkedList.size(); i++) {
            res[i] = linkedList.get(i);
            System.out.println(res[i]);
        }
        return res;
    }
}
