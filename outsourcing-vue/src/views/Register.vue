<template>

  <v-card
    style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -40%);"
    width="500px"
  >
    <v-card-title>
      <v-btn icon>
        <v-icon @click="$router.push('/login')">mdi-arrow-left</v-icon>
      </v-btn>注册
    </v-card-title>
    <v-card-text class="pb-0">
      <v-form
        id="form"
        class="content"
      >
        <v-select
          :items="[{ text: '企业', value: 0 },{ text: '工作室', value: 1 }]"
          v-model="type"
          label="点击以选择"
          filled
          outlined
        />
        <v-text-field
          v-model="username"
          :label="userTypeName[type]"
          type="text"
          required
          filled
        />
        <v-text-field
          v-model="name"
          label="联系人姓名"
          type="text"
          required
          filled
        />
        <v-text-field
          v-model="phone"
          label="电话"
          type="text"
          style="width: 70%"
          class="d-inline-block mr-4"
          required
          filled
        />
        <v-btn
          color="primary"
          width="25%"
          @click="send()"
        >发送验证码</v-btn>
        <v-text-field
          v-model="code"
          label="手机验证码"
          type="text"
          required
          filled
        />
        <v-text-field
          v-model="password"
          label="密码"
          type="password"
          required
          filled
        />
        <v-text-field
          v-model="repeatpassword"
          label="重复密码"
          type="password"
          @click="check"
          required
          filled
        />
        <v-text-field
          v-model="email"
          label="邮箱"
          type="email"
          required
          filled
        />
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn
        width="100%"
        color="primary"
        @click="submit()"
      >注册</v-btn>
    </v-card-actions>
  </v-card>

</template>

<script>
import axios from 'axios'

export default {
  name: 'register',
  props: {
    snackbar: Object
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
      text: '',
      code: '',
      type: 0
    }
  },
  methods: {
    submit: function () {
      if (this.password !== this.repeatpassword) {
        this.snackbar.text = '密码不匹配!'
        this.snackbar.open = true
        return false
      }
      let data = new FormData()
      data.append('username', this.username)
      data.append('name', this.name)
      data.append('phone', this.phone)
      data.append('password', this.password)
      data.append('email', this.email)
      data.append('type', this.type)
      data.append('code', this.code)
      axios
        .post(this.utils.baseURL + '/register', data)
        .then(response => {
          if (response.data === 'success') {
            this.$router.push({ path: './home' })
            var OneSignal = window.OneSignal || []
            OneSignal.getUserId(function (userId) {
              axios.post(this.utils.baseURL + '/token', userId)
                .then(response => {
                  if (response.data !== 'success') console.log('token上传失败')
                })
                .catch(error => {
                  console.log(error)
                })
            })
          }
        })
        .catch(function (error) {
          console.log(error)
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    check: function () {
      if (this.password.length < 6) {
        this.snackbar.text = '密码太短!'
        this.snackbar.open = true
      }
    },
    send () {
      axios
        .post(this.utils.baseURL + '/getverifycode' + '?tel=' + this.phone)
        .then(response => {
          if (response.data === 'success') {
            this.snackbar.text = '发送成功!'
            this.snackbar.open = true
          }
        }).catch(error => {
          console.log(error)
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    }
  }
}

</script>

<style scoped lang="scss">
// .title {
//   top: 10%;
// }

// .body {
//   background-image: url('../assets/backimg.jpg');
//   height: 100%;
//   width: 100%;
//   position: absolute;
//   background-position: center;
//   overflow: hidden;
// }

// form {
//   top: 55%;
// }

// form button {
//   width: 100%;
//   margin-top: 20px;
//   height: 30px;
// }

// a {
//   text-decoration: none;
//   outline: none;
//   color: #000000;
// }

// .hover {
//   position: absolute;
//   left: 50%;
//   top: 50%;
//   transform: translate(-50%, -50%);
//   height: 550px;
//   width: 400px;
// }

// .cover {
//   position: absolute;
//   height: 100%;
//   width: 100%;
//   z-index: -1;
//   transition: 200ms;
//   background-color: rgba(255, 255, 255, 0.4);
//   border: transparent;
//   border-radius: 3px;
//   box-shadow: grey 0px 1px 4px;
// }

// .cover:hover {
//   box-shadow: grey 0px 1px 10px;
// }

// .blur {
//   position: absolute;
//   overflow: hidden;
//   height: 100%;
//   width: 100%;
//   z-index: -2;
//   background-image: url('../assets/backimg.jpg');
//   filter: blur(4px);
//   background-position: center;
// }

// .title {
//   position: absolute;
//   left: 50%;
//   top: 7%;
//   transform: translate(-50%, -50%);
//   width: 300px;
//   text-align: center;
// }

// form {
//   position: absolute;
//   transform: translate(0, -50%);
//   width: 100%;
//   text-align: center;
// }

// input,
// select {
//   border: transparent;
//   background: rgba(255, 255, 255, 0.4);
//   border-radius: 3px;
//   transition: 200ms;
// }

// input:hover,
// select:hover {
//   background: white;
//   box-shadow: grey 0px 1px 4px;
// }

// .content {
//   padding-right: 10%;
//   padding-left: 10%;
//   padding-bottom: 20px;
//   height: fit-content;
// }

// .content select,
// .content input {
//   margin-top: 20px;
//   display: block;
//   width: 100%;
//   height: 42px;
// }

// h1 {
//   position: absolute;
//   top: 25px;
//   width: 100%;
//   text-align: center;
//   font-size: 16px;
// }

// .register {
//   cursor: pointer;
//   padding-top: 10px;
//   font-size: 15px;
// }

// .img {
//   cursor: pointer;
// }

// button {
//   padding: 5px;
//   border: transparent;
//   border-radius: 3px;
//   background: rgba(255, 255, 255, 0.4);
//   transition: 200ms;
// }

// button:hover {
//   background: white;
//   box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
// }
</style>
