# B = Budget
# R = Revenue
# P = Background Poster

import pymysql.cursors
import json
import requests
import datetime
import random

# user = 'root'
# password = 'songkuixi'
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
    'User-Agent': random.choice(agents)
}


def getUserAgentHeader():
    return headers


def updateMovieBRP():
    try:
        with connection.cursor() as cursor:
            # 创建数据库

            updateMovieSQL = """UPDATE `tmdb_movie` SET budget = %s, revenue = %s, background_poster = %s WHERE tmdbid = %s;"""
            selectMovieIDSQL = """SELECT tmdbid FROM `tmdb_movie` LIMIT %s, %s;"""

            count = 81509
            i = 45655
            while i < count:
                print(i)
                try:
                    cursor.execute(selectMovieIDSQL, (i, 1))
                    tmp = cursor.fetchone()
                    tmdbid = tmp["tmdbid"]

                    movieRequestURL = 'https://api.themoviedb.org/3/movie/' + str(
                        tmdbid) + '?api_key=543a5c347a7e1af9b6f8fcc1a15256d0&language=en-US'
                    movieDetailJson = json.loads(
                        requests.get(movieRequestURL, headers=getUserAgentHeader()).text)

                    budget = int(movieDetailJson["budget"])
                    revenue = int(movieDetailJson["revenue"])
                    bgposter = movieDetailJson["backdrop_path"]

                    print(budget, revenue, bgposter)

                    if budget == 0:
                        budget = None

                    if revenue == 0:
                        revenue = None

                    cursor.execute(updateMovieSQL,
                                   (budget, revenue, bgposter, tmdbid))
                except:
                    pass
                finally:
                    connection.commit()
                    i += 1


    finally:
        connection.close()

    return


updateMovieBRP()
