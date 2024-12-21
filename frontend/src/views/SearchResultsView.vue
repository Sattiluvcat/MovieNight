<template>
  <div class="search-results">
    <p class="header-message">这里是所有搜索结果😁</p>
    <div v-if="movies && movies.length > 0">
      <div v-for="movie in paginatedMovies" :key="movie._id" class="movie-item" @click="goToMovieDetail(movie._id)">
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
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
      <span v-for="page in paginationPages" :key="page">
        <button v-if="page !== '...'" @click="goToPage(page)" :class="{ active: currentPage === page }">{{ page }}</button>
        <span v-else>...</span>
      </span>
      <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      currentPage: 1,
      itemsPerPage: 10 // Each page shows 10 movies
    };
  },
  computed: {
    ...mapGetters(['getMovies']),
    movies() {
      return this.getMovies;
    },
    totalPages() {
      return Math.ceil(this.movies.length / this.itemsPerPage);
    },
    paginatedMovies() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.movies.slice(start, end);
    },
    paginationPages() {
      const pages = [];
      if (this.totalPages <= 5) {
        for (let i = 1; i <= this.totalPages; i++) {
          pages.push(i);
        }
      } else {
        if (this.currentPage > 3) {
          pages.push(1);
          pages.push('...');
        }
        for (let i = Math.max(1, this.currentPage - 2); i <= Math.min(this.totalPages, this.currentPage + 2); i++) {
          pages.push(i);
        }
        if (this.currentPage < this.totalPages - 2) {
          pages.push('...');
          pages.push(this.totalPages);
        }
      }
      return pages;
    }
  },
  methods: {
    goToMovieDetail(_id) {
      this.$router.push({ name: 'MovieDetail', params: { _id } });
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    goToPage(page) {
      if (page !== '...') {
        this.currentPage = page;
      }
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
.movie-languages {
  font-size: 14px;
  margin-bottom: 5px;
  text-align: left;
}

.movie-release {
  font-size: 14px;
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 40px;
  margin-bottom: 20px;
}

.pagination button {
  padding: 10px 20px;
  font-size: 16px;
  margin: 0 20px;
  background-color: #b79292;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination button.active {
  background-color: #883333;
}
</style>