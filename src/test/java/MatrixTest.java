import org.junit.Test;
import pl.gda.pg.student.nikgracz.Math.Matrix;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MatrixTest {

    private static final double[][] MATRIX_AS_ARRAY = new double[][] {  {1.2,  2.6, -0.1,  1.5,  13.15},
                                                                        {4.5,  9.8, -0.4,  5.7,  49.84},
                                                                        {0.1, -0.1, -0.3, -3.5, -14.08},
                                                                        {4.5, -5.2,  4.2, -3.4, -46.51}};

    private static final double[][] TRANSPOSED_MATRIX_AS_ARRAY = new double[][] {   {  1.2 ,   4.5 ,   0.1 ,   4.5 },
                                                                                    {  2.6 ,   9.8 , - 0.1 , - 5.2 },
                                                                                    {- 0.1 , - 0.4 , - 0.3 ,   4.2 },
                                                                                    {  1.5 ,   5.7 , - 3.5 , - 3.4 },
                                                                                    { 13.15,  49.84, -14.08, -46.51}};

    private static final List<Double> RESULT = Arrays.asList(-1.29983, 3.19989, -2.40029, 4.10003);
    private static final double EPSILON = 0.001;
    private static final int SIZE = 4;

    @Test
    public void shouldGetSize() {
        Matrix matrix = new Matrix(SIZE, SIZE + 1, MATRIX_AS_ARRAY);
        assertEquals(SIZE, matrix.getSizeM());
    }

    @Test
    public void shouldResolve() {

        Matrix matrix = new Matrix(SIZE, SIZE + 1, MATRIX_AS_ARRAY);

        List<Double> result = matrix.resolve();

        for (int i = 0; i < SIZE; i++) {
            assertTrue(Math.abs(result.get(i) - RESULT.get(i)) < EPSILON);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToResolveWithWrongMatrix() {
        Matrix matrix = new Matrix(SIZE, SIZE);
        matrix.resolve();
    }

    @Test
    public void shouldTranspose() {

        Matrix matrix = new Matrix(SIZE, SIZE + 1, MATRIX_AS_ARRAY);
        Matrix transposed = new Matrix(SIZE + 1, SIZE, TRANSPOSED_MATRIX_AS_ARRAY);

        assertEquals(transposed, matrix.transpose());
    }
}
