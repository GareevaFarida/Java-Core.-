package j3_lesson7;

import j3_lesson7.annotations.AfterSuite;
import j3_lesson7.annotations.BeforeSuite;
import j3_lesson7.annotations.Test;
import j3_lesson7.сlassesForTests.Class1;
import j3_lesson7.сlassesForTests.Сlass2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReflectionClass {

    public static void main(String[] args) {
        start(Class1.class);
        start(Сlass2.class);
    }
    
    public static void start(Class clazz) throws RuntimeException {

        List<MethodForTest> testList = new ArrayList<>();
        boolean itIsBeforeSuite = false;
        boolean itIsAfterSuite = false;
        System.out.println("Analyze of " + clazz.getCanonicalName()+"...");

        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);
            BeforeSuite before = method.getAnnotation(BeforeSuite.class);
            if (before != null) {
                if (itIsBeforeSuite) throw new RuntimeException("Class " + clazz.getSimpleName()
                        + " contains more than one method with annotation 'BeforeSuite'.");
                itIsBeforeSuite = true;

                //outputInformation(method,before,0);
                MethodForTest methodForTest = new MethodForTest(clazz,method,-1);
                testList.add(methodForTest);
               // continue;
            }

            AfterSuite after = method.getAnnotation(AfterSuite.class);
            if (after != null) {
                if (itIsAfterSuite) throw new RuntimeException("Class " + clazz.getSimpleName()
                        + " contains more than one method with annotation 'AfterSuite'.");
                itIsAfterSuite = true;
                //outputInformation(method,after,0);
                MethodForTest methodForTest = new MethodForTest(clazz,method,1000);
                testList.add(methodForTest);
                continue;
            }

            Test test = method.getAnnotation(Test.class);
            if (test == null) continue;

            int priority = test.value();
            //outputInformation(method,test,priority);
            MethodForTest methodForTest = new MethodForTest(clazz,method,priority);
            testList.add(methodForTest);
        }

        Collections.sort(testList, new Comparator<MethodForTest>() {
            @Override
            public int compare(MethodForTest p1, MethodForTest p2) {
                return p1.getPriority()-(p2.getPriority());
            }
        });

        for (MethodForTest methodForTest : testList) {
            methodForTest.execute();
        }
    }


    private static void outputInformation(Method method, Annotation annotation, int priority) {
            System.out.println(
                    "@"+annotation.annotationType().getSimpleName() + " " +
                            (priority==0?"":"(value = "+priority+") ")
+                            method.getName() + " " +
                            method.getReturnType()
            );

    }


}
