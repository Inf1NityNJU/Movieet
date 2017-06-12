import pymysql.cursors
import json
import requests
import re
import linecache
import datetime
from iteration3.TMDB import UserAgentHelper

path = "/Users/Kray/Desktop/data/TMDB/"
user = 'root'
password = 'songkuixi'
# path = "/mydata/moviereview/iteration3/"
# user = 'infinity'
# password = 'Infinity123!'
fmovie = open(path + "movie_ids_04_28_2017.json", "r")

config = dict(host='127.0.0.1',
              port=3306,
              user=user,
              password=password,
              db='MovieReview',
              charset='utf8',
              cursorclass=pymysql.cursors.DictCursor)
# 创建连接
connection = pymysql.connect(**config)

def createGenre():
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createTMDBGnreSQL = """CREATE TABLE IF NOT EXISTS `tmdb_genre`(
                `tmdbgenreid` INTEGER (255) PRIMARY KEY,
                `tmdbgenre_en` VARCHAR (255) NOT NULL,
                `tmdbgenre_cn` VARCHAR (255) NOT NULL
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBGnreSQL)

            insertTMDBGenreSQL = """INSERT INTO `tmdb_genre` VALUES (%s, %s, %s);"""

            genreRequestURL = r"https://api.themoviedb.org/3/genre/movie/list?api_key=543a5c347a7e1af9b6f8fcc1a15256d0&language=en_US"
            genreDict = json.loads(requests.get(genreRequestURL, headers=UserAgentHelper.getUserAgentHeader()).text)

            genreRequestURLCN = r"https://api.themoviedb.org/3/genre/movie/list?api_key=543a5c347a7e1af9b6f8fcc1a15256d0&language=zh_CN"
            genreDictCN = json.loads(requests.get(genreRequestURLCN, headers=UserAgentHelper.getUserAgentHeader()).text)

            failList = []
            for gD in genreDict["genres"]:
                # try:
                    temp = []
                    temp.append(gD["genreId"])
                    temp.append(gD["name"])

                    for gDCN in genreDictCN["genres"]:
                        if(gDCN["genreId"] == gD["genreId"]):
                            temp.append(gDCN["name"])
                            break

                    cursor.execute(insertTMDBGenreSQL, (temp[0], temp[1], temp[2]))
                # except:
                #     failList.append(gD)
                # finally:
                    connection.commit()

    finally:
        connection.close()
        print(failList)

    return

# createGenre()