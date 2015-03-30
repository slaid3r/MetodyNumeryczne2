package pl.gda.pg.student.nikgracz.mnum2.Search;


import pl.gda.pg.student.nikgracz.mnum2.Math.Matrix;
import pl.gda.pg.student.nikgracz.mnum2.SNAP.SNAPGraph;
import pl.gda.pg.student.nikgracz.mnum2.Utils.MatrixUtils;
import pl.gda.pg.student.nikgracz.mnum2.Utils.PageRankUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageRank {

    private final SNAPGraph graph;
    private final double d;
    private long time;

    public PageRank(SNAPGraph graph, double d) {
        this.graph = graph;
        this.d = d;
        this.time = -1;
    }

    public Map<Integer, Double> rank() {

        long start = System.nanoTime();

        Map<Integer, Double> ranks = new LinkedHashMap<>();

        Matrix adjacencyMatrix = MatrixUtils.convertToMatrix(graph);
        PageRankUtils.handlePagesWithoutLinks(adjacencyMatrix);
        Matrix diagonal = PageRankUtils.prepareMatrixWithLinksCount(adjacencyMatrix);
        adjacencyMatrix.multiplyBy(d);
        Matrix multiplied = MatrixUtils.multiplyByDiagonal(adjacencyMatrix, diagonal);
        Matrix equationsSystem = MatrixUtils.substractMatrices(Matrix.identityMatrix(graph.getNodes()), multiplied);

        equationsSystem.addColumn(prepareColumn());

        Map<Integer, Double> result = equationsSystem.resolve();

        time = System.nanoTime() - start;

        return result;
    }

    private double[] prepareColumn() {
        double[] column = new double[graph.getNodes()];
        Arrays.fill(column, (1 - d) / graph.getNodes());
        return column;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
