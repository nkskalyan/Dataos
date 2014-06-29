from matplotlib import pyplot

		
file = open('fraudulentSuppliers.txt','r')
fraudData= file.readlines()
factDict = {}
repeat = {}
for lines in fraudData:
	id = lines.split()[0]
	rating = float(lines.split()[1])
	if id in factDict.keys():
		factDict[id] = (repeat[id]*factDict[id]+ rating)/(repeat[id]+1) #Compute average for stream of data
		repeat[id] = repeat[id] + 1
	else:
		repeat[id] = 1
		factDict[id] = rating
print len(factDict)
		
file = open('factualSuppliers.txt','r')
fraudData= file.readlines()
for lines in fraudData:
	id = lines.split()[0]
	rating = float(lines.split()[1])
	if id in factDict.keys():
		factDict[id] = (repeat[id]*factDict[id]+ rating)/(repeat[id]+1) #Compute average for stream of data
		repeat[id] = repeat[id] + 1
	else:
		repeat[id] = 1
		factDict[id] = rating
		
print len(factDict)

		
merIDs = factDict.keys()		
merRatings = factDict.values()
print merRatings
positiveRatings = [rating for rating in merRatings if rating> 0 ]
print len(positiveRatings)
print len(merRatings)
merRatings=[len(positiveRatings),len(merRatings) - len(positiveRatings)]
merType =["Positive Reviewed","Negative Reviewed"]


#We need number of positive and negative reviews
pyplot.axis("equal")
pyplot.pie(
merRatings,
labels=merType,
autopct="%1.1f%%"
)
pyplot.title("Merchant Reviews: Positive and Negative")
pyplot.show()	