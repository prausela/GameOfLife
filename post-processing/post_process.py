import pandas as pd
import math
import shutil
import os
import matplotlib.pyplot as plt
import numpy as np

def getMaxRadius(df, dimensions):
	boardSize = 1 + df['x'].max()
	maxRadius = 0
	for idx, cell in df.iterrows():
		if(cell['status'] == 1):
			x = cell['x']+0.5
			y = cell['y']+0.5
			if(dimensions == 2):
				radius = math.sqrt(math.pow(x - boardSize/2.0, 2) + math.pow(y - boardSize/2.0, 2))
			else:
				z = cell['z']
				radius = sqrt(math.pow(x - boardSize/2.0, 2) + math.pow(y - boardSize/2.0, 2) + math.pow(z - boardSize/2.0, 2))
			if(radius > maxRadius):
				maxRadius = radius
	return maxRadius

def main():
	filename = "./../output/evolution-"
	outputDir = "./../postProcess/"
	outputFilename = outputDir + "gameCellsState_"
	scalarsFilename = outputDir + "scalars.csv"
	try:
		shutil.rmtree(outputDir)
	except:
		print("")
	os.mkdir(outputDir)
	dimensions = 0
	simulations = 5
	perc = 5
	scalarsOutput = open(scalarsFilename, 'a+')
	scalarsOutput.write("genAlivePerc;iterations;massDiffPerc\n")
	while perc <= 100:
		output = open(outputFilename + str(perc) +".csv", 'a+')
		output.write("t;aliveCells;maxRadius\n")
		for i in range(0, simulations):
			t = 0
			inicialCells = 0
			finalCells = 0
			file = open(filename + str(perc) +"-" +str(i) +".txt", 'r')
			cells = int(file.readline())
			while(cells):
				file.readline()
				t_status = {
				    'x': [0] * cells,
				    'y': [0] * cells,
				    'z': [0] * cells,
				    'status': [0] * cells
				}
				for c in range(0, cells):
					currentLine = file.readline().replace("\n", "").split("\t")
					if(len(currentLine) == 6):
						dimensions = 3
						cell = {'x': int(currentLine[0]), 'y': int(currentLine[1]), 'z': int(currentLine[2]), 'status': int(currentLine[3])}
					else:
						dimensions = 2
						cell = {'x': int(currentLine[0]), 'y': int(currentLine[1]), 'status': int(currentLine[2])}
					t_status['x'][c] = cell['x']
					t_status['y'][c] = cell['y']
					if(dimensions == 3):
						t_status['z'][c] = cell['z']
					t_status['status'][c] = cell['status']

				#Prepare output
				df = pd.DataFrame(t_status)
				aliveCells = df['status'].sum()
				maxRadius = getMaxRadius(df, dimensions)
				output.write(str(t) +";" +str(aliveCells) +";" +str(maxRadius) +"\n")

				if(t==0):
					initialCells = aliveCells
				cells = file.readline()
				if(cells != ''):
					cells = int(cells)
				else:
					finalCells = aliveCells
				t+=1
			massDiffPerc = "NaN"
			if(initialCells != 0):
				massDiffPerc = 100 * (finalCells - initialCells)/initialCells
			iterations = t
			scalarsOutput.write(str(perc) +";" +str(iterations) +";" +str(massDiffPerc) +"\n")
		output.close()
		perc += 5
	scalarsOutput.close()
	print("DONE")

if __name__ == "__main__":
    main()