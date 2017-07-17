<template>
  <div id="wrapper">

    <!--主体展示块-->
    <div class="wrapper-content animated fadeInRight  white-bg">
      <!--loading 部分-->
      <div class="loading" v-if="loading">
        <div class="sk-circle">
          <div class="sk-circle1 sk-child"></div>
          <div class="sk-circle2 sk-child"></div>
          <div class="sk-circle3 sk-child"></div>
          <div class="sk-circle4 sk-child"></div>
          <div class="sk-circle5 sk-child"></div>
          <div class="sk-circle6 sk-child"></div>
          <div class="sk-circle7 sk-child"></div>
          <div class="sk-circle8 sk-child"></div>
          <div class="sk-circle9 sk-child"></div>
          <div class="sk-circle10 sk-child"></div>
          <div class="sk-circle11 sk-child"></div>
          <div class="sk-circle12 sk-child"></div>
        </div>
      </div>

      <!--文件-->
      <div class="ibox-div-map">
        <div class="ibox ibox-map" v-for="(map, index) in maps">
          <div class="img-box img-box-map" @click="enterMap(index)">
            <img class="map-img" src="../../assets/defaultMap.jpg">
          </div>
          <label class="name">{{map.mapName}}</label>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    name: 'manManager',
    data () {
        return {
          loading: true,
          maps: [],
        }
    },
    computed: {
      ...mapGetters({
        userId: 'userId',
        roles: 'roles',
      })
    },
    methods: {
      // 超级管理员获取地图
      getAllMaps: function () {
        this.$http.get(global.server+'/map/allMaps').then(response => {
          this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
          this.loading = false
          // toastr.success("获取用户地图成功")
        }, response => {
          toastr.error("获取用户地图失败")
        })
      },
      // 组织管理员获取地图
      getMapsByOrganId: function () {
        let formData = new FormData();
        formData.append('organId', this.organId)
        this.$http.get(global.server+'/map/organMaps', formData).then(response => {
          this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
          this.loading = false
          // toastr.success("获取用户地图成功")
        }, response => {
          toastr.error("获取用户地图失败")
        })
      },
      // 用户获取地图
      getMapsByUserId: function () {
        let formData = new FormData();
        formData.append('userId', this.userId)
        this.$http.get(global.server+'/map/userMaps', formData).then(response => {
          this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
          this.loading = false
          // toastr.success("获取用户地图成功")
        }, response => {
          toastr.error("获取用户地图失败")
        })
      },

      getMaps: function () {
        if (typeof this.roles === 'string') {
          switch (this.roles) {
            case 'superAdmin':
              this.getAllMaps()
              break
            case 'admin':
              this.getMapsByOrganId()
              break
            case 'user':
              this.getMapsByUserId()
              break
          }
        } else {
          switch (this.roles[0]) {
            case 'superAdmin':
              this.getAllMaps()
              break
            case 'admin':
              this.getMapsByOrganId()
              break
            case 'user':
              this.getMapsByUserId()
              break
          }
        }
      },
      enterMap: function (index) {
        this.$router.push('/show/'+this.maps[index].id)
      }
    },
    created () {
      this.getMaps()
    }
  }
</script>

<style>

</style>
