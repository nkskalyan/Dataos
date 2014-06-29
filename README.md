Dataos
======

Analysing data from BestBuy to provide better business intelligence.


DATASET:
Data has been obtained from BESTBUY website. We made use of Bestbuy api to obtain the product information. 
We downloaded data and had it stored.

As reviews and information from sellers was not available, we had to scrape the same. Our Code for scraping is in the following files:

parsing.py
read.py
soup.py

Tweets:
Tweets have been obtained from twitter live streaming api. Code for the same can be found in Recipes-For-Mining-Twitter.
Before you run it, make sure you update recipe__oauth_login.py with your own Twitter App Login details.

visualise.py file shows a visualisation of Merchants having positive reviews versus Merchants having negative reviews
printFraudMerchants.py uses data from reviews analysed using sentimental analysis to identify fraudulent merchants

Libraries and API Used:
Python BeautifulSoup
Python Urllib
BestBuy API
Wordnet 
Stanford POS Tagger