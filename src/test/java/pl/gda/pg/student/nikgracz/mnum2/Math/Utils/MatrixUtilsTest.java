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

        Matrix matrixOne = new Matrix(MATRIX_ONE);
        Matrix matrixTwo = new Matrix(MATRIX_TWO);
        Matrix expected = new Matrix(MULTIPLY_RESULT);

        assertEquals(expected, MatrixUtils.multiplyByDiagonal(matrixOne, matrixTwo));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailMultiplicationForIncompatibleMatrices() {

        Matrix matrixOne = new Matrix(MATRIX_TWO);
        Matrix matrixTwo = new Matrix(MATRIX_THREE);

        MatrixUtils.multiplyByDiagonal(matrixOne, matrixTwo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailSubtractionForIncompatibleMatrices() {

        Matrix matrixOne = new Matrix(MATRIX_ONE);
        Matrix matrixTwo = new Matrix(MATRIX_THREE);

        MatrixUtils.substractMatrices(matrixOne, matrixTwo);
    }

    @Test
    public void shouldSubstractMatrices() {

        Matrix matrixOne = new Matrix(MATRIX_TWO);
        Matrix matrixTwo = new Matrix(MATRIX_THREE);
        Matrix expected = new Matrix(SUBSTRACT_RESULT);

        assertEquals(expected, MatrixUtils.substractMatrices(matrixOne, matrixTwo));
    }

    private static SNAPGraph prepareGraph() {

        Set<Pair<Integer, Integer>> values = new LinkedHashSet<>();
        values.add(new Pair<>(0, 1));
        values.add(new Pair<>(0, 3));
        values.add(new Pair<>(1, 0));
        values.add(new Pair<>(2, 0));
        values.add(new Pair<>(2, 1));
        values.add(new Pair<>(3, 2));

        return new SNAPGraph(4, values);
    }

    private static Matrix prepareExpectedMatrix() {

        Matrix expected = new Matrix(4, 4);
        expected.setAt(0, 1, 1);
        expected.setAt(0, 3, 1);
        expected.setAt(1, 0, 1);
        expected.setAt(2, 0, 1);
        expected.setAt(2, 1, 1);
        expected.setAt(3, 2, 1);
        return expected;
    }
}
