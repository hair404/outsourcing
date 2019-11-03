<template>
  <div class="body">
    <v-snackbar
      color="error"
      v-model="snackbar"
    >
      {{ text }}
      <v-btn
        text
        color="red"
        @click="snackbar = false"
      >
        关闭
      </v-btn>
    </v-snackbar>

    <Head />
    <div class="hover">
      <div class="blur"></div>
      <div class="cover"></div>
      <i
        class="material-icons arrow_back"
        onclick="window.location.href='./login.html';"
        style="cursor: pointer;padding-left: 10%;padding-top: 10%;color: grey"
      ></i>
      <h1>注册</h1>
      <form
        id="form"
        @submit.prevent="submit"
        class="content"
      >
        <select v-model="type">
          <option value="0">发包公司</option>
          <option value="1">工作室</option>
          <option value="2">管理员</option>
        </select>
        <input
          v-model="username"
          :placeholder="userTypeName[type]"
          type="text"
          required
        >
        <input
          v-model="name"
          placeholder="联系人姓名"
          type="text"
          required
        >
        <input
          v-model="phone"
          placeholder="电话"
          type="text"
          required
        >
        <input
          v-model="password"
          placeholder="密码"
          type="password"
          required
        >
        <input
          v-model="repeatpassword"
          placeholder="重复密码"
          type="password"
          @click="check"
          required
        >
        <input
          v-model="email"
          placeholder="邮箱"
          type="email"
          required
        >
        <button>注册</button>
      </form>
    </div>
  </div>
</template>

<script>
import Head from '@/components/Head.vue'
import axios from 'axios'

export default {
  name: 'register',
  components: {
    Head
  },
  data () {
    return {
      userTypeName: ['公司名', '工作室名', '管理员名称'],
      username: '',
      name: '',
      phone: '',
      password: '',
      repeatpassword: '',
      email: '',
      snackbar: false,
      text: '',
      type: 0
    }
  },
  methods: {
    submit: function () {
      if (this.password !== this.repeatpassword) {
        this.text = '密码不匹配!'
        this.snackbar = true
        return false
      }
      let data = new FormData()
      data.append('username', this.username)
      data.append('name', this.name)
      data.append('phone', this.phone)
      data.append('password', this.password)
      data.append('email', this.email)
      data.append('type', this.type)
      axios
        .post('/Platform/register', data)
        .then(response => (this.$router.push({ path: './home' })))
        .catch(function (error) {
          console.log(error)
          this.text = '服务器错误'
          this.snackbar = true
        })
    },
    check: function () {
      if (this.password.length < 6) {
        this.text = '密码太短!'
        this.snackbar = true
      }
    }
  }
}

</script>

<style scoped lang="scss">
.title {
  top: 10%;
}

.body {
  background-image: url("../assets/backimg.jpg");
  height: 100%;
  background-position: center;
  overflow: hidden;
}

form {
  top: 55%;
}

form button {
  width: 100%;
  margin-top: 20px;
  height: 30px;
}

a {
  text-decoration: none;
  outline: none;
  color: #000000;
}

.hover {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  height: 550px;
  width: 400px;
}

.cover {
  position: absolute;
  height: 100%;
  width: 100%;
  z-index: -1;
  transition: 200ms;
  background-color: rgba(255, 255, 255, 0.4);
  border: transparent;
  border-radius: 3px;
  box-shadow: grey 0px 1px 4px;
}

.cover:hover {
  box-shadow: grey 0px 1px 10px;
}

.blur {
  position: absolute;
  overflow: hidden;
  height: 100%;
  width: 100%;
  z-index: -2;
  background-image: url("../assets/backimg.jpg");
  filter: blur(4px);
  background-position: center;
}

.title {
  position: absolute;
  left: 50%;
  top: 7%;
  transform: translate(-50%, -50%);
  width: 300px;
  text-align: center;
}

form {
  position: absolute;
  transform: translate(0, -50%);
  width: 100%;
  text-align: center;
}

input,
select {
  border: transparent;
  background: rgba(255, 255, 255, 0.4);
  border-radius: 3px;
  transition: 200ms;
}

input:hover,
select:hover {
  background: white;
  box-shadow: grey 0px 1px 4px;
}

.content {
  padding-right: 10%;
  padding-left: 10%;
  padding-bottom: 20px;
  height: fit-content;
}

.content select,
.content input {
  margin-top: 20px;
  display: block;
  width: 100%;
  height: 42px;
}

h1 {
  position: absolute;
  top: 25px;
  width: 100%;
  text-align: center;
  font-size: 16px;
}

.register {
  cursor: pointer;
  padding-top: 10px;
  font-size: 15px;
}

.img {
  cursor: pointer;
}

button {
  padding: 5px;
  border: transparent;
  border-radius: 3px;
  background: rgba(255, 255, 255, 0.4);
  transition: 200ms;
}

button:hover {
  background: white;
  box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}
</style>
