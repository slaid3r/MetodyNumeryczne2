package pl.gda.pg.student.nikgracz.mnum2;

import org.apache.commons.lang3.Validate;
import pl.gda.pg.student.nikgracz.mnum2.Math.Matrix;
import pl.gda.pg.student.nikgracz.mnum2.SNAP.SNAPGraph;
import pl.gda.pg.student.nikgracz.mnum2.Utils.MatrixUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Application main class.
 */
public class MetodyNumeryczne2 {

    /**
     * Application entry point.
     *
     * @param args  the array of arguments
     */
    public static void main(String[] args) throws IOException {

        Validate.isTrue(args.length > 0, "Must specify file name!");

        File file = new File(args[0]);

        double d = 0.85;

        SNAPGraph graph = new SNAPGraph(file);

        Matrix adjacencyMatrix = MatrixUtils.convertToMatrix(graph);

        handleNodesWithoutConnections(adjacencyMatrix);

        Matrix diag = prepareDiagonalMatrix(adjacencyMatrix);

        adjacencyMatrix.multiplyBy(d);

        Matrix subresult = MatrixUtils.substractMatrices(Matrix.identityMatrix(graph.getNodes()), MatrixUtils.multiplyMatrices(adjacencyMatrix, diag));

        double[] column = new double[graph.getNodes()];

        Arrays.fill(column, (1 - d) / graph.getNodes());

        subresult.addColumn(column);

        List<Double> result = subresult.resolve();

        for (Double x : result) {
            System.out.println(x);
        }
    }

    private static void handleNodesWithoutConnections(Matrix adjacencyMatrix) {
        for (int i = 0; i < adjacencyMatrix.getSizeM(); i++) {
            if (sum(adjacencyMatrix.getRow(i)) == 0) {
                for (int j = 0; j < adjacencyMatrix.getSizeN(); j++) {
                    if (j != i) {
                        adjacencyMatrix.setAt(i, j, 1);
                    }
                }
            }
        }
    }

    private static double sum(double[] row) {
        double result = 0;
        for (double value : row) {
            result += value;
        }
        return result;
    }

    private static Matrix prepareDiagonalMatrix(Matrix matrix) {
        Matrix diag = new Matrix(matrix.getSizeM(), matrix.getSizeN());

        for (int i = 0; i < matrix.getSizeM(); i++) {
            double connections = 0;
            for (int j = 0; j < matrix.getSizeN(); j++) {
                connections += matrix.getAt(i, j);
            }
            diag.setAt(i, i, 1 / connections);
        }

        return diag;
    }
}
