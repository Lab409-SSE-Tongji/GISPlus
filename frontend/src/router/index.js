import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/login'
import Signup from '@/views/signup'

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
  ]
})

router.beforeEach( (to, from, next) => {
    if (global.login == true) {
      next()
    } else {
      next('/')
    }
  }
)

export default router
