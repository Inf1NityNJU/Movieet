#!/usr/bin/python3

import sqlite3

connection = sqlite3.connect('IMDB.sqlite')

try:
    # cursor =  connection.execute("SELECT keywords.keyword FROM keywords, movies, movies_keywords "
    #                              "WHERE movies.title LIKE '%Zootopia%' "
    #                              "AND movies_keywords.idmovies == movies.idmovies "
    #                              "AND movies_keywords.idkeywords == keywords.idkeywords")
    cursor =  connection.execute("SELECT movies.idmovies, movies.title FROM movies "
                                 "WHERE movies.title LIKE '%Zootopia%'")
    # cursor =  connection.execute("SELECT movies_keywords.idkeywords, movies_keywords.idmovies FROM movies_keywords "
    #                              "WHERE movies_keywords.idmovies == 232588")
    for row in cursor:
        print(row)
finally:
    connection.close()
