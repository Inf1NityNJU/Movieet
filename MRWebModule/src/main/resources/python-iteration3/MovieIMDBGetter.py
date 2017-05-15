#!/usr/bin/env python3

import requests
from iteration3 import UserAgentHelper

def getIMDBFromID(imdbID):
    try:
        omdbRequestURL = 'http://www.omdbapi.com/?i=' + imdbID + '&plot=full'
        return requests.get(omdbRequestURL, headers=UserAgentHelper.getUserAgentHeader()).text
    except:
        return ''