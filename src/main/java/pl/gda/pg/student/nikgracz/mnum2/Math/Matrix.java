package pl.gda.pg.student.nikgracz.mnum2.Math;

import javafx.util.Pair;
import org.apache.commons.lang3.Validate;

import java.util.*;

/**
 * Represents MxN matrix.
 */
public class Matrix {

    private double[][] matrix;
    private int sizeM;
    private int sizeN;
    private int[] rows;
    private int[] columns;

    /**
     * Creates MxN matrix full of zeros.
     *
     * @param sizeM  the number of rows
     * @param sizeN  the number of columns
     */
    public Matrix(int sizeM, int sizeN) {
        this(new double[sizeM][sizeN]);
    }

    /**
     * Creates MxN matrix with values stored in given array.
     *
     * @param matrixAsArray  the values of the matrix
     */
    public Matrix(double[][] matrixAsArray) {
        sizeM = matrixAsArray.length;
        sizeN = matrixAsArray[0].length;

        rows = new int[sizeM];
        for (int i = 0; i < sizeM; i++) {
            rows[i] = i;
        }

        columns = new int[sizeN];
        for (int i = 0; i < sizeN; i++) {
            columns[i] = i;
        }

        matrix = matrixAsArray;
    }

    /**
     * Resolves this matrix as simultaneous equations with use of Gauss elimination. Works only if this matrix is of
     * Nx(N+1) size.
     *
     * @return  the result of equations
     */
    public Map<Integer, Double> resolve() {

        Validate.isTrue(sizeM == sizeN -1, "Invalid matrix for Gauss elimination method! Matrix must be of size Mx(M+1)");

        double[][] matrix = copyArray();

        eliminateUnknowns(matrix);

        return reverseBehavior(matrix);
    }

    /**
     * Transposes this matrix.
     *
     * @return  the transposed matrix
     */
    public Matrix transpose() {
        double[][] transposed = new double[sizeN][sizeM];

        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return new Matrix(transposed);
    }

    /**
     * Returns value at a(m)(n).
     *
     * @param m  the number of the row
     * @param n  the number of the column
     * @return  the value stored at a(m)(n)
     */
    public double getAt(int m, int n) {
        validateCoords(m, n);
        return matrix[m][n];
    }

    /**
     * Sets value at a(m)(n) to the given value.
     *
     * @param m  the number of the row
     * @param n  the number of the column
     * @param value  the value to be set
     */
    public void setAt(int m, int n, double value) {
        validateCoords(m, n);
        matrix[m][n] = value;
    }

    /**
     * Prints the matrix to the standard output stream.
     */
    public void print() {
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Multiplies this matrix by given scalar
     */
    public void multiplyBy(double scalar) {
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                matrix[i][j] *= scalar;
            }
        }
    }

    /**
     * Creates identity matrix of size n.
     *
     * @param n  the size of the matrix
     * @return  the identity matrix
     */
    public static Matrix identityMatrix(int n) {
        Matrix matrix = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            matrix.setAt(i, i, 1);
        }
        return matrix;
    }

    /**
     * Returns nth row.
     *
     * @param n  the number of the row
     * @return  the nth row
     */
    public double[] getRow(int n) {
        return matrix[n];
    }

    /**
     * Adds column.
     *
     * @param column  the column to be added
     */
    public void addColumn(double[] column) {

        Validate.isTrue(sizeM == column.length, "Number of rows of the column must be equal to the number of matrix rows");

        double[][] newMatrix = new double[sizeM][sizeN + 1];

        for (int i = 0; i < sizeM; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
            newMatrix[i][sizeN] = column[i];
        }

        this.matrix = newMatrix;
        this.sizeN += 1;
    }

    /**
     * Checks whether given matrix is diagonal.
     *
     * @return  true if matrix is diagonal, false otherwise
     */
    public boolean isDiagonal() {

        if (sizeM != sizeN) {
            return false;
        }

        for (int i = 1; i < sizeM; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j] != 0 || matrix[j][i] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix)) {
            return false;
        }

        Matrix other = (Matrix) o;

        boolean result;

        result = sizeM == other.sizeM;

        if (result) {
            result = sizeN == other.sizeN;
        }

        if (result) {
            result = Arrays.deepEquals(matrix, other.matrix);
        }

        return result;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public int getSizeM() {
        return sizeM;
    }

    public void setSizeM(int sizeM) {
        this.sizeM = sizeM;
    }

    public int getSizeN() {
        return sizeN;
    }

    public void setSizeN(int sizeN) {
        this.sizeN = sizeN;
    }

    private double[][] copyArray() {
        double[][] result = new double[sizeM][sizeN];
        for (int i = 0; i < sizeM; i++) {
            result[i] = Arrays.copyOf(matrix[i], sizeN);
        }
        return result;
    }

    private void eliminateUnknowns(double[][] matrix) {
        for (int k = 0; k < sizeM; k++) {
            Pair<Integer, Integer> max = getMax(k, matrix);
            swapRows(k, max, matrix);
            swapColumn(k, max, matrix);
            for (int j = sizeN - 1; j >= k; j--) {
                matrix[k][j] = matrix[k][j] / matrix[k][k];
            }
            matrix[k][0] = matrix[k][0] / matrix[k][k];

            for(int i = k +1; i < sizeM; i++) {
                for (int j = sizeN - 1; j >= k; j--) {
                    matrix[i][j] = matrix[i][j] - matrix[i][k]*matrix[k][j];
                }
                matrix[i][0] = matrix[i][0] - matrix[i][k]*matrix[k][0];
            }
        }
    }

    private void swapColumn(int k, Pair<Integer, Integer> max, double[][] matrix) {

        int tempColumn = columns[k];
        columns[k] = max.getValue();
        columns[max.getValue()] = tempColumn;
        System.out.println("Swapped " + k + " " + tempColumn + " with " + max.getValue() + " " + columns[k]);

        for (int i = 0; i < matrix.length; i++) {
            double temp = matrix[i][k];
            matrix[i][k] = matrix[i][max.getValue()];
            matrix[i][max.getValue()] = temp;
        }
    }

    private void swapRows(int k, Pair<Integer, Integer> max, double[][] matrix) {
        int temp = rows[k];
        rows[k] = max.getKey();
        rows[max.getKey()] = temp;
        double[] row = matrix[k];
        matrix[k] = matrix[max.getKey()];
        matrix[max.getKey()] = row;
    }

    private Pair<Integer, Integer> getMax(int k, double[][] matrix) {
        Pair<Integer, Integer> maxLoc = new Pair<>(k, k);
        double max = 0;
        for (int i = k; i < matrix.length; i++) {
            for (int j = k; j < matrix[0].length - 1; j++) {
                if (max < Math.abs(matrix[i][j])) {
                    maxLoc = new Pair<>(i, j);
                    max = Math.abs(matrix[i][j]);
                }
            }
        }

        return maxLoc;
    }

    private Map<Integer, Double> reverseBehavior(double[][] matrix) {
        List<Double> result = new ArrayList<>();

        for (int i = sizeM - 1; i >= 0; i--) {

            double xi = matrix[i][sizeN - 1];

            for (int j = i + 1; j < sizeN - 1; j++) {
                xi -= matrix[i][j] * result.get(sizeM - j - 1);
            }

            result.add(xi);
        }

        Collections.reverse(result);

        for (Double element : result) {
            System.out.println(element);
        }

        Map<Integer, Double> map = new TreeMap<>();

        for (int i = 0; i < result.size(); i++) {
            System.out.println(i + " " + columns[i]);
            map.put(columns[i], result.get(i));
        }

        return map;
    }

    private void validateCoords(int m, int n) {
        Validate.isTrue(m < sizeM, "Given m " + m + " is out of bound!");
        Validate.isTrue(n < sizeN, "given n " + n + " is out of bound!");
    }
}
