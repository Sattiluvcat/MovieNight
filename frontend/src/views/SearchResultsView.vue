<template>
  <div class="search-results">
    <p class="header-message">这里是所有搜索结果😁</p>
    <div v-if="movies && movies.length > 0">
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
    <div v-else>
      没有相关电影噢，请重试😢
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: {
    ...mapGetters(['getMovies']),
    movies() {
      return this.getMovies;
    }
  },
  methods: {
    goToMovieDetail(_id) {
      console.log(_id);
      this.$router.push({ name: 'MovieDetail', params: { _id } });
    }
  }
}
</script>

<style scoped>
.search-results {
  text-align: center;
  margin-top: 20px;
}

.header-message {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 20px;
}

.movie-item {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 20px auto;
  padding: 20px;
  width: 80%;
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
.movie-languages
{
  font-size: 14px;
  margin-bottom: 5px;
  text-align: left;
}

.movie-release {
  font-size: 14px;
  margin-bottom: 5px;
}
</style>