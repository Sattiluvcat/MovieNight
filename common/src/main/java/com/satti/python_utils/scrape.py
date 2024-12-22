import sys
import requests
from bs4 import BeautifulSoup
import re

def scrape_movie_info(url):
    # Extract the information you need from the webpage
    upload_auth_token = "240508762:8deda7399089dacbaea176d08ecf75a62947ac55"
    header_movie = {
        'Authorization': f'Bearer {upload_auth_token}',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
        'Connection': 'keep-alive',
        'Referer': 'https://movie.douban.com/',
        # Cookie可能需要更新
        'Cookie': 'bid=m8FjVLf2VIQ; viewed="33408247"; ll="108296"; _pk_id.100001.4cf6=5af1ebee05d5a452.1731507051.; '
                  '__yadk_uid=QMLI6Uvhk52vHqY5JrtZ1gcnwauJqXAM; push_noty_num=0; push_doumail_num=0; '
                  '_pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1734876540%2C%22https%3A%2F%2Fcn.bing.com%2F%22%5D; '
                  '_pk_ses.100001.4cf6=1; ap_v=0,6.0; dbcl2="240508762:UGTvyWfS/xM"; ck=qv59; '
                  'frodotk_db="fa69987fcdcf8500ef3cc97f696e8f00"'
    }
    response_movie = requests.get(url, headers=header_movie)
    if response_movie.status_code != 200:
        return 'Error,Error,Error'
    soup_movie = BeautifulSoup(response_movie.text, 'html.parser')
    rating = soup_movie.select_one('div.rating_self.clearfix strong.ll.rating_num').text.strip()
    photo_url = soup_movie.select_one('div.subject.clearfix #mainpic a.nbgnbg img').get('src')
    summary = soup_movie.select_one('div.related-info div.indent span[property="v:summary"]').text.strip()

    # Remove excessive whitespace and newlines
    summary = re.sub(r'[ \t]+', ' ', summary)

    return rating, photo_url, summary

if __name__ == '__main__':
    # url = 'https://movie.douban.com/subject/26820448/'
    url = sys.argv[1]
    rating, cover_url, summary = scrape_movie_info(url)
    if rating == 'Error':
        print('Error')
    else:
        result = f"{rating},{cover_url},{summary}"
        print(result)