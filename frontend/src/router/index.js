import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/Login'
import Signup from '@/views/Signup'

import MainPage from '@/views/MainPage'

import MapManager from '@/views/context/MapManager'
import Recycle from '@/views/context/Recycle'
import UserManager from '@/views/context/UserManager'
import UserProfile from '@/views/context/UserProfile'

import MapEditor from '@/views/context/MapEditor'

Vue.use(Router)

const router = new Router({
  routes: [
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
      path: '/',
      redirect: '/mapManager',
      name: 'mainpage',
      components: {
        login: MainPage
      },
      children: [
        {
          path: 'mapManager',
          components: {
            context: MapManager
          },
          children: [
            {
              path: ':mapId',
              redirect: '/:mapId'
            }
          ]
        },
        {
          path: 'userManager',
          components: {
            context: UserManager
          }
        },
        {
          path: 'userProfile',
          components: {
            context: UserProfile
          }
        },
        {
          path: 'recycle',
          components: {
            context: Recycle
          }
        },
        {
          path: ':mapId',
          components: {
            context: MapEditor
          }
        },
      ]
    },
    {
      path: '*',
      name: 'others',
      redirect: '/'
    }
  ]
})

// 登录验证
router.beforeEach( (to, from, next) => {
  // 放行路由
  if (to.name === 'login' || to.name === 'signup') {
    next()
  }
  // 登录后正常跳转
  else if (router.app.$store.state.session.login === true) {
    next()
  }
  // 没有登录强制跳转登录页面
  else {
    console.log('还木有登录')
    next('login')
  }
})

export default router
