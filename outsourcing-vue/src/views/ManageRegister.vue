<template>
  <v-card
    style="position: absolute;top: 40%;left: 50%;transform: translate(-50%, -50%);"
    width="400px"
  >
    <v-card-title>
      <v-btn color="light-blue" icon to="/Manage/Users" class="ml-n2">
        <v-icon class="mt-1">mdi-chevron-left-circle-outline</v-icon> </v-btn
      >注册管理员</v-card-title
    >
    <v-card-text class="pb-0">
      <v-form id="register" ref="register">
        <v-text-field
          v-model="register.username"
          id="username"
          name="username"
          type="text"
          label="手机号"
          :rules="rules"
        />
        <v-text-field
          v-model="register.password"
          id="password"
          name="password"
          type="password"
          label="密码"
          :rules="rules"
        />
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn width="100%" @click="submit" color="light-blue">注册</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: {
    snackbar: Object
  },
  data() {
    return {
      rules: [v => !!v || 'This is required'],
      register: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    submit: function() {
      this.axios
        .post('/login', this.qs.stringify(this.register))
        .then(response => {
          if (response.data === 'success') {
            this.snackbar.text = '注册成功'
            this.snackbar.open = true
          }
        })
        .catch(error => {
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
          this.snackbar.color = 'error'
          console.log(error)
        })
    }
  }
}
</script>
