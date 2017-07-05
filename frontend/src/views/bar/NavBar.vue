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
            GIS+
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
    name: "",
    data () {
      return {
        activeItem:0,
        items: [
          {
            target: '/mapManage',
            title: ' 地图管理',
            iconName: 'fa-map-o'
          },
          {
            target: '/userManage',
            title: '用户管理',
            iconName: 'fa-address-book'
          },
          {
            target: '/profile',
            title: '个人信息',
            iconName: 'fa-recycle'
          },
          {
            target: '/recycle',
            title: '回收站',
            iconName: 'fa-recycle'
          },
        ]
      }
    },
    computed: {
      ...mapGetters({
        username: 'username'
      })
    },
    methods: {
      itemClick: function(index){
        this.activeItem = index;
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .nav li{
    overflow: hidden;
  }
</style>
