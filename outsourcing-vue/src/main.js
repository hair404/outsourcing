import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import Axios from 'axios'

Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

var OneSignal = window.OneSignal || []
OneSignal.push(function () {
  OneSignal.init({
    appId: 'c386c41e-fba0-4c27-b561-10d8a6d9b13c'
  })
  OneSignal.on('subscriptionChange', function (isSubscribed) {
    OneSignal.getUserId(function (userId) {
      Axios.post('/Platform/token', userId)
        .then(response => {
          if (response.data !== 'success') console.log('token上传失败')
        })
        .catch(error => {
          console.log(error)
        })
    })
  })
})
