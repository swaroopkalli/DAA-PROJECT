import java.util.*;

public class NQueensSolver {
    private int N;
    private int[] board;
    private Random random = new Random();

    public NQueensSolver(int N) {
        this.N = N;
        this.board = new int[N];
    }

    // Solve using only NCSR (full initialization)
    public void solveNCSROnly() {
        ncsrInitialization();
    }

    // Solve using only Min-Conflict with random initialization
    public void solveMinConflictOnly() {
        randomInitialization();
        minimumConflicts();
    }

    // Solve using Hybrid: partial NCSR + Min-Conflict
    public void solveHybrid() {
        ncsrInitializationLimited();
        minimumConflicts();
    }

    public int[] getBoard() {
        return board;
    }

    private void ncsrInitialization() {
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

    // NCSR for first n/5 columns, rest random
    private void ncsrInitializationLimited() {
        int limit = N / 5;
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

    private void randomInitialization() {
        for (int col = 0; col < N; col++) {
            board[col] = random.nextInt(N);
        }
    }

    private void minimumConflicts() {
        int maxSteps = N * N;
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

    public void solve() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }
}
