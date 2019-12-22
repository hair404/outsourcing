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
import Account from './views/Account.vue'
import Forget from './views/Forget.vue'
import ManageLogin from './views/ManageLogin.vue'
import Manage from './views/Manage.vue'
import TopBar from './views/TopBar.vue'
import Users from './views/Users.vue'
import Advertise from './views/Advertise.vue'
import Acount from './views/Acount.vue'
import Activity from './views/Activity.vue'
import Classification from './views/Classification.vue'
import ManageApp from './views/ManageApp.vue'
import ManageRegister from './views/ManageRegister.vue'

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
          path: '/display/:id',
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
          path: '/detail/:id',
          name: 'detail',
          component: Detail
        }
      ]
    },
    {
      path: '/ManageApp',
      name: 'ManageApp',
      component: ManageApp,
      children: [
        {
          path: '/TopBar',
          name: 'TopBar',
          component: TopBar,
          children: [
            {
              path: '/ManageLogin',
              name: 'ManageLogin',
              component: ManageLogin
            }
          ]
        },

        {
          path: '/Manage',
          name: 'Manage',
          component: Manage,
          redirect: { path: '/Manage/Users' },
          children: [
            {
              path: 'ManageRegister',
              name: 'ManageRegister',
              component: ManageRegister
            },
            {
              path: 'Users',
              name: 'Users',
              component: Users
            },
            {
              path: 'Advertise',
              name: 'Advertise',
              component: Advertise
            },
            {
              path: 'Acount',
              name: 'Acount',
              component: Acount
            },
            {
              path: 'Activity',
              name: 'Activity',
              component: Activity
            },
            {
              path: 'Classification',
              name: 'Classification',
              component: Classification
            }
          ]
        }
      ]
    },
    {
      path: '/account',
      name: 'account',
      component: Account,
      children: [
        {
          path: '/login',
          name: 'login',
          component: Login
        },
        {
          path: '/register',
          name: 'register',
          component: Register
        },
        {
          path: '/forget',
          name: 'forget',
          component: Forget
        }
      ]
    }
  ]
})
