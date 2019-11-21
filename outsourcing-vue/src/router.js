import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import Index from './views/Index.vue'
import Detail from './views/Detail.vue'
import Home from './views/Home.vue'
import Center from './views/Center.vue'
import Edit from './views/Edit.vue'
import Search from './views/Search.vue'
import Show from './views/Show.vue'
import Manage from './views/Manage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index,
      redirect: { name: 'home' },
      children: [
        {
          path: '/home',
          name: 'home',
          component: Home
        },
        {
          path: '/center',
          name: 'center',
          component: Center
        },
        {
          path: '/display',
          name: 'display',
          component: Show
        },
        {
          path: '/edit',
          name: 'edit',
          component: Edit
        },
        {
          path: '/search',
          name: 'search',
          component: Search
        },
        {
          path: '/detail',
          name: 'detail',
          component: Detail
        },
        {
          path: '/manage',
          name: 'manage',
          component: Manage
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    }
  ]
})
