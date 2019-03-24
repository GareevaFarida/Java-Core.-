import j3_lesson6.task1.Task1;
import j3_lesson6.task2.Task2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class TestTask1 {
    private Task1 t1;

    @Before
    public void prepare() {
        t1 = new Task1();
    }

    @Test
    public void test1(){
        int[]res = t1.dataAfterLast4(new int[]{1,2,3,4,5,5,6});
        Assert.assertArrayEquals(new int[]{5,5,6},res);
    }

    @Test(expected = RuntimeException.class)
    public void test_empty_massive(){
        t1.dataAfterLast4(new int[]{});
    }

    @Test(expected = RuntimeException.class)
    public void test_without_4(){
        t1.dataAfterLast4(new int[]{1,2,3,5,5,6});
    }

    @ParameterizedTest
    @MethodSource("params")
    public void test_task2_with_params(int[] data, int[]result) {
        Assertions.assertArrayEquals(new Task1().dataAfterLast4(data),result);
    }

    private static Stream params() {
        return Stream.of(
                Arguments.of(
                        new int[]{1, 4, 1, 4},
                        new int[]{}),
                Arguments.of(
                        new int[]{1,2,3,4,5,5,6},
                        new int[]{5,5,6}),
                Arguments.of(
                        new int[]{4, 1, 1, 1},
                        new int[]{1,1,1})
        );
    }

}
