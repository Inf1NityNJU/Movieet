#!/usr/bin/env python3
# coding=utf-8

import re
import requests
from bs4 import BeautifulSoup

def getNameByID(movieID):
    movieURL = 'https://www.amazon.com/dp/' + movieID
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}
    page = requests.get(movieURL, headers=headers)
    htmlData = page.text
    soup = BeautifulSoup(htmlData, "html.parser")
    moviename = soup.select('h1')[0].text.strip()
    return ' '.join(moviename.split())

def getNameByIDWithoutMoreInfo(movieID):
    return ''.join(re.compile(r"[\(|\[](.*?)[\]|\)]|[0-9]").sub('', getNameByID(movieID)))

# movieID = input("Enter movie ID\n")
# print(getNameByID(movieID))
# print(getNameByIDWithoutMoreInfo("B001KZG99A"))