import ElementUI from 'element-ui' //新添加
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from '../node_modules/axios'
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI)   //新添加

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
}).$mount('#app')
