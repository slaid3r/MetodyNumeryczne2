package pl.gda.pg.student.nikgracz.mnum2.Utils;

import javafx.util.Pair;
import org.apache.commons.lang3.Validate;
import pl.gda.pg.student.nikgracz.mnum2.Math.Matrix;
import pl.gda.pg.student.nikgracz.mnum2.SNAP.SNAPGraph;

/**
 * Utility class for {@code Matrix}.
 */
public class MatrixUtils {

    /**
     * Utility class, should not be instantiated.
     */
    private MatrixUtils() {

    }

    /**
     * Converts given SNAPGraph to the adjacency matrix.
     *
     * @param graph  the graph to be converted
     * @return  the adjacency matrix for given graph
     */
    public static Matrix convertToMatrix(SNAPGraph graph) {

        Matrix matrix = new Matrix(graph.getNodes(), graph.getNodes());

        for (Pair<Integer, Integer> pair : graph.getGraph()) {
            matrix.setAt(pair.getKey(), pair.getValue(), 1);
        }

        return matrix;
    }

    /**
     * Multiplies the given matrices.
     *
     * @param A  the first matrix to multiply
     * @param B  the second matrix to multiply
     * @return  the result of multiplying
     */
    public static Matrix multiplyMatrices(Matrix A, Matrix B) {

        Validate.isTrue(A.getSizeN() == B.getSizeM(), "The number of columns in A must equal the number of rows in B!");

        Matrix result = new Matrix(A.getSizeM(), B.getSizeN());

        for (int i = 0; i < A.getSizeM(); i++) {
            for (int j = 0; j < B.getSizeN(); j++) {
                double value = 0;
                for (int k = 0; k < A.getSizeN(); k++) {
                    value += A.getAt(i, k) * B.getAt(k, j);
                }
                result.setAt(i, j, value);
            }
        }

        return result;
    }

    /**
     * Subtracts the second matrix from the first.
     * @param A  the first matrix
     * @param B  the second matrix
     * @return  the result of subtraction
     */
    public static Matrix substractMatrices(Matrix A, Matrix B) {

        Validate.isTrue(A.getSizeM() == B.getSizeM() && A.getSizeN() == B.getSizeN(), "Matrices must have the same size!");

        Matrix result = new Matrix(A.getSizeM(), A.getSizeN());
        for (int i = 0; i < A.getSizeM(); i++) {
            for (int j = 0; j < A.getSizeN(); j++) {
                result.setAt(i, j, A.getAt(i, j) - B.getAt(i, j));
            }
        }
        return result;
    }


}
