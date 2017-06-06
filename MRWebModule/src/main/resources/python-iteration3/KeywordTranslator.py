#!/usr/bin/env python3
import pymysql.cursors
import requests
import random
import time
import string
import re
import sys
import copy
import traceback
import _md5
import urllib
import json

user = 'infinity'
password = 'Infinity123!'
# user = 'root'
# password = 'songkuixi'

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
    proxies = ["59.78.14.224:1080", "60.184.174.250:808", "220.160.10.18:808", "121.226.154.157:808",
               "180.180.16.65:8080", "94.200.103.99:3128", "120.132.26.37:8080", "2.180.27.146:80",
               "121.237.139.180:808", "58.97.35.187:8080", "122.241.75.248:808", "219.226.169.81:8998",
               "175.155.25.39:808", "125.120.8.8:808", "27.18.132.84:8998", "123.138.89.130:9999", "119.84.15.227:9001",
               "115.237.237.4:808", "180.148.33.29:8080", "119.57.105.235:8080", "36.66.242.74:53281",
               "202.194.44.245:8998", "175.155.233.88:808", "123.169.87.0:808", "222.222.195.249:8998",
               "45.64.99.99:3128", "41.45.169.60:8080", "122.245.67.231:808", "121.63.178.111:8998",
               "110.72.150.117:8123", "1.20.109.229:8080", "195.98.189.178:3128", "120.132.26.30:8080",
               "113.69.252.238:808", "58.147.174.113:8080", "178.33.4.48:3128", "110.73.4.72:8123", "60.178.8.78:8081",
               "183.153.11.178:808", "175.155.24.43:808", "35.160.228.40:80", "103.217.89.1:8080", "182.92.219.43:3128",
               "182.43.232.190:808", "182.203.211.229:8998", "191.101.229.239:3129", "141.196.65.85:8080",
               "141.196.142.137:8080", "36.78.130.45:8080", "36.81.85.131:8080", "1.51.145.142:8998",
               "121.206.17.98:808", "181.49.200.90:8080", "222.186.45.122:62222", "141.196.151.26:8080",
               "121.41.110.49:3128", "123.55.191.86:808", "180.118.242.213:808", "1.20.75.46:8080",
               "141.196.143.112:8080", "36.62.117.55:8118", "144.255.48.255:808", "175.155.24.55:808",
               "81.211.43.102:8081", "115.220.6.33:808", "134.17.128.173:8080", "5.202.214.198:8080",
               "47.52.34.73:3128", "218.56.132.154:8080", "91.120.7.237:8080", "109.254.6.40:8080", "119.5.1.31:808",
               "61.163.39.70:9999", "188.0.132.180:8080", "210.35.40.116:8998", "117.91.138.239:808",
               "218.87.242.71:8998", "123.55.185.245:808", "141.196.64.216:8080", "119.6.87.191:8081",
               "109.254.143.7:53281", "115.202.177.85:808", "115.207.4.171:808", "182.88.45.228:8123",
               "120.132.25.252:8080", "36.67.128.145:8080", "82.146.37.33:8888", "218.18.232.29:8080",
               "175.155.25.71:808", "122.195.46.201:808", "42.233.238.117:8118", "114.239.3.248:808",
               "121.61.106.60:808", "123.170.255.7:808", "1.20.91.102:8080", "116.23.138.141:9999",
               "182.88.212.127:8123", "219.144.45.201:8998", "116.28.120.43:808", "182.87.15.136:808"]
    return proxies


def change_proxy():
    proxy = random.choice(get_proxy())
    while proxy is None:
        proxy = random.choice(get_proxy())
    return proxy


appid = '20170606000052746'
secretKey = 'XivKA_6K0XZGXBwUF1nz'

movieCount = 35529
i = 0
failList = []
try:
    with connection.cursor() as cursor:
        while i < movieCount:
            # try:
            searchKeywordSQL = """SELECT keywordid, keyword_cn FROM `tmdb_keyword` LIMIT %s, %s"""

            updateMovieSQL = """UPDATE `tmdb_keyword` SET keyword_en = %s WHERE keywordid = %s"""

            cursor.execute(searchKeywordSQL, (i, 1))

            i += 1

            result = cursor.fetchone()
            keywordid = result["keywordid"]
            keyword_cn = result["keyword_cn"]

            print(result)

            try:
                httpClient = None
                myurl = 'http://api.fanyi.baidu.com/api/trans/vip/translate'
                q = keyword_cn
                fromLang = 'zh'
                toLang = 'en'
                salt = random.randint(32768, 65536)

                sign = appid + q + str(salt) + secretKey
                m1 = _md5.md5(sign.encode("utf8"))
                sign = m1.hexdigest()

                myurl = myurl + '?appid=' + appid + '&q=' + q + '&from=' + fromLang + '&to=' + toLang + '&salt=' + str(salt) + '&sign=' + sign

                jsonDict = json.loads(requests.get(myurl, headers=getUserAgentHeader()).text)
                print(jsonDict)
                english = jsonDict["trans_result"][0]["dst"]

                cursor.execute(updateMovieSQL, (english, keywordid))
                connection.commit()
            except:
                pass
            # except:
            #     pass

            time.sleep(random.randint(0, 1))
finally:
    connection.close()

