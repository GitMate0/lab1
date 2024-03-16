import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayMatrixProgram {
    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> type, int size) {
        return (T[]) Array.newInstance(type, size);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] createMatrix(Class<T> type, int rows, int cols) {
        return (T[][]) Array.newInstance(type, rows, cols);
    }

    public static <T> T[] resizeArray(T[] array, int newSize) {
        return Arrays.copyOf(array, newSize);
    }

    public static <T> T[][] resizeMatrix(T[][] matrix, int newRows, int newCols) {
        int oldRows = matrix.length;
        Class<?> componentType = matrix.getClass().getComponentType().getComponentType();
        @SuppressWarnings("unchecked")
        T[][] newMatrix = (T[][]) Array.newInstance(componentType, newRows, newCols);
        for (int i = 0; i < Math.min(oldRows, newRows); i++) {
            newMatrix[i] = Arrays.copyOf(matrix[i], newCols);
        }
        return newMatrix;
    }

    public static <T> String arrayToLine(T[] array) {
        StringBuilder sb = new StringBuilder();
        for (T element : array) {
            sb.append(element).append(" ");
        }
        return sb.toString().trim();
    }

    public static <T> String matrixToLine(T[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (T[] row : matrix) {
            for (T element : row) {
                sb.append(element).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        Integer[] intArray = createArray(Integer.class, 5);
        intArray[0] = 1;
        intArray[1] = 2;
        intArray[2] = 3;
        intArray[3] = 4;
        intArray[4] = 5;
        System.out.println("Original Integer Array: " + Arrays.toString(intArray));
        intArray = resizeArray(intArray, 8);
        System.out.println("Resized Integer Array: " + Arrays.toString(intArray));

        String[][] stringMatrix = createMatrix(String.class, 2, 3);
        stringMatrix[0][0] = "a";
        stringMatrix[0][1] = "b";
        stringMatrix[0][2] = "c";
        stringMatrix[1][0] = "d";
        stringMatrix[1][1] = "e";
        stringMatrix[1][2] = "f";
        System.out.println("Original String Matrix: \n" + matrixToLine(stringMatrix));
        stringMatrix = resizeMatrix(stringMatrix, 3, 4);
        System.out.println("Resized String Matrix: \n" + matrixToLine(stringMatrix));
    }
}
