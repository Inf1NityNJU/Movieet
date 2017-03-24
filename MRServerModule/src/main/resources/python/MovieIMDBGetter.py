#!/usr/bin/env python3

import sys
import re
import requests
from bs4 import BeautifulSoup
import MovieNameGetter

headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}

def getIMDBFromID(movieID):
    try:
        movieURL = 'https://www.amazon.com/dp/' + movieID
        page = requests.get(movieURL, headers=headers)
        htmlData = page.text

        soup = BeautifulSoup(htmlData, "html.parser")
        imdbLink = soup.find('div', id='detail-bullets').find_all("li")[-1].a['href']
        imdbID = re.findall(r".*?/title/(.*?)/.*?", imdbLink)[0]

        omdbRequestURL = 'http://www.omdbapi.com/?i=' + imdbID + '&plot=full'
        json = requests.get(omdbRequestURL, headers=headers).text
        print(json)
        return json
    except:
        return None

def getIMDBFromName(movieName):
    try:
        omdbRequestURL = 'http://www.omdbapi.com/?t=' + movieName + '&plot=full'
        json = requests.get(omdbRequestURL, headers=headers).text
        print(json)
        return json
    except:
        return '{"Response":"False","Error":"Movie not found!"}'

# id = "B006H90TLI"
id = sys.argv[1]
if(getIMDBFromID(id) == None):
    if(getIMDBFromName(MovieNameGetter.getNameByIDWithoutMoreInfo(id)) == r'{"Response":"False","Error":"Movie not found!"}'):
        print("fail to load imdb")