#!/usr/bin/env python3

import sys
import re
import requests
from bs4 import BeautifulSoup
import json
import random

agents = [
    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0",
    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
    "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
    "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
    "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Avant Browser)",
    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"
]

headers = {
    'User-Agent': random.choice(agents)
}


def getUserAgentHeader():
    return headers


def getIMDBReviewWithNameAndYear(name, year, pageStart):
    resultList = []

    omdbRequestURL = 'http://www.omdbapi.com/?t=' + str(name) + '&y=' + str(year) + '&plot=full'

    result = requests.get("+".join(omdbRequestURL.split(" ")), headers=getUserAgentHeader()).text

    try:
        movieID = json.loads(result)["imdbID"]
    except:
        print("{'Response': 'False', 'Error': 'Movie not found!'}")
        return

    movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews?start=' + str(int(pageStart) * 10)
    moviePage = requests.get(movieURL, headers=getUserAgentHeader())
    htmlData = moviePage.text

    soup = BeautifulSoup(htmlData, "html.parser")

    list = soup.find("div", id="tn15content")

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
                               "content": content})
        except:
            continue
        finally:
            j += 1

    print(json.dumps(resultList))


# name = "Melanie's+Muses"
# year = 2016
# pageStart = 1
name = sys.argv[1]
year = sys.argv[2]
pageStart = sys.argv[3]
getIMDBReviewWithNameAndYear(name, year, pageStart)
