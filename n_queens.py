import pandas as pd
import matplotlib.pyplot as plt

# Load the updated CSV file
df = pd.read_csv("nqueens_all_timings_15000.csv")

# Print the columns to check their exact names (optional)
print("Columns in CSV:", df.columns.tolist())

# Column names as per your CSV
col_ncsr = "Time_NCSR(ms)"
col_minconflict = "Time_MinConflict(ms)"
col_hybrid = "Time_Hybrid(ms)"

# Plotting all three curves directly since all columns exist
plt.figure(figsize=(10, 6))
plt.plot(df["N"], df[col_ncsr], label="NCSR Only", marker='o', color='blue')
plt.plot(df["N"], df[col_minconflict], label="Min-Conflict", marker='s', color='green')
plt.plot(df["N"], df[col_hybrid], label="Adaptive Hybrid", marker='^', color='red')

plt.title("N-Queens Algorithm Time Comparison")
plt.xlabel("N (Number of Queens)")
plt.ylabel("Time (ms)")
plt.grid(True)
plt.legend()
plt.tight_layout()

plt.show()
