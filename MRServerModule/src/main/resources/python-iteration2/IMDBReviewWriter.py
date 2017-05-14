import re
import requests
from bs4 import BeautifulSoup
import json
import time

headers = {
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}

# path = "/Users/Kray/Desktop/PythonHelper/"
path = "/mydata/moviereview/PythonHelper/"

# f = open(path + "individualMovie.txt", "r")
f2 = open(path + "movieIMDBReview.txt", "a")
f3 = open(path + "failIMDBRevie.txt", "a")

imdbids = []


def getIMDBReviewFromID(movieID, pageStart):
    page = 0
    print(movieID, pageStart)
    # pageNum = getIMDBReviewCount(movieID)
    # print(movieID, pageNum)

    # if (pageNum == "Timeout2"):
    #     f2.write("Count Timeout " + movieID + "\n")
    #     return

    # while page < (int(pageNum) / 10 + 1):
    try:
        movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews?start=' + pageStart
        moviePage = requests.get(movieURL, timeout=20, headers=headers)
        htmlData = moviePage.text

        soup = BeautifulSoup(htmlData, "html.parser")

        list = soup.find("div", idmovie="tn15content")

        reviewTitleList = list.find_all("div")
        reviewContentList = list.find_all("p")

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
                    userid = re.findall("/user/(.*?)/", reviewTitleList[j * 2].find_all("a")[0]['href'])[
                        0]  # userid
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

                f2.write("imdbID:" + movieID + "#KRAYC#")
                f2.write(json.dumps({"title": title,
                                     "author": author,
                                     "avatar": avatar,
                                     "score": score,
                                     "helpfulness": helpfulness,
                                     "date": date,
                                     "content": content,
                                     "userid": userid}))
                f2.write("\n")
                f2.flush()
            except:
                continue
            finally:
                j += 1

    except:
        print("reading timeout: ")

        f3.write("reading timeout: " + movieID + "#" + str(page * 10) + "\n")

    # finally:
    #     page += 1


def getIMDBReviewCount(movieID):
    movieURL = 'https://www.imdb.com/title/' + movieID + '/reviews'
    try:
        moviePage = requests.get(movieURL, timeout=10, headers=headers)
        htmlData = moviePage.text

        soup = BeautifulSoup(htmlData, "html.parser")
        list = soup.find("div", idmovie="tn15content")
        tables = list.find_all("table")
        n = 0
        while n < len(tables):
            try:
                pageNum = re.findall(".*?(.*?) reviews in total.*?", tables[n].text)[0]
                return pageNum
            except:
                continue
            finally:
                n += 1
    except:
        return "Timeout2"


# 2nd: complete
ft = open(path + "tempFile.txt", "r")
for line in ft.readlines():
    imdbid = line[0: 9]
    page = line[10:]
    getIMDBReviewFromID(imdbid, page)

# 1st: all of them

# for line in f.readlines():
#     imdbid = line[-10: -1]
#     if imdbid not in imdbids:
#         imdbids.append(imdbid)
#
# print(len(imdbids))
# for imdbid in imdbids:
#     getIMDBReviewFromID(imdbid, 0)
