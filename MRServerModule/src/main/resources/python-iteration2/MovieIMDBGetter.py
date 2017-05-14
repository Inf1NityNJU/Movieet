#!/usr/bin/env python3

import re

import requests
from bs4 import BeautifulSoup

from iteration2 import MovieNameGetter

headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}

def getIMDBFromID(movieID):
    try:
        movieURL = 'https://www.amazon.com/dp/' + movieID
        page = requests.get(movieURL, headers=headers)
        htmlData = page.text

        soup = BeautifulSoup(htmlData, "html.parser")
        imdbLink = soup.find('div', idmovie='detail-bullets').find_all("li")[-1].a['href']
        imdbID = re.findall(r".*?/title/(.*?)/.*?", imdbLink)[0]

        omdbRequestURL = 'http://www.omdbapi.com/?i=' + imdbID + '&plot=full'
        json = requests.get(omdbRequestURL, headers=headers).text
        # print("From ID " + json)
        print(json)
        return json
    except:
        return None

def getIMDBFromName(movieName):
    try:
        omdbRequestURL = 'http://www.omdbapi.com/?t=' + movieName + '&plot=full'
        json = requests.get(omdbRequestURL, headers=headers).text
        # print("From Name " + json)
        print(json)
        return json
    except:
        return ''

idmovie = "B006H90TLI"
# idmovie = sys.argv[1]
if(getIMDBFromID(idmovie) == None):
    if(getIMDBFromName(MovieNameGetter.getNameByIDWithoutMoreInfo(idmovie)) == r''):
        print('')