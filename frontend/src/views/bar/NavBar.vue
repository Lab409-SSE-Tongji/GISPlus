<template>
  <nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
      <ul class="nav metismenu" id="side-menu">
        <li class="nav-header">
          <div class="dropdown profile-element">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
              <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">用户: {{username}}</strong> </span> </span>
              <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">公司: {{organName }}</strong> </span> </span>
              <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">角色: {{roles | showRoles}}</strong> </span> </span>

            </a>
          </div>
          <div class="logo-element">
            G+
          </div>
        </li>

        <li :class="{active:0===activeItem}" @click="itemClick(0)">
          <router-link to="/mapManager"><i class="fa fa-map-o"></i><span class="nav-label">地图管理</span></router-link>
        </li>
        <li :class="{active:1===activeItem}" @click="itemClick(1)">
          <router-link to="/mapShowManager"><i class="fa fa-map-o"></i><span class="nav-label">地图展示</span></router-link>
        </li>
        <li :class="{active:2===activeItem}" @click="itemClick(2)" v-show="userManagerShow">
          <router-link to="/userManager"><i class="fa fa-address-book"></i><span class="nav-label">用户管理</span></router-link>
        </li>
        <li :class="{active:3===activeItem}" @click="itemClick(3)" v-show="organManagerShow">
          <router-link to="/organManager"><i class="fa fa-address-book"></i><span class="nav-label">组织管理</span></router-link>
        </li>
        <li :class="{active:4===activeItem}" @click="itemClick(4)">
          <router-link to="/userProfile"><i class="fa fa-user-o"></i><span class="nav-label">个人信息</span></router-link>
        </li>
        <li :class="{active:5===activeItem}" @click="itemClick(5)">
          <router-link to="/recycle"><i class="fa fa-recycle"></i><span class="nav-label">回收站</span></router-link>
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
        organName: null,
      }
    },
    computed: {
      ...mapGetters({
        username: 'username',
        organId: 'organId',
        roles: 'roles',
      }),
      organManagerShow: function () {
        return this.roles === 'superAdmin' || this.roles[0] === 'superAdmin'
      },
      userManagerShow: function () {
        return this.roles !== 'user' && this.roles[0] !== 'user'
      },
    },
    filters: {
      showRoles: function (value) {
        if (typeof value === 'string') {
          switch (value)
          {
            case 'superAdmin':
              return '超级管理员'
            case 'admin':
              return '管理员'
            case 'user':
              return '用户'
          }
        } else {
          switch (value[0]) {
            case 'superAdmin':
              return '超级管理员'
            case 'admin':
              return '管理员'
            case 'user':
              return '用户'
          }
        }
      },
    },
    methods: {
      itemClick: function(index){
        this.activeItem = index;
      },
      getOrganName: function () {
        console.log(this.organId)
        if (this.organId === '' || this.organId === null) {
          this.organName = 'Root'
        } else {
          this.$http.get(global.server+'/organ/'+this.organId).then(response => {
            if (response.bodyText === '') {
              this.organName = 'Root'
            } else {
              this.organName = JSON.parse(response.bodyText).name
            }
          }, response => {

          })
        }
      }
    },
    created () {
        this.getOrganName()
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .nav li{
    overflow: hidden;
  }
</style>
