from bs4 import BeautifulSoup
import pickle
import urllib2

d = pickle.load(open('sel_prod'))

f=open('id_text','w')
for i in d:
	for j in d[i]:
		try:
			response = urllib2.urlopen(j,timeout = 5)
			html = response.read()
			soup = BeautifulSoup(html)
			for k in soup.find_all(attrs={'itemprop':'description'}):
				print i,k.get_text()
				f.write(str(i)+" "+k.get_text().encode('ascii','ignore')+"\n\n..\n\n")
		except:
			continue
