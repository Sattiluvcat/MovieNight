// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import HomeView from '../views/HomeView.vue'
import TagsSearchView from '@/views/TagsSearchView.vue'
import SearchResultsView from '@/views/SearchResultsView.vue'
import MovieDetailView from '@/views/MovieDetailView.vue';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/home',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/tags-search',
    name: 'TagsSearch',
    component: TagsSearchView
  },
  {
    path: '/search-results',
    name: 'SearchResults',
    component: SearchResultsView,
    props: route => ({ movies: route.params.movies })
  },
  {
    path: '/movie/:_id',
    name: 'MovieDetail',
    component: MovieDetailView,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router