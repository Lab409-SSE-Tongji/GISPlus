<template>
  <div class="row border-bottom">
      <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
          <div class="navbar-header">
              <a class="minimalize-styl-2 btn btn-primary" @click="toggleMenu"><i class="fa fa-bars"></i> </a>
              <router-link :to="path.route" type="button" class="btn btn-sm btn-primary" style="margin-left: 20px; margin-top: 14px">{{path.title}}</router-link>
              <!--<button type="button" class="btn btn-sm btn-primary" style="margin-left: 5px; margin-top: 14px" v-show="recycle" @click="cleanRecycle()">清空回收站</button>-->
          </div>
          <ul class="nav navbar-top-links navbar-right">
              <li>
                  <router-link to="/login" class="fa fa-sign-out" @click.native="logout">登出</router-link>
              </li>
          </ul>

      </nav>
  </div>
</template>

<script>
import {mapGetters, mapMutations} from 'vuex'
export default {
  name: 'headBar',
  computed: {
    ...mapGetters({
      userId: 'userId'
    }),
    path: function () {
      let title = null
      let route = null
      switch (this.$route.name) {
        case 'welcome':
          title = '欢迎XTD'
          route = '/welcome'
          break
        case 'mapManager':
          title = '地图管理'
          route = '/mapManager'
          break
        case 'edit':
          title = '地图管理'
          route = '/mapManager'
          break
        case 'mapShowManager':
          title = '地图管理'
          route = '/mapShowManager'
          break
        case 'show':
          title = '地图管理'
          route = '/mapShowManager'
          break
        case 'userManager':
          title = '用户管理'
          route = '/userManager'
          break
        case 'organManager':
          title = '组织管理'
          route = '/organManager'
          break
        case 'userProfile':
          title = '个人信息'
          route = '/userProfile'
          break
        case 'recycle':
          title = '回收站'
          route = '/recycle'
          break
      }
      return {title, route}
    },
    recycle: function () {
      return this.path.route === '/recycle'
    }
  },
  methods: {
    ...mapMutations({
      s_logout: 'LOG_OUT'
    }),

    logout: function () {
      this.s_logout()
      this.$router.push('/');
    },

    toggleMenu: function () {
      $("body").toggleClass("mini-navbar");
    },

    cleanRecycle: function () {
      this.$http.delete(global.server+'/recycle/all/'+this.userId).then(response => {
        toastr.success("清空回收站成功")
        // todo 向回收站传递消息 组件间通讯 mutation?
      }, response => {
        toastr.error("清空回收站失败")
      })
    }
  },
  created () {
//    this.toggleMenu()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
