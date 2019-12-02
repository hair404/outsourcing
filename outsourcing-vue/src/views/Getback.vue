<template>
  <div class="body">
    <v-snackbar color="error" v-model="snackbar">
      {{ text }}
      <v-btn text color="red" @click="snackbar = false">关闭</v-btn>
    </v-snackbar>
    <Head />
    <div class="hover">
      <div class="blur"></div>
      <div class="cover"></div>
      <i
        class="material-icons arrow_back"
        onclick="window.location.href='./login.html';"
        style="cursor: pointer;padding-left: 10%;padding-top: 10%;color: grey"
      />
      <h1>找回密码</h1>
      <form id="form" class="content">
        <v-row dense>
          <v-btn
            max-width="100"
            v-if="state===1||state===2"
            style="margin-left:50px"
            @click="change(0)"
          >邮箱找回</v-btn>
          <v-btn
            max-width="100"
            v-if="state==1||state==2"
            style="margin-left:15px"
            @click="change(1)"
          >手机找回</v-btn>
        </v-row>
        <v-col v-if="model==1&&state==1">
          <select id="type" v-model="type">
            <option value="0">发包公司</option>
            <option value="1">工作室</option>
            <option value="2">管理员</option>
          </select>
          <input v-model="username" :placeholder="userTypeName[type]" type="text" required />
          <button @click="check_step1(1)">下一步</button>
        </v-col>
        <v-col id="col2" v-if="model==2&&state==1">
          <select id="type" v-model="type">
            <option value="0">发包公司</option>
            <option value="1">工作室</option>
            <option value="2">管理员</option>
          </select>
          <input v-model="username" :placeholder="userTypeName[type]" type="text" required />
          <button @click="check_step1(2)">下一步</button>
        </v-col>
        <v-col id="col3" v-if="model==1&&state==2">
          <input v-model="email" placeholder="邮箱" id="email" type="text" readonly unselectable="on" />
          <input type="button" id="button" value="发送验证码" />
          <input v-model="code" placeholder="验证码" id="code" type="text" required />
          <br />
          <a></a>
          <button @click="check_step2(1)">下一步</button>
        </v-col>
        <v-col id="col4" v-if="model==2&&state==2">
          <input
            v-model="phone"
            placeholder="手机"
            id="phone"
            type="number"
            maxlength="11"
            readonly
            unselectable="on"
          />
          <input type="button" id="button" value="发送验证码" />
          <input v-model="code" placeholder="验证码" id="code" type="text" required />
          <button @click="check_step2(2)">下一步</button>
        </v-col>
        <v-col id="col5" v-if="model==1&&state==3">
          <input v-model="password" placeholder="输入新密码" id="password" type="password" required />
          <input
            v-model="repeatpassword"
            placeholder="确认新密码"
            id="repeatpassword"
            type="password"
            required
          />
          <button @click="check_step3()">确定</button>
        </v-col>
        <v-col id="col6" v-if="model==2&&state==3">
          <input v-model="password" placeholder="输入新密码" id="password" type="password" required />
          <input
            v-model="repeatpassword"
            placeholder="确认新密码"
            id="repeatpassword"
            type="password"
            required
          />
          <button @click="check_step3()">确定</button>
        </v-col>
      </form>
    </div>
  </div>
</template>
<script>
import Head from '@/components/Head.vue'
import Axios from 'axios'

export default {
  name: 'getback',
  components: {
    Head
  },
  data () {
    return {
      userTypeName: ['公司名', '工作室名', '管理员名称'],
      username: '',
      phone: '',
      email: '',
      snackbar: false,
      text: '',
      type: 0,
      code: '',
      model: 1,
      state: 1,
      password: '',
      repeatpassword: '',
      info: {}
    }
  },
  methods: {
    change: function (a) {
      if (a === 0) {
        this.model = 1
        this.state = 1
        return this.model & this.state
      } else {
        this.model = 2
        this.state = 1
        return this.model & this.state
      }
    },
    check_step1: function (a) {
      Axios
        .post(this.utils.baseURL + '/change1', 'username=' + this.username)
        .then(response => {
          this.info = response.data
          if (this.info.state === 'success') {
            if (a === 1) {
              this.model = 1
              this.state = 2
              this.email = this.info.email
              return this.model & this.state
            } else {
              this.model = 2
              this.state = 2
              this.phone = this.info.phone
              return this.model & this.state
            }
          } else {
            this.text = '账号不存在'
            this.snackbar = true
          }
        })
    },
    check_step2: function (a) {
      Axios
        .post(this.utils.baseURL + '/change2', 'code=' + this.code)
        .then(response => {
          if (response.data === 'success') {
            if (a === 1) {
              this.model = 1
              this.state = 3
              return this.model & this.state
            } else {
              this.model = 2
              this.state = 3
              return this.model & this.state
            }
          } else {
            this.text = '验证码错误'
            this.snackbar = true
          }
        })
    },
    check_step3 () {
      if (this.password !== this.repeatpassword) {
        this.text = '密码不一致!'
        this.snackbar = true
      } else {
        Axios
          .post(this.utils.baseURL + '/change3', 'username=' + this.username + 'password=' + this.password)
          .then(response => {
            if (response.data === 'success') {
              alert('success')
            }
          })
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
  top: 55%;
  transform: translate(-50%, -50%);
  height: 550px;
  width: 400px;
}

.cover {
  position: absolute;
  height: 90%;
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
input[type="number"] {
  -moz-appearance: textfield;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
</style>
