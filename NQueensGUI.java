import javax.swing.*;
import java.awt.*;

public class NQueensGUI extends JFrame {

    private int N;
    private int[] board;
    private final int TILE_SIZE = 60;

    public NQueensGUI(int N) {
        this.N = N;
        setTitle("N-Queens Solver - Hybrid (NCSR + Minimum Conflicts)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        long startTime = System.nanoTime();
        NQueensSolver solver = new NQueensSolver(N);
        solver.solve();
        long endTime = System.nanoTime();

        this.board = solver.getBoard();

        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Solved %d-Queens in %.3f ms%n", N, duration);

        if (N <= 100) {
            BoardPanel panel = new BoardPanel();
            setContentPane(panel);
            setSize(N * TILE_SIZE, N * TILE_SIZE);
        } else {
            setSize(400, 100);
            JLabel label = new JLabel("Solved large N=" + N + " in " + duration + " ms");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            add(label);
        }
    }

    private class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    boolean isWhite = (row + col) % 2 == 0;
                    g.setColor(isWhite ? Color.WHITE : Color.GRAY);
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (board[col] == row) {
                        g.setColor(Color.RED);
                        g.fillOval(col * TILE_SIZE + 15, row * TILE_SIZE + 15, 30, 30);
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(N * TILE_SIZE, N * TILE_SIZE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String input = JOptionPane.showInputDialog("Enter N (number of queens, min 4):");
            if (input != null) {
                try {
                    int N = Integer.parseInt(input.trim());
                    if (N < 4) throw new NumberFormatException();
                    new NQueensGUI(N).setVisible(true);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter an integer ≥ 4.");
                }
            }
        });
    }
}
