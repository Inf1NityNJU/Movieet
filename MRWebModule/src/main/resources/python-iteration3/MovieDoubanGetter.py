#!/usr/bin/env python3
import pymysql.cursors
import requests
import json
import random
import time
import string

# path = "/Users/Kray/Desktop/data/TMDB/"
# user = 'root'
# password = 'songkuixi'
path = "/mydata/moviereview/iteration3/"
user = 'infinity'
password = 'Infinity123!'

config = dict(host='127.0.0.1',
              port=3306,
              user=user,
              password=password,
              db='MovieReview',
              charset='utf8',
              cursorclass=pymysql.cursors.DictCursor)
# 创建连接
connection = pymysql.connect(**config)

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
    'User-Agent': random.choice(agents),
    "Cookie": "bid=%s" % "".join(random.sample(string.ascii_letters + string.digits, 11))
}


def getUserAgentHeader():
    return headers


def getDoubanFromIMDBID(imdbID):
    try:
        doubanRequestURL = 'http://api.douban.com/v2/movie/search?q=' + imdbID + "&apikey=0df993c66c0c636e29ecbb5344252a4a"
        return json.loads(requests.get(doubanRequestURL,
                                       headers=getUserAgentHeader(),
                                       # proxies={"https": "123.206.185.186:8080"}
                                       ).text)
    except:
        return {}

def getDoubanFromTitle(title):
    title = "+".join(title.split(" "))
    try:
        doubanRequestURL = 'http://api.douban.com/v2/movie/search?q=' + title + "&apikey=0df993c66c0c636e29ecbb5344252a4a"
        return json.loads(requests.get(doubanRequestURL,
                                       headers=getUserAgentHeader(),
                                       # proxies={"https": "123.206.185.186:8080"}
                                       ).text)
    except:
        return {}

movieCount = 31278
i = 0
failList = []
try:
    with connection.cursor() as cursor:
        while i < movieCount:
            selectMovieIDSQL = """SELECT tmdbtitle, tmdbid FROM `tmdb_movie` WHERE doubanid IS NULL LIMIT %s, %s;"""
            cursor.execute(selectMovieIDSQL, (i, 1))
            result = cursor.fetchone()
            tmdbtitle = result["tmdbtitle"]
            tmdbid = result["tmdbid"]

            print(i, tmdbtitle)
            updateMovieSQL = """UPDATE `tmdb_movie` SET douban_distribution = %s, douban_score = %s, doubanid = %s, doubantitle = %s WHERE tmdbid = %s"""

            i += 1

            try:
                jsonDict = getDoubanFromTitle(tmdbtitle)
            except:
                failList.append(tmdbtitle)
                continue

            print(jsonDict)

            try:
                doubantitle = jsonDict["subjects"][0]["title"]
                doubanid = jsonDict["subjects"][0]["genreId"]
                rating = jsonDict["subjects"][0]['rating']
                scoreFR = rating['average']
                detail = rating['details']
                distribution = ''
                distribution = distribution + str(detail['1']) + ','
                distribution = distribution + str(detail['2']) + ','
                distribution = distribution + str(detail['3']) + ','
                distribution = distribution + str(detail['4']) + ','
                distribution += str(detail['5'])
                print(scoreFR, distribution, doubanid, doubantitle)

                cursor.execute(updateMovieSQL, (distribution, scoreFR, doubanid, doubantitle, tmdbid))
                connection.commit()

            except:
                failList.append(tmdbtitle)
                continue

            # time.sleep(random.randint(0, 1))
finally:
    connection.close()

print("Done")
print(failList)
