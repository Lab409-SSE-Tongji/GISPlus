import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/Login'
import Signup from '@/views/Signup'

import MainPage from '@/views/MainPage'

import Welcome from '@/views/context/Welcome'
import MapManager from '@/views/context/MapManager'
import MapShowManager from '@/views/context/MapShowManager'
import MapShow from '@/views/context/MapShow'
import Recycle from '@/views/context/Recycle'
import UserManager from '@/views/context/UserManager'
import UserProfile from '@/views/context/UserProfile'

import MapEditor from '@/views/context/MapEditor'

Vue.use(Router)

const router = new Router({
  routes: [
    // 重定向 优化路由
    {
      path: '/',
      redirect: '/mainPage'
    },
    {
      path: '/mapManager',
      redirect: '/mainPage/mapManager'
    },
    {
      path: '/edit/:mapId',
      redirect: '/mainPage/edit/:mapId'
    },
    {
      path: '/mapShowManager',
      redirect: '/mainPage/mapShowManager'
    },
    {
      path: '/show/:mapId',
      redirect: '/mainPage/show/:mapId'
    },
    {
      path: '/userManager',
      redirect: '/mainPage/userManager'
    },
    {
      path: '/userProfile',
      redirect: '/mainPage/userProfile'
    },
    {
      path: '/recycle',
      redirect: '/mainPage/recycle'
    },

    // 配置路由
    {
      path: '/login',
      name:'login',
      components: {
        root: Login
      }
    },
    {
      path: '/signup',
      name: 'signup',
      components:{
        root: Signup
      }
    },
    {
      path: '/mainPage',
      name: 'mainPage',
      redirect: '/mainPage/welcome',  // 默认子页面
      components: {
        root: MainPage,
      },
      children: [
        {
          path: 'welcome',
          name: 'welcome',
          components: {
            context: Welcome
          }
        },
        {
          path: 'mapManager',
          name: 'mapManager',
          components: {
            context: MapManager
          }
        },
        {
          path: 'edit/:mapId',
          name: 'edit',
          components: {
            context: MapEditor
          }
        },
        {
          path: 'mapShowManager',
          name: 'mapShowManager',
          components: {
            context: MapShowManager
          }
        },
        {
          path: 'show/:mapId',
          name: 'show',
          components: {
            context: MapShow
          }
        },
        {
          path: 'userManager',
          name: 'userManager',
          components: {
            context: UserManager
          }
        },
        {
          path: 'userProfile',
          name: 'userProfile',
          components: {
            context: UserProfile
          }
        },
        {
          path: 'recycle',
          name: 'recycle',
          components: {
            context: Recycle
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
