package pl.gda.pg.student.nikgracz.mnum2.Math.SNAP;


import javafx.util.Pair;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.gda.pg.student.nikgracz.mnum2.SNAP.SNAPGraph;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class SNAPGraphTest {

    private static SNAPGraph graph;

    File file = new File(getClass().getClassLoader().getResource("testSnapGraph.txt").getFile());

    @BeforeClass
    public static void prepareClass() {

        Set<Pair<Integer, Integer>> values = new LinkedHashSet<>();
        values.add(new Pair<>(0, 2));
        values.add(new Pair<>(2, 4));
        values.add(new Pair<>(3, 1));
        values.add(new Pair<>(4, 2));
        values.add(new Pair<>(1, 3));
        values.add(new Pair<>(2, 3));

        graph = new SNAPGraph(5, values);
    }

    @Test
    public void shouldCreate() throws IOException {
        SNAPGraph created = new SNAPGraph(file);
        assertEquals(graph, created);
    }
}
