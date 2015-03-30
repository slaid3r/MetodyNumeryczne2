package pl.gda.pg.student.nikgracz.mnum2;

import org.apache.commons.lang3.Validate;
import pl.gda.pg.student.nikgracz.mnum2.Math.Matrix;
import pl.gda.pg.student.nikgracz.mnum2.SNAP.SNAPGraph;
import pl.gda.pg.student.nikgracz.mnum2.Search.PageRank;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Application main class.
 */
public class MetodyNumeryczne2 {

    private static Logger LOGGER = Logger.getLogger(MetodyNumeryczne2.class.getName());

    /**
     * Application entry point.
     *
     * @param args  the array of arguments
     */
    public static void main(String[] args) throws IOException {

        if (args.length > 0) {

            File file = new File(args[0]);

            PageRank page = new PageRank(new SNAPGraph(file), 0.85);

            Map<Integer, Double> result = page.rank();

            for (Map.Entry<Integer, Double> entry : result.entrySet()) {
                System.out.println("x" + entry.getKey() + " = " + entry.getValue());
            }

            System.out.println("Time used: " + page.getTime() / 1000000000.0);

        } else {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                int m = scanner.nextInt();
                int n = scanner.nextInt();

                Matrix matrix = new Matrix(m, n);

                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        Validate.isTrue(scanner.hasNext(), "Invalid input!");
                        double value = scanner.nextDouble();
                        matrix.setAt(i, j, value);
                        System.out.println("Put " + value + " at " + i + "," + j);
                    }
                }

                Map<Integer, Double> result = matrix.resolve();

                for (Map.Entry<Integer, Double> entry : result.entrySet()) {
                    System.out.println("x" + entry.getKey() + " = " + entry.getValue());
                }
            }
        }

    }
}
