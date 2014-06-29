import json
import heapq
factDict = {}
repeat = {}
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

#We have about 100 merchants, so we take 5 
fraudList = heapq.nlargest(5, factDict, key=factDict.get)

print fraudList

mercList = json.loads(open("merchants.json").read())
#print mercList

5 Fraudulent Merchants:
for merID in fraudList:
	for merchant in mercList:
		if merchant[u'id']==unicode(merID):
			print merchant[u'name']
