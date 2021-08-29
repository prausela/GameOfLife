import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

i = 5
while i <= 100:
    df = pd.read_csv("./../output/gameCellsState_" + str(i) + ".csv", sep=';')

    tsGroups = df.groupby(['t'])
    keys = [key for key, _ in tsGroups]

    plt.errorbar(   keys, 
                    tsGroups['aliveCells'].mean(), 
                    yerr = tsGroups['aliveCells'].std()*2, 
                    fmt = '-o',
                )
    plt.xticks(keys)
    plt.xlabel("t")
    plt.ylabel("Alive Cells")
    plt.savefig("images/gameCellsState" + str(i) + "_activeCells" + ".png")
    plt.close()
    plt.errorbar(   keys, 
                    tsGroups['maxRadius'].mean(), 
                    yerr = tsGroups['maxRadius'].std()*2, 
                    fmt = '-o',
                )
    plt.xticks(keys)
    plt.xlabel("t")
    plt.ylabel("Max Radius")
    plt.savefig("images/gameCellsState" + str(i) + "_maxRadius" + ".png")
    plt.close()
    i += 5