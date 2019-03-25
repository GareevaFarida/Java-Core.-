package j3_lesson7.сlassesForTests;

import j3_lesson7.annotations.AfterSuite;
import j3_lesson7.annotations.BeforeSuite;
import j3_lesson7.annotations.Test;

public class Сlass2 {

    @BeforeSuite
    public void prepare(){
        System.out.println("Class2: Подготовка к тестированию");
    }

    @AfterSuite
    public void finish(){
        System.out.println("Class2: Тестирование завершено.");
    }

    @AfterSuite
    public void finish2(){
        System.out.println("Class2: Тестирование совсем завершено.");
    }

    @Test(value = 7)
    public void method1(){
        System.out.println("Class2: Выполняется public метод method1, приоритет метода 7");
    }

    @Test(value = 4)
    private void method2(){
        System.out.println("Class2: Выполняется private метод method2, приоритет метода 4");
    }

    @Test(value = 4)
    public static void method3(){
        System.out.println("Class2: Выполняется public static метод method3, приоритет метода 4");
    }

    @Test(value = 4)
    private static void method4(){
        System.out.println("Class2: Выполняется private static метод method4, приоритет метода 4");
    }
}
