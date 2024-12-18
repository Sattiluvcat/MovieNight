import sys
import requests
from bs4 import BeautifulSoup


def scrape_movie_info(url):
    # Extract the information you need from the webpage
    print("in python")
    header_movie = {
        # 'Authorization': f'Bearer {upload_auth_token}',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0',
    }
    response_movie = requests.get(url, headers=header_movie)
    if response_movie.status_code!=200:
        return 'Error'
    soup_movie = BeautifulSoup(response_movie.text, 'html.parser')
    rating=soup_movie.select_one('div.rating_self.clearfix strong.ll.rating_num').text.strip()
    photo_url=soup_movie.select_one('div.mainpic a.nbgnbg img').get('src')
    return rating,photo_url


if __name__ == '__main__':
    url = sys.argv[1]
    rating, cover_url = scrape_movie_info(url)
    print(f"{rating},{cover_url}")
