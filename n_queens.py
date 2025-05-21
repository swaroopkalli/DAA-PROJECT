import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("nqueens_all_timings.csv")

plt.plot(df["N"], df["Time_NCSR(ms)"], label='NCSR Only', marker='^', color='blue')
plt.plot(df["N"], df["Time_MinConflict(ms)"], label='Min-Conflict Only', marker='s', color='red')
plt.plot(df["N"], df["Time_Hybrid(ms)"], label='Hybrid', marker='o', color='green')

plt.title("N-Queens Time Complexity Comparison")
plt.xlabel("Board Size (N)")
plt.ylabel("Execution Time (ms)")
plt.grid(True)
plt.legend()
plt.tight_layout()
plt.show()
