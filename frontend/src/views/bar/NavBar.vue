<template>
  <nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
      <ul class="nav metismenu" id="side-menu">
        <li class="nav-header">
          <div class="dropdown profile-element">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
              <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">用户: {{username}}</strong> </span> </span>
              <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">公司: {{organId | showOrgan}}</strong> </span> </span>
              <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">角色: {{roles | showRoles}}</strong> </span> </span>

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
        organId: 'organId',
        roles: 'roles',
      })
    },
    filters: {
      showRoles: function (value) {
        switch (value[0]) {
          case 'superAdmin':
              return '超级管理员'
          case 'admin':
              return '管理员'
          case 'user':
              return '用户'
        }
      },
      showOrgan: function (value) {
        if (value) {

        } else {
          return 'Root'
        }
      }
    },
    watch: {
      roles: function () {
        this.items = [
          {
            target: '/mapManager',
            title: '地图管理',
            iconName: 'fa-map-o'
          },
          {
            target: '/mapShowManager',
            title: '地图展示',
            iconName: 'fa-map-o'
          },
          {
            target: '/userManager',
            title: '用户管理',
            iconName: 'fa-address-book'
          },
          {
            target: '/organManager',
            title: '组织管理',
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
