<template>
  <div class="home">
    <div class="search-container">
      <input type="text" v-model="searchKeyword" placeholder="搜索电影名 / 导演 / 演员" class="search-box">
      <button @click="searchByKeyword" class="search-button">通过关键词搜索</button>
      <button @click="goToTagsSearch" class="search-button">通过标签搜索</button>
    </div>
  </div>
</template>

<script>
import {searchMovies} from "@/services/api";

export default {
  data() {
    return {
      searchKeyword: ''
    }
  },
  methods: {
    async searchByKeyword() {
      try {
        const response = await searchMovies(this.searchKeyword);
        this.$router.push({ name: 'SearchResults', params: { movies: response.data } });
      } catch (error) {
        console.error(error);
      }
    },
    goToTagsSearch() {
      this.$router.push({ name: 'TagsSearch' });
    }
  }
}
</script>

<style scoped>
.search-container {
  text-align: center;
  margin-top: 50px;
}

.search-box {
  width: 50%;
  padding: 15px;
  font-size: 18px;
  margin-bottom: 20px;
}

.search-button {
  width: 50%;
  padding: 15px;
  font-size: 18px;
  background-color: #b79292;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-button:hover {
  background-color: #883333;
}
</style>