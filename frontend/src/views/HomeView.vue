<template>
  <div class="home">
    <div class="typing-effect">
      <p class="typing-text">Select one for movie night</p>
    </div>
    <div class="search-container">
      <input type="text" v-model="searchKeyword" placeholder="输入电影名 / 导演 / 演员" class="search-box">
      <button @click="searchByKeyword" class="search-button">搜索😘</button>
    </div>
    <div class="category-search-container">
      <button @click="fetchAllMovies" class="view-all-button">查看全部电影😎</button>
      <button @click="goToTagsSearch" class="category-search-button">通过标签搜索😎</button>
    </div>
    <div class="recommendation-header">
      <p class="header-text">电影推荐</p>
      <hr class="header-line">
    </div>
    <div class="movie-overview-container">
      <div v-for="movie in movies" :key="movie._id" class="movie-item" @click="goToMovieDetail(movie._id)">
        <div class="movie-header">
          <h2 class="movie-title">{{ movie.title }}</h2>
          <p class="movie-score">评分: {{ movie.star_mine }} / 5</p>
        </div>
        <div class="movie-details">
          <div class="left-column">
            <p class="movie-actors">演员: {{ movie.actor_actresses.join(', ') }}</p>
            <p class="movie-tags">标签: {{ movie.tags.join(', ') }}</p>
            <p class="movie-languages">语言: {{ movie.languages.join(', ') }}</p>
          </div>
          <div class="right-column">
            <p class="movie-release">上映时间: {{ movie.release_time }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getSuggestedMovies, searchMovies,getAllMovies } from "@/services/api";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      searchKeyword: '',
      movies: []
    }
  },
  methods: {
    ...mapActions(['updateMovies']),
    async searchByKeyword() {
      if (!this.searchKeyword) {
        alert('请输入搜索关键词');
        return;
      }
      try {
        const response = await searchMovies(this.searchKeyword);
        const movies = response.data.data;
        this.updateMovies(movies);
        this.$router.push({ name: 'SearchResults' });
      } catch (error) {
        console.error(error);
      }
    },
    ...mapActions(['updateMovies']),
    async fetchAllMovies() {
      try {
        const response = await getAllMovies();
        console.log(response);
        const movies = response.data.data;
        this.updateMovies(movies);
        this.$router.push({ name: 'SearchResults' });
      } catch (error) {
        console.error(error);
      }
    },
    goToTagsSearch() {
      this.$router.push({ name: 'TagsSearch' });
    },
    async fetchSuggestedMovies() {
      try {
        const response = await getSuggestedMovies();
        this.movies = response.data.data;
      } catch (error) {
        console.error(error);
      }
    },
    goToMovieDetail(_id) {
      this.$router.push({ name: 'MovieDetail', params: { _id } });
    }
  },
  created() {
    this.fetchSuggestedMovies();
  }
}
</script>

<style scoped>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}

.home {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 0;
  padding-top: 0;
}

.typing-effect {
  text-align: center;
  margin-top: 0;
  padding-top: 0;
}

.typing-text {
  font-size: 30px;
  font-weight: bold;
  font-family: 'Technic';
  overflow: hidden;
  white-space: nowrap;
  border-right: .15em solid #b06767;
  max-width: 22.5ch;
  animation: typing 3.5s steps(40, end), blink-caret .75s step-end infinite;
  text-align: center;
  margin-bottom: 30px;
}

@keyframes typing {
  from { width: 0 }
  to { width: 100% }
}

@keyframes blink-caret {
  from, to { border-color: transparent }
  50% { border-color: #b06767; }
}

.search-container {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.search-box {
  width: 600px;
  padding: 10px;
  font-size: 16px;
  margin-right: 10px;
}

.search-button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #b79292;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-button:hover {
  background-color: #883333;
}

.category-search-container {
  display: flex;
  align-items: center;
  margin-top: 20px;
}

.view-all-button {
  padding: 20px 30px;
  font-size: 20px;
  background-color: #b79292;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 40px;
}

.view-all-button:hover {
  background-color: #883333;
}

.category-search-button {
  padding: 20px 30px;
  font-size: 20px;
  background-color: #b79292;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.category-search-button:hover {
  background-color: #883333;
}

.recommendation-header {
  text-align: center;
  margin-top: 20px;
}

.header-text {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 10px;
}

.header-line {
  width: 80%;
  margin: 0 auto;
  border: none;
  border-top: 2px solid #b79292;
}

.movie-overview-container {
  margin-top: 20px;
  width: 80%;
}

.movie-item {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 20px auto;
  padding: 20px;
  width: 100%;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 10px;
  cursor: pointer;
}

.movie-item:hover {
  background-color: #e0e0e0;
}

.movie-header {
  display: flex;
  justify-content: space-between;
  width: 100%;
  align-items: center;
}

.movie-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 2px;
}

.movie-score {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.movie-details {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.left-column,
.right-column {
  display: flex;
  flex-direction: column;
}

.movie-actors,
.movie-tags,
.movie-languages {
  font-size: 14px;
  margin-bottom: 5px;
  text-align: left;
}

.movie-release {
  font-size: 14px;
  margin-bottom: 5px;
}
</style>