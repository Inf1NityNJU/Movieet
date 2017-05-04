#!/usr/bin/env python3

import sys
import re
import requests
from bs4 import BeautifulSoup

headers = {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}


def getIMDBReviewCount(movieID):
    movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews'
    moviePage = requests.get(movieURL, headers=headers)
    htmlData = moviePage.text

    soup = BeautifulSoup(htmlData, "html.parser")
    list = soup.find("div", id="tn15content")
    tables = list.find_all("table")
    n = 0
    while n < len(tables):
        try:
            pageNum = re.findall(".*?(.*?) reviews in total.*?", tables[n].text)[0]
            print(pageNum)
        except:
            continue
        finally:
            n += 1


# id = "tt1372710"
id = sys.argv[1]
getIMDBReviewCount(id)
