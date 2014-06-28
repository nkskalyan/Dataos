import urllib2
from bs4  import BeautifulSoup
import pickle
import re
import json

idDict = pickle.load(open('sel_prod','rb'))
merchantList =[]
for id in idDict.iterkeys():
	obj ={}
	response = urllib2.urlopen('http://www.bestbuy.com/site/olspage.jsp?id=pcat17089&type=page&sellerId='+id)
	html = response.read()
	soup = BeautifulSoup(html)
	obj['id']=id
	obj['name'] = re.sub('\n','',soup.h1.get_text())
	obj['img'] = soup.find('img')['src']
	merchantList.append(obj)
open("merchants.json","w").write(json.dumps(merchantList))
print len(merchantList)
'''
for id in idList:
	response = urllib2.urlopen('http://www.bestbuy.com/site/olspage.jsp?id=pcat17089&type=page&sellerId='+id)
	html = response.read()
	soup = BeautifulSoup(html)
	title = soup.h1.get_text()
	img = soup.find('img')['src']
'''