import j3_lesson6.task2.Task2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestTask2 {

    private Task2 task2;

    @Before
    public void prepare() {
        task2 = new Task2();
    }

    @Test
    public void test_contains_only_1_and_4() {
        boolean res = task2.checkList(new int[]{1, 4, 4, 4, 1, 4, 1, 1});
        Assert.assertTrue(res==true);
    }

    @Test
    public void test_contains_only_1() {
        boolean res = task2.checkList(new int[]{1, 1, 1});
        Assert.assertTrue(res==false);
    }

    @Test
    public void test_contains_only_4() {
        boolean res = task2.checkList(new int[]{4,4,4,4});
        Assert.assertTrue(res==false);
    }

    @Test
    public void test_contains_different_values() {
        boolean res = task2.checkList(new int[]{1,6,0,4,4,4,4});
        Assert.assertTrue(res==false);
    }

    @Test
    public void test_contains_empty_massive() {
        boolean res = task2.checkList(new int[]{});
        Assert.assertTrue(res==false);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void test_task2_with_params(int[] data, boolean result) {
        Assertions.assertTrue(new Task2().checkList(data)==result);
    }

    private static Stream params() {
        return Stream.of(
                Arguments.of(
                        new int[]{1, 4, 1, 4},
                        true),
                Arguments.of(
                        new int[]{1, 2, 3, 4},
                        false),
                Arguments.of(
                        new int[]{1, 1, 1, 1},
                        false),
                Arguments.of(
                        new int[]{4, 4},
                        false),
                Arguments.of(
                        new int[]{},
                        false)
        );
    }
}
