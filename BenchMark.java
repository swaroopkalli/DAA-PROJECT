import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class BenchMark {
    public static void main(String[] args) {
        try {
            String inputN = JOptionPane.showInputDialog(
                null,
                "Enter max N (number of queens, min 5000):",
                "N-Queens Benchmark",
                JOptionPane.QUESTION_MESSAGE
            );
            if (inputN == null) return;
            int maxN = Integer.parseInt(inputN.trim());
            if (maxN < 5000) throw new NumberFormatException();

            List<String> results = new ArrayList<>();
            // CSV header includes all three methods
            results.add("N,Time_NCSR(ms),Time_MinConflict(ms),Time_AdaptiveHybrid(ms)");

            int step = 1000;

            for (int N = 0; N <= maxN; N += step) {
                if (N == 0) {
                    // Trivial case for 0 queens
                    results.add("0,0.000,0.000,0.000");
                    System.out.println("N=0 trivial case, time=0 ms for all methods");
                    continue;
                }

                // NCSR Only
                NQueensSolver solverNCSR = new NQueensSolver(N);
                long startNCSR = System.nanoTime();
                solverNCSR.solveNCSROnly();
                long endNCSR = System.nanoTime();
                double durationNCSR = (endNCSR - startNCSR) / 1_000_000.0;

                // Min-Conflict Only
                NQueensSolver solverMinConflict = new NQueensSolver(N);
                long startMinConflict = System.nanoTime();
                solverMinConflict.solveMinConflictsOnly();
                long endMinConflict = System.nanoTime();
                double durationMinConflict = (endMinConflict - startMinConflict) / 1_000_000.0;

                // Hybrid Adaptive
                NQueensSolver solverHybrid = new NQueensSolver(N);
                long startHybrid = System.nanoTime();
                solverHybrid.solveHybridAdaptive();
                long endHybrid = System.nanoTime();
                double durationHybrid = (endHybrid - startHybrid) / 1_000_000.0;

                System.out.printf("N=%d times => NCSR: %.3f ms, MinConflict: %.3f ms, Hybrid: %.3f ms%n",
                                  N, durationNCSR, durationMinConflict, durationHybrid);

                results.add(String.format("%d,%.3f,%.3f,%.3f",
                                          N, durationNCSR, durationMinConflict, durationHybrid));
            }

            try (PrintWriter writer = new PrintWriter("nqueens_all_timings.csv")) {
                for (String line : results) {
                    writer.println(line);
                }
            }

            JOptionPane.showMessageDialog(
                null,
                "Benchmark complete!\nResults saved to nqueens_all_timings.csv",
                "Done",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null,
                "Invalid input. Please enter a valid integer ≥ 5000.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "An error occurred: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
