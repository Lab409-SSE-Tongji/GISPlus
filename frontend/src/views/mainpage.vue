<template>
  <div id="app-wrapper">
  <navBar></navBar>
  <div id="page-wrapper" class="gray-bg">
    <headBar></headBar>
    <router-view name="context"></router-view>
    <!--<footBar></footBar>-->
    </div>
  </div>
</template>

<script>
  import HeadBar from '@/views/bar/HeadBar'
  import NavBar from '@/views/bar/NavBar'
  import FootBar from '@/views/bar/FootBar'
  import {mapGetters, mapMutations} from 'vuex'

  export default {
    name: 'mainpage',
    computed: {
      ...mapGetters({
        username: "username"
      }),
    },
    methods: {
      ...mapMutations({
        userInit: 'USER_INIT'
      })
    },
    components: {
      'headBar': HeadBar,
      'navBar': NavBar,
      'footBar': FootBar
    },
    created () {
      this.$http.get(global.server+'/user/'+this.username).then(response => {
        this.userInit(JSON.parse(response.bodyText))
        toastr.success("登录成功")
      }, response => {
        toastr.error("获取用户信息失败")
      })
    }
  }
</script>
