package arrays;

import java.util.ArrayList;
import java.util.List;

public class ArraysExtended<T> {

    public ArraysExtended() {
    }

    // Метод замены одного элемента другим по индексу
    public void swapTwoElements(T[] array, int indexFrom, int indexTo) {
        if (array.length != 0 && indexFrom >= 0 && indexFrom < array.length
                && indexTo >= 0 && indexTo < array.length) {
            T temp = array[indexFrom];
            array[indexFrom] = array[indexTo];
            array[indexTo] = temp;
        }
    }

    // Метод конвертации массива в список
    public List<T> convertArrayToList(T[] array) {
        List<T> list = new ArrayList<>();;
        if (array.length != 0) {
            for (T t : array) {
                list.add(t);
            }
        }
        return list;
    }

    public void printArray(T[] array) {
        for (T anArray : array) {
            System.out.print(anArray + " ");
        }
        System.out.println();
    }

}
