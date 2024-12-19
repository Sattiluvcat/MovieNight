<template>
  <div class="welcome-message">
    <h1>{{ welcomeText }}</h1>
  </div>
  <div class="login">
    <h2>登入</h2>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="username">用户名：</label>
        <input type="text" v-model="username" required>
      </div>
      <div class="form-group">
        <label for="title">称呼：</label>
        <input type="text" v-model="title" required>
      </div>
      <div class="form-group">
        <label for="email">邮箱：</label>
        <input type="email" v-model="email" required>
      </div>
    </form>
    <button class="submit-button" @click="login">进入主页</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      username: '',
      title: '',
      email: '',
      welcomeText: 'Enjoy your movie night!'
    }
  },
  methods: {
    async login() {
      if (!this.username || !this.title || !this.email) {
        alert('所有信息都必须填写噢~');
        return;
      }
      try {
        const response = await axios.post('http://localhost:8080/user/movie/login', {
          nickname: this.username,
          title: this.title,
          contact: this.email
        })
        if (response.data.code === 1) {
          this.$router.push({ name: 'Home' })
        } else {
          alert('登录失败: ' + response.data.msg)
        }
      } catch (error) {
        console.error(error)
        alert('出错了！请重试~')
      }
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

.welcome-message {
  text-align: center;
  margin-bottom: 20px;
}

.welcome-message h1 {
  margin-bottom: 50px;
  display: inline-block;
  overflow: hidden;
  border-right: .15em solid #b06767;
  white-space: nowrap;
  max-width: 26ch;
  letter-spacing: .15em;
  animation: typing 3.5s steps(40, end), blink-caret .75s step-end infinite;
  font-family: 'Technic';

}

@keyframes typing {
  from { width: 0 }
  to { width: 100% }
}

@keyframes blink-caret {
  from, to { border-color: transparent }
  50% { border-color: #b06767; }
}

.login {
  max-width: 350px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

label {
  flex: 1;
  //margin-right: 5px;
  font-weight: bold;
  text-align: center;
}

input[type="text"],
input[type="email"] {
  flex: 2;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.submit-button {
  width: 40%;
  padding: 10px;
  background-color: #b79292;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
}

.submit-button:hover {
  background-color: #883333;
}
</style>