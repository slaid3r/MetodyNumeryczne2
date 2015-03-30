package pl.gda.pg.student.nikgracz.mnum2.Search;


import pl.gda.pg.student.nikgracz.mnum2.Math.Matrix;
import pl.gda.pg.student.nikgracz.mnum2.SNAP.SNAPGraph;
import pl.gda.pg.student.nikgracz.mnum2.Utils.MatrixUtils;
import pl.gda.pg.student.nikgracz.mnum2.Utils.PageRankUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageRank {

    private final SNAPGraph graph;
    private final double d;

    public PageRank(SNAPGraph graph, double d) {
        this.graph = graph;
        this.d = d;
    }

    public Map<Integer, Double> rank() {

        Map<Integer, Double> ranks = new LinkedHashMap<>();

        Matrix adjacencyMatrix = MatrixUtils.convertToMatrix(graph);
        PageRankUtils.handlePagesWithoutLinks(adjacencyMatrix);
        Matrix diagonal = PageRankUtils.prepareMatrixWithLinksCount(adjacencyMatrix);
        adjacencyMatrix.multiplyBy(d);
        Matrix multiplied = MatrixUtils.multiplyByDiagonal(adjacencyMatrix, diagonal);
        Matrix equationsSystem = MatrixUtils.substractMatrices(Matrix.identityMatrix(graph.getNodes()), multiplied);

        equationsSystem.addColumn(prepareColumn());

        List<Double> result = equationsSystem.resolve();

        for (Double x : result) {
            System.out.println(x);
        }

        return ranks;
    }

    private double[] prepareColumn() {
        double[] column = new double[graph.getNodes()];
        Arrays.fill(column, (1 - d) / graph.getNodes());
        return column;
    }

}
