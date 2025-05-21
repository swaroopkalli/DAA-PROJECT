import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class BenchMark {
    public static void main(String[] args) {
        try {
            String input = JOptionPane.showInputDialog(
                null,
                "Enter maximum N (number of queens, min 4):",
                "N-Queens Benchmark",
                JOptionPane.QUESTION_MESSAGE
            );
            if (input == null) return;

            int maxN = Integer.parseInt(input.trim());
            if (maxN < 4) throw new NumberFormatException();

            try (PrintWriter writer = new PrintWriter("nqueens_all_timings.csv")) {
                writer.println("N,Time_NCSR(ms),Time_MinConflict(ms),Time_Hybrid(ms)");
                for (int N = 4; N <= maxN; N += Math.max(1, maxN / 10)) {
                    // NCSR only
                    NQueensSolver solverNCSR = new NQueensSolver(N);
                    long startNCSR = System.nanoTime();
                    solverNCSR.solveNCSROnly();
                    long endNCSR = System.nanoTime();
                    double timeNCSR = (endNCSR - startNCSR) / 1_000_000.0;

                    // Min-Conflict only
                    NQueensSolver solverMinConf = new NQueensSolver(N);
                    long startMinConf = System.nanoTime();
                    solverMinConf.solveMinConflictOnly();
                    long endMinConf = System.nanoTime();
                    double timeMinConf = (endMinConf - startMinConf) / 1_000_000.0;

                    // Hybrid (NCSR + Min-Conflict)
                    NQueensSolver solverHybrid = new NQueensSolver(N);
                    long startHybrid = System.nanoTime();
                    solverHybrid.solveHybrid();
                    long endHybrid = System.nanoTime();
                    double timeHybrid = (endHybrid - startHybrid) / 1_000_000.0;

                    writer.printf("%d,%.3f,%.3f,%.3f%n", N, timeNCSR, timeMinConf, timeHybrid);
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
                "Invalid input. Please enter a valid integer >= 4.",
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
