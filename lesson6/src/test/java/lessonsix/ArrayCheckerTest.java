package lessonsix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayCheckerTest {

    public ArrayChecker arrayChecker;

    public ArrayCheckerTest() {
        arrayChecker = new ArrayChecker();
    }

    @BeforeEach
    public void init() {
        Assertions.assertNotNull(arrayChecker);
    }

    @Test
    public void checkFor1and4Test() {
        int[] test1 = new int[]{1, 2, 4, 5};
        int[] test2 = new int[]{2, 4, 5};
        int[] test3 = new int[]{2, 1, 5};
        int[] test4 = new int[]{2, 0, 5};
        int[] test5 = new int[]{2, -1, 5};
        int[] test6 = new int[]{2, -4, 5};
        int[] test7 = new int[]{2, -4, -1};
        Assertions.assertTrue(arrayChecker.checkFor1and4(test1));
        Assertions.assertTrue(arrayChecker.checkFor1and4(test2));
        Assertions.assertTrue(arrayChecker.checkFor1and4(test3));
        Assertions.assertFalse(arrayChecker.checkFor1and4(test4));
        Assertions.assertFalse(arrayChecker.checkFor1and4(test5));
        Assertions.assertFalse(arrayChecker.checkFor1and4(test6));
        Assertions.assertFalse(arrayChecker.checkFor1and4(test7));

    }

    @Test
    public void subArrayTest1() {
        int[] data1 = new int[]{1, 2, 3, 4, 5, 6, 7, 4, 1, 1};
        int[] expected1 = new int[]{1, 1};
        int[] data2 = new int[]{1, 2, 4};
        int[] expected2 = new int[0];
        Assertions.assertArrayEquals(expected1, arrayChecker.subArray(data1));
        Assertions.assertArrayEquals(expected2, arrayChecker.subArray(data2));

        int[] data3 = new int[]{1, 2, 3, 1};
        int[] data4 = new int[]{};
        Assertions.assertThrows(RuntimeException.class, () -> arrayChecker.subArray(data3));
        Assertions.assertThrows(RuntimeException.class, () -> arrayChecker.subArray(data4));

    }

}
