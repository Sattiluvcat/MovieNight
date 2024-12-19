<template>
  <div class="search-results">
    <div v-if="movies && movies.length > 0">
      <div v-for="movie in movies" :key="movie.title" class="movie-item">
        {{ movie.title }}
      </div>
    </div>
    <div v-else>
      No results found.
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
  }
}
</script>

<style scoped>
.search-results {
  text-align: center;
  margin-top: 50px;
}

.movie-item {
  margin: 10px 0;
}
</style>