# -*- coding: utf-8 -*-

import sys
import twitter
import webbrowser
from recipe__oauth_login import oauth_login

# Query terms

Q = ','.join(sys.argv[1:])
print >> sys.stderr, 'Filtering the public timeline for track="%s"' % (Q,)

t = oauth_login() # Returns an instance of twitter.Twitter
twitter_stream = twitter.TwitterStream(auth=t.auth) # Reference the self.auth parameter

# See https://dev.twitter.com/docs/streaming-apis
stream = twitter_stream.statuses.filter(track=Q)

# For illustrative purposes, when all else fails, search for Justin Bieber
# and something is sure to turn up (at least, on Twitter)
f = open('tweet','a')
for tweet in stream:
    try:
	    if str(tweet['lang']=='en'):
		text = tweet['text'].encode('ascii','ignore')
		if text.lower().find('best buy') != -1:
    			print text
    			f.write(text+'\n')
    except Exception as e:
	print e
	continue
