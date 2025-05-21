import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class NCSRBenchMark {
    public static void main(String[] args) {
        try {
            String input = JOptionPane.showInputDialog(
                null,
                "Enter maximum N (number of queens, min 4):",
                "NCSR Benchmark",
                JOptionPane.QUESTION_MESSAGE
            );
            if (input == null) return;

            int maxN = Integer.parseInt(input.trim());
            if (maxN < 4) throw new NumberFormatException();

            String csvFile = "ncsr_timing.csv";
            try (PrintWriter writer = new PrintWriter(csvFile)) {
                writer.println("N,Time(ms)");
                for (int N = 4; N <= maxN; N += Math.max(1, maxN / 10)) {
                    long start = System.nanoTime();
                    NQueensSolver solver = new NQueensSolver(N);
                    solver.solveNCSROnly();
                    long end = System.nanoTime();

                    double timeMs = (end - start) / 1_000_000.0;
                    writer.printf("%d,%.3f%n", N, timeMs);
                }
            }
            JOptionPane.showMessageDialog(
                null,
                "Benchmark complete!\nResults saved to ncsr_timing.csv",
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