import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/login'

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
