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


def createDoubanMovie():
    try:
        with connection.cursor() as cursor:
            # 创建数据库

            updateDoubanCount = """UPDATE `tmdb_movie` SET douban_count = %s WHERE tmdbid = %s;"""
            selectMovieIDSQL = """SELECT tmdbid, douban_distribution FROM `tmdb_movie` LIMIT %s, %s;"""

            count = 81509
            i = 0
            while i < count:
                print(i)
                try:
                    cursor.execute(selectMovieIDSQL, (i, 1))
                    tmp = cursor.fetchone()
                    douban_distribution = tmp["douban_distribution"]
                    tmdbid = tmp["tmdbid"]


                    print(douban_distribution)
                    douban_count = 0
                    for num in douban_distribution.split(","):
                        douban_count += int(num)
                    print(douban_count)

                    cursor.execute(updateDoubanCount, (douban_count, tmdbid))
                except:
                    pass
                finally:
                    connection.commit()
                    i += 1


    finally:
        connection.close()

    return


createDoubanMovie()
