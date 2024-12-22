<template>
  <div class="tags-search">
    <h2>选择分类或语言</h2>
    <div class="tags-container">
      <label>分类</label>
      <button v-for="tag in tags" :key="tag" @click="toggleTag(tag)" :class="{ selected: selectedTags.includes(tag) }">{{ tag }}</button>
    </div>
    <hr class="divider">
    <div class="languages-container">
      <label>语言</label>
      <button v-for="language in languages" :key="language" @click="toggleLanguage(language)"
              :class="{ selected: selectedLanguages.includes(language) }">{{ language }}</button>
    </div>
    <button @click="searchByTagsAndLanguages" class="search-button">搜索</button>
  </div>
</template>

<script>
import { searchByTags } from '@/services/api';
import {mapActions} from "vuex";

export default {
  data() {
    return {
      tags: ['剧情', '喜剧', '冒险', '动画', '奇幻', '爱情', '动作', '科幻', '家庭', '犯罪', '战争', '悬疑', '历史', '惊悚', '古装',
        '传记', '歌舞', '纪录片', '音乐', '短片', '西部', '灾难', '武侠', '运动', '同性', '恐怖', '儿童', '情色'],
      languages: ['英语', '汉语普通话', '日语', '法语', '西班牙语', '德语', '粤语', '俄语', '意大利语', '韩语', '印地语', '阿拉伯语',
        '泰语', '拉丁语', '上海话', '瑞典语', '葡萄牙语', '波斯语', '辛达林语', '重庆话', '美国手语', '丹麦语', '荷兰语', '中国手语',
        '土耳其语', '四川话', '山西话', '沪语', '挪威语', '唐山话', '手语', '克罗地亚语', '科萨语', '潮州话', '武汉话', '毛利语',
        '芬兰语', '匈牙利语', '闽南语', '爱尔兰语', '苏格兰盖尔语', '多斯拉克语', '柏柏尔语', '日本手语', '意第绪语', '希腊语', '冰岛语',
        '晋语', '南非荷兰语', '湖北话', '湖南话', '蒙古语', '捷克语', '爱沙尼亚语', '越南语', '武汉方言', '四川方言', '波尼语', '南京话',
        '昆雅语', '古英语', '古代英语', '泰米尔语', '因纽特语', '塞尔维亚语', '斯瓦希里语', '祖鲁语', '山东方言', '夏威夷语', '乌尔都语',
        '乌克兰语', '索马里语', '贵州方言', '印尼语', '阿姆哈拉语'],
      selectedTags: [],
      selectedLanguages: []
    }
  },
  methods: {
    toggleTag(tag) {
      const index = this.selectedTags.indexOf(tag);
      if (index > -1) {
        this.selectedTags.splice(index, 1);
      } else {
        this.selectedTags.push(tag);
      }
    },
    toggleLanguage(language) {
      const index = this.selectedLanguages.indexOf(language);
      if (index > -1) {
        this.selectedLanguages.splice(index, 1);
      } else {
        this.selectedLanguages.push(language);
      }
    },
    ...mapActions(['updateMovies']),
    async searchByTagsAndLanguages() {
      if (this.selectedTags.length === 0 && this.selectedLanguages.length === 0) {
        alert('至少要选择一个标签噢');
        return;
      }
      try {
        // console.log(this.selectedTags, this.selectedLanguages);
        const response = await searchByTags(this.selectedTags, this.selectedLanguages);
        const movies = response.data.data; // Access the data property of the Result object
        // console.log(movies);
        this.updateMovies(movies);
        this.$router.push({ name: 'SearchResults'});
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>

<style scoped>
.tags-search {
  text-align: center;
  margin-top: 50px;
}

.tags-container, .languages-container {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 10px;
  font-weight: bold;
}

button {
  margin: 7px;
  padding: 10px 20px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
}

button.selected {
  background-color: #b06767;
  color: white;
}

.divider {
  border: 0;
  height: 1px;
  background: #ccc;
  margin: 20px 0;
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