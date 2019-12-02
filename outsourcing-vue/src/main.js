import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import utils from './js/utils'
import GoEasy from 'goeasy'

Vue.config.productionTip = false

Vue.prototype.utils = utils

axios.defaults.crossDomain = true
axios.defaults.withCredentials = true

Vue.prototype.$goEasy = new GoEasy({
  host: 'hangzhou.goeasy.io', // 应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
  appkey: 'BS-a4ed6edd774b4e66bd3205a7788f85c5', // 替换为您的应用appkey
  forceTLS: false,
  onConnected: function () {
    console.log('连接成功！')
  },
  onDisconnected: function () {
    console.log('连接断开！')
  },
  onConnectFailed: function () {
    console.log('连接失败或错误！')
  }
})

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
