import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/Login'
import Signup from '@/views/Signup'

import MainPage from '@/views/MainPage'

import MapManager from '@/views/context/MapManager'
import Recycle from '@/views/context/Recycle'
import UserManager from '@/views/context/UserManager'
import UserProfile from '@/views/context/UserProfile'

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
    {
      path: '/mapManager',
      name: 'mapManager',
      components:{
        context: MapManager
      }
    },
  ]
})

// 是否需要??
// router.beforeEach( (to, from, next) => {
//   // console.log(router.app.$store.state.session.login)
//     if (to.name === 'login') {
//       next()
//     }
//     else if (router.app.$store.state.session.login === true) {
//       next()
//     }
//     else {
//       console.log('还木有登录')
//       next('login')
//     }
//   }
// )

export default router
