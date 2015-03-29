package pl.gda.pg.student.nikgracz.Math;

/**
 * Represents NxN matrix.
 */
public class Matrix {

    private final double[][] matrix;
    private final int size;

    public Matrix(int size) {
        matrix = new double[size][size];
        this.size = size;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return size;
    }
}
