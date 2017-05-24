import pymysql.cursors
import json
import requests
import datetime
import random

# path = "/Users/Kray/Desktop/data/TMDB/"
# user = 'root'
# password = 'songkuixi'
path = "/mydata/moviereview/iteration3/"
user = 'infinity'
password = 'Infinity123!'
fmovie = open(path + "onlyid.json", "r")

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


def createLittleMovie():
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createTMDBMovieSQL = """CREATE TABLE IF NOT EXISTS `tmdb_movie_test`(
                `tmdbid` INTEGER (255) PRIMARY KEY,
                `tmdbtitle` VARCHAR (255) NOT NULL,
                `popularity` DECIMAL (10,6)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBMovieSQL)

            insertTMDBMovieSQL = """INSERT INTO `tmdb_movie_test` VALUES (%s, %s, %s);"""
            failList = []
            for line in fmovie.readlines():
                try:
                    jsonDict = json.loads(line)
                    if (jsonDict["video"] == "true" or jsonDict["adult"] == "true"):
                        continue

                    cursor.execute(insertTMDBMovieSQL,
                                   (jsonDict["id"], jsonDict["original_title"],
                                    jsonDict["popularity"]))

                except:
                    failList.append(line)
                finally:
                    connection.commit()


    finally:
        connection.close()

    return


def createMovie():
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createTMDBMovieSQL = """CREATE TABLE IF NOT EXISTS `tmdb_movie`(
                `tmdbid` INTEGER (255) PRIMARY KEY,
                `tmdbtitle` VARCHAR (255) NOT NULL,
                `tmdb_original_title` VARCHAR (255) NOT NULL,
                `popularity` DECIMAL (10,6),
                `imdbid` VARCHAR (10),
                `language` VARCHAR (255),
                `poster` VARCHAR (255),
                `plot` VARCHAR (1023),
                `release_date` DATE,
                `runtime` INTEGER (255)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBMovieSQL)

            createTMDBMovieGenreSQL = """CREATE TABLE IF NOT EXISTS `tmdb_movie_genre`(
                `tmdbid` INTEGER (255) NOT NULL,
                `tmdbgenreid` INTEGER (255) NOT NULL,
                 PRIMARY KEY(`tmdbid`, `tmdbgenreid`)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBMovieGenreSQL)

            # CONSTRAINT `fk_genre` FOREIGN KEY (`tmdbgenreid`) REFERENCES `tmdb_genre` (`tmdbgenreid`),
            # CONSTRAINT `fk_movie_genre` FOREIGN KEY (`tmdbid`) REFERENCES `tmdb_movie` (`tmdbid`)

            insertTMDBMovieSQL = """INSERT INTO `tmdb_movie` VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"""
            insertTMDBMovieGenreSQL = """INSERT INTO `tmdb_movie_genre` VALUES (%s, %s);"""
            failList = []
            count = 8651
            i = 0
            for line in fmovie.readlines():
                if line[0] != r'"':
                    continue

                i += 1
                if i < count:
                    continue

                try:
                    jsonDict = json.loads('{' + line.strip() + '}')
                    # if (jsonDict["video"] == "true" or jsonDict["adult"] == "true"):
                    #     continue

                    movieRequestURL = 'https://api.themoviedb.org/3/movie/' + str(
                        jsonDict["tmdbid"]) + '?api_key=543a5c347a7e1af9b6f8fcc1a15256d0&language=en-US'
                    movieDetailJson = json.loads(
                        requests.get(movieRequestURL, headers=getUserAgentHeader()).text)

                    if movieDetailJson["runtime"] == None:
                        runtime = None
                    else:
                        runtime = movieDetailJson["runtime"]

                    if movieDetailJson["release_date"] == None or movieDetailJson["release_date"] == "":
                        date = None
                    else:
                        date = datetime.datetime.strptime(movieDetailJson["release_date"], '%Y-%m-%d')

                    cursor.execute(insertTMDBMovieSQL,
                                   (jsonDict["tmdbid"], movieDetailJson["title"], movieDetailJson["original_title"],
                                    movieDetailJson["popularity"], movieDetailJson["imdb_id"],
                                    movieDetailJson["original_language"], movieDetailJson["poster_path"],
                                    movieDetailJson["overview"], date, runtime))

                    for genre in movieDetailJson["genres"]:
                        cursor.execute(insertTMDBMovieGenreSQL, (jsonDict["tmdbid"], genre["id"]))
                except:
                    failList.append(line)
                finally:
                    connection.commit()
            print(failList)
    finally:
        connection.close()

    return


createMovie()
#
# createLittleMovie()
