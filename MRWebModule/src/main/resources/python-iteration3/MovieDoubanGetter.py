#!/usr/bin/env python3

import requests
from iteration3 import UserAgentHelper
import json
from douban_client import DoubanClient

def getDoubanFromID(doubanID):
    try:
        doubanRequestURL = 'http://api.douban.com/v2/movie/subject/' + doubanID
        return requests.get(doubanRequestURL, headers=UserAgentHelper.getUserAgentHeader()).text
    except:
        return ''

def getDoubanFromIMDBID(imdbID):
    # client = DoubanClient(API_KEY, API_SECRET, your_redirect_uri, SCOPE)
    # client.movie.imdb(imdbID)
    return

print(json.loads(getDoubanFromID("1764796")))