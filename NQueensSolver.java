import java.util.*;

public class NQueensSolver {
    private int N;
    private int[] board;
    private Random random = new Random();

    public NQueensSolver(int N) {
        this.N = N;
        this.board = new int[N];
    }

    public void solve() {
        ncsrInitialization(N / 5);  // Use NCSR for first n/5 columns
        minimumConflicts();
    }

    // Solve using only NCSR initialization
    public void solveNCSROnly() {
        ncsrInitialization(N); // Use NCSR for all columns
    }

    // Solve using only Minimum Conflicts
    public void solveMinConflictOnly() {
        // Fill the board randomly
        for (int col = 0; col < N; col++) {
            board[col] = random.nextInt(N);
        }
        minimumConflicts();
    }

    public int[] getBoard() {
        return board;
    }

    private void ncsrInitialization(int limit) {
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

        // Fill rest of the board randomly to allow min-conflict to work
        for (int col = limit; col < N; col++) {
            board[col] = random.nextInt(N);
        }
    }

    private void minimumConflicts() {
        int maxSteps = N * 10;
        for (int step = 0; step < maxSteps; step++) {
            List<Integer> conflicted = getConflictedColumns();
            if (conflicted.isEmpty()) return;

            int col = conflicted.get(random.nextInt(conflicted.size()));
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

            // Random reshuffling to escape local maxima every 200 steps
            if (step % 200 == 0) {
                randomReshuffle();
            }
        }
    }

    private void randomReshuffle() {
        int numColsToShuffle = Math.max(1, N / 20);
        for (int i = 0; i < numColsToShuffle; i++) {
            int col = random.nextInt(N);
            board[col] = random.nextInt(N);
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
            if (countConflicts(col) > 0) {
                conflicted.add(col);
            }
        }
        return conflicted;
    }
}
