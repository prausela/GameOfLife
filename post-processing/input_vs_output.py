import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

filename = "./../postProcess/scalars.csv"
df = pd.read_csv(filename, sep=';')
percGroups = df.groupby(['genAlivePerc'])
keys = [key for key, _ in percGroups]

plt.errorbar(   keys, 
                percGroups['iterations'].mean(), 
                yerr = percGroups['iterations'].std(),
                ecolor = "lightblue", 
                fmt = '-o',
                ms = 2
            )
plt.xlabel("Porcentaje de celdas generadas vivas (%)")
plt.ylabel("Iteraciones")
plt.ylim( bottom = 0)
#plt.ylim( top = max(df['iterations']))
plt.ylim( top = 500)
plt.xticks(
            np.arange(
                        5,
                        105,
                        step = 5
                    )
        )
plt.grid(color='grey', linestyle='-', linewidth=0.05)
plt.tight_layout()
plt.savefig("images/iterations.png", dpi = 200)
plt.close()

plt.errorbar(   keys, 
                percGroups['massDiffPerc'].mean(), 
                yerr = percGroups['massDiffPerc'].std(),
                ecolor = "lightblue", 
                fmt = '-o',
                ms = 2
            )
plt.xlabel("Porcentaje de celdas generadas vivas (%)")
plt.ylabel("Cambio de masa tras simulación (%)")
plt.xticks(
            np.arange(
                        5,
                        105,
                        step = 5
                    )
        )
plt.grid(color='grey', linestyle='-', linewidth=0.05)
plt.tight_layout()
plt.savefig("images/massChange.png", dpi = 200)
plt.close()