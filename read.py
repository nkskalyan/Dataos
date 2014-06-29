import pickle
import json
lines=open('list').readlines()
d = {}
for line in lines:
	line = line.strip()
	if line:
		J = json.loads(open('pa/'+line).read())
		for P in J['products']:
			if P['customerReviewCount'] != None:
				if d.get(P['sellerId']):
					d[P['sellerId']].append(P)
				else:
					d[P['sellerId']] = [P]
f = open('sel_prod','w')
pickle.dump(d,f)
print d
f.close()
