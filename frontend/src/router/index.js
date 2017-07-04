import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/login'
import Signup from '@/views/signup'

import MainPage from '@/views/mainpage'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name:'login',
      components: {
        login: Login
      }
    },
    {
      path: '/signup',
      name: 'signup',
      components:{
        login: Signup
      }
    },
    {
      path: '/mainpage',
      name: 'mainpage',
      components:{
        login: MainPage
      }
    },
  ]
})

router.beforeEach( (to, from, next) => {
    if (to.name === 'login') {
      next()
    }
    else if (global.login === true) {
      next()
    }
    else {
      console.log('还木有登录')
      next('login')
    }
  }
)

export default router
