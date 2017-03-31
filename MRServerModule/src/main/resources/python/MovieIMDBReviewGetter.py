#!/usr/bin/env python3

import sys
import re
import requests
from bs4 import BeautifulSoup

headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}

def getIMDBReviewFromID(movieID):
    movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews'
    moviePage = requests.get(movieURL, headers=headers)
    htmlData = moviePage.text

    soup = BeautifulSoup(htmlData, "html.parser")

    list = soup.find("div", id="tn15content")
    totalNum = list.find_all("table")[2].text
    pageNum = re.findall(".*?(.*?) review.*?", totalNum)[0]
    page = 0

    while page < (int(pageNum) / 10 + 1):
        movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews?start=' + str(page * 10)
        moviePage = requests.get(movieURL, headers=headers)
        htmlData = moviePage.text

        soup = BeautifulSoup(htmlData, "html.parser")

        list = soup.find("div", id="tn15content")

        reviewTitleList = list.find_all("div")
        reviewContentList = list.find_all("p")

        #remove useless <p>
        for tag in reviewContentList:
            if "*** This review may contain spoilers ***" in tag.text:
                reviewContentList.remove(tag)

        j = 0
        while j < len(reviewContentList) - 1:   #ignore the last one
            try:
                print(reviewTitleList[j * 2].find_all("h2")[0].text)    #title
                print(reviewTitleList[j * 2].find_all("a")[1].text)     #author

                if len(reviewTitleList[j * 2].find_all("small")) == 2:
                    print(reviewTitleList[j * 2].find_all("small")[0].text) #helpfulness
                    print(reviewTitleList[j * 2].find_all("small")[1].text) #date
                else:
                    print(reviewTitleList[j * 2].find_all("small")[0].text) #helpfulness
                    print(reviewTitleList[j * 2].find_all("small")[1].text) #place
                    print(reviewTitleList[j * 2].find_all("small")[2].text) #date

                print("----------------")
                print(reviewContentList[j].text.replace("\n", " ").strip())
                print("----------------")
            except:
                print("ERROR: ", page * 10 + j)
                continue
            finally:
                j += 1

        page += 1

# id = "tt0120772"
# id = sys.argv[1]
getIMDBReviewFromID(id)
