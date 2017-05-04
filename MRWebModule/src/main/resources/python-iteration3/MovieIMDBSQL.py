#!/usr/bin/python3
# -*- coding: UTF-8 -*-

import pymysql.cursors
import json
from iteration3 import MovieIMDBGetter

config = dict(host='127.0.0.1', port=3306, user='root', password='songkuixi', db='MovieReview', charset='utf8mb4',
              cursorclass=pymysql.cursors.DictCursor)
# 创建连接
connection = pymysql.connect(**config)

# 执行sql语句
try:
    with connection.cursor() as cursor:

        imdbID = "tt0942903"
        movieIMDB = json.loads(MovieIMDBGetter.getIMDBFromID(imdbID))
        print(movieIMDB)

        sql = """INSERT INTO MovieIMDB VALUES (%s, %s, %s, %s)"""
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
                             movieIMDB["BoxOffice"],
                             ))
        # 获取查询结果
        result = cursor.fetchall()
    # 没有设置默认自动提交，需要主动提交，以保存所执行的语句
    connection.commit()

finally:
    connection.close()

