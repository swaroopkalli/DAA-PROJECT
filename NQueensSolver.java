import java.util.*;

public class NQueensSolver {
    private int N;
    private int[] board;
    private Random random = new Random();

    public NQueensSolver(int N) {
        this.N = N;
        this.board = new int[N];
    }

    // Hybrid Adaptive Solver (Optimized)
    public void solveHybridAdaptive() {
        int limit = N / 4;
        int maxSteps = N * 20;
        int maxStagnantSteps = N;

        ncsrInitializationPartial(limit);

        int stepsSinceImprovement = 0;
        int previousConflicts = totalConflicts();

        for (int step = 0; step < maxSteps; step++) {
            List<Integer> conflictedCols = getConflictedColumns();
            if (conflictedCols.isEmpty()) return;

            int col = conflictedCols.get(random.nextInt(conflictedCols.size()));
            int originalRow = board[col];

            int minConflicts = Integer.MAX_VALUE;
            List<Integer> bestRows = new ArrayList<>();

            for (int row = 0; row < N; row++) {
                if (row == originalRow) continue;
                board[col] = row;
                int conflicts = countConflicts(col);
                if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    bestRows.clear();
                    bestRows.add(row);
                } else if (conflicts == minConflicts) {
                    bestRows.add(row);
                }
            }

            if (!bestRows.isEmpty()) {
                board[col] = bestRows.get(random.nextInt(bestRows.size()));
            } else {
                board[col] = originalRow;
            }

            int currentConflicts = totalConflicts();
            if (currentConflicts < previousConflicts) {
                stepsSinceImprovement = 0;
                previousConflicts = currentConflicts;
            } else {
                stepsSinceImprovement++;
            }

            if (stepsSinceImprovement >= maxStagnantSteps) {
                ncsrInitializationPartial(limit);
                stepsSinceImprovement = 0;
                previousConflicts = totalConflicts();
            }
        }
    }

    // NCSR Only
    public void solveNCSROnly() {
        for (int col = 0; col < N; col++) {
            int minConflicts = Integer.MAX_VALUE;
            List<Integer> bestRows = new ArrayList<>();
            for (int row = 0; row < N; row++) {
                board[col] = row;
                int conflicts = countConflicts(col);
                if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    bestRows.clear();
                    bestRows.add(row);
                } else if (conflicts == minConflicts) {
                    bestRows.add(row);
                }
            }
            board[col] = bestRows.get(random.nextInt(bestRows.size()));
        }
    }

    // Min-Conflicts Only (fully random init)
    public void solveMinConflictsOnly() {
        for (int col = 0; col < N; col++) {
            board[col] = random.nextInt(N);
        }

        int maxSteps = N * 20;
        for (int step = 0; step < maxSteps; step++) {
            List<Integer> conflictedCols = getConflictedColumns();
            if (conflictedCols.isEmpty()) return;

            int col = conflictedCols.get(random.nextInt(conflictedCols.size()));
            int minConflicts = Integer.MAX_VALUE;
            List<Integer> bestRows = new ArrayList<>();

            for (int row = 0; row < N; row++) {
                board[col] = row;
                int conflicts = countConflicts(col);
                if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    bestRows.clear();
                    bestRows.add(row);
                } else if (conflicts == minConflicts) {
                    bestRows.add(row);
                }
            }

            board[col] = bestRows.get(random.nextInt(bestRows.size()));
        }
    }

    // Partial NCSR Initialization
    private void ncsrInitializationPartial(int limit) {
        for (int col = 0; col < limit; col++) {
            int minConflicts = Integer.MAX_VALUE;
            List<Integer> bestRows = new ArrayList<>();
            for (int row = 0; row < N; row++) {
                board[col] = row;
                int conflicts = countConflicts(col);
                if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    bestRows.clear();
                    bestRows.add(row);
                } else if (conflicts == minConflicts) {
                    bestRows.add(row);
                }
            }
            board[col] = bestRows.get(random.nextInt(bestRows.size()));
        }

        for (int col = limit; col < N; col++) {
            board[col] = random.nextInt(N);
        }
    }

    private int totalConflicts() {
        int total = 0;
        for (int col = 0; col < N; col++) {
            total += countConflicts(col);
        }
        return total / 2;
    }

    private int countConflicts(int col) {
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            if (i == col) continue;
            if (board[i] == board[col] || Math.abs(board[i] - board[col]) == Math.abs(i - col)) {
                conflicts++;
            }
        }
        return conflicts;
    }

    private List<Integer> getConflictedColumns() {
        List<Integer> conflicted = new ArrayList<>();
        for (int col = 0; col < N; col++) {
            if (countConflicts(col) > 0)
                conflicted.add(col);
        }
        return conflicted;
    }

    public int[] getBoard() {
        return board;
    }
}
