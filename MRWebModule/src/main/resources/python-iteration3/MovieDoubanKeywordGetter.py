#!/usr/bin/env python3
import pymysql.cursors
import requests
import json
import random
import time
import string
import re

# path = "/Users/Kray/Desktop/data/TMDB/"
# user = 'root'
# password = 'songkuixi'
path = "/mydata/moviereview/iteration3/"
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
    'User-Agent': random.choice(agents),
    "Cookie": "bid=%s" % "".join(random.sample(string.ascii_letters + string.digits, 11))
}


def getUserAgentHeader():
    return headers


def get_proxy():
    proxies = ["202.137.5.165:8080", "115.207.36.150:808", "185.80.41.45:8080", "190.205.250.167:8080",
               "61.191.41.130:80", "112.95.206.243:9999", "54.208.131.39:80", "113.69.252.75:808",
               "212.237.23.233:1189", "182.43.232.214:808", "202.143.122.25:8080", "123.163.160.252:808",
               "182.90.13.107:80", "171.13.37.230:808", "122.245.64.188:808", "123.117.195.161:8118",
               "89.26.253.57:8080", "47.52.18.182:80", "218.64.93.12:808", "94.176.183.204:8080", "203.190.14.34:8080",
               "91.120.7.237:8080", "189.211.198.181:3128", "121.226.175.221:808", "5.1.73.11:3128",
               "112.66.60.57:8998", "31.131.67.76:8080", "114.239.149.185:808", "221.217.34.87:9000",
               "110.73.2.170:8123", "195.49.187.49:8080", "122.245.64.252:808", "212.237.21.130:1189",
               "106.80.50.208:8998", "175.155.24.7:808", "115.213.200.215:808", "180.254.58.150:8080",
               "103.58.73.246:8080", "138.201.63.123:31288", "154.119.82.170:8080", "120.43.182.115:8998",
               "202.56.203.40:80", "116.226.69.141:9797", "88.191.33.53:3128", "162.243.28.189:8080",
               "216.198.170.70:8080", "219.216.110.229:8998", "200.37.231.66:8080", "183.128.42.59:808",
               "183.128.65.174:808", "5.160.196.211:8080", "115.215.69.151:808", "119.5.159.217:808", "138.68.233.9:80",
               "114.239.248.240:808", "167.114.235.41:3128", "190.147.208.143:8080", "218.67.57.187:8998",
               "195.9.6.50:8080", "114.239.144.147:808", "94.200.103.99:3128", "101.51.50.53:8080", "113.58.233.54:808",
               "85.202.11.47:3128", "24.37.151.46:8080", "117.43.0.39:808", "36.106.214.62:8998", "110.73.2.105:8123",
               "218.64.92.243:808", "171.13.36.102:808", "113.122.106.243:9000", "200.122.209.110:8080",
               "183.149.49.211:808", "201.73.151.12:3128", "120.37.216.198:808", "36.231.57.244:3128",
               "5.160.114.54:8080", "121.226.161.224:808", "27.18.147.199:8998", "178.219.41.140:3333",
               "119.136.199.85:9797", "122.245.67.39:808", "218.73.128.109:808", "113.251.170.114:8998",
               "221.230.72.221:80", "181.119.21.250:3128", "107.178.4.109:8080", "59.42.41.130:808",
               "182.99.246.139:808", "218.29.111.106:9999", "188.130.230.40:8081", "109.73.32.198:8080",
               "172.104.41.90:8080", "203.81.71.115:8080", "221.229.46.35:808", "180.118.242.227:808",
               "123.163.19.133:808", "191.102.245.35:8080", "103.212.33.57:3128", "5.160.114.106:8080"]
    # url = "http://www.xicidaili.com"
    # req = requests.get(url, headers=getUserAgentHeader()).text
    # print(req)
    # IP = re.compile('<td>(\d+)\.(\d+)\.(\d+)\.(\d+)</td>\s*<td>(\d+)</td>')
    # proxy_ip = IP.findall(req)
    # for each in proxy_ip:
    #     proxies.append(":".join([(".".join(each[0:4])), each[4]]))
    return proxies


def change_proxy():
    proxy = random.choice(get_proxy())
    while proxy is None:
        proxy = random.choice(get_proxy())
    return proxy


def getDoubanFromDoubanID(doubanID):
    doubanRequestURL = 'https://api.douban.com/v2/movie/' + str(doubanID) + "?apikey=0df993c66c0c636e29ecbb5344252a4a"
    while True:
        try:
            proxy = change_proxy()
            print("using IP : ", proxy)
            result = requests.get(doubanRequestURL,
                                  headers=getUserAgentHeader(),
                                  proxies=proxy,
                                  timeout=10,
                                  ).text
            print(result)
            break
        except:
            continue
    return json.loads(result)


movieCount = 81509
i = 26175
failList = []
try:
    with connection.cursor() as cursor:
        while i < movieCount:
            try:
                selectMovieIDSQL = """SELECT tmdbid, doubanid FROM `tmdb_movie` LIMIT %s, %s;"""
                cursor.execute(selectMovieIDSQL, (i, 1))
                result = cursor.fetchone()
                doubanID = result["doubanid"]
                tmdbid = result["tmdbid"]

                print(i, tmdbid, doubanID)

                insertKeywordSQL = """INSERT INTO `tmdb_keyword`(keyword_cn, keyword_en) VALUES (%s, %s)"""
                insertMovieKeywordSQL = """INSERT INTO `tmdb_movie_keyword` VALUES (%s, %s)"""
                searchKeywordSQL = """SELECT MIN(keywordid) FROM `tmdb_keyword` WHERE keyword_cn = %s"""

                insertCountrySQL = """INSERT INTO `tmdb_country`(countryname) VALUES (%s)"""
                insertMovieCountrySQL = """INSERT INTO `tmdb_movie_country` VALUES (%s, %s)"""
                searchCountrySQL = """SELECT MIN(countryid) FROM `tmdb_country` WHERE countryname = %s"""

                updateMovieSQL = """UPDATE `tmdb_movie` SET plot_cn = %s WHERE tmdbid = %s"""

                i += 1

                # try:
                jsonDict = getDoubanFromDoubanID(str(doubanID))
                print(jsonDict)
                # except:
                #     failList.append(doubanID)
                #     continue

                # 国家
                try:
                    for country in jsonDict["attrs"]["country"]:
                        try:
                            cursor.execute(searchCountrySQL, country)
                            sqlCountry = cursor.fetchone()
                            if sqlCountry["MIN(countryid)"] is None:
                                cursor.execute(insertCountrySQL, country)
                                connection.commit()

                            cursor.execute(searchCountrySQL, country)
                            sqlCountry = cursor.fetchone()
                            print("Country: ", sqlCountry)
                            if sqlCountry is not None:
                                countryid = sqlCountry["MIN(countryid)"]
                                cursor.execute(insertMovieCountrySQL, (countryid, tmdbid))
                                connection.commit()
                            else:
                                failList.append(country)
                        except:
                            pass
                except:
                    pass

                # 中文情节
                try:
                    plotcn = jsonDict["summary"]
                    cursor.execute(updateMovieSQL, (plotcn, tmdbid))
                    connection.commit()
                except:
                    pass

                # 关键字
                try:
                    for keyword in jsonDict["tags"]:
                        keyword = keyword["name"]
                        try:
                            cursor.execute(searchKeywordSQL, keyword)
                            sqlWord = cursor.fetchone()

                            if sqlWord["MIN(keywordid)"] is None:
                                # 没有这个关键字
                                cursor.execute(insertKeywordSQL, (keyword, None))
                                connection.commit()

                            cursor.execute(searchKeywordSQL, keyword)
                            sqlWord = cursor.fetchone()
                            print("Keyword: ", sqlWord)
                            if sqlWord is not None:
                                wordid = sqlWord["MIN(keywordid)"]
                                cursor.execute(insertMovieKeywordSQL, (wordid, tmdbid))
                                connection.commit()
                            else:
                                failList.append(keyword)
                        except:
                            continue
                except:
                    failList.append(doubanID)
                    continue
            except:
                continue

            time.sleep(random.randint(0, 1))
finally:
    connection.close()

print("Done")
print(failList)
