import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

filename = "./../postProcess/gameCellsState_"
tStep = 0
maxT = 0
i = 5
while i <= 100:
    df = pd.read_csv(filename + str(i) + ".csv", sep=';')

    tsGroups = df.groupby(['t'])
    keys = [key for key, _ in tsGroups]
    if(len(keys) > maxT):
        maxT = len(keys)
    if len(keys) < 0:
        tempStep = 1 
    else:
        tempStep = int(len(keys)/10)
    if(tempStep > tStep):
        tStep = tempStep
    plt.plot(keys, tsGroups['aliveCells'].mean(), label=""+str(i)+"%")
    i += 15
plt.xlabel("Iteraciones")
plt.ylabel("Celdas Vivas")
plt.ylim(bottom = 0)
plt.xticks(np.arange(0, maxT+1, step = tStep))
plt.tight_layout()
plt.legend()
plt.savefig("images/timelineCells.png", dpi = 400)
plt.close()

tStep = 0
i = 5
while i <= 100:
    df = pd.read_csv(filename + str(i) + ".csv", sep=';')
    tsGroups = df.groupby(['t'])
    keys = [key for key, _ in tsGroups]
    if(len(keys) > maxT):
        maxT = len(keys)
    if int(len(keys)/10) == 0:
        tempStep = 1 
    else:
        tempStep = int(len(keys)/10)
    if(tempStep > tStep):
        tStep = tempStep
    plt.plot(keys, tsGroups['maxRadius'].mean(), label=""+str(i)+"%")
    i += 15
plt.xlabel("Iteraciones")
plt.ylabel("Radio del Patrón (celdas)")
plt.ylim(bottom = 0)
plt.xticks(np.arange(0, maxT+1, step = tStep))
plt.tight_layout()
plt.legend()
plt.savefig("images/timelineRadius.png", dpi = 400)
plt.close()