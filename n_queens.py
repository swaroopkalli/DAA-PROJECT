import pandas as pd
import matplotlib.pyplot as plt

# Load the updated CSV file
df = pd.read_csv("nqueens_all_timings.csv")

# Plot each algorithm's timings
plt.figure(figsize=(10, 6))
plt.plot(df["N"], df["Time_NCSR(ms)"], label="NCSR Only", marker='o', color='blue')
plt.plot(df["N"], df["Time_MinConflict(ms)"], label="Min-Conflict", marker='s', color='green')
plt.plot(df["N"], df["Time_AdaptiveHybrid(ms)"], label="Adaptive Hybrid", marker='^', color='red')

# Customize the plot
plt.title("N-Queens Algorithm Time Comparison")
plt.xlabel("N (Number of Queens)")
plt.ylabel("Time (ms)")
plt.grid(True)
plt.legend()
plt.tight_layout()

# Show the plot
plt.show()
