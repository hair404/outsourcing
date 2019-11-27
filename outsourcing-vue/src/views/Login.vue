<template>

  <v-card
    style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);"
    width="400px"
  >
    <v-card-title>登录</v-card-title>
    <v-card-text class="pb-0">
      <v-form id="login">
        <v-text-field
          v-model="username"
          name="name"
          id="account"
          type="text"
          label="账号或者E-mail"
          required
          filled
        />
        <v-text-field
          v-model="password"
          name="password"
          id="password"
          type="password"
          label="密码"
          required
          filled
        />
        <v-select
          :items="[{ text: '企业', value: 0 },{ text: '工作室', value: 1 }]"
          v-model="type"
          label="点击以选择"
          filled
        />
        <v-text-field
          v-model="code"
          style="display: inline-block;width: 230px"
          name="code"
          label="验证码"
          required
          filled
        />
        <v-img
          width="100"
          height="50"
          class="d-inline-block"
          style="margin-left: 28px;float: right"
          :src="utils.baseURL+'/code'"
        />
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn
        width="100%"
        @click="submit"
        color="primary"
      >登录</v-btn>
    </v-card-actions>
    <v-card-actions>
      <v-btn
        text
        @click="$router.push('/register')"
      >
        注册
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
        text
        @click="$router.push('/forget')"
      >
        忘记密码
      </v-btn>
    </v-card-actions>
  </v-card>

</template>

<script>
import axios from 'axios'
export default {
  name: 'Login',
  props: {
    snackbar: Object
  },
  data () {
    return {
      username: '',
      password: '',
      code: '',
      type: 0
    }
  },
  methods: {
    submit: function (event) {
      let data = new FormData()
      data.append('name', this.username)
      data.append('password', this.password)
      data.append('type', this.type)
      data.append('code', this.code)
      axios
        .post(this.utils.baseURL + '/login', data)
        .then(response => {
          if (response.data === 'success') {
            this.$router.push({ path: '/home' })
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
          } else {
            console.log(this)
            this.snackbar.text = '密码错误或者未注册'
            this.snackbar.open = true
          }
        })
        .catch((error) => {
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
          console.log(error)
        })
    }
  }
}

</script>
<style scoped lang="scss">
// .backimg {
//   background-image: url('../assets/backimg.jpg');
//   height: 100%;
//   width: 100%;
//   position: absolute;
//   background-position: center;
//   overflow: hidden;
// }
// .logintitle {
//   float: left;
//   ul {
//     color: #2c3e50;
//     padding: 0 0 0 35px;
//     text-align: center;
//   }
// }

// .logintitle ul {
//   list-style: none;
// }
// .pagetitle {
//   float: left;
//   text-align: center;
//   line-height: 42px;
//   padding-left: 10px;
//   padding-right: 10px;
//   transition: 200ms;
// }
// .pagetitle:hover {
//   cursor: pointer;
//   background-color: rgba(255, 255, 255, 0.2);
// }
// .hover {
//   position: absolute;
//   left: 50%;
//   top: 50%;
//   transform: translate(-50%, -50%);
//   height: 500px;
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
//   top: 10%;
//   transform: translate(-50%, -50%);
//   width: 300px;
//   text-align: center;
//   font-size: 25px;
// }

// #login {
//   position: absolute;
//   top: 55%;
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
//   padding-left: 10%;
//   padding-right: 10%;
//   padding-bottom: 20px;
//   height: fit-content;
// }

// .content select,
// .content input,
// .content > div {
//   margin-top: 20px;
//   display: block;
//   width: 100%;
//   height: 50px;
// }

// .button a {
//   padding: 5px 150px;
//   border: transparent;
//   border-radius: 3px;
//   background: rgba(255, 255, 255, 0.4);
//   transition: 200ms;
// }

// .button a:hover {
//   background: white;
//   box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
// }
// .button1 {
//   padding: 5px 150px;
//   border: transparent;
//   border-radius: 3px;
//   background: rgba(255, 255, 255, 0.4);
//   transition: 200ms;
//   cursor: pointer;
// }

// .button1:hover {
//   background: white;
//   box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
// }
</style>
