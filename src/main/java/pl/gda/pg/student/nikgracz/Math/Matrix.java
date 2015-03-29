package pl.gda.pg.student.nikgracz.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents Nx(N+1) matrix.
 */
public class Matrix {

    private final double[][] matrix;
    private final int size;

    public Matrix(int size) {
        matrix = new double[size][size+1];
        this.size = size;
    }

    public Matrix(int size, double[][] matrixAsArray) {
        this.size = size;
        matrix = matrixAsArray;
    }

    public List<Double> resolve() {
        List<Double> result = new ArrayList<Double>();

        double[][] matrix = copyArray();

        eliminateUnknowns(matrix);

        return reverseBehavior(matrix);
    }

    private List<Double> reverseBehavior(double[][] matrix) {
        List<Double> result = new ArrayList<Double>();

        for (int i = size - 1; i >= 0; i--) {

            double xi = matrix[i][size];

            for (int j = i + 1; j < size; j++) {
                xi -= matrix[i][j] * result.get(size - j - 1);
            }

            result.add(xi);
        }

        Collections.reverse(result);

        return result;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return size;
    }

    private double[][] copyArray() {
        double[][] result = new double[size][size + 1];
        for (int i = 0; i < size; i++) {
            result[i] = Arrays.copyOf(matrix[i], size + 1);
        }
        return result;
    }

    private void eliminateUnknowns(double[][] matrix) {
        for (int k = 0; k < size; k++) {
            for (int j = size; j >= k; j--) {
                matrix[k][j] = matrix[k][j] / matrix[k][k];
            }
            matrix[k][0] = matrix[k][0] / matrix[k][k];

            for(int i = k +1; i < size; i++) {
                for (int j = size; j >= k; j--) {
                    matrix[i][j] = matrix[i][j] - matrix[i][k]*matrix[k][j];
                }
                matrix[i][0] = matrix[i][0] - matrix[i][k]*matrix[k][0];
            }

            print(matrix);
            System.out.println();
        }
    }

    private void print(double[][] matrix) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
