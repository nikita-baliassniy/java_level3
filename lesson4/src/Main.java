import interfaces.AverageSearch;
import interfaces.MaximumSearch;
import interfaces.NumberSearch;
import interfaces.StringsSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {

        Integer[] integers = new Integer[]{5, 10, 15, 20, 25, 30, 25, 20, 15, 10};
        List<String> strings = new ArrayList<>(
                Arrays.asList("apple", "melon", "banana", "pineapple", "abc", "anckle", "arg"));

        NumberSearch numberSearch = (number, ints) -> {
            for (int i = 0; i < ints.length; i++) {
                if (ints[i].equals(number)) {
                    return i;
                }
            }
            return -1;
        };

        UnaryOperator reverser = (string) -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = ((String) string).length() - 1; i >= 0; i--) {
                stringBuilder.append(((String) string).charAt(i));
            }
            return stringBuilder.toString();
        };

        MaximumSearch maximumSearch = ints -> {
            Integer max = ints[0];
            for (Integer anInt : ints) {
                if (max < anInt) {
                    max = anInt;
                }
            }
            return max;
        };

        AverageSearch averageSearch = ints -> {
            Double avg = 0.0;
            for (Integer anInt : ints) {
                avg += anInt;
            }
            return avg / ints.size();
        };

        StringsSearch stringsSearch = strs -> {
            List<String> resultList = new ArrayList<String>();
            for (String string : strs) {
                if (string.length() == 3 && string.startsWith("a")) {
                    resultList.add(string);
                }
            }
            return resultList;
        };

        System.out.println(numberSearch.search(20, integers));
        System.out.println(reverser.apply("HELLO WORLD"));
        System.out.println(maximumSearch.maximum(integers));
        System.out.println(averageSearch.average(Arrays.asList(integers.clone())));
        System.out.println(stringsSearch.search(strings));

    }

}
