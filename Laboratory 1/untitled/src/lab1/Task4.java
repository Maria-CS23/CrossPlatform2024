package lab1;

import java.lang.reflect.Array;

public class Task4 {
    public static Object createArray(Class<?> type, int length) {
        return Array.newInstance(type, length);
    }

    public static Object createMatrix(Class<?> type, int rows, int cols) {
        return Array.newInstance(type, rows, cols);
    }

    public static Object resizeArray(Object array, int newSize) {
        int length = Array.getLength(array);
        Object newArray = Array.newInstance(array.getClass().getComponentType(), newSize);
        System.arraycopy(array, 0, newArray, 0, Math.min(length, newSize));
        return newArray;
    }

    public static Object resizeMatrix(Object matrix, int newRows, int newCols) {
        int rows = Array.getLength(matrix);
        int cols = rows > 0 ? Array.getLength(Array.get(matrix, 0)) : 0;
        Object newMatrix = Array.newInstance(matrix.getClass().getComponentType().getComponentType(), newRows, newCols);

        for (int i = 0; i < Math.min(rows, newRows); i++) {
            Object newRow = Array.newInstance(matrix.getClass().getComponentType().getComponentType(), newCols);
            System.arraycopy(Array.get(matrix, i), 0, newRow, 0, Math.min(cols, newCols));
            Array.set(newMatrix, i, newRow);
        }

        return newMatrix;
    }

    public static String arrayToString(Object array) {
        if (array.getClass().getComponentType().isArray()) {
            StringBuilder sb = new StringBuilder();
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(arrayToString(Array.get(array, i)));
            }
            return "{" + sb.toString() + "}";
        } else {
            StringBuilder sb = new StringBuilder();
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(Array.get(array, i));
            }
            return "{" + sb.toString() + "}";
        }
    }

    public static void main(String[] args) {
        int[] intArray = (int[]) createArray(int.class, 2);
        System.out.println("int[2] = " + arrayToString(intArray));

        intArray = (int[]) resizeArray(intArray, 6);
        System.out.println("int[6] = " + arrayToString(intArray));

        String[] stringArray = (String[]) createArray(String.class, 3);
        System.out.println("java.lang.String[3] = " + arrayToString(stringArray));

        Double[] doubleArray = (Double[]) createArray(Double.class, 5);
        System.out.println("java.lang.Double[5] = " + arrayToString(doubleArray));

        int[][] intMatrix = (int[][]) createMatrix(int.class, 3, 5);
        for (int i = 0; i < intMatrix.length; i++) {
            for (int j = 0; j < intMatrix[i].length; j++) {
                intMatrix[i][j] = i * 10 + j;
            }
        }
        System.out.println("int[3][5] = " + arrayToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 4, 6);
        System.out.println("int[4][6] = " + arrayToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 3, 7);
        System.out.println("int[3][7] = " + arrayToString(intMatrix));

        intMatrix = (int[][]) resizeMatrix(intMatrix, 2, 2);
        System.out.println("int[2][2] = " + arrayToString(intMatrix));
    }
}