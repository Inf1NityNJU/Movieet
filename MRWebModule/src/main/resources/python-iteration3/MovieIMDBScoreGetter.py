#!/usr/bin/env python3
import pymysql.cursors
import requests
import re
import random
import string
from bs4 import BeautifulSoup

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


def findIMDBScore(imdbid):
    try:
        url = 'http://www.imdb.com/title/' + imdbid + '/ratings'
        response = requests.get(url, headers=getUserAgentHeader())
        response.encoding = 'utf-8'
        html = response.text

        soup = BeautifulSoup(html, "html.parser")

        matchResult = re.match("([0-9]*?) IMDb users have given a weighted average vote of (.*?) / 10",
                               soup.find("div", id="tn15content").find_all("p")[0].text)
        imdb_count = matchResult.group(1)
        imdb_score = matchResult.group(2)

        table = soup.table

        trs = table.find_all("tr")

        i = 10
        imdb_distribution = ""
        while i >= 1:
            imdb_distribution += trs[i].td.string
            if i != 1:
                imdb_distribution += ','
            i -= 1

        print(imdb_score, imdb_count, imdb_distribution)
        return {"scoreFR": imdb_score, "count": imdb_count, "distribution": imdb_distribution}
    except:
        return {}


movieCount = 81509
i = 12398
failList = []
try:
    with connection.cursor() as cursor:
        while i < movieCount:
            try:
                selectMovieIDSQL = """SELECT imdbid FROM `tmdb_movie` LIMIT %s, %s;"""
                cursor.execute(selectMovieIDSQL, (i, 1))
                result = cursor.fetchone()
                imdbid = result["imdbid"]

                print(i, imdbid)
                updateMovieSQL = """UPDATE `tmdb_movie` SET imdb_distribution = %s, imdb_score = %s, imdb_count = %s WHERE imdbid = %s"""

                i += 1

                imdbResult = findIMDBScore(imdbid)
                print(imdbResult)

                try:
                    cursor.execute(updateMovieSQL,
                                   (imdbResult["distribution"], imdbResult["scoreFR"], imdbResult["count"], imdbid))
                    connection.commit()

                except:
                    failList.append(imdbid)
                    continue
            except:
                continue

                # time.sleep(random.randint(0, 1))
finally:
    connection.close()

print("Done")
print(failList)
