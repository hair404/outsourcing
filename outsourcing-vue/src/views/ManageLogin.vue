<template>
  <v-card
    style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);"
    width="400px"
  >
    <v-card-title>登录</v-card-title>
    <v-card-text class="pb-0">
      <v-form id="login">
        <v-text-field
          v-model="manage.username"
          id="username"
          name="username"
          type="text"
          label="手机号"
          :rules="rules"
        />
        <v-text-field
          v-model="manage.password"
          id="password"
          name="password"
          type="password"
          label="密码"
          :rules="rules"
        />
        <v-select
          :items="[
            { text: '管理员', value: 0 },
            { text: '专家', value: 1 }
          ]"
          v-model="manage.type"
          label="点击以选择"
        />
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn width="100%" @click="submit" color="light-blue">登录</v-btn>
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
      rules: [v => !!v || 'Name is required'],
      manage: {
        username: '',
        password: '',
        type: 0
      }
    }
  },
  methods: {
    submit: function() {
      this.$router.push({ path: '/Manage' })
      // this.axios
      //   .post('/login', this.qs.stringify(this.manage))
      //   .then(response => {
      //     if (response.data === 'success') {
      //       this.$router.push({ path: '/Manage' })
      //     } else {
      //       console.log(this)
      //       this.snackbar.text = '账号或密码错误'
      //       this.snackbar.open = true
      //       this.snackbar.color = 'error'
      //     }
      //   })
      //   .catch(error => {
      //     this.snackbar.text = '服务器错误'
      //     this.snackbar.open = true
      //     this.snackbar.color = 'error'
      //     console.log(error)
      //   })
    }
  }
}
</script>
