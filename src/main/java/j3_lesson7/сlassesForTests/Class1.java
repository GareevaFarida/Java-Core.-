package j3_lesson7.сlassesForTests;

import j3_lesson7.annotations.AfterSuite;
import j3_lesson7.annotations.BeforeSuite;
import j3_lesson7.annotations.Test;

public class Class1 {

    @BeforeSuite
    public void prepare(){
       System.out.println("Class1: Подготовка к тестированию");
    }

    @AfterSuite
    public void finish(){
        System.out.println("Class1: Тестирование завершено.");
    }

    @Test(value = 1)
    public void method1(){
        System.out.println("Class1: Выполняется public метод method1, приоритет метода 1");
    }

    @Test(value = 3)
    private void method2(){
        System.out.println("Class1: Выполняется private метод method2, приоритет метода 3");
    }

    @Test(value = 3)
    public static void method3(){
        System.out.println("Class1: Выполняется public static метод method3, приоритет метода 3");
    }

    @Test(value = 8)
    private static void method4(){
        System.out.println("Class1: Выполняется private static метод method4, приоритет метода 8");
    }
}
