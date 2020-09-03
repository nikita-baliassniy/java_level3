package arrays;

public class MainForArrays {

    public static void main(String[] args) {
        Integer[] ints = new Integer[]{1, 10, 5, 16, 26};
        String[] strings = new String[]{"A", "B", "C", "D", "E"};
        Double[] doubles = new Double[]{1.3, 2.5, 4.6, 1.89, 0.223};

        ArraysExtended<Integer> arrayForInts = new ArraysExtended<>();
        ArraysExtended<String> arrayForStrings = new ArraysExtended<>();
        ArraysExtended<Double> arrayForDoubles = new ArraysExtended<>();

        arrayForInts.swapTwoElements(ints, 2, 4);
        arrayForInts.printArray(ints);

        arrayForStrings.swapTwoElements(strings,1, 3);
        arrayForStrings.printArray(strings);

        arrayForDoubles.swapTwoElements(doubles, 3, 4);
        arrayForDoubles.printArray(doubles);

        System.out.println(arrayForInts.convertArrayToList(ints));
        System.out.println(arrayForStrings.convertArrayToList(strings));
        System.out.println(arrayForDoubles.convertArrayToList(doubles));
    }
}
