package pl.gda.pg.student.nikgracz.Math;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents MxN matrix.
 */
public class Matrix {

    private final double[][] matrix;
    private final int sizeM;
    private final int sizeN;

    public Matrix(int sizeM, int sizeN) {
        matrix = new double[sizeM][sizeN];
        this.sizeM = sizeM;
        this.sizeN = sizeN;
    }

    public Matrix(int sizeM, int sizeN, double[][] matrixAsArray) {
        this.sizeM = sizeM;
        this.sizeN = sizeN;
        matrix = matrixAsArray;
    }

    public List<Double> resolve() {

        Validate.isTrue(sizeM == sizeN -1, "Invalid matrix for Gauss elimination method! Matrix must be of size Mx(M+1)");

        List<Double> result = new ArrayList<Double>();

        double[][] matrix = copyArray();

        eliminateUnknowns(matrix);

        return reverseBehavior(matrix);
    }

    public Matrix transpose() {
        double[][] transposed = new double[sizeN][sizeM];

        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return new Matrix(sizeN, sizeM, transposed);
    }

    public void print() {
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getSizeM() {
        return sizeM;
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

            print(matrix);
            System.out.println();
        }
    }

    private List<Double> reverseBehavior(double[][] matrix) {
        List<Double> result = new ArrayList<Double>();

        for (int i = sizeM - 1; i >= 0; i--) {

            double xi = matrix[i][sizeN - 1];

            for (int j = i + 1; j < sizeN - 1; j++) {
                xi -= matrix[i][j] * result.get(sizeM - j - 1);
            }

            result.add(xi);
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
}
