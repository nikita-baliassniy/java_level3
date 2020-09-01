package arrays;

public class MainForArrays {

    public static void main(String[] args) {
        Integer[] ints = new Integer[]{1, 10, 5, 16, 26};
        String[] strings = new String[]{"A", "B", "C", "D", "E"};
        Double[] doubles = new Double[]{1.3, 2.5, 4.6, 1.89, 0.223};

        ArraysExtended<Integer> arrayForInts = new ArraysExtended<>(ints);
        ArraysExtended<String> arrayForStrings = new ArraysExtended<>(strings);
        ArraysExtended<Double> arrayForDoubles = new ArraysExtended<>(doubles);

        arrayForInts.swapTwoElements(2, 4);
        arrayForInts.printArray();

        arrayForStrings.swapTwoElements(1, 3);
        arrayForStrings.printArray();

        arrayForDoubles.swapTwoElements(3, 4);
        arrayForDoubles.printArray();

        System.out.println(arrayForInts.convertArrayToList());
        System.out.println(arrayForStrings.convertArrayToList());
        System.out.println(arrayForDoubles.convertArrayToList());
    }
}
