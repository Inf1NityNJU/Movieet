#!/usr/bin/env python3
# coding=utf-8

import sys
import os

PATH = os.path.dirname(os.path.abspath(r'/Library/Frameworks/Python.framework/Versions/3.5/lib/python3.5/site-packages/requests/__init__.py'))
sys.path.insert(0, PATH)

import requests
from bs4 import BeautifulSoup

def getNameByID(movieID):
    # 读取 URL，伪造 User-Agent
    movieURL = 'https://www.amazon.com/dp/' + movieID
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}
    page = requests.get(movieURL, headers=headers)
    htmlData = page.text
    # BS读取
    soup = BeautifulSoup(htmlData, "html.parser")
    moviename = soup.select('h1')[0].text.strip()
    return ' '.join(moviename.split())

movieID = input("Enter movie ID\n")
print(getNameByID(movieID))