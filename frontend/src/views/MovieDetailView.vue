<template>
  <div class="movie-detail-container">
    <div class="beautify-element">
      <hr class="beautify-line" />
      <p class="beautify-text">Welcome to the Movie Detail Page!🤩</p>
      <hr class="beautify-line" />
    </div>
      <div v-if="movie" class="movie-detail">
        <div class="top-section">
          <div class="left-column">
            <img referrerpolicy="no-referrer" :src="cover_url" alt="Movie Cover" class="movie-cover" />
          </div>
        <div class="middle-column">
        <h1 class="movie-title">{{ movie.title }}</h1>
        <p class="movie-director"> {{ movie.director }}</p>
        <p class="movie-duration"> {{ movie.duration }}</p>
          <p class="movie-actors"> {{ movie.actor_actresses.join(', ') }}</p>
      </div>
      <div class="right-column">
        <p class="movie-score-official">豆瓣评分: {{ score_official }}</p>
        <p class="movie-score-mine">我的评分: {{ movie.star_mine }} / 5</p>
        <button @click="watchTogether" class="watch-together-button">一起看</button>
      </div>
    </div>
    <div class="bottom-section">
<!--    把演员挪到上面-->
      <p class="movie-tags">  {{ movie.tags.join(', ') }}</p>
      <p class="movie-languages">  {{ movie.languages.join(', ') }}</p>
      <p class="movie-summary preserve-whitespace">  {{ summary }}</p>
      <div class="comment-section">
        <input v-model="comment" placeholder="看过了？请跟Satti分享评论😸" class="comment-input" />
        <button @click="sendComment" class="send-comment-button">发送评论</button>
      </div>
    </div>
  </div>
  <div v-else>
    加载中 请稍候😣
  </div>
  </div>
</template>

<script>
import { getMovieDetail, watchTogether, sendComment } from '@/services/api'; // Adjust the path as necessary

export default {
  props: ['_id'],
  data() {
    return {
      movie: null,
      cover_url: '',
      score_official: '',
      summary:'',
      comment: ''
    }
  },
  async created() {
    try {
      const response = await getMovieDetail(this._id);
      if (response && response.data) {
        this.movie = response.data.data.movie;
        this.cover_url = response.data.data.cover_url;
        this.score_official = response.data.data.score_official;
        this.summary = response.data.data.summary;
      }
    } catch (error) {
      console.error('抓取电影内容时出错了:', error);
    }
  },
  methods: {
    async watchTogether() {
      try {
        const response = await watchTogether(this._id);
        console.log('一起看 响应:', response);
        if (response.data.code===0)
          alert('请登录后再发送噢');
        else
          alert('已向Satti发送一起看邀请');
      } catch (error) {
        console.error('一起看功能出错了:', error);
      }
    },
    async sendComment() {
      try {
        const response = await sendComment(this._id, this.comment);
        console.log('发送评论 响应:', response);
        if (response.data.code==='0')
          alert('请登录后再发送噢');
        else
          alert('已向Satti发送电影评论');
        this.comment = ''; // Clear the input after sending the comment
      } catch (error) {
        console.error('发送评论功能出错了:', error);
      }
    }
  }
}
</script>

<style scoped>

.preserve-whitespace {
  white-space: pre-wrap;
}

.movie-detail-container {
  position: relative;
  padding-top: 40px;
  //background-color: #eff1f5;
  background-color: #f8f6f6;
}

.beautify-element {
  position: fixed;
  top: 0;
  width: 100%;
  text-align: center;
  background-color: #f8f6f6;
  padding: 10px 0;
  box-shadow: 0 2px 5px rgb(203, 166, 166);
  z-index: 1000;
}

.beautify-line {
  width: 80%;
  margin: 0 auto;
  border: none;
  border-top: 2px solid #b79292;
}

.beautify-text {
  font-size: 30px;
  font-weight: bold;
  font-family: 'Technic';
  overflow: hidden; /* 确保文字不会溢出 */
  white-space: nowrap; /* 防止文字换行 */
  border-right: .15em solid #b06767; /* 添加光标效果 */
  max-width: 29ch;
  animation: typing 3.5s steps(30, end), blink-caret .75s step-end infinite; /* 添加打字机和光标闪烁动画 */
  text-align: center;
  margin: 10px auto 20px;
}

@keyframes typing {
  from { width: 0 }
  to { width: 100% }
}

@keyframes blink-caret {
  from, to { border-color: transparent }
  50% { border-color: #b06767; }
}

.movie-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
  padding: 0 50px 200px;
  background-color: #f8f6f6;
}

.top-section {
  display: flex;
  width: 100%;
  align-items: flex-start;
}

.left-column,
.middle-column,
.right-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.middle-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  //border: 2px solid #ccc; /* 添加边框 */
  //border-radius: 10px; /* 添加圆角 */
  //padding: 10px; /* 添加内边距 */
}

.left-column {
  align-items: center;
  justify-content: center;
}

.right-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-right: 150px;
  //border: 2px solid #ccc; /* 添加边框 */
  //border-radius: 10px; /* 添加圆角 */
  //padding: 10px; /* 添加内边距 */
}

.bottom-section {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 83%;
  margin-top: 20px;
  //padding-left: 20px;
  border: 2px solid #ccc; /* 添加边框 */
  border-radius: 10px; /* 添加圆角 */
  padding: 10px; /* 添加内边距 */
}

.movie-tags,
.movie-languages,
.movie-summary {
  width: 100%; /* 确保这些行占满整个宽度 */
  padding: 0; /* 移除默认的内边距 */
  margin: 0 0 10px;
  text-align: left;
  letter-spacing: 0.1em;
}

.movie-tags::before,
.movie-languages::before,
.movie-summary::before {
  font-weight: bold;
}

.movie-tags::before {
  content: "标签: ";
}

.movie-languages::before {
  content: "语言: ";
}

.movie-summary::before {
  content: "简介: ";
}

.movie-actors {
  font-size: 16px;
  margin-top: 10px;
  margin-bottom: 10px;
}

.movie-cover {
  width: 200px;
  height: 300px;
  object-fit: cover;
  border: 5px solid #ccc; /* 添加边框 */
  border-radius: 10px; /* 添加圆角 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 添加阴影 */
}

.movie-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.movie-director,
.movie-duration,
.movie-score-official,
.movie-score-mine {
  font-size: 16px;
  margin-bottom: 10px;
}

.movie-director::before,
.movie-duration::before,
.movie-actors::before {
  font-weight: bold;
}

.movie-director::before {
  content: "导演: ";
}

.movie-duration::before {
  content: "时长: ";
}

.movie-actors::before {
  content: "演员: ";
}

.movie-score-official,
.movie-title {
  font-size: 20px;
  font-weight: bold;
}

.movie-score-mine {
  font-size: 18px;
  font-weight: bold;
}

.comment-section {
  display: flex;
  align-items: center;
  justify-content: center; /* Center the content horizontally */
  width: 100%;
  margin-top: 20px;
  position: fixed; /* Fixed position */
  bottom: 0; /* Fixed at the bottom of the page */
  left: 0; /* Fixed at the left side of the page */
  background-color: #f8f6f6; /* Background color */
  padding: 10px; /* Padding */
  box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1); /* Add shadow */
}

.comment-input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
  text-align: center;
  width: 80%;
  border: 1px solid #f8f6f6;
  border-radius: 5px;
  margin: 0 auto;
}

.watch-together-button{
  padding: 10px 30px;
  font-size: 16px;
  background-color: #b79292;
  color: white;
  border: none;
  margin-top: 10px;
  border-radius: 5px;
  cursor: pointer;
}
.send-comment-button {
  padding: 10px 20px;
  font-size: 16px;
  margin-left: 10px;
  background-color: #b79292;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.watch-together-button:hover,
.send-comment-button:hover {
  background-color: #883333;
}
</style>