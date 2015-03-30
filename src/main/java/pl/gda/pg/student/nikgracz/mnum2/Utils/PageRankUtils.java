package pl.gda.pg.student.nikgracz.mnum2.Utils;


import pl.gda.pg.student.nikgracz.mnum2.Math.Matrix;

/**
 * Utility class for {@code PageRank}.
 */
public class PageRankUtils {

    /**
     * Utility class, should not be instantiated.
     */
    private PageRankUtils() {

    }

    public static void handlePagesWithoutLinks(Matrix adjacencyMatrix) {

        for (int i = 0; i < adjacencyMatrix.getSizeM(); i++) {
            if (MathUtils.sum(adjacencyMatrix.getRow(i)) == 0) {
                for (int j = 0; j < adjacencyMatrix.getSizeN(); j++) {
                    if (j != i) {
                        adjacencyMatrix.setAt(i, j, 1);
                    }
                }
            }
        }
    }

    public static Matrix prepareMatrixWithLinksCount(Matrix matrix) {

        Matrix diagonal = new Matrix(matrix.getSizeM(), matrix.getSizeN());

        for (int i = 0; i < matrix.getSizeM(); i++) {
            double connections = 0;
            for (int j = 0; j < matrix.getSizeN(); j++) {
                connections += matrix.getAt(i, j);
            }
            diagonal.setAt(i, i, 1 / connections);
        }

        return diagonal;
    }
}
