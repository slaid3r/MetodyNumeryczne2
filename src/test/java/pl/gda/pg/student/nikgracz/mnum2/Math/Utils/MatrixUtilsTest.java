package pl.gda.pg.student.nikgracz.mnum2.Math.Utils;


import javafx.util.Pair;
import org.junit.Test;
import pl.gda.pg.student.nikgracz.mnum2.Math.Matrix;
import pl.gda.pg.student.nikgracz.mnum2.SNAP.SNAPGraph;
import pl.gda.pg.student.nikgracz.mnum2.Utils.MatrixUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MatrixUtilsTest {

    private static final double[][] MATRIX_ONE = {{1, 2},
                                                  {3, 4},
                                                  {5, 6}};
    private static final double[][] MATRIX_TWO = {{1, 0},
                                                  {0, 2}};
    private static final double[][] MATRIX_THREE = {{5, 4},
                                                    {2, 1}};
    private static final double[][] SUBSTRACT_RESULT = {{-4, -4},
                                                        {-2,  1}};
    private static final double[][] MULTIPLY_RESULT = {{1,  4},
                                                       {3,  8},
                                                       {5, 12}};

    @Test
    public void shouldConvertToMatrix() {

        SNAPGraph graph = prepareGraph();
        Matrix expected = prepareExpectedMatrix();

        assertEquals(expected, MatrixUtils.convertToMatrix(graph));
    }

    @Test
    public void shouldMultiplyMatrices() {

        Matrix matrixOne = new Matrix(3, 2, MATRIX_ONE);
        Matrix matrixTwo = new Matrix(2, 2, MATRIX_TWO);
        Matrix expected = new Matrix(3, 2, MULTIPLY_RESULT);

        assertEquals(expected, MatrixUtils.multiplyByDiagonal(matrixOne, matrixTwo));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailMultiplicationForIncompatibleMatrices() {

        Matrix matrixOne = new Matrix(2, 3, MATRIX_TWO);
        Matrix matrixTwo = new Matrix(2, 3, MATRIX_THREE);

        MatrixUtils.multiplyByDiagonal(matrixOne, matrixTwo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailSubtractionForIncompatibleMatrices() {

        Matrix matrixOne = new Matrix(3, 2, MATRIX_ONE);
        Matrix matrixTwo = new Matrix(2, 3, MATRIX_THREE);

        MatrixUtils.substractMatrices(matrixOne, matrixTwo);
    }

    @Test
    public void shouldSubstractMatrices() {

        Matrix matrixOne = new Matrix(2, 2, MATRIX_TWO);
        Matrix matrixTwo = new Matrix(2, 2, MATRIX_THREE);
        Matrix expected = new Matrix(2, 2, SUBSTRACT_RESULT);

        assertEquals(expected, MatrixUtils.substractMatrices(matrixOne, matrixTwo));
    }

    private static SNAPGraph prepareGraph() {

        Set<Pair<Integer, Integer>> values = new LinkedHashSet<>();
        values.add(new Pair<>(0, 2));
        values.add(new Pair<>(2, 4));
        values.add(new Pair<>(3, 1));
        values.add(new Pair<>(4, 2));
        values.add(new Pair<>(1, 3));
        values.add(new Pair<>(2, 3));

        return new SNAPGraph(5, values);
    }

    private static Matrix prepareExpectedMatrix() {

        Matrix expected = new Matrix(5, 5);
        expected.setAt(0, 2, 1);
        expected.setAt(2, 4, 1);
        expected.setAt(3, 1, 1);
        expected.setAt(4, 2, 1);
        expected.setAt(1, 3, 1);
        expected.setAt(2, 3, 1);
        return expected;
    }
}
