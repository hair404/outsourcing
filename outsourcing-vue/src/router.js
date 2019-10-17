import Vue from 'vue'
import Router from 'vue-router'
import Register from './views/Register.vue'
import Index from './views/Index.vue'
import Home from './views/Home.vue'
import Search from './views/Search.vue'
import Edit from './views/Edit'
import Display from './views/Display'
import Center from './views/Center'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index,
      children: [
        { path: '/home', name: 'home', component: Home },
        {
          path: '/search',
          name: 'search',
          component: Search
        },
        {
          path: '/edit',
          name: 'edit',
          component: Edit
        },
        {
          path: '/display',
          name: 'display',
          component: Display
        },
        {
          path: '/center',
          name: 'center',
          component: Center
        }
      ]
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    }
  ]
})
