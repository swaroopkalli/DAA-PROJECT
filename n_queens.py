import pandas as pd
import matplotlib.pyplot as plt

hybrid = pd.read_csv("hybrid_nqueens_timing.csv")
min_conflict = pd.read_csv("min_conflict_timing.csv")
ncsr = pd.read_csv("ncsr_timing.csv")

plt.plot(hybrid["N"], hybrid["Time(ms)"], label='Hybrid', marker='o', color='green')
plt.plot(min_conflict["N"], min_conflict["Time(ms)"], label='Min-Conflict Only', marker='s', color='red')
plt.plot(ncsr["N"], ncsr["Time(ms)"], label='NCSR Only', marker='^', color='blue')

plt.title("N-Queens Time Complexity Comparison")
plt.xlabel("Board Size (N)")
plt.ylabel("Execution Time (ms)")
plt.grid(True)
plt.legend()
plt.tight_layout()
plt.show()
