import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class BenchMark {
    public static void main(String[] args) {
        try {
            String inputMin = JOptionPane.showInputDialog(
                null,
                "Enter minimum N (number of queens, min 5000):",
                "N-Queens Benchmark",
                JOptionPane.QUESTION_MESSAGE
            );
            if (inputMin == null) return;
            int minN = Integer.parseInt(inputMin.trim());
            if (minN < 5000) throw new NumberFormatException();

            String inputMax = JOptionPane.showInputDialog(
                null,
                "Enter maximum N (number of queens, max 100000):",
                "N-Queens Benchmark",
                JOptionPane.QUESTION_MESSAGE
            );
            if (inputMax == null) return;
            int maxN = Integer.parseInt(inputMax.trim());
            if (maxN < minN || maxN > 100000) throw new NumberFormatException();

            try (PrintWriter writer = new PrintWriter("nqueens_all_timings.csv")) {
                writer.println("N,Time_AdaptiveHybrid(ms)");
                for (int N = minN; N <= maxN; N += (maxN - minN) / 10) {
                    NQueensSolver solver = new NQueensSolver(N);

                    long start = System.nanoTime();
                    solver.solveHybridAdaptive();
                    long end = System.nanoTime();
                    double duration = (end - start) / 1_000_000.0;

                    System.out.printf("N=%d solved in %.3f ms%n", N, duration);
                    writer.printf("%d,%.3f%n", N, duration);
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
                "Invalid input. Please enter valid integers: min ≥ 5000, max ≤ 100000, max ≥ min.",
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
