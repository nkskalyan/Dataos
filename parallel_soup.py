from bs4 import BeautifulSoup
import pickle
import urllib2
from joblib import Parallel, delayed

d = pickle.load(open('sel_prod'))

L=[]
def process(j):
		try:
			response = urllib2.urlopen(j,timeout = 5)
			html = response.read()
			soup = BeautifulSoup(html)
			for k in soup.find_all(attrs={'itemprop':'description'}):
				txt = k.get_text().replace('\n',' ')
				L.append((i,txt.encode('ascii','ignore')))
				return True
		except:
			print 'e'
			return False
		return False

for i in d:
		try:
			Parallel(n_jobs=10)(delayed(process)(j) for j in d[i])
		except:
			print 'er'
			continue

pickle.dump(L,open('Mega_dump','w'))			
