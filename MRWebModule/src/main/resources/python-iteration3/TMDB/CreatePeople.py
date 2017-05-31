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
fpeoplelittle = open(path + "person_ids_04_28_2017.json", "r")

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


def createLittlePeople():
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createTMDBPeopleSQL = """CREATE TABLE IF NOT EXISTS `tmdb_people_test`(
                `tmdbpeopleid` INTEGER (255) PRIMARY KEY,
                `tmdbname` VARCHAR (255) NOT NULL,
                `popularity` DECIMAL (10,6)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBPeopleSQL)

            insertTMDBPeopleSQL = """INSERT INTO `tmdb_people_test` VALUES (%s, %s, %s);"""
            failList = []
            for line in fpeoplelittle.readlines():
                try:
                    jsonDict = json.loads(line)

                    if jsonDict["adult"] == "true" or float(jsonDict["popularity"]) < 0.00001:
                        continue

                    print(jsonDict["id"], jsonDict["name"], jsonDict["popularity"])
                    cursor.execute(insertTMDBPeopleSQL,
                                   (jsonDict["id"], jsonDict["name"], jsonDict["popularity"]))

                except:
                    failList.append(line)
                finally:
                    connection.commit()


    finally:
        connection.close()

    return


def createPeople():
    try:
        with connection.cursor() as cursor:

            selectMovieIDSQL = """SELECT tmdbid FROM `tmdb_movie` LIMIT %s, %s;"""

            # 创建数据库
            createTMDBActorSQL = """CREATE TABLE IF NOT EXISTS `tmdb_actor`(
                `tmdbpeopleid` INTEGER (255) PRIMARY KEY,
                `name` VARCHAR (255) NOT NULL,
                `profile` VARCHAR (255),
                `popularity` DECIMAL (10,6)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBActorSQL)

            createTMDBDirectorSQL = """CREATE TABLE IF NOT EXISTS `tmdb_director`(
                `tmdbpeopleid` INTEGER (255) PRIMARY KEY,
                `name` VARCHAR (255) NOT NULL,
                `profile` VARCHAR (255),
                `popularity` DECIMAL (10,6)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBDirectorSQL)

            createTMDBMovieActorSQL = """CREATE TABLE IF NOT EXISTS `tmdb_movie_actor`(
                `tmdbid` INTEGER (255) NOT NULL,
                `tmdbpeopleid` INTEGER (255) NOT NULL,
                `character` VARCHAR (255),
                `order` INTEGER (255),
                 PRIMARY KEY(`tmdbid`, `tmdbpeopleid`)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBMovieActorSQL)

            createTMDBMovieDirectorSQL = """CREATE TABLE IF NOT EXISTS `tmdb_movie_director`(
                `tmdbid` INTEGER (255) NOT NULL,
                `tmdbpeopleid` INTEGER (255) NOT NULL,
                 PRIMARY KEY(`tmdbid`, `tmdbpeopleid`)
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBMovieDirectorSQL)

            insertTMDBActorSQL = """INSERT INTO `tmdb_actor` VALUES (%s, %s, %s, %s);"""
            insertTMDBDirectorSQL = """INSERT INTO `tmdb_director` VALUES (%s, %s, %s, %s);"""
            insertTMDBMovieActorSQL = """INSERT INTO `tmdb_movie_actor` VALUES (%s, %s, %s, %s);"""
            insertTMDBMovieDirectorSQL = """INSERT INTO `tmdb_movie_director` VALUES (%s, %s);"""

            findPeoplePopularitySQL = """SELECT popularity FROM `tmdb_people_test` WHERE tmdbpeopleid = %s"""

            failList = []
            count = 81509
            i = 0

            while i < count:
                print("No. ", i)
                try:
                    cursor.execute(selectMovieIDSQL, (i, 1))
                    result = cursor.fetchone()
                    tmdbid = result["tmdbid"]

                    print("Id: ", tmdbid)

                    peopleRequestURL = 'https://api.themoviedb.org/3/movie/' + str(
                        tmdbid) + '/credits?api_key=543a5c347a7e1af9b6f8fcc1a15256d0'
                    peopleDetailJson = json.loads(
                        requests.get(peopleRequestURL, headers=getUserAgentHeader()).text)

                    actors = peopleDetailJson["cast"]
                    directors = peopleDetailJson["crew"]

                    # 插入演员
                    for actorDict in actors[0:5]:
                        tmdbpeopleid = actorDict["id"]
                        name = actorDict["name"]
                        profile = actorDict["profile_path"]

                        try:
                            cursor.execute(findPeoplePopularitySQL, (tmdbpeopleid))
                            popularity1 = cursor.fetchone()
                            popularity = float(popularity1["popularity"])
                            character = actorDict["character"]
                            order = actorDict["order"]

                            try:
                                cursor.execute(insertTMDBActorSQL, (tmdbpeopleid, name, profile, popularity))
                                connection.commit()
                                print("Success Insert Actor") 
                            except:
                                print("Fail Insert Actor") 
                                pass

                            try:
                                cursor.execute(insertTMDBMovieActorSQL, (tmdbid, tmdbpeopleid, character, order))
                                connection.commit()
                                print("Success Insert Movie Actor") 
                            except:
                                print("Fail Insert Movie Actor") 
                                pass


                        except:
                            pass

                    # 插入导演
                    for directorDict in directors:
                        if directorDict["job"] == "Director":
                            tmdbpeopleid = directorDict["id"]
                            name = directorDict["name"]
                            profile = directorDict["profile_path"]
                            try:
                                cursor.execute(findPeoplePopularitySQL, (tmdbpeopleid))
                                popularity1 = cursor.fetchone()
                                popularity = float(popularity1["popularity"])

                                try:
                                    cursor.execute(insertTMDBDirectorSQL, (tmdbpeopleid, name, profile, popularity))
                                    connection.commit()
                                    print("Success Insert Director")
                                except:
                                    print("Fail Insert Director")
                                    pass

                                try:
                                    cursor.execute(insertTMDBMovieDirectorSQL, (tmdbid, tmdbpeopleid))
                                    connection.commit()
                                    print("Success Insert Movie Director")
                                except:
                                    print("Fail Insert Movie Director")
                                    pass

                            except:
                                pass
                except:
                    pass

                i += 1

            print(failList)
    finally:
        connection.close()

    return


# createLittlePeople()
createPeople()
