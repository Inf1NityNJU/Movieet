import pymysql.cursors
import json
import re
import linecache
import datetime

path = "/Users/Kray/Desktop/data/"
user = 'root'
password = 'songkuixi'
# path = "/mydata/moviereview/iteration3/"
# user = 'infinity'
# password = 'Infinity123!'
fdirectors = open(path + "directors.json", "r")
fratings = open(path + "ratings.json", "r")
fmovies = open(path + "movies.json", "r")
factors = open(path + "actors.json", "r")
fgenres = open(path + "genres.json", "r")
fkeywords = open(path + "keywords.json", "r")
freleasedates = open(path + "release-dates.json", "r")
fcountries = open(path + "countries.json", "r")

config = dict(host='127.0.0.1',
              port=3306,
              user=user,
              password=password,
              db='MovieReview',
              charset='utf8mb4',
              cursorclass=pymysql.cursors.DictCursor)
# 创建连接
connection = pymysql.connect(**config)


def createDirectors():
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createDirectorSql = """CREATE TABLE IF NOT EXISTS `director`(
                `iddirector` VARCHAR (255) PRIMARY KEY
            );"""
            cursor.execute(createDirectorSql)
            createMovieDirectorSql = """CREATE TABLE IF NOT EXISTS `is_director`(
                `iddirector` VARCHAR (255),
                `idmovie` VARCHAR (255),
                `role` VARCHAR (255),
                 PRIMARY KEY (`iddirector`, `idmovie`)
            );"""
            cursor.execute(createMovieDirectorSql)

            insertDirectorSql = """INSERT INTO `director` VALUES (%s);"""
            insertMovieDirectorSql = """INSERT INTO `direct` VALUES (%s, %s, %s);"""
            failList = []
            for line in fdirectors.readlines():
                try:
                    jsonDict = json.loads(line)
                    name = "".join(jsonDict["id"].split(","))
                    cursor.execute(insertDirectorSql, name)

                    for role in jsonDict["roles"]:
                        # { means a TV / episode
                        if role["title"].find("{") != -1:
                            continue
                        title = role["title"]  # 电影 id
                        role = role["role"]
                        print(name, title, role)
                        cursor.execute(insertMovieDirectorSql, (name, title, role))
                except:
                    failList.append(line)
                finally:
                    connection.commit()


    finally:
        connection.close()


def createMovies():
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createMovieSql = """CREATE TABLE IF NOT EXISTS `movie`(
                `idmovie` VARCHAR (255) PRIMARY KEY,
                `title` VARCHAR (255) NOT NULL,
                `year` VARCHAR (4) NOT NULL,
                `kind` VARCHAR (15) NOT NULL,
                `genre` VARCHAR (255) NOT NULL,
            );"""
            cursor.execute(createMovieSql)

            insertMovieSql = """INSERT INTO `movie` VALUES (%s, %s, %s, %s);"""
            failList = []
            for line in fmovies.readlines():
                try:
                    jsonDict = json.loads(line)
                    kind = jsonDict["kind"]
                    id = jsonDict["id"]
                    title = jsonDict["title"]
                    year = jsonDict["year"][0]
                    if kind == "episode":
                        print("episode, skip")
                        continue
                    print(id, title, year, kind)
                    cursor.execute(insertMovieSql, (id, title, year, kind))
                except:
                    failList.append(line)
                finally:
                    connection.commit()


    finally:
        connection.close()


def createRatings():
    try:
        with connection.cursor() as cursor:
            createRatingSql = """CREATE TABLE IF NOT EXISTS `rating`(
                            `idmovie` VARCHAR (255) PRIMARY KEY,
                            `votesFR` INT (10) NOT NULL,
                            `rank` DECIMAL (2, 1) NOT NULL,
                            `distribution` VARCHAR (10) NOT NULL
                        );"""
            cursor.execute(createRatingSql)
            insertRatingSql = """INSERT INTO `rating` VALUES (%s, %s, %s, %s);"""
            failList = []
            for line in fratings.readlines():
                try:
                    jsonDict = json.loads(line)
                    kind = jsonDict["kind"]
                    if kind == "episode":
                        print("episode, skip")
                        continue
                    id = jsonDict["id"]  # 电影 id
                    para = jsonDict["rating"]
                    votesFR = para["votesFR"]
                    rank = para["rank"]
                    distribution = para["distribution"]
                    print(id, votesFR, rank, distribution)
                    cursor.execute(insertRatingSql, (id, votesFR, rank, distribution))
                except:
                    failList.append(line)
                    print("fail")
                finally:
                    connection.commit()


    finally:
        connection.close()


def createActors():
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createActorSql = """CREATE TABLE IF NOT EXISTS `actor`(
                `idactor` VARCHAR (255) PRIMARY KEY
            );"""
            cursor.execute(createActorSql)
            createMovieActorSql = """CREATE TABLE IF NOT EXISTS `is_actor`(
                `idactor` VARCHAR (255),
                `idmovie` VARCHAR (255),
                `character` VARCHAR (255),
                 PRIMARY KEY (`idactor`, `idmovie`)
            );"""
            cursor.execute(createMovieActorSql)

            insertActorSql = """INSERT INTO `actor` VALUES (%s);"""
            insertMovieActorSql = """INSERT INTO `is_actor` VALUES (%s, %s, %s);"""
            failList = []
            lineNum = 1
            for line in factors.readlines():
                lineNum += 1
                # print(lineNum)
                if lineNum < 3994078:
                    continue
                else:
                    try:
                        jsonDict = json.loads(line)
                        name = "".join(jsonDict["id"].split(","))
                        cursor.execute(insertActorSql, name)
                    except:
                        failList.append(line)
                    finally:
                        for role in jsonDict["roles"]:
                            # { means a TV / episode
                            if role["title"].find("{") != -1:
                                # print("episode, skip")
                                continue
                            title = role["title"]  # 电影 id
                            try:
                                character = role["character"]
                            except:
                                character = None
                            print(name, title, character)
                            try:
                                cursor.execute(insertMovieActorSql, (name, title, character))
                            except:
                                pass
                            finally:
                                connection.commit()


    finally:
        connection.close()


def createGenres():
    try:
        with connection.cursor() as cursor:
            createGenreSql = """CREATE TABLE IF NOT EXISTS `genre`(
                            `idgenre` VARCHAR (255) PRIMARY KEY
                        );"""
            cursor.execute(createGenreSql)
            createMovieGenreSql = """CREATE TABLE IF NOT EXISTS `is_genre`(
                            `idmovie` VARCHAR (255),
                            `idgenre` VARCHAR (255),
                            PRIMARY KEY (`idmovie`, `idgenre`)
                        );"""
            cursor.execute(createMovieGenreSql)
            insertGenreSql = """INSERT INTO `genre` VALUES (%s);"""
            insertMovieGenreSql = """INSERT INTO `is_genre` VALUES (%s, %s);"""
            failList = []
            for line in fgenres.readlines():
                try:
                    jsonDict = json.loads(line)
                    kind = jsonDict["kind"]
                    if kind == "episode":
                        print("episode, skip")
                        continue
                    id = jsonDict["id"]  # 电影 id
                    genres = jsonDict["genres"]
                    for genre in genres:
                        try:
                            cursor.execute(insertGenreSql, genre)
                        except:
                            pass
                        print(id, genre)
                        cursor.execute(insertMovieGenreSql, (id, genre))
                except:
                    failList.append(line)
                    print("fail")
                finally:
                    connection.commit()

    finally:
        connection.close()


def createKeywords():
    try:
        with connection.cursor() as cursor:
            createKeywordSql = """CREATE TABLE IF NOT EXISTS `keyword`(
                            `idkeyword` VARCHAR (255) PRIMARY KEY
                        );"""
            cursor.execute(createKeywordSql)
            createMovieKeywordSql = """CREATE TABLE IF NOT EXISTS `is_keyword`(
                            `idmovie` VARCHAR (255),
                            `idkeyword` VARCHAR (255),
                            PRIMARY KEY (`idmovie`, `idkeyword`)
                        );"""
            cursor.execute(createMovieKeywordSql)
            insertKeywordSql = """INSERT INTO `keyword` VALUES (%s);"""
            insertMovieKeywordSql = """INSERT INTO `is_keyword` VALUES (%s, %s);"""
            failList = []
            for line in fkeywords.readlines():
                try:
                    jsonDict = json.loads(line)
                    kind = jsonDict["kind"]
                    if kind == "episode":
                        print("episode, skip")
                        continue
                    keywords = jsonDict["keywords"]
                    id = jsonDict["id"]  # 电影 id
                    for keyword in keywords:
                        try:
                            cursor.execute(insertKeywordSql, keyword)
                        except:
                            pass
                        print(id, keyword)
                        cursor.execute(insertMovieKeywordSql, (id, keyword))
                except:
                    failList.append(line)
                    print("fail")
                finally:
                    connection.commit()


    finally:
        connection.close()

def createReleaseDate():
    try:
        with connection.cursor() as cursor:
            createDateSql = """CREATE TABLE IF NOT EXISTS `date`(
                            `iddate` DATE PRIMARY KEY
                        );"""
            cursor.execute(createDateSql)
            createMovieDateSql = """CREATE TABLE IF NOT EXISTS `is_release_date`(
                            `idmovie` VARCHAR (255),
                            `iddate` DATE,
                            `country` VARCHAR (255),
                            PRIMARY KEY (`idmovie`, `iddate`)
                        );"""
            cursor.execute(createMovieDateSql)
            insertDateSql = """INSERT INTO `date` VALUES (%s);"""
            insertMovieDateSql = """INSERT INTO `is_release_date` VALUES (%s, %s, %s);"""
            failList = []
            for line in freleasedates.readlines():
                try:
                    jsonDict = json.loads(line)
                    kind = jsonDict["kind"]
                    if kind == "episode":
                        print("episode, skip")
                        continue
                    releaseDates = jsonDict["release_dates"]
                    id = jsonDict["id"]  # 电影 id
                    for date in releaseDates:
                        try:
                            country = date["country"]
                        except:
                            country = None
                        try:
                            releasedate = datetime.datetime.strptime(date["date"], '%d %B %Y')
                        except:
                            releasedate = 0
                        print(id, country, releasedate)
                        try:
                            cursor.execute(insertDateSql, releasedate)
                        except:
                            pass
                        try:
                            cursor.execute(insertMovieDateSql, (id, releasedate, country))
                        except:
                            pass
                except:
                    failList.append(line)
                    print("fail")
                finally:
                    connection.commit()

    finally:
        connection.close()

def createCountries():
    try:
        with connection.cursor() as cursor:
            createCountrySql = """CREATE TABLE IF NOT EXISTS `country`(
                            `idcountry` VARCHAR PRIMARY KEY
                        );"""
            cursor.execute(createCountrySql)
            createMovieCountrySql = """CREATE TABLE IF NOT EXISTS `is_country`(
                            `idmovie` VARCHAR (255),
                            `idcountry` VARCHAR (255),
                            PRIMARY KEY (`idmovie`, `idcountry`)
                        );"""
            cursor.execute(createMovieCountrySql)
            insertCountrySql = """INSERT INTO `country` VALUES (%s);"""
            insertMovieCountrySql = """INSERT INTO `is_country` VALUES (%s, %s);"""
            failList = []
            for line in freleasedates.readlines():
                try:
                    jsonDict = json.loads(line)
                    kind = jsonDict["kind"]
                    if kind == "episode":
                        print("episode, skip")
                        continue
                    countries = jsonDict["countries"]
                    id = jsonDict["id"]  # 电影 id
                    for country in countries:
                        print(id, country)
                        try:
                            cursor.execute(insertCountrySql, country)
                        except:
                            pass
                        try:
                            cursor.execute(insertMovieCountrySql, (id, country))
                        except:
                            pass
                except:
                    failList.append(line)
                    print("fail")
                finally:
                    connection.commit()
    finally:
        connection.close()

# createDirectors()
# createRatings()
# createMovies()
# createActors()
# createGenres()
# createKeywords()
# createReleaseDate()
createCountries()
