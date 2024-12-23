import sys
import requests
from bs4 import BeautifulSoup
import re

def scrape_movie_info(url):
    upload_auth_token = "240508762:8deda7399089dacbaea176d08ecf75a62947ac55"
    header_movie = {
        'Authorization': f'Bearer {upload_auth_token}',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
        'Connection': 'keep-alive',
        'Referer': 'https://movie.douban.com/',
        # Cookie可能需要更新
        'Cookie': ''
    }
    response_movie = requests.get(url, headers=header_movie)
    if response_movie.status_code != 200:
        return 'Error','Error','Error'
    soup_movie = BeautifulSoup(response_movie.text, 'html.parser')
    rating = soup_movie.select_one('div.rating_self.clearfix strong.ll.rating_num').text.strip()
    photo_url = soup_movie.select_one('div.subject.clearfix #mainpic a.nbgnbg img').get('src')
    summary = soup_movie.select_one('div.related-info div.indent span[property="v:summary"]').text.strip()

    # Remove excessive whitespace and newlines
    summary = re.sub(r'[ \t]+', ' ', summary)

    return rating, photo_url, summary
    # return 'Error','Error','Error'

if __name__ == '__main__':
    # url = 'https://movie.douban.com/subject/1295437/'
    url = sys.argv[1]
    rating, cover_url, summary = scrape_movie_info(url)
    if rating == 'Error':
        print('Error')
    else:
        result = f"{rating},{cover_url},{summary}"
        print(result)