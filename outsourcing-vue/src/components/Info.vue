<template>
  <div>
    <v-dialog
      width="400"
      v-model="dialog"
    >
      <v-card>
        <v-card-title>修改密码</v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-text-field
              v-model="password"
              label="输入密码"
              type="password"
              :rules="[v => !!v || '必需项',]"
              required
            ></v-text-field>
            <v-text-field
              v-model="repassword"
              label="重复密码"
              type="password"
              :rules="[v => v===password || '密码不一致',]"
              required
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="change()"
          >提交</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-form class="wrapper">
      <template v-for="(item,i) in editinfo">
        <v-text-field
          v-if="i !== 2"
          :key="i"
          v-model="item.value"
          type="text"
          :label="item[Object.keys(item)[0]]"
          required
          filled
        />
        <div
          :key="i"
          style="height: 70px;overflow-x: auto;"
          v-else-if="info.type === 1"
        >
          <div style="display: inline-flex">
            <v-chip
              v-for="(tag,i) in item.value"
              class="ma-2"
              ripple
              close
              outlined
              :key="tag"
              @click:close="item.value.splice(i, 1)"
            >{{utils.ctg[tag + 1].name}}</v-chip>
            <v-menu offset-y>
              <template v-slot:activator="{ on }">
                <v-chip
                  class="ma-2"
                  ripple
                  outlined
                  v-on="on"
                >
                  <v-icon>mdi-plus</v-icon>
                </v-chip>
              </template>
              <v-list>
                <v-list-item
                  v-for="(item, i) in utils.ctg.slice(1)"
                  :key="i"
                  @click="editinfo[2].value.push(i)"
                >
                  <v-list-item-title>{{ item.name }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>
      </template>
      <v-btn
        text
        color="red"
        class="mr-4"
        @click="dialog = true"
      >修改密码</v-btn>
      <v-btn
        @click="upload()"
        color="primary"
      >
        保存
      </v-btn>
    </v-form>
  </div>
</template>

<script>
import utils from '../js/utils'
import Axios from 'axios'
import qs from 'qs'

export default {
  name: 'Info',
  props: {
    info: Object,
    myinfo: Object,
    snackbar: Object
  },
  data () {
    return {
      utils: utils,
      editinfo: [
        { username: this.info.type === 0 ? '公司名' : '工作室名称', value: this.myinfo.username },
        { name: '真实名字', value: this.myinfo.name },
        { tag: '标签', value: this.myinfo.tag },
        { email: '电子邮件', value: this.myinfo.email },
        { info: '介绍', value: this.myinfo.info }
      ],
      password: '',
      repassword: '',
      dialog: false
    }
  },
  methods: {
    upload () {
      var temp = {}
      this.editinfo.forEach(item => {
        temp[Object.keys(item)[0]] = item.value
      })
      if (this.info.type === 1)
        temp['tag'] = JSON.stringify(this.editinfo[2].value)
      else
        temp['tag'] = undefined
      Axios.post(this.utils.baseURL + '/edit', qs.stringify(temp))
        .then(response => {
          if (response.data === 'success') {
            this.snackbar.color = 'green'
            this.snackbar.text = '修改成功'
            this.snackbar.open = true
          }
        }).catch(error => {
          console.log(error)
          this.snackbar.color = 'error'
          this.snackbar.text = '服务器错误'
          this.snackbar.open = true
        })
    },
    change () {
      if (this.$refs.form.validate())
        Axios.post(this.utils.baseURL + '/changepassword', 'password=' + this.password)
          .then(response => {
            if (response.data === 'success') {
              this.snackbar.color = 'green'
              this.snackbar.text = '修改成功'
              this.snackbar.open = true
            }
          }).catch(error => {
            console.log(error)
            this.snackbar.color = 'error'
            this.snackbar.text = '服务器错误'
            this.snackbar.open = true
          })
    },
    addCtg (i) {
      for (const item in this.editinfo[2].value) {
        if (item === i) {
          return
        }
      }
      this.editinfo[2].value.push(i)
    }
  }
}
</script>
