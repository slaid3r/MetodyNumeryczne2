package pl.gda.pg.student.nikgracz.mnum2.Math;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents MxN matrix.
 */
public class Matrix {

    private static final Logger LOGGER = Logger.getLogger(Matrix.class.getName());

    private double[][] matrix;
    private int sizeM;
    private int sizeN;

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
        matrix = matrixAsArray;
    }

    /**
     * Resolves this matrix as simultaneous equations with use of Gauss elimination. Works only if this matrix is of
     * Nx(N+1) size.
     *
     * @return  the result of equations
     */
    public List<Double> resolve() {

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
            LOGGER.info("Row " + i + " done");
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
            LOGGER.info("Unknows eliminated for row " + k);
        }
    }

    private List<Double> reverseBehavior(double[][] matrix) {
        List<Double> result = new ArrayList<>();

        for (int i = sizeM - 1; i >= 0; i--) {

            double xi = matrix[i][sizeN - 1];

            for (int j = i + 1; j < sizeN - 1; j++) {
                xi -= matrix[i][j] * result.get(sizeM - j - 1);
            }

            result.add(xi);
            LOGGER.info("X" + i + " is " + xi);
        }

        Collections.reverse(result);

        return result;
    }

    private void print(double[][] matrix) {
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private void validateCoords(int m, int n) {
        Validate.isTrue(m < sizeM, "Given m " + m + " is out of bound!");
        Validate.isTrue(n < sizeN, "given n " + n + " is out of bound!");
    }
}
