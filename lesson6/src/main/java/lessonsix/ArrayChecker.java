package lessonsix;

public class ArrayChecker {

    public boolean checkFor1and4(int[] array) {
        boolean fourOrOne = false;
        for (int currentNumber : array) {
            if (currentNumber == 1 || currentNumber == 4) {
                fourOrOne = true;
                break;
            }
        }
        return fourOrOne;
    }

    public int[] subArray(int[] array) {
        int lastPosition = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                lastPosition = i + 1;
            }
        }
        if (lastPosition == -1) {
            throw new RuntimeException();
        } else {
            int[] resultArray = new int[array.length - lastPosition];
            if (array.length - lastPosition >= 0)
                System.arraycopy(array, lastPosition, resultArray, 0, resultArray.length);
            return resultArray;
        }
    }


}
