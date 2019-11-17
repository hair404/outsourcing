<template>
  <div class="backimg">
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
      <h1 class="title">人力和项目服务平台</h1>
      <form id="login">
        <div class="content">
          <input
            v-model="username"
            name="name"
            id="account"
            type="text"
            placeholder="账号或者E-mail"
            required
          />
          <input
            v-model="password"
            name="password"
            id="password"
            type="password"
            placeholder="密码"
            required
          />
          <select v-model="type">
            <option value="0">发包公司</option>
            <option value="1">工作室</option>
            <option value="2">管理员</option>
          </select>
          <div>
            <input
              v-model="code"
              style="width: 60%;display: inline-block;float: left;margin-top: 0px"
              name="code"
              placeholder="验证码"
              required
            />
            <v-img
              width="100"
              class="d-inline-block"
              style="margin-left: 28px;float: right"
              src="/Platform/code"
            />
          </div>
        </div>
        <br />
        <div class="button">
          <v-btn
            style="width: 80%;"
            @click="submit"
          >登录</v-btn>
          <br />
          <br />
          <button @click="$router.push('/register')">
            注册
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import Head from '@/components/Head.vue'
import axios from 'axios'
export default {
  name: 'Login',
  components: {
    Head
  },
  data () {
    return {
      username: '',
      password: '',
      type: 0,
      snackbar: false,
      text: ''
    }
  },
  methods: {
    submit: function (event) {
      let data = new FormData()
      data.append('name', this.username)
      data.append('password', this.password)
      data.append('type', this.type)
      axios
        .post('/Platform/login', data)
        .then(response => {
          if (response.data === 'success') {
            this.$router.push({ path: '/home' })
            var OneSignal = window.OneSignal || []
            OneSignal.getUserId(function (userId) {
              axios.post('/Platform/token', userId)
                .then(response => {
                  if (response.data !== 'success') console.log('token上传失败')
                })
                .catch(error => {
                  console.log(error)
                })
            })
          } else {
            this.text = '密码错误或者未注册'
            this.snackbar = true
          }
        })
        .catch(function (error) {
          console.log(error)
          this.text = '服务器错误'
          this.snackbar = true
        })
    }
  }
}

</script>
<style scoped lang="scss">
.backimg {
  background-image: url('../assets/backimg.jpg');
  height: 100%;
  width: 100%;
  position: absolute;
  background-position: center;
  overflow: hidden;
}
.logintitle {
  float: left;
  ul {
    color: #2c3e50;
    padding: 0 0 0 35px;
    text-align: center;
  }
}

.logintitle ul {
  list-style: none;
}
.pagetitle {
  float: left;
  text-align: center;
  line-height: 42px;
  padding-left: 10px;
  padding-right: 10px;
  transition: 200ms;
}
.pagetitle:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.2);
}
.hover {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  height: 500px;
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
  background-image: url('../assets/backimg.jpg');
  filter: blur(4px);
  background-position: center;
}

.title {
  position: absolute;
  left: 50%;
  top: 10%;
  transform: translate(-50%, -50%);
  width: 300px;
  text-align: center;
  font-size: 25px;
}

#login {
  position: absolute;
  top: 55%;
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
  padding-left: 10%;
  padding-right: 10%;
  padding-bottom: 20px;
  height: fit-content;
}

.content select,
.content input,
.content > div {
  margin-top: 20px;
  display: block;
  width: 100%;
  height: 50px;
}

.button a {
  padding: 5px 150px;
  border: transparent;
  border-radius: 3px;
  background: rgba(255, 255, 255, 0.4);
  transition: 200ms;
}

.button a:hover {
  background: white;
  box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}
.button1 {
  padding: 5px 150px;
  border: transparent;
  border-radius: 3px;
  background: rgba(255, 255, 255, 0.4);
  transition: 200ms;
  cursor: pointer;
}

.button1:hover {
  background: white;
  box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}
</style>
