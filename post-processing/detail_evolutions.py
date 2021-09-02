import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

i = 5
while i <= 100:
    df = pd.read_csv("./../output/gameCellsState_" + str(i) + ".csv", sep=';')

    tsGroups = df.groupby(['t'])
    keys = [key for key, _ in tsGroups]
    if int(len(keys)/10) == 0:
        tStep = 1 
    else:
        tStep = int(len(keys)/10)

    plt.errorbar(   keys, 
                    tsGroups['aliveCells'].mean(), 
                    yerr = tsGroups['aliveCells'].std(),
                    ecolor = "lightblue", 
                    fmt = '-o',
                    ms = 2
                )
    plt.xlabel("t")
    plt.ylabel("Alive Cells")
    plt.ylim( bottom = 0)
    plt.xticks(
                np.arange(
                            0, 
                            len(keys), 
                            step = tStep
                        )
            )
    plt.savefig("images/gameCellsState" + str(i) + "_activeCells" + ".png", dpi = 200)
    plt.close()
    
    
    plt.errorbar(   keys, 
                    tsGroups['maxRadius'].mean(), 
                    yerr = tsGroups['maxRadius'].std(), 
                    ecolor = "lightblue",
                    fmt = '-o',
                    ms = 2
                )
    plt.ylim( bottom = 0)
    plt.xlabel("t")
    plt.ylabel("Max Radius")
    plt.xticks(
                np.arange(
                            0, 
                            len(keys), 
                            step = tStep
                        )
            )
    plt.savefig("images/gameCellsState" + str(i) + "_maxRadius" + ".png", dpi = 300)
    plt.close()
    i += 5