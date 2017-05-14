#!/usr/bin/env python3

import sys
import re
import requests
from bs4 import BeautifulSoup
import json

headers = {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}


def getIMDBReviewFromID(movieID, pageStart):
    # movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews'
    # moviePage = requests.get(movieURL, headers=headers)
    # htmlData = moviePage.text

    # soup = BeautifulSoup(htmlData, "html.parser")
    # list = soup.find("div", idmovie="tn15content")
    # totalNum = list.find_all("table")[2].text
    # pageNum = re.findall(".*?(.*?) review.*?", totalNum)[0]
    # page = 0

    resultList = []

    # while page < (int(pageNum) / 10 + 1):
    movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews?start=' + str(int(pageStart) * 10)
    moviePage = requests.get(movieURL, headers=headers)
    htmlData = moviePage.text

    soup = BeautifulSoup(htmlData, "html.parser")

    list = soup.find("div", idmovie="tn15content")

    reviewTitleList = list.find_all("div")
    reviewContentList = list.find_all("p")

    # remove useless <p>
    for tag in reviewContentList:
        if "*** This review may contain spoilers ***" in tag.text:
            reviewContentList.remove(tag)

    j = 0

    while j < len(reviewContentList) - 1:  # ignore the last one
        try:
            title = reviewTitleList[j * 2].find_all("h2")[0].text  # title
            author = reviewTitleList[j * 2].find_all("a")[1].text  # author
            avatar = reviewTitleList[j * 2].find_all("img")[0]['src']  # avatar

            try:
                userid = re.findall("/user/(.*?)/", reviewTitleList[j * 2].find_all("a")[0]['href'])[0]  # userid
            except:
                userid = "no user idmovie"

            try:
                match = re.search(r"(.*?) out of (.*?) people.*?",
                                  reviewTitleList[j * 2].find_all("small")[0].text)  # helpfulness
                a = match.group(1)
                b = match.group(2)
                helpfulness = a + "/" + b
            except:
                helpfulness = "0/0"

            try:
                score = reviewTitleList[j * 2].find_all("img")[1]['alt']  # score
            except:
                score = "0"

            if len(reviewTitleList[j * 2].find_all("small")) == 2:
                date = reviewTitleList[j * 2].find_all("small")[1].text  # date
            else:
                date = reviewTitleList[j * 2].find_all("small")[2].text  # date

            content = reviewContentList[j].text
            content = content.replace("\n", " ").strip()
            # .replace('\"', '\\\"')
            # .replace("\\", "\\\\")
            resultList.append({"title": title,
                               "author": author,
                               "avatar": avatar,
                               "score": score,
                               "helpfulness": helpfulness,
                               "date": date,
                               "content": content,
                               "userid": userid})
        except:
            # print("ERROR: ", page * 10 + j)
            continue
        finally:
            j += 1

        # page += 1

    # print(json.dumps(resultList))

# idmovie = "tt0942903"
# pageStart = 2
# idmovie = sys.argv[1]
# pageStart = sys.argv[2]
# getIMDBReviewFromID(idmovie, pageStart)
