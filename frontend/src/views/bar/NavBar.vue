<template>
  <nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
      <ul class="nav metismenu" id="side-menu">
        <li class="nav-header">
          <div class="dropdown profile-element">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
              <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">用户: {{username}}</strong> </span> </span>
            </a>
          </div>
          <div class="logo-element">
            G+
          </div>
        </li>

        <li v-for="(item, index) in items" :class="{active:index==activeItem}" @click="itemClick(index)">
          <router-link :to="item.target">
            <i class="fa" :class="item.iconName"></i>
            <span class="nav-label"> {{item.title}} </span>
          </router-link>
        </li>
      </ul>

    </div>
  </nav>

</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    name: "navBar",
    data () {
      return {
        activeItem:0,
        items: [],
      }
    },
    computed: {
      ...mapGetters({
        username: 'username',
        roles: 'roles',
      })
    },
    watch: {
      roles: function () {
        if (this.roles.includes('ROLE_ADMIN')) {
            this.items = [
              {
                target: '/mapManager',
                title: '地图管理 admin',
                iconName: 'fa-map-o'
              },
              {
                target: '/mapShowManager',    // todo 删除
                title: '地图管理 user',
                iconName: 'fa-map-o'
              },
              {
                target: '/userManager',
                title: '用户管理',
                iconName: 'fa-address-book'
              },
              {
                target: '/userProfile',
                title: '个人信息',
                iconName: 'fa-user-o'
              },
              {
                target: '/recycle',
                title: '回收站',
                iconName: 'fa-recycle'
              },
            ]
        } else if (this.roles.includes('ROLE_USER')) {
          this.items = [
            {
              target: '/mapShowManager',
              title: '地图管理 user',
              iconName: 'fa-map-o'
            },
            {
              target: '/userProfile',
              title: '个人信息',
              iconName: 'fa-user-o'
            },
          ]
        }
      }
    },
    methods: {
      itemClick: function(index){
        this.activeItem = index;
      }
    },
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .nav li{
    overflow: hidden;
  }
</style>
