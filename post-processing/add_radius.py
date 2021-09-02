import pandas as pd
import math
import os
import matplotlib.pyplot as plt
import numpy as np

def getRadius(df, dimensions, cell):
	boardSize = 1 + df['x'].max()
	boardRadius = math.sqrt(math.pow((boardSize-1.0)/2.0, 2) * dimensions);
	x = cell['x']+0.5
	y = cell['y']+0.5
	if(dimensions == 2):
		radius = math.sqrt(math.pow(x - boardSize/2.0, 2) + math.pow(y - boardSize/2.0, 2))
	else:
		z = cell['z']
		radius = sqrt(math.pow(x - boardSize/2.0, 2) + math.pow(y - boardSize/2.0, 2) + math.pow(z - boardSize/2.0, 2))
	return radius/boardRadius

def main():
	outputDir = "./../postProcess/"
	outputFilename = outputDir + "evolution_with_radius.txt"
	try:
		os.mkdir(outputDir)
	except:
		print("")
	try:
		os.remove(outputFilename)
	except:
		print("")

	input = open("./../output/evolution.txt", 'r')
	output = open(outputFilename, 'a+')
	t = 0
	cells = int(input.readline())
	while(cells):
		input.readline()
		t_status = {
		    'x': [0] * cells,
		    'y': [0] * cells,
		    'z': [0] * cells,
		    'status': [0] * cells
		}
		for c in range(0, cells):
			currentLine = input.readline().replace("\n", "").split("\t")
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
		output.write(str(cells) + "\n\n")
		for idx, cell in df.iterrows():
			radius = getRadius(df, dimensions, cell)
			if(dimensions == 3):
				output.write(str(cell['x']) +"\t" +str(cell['y']) +"\t" +str(cell['z']) +"\t" +str(cell['status']) +"\t" +str(radius) +"\t" +str(1-radius) +"\n")
			else:
				output.write(str(cell['x']) +"\t" +str(cell['y']) +"\t" +str(cell['status']) +"\t" +str(radius) +"\t" +str(1-radius) +"\n")
		cells = input.readline()
		if(cells != ''):
			cells = int(cells)
		print(str(t) +" -> Cells is " +str(cells))
		t+=1
	input.close()
	output.close()

if __name__ == "__main__":
    main()