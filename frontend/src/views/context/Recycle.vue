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
          <div class="img-box img-box-map">
            <img class="map-img" src="../../assets/defaultMap.jpg">
          </div>
          <label class="name">{{map.mapName}}</label>
          <div class="checkBox-map">
          </div>
          <div class="op-map" @click="openOp(index)">
            <img class="op-icon" src="../../assets/op-icon.png">
          </div>
          <!--文件操作-->
          <ul class="op-list" v-show="map.opDisplay" style="left:113px; top:12px; display:block">
            <li @click="restoreMap(index)"><img src="../../assets/rename-icon.png"><label>还原</label></li>
            <li @click="deleteMap(index)"><img src="../../assets/delete-icon.png"><label>删除</label></li>
          </ul>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    name: 'recycle',
    data () {
      return {
        loading: true,
        maps: [],
      }
    },
    computed: {
      ...mapGetters({
        userId: 'userId',
        organId: 'organId',
        roles: 'roles',
      })
    },
    methods: {
      getRecyclesByUserId: function () {
        let params = {'userId': this.userId}
        this.$http.get(global.server+'/recycle/userRecycle', {params: params}).then(response => {
          this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
          this.loading = false
          // toastr.success("获取回收站地图成功")
        }, response => {
          toastr.error("获取回收站地图失败")
        })
      },
      getRecyclesByOrganId: function () {
        let params = {'organId': this.organId}
        this.$http.get(global.server+'/recycle/organRecycle', {params: params}).then(response => {
          this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
          this.loading = false
          // toastr.success("获取回收站地图成功")
        }, response => {
          toastr.error("获取回收站地图失败")
        })
      },
      getAllRecycles: function () {
        this.$http.get(global.server+'/recycle/allRecycle').then(response => {
          this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
          this.loading = false
          // toastr.success("获取回收站地图成功")
        }, response => {
          toastr.error("获取回收站地图失败")
        })
      },
      getRecycles: function () {
        if (typeof this.roles === 'string') {
          switch (this.roles)
          {
            case 'superAdmin':
              this.getAllRecycles()
              break
            case 'admin':
              this.getRecyclesByOrganId()
              break
            case 'user':
              this.getRecyclesByUserId()
              break
          }
        } else {
          switch (this.roles[0]) {
            case 'superAdmin':
              this.getAllRecycles()
              break
            case 'admin':
              this.getRecyclesByOrganId()
              break
            case 'user':
              this.getRecyclesByUserId()
              break
          }
        }
      },
      openOp: function (index) {
        this.maps[index].opDisplay = !this.maps[index].opDisplay
      },
      restoreMap: function (index) {
        this.$http.post(global.server+'/recycle/restore/'+this.maps[index].id).then(response => {
          // toastr.success("还原用户地图成功")
          this.getRecycles()
        }, response => {
          toastr.error("还原用户地图失败")
        })
      },
      deleteMap: function (index) {
        this.$http.delete(global.server+'/recycle/'+this.maps[index].id).then(response => {
          // toastr.success("删除用户地图成功")
          this.getRecycles()
        }, response => {
          toastr.error("删除用户地图失败")
        })
      }
    },
    created () {
      this.getRecycles()
    }
  }
</script>

<style>

</style>
