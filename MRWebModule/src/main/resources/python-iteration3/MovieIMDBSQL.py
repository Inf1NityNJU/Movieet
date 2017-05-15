#!/usr/bin/python3
# -*- coding: UTF-8 -*-

import pymysql.cursors
import json
import re
import random
import requests
from datetime import datetime, date, time

path = "/Users/Kray/Desktop/PythonHelper/iteration3/"
user = 'root'
password = 'songkuixi'
# path = "/mydata/moviereview/iteration3/"
# user = 'infinity'
# password = 'Infinity123!'
f = open(path + "failIMDBIDs.txt", "a")


def getIMDBFromID(imdbID):
    try:
        omdbRequestURL = 'http://www.omdbapi.com/?i=' + imdbID + '&plot=full'
        return requests.get(omdbRequestURL, headers=getUserAgentHeader()).text
    except:
        return ''


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


config = dict(host='127.0.0.1',
              port=3306,
              user=user,
              password=password,
              db='MovieReview',
              charset='utf8mb4',
              cursorclass=pymysql.cursors.DictCursor)
# 创建连接
connection = pymysql.connect(**config)

deleteSql = """DROP TABLE MovieReview.MovieIMDB;"""

createSql = """CREATE TABLE IF NOT EXISTS `MovieIMDB` (
`imdbID` VARCHAR (9) NOT NULL,
`Title` VARCHAR (100) NOT NULL,
`Year` INTEGER (4) NOT NULL,
`Language` VARCHAR (50),
`Country` varchar (50),
`Genre` VARCHAR (30),
`Released` VARCHAR (20),
`Actors` VARCHAR (100),
`Awards` VARCHAR (50),
`Runtime` INTEGER (3),
`Writer` VARCHAR (100),
`Plot` VARCHAR (1000),
`Rated` VARCHAR (20),
`Director` VARCHAR (100),
`Type` VARCHAR (20),
`Poster` VARCHAR (200),
PRIMARY KEY (`imdbID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;"""

# 执行sql语句

try:
    with connection.cursor() as cursor:

        # try:
        # cursor.execute(deleteSql)
        # finally:
        cursor.execute(createSql)

        failIMDBList = []

        i = 1
        while i < 9999999:
            imdbID = "tt%07d" % i
            print(imdbID)
            try:
                movieIMDB = json.loads(getIMDBFromID(imdbID))
            except:
                f.write(imdbID + "\n")
                f.flush()
                i += 1
                continue

            for k in movieIMDB:
                if movieIMDB[k] == "N/A":
                    movieIMDB[k] = None
                if k == 'Runtime':
                    try:
                        movieIMDB[k] = int(str(re.findall('\d*', movieIMDB[k])[0]))
                    except:
                        movieIMDB[k] = None
                if k == 'Year':
                    try:
                        movieIMDB[k] = int(movieIMDB[k])
                    except:
                        movieIMDB[k] = None
            print(movieIMDB)

            try:
                sql = """INSERT INTO `MovieIMDB` VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"""
                # 执行sql语句，进行查询
                # sql = 'SELECT * FROM Movie'
                cursor.execute(sql, (imdbID,
                                     movieIMDB["Title"],
                                     movieIMDB["Year"],
                                     movieIMDB["Language"],
                                     movieIMDB["Country"],
                                     movieIMDB["Genre"],
                                     movieIMDB["Released"],
                                     movieIMDB["Actors"],
                                     movieIMDB["Awards"],
                                     movieIMDB["Runtime"],
                                     movieIMDB["Writer"],
                                     movieIMDB["Plot"],
                                     movieIMDB["Rated"],
                                     movieIMDB["Director"],
                                     movieIMDB["Type"],
                                     movieIMDB["Poster"]
                                     ))
                # 获取查询结果
                result = cursor.fetchall()
                # 没有设置默认自动提交，需要主动提交，以保存所执行的语句
                connection.commit()
            except:
                f.write(imdbID + "\n")
                f.flush()
            finally:
                i += 1

finally:
    connection.close()
