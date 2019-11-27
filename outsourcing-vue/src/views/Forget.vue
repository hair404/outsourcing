<template>
  <v-card
    style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);"
    width="500px"
  >
    <v-card-title>
      <v-btn icon>
        <v-icon @click="$router.push('/login')">mdi-arrow-left</v-icon>
      </v-btn>找回密码
    </v-card-title>
    <v-window
      v-model="window"
      class="elevation-1"
    >
      <v-window-item>
        <v-card-text class="pb-0">
          <v-text-field
            v-model="number"
            name="name"
            id="account"
            type="text"
            label="电话或者E-mail"
            required
            filled
          />
        </v-card-text>
        <v-card-actions>
          <v-btn
            width="100%"
            @click="getCode()"
            color="primary"
          >收取验证码</v-btn>
        </v-card-actions>
      </v-window-item>

      <v-window-item>
        <v-card-text class="pb-0">
          <v-text-field
            v-model="code"
            type="text"
            label="填入验证码"
            required
            filled
          />
        </v-card-text>
        <v-card-actions>
          <v-btn
            width="100%"
            @click="check()"
            color="primary"
          >提交验证</v-btn>
        </v-card-actions>
      </v-window-item>

      <v-window-item>
        <v-card-text class="pb-0">
          <v-text-field
            v-model="password"
            type="text"
            label="重设密码"
            required
            filled
          />
        </v-card-text>
        <v-card-actions>
          <v-btn
            width="100%"
            @click="reset()"
            color="primary"
          >提交</v-btn>
        </v-card-actions>
      </v-window-item>
    </v-window>
  </v-card>
</template>

<script>
import Axios from 'axios'
export default {
  props: {
    snackbar: Object
  },
  data () {
    return {
      window: 0,
      code: '',
      number: ''
    }
  },
  methods: {
    getCode () {
      Axios.post(this.utils.baseURL + '/getverifycode', 'tel=' + this.number)
        .then(response => {
          this.window += 1
        })
        .catch((error) => {
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
          console.log(error)
        })
    },
    check () {
      Axios.post(this.utils.baseURL + '/checkverifycode', 'verifyCode=' + this.code)
        .then(response => {
          if (response.data === 'success')
            this.window += 1
        })
        .catch((error) => {
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
          console.log(error)
        })
    },
    reset () {
      Axios.post(this.utils.baseURL + '/changepassword', 'password=' + this.password)
        .then(response => {
          if (response.data === 'success')
            this.$router.push('/login')
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
