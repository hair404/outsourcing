import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import vuetify from './plugins/vuetify'
// import vuetify from './plugins/vuetify'

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
   vuetify,
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
}).$mount('#app')
