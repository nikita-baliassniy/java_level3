package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysExtended<T> {

    private T[] array;

    public ArraysExtended(T[] array) {
        this.array = array;
    }

    // Метод замены одного элемента другим по индексу
    public void swapTwoElements(int indexFrom, int indexTo) {
        if (array.length != 0 && indexFrom >= 0 && indexFrom < array.length
                && indexTo >= 0 && indexTo < array.length) {
            T temp = array[indexFrom];
            array[indexFrom] = array[indexTo];
            array[indexTo] = temp;
        }
    }

    // Метод конвертации массива в список
    public List<T> convertArrayToList() {
        List<T> list = null;
        if (array.length != 0) {
            list = new ArrayList<>();
            for (T t :array) {
                list.add(t);
            }
        }
        return list;
    }

    public void printArray() {
        for (T anArray : array) {
            System.out.print(anArray + " ");
        }
        System.out.println();
    }

}
