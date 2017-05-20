import pymysql.cursors
import json

path = "/Users/Kray/Desktop/data/TMDB/"
user = 'root'
password = 'songkuixi'
# path = "/mydata/moviereview/iteration3/"
# user = 'infinity'
# password = 'Infinity123!'
fkeyword = open(path + "keyword_ids_04_28_2017.json", "r")

config = dict(host='127.0.0.1',
              port=3306,
              user=user,
              password=password,
              db='MovieReview',
              charset='utf8',
              cursorclass=pymysql.cursors.DictCursor)
# 创建连接
connection = pymysql.connect(**config)


def createKeyword():
    print("create")
    try:
        with connection.cursor() as cursor:
            # 创建数据库
            createTMDBKeywordSQL = """CREATE TABLE IF NOT EXISTS `tmdb_keyword`(
                `tmdbkeywordid` INTEGER (255) PRIMARY KEY,
                `tmdbkeyword` VARCHAR (255) NOT NULL
            ) DEFAULT charset utf8;"""
            cursor.execute(createTMDBKeywordSQL)

            insertTMDBKeywordSQL = """INSERT INTO `tmdb_keyword` VALUES (%s, %s);"""
            failList = []
            for line in fkeyword.readlines():
                try:
                    jsonDict = json.loads(line)
                    cursor.execute(insertTMDBKeywordSQL,
                                   (jsonDict["id"], jsonDict["name"]))

                except:
                    failList.append(line)
                finally:
                    connection.commit()

    finally:
        connection.close()

    return

createKeyword()
